package com.hfad.nablusmunicipality1;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.Toolbar;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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


public class Reports1 extends AppCompatActivity{

    String myJSON;

    int CHEKER = 0;
    public static int Counter = 0;

    private ProgressDialog pDialog;
    public static String Description;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_LIKES = "likes";
    private static final String TAG_DESCRIPTION = "description";
    JSONArray peoples = null;
    public static final String TAG_ID = "id";
    public static String idNum;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<HashMap<String, String>> personList;
    ListView list;
    public static String image_id;
    String hey = "http://androdimysqlapp.azurewebsites.net//images/";
    String url = hey + image_id;

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
                            Toast.makeText(Reports1.this, "تامد من اتصال الشبكه", Toast.LENGTH_LONG);
                        else {
                            Description = s;
                            Intent in = new Intent(Reports1.this, Test.class);
                            startActivity(in);
                        }
                    }
                });
                task.execute("http://androdimysqlapp.azurewebsites.net/getOrder-1.php");

            }
        });


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
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://androdimysqlapp.azurewebsites.net/android_connect/get-data.php");

                    // Depends on your web service
                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
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

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                    pDialog.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
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



}