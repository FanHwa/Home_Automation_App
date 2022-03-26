package com.example.home_automation_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DevicesActivity extends AppCompatActivity {
    // Widgets
    Button ledOn, ledOff;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_devices);

        // Call the widgets
        ledOn = (Button) findViewById(R.id.tempOnBtn);
        ledOff = (Button) findViewById(R.id.tempOffBtn);

        ledOn.setOnClickListener(v -> {
            HomeActivity.btHelper.sendMessage("LEDON");
        });

        ledOff.setOnClickListener(v -> {
            HomeActivity.btHelper.sendMessage("LEDOFF");
        });
    }
}
