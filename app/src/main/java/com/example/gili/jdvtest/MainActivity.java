package com.example.gili.jdvtest;

import android.content.DialogInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity  {

/*
    Button btnVisible;
    Button btnHidden;
    TextView txtView;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test5_1);

        final ArrayList<String> arrSelected = new ArrayList<String>();

        // 진동
        //Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        //vib.vibrate(new long[]{500,1000,500,1000,500,1000},-1);

        // 효과음
        /*Uri noti = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), noti);
        ringtone.play();*/

        // 토스트
        /*Toast t = Toast.makeText(this,"TEST 입니다.", Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER,0,0);
        t.show();*/

        // Single alert
        /*AlertDialog alertDialog;
        // alert
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(android.R.drawable.ic_dialog_alert);
        alert.setTitle("위험");
        alert.setMessage("헐.....!!!");
        alert.setPositiveButton("OK", null);
        alert.setNegativeButton("NO", null);

        alert.setCancelable(false);



        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();*/

        // Choice
        AlertDialog alertDialog;
        // alert
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(android.R.drawable.ic_dialog_alert);
        alert.setTitle("위험");
        //alert.setMessage("헐.....!!!");
        final CharSequence items[] = {"item1","item2","item3","item4"};
        //alert.setItems(items, null);

        /*alert.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TextView txt = (TextView)findViewById(R.id.txtSelected);
                //if(txt!=null){
                    txt.setText(items[i]);
                    Toast.makeText(MainActivity.this,items[i], Toast.LENGTH_LONG).show();
               // }
            }
        });*/


       alert.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                //TextView txt = (TextView)findViewById(R.id.txtSelected);
                if(b) {
                  //  txt.setText(items[i]);
                    arrSelected.add(items[i].toString());
                } else{
                    arrSelected.remove(items[i].toString());
                }
            }
        });

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Collections.sort(arrSelected);

                String strSelected = "";
                for(int j=0;j<arrSelected.size();j++){
                    /*if(j==0){
                        strSelected += arrSelected.get(j);
                    }
                    else{
                        strSelected += ","+arrSelected.get(j);
                    }*/
                    strSelected += arrSelected.get(j).toString()+",";
                }
                strSelected = strSelected.substring(0,strSelected.lastIndexOf(','));
                /*strSelected = String.join(",", arrSelected);*/
                /*strSelected = arrSelected.stream().map(Object::toString).collect(Collectors.joining(","));*/
                Toast.makeText(MainActivity.this, strSelected,Toast.LENGTH_LONG).show();
            }
        });

        alert.setNegativeButton("NO", null);
        alert.setCancelable(false);

        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

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

    DialogInterface.OnClickListener dialogOk = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            //dialogInterface.

        }
    };

}
