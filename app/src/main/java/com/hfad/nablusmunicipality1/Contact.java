package com.hfad.nablusmunicipality1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class Contact extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

    }


    public void onClickFacebook (View view)
    {
        goTOUrl("https://www.facebook.com/Municipality.of.Nablus");
    }

    public void onClickYoutube (View view)
    {
        goTOUrl("https://www.youtube.com/channel/UCU99J3y1wBLflYNiRxKv8jw");
    }

    public void onClickWebsite(View view)
    {
        goTOUrl("http://www.nablus.org/");
    }


    private void goTOUrl(String url)
    {
        Uri uriUrl = Uri.parse(url);
        Intent lunchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(lunchBrowser);

    }

    public void onClickGmail(View view)
    {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("plain/text");
        sendIntent.setData(Uri.parse("grayllow@gmail.com"));
        sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "grayllow@gmail.com" });
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(sendIntent);

    }

}
