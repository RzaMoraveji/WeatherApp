package com.rza.homework.weatherproject.api;

import com.rza.homework.weatherproject.Model.City;

import java.util.List;

public interface SearchCityListener {

    void onCitiesFound(List<City> cities);

}
