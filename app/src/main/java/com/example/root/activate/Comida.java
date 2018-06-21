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

public class Comida extends AppCompatActivity implements View.OnClickListener {

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
        //enviar al servidor la variable
        //comida, id
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
