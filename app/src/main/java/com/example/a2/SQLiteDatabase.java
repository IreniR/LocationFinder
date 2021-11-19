package com.example.a2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDatabase extends SQLiteOpenHelper {

    private static SQLiteDatabase db;
    private static final String DATABASE_NAME = "location.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "location";

    private static final String ID_FIELD = "id";
    private static final String ADDRESS_FIELD = "address";
    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";

    LocationAdaptor locationAdaptor;

    public SQLiteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteDatabase instanceOfDatabase(Context context){

        //if database isn't created
        if(db == null){
            db = new SQLiteDatabase(context);
        }
        return db;
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase sqLiteDatabase) {

        //Creating the table with respective columns
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ADDRESS_FIELD)
                .append(" TEXT UNIQUE, ")
                .append(LATITUDE_FIELD)
                .append(" TEXT, ")
                .append(LONGITUDE_FIELD)
                .append(" TEXT )");

        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Inserting a location within the database with respective values obtained
    public void insertLocation(LocationAddress locationAddress){
        android.database.sqlite.SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

      //  cv.put(ID_FIELD, locationAddress.getId());
        cv.put(ADDRESS_FIELD, locationAddress.getLocation_address());
        cv.put(LATITUDE_FIELD, locationAddress.getLatitude());
        cv.put(LONGITUDE_FIELD, locationAddress.getLongitude());

        sqLiteDatabase.insert(TABLE_NAME, null, cv);
    }

    //populating db listview array with respective values
    public void populateListArray() {
        android.database.sqlite.SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            try{
                while(result.moveToNext()){

                    int id = result.getInt(0);
                    String address = result.getString(1);
                    String latitude = result.getString(2);
                    String longitude = result.getString(3);

                    //getting values as expected and adding to the database
                    LocationAddress locationAddress = new LocationAddress(id, address, latitude, longitude);
                    LocationAddress.locationArrayList.add(locationAddress);
                }
            } catch (Exception e){

            }
    }

    //Updating the location of the previous location
    public void updateLocation(LocationAddress locationAddress){
        android.database.sqlite.SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //putting values into the database accordingly
    //    cv.put(ID_FIELD, locationAddress.getId());
        cv.put(ADDRESS_FIELD, locationAddress.getLocation_address());
        cv.put(LATITUDE_FIELD, locationAddress.getLatitude());
        cv.put(LONGITUDE_FIELD, locationAddress.getLongitude());

        //updating values into the db
        sqLiteDatabase.update(TABLE_NAME, cv, ID_FIELD + " =? ", new String[]{String.valueOf(locationAddress.getId())});
    }

    //deleting location from db
    public void deleteLocation(String value){
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        db.delete("location","id=?", new String[]{value});
    }

    //search query to get specified location coordinates
    public Cursor getLocation(String address) {
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();

        //selecting distinct latitude and longitude values within the location table where the address field is like what had been inserted by the end-user
        String query = ("SELECT DISTINCT " + LATITUDE_FIELD + ", " + LONGITUDE_FIELD + " FROM "+ TABLE_NAME + " WHERE " + ADDRESS_FIELD + " LIKE '%" +address+"%'");
        Cursor cursor =db.rawQuery(query, null);
        try{
            while(cursor.moveToNext()){

                String latitude = cursor.getString(0);
                String longitude = cursor.getString(1);

                //presenting to the user latitude and longitude values
                LocationAddress locationAddress = new LocationAddress(latitude, longitude);
                LocationAddress.locationArrayList.add(locationAddress);
                locationAdaptor.notifyDataSetChanged();

            }
        } catch (Exception e){

        }

        return cursor;
    }
}



