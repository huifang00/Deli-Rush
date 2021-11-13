package com.example.delirush.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.delirush.OrderListData;
import com.example.delirush.PrefConfigOrderList;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TimerService extends Service {

    private ArrayList<OrderListData> orderData;
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
        orderData = (ArrayList<OrderListData>) PrefConfigOrderList.readListFromPref(this);
    }

    /**
     * Start the timer to update the order status to ready
     * This supposed to be done in server side, but currently no server is implemented yet
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand (Intent intent,int flags, int startId){
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
                        for (int i = 0; i < orderData.size(); i++) {
                            if (orderData.get(i).getOrderID().equals(orderID)) {
                                orderData.get(i).setOrderStatus("Ready");
                                PrefConfigOrderList.writeListInPref(getApplicationContext(), orderData);
                                break;
                            }
                        }
                        orderData = (ArrayList<OrderListData>) PrefConfigOrderList.readListFromPref(getApplicationContext());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();
        if(!orderID.isEmpty()) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        /*
                        Update the status to ready after 5 seconds
                         (This should be updated from food seller, but food seller was not implemented yet
                         */
                        TimeUnit.MILLISECONDS.sleep(5000);
                        for (int i = 0; i < orderData.size(); i++) {
                            if (orderData.get(i).getOrderID().equals(orderID)) {
                                orderData.get(i).setOrderStatus("Ready");
                                PrefConfigOrderList.writeListInPref(getApplicationContext(), orderData);
                                break;
                            }
                        }
                        orderData = (ArrayList<OrderListData>) PrefConfigOrderList.readListFromPref(getApplicationContext());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
//        Toast.makeText(getApplicationContext(), "onTaskRemoved", Toast.LENGTH_SHORT).show();
        if(!orderID.isEmpty()) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        /*
                        Update the status to ready after 5 seconds
                         (This should be updated from food seller, but food seller was not implemented yet
                         */
                        System.out.println("5000 milliseconds");
                        TimeUnit.MILLISECONDS.sleep(2000);
                        System.out.println("HIHI");
                        for (int i = 0; i < orderData.size(); i++) {
                            if (orderData.get(i).getOrderID().equals(orderID)) {
                                orderData.get(i).setOrderStatus("Ready");
                                PrefConfigOrderList.writeListInPref(getApplicationContext(), orderData);
                                break;
                            }
                        }
                        orderData = (ArrayList<OrderListData>) PrefConfigOrderList.readListFromPref(getApplicationContext());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }



//    /**
//     * Destroy the instance of the service class created.
//     */
//    @Override
//    public void onDestroy(){
//        super.onDestroy();
////        Intent broadcastIntent = new Intent(this, RestarterBroadcastReceiver.class);
////        sendBroadcast(broadcastIntent);
//    }
}
