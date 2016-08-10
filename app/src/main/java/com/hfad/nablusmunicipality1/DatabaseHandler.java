package com.hfad.nablusmunicipality1;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "REPORTS";

    // Table Name
    private static final String TABLE_SAVED_REPORTS = "SAVED_REPORTS";


    // Table Columns
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_LOCATION = "location";




    private static final int DATABASE_VERSION = 1;



    private static final String TABLE_REPORTS = "SAVED_REPORTS";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_REPORTS_TABLE = "CREATE_TABLE" + TABLE_REPORTS + "(" + KEY_DESCRIPTION + "TEXT," + KEY_PHONE_NUMBER + "TEXT," + KEY_LOCATION
                + "TEXT" + ")";
        db.execSQL(CREATE_REPORTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXIST " + TABLE_REPORTS);

        // Create tables again
        onCreate(db);

    }


    // Adding new contact
    void addReport(Order order)
    {
        SQLiteDatabase db = this.getWritableDatabase();



        db.close();
    }

}
