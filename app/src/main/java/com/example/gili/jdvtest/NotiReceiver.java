package com.example.gili.jdvtest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotiReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent aIntent = new Intent(context, AlarmActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 10, aIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent bIntent = new Intent(context, CancelReceiver.class);
        PendingIntent cacelIntent =PendingIntent.getBroadcast(context, 20, bIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            String channelId = "one-channel";
            String channelName = "My Channel One";
            String channelDescription = "My Channel One Description";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channelDescription);
            manager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(context, channelId);

        }else{
            builder = new NotificationCompat.Builder(context);
        }
        builder.setSmallIcon(R.drawable.ic_alarm);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle("예정된 알람");
        builder.setContentText(intent.getStringExtra("time"));
        builder.setContentIntent(pIntent);
        builder.addAction(R.drawable.ic_alarm, "Cancel", cacelIntent);
        builder.setAutoCancel(true);
        builder.setOngoing(false);

        manager.notify(222, builder.build());
    }
}
