package com.hfad.nablusmunicipality1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Random;

public class ForgetPassword extends AppCompatActivity {

    EditText editTextemail;
    EditText editTextcounterNumber;

    public final String subject = "Nablus Municipality Forgot Password";

    int random;

    int counter = 0;
    String counterNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        editTextemail = (EditText) findViewById(R.id.editText15);
        editTextcounterNumber = (EditText) findViewById(R.id.editText16);

        }


    public void onClickButton(View view) {
        if (counter == 0) {
            final String email = editTextemail.getText().toString();
            counterNumber = editTextcounterNumber.getText().toString();

            if (email.matches(""))
                Toast.makeText(ForgetPassword.this, "لقد نسيت ان تكتب البريد الالكتروني", Toast.LENGTH_SHORT).show();

            else if (counterNumber.matches(""))
                Toast.makeText(ForgetPassword.this, "لقد نسيت ان تكتب رقم العداد", Toast.LENGTH_SHORT).show();

            else {
                HashMap postData = new HashMap();
                postData.put("email", email);
                postData.put("counterNumber", counterNumber);

                com.kosalgeek.genasync12.PostResponseAsyncTask task = new com.kosalgeek.genasync12.PostResponseAsyncTask(this, postData, false, new com.kosalgeek.genasync12.AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if (s.matches("success")) {

                            Random rand = new Random();
                            random = rand.nextInt((1999999999 - 1) + 1) + 1;

                            String message = "مرحبا بك في تطبيق بلدية نابلس كود التفعيل:" +
                                    " " +
                                    "\n\n" + String.valueOf(random);

                            SendMail sm = new SendMail(ForgetPassword.this, email, subject, message);
                            sm.execute();

                            TextView textViewCode = (TextView) findViewById(R.id.textView35);
                            TextView textViewNewPassword = (TextView) findViewById(R.id.textView37);

                            textViewCode.setText("كود التفعيل");
                            textViewNewPassword.setText("كلمة السر الجديدة");
                            editTextemail.setText("");
                            editTextcounterNumber.setText("");
                            editTextemail.setInputType(InputType.TYPE_CLASS_NUMBER);
                            editTextcounterNumber.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            counter = 1;

                        } else
                            Toast.makeText(ForgetPassword.this, "حدث حطأ ما!, تاكد من اتصال الشبكة او المعلومات المدخلة", Toast.LENGTH_SHORT).show();
                    }

                });

                task.execute("http://10.0.2.2/forgetPassword.php");
            }

        }

        else
        {
            final String code = editTextemail.getText().toString();
            String newPassword = editTextcounterNumber.getText().toString();

            if (code.matches(""))
                Toast.makeText(ForgetPassword.this, "نسيت ان تضع كود التفعيل", Toast.LENGTH_SHORT).show();

            else if (newPassword.matches(""))
                Toast.makeText(ForgetPassword.this, "نسيت ان تكتب الباسوورد الجديد", Toast.LENGTH_SHORT).show();

            else if(!code.matches(String.valueOf(random)))
            {
                Toast.makeText(ForgetPassword.this, "كود التفعيل خاطئ", Toast.LENGTH_SHORT).show();
            }

            else
            {
                HashMap postData = new HashMap();
                postData.put("counterNumber", counterNumber);
                postData.put("newPassword", newPassword);


                com.kosalgeek.genasync12.PostResponseAsyncTask task2 = new com.kosalgeek.genasync12.PostResponseAsyncTask(this, postData, false, new com.kosalgeek.genasync12.AsyncResponse()
                {

                    @Override
                    public void processFinish(String s) {

                        if (s.matches("success"))
                        {
                            Toast.makeText(ForgetPassword.this, "تم تغير كلمة السر بنجاح!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ForgetPassword.this, Login.class);
                            startActivity(intent);
                            finish();
                        }

                        else
                        {
                            Toast.makeText(ForgetPassword.this, "حدث خطأ ما, الرجاء التاكد من اتصال الشبكة", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                task2.execute("http://10.0.2.2/forgetPassword1.php");

            }
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
