package com.example.home_automation_app.Activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home_automation_app.BluetoothHelper.BtHelper;
import com.example.home_automation_app.Components.Adapters.RoomCardAdapter;
import com.example.home_automation_app.Components.Dialogs.AddDeviceDialog;
import com.example.home_automation_app.DatabaseHelper.MyDBHelper;
import com.example.home_automation_app.Models.Device;
import com.example.home_automation_app.R;

import java.util.ArrayList;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity implements RoomCardAdapter.OnItemClickListener, AddDeviceDialog.AddDeviceListener {

    // Widgets
    private Button addDeviceBtn, allDeviceBtn;
    private PopupWindow addDevicePopUp;
    private RelativeLayout mRelativeLayout;
    private RecyclerView roomRecyclerView;

    private RoomCardAdapter roomCardAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // Bluetooth
    String deviceAddress = null;
    public static BtHelper btHelper;

    //SPP UUID
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // Database
    public MyDBHelper dbHelper;
    private ArrayList<Device> deviceArrayList;
    private ArrayList<String> roomArrayList;

    //Static String
    public static String LOCATION = "LOCATION";
    public static String EDIT_CONFIRM = "EDIT_CONFIRM";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();


//        deviceAddress = intent.getStringExtra(BluetoothConnectActivity.DEVICE_ADDRESS);

//        btHelper = new BtHelper(deviceAddress, myUUID, HomeActivity.this);
//        btHelper.Connect();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_home);

        callWidgets();

        dbHelper = new MyDBHelper(this,null,null,1);
        deviceArrayList = dbHelper.getAllDevices();
        roomArrayList = dbHelper.getRoomList();

        addDeviceBtn.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, AddDeviceActivity.class);
            startActivityForResult(i, 2);
        });

        allDeviceBtn.setOnClickListener(v -> {
            viewAllDevice();
        });


        if(roomArrayList != null) {
            setRoomsViewAdapter();
        }

    }


    private void callWidgets() {
        addDeviceBtn = (Button)findViewById(R.id.add_device_btn);
        allDeviceBtn = (Button)findViewById(R.id.show_all_devices_btn);
        mRelativeLayout = new RelativeLayout(this);
        roomRecyclerView = findViewById(R.id.room_recycle_view);
    }

    private void viewAllDevice() {
        Intent i = new Intent(HomeActivity.this, ControlDeviceActivity.class);
        i.putExtra(LOCATION, "All Devices");
        startActivityForResult(i, 2);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == 2) {
                deviceArrayList = dbHelper.getAllDevices();
                roomArrayList = dbHelper.getRoomList();
                if (roomArrayList != null) {
                    setRoomsViewAdapter();
                }
            }
            if(resultCode == -1) {
                finish();
            }
        }
    }

    public void setRoomsViewAdapter() {
        roomRecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        roomCardAdapter = new RoomCardAdapter(roomArrayList, dbHelper);

        if(roomCardAdapter.getItemCount() > 0) {
            roomRecyclerView.setLayoutManager(layoutManager);
            roomRecyclerView.setAdapter(roomCardAdapter);

            roomCardAdapter.setOnItemClickListener(position -> {
                onItemClick(position);
            });
        }

    }


    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(HomeActivity.this, ControlDeviceActivity.class);

        // Filter Device
        i.putExtra(LOCATION, roomArrayList.get(position));
        startActivityForResult(i, 2);

    }

    public void onAddDeviceConfirm(String name, String type, String location, String onCmd, String offCmd) {
        Toast.makeText(HomeActivity.this, "Confirm Added", Toast.LENGTH_SHORT).show();
    }

}
