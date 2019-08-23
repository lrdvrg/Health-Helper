package com.santodomingostudios.healthhelper;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ActividadActivity extends AppCompatActivity implements SensorEventListener {
    TextView tv_pasos, tv_km, tv_kal, tv_info;
    Button btn_activar, btn_map;
    SensorManager sm;
    boolean activo, iniciarActividad;
    private static DecimalFormat dcmf;
    private int pasosInicio = 0;
    private int pasosTotal;
    private double kmTotal, kalTotal;
    SQLiteDatabase db;

    private Cronometro cronom;

    private int perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad);

        tv_pasos = (TextView) findViewById(R.id.textView_steps);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        tv_km = (TextView) findViewById(R.id.textView_km);
        tv_kal = (TextView) findViewById(R.id.textView_kal);
        tv_info = (TextView) findViewById(R.id.textView_info);
        dcmf = new DecimalFormat(".###");
        activo = false;
        iniciarActividad = false;

        BaseDatos base = new BaseDatos(this, "usuario", null, 1);
        db = base.getWritableDatabase();

//        db.execSQL("drop table if exists actividades");
//        db.execSQL("create table actividades(tiempo_total int, calorias_total decimal(10,2), distancia_total decimal(10,2), cant_pasos int, fecha date)");


        //el perfil
        perfil = getIntent().getIntExtra("tipo", 0);

        if (perfil == 2){
            tv_info.setText("Velocidad");

        }

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        btn_activar = (Button) findViewById(R.id.btn_iniciar);
        btn_activar.setBackgroundColor(Color.GREEN);
        btn_activar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cambia los colores y el texto
                if (btn_activar.getText().toString() == "Parar"){
                    btn_activar.setText("Iniciar");
                    btn_activar.setBackgroundColor(Color.GREEN);


                    db.execSQL("insert into actividades(tiempo_total, calorias_total, distancia_total, cant_pasos , fecha) values("+cronom.segundosPasados()+","+kalTotal+","+kmTotal+","+pasosTotal+", date('now'))");
                    Toast.makeText(ActividadActivity.this, "Caminata Registrada", Toast.LENGTH_SHORT).show();
                    volverMain();

                }
                else{
                    btn_activar.setText("Parar");
                    btn_activar.setBackgroundColor(Color.RED);
                    iniciarActividad = true;
                    cronom = new Cronometro();

                }

            }
        });
        btn_map = (Button) findViewById(R.id.btn_ver_ruta);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itn = new Intent(ActividadActivity.this, MapsActivity.class);
                startActivity(itn);
            }
        });
    }

    public void volverMain(){
        Intent itn = new Intent(ActividadActivity.this, MainActivity.class);
        startActivity(itn);
        this.finish();
    }

    @Override
    public void onResume(){
        super.onResume();
        activo = true;
        Sensor sensorCuentaPasos = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if( sensorCuentaPasos != null){
            sm.registerListener(this, sensorCuentaPasos, sm.SENSOR_DELAY_UI);
        }
        else {
            Toast.makeText(this,"UPS sensor no encontrado", Toast.LENGTH_SHORT);
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (iniciarActividad){
            //se obtiene la cantidad de pasos dados antes de iniciar el activity
            if (pasosInicio < 1){
                pasosInicio = (int)event.values[0];
            }
            if(activo){
                pasosTotal = (int) event.values[0] - pasosInicio;
                tv_pasos.setText(String.valueOf(pasosTotal));
                //perfil caminata
                if (perfil == 0) {
                    //segun una investigacion de la journal Medicine and Science in Sports and Exercise
                    //publicada en el 2017 una persona quema el doble de calorias al correr
                    kmTotal = Double.parseDouble(dcmf.format(pasosTotal * (0.0008)));
                    kalTotal = Double.parseDouble(dcmf.format(pasosTotal * (0.05*2)));
                }
                //perfil corredor
                else if (perfil == 1){
                    kmTotal = Double.parseDouble(dcmf.format(pasosTotal * 0.0008));
                    kalTotal = Double.parseDouble(dcmf.format(pasosTotal * 0.05));
                }
                //perfil bicicleta
                else{
                    kmTotal = Double.parseDouble(dcmf.format(pasosTotal * 0.0008));
                    kalTotal = Double.parseDouble(dcmf.format(pasosTotal * 0.05));
                }


                tv_km.setText(String.valueOf(kmTotal + " KM"));
                tv_kal.setText(String.valueOf(kalTotal + " Kcal"));
            }
        }

    }


    private void ingresarInfo(){

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
