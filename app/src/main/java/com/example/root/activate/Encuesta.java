package com.example.root.activate;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class Encuesta extends AppCompatActivity {

    MaterialBetterSpinner alimentacion,ejercicio,fumar,tomar,estancia;

    String[] ALIM={"Mala","Regular","Buena","Excelente"};
    String[] EJER={"<1 Hora","1-5 Horas","5-10 Horas",">10 Horas"};
    String[] FU ={"Si","No"};
    String[] TO ={"Si","No"};
    String[] ESTAN={"<4 Horas","4-6 Horas","6-8 Horas",">8 Horas"};

    Button enviar;
    TextInputLayout Peso,Altura;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String id_user=getIntent().getStringExtra("Id");
        final String NickName=getIntent().getStringExtra("Nickname");
        final String Edad=getIntent().getStringExtra("Edad");
        final String Genero=getIntent().getStringExtra("Genero");
        final String Coordinacion=getIntent().getStringExtra("Coordinacion");
        final String Rol=getIntent().getStringExtra("Rol");

        alimentacion=(MaterialBetterSpinner)findViewById(R.id.Alimentacion);
        ejercicio=(MaterialBetterSpinner)findViewById(R.id.Ejercicio);
        fumar=(MaterialBetterSpinner)findViewById(R.id.Fumar);
        tomar=(MaterialBetterSpinner)findViewById(R.id.Alcohol);
        estancia=(MaterialBetterSpinner)findViewById(R.id.Tiempo);

        enviar=(Button)findViewById(R.id.button1);

        Peso=(TextInputLayout)findViewById(R.id.Peso);
        Altura=(TextInputLayout)findViewById(R.id.Altura);

        ArrayAdapter<String> adapter_Alimentacion = new ArrayAdapter<String>(Encuesta.this, android.R.layout.simple_dropdown_item_1line, ALIM);
        ArrayAdapter<String> adapter_Ejercicio = new ArrayAdapter<String>(Encuesta.this, android.R.layout.simple_dropdown_item_1line, EJER);
        ArrayAdapter<String> adapter_Fumar = new ArrayAdapter<String>(Encuesta.this, android.R.layout.simple_dropdown_item_1line, FU);
        ArrayAdapter<String> adapter_Tomar = new ArrayAdapter<String>(Encuesta.this, android.R.layout.simple_dropdown_item_1line, TO);
        ArrayAdapter<String> adapter_Estancia = new ArrayAdapter<String>(Encuesta.this, android.R.layout.simple_dropdown_item_1line, ESTAN);

        alimentacion.setAdapter(adapter_Alimentacion);
        ejercicio.setAdapter(adapter_Ejercicio);
        fumar.setAdapter(adapter_Fumar);
        tomar.setAdapter(adapter_Tomar);
        estancia.setAdapter(adapter_Estancia);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comida=alimentacion.getText().toString();
                String deport=ejercicio.getText().toString();
                String fum=fumar.getText().toString();
                String tom=tomar.getText().toString();
                String estanc=estancia.getText().toString();

                String peso=Peso.getEditText().getText().toString();
                String altura=Altura.getEditText().getText().toString();

                boolean A=validarSpinner(comida,alimentacion);
                boolean B=validarSpinner(deport,ejercicio);
                boolean C=validarSpinner(fum,fumar);
                boolean D=validarSpinner(tom,tomar);
                boolean E=validarSpinner(estanc,estancia);
                boolean F=ValidarPeso(peso);
                boolean G=ValidarPeso(altura);

                if(A && B && C && D && E && F && G ) {
                    //AQUI YA PUEDES MANDAR LOS DATOS QUE SE OBTIENEN DE LA APP
                    //A LA BASE DE DATOS
                    //Los datos son: id,NickName, Edad, Genero, Coordinacion, Rol, comida,deport,fum,tom,estanc,peso,altura

                    //para despues pasar a la siguiente pantalla
                    Intent intent = new Intent(Encuesta.this, MainActivity.class);
                    startActivity(intent);
                    finish();

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

    private boolean ValidarPeso(String dato) {
        if (dato.isEmpty()) {
            Peso.setError("Valor inválido");
            return false;
        } else {
            Peso.setError(null);
        }

        return true;
    }
}
