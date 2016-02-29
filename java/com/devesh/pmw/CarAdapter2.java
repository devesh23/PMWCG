package com.devesh.pmw;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 6/3/2015.
 */
public class CarAdapter2 extends BaseAdapter {
    List list=new ArrayList();
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Product> objects;
    private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();


    /*public void add(Object object)
    {
        super.add(object);
        objects.add(object);//helps to add element to list
    }*/

    @Override
    public int getCount() {
        return objects.size();//returns list size
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);//returns list's position
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    //A simple class to group together different values
    static class DataHandler{
        CheckBox chick;
        TextView tv;

    }
    CarAdapter2(Context context, ArrayList<Product> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    //Overriding getview function.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.listitemlayout, parent, false);
        }

        Product p = getProduct(position);

        ((TextView) view.findViewById(R.id.checkboxtextview)).setText(p.name);

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.textView);
        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        cbBuy.setTag(position);
        cbBuy.setChecked(p.box);
        return view;
    }

    Product getProduct(int position) {
        return ((Product) getItem(position));
    }

    ArrayList<Product> getBox() {
        ArrayList<Product> box = new ArrayList<Product>();
        for (Product p : objects) {
            if (p.box)
                box.add(p);
        }
        return box;
    }
    ArrayList<Product> getItems() {
        ArrayList<Product> box = new ArrayList<Product>();
        for (Product p : objects) {
            box.add(p);
        }
        return box;
    }
    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getProduct((Integer) buttonView.getTag()).box = isChecked;

        }
    };

}
