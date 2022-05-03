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
import com.example.home_automation_app.DatabaseHelper.MyDBHelper;
import com.example.home_automation_app.Models.Device;
import com.example.home_automation_app.R;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This is activity class for home activity
 */
public class HomeActivity extends AppCompatActivity implements RoomCardAdapter.OnItemClickListener {

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

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_home);

        callWidgets();

        dbHelper = new MyDBHelper(this,null,null,1);
        deviceArrayList = dbHelper.getAllDevices();
        roomArrayList = dbHelper.getRoomList();

        // Set Add device Button Listener
        addDeviceBtn.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, AddDeviceActivity.class);
            startActivityForResult(i, 2);
        });

        // Set All device button listener
        allDeviceBtn.setOnClickListener(v -> {
            viewAllDevice();
        });

        // If the room array list is not null then set up the room list recycler view
        if(roomArrayList != null) {
            setRoomsViewAdapter();
        }

    }

    // Call all the widgets
    private void callWidgets() {
        addDeviceBtn = (Button)findViewById(R.id.add_device_btn);
        allDeviceBtn = (Button)findViewById(R.id.show_all_devices_btn);
        mRelativeLayout = new RelativeLayout(this);
        roomRecyclerView = findViewById(R.id.room_recycle_view);
    }

    /**
     * When All devices button click
     */
    private void viewAllDevice() {
        Intent i = new Intent(HomeActivity.this, ControlDeviceActivity.class);
        i.putExtra(LOCATION, "All Devices");
        startActivityForResult(i, 2);
    }


    @SuppressLint("NotifyDataSetChanged")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // Refresh the room and device list when the reqest code is 2
            if (resultCode == 2) {
                deviceArrayList = dbHelper.getAllDevices();
                roomArrayList = dbHelper.getRoomList();
                if (roomArrayList != null) {
                    setRoomsViewAdapter();
                }
            }
            // Close the activity if the result code is -1
            if(resultCode == -1) {
                finish();
            }
        }
    }

    /**
     * Set up the recycle view for room list
     */
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


    /**
     * Set action when the room item card click
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        // Start ControlDeviceActivity and pass the location name to the activity
        Intent i = new Intent(HomeActivity.this, ControlDeviceActivity.class);

        // Filter Device
        i.putExtra(LOCATION, roomArrayList.get(position));
        startActivityForResult(i, 2);

    }
}
