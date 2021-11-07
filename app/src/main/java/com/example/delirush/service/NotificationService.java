package com.example.delirush.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

import com.example.delirush.OrderActivity;
import com.example.delirush.R;

public class NotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    @Override
    public void onCreate(){ super.onCreate(); }

    @Override
    public int onStartCommand (Intent intent,int flags, int startId) {
        String orderID = (String) intent.getExtras().get("orderID");
        showNotifications(orderID);
        return START_STICKY;
    }

    public void showNotifications(String orderID) {
        final String CHANNEL_ID = "001";
        final String msg = "Food order ID: " + orderID + " ready to be collected.";
        Intent order_intent = new Intent(getApplicationContext(), OrderActivity.class).
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        order_intent.putExtra("orderID", orderID);
        order_intent.putExtra("ringing", "ringing");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 1, order_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "001", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("This is description.");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

            Notification.Builder builder = new Notification.Builder(this, CHANNEL_ID);
            builder.setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentText(msg)
                    .setContentTitle(msg)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true);   // when click the notification the notification is disappear

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(001, builder.build());
            // we will write code for the android above and start.

        } else {
            // we wil write code for bellow area
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText("This is my notification")
                    .setContentTitle("This is title")
                    .setPriority(Notification.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(001, builder.build());
        }
    }
}