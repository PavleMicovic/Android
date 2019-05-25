package com.example.mama_tvoja;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Paket {
    private String date_time;
    private String city;
    private String temperature;
    private String pressure;
    private String humidity;
    private String sun_set;
    private String sun_rise;
    private String wind_speed;
    private String wind_direction;
    private String day;

    public Paket(String date_time, String day,  String location, String temperature, String pressure, String humidity, String sun_set, String sun_rise, String wind_speed, String wind_direction) {
        this.date_time = date_time;
        this.city = location;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.sun_set = sun_set;
        this.sun_rise = sun_rise;
        this.wind_speed = wind_speed;
        this.wind_direction = wind_direction;
        this.day=day;
    }

    public String getDate_time() {
        return date_time;
    }

    public String getLocation() {
        return city;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getSun_set() {
        return sun_set;
    }

    public String getSun_rise() {
        return sun_rise;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public String getDay(){return day;}

    public void setDay(String day){this.day=day;}

    public void setCity(String city) {
        this.city=city;
    }

    public void setDate(String date) {
        this.date_time=date;
    }

    public void setTemp(String temp) {
        this.temperature=temp;
    }

    public void setHum(String humidity) { this.humidity=humidity; }

    public void setPressure(String pressure) { this.pressure=pressure;}

    public void setSunrise(String sunrise) { this.sun_rise=sunrise;}

    public void setSunset(String sunset) {
        this.sun_set=sunset;
    }

    public void setWindSpeed(String wind_speed){ this.wind_speed=wind_speed; }

    public void setWindDirection(String wind_direction) {
        this.wind_direction=wind_direction;
    }
}
