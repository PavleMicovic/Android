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

    public Paket(String city) {
        final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
        final String API_KEY = "&APPID=b90fba716e00f3e7ec2b4a93e350a3e9&units=metric";
        final String URL = BASE_URL + city + API_KEY;

        try {
            JSONObject json = helper.getJSONObjectFromURL(URL);

            JSONObject main = json.getJSONObject("main");
            JSONObject wind = json.getJSONObject("wind");
            JSONObject sys = json.getJSONObject("sys");

            this.temp = main.getDouble("temp");
            this.humidity = main.getDouble("humidity");
            this.pressure = main.getDouble("pressure");
            this.wind_speed = wind.getDouble("speed");
            this.city = json.getString("name");
            this.date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            this.wind_direction = degreeToString(wind.getDouble("deg"));
            long sun = Long.valueOf(sys.get("sunrise").toString()) * 1000;
            Date date1 = new Date(sun);
            this.sunrise = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(date1);
            long night = Long.valueOf(sys.get("sunset").toString()) * 1000;
            Date date2 = new Date(night);
            this.sunset = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(date2);

        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }

    public Paket(String city, String date, String weekday, double temperature, double humidity, double pressure, String sunrise, String sunset, double windSpeed, String windDirection) {
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

    public String degreeToString(Double degree) {
        if (degree > 337.5)
            return "North";
        if (degree > 292.5)
            return "North West";
        if (degree > 247.5)
            return "West";
        if (degree > 202.5)
            return "South West";
        if (degree > 157.5)
            return "South";
        if (degree > 122.5)
            return "South East";
        if (degree > 67.5)
            return "East";
        if (degree > 22.5) {
            return "North East";
        } else
            return "North";
    }
}
