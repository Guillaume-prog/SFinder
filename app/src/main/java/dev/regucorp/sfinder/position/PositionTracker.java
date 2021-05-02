package dev.regucorp.sfinder.position;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by Guillaume ROUSSIN on 12/08/20
 */
public class PositionTracker {

    // Static instance
    private static PositionTracker instance;

    public static final String TAG = "PositionTracker";

    private AppCompatActivity act;

    // Google API location manager
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationManager locationManager;

    private Location lastLocation;

    public static PositionTracker getInstance() {
        if (instance == null) instance = new PositionTracker();
        return instance;
    }

    public void initLocationManager(AppCompatActivity act, OnSuccessListener<Location> onReadyListener) {
        this.act = act;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(act);
        locationManager = (LocationManager) act.getSystemService(Context.LOCATION_SERVICE);

        getLocation(onReadyListener);
    }


    public void getLocation(final OnSuccessListener<Location> listener) {
        Log.d(TAG, "location" + (fusedLocationProviderClient == null));
        if (ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(act, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location == null)
                    listener.onSuccess(lastLocation);
                else {
                    Log.d(TAG, "Get location - got new location");
                    lastLocation = location;
                    listener.onSuccess(location);
                }
            }
        });
    }

    public boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
