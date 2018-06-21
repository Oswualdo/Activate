package com.example.root.activate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //Para lanzar la encuesta de comida
            AlarmManager manager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent alarma=new Intent(context,Comida1.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,alarma,0);
            Calendar calendar=Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY,17);
            //calendar.set(Calendar.MINUTE,46);

            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

        }
        AlarmManager manager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarma=new Intent(context,Comida1.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,alarma,0);
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,17);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

    }
}
