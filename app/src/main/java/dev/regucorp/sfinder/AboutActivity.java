package dev.regucorp.sfinder;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Guillaume ROUSSIN on 21/11/20
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_screen_layout);

        TextView link = findViewById(R.id.about_linkText);
        link.setMovementMethod(LinkMovementMethod.getInstance());

        Button backBtn = findViewById(R.id.about_backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this, MainActivity.class));
            }
        });
    }
}
