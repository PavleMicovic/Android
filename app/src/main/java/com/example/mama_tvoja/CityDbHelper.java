package com.example.mama_tvoja;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CityDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "weather1.db";
    public static final int DATABASE_VERSION = 1;


    public static final String TABLE_NAME = "weather";
    public static final String COLUMN_DATE_TIME = "date_time";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_TEMPERATURE = "temperature";
    public static final String COLUMN_PRESSURE = "pressuere";
    public static final String COLUMN_HUMIDITY = "humidity";
    public static final String COLUMN_SUNSET = "sunset";
    public static final String COLUMN_SUNRISE = "sunrise";
    public static final String COLUMN_WIND_SPEED = "wind_speed";
    public static final String COLUMN_WIND_DIRECTION = "wind_direction";


    private SQLiteDatabase mDb = null;

    public CityDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MSG", "CREATE");
        final String SQL_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_DATE_TIME + " TEXT," +
                COLUMN_LOCATION + " TEXT," +
                COLUMN_TEMPERATURE + " TEXT," +
                COLUMN_PRESSURE + " TEXT," +
                COLUMN_HUMIDITY + " TEXT," +
                COLUMN_SUNSET + " TEXT," +
                COLUMN_SUNRISE + " TEXT," +
                COLUMN_WIND_SPEED + " TEXT," +
                COLUMN_WIND_DIRECTION + " TEXT" +
                ");";

        db.execSQL(SQL_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(Paket p){
        Log.d("MSG", "INSERT");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_LOCATION, p.getLocation());
        cv.put(COLUMN_DATE_TIME, p.getDate_time());
        cv.put(COLUMN_TEMPERATURE, p.getTemperature());
        cv.put(COLUMN_PRESSURE, p.getPressure());
        cv.put(COLUMN_HUMIDITY, p.getHumidity());
        cv.put(COLUMN_SUNRISE, p.getSun_rise());
        cv.put(COLUMN_SUNSET, p.getSun_set());
        cv.put(COLUMN_WIND_SPEED, p.getWind_speed());
        cv.put(COLUMN_WIND_DIRECTION, p.getWind_direction());

        Paket p1=contains(p.getLocation(), p.getDate_time());
        if(p1==null)
            db.insert(TABLE_NAME, null, cv);
        close();
    }

    public Paket contains(String city, String date){
        SQLiteDatabase db = this.getReadableDatabase();
        Paket p;
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_LOCATION + " = \"" + city + "\" AND "
                + COLUMN_DATE_TIME + " = \"" + date + "\" ;", null, null);



        if (cursor.getCount() <= 0) {
            return null;
        }

        cursor.moveToFirst();
        p = createObject(cursor);

        return p;

    }

    public Paket createObject(Cursor cursor)
    {
        String date_time = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME));
        String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
        String temperature = cursor.getString(cursor.getColumnIndex(COLUMN_TEMPERATURE));
        String pressure = cursor.getString(cursor.getColumnIndex(COLUMN_PRESSURE));
        String humidity = cursor.getString(cursor.getColumnIndex(COLUMN_HUMIDITY));
        String sunset = cursor.getString(cursor.getColumnIndex(COLUMN_SUNSET));
        String sunrise = cursor.getString(cursor.getColumnIndex(COLUMN_SUNRISE));
        String wind_speed = cursor.getString(cursor.getColumnIndex(COLUMN_WIND_SPEED));
        String wind_direction = cursor.getString(cursor.getColumnIndex(COLUMN_WIND_DIRECTION));
        Log.d("MSG", "5027542930572");


        return new Paket(location, date_time, temperature, humidity, pressure, sunrise, sunset, wind_speed, wind_direction);
    }

    public Paket[] read_all_data(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        if(cursor.getCount() <= 0){
            return null;
        }

        Paket[] all_data = new Paket[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            all_data[i++] = createObject(cursor);
        }

        close();
        return all_data;

    }

    public Paket readData(String location){
        SQLiteDatabase db = getReadableDatabase();
        Log.d("MSG", "1");

        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_LOCATION + "=?",
                new String[] {location}, null, null, null);
        Log.d("MSG", "2");


        cursor.moveToFirst();
        Log.d("MSG", "3");

        Paket data = createObject(cursor);
        Log.d("MSG", "4");


        close();
        return data;
    }

}
