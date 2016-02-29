package com.devesh.pmw;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class RecentlyView extends ActionBarActivity implements AdapterView.OnItemClickListener {

	protected DrawerLayout drawerLayout;
	private String[] options;
	private ActionBarDrawerToggle drawerListener;

	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recently_view);

		SQLite.CompanyArray.clear();
		SQLite.ModelArray.clear();
		SQLite.ConditionArray.clear();
		SQLite.Car_imageArray.clear();

		SQLite info = new SQLite(RecentlyView.this);
		info.open();
		String[] columns= new String[]{SQLite.Company, SQLite.Model, SQLite.Cond, SQLite.Car_image};
		Cursor c = SQLite.ourDatabase.query(true, SQLite.Database_Table, columns, null, null, null, null, SQLite.Row_id + " DESC", "3");

		System.out.println("getdata enterde");
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			String company = c.getString(c.getColumnIndex(SQLite.Company));
			System.out.println("company name="+ company);
			String model = c.getString(c.getColumnIndex(SQLite.Model));
			System.out.println("model="+model);
			String cond = c.getString(c.getColumnIndex(SQLite.Cond));
			System.out.println("cond="+cond);
			String car_image = c.getString(c.getColumnIndex(SQLite.Car_image));
			System.out.println("car_image="+car_image);
			//String type_image = c.getString(c.getColumnIndex(SQLite.Type_image));


			SQLite.CompanyArray.add(company);
			SQLite.ModelArray.add(model);
			SQLite.ConditionArray.add(cond);
			SQLite.Car_imageArray.add(car_image);
		}

		info.close();

		System.out.println("Size of arraylist..."+SQLite.CompanyArray.size());


		ListView list = (ListView)findViewById(R.id.list1111);
		CarAdapter5 adapter = new CarAdapter5(getApplicationContext(), R.layout.list_item);
		//if(!SQLite.CompanyArray.isEmpty())
		list.setAdapter(adapter);options=getResources().getStringArray(R.array.options);
		listView=(ListView) findViewById(R.id.drawerList);
		listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options));
		listView.setOnItemClickListener(this);
		drawerLayout=(DrawerLayout) findViewById(R.id.drawerLayout);
		drawerListener = new ActionBarDrawerToggle(this, drawerLayout,R.drawable.ic_drawer1,R.string.drawer_open,R.string.drawer_close);
		drawerLayout.setDrawerListener(drawerListener);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
		getMenuInflater().inflate(R.menu.recently_view, menu);
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
			Intent intent= new Intent(RecentlyView.this,MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			selectItem(position);
			finish();

		}

		else if(position==1)
		{
			//Toast.makeText(this, options[position], Toast.LENGTH_LONG).show();
			Intent intent= new Intent(RecentlyView.this,RecentlyView.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			selectItem(position);
			finish();
		}
		else if(position==2)
		{
			new LoadAll2(RecentlyView.this).execute();
			finish();
		}
		else
		{
			Intent intent=new Intent(RecentlyView.this,fragments.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("extra",position);
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


	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		Intent in = new Intent(RecentlyView.this, MainActivity.class);
		in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(in);
		finish();

	}
}
