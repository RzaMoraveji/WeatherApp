package com.rza.homework.weatherproject.api.okhttp;

import com.google.gson.annotations.SerializedName;

public class SearchCityItem {
    @SerializedName("id")
     int id;
    @SerializedName("name")
     String name;
    @SerializedName("region")
     String region;
    @SerializedName("country")
     String country;
    @SerializedName("url")
     String url;
}
