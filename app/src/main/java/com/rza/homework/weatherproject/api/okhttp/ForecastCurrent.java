package com.rza.homework.weatherproject.api.okhttp;

import com.google.gson.annotations.SerializedName;

public class ForecastCurrent {

    @SerializedName("temp_c")
    float temp;

    @SerializedName("condition")
    ForecastCondition condition;


}
