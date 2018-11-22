package com.example.administrator.fpcamera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import me.leefeng.promptlibrary.PromptButton;
import me.leefeng.promptlibrary.PromptButtonListener;
import me.leefeng.promptlibrary.PromptDialog;


public class SettingFragment extends Fragment implements View.OnClickListener {
    private Spinner sp = null;
    public SettingFragment() {
        // Required empty public constructor
        super();
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button).setOnClickListener(this);
        view.findViewById(R.id.setback).setOnClickListener(this);
        sp = (Spinner) view.findViewById(R.id.spinner);
    }
    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if(CustomApplication.getFrageIndex() == 1){
                        Activity activity = getActivity();
                        if (null != activity) {
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.container, Camera2RawFragment.newInstance())
                                    .commit();
                        }
                    }else{
                        Activity activity = getActivity();
                        if (null != activity) {
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.container, Camera2VideoFragment.newInstance())
                                    .commit();
                        }
                    }
                }
                return false;//当fragmenet没有消费点击事件，返回false，activity中继续执行对应的逻辑
            }
        });
    }

    @Override
    public void onPause() {
        // stopBackgroundThread();
        super.onPause();
    }
    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.setback:{
                try {
                    if (CustomApplication.getFrageIndex() == 1) {
                        Activity activity = getActivity();
                        if (null != activity) {
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.container, Camera2RawFragment.newInstance())
                                    .commit();
                        }
                    } else {
                        Activity activity = getActivity();
                        if (null != activity) {
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.container, Camera2VideoFragment.newInstance())
                                    .commit();
                        }
                    }
                }catch(Exception ex){
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.button: {
                //按钮的定义
                PromptButton confirm = new PromptButton("确定", new PromptButtonListener() {
                    @Override
                    public void onClick(PromptButton button) {
                        Activity activity = getActivity();
                        if (activity != null) {
                            try {
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
                break;
            }
        }
    }

}


