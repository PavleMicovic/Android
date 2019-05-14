package com.example.mama_tvoja;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Paket {
    private String city, date, sunrise, sunset, wind_direction;
    private double temp, humidity, pressure, wind_speed;

    http_helper helper = new http_helper();

    public Paket(String city, String date, double temperature, double humidity, double pressure, String sunrise, String sunset, double windSpeed, String windDirection) {
        this.city = city;
        this.date = date;
        this.temp = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.wind_speed = windSpeed;
        this.wind_direction = windDirection;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public double getTemperature() {
        return temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public double getWindSpeed() {
        return wind_speed;
    }

    public String getWindDirection() {
        return wind_direction;
    }

    public void setCity(String city) {
        this.city=city;
    }

    public void setDate(String date) {
        this.date=date;
    }

    public void setTemp(Double temp) {
        this.temp=temp;
    }

    public void setHum(Double humidity) { this.humidity=humidity; }

    public void setPressure(Double pressure) { this.pressure=pressure;}

    public void setSunrise(String sunrise) { this.sunrise=sunrise;}

    public void setSunset(String sunset) {
        this.sunset=sunset;
    }

    public void setWindSpeed(Double wind_speed){ this.wind_speed=wind_speed; }

    public void setWindDirection(String wind_direction) {
        this.wind_direction=wind_direction;
    }
}
