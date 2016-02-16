package com.hfad.nablusmunicipality1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.widget.CursorAdapter;
import android.database.sqlite.SQLiteOpenHelper;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

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


public class Order extends AppCompatActivity implements AsyncResponse  {
    public static final String EXTRA_REPORT = "reporteNo";
    public String area;
    public String description;
    public String phoneNumber;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private ReportsDatabaseHelper helper = new ReportsDatabaseHelper(this);
    public int likes = 0;
    private String Report_status = "لم ينظر به بعد";

    public Button library_button;

    // This integers related to integers
    private int PICK_IMAGE_REQUEST = 1;
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";
    private Bitmap bitmap;
    private Uri filePath;
    String filname = "images";
    int random;
    String reportDate;
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("بلاغ تسرب مياه");

        editText1 = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);

        library_button = (Button) findViewById(R.id.button);

        Random rand = new Random();

        random = rand.nextInt((1999999999 - 1) + 1) + 1;


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        reportDate = sdf.format(date);

    }


    public void onClickLibraryPhoto(View view)
    {
        showFileChooser();
    }


    private void showFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");

        }


    }


    public void onFloatButtonClicked(View view) {

        area = editText1.getText().toString();
        description = editText2.getText().toString();
        phoneNumber = editText3.getText().toString();

        if (area.matches("")) {
            Context context = getApplicationContext();
            Toast.makeText(this, "نسيت ان تضع المنطقه", Toast.LENGTH_SHORT).show();
            return;
        } else if (description.matches("")) {
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
            values.put("IMAGE_RESOURCE_ID", phoneNumber);
            values.put("LIKES", likes);
            database.insert("REPORTSE", null, values);
            Intent loginIntent = new Intent (this, Reports.class);
            startActivity(loginIntent);
            Context context = getApplicationContext();
            */

            String uploadImage = "NoPhoto";
            if (bitmap != null) {
                uploadImage = getStringImage(bitmap);
            }
            // This realted to async
            HashMap postData = new HashMap();
            postData.put("area", area);
            postData.put("description", description);
            postData.put("phonenumber", phoneNumber);
            postData.put("counternumber", LoginActicity.COUNTER_NUMBER);
            postData.put("likes", String.valueOf(likes));
            postData.put("image", uploadImage);
            postData.put("filename", String.valueOf(random) + ".jpeg");
            postData.put("report_status", Report_status);
            postData.put("report_date",reportDate);
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute("http://androdimysqlapp.azurewebsites.net/addData.php");

        }
    }

    @Override
    public void processFinish(String result) {
        if (result.equals("success")) {
            Toast.makeText(this, "تم اضافة البلاغ", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Reports1.class);
            startActivity(intent);
            finish();
        }

        else {
            Toast.makeText(this, "يوجد خطأ في الشبكة اعد المحاوله", Toast.LENGTH_LONG).show();
        }
    }


    public String getStringImage(Bitmap bmp)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage;

    }



    public void onClickTakePhoto(View view)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


}