package com.rza.homework.weatherproject.api;

import com.rza.homework.weatherproject.Model.WeatherModel;

public interface WeatherApi {

    void searchCity(String name, SearchCityListener listener);

    void getForecast(String cityName, WeatherListener listener );

}
