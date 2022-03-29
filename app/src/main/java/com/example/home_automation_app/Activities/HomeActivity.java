package com.example.home_automation_app.Activities;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home_automation_app.BluetoothHelper.BtHelper;
import com.example.home_automation_app.Components.Dialogs.AddDeviceDialog;
import com.example.home_automation_app.DatabaseHelper.MyDBHelper;
import com.example.home_automation_app.R;

import java.util.ArrayList;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity implements AddDeviceDialog.AddDeviceListener {

    // Widgets
    private Button addDeviceBtn, allDeviceBtn;
    private PopupWindow addDevicePopUp;
    private RelativeLayout mRelativeLayout;
    private RecyclerView roomRecyclerView;
    private ListView tempRoomView;

    // Bluetooth
    String deviceAddress = null;
    public static BtHelper btHelper;

    //SPP UUID
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // Database
    private MyDBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent intent = getIntent();
//        deviceAddress = intent.getStringExtra(BluetoothConnectActivity.DEVICE_ADDRESS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_home);

        callWidgets();

        dbHelper = new MyDBHelper(this,null,null,1);

        addDeviceBtn.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, AddDeviceActivity.class);
            startActivity(i);
        });

        allDeviceBtn.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AllDeviceActivity.class));
        });

        tempDisplayRoom();

    }


    private void callWidgets() {
        addDeviceBtn = (Button)findViewById(R.id.add_device_btn);
        allDeviceBtn = (Button)findViewById(R.id.show_all_devices_btn);
        mRelativeLayout = new RelativeLayout(this);
        roomRecyclerView = findViewById(R.id.room_recycle_view);
    }

    public void onAddDeviceConfirm(String name, String type, String location, String onCmd, String offCmd) {
        Toast.makeText(HomeActivity.this, "Confirm Added", Toast.LENGTH_SHORT).show();
    }

    private void tempDisplayRoom() {
        tempRoomView = findViewById(R.id.temp_room_list);
        ArrayList<String> roomArrayList = dbHelper.getRoomList();

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, roomArrayList);
        tempRoomView.setAdapter(adapter);

    }

//    private void showAddDevicePopUp() {
//        LayoutInflater inflater = (LayoutInflater) HomeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.activity_add_edit_device, null);
//
//        addDevicePopUp = new PopupWindow(
//                view,
//                LayoutParams.WRAP_CONTENT,
//                LayoutParams.WRAP_CONTENT);
//
//        Button confirmBtn = (Button) view.findViewById(R.id.add_device_confirm_btn);
//        Button cancelBtn = (Button) view.findViewById(R.id.add_device_cancel_btn);
//
//        addDevicePopUp.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
//
//        confirmBtn.setOnClickListener(v -> {
//            Toast.makeText(getApplicationContext(), "Device Added", Toast.LENGTH_SHORT).show();
//        });
//
//        cancelBtn.setOnClickListener(v -> {
//           addDevicePopUp.dismiss();
//        });
//    }


}
