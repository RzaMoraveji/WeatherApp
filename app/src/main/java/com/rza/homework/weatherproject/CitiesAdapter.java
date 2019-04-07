package com.rza.homework.weatherproject;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CitiesAdapter extends ArrayAdapter<String> {

    List<String> data = new ArrayList<>();
    public CitiesAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_dropdown_item_1line);
    }

    public void setCities(List<String> data){
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
        notifyDataSetInvalidated();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
