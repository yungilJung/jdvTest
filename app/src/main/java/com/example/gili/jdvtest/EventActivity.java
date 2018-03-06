package com.example.gili.jdvtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        TextView txtLabel = (TextView)findViewById(R.id.txtLabel);
        Switch swTime = (Switch)findViewById(R.id.swTime);
        CheckBox chkHeap = (CheckBox)findViewById(R.id.chkHeap);
        CheckBox chkRepeat = (CheckBox)findViewById(R.id.chkRepeat);

        txtLabel.setOnClickListener(new TextView.OnClickListener(){

            @Override
            public void onClick(View view) {
                showMessage("Label Clicked");
            }
        });

        swTime.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                showMessage("Time Checked Change");
            }
        });

        chkHeap.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                showMessage("Heap Checked Change");
            }
        });

        chkRepeat.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                showMessage("Repeat Checked Change");
            }
        });
    }

    private  void showMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    float initX=0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            initX = event.getRawX();
            //showMessage(" x : " +  initX);
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            float diffX = initX-event.getRawX();
            if (diffX > 30) {
                showMessage("왼쪽으로 화면을 밀었음");
            }
            else if(diffX<-30){
                showMessage("오른쪽으로 화면을 밀었음");
            }
        }
        return super.onTouchEvent(event);
    }

    long initTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis()-initTime>3000){
                showMessage("종료할려면 한번 더.");
                initTime = System.currentTimeMillis();
            }else
            {
                finish();
            }
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
