package com.hfad.nablusmunicipality1;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import java.util.HashMap;

public class Test extends AppCompatActivity{
int counter  = 0;
public static int image_checker = 0;
private View mProgressView;
    String hey = "http://10.0.2.2/images/";
    String Image_id;
    String url = hey + Image_id;
    public static int likesChecker = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        HashMap postData = new HashMap();
        postData.put("id", Reports1.idNum);

        HashMap postData1 = new HashMap();
        postData1.put("counterNumber", LoginActicity.COUNTER_NUMBER);
        postData1.put("reportId", R5.COUNTERNUMBER);


        mProgressView = findViewById(R.id.login_progress1);
        showProgress(true);

        PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData, false, new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                R2.Likes = s;
            }
        });
        task.execute("http://10.0.2.2/getOrder-3.php");
        PostResponseAsyncTask task1 = new PostResponseAsyncTask(this, postData, false, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                R4.area = s;
            }
        });
        task1.execute("http://10.0.2.2/getOrder.php");
        PostResponseAsyncTask task2 = new PostResponseAsyncTask(this, postData, false, new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                if (s.matches(""))
                    Report.Checker = 1;

                R5.COUNTERNUMBER = s;
            }
        });
        task2.execute("http://10.0.2.2/getCounter.php");
        PostResponseAsyncTask task3 = new PostResponseAsyncTask(this, postData1, false, new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                if (s.matches("success"))
                    likesChecker = 1;

                else
                    likesChecker = 0;
            }
        });
        task3.execute("http://10.0.2.2/checkLikes.php");
        PostResponseAsyncTask task4 = new PostResponseAsyncTask(this, postData, false, new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                if (s.matches("NoPhoto"))
                {
                    image_checker = 1;

                }

                else {

                    image_checker = 0;
                    Report.image_id = s;
                }

            }
        });
        task4.execute("http://10.0.2.2/getOrder-4.php");
        PostResponseAsyncTask task5 = new PostResponseAsyncTask(this, postData, false, new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                if (s.matches("1"))
                R8.report_status = "لم ينظر به بعد";
                else if (s.matches("2"))
                    R8.report_status = "يتم العمل عليه";
                else if(s.matches("3"))
                    R8.report_status = "تم الانتهاء من العمل على البلاغ"
                            ;


            }
        });
        task5.execute("http://10.0.2.2/getOrder-5.php");

        PostResponseAsyncTask task6 = new PostResponseAsyncTask(this, postData, false, new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                R9.report_date = s;
                Intent intent = new Intent(Test.this, Report.class);
                startActivity(intent);
                showProgress(false);
                finish();
            }
        });

        task6.execute("http://10.0.2.2/getOrder-6.php");
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show)
    {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            int shortAnimeTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimeTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }
        else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        }

    }



}
