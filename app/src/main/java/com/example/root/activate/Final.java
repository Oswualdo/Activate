package com.example.root.activate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.HashMap;
import java.util.Map;

public class Final extends AppCompatActivity {

    MaterialBetterSpinner alimentacion,ejercicio,fumar,tomar,estancia,CPESO;

    String[] ALIM={"Malos","Regulares","Buenos","Excelentes"};
    String[] EJER={"Menos de 1 Hora","De 1 a 5 Horas","De 6 a 10 Horas","Más de 10 Horas"};
    String[] FU ={"Nunca","Ocasionalmente", "Regularmente"};
    String[] TO ={"Nunca","Ocasionalmente", "Regularmente"};
    String[] ESTAN={"Menos de 4 Horas","De 4 a 6 Horas","De 7 a 9 Horas","Más de 9 Horas"};
    String[] CP={"Si","No"};

    Button enviar;
    TextInputLayout Peso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle("Encuesta Final");
        setSupportActionBar(toolbar);

        alimentacion=(MaterialBetterSpinner)findViewById(R.id.Alimentacion1);
        ejercicio=(MaterialBetterSpinner)findViewById(R.id.Ejercicio1);
        fumar=(MaterialBetterSpinner)findViewById(R.id.Fumar1);
        tomar=(MaterialBetterSpinner)findViewById(R.id.Alcohol1);
        estancia=(MaterialBetterSpinner)findViewById(R.id.Tiempo1);
        CPESO=(MaterialBetterSpinner)findViewById(R.id.CPeso1);

        enviar=(Button)findViewById(R.id.button1111);

        Peso=(TextInputLayout)findViewById(R.id.Peso1);

        ArrayAdapter<String> adapter_Alimentacion = new ArrayAdapter<String>(Final.this, android.R.layout.simple_dropdown_item_1line, ALIM);
        ArrayAdapter<String> adapter_Ejercicio = new ArrayAdapter<String>(Final.this, android.R.layout.simple_dropdown_item_1line, EJER);
        ArrayAdapter<String> adapter_Fumar = new ArrayAdapter<String>(Final.this, android.R.layout.simple_dropdown_item_1line, FU);
        ArrayAdapter<String> adapter_Tomar = new ArrayAdapter<String>(Final.this, android.R.layout.simple_dropdown_item_1line, TO);
        ArrayAdapter<String> adapter_Estancia = new ArrayAdapter<String>(Final.this, android.R.layout.simple_dropdown_item_1line, ESTAN);
        ArrayAdapter<String> adapter_CPESO = new ArrayAdapter<String>(Final.this, android.R.layout.simple_dropdown_item_1line, CP);

        alimentacion.setAdapter(adapter_Alimentacion);
        ejercicio.setAdapter(adapter_Ejercicio);
        fumar.setAdapter(adapter_Fumar);
        tomar.setAdapter(adapter_Tomar);
        estancia.setAdapter(adapter_Estancia);
        CPESO.setAdapter(adapter_CPESO);



        CPESO.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("Si")){
                    Peso.setVisibility(View.VISIBLE);
                    Peso.setHint("¿Cuánto pesas?");
                }
                if(s.toString().equals("No"))
                {
                    Peso.setVisibility(View.VISIBLE);
                    Peso.setHint("¿Cuánto calculas que pesas?");
                }

            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comida=alimentacion.getText().toString();
                String deport=ejercicio.getText().toString();
                String fum=fumar.getText().toString();
                String tom=tomar.getText().toString();
                String estanc=estancia.getText().toString();
                String p=CPESO.getText().toString();
                String peso=Peso.getEditText().getText().toString();


                boolean A=validarSpinner(comida,alimentacion);
                boolean B=validarSpinner(deport,ejercicio);
                boolean C=validarSpinner(fum,fumar);
                boolean D=validarSpinner(tom,tomar);
                boolean E=validarSpinner(estanc,estancia);
                boolean F=ValidarPeso(peso);
                boolean G=validarSpinner(p,CPESO);


                if(A && B && C && D && E && F && G ) {
                    //AQUI YA PUEDES MANDAR LOS DATOS QUE SE OBTIENEN DE LA APP
                    //A LA BASE DE DATOS
                    //Los datos son: comida,deport,fum,tom,estanc,peso,altura


                    //para despues pasar a la siguiente pantalla
                    Intent intent = new Intent(Final.this, Final2.class);
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
