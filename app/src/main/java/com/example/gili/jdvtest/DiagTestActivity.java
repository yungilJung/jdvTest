package com.example.gili.jdvtest;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class DiagTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diag_test);

        Button btnTime = (Button) findViewById(R.id.btnTime);
        Button btnDate = (Button) findViewById(R.id.btnDate);
        Button btnCustom = (Button) findViewById(R.id.btnCustom);
        Button btnProgress = (Button) findViewById(R.id.btnProgress);


        btnTime.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timeDialog = new TimePickerDialog(DiagTestActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        showToast(i+":"+i1);
                    }
                },hour, minute, false);
                timeDialog.show();
            }
        });

        btnDate.setOnClickListener(new  Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(DiagTestActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        showToast(i+"-"+i1+"-"+i2);
                    }

                },year,month,day);
                dateDialog.show();
            }
        });

        btnCustom.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DiagTestActivity.this);
                LayoutInflater inflator = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                View vw = inflator.inflate(R.layout.dialog, null);
                builder.setView(vw);
                builder.setPositiveButton("OK", null);
                builder.setNegativeButton("NO", null);

                AlertDialog custDiag = builder.create();
                custDiag.show();
            }
        });

        btnProgress.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(DiagTestActivity.this);
                progressDialog.setTitle("Wait..");
                progressDialog.setMessage("......");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
        }
        });

    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
