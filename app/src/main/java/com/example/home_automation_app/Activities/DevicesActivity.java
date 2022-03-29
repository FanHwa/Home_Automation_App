package com.example.home_automation_app.Activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.home_automation_app.DatabaseHelper.MyDBHelper;
import com.example.home_automation_app.Models.Device;
import com.example.home_automation_app.R;

import java.util.ArrayList;

public class DevicesActivity extends AppCompatActivity {
    // Widgets
    private Button ledOn, ledOff;

    private MyDBHelper dbHelper;
    private ArrayList<Device> deviceArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_devices);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Call the widgets
        ledOn = (Button) findViewById(R.id.tempOnBtn);
        ledOff = (Button) findViewById(R.id.tempOffBtn);

        dbHelper = new MyDBHelper(this,null,null,1);
        deviceArrayList = dbHelper.getAllDevices();


        ledOn.setOnClickListener(v -> {
//            HomeActivity.btHelper.sendMessage("LEDON");
            Toast.makeText(getApplicationContext(), deviceArrayList.get(0).getDeviceOnCmd(), Toast.LENGTH_SHORT).show();
        });

        ledOff.setText(deviceArrayList.get(0).getDeviceName());
        ledOff.setOnClickListener(v -> {
//            HomeActivity.btHelper.sendMessage("LEDOFF");
            Toast.makeText(getApplicationContext(), deviceArrayList.get(0).getDeviceOffCmd(), Toast.LENGTH_SHORT).show();
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_action_bar, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.add_device_btn) {
//            Toast.makeText(this, "Add Device", Toast.LENGTH_SHORT).show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
