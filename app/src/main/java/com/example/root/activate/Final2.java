package com.example.root.activate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class Final2 extends AppCompatActivity {

    MaterialBetterSpinner Gusto,Consulta,Distincion,Pulsera,Persona,Amigo, Habito,Comieron;

    String[] G={"Nada","Poco","Me agradó","Mucho"};
    String[] C={"Nunca","1 vez por semana","1 vez al dia","Más de una vez al dia"};
    String[] D ={"Nunca","1 vez", "De 2 a 3 veces","Más de 4 veces"};
    String[] P ={"Nunca","Regularmente", "Siempre"};
    String[] Pe={"Si","No"};
    String[] H={"Ninguno","Algunos","Todos"};
    String[] Co={"Ningún dia","Algunos dias","Todos los dias"};

    Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Gusto=(MaterialBetterSpinner)findViewById(R.id.Gusto);
        Consulta=(MaterialBetterSpinner)findViewById(R.id.Consulta);
        Distincion=(MaterialBetterSpinner)findViewById(R.id.Distincion);
        Pulsera=(MaterialBetterSpinner)findViewById(R.id.Pulsera);
        Persona=(MaterialBetterSpinner)findViewById(R.id.Persona);
        Amigo=(MaterialBetterSpinner)findViewById(R.id.Amigo);
        Habito=(MaterialBetterSpinner)findViewById(R.id.Habito);
        Comieron=(MaterialBetterSpinner)findViewById(R.id.Comieron);

        enviar=(Button)findViewById(R.id.BtnFinal);

        ArrayAdapter<String> adapter_Gusto = new ArrayAdapter<String>(Final2.this, android.R.layout.simple_dropdown_item_1line, G);
        ArrayAdapter<String> adapter_Consulta = new ArrayAdapter<String>(Final2.this, android.R.layout.simple_dropdown_item_1line, C);
        ArrayAdapter<String> adapter_Distincion = new ArrayAdapter<String>(Final2.this, android.R.layout.simple_dropdown_item_1line, D);
        ArrayAdapter<String> adapter_Pulsera = new ArrayAdapter<String>(Final2.this, android.R.layout.simple_dropdown_item_1line, P);
        ArrayAdapter<String> adapter_Persona = new ArrayAdapter<String>(Final2.this, android.R.layout.simple_dropdown_item_1line, Pe);
        ArrayAdapter<String> adapter_Amigo = new ArrayAdapter<String>(Final2.this, android.R.layout.simple_dropdown_item_1line, Pe);
        ArrayAdapter<String> adapter_Habito = new ArrayAdapter<String>(Final2.this, android.R.layout.simple_dropdown_item_1line, H);
        ArrayAdapter<String> adapter_Comieron = new ArrayAdapter<String>(Final2.this, android.R.layout.simple_dropdown_item_1line, Co);

        Gusto.setAdapter(adapter_Gusto);
        Consulta.setAdapter(adapter_Consulta);
        Distincion.setAdapter(adapter_Distincion);
        Pulsera.setAdapter(adapter_Pulsera);
        Persona.setAdapter(adapter_Persona);
        Amigo.setAdapter(adapter_Amigo);
        Habito.setAdapter(adapter_Habito);
        Comieron.setAdapter(adapter_Comieron);


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gusto=Gusto.getText().toString();
                String consulta=Consulta.getText().toString();
                String distincion=Distincion.getText().toString();
                String pulsera=Pulsera.getText().toString();
                String persona=Persona.getText().toString();
                String amigo=Amigo.getText().toString();
                String habito=Habito.getText().toString();
                String comieron=Comieron.getText().toString();


                boolean A=validarSpinner(gusto,Gusto);
                boolean B=validarSpinner(consulta,Consulta);
                boolean C=validarSpinner(distincion,Distincion);
                boolean D=validarSpinner(pulsera,Pulsera);
                boolean E=validarSpinner(persona,Persona);
                boolean F=validarSpinner(amigo,Amigo);
                boolean G=validarSpinner(habito,Habito);
                boolean H=validarSpinner(comieron,Comieron);


                if(A && B && C && D && E && F && G && H ) {
                    //AQUI YA PUEDES MANDAR LOS DATOS QUE SE OBTIENEN DE LA APP
                    //A LA BASE DE DATOS
                    //Los datos son: gusto,consulta,distincion.pulsera,persona,amigo,habito,comieron

                    AlertDialog.Builder builder = new AlertDialog.Builder(Final2.this, android.R.style.Theme_Material_Light_Dialog);
                    builder.setTitle("Datos enviados correctamente")
                            .setMessage("Gracias por responder la encuesta y utilizar la aplicación")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setIcon(R.drawable.opcion4)
                            .show();

                }

            }
        });

    }

    private boolean validarSpinner(String dato, MaterialBetterSpinner Comp) {
        if (dato.isEmpty()) {
            Comp.setError("Seleccione una opción");
            return false;
        } else {
            Comp.setError(null);
        }

        return true;
    }

}