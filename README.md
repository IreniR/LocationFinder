# Location Finder

**Location Finder** is a native Android application developed in **Java** that allows users to manage and store specific geographical locations. Users can input latitude and longitude coordinates to find a corresponding address, save locations locally, and later view, edit, or delete them from a persistent list. This app showcases core Android development skills, including user input handling, persistent data storage, and integration with location services.

---

## Features

- **Geocoding Functionality**  
  Converts latitude and longitude coordinates into human-readable addresses using Android’s built-in `Geocoder`.

- **Location Management**
  - **Add New Locations**: Input coordinates to find and save new addresses.
  - **View Saved Locations**: Display a list of previously stored locations.
  - **Edit Existing Locations**: Modify address or coordinates of saved entries.
  - **Delete Locations**: Remove locations from the local database.

- **Persistent Data Storage**  
  Uses SQLite to ensure locations are retained even after the app is closed.

- **User-Friendly Interface**  
  Simple input fields, buttons, and intuitive navigation between screens.

- **Seamless Navigation**  
  Switch between the list view and the add/edit location screen with ease.

---

## Technologies Used

- **Java** – Primary language for Android app development  
- **Android SDK** – Core platform for native Android development  
- **SQLite** – For local, persistent data storage  
- **Geocoder API** – Used to translate coordinates into street addresses  
- **Android UI Components** – Includes `EditText`, `Button`, `TextView`, `ImageButton`, `ListView` with a custom adapter  
- **Android Permissions** – Requests required location and internet permissions

---

## Project Structure Highlights

- `AndroidManifest.xml`  
  Defines app components, required permissions (`ACCESS_FINE_LOCATION`, `INTERNET`), and activities (`MainActivity`, `AddLocation`)

- `MainActivity.java`  
  Displays the list of saved locations and provides navigation to add/edit screens.

- `AddLocation.java`  
  Handles input, geocoding, and saving/updating/deleting location entries.

- `LocationAddress.java`  
  Model class representing a saved location (address, lat, long, ID). Also manages a static list of all entries.

- `LocationAdaptor.java`  
  Custom `ArrayAdapter` used to populate the `ListView` in `MainActivity` with `LocationAddress` objects.

- `SQLiteDatabase.java` *(assumed)*  
  Manages all database operations such as insert, update, delete, and retrieve location records.

- `res/layout/`  
  Contains XML layout files:
  - `activity_main.xml` – Main list view screen  
  - `add_location.xml` – Form for entering/editing location data  
  - `location_container.xml` – Layout for individual list items

---

## Permissions

The application requests the following permissions:

- `android.permission.ACCESS_FINE_LOCATION` – To access device location for geocoding
- `android.permission.INTERNET` – Required for network-based geocoding operations

---

## Getting Started

### Prerequisites

- Android Studio (latest stable version)
- Java SDK
- Android SDK
- A physical or virtual Android device with location services enabled
