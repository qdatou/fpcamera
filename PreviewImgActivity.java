package com.example.administrator.fpcamera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class PreviewImgActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_img);
        GridView lv_images = (GridView) findViewById(R.id.lv_images);
        lv_images.setNumColumns(2);
        ImageAdapter adapter = new ImageAdapter(this,2);
        lv_images.setAdapter(adapter);
        lv_images.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridView gv = (GridView)parent;
                ImageAdapter a = (ImageAdapter) gv.getAdapter();
                ImageInfo ii = a.list.get(position);
                Intent intent = new Intent(PreviewImgActivity.this,OperateImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",ii.displayName);
                bundle.putString("title",ii.title);
                bundle.putString("data",ii.data);
                bundle.putString("desc",ii.description);
                bundle.putString("size",ii.size);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.previewmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.showBig:
                GridView gv = (GridView) findViewById(R.id.lv_images);
                if(gv.getNumColumns() != 2) {
                    int widthAndHeight = gv.getColumnWidth()*2;
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(widthAndHeight,
                            widthAndHeight);

                    gv.setNumColumns(2);
                    gv.setLayoutParams(params);
                }
                break;
            case R.id.showSmall:
                GridView gv2 = (GridView) findViewById(R.id.lv_images);
                if(gv2.getNumColumns() != 4) {
                    int widthAndHeight2 = gv2.getColumnWidth()/2;
                    ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(widthAndHeight2,
                            widthAndHeight2);
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(widthAndHeight2,widthAndHeight2);
//                    gv2.setLayoutParams(params2);
                    gv2.setNumColumns(4);

                }
                break;
            case R.id.backTop:
                finish();
//                Intent intent = new Intent(PreviewImgActivity.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
                break;
//            case R.id.filterAll:
//                break;
//            case R.id.filterNow:
//                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
