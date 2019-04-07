package com.rza.homework.weatherproject.Model;

import java.util.List;

public class WeatherModel {
    WeatherForecast today;
    List<WeatherForecast> laterDays;

    public WeatherModel(WeatherForecast today, List<WeatherForecast> laterDays) {
        this.today = today;
        this.laterDays = laterDays;
    }

    public WeatherForecast getToday() {
        return today;
    }

    public List<WeatherForecast> getLaterDays() {
        return laterDays;
    }
}
