package dev.regucorp.sfinder.database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Guillaume ROUSSIN on 06/11/20
 */
public class LocationStorage {

    private static LocationStorage instance;

    public static LocationStorage getInstance() {
        if(instance == null) instance = new LocationStorage();
        return instance;
    }

    /* Main class */
    private JSONArray locations;

    private LocationStorage() {
        locations = new JSONArray();
    }

    public void setLocations(JSONArray locations) {
        this.locations = locations;
    }

    public JSONArray getLocations() {
        return locations;
    }

    public JSONObject getLocation(String id) throws JSONException {
        for(int i = 0; i < locations.length(); i++) {
            JSONObject location = locations.getJSONObject(i);
            if (location.getString("id").equals(id))
                return location;
        }

        return null;
    }

    public void addLocation(JSONObject location) {
        locations.put(location);
    }
}
