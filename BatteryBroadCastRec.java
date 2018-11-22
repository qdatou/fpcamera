package com.example.administrator.fpcamera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class BatteryBroadCastRec extends BroadcastReceiver {
    private int batLevel = 0;
    private int batPlugged = 0;
    private int batScale = 0;
    private int batTemperature = 0;
    private int batVoltage = 0;
    public int getBatLevel() {
        return batLevel;
    }

    public void setBatLevel(int batLevel) {
        this.batLevel = batLevel;
    }

    public int getBatPlugged() {
        return batPlugged;
    }

    public void setBatPlugged(int batPlugged) {
        this.batPlugged = batPlugged;
    }

    public int getBatScale() {
        return batScale;
    }

    public void setBatScale(int batScale) {
        this.batScale = batScale;
    }

    public int getBatTemperature() {
        return batTemperature;
    }

    public void setBatTemperature(int batTemperature) {
        this.batTemperature = batTemperature;
    }

    public int getBatVoltage() {
        return batVoltage;
    }

    public void setBatVoltage(int batVoltage) {
        this.batVoltage = batVoltage;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //所剩的电量
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        //是否在充电，0表示在充电
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
        //总电量
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
        //电池温度
        int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
        //电压
        int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
        setBatLevel(level);
        setBatPlugged(plugged);
        setBatScale(scale);
        setBatTemperature(temperature);
        setBatVoltage(voltage);
    }
}
