package com.hfad.nablusmunicipality1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebIconDatabase;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ShareActionProvider;


public class Login extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView,
                                    View itemView,
                                    int position,
                                    long id) {
                if (position == 0) {
                    Intent intent = new Intent(Login.this, LoginActicity.class);
                    startActivity(intent);
                    finish();
                }

                else if (position == 1) {
                    Intent intent = new Intent(Login.this, RigesterActivity.class);
                    startActivity(intent);
                    finish();
                }


                else if (position == 2)
                {
                    Intent intent = new Intent(Login.this, ForgetPassword.class);
                    startActivity(intent);
                    finish();
                }
            }

        };

        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}





