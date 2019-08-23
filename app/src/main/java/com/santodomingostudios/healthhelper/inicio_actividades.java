package com.santodomingostudios.healthhelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

public class inicio_actividades extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_actividades);

        TextView tvAcaminata = findViewById(R.id.tvActividadCaminata);
        TextView tvAciclismo = findViewById(R.id.tvActividadCiclismo);
        TextView tvAcorrer = findViewById(R.id.tvActividadCorrer);
        TextView tvAmensaje = findViewById(R.id.tvNombre);

        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/MontserratAlternates-Regular.ttf");
        Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/MontserratAlternates-Bold.ttf");

        tvAmensaje.setTypeface(face2);
        tvAcaminata.setTypeface(face);
        tvAciclismo.setTypeface(face);
        tvAcorrer.setTypeface(face);


    }

    public void empezarCaminata(View view){
        Intent itnCaminata = new Intent(inicio_actividades.this, MapsActivity.class);
        itnCaminata.putExtra("tipo",0 );
        startActivity(itnCaminata);
    }
    public void empezarCorrer(View view){
        Intent itnCaminata = new Intent(inicio_actividades.this, MapsActivity.class);
        itnCaminata.putExtra("tipo", 1);
        startActivity(itnCaminata);
    }
    public void empezarCiclismo(View view){
        Intent itnCaminata = new Intent(inicio_actividades.this, MapsActivity.class);
        itnCaminata.putExtra("tipo", 2);
        startActivity(itnCaminata);
    }
}
