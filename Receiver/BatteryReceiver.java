package com.example.administrator.fpcamera.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int currLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);  //当前电量
        int total = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 1);      //总电量
        int technology= intent.getIntExtra(BatteryManager.EXTRA_TECHNOLOGY, 2);
        //...还可以获得很多信息
        //剩余电量
        int percent = currLevel * 100 / total;
        Toast.makeText(context,"总电量: " + total + "%"
                +"电池型号："+technology+"currLevel电量: "
                + currLevel,Toast.LENGTH_SHORT).show();
    }
}
