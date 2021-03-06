package com.example.root.activate;
import android.annotation.SuppressLint;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import java.util.ArrayList;

import static com.example.root.activate.R.id.drawer_layout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener {
    private static final String TAG = "Pedometer";
    private SharedPreferences mSettings;
    private PedometerSettings mPedometerSettings;
    private Utils mUtils;

    private TextView mStepValueView;
    private TextView mPaceValueView;
    private TextView mDistanceValueView;
    private TextView mSpeedValueView;
    private TextView mCaloriesValueView;
    TextView mDesiredPaceView;
    private int mStepValue;
    public static float pasos;  //mio
    private int mPaceValue;
    private float mDistanceValue;
    public static float dis;  //mio
    private float mSpeedValue;
    private int mCaloriesValue;
    private float mDesiredPaceOrSpeed;
    private int mMaintain;
    private boolean mIsMetric;
    private float mMaintainInc;
    private boolean mQuitting = false; // Set when user selected Quit from menu, can be used by onPause, onStop, onDestroy

    private LineChart mChart;
    public ArrayList<String> lista = new ArrayList<String>();

    /**
     * True, when service is running.
     */
    private boolean mIsRunning;

    private SensorManager sensorManager;
    private TextView count,nombre;
    boolean activityRunning;
    ImageView imagen_rota;
    SharedPreferences prefs;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Kmina\u2295e");
        setSupportActionBar(toolbar);
        mChart = (LineChart) findViewById(R.id.linechart);

        prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        String IDNombre = prefs.getString("Nick","Usuario");
        nombre=(TextView)findViewById(R.id.textView9);
        //nombre.setText(MessageFormat.format("Bienvenido: {0}", IDNombre));
        nombre.setText(IDNombre);


        String archivos [] = fileList();

        if(ArchivoExiste(archivos, "bitacora.txt")){
            try {
                InputStreamReader archivo = new InputStreamReader(openFileInput("bitacora.txt"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String bitacoraCompleta = "";
                lista.clear();
                while (linea != null) {
                    lista.add(linea);
                    bitacoraCompleta = bitacoraCompleta + linea + "\n";
                    linea = br.readLine();

                }
                br.close();
                archivo.close();
                //et1.setText(bitacoraCompleta);
                //Toast.makeText(this, "puto", Toast.LENGTH_SHORT).show();

                }catch (Exception ex){ //IOException e
                  Log.e("Ficheros", "Error al escribir fichero a memoria interna");
                }}else {
                    try {
                       // Toast.makeText(this, "puto", Toast.LENGTH_SHORT).show();
                        OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("bitacora.txt", Context.MODE_PRIVATE));
                        archivo.write("0" +  "\n");
                    }catch (Exception ex){}
                    }

        setData();
        mChart.setDescription("Número de pasos hora");
        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        mChart.invalidate();


        imagen_rota=(ImageView)findViewById(R.id.Rota);
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(imagen_rota, "rotation", 0f, 360f);
        rotateAnimator.setRepeatCount(Animation.INFINITE);
        rotateAnimator.setDuration(1000);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.start();


        //Selecciona lugar donde se mostraran los datos
        //count = (TextView) findViewById(R.id.txtView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);



        //##############################################################################################
        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        mStepValue = 0;
        mPaceValue = 0;



        mUtils = Utils.getInstance();
    }

    /*private void displayDesiredPaceOrSpeed() {
        if (mMaintain == PedometerSettings.M_PACE) {
            mDesiredPaceView.setText("" + (int)mDesiredPaceOrSpeed);
        }
        else {
            mDesiredPaceView.setText("" + mDesiredPaceOrSpeed);
        }
    }
*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Sharing App");
            i.putExtra(Intent.EXTRA_TEXT, "https://ccc.inaoep.mx/~kaminaoemas/");
            startActivity(Intent.createChooser(i, "Share URL"));

            startActivity(Intent.createChooser(i, getString(R.string.compartir)));


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //###################################################333
    @Override
    protected void onResume() {
        Log.i(TAG, "[ACTIVITY] onResume");
        super.onResume();

        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        mPedometerSettings = new PedometerSettings(mSettings);

        mUtils.setSpeak(mSettings.getBoolean("speak", false));

        // Read from preferences if the service was running on the last onPause
        mIsRunning = mPedometerSettings.isServiceRunning();

        // Start the service if this is considered to be an application start (last onPause was long ago)
        if (!mIsRunning && mPedometerSettings.isNewStart()) {
            startStepService();
            bindStepService();
        }
        else if (mIsRunning) {
            bindStepService();
        }

        mPedometerSettings.clearServiceRunning();

        mStepValueView     = (TextView) findViewById(R.id.step_value);
        mDistanceValueView = (TextView) findViewById(R.id.distance_value);
        //mDesiredPaceView   = (TextView) findViewById(R.id.desired_pace_value);

        mIsMetric = mPedometerSettings.isMetric();




        if (mMaintain == PedometerSettings.M_PACE) {
            mMaintainInc = 5f;
            mDesiredPaceOrSpeed = (float)mPedometerSettings.getDesiredPace();
        }
        else
        if (mMaintain == PedometerSettings.M_SPEED) {
            mDesiredPaceOrSpeed = mPedometerSettings.getDesiredSpeed();
            mMaintainInc = 0.1f;
        }



//        displayDesiredPaceOrSpeed();


        String archivos [] = fileList();

        if(ArchivoExiste(archivos, "bitacora.txt")){
            try {
                InputStreamReader archivo = new InputStreamReader(openFileInput("bitacora.txt"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String bitacoraCompleta = "";
                lista.clear();
                while (linea != null) {
                    lista.add(linea);
                    bitacoraCompleta = bitacoraCompleta + linea + "\n";
                    linea = br.readLine();

                }
                br.close();
                archivo.close();
                //et1.setText(bitacoraCompleta);
                //Toast.makeText(this, "puto", Toast.LENGTH_SHORT).show();

            }catch (Exception ex){ //IOException e
                Log.e("Ficheros", "Error al escribir fichero a memoria interna");
            }}else {
            try {
                // Toast.makeText(this, "puto", Toast.LENGTH_SHORT).show();
                OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("bitacora.txt", Context.MODE_PRIVATE));
            }catch (Exception ex){}
        }

        setData();
        mChart.setDescription("Número de pasos cada hora");
        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        mChart.invalidate();




    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
        // En caso de que la pausen, se supone esto no va a pasar
//        sensorManager.unregisterListener(this);
        Log.i(TAG, "[ACTIVITY] onPause");
        if (mIsRunning) {
            unbindStepService();
        }
        if (mQuitting) {
            mPedometerSettings.saveServiceRunningWithNullTimestamp(mIsRunning);
        }
        else {
            mPedometerSettings.saveServiceRunningWithTimestamp(mIsRunning);
        }

        super.onPause();
        savePaceSetting();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Se actualiza el valor del contador
        if (activityRunning) {
            count.setText(String.valueOf(event.values[0]));
        }

    }
    @Override
    protected void onStop() {
        Log.i(TAG, "[ACTIVITY] onStop");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "[ACTIVITY] onStart");
        super.onStart();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void setDesiredPaceOrSpeed(float desiredPaceOrSpeed) {
        if (mService != null) {
            if (mMaintain == PedometerSettings.M_PACE) {
                mService.setDesiredPace((int)desiredPaceOrSpeed);
            }
            else
            if (mMaintain == PedometerSettings.M_SPEED) {
                mService.setDesiredSpeed(desiredPaceOrSpeed);
            }
        }
    }

    private void savePaceSetting() {
        mPedometerSettings.savePaceOrSpeedSetting(mMaintain, mDesiredPaceOrSpeed);
    }

    private StepService mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = ((StepService.StepBinder)service).getService();

            mService.registerCallback(mCallback);
            mService.reloadSettings();

        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
        }
    };

    private void startStepService() {
        if (! mIsRunning) {
            Log.i(TAG, "[SERVICE] Start");
            mIsRunning = true;
            startService(new Intent(MainActivity.this,
                    StepService.class));
        }
    }

    private void bindStepService() {
        Log.i(TAG, "[SERVICE] Bind");
        bindService(new Intent(MainActivity.this,
                StepService.class), mConnection, Context.BIND_AUTO_CREATE + Context.BIND_DEBUG_UNBIND);
    }

    private void unbindStepService() {
        Log.i(TAG, "[SERVICE] Unbind");
        unbindService(mConnection);
    }

    private void stopStepService() {
        Log.i(TAG, "[SERVICE] Stop");
        if (mService != null) {
            Log.i(TAG, "[SERVICE] stopService");
            stopService(new Intent(MainActivity.this,
                    StepService.class));
        }
        mIsRunning = false;
    }

    private void resetValues(boolean updateDisplay) {
        if (mService != null && mIsRunning) {
            mService.resetValues();
        }
        else {
            mStepValueView.setText("0");
            mPaceValueView.setText("0");
            mDistanceValueView.setText("0");
            mSpeedValueView.setText("0");
            mCaloriesValueView.setText("0");
            SharedPreferences state = getSharedPreferences("state", 0);
            SharedPreferences.Editor stateEditor = state.edit();
            if (updateDisplay) {
                stateEditor.putInt("steps", 0);
                stateEditor.putInt("pace", 0);
                stateEditor.putFloat("distance", 0);
                stateEditor.putFloat("speed", 0);
                stateEditor.putFloat("calories", 0);
                stateEditor.commit();

                try {
                    // Toast.makeText(this, "puto", Toast.LENGTH_SHORT).show();
                    OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("bitacora.txt", Context.MODE_PRIVATE));
                    archivo.write("0" +  "\n");
                }catch (Exception ex){}
            }
        }
    }

    private static final int MENU_SETTINGS = 8;
    private static final int MENU_QUIT     = 9;

    private static final int MENU_PAUSE = 1;
    private static final int MENU_RESUME = 2;
    private static final int MENU_RESET = 3;

    /* Creates the menu items */
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (mIsRunning) {
            menu.add(0, MENU_PAUSE, 0, "Pausar")
                    .setIcon(android.R.drawable.ic_media_pause)
                    .setShortcut('1', 'p');
        }
        else {
            menu.add(0, MENU_RESUME, 0, "Reanudar")
                    .setIcon(android.R.drawable.ic_media_play)
                    .setShortcut('1', 'p');
        }
        menu.add(0, MENU_RESET, 0, "Reiniciar")
                .setIcon(android.R.drawable.ic_menu_close_clear_cancel)
                .setShortcut('2', 'r');
        menu.add(0, MENU_QUIT, 0, "Salir")
                .setIcon(android.R.drawable.ic_lock_power_off)
                .setShortcut('9', 'q');
        return true;
    }

    /* Handles item selections */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_PAUSE:
                unbindStepService();
                //stopStepService();
                return true;
            case MENU_RESUME:
                startStepService();
                bindStepService();
                return true;
            case MENU_RESET:
                resetValues(true);
                return true;
            case MENU_QUIT:
                resetValues(false);
                unbindStepService();
                //stopStepService();
                mQuitting = true;
                finish();
                return true;
        }
        return false;
    }

    // TODO: unite all into 1 type of message
    private StepService.ICallback mCallback = new StepService.ICallback() {
        public void stepsChanged(int value) {
            mHandler.sendMessage(mHandler.obtainMessage(STEPS_MSG, value, 0));
        }
        public void paceChanged(int value) {
            mHandler.sendMessage(mHandler.obtainMessage(PACE_MSG, value, 0));
        }
        public void distanceChanged(float value) {
            mHandler.sendMessage(mHandler.obtainMessage(DISTANCE_MSG, (int)(value*1000), 0));
        }
        public void speedChanged(float value) {
            mHandler.sendMessage(mHandler.obtainMessage(SPEED_MSG, (int)(value*1000), 0));
        }
        public void caloriesChanged(float value) {
            mHandler.sendMessage(mHandler.obtainMessage(CALORIES_MSG, (int)(value), 0));
        }
    };

    private static final int STEPS_MSG = 1;
    private static final int PACE_MSG = 2;
    private static final int DISTANCE_MSG = 3;
    private static final int SPEED_MSG = 4;
    private static final int CALORIES_MSG = 5;

    private Handler mHandler = new Handler() {
        @Override public void handleMessage(Message msg) {
            switch (msg.what) {
                case STEPS_MSG:
                    mStepValue = (int)msg.arg1;
                    mStepValueView.setText("" + mStepValue);
                    pasos = mStepValue;//mio
                    break;

                case DISTANCE_MSG:
                    mDistanceValue = ((int)msg.arg1)/1000f;
                    //mDistanceValueView.setText(""+mStepValue);
                    if (mDistanceValue <= 0) {
                        mDistanceValueView.setText("0");
                    }
                    else {
                        mDistanceValueView.setText(
                                ("" + (mDistanceValue + 0.000001f)).substring(0, 5)
                        );
                        dis = mDistanceValue;//mio
                    }
                    break;


                default:
                    super.handleMessage(msg);
            }
        }

    };
    protected void onDestroy() {
        Log.i(TAG, "[ACTIVITY] onDestroy");
        super.onDestroy();




    }

    @SuppressLint("MissingSuperCall")
    protected void onRestart() {

        Log.i(TAG, "[ACTIVITY] onRestart");
        super.onRestart();
        //super.onDestroy();
    }

    private ArrayList<String> setXAxisValues(){
        ArrayList<String> xVals = new ArrayList<String>();


        for(int q=0; q<lista.size();q++){
            xVals.add(lista.get(q));
        }
       /* xVals.add("10");
        xVals.add("20");
        xVals.add("30");
        xVals.add("30.5");
        xVals.add("40");
        xVals.add("50");
*/

        return xVals;
    }

    private ArrayList<Entry> setYAxisValues(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for(int q=0; q<lista.size();q++){
            yVals.add(new Entry(Integer.parseInt(lista.get(q)), q));
        }

        /*yVals.add(new Entry(60, 0));
        yVals.add(new Entry(48, 1));
        yVals.add(new Entry(70.5f, 2));
        yVals.add(new Entry(100, 3));
        yVals.add(new Entry(180.9f, 4));
        yVals.add(new Entry(190.9f, 5));
*/
        return yVals;
    }

    private void setData() {
        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals = setYAxisValues();

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "Pasos");

        set1.setFillAlpha(110);
        //set1.setFillColor(Color.RED);

        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // agregamos los datos

        LineData data = new LineData(xVals, dataSets);


        mChart.setData(data);

    }



    private boolean ArchivoExiste(String archivos [], String NombreArchivo){
        for(int i = 0; i < archivos.length; i++)
            if(NombreArchivo.equals(archivos[i]))
                return true;
        return false;
    }

    //Método para el botón Guardar
    public void Guardar(View view){

        try {

            String valor =  mStepValueView.getText().toString() ;

            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("bitacora.txt", Activity.MODE_APPEND));
            archivo.write(valor +  "\n");

            archivo.flush();
            archivo.close();
        }catch (Exception ex){ //IOException e
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }
        //Toast.makeText(this, "Bitacora guardada correctamente", Toast.LENGTH_SHORT).show();

        String archivos [] = fileList();

        if(ArchivoExiste(archivos, "bitacora.txt")){
            try {
                InputStreamReader archivo = new InputStreamReader(openFileInput("bitacora.txt"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String bitacoraCompleta = "";
                lista.clear();
                while(linea != null){
                    lista.add(linea);
                    bitacoraCompleta = bitacoraCompleta + linea + "\n";
                    linea = br.readLine();

                    // AAAA.add(linea);
                    //linea2 = Integer.parseInt(br.readLine());;
                }
                br.close();
                archivo.close();
                //et1.setText(bitacoraCompleta);
                //Toast.makeText(this, "puto", Toast.LENGTH_SHORT).show();

            }catch (Exception ex){ //IOException e
                Log.e("Ficheros", "Error al escribir fichero a memoria interna");
            }
        }
        setData();
        mChart.setDescription("Distancia recorrida");
        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        mChart.invalidate();
    }


}


