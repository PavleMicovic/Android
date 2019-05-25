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


    public static final String COLUMN_DAY = "day";
    public static final String TABLE_NAME = "weather";
    public static final String COLUMN_DATE_TIME = "date_time";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_TEMPERATURE = "temperature";
    public static final String COLUMN_PRESSURE = "pressuere";
    public static final String COLUMN_HUMIDITY = "humidity";
    public static final String COLUMN_SUN_SET = "sun_set";
    public static final String COLUMN_SUN_RISE = "sun_rise";
    public static final String COLUMN_WIND_SPEED = "wind_speed";
    public static final String COLUMN_WIND_DIRECTION = "wind_direction";


    private SQLiteDatabase mDb = null;

    public CityDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_DAY + " TEXT," +
                COLUMN_DATE_TIME + " TEXT," +
                COLUMN_LOCATION + " TEXT," +
                COLUMN_TEMPERATURE + " TEXT," +
                COLUMN_PRESSURE + " TEXT," +
                COLUMN_HUMIDITY + " TEXT," +
                COLUMN_SUN_SET + " TEXT," +
                COLUMN_SUN_RISE + " TEXT," +
                COLUMN_WIND_SPEED + " TEXT," +
                COLUMN_WIND_DIRECTION + " TEXT" +
                ");";

        db.execSQL(SQL_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*public void insert(Data data){
        SQLiteDatabase db = getWritableDatabase();

        Log.d("WEATHER", "Provera");
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE_TIME, data.getDate_time());
        values.put(COLUMN_LOCATION, data.getLocation());
        values.put(COLUMN_TEMPERATURE, data.getTemperature());
        values.put(COLUMN_PRESSURE, data.getPressure());
        values.put(COLUMN_HUMIDITY, data.getHumidity());
        values.put(COLUMN_SUN_SET, data.getSun_set());
        values.put(COLUMN_SUN_RISE, data.getSun_rise());
        values.put(COLUMN_WIND_SPEED, data.getWind_speed());
        values.put(COLUMN_WIND_DIRECTION, data.getWind_direction());

        db.insert(TABLE_NAME, null, values);
        close();

    }*/

    public void insert(Paket data) {
        SQLiteDatabase db = this.getWritableDatabase();



        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY, data.getDay());
        values.put(COLUMN_DATE_TIME, data.getDate_time());
        values.put(COLUMN_LOCATION, data.getLocation());
        values.put(COLUMN_TEMPERATURE, data.getTemperature());
        values.put(COLUMN_PRESSURE, data.getPressure());
        values.put(COLUMN_HUMIDITY, data.getHumidity());
        values.put(COLUMN_SUN_SET, data.getSun_set());
        values.put(COLUMN_SUN_RISE, data.getSun_rise());
        values.put(COLUMN_WIND_SPEED, data.getWind_speed());
        values.put(COLUMN_WIND_DIRECTION, data.getWind_direction());

        Paket d = exists(data.getLocation(), data.getDate_time());


        if(d == null){
            db.insert(TABLE_NAME, null, values);
            close();
        }else{
            close();
        }

    }

    public Paket exists(String location, String date_time){
        SQLiteDatabase db = this.getReadableDatabase();
        Paket data;
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_LOCATION + " = \"" + location + "\" AND "
                + COLUMN_DATE_TIME + " = \"" + date_time + "\" ;", null, null);



        if (cursor.getCount() <= 0) {
            return null;
        }

        cursor.moveToFirst();
        data = createData(cursor);


        return data;


    }

    public void insert_location(Paket data) {
        SQLiteDatabase db = this.getWritableDatabase();



        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY, data.getDay());
        values.put(COLUMN_DATE_TIME, data.getDate_time());
        values.put(COLUMN_LOCATION, data.getLocation());
        values.put(COLUMN_TEMPERATURE, data.getTemperature());
        values.put(COLUMN_PRESSURE, data.getPressure());
        values.put(COLUMN_HUMIDITY, data.getHumidity());
        values.put(COLUMN_SUN_SET, data.getSun_set());
        values.put(COLUMN_SUN_RISE, data.getSun_rise());
        values.put(COLUMN_WIND_SPEED, data.getWind_speed());
        values.put(COLUMN_WIND_DIRECTION, data.getWind_direction());

        Paket d = exists_location(data.getLocation());


        if(d == null){
            db.insert(TABLE_NAME, null, values);
            close();
        }else{
            close();
        }

    }


    public Paket exists_location(String location){
        SQLiteDatabase db = this.getReadableDatabase();
        Paket data;
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_LOCATION + "=?",
                new String[] {location}, null, null, null);

        if (cursor.getCount() <= 0) {
            return null;
        }

        cursor.moveToFirst();
        data = createData(cursor);



        return data;


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
            all_data[i++] = createData(cursor);
        }

        close();
        return all_data;

    }

    public Paket readData(String location){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_LOCATION + "=?",
                new String[] {location}, null, null, null);
        cursor.moveToLast();
        Paket data = createData(cursor);
        Log.d("read_data kreira ovo:", "temp:"+data.getTemperature()+"pritisak:"+data.getPressure()+"humidity:"+data.getHumidity());

        close();
        return data;
    }


    public Paket readDay(String day){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_DAY + "=?",
                new String[] {day}, null, null, null);
        cursor.moveToFirst();
        Paket data = createData(cursor);

        close();
        return data;
    }

    public Paket[] read_data_location(String location){
        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_LOCATION + "=?",
                new String[] {location}, null, null, null);

        if(cursor.getCount() <= 0){
            return null;
        }


        Paket[] all_data = new Paket[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            all_data[i++] = createData(cursor);
        }



        close();
        return all_data;


    }

    public void deleteData(String date_time, String location){
        SQLiteDatabase db = getWritableDatabase();
        //String whereClause = "id=?";
        //String whereArgs[] = {date_time, location};
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_LOCATION + " = \"" + location + "\" AND "
                + COLUMN_DATE_TIME + " = \"" + date_time + "\" ;", null, null);

        if(cursor.getCount() > 0){
            db.delete(TABLE_NAME, COLUMN_DATE_TIME + "=? and " + COLUMN_LOCATION , new String[] { date_time, location});
        }


        //db.delete(TABLE_NAME, COLUMN_DATE_TIME + "=? AND " + COLUMN_LOCATION , new String[] { date_time, location});
        //db.delete(TABLE_NAME, COLUMN_DATE_TIME + "=?", new String[] {date_time});
        //close();
    }

    /*public String[] readDataLocation(String location){
        String[] lista = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_LOCATION + "=?",
                new String[] {location}, null, null, null);

        if(cursor.getCount() <= 0){
            return null;
        }

        int i = 0;

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            lista[i++] = (cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME)) + "   " + cursor.getString(cursor.getColumnIndex(COLUMN_TEMPERATURE)) + "   " + cursor.getString(cursor.getColumnIndex(COLUMN_PRESSURE)) + cursor.getString(cursor.getColumnIndex(COLUMN_HUMIDITY)) + "/n");
        }

        return lista;

    }*/

    public Paket[] sort_date_time(String location){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_DATE_TIME+" DESC");

        Paket[] all_data = new Paket[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            all_data[i++] = createData(cursor);
        }



        close();
        return all_data;

    }



    private Paket createData(Cursor cursor){
        String day = cursor.getString(cursor.getColumnIndex(COLUMN_DAY));
        String date_time = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME));
        String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
        String temperature = cursor.getString(cursor.getColumnIndex(COLUMN_TEMPERATURE));
        String pressure = cursor.getString(cursor.getColumnIndex(COLUMN_PRESSURE));
        String humidity = cursor.getString(cursor.getColumnIndex(COLUMN_HUMIDITY));
        String sun_set = cursor.getString(cursor.getColumnIndex(COLUMN_SUN_SET));
        String sun_rise = cursor.getString(cursor.getColumnIndex(COLUMN_SUN_RISE));
        String wind_speed = cursor.getString(cursor.getColumnIndex(COLUMN_WIND_SPEED));
        String wind_direction = cursor.getString(cursor.getColumnIndex(COLUMN_WIND_DIRECTION));
        return new Paket(date_time, day, location, temperature, pressure, humidity, sun_set, sun_rise, wind_speed, wind_direction);
    }

}
