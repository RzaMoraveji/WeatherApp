package com.rza.homework.weatherproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.rza.homework.weatherproject.Model.City;
import com.rza.homework.weatherproject.api.okhttp.OkHttpWeatherApi;
import com.rza.homework.weatherproject.api.SearchCityListener;
import com.rza.homework.weatherproject.api.WeatherApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SetLocationActivity extends AppCompatActivity {

    WeatherApi api = new OkHttpWeatherApi("e31ec19185f64b10bde165131190704");

    EditText cityName;
    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utils.Storage.getCity(this) != null) {
            finish();
            startActivity(new Intent(this, WeatherForecastActivity.class));
            return;
        }
        setContentView(R.layout.layout_set_location);
        cityName = findViewById(R.id.city_name);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new Adapter());
        cityName.setOnEditorActionListener(new ActionListener());
    }


    /// keyboard search button listener

    private class ActionListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                adapter.set(Collections.<City>emptyList());
                Utils.hideKeyboard(SetLocationActivity.this);
                api.searchCity(v.getText().toString(), new SearchListener());
                return true;
            }
            return false;
        }
    }

    /// classes for showing and handling list

    private class Adapter extends RecyclerView.Adapter<VH> {

        private List<City> cities;

        Adapter() {
            cities = new ArrayList<>();
        }

        void set(List<City> cities) {
            this.cities.clear();
            this.cities.addAll(cities);
            this.notifyDataSetChanged();
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_city, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            holder.bind(cities.get(position));
        }

        @Override
        public int getItemCount() {
            return cities.size();
        }
    }

    private class VH extends RecyclerView.ViewHolder {

        private TextView cityName;
        private TextView countryName;

        VH(@NonNull View itemView) {
            super(itemView);
            this.cityName = itemView.findViewById(R.id.city_name);
            this.countryName = itemView.findViewById(R.id.country_name);
        }

        void bind(final City city) {
            this.cityName.setText(city.getName());
            this.countryName.setText(city.getCountry());
            itemView.setOnClickListener(new ItemClick(SetLocationActivity.this, city));
        }
    }

    private class ItemClick implements View.OnClickListener {

        private final Activity activity;
        private final City city;

        ItemClick(Activity activity, City city) {
            this.activity = activity;
            this.city = city;
        }

        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(v.getContext())
                    .setMessage("Are you sure you are in '" + city.getName() + "'? ")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Utils.Storage.saveCity(activity, city);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if (Utils.Storage.getCity(activity) == null)
                                return;
                            activity.finish();
                            activity.startActivity(new Intent(activity, WeatherForecastActivity.class));
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            if (Utils.Storage.getCity(activity) == null)
                                return;
                            activity.finish();
                            activity.startActivity(new Intent(activity, WeatherForecastActivity.class));
                        }
                    }).create()
                    .show();
        }
    }

    /// api listener

    private class SearchListener implements SearchCityListener {

        @Override
        public void onCitiesFound(List<City> cities) {
            adapter.set(cities);
        }
    }
}
