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
    public static final String DEVICE_TYPE = "device_type";
    public static final String DEVICE_LOCATION = "device_location";
    public static final String DEVICE_ON_CMD = "device_on_cmd";
    public static final String DEVICE_OFF_CMD = "device_off_cmd";

    // Query to create table
    public static final String CREATE_TABLE_DEVICES = "CREATE TABLE " + TABLE_DEVICES  +
            "(" + DEVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + DEVICE_NAME + " TEXT, " +
            DEVICE_TYPE + " TEXT, " +  DEVICE_LOCATION + " TEXT, " + DEVICE_ON_CMD + " TEXT, " +
            DEVICE_OFF_CMD + " TEXT );";

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DEVICES);
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
        values.put(DEVICE_TYPE, device.getDeviceType());
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
                device.setDeviceType(cursor.getString(2));
                device.setDeviceLocation(cursor.getString(3));
                device.setDeviceOnCmd(cursor.getString(4));
                device.setDeviceOffCmd(cursor.getString(5));
                deviceArrayList.add(device);
            } while(cursor.moveToNext());

        } else {
            deviceArrayList = null;
        }

        cursor.close();
        db.close();
        return deviceArrayList;

    }

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
        values.put(DEVICE_TYPE, device.getDeviceType());
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
