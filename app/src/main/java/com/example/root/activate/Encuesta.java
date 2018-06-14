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

import com.google.firebase.firestore.FirebaseFirestore;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.HashMap;
import java.util.Map;

public class Encuesta extends AppCompatActivity {
    /*Para el servidor*/
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String NICKNAME_KEY="nickname";
    public static final String ID_USUARIO_KEY="id_usuario";
    public static final String COORDINACION_KEY="id_coordinacion";
    public static final String LAST_UPDATE_KEY="Last_Update";
    public static final String ROL_KEY="id_rol";
    public static final String SEXO_KEY="Sexo";
    public static final String EDAD_KEY="Edad";
    public static final String USERS_REF="Usuarios";

    public static final String ENCUESTA_REF="Encuesta";
    public static final String ALIMENTACION_KEY="Alimentacion";
    public static final String PESO_KEY="Peso";
    public static final String ALTURA_KEY="Altura";
    public static final String EJERCICIO_KEY="Ejercicio";
    public static final String FUMAR_KEY="Fumar";
    public static final String ALCOHOL_KEY="Alcohol";
    public static final String HORAS_KEY="Horas";

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

                    /*para el servidor*/
                    Map<String,Object> dataOfUser = new HashMap<String, Object>();
                    dataOfUser.put(NICKNAME_KEY,NickName);
                    dataOfUser.put(ID_USUARIO_KEY,id_user);
                    dataOfUser.put(COORDINACION_KEY,Coordinacion);
                    dataOfUser.put(ROL_KEY,Rol);
                    dataOfUser.put(SEXO_KEY,Genero);
                    dataOfUser.put(EDAD_KEY,Edad);
                    //esto lo estoy probando como opcion
                    long d = System.currentTimeMillis()/1000;
                    dataOfUser.put(LAST_UPDATE_KEY,d);

                    db.collection(USERS_REF).document(id_user).set(dataOfUser);

                    Map<String,Object> encuestaToSend = new HashMap<String, Object>();
                    encuestaToSend.put(ALIMENTACION_KEY,comida);
                    encuestaToSend.put(PESO_KEY,peso);
                    encuestaToSend.put(ALTURA_KEY,altura);
                    encuestaToSend.put(EJERCICIO_KEY,deport);
                    encuestaToSend.put(FUMAR_KEY,fum);
                    encuestaToSend.put(ALCOHOL_KEY,tom);
                    encuestaToSend.put(HORAS_KEY,estanc);

                    db.collection(USERS_REF).document(id_user).collection(ENCUESTA_REF).document("1").set(encuestaToSend);

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
