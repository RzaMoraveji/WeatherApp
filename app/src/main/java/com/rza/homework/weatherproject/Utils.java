package com.rza.homework.weatherproject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.rza.homework.weatherproject.Model.City;

import androidx.annotation.Nullable;

public class Utils {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null)
            return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static class Storage {

        public static void saveCity(Context context, City city) {
            SharedPreferences sp = context.getSharedPreferences("CITY_STORAGE", 0);
            sp.edit()
                    .putString("city_name", city.getName())
                    .putString("city_country", city.getCountry())
                    .apply();
        }

        @Nullable
        public static City getCity(Context context) {
            SharedPreferences sp = context.getSharedPreferences("CITY_STORAGE", 0);
            if (sp.contains("city_name") && sp.contains("city_country"))
                return new City(sp.getString("city_name", ""), sp.getString("city_country", ""));
            else return null;
        }

        public static void purgeCity(Context context) {
            SharedPreferences sp = context.getSharedPreferences("CITY_STORAGE", 0);
            sp.edit().remove("city_name").remove("city_country").apply();
        }
    }
}
