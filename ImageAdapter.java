package com.example.administrator.fpcamera;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;



import java.util.ArrayList;
import java.util.List;

/**
 * 当前的图片浏览器的适配器
 *
 */
public class ImageAdapter extends BaseAdapter {
    /**
     * ctx
     */
    private Context context;
    private int num=0;
    /**
     * list
     */
    List<ImageInfo> list = new ArrayList<ImageInfo>();

    /**
     * ctor
     */
    public ImageAdapter(Context context,int num) {
        this.context = context;
        this.num = num;
        // 加载数据库中的图片信息
        loadImages();
    }

    /**
     * 加载图片信息
     */
    private void loadImages() {
        list.clear();
        getImages(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, list);
        getImages(MediaStore.Images.Media.INTERNAL_CONTENT_URI, list);
//        Log.i("list.size(): ", list.size() + "");
    }

    /**
     * 得到list
     *
     * @param uri
     * @param list
     */
    private void getImages(Uri uri, List<ImageInfo> list) {
        Cursor externalCursor = MediaStore.Images.Media.query(
                context.getContentResolver(), uri, new String[] {
                        MediaStore.Images.Media.TITLE,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.SIZE,
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DESCRIPTION });
        if (externalCursor != null) {
            while (externalCursor.moveToNext()) {
                ImageInfo model = new ImageInfo();
                model.title = externalCursor.getString(externalCursor
                        .getColumnIndex(MediaStore.Images.Media.TITLE));
                model.displayName = externalCursor.getString(externalCursor
                        .getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                model.description = externalCursor.getString(externalCursor
                        .getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
                model.size = externalCursor.getString(externalCursor
                        .getColumnIndex(MediaStore.Images.Media.SIZE));
                model.data = externalCursor.getString(externalCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                list.add(model);
            }
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view;
        int widthAndHeight = 0;
        if(num!=0) {
            widthAndHeight = (int) (getScreenWidth() / num);
        }else{
            widthAndHeight = (int) (getScreenWidth() / 4);
        }
        if (convertView != null) {
            view = (ImageView) convertView;
        } else {
            view = new ImageView(context);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(widthAndHeight,
                    widthAndHeight);
            view.setLayoutParams(params);
        }
        view.setImageBitmap(getThumbnail(list.get(position).data,
                widthAndHeight, widthAndHeight));
        return view;
    }

    /**
     * 获取缩略图
     *
     * @param pathName
     * @param width
     * @param height
     * @return
     */
    private Bitmap getThumbnail(String pathName, int width, int height) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, opts);// 图片未加载进内存，但是可以读取长宽
        int oriWidth = opts.outWidth;
        int oriHeight = opts.outHeight;
        opts.inSampleSize = oriWidth / width;
        opts.inSampleSize = opts.inSampleSize > oriHeight / height ? opts.inSampleSize
                : oriHeight / height;
        opts.inJustDecodeBounds = false;
        Bitmap decodeFile = BitmapFactory.decodeFile(pathName, opts);// 图片加载进内存
        Bitmap result = Bitmap.createScaledBitmap(decodeFile, width, height,
                false);
        decodeFile.recycle();
        return result;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    private float getScreenWidth() {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    private float getScreenHeight() {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getHeight();
    }
}
