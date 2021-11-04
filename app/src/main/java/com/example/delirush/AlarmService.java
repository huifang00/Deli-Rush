package com.example.delirush;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class AlarmService extends Service {
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

        if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
            mediaPlayer.start();    // play the ringtone
            display(orderID);

        return START_STICKY;
    }

    /**
     * Used to destroy the instance of the service class created.
     * Also used for testing purposes in SoundServiceTest class.
     */
    @Override
    public void onDestroy(){
        Toast.makeText(getApplicationContext(),"DESTROYED",Toast.LENGTH_SHORT).show();
        mediaPlayer.stop();
    }

    public void display(String orderID){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        for(int i=0;i<OrderActivity.getOrderData().size();i++){
            if(OrderActivity.getOrderData().get(i).getOrderID() == orderID){
                OrderActivity.getOrderData().get(i).setOrderStatus("ON MY WAY");
                break;
            }
        }
        String msg = "Food order ID: " + orderID + " ready to be collected.";
        builder1.setMessage(msg);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        mediaPlayer.stop();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
        }

}