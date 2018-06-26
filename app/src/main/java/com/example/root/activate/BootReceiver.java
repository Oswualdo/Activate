package com.example.root.activate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import java.util.Calendar;

public class BootReceiver extends BroadcastReceiver {

    private static final int PERIOD_MS = 5000; // modificar a dos horas

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Boot Receiver", Toast.LENGTH_SHORT).show();

        Intent newIntent = new Intent(context, StepSend.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 1, newIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), PERIOD_MS, pendingIntent);

        /*  Otro   */

        Intent newIntent2 = new Intent(context, StepSend.class);
        PendingIntent pendingIntent2 = PendingIntent.getService(context, 1, newIntent2, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar4 = Calendar.getInstance();
        calendar4.setTimeInMillis(System.currentTimeMillis());
        calendar4.set(Calendar.HOUR_OF_DAY, 00);
        calendar4.set(Calendar.MINUTE,00);

        AlarmManager manager2 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //manager2.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), PERIOD_MS, pendingIntent2);
        manager2.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar4.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent2);
    }
}
