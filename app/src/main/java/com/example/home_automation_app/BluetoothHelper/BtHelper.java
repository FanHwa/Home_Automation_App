package com.example.home_automation_app.BluetoothHelper;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.home_automation_app.Activities.HomeActivity;

import java.io.IOException;
import java.util.UUID;

/**
 * This class is used to help the app to connect with the correct bluetooh devices
 */
public class BtHelper {
    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothSocket bluetoothSocket = null;
    private BluetoothDevice bluetoothDevice = null;
    private boolean isBtConnected = false;

    private ProgressDialog progressDialog;

    private String deviceAddress = null;
    private UUID uuid = null;
    private Context context;

    /**
     * Constructor for the BtHelper Class
     * @param deviceAddress Device MAC Address
     * @param uuid UUID of the devices
     */
    public BtHelper(String deviceAddress, UUID uuid, Context context) {
        this.deviceAddress = deviceAddress;
        Log.d("BThelper", "Device address: " + deviceAddress);
        this.uuid = uuid;
        this.context = context;
    }

    /**
     * Public method to call for connection in other class
     */
    public void Connect() {
        new ConnectBt().execute();
    }

    /**
     * Funtion to send the command to the HC-05
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        if(bluetoothSocket!=null) {
            try {
                bluetoothSocket.getOutputStream().write(message.getBytes());
            } catch (IOException e) {
                Toast.makeText(context.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                throw new IOException("Failed to write to device");
            }
        }
    }

    /**
     * Function to connect the devices
     */
    private class ConnectBt extends AsyncTask<Void, Void, Void> {
        private boolean connectSuccess = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();;
            progressDialog = ProgressDialog.show(context, "Connecting...", "Please wait!!!");
        }

        @Override
        protected Void doInBackground(Void... devices) {

            try {
                if(bluetoothSocket == null || !isBtConnected) {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddress);
                    bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    bluetoothSocket.connect();
                }
            } catch (IOException e) {
                connectSuccess = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // If connection not success mean that the device connected is not HC-O5 And it is not able to work on the app
            if(!connectSuccess) {
                Toast.makeText(context, "Connection Failed. Is it a SSP Bluetooth? Try Again.", Toast.LENGTH_SHORT).show();
//                ((Activity)context).finish();
            } else {
                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, HomeActivity.class);
                context.startActivity(i);
            }
            progressDialog.dismiss();
        }
    }
}
