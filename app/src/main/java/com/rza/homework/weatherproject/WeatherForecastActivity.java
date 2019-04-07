package com.rza.homework.weatherproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rza.homework.weatherproject.Model.City;
import com.rza.homework.weatherproject.Model.WeatherForecast;
import com.rza.homework.weatherproject.Model.WeatherModel;
import com.rza.homework.weatherproject.api.MockWeatherApi;
import com.rza.homework.weatherproject.api.WeatherApi;
import com.rza.homework.weatherproject.api.WeatherListener;
import com.rza.homework.weatherproject.api.okhttp.OkHttpWeatherApi;

import androidx.appcompat.app.AppCompatActivity;

public class WeatherForecastActivity extends AppCompatActivity {

    WeatherApi api = new OkHttpWeatherApi("e31ec19185f64b10bde165131190704");
    TextView todayTemp,todayCondition;
    TextView oneTemp,oneCondition;
    TextView twoTemp,twoCondition;
    TextView threeTemp,threeCondition;
    View inc0, inc1, inc2, inc3;
    private City city;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        city = Utils.Storage.getCity(this);
        if (city == null) {
            finish();
            startActivity(new Intent(this, SetLocationActivity.class));
            return;
        }
        setContentView(R.layout.layout_forecast_activity);
        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        ((TextView) findViewById(R.id.city_name)).setText(city.getName() + " - " + city.getCountry());
        findViews();
        api();
    }

    private void findViews() {
        inc0 = findViewById(R.id.today);
        todayTemp = inc0.findViewById(R.id.temp);
        todayCondition = inc0.findViewById(R.id.condition);

        inc1 = findViewById(R.id.one);
        oneTemp = inc1.findViewById(R.id.temp);
        oneCondition = inc1.findViewById(R.id.condition);

        inc2 = findViewById(R.id.two);
        twoTemp = inc2.findViewById(R.id.temp);
        twoCondition = inc2.findViewById(R.id.condition);

        inc3 = findViewById(R.id.three);
        threeTemp = inc3.findViewById(R.id.temp);
        threeCondition = inc3.findViewById(R.id.condition);
    }

    private void api() {
        api.getForecast(city.getName(), new WeatherListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onWeather(WeatherModel model) {
                if (isDestroyed())
                    return;
                todayTemp.setText(String.valueOf(model.getToday().getTemp()) + " C");
                todayCondition.setText(model.getToday().getState());
                WeatherForecast w = model.getLaterDays().get(0);
                oneTemp.setText(w.getTemp().toString());
                oneCondition.setText(w.getState());

                w = model.getLaterDays().get(1);
                twoTemp.setText(w.getTemp().toString());
                twoCondition.setText(w.getState());

                w = model.getLaterDays().get(2);
                threeTemp.setText(w.getTemp().toString());
                threeCondition.setText(w.getState());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("delete");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("delete")) {
            Utils.Storage.purgeCity(this);
            recreate();
        }
        return super.onOptionsItemSelected(item);
    }
}
