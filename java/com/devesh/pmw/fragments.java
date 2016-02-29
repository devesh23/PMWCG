package com.devesh.pmw;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileDescriptor;
import java.io.PrintWriter;


public class fragments extends ActionBarActivity {
    DrawerLayout drawerLayout;
    android.support.v4.app.ActionBarDrawerToggle drawerListener;
    ListView listView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
         int pos=getIntent().getExtras().getInt("extra");
        if(pos==3)
        {
            System.out.println("hissdhaushf");
            fragment_about fa=new fragment_about();
            //android.support.v4.app.FragmentManager fm=getSupportFragmentManager();
            //android.support.v4.app.FragmentTransaction ft=fm.beginTransaction();
            getSupportFragmentManager().beginTransaction().add(R.id.frame,fa).commit();
            setTitle("About us");
        }

        else if(pos==4)
        {
            Fragment_Contact fg=new Fragment_Contact();
            getSupportFragmentManager().beginTransaction().add(R.id.frame,fg).commit();
            setTitle("Contact us");
        }
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.images);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        listView3=(ListView)findViewById(R.id.navdrawerlistview);
        drawerListener=new android.support.v4.app.ActionBarDrawerToggle(this,drawerLayout,R.drawable.ic_drawer1,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView3.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.options)));
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    //Toast.makeText(this, options[position], Toast.LENGTH_LONG).show();
                    Intent intent=new Intent (fragments.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else if(position==1){
                    Intent intent=new Intent(fragments.this,RecentlyView.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else if(position==2)
                {
                    new LoadAll2(fragments.this).execute();
                    finish();
                }
                else
                {
                    Intent intent=new Intent(fragments.this,fragments.class);
                    intent.putExtra("extra",position);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            }
        });


    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent in = new Intent(fragments.this, MainActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fragments, menu);
        return true;
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
