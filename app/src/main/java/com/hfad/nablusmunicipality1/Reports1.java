package com.hfad.nablusmunicipality1;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.EventLogTags;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.AnnotationFormatError;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.Attributes;


public class Reports1 extends AppCompatActivity{

    String myJSON;

    int CHEKER = 0;

    int COUNTER = 0;
    public static int Counter = 0;

    private ProgressDialog pDialog;
    public static String Description;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_LIKES = "likes";
    private static final String TAG_DESCRIPTION = "description";
    JSONArray peoples = null;
    public static  final String TAG_ID = "id";
    public static String idNum;
    private ActionBarDrawerToggle drawerToggle;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<HashMap<String, String>> personList;
    ListView list;
    public static String image_id;
    String hey = "http://52.42.94.127//images/";
    String url = hey + image_id;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    String SavedCheker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reports1);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CHEKER = 1;
                personList = new ArrayList<HashMap<String, String>>();
                getData();
            }
            });





        SharedPreferences load = getSharedPreferences("hi", 0);
        SavedCheker = load.getString("description", "");

        if (!SavedCheker.matches(""))
            Toast.makeText(Reports1.  this, "انتبه لديك بلاغ محفوظ في الحافظة", Toast.LENGTH_LONG).show();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("القائمة");

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        mDrawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);

        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {
                selectDrawerItem(item);
                return true;
            }
        });

        personList = new ArrayList<HashMap<String, String>>();
        getData();
        list = (ListView)findViewById(R.id.listView);
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               idNum = ((TextView) view.findViewById(R.id.id)).getText().toString();

                HashMap posData = new HashMap();
                posData.put("id", idNum);
                PostResponseAsyncTask task = new PostResponseAsyncTask(Reports1.this, posData, false, new AsyncResponse() {

                    @Override
                    public void processFinish(String s) {

                        if (s.matches(""))
                            Toast.makeText(Reports1.this, "تاكد من اتصال الشبكة", Toast.LENGTH_LONG);
                        else {
                            Description = s;
                            Intent in = new Intent(Reports1.this, Test.class);
                            startActivity(in);
                        }
                    }
                });
                task.execute("http://52.42.94.127/getOrder-1.php");
            }
        });

    }

    public void selectDrawerItem(MenuItem menuItem)
    {
        android.support.v4.app.Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                Intent intent2 = new Intent(Reports1.this, Order.class);
                mDrawer.closeDrawer(GravityCompat.START);
                startActivity(intent2);
                break;


            case R.id.nav_second_fragmnet:
                Intent intent3 = new Intent(Reports1.this, WaterCycle.class);
                mDrawer.closeDrawer(GravityCompat.START);
                startActivity(intent3);
                break;

            case R.id.nab_third_fragment:
                Intent intent4 = new Intent(Reports1.this, Contact.class);
                mDrawer.closeDrawer((GravityCompat.START));
                startActivity(intent4);
                break;

            case R.id.nab_four_fragment:
                Intent intent5 = new Intent(Reports1.this, Contact.class);
                mDrawer.closeDrawer(GravityCompat.START);
                startActivity(intent5);
                break;

            case R.id.nab_five_fragment:
                Intent intent6 = new Intent(Reports1.this, MainActivity.class);
                mDrawer.closeDrawer(GravityCompat.START);
                startActivity(intent6);
                finish();
                break;

            case R.id.nab_six_fragment:
                Intent intent7 = new Intent(Reports1.this, Accounts.class);
                mDrawer.closeDrawer(GravityCompat.START);
                startActivity(intent7);
                break;


            case R.id.nab_seven_fragment:
                COUNTER = 1;
                mDrawer.closeDrawer(GravityCompat.START);
                personList = new ArrayList<HashMap<String, String>>();
                getData();
                break;

            case R.id.nav_eight_fragment:
                COUNTER = 0;
                mDrawer.closeDrawer(GravityCompat.START);
                personList = new ArrayList<HashMap<String, String>>();
                getData();
                break;

            case R.id.nab_ninth_fragment:
                Order.CHEKER = 1;
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(Reports1.this, Order.class);
                startActivity(intent);
                break;
            default:

        }
    }


    @Override
    public void onBackPressed()
    {
        mDrawer.closeDrawer(GravityCompat.START);
    }

    // TODO The Activity result

    protected void showList() {

        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String description = c.getString(TAG_DESCRIPTION);
                String likes = c.getString(TAG_LIKES);
                String report_date = c.getString("report_date");
                String area = c.getString("area");

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_ID, id);
                persons.put(TAG_DESCRIPTION, description);
                persons.put(TAG_LIKES, likes);
                persons.put("report_date", report_date);
                persons.put("area", area);

                personList.add(persons);


            }

            ListAdapter adapter = new SimpleAdapter(
                    Reports1.this, personList, R.layout.list_item,
                    new String[]{TAG_ID, TAG_DESCRIPTION, TAG_LIKES, "report_date", "area"},
                    new int[]{R.id.id, R.id.description, R.id.likes, R.id.date, R.id.area}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_create_order)
        {
            Intent intent = new Intent(this, Order.class);
            startActivity(intent);

            return true;
        }

        else {
            return super.onOptionsItemSelected(item);
        }
    }


    public void getData() {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute()
            {
                if (CHEKER == 0) {
                    super.onPreExecute();
                    pDialog = new ProgressDialog(Reports1.this);
                    pDialog.setMessage("الرجاء الانتظار...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(true);
                    pDialog.show();
                }
            }


            @Override
            protected String doInBackground(String... params) {

                String result = null;
                if (COUNTER == 0) {

                    DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                    HttpPost httppost = new HttpPost("http://52.42.94.127/get-data.php");

                    // Depends on your web service
                    httppost.setHeader("Content-type", "application/json");

                    InputStream inputStream = null;

                    try {
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();

                        inputStream = entity.getContent();
                        // json is UTF-8 by default
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                        StringBuilder sb = new StringBuilder();

                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        result = sb.toString();
                    } catch (Exception e) {
                        // Oops
                    } finally {
                        try {
                            if (inputStream != null) inputStream.close();
                        } catch (Exception squish) {
                        }
                    }
                    return result;
                }

                else if(COUNTER == 1)
                {
                    DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                    HttpPost httppost = new HttpPost("http://52.42.94.127/get-my-data.php");

                    // Encoding POST data
                    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
                    nameValuePair.add(new BasicNameValuePair("email", LoginActicity.COUNTER_NUMBER));

                    try {
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                    } catch (UnsupportedEncodingException e) {
                        // log exception
                        e.printStackTrace();
                    }


                    // Depends on your web service

                    InputStream inputStream = null;

                    try {
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();

                        Log.d("Http Post Response:", response.toString());

                        inputStream = entity.getContent();
                        // json is UTF-8 by default
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                        StringBuilder sb = new StringBuilder();

                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        result = sb.toString();
                    } catch (Exception e) {
                        // Oops
                    } finally {
                        try {
                            if (inputStream != null) inputStream.close();
                        } catch (Exception squish) {
                        }
                    }
                    return result;
                }

                return result;
            }


            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                    pDialog.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
                if (myJSON == null)
                    Toast.makeText(Reports1.this, "Please check your internet connection", Toast.LENGTH_LONG).show();

                if (myJSON != null)
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    public void onClickFloat(View view)
    {
        Intent intent = new Intent(Reports1.this, Order.class);
        startActivity(intent);
    }


    private ActionBarDrawerToggle setupDrawerToggle()
    {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.open_drawer, R.string.close_drawer)
        {
            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }


        };
    }




}