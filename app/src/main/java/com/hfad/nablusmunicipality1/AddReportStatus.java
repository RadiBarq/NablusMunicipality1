package com.hfad.nablusmunicipality1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddReportStatus extends AppCompatActivity {

    EditText EditTextStatus;
    String reportDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EditTextStatus = (EditText) findViewById(R.id.editText17);


    }

    public void onClickAdd (View view)
    {
        String report_status = EditTextStatus.getText().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        reportDate = sdf.format(date);

        HashMap hashMap = new HashMap();
        hashMap.put("report_id", Reports1.idNum + "_table");
        hashMap.put("report_status", report_status);
        hashMap.put("report_date", reportDate);



        PostResponseAsyncTask task = new PostResponseAsyncTask(this, hashMap, false, new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                if (s.matches("success")) {
                    Toast.makeText(AddReportStatus.this, "تم اضافة الحالة", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddReportStatus.this, Reports1.class);
                    startActivity(intent);
                    finish();
                }
                else
                {

                    Toast.makeText(AddReportStatus.this, "يوجد مشكلة تاكد من اتصال الشبكة", Toast.LENGTH_LONG).show();
                }
            }
        });

        task.execute("http://10.0.2.2/addReportStatus.php");
    }
    public void onClickEnd(View view)
    {
        HashMap hashMap = new HashMap();
        hashMap.put("id", Reports1.idNum + "_table");
        PostResponseAsyncTask task = new PostResponseAsyncTask(this, hashMap, false, new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                if (s.matches("success")) {
                    Toast.makeText(AddReportStatus.this, "تم الانتهاء من البلاغ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddReportStatus.this, Reports1.class);
                    startActivity(intent);
                    finish();
                }

                else
                {
                    Toast.makeText(AddReportStatus.this, "يوجد مشكلة تاكد من اتصال الشبكة", Toast.LENGTH_LONG).show();
                }
            }
        });

        task.execute("http://10.0.2.2/endReportStatus.php");
    }


}
