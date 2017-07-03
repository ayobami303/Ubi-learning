package com.project.omotoso.ubi_learning;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omotoso on 4/22/2016.
 */
public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    //private final String[] web;
    private final Integer[] imageId;
    List<String> test = new ArrayList<String>();

    public CustomList(Activity context,
                      List<String> test,Integer[] imageId) {
        super(context, R.layout.list_single, test);

        this.context = context;
        //this.web = web;
        this.test= test;
        this.imageId = imageId;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(test.get(position));

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
