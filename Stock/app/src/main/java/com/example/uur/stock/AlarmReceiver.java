package com.example.uur.stock;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Uğur on 15.5.2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    NotificationManager notificationManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,AlarmService.class);
        context.startService(i);
    }
}
