package com.hfad.nablusmunicipality1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportStatus extends AppCompatActivity {


    String myJSON;
    ListView list;
    private ProgressDialog pDialog;
    private static final String TAG_RESULTS = "result";
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    TextView textViewEmail;
    TextView textViewId;
    TextView textViewIdPhoneNumber;
    TextView textViewArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textViewEmail = (TextView) findViewById(R.id.textView43);
        textViewId = (TextView) findViewById(R.id.textView41);
        textViewIdPhoneNumber = (TextView) findViewById(R.id.textView40);
        list = (ListView) findViewById(R.id.listView);
        textViewIdPhoneNumber = (TextView) findViewById(R.id.textView48);
        textViewArea = (TextView) findViewById(R.id.textView46);



        textViewId.setText(Reports1.idNum);
        textViewEmail.setText(Test.emailAdress);
        textViewArea.setText(Test.area);
        textViewIdPhoneNumber.setText(Test.phoneNumber);

        personList = new ArrayList<HashMap<String, String>>();
        getData();
    }



    public void getData()
    {
        class GetDataJSON extends AsyncTask<String, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                pDialog = new ProgressDialog(ReportStatus.this);
                pDialog.setMessage("الرجاء الانتظار...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }


            @Override
            protected String doInBackground(String... params)
            {
                String result = null;
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://52.42.94.127/get-report-status.php");

                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>(4);
                nameValuePairList.add(new BasicNameValuePair("table_id", Reports1.idNum));

                try {
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
                } catch (UnsupportedEncodingException e) {
                    // log exception
                    e.printStackTrace();
                }

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

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                pDialog.dismiss();
                if (myJSON == null)
                    Toast.makeText(ReportStatus.this, "Please check your internet connection", Toast.LENGTH_LONG).show();

                if (myJSON != null)
                    showList();
            }

        }

        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    protected void showList() {

        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String reportStatus = c.getString("report_status");
                String date = c.getString("date");
                String manuStatus = c.getString("manu_status");

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put("description", reportStatus);
                persons.put("report_date", date);

                if (LoginActicity.COUNTER_NUMBER.matches("admin@gmail.com"))
                    persons.put("area", manuStatus);

                else
                {
                    persons.put("area", "");
                }

                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    ReportStatus.this, personList, R.layout.list_item1,
                    new String[]{"description", "report_date", "area"},
                    new int[]{R.id.description, R.id.date, R.id.area}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}