package com.devesh.pmw;

import java.util.ArrayList;
//import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


//import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.provider.Settings;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
//import android.view.ViewGroup;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnItemClickListener {
	
	protected DrawerLayout drawerLayout;
	private ListView listView;
    public static int success;

	CarAdapter5 carAdapter5;
	private String[] options;
	private ActionBarDrawerToggle drawerListener;
	private  ProgressDialog pDialog;
	LoginDataBaseAdapter loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	static ProgressDialog pDialog2;
	JSONParser jparser = new JSONParser() ;
	JSONArray response=null;
	private static int check;
	public static String car_name;
    public static String car_company;
	public static ArrayList<String> Company;
	public static ArrayList<String> Model;
	public static ArrayList<String> Condition;
	public static ArrayList<String> Car_image;
    public static ArrayList<String> Car_id;

	public static String Comp[]=new String[100];
	public static String Mod[]=new String[100];
	public static String Cond[]=new String[100];
	public static String CarImage[]=new String[100];
	public static String TypeImage[]=new String[100];
	public static final String TAG_CNAME = "CNAME";
    public static final String TAG_ModelName = "MNAME";
    public static final String TAG_Cond = "COND";
    public static final String TAG_CarImage = "CARIMAGE";
    public static final String TAG_TypeImage = "TYPEIMAGE";
   // public static int pos=-1;
    String[] company=new String[100];
	String[] condition=new String[100];
	String[] image=new String[100];
    EditText ed,ed2;
    public static MainActivity fa;
	MyAdapter adapter;
    MyAdapter2 adapter2;
	static String[] icons={"Suv","Sedan","Premium Suv","Premium Sedan","Muv","Hatchback"};
	static int[] logos={R.drawable.audi,R.drawable.bmw,R.drawable.chevrolet,R.drawable.datsun,R.drawable.fiat,R.drawable.ford,R.drawable.hyundai,R.drawable.jaguar,R.drawable.mahindra,R.drawable.maruti,R.drawable.mercedes,R.drawable.renault,R.drawable.skoda,R.drawable.tata,R.drawable.toyota,R.drawable.volkswagen};
    public static List<information> getData()
	{
		List<information> data=new ArrayList<>();

		int[] id={R.drawable.suv,R.drawable.sedan,R.drawable.suv,R.drawable.sedan,R.drawable.suv,R.drawable.crossover,R.drawable.crossover};

		for(int i=0;i<icons.length;i++)
		{
			System.out.println("hola"+i);
			information current=new information();
			current.title=icons[i];
			current.id=id[i];
			data.add(current);
		}
		System.out.println(data);
		return data;

	}
    public static List<information> getData2()
    {
        List<information> data=new ArrayList<>();



        for(int i=0;i<logos.length;i++)
        {
            System.out.println("hola"+i);
            information current=new information();

            current.id=logos[i];
            data.add(current);
        }
        System.out.println(data);
        return data;

    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		loginDataBaseAdapter.open();
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setIcon(R.drawable.images);
        Car_id=new ArrayList<>();
		Company = new ArrayList<>();
		Model = new ArrayList<>();
		Condition = new ArrayList<>();
		Car_image = new ArrayList<>();

		options=getResources().getStringArray(R.array.options);
		listView=(ListView) findViewById(R.id.drawerList);
		listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options));
		listView.setOnItemClickListener(this);
		drawerLayout=(DrawerLayout) findViewById(R.id.drawerLayout);
		drawerListener = new ActionBarDrawerToggle(this, drawerLayout,R.drawable.ic_drawer1,R.string.drawer_open,R.string.drawer_close);
		drawerLayout.setDrawerListener(drawerListener);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);


		List<information> data = getData();
		final RecyclerView recycler = (RecyclerView) findViewById(R.id.recyclerview);
		adapter=new MyAdapter(data);
		//System.out.println("Set Adapter");
		recycler.setAdapter(adapter);
		//System.out.println("Setting layout manager");
		recycler.setLayoutManager(new GridLayoutManager(this,3));
        int spanCount = 3;
        int spacing = 50;
        boolean includeEdge = true;
        recycler.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        recycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        List<information> data2 = getData2();
        final RecyclerView recycler2 = (RecyclerView) findViewById(R.id.recyclerview2);
        adapter2=new MyAdapter2(data2);
        //System.out.println("Set Adapter");
        recycler2.setAdapter(adapter2);
        //System.out.println("Setting layout manager");
        recycler2.setLayoutManager(new GridLayoutManager(this, 5));
         spanCount = 5;
         spacing = 8;
         includeEdge = true;
        recycler2.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        recycler2.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        ed=(EditText)findViewById(R.id.MinValue);
        ed.setText(String.valueOf(0));
        ed2=(EditText)findViewById(R.id.MaxValue);
        ed2.setText(String.valueOf(5000000));

        Button set=(Button)findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RangeSeekBar<Integer> seekBar = (RangeSeekBar<Integer>) findViewById(R.id.seekbar_Activity);
                seekBar.setSelectedMinValue(Integer.valueOf(ed.getText().toString()));
                seekBar.setSelectedMaxValue(Integer.valueOf(ed2.getText().toString()));
                new LoadAll(5).execute();
            }
        });
           RangeSeekBar<Integer> rangeSeekBar = (RangeSeekBar<Integer>) findViewById(R.id.seekbar_Activity);
        rangeSeekBar.setRangeValues(0, 5000000);
        rangeSeekBar.setSelectedMinValue(0);
        rangeSeekBar.setSelectedMaxValue(5000000);

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                // handle changed range values
                Log.i("Seekbar-:", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
                ed.setText(String.valueOf(minValue));
                ed2.setText(String.valueOf(maxValue));
            }
        });

// add RangeSeekBar to pre-defined layout


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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (drawerListener.onOptionsItemSelected(item)) {
			return true;
		}
		int id = item.getItemId();
		return id == R.id.action_settings || super.onOptionsItemSelected(item);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, options[position], Toast.LENGTH_SHORT).show();
		selectItem(position);


        // TODO Auto-generated method stub
		if(position==0)
		{
			//Toast.makeText(this, options[position], Toast.LENGTH_LONG).show();
			Intent intent= new Intent(MainActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.this.startActivity(intent);
			selectItem(position);

            finish();
		}

        else if(position==1)
        {
            //Toast.makeText(this, options[position], Toast.LENGTH_LONG).show();
            Intent intent= new Intent(MainActivity.this,RecentlyView.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.this.startActivity(intent);
            selectItem(position);
            finish();
        }
        else if(position==2)
        {
            new LoadAll2(MainActivity.this).execute();
        }
		else
        {
            Intent intent=new Intent(MainActivity.this,fragments.class);
            intent.putExtra("extra",position);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
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

	    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {
        private LayoutInflater inflater;
        private List<information> data;
        public MyAdapter(List<information> data)
        {

            System.out.println(data+"data Recieved");
            this.data=data;
            System.out.println(this.data);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridlayout, parent, false);
            System.out.println("entered here");
            //view.setOnClickListener(click);
            return new MyViewHolder(view);
            //return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder,final int position) {
            information current=data.get(position);
            //System.out.println(current.title + "sgsdgfgudf");
            holder.textView.setText(current.title);
            holder.imagebutton.setImageResource(current.id);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            public TextView textView;
            public ImageButton imagebutton;
            public MyViewHolder(View itemView) {
                super(itemView);
                imagebutton=(ImageButton)itemView.findViewById(R.id.imagebutton11);
                textView=(TextView)itemView.findViewById(R.id.textView11);
                imagebutton.setOnClickListener(this);
                textView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int pos=getAdapterPosition();
                if(pos==0)
                {
                    MainActivity.car_name="SUV";
                    new LoadAll(0,MainActivity.car_name).execute();
                }
                else if(pos==1)
                {
                    car_name = "Sedan";
                    new LoadAll(0,MainActivity.car_name).execute();

                }
                else if(pos==2)
                {
                    car_name="Premium Suv";
                    new LoadAll(0,MainActivity.car_name).execute();
                }
                else if(pos==3)
                {
                    car_name="Premium Sedan";
                    new LoadAll(0,MainActivity.car_name).execute();
                }
                else if(pos==4)
                {
                    car_name="MUV";
                    new LoadAll(0,MainActivity.car_name).execute();
                }
                else if(pos==5)
                {
                    car_name="Hatchback";
                    new LoadAll(0,MainActivity.car_name).execute();
                }

            }

        }

    }
    class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder>  {
        private LayoutInflater inflater;
        private List<information> data;
        public MyAdapter2(List<information> data)
        {

            System.out.println(data+"data Recieved");
            this.data=data;
            System.out.println(this.data);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridlayout2, parent, false);
            System.out.println("entered here");
            //view.setOnClickListener(click);
            return new MyViewHolder(view);
            //return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder,final int position) {
            information current=data.get(position);
            //System.out.println(current.title + "sgsdgfgudf");
            //holder.textView.setText(current.title);
            holder.imagebutton.setImageResource(current.id);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            public TextView textView;
            public ImageButton imagebutton;
            public MyViewHolder(View itemView) {
                super(itemView);
                imagebutton=(ImageButton)itemView.findViewById(R.id.imagebutton111);
                //textView=(TextView)itemView.findViewById(R.id.textView11);
                imagebutton.setOnClickListener(this);
                //textView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int pos=getAdapterPosition();
                if(pos==0)
                {
                    car_company="Audi";
                    new LoadAll1().execute();
                }
                else if(pos==1)
                {
                    car_company = "BMW";
                    new LoadAll1().execute();

                }
                else if(pos==2)
                {
                    car_company="Chevrolet";
                    new LoadAll1().execute();
                }
                else if(pos==3)
                {
                    car_company="Datsun";
                    new LoadAll1().execute();
                }
                else if(pos==4)
                {
                    car_company="Fiat";
                    new LoadAll1().execute();
                }
                else if(pos==5)
                {
                    car_company="Ford";
                    new LoadAll1().execute();
                }
                else if(pos==6)
                {
                    car_company="Hyundai";
                    new LoadAll1().execute();
                }
                else if(pos==7)
                {
                    car_company="Jaguar";
                    new LoadAll1().execute();
                }
                else if(pos==8)
                {
                    car_company="Mahindra";
                    new LoadAll1().execute();
                }
                else if(pos==9)
                {
                    car_company="Maruti Suzuki";
                    new LoadAll1().execute();
                }
                else if(pos==10)
                {
                    car_company="Mercedes";
                    new LoadAll1().execute();
                }
                else if(pos==11)
                {
                    car_company="Renault";
                    new LoadAll1().execute();
                }
                else if(pos==12)
                {
                    car_company="Skoda";
                    new LoadAll1().execute();
                }
                else if(pos==13)
                {
                    car_company="Tata";
                    new LoadAll1().execute();
                }
                else if(pos==14)
                {
                    car_company="Toyota";
                    new LoadAll1().execute();
                }
                else if(pos==15)
                {
                    car_company="Volkswagon";
                    new LoadAll1().execute();
                }


            }

        }

    }


    class LoadAll extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
        String car_name2;
        int b=1;
        public LoadAll(int b,String car_name)
        {
            this.car_name2=car_name;
            this.b=b;
        }
        public LoadAll(int b)
        {
            this.b=b;
        }
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Loading . Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
            Car_id.clear();
			Company.clear();
			Model.clear();
			Condition.clear();
			Car_image.clear();
			System.out.println("THE NAme OF BODY TYPE"+car_name);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL

			params.add(new BasicNameValuePair("car_type", car_name));
            params.add(new BasicNameValuePair("minPrice",ed.getText().toString() ));
            params.add(new BasicNameValuePair("maxPrice",ed2.getText().toString()));
			System.out.println("Entered doonback......");
			try{check =0;
                String url_;
                if(b==0)
                {  url_ = "http://pmwcg.com/mobi/carlist.php";}
                else
                {
                    url_="http://pmwcg.com/mobi/value.php";
                }
				JSONObject json = jparser.makeHttpRequest(url_, "POST", params);
				System.out.println("Entered jsonobj......");
                success = json.getInt("suc");
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
                    Mod[i ]=model_name;
                    System.out.println("get string TAG MNAME......");
                    String car_condition = c.getString("condition");
                    Cond[i]=car_condition;
                    System.out.println("get string TAG COND......");
                    String car_image = c.getString("default_image");
                    CarImage[i]=car_image;
                    System.out.println("get string TAG CIMAGE......");
                    String type_image = c.getString("image");
                    TypeImage[i]=type_image;
                    System.out.println("get string TAG TIMAGE......");
                    String car_id=c.getString("id");


                    // creating new HashMap
					//HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
                /*map.put(TAG_CNAME, company_name);
                map.put(TAG_ModelName, model_name);
                map.put(TAG_Cond, car_condition);*/



					// adding HashList to ArrayList
                    Car_id.add(car_id);
					Company.add(company_name);
					Model.add(model_name);
					Condition.add(car_condition);
					Car_image.add(car_image);
					System.out.println("passes NAMELIST......");
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
						Toast.makeText(MainActivity.this, "Json Exception",Toast.LENGTH_LONG).show();
					}

					else if(check==2)
					{
						Toast.makeText(MainActivity.this, "Check Your Connection",Toast.LENGTH_LONG).show();
					}
					else
					{
						Intent in= new Intent(MainActivity.this,Carlist.class);
						startActivity(in);
					}
				}
			});

		}


	}

    class LoadAll1 extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading . Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub

            Company.clear();
            Model.clear();
            Condition.clear();
            Car_image.clear();
            Car_id.clear();
            System.out.println("THE NAme OF BODY TYPE"+car_name);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL

            params.add(new BasicNameValuePair("car_company", car_company));
            params.add(new BasicNameValuePair("minPrice",ed.getText().toString() ));
            params.add(new BasicNameValuePair("maxPrice",ed2.getText().toString()));
            System.out.println("Entered doonback......");
            try{check =0;

                String url_ = "http://pmwcg.com/mobi/carlist_company.php";
                JSONObject json = jparser.makeHttpRequest(url_, "POST", params);
                System.out.println("Entered jsonobj......");
                success = json.getInt("suc");
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
                    String type_image = c.getString("image");
                    TypeImage[i]=type_image;
                    System.out.println("get string TAG TIMAGE......");
                    String car_id=c.getString("id");



                    // creating new HashMap
                    //HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                /*map.put(TAG_CNAME, company_name);
                map.put(TAG_ModelName, model_name);
                map.put(TAG_Cond, car_condition);*/



                    // adding HashList to ArrayList
                    Car_id.add(car_id);
                    Company.add(company_name);
                    Model.add(model_name);
                    Condition.add(car_condition);
                    Car_image.add(car_image);

                    System.out.println("passes NAMELIST......");
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
                        Toast.makeText(MainActivity.this, "Json Exception",Toast.LENGTH_LONG).show();
                    }

                    else if(check==2)
                    {
                        Toast.makeText(MainActivity.this, "Check Your Connection",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Intent in= new Intent(MainActivity.this,Carlist.class);
                        startActivity(in);
                    }
                }
            });

        }


    }


}
