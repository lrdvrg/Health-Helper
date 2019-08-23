package com.santodomingostudios.healthhelper;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MyService extends Service {
    SQLControlador dbconeccion;

    private Timer temporizador = new Timer();
    private static final long INTERVALO_ACTUALIZACION = 10;
    int diaHoy = 0, mesHoy = 0, anoHoy = 0;

    //public static PoiService UPDATE_LISTENER;
    private double cronometro = 0;
    private Handler handler;


    /*public static void setUpdateListener(PoiService poiService) {
        UPDATE_LISTENER = poiService;
    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();
        Calendar c = Calendar.getInstance();
        c.getTime();

        diaHoy = Calendar.DAY_OF_MONTH;
        mesHoy = Calendar.MONTH;
        anoHoy = Calendar.YEAR;

        Cursor cur = dbconeccion.compararFechas(diaHoy, mesHoy, anoHoy);

        while(cur.moveToNext()){
            Log.v("KLKKK","KLKKKK");
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setLargeIcon((((BitmapDrawable)getResources()
                        .getDrawable(R.drawable.ic_launcher)).getBitmap()))
                        .setContentTitle("¡Recordatorio!")
                        .setContentText("Tienes que tomarte una pastilla.")
                        .setContentInfo("4")
                        .setTicker("¡Recordatorio!");
        }

    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}