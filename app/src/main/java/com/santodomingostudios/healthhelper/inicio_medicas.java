package com.santodomingostudios.healthhelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class inicio_medicas extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_medicas);

        TextView tvMmensaje = findViewById(R.id.tvNombreMedicas);
        TextView tvMprescripcion = findViewById(R.id.tvMedicinaPrescripcion);
        TextView tvMcitas = findViewById(R.id.tvCitasMedicas);


        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/MontserratAlternates-Regular.ttf");
        Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/MontserratAlternates-Bold.ttf");

        tvMmensaje.setTypeface(face2);
        tvMprescripcion.setTypeface(face);
        tvMcitas.setTypeface(face);

    }

    public void menuPrescripcion(View view){
        Intent itnPrescripcion = new Intent(inicio_medicas.this, prescripcion.class);
        startActivity(itnPrescripcion);
    }

    public void menuCitas(View view){
        Intent itnCitas = new Intent(inicio_medicas.this, citas.class);
        startActivity(itnCitas);
    }
}
