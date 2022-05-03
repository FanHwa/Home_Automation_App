package com.example.home_automation_app.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home_automation_app.BluetoothConnectActivity;
import com.example.home_automation_app.Components.Adapters.DeviceCardAdapter;
import com.example.home_automation_app.DatabaseHelper.MyDBHelper;
import com.example.home_automation_app.Models.Device;
import com.example.home_automation_app.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This is a activity class for the user to control the device in the app
 */
public class ControlDeviceActivity extends AppCompatActivity implements DeviceCardAdapter.OnItemClickListener, DeviceCardAdapter.OnBtnClickListener, DeviceCardAdapter.OffBtnClickListener{

    private RecyclerView allDevicesRecyclerView;
    private DeviceCardAdapter deviceCardAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private MyDBHelper dbHelper;
    private ArrayList<Device> deviceArrayList;

    private String location;

    public static String DEVICE_ID = "DEVICE_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        location = i.getStringExtra(HomeActivity.LOCATION);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_control_device);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(location);

        allDevicesRecyclerView = (RecyclerView) findViewById(R.id.all_device_recycle_view);

        dbHelper = new MyDBHelper(this,null,null,1);

        // If the location pass on is ALl devices then call data of all deviecs
        // else call data of the devices according the location
        if(location.equals("All Devices")) {
            deviceArrayList = dbHelper.getAllDevices();
        } else {
            deviceArrayList = dbHelper.getDevicesByLocation(location);
        }

        // If device list not null then set the recycle view for the devices
        if(deviceArrayList != null) {
            setDevicesViewAdapter();
        } else {
            Toast.makeText(getApplicationContext(), "No Device", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * function to set up the recycle view of all the devices
     */
    private void setDevicesViewAdapter() {
        allDevicesRecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        deviceCardAdapter = new DeviceCardAdapter(deviceArrayList);

        allDevicesRecyclerView.setLayoutManager(layoutManager);
        allDevicesRecyclerView.setAdapter(deviceCardAdapter);

        deviceCardAdapter.setOnItemClickListener(position -> {
            onItemClick(position);
        });

        deviceCardAdapter.setOnBtnClick(position -> {
            onBtnClick(position);
        });

        deviceCardAdapter.setOffBtnClick(position -> {
            offBtnClick(position);
        });
    }

    /**
     * Go to edit device activity when the device item card is click
     * @param position Position of the device in the list
     */
    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(ControlDeviceActivity.this, EditDeviceActivity.class);
        i.putExtra("DEVICE", deviceArrayList.get(position));
        i.putExtra("POSITION", position);
        startActivityForResult(i, 1);
    }

    // Refresh the device list when the edit editdevice activity end
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                if(location.equals("All Devices")){
                    deviceArrayList = dbHelper.getAllDevices();
                    if(deviceArrayList != null){
                        setDevicesViewAdapter();
                    } else {
                        backToHome();
                    }
                } else {
                    deviceArrayList = dbHelper.getDevicesByLocation(location);
                    if(deviceArrayList != null){
                        setDevicesViewAdapter();
                    }else{
                        backToHome();
                    }
                }
            }
        }
    }

    /**
     * Function to send out on command when the on button click
     * @param position position of the device in the array list
     */
    @Override
    public void onBtnClick(int position) {
        Device tempDevice = deviceArrayList.get(position);
        try {
            BluetoothConnectActivity.btHelper.sendMessage(tempDevice.getDeviceOnCmd());
        } catch(IOException e) {
            Intent i = new Intent(ControlDeviceActivity.this, BluetoothConnectActivity.class);
            Toast.makeText(getApplicationContext(), "Connection Lost Please Connect to The System Again", Toast.LENGTH_SHORT).show();
            setResult(-1, i);
            finish();
        }
        //Remove  Before Submit
        //Toast.makeText(getApplicationContext(), tempDevice.getDeviceOnCmd(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Function to send out off command when the off button click
     * @param position position of the device in the array list
     */
    @Override
    public void offBtnClick(int position) {
        Device tempDevice = deviceArrayList.get(position);
        try {
            BluetoothConnectActivity.btHelper.sendMessage(tempDevice.getDeviceOffCmd());

        } catch(IOException e) {
            Intent i = new Intent(ControlDeviceActivity.this, BluetoothConnectActivity.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Connection Lost", Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(getApplicationContext(), tempDevice.getDeviceOffCmd(), Toast.LENGTH_SHORT).show();
    }

    // Return to home activity function
    private void backToHome(){
        Intent i = new Intent(this, HomeActivity.class);
        setResult(2, i);
        finish();
    }


}
