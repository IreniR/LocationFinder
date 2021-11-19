package com.example.a2;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;

public class AddLocation extends AppCompatActivity {

    Button btn_findAddress;
    ImageButton btn_navigateBack, btn_saveChanges, btn_deleteLocation;

    EditText et_latitude, et_longitude;
    TextView view_address;

    private LocationAddress selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_location);

        btn_findAddress = findViewById(R.id.btn_find_location);
        btn_navigateBack = findViewById(R.id.btn_nav_back);
        btn_saveChanges = findViewById(R.id.btn_save_changes);
        btn_deleteLocation = findViewById(R.id.delete_btn);

        et_latitude = findViewById(R.id.et_latitude);
        et_longitude = findViewById(R.id.et_longitude);

        view_address = findViewById(R.id.show_address);

        //navigate user back to main activity
        btn_navigateBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddLocation.this, "Location Information Was Not Saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //save changes and navigate user back to main activity
        btn_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //save data to db
                saveLocationChange();

                //navigate back to main activity
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                finish();
            }
        });

        btn_findAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //find address given latitude and longitude
                if(TextUtils.isEmpty(et_latitude.getText().toString()) || TextUtils.isEmpty(et_longitude.getText().toString())){

                    //make sure fields are not empty, or else it cannot be saved
                    Toast.makeText(AddLocation.this, "Field(s) Cannot Be Empty", Toast.LENGTH_SHORT).show();
                }else{

                    //get address for given latitude and longitude values
                    calcLocation(et_latitude.getText().toString(), et_longitude.getText().toString());
                }
            }
        });

        //delete button made from invisible to visible
        btn_deleteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSetLocation(); //delete the location from database and navigate user back to main activity
                Toast.makeText(AddLocation.this, "Location Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //check if user is adding or updating/deleting a location
        checkForEditLocation();

    }

    //checking if the location was already created or not
    private void checkForEditLocation() {
        Intent previousIntent = getIntent();
        int passedLocationID = previousIntent.getIntExtra(LocationAddress.LOCATION_EDIT_EXTRA, -1);
        selectedLocation = LocationAddress.getIDForLocation(passedLocationID);

        if(selectedLocation != null){

            //setting text fields as expected
            et_latitude.setText(selectedLocation.getLatitude());
            et_longitude.setText(selectedLocation.getLongitude());
            view_address.setText(selectedLocation.getLocation_address());
            btn_deleteLocation.setVisibility(View.VISIBLE);
        }
    }

    //saving the location once changed
    private void saveLocationChange() {

        //if it is an already available location >> else if it doesn't exist
        SQLiteDatabase db = SQLiteDatabase.instanceOfDatabase(this);

        String locationSet = String.valueOf(view_address.getText());
        String latitudeSet = String.valueOf(et_latitude.getText());
        String longitudeSet = String.valueOf(et_longitude.getText());

        if(selectedLocation == null){
            int id = LocationAddress.locationArrayList.size(); //id = location size
            LocationAddress newLocationAddress = new LocationAddress(id, locationSet, latitudeSet, longitudeSet);
            LocationAddress.locationArrayList.add(newLocationAddress);

            //insert data into the database
            db.insertLocation(newLocationAddress);

        } else{
            selectedLocation.setLocation_address(view_address.getText().toString());
            selectedLocation.setLatitude(et_latitude.getText().toString());
            selectedLocation.setLongitude(et_longitude.getText().toString());

            //updating values in database accordingly
            db.updateLocation(selectedLocation);
        }

        finish();

    }

    //finding the location with geocoder
    public void calcLocation(String latLocation, String longLocation){

        //get latitude and longitude
        double latitude = Double.parseDouble(latLocation);
        double longitude = Double.parseDouble(longLocation);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            //finding address from values given
            List <Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            String address = addressList.get(0).getAddressLine(0);

            view_address.setText(address);

        }catch(Exception e){

        }
    }

    //function used within the delete btn
    //updates listview once deleted
    public void deleteSetLocation(){
        //LocationAddress.locationArrayList.clear();
        SQLiteDatabase db = SQLiteDatabase.instanceOfDatabase(this);
        db.deleteLocation(String.valueOf(selectedLocation.getId()));
        LocationAddress.locationArrayList.remove(selectedLocation.getId());
    }
}
