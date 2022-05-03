package com.example.home_automation_app.Models;

import android.os.Parcel;
import android.os.Parcelable;

/***
 * This is a model class to store the information of a device
 */
public class Device implements Parcelable {

    private int deviceId;
    private String deviceName;
    private String deviceLocation;
    private String deviceOnCmd;
    private String deviceOffCmd;

    public Device() {
    }

    /**
     * Constructor for Device Class
     * @param deviceName Device Name
     * @param deviceLocation Location of Device
     * @param deviceOnCmd On Command of the device
     * @param deviceOffCmd Off Command of the device
     */
    public Device(String deviceName, String deviceLocation, String deviceOnCmd, String deviceOffCmd) {
        this.deviceName = deviceName;
        this.deviceLocation = deviceLocation;
        this.deviceOnCmd = deviceOnCmd;
        this.deviceOffCmd = deviceOffCmd;
    }

    /**
     * Get Device ID
     * @return Device ID
     */
    public int getDeviceId() {
        return deviceId;
    }

    /**
     * Set Device ID
     * @param deviceId Device Id
     */
    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public String getDeviceOnCmd() {
        return deviceOnCmd;
    }

    public void setDeviceOnCmd(String deviceOnCmd) {
        this.deviceOnCmd = deviceOnCmd;
    }

    public String getDeviceOffCmd() {
        return deviceOffCmd;
    }

    public void setDeviceOffCmd(String deviceOffCmd) {
        this.deviceOffCmd = deviceOffCmd;
    }

    protected Device(Parcel in) {
        deviceId = in.readInt();
        deviceName = in.readString();
        deviceLocation = in.readString();
        deviceOnCmd = in.readString();
        deviceOffCmd = in.readString();
    }

    public static final Creator<Device> CREATOR = new Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(deviceId);
        dest.writeString(deviceName);
        dest.writeString(deviceLocation);
        dest.writeString(deviceOnCmd);
        dest.writeString(deviceOffCmd);
    }
}
