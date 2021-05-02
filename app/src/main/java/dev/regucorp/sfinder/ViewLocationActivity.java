package dev.regucorp.sfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import dev.regucorp.sfinder.database.LocationStorage;

/**
 * Created by Guillaume ROUSSIN on 15/10/20
 */
public class ViewLocationActivity extends AppCompatActivity {

    private LocationStorage storage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_screen_layout);

        storage = LocationStorage.getInstance();

        String id = getIntent().getExtras().get("markerID").toString();
        displayObject(id);

        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private void displayObject(String id) {
        try {
            JSONObject result = storage.getLocation(id);

            //ImageView image = findViewById(R.id.locationImage);
            TextView text = findViewById(R.id.locationText);

            text.setText(result.getString("text"));
            /*Glide.with(this).load(result.getString("image")).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    Toast.makeText(ViewLocationActivity.this, getString(R.string.info_noWifi), Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into(image);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
