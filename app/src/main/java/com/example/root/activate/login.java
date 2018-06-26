package com.example.root.activate;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.UUID;
import java.util.regex.Pattern;

public class login extends AppCompatActivity {

    /*Para el servidor*/
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String USERS_REF="Usuarios";
    public static final String NICKNAME_KEY="nickname";
    static final String TAG="consulta";

    MaterialBetterSpinner genero, coordinacion, rol;

    String[] GENERO = {"Masculino", "Femenino"};
    String[] COORDINACION = {"Astrofísica", "Ciencias Computacionales","Ciencias y Tecnologías Biomédicas",
            "Ciencias y Tecnologías del Espacio","Ciencias y Tecnologías de Seguridad",
            "Enseñanza de Ciencias Exactas", "Electrónica","Óptica", "No aplica"};
    String[] ROL = {"Administrativo","Estudiante", "Investigador", "Residente (Servicio Social, Practicante, Visitante)"};

    Button enviar;

    TextInputLayout Nick, Edad,Altura;

    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Registro");
        setSupportActionBar(toolbar);

        genero = (MaterialBetterSpinner) findViewById(R.id.Genero);
        coordinacion = (MaterialBetterSpinner) findViewById(R.id.Coordinacion);
        rol = (MaterialBetterSpinner) findViewById(R.id.Rol);

        Nick = (TextInputLayout) findViewById(R.id.NickName);
        Edad = (TextInputLayout) findViewById(R.id.Edad);
        Altura=(TextInputLayout) findViewById(R.id.Altura);

        enviar = (Button) findViewById(R.id.button);

        ArrayAdapter<String> adapter_Genero = new ArrayAdapter<String>(login.this, android.R.layout.simple_dropdown_item_1line, GENERO);
        ArrayAdapter<String> adapter_Coordinacion = new ArrayAdapter<String>(login.this, android.R.layout.simple_dropdown_item_1line, COORDINACION);
        ArrayAdapter<String> adapter_Rol = new ArrayAdapter<String>(login.this, android.R.layout.simple_dropdown_item_1line, ROL);

        genero.setAdapter(adapter_Genero);
        coordinacion.setAdapter(adapter_Coordinacion);
        rol.setAdapter(adapter_Rol);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombre = Nick.getEditText().getText().toString();
                final String edad = Edad.getEditText().getText().toString();
                final String altura=Altura.getEditText().getText().toString();

                final String gen = genero.getText().toString();
                final String coor = coordinacion.getText().toString();
                final String ro = rol.getText().toString();
                boolean N = esNombreValido(nombre);
                boolean E = esEdadValido(edad);
                boolean A = esAlturaValido(altura);

                boolean G = validarSpinner(gen, genero);
                boolean C = validarSpinner(coor, coordinacion);
                boolean R = validarSpinner(ro, rol);


                final String deviceID = id(login.this);
                Log.d("ID1:",deviceID);
                String deviceID2 = id(login.this);
                Log.d("ID1:",deviceID2);



              if (N && E && G && C && R && A) {

                  db.collection(USERS_REF).document(nombre).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                      @Override
                      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                          if (task.isSuccessful()) {
                              DocumentSnapshot document = task.getResult();
                              if (document.exists()) {
                                  Nick.setError("Escribe otro NickName. "+nombre+" se encuentra en uso.");
                                  Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                              } else {
                                  Log.d(TAG, "No such document");
                                  Intent intent = new Intent(login.this, Encuesta.class);
                                  intent.putExtra("Id", deviceID);
                                  intent.putExtra("Nickname", nombre);
                                  intent.putExtra("Edad", edad);
                                  intent.putExtra("Alt", altura);
                                  intent.putExtra("Genero", gen);
                                  intent.putExtra("Coordinacion", coor);
                                  intent.putExtra("Rol", ro);
                                  startActivity(intent);
                                  finish();
                                  Log.d(TAG, "no esta ese datp");
                              }
                          } else {
                              Log.d(TAG, "get failed with ", task.getException());
                          }
                      }
                  });

                }
            }
        });


    }


    private boolean esNombreValido(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z0-9]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 50) {
            Nick.setError("NickName inválido");
            return false;
        } else {
            Nick.setError(null);
        }

        return true;
    }


    private boolean esEdadValido(String edad) {
        if (edad.isEmpty()) {
            Edad.setError("Edad inválido");
            return false;
        } else {
            Edad.setError(null);
        }

        return true;
    }
    private boolean esAlturaValido(String edad) {
        if (edad.isEmpty()) {
            Altura.setError("Altura inválida");
            return false;
        } else {
            Altura.setError(null);
        }

        return true;
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

   public synchronized static String id(Context context) {
       if (uniqueID == null) {
           SharedPreferences sharedPrefs = context.getSharedPreferences(PREF_UNIQUE_ID, Context.MODE_PRIVATE);
           uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
           if (uniqueID == null) {
               uniqueID = UUID.randomUUID().toString();
               SharedPreferences.Editor editor = sharedPrefs.edit();
               editor.putString(PREF_UNIQUE_ID, uniqueID);
               editor.commit();
           }
       }
       return uniqueID;
   }

}