package com.example.mama_tvoja;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Forecast extends AppCompatActivity implements View.OnClickListener {

    Button temperature, wind, sun;
    TextView temp, pressure, humidity, unit, sunrise, sunset, wind_speed, wind_direction;
    ImageView sunce;
    View temp1, sun_view, wind_view;
    Spinner temps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        temperature=(Button)findViewById(R.id.temp);
        wind=(Button)findViewById(R.id.wind);
        sun=(Button)findViewById(R.id.sun);
        temp=(TextView)findViewById(R.id.temp_text);
        pressure=(TextView)findViewById(R.id.pressure_text);
        humidity=(TextView)findViewById(R.id.humidity_text);
        unit=(TextView)findViewById(R.id.unit);
        sunrise=(TextView)findViewById(R.id.sunrise);
        sunset=(TextView)findViewById(R.id.sunset);
        wind_speed=(TextView)findViewById(R.id.wind_speed);
        wind_direction=(TextView)findViewById(R.id.wind_direction);
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

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.temp:
                temp.setVisibility(View.VISIBLE);
                sunce.setVisibility(View.VISIBLE);
                pressure.setVisibility(View.VISIBLE);
                humidity.setVisibility(View.VISIBLE);
                temps.setVisibility(View.VISIBLE);
                unit.setVisibility(View.VISIBLE);
                temp1.setVisibility(View.VISIBLE);

                sun_view.setVisibility(View.GONE);
                sunrise.setVisibility(View.GONE);
                sunset.setVisibility(View.GONE);

                wind_view.setVisibility(View.GONE);
                wind_direction.setVisibility(View.GONE);
                wind_speed.setVisibility(View.GONE);
                break;
            case R.id.sun:
                temp.setVisibility(View.GONE);
                sunce.setVisibility(View.GONE);
                pressure.setVisibility(View.GONE);
                humidity.setVisibility(View.GONE);
                temps.setVisibility(View.GONE);
                unit.setVisibility(View.GONE);
                temp1.setVisibility(View.GONE);

                sun_view.setVisibility(View.VISIBLE);
                sunrise.setVisibility(View.VISIBLE);
                sunset.setVisibility(View.VISIBLE);

                wind_view.setVisibility(View.GONE);
                wind_direction.setVisibility(View.GONE);
                wind_speed.setVisibility(View.GONE);
                break;
            case R.id.wind:
                temp.setVisibility(View.GONE);
                sunce.setVisibility(View.GONE);
                pressure.setVisibility(View.GONE);
                humidity.setVisibility(View.GONE);
                temps.setVisibility(View.GONE);
                unit.setVisibility(View.GONE);
                temp1.setVisibility(View.GONE);

                sun_view.setVisibility(View.GONE);
                sunrise.setVisibility(View.GONE);
                sunset.setVisibility(View.GONE);

                wind_view.setVisibility(View.VISIBLE);
                wind_direction.setVisibility(View.VISIBLE);
                wind_speed.setVisibility(View.VISIBLE);
                break;
        }
    }
}
