package dev.regucorp.sfinder.database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import dev.regucorp.sfinder.R;

/**
 * Created by Guillaume ROUSSIN on 14/09/20
 */

public class Database {

    public interface DatabaseRequestListener {
        void onResult(JSONObject data);
    }

    public static final String TAG = "Database";

    private static Database instance;

    private Context context;
    private RequestQueue queue;

    private Database(AppCompatActivity act) {
        context = act.getApplicationContext();
        queue = Volley.newRequestQueue(context);
    }

    public static synchronized Database getInstance(AppCompatActivity act) {
        if(instance == null) instance = new Database(act);
        return instance;
    }

    public void sendRequest(final PositionRequest request, final DatabaseRequestListener listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, request.getUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listener.onResult(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "pushGetRequest - " + error.toString());
                Toast.makeText(context, context.getString(R.string.splash_toastNoWifi), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return request.getParams();
            }
        };

        queue.add(stringRequest);
    }

}
