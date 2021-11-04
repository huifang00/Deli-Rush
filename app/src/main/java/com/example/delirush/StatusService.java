package com.example.delirush;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class StatusService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand (Intent intent,int flags, int startId){
        ArrayList<OrderListData> orderData = OrderActivity.getOrderData();
        for(int position=0; position<orderData.size();position++) {
            if (orderData.get(position).getOrderStatus() == "Ready") {
                Intent intent_status = new Intent(getApplicationContext(), AlarmService.class);
                intent_status.putExtra("orderID", orderData.get(position).getOrderID());
                startService(intent_status);
//                startService(new Intent(getApplicationContext(), AlarmService.class));
            }
        }
        // Keep checking the status of food until all are collected
        for(int position=0; position<orderData.size();position++) {
            if (orderData.get(position).getOrderStatus() != "Collected") {
                startService(new Intent(getApplicationContext(),StatusService.class));
            }
        }
        // if the status is like on the way for colletion, loop the one for 5 minutees
        // if no then ring again? this might need one more service to update the random time
        return START_STICKY;
    }

    /**
     * Used to destroy the instance of the service class created.
     * Also used for testing purposes in SoundServiceTest class.
     */
    @Override
    public void onDestroy(){
        Toast.makeText(getApplicationContext(),"DESTROYED",Toast.LENGTH_SHORT).show();
    }
}