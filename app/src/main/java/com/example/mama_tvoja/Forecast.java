package com.example.mama_tvoja;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView wind_speed1, wind_direction1, city, day;
    ImageView sunce;
    View temp1, sun_view, wind_view;
    Spinner temps;
    private http_helper httpHelper;
    private ArrayAdapter<String> mListAdapter;
    public static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static String API_KEY="&APPID=b90fba716e00f3e7ec2b4a93e350a3e9&units=metric";
    public static String CITY;
    public String CURRENT_WEATHER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        Calendar kalendar=Calendar.getInstance();
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
        city.setText("City:"+bundle.get("city_name").toString());
        CITY=bundle.get("city_name").toString();

        CURRENT_WEATHER=BASE_URL+CITY+API_KEY;
        Log.d("URL", "url: "+ CURRENT_WEATHER);

        day=(TextView)findViewById(R.id.day_write);
        switch (kalendar.get(Calendar.DAY_OF_WEEK))
        {
            case 2:
                day.setText(R.string.mon);
                break;
            case 3:
                day.setText(R.string.tue);
                break;
            case 4:
                day.setText(R.string.wed);
                break;
            case 5:
                day.setText(R.string.thu);
                break;
            case 6:
                day.setText(R.string.fri);
                break;
            case 7:
                day.setText(R.string.sat);
                break;
            case 1:
                day.setText(R.string.sun);
                break;
        }

        sunce=(ImageView)findViewById(R.id.sunce);

        temps=(Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.temps, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temps.setAdapter(adapter);

        temp1=(View)findViewById(R.id.view_temp);
        sun_view=(View)findViewById(R.id.view_sun);
        wind_view=(View)findViewById(R.id.wind_view);

        temperature.setOnClickListener(this);
        wind.setOnClickListener(this);
        sun.setOnClickListener(this);

        httpHelper=new http_helper();
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
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

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonobject = httpHelper.getJSONObjectFromURL(CURRENT_WEATHER);
                            JSONObject mainobject = jsonobject.getJSONObject("main");

                            final String temp = mainobject.get("temp").toString();
                            final String pressure = mainobject.get("pressure").toString();
                            final String humidity = mainobject.get("humidity").toString();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    temp2.setText("Temperature: " + temp);
                                    pressure1.setText("Pressure: " + pressure + " mbar");
                                    humidity1.setText("Humidity: " + humidity + " %");

                                    temps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            String selectedItem = parent.getItemAtPosition(position).toString();
                                            if (selectedItem.equals("C")) {
                                                temp2.setText("Temperature: " + temp);
                                            } else if (selectedItem.equals("F")){
                                                double tmp = Double.parseDouble(temp);
                                                tmp = (int) (tmp * (9 / 5) + 32);
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
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
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

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonobject = httpHelper.getJSONObjectFromURL(CURRENT_WEATHER);
                            JSONObject sysobject = jsonobject.getJSONObject("sys");

                            long sun = Long.valueOf(sysobject.get("sunrise").toString()) * 1000;
                            Date date1 = new Date(sun);
                            final String sunrise = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(date1);

                            long night = Long.valueOf(sysobject.get("sunset").toString()) * 1000;
                            Date date2 = new Date(night);
                            final String sunset = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(date2);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    sunrise1.setText("Sunrise: " + sunrise);
                                    sunset1.setText("Sunset: " + sunset);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

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

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonobject = httpHelper.getJSONObjectFromURL(CURRENT_WEATHER);
                            JSONObject windobject = jsonobject.getJSONObject("wind");
                            final String wind_speed = windobject.get("speed").toString();
                            double degree = windobject.getDouble("deg");
                            final String wind_direction = degreeToString(degree);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    wind_speed1.setText("Wind speed: " + wind_speed + " m/s");
                                    wind_direction1.setText("Wind direction: " + wind_direction);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
            default:
                break;
        }
    }
}
