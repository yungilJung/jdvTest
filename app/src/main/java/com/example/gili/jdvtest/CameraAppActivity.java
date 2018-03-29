package com.example.gili.jdvtest;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;

import com.example.gili.comutil.CameraUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class CameraAppActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener, View.OnClickListener {

    TextureView textureView;
    ImageView btnPhoto;
    ImageView btnRecord;
    ImageView btnVideo;

    boolean isVideoBtnEnable = false;
    boolean isPictureBtnEnable = true;
    boolean isRecording;

    Camera camera;
    List<Camera.Size> supportedPreviewSizes;
    Camera.Size previewSize;

    SurfaceTexture surface;
    MediaRecorder mediaRecorder;
    Drawable recordNormalDr;
    Drawable recordActivDr;
    Drawable videoDisableDr;
    Drawable videoEnableDr;
    Drawable picutreDisableDr;
    Drawable picutreEnableDr;
    private int result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(  ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED ||
             ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ||
             ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 200);
        }else{
            initLayout();
        }
        recordNormalDr = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_btn_normal, null);
        recordActivDr= ResourcesCompat.getDrawable(getResources(), R.drawable.ic_btn_recording, null);
        videoDisableDr= ResourcesCompat.getDrawable(getResources(), R.drawable.ic_video_disable, null);
        videoEnableDr= ResourcesCompat.getDrawable(getResources(), R.drawable.ic_video, null);
        picutreDisableDr= ResourcesCompat.getDrawable(getResources(), R.drawable.ic_picture_disable, null);
        picutreEnableDr= ResourcesCompat.getDrawable(getResources(), R.drawable.ic_picture, null);
    }

    private void initLayout(){
        setContentView(R.layout.activity_camera_app);
        textureView = (TextureView)findViewById(R.id.textureView);
        btnPhoto = (ImageView)findViewById(R.id.btnPhoto);
        btnRecord = (ImageView)findViewById(R.id.btnRecord);
        btnVideo = (ImageView)findViewById(R.id.btnVideo);

        textureView.setSurfaceTextureListener(this);
        btnPhoto.setOnClickListener(this);
        btnRecord.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 200 && grantResults.length>0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                initLayout();
            }
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int i, int i1) {
        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        supportedPreviewSizes= parameters.getSupportedPreviewSizes();
        if(supportedPreviewSizes != null){
            previewSize = CameraUtil.getOptimalPreviewSize(supportedPreviewSizes, i, i1);
            parameters.setPreviewSize(previewSize.width, previewSize.height);
        }
        int result = CameraUtil.setCameraDisplayOrientation(this,0);
        this.result=result;
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        parameters.setRotation(result);

        camera.setParameters(parameters);
        camera.setDisplayOrientation(result);
        try{
            camera.setPreviewTexture(surface);
        }catch(Exception e){}
        camera.startPreview();

        this.surface = surface;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        camera.stopPreview();;
        camera.release();
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == btnPhoto){
            btnPhoto.setImageDrawable(picutreEnableDr);
            btnVideo.setImageDrawable(videoDisableDr);
            isPictureBtnEnable = true;
            isVideoBtnEnable =false;
        }else if(view == btnVideo){
            btnPhoto.setImageDrawable(picutreDisableDr);
            btnVideo.setImageDrawable(videoEnableDr);
            isPictureBtnEnable = false;
            isVideoBtnEnable =true;
        }else if(view == btnRecord){
            if (camera != null) {
                if(isPictureBtnEnable){
                    btnRecord.setImageDrawable(recordActivDr);
                    recordPicture();
                }else if(isVideoBtnEnable){
                    recordVideo();
                }
            }
        }
    }

    private void recordPicture(){
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                FileOutputStream fos;
                try{
                    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/myApp");
                    if(!dir.exists()){
                        dir.mkdir();
                    }
                    File file = File.createTempFile("IMG-",".jpg", dir);
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    fos = new FileOutputStream(file);
                    fos.write(bytes);
                    fos.flush();
                    fos.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                camera.startPreview();
                btnRecord.setImageDrawable(recordNormalDr);
            }
        });
    }

    private void recordVideo(){
        if(isRecording){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            btnRecord.setImageDrawable(recordNormalDr);
        }else{
            try
            {
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/myApp");
                if(!dir.exists()){
                    dir.mkdir();
                }
                File file = File.createTempFile("VIDEO-",".3gp", dir);
                mediaRecorder = new MediaRecorder();
                camera.unlock();
                mediaRecorder.setCamera(camera);
                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
                mediaRecorder.setOutputFile(file.getAbsolutePath());
                mediaRecorder.setOrientationHint(result);
                mediaRecorder.prepare();
                /*if(!file.exists()){
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                fos.write(bytes);
                fos.flush();
                fos.close();*/
            }catch (Exception e){
                e.printStackTrace();
            }
            mediaRecorder.start();
            isRecording = true;
            btnRecord.setImageDrawable(recordActivDr);
        }
    }
}


