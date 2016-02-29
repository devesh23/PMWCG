package com.devesh.pmw;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Carlist extends ActionBarActivity implements AdapterView.OnItemClickListener {

	
	private DrawerLayout drawerLayout;
	private ListView listView;
	private String[] options;
	private ActionBarDrawerToggle drawerListener;
	
	public static int pos=-1;
	public static Carlist fa;
	Button button;
	ListView list;
	CarAdapter3 adapter;
private ProgressDialog pDialog;
private static String url_ = "http://pmwcg.com/mobi/specs.php";
	
	JSONParser jparser = new JSONParser() ;
	JSONArray response=null;
	private static int check;
	public static String model;
	public static String company;
	public static String condition;
	public static String car_image;
	public static String type_image;
	public static String car_id;

	public static String Company;
	public static String Model;
	public static String Type;
	public static String Title;
	public static String Reg_no;
	public static String Condition;
	public static String Year;
	public static String Mileage;
	public static String Fuel;
	public static String Engine;
	public static String Cylinder;
	public static String Fuel_type;
	public static String Default_Image;
	public static String Images;
	public static ArrayList<String> Car_images;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_carlist);
		if(MainActivity.success==1)
		{
			Toast.makeText(this,"Selected Type Not Availabe, List of all cars is displayed ",Toast.LENGTH_LONG).show();
		}
		 Car_images = new ArrayList<String>();
		 
		 
		 button = (Button) findViewById(R.id.filter_button);
		 button.setOnClickListener(new View.OnClickListener() {

	     	 
			 
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(),fliter.class);
		    	startActivity(intent);	
		    	finish();
			}			 
		 });
		 
		 options=getResources().getStringArray(R.array.options);
	        listView=(ListView) findViewById(R.id.navdrawerlistview);
	        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options));
	        
	        drawerLayout=(DrawerLayout) findViewById(R.id.drawer);
	        drawerListener = new ActionBarDrawerToggle(this, drawerLayout,R.drawable.ic_drawer1,R.string.drawer_open,R.string.drawer_close);
	        drawerLayout.setDrawerListener(drawerListener);
	        getSupportActionBar().setHomeButtonEnabled(true);
	        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					if (position == 0) {
						//Toast.makeText(this, options[position], Toast.LENGTH_LONG).show();
						Intent intent = new Intent(Carlist.this, MainActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
					}

					else if(position==1)
					{
						//Toast.makeText(this, options[position], Toast.LENGTH_LONG).show();
						Intent intent= new Intent(Carlist.this,RecentlyView.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();

					}
					else if(position==2)
					{
						new LoadAll2(Carlist.this).execute();

					}
					else
					{
						Intent intent=new Intent(Carlist.this,fragments.class);
						intent.putExtra("extra",position);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
					}
				}
			});
	        
	        
		 
		list = (ListView)findViewById(android.R.id.list);
		adapter = new CarAdapter3(getApplicationContext(), R.layout.list_item);
		list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				car_id=(String) MainActivity.Car_id.get(position);
				model= (String) adapter.getModel(position);
				company=(String) adapter.getComp(position);
				condition=(String) adapter.getCond(position);
				car_image=(String) adapter.getCar_image(position);
				
				SQLite.CompanyArray.clear();
				SQLite.ModelArray.clear();
				SQLite.ConditionArray.clear();
				SQLite.Car_imageArray.clear();
				
				boolean DiditWork = true;
				try{
				SQLite entry = new SQLite(Carlist.this);
				entry.open();
				entry.createEntry(company,model,condition,car_image,type_image);
				entry.close();
				}catch(Exception e){
					DiditWork=false;
				}
				new LoadAll().execute();		
			}	
        });
	}
	@Override
	public void onBackPressed()
	{
	    super.onBackPressed(); 
	    Intent in = new Intent(Carlist.this, MainActivity.class);
	    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    startActivity(in);
	    finish();

	}
	
	public void onConfigurationChanged(Configuration newConfig){
    	super.onConfigurationChanged(newConfig);
    	drawerListener.onConfigurationChanged(newConfig);
    }
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		drawerListener.syncState();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.carlist, menu);
		return true;
	}

	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	    	if(drawerListener.onOptionsItemSelected(item))
	        {
	        	return true;
	        }
	    	int id = item.getItemId();
	        /*if (id == R.id.action_settings) {
	            return true;
	        }*/
	        return super.onOptionsItemSelected(item);
		}
	
	
	
	private void selectItem(int position) {
		// TODO Auto-generated method stub
		listView.setItemChecked(position, true);
		setTitle(options[position]);
	}
	public void setTitle(String title)
	{
		getSupportActionBar().setTitle(title);
	}
	
	
	class LoadAll extends AsyncTask<String, String, String> {
		 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Carlist.this);
            pDialog.setMessage("Loading . Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

		@Override
		protected String doInBackground(String... args) {
			
			
			Car_images.clear();
			
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            
            params.add(new BasicNameValuePair("car_id", car_id));
		
            System.out.println("passes params");
            try{check =0;
            
            JSONObject json = jparser.makeHttpRequest(url_, "POST", params);
         
            System.out.println("passes Json Object");
            response = json.getJSONArray("data");       // These throws an EXCEPTION e
            
            
            
            for (int i = 0; i < response.length(); i++) {
                 JSONObject c = response.getJSONObject(i);
         
                // Storing each json item in variable
                 Company = c.getString("name");
                 Model= c.getString("model");
                 Type=c.getString("type");
                 Title = c.getString("title");
                 //Reg_no=c.getString("reg_no");
                 Condition=c.getString("condition");
                 Year=c.getString("year");
                 Mileage=c.getString("mileage");
                 Fuel=c.getString("fuel_economy");
                 Engine=c.getString("engine_size");
                 Cylinder=c.getString("cylinders");
                 Fuel_type=c.getString("fuel_type");
                 Default_Image=c.getString("default_image");
                 Images=c.getString("image");
                 
                 
                 Car_images.add(Images);
                 
            }        
            	
            
            }
            catch (JSONException e) {
                check=1;
            	e.printStackTrace();
            }

            catch(Exception e)
            {
            	System.out.println(e);
            	check=2;
            }
			return null;
		}
		
		protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            
            runOnUiThread(new Runnable() {
                public void run() 
                {
                	if(check==1)
                	{
                		Toast.makeText(Carlist.this, "Json Exception",Toast.LENGTH_LONG).show();
                   	}
                	
                	else if(check==2)
                	{
                		Toast.makeText(Carlist.this, "Check Your Connection",Toast.LENGTH_LONG).show();
                  	}
                	else
                	{	
                		Intent in= new Intent(Carlist.this, CarDetails.class);
                	    Bundle bun=new Bundle();
						bun.putString("carname", Company);
						bun.putString("carmodel", Model);
						bun.putString("cartype",Type);
						bun.putString("carid",car_id);
						in.putExtras(bun);
						startActivity(in);
                	}
                }
            });
 
		}
	}
	
	

	




	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}
	
}




