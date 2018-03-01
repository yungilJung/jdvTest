package com.example.gili.jdvtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {

/*
    Button btnVisible;
    Button btnHidden;
    TextView txtView;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test4_5);

       /* btnVisible=(Button)findViewById(R.id.btnVisible);
        btnHidden=(Button)findViewById(R.id.btnHidden);
        txtView=(TextView)findViewById(R.id.txtView);

        btnVisible.setOnClickListener(this);
        btnHidden.setOnClickListener(this);*/

/*        TabHost hos = (TabHost)findViewById(R.id.host);
        hos.setup();

        hos.addTab(hos.newTabSpec("").setContent(R.id.tab1).setIndicator("tab1"));
        hos.addTab(hos.newTabSpec("").setContent(R.id.tab2).setIndicator("tab2"));
        hos.addTab(hos.newTabSpec("").setContent(R.id.tab3).setIndicator("tab3"));
        hos.addTab(hos.newTabSpec("").setContent(R.id.tab3).setIndicator("tab4"));*/
     /*   TabHost.TabSpec spec = hos.newTabSpec("tab1");
        spec.setContent(R.id.tab1);
        hos.addTab(spec);

        spec = hos.newTabSpec("tab2");
        spec.setContent(R.id.tab2);
        hos.addTab(spec);

        spec = hos.newTabSpec("tab3");
        spec.setContent(R.id.tab3);
        hos.addTab(spec);*/

    }

/*    @Override
    public void onClick(View v){
       *//* if(v==btnVisible){
            txtView.setVisibility(View.VISIBLE);
        }
        else if(v==btnHidden){
            txtView.setVisibility(View.INVISIBLE);
        }*//*
    }*/
}
