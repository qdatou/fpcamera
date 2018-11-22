package com.example.administrator.fpcamera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.widget.ImageView;

import java.util.List;

public class OperateImageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate_image);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String name = bundle.getString("name");
        final String title = bundle.getString("title");
        final String data = bundle.getString("data");
        final String desc = bundle.getString("desc");
        final String size = bundle.getString("size");
        ImageView ii = (ImageView)findViewById(R.id.iv);
        Uri uri = Uri.parse(data);
        ii.setImageURI(uri);
    }
}
