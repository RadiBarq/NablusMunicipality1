package com.hfad.nablusmunicipality1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.HashMap;

public class WaterCycle extends Activity {

    String url = "www.com";
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_cycle);

         webView = new WebView(this);
         setContentView(webView);


        HashMap postData = new HashMap();
        postData.put("Hi", "Hi");




        com.kosalgeek.genasync12.PostResponseAsyncTask task1 = new com.kosalgeek.genasync12.PostResponseAsyncTask(this, postData, true, new com.kosalgeek.genasync12.AsyncResponse() {
            @Override
            public void processFinish(String s) {

                if(s.matches(""))
                    Toast.makeText(WaterCycle.this, "الرجاء التاكد من اتصال الشبكة", Toast.LENGTH_LONG).show();

              else
                    webView.loadUrl(s);
            }
        });

        task1.execute("http://52.42.94.127/WaterCycleLink.php");
    }

}