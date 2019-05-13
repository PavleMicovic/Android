package com.example.mama_tvoja;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    public static final String column_wind_dir="wind direction";


    public CityDbHelper(Context context){
        super(context, db_name, null, db_version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_TABLE = "CREATE TABLE " + table_name + " (" +
                column_city + " TEXT NOT NULL," +
                column_date + " TEXT NOT NULL," +
                column_temp + " DOUBLE NOT NULL," +
                column_pressure + " DOUBLE NOT NULL," +
                column_humidity + " DOUBLE NOT NULL," +
                column_sunrise + " TEXT NOT NULL," +
                column_sunset + " TEXT NOT NULL," +
                column_wind_speed + " DOUBLE NOT NULL," +
                column_wind_dir + " TEXT NOT NULL" +
                ");";
        db.execSQL(SQL_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insert(Paket p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(column_city, p.getCity());
        cv.put(column_date, p.getDate());
        cv.put(column_temp, p.getTemperature());
        cv.put(column_pressure, p.getPressure());
        cv.put(column_humidity, p.getHumidity());
        cv.put(column_sunrise, p.getSunrise());
        cv.put(column_sunset, p.getSunset());
        cv.put(column_wind_speed, p.getWindSpeed());
        cv.put(column_wind_dir, p.getWindDirection());

        if(db.insert(table_name, null, cv)==-1) {
            db.close();
            return false;
        }
        else{
            db.close();
            return true;
        }

    }
    public boolean remove(String city){
        SQLiteDatabase db=this.getWritableDatabase();
        if(db.delete(table_name, column_city+"=?", new String[]{city})==-1){
            db.close();
            return false;
        }
        else{
            db.close();
            return true;
        }
    }

    public String[] getCities(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + column_city + " FROM " + table_name + " GROUP BY " + column_city + " ;", null, null);
        if (cursor.getCount() <= 0)
        return null;

        String[] cities = new String[cursor.getCount()];

        int i = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
            cities[i++] = cursor.getString(0);
        db.close();
        cursor.close();

        return cities;
    }
}
