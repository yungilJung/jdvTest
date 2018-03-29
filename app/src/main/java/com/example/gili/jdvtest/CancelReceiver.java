package com.example.gili.jdvtest;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

public class CancelReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager alram = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent bIntent = new Intent("com.example.gili.jdvtest.ACTION_ALARM");
        PendingIntent alaramIntent = PendingIntent.getActivity(context, 100, bIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alram.cancel(alaramIntent);
        alaramIntent.cancel();

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(222);

    }
}
