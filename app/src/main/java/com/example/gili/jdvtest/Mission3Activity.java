package com.example.gili.jdvtest;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gili.comutil.CommonUtils;

import org.w3c.dom.Text;

public class Mission3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_mission3);

       CommonUtils.setAwesomeTextView(this, R.id.txtLastName).setText("\uf2bd");
       CommonUtils.setAwesomeTextView(this,R.id.txtPhone).setText("\uf2bd");

    }
}
