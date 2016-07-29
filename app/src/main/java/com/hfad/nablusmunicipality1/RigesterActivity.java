package com.hfad.nablusmunicipality1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.Toast;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import com.kosalgeek.genasync12.AsyncResponse;

public class RigesterActivity extends AppCompatActivity implements AsyncResponse {


public static String counterNumber;
public static String userName;
    public static String password;
   public static String second_password;
   public static String location;
   public static String email;
   public static String idNumber;
    String subject = "Nablus Municipality App Registration";


    public static String regDate;
    String Checker = "hi";
    public static int random;
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
        EditText editText9 = (EditText) findViewById(R.id.editText11);
        EditText editText10 = (EditText) findViewById(R.id.editText12);
        EditText editText19 = (EditText) findViewById(R.id.editText19);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        regDate = sdf.format(date);


         counterNumber = edittext4.getText().toString();
         userName = editText5.getText().toString();
         password = editText6.getText().toString();
         second_password = editText7.getText().toString();
         location = editText9.getText().toString();
         email = editText10.getText().toString().toLowerCase();
         idNumber = editText19.getText().toString();

        if (counterNumber.matches(""))
            Toast.makeText(this, "نسيت ان تكتب رقم الخدمة", Toast.LENGTH_SHORT).show();

        else if (userName.matches(""))
            Toast.makeText(this, "نسيت خانة الاسم", Toast.LENGTH_SHORT).show();

        else if (password.matches(""))
            Toast.makeText(this, "نسيت كلمة السر", Toast.LENGTH_SHORT).show();

        else if (location.matches(""))
            Toast.makeText(this, "نسيت ان تكتب منطقة السكن", Toast.LENGTH_SHORT).show();

        else if (idNumber.matches(""))
            Toast.makeText(this, "نسيت ان تكتب رقم الهوية", Toast.LENGTH_LONG).show();

        else if (password.length() <= 6)
            Toast.makeText(this, "كلمة السر يجب ان تكون اكثر من 6 خانات", Toast.LENGTH_SHORT).show();

        else if (email.matches(""))
            Toast.makeText(this, "نسيت ان تكتب البريد الالكتروني", Toast.LENGTH_SHORT).show();

        else if (!email.contains("@"))
            Toast.makeText(this, "صيغة البريد الالكتروني غير صحيحة", Toast.LENGTH_SHORT).show();

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
            postData.put("id_number", idNumber);

            PostResponseAsyncTask task12 = new PostResponseAsyncTask(this, postData, false, new AsyncResponse() {
                @Override

                public void processFinish(String s) {


                    Checker = s;
                    if (s.matches("unsuccess")) {
                        Random rand = new Random();
                        random = rand.nextInt((1999999999 - 1) + 1) + 1;

                        String message = "مرحبا بك في بلدية نابلس قسم شكاوي الماء, كود التفعيل: " +
                                "" +
                                "\n\n" + String.valueOf(random);

                        SendMail sm = new SendMail(RigesterActivity.this, email, subject, message);
                        sm.execute();

                        Intent intent = new Intent(RigesterActivity.this, ActiveRegister.class);
                        startActivity(intent);
                        finish();
                    } else if (Checker.matches("")) {
                        Toast.makeText(RigesterActivity.this, "الرجاء التاكد من اتصال الشبكة", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RigesterActivity.this, " تم تسجيل هاذا البريد الالكتروني مسبقا اعد المحاولة", Toast.LENGTH_LONG).show();
                    }
                }
            });

            task12.execute("http://52.42.94.127/registerCheck.php");

        }
    }

            @Override
            public void processFinish (String result){
                if (result.equals("success")) {
                    Toast.makeText(this, "تم تسجيلك بنجاح", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActicity.class);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(this, "يوجد خطا في الشبكه اعد المحاوله", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onBackPressed ()
                {
                    Intent intent = new Intent(RigesterActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                }
}