package com.hfad.nablusmunicipality1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddReportStatus extends AppCompatActivity {

    EditText EditTextStatus;
    String reportDate;
    EditText EditTextManuSatatus;
    SQLiteDatabase db;
    String description;
    String area;
    String phoneNumber;
    private Cursor c;
    int Checker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EditTextStatus = (EditText) findViewById(R.id.editText17);
        EditTextManuSatatus = (EditText) findViewById(R.id.editText18);

        db = openOrCreateDatabase(
                "savedData.db"
                , SQLiteDatabase.CREATE_IF_NECESSARY
                , null
        );


        if (Checker == 1
                ) {
            getDATA();
        }


        final String Create_savedData =

                "CREATE TABLE CashData ("
                        + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "Description TEXT,"
                        + "AREA TEXT,"
                        + "PHONE_NUMBER INTEGER,"
                        + "DATE TEXT);";


    }

    public void onClickAdd(View view) {
        String report_status = EditTextStatus.getText().toString();
        String manusStatus = EditTextManuSatatus.getText().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        reportDate = sdf.format(date);

        HashMap hashMap = new HashMap();
        hashMap.put("report_id", Reports1.idNum);
        hashMap.put("report_status", report_status);
        hashMap.put("report_date", reportDate);
        hashMap.put("manu_status", manusStatus);


        PostResponseAsyncTask task = new PostResponseAsyncTask(this, hashMap, false, new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                if (s.matches("success")) {
                    Toast.makeText(AddReportStatus.this, "تم اضافة الحالة", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddReportStatus.this, Reports1.class);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(AddReportStatus.this, "يوجد مشكلة تاكد من اتصال الشبكة", Toast.LENGTH_LONG).show();
                }
            }
        });


        task.execute("http://10.0.2.2/addReportStatus.php");
    }

    public void onClickEnd(View view) {
        HashMap hashMap = new HashMap();
        hashMap.put("id", Reports1.idNum);
        PostResponseAsyncTask task = new PostResponseAsyncTask(this, hashMap, false, new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                if (s.matches("success")) {
                    Toast.makeText(AddReportStatus.this, "تم الانتهاء من البلاغ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddReportStatus.this, Reports1.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddReportStatus.this, "يوجد مشكلة تاكد من اتصال الشبكة", Toast.LENGTH_LONG).show();
                }
            }
        });

        task.execute("http://10.0.2.2/endReportStatus.php");
    }


    public void onClickSave(View view) {
        TextView textViewArea = (TextView) findViewById(R.id.textView3);
        TextView textViewDesscription = (TextView) findViewById(R.id.textView2);
        TextView textViewPhoneNumber = (TextView) findViewById(R.id.textView2);
        TextView textViewDate = (TextView) findViewById(R.id.textView2);
        String area = textViewArea.getText().toString();
        String description = textViewDesscription.getText().toString();
        String phoneNumber = textViewPhoneNumber.getText().toString();
        String date = textViewDate.getText().toString();

        Checker  = 1;

        db.execSQL("Create_Crash_Course");
        final String Insert_Data = "INSERT INTO CashData VALUES(area, description ,phoneNumber, date)";
        db.execSQL(Insert_Data);
    }

    public void getDATA() {
        db.rawQuery("SELECT * FROM data", null);
        c.moveToFirst();
        showRecords();
    }


    protected void showRecords() {
        String area = c.getString(0);
        String description = c.getString(1);
        String phoneNumber = c.getString(2);
        String date = c.getString(3);

        TextView textViewArea = (TextView) findViewById(R.id.textView3);
        TextView textViewDesscription = (TextView) findViewById(R.id.textView2);
        TextView textViewPhoneNumber = (TextView) findViewById(R.id.textView2);
        TextView textViewDate = (TextView) findViewById(R.id.textView2);

        textViewArea.setText(area);
        textViewDesscription.setText(description);
        textViewPhoneNumber.setText(phoneNumber);
        textViewDate.setText(date);

    }
}