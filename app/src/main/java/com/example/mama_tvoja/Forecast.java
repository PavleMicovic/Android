package com.example.mama_tvoja;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ServiceConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Forecast extends AppCompatActivity implements View.OnClickListener{

    Button temperature, wind, sun;
    TextView temp2, pressure1, humidity1, unit, sunrise1, sunset1;
    TextView wind_speed1, wind_direction1, city, day, update;
    ImageView sunce;
    View temp1, sun_view, wind_view;
    Spinner temps;
    ImageButton refresh;
    private http_helper httpHelper;
    private ArrayAdapter<String> mListAdapter;
    public static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static String API_KEY="&APPID=b90fba716e00f3e7ec2b4a93e350a3e9&units=metric";
    public static String CITY;
    public String CURRENT_WEATHER;
    private CityDbHelper db;
    private Paket p;
    Calendar kalendar;
    String dan;
    ArrayAdapter<CharSequence> adapter;
    boolean mBound=false;
    private ExampleService mService;
    MyNDK ndk;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ExampleService.LocalBinder binder = (ExampleService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Log.d("START SERVICE", "START SERVICE");
        Intent intent = new Intent(this, ExampleService.class);
        intent.putExtra("location", city.getText().toString());
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBound == true)
            unbindService(connection);
        mBound = false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        kalendar=Calendar.getInstance();
        temperature=(Button)findViewById(R.id.temp);
        wind=(Button)findViewById(R.id.wind);
        sun=(Button)findViewById(R.id.sun);

        temp2=(TextView)findViewById(R.id.temp_text);
        pressure1=(TextView)findViewById(R.id.pressure_text);
        humidity1=(TextView)findViewById(R.id.humidity_text);
        unit=(TextView)findViewById(R.id.unit);
        sunrise1=(TextView)findViewById(R.id.sunrise);
        sunset1=(TextView)findViewById(R.id.sunset);
        wind_speed1=(TextView)findViewById(R.id.wind_speed);
        wind_direction1=(TextView)findViewById(R.id.wind_direction);

        city=(TextView)findViewById(R.id.city_write);
        Bundle bundle=getIntent().getExtras();
        city.setText(bundle.get("city_name").toString());
        CITY=bundle.get("city_name").toString();

        CURRENT_WEATHER=BASE_URL+CITY+API_KEY;
        Log.d("URL", "url: "+ CURRENT_WEATHER);

        day=(TextView)findViewById(R.id.day_write);
        SimpleDateFormat date= new SimpleDateFormat("dd MMM yyyy");
        day.setText(date.format(kalendar.getTime()));

        sunce=(ImageView)findViewById(R.id.sunce);

        temps=(Spinner)findViewById(R.id.spinner1);
        adapter=ArrayAdapter.createFromResource(this, R.array.temps, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temps.setAdapter(adapter);

        temp1=(View)findViewById(R.id.view_temp);
        sun_view=(View)findViewById(R.id.view_sun);
        wind_view=(View)findViewById(R.id.wind_view);

        temperature.setOnClickListener(this);
        wind.setOnClickListener(this);
        sun.setOnClickListener(this);

        httpHelper=new http_helper();
        db=new CityDbHelper(this);

        refresh=(ImageButton)findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
        update=(TextView)findViewById(R.id.last_updated);
        update.setVisibility(View.VISIBLE);
        ndk=new MyNDK();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        JSONObject jsonobject = httpHelper.getJSONObjectFromURL(CURRENT_WEATHER);
                        JSONObject mainobject = jsonobject.getJSONObject("main");
                        JSONObject sysobject = jsonobject.getJSONObject("sys");
                        JSONObject windobject = jsonobject.getJSONObject("wind");

                        final String wind_speed = windobject.get("speed").toString();
                        double degree = windobject.getDouble("deg");
                        final String wind_direction = degreeToString(degree);

                        long sun = Long.valueOf(sysobject.get("sunrise").toString()) * 1000;
                        Date date1 = new Date(sun);
                        final String sunrise = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(date1);

                        long night = Long.valueOf(sysobject.get("sunset").toString()) * 1000;
                        Date date2 = new Date(night);
                        final String sunset = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(date2);

                        final String temp = mainobject.get("temp").toString();
                        final String pressure = mainobject.get("pressure").toString();
                        final String humidity = mainobject.get("humidity").toString();


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                temp2.setText("Temperature: " + temp);
                                pressure1.setText("Pressure: " + pressure + " mbar");
                                humidity1.setText("Humidity: " + humidity + " %");
                                sunrise1.setText("Sunrise: " + sunrise);
                                sunset1.setText("Sunset: " + sunset);
                                wind_speed1.setText("Wind speed: " + wind_speed + " m/s");
                                wind_direction1.setText("Wind direction: " + wind_direction);

                                temps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String selectedItem = parent.getItemAtPosition(position).toString();
                                        if (selectedItem.equals("C")) {
                                            temp2.setText("Temperature: " + temp);
                                        } else if (selectedItem.equals("F")) {
                                            double tmp = Double.parseDouble(temp);
                                            tmp = ndk.calculate_temp(tmp, 0);
                                            String stemp = Double.toString(tmp);
                                            temp2.setText("Temperature: " + stemp);
                                        }
                                    }


                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        temp2.setText("Temperature: " + temp);
                                    }
                                });
                                String[] days = new String[] {"Ponedeljak", "Utorak", "Sreda", "Cetvrtak", "Petak", "Subota", "Nedelja"};
                                dan = days[kalendar.get(Calendar.DAY_OF_WEEK) - 2];
                                p=new Paket(day.getText().toString(), dan, city.getText().toString(), temp, pressure, humidity, sunset, sunrise, wind_speed, wind_direction);
                                db.insert_location(p);
                                Paket data_read = db.readData(city.getText().toString());
                                update.setText("last updated " + data_read.getDate_time());
                            }

                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
   // }


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

    @Override
    public void onClick(View v) {

        final Paket read=db.readData(city.getText().toString());
        switch(v.getId()){

            case R.id.refresh:
                update.setVisibility(View.INVISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonobject = httpHelper.getJSONObjectFromURL(CURRENT_WEATHER);
                            JSONObject mainobject = jsonobject.getJSONObject("main");
                            JSONObject sysobject = jsonobject.getJSONObject("sys");
                            JSONObject windobject = jsonobject.getJSONObject("wind");

                            final String wind_speed = windobject.get("speed").toString();
                            double degree = windobject.getDouble("deg");
                            final String wind_direction = degreeToString(degree);

                            long sun = Long.valueOf(sysobject.get("sunrise").toString()) * 1000;
                            Date date1 = new Date(sun);
                            final String sunrise = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(date1);

                            long night = Long.valueOf(sysobject.get("sunset").toString()) * 1000;
                            Date date2 = new Date(night);
                            final String sunset = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(date2);

                            final String temp = mainobject.get("temp").toString();
                            final String pressure = mainobject.get("pressure").toString();
                            final String humidity = mainobject.get("humidity").toString();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    temp2.setText("Temperature: " + temp);
                                    pressure1.setText("Pressure: " + pressure + " mbar");
                                    humidity1.setText("Humidity: " + humidity + " %");
                                    sunrise1.setText("Sunrise: " + sunrise);
                                    sunset1.setText("Sunset: " + sunset);
                                    wind_speed1.setText("Wind speed: " + wind_speed + " m/s");
                                    wind_direction1.setText("Wind direction: " + wind_direction);

                                    temps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            String selectedItem = parent.getItemAtPosition(position).toString();
                                            if (selectedItem.equals("C")) {
                                                temp2.setText("Temperature: " + temp);
                                            } else if (selectedItem.equals("F")) {
                                                double tmp = Double.parseDouble(temp);
                                                tmp = ndk.calculate_temp(tmp, 0);
                                                String stemp = Double.toString(tmp);
                                                temp2.setText("Temperature: " + stemp);
                                            }
                                        }


                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                            temp2.setText("Temperature: " + temp);
                                        }
                                    });
                                }

                            });
                            String[] days = new String[] {"Ponedeljak", "Utorak", "Sreda", "Cetvrtak", "Petak", "Subota", "Nedelja"};
                            dan = days[kalendar.get(Calendar.DAY_OF_WEEK) - 2];

                            p=new Paket(day.getText().toString(), dan, city.getText().toString(), temp, pressure, humidity, sunset, sunrise, wind_speed, wind_direction);
                            Paket[] read_day=db.read_data_location(city.getText().toString());
                            db.insert_location(p);
                            Paket data_read = db.readData(city.getText().toString());
                            update.setText("last updated " + data_read.getDate_time());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            case R.id.temp:
                temp2.setVisibility(View.VISIBLE);
                sunce.setVisibility(View.VISIBLE);
                pressure1.setVisibility(View.VISIBLE);
                humidity1.setVisibility(View.VISIBLE);
                temps.setVisibility(View.VISIBLE);
                unit.setVisibility(View.VISIBLE);
                temp1.setVisibility(View.VISIBLE);

                sun_view.setVisibility(View.GONE);
                sunrise1.setVisibility(View.GONE);
                sunset1.setVisibility(View.GONE);

                wind_view.setVisibility(View.GONE);
                wind_direction1.setVisibility(View.GONE);
                wind_speed1.setVisibility(View.GONE);

                temps.setSelection(0);

                temps.setAdapter(adapter);
                update.setText("last updated " + read.getDate_time());

                temps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        //------------------------------------------------------------------
                        String selected = parent.getItemAtPosition(position).toString();
                        if (selected.equals("C")) {
                            //Temperature.setText("Temperatura: " + read.getTemperature() + " °C\nPritisak: " + read.getPressure() + " hPA" + "\nVlažnost vazduha: " + read.getHumidity() + " %");
                            temp2.setText("Temperature: " + read.getTemperature() + " °C");
                            pressure1.setText("Pressure: " + read.getPressure()+ " mbar");
                            humidity1.setText("Humidity: " + read.getHumidity() + " %");
                        } else {
                            double tmp = Double.parseDouble(read.getTemperature());
                            tmp = ndk.calculate_temp(tmp, 0);
                            String temperature = Double.toString(tmp);
                            temp2.setText("Temperature: " + temperature + " °F");
                            pressure1.setText("Pressure: " + read.getPressure() + " mbar");
                            humidity1.setText("Humidity: " + read.getHumidity() + " %");

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        temp2.setText("Temperature: " + read.getTemperature() + " °C");
                        pressure1.setText("Pressure: " + read.getPressure()+ " mbar");
                        humidity1.setText("Humidity: " + read.getHumidity() + " %");
                    }
                });
                break;
            case R.id.sun:
                temp2.setVisibility(View.GONE);
                sunce.setVisibility(View.GONE);
                pressure1.setVisibility(View.GONE);
                humidity1.setVisibility(View.GONE);
                temps.setVisibility(View.GONE);
                unit.setVisibility(View.GONE);
                temp1.setVisibility(View.GONE);

                sun_view.setVisibility(View.VISIBLE);
                sunrise1.setVisibility(View.VISIBLE);
                sunset1.setVisibility(View.VISIBLE);

                wind_view.setVisibility(View.GONE);
                wind_direction1.setVisibility(View.GONE);
                wind_speed1.setVisibility(View.GONE);

                update.setText("last updated " + read.getDate_time());
                sunrise1.setText("Sunrise: "+read.getSun_rise());
                sunset1.setText("Sunset: "+read.getSun_set());
                break;
            case R.id.wind:
                temp2.setVisibility(View.GONE);
                sunce.setVisibility(View.GONE);
                pressure1.setVisibility(View.GONE);
                humidity1.setVisibility(View.GONE);
                temps.setVisibility(View.GONE);
                unit.setVisibility(View.GONE);
                temp1.setVisibility(View.GONE);

                sun_view.setVisibility(View.GONE);
                sunrise1.setVisibility(View.GONE);
                sunset1.setVisibility(View.GONE);

                wind_view.setVisibility(View.VISIBLE);
                wind_direction1.setVisibility(View.VISIBLE);
                wind_speed1.setVisibility(View.VISIBLE);

                update.setText("last updated " + read.getDate_time());
                wind_speed1.setText("Wind speed: "+read.getWind_speed());
                wind_direction1.setText("Wind direction: "+read.getWind_direction());
                break;
            default:
                break;
        }
    }
}
