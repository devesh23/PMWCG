package com.devesh.pmw;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BookForTestDrive extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private DrawerLayout drawerLayout;
    //private DrawerListener drawerListener;
    private ActionBarDrawerToggle drawerListener;
    private String[] options;
    static ProgressDialog pDialog;
    JSONParser jparser = new JSONParser() ;
    JSONArray response=null;
    private static int check;
    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookfortestdrive);
        Bundle bun=this.getIntent().getExtras();
        final String carname=bun.getString("carname");
        final String carmodel=bun.getString("carmodel");
        final String cartype=bun.getString("cartype");
        final String carid=bun.getString("carid");

        EditText editText;
        EditText editText2;
        EditText editText3;
        EditText editText4;
        Button button;


        button = (Button) findViewById(R.id.book_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadAll(carname,carmodel,cartype,carid).execute();
            //Toast.makeText(BookForTestDrive.this,"Will Contact You Shortly",Toast.LENGTH_SHORT).show();
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer1, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView=(ListView)findViewById(R.id.drawerList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //Toast.makeText(this, options[position], Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(BookForTestDrive.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else if (position == 1) {
                    //Toast.makeText(this, options[position], Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(BookForTestDrive.this, RecentlyView.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else if (position == 2) {
                    new LoadAll2(BookForTestDrive.this).execute();

                } else {
                    Intent intent = new Intent(BookForTestDrive.this, fragments.class);
                    intent.putExtra("extra", position);
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

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub

    }

    class LoadAll extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        String carname;
        String cartype;
        String carmodel;
        String carid;
        public LoadAll(String carname,String cartype,String carmodel,String carid)
        {
            this.carname=carname;
            this.cartype=cartype;
            this.carmodel=carmodel;
            this.carid=carid;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BookForTestDrive.this);
            pDialog.setMessage("Loading . Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            EditText editText;
            EditText editText2;
            EditText editText3;
            EditText editText4;


            editText = (EditText) findViewById(R.id.editText);
            editText2 = (EditText) findViewById(R.id.editText2);
            editText3 = (EditText) findViewById(R.id.editText3);
            editText4 = (EditText) findViewById(R.id.editText4);

            System.out.println("THE NAme OF BODY TYPE"+carname);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL

            params.add(new BasicNameValuePair("name", editText.getText().toString()));
            params.add(new BasicNameValuePair("phone", editText2.getText().toString()));
            params.add(new BasicNameValuePair("date", editText3.getText().toString()));
            params.add(new BasicNameValuePair("email", editText4.getText().toString()));
            params.add(new BasicNameValuePair("carname", carname));
            params.add(new BasicNameValuePair("carmodel",carmodel ));
            params.add(new BasicNameValuePair("cartype", cartype));
            params.add(new BasicNameValuePair("cid", carid));

            System.out.println("Entered doonback......");
            try{check =0;

                //String url_ = "http://pmwcg.com/mobi/book_testdrive.php";
                String url_ = "http://pmwcg.com/bookTestDrive";
                JSONObject json = jparser.makeHttpRequest(url_, "POST", params);
                System.out.println("Entered jsonobj......");

                success = 1;       // These throws an EXCEPTION e
  //              System.out.println("passes jsonarray......");
                }
    //        catch (JSONException e) {
      //          check=1;
        //        e.printStackTrace();
          //  }

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
                        Toast.makeText(BookForTestDrive.this, "Json Exception", Toast.LENGTH_LONG).show();
                    }

                    else if(check==2)
                    {
                        Toast.makeText(BookForTestDrive.this, "Check Your Connection",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        if(success==1){
                            Toast.makeText(BookForTestDrive.this, "Message is successfully sent",Toast.LENGTH_LONG).show();
                        Intent in= new Intent(BookForTestDrive.this,Carlist.class);
                        startActivity(in);}
                    }
                }
            });

        }


    }

}
