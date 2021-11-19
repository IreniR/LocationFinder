package com.example.a2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageButton add_location, search_location_btn;
    ListView lv_listOfLocations;
    EditText et_searchedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_location = findViewById(R.id.add_btn);
        lv_listOfLocations = findViewById(R.id.listOfLocations);

        et_searchedLocation = findViewById(R.id.search_location);
        search_location_btn = findViewById(R.id.search_btn);

        //add button navigates user to AddLocation page
        add_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddLocation.class));
            }
        });

        //displays search results to user as latitude and longitude values only (as expected)
        search_location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchForLocation();
            }
        });

        loadFromDBToMemory();
        getPrePopulatedCoordinates();
        setLocationAdaptor();
        setOnClickListener();
    }

    //searching for the locations specified attributes
    private void searchForLocation() {
        LocationAddress.locationArrayList.clear();
        SQLiteDatabase db = SQLiteDatabase.instanceOfDatabase(this);

        //sending searched value to check for in database file
        db.getLocation(et_searchedLocation.getText().toString());
        setLocationAdaptor();
    }

    //navigate to page to update/ delete location
    private void setOnClickListener() {
        lv_listOfLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                LocationAddress selectedLocation = (LocationAddress) lv_listOfLocations.getItemAtPosition(position);
                Intent editLocation = new Intent(getApplicationContext(), AddLocation.class);
                editLocation.putExtra(LocationAddress.LOCATION_EDIT_EXTRA, selectedLocation.getId());

                //go to specific list view item
                startActivity(editLocation);
            }
        });
    }

    //pre-populating the database
    public void getPrePopulatedCoordinates(){
        SQLiteDatabase db = SQLiteDatabase.instanceOfDatabase(this);
        db.getReadableDatabase();
        db.getWritableDatabase();

        //get location
        //store id, location, lat, long into db accordingly
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        for(double[] prepopulate_coordinates: this.prepopulate_coordinate){
            double latitudeVal = prepopulate_coordinates[0];
            double longitudeVal = prepopulate_coordinates[1];

            try{
                //getting the address from predefined list of latitude and longitude values
                List<Address> addressList = geocoder.getFromLocation(latitudeVal, longitudeVal, 1);
                String streetAddress = addressList.get(0).getAddressLine(0);

                int id = LocationAddress.locationArrayList.size();
                LocationAddress newLocationAddress = new LocationAddress(id, streetAddress, String.valueOf(latitudeVal), String.valueOf(longitudeVal));
                LocationAddress.locationArrayList.add(newLocationAddress);

                //inserting the values accordingly into the database
                db.insertLocation(newLocationAddress);

            }catch (Exception e){}
        }
    }

    //loading data from database
    private void loadFromDBToMemory() {
        LocationAddress.locationArrayList.clear();
        SQLiteDatabase db = SQLiteDatabase.instanceOfDatabase(this);
        db.populateListArray();
    }

    //listview is to look as expected
    public void setLocationAdaptor(){
        LocationAdaptor locationAdaptor = new LocationAdaptor(getApplicationContext(), LocationAddress.locationArrayList);
        lv_listOfLocations.setAdapter(locationAdaptor);
    }

    //50 latitude and longitude coordinates respectively
    double[][] prepopulate_coordinate = {
            {48.8584, 2.2945},
            {27.1751, 78.0421},
            {40.6892, -74.0445},
            {40.4319, 116.5704},
            {-33.8568, 151.2153},
            {41.8902, 12.4922},
            {-13.1631, -72.5450},
            {43.7230, 10.3966},
            {-22.9519, -43.2105},
            {13.4125, 103.8670},
            {51.5007, -0.1246},
            {37.8199, -122.4783},
            {51.1789, -1.8262},
            {37.9715, 23.7257},
            {41.4036, 2.1744},
            {40.7484, -73.9857},
            {29.9792, 31.1342},
            {41.9022, 12.4539},
            {55.7525, 37.6231},
            {35.3606, 138.7274},
            {52.5163, 13.3777},
            {25.1972, 55.2744},
            {41.9009, 12.4833},
            {-25.3444, 131.0369},
            {48.8606, 2.3376},
            {47.5576, 10.7498},
            {29.9753, 31.1376},
            {48.8738, 2.2950},
            {39.9169, 116.3907},
            {48.6361, -1.5115},
            {51.5014, -0.1419},
            {48.8049, 2.1204},
            {24.4128, 54.4750},
            {48.8530, 2.3499},
            {50.8450, 4.3500},
            {-17.9243, 25.8572},
            {43.6426, -79.3871},
            {25.1412, 55.1852},
            {41.0086, 28.9802},
            {-33.8523, 151.2108},
            {52.5072, 13.3835},
            {-3.0674, 37.3556},
            {38.8977, -77.0365},
            {20.6843, -88.5678},
            {38.8899, -77.0091},
            {43.8791, -103.4591},
            {51.5033, -0.1196},
            {47.5071, 19.0457},
            {55.7520, 37.6175},
            {45.5149, 25.3672}
    };

    @Override
    protected void onResume(){
        super.onResume();
        setLocationAdaptor();
    }
}