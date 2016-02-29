package com.devesh.pmw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 6/10/2015.
 */
public class CarAdapter4 extends ArrayAdapter {

    List list=new ArrayList();
    public CarAdapter4(Context context, int resource) {
        super(context, resource);
    }
    @Override
    public void add(Object object)
    {
        super.add(object);
        list.add(object);//helps to add element to list
    }

    @Override
    public int getCount() {
        return this.list.size();//returns list size
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);//returns list's position
    }
    //A simple class to group together different values
    static class DataHandler{

        TextView chick;
        TextView  pri;
    }
    //Overriding getview function.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        DataHandler handler;

        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.carspecrowlayout,parent,false);
            handler=new DataHandler();
            handler.pri=(TextView)row.findViewById(R.id.carspectext2);
            handler.chick=(TextView)row.findViewById(R.id.carspectext1);
            row.setTag(handler);
        }
        else
            handler = (DataHandler) row.getTag();
        DataProvider dataProvider;
        dataProvider=(DataProvider)this.getItem(position);
        handler.pri.setText(dataProvider.getPrice());
        handler.chick.setText(dataProvider.getCarName());
        return row;
    }
}