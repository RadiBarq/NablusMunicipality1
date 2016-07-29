package com.hfad.nablusmunicipality1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Accounts extends AppCompatActivity {

    EditText editTextOldPassword;
    EditText editTextNewPassword;
    TextView textViewName;
    TextView textViewCounterNumber;
    TextView textViewRegDate;
    TextView textViewLocation;
    TextView textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         editTextOldPassword = (EditText) findViewById(R.id.editText9);
         editTextNewPassword = (EditText) findViewById(R.id.editText10);

         textViewCounterNumber = (TextView) findViewById(R.id.textView22);
         textViewCounterNumber.setText(LoginActicity.COUNTER_NUMBER);

        textViewName = (TextView) findViewById(R.id.textView20);
        textViewRegDate = (TextView) findViewById(R.id.textView26);
        textViewLocation = (TextView) findViewById(R.id.textView24);
        textViewEmail = (TextView) findViewById(R.id.textView34);

        HashMap postData = new HashMap();
        postData.put("counterNumber", LoginActicity.COUNTER_NUMBER);
        com.kosalgeek.genasync12.PostResponseAsyncTask task = new com.kosalgeek.genasync12.PostResponseAsyncTask(this, postData, false, new com.kosalgeek.genasync12.AsyncResponse() {
            @Override
            public void processFinish(String s) {

                textViewName.setText(s);
            }
        });

        postData.put("counterNumber", LoginActicity.COUNTER_NUMBER);
        com.kosalgeek.genasync12.PostResponseAsyncTask task2 = new com.kosalgeek.genasync12.PostResponseAsyncTask(this, postData, false, new com.kosalgeek.genasync12.AsyncResponse() {
            @Override
            public void processFinish(String s) {

                textViewRegDate.setText(s);
            }
        });

        postData.put("counterNumber", LoginActicity.COUNTER_NUMBER);
        com.kosalgeek.genasync12.PostResponseAsyncTask task3 = new com.kosalgeek.genasync12.PostResponseAsyncTask(this, postData, false, new com.kosalgeek.genasync12.AsyncResponse() {
            @Override
            public void processFinish(String s) {

                textViewLocation.setText(s);
            }
        });

        postData.put("email", LoginActicity.COUNTER_NUMBER);
        com.kosalgeek.genasync12.PostResponseAsyncTask task4 = new com.kosalgeek.genasync12.PostResponseAsyncTask(this, postData, true, new com.kosalgeek.genasync12.AsyncResponse() {
            @Override
            public void processFinish(String s)
            {
                textViewEmail.setText(s);
            }
        });

        task.execute("http://52.42.94.127/accounts-name.php");
        task2.execute("http://52.42.94.127/accounts-regDate.php");
        task3.execute("http://52.42.94.127/accounts-location.php");
        task4.execute("http://52.42.94.127/email.php");
    }

    public void onClickButton(View view)
    {



        String password = editTextOldPassword.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();

        if(password.matches(""))
            Toast.makeText(Accounts.this, "نسيت ان تضع كلمة السر السابقة", Toast.LENGTH_SHORT).show();

        else if (newPassword.matches(""))
            Toast.makeText(Accounts.this, "نسيت ان تضع كلمة السر الجديد", Toast.LENGTH_SHORT).show();

        else {
            HashMap postData1 = new HashMap();

            postData1.put("newPassword", newPassword);
            postData1.put("counterNumber", LoginActicity.COUNTER_NUMBER);

            com.kosalgeek.genasync12.PostResponseAsyncTask task3 = new com.kosalgeek.genasync12.PostResponseAsyncTask(this, postData1, false, new com.kosalgeek.genasync12.AsyncResponse() {
                @Override
                public void processFinish(String s) {

                    if (s.matches("success"))
                    {
                        Toast.makeText(Accounts.this, "تم تغير كلمة السر بنجاح", Toast.LENGTH_LONG).show();
                        editTextNewPassword.setText("");
                        editTextOldPassword.setText("");
                    }


                    else {
                        Toast.makeText(Accounts.this, "حدث خطأ ما, تاكد من كلمة السر السابقة او اتصال الشبكة", Toast.LENGTH_LONG).show();
                    }
                }
            });

            task3.execute("http://52.42.94.127/change-password.php");

        }
    }

}
