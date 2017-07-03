package com.project.omotoso.ubi_learning;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    ListView list;
    TextView mTextView,mTextView2;
    //String[] web = {"Lab 1(software lab)", "Lab 2 (hardware lab)", "HOD's office"};
    List<String> test = new ArrayList<String>();
    ArrayAdapter<String> adapter;


    private final String[][] techList = new String[][] {
            new String[] {
                    NfcA.class.getName(),
                    NfcB.class.getName(),
                    NfcF.class.getName(),
                    NfcV.class.getName(),
                    IsoDep.class.getName(),
                    MifareClassic.class.getName(),
                    MifareUltralight.class.getName(), Ndef.class.getName()
            }
    };

    String tagId;

    Integer[] imageId = {R.drawable.slab };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost th = (TabHost) findViewById(R.id.tabHost);
        mTextView = (TextView) findViewById(R.id.textView);
        mTextView2 = (TextView) findViewById(R.id.textView3);
        th.setup();

        TabHost.TabSpec specs = th.newTabSpec("tag1");
        specs.setContent(R.id.linearLayout);
        specs.setIndicator("Search ");
        th.addTab(specs);

        specs = th.newTabSpec("tag2");
        specs.setContent(R.id.linearLayout2);
        specs.setIndicator("Navigation ");
        th.addTab(specs);

        specs = th.newTabSpec("tag2");
        specs.setContent(R.id.linearLayout3);
        specs.setIndicator("General Info");
        th.addTab(specs);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,test);

        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;

        }

        if (!mNfcAdapter.isEnabled()) {
            mTextView.setText("Pls enable NFC.");
        } else {
            mTextView.setText("Pls ensure you're connected to the Wi-fi \"ESP8266\" and Tap on a NFC tag");
        }

        //createCustomList();


    }

    private void createCustomList(){


        /*list=(ListView)findViewById(R.id.listView);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(MainActivity.this, "You Clicked at " + test.get(position ), Toast.LENGTH_SHORT).show();

                String bread = test.get(position);
                String[] breadArray = {"fff"};
                breadArray[0] = bread;


                int tea = position - 1;

                Bundle basket = new Bundle();
                basket.putString("key", bread);
                //basket.putStringArray("key", breadArray);
                basket.putInt("keys", tea);

                Intent nextActivity = new Intent(MainActivity.this, Details.class);
                nextActivity.putExtras(basket);
                startActivity(nextActivity);

            }
        });*/

        CustomList adapter = new CustomList(MainActivity.this, test,imageId) ;
        //adapter.clear();
        adapter.notifyDataSetChanged();
        adapter = new CustomList(MainActivity.this, test,imageId);

        list=(ListView)findViewById(R.id.listView);
        //list.setAdapter(null);

       /* list.addHeaderView(new View(this));
        list.addFooterView(new View(this));*/

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "You Clicked at " + test.get(position), Toast.LENGTH_SHORT).show();

                String bread = test.get(position );
                String[] breadArray = {"fff"};
                breadArray[0] = bread;
                String milk = tagId;

                int tea = position;

                Bundle basket = new Bundle();
                basket.putString("key", bread);
                basket.putString("key1", milk);
                //basket.putStringArray("key", breadArray);
                basket.putInt("keys", tea);

                Intent nextActivity = new Intent(MainActivity.this, Details.class);
                nextActivity.putExtras(basket);
                startActivity(nextActivity);

            }
        });
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        String s = "";
        //clears array beffore adding new content
        test.clear();
        // parse through all NDEF messages and their records and pick text type only
        Parcelable[] data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);


        if (data != null) {
            try {
                for (int i = 0; i < data.length; i++) {
                    NdefRecord[] recs = ((NdefMessage)data[i]).getRecords();
                    for (int j = 0; j < recs.length; j++) {
                        if (recs[j].getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                                Arrays.equals(recs[j].getType(), NdefRecord.RTD_TEXT)) {

                            byte[] payload = recs[j].getPayload();
                            String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
                            int langCodeLen = payload[0] & 0077;

                            s += ( new String(payload, langCodeLen + 1,
                                            payload.length - langCodeLen - 1, textEncoding) );
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("TagDispatch", e.toString());
            }

        }
        //Iterator it = testArray.iterator();

        /*while(it.hasNext()){

            if(it.next().toString().equalsIgnoreCase("") ){
                break;
            }else if(it.next().toString().equalsIgnoreCase("") && !it.hasNext()){
                break;
            }else{
                //add to list
            }
        }*/
        test.add(s);
       // adapter.add(s);
        //adapter.notifyDataSetChanged();
        mTextView2.setText(s);

        createCustomList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // creating pending intent:
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // creating intent receiver for NFC events:
        IntentFilter filter = new IntentFilter();
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        // enabling foreground dispatch for getting intent from NFC event:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{filter}, this.techList);
    }
    @Override
    protected void onPause() {
        super.onPause();
        // disabling foreground dispatch:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            ((TextView)findViewById(R.id.textView2)).setText(
                    "NFC Tag\n" +
                            ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)));
            tagId= ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
            handleIntent(intent);
            //call custom listview
        }
    }

    private String ByteArrayToHexString(byte [] inarray) {
        int i, j, in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out= "";

        for(j = 0 ; j < inarray.length ; ++j)
        {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        //handles closing the app
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

}
