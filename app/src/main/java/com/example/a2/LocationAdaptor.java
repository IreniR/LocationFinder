package com.example.a2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class LocationAdaptor extends ArrayAdapter<LocationAddress> {

    public LocationAdaptor(Context context, List<LocationAddress> locationsList){
        super(context, 0, locationsList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LocationAddress locationAddress = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.location_container, parent, false);
        }

        //setting text views as accordingly to show data pleasantly for each location
        TextView location_title = convertView.findViewById(R.id.title_location);
        TextView location_lat = convertView.findViewById(R.id.lat_value);
        TextView location_long = convertView.findViewById(R.id.long_value);

        location_title.setText(locationAddress.getLocation_address());
        location_lat.setText(locationAddress.getLatitude());
        location_long.setText(locationAddress.getLongitude());

        return convertView;
    }
}
