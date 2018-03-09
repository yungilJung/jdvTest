package com.example.gili.jdvtest;

import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gili.data.FourFragment;
import com.example.gili.data.OneFragment;
import com.example.gili.data.ThreeFragment;
import com.example.gili.data.TwoFragment;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {

    android.support.v4.app.FragmentManager manager;
    OneFragment oneFragment;
    TwoFragment twoFragment;
    ThreeFragment threeFragment;
    FourFragment fourFragment;
    Button b1;
    Button b2;
    Button b3;
    Button b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        b1 = (Button)findViewById(R.id.btnFlag1);
        b2 = (Button)findViewById(R.id.btnFlag2);
        b3 = (Button)findViewById(R.id.btnFlag3);
        b4 = (Button)findViewById(R.id.btnFlag4);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);

        manager=getSupportFragmentManager();
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();
        fourFragment = new FourFragment();

        android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
        ft.addToBackStack(null);
        ft.add(R.id.main_container, oneFragment);
        ft.commit();

    }


    @Override
    public void onClick(View view) {
        if(view == b1){
            if(!oneFragment.isVisible()){
                android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.main_container, oneFragment);
                ft.commit();
            }
        }else if(view == b2){
            if(!twoFragment.isVisible()){
                twoFragment.show(manager, null);
            }
        }else if(view == b3){
            if(!threeFragment.isVisible()){
                android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.main_container, threeFragment);
                ft.commit();
            }
        }else if(view == b4){
            /*if(!fourFragment.isVisible()){
                android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
                ft.addToBackStack(null);
              //  ft.replace(R.id.main_container, fourFragment);
                ft.commit();
            }*/
        }
    }
}
