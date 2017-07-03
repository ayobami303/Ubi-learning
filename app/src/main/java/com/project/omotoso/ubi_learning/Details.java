package com.project.omotoso.ubi_learning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLogTags;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by omotoso on 5/6/2016.
 */
public class Details extends AppCompatActivity {

    ListView list;
    List<String> info = new ArrayList<String>();

    String getBread[];
    String gotBread,gotMilk;
    int gotTea;
    Integer[] imageId2={R.drawable.slab, R.drawable.hlab, R.drawable.hod};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        //bundle to get string from another activity
        Bundle gotBasket = getIntent().getExtras();
        //getBread = gotBasket.getStringArray("key");
        gotTea = gotBasket.getInt("keys");
        gotBread = gotBasket.getString("key");
        gotMilk = gotBasket.getString("key1");

        //imageId2[0] = imageId[gotTea];
        //imageId[0] =    R.drawable.slab ;


        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = getApplicationContext().getAssets().open(gotMilk+".xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            parseXML(parser);

        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        createCustomList();

    }


    public void createCustomList(){
        CustomList adapter = new
                CustomList(Details.this,info , imageId2);
        list=(ListView)findViewById(R.id.listViewDetails);
        TextView myText = (TextView) findViewById(R.id.textViewTitle);
        myText.setText(gotBread);

        //sets up list for details layout
        list.addHeaderView(new View(this));
        list.addFooterView(new View(this));
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(Details.this, "You Clicked at " + web[position - 1], Toast.LENGTH_SHORT).show();
                int bread = gotTea;
                Bundle basket = new Bundle();
                basket.putInt("key", bread);

                Intent nextActivity = new Intent(Details.this,Description.class);
                nextActivity.putExtras(basket);

                startActivity(nextActivity);

            }
        });

    }

    private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        ArrayList <Info> products = null;
        int eventType = parser.getEventType();
        Info currentProduct = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    products = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();

                    if (name.equalsIgnoreCase("info")){
                        currentProduct = new Info();
                        //currentProduct.name="ayo";
                    } else if (currentProduct != null) {
                        if (name.equalsIgnoreCase("infoname")) {
                            currentProduct.name = parser.nextText();
                        } else if (name.equalsIgnoreCase("imgurl")) {
                            currentProduct.url = parser.nextText();
                        } else if (name.equalsIgnoreCase("description")) {
                            currentProduct.description = parser.nextText();
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("info") && currentProduct != null){
                        products.add(currentProduct);
                    }
            }
            eventType = parser.next();
        }

        printDetails(products);

    }


    private void printDetails(ArrayList<Info>  products)
    {
        //int counter=0;
        //String [] test = new String[100];
        ArrayList test = new ArrayList();
        String content = "";
        Iterator<Info> it = products.iterator();
        while(it.hasNext())
        {
            Info currProduct  = it.next();
            //test[counter] = currProduct.name;
            info.add(currProduct.name);
            content = content + "info name :" +  currProduct.name + "\n";
            content = content + "color :" +  currProduct.url + "\n";
            content = content + "description :" +  currProduct.description + "\n";
            //counter++;
        }

//        TextView display = (TextView)findViewById(R.id.info);
//        TextView display2 = (TextView)findViewById(R.id.info2);
        //display.setText(content);
        //
        //display2.setText(""+info.get(2));
    }



}
