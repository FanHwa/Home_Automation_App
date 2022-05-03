package com.example.home_automation_app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

import com.example.home_automation_app.BluetoothHelper.BtHelper;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * This activity class is used to choose the device to be connected when the app start
 */
public class BluetoothConnectActivity extends AppCompatActivity {

    // Widgets
    Button scanDeviceBtn;
    ListView devicesList;

    //Bluetooth
    private BluetoothAdapter bluetoothAdapter = null;
    private Set<BluetoothDevice> devices;
    public static String DEVICE_ADDRESS = "DEVICE_ADDRESS";

    public static BtHelper btHelper;

    //UUID of the HC-05 module
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

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

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Calling widgets
        scanDeviceBtn = (Button) findViewById(R.id.scanDeviceButton);
        devicesList = (ListView) findViewById(R.id.devicesList);

        // Get bluetooth adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Found", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(!bluetoothAdapter.isEnabled()) {
            Intent turnOnBlt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activityResultLauncher.launch(turnOnBlt);
        }

        scanDeviceBtn.setOnClickListener(v -> scanDevicesList());

    }

    /**
     * Function to get the devices that had been paired with the phone
     */
    private void scanDevicesList() {
        devices = bluetoothAdapter.getBondedDevices();
        ArrayList list = new ArrayList();

        // Get Paired Device List
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

            btHelper = new BtHelper(address, myUUID, BluetoothConnectActivity.this);
            btHelper.Connect();

        }
    };

}
