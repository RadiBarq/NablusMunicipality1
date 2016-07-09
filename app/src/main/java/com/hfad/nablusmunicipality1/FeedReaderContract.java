package com.hfad.nablusmunicipality1;

import android.provider.BaseColumns;

/**
 * Created by sbitanyhome on 6/21/2016.
 */
public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "saved_report";
        public static final String COLUMN_NAME_DESCRIPTION  = "description";
        public static final String COLUMN_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        public static final String COLUMN_NAME_LOCATION = "location";
    }
}
