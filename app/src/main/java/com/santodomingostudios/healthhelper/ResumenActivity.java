package com.santodomingostudios.healthhelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResumenActivity extends AppCompatActivity {
    TextView tv_tiempo, tv_calorias, tv_distancia, tv_pasos;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);


        tv_tiempo = (TextView) findViewById(R.id.tv_tiempo_total);
        tv_calorias = (TextView) findViewById(R.id.tv_calorias_quemadas);
        tv_distancia = (TextView) findViewById(R.id.tv_distancia);
        tv_pasos = (TextView) findViewById(R.id.tv_cantidad_pasos);


        BaseDatos baseDatos = new BaseDatos(this, "usuario", null, 1);
        db = baseDatos.getWritableDatabase();

        cargarInfo();

    }

    private void cargarInfo(){
       Cursor cTiempo =  db.rawQuery("select sum(tiempo_total) from actividades WHERE fecha >= date('now','-7 day')", null);
       while(cTiempo.moveToNext()){
          float tiempo = Integer.parseInt(cTiempo.getString(0)) / 60;
//           cTiempo.getColumnCount()
            tv_tiempo.setText("El tiempo total caminado es: "+tiempo+" minutos.");
       }
       Cursor cCalorias = db.rawQuery("select sum(calorias_total) from actividades WHERE fecha >= date('now','-7 day')", null);
       while (cCalorias.moveToNext()){
           tv_calorias.setText("Total de calorias perdidas: "+cCalorias.getDouble(0)+" calorias.");
       }
       Cursor cDistancia = db.rawQuery("select sum(distancia_total) from actividades WHERE fecha >= date('now','-7 day')", null);
       while (cDistancia.moveToNext()){
            tv_distancia.setText("Distancia total: "+cDistancia.getDouble(0)+" KM.");
       }
       Cursor cPasos = db.rawQuery("select sum(cant_pasos) from actividades WHERE fecha >= date('now','-7 day')", null);
        while (cPasos.moveToNext()){
            tv_pasos.setText("Cantidad total de pasos: "+cPasos.getInt(0)+" pasos.");
        }
    }
}
