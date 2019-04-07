package com.rza.homework.weatherproject.api.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.rza.homework.weatherproject.Model.City;
import com.rza.homework.weatherproject.Model.WeatherForecast;
import com.rza.homework.weatherproject.Model.WeatherModel;
import com.rza.homework.weatherproject.api.SearchCityListener;
import com.rza.homework.weatherproject.api.WeatherApi;
import com.rza.homework.weatherproject.api.WeatherListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpWeatherApi implements WeatherApi {

    private static final OkHttpClient client = new OkHttpClient.Builder().build();

    private final String key;
    private final Handler handler;
    private final Gson gson;

    public OkHttpWeatherApi(String key) {
        this.key = key;
        this.gson = new Gson();
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void searchCity(String name, final SearchCityListener listener) {
        client.newCall(new Request.Builder().get()
                .url(
                        HttpUrl.parse("http://api.apixu.com/v1/search.json")
                                .newBuilder()
                                .addQueryParameter("key", key)
                                .addQueryParameter("q", name)
                                .build())
                .build())
                .enqueue(new Callback() {
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        try {
                            ResponseBody body = response.body();
                            String json = body.string();
                            SearchCityRoot root = gson.fromJson(json, SearchCityRoot.class);

                            final List<City> list = new ArrayList<>();
                            for (SearchCityItem searchCityItem : root) {
                                City city = new City(searchCityItem.name, searchCityItem.country);
                                list.add(city);
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onCitiesFound(list);
                                }
                            });
                            body.close();
                        } catch (Exception e) {
                            onFailure(call, new IOException(e));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onCitiesFound(new ArrayList<City>());
                            }
                        });
                    }

                });
    }

    @Override
    public void getForecast(String name, final WeatherListener listener) {
        client.newCall(new Request.Builder().get()
                .url(
                        HttpUrl.parse("http://api.apixu.com/v1/forecast.json")
                                .newBuilder()
                                .addQueryParameter("key", key)
                                .addQueryParameter("q", name)
                                .addQueryParameter("days",String.valueOf(4))
                                .build())
                .build()).enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)  {
                try {
                    ResponseBody body = response.body();
                    String json = body.string();
                    ForecastRoot root = gson.fromJson(json, ForecastRoot.class);

                    WeatherForecast today = new WeatherForecast(root.current.condition.condition,root.current.temp);

                    final List<WeatherForecast> list = new ArrayList<>();
                    for (int i = 1; i < root.days.forecastDayList.size(); i++) {
                        ForecastDayItem dayItem = root.days.forecastDayList.get(i);
                        list.add(new WeatherForecast(dayItem.day.condition.condition,dayItem.day.temp));
                    }

                    final WeatherModel model = new WeatherModel(today, list);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onWeather(model);
                        }
                    });

                    body.close();
                } catch (Exception e) {
                    onFailure(call, new IOException(e));
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });
    }

}
