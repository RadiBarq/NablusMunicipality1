package com.hfad.nablusmunicipality1;
    import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;



public class LoginActicity extends AppCompatActivity implements AsyncResponse {


    public static String COUNTER_NUMBER = "reporteNo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acticity);


        }
    public void onClickLoginButton(View view) {
        EditText editText7 = (EditText) findViewById(R.id.editText7);
        EditText editText8 = (EditText) findViewById(R.id.editText8);

        String buildNumber = editText7.getText().toString();
        String password = editText8.getText().toString();

        if (buildNumber.matches(""))
            Toast.makeText(this, "نسيت رقم العداد", Toast.LENGTH_SHORT).show();
        else if (password.matches(""))
            Toast.makeText(this, "نسيت ان تضع كلمة السر", Toast.LENGTH_SHORT).show();

      else {
            // This realted to async
            HashMap postData = new HashMap();
            postData.put("txtUsername", buildNumber);
            postData.put("txtPassword", password);
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute("http://androdimysqlapp.azurewebsites.net/login.php");
            COUNTER_NUMBER = buildNumber;

        }
    }
    @Override
    public void processFinish(String result) {

        if (result.equals("success")) {
            Toast.makeText(this, "تم تسجيل دخولك بنجاح", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, Reports1.class);
            startActivity(intent);
            finish();
        } else
            Toast.makeText(this, "تاكد من اتصال الشبكة او المعلومات المدخله", Toast.LENGTH_SHORT).show();
    }
}
