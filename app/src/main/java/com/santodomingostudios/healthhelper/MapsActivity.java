package com.santodomingostudios.healthhelper;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Camera;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, SensorEventListener {
    final static int PERMISOS_TODOS = 1;
    final static String[] PERMISOS = {android.Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    private GoogleMap mMap;
    MarkerOptions mo;
    Marker marker;
    LocationManager locationManager;
    Polyline polyline;
    ArrayList<LatLng> rutaTomada;
    Button btn_volver;


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
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 23 && !permisoAutorizado()) {
            requestPermissions(PERMISOS, PERMISOS_TODOS);
        } else pedirLocalizacion();
        if (!isLocationEnabled())
            mostrarAlerta(1);
        //aqui se almacena la latitud y longitud en la que ha estado el usuari
        rutaTomada = new ArrayList<LatLng>();

//        btn_volver = (Button) findViewById(R.id.btn_volver);
//        btn_volver.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                (MapsActivity.this).finish();
//            }
//        });

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
            tv_info.setText("Pedaleos");

        }

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        btn_activar = (Button) findViewById(R.id.btn_iniciar);
        btn_activar.setBackgroundColor(Color.GREEN);
        btn_activar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cambia los colores y el texto
                if (btn_activar.getText().toString() == "Parar"){
//                    btn_activar.setText("Iniciar");
//                    btn_activar.setBackgroundColor(Color.GREEN);


                    db.execSQL("insert into actividades(tiempo_total, calorias_total, distancia_total, cant_pasos , fecha) values("+cronom.segundosPasados()+","+kalTotal+","+kmTotal+","+pasosTotal+", date('now'))");
                    Toast.makeText(MapsActivity.this, "Caminata Registrada", Toast.LENGTH_SHORT).show();
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


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng lot = new LatLng(18.489899, -69.881470);
        mo = new MarkerOptions().position(lot).title("Estas Aqui");
        mMap = googleMap;
        marker = mMap.addMarker(mo);
        //colocamos la camara en nuesta localizacion
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lot, 7));
        //bloquea el mapa para que no se mueva
//        mMap.getUiSettings().setAllGesturesEnabled(false);




    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());

        rutaTomada.add(myCoordinates);
        marker.setPosition(myCoordinates);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCoordinates, 17));
        mMap.setMinZoomPreference(18);

        //linea que se muestra la ruta tomada
        if (iniciarActividad){

            polyline = mMap.addPolyline(new PolylineOptions()
                    .add(rutaTomada.toArray(new LatLng[0]))
                    .width(5)
                    .color(Color.RED));
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void pedirLocalizacion() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 300, 1, this);
    }
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private boolean permisoAutorizado() {
        if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED || checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.v("mylog", "Permiso autorizado");
            return true;
        } else {
            Log.v("mylog", "permiso no autorizado");
            return false;
        }
    }
    private void mostrarAlerta(final int status) {
        String message, title, btnText;
        if (status == 1) {
            message = "Sus opciones de localizacion estan apagadas.\nDebe activarlas para poder " +
                    "usar esta app";
            title = "Habilitar localizacion";
            btnText = "Opciones de localizacion";
        } else {
            message = "Permita que esta app accedea a la localizacion!";
            title = "Acceso de permisos";
            btnText = "Permitir";
        }
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle(title)
                .setMessage(message)
                .setPositiveButton(btnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        if (status == 1) {
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(myIntent);
                        } else
                            requestPermissions(PERMISOS, PERMISOS_TODOS);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
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

                    kmTotal = Double.parseDouble(dcmf.format(pasosTotal * (0.0008)));
                    kalTotal = Double.parseDouble(dcmf.format(pasosTotal * (0.05)));
                }
                //perfil corredor
                //segun una investigacion de la journal Medicine and Science in Sports and Exercise
                //publicada en el 2017 una persona quema el doble de calorias al correr
                else if (perfil == 1){
                    kmTotal = Double.parseDouble(dcmf.format(pasosTotal * 0.0008*2));
                    kalTotal = Double.parseDouble(dcmf.format(pasosTotal * 0.05*2));
                }
                //perfil bicicleta
                else{
                    kmTotal = Double.parseDouble(dcmf.format(pasosTotal * 0.0008 * 4));
                    kalTotal = Double.parseDouble(dcmf.format(pasosTotal * 0.05 * 3));
                }


                tv_km.setText(String.valueOf(kmTotal + " KM"));
                tv_kal.setText(String.valueOf(kalTotal + " Kcal"));
            }
        }

    }
    public void volverMain(){
        Intent itn = new Intent(MapsActivity.this, MainActivity.class);
        startActivity(itn);
        this.finish();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
