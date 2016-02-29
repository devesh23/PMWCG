package com.devesh.pmw;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CarDetails extends ActionBarActivity implements AdapterView.OnItemClickListener {
	
	private DrawerLayout drawerLayout;
	private ListView listView;
	private LinearLayout lv;
	private String[] options;
	private ActionBarDrawerToggle drawerListener;
	private View cell;
	
	TextView textview1, textview2;
	ImageView image1;

	//ImageView imageView;
	ImageView image[]=new ImageView[10];
	String url1,url2,url3,url4,url5,url6,url7,url8,url9;
	 int i,j;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_details);
		Intent intent=getIntent();
		final Bundle bun=this.getIntent().getExtras();
		
		 options=getResources().getStringArray(R.array.options);
	        listView=(ListView) findViewById(R.id.drawerList);
	        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options));
	        
	        drawerLayout=(DrawerLayout) findViewById(R.id.drawerLayout);
	        drawerListener = new ActionBarDrawerToggle(this, drawerLayout,R.drawable.ic_drawer1,R.string.drawer_open,R.string.drawer_close);
	        drawerLayout.setDrawerListener(drawerListener);
	        getSupportActionBar().setHomeButtonEnabled(true);
	        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					if (position == 0) {
						//Toast.makeText(this, options[position], Toast.LENGTH_LONG).show();
						Intent intent = new Intent(CarDetails.this, MainActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
					}
					else if (position == 1) {
						//Toast.makeText(this, options[position], Toast.LENGTH_LONG).show();
						Intent intent = new Intent(CarDetails.this, RecentlyView.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
					}
					else if(position==2)
					{
						new LoadAll2(CarDetails.this).execute();

					}
					else
					{
						Intent intent=new Intent(CarDetails.this,fragments.class);
						intent.putExtra("extra",position);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
					}
				}
			});
		
		Button button=(Button)findViewById(R.id.bookforbutton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(CarDetails.this,BookForTestDrive.class);
				Bundle bund=new Bundle();
				bund.putString("carname",bun.getString("carname"));
				bund.putString("carmodel",bun.getString("carmodel"));
				bund.putString("cartype",bun.getString("cartype"));
				bund.putString("carid",bun.getString("carid"));
				intent.putExtras(bund);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		
		
		
		
		image1=(ImageView)findViewById(R.id.imageView1);
		
		final ImageLoader1 imgLoader1 = new ImageLoader1(getApplicationContext());
		
		imgLoader1.DisplayImage(Carlist.Default_Image, R.drawable.images, image1);
		url1=Carlist.Default_Image;
		int lenght=Carlist.Car_images.size();
		
		


        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id._linearLayout);


        for (int i = 0; i < Carlist.Car_images.size(); i++) {

            cell = getLayoutInflater().inflate(R.layout.listitem2, null);

           final ImageView imageView = (ImageView) cell.findViewById(R.id.imageView1);
            
            imageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // do whatever you want ...
                	//Toast.makeText(getApplicationContext(),(CharSequence) imageView.getTag() , Toast.LENGTH_LONG).show();
                	String car_url=(String) imageView.getTag();
    				imgLoader1.DisplayImage(car_url, R.drawable.images, image1);

    				
                }
            });
            
            imageView.setTag(Carlist.Car_images.get(i));
            String url=Carlist.Car_images.get(i);
            ImageLoader1 imgLoader = new ImageLoader1(getApplicationContext());
            
            // whenever you want to load an image from url
            // call DisplayImage function
            // url - image url to load
            // loader - loader image, will be displayed before getting image
            // image - ImageView 
            imgLoader.DisplayImage(url, R.drawable.images, imageView);
            

            mainLayout.addView(cell);
            
        }

		
		lv = (LinearLayout) findViewById(R.id.carspeclistview);
        View row;
        TextView tv1;
		TextView tv2;
		String[] details = {Carlist.Company, Carlist.Model, Carlist.Type,Carlist.Condition, Carlist.Year, Carlist.Mileage, Carlist.Fuel, Carlist.Engine, Carlist.Cylinder, Carlist.Fuel_type};
        for (String titles : getResources().getStringArray(R.array.carspecs)) {
            row=getLayoutInflater().inflate(R.layout.carspecrowlayout,null);
			tv1=(TextView)row.findViewById(R.id.carspectext1);
			tv2=(TextView)row.findViewById(R.id.carspectext2);
			tv1.setText(titles);
			tv2.setText(details[i]);
			lv.addView(row);
			i++;
        }


		
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


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	};
}
