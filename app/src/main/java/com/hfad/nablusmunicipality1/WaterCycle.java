package com.hfad.nablusmunicipality1;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WaterCycle extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_cycle);

        WebView webView = new WebView(this);
        setContentView(webView);

        webView.loadUrl("http://www.nablus.org/?page_id=1390");
    }

}