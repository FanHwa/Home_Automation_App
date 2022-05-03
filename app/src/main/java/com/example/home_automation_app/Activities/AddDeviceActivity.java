package com.example.home_automation_app.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.home_automation_app.DatabaseHelper.MyDBHelper;
import com.example.home_automation_app.Models.Device;
import com.example.home_automation_app.R;
import com.google.android.material.textfield.TextInputLayout;

/**
 * This is an activity class to add new device
 */
public class AddDeviceActivity extends AppCompatActivity {

    private TextInputLayout nameLayout, locationLayout, onCmdLayout, offCmdLayout;
    private EditText deviceName, deviceLocation, deviceOnCmd, deviceOffCmd;
    private Button confirmBtn, cancelBtn;

    private MyDBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add_edit_device);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add Device");

        callWidgets();

        dbHelper = new MyDBHelper(this,null,null,1);

        confirmBtn.setOnClickListener(v -> {
            confirm();
        });

        cancelBtn.setOnClickListener(v -> {
            finish();
        });

    }

    // Call all the widgets
    private void callWidgets() {
        nameLayout = findViewById(R.id.input_layout_device_name);
        locationLayout = findViewById(R.id.input_layout_device_location);
        onCmdLayout = findViewById(R.id.input_layout_device_onCmd);
        offCmdLayout = findViewById(R.id.input_layout_device_offCmd);

        deviceName = findViewById(R.id.device_name_field);
        deviceLocation = findViewById(R.id.device_location_field);
        deviceOnCmd = findViewById(R.id.device_onCmd_field);
        deviceOffCmd = findViewById(R.id.device_offCmd_field);

        confirmBtn = findViewById(R.id.add_edit_device_confirm_btn);
        cancelBtn = findViewById(R.id.add_edit_device_cancel_btn);
    }

    /**
     * Function when conform button is pressed
     */
    private void confirm() {
        String name = deviceName.getText().toString();
        String location = deviceLocation.getText().toString();
        String onCmd = deviceOnCmd.getText().toString();
        String offCmd = deviceOffCmd.getText().toString();
        Device device = new Device();

        if(checkInputValid(name, location, onCmd, offCmd)){
            device.setDeviceName(name);
            device.setDeviceLocation(location);
            device.setDeviceOnCmd(onCmd);
            device.setDeviceOffCmd(offCmd);
            dbHelper.addDevice(device);

            startActivity(new Intent(this, HomeActivity.class));
            finish();
            Toast.makeText(getApplicationContext(), "Device Added", Toast.LENGTH_SHORT).show();
        }

    }

    // Function to check whether all the text input is filled
    private boolean checkInputValid(String name, String location, String onCmd, String offCmd){
        boolean valid = true;

        if(name.isEmpty()){
            valid = false;
            nameLayout.setError("This field cannot be empty");
        }

        if(location.isEmpty()){
            valid = false;
            locationLayout.setError("This field cannot be empty");
        }

        if(onCmd.isEmpty()) {
            valid = false;
            onCmdLayout.setError("Please enter switch on command");
        }

        if(offCmd.isEmpty()){
            valid = false;
            offCmdLayout.setError("Please enter switch off command");
        }

        return valid;
    }
}
