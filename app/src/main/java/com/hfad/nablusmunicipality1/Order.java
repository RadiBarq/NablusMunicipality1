package com.hfad.nablusmunicipality1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.widget.CursorAdapter;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;


public class Order extends AppCompatActivity implements AsyncResponse, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
    public static final String EXTRA_REPORT = "reporteNo";
    public String area;
    public String description = "";
    public String phoneNumber = "";
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private ReportsDatabaseHelper helper = new ReportsDatabaseHelper(this);
    public int likes = 0;
    private String Report_status = "1";
    String sLocation = "";
    public Button library_button;

    // Those related to the spinner
    Spinner spinner;


    static int CHEKER = 0;

    String selected_item = null;

    // Specify the layout to use when the list of choices appears

    // This integers related to integers
    private int PICK_IMAGE_REQUEST = 1;
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";
    private Bitmap bitmap;
    private Uri filePath;
    String filname = "images";
    int random;
    Location lastLocation;
    String reportDate;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    //This is Related to the location
    GoogleApiClient client;


    // This is related to keep the screen on while uploading the report
    // This is related to keep the screen on while uploading the photo
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;


    String uploadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);



        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyWakelockTag");

        // This is related to the spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sectionarray, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(this);





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("بلاغ تسرب مياه");

        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);

        library_button = (Button) findViewById(R.id.button);

        Random rand = new Random();

        random = rand.nextInt((1999999999 - 1) + 1) + 1;


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        reportDate = sdf.format(date);

        //TODO put the data inside sql database

        if (CHEKER == 1)
        {


            SharedPreferences load = getSharedPreferences("hi", 0);
            description = load.getString("description", "");
            phoneNumber = load.getString("phoneNumber", "");
            sLocation  = load.getString("location", "");
            uploadImage = load.getString("uploadImage", "");
            editText2.setText(description);
            editText3.setText(phoneNumber);

        }

    }


    public void onClickLibraryPhoto(View view) {
        showFileChooser();
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }



    // Empty constructor
    public Order()
    {

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                CHEKER = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            filePath = data.getData().normalizeScheme();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                CHEKER = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void onFloatButtonClicked(View view) {
        description = editText2.getText().toString();
        phoneNumber = editText3.getText().toString();

        if (description.matches("")) {
            Context context = getApplicationContext();
            Toast.makeText(this, "نسيت خانة الوصف", Toast.LENGTH_SHORT).show();
            return;
        } else if (phoneNumber.matches("")) {
             Context context = getApplicationContext();
            Toast.makeText(this, "نسيت ان تضع رقم هاتفك", Toast.LENGTH_SHORT).show();
            return;
        } else {

            ContentValues values = new ContentValues();
            /**
             values.put("AREA", area);
             values.put("DESCRIPTION", description);
             values.put("IMAGE_RESOURCE_ID"*, phoneNumber);
             values.put("LIKES", likes);   *         database.insert("REPORTSE", null, values);
             Intent loginIntent = new Intent (this, Reports.class);
             startActivity(loginIntent);
             Context context = getApplicationContext();
             */

            wakeLock.acquire();

            if (bitmap != null) {

                uploadImage = getStringImage(bitmap);
            }

            else if (CHEKER == 1)
            {

            }

            else {
                uploadImage = "NoPhoto";
            }

            // This realted to async
            HashMap postData = new HashMap();
            postData.put("area", sLocation);
            postData.put("description", description);
            postData.put("phonenumber", phoneNumber);
            postData.put("counternumber", LoginActicity.COUNTER_NUMBER);
            postData.put("likes", String.valueOf(likes));
            postData.put("image", uploadImage);

            // This check if the photo there is a photo or not
            if (bitmap != null || CHEKER == 1) {
                postData.put("filename", String.valueOf(random) + ".jpeg");
            }
            else {
                postData.put("filename", uploadImage);
            }

            postData.put("report_status", Report_status);
            postData.put("report_date", reportDate);
            postData.put("section", selected_item);
            com.kosalgeek.genasync12.PostResponseAsyncTask task1 = new com.kosalgeek.genasync12.PostResponseAsyncTask(this, postData, false, new com.kosalgeek.genasync12.AsyncResponse() {
                @Override
                public void processFinish(String s) {

                     if (!s.equals("success")) {

                        Toast.makeText(Order.this, "تم اضافة البلاغ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Order.this, Reports1.class);
                        CHEKER = 0;
                        SharedPreferences save = getSharedPreferences("hi", 0);
                        save.edit().putString("description","").commit();
                        startActivity(intent);
                        wakeLock.release();
                        finish();
                     }

                    else
                    {
                        Toast.makeText(Order.this, "يود خطأ في الشبكة اعد المحاولة", Toast.LENGTH_LONG).show();
                        wakeLock.release();
                    }
                }
            });

            task1.execute("http://10.0.2.2/addData.php");
        }
    }

    @Override
    public void processFinish(String result) {
        if (result.equals("success")) {
            Toast.makeText(this, "تم اضافة البلاغ", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Reports1.class);
            finish();
            wakeLock.release();
        } else {
            Toast.makeText(this, "يوجد خطأ في الشبكة اعد المحاوله", Toast.LENGTH_LONG).show();
            wakeLock.release();
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage;
    }


    public void onClickTakePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onConnected(Bundle bundle) {
        // This is can be used in the playstore in the future.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(client);
        if(lastLocation != null)
        {
            sLocation = String.valueOf(lastLocation.getLatitude()) + " " + String.valueOf(lastLocation.getLongitude());
            Toast.makeText(Order.this, " تم اضافة موقعك", Toast.LENGTH_LONG).show();
            client.disconnect();
        }

        if (lastLocation == null) {
                   Toast.makeText(Order.this, "الرجاء تشغيل خدمة الموقع من الاعدادات", Toast.LENGTH_LONG).show();
                    client.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {


        Toast.makeText(Order.this, "الرجاء تشغيل خدمة الموقع من الاعدادات", Toast.LENGTH_LONG).show();

    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }



        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {


        }

        @Override
        public void onProviderDisabled(String provider) {

            Toast.makeText(Order.this, "الرجاء تشغيل خدمة الموقع من الاعدادات", Toast.LENGTH_LONG).show();
        }


        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    };
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selected = String.valueOf(parent.getSelectedItem());
    }

    public void onClickLocation(View view)
    {
        if (client == null) {
            client = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        client.connect();
    }

    public void onClickSave(View view)
    {

        description = editText2.getText().toString();
        phoneNumber = editText3.getText().toString();

        if (description.matches("")) {
            Context context = getApplicationContext();
            Toast.makeText(this, "نسيت خانة الوصف", Toast.LENGTH_SHORT).show();
            return;
        } else if (phoneNumber.matches("")) {
            Context context = getApplicationContext();
            Toast.makeText(this, "نسيت ان تضع رقم هاتفك", Toast.LENGTH_SHORT).show();
            return;
        }

        else {
            SharedPreferences save = getSharedPreferences("hi", 0);
            save.edit().putString("description", description).commit();
            save.edit().putString("phoneNumber", phoneNumber).commit();
            Intent intent = new Intent(Order.this, Reports1.class);
            startActivity(intent);
            if (!sLocation.matches(""))
            {
                save.edit().putString("location", sLocation).commit();
            }

            if (bitmap != null)
            {
                uploadImage = getStringImage(bitmap);
                save.edit().putString("uploadImage", uploadImage).commit();
            }

            CHEKER = 1;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selected_item = String.valueOf(parent.getSelectedItem());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // constructor
    public Order(String description, String phoneNumber, String sLocation)
    {
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.sLocation = sLocation;
    }

    // getting Description
    public String getDescription()
    {
        return this.description;
    }

    // getting the PhoneNumber
    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }
    public String getsLocation() {
        return this.sLocation;
    }

}