package com.example.instacalcid1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class last_activity_data extends SQLiteOpenHelper {

    public static final String LOGIN_DATA = "LOGIN_DATA";
    public static final String COLUMN_ID = "COLUMN_ID";
    public static final String COLUMN_USERNAME = "COLUMN_USERNAME";
    public static final String COLUMN_DESCRIPTION = "COLUMN_DESCRIPTION";
    public static final String COLUMN_IS_LOGIN = "COULUMN_IS_LOGIN";

    private static final String LOGIN_DATABASE = "login.db";
    public last_activity_data(@Nullable Context context) {
        super(context, LOGIN_DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createdatabase = "CREATE TABLE " + LOGIN_DATA + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY  , "  +
                COLUMN_USERNAME + " TEXT , " +
                COLUMN_DESCRIPTION + " TEXT , " +
                COLUMN_IS_LOGIN +"  INT ) ";
        db.execSQL(createdatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void updatedatabase(loggined_user_details applicationStatus) {
        SQLiteDatabase login_data = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, 0);
        cv.put(COLUMN_USERNAME, applicationStatus.getName());
        cv.put(COLUMN_DESCRIPTION, applicationStatus.getDescription());
        cv.put(COLUMN_IS_LOGIN,applicationStatus.isIslogined());

        long insert = login_data.insert(LOGIN_DATA, null, cv);


    }

    public int islogined(){
        String querystring = "SELECT * FROM " + LOGIN_DATA;
        SQLiteDatabase login_data  = this.getReadableDatabase();
        Cursor cursor = login_data.rawQuery(querystring, null);
        int islogin =  0;

        if (cursor.moveToFirst()){
            String person_name = cursor.getString(1);
            islogin = cursor.getInt(3) ;
        }
        cursor.close();
        login_data.close();
        return islogin;
    }
}
