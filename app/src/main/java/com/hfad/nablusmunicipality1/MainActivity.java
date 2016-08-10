package com.hfad.nablusmunicipality1;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClickLogin(View view)
    {
        Intent loginIntent = new Intent (this, Login.class);
        startActivity(loginIntent);
        finish();
    }

    public void onClickWaterCycle(View view)
    {
        Intent waterCycleIntent = new Intent(this, WaterCycle.class);
        startActivity(waterCycleIntent);
    }

    public void onClickContact(View view)
    {
        Intent contactIntent = new Intent(this, Contact.class);
        startActivity(contactIntent);
    }

    public void onClickAbout(View view)
    {
        Intent aboutIntent = new Intent(this, About.class);
        startActivity(aboutIntent);

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }


}


