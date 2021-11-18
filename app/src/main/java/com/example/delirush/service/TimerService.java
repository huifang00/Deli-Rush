package com.example.delirush.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.delirush.Database;
import com.example.delirush.OrderActivity;
import com.example.delirush.R;

import java.util.concurrent.TimeUnit;

public class TimerService extends Service {

    private String orderID = "";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Read the order data from memory
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Start the timer to update the order status to ready
     * This supposed to be done in server side, but currently no server is implemented yet
     * This create a foreground service to notify user that something is running.
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand (Intent intent,int flags, int startId){
        final String CHANNEL_ID = "001";
        Intent order_intent = new Intent(getApplicationContext(), OrderActivity.class).
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 1, order_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Preparing your order [ID: " + intent.getExtras().getString("orderId_fromCart") + "]")
                .setContentIntent(contentIntent);
        Notification notification=builder.build();
        if(Build.VERSION.SDK_INT>=26) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "001", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
        startForeground(1, notification);
        Bundle extras = intent.getExtras();
        if(extras.getString("orderId_fromCart")!=null) {
            orderID = extras.getString("orderId_fromCart");
            new Thread(new Runnable() {
                public void run() {
                    try {
                        /*
                        Update the status to ready after 5 seconds
                         (This should be updated from food seller, but food seller was not implemented yet
                         */
                        TimeUnit.MILLISECONDS.sleep(5000);
                        Database dbHandler = new Database(getApplicationContext(), null, null, 1);
                        dbHandler.updateOrder(Integer.parseInt(orderID), "Ready");
                        OrderActivity.orderData.get(Integer.parseInt(orderID)-1).setOrderStatus("Ready");
                        // update the static variable
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return START_STICKY;
    }

    /**
     * Destroy the instance of the service class created and
     * remove the notification from the status bar
     */
    @Override
    public void onDestroy(){
        stopForeground(true);
        super.onDestroy();
    }
}
