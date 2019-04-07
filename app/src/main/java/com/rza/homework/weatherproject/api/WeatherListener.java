package com.rza.homework.weatherproject.api;

import com.rza.homework.weatherproject.Model.WeatherModel;

public interface WeatherListener {
    void onWeather(WeatherModel model);
}
