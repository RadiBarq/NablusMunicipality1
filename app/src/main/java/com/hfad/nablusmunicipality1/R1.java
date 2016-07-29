package com.hfad.nablusmunicipality1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

/**
 * Created by sbitanyhome on 1/11/2016.
 */
public class R1 extends AppCompatActivity implements AsyncResponse {

    String id;
    String description;

    R1(String id)
    {
        HashMap postData = new HashMap();
        postData.put("id", id);
        PostResponseAsyncTask task = new PostResponseAsyncTask(this, (AsyncResponse) postData);
        task.execute("http://52.42.94.127/R1.php");
    }


    @Override
    public void processFinish(String s) {

        description = s;

    }

    public String returnDescription()
    {
        return description;
    }
}
