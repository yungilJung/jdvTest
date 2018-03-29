package com.example.gili.jdvtest;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmDispActivity extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_disp);

        TextView timeView = (TextView)findViewById(R.id.mission1_alarm_time);
        ImageView stopView = (ImageView)findViewById(R.id.mission1_alarm_stop);

        stopView.setOnClickListener(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("enable", false);
        editor.commit();

        int hour = prefs.getInt("hour", -1);
        int minute = prefs.getInt("minute", -1);
        if (hour > -1 && minute > -1) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);

            SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
            timeView.setText(sd.format(calendar.getTime()));
        }
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(222);

        PowerManager pm = (PowerManager)getSystemService(POWER_SERVICE);
        if(!pm.isScreenOn()){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        }

        Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        player = MediaPlayer.create(getApplicationContext(), alarm);
        player.start();

    }

    @Override
    public void onClick(View view) {
        player.stop();
    }
}
