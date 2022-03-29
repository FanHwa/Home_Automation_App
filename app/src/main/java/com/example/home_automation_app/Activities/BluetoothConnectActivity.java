package com.example.home_automation_app.Activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.home_automation_app.R;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothConnectActivity extends AppCompatActivity {

    // Widgets
    Button scanDeviceBtn;
    ListView devicesList;

    //Bluetooth
    private BluetoothAdapter bluetoothAdapter = null;
    private Set<BluetoothDevice> devices;
    public static String DEVICE_ADDRESS = "DEVICE_ADDRESS";

//    ActivityResultLauncher<Intent> activityResultLauncher=
//            registerForActivityResult(
//                    new ActivityResultContracts.StartActivityForResult(ActivityResult activityResult),
//                    new ActivityResultCallback<ActivityResult>() {
//                        @Override
//                        public void onActivityResult(ActivityResult result) {
//
//                        }
//                    }
//            );
    ActivityResultLauncher<Intent> activityResultLauncher =
        registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                activityResult -> {
                    int result = activityResult.getResultCode();

                    if (result == RESULT_OK) {
                        Toast.makeText(BluetoothConnectActivity.this, "Bluetooth Enabled", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(BluetoothConnectActivity.this, "Bluetooth is Not Enabled", Toast.LENGTH_LONG).show();
                    }
                }
        );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connect);

        // Calling widgets
        scanDeviceBtn = (Button) findViewById(R.id.scanDeviceButton);
        devicesList = (ListView) findViewById(R.id.devicesList);

        // Get bluetooth adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Found", Toast.LENGTH_SHORT).show();

            // Finish APK
            finish();
        }
        else if(!bluetoothAdapter.isEnabled()) {
            Intent turnOnBlt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(turnOnBlt,1);
            activityResultLauncher.launch(turnOnBlt);
        }

        scanDeviceBtn.setOnClickListener(v -> scanDevicesList());

    }

    private void scanDevicesList() {
        devices = bluetoothAdapter.getBondedDevices();
        ArrayList list = new ArrayList();

        if(devices.size()>0) {

            // Get devices name and MAC Address
            for(BluetoothDevice bt : devices) {
                list.add(bt.getName() + "\n" + bt.getAddress());
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Paired Devices Found\nPlease Check Bluetooth Connection", Toast.LENGTH_SHORT).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        devicesList.setAdapter(adapter);
        devicesList.setOnItemClickListener(devicesListClickListener);
    }

    private AdapterView.OnItemClickListener devicesListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Get the device last 17 char MAC Address
            String info = ((TextView) view).getText().toString();
            String address = info.substring(info.length() - 17);

            //Change to next activity
            Intent i = new Intent(BluetoothConnectActivity.this, HomeActivity.class);
            i.putExtra(DEVICE_ADDRESS, address);
            startActivity(i);
        }
    };

}
