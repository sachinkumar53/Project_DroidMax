package com.sachin.volumepanel;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

public class VolumeChangeListener extends BroadcastReceiver {
    boolean isSoundSettings = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        String s = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        Log.d("Activity",""+s);
        if (s.equals("com.android.settings.SoundSettings")){
            isSoundSettings = true;
        }else {
            isSoundSettings = false;
        }
        PowerManager powerManager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn  = powerManager.isScreenOn();
        intent = new Intent(context,VolumePanel.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS|Intent.FLAG_ACTIVITY_NO_ANIMATION);
        if (isScreenOn){
            if (!isSoundSettings){
                context.startActivity(intent);
            }
        }
    }
}
