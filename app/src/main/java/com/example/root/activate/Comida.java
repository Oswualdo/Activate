package com.example.root.activate;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Comida extends AppCompatActivity implements View.OnClickListener {

    /*Para el servidor*/
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String USERS_REF="Usuarios";
    public static final String TIPO_COMIDA_KEY="Tipo_comida";
    public static final String ALIMENTO_REF="Alimento";
    public static final String DIA_KEY="Dia";
    public static final String MES_KEY="Mes";
    public static final String LAST_UPDATE_KEY="Last_Update";

    ImageButton carne,pescado,pollo,vegetariano;
    String comida="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida);
        carne=(ImageButton) findViewById(R.id.imageButton2);
        pescado=(ImageButton)findViewById(R.id.imageButton3);
        pollo=(ImageButton)findViewById(R.id.imageButton4);
        vegetariano=(ImageButton)findViewById(R.id.imageButton5);

        carne.setOnClickListener(this);
        pescado.setOnClickListener(this);
        pollo.setOnClickListener(this);
        vegetariano.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==carne){
            comida="Carne";
        }
        if(v==pescado){
            comida="Pescado";
        }
        if(v==pollo){
            comida="Pollo";
        }
        if(v==vegetariano){
            comida="Vegetariano";
        }
        String id=login.id(Comida.this);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        String[] separated = date.split("-");
        String dia=separated[0];
        String mes=separated[1];
        //enviar al servidor la variable
        //comida, id, date

        Map<String,Object> dataOfFood = new HashMap<String, Object>();
        dataOfFood.put(TIPO_COMIDA_KEY,comida);
        dataOfFood.put(LAST_UPDATE_KEY,date);
        dataOfFood.put(DIA_KEY,dia);
        dataOfFood.put(MES_KEY,mes);

        String idComida = date+"-1";
        db.collection(USERS_REF).document(id).collection(ALIMENTO_REF).document(idComida).set(dataOfFood);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog);
        builder.setTitle("Datos enviados correctamente")
                .setMessage("Gracias por responder esta encuesta")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(R.drawable.opcion4)
                .show();

    }
}
