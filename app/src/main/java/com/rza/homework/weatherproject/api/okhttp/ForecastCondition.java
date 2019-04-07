package com.rza.homework.weatherproject.api.okhttp;

import com.google.gson.annotations.SerializedName;

public class ForecastCondition {

    @SerializedName("text")
    String condition;
    @SerializedName("icon")
    String icon;
    @SerializedName("code")
    int code;
}
