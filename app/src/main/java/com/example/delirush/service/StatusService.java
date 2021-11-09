package com.example.delirush.service;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.delirush.OrderActivity;
import com.example.delirush.OrderListData;
import com.example.delirush.PrefConfig;

import java.util.ArrayList;

public class StatusService extends Service {
//    private ArrayList<OrderListData> orderData = OrderActivity.getOrderData();
    private ArrayList<OrderListData> orderData;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        orderData = (ArrayList<OrderListData>) PrefConfig.readListFromPref(this);
    }

    @Override
    public int onStartCommand (Intent intent,int flags, int startId){
        new Thread(new Runnable() {
            public void run() {
                boolean match_collected = true;
                // check if all order are collected status, if not then need loop the order status until ready is found
                for(int i=0;i<orderData.size();i++){
                    if(orderData.get(i).getOrderStatus() != "Collected"){
                        match_collected = false;
                        break;
                    }
                }
                if(!match_collected && !orderData.isEmpty()){
                    for(int position=0; position<orderData.size();position++) {
                        if (orderData.get(position).getOrderStatus().equals("Ready")) {
                            intentAlarm(position);
                            break;
                        }
                        if(position == orderData.size()-1) {
                            position = -1;   // loop again until ready is found
                            // read again the orderData
                            orderData = (ArrayList<OrderListData>) PrefConfig.readListFromPref(getApplicationContext());;   // update the data
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