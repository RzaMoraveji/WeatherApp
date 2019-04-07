package com.rza.homework.weatherproject.api.okhttp;

import com.google.gson.annotations.SerializedName;

public class ForecastDayItem {

    @SerializedName("date")
    String date;
    @SerializedName("day")
    ForecastDay day;

}
