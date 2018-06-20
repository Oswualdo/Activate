package com.example.root.activate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;


public class splash extends AppCompatActivity {
    TextView iniciando;
    private static final int duracion= 500;
    private static final int terminar=300;
    private static final int DURACION_SPLASH=5000;

    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent= new Intent(splash.this,Comida.class);
               // startActivity(intent);
               // finish();
                switch(getFirstTimeRun(splash.this)) {
                    case 0:
                        //Es la primera vez
                        //Declaramos bandera
                        prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("bandera", false);
                        editor.commit();

                        Intent intent = new Intent(splash.this, Consentimiento.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        //Ya has iniciado la app alguna vez

                        prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
                        Boolean bandActivity = prefs.getBoolean("bandera", false);
                        if (!bandActivity){
                            Intent main = new Intent(splash.this, Consentimiento.class);
                            startActivity(main);
                            finish();
                            break;
                        }else{
                            Intent main = new Intent(splash.this, MainActivity.class);
                            startActivity(main);
                            finish();
                            break;
                        }


                    case 2:
                        //Es una versión nueva (actualizacion de la app)
                        //
                        break;
                }

            };
        },DURACION_SPLASH);

        iniciando=findViewById(R.id.textView);
        final AlphaAnimation fadeIn =new AlphaAnimation(0.0f,1.0f);
        fadeIn.setDuration(duracion);
        fadeIn.setStartOffset(terminar);
        fadeIn.setFillAfter(true);

        final AlphaAnimation fadeOut=new AlphaAnimation(1.0f,0.0f);
        fadeOut.setDuration(duracion);
        fadeOut.setStartOffset(terminar);
        fadeOut.setFillAfter(true);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iniciando.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        iniciando.startAnimation(fadeIn);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iniciando.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //Retorna: 0 primera vez / 1 no es primera vez / 2 nueva versión
    public static int getFirstTimeRun(Context contexto) {
        SharedPreferences sp = contexto.getSharedPreferences("GetApp", 0);
        int result, currentVersionCode = BuildConfig.VERSION_CODE;
        int lastVersionCode = sp.getInt("FIRSTTIMERUN", -1);
        if (lastVersionCode == -1) result = 0; else
            result = (lastVersionCode == currentVersionCode) ? 1 : 2;
        sp.edit().putInt("FIRSTTIMERUN", currentVersionCode).apply();
        return result;
    }


}
