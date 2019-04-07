package com.rza.homework.weatherproject.api.okhttp;

import com.google.gson.annotations.SerializedName;

public class ForecastRoot {

    @SerializedName("current")
    ForecastCurrent current;

    @SerializedName("forecast")
    ForecastDayRoot days;


}
