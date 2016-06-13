package com.hfad.nablusmunicipality1;

import android.content.Context;
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

import com.kosalgeek.genasync12.AsyncResponse;

public class RigesterActivity extends AppCompatActivity implements AsyncResponse {



    String regDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigester);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    public void onClickRegButton(View view) {

        EditText edittext4 = (EditText) findViewById(R.id.editText14);
        EditText editText5 = (EditText) findViewById(R.id.editText13);
        EditText editText6 = (EditText) findViewById(R.id.editText6);
        EditText editText7 = (EditText) findViewById(R.id.editText);
        EditText editText9 = (EditText)  findViewById(R.id.editText11);
        EditText editText10 = (EditText) findViewById(R.id.editText12);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        regDate = sdf.format(date);


        String counterNumber = edittext4.getText().toString();
        String userName = editText5.getText().toString();
        String password = editText6.getText().toString();
        String second_password = editText7.getText().toString();
        String location = editText9.getText().toString();
        String email = editText10.getText().toString();
        if (counterNumber.matches(""))
            Toast.makeText(this, "نسيت رقم العداد", Toast.LENGTH_SHORT).show();
        else if (userName.matches(""))
            Toast.makeText(this, "نسيت خانة الاسم", Toast.LENGTH_SHORT).show();
        else if (password.matches(""))
            Toast.makeText(this, "نسيت كلمة السر", Toast.LENGTH_SHORT).show();

        else if (location.matches(""))
            Toast.makeText(this, "نسيت ان تكتب منطقة السكن", Toast.LENGTH_SHORT).show();

        else if (password.length() <= 6)
            Toast.makeText(this, "كلمة السر يجب ان تكون اكثر من 6 خانات", Toast.LENGTH_SHORT).show();

        else if (email.matches(""))
            Toast.makeText(this, "نسيت ان تكتب البريد الالكتروني", Toast.LENGTH_SHORT);

        if (!email.contains("@"))
            Toast.makeText(this, "صيغة البريد الالكتروني غير صحيحة", Toast.LENGTH_SHORT);


        else if (!password.matches(second_password))
            Toast.makeText(this, "كلمات السر غير متطابقه", Toast.LENGTH_SHORT).show();

        else {

            HashMap postData = new HashMap();
            postData.put("name", userName);
            postData.put("counterNumber", counterNumber);
            postData.put("password", password);
            postData.put("regDate", regDate);
            postData.put("location", location);
            postData.put("email", email);
            com.kosalgeek.genasync12.PostResponseAsyncTask task = new com.kosalgeek.genasync12.PostResponseAsyncTask(this, postData, false, new com.kosalgeek.genasync12.AsyncResponse() {
                @Override
                public void processFinish(String s) {

                    if (s.equals("success")) {
                        Toast.makeText(RigesterActivity.this, "تم تسجيلك بنجاح", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RigesterActivity.this, LoginActicity.class);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(RigesterActivity.this, "يوجد خطا في الشبكه اعد المحاوله", Toast.LENGTH_LONG).show();
                }
            });

            task.execute("http://10.0.2.2//register.php");
        }
    }

    @Override
    public void processFinish(String result) {
        if (result.equals("success")) {
            Toast.makeText(this, "تم تسجيلك بنجاح", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActicity.class);
            startActivity(intent);
            finish();
        }

        else
            Toast.makeText(this, "يوجد خطا في الشبكه اعد المحاوله", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(RigesterActivity.this, Login.class);
        startActivity(intent);
        finish();
    }

}
