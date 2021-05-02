package dev.regucorp.sfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dev.regucorp.sfinder.database.Database;
import dev.regucorp.sfinder.database.LocationStorage;
import dev.regucorp.sfinder.database.PositionRequest;
import dev.regucorp.sfinder.map.MapHandler;
import dev.regucorp.sfinder.permissions.Permission;
import dev.regucorp.sfinder.position.PositionTracker;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    public static final String TAG = "MainActivity";

    private static final int LOCATION_REQUEST_CODE = 1212;

    private MapHandler map;
    private LatLng nantesLocation = new LatLng(47.218102, -1.552800);

    private Database db;
    private LocationStorage storage;
    private PositionTracker pos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_layout);

        if (Permission.requestPermissions(this, Permission.LOCATION_PERMISSIONS, LOCATION_REQUEST_CODE))
            init();
    }

    public void init() {
        // Button listeners
        ImageButton addLocationButton = findViewById(R.id.addLocationBtn);
        addLocationButton.setOnClickListener(v -> {
                if(!pos.isLocationEnabled())
                    Toast.makeText(MainActivity.this, getString(R.string.main_toastNoGPS), Toast.LENGTH_SHORT).show();
                else {
                    AddLocationDialog.showDialog(this, text -> {
                        postLocation(text);
                    });
                }
        });

        ImageButton aboutButton = findViewById(R.id.tutorialBtn);
        aboutButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        });

        // Database init
        db = Database.getInstance(this);
        storage = LocationStorage.getInstance();
        pos = PositionTracker.getInstance();

        // Map init
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Run Function
        Log.d(TAG, "onMapReady() called");

        // Create map object
        map = new MapHandler(googleMap, nantesLocation, 11.5f);
        map.setupMap(this);
        map.setMarkerListener(this);

        //Init markers
        try {
            JSONArray results = storage.getLocations();
            for(int i = 0; i < results.length(); i++)
                addMarkerFromLocation(results.getJSONObject(i));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ImageButton homeBtn = findViewById(R.id.centerHomeBtn);
        homeBtn.setOnClickListener(v -> {
            map.centerOnHome();
        });

        ImageButton locationBtn = findViewById(R.id.centerLocationBtn);
        locationBtn.setOnClickListener(v -> {
            if(!pos.isLocationEnabled()) {
                Toast.makeText(MainActivity.this, getString(R.string.main_toastNoGPS), Toast.LENGTH_SHORT).show();
                return;
            }
            pos.getLocation(location -> {
                if(location == null) return;
                map.centerOnLocation(map.toLatLng(location), 15f);
            });
        });
    }

    public void postLocation(String text) {
        pos.getLocation(location -> {
            String formattedPos = location.getLatitude() + ";" + location.getLongitude();

            Log.d(TAG, "add - " + formattedPos);
            Log.d(TAG, "add - " + text);

            final PositionRequest request = PositionRequest.newRequest(PositionRequest.ADD_LOCATION_REQUEST);
            request.setParams(new String[] {
                    formattedPos,
                    text
            });

            Toast addLocationSuccess = Toast.makeText(this, getString(R.string.add_toastLocationAdded), Toast.LENGTH_SHORT);
            db.sendRequest(request, data -> {
                Log.d(TAG, "Added request - " + data.toString());
                storage.addLocation(data);
                try {
                    addMarkerFromLocation(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                addLocationSuccess.show();
            });
        });
    }

    public void addMarkerFromLocation(JSONObject marker) throws JSONException {
        String[] pos = marker.getString("location").split(";");
        map.addMarker(
                new LatLng(Double.parseDouble(pos[0]), Double.parseDouble(pos[1])),
                marker.getString("text"),
                R.drawable.ic_sfinder_marker
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(Permission.accepted(grantResults) && requestCode == LOCATION_REQUEST_CODE)
            init();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        ViewLocationDialog.showDialog(this, marker.getTag().toString());

        return true;
    }
}