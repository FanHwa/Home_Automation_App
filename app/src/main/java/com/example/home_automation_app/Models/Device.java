package com.example.home_automation_app.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Device implements Parcelable {

    private int deviceId;
    private String deviceName;
    private String deviceType;
    private String deviceLocation;
    private String deviceOnCmd;
    private String deviceOffCmd;

    public Device() {
    }

    public Device(String deviceName, String deviceType, String deviceLocation, String deviceOnCmd, String deviceOffCmd) {
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.deviceLocation = deviceLocation;
        this.deviceOnCmd = deviceOnCmd;
        this.deviceOffCmd = deviceOffCmd;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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
        deviceType = in.readString();
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
        dest.writeString(deviceType);
        dest.writeString(deviceLocation);
        dest.writeString(deviceOnCmd);
        dest.writeString(deviceOffCmd);
    }
}
