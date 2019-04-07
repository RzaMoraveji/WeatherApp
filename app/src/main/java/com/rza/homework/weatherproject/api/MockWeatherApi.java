package com.rza.homework.weatherproject.api;

import com.rza.homework.weatherproject.Model.City;
import com.rza.homework.weatherproject.Model.WeatherForecast;
import com.rza.homework.weatherproject.Model.WeatherModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockWeatherApi implements WeatherApi {
    @Override
    public void searchCity(String name, SearchCityListener listener) {
        listener.onCitiesFound(Arrays.asList(
                new City(name + "1", "Iran"),
                new City(name + "2", "Australia"),
                new City(name + "3", "Duetchland"),
                new City(name + "4", "Netherland"),
                new City(name + "5", "Spain"),
                new City(name + "6", "Russia")));
    }
    @Override
    public void getForecast(String cityName, WeatherListener listener) {
        WeatherForecast today = new WeatherForecast("Rainy",25f);
        List<WeatherForecast> laterDays = new ArrayList<>();
        laterDays.add(new WeatherForecast("Sunny",26f));
        laterDays.add(new WeatherForecast("Cloudy with a chance of meatball", 2f));
        laterDays.add(new WeatherForecast("Rainy",20f));
        WeatherModel model = new WeatherModel(today, laterDays);
        listener.onWeather(model);
    }

}
