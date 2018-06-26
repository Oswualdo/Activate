package com.example.root.activate;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StepSend extends IntentService{
    /*Para el servidor*/
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String USERS_REF="Usuarios";
    public static final String ACTIVIDAD_REF="Actividad";
    public static final String DIA_KEY="Dia";
    public static final String MES_KEY="Mes";
    public static final String DISTANCIA_KEY="Distancia";
    public static final String PASOS_KEY="Pasos";
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
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String IDNombre = prefs.getString("Nick","Usuario");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        String[] separated = date.split("-");
        String dia=separated[0];
        String mes=separated[1];

        Map<String,Object> activityData = new HashMap<String, Object>();
        activityData.put(DIA_KEY,dia);
        activityData.put(MES_KEY,mes);
        activityData.put(DISTANCIA_KEY,distnacia);
        activityData.put(PASOS_KEY,pasos);

        db.collection(USERS_REF).document(IDNombre).collection(ACTIVIDAD_REF).document().set(activityData);



    }
}
