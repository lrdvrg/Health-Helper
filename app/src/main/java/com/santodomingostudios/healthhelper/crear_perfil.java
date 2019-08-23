package com.santodomingostudios.healthhelper;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class crear_perfil extends Activity{
    SQLiteDatabase db;
    int edadFinal = 0, alturaFinal = 0, pesoFinal = 0;
    EditText etNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_perfil);

        BaseDatos BD = new BaseDatos(this, "usuario", null, 1);
        db=BD.getWritableDatabase();

        final TextView tvEdad = findViewById(R.id.tvedad);
        final TextView tvPeso = findViewById(R.id.tvPeso);
        final TextView tvAltura = findViewById(R.id.tvAltura);
        Button btnListo = findViewById(R.id.btnCrear);
        final SeekBar edadp = findViewById(R.id.seekBarE);
        SeekBar pesop = findViewById(R.id.seekBarP);
        SeekBar alturap = findViewById(R.id.seekBarA);
        final Spinner spinnerp = findViewById(R.id.spinner);

        String[] letra = {"Masculino","Femenino"};
        spinnerp.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, letra));

        edadp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                edadFinal = progress;
                tvEdad.setText(progress + " años" + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        pesop.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                pesoFinal = progress;
                tvPeso.setText(progress + " lbs" + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        alturap.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                alturaFinal = progress;
                tvAltura.setText(progress + " cm" + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        btnListo.setOnClickListener(new View.OnClickListener() {
            String sexoSeleccionado = spinnerp.getSelectedItem().toString();

            @Override
            public void onClick(View view) {

              db.execSQL("insert into perfiles(id, sexo, edad, peso, altura) values(1,'"+sexoSeleccionado+"', "+edadFinal+", "+pesoFinal+", "+alturaFinal+")");
              Intent itn = new Intent(crear_perfil.this, MainActivity.class);
              startActivity(itn);
            }
        });

    }

}