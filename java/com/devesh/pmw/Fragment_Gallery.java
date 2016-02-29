package com.devesh.pmw;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Gallery extends ActionBarActivity {
    int i = 0;
    private android.support.v4.app.ActionBarDrawerToggle drawerListener;
    private ListView listView3;
    String[] options;
    int loader = R.drawable.images;
    MyAdapter adapter;
    public static ArrayList<String> CarImage;

    private static int check;
    private ProgressDialog pDialog;

    JSONParser jparser = new JSONParser();
    JSONArray response = null;
    public static final String TAG_CarImage = "CARIMAGE";

    public static List<information> getData() {
        List<information> data = new ArrayList<>();

        //int[] id={R.drawable.wagon,R.drawable.coupe,R.drawable.convertible,R.drawable.minivan,R.drawable.minicar,R.drawable.sedan,R.drawable.suv,R.drawable.crossover};

        for (int i = 0; i < MainActivity.Car_image.size(); i++) {
            System.out.println("hola" + i);
            information current = new information();
            current.image = MainActivity.Car_image.get(i);
            data.add(current);
        }
        System.out.println(data);
        return data;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment__gallery);

        List<information> data = getData();
        final RecyclerView recycler = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new MyAdapter(data);
        //System.out.println("Set Adapter");
        recycler.setAdapter(adapter);
        //System.out.println("Setting layout manager");
        recycler.setLayoutManager(new GridLayoutManager(this, 4));
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.images);
        options = getResources().getStringArray(R.array.options);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        listView3 = (ListView) findViewById(R.id.drawerList);
        drawerListener = new android.support.v4.app.ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer1, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView3.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options));
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //Toast.makeText(this, options[position], Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Fragment_Gallery.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else if (position == 1) {
                    //Toast.makeText(this, options[position], Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Fragment_Gallery.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else if(position==2)
                {
                    new LoadAll2(Fragment_Gallery.this).execute();

                }
                else
                {
                    Intent intent=new Intent(Fragment_Gallery.this,fragments.class);
                    intent.putExtra("extra",position);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }



        class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
            private LayoutInflater inflater;
            private List<information> data;

            public MyAdapter(List<information> data) {

                System.out.println(data + "data Recieved");
                this.data = data;
                System.out.println(this.data);
            }

            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridlayout3, parent, false);
                System.out.println("entered here");
                //view.setOnClickListener(click);
                return new MyViewHolder(view);
                //return holder;
            }

            @Override
            public void onBindViewHolder(final MyViewHolder holder, final int position) {
                information current = data.get(position);
                //System.out.println(current.title + "sgsdgfgudf");
                ImageLoader imgLoader = new ImageLoader(getApplicationContext());

                // whenever you want to load an image from url
                // call DisplayImage function
                // url - image url to load
                // loader - loader image, will be displayed before getting image
                // image - ImageView

                imgLoader.DisplayImage(current.image, loader, holder.imagebutton);

                //holder.imagebutton.setImageResource(current.id);
            }

            @Override
            public int getItemCount() {
                return data.size();
            }

            class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

                public ImageButton imagebutton;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    imagebutton = (ImageButton) itemView.findViewById(R.id.imagebutton11);
                    //               textView=(TextView)itemView.findViewById(R.id.textView11);
                    //           imagebutton.setOnClickListener(this);
                    //             textView.setOnClickListener(this);
                }

                @Override
                public void onClick(View v) {


                }

            }

        }
    }

