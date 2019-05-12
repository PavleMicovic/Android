package com.example.mama_tvoja;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CityDbHelper extends SQLiteOpenHelper {

    public static final String db_name="weather.db";
    public static final int db_version=1;

    public static final String table_name="table";
    public static final String column_date="date/time";
    public static final String column_city="city name";
    public static final String column_temp="temperature";
    public static final String column_pressure="pressure";
    public static final String column_humidity="humidity";
    public static final String column_sunrise="sunrise";
    public static final String column_sunset="sunset";
    public static final String column_wind_speed="wind speed";
    public static final String column_wind_direction="wind direction";


    public CityDbHelper(Context context){
        super(context, db_name, null, db_version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE"+ table_name+" (" + column_date+ " ,");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
