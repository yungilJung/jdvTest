package com.example.gili.jdvtest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    FloatingActionButton fab;


    TextView timeView;
    Switch aSwitch;

    SharedPreferences prefs;

    AlarmManager alram;
    PendingIntent preIntent;
    PendingIntent alaramIntent;
    Intent aIntent;

    boolean isClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        fab = (FloatingActionButton)findViewById(R.id.mission1_fab);
        timeView = (TextView)findViewById(R.id.mission1_time);
        aSwitch = (Switch)findViewById(R.id.mission1_switch);

        alram = (AlarmManager)getSystemService(ALARM_SERVICE);

        aIntent = new Intent(this, NotiReceiver.class);
        preIntent = PendingIntent.getBroadcast(this, 50, aIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent bIntent = new Intent("com.example.gili.jdvtest.ACTION_ALARM");
        alaramIntent = PendingIntent.getActivity(this, 100, bIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        int hour = prefs.getInt("hour", -1);
        int minutue = prefs.getInt("minute", -1);
        boolean enable = prefs.getBoolean("enable", false);

        if(hour > -1 && minutue > -1){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minutue);
            SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
            timeView.setText(sd.format(calendar.getTime()));
        }

        if(enable){
            aSwitch.setChecked(true);
        }
        fab.setOnClickListener(this);
        aSwitch.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View view) {
        final Calendar c = Calendar.getInstance();
        int currentHour = c.get(Calendar.HOUR_OF_DAY);
        int currentMinutes = c.get(Calendar.MINUTE);
        final TimePickerDialog timeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                isClick = true;
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("hour", hour);
                editor.putInt("minute", minute);
                editor.putBoolean("enable", true);
                editor.commit();

                SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
                timeView.setText(sd.format(calendar.getTime()));
                aSwitch.setChecked(true);
                isClick = false;

                aIntent.putExtra("time", sd.format(calendar.getTime()));
                alram.set(AlarmManager.RTC, calendar.getTimeInMillis()-120000, preIntent);
                alram.set(AlarmManager.RTC, calendar.getTimeInMillis(), alaramIntent);
            }
        }, currentHour, currentMinutes, false);
        timeDialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("enable", isChecked);
        editor.commit();

        if(isChecked){
            if(!isClick) {
                int hour = prefs.getInt("hour", -1);
                int minute = prefs.getInt("minute", -1);
                if (hour > -1 && minute > -1) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);

                    alram.set(AlarmManager.RTC, calendar.getTimeInMillis() - 60000, preIntent);
                    alram.set(AlarmManager.RTC, calendar.getTimeInMillis(), alaramIntent);
                }
            }
        }else{
            alram.cancel(preIntent);
            alram.cancel(alaramIntent);
            preIntent.cancel();
            alaramIntent.cancel();
            editor.putBoolean("enable", false);

        }

    }

}
