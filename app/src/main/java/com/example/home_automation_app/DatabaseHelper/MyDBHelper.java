package com.example.home_automation_app.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import com.example.home_automation_app.Models.Device;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "homeDeviceDB.db";

    // Table Name
    private static final String TABLE_DEVICES = "devices";

    // Table devices - column names
    public static final String DEVICE_ID = "device_id";
    public static final String DEVICE_NAME = "device_name";
//    public static final String DEVICE_TYPE = "device_type";
    public static final String DEVICE_LOCATION = "device_location";
    public static final String DEVICE_ON_CMD = "device_on_cmd";
    public static final String DEVICE_OFF_CMD = "device_off_cmd";

    // Query to create table
    public static final String CREATE_TABLE_DEVICES = "CREATE TABLE " + TABLE_DEVICES  +
            "(" + DEVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + DEVICE_NAME + " TEXT, " +
            DEVICE_LOCATION + " TEXT, " + DEVICE_ON_CMD + " TEXT, " +
            DEVICE_OFF_CMD + " TEXT );";

    // Query to add initial devices
//    private final Device device1 = new Device("LED 1", "Dining Room", "led1_on", "led1_off");
    private static final String led1 = createInitialDevices("LED 1", "Dining Room", "led1_on", "led1_off");
    private static final String led2 = createInitialDevices("LED 2", "Kitchen", "led2_on", "led2_off");
    public static final String led3 = createInitialDevices("LED 3", "Living Room", "led3_on", "led3_off");
    public static final String led4 = createInitialDevices("LED 4", "Bedroom 1", "led4_on", "led4_off");
    public static final String motion = createInitialDevices("Light Motion", "Living Room", "motion_on", "motion_off");
    public static final String gateMotion = createInitialDevices("Gate Motion", "Car Porch", "gateMotion_on", "gateMotion_off");
    public static final String gate = createInitialDevices("Gate", "Car Porch", "gate_on", "gate_off");
    public static final String airCond = createInitialDevices("Air Cond", "Living Room", "airCond_on",  "airCond_off");
    public static final String led5 = createInitialDevices("LED 5", "Bedroom 2", "led5_on", "led5_off");
    public static final String led6 = createInitialDevices("LED 6", "Bedroom 3", "led6_on", "led6_off");
    public static final String led7 = createInitialDevices("LED 7", "Bedroom 4", "led7_on", "led7_off");

    private static String createInitialDevices(String name, String location, String onCmd, String offCmd) {
        String query = "INSERT INTO " + TABLE_DEVICES + "(" + DEVICE_NAME + ", " + DEVICE_LOCATION + ", " +
                DEVICE_ON_CMD + ", " + DEVICE_OFF_CMD + ") VALUES ('" + name + "', '" + location + "', '" +
                onCmd + "', '" + offCmd + "');";
        return query;
    }

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DEVICES);
        db.execSQL(led1);
        db.execSQL(led2);
        db.execSQL(led3);
        db.execSQL(led4);
        db.execSQL(motion);
        db.execSQL(gateMotion);
        db.execSQL(gate);
        db.execSQL(airCond);
        db.execSQL(led5);
        db.execSQL(led6);
        db.execSQL(led7);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICES);
    }

    /***
     * Function to add new device
     * @param device New Device Object
     */
    public boolean addDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Adding Device Information
        ContentValues values = new ContentValues();
        values.put(DEVICE_NAME, device.getDeviceName());
        values.put(DEVICE_LOCATION, device.getDeviceLocation());
        values.put(DEVICE_ON_CMD, device.getDeviceOnCmd());
        values.put(DEVICE_OFF_CMD, device.getDeviceOffCmd());
        long result = db.insert(TABLE_DEVICES, null, values);
        db.close();

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /***
     * Function to get all devices
     * @return All devices array list
     */
    public ArrayList<Device> getAllDevices() {
        String query = "SELECT * FROM " + TABLE_DEVICES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Device> deviceArrayList = new ArrayList<>();

        if(cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                Device device = new Device();
                device.setDeviceId(Integer.parseInt(cursor.getString(0)));
                device.setDeviceName(cursor.getString(1));
                device.setDeviceLocation(cursor.getString(2));
                device.setDeviceOnCmd(cursor.getString(3));
                device.setDeviceOffCmd(cursor.getString(4));
                deviceArrayList.add(device);
            } while(cursor.moveToNext());

        } else {
            deviceArrayList = null;
        }

        cursor.close();
        db.close();
        return deviceArrayList;

    }

    /***
     * Function to get devices according to location
     * @param location Location name
     * @return Array List of devices
     */
    public ArrayList<Device> getDevicesByLocation(String location) {
        String query = "SELECT * FROM " + TABLE_DEVICES +
                " WHERE " + DEVICE_LOCATION + " = '" + location + "' ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Device> deviceArrayList = new ArrayList<>();

        if(cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                Device device = new Device();
                device.setDeviceId(Integer.parseInt(cursor.getString(0)));
                device.setDeviceName(cursor.getString(1));
                device.setDeviceLocation(cursor.getString(2));
                device.setDeviceOnCmd(cursor.getString(3));
                device.setDeviceOffCmd(cursor.getString(4));
                deviceArrayList.add(device);
            } while(cursor.moveToNext());

        } else {
            deviceArrayList = null;
        }

        cursor.close();
        db.close();
        return deviceArrayList;
    }

    public int getNumOfDevicesByLocation(String location) {
        String query = "SELECT * FROM " + TABLE_DEVICES +
                " WHERE " + DEVICE_LOCATION + " = '" + location + "' ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }

    public Device getDeviceById(int id) {
        String query = "SELECT * FROM " + TABLE_DEVICES +
                " WHERE " + DEVICE_ID + " = " + id + " ";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Device device = new Device();

        if(cursor.moveToFirst()) {
            device.setDeviceId(Integer.parseInt(cursor.getString(0)));
            device.setDeviceName(cursor.getString(1));
            device.setDeviceLocation(cursor.getString(2));
            device.setDeviceOnCmd(cursor.getString(3));
            device.setDeviceOffCmd(cursor.getString(4));
        }


        cursor.close();
        db.close();
        return device;
    }

    /***
     * Function to get all location of devices
     * @return room list
     */
    public ArrayList<String> getRoomList() {
        String query = "SELECT DISTINCT " + DEVICE_LOCATION + " FROM " + TABLE_DEVICES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<String> roomArrayList = new ArrayList<>();

        if(cursor.moveToFirst()) {

            cursor.moveToFirst();
            do {
                roomArrayList.add(cursor.getString(0));
            } while (cursor.moveToNext());

        } else {
            roomArrayList = null;
        }
        cursor.close();
        db.close();
        return roomArrayList;
    }

    /***
     * Function to update device information after being edited
     * @param device Device that been edited
     * @return true if the device edited successfully
     */
    public boolean updateDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DEVICE_NAME, device.getDeviceName());
        values.put(DEVICE_LOCATION, device.getDeviceLocation());
        values.put(DEVICE_ON_CMD, device.getDeviceOnCmd());
        values.put(DEVICE_OFF_CMD, device.getDeviceOffCmd());
        db.update(TABLE_DEVICES, values, DEVICE_ID + " = ? ",
                new String[]{String.valueOf(device.getDeviceId())});

        db.close();
        return true;
    }

    public  boolean deleteDevice(int deviceID) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_DEVICES + " WHERE " +
                DEVICE_ID + " = \"" + deviceID + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            db.delete(TABLE_DEVICES, DEVICE_ID + " = ?",
                    new String[]{String.valueOf(deviceID)});
            cursor.close();
            result = true;
        }
        return result;
    }

}
