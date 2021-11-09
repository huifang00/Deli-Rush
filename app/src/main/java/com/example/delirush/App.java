package com.example.delirush;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class App extends Application implements Application.ActivityLifecycleCallbacks{
    private static int activityReferences = 0;
    private boolean isActivityChangingConfigurations = false;

        @Override
        public void onCreate() {
            super.onCreate();
            registerActivityLifecycleCallbacks(this);
        }

        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (++activityReferences == 1 && !isActivityChangingConfigurations) {
                // App enters foreground
            }
        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {

        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            isActivityChangingConfigurations = activity.isChangingConfigurations();
            if (--activityReferences == 0 && !isActivityChangingConfigurations) {
                // App enters background
            }
        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            System.out.println("KILLED");
        }

        public static int getStatus(){
            return activityReferences;
        }

}