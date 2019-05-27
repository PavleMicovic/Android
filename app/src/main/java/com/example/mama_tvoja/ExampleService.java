package com.example.mama_tvoja;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExampleService extends Service {

    private static final String LOG_TAG="ExampleService";
    private static final long PERIOD=5000L;
    private RunnableExample mRunnable;

    public static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static String API_KEY="&APPID=b90fba716e00f3e7ec2b4a93e350a3e9&units=metric";
    public String temp, location, info;
    private http_helper mHTTP;
    public String CURRENT_WEATHER;
    private CityDbHelper db;
    private Calendar kalendar;
    private String day, date;


    @Override
    public void onCreate() {
        super.onCreate();
        //mThread = new ThreadExample();

        mHTTP = new http_helper();
        db = new CityDbHelper(this);
        mRunnable = new RunnableExample();
        mRunnable.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mThread.exit();
        mRunnable.stop();
    }

    public class LocalBinder extends Binder {
        ExampleService getService() {
            return ExampleService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        IBinder binder = new LocalBinder();

        // TODO: Return the communication channel to the service.
        location = intent.getStringExtra("location");
        return binder;
    }


    private class RunnableExample implements Runnable {
        private Handler mHandler;
        private boolean mRun = false;

        public RunnableExample() {
            mHandler = new Handler(getMainLooper());
        }

        public void start() {
            mRun = true;
            mHandler.postDelayed(this, PERIOD);
        }

        public void stop() {
            Log.d("STOP SERVICE", "STOP SERVICE");
            mRun = false;
            mHandler.removeCallbacks(this);
        }

        @Override
        public void run() {
            if (!mRun) {
                return;
            }

            CURRENT_WEATHER = BASE_URL + location + API_KEY;


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonobject = mHTTP.getJSONObjectFromURL(CURRENT_WEATHER);
                        JSONObject mainobject = jsonobject.getJSONObject("main");

                        final String temp = mainobject.get("temp").toString();
                        final String pressure = mainobject.get("pressure").toString();
                        final String humidity = mainobject.get("humidity").toString();

                        JSONObject sysobject = jsonobject.getJSONObject("sys");

                        long sun = Long.valueOf(sysobject.get("sunrise").toString()) * 1000;
                        Date date1 = new Date(sun);
                        final String sunrise = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(date1);
                        long night = Long.valueOf(sysobject.get("sunset").toString()) * 1000;
                        Date date2 = new Date(night);
                        final String sunset = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(date2);

                        JSONObject windobject = jsonobject.getJSONObject("wind");

                        final String wind_speed = windobject.get("speed").toString();

                        double degree = windobject.getDouble("deg");
                        final String wind_direction = degreeToString(degree);

                        kalendar = Calendar.getInstance();
                        String[] days = new String[]{"Ponedeljak", "Utorak", "Sreda", "Cetvrtak", "Petak", "Subota", "Nedelja"};
                        day = days[kalendar.get(Calendar.DAY_OF_WEEK) - 2];

                        SimpleDateFormat data_1 = new SimpleDateFormat("dd MMM yyyy");
                        date = data_1.format(kalendar.getTime());

                        Paket data=new Paket(date, "Ponedeljak", location, temp, pressure, humidity, sunset, sunrise, wind_speed, wind_direction);
                        db.insert(data);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            
            Paket read = db.readData(location);
            //Toast.makeText(ExampleService.this, "Temperatura je azurirana, "+read.getTemperature(), Toast.LENGTH_SHORT).show();
            NotificationCompat.Builder b = new NotificationCompat.Builder(ExampleService.this);
            b.setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.download)
                    .setTicker("Vremenska prognoza")
                    .setContentTitle("Temperatura je azurirana, " + read.getTemperature())
                    .setContentText(" °C")
                    .setContentInfo("INFO");

            NotificationManager manager = (NotificationManager) ExampleService.this.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(1, b.build());

            Log.d(LOG_TAG, "Hello from Runnable");
            mHandler.postDelayed(this, PERIOD);

        }
    }

    public String degreeToString(Double degree) {
        if (degree>337.5)
            return "North";
        if (degree>292.5)
            return "North West";
        if(degree>247.5)
            return "West";
        if(degree>202.5)
            return "South West";
        if(degree>157.5)
            return "South";
        if(degree>122.5)
            return "South East";
        if(degree>67.5)
            return "East";
        if(degree>22.5){
            return "North East";
        } else
            return "North";
    }
}
