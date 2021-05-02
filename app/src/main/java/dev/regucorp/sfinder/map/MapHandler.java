package dev.regucorp.sfinder.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import dev.regucorp.sfinder.R;

/**
 * Created by Guillaume ROUSSIN on 13/10/20
 */
public class MapHandler {

    private final GoogleMap googleMap;
    private final CameraUpdate homeCameraUpdate;
    private int numMarkers = 0;

    private Resources res;

    public MapHandler(GoogleMap googleMap, LatLng homeLocation, float defaultZoom) {
        this.googleMap = googleMap;
        this.homeCameraUpdate = CameraUpdateFactory.newLatLngZoom(homeLocation, defaultZoom);
    }

    public void setupMap(AppCompatActivity act) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        res = act.getResources();

        if (ActivityCompat.checkSelfPermission(act.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(act.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        centerOnHome();
    }

    public void setMarkerListener(GoogleMap.OnMarkerClickListener listener) {
        googleMap.setOnMarkerClickListener(listener);
    }

    public void centerOnHome() {
        googleMap.moveCamera(homeCameraUpdate);
    }

    public void centerOnLocation(LatLng pos, float zoom) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, zoom));
    }

    public LatLng toLatLng(Location l) {
        return new LatLng(l.getLatitude(), l.getLongitude());
    }

    public int getNumMarkers() {
        return numMarkers;
    }

    public Marker addMarker(LatLng pos, String id, int iconPath) {
        Bitmap b = BitmapFactory.decodeResource(res, R.drawable.sfinder_marker);
        Bitmap icon = Bitmap.createScaledBitmap(b, 80, 80, false);

        MarkerOptions markerOptions = new MarkerOptions()
                .position(pos)
                .icon(BitmapDescriptorFactory.fromBitmap(icon))
                .anchor(0.5f, 0.5f);
        Marker marker = googleMap.addMarker(markerOptions);
        marker.setTag(id);

        numMarkers++;

        return marker;
    }
}
