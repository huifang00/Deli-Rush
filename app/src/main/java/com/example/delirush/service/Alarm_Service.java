package com.example.delirush.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.Nullable;

import com.example.delirush.App;
import com.example.delirush.OrderActivity;
import com.example.delirush.R;

public class Alarm_Service extends Service{
    //getting the sound snippet from resources folder
    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Once an instance of the service class is created mediaPlayer, audioManager, and originalVolume are initialised.
     * mediaPlayer allows us to obtain the sound file from the resources folder.
     * audioManger is used for handling management of volume, ringer modes and audio routing.
     * originalVolume is used to store the current volume of the device.
     */
    @Override
    public void onCreate() {
        stopService(new Intent(getApplicationContext(), Status_Service.class));
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.ringtone);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    }

    /**
     * Once the service activity is started, we verify whether the phone is on ringer mode.
     * If the phone is muted, then the snippet shall not be played.
     * Once the snippet is completed, the original volume is restored.
     * @param intent
     * @param flags
     * @param startId
     * @return constant
     */
    @Override
    public int onStartCommand (Intent intent,int flags, int startId){
        String orderID  = (String) intent.getExtras().get("orderID");
        if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL){
            mediaPlayer.start();    // play the ringtone
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
            order_intent.putExtra("orderID", orderID);
            order_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startService(order_intent);
        }

        return START_STICKY;
    }

    /**
     * Used to destroy the instance of the service class created.
     * Also used for testing purposes in SoundServiceTest class.
     */
    @Override
    public void onDestroy(){
        Toast.makeText(getApplicationContext(),"DESTROYED2",Toast.LENGTH_SHORT).show();
        mediaPlayer.stop();
    }
}