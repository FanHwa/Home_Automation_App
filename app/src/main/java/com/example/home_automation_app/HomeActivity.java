package com.example.home_automation_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.home_automation_app.BluetoothHelper.BtHelper;

import java.io.IOException;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

    // Widgets
    TextView deviceDetail;
    Button tempBtn;
    private ProgressDialog progressDialog;

    // Bluetooth
    String deviceAddress = null;
    public static BtHelper btHelper;

    //SPP UUID
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        deviceAddress = intent.getStringExtra(BluetoothConnectActivity.DEVICE_ADDRESS);

        setContentView(R.layout.activity_home);

        // Call widgets
        deviceDetail = (TextView) findViewById(R.id.tempName);
        tempBtn = (Button)findViewById(R.id.tempBtn);

        tempBtn.setOnClickListener(v -> {
            Intent i  = new Intent(HomeActivity.this, DevicesActivity.class);
            startActivity(i);
        });

        btHelper = new BtHelper(deviceAddress, myUUID, HomeActivity.this);
        btHelper.Connect();

        deviceDetail.setText(deviceAddress);

    }


}
