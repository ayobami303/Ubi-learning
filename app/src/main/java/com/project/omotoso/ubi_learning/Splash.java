package com.project.omotoso.ubi_learning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by omotoso on 5/22/2016.
 */
public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timer = new Thread(){
          public void run(){
              try {
                  sleep(5000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }finally {
                  Intent myIntent = new Intent("com.project.omotoso.ubi_learning.MAINACTIVITY");
                  myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                  startActivity(myIntent);
              }

          }
        };
        timer.start();

    }
}
