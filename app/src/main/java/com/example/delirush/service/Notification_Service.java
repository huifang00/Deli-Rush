package com.example.delirush.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

import com.example.delirush.App;
import com.example.delirush.OrderActivity;
import com.example.delirush.R;

public class Notification_Service extends Service {
    public static boolean notified = false;
    public static String orderID = "";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Call the function which shows the notification in notification bar
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand (Intent intent,int flags, int startId) {
        String orderID = (String) intent.getExtras().get("orderID");
        notified = true;
        if (App.getStatus() == 0) {
            Notification_Service.orderID = orderID;
        }
        showNotifications(orderID);
        // if the app is running in background
        return START_STICKY;
    }

    /**
     * Show the notification in notification bar
     * @param orderID
     */
    private void showNotifications(String orderID) {
        final String CHANNEL_ID = "001";
        final String msg = "Food order ID: " + orderID + " ready to be collected.";
        Intent order_intent = new Intent(getApplicationContext(), OrderActivity.class).
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        order_intent.putExtra("orderID", orderID);
        order_intent.putExtra("ringing", "ringing");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 1, order_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "001", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

            android.app.Notification.Builder builder = new android.app.Notification.Builder(this, CHANNEL_ID);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(msg)
                    .setPriority(android.app.Notification.PRIORITY_DEFAULT)
                    .setContentIntent(contentIntent)
                    .setOngoing(true)   // when user clear notification, it won't clear till they click in the apps
                    .setAutoCancel(true);   // when notification is clicked, remove from notification bar

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(001, builder.build());

        } else {
            android.app.Notification.Builder builder = new android.app.Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(msg)
                    .setPriority(android.app.Notification.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(001, builder.build());
        }
    }

    /**
     * Destroy the instance of the service class created.
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}