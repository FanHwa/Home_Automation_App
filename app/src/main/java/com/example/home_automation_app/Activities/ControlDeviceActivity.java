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

import com.example.home_automation_app.Components.Adapters.DeviceCardAdapter;
import com.example.home_automation_app.DatabaseHelper.MyDBHelper;
import com.example.home_automation_app.Models.Device;
import com.example.home_automation_app.R;

import java.util.ArrayList;

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
        if(location.equals("All Devices")) {
            deviceArrayList = dbHelper.getAllDevices();
        } else {
            deviceArrayList = dbHelper.getDevicesByLocation(location);
        }

        if(deviceArrayList != null) {
            setDevicesViewAdapter();
        } else {
            Toast.makeText(getApplicationContext(), "No Device", Toast.LENGTH_SHORT).show();
        }

    }


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

    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(ControlDeviceActivity.this, EditDeviceActivity.class);
        i.putExtra(DEVICE_ID, deviceArrayList.get(position).getDeviceId());
        i.putExtra(HomeActivity.LOCATION, location);
        startActivity(i);
    }

    @Override
    public void onBtnClick(int position) {
        Device tempDevice = deviceArrayList.get(position);
        Toast.makeText(getApplicationContext(), tempDevice.getDeviceOnCmd(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void offBtnClick(int position) {
        Device tempDevice = deviceArrayList.get(position);
        Toast.makeText(getApplicationContext(), tempDevice.getDeviceOffCmd(), Toast.LENGTH_SHORT).show();
    }

}
