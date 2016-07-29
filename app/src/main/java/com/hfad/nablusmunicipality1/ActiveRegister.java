package com.hfad.nablusmunicipality1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class ActiveRegister extends AppCompatActivity {

    String code;
    EditText editTextCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_register);
        editTextCode = (EditText) findViewById(R.id.editText20);



    }


    public void onClickActive(View view)
    {

        code = editTextCode.getText().toString();

        HashMap postData = new HashMap();
        postData.put("name", RigesterActivity.userName);
        postData.put("counterNumber", RigesterActivity.counterNumber);
        postData.put("password", RigesterActivity.password);
        postData.put("regDate", RigesterActivity.regDate);
        postData.put("location", RigesterActivity.location);
        postData.put("email", RigesterActivity.email);
        postData.put("id_number", RigesterActivity.idNumber);



        if (code.matches(String.valueOf(RigesterActivity.random)))
        {

            com.kosalgeek.genasync12.PostResponseAsyncTask task = new com.kosalgeek.genasync12.PostResponseAsyncTask(this, postData, true, new com.kosalgeek.genasync12.AsyncResponse() {
                @Override
                public void processFinish(String s) {

                    if (s.equals("success")) {
                        Toast.makeText(ActiveRegister.this, "تم تسجيلك بنجاح", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ActiveRegister.this, LoginActicity.class);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(ActiveRegister.this, "يوجد خطا في الشبكه اعد المحاوله", Toast.LENGTH_LONG).show();
                }
            });

            task.execute("http://52.42.94.127/register.php");
        }

        else
        {
            Toast.makeText(ActiveRegister.this, "كود التفعيل الذي ادخلته غير صحيح اعد المحاولة", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed ()
    {
        Intent intent = new Intent(ActiveRegister.this,  Login.class);
        startActivity(intent);
        finish();
    }
}
