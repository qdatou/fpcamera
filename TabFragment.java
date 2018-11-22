package com.example.administrator.fpcamera;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import me.leefeng.promptlibrary.PromptButton;
import me.leefeng.promptlibrary.PromptButtonListener;
import me.leefeng.promptlibrary.PromptDialog;

import static android.content.Context.BATTERY_SERVICE;
import static android.content.Context.WIFI_SERVICE;

/**
 * Created by ASUS on 2018/1/27.
 */

public class TabFragment extends Fragment {

    public static final String TITLE_TAG = "tabTitle";

    private int[] colors = {0xff009999, 0xffc6e2ff, 0xff668b8b, 0xff7A67EE, 0xffCD853F, 0xffEECFA1};

    public static TabFragment newInstance(String tabTitle) {

        Bundle args = new Bundle();

        TabFragment fragment = new TabFragment();
        args.putString(TITLE_TAG, tabTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if (getArguments() != null) {
            String title = getArguments().getString(TITLE_TAG);
            if(title == "相机设置"){
                view = inflater.inflate(R.layout.fragment_tab_one, container, false);
                Button btn = (Button)view.findViewById(R.id.tabOneSub);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //按钮的定义
                        PromptButton confirm = new PromptButton("确定", new PromptButtonListener() {
                            @Override
                            public void onClick(PromptButton button) {
                                Activity activity = getActivity();
                                if (activity != null) {
                                    try {
                                        Spinner sp = (Spinner) activity.findViewById(R.id.spinner);
                                        String str = sp.getSelectedItem().toString();
                                        CustomApplication.setResolution(str);
                                        Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
                                    }catch(Exception ex){
                                        Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        });
                        confirm.setFocusBacColor(Color.parseColor("#FAFAD2"));
                        //Alert的调用
                        PromptDialog promptDialog = new PromptDialog(getActivity());
                        promptDialog.showWarnAlert("你确定要提交吗？", new PromptButton("取消", new PromptButtonListener() {
                            @Override
                            public void onClick(PromptButton button) {
                                Activity activity = getActivity();
                                if (activity != null) {
                                    Toast.makeText(activity, button.getText(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }), confirm);
                    }
                });
            }
            else if(title == "系统信息"){
                view = inflater.inflate(R.layout.fragment_tab_two, container, false);
                TextView tv1 = view.findViewById(R.id.tabtwo1);
                tv1.setText( Build.BOARD);
                TextView tv2 = view.findViewById(R.id.tabtwo2);
                tv2.setText( Build.BRAND);
                TextView tv3 = view.findViewById(R.id.tabtwo3);
                tv3.setText( Build.MODEL);
                TextView tv4 = view.findViewById(R.id.tabtwo4);
                tv4.setText( Build.ID);
            }else if(title == "内存和存储"){
                view = inflater.inflate(R.layout.fragment_tab_three, container, false);
                ActivityManager am = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
                ActivityManager.MemoryInfo memeInfo = new ActivityManager.MemoryInfo();
                final StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());//调用该类来获取磁盘信息（而getExternalStorageDirectory就是外置存储）
                long tcounts = statFs.getBlockCount();//总共的block数
                long counts = statFs.getAvailableBlocks() ; //获取可用的block数
                long size = statFs.getBlockSize(); //每格所占的大小，一般是4KB==
                long availROMSize = counts * size;//可用内部存储大小
                long totalROMSize = tcounts *size; //内部存储总大小
                TextView tv1 = view.findViewById(R.id.tabthree1);
                tv1.setText( String.valueOf(memeInfo.availMem));
                TextView tv2 = view.findViewById(R.id.tabthree2);
                tv2.setText( String.valueOf(memeInfo.availMem));
                TextView tv3 = view.findViewById(R.id.tabthree3);
                tv3.setText( String.valueOf(availROMSize));
                TextView tv4 = view.findViewById(R.id.tabthree4);
                tv4.setText(String.valueOf(totalROMSize));
            }else if(title == "电池") {
                view = inflater.inflate(R.layout.fragment_tab_four, container, false);
//                IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//                registerReceiver(mReceiver, filter);

                BatteryManager batteryManager = (BatteryManager)getActivity().getSystemService(BATTERY_SERVICE);
                int battery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                TextView tv1 = view.findViewById(R.id.tabfour1);
                tv1.setText( String.valueOf(battery));
            }else if(title == "WLAN") {
                view = inflater.inflate(R.layout.fragment_tab_five, container, false);
                WifiManager mWifiManager = (WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                Method method = null;
                int i = 0;
                try {
                    method = mWifiManager.getClass().getMethod("getWifiApState");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    i = (Integer) method.invoke(mWifiManager);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                // 10---正在关闭；11---已关闭；12---正在开启；13---已开启
                TextView tv1 = view.findViewById(R.id.tabfour1);
                if(i==10){
                    tv1.setText( String.valueOf("正在关闭"));
                }else if(i==11){
                    tv1.setText( String.valueOf("已关闭"));
                }else if(i==12){
                    tv1.setText( String.valueOf("正在开启"));
                }else if(i==13){
                    tv1.setText( String.valueOf("已开启"));
                }else {
                    tv1.setText( String.valueOf("未知"));
                }
            }else {
                view = inflater.inflate(R.layout.fragment_tab, container, false);
                TextView tv = view.findViewById(R.id.tv);
                tv.setBackgroundColor(colors[new Random().nextInt(colors.length)]);

                BatteryManager batteryManager = (BatteryManager)getActivity().getSystemService(BATTERY_SERVICE);
                int battery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            }
        }
        return view;
    }
}
