package dev.regucorp.sfinder;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dev.regucorp.sfinder.database.Database;
import dev.regucorp.sfinder.database.LocationStorage;
import dev.regucorp.sfinder.database.PositionRequest;
import dev.regucorp.sfinder.permissions.Permission;
import dev.regucorp.sfinder.position.PositionTracker;

/**
 * Created by Guillaume ROUSSIN on 06/11/20
 */
public class SplashScreen extends AppCompatActivity {

    public static final String TAG = "SplashScreen";
    private static final int LOCATION_CODE = 1234;

    private boolean locationReady = false, databaseReady = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);

        initLocation();
        initDatabase();
    }

    private void checkAppReady() {
        if(databaseReady && locationReady) {
            Log.d(TAG, "SPLASH SCREEN - App ready !");
            startActivity(new Intent(this, MainActivity.class));
        }
    }


    /* LOCATION INITIALISATION */
    private void initLocation() {
        if(!Permission.requestPermissions(this, Permission.LOCATION_PERMISSIONS, LOCATION_CODE))
            return;

        PositionTracker pos = PositionTracker.getInstance();
        pos.initLocationManager(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.d(TAG, "SPLASH SCREEN - Location ready");
                locationReady = true;
                checkAppReady();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == LOCATION_CODE && Permission.accepted(grantResults)) {
            Log.d(TAG, "SPLASH SCREEN - Location allowed");
            initLocation();
        } else {
            Toast.makeText(this, getString(R.string.splash_toastNoLocation), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "SPLASH SCREEN - Couldn't get location permissions");
        }
    }

    /* DATABASE INITIALISATION */
    private void initDatabase() {
        Database db = Database.getInstance(this);
        final LocationStorage storage = LocationStorage.getInstance();

        PositionRequest request = PositionRequest.newRequest(PositionRequest.GET_LOCATIONS_REQUEST);
        db.sendRequest(request, new Database.DatabaseRequestListener() {
            @Override
            public void onResult(JSONObject data) {
                try {
                    JSONArray results = data.getJSONArray("results");
                    storage.setLocations(results);

                    databaseReady = true;
                    checkAppReady();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
