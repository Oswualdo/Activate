package com.example.root.activate;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ServicioEncuestas extends IntentService {

    public ServicioEncuestas() {
        super("ServicioEncuestas");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent alarma = new Intent(this, Comida1.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, alarma, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE,30);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String date = df.format(Calendar.getInstance().getTime());

       if(date.equals("25-06-2018")){
           AlarmManager manager2 = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
           Intent alarma2 = new Intent(this, Final.class);
           PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, alarma2, 0);
           Calendar calendar2 = Calendar.getInstance();
           calendar2.setTimeInMillis(System.currentTimeMillis());
           calendar2.set(Calendar.HOUR_OF_DAY, 10);
           calendar2.set(Calendar.MINUTE,40);
           manager2.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent2);

       }
    }

}
