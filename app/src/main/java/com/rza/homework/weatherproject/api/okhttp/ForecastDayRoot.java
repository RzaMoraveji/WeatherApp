package com.rza.homework.weatherproject.api.okhttp;

import com.google.gson.annotations.SerializedName;

public class ForecastDayRoot {

    @SerializedName("forecastday")
    ForecastDayList forecastDayList;
}
