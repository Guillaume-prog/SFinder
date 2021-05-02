package dev.regucorp.sfinder.database;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Guillaume ROUSSIN on 30/10/20
 */
public class PositionRequest {

    // Static stuff

    public static final String TAG = "PositionRequest";

    public static final int GET_LOCATIONS_REQUEST = 0;
    public static final int ADD_LOCATION_REQUEST = 1;

    private static PositionRequest[] requests = {
            new PositionRequest("https://regucorp.tk/sfinder/getLocations.php", new String[] {"id"}),
            new PositionRequest("https://regucorp.tk/sfinder/addLocation.php", new String[] {"location", "text"})
    };

    // Other stuff

    private String link;
    private String[] keys;
    private Map<String, String> params;

    private PositionRequest(String link, String[] parameters) {
        this.link = link;
        this.keys = parameters;
        this.params = new HashMap<>();
    }

    public static PositionRequest newRequest(int id) {
        if(id >= 0 && id < requests.length) {
            return new PositionRequest(
                    requests[id].link,
                    requests[id].keys
            );
        }
        return null;
    }

    public void setParams(String[] values) {
        if(values.length != keys.length) {
            Log.d(TAG, "PositionRequest - Wrong number of arguments");
            return;
        }

        params = new HashMap<>();
        for(int i = 0; i < keys.length; i++) {
            params.put(keys[i], values[i]);
        }
    }

    public String getUrl() {
        return this.link;
    }
    public Map<String, String> getParams() {
        return this.params;
    }
}
