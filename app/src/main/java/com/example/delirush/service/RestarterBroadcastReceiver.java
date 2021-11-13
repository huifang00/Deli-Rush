package com.example.delirush.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestarterBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(RestarterBroadcastReceiver.class.getSimpleName(), "Service Stopped, but this is a never ending service.");
        context.startService(new Intent(context, Alarm_Service.class));;
    }
}

