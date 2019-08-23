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

public class modificar_perfil extends Activity{
    int edadFinal = 0, alturaFinal = 0, pesoFinal = 0;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_perfil);

        BaseDatos BD = new BaseDatos(this, "usuario", null, 1);
        db=BD.getWritableDatabase();

        final TextView tvEdad = findViewById(R.id.tvedad);
        final TextView tvPeso = findViewById(R.id.tvPeso);
        final TextView tvAltura = findViewById(R.id.tvAltura);
        SeekBar edad = findViewById(R.id.seekBarE);
        SeekBar peso = findViewById(R.id.seekBarP);
        SeekBar altura = findViewById(R.id.seekBarA);
        final Spinner spinnerm = findViewById(R.id.spinner);
        final EditText evtn = findViewById(R.id.evNombre);
        Button modficar = findViewById(R.id.modificarPerfil);

        evtn.setText(getIntent().getStringExtra("nombre"));
        edad.setProgress(getIntent().getIntExtra("edad", 0));
        peso.setProgress(getIntent().getIntExtra("peso",0));
        altura.setProgress(getIntent().getIntExtra("altura", 0));
        String[] letra = {"Masculino","Femenino"};
        spinnerm.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, letra));

        edad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                tvEdad.setText(progress + " años" + "");
                edadFinal = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        peso.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                tvPeso.setText(progress + " lbs" + "");
                pesoFinal = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        altura.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                tvAltura.setText(progress + " cm" + "");
                alturaFinal = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        modficar.setOnClickListener(new View.OnClickListener() {
            String sexoSeleccionado = spinnerm.getSelectedItem().toString();
            String nombreEscogido = evtn.getText().toString();

            @Override
            public void onClick(View view) {
                db.execSQL("update perfiles set id=1, nombre=nombreEscogido, sexo=sexoSeleccionado, edad=edadFinal, peso=pesoFinal, altura=alturaFinal");
                Intent itn = new Intent(modificar_perfil.this, perfiles.class);
                startActivity(itn);
            }
        });
    }

}