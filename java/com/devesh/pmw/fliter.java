package com.devesh.pmw;

import android.app.ProgressDialog;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class fliter extends ActionBarActivity implements AdapterView.OnItemClickListener {

	
private ProgressDialog pDialog;
	
	JSONParser jparser = new JSONParser() ;
	JSONArray response=null;
	private static int check;
    ListView listView;
    ListView listView3;
    // Array variable for Food items, their description and their rates.
    int[] Car_Posters={R.drawable.image_1,R.drawable.image_1,R.drawable.image_1,R.drawable.image_1,R.drawable.image_1,R.drawable.image_1,R.drawable.image_1};
    String[] Carnames;
    CarAdapter adapter;//Custom Adapter to help with relative layout. It helps in uploading data.
    ListView listView2;
    private static boolean[] year=new boolean[20];
    private static boolean[] make=new boolean[20];
    private static boolean[] type=new boolean[20];
    private static boolean[] mileage=new boolean[20];
    private static boolean[] fuel=new boolean[20];
    private static boolean[] Color=new boolean[20];
    private DrawerLayout drawerLayout;
    int m=0;
    int y=0;
    int c=0;
    int M=0;
    int f=0;
    int t=0;
    CarAdapter2 adapter2;
    CarAdapter2 adapter22;
    int old_position=0;
    String[] color = new String[15];
    String[] Year = new String[15];
    String[] Make = new String[15];
    String[] Type = new String[15];
    String[] Fuel = new String[15];

    String[] Mileage = new String[15];
    public static String Comp[]=new String[30];
    public static String Mod[]=new String[30];
    public static String Cond[]=new String[30];
    public static String CarImage[]=new String[30];
    public static String TypeImage[]=new String[30];

    View old_view;
    String[] options;
    private android.support.v4.app.ActionBarDrawerToggle drawerListener;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filterlayout);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.images);
        options=getResources().getStringArray(R.array.options);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        listView3=(ListView)findViewById(R.id.navdrawerlistview);
        drawerListener=new android.support.v4.app.ActionBarDrawerToggle(this,drawerLayout,R.drawable.ic_drawer1,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView3.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options));
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    //Toast.makeText(this, options[position], Toast.LENGTH_LONG).show();
                    Intent intent=new Intent (fliter.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else if(position==1)
                {
                    //Toast.makeText(this, options[position], Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(fliter.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else if(position==2)
                {
                    new LoadAll2(fliter.this).execute();
                    finish();
                }
                else
                {
                    Intent intent=new Intent(fliter.this,fragments.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

            }
        });
        final ArrayList<Product> products = new ArrayList<>();
        adapter2 = new CarAdapter2(fliter.this, products);
        final ArrayList<Product> pro = new ArrayList<>();
        adapter22 = new CarAdapter2(fliter.this, pro);
        listView = (ListView) findViewById(R.id.filterlistview);
        listView2 = (ListView) findViewById(R.id.optionslistview);

        Carnames = getResources().getStringArray(R.array.CarItems);

        adapter = new CarAdapter(getApplicationContext(), R.layout.rowlayout);//"RowLayout is the custom layout created. It is defined as Relative layout.
        //Helps with multiple things in app and their placing
        listView.setAdapter(adapter);//setting adapter of class foodadapter to listview to help with adding data.
        int i = 0;

        for (String titles : Carnames) {
            DataProvider dataProvider = new DataProvider(Car_Posters[i], titles);//Data Provider is a class which provides data to listview.
            adapter.add(dataProvider);
            i++;
        }

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                if (old_position == 0) {
                    //String result = "Selected Product are :";
                    int i = 0;
                    change_back(old_position);
                    for (Product p : adapter2.getItems()) {
                        year[i] = p.box;
                        i++;
                        //Log.d("year_click", "position: " + position + " val: " + year[old_position]);
                    }
                } else if (old_position == 1) {
                    int i = 0;
                    change_back(old_position);
                    for (Product p : adapter2.getItems()) {
                        make[i] = p.box;
                        i++;
                        //Log.d("year_click", "position: " + position + " val: " + year[old_position]);
                    }
                } else if (old_position == 2) {
                    int i = 0;
                    change_back(old_position);
                    for (Product p : adapter2.getItems()) {
                        type[i] = p.box;
                        i++;
                        //Log.d("year_click", "position: " + position + " val: " + year[old_position]);
                    }
                } else if (old_position == 3) {
                    int i = 0;
                    change_back(old_position);
                    for (Product p : adapter2.getItems()) {
                        mileage[i] = p.box;
                        i++;
                        //Log.d("year_click", "position: " + position + " val: " + year[old_position]);
                    }
                } else if (old_position == 4) {
                    int i = 0;
                    change_back(old_position);
                    for (Product p : adapter2.getItems()) {
                        fuel[i] = p.box;
                        i++;
                        //Log.d("year_click", "position: " + position + " val: " + year[old_position]);
                    }
                }
                 else if (old_position == 5) {
                    int i = 0;
                    change_back(old_position);
                    for (Product p : adapter2.getItems()) {
                        Color[i] = p.box;
                        i++;
                        //Log.d("year_click", "position: " + position + " val: " + year[old_position]);
                    }
                }
                products.clear();
                old_position = position;
                old_view=view;
                if (position == 0) {
                    listView.getItemAtPosition(0);
                    int i = 0;


                    if(listView.isFocused())
                        change(view);
                    listView2.setAdapter(adapter2);
                    for (String titles : getResources().getStringArray(R.array.Year)) {
                        //products.add(new Product(titles, year[i]));

                        if (!year[i]) {
                            products.add(new Product(titles, false));
                        } else {
                            products.add(new Product(titles, true));
                        }

                        i++;

                    }
                    listView2.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                } else if (position == 1) {
                    int i = 0;

                    change(view);
                    listView2.setAdapter(adapter2);
                    for (String titles : getResources().getStringArray(R.array.Make)) {
                        if (!make[i]) {
                            products.add(new Product(titles, false));
                        } else {
                            products.add(new Product(titles, true));
                        }
                        i++;

                    }

                    listView2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


                } else if (position == 2) {
                    int i = 0;

                    change(view);
                    listView2.setAdapter(adapter2);
                    for (String titles : getResources().getStringArray(R.array.Type)) {
                        if (!type[i]) {
                            products.add(new Product(titles, false));
                        } else {
                            products.add(new Product(titles, true));
                        }
                        i++;

                    }
                    listView2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


                } else if (position == 3) {

                   int i = 0;
                    change(view);
                    listView2.setAdapter(adapter2);
                    for (String titles : getResources().getStringArray(R.array.Mileage)) {
                        if (!mileage[i]) {
                            products.add(new Product(titles, false));
                        } else {
                            products.add(new Product(titles, true));
                        }
                        i++;

                    }
                    listView2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


                } else if (position == 4) {
                    int i = 0;

                    change(view);
                    listView2.setAdapter(adapter2);
                    for (String titles : getResources().getStringArray(R.array.Fuel)) {

                        if (!fuel[i]) {
                            products.add(new Product(titles, false));
                        } else {
                            products.add(new Product(titles, true));
                        }
                        i++;

                    }
                    listView2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


                } /*else if (position == 5) {
                    int i = 0;
                    i = 0;
                    change(view);
                    listView2.setAdapter(adapter2);
                    for (String titles : getResources().getStringArray(R.array.Price)) {

                        if (price[i] == false) {
                            products.add(new Product(titles, false));
                        } else {
                            products.add(new Product(titles, true));
                        }
                        i++;

                    }
                    listView2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


                } */else if (position == 5) {
                    int i = 0;

                    change(view);
                    listView2.setAdapter(adapter2);
                    for (String titles : getResources().getStringArray(R.array.color)) {
                        if (!Color[i]) {
                            products.add(new Product(titles, false));
                        } else {
                            products.add(new Product(titles, true));
                        }
                        i++;

                    }
                    listView2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


                }
            }
        });

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                pro.clear();
                if (old_position == 0) {
                    //String result = "Selected Product are :";
                    int i = 0;
                    for (Product p : adapter2.getItems()) {
                        year[i] = p.box;
                        i++;
                        //Log.d("year_click", "position: " + position + " val: " + year[old_position]);
                    }
                } else if (old_position == 1) {
                    int i = 0;
                    for (Product p : adapter2.getItems()) {
                        make[i] = p.box;
                        i++;
                        //Log.d("year_click", "position: " + position + " val: " + year[old_position]);
                    }
                } else if (old_position == 2) {
                    int i = 0;
                    for (Product p : adapter2.getItems()) {
                        type[i] = p.box;
                        i++;
                        //Log.d("year_click", "position: " + position + " val: " + year[old_position]);
                    }
                } else if (old_position == 3) {
                    int i = 0;
                    for (Product p : adapter2.getItems()) {
                        mileage[i] = p.box;
                        i++;
                        //Log.d("year_click", "position: " + position + " val: " + year[old_position]);
                    }
                } else if (old_position == 4) {
                    int i = 0;
                    for (Product p : adapter2.getItems()) {
                        fuel[i] = p.box;
                        i++;
                        //Log.d("year_click", "position: " + position + " val: " + year[old_position]);
                    }
                } /*else if (old_position == 5) {
                    int i = 0;
                    for (Product p : adapter2.getItems()) {
                        if (p.box) {
                            price[i] = true;
                        } else
                            price[i] = false;
                        i++;
                        //Log.d("year_click", "position: " + position + " val: " + year[old_position]);
                    }
                } */else if (old_position == 5) {
                    int i = 0;
                    for (Product p : adapter2.getItems()) {
                        Color[i] = p.box;
                        i++;
                        //Log.d("year_click", "position: " + position + " val: " + year[old_position]);
                    }

                }
               int i = 0;

                for (String titles : getResources().getStringArray(R.array.color)) {
                    if (!Color[i]) {
                        pro.add(new Product(titles, false));
                    } else {
                        pro.add(new Product(titles, true));
                    }
                    i++;
                }


                for (Product p : adapter22.getBox()) {
                    if (p.box) {
                        color[c]=p.name;
                        c++;
                    }
                }

                pro.clear();
                i = 0;
                for (String titles : getResources().getStringArray(R.array.Year)) {
                    if (!year[i]) {
                        pro.add(new Product(titles, false));
                    } else {
                        pro.add(new Product(titles, true));
                    }
                    i++;
                }


                for (Product p : adapter22.getBox()) {
                    if (p.box) {
                        Year[y]=p.name;
                        y++;
                    }
                }

                pro.clear();
                i = 0;
                for (String titles : getResources().getStringArray(R.array.Make)) {
                    if (!make[i]) {
                        pro.add(new Product(titles, false));
                    } else {
                        pro.add(new Product(titles, true));
                    }
                    i++;
                }


                for (Product p : adapter22.getBox()) {
                    if (p.box) {
                        Make[m]=p.name;
                        m++;
                    }
                }

                pro.clear();
                i = 0;
                for (String titles : getResources().getStringArray(R.array.Type)) {
                    if (!type[i]) {
                        pro.add(new Product(titles, false));
                    } else {
                        pro.add(new Product(titles, true));
                    }
                    i++;
                }


                for (Product p : adapter22.getBox()) {
                    if (p.box) {
                        Type[t]=p.name;
                        t++;
                    }
                }

                pro.clear();
                i = 0;
                for (String titles : getResources().getStringArray(R.array.Fuel)) {
                    if (!fuel[i]) {
                        pro.add(new Product(titles, false));
                    } else {
                        pro.add(new Product(titles, true));
                    }
                    i++;
                }


                for (Product p : adapter22.getBox()) {
                    if (p.box) {
                        Fuel[f]=p.name;
                        f++;
                    }
                }

                pro.clear();
                i = 0;
                for (String titles : getResources().getStringArray(R.array.Mileage)) {
                    if (!mileage[i]) {
                        pro.add(new Product(titles, false));
                    } else {
                        pro.add(new Product(titles, true));
                    }
                    i++;
                }

                for (Product p : adapter22.getBox()) {
                    if (p.box) {
                        Mileage[M]=p.name;
                        M++;

                    }
                }

                pro.clear();

                new LoadAll().execute();
                
            
            }
            
            
        });
        
    }  
        class LoadAll extends AsyncTask<String, String, String> {

            /**
             * Before starting background thread Show Progress Dialog
             * */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(fliter.this);
                pDialog.setMessage("Loading . Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }

    		@Override
    		protected String doInBackground(String... args) {

                MainActivity.Company.clear();
                MainActivity.Model.clear();
                MainActivity.Condition.clear();
                MainActivity.Car_image.clear();
    			
    			 
    			
    			List<NameValuePair> params = new ArrayList<>();
                // getting JSON string from URL
                String C=Integer.toString(c);
                String Y=Integer.toString(y);
                String T=Integer.toString(t);
                String MM=Integer.toString(M);
                String F=Integer.toString(f);
                String makeis=Integer.toString(m);
                params.add(new BasicNameValuePair("cartype",MainActivity.car_name));
                params.add(new BasicNameValuePair("c",C));
                params.add(new BasicNameValuePair("y",Y));
                params.add(new BasicNameValuePair("t",T));
                params.add(new BasicNameValuePair("mm",MM));
                params.add(new BasicNameValuePair("f",F));
                params.add(new BasicNameValuePair("make",makeis));
                if(c==0)
                    params.add(new BasicNameValuePair("Color"+Integer.toString(0),"" ));
                else if(c!=0)
                for(int i=0;i<c;i++)
                params.add(new BasicNameValuePair("Color"+Integer.toString(i),color[i] ));
                if(y==0)
                    params.add(new BasicNameValuePair("Year"+Integer.toString(0),"" ));
                else if(y!=0)
                for(int i=0;i<y;i++)
                params.add(new BasicNameValuePair("Year"+Integer.toString(i),Year[i] ));
                if(t==0)
                    params.add(new BasicNameValuePair("Type"+Integer.toString(0),"" ));
                else if(t!=0)
                for(int i=0;i<t;i++)
                params.add(new BasicNameValuePair("Type"+Integer.toString(i),Type[i] ));
                if(M==0)
                    params.add(new BasicNameValuePair("Mileage"+Integer.toString(0),"" ));
                else if(M!=0)
                for(int i=0;i<M;i++)
                params.add(new BasicNameValuePair("Mileage"+Integer.toString(i),Mileage[i] ));
                if(f==0)
                    params.add(new BasicNameValuePair("Fuel"+Integer.toString(0),"" ));
                else if(f!=0)
                for(int i=0;i<f;i++)
                params.add(new BasicNameValuePair("Fuel"+Integer.toString(i),Fuel[i] ));
                if(m==0)
                    params.add(new BasicNameValuePair("Make"+Integer.toString(0),"" ));
                else if(m!=0) {
                    for (int i = 0; i < m; i++)
                        params.add(new BasicNameValuePair("Make" + Integer.toString(i), Make[i]));
                }
                System.out.println("Entered doonback......");
                try{check =0;

                    String url_ = "http://pmwcg.com/mobi/tPage.php";
                    JSONObject json = jparser.makeHttpRequest(url_, "POST", params);
                System.out.println("Entered jsonobj......");
                
                response = json.getJSONArray("data");       // These throws an EXCEPTION e
                System.out.println("passes jsonarray......");
                for (int i = 0; i < response.length(); i++) {

                    JSONObject c = response.getJSONObject(i);
                    System.out.println("passes jsonobject c......");
                    // Storing each json item in variable
                    
                    String company_name = c.getString("name");
                    Comp[i]=company_name;
                    System.out.println("get string TAG CNAME......");
                    String model_name = c.getString("model");
                    Mod[i]=model_name;
                    System.out.println("get string TAG MNAME......");
                    String car_condition = c.getString("condition");
                    Cond[i]=car_condition;
                    System.out.println("get string TAG COND......");
                    String car_image = c.getString("default_image");
                    CarImage[i]=car_image;
                    System.out.println("get string TAG CIMAGE......");
                    String type_image = c.getString("type");
                    TypeImage[i]=type_image;
                    System.out.println("get string TAG TIMAGE......");
                    

                    // creating new HashMap
                    //HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    /*map.put(TAG_CNAME, company_name);
                    map.put(TAG_ModelName, model_name);
                    map.put(TAG_Cond, car_condition);*/
                    
                    
                    
                    // adding HashList to ArrayList
                    MainActivity.Company.add(company_name);
                    MainActivity.Model.add(model_name);
                    MainActivity.Condition.add(car_condition);
                    MainActivity.Car_image.add(car_image);
                    System.out.println("passes NAMELIST......");
                }        	
                }
                catch (JSONException e) {
                    check=1;
                	e.printStackTrace();
                }

                catch(Exception e)
                {
                    //noinspection ThrowablePrintedToSystemOut
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
                    public void run() {
                        if (check == 1) {
                            Toast.makeText(fliter.this, "Json Exception", Toast.LENGTH_LONG).show();
                        } else if (check == 2) {
                            Toast.makeText(fliter.this, "Check Your Connection", Toast.LENGTH_LONG).show();
                        } else
                    	{	
                    		Intent in= new Intent(fliter.this,Carlist.class);
                    	    startActivity(in);
                    	}
                    }
                });
     
    		}
    		
        }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    public void change(View view)
    {
        RelativeLayout ll=(RelativeLayout)view.findViewById(R.id.linear);
        ll.setBackgroundColor(android.graphics.Color.BLACK);
        TextView tv=(TextView)view.findViewById(R.id.Text_View);
        tv.setTextColor(android.graphics.Color.WHITE);
    }
    public void change_back(int old_position)
    {
        View view;
        view=listView.getChildAt(old_position);
        RelativeLayout ll=(RelativeLayout)view.findViewById(R.id.linear);
        ll.setBackgroundColor(android.graphics.Color.WHITE);
        TextView tv=(TextView)view.findViewById(R.id.Text_View);
        tv.setTextColor(android.graphics.Color.BLACK);
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
}
