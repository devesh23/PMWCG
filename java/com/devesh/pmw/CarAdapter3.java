package com.devesh.pmw;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.devesh.pmw.CarAdapter.DataHandler;

public class CarAdapter3 extends ArrayAdapter {

    List list=new ArrayList();
    Bitmap bitmap;
    int loader = R.drawable.images;
    
    public CarAdapter3(Context context, int resource) {
        super(context, resource);
    }
   

    @Override
    public int getCount() {
        return MainActivity.Company.size();//returns list size
    }

    public String getComp(int position) {
        return MainActivity.Company.get(position);//returns list's position
    }
    
    
    public String getModel(int position){
    	return MainActivity.Model.get(position);
    }
    
    public String getCond(int position){
    	return MainActivity.Condition.get(position);
    }
    
    public String getCar_image(int position){
    	return MainActivity.Car_image.get(position);
    }
    
    
    
    
    
    //A simple class to group together different values
    static class DataHandler{
        ImageView  car_image;
        TextView  company;
        TextView  model;
        TextView  cond;
        boolean loaded;
    }
    
    //Overriding getview function.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        DataHandler handler;
        
        Log.d("CarAdapter3", "getView(" + position + ")");

        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.list_item,parent,false);
            handler=new DataHandler();
            handler.car_image=(ImageView)row.findViewById(R.id.imageView1);
            handler.company=(TextView)row.findViewById(R.id.textView1);
            handler.model=(TextView)row.findViewById(R.id.textView2);
            handler.cond=(TextView)row.findViewById(R.id.textView3);
            row.setTag(handler);
        }
        else
        handler = (DataHandler) row.getTag();
        String comp,mod, cond, car_image;
        comp=this.getComp(position);
        mod=this.getModel(position);
        cond=this.getCond(position);
        car_image=this.getCar_image(position);
        
        //handler.car_image.setImageResource(dataProvider.getCarImage());
        handler.company.setText(comp);
        handler.model.setText(mod);
        handler.cond.setText(cond);
        
        handler.loaded = false;
        
        ImageLoader imgLoader = new ImageLoader(getContext());
        
        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView 

        imgLoader.DisplayImage(car_image, loader, handler.car_image);
        //new ImageLoadTask(car_image, handler).execute();
        return row;
    }
        
}

