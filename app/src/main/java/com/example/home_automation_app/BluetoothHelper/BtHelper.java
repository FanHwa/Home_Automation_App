package com.example.home_automation_app.BluetoothHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.home_automation_app.Activities.BluetoothConnectActivity;
import com.example.home_automation_app.Activities.HomeActivity;

import java.io.IOException;
import java.util.UUID;

public class BtHelper {
    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothSocket bluetoothSocket = null;
    private BluetoothDevice bluetoothDevice = null;
    private boolean isBtConnected = false;

    private ProgressDialog progressDialog;

    private String deviceAddress = null;
    private UUID uuid = null;
    private Context context;

    public BtHelper(String deviceAddress, UUID uuid, Context context) {
        this.deviceAddress = deviceAddress;
        Log.d("BThelper", "Device address: " + deviceAddress);
        this.uuid = uuid;
        this.context = context;
    }

    public void Connect() {
        new ConnectBt().execute();
    }
    
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

    private class ConnectBt extends AsyncTask<Void, Void, Void> {
        private boolean connectSuccess = true;

        @Override
        protected void onPreExecute() {
//            progressDialog = ProgressDialog.show(get, "Connecting...", "Please wait!!!");
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
                Toast.makeText(context, "Connection Failed. Is it a SPP Bluetooth? Try Again.", Toast.LENGTH_SHORT).show();
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
