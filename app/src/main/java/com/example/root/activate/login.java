package com.example.root.activate;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.regex.Pattern;

public class login extends AppCompatActivity {

    MaterialBetterSpinner genero, coordinacion, rol;

    String[] GENERO = {"Masculino", "Femenino"};
    String[] COORDINACION = {"Astrofísica", "Óptica", "Ciencias Computacionales",
            "Ciencias y Tecnologías del Espacio", "Ciencias y Tecnologías Biomédicas",
            "Enseñanza de Ciencias Exactas", "Ciencias y Tecnologías de Seguridad"};
    String[] ROL = {"Estudiante", "Investigador", "Administración", "Otros"};

    Button enviar;

    TextInputLayout Nick, Edad;

    String TAG = "PhoneActivityTAG";
    Activity activity = login.this;
    String wantPermission = Manifest.permission.READ_PHONE_STATE;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Registro");
        setSupportActionBar(toolbar);

        //String android_id = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);

       /* if (!checkPermission(wantPermission)) {
            requestPermission(wantPermission);
        } else {
            Log.d(TAG, "Phone number: " + getPhone());
        }*/
       // System.out.print("NUMERO DE TELEFONO ANDROID: " + getPhone()); //obtiene el numero del telefono

        genero = (MaterialBetterSpinner) findViewById(R.id.Genero);
        coordinacion = (MaterialBetterSpinner) findViewById(R.id.Coordinacion);
        rol = (MaterialBetterSpinner) findViewById(R.id.Rol);

        Nick = (TextInputLayout) findViewById(R.id.NickName);
        Edad = (TextInputLayout) findViewById(R.id.Edad);

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
                String nombre = Nick.getEditText().getText().toString();
                String edad = Edad.getEditText().getText().toString();

                String gen = genero.getText().toString();
                String coor = coordinacion.getText().toString();
                String ro = rol.getText().toString();
                boolean N = esNombreValido(nombre);
                boolean E = esEdadValido(edad);

                boolean G = validarSpinner(gen, genero);
                boolean C = validarSpinner(coor, coordinacion);
                boolean R = validarSpinner(ro, rol);
                String id="0123456789";
                if (N && E && G && C && R) {
                    Intent intent = new Intent(login.this, Encuesta.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("Nickname",nombre);
                    intent.putExtra("Edad",edad);
                    intent.putExtra("Genero",gen);
                    intent.putExtra("Coordinacion",coor);
                    intent.putExtra("Rol",ro);
                    startActivity(intent);
                    finish();

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

    private boolean validarSpinner(String dato, MaterialBetterSpinner Comp) {
        if (dato.isEmpty()) {
            Comp.setError("Seleccione una opción");
            return false;
        } else {
            Comp.setError(null);
        }

        return true;
    }

   /* private String getPhone() {
        TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, wantPermission) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return phoneMgr.getLine1Number();
    }

    private void requestPermission(String permission){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)){
            Toast.makeText(activity, "Phone state permission allows us to get phone number. Please allow it for additional functionality.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(activity, new String[]{permission},PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Phone number: " + getPhone());
                } else {
                    Toast.makeText(activity,"Permission Denied. We can't get phone number.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private boolean checkPermission(String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(activity, permission);
            if (result == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }*/


}