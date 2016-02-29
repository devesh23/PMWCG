package com.devesh.pmw;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.MacSpi;

/**
 * Created by LENOVO on 6/27/2015.
 */
public class LoadAll2 extends AsyncTask<String, String, String> {

    /**
     * Before starting background thread Show Progress Dialog
     */
    private ProgressDialog pDialog;
    private Context context;
    private int check=0;
    JSONParser jparser = new JSONParser() ;
    JSONArray response=null;
    public LoadAll2(Context context)
    {
        this.context=context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading . Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... args) {
        // TODO Auto-generated method stub


        MainActivity.Car_image.clear();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // getting JSON string from URL

        //params.add(new BasicNameValuePair("car_type", car_name));

        System.out.println("Entered doonback......");
        try {
            check = 0;

            String url_ = "http://pmwcg.com/mobi/gallery.php";
            JSONObject json = jparser.makeHttpRequest(url_, "POST", params);
            System.out.println("Entered jsonobj......");

            response = json.getJSONArray("data");       // These throws an EXCEPTION e
            System.out.println("passes jsonarray......");
            for (int i = 0; i < response.length(); i++) {
                JSONObject c = response.getJSONObject(i);
                System.out.println("passes jsonobject c......");
                // Storing each json item in variable


                String car_image = c.getString("image");
                System.out.println("get string TAG CIMAGE......");
                MainActivity.Car_image.add(car_image);
                System.out.println("passes NAMELIST......");
            }
        } catch (JSONException e) {
            check = 1;
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
            check = 2;
        }
        return null;
    }

    protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
       if(pDialog!=null)
        pDialog.dismiss();
        // updating UI from Background Thread

               if(check==1)
                {
                    Toast.makeText(context, "Json Exception", Toast.LENGTH_LONG).show();
                }

                else if(check==2)
                {
                    Toast.makeText(context, "Check Your Connection",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent in= new Intent(context,Fragment_Gallery.class);
                    context.startActivity(in);
                }
            }

    }

