package com.example.gili.jdvtest;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReadFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_file);

        TextView txtView = (TextView)findViewById(R.id.lblContent);

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/myApp/myfile.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            txtView.setText(buffer.toString());
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
