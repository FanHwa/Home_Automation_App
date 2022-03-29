package com.example.home_automation_app.Activities;

import android.app.Activity;
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

public class AllDeviceActivity extends AppCompatActivity implements DeviceCardAdapter.OnBtnClickListener, DeviceCardAdapter.OffBtnClickListener{

    private RecyclerView allDevicesRecyclerView;
    private DeviceCardAdapter deviceCardAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private MyDBHelper dbHelper;
    private ArrayList<Device> deviceArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_all_devices);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("All Devices");

        allDevicesRecyclerView = (RecyclerView) findViewById(R.id.all_device_recycle_view);

        dbHelper = new MyDBHelper(this,null,null,1);
        deviceArrayList = dbHelper.getAllDevices();

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

        deviceCardAdapter.setOnBtnClick(position -> {
            onBtnClick(position);
        });

        deviceCardAdapter.setOffBtnClick(position -> {
            offBtnClick(position);
        });
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
