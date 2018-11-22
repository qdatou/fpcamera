package com.example.administrator.fpcamera;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link PreviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreviewFragment extends Fragment
        implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
      public PreviewFragment() {
        // Required empty public constructor
        super();
    }
    /**
     * 显示一个本地图片。
     *
     * @param draweeView imageView。
     * @param path       路径。
     * @param width      实际宽。
     * @param height     实际高度。
     */
    public static void showFile(SimpleDraweeView draweeView, String path, int width, int height) {
        try {
            Uri uri = Uri.parse("file://" + path);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();
            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(draweeView.getController())
                    .setImageRequest(request)
                    .build();
            draweeView.setController(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示本地图片。
     *
     * @param draweeView imageView。
     * @param path       路径。
     */
    public static void showFile(SimpleDraweeView draweeView, String path) {
        try {
            Uri uri = Uri.parse("file://" + path);
            draweeView.setImageURI(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PreviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreviewFragment newInstance() {
        PreviewFragment fragment = new PreviewFragment();
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
        return inflater.inflate(R.layout.fragment_preview, container, false);
    }
    private SimpleDraweeView sdv;
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
//        view.findViewById(R.id.picture).setOnClickListener(this);
        super.onViewCreated(view,savedInstanceState);
        view.findViewById(R.id.backbutton).setOnClickListener(this);
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null){
//            actionBar.setHomeButtonEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

        sdv = (SimpleDraweeView) view.findViewById(R.id.iv_head_background);
        Uri uri = CustomApplication.getPhotoUri();
//        Uri uri =  Uri.parse("http://dynamic-image.yesky.com/740x-/uploadImages/2015/163/50/690V3VHW0P77.jpg");
//        sdv.setImageURI(uri);

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        sdv.setController(controller);
    }
    @Override
    public void onResume() {
        super.onResume();
        //startBackgroundThread();
    }

    @Override
    public void onPause() {
       // stopBackgroundThread();
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backbutton: {
                Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT) ; //"android.intent.action.GET_CONTENT"
                innerIntent.setType( "image/*"); //查看类型 String IMAGE_UNSPECIFIED = "image/*";
                innerIntent.addCategory(Intent. CATEGORY_OPENABLE );
                startActivityForResult(Intent. createChooser(innerIntent, null) , 2) ;
//                this.onStop();

//                if(CustomApplication.getFrageIndex() == 1){
//                    Activity activity = getActivity();
//                    if (null != activity) {
//                        getFragmentManager().beginTransaction()
//                                .replace(R.id.container, Camera2RawFragment.newInstance())
//                                .commit();
//                    }
//                }else{
//                    Activity activity = getActivity();
//                    if (null != activity) {
//                        getFragmentManager().beginTransaction()
//                                .replace(R.id.container, Camera2VideoFragment.newInstance())
//                                .commit();
//                    }
//                }
                break;
            }

        }
    }
}
