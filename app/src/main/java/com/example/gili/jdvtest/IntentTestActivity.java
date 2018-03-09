package com.example.gili.jdvtest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class IntentTestActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    File filePath;
    ImageView resultImgView;
    TextView resultView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_test);

        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
        btn7 = (Button)findViewById(R.id.btn7);
        resultImgView = (ImageView)findViewById(R.id.imgResult);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        resultImgView.setOnClickListener(this);

        resultView = (TextView)findViewById(R.id.txtResult);
    }

    @Override
    public void onClick(View view) {
        if(view == btn1){
            // 주소록
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent, 10);
        }else if (view == btn2){
            // 카메라 Data
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 30);
        }else if (view == btn3){
            // 카메라 File
            //if(ContextCompat.checkSelfPermission(IntentTestActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                try {
                    String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myapp";
                    File dir = new File(dirPath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    filePath = File.createTempFile("IMG", ".jpg", dir);
                    if (!filePath.exists()) {
                        filePath.createNewFile();
                    }
                    Uri photoURI = FileProvider.getUriForFile(IntentTestActivity.this, BuildConfig.APPLICATION_ID+".provider",filePath);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, 40);
                }catch (Exception e){
                    e.printStackTrace();
                }
            //}

        }else if (view == btn4){
            // 음성인식
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성인식테스트");
            startActivityForResult(intent, 50);
        }else if (view == btn5){
            // 지도
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952,126.9779451"));
            startActivity(intent);
        }else if (view == btn6){
            // 브라우저
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com"));
            startActivity(intent);
        }else if (view == btn7){
            // call
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-6750-2613"));
                startActivity(intent);
            }else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},100);
            }
        }
    }


    int reqHeight = 768;
    int reqWidth = 1024;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK)return;


        if(requestCode==10){
            String result = data.getDataString();
            resultView.setText(result);
        }else if(requestCode==30){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            resultImgView.setImageBitmap(bitmap);
        }else if(requestCode==40) {
            if (filePath != null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                try {
                    options.inJustDecodeBounds = true;
                    InputStream in = new FileInputStream(filePath);
                    BitmapFactory.decodeStream(in, null, options);
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final int height = options.outHeight;
                final int width = options.outWidth;
                int inSampleSize = 1;
                if (height > reqHeight || width > reqWidth) {
                    final int heightRatio = Math.round((float) height / (float) reqHeight);
                    final int widthRatio = Math.round((float) width / (float) reqWidth);
                    inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
                }
                BitmapFactory.Options imgOptions = new BitmapFactory.Options();
                imgOptions.inSampleSize = inSampleSize;
                Bitmap bitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath(), imgOptions);
                resultImgView.setImageBitmap(bitmap);
            }
        }else if (requestCode == 50){
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String result = results.get(0);
            resultView.setText(result);
        }
//        super.onActivityResult(requestCode, resultCode, data);
    }
}
