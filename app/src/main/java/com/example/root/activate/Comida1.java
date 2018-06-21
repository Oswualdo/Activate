package com.example.root.activate;

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

public class Comida1 extends AppCompatActivity {

    MaterialBetterSpinner Cena, Desayuno, Colacion,Bebida;

    String[] _sn = {"Si", "No"};
    String[] _colacion = {"Ninguna", "Frituras, galletas y pastelillos", "Fruta, gbarra de cereal y yogur"};
    String[] _bebida = {"Refresco","Agua fresca de sabor", "Agua natural o mineral"};

    Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Alimentación");
        setSupportActionBar(toolbar);


        Cena=(MaterialBetterSpinner)findViewById(R.id.Cena);
        Desayuno=(MaterialBetterSpinner)findViewById(R.id.Desayuno);
        Colacion=(MaterialBetterSpinner)findViewById(R.id.Colacion);
        Bebida=(MaterialBetterSpinner)findViewById(R.id.Bebida);

        enviar = (Button) findViewById(R.id.Cenviar);

        ArrayAdapter<String> adapter_cena = new ArrayAdapter<String>(Comida1.this, android.R.layout.simple_dropdown_item_1line, _sn);
        ArrayAdapter<String> adapter_desayuno = new ArrayAdapter<String>(Comida1.this, android.R.layout.simple_dropdown_item_1line, _sn);
        ArrayAdapter<String> adapter_colacion = new ArrayAdapter<String>(Comida1.this, android.R.layout.simple_dropdown_item_1line, _colacion);
        ArrayAdapter<String> adapter_bebida = new ArrayAdapter<String>(Comida1.this, android.R.layout.simple_dropdown_item_1line, _bebida);

        Cena.setAdapter(adapter_cena);
        Desayuno.setAdapter(adapter_desayuno);
        Colacion.setAdapter(adapter_colacion);
        Bebida.setAdapter(adapter_bebida);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cen = Cena.getText().toString();
                String des = Desayuno.getText().toString();
                String col = Colacion.getText().toString();
                String beb = Bebida.getText().toString();

                boolean A = validarSpinner(cen, Cena);
                boolean B = validarSpinner(des, Desayuno);
                boolean C = validarSpinner(col, Colacion);
                boolean D = validarSpinner(beb, Bebida);

                if (A && B && C && D) {
                    Intent intent = new Intent(Comida1.this, Comida.class);
                    //intent.putExtra("Id",deviceID);
                    //intent.putExtra("Nickname",nombre);
                    //intent.putExtra("Edad",edad);
                    //intent.putExtra("Alt",altura);
                    //intent.putExtra("Genero",gen);
                    //intent.putExtra("Coordinacion",coor);
                    //intent.putExtra("Rol",ro);
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
}
