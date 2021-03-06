package com.example.root.activate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.sql.Struct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Comida1 extends AppCompatActivity {

    /*Para el servidor*/
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String USERS_REF="Usuarios";
    public static final String ALIMENTO_REF="Alimento";
    public static final String DIA_KEY="Dia";
    public static final String MES_KEY="Mes";
    public static final String CENA_KEY="Cena";
    public static final String DESAYUNO_KEY="Desayuno";
    public static final String COLACION_KEY="Colacion";
    public static final String BEBIDA_KEY="Bebida";
    public static final String LAST_UPDATE_KEY="Last_Update";

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

        Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

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

                    String id = login.id(Comida1.this);
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String date = df.format(Calendar.getInstance().getTime());
                    //datos para enviar al servidor
                    // cen,des,col,beb,id,date
                    String[] separated = date.split("-");
                    String dia=separated[0];
                    String mes=separated[1];

                    SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
                    String IDNombre = prefs.getString("Nick","Usuario");

                    Map<String,Object> dataOfFood = new HashMap<String, Object>();
                    dataOfFood.put(CENA_KEY,cen);
                    dataOfFood.put(DESAYUNO_KEY,des);
                    dataOfFood.put(COLACION_KEY,col);
                    dataOfFood.put(BEBIDA_KEY,beb);
                    dataOfFood.put(DIA_KEY,dia);
                    dataOfFood.put(MES_KEY,mes);
                    dataOfFood.put(LAST_UPDATE_KEY,date);
                    String idComida = date+"-2";

                    db.collection(USERS_REF).document(IDNombre).collection(ALIMENTO_REF).document(idComida).set(dataOfFood);

                    Intent intent = new Intent(Comida1.this, Comida.class);

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
