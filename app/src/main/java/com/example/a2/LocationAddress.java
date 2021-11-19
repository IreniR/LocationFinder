package com.example.a2;

import java.util.ArrayList;

public class LocationAddress {

    public static final String LOCATION_EDIT_EXTRA = "editLocation";
    public static ArrayList<LocationAddress> locationArrayList = new ArrayList<>();

    private int id;
    private String location_address;
    private String latitude;
    private String longitude;

    public LocationAddress(int id, String location_address, String latitude, String longitude){

        this.id = id;
        this.location_address = location_address;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public LocationAddress(String latitude, String longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //checking if the user is adding a new location or updating a current location
    public static LocationAddress getIDForLocation(int passedLocationID) {

        for(LocationAddress locationAddress: locationArrayList){
            if(locationAddress.getId() == passedLocationID){
                return locationAddress;
            }
        }
        return null;
    }

    //generated getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation_address() {
        return location_address;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    //    @Override // necessary to print
    //    public String toString() {
    //        return getLocation_address();// + " " + getLatitude() + " " + getLongitude();
    //    }

}
