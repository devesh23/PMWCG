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


//Creating a custom adapter class for relative placement of object in listview.
public class CarAdapter extends ArrayAdapter {

    List list=new ArrayList();
    public CarAdapter(Context context, int resource) {
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
        ImageView  car_poster;
        TextView  chick;
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
            row=inflater.inflate(R.layout.rowlayout,parent,false);
            handler=new DataHandler();
            handler.car_poster=(ImageView)row.findViewById(R.id.FoodPoster);
            handler.chick=(TextView)row.findViewById(R.id.Text_View);
            row.setTag(handler);
        }
        else
        handler = (DataHandler) row.getTag();
        DataProvider dataProvider;
        dataProvider=(DataProvider)this.getItem(position);
        handler.car_poster.setImageResource(dataProvider.getCar_poster_resource());
        handler.chick.setText(dataProvider.getCarName());
        return row;
    }
}
