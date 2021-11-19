package com.example.delirush.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.delirush.App;
import com.example.delirush.OrderActivity;
import com.example.delirush.R;

public class Alarm_Service extends Service{
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    public static String orderID;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Initialization of mediaPlayer(obtain the sound file) and audioManager(get the volume)
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.ringtone);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        stopService(new Intent(getApplicationContext(), Status_Service.class));
    }

    /**
     * Verify whether the app is in background or foreground
     * Background -> display notification
     * Foreground -> navigate to orderActivity
     */
    @Override
    public int onStartCommand (Intent intent,int flags, int startId){
        orderID  = (String) intent.getExtras().get("orderID");
        // verify whether the phone is on ringer mode, if muted the alarm shall not be played
        if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL){
            mediaPlayer.start();
        }
        if(App.getStatus() != 0){
            Intent order_intent = new Intent(getApplicationContext(), OrderActivity.class).
                    setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            order_intent.putExtra("ringing", "ringing");
            order_intent.putExtra("orderID", orderID);
            order_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(order_intent);
        }
        else{
            Intent order_intent = new Intent(this, Notification_Service.class);
            order_intent.putExtra("ringing", "ringing");
            order_intent.putExtra("orderID", orderID);
            order_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startService(order_intent);
        }

        return START_STICKY;
    }

    /**
     * Destroy the instance of the service class created and stop the media playing
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
        mediaPlayer.stop();
    }
}