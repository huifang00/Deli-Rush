package com.example.delirush.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.delirush.Database;
import com.example.delirush.OrderActivity;

public class Status_Service extends Service {

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
        stopService(new Intent(getApplicationContext(), TimerService.class));
    }

    /**
     * Check the status of order until call AlarmService when ready status is found from the order list
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand (Intent intent,int flags, int startId){
        Database dbHandler = new Database(getApplicationContext(), null, null, 1);
        new Thread(new Runnable() {
            public void run() {
                boolean match_collected = true;
                // check if all order are collected status, if not then need loop the order status until ready is found
                for (int i = 0; i < OrderActivity.orderData.size(); i++) {
                    if (!OrderActivity.orderData.get(i).getOrderStatus().equals("On My Way")) {
                        match_collected = false;
                        break;
                    }
                }
                if (!match_collected && !OrderActivity.orderData.isEmpty()) {
                    for (int position = 0; position < OrderActivity.orderData.size(); position++) {
                        if (OrderActivity.orderData.get(position).getOrderStatus().equals("Ready")) {
                            intentAlarm(position);
                            break;
                        }
                        if (position == OrderActivity.orderData.size() - 1) {
                            position = -1;   // loop again until ready is found
                        }
                    }
                }
            }

            private void intentAlarm(int position) {
                Intent intent_alarm = new Intent(getApplicationContext(), Alarm_Service.class);
                intent_alarm.putExtra("orderID", String.valueOf(OrderActivity.orderData.get(position).getOrderID()));
                startService(intent_alarm);
            }
        }).start();
        return START_STICKY;
    }

    /**
     * Destroy the instance of the service class created.
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}