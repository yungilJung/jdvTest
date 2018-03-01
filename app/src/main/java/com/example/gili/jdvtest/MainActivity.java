package com.example.gili.jdvtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnVisible;
    Button btnHidden;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test4_1);

       /* btnVisible=(Button)findViewById(R.id.btnVisible);
        btnHidden=(Button)findViewById(R.id.btnHidden);
        txtView=(TextView)findViewById(R.id.txtView);

        btnVisible.setOnClickListener(this);
        btnHidden.setOnClickListener(this);*/

    }

    @Override
    public void onClick(View v){
       /* if(v==btnVisible){
            txtView.setVisibility(View.VISIBLE);
        }
        else if(v==btnHidden){
            txtView.setVisibility(View.INVISIBLE);
        }*/
    }
}
