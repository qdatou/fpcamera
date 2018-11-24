package com.example.administrator.fpcamera;

import android.app.Application;
import android.media.session.PlaybackState;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;

public class CustomApplication extends Application {

    private  int frageIndex =1;

    public  int getFrageIndex() {
        return frageIndex;
    }

    public  void setFrageIndex(int frageIndex) {
        this.frageIndex = frageIndex;
    }

    private static CustomApplication app;

    public static CustomApplication getApp() {
        return app;
    }

    public static String getResolution() {
        return resolution;
    }

    public static void setResolution(String resolution) {
        CustomApplication.resolution = resolution;
    }

    private static String resolution = "4：3";

    public static void setPhotoUri(Uri photoUri) {
        CustomApplication.photoUri = photoUri;
    }

    public static Uri getPhotoUri() {
        return photoUri;
    }

    private static Uri photoUri = null;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Fresco.initialize(this);
    }
}
