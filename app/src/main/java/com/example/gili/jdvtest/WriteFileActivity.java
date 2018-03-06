package com.example.gili.jdvtest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gili.comutil.CommonUtils;

import java.io.File;
import java.io.FileWriter;

public class WriteFileActivity extends AppCompatActivity {

    boolean fileReadPermission = false;
    boolean fileWritePermission = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_file);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            fileReadPermission = true;
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            fileWritePermission = true;
        }

        if(!fileWritePermission || !fileReadPermission){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }

        Button btnSave=(Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = CommonUtils.getEditText(WriteFileActivity.this, R.id.txtContent).getText().toString();
                if(fileReadPermission && fileWritePermission){
                    FileWriter writer;
                    try{
                        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/myApp";
                        File dir = new File(dirPath);
                        if(!dir.exists()){
                            dir.mkdir();
                        }

                        File file = new File(dir+"/myfile.txt");
                        if(!file.exists()){
                            file.createNewFile();
                        }

                        writer = new FileWriter(file, true);
                        writer.write(content);
                        writer.flush();
                        writer.close();

                        Intent intent = new Intent(WriteFileActivity.this,ReadFileActivity.class);
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    CommonUtils.showLongMessage(WriteFileActivity.this,"권한이 없어 기능 실현이 안되요..");
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==200 && grantResults.length>0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                fileReadPermission = true;
            }
            if(grantResults[1]==PackageManager.PERMISSION_GRANTED){
                fileWritePermission = true;
            }
        }
    }
}
