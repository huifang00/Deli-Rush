package com.example.delirush.service;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.delirush.OrderActivity;
import com.example.delirush.OrderListData;

import java.util.ArrayList;

public class StatusService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private ArrayList<OrderListData> orderData = OrderActivity.getOrderData();
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand (Intent intent,int flags, int startId){
        new Thread(new Runnable() {
            public void run() {
                boolean match_collected = true;
                // check if all order are collected status, if not then need loop the order status until ready is found
                for(int i=0;i<OrderActivity.getOrderStatusList().size();i++){
                    if(OrderActivity.getOrderStatusList().get(i) != "Collected"){
                        match_collected = false;
                        break;
                    }
                }

                if(!match_collected && !OrderActivity.getOrderData().isEmpty()){
                    for(int position=0; position<orderData.size();position++) {
                        if (orderData.get(position).getOrderStatus() == "Ready") {
                            intentAlarm(position);
                            break;
                        }
                        if(position == orderData.size()-1) {
                            position = -1;   // loop again until ready is found
                            orderData = OrderActivity.getOrderData();   // update the data
                        }
                    }
                }
            }

            private void intentAlarm(int position) {
                Intent intent_alarm = new Intent(getApplicationContext(), AlarmService.class);
                intent_alarm.putExtra("orderID", orderData.get(position).getOrderID());
                startService(intent_alarm);
            }
        }).start();
        return START_STICKY;
    }

    /**
     * Used to destroy the instance of the service class created.
     * Also used for testing purposes in SoundServiceTest class.
     */
    @Override
    public void onDestroy(){
        Toast.makeText(getApplicationContext(),"DESTROYED1",Toast.LENGTH_SHORT).show();
    }
}