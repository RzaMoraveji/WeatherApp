package com.rza.homework.weatherproject.Model;

public class WeatherForecast {
    String state;
    Float temp;

    public WeatherForecast(String state, Float temp) {
        this.state = state;
        this.temp = temp;
    }

    public String getState() {
        return state;
    }

    public Float getTemp() {
        return temp;
    }
}
