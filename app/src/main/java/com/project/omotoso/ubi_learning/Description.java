package com.project.omotoso.ubi_learning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by omotoso on 5/8/2016.
 */
public class Description extends AppCompatActivity {

    Integer[] imageId={  R.drawable.slab,
            R.drawable.hlab,
            R.drawable.hod
    } ;
    String text="Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod " +
            "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
            "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo " +
            "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse " +
            "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_layout);

        ImageView imageView= (ImageView) findViewById(R.id.imageView);
        TextView myText =(TextView) findViewById(R.id.textView4);

        Bundle getId = getIntent().getExtras();
        int myId = getId.getInt("key");
        imageView.setImageResource(imageId[myId]);
        myText.setText(text+myId);
    }
}
