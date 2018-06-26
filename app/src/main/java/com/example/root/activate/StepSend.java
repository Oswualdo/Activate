package com.example.root.activate;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StepSend extends IntentService{
    //float dis =
    float distnacia = MainActivity.dis;
    float pasos = MainActivity.pasos;
    public StepSend() {
        super(StepSend.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(this.getClass().getSimpleName(),"Prueba");
        //Aqui se pone lo q se va a enviar
        String id=login.id(StepSend.this);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        String[] separated = date.split("-");
        String dia=separated[0];
        String mes=separated[1];
    }
}
