package com.example.root.activate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
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

    MaterialBetterSpinner alimentacion,ejercicio,fumar,tomar,estancia,CPESO;

    String[] ALIM={"Malos","Regulares","Buenos","Excelentes"};
    String[] EJER={"Menos de 1 Hora","De 1 a 5 Horas","De 6 a 10 Horas","Más de 10 Horas"};
    String[] FU ={"Nunca","Ocasionalmente", "Regularmente"};
    String[] TO ={"Nunca","Ocasionalmente", "Regularmente"};
    String[] ESTAN={"Menos de 4 Horas","De 4 a 6 Horas","De 7 a 9 Horas","Más de 9 Horas"};
    String[] CP={"Si","No"};

    Button enviar;
    TextInputLayout Peso;

    String Final="";
    int TAM=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String id_user=getIntent().getStringExtra("Id");
        final String NickName=getIntent().getStringExtra("Nickname");
        final String Edad=getIntent().getStringExtra("Edad");
        final String Altura=getIntent().getStringExtra("Alt");
        final String Genero=getIntent().getStringExtra("Genero");
        final String Coordinacion=getIntent().getStringExtra("Coordinacion");
        final String Rol=getIntent().getStringExtra("Rol");

        alimentacion=(MaterialBetterSpinner)findViewById(R.id.Alimentacion);
        ejercicio=(MaterialBetterSpinner)findViewById(R.id.Ejercicio);
        fumar=(MaterialBetterSpinner)findViewById(R.id.Fumar);
        tomar=(MaterialBetterSpinner)findViewById(R.id.Alcohol);
        estancia=(MaterialBetterSpinner)findViewById(R.id.Tiempo);
        CPESO=(MaterialBetterSpinner)findViewById(R.id.CPeso);

        enviar=(Button)findViewById(R.id.button1);

        Peso=(TextInputLayout)findViewById(R.id.Peso);

        ArrayAdapter<String> adapter_Alimentacion = new ArrayAdapter<String>(Encuesta.this, android.R.layout.simple_dropdown_item_1line, ALIM);
        ArrayAdapter<String> adapter_Ejercicio = new ArrayAdapter<String>(Encuesta.this, android.R.layout.simple_dropdown_item_1line, EJER);
        ArrayAdapter<String> adapter_Fumar = new ArrayAdapter<String>(Encuesta.this, android.R.layout.simple_dropdown_item_1line, FU);
        ArrayAdapter<String> adapter_Tomar = new ArrayAdapter<String>(Encuesta.this, android.R.layout.simple_dropdown_item_1line, TO);
        ArrayAdapter<String> adapter_Estancia = new ArrayAdapter<String>(Encuesta.this, android.R.layout.simple_dropdown_item_1line, ESTAN);
        ArrayAdapter<String> adapter_CPESO = new ArrayAdapter<String>(Encuesta.this, android.R.layout.simple_dropdown_item_1line, CP);

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

                String peso=Peso.getEditText().getText().toString();


                boolean A=validarSpinner(comida,alimentacion);
                boolean B=validarSpinner(deport,ejercicio);
                boolean C=validarSpinner(fum,fumar);
                boolean D=validarSpinner(tom,tomar);
                boolean E=validarSpinner(estanc,estancia);
                boolean F=ValidarPeso(peso);


                if(A && B && C && D && E && F ) {
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
                    encuestaToSend.put(ALTURA_KEY,Altura);
                    encuestaToSend.put(EJERCICIO_KEY,deport);
                    encuestaToSend.put(FUMAR_KEY,fum);
                    encuestaToSend.put(ALCOHOL_KEY,tom);
                    encuestaToSend.put(HORAS_KEY,estanc);

                    db.collection(USERS_REF).document(id_user).collection(ENCUESTA_REF).document("1").set(encuestaToSend);

                    //para despues pasar a la siguiente pantalla
                    Intent intent = new Intent(Encuesta.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("bandera", true);
                    editor.commit();

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