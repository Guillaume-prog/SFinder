package dev.regucorp.sfinder;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import dev.regucorp.sfinder.database.Database;
import dev.regucorp.sfinder.database.LocationStorage;
import dev.regucorp.sfinder.position.PositionTracker;

/**
 * Created by Guillaume ROUSSIN on 30/09/20
 */
public class AddLocationActivity extends AppCompatActivity {

    public static final String TAG = "AddLocationActivity";

    public static final int LOCATION_REQUEST_CODE = 7676;

    // Views
    private EditText inputText;
    private ImageView resultImage;
    private Button cancelBtn, addBtn;

    // Info to send
    private Database db;
    private LocationStorage storage;

    private Bitmap bitmap;
    private PositionTracker positionTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_screen_layout);

        /*initViews();

        resultImage.setImageURI((Uri) getIntent().getExtras().get("imageUri"));
        bitmap = ((BitmapDrawable) resultImage.getDrawable()).getBitmap();

        if(Permission.requestPermissions(this, Permission.LOCATION_PERMISSIONS, LOCATION_REQUEST_CODE))
            run();*/
    }

    /*private void run() {
        positionTracker = PositionTracker.getInstance();
        db = Database.getInstance(this);
        storage = LocationStorage.getInstance();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPostRequest();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private void initViews() {
        inputText = findViewById(R.id.textInput);
        resultImage = findViewById(R.id.imageResult);
        cancelBtn = findViewById(R.id.cancelBtn);
        addBtn = findViewById(R.id.addBtn);
    }

    public void sendPostRequest() {
        positionTracker.getLocation(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                String formattedPos = location.getLatitude() + ";" + location.getLongitude();

                Log.d(TAG, "add - " + ImageParser.encodeImage(bitmap));
                Log.d(TAG, "add - " + formattedPos);
                Log.d(TAG, "add - " + inputText.getText().toString());

                final PositionRequest request = PositionRequest.newRequest(PositionRequest.ADD_LOCATION_REQUEST);
                request.setParams(new String[] {
                        formattedPos,
                        ImageParser.encodeImage(bitmap),
                        inputText.getText().toString()
                });

                db.sendRequest(request, new Database.DatabaseRequestListener() {
                    @Override
                    public void onResult(JSONObject data) {
                        Log.d(TAG, "Added request - " + data.toString());
                        toastAddedLocation();
                        storage.addLocation(data);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
            }
        });
    }

    private void toastAddedLocation() {
        Toast.makeText(this, getString(R.string.add_toastLocationAdded), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(Permission.accepted(grantResults) && requestCode == LOCATION_REQUEST_CODE)
            run();
    }*/
}
