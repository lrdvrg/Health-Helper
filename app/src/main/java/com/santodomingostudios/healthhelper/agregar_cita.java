package com.santodomingostudios.healthhelper;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class agregar_cita extends Activity {
    int Sdia = 0, Smes = 0, Sano = 0, Smin = 0, Shora = 0;
    SQLControlador dbconection;
    private static final String CERO = "0";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    //Widgets
    EditText etNoD, etNoH, etInfo;
    TextView tvHora, tvMinuto, tvSeparador, msgSeleccioneH;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_cita);

        etInfo = findViewById(R.id.etInfoC);
        etNoD = findViewById(R.id.etDoctor);
        etNoH = findViewById(R.id.etCSalud);
        Button bAgregar = findViewById(R.id.btnAgregar);
        tvMinuto= findViewById(R.id.tvMinuto);
        tvHora= findViewById(R.id.tvHora);
        tvSeparador= findViewById(R.id.separador);
        msgSeleccioneH = findViewById(R.id.msgSeleccioneH);
        TextView msg1 = findViewById(R.id.msgIngreso);
        TextView msg2 = findViewById(R.id.msgSeleccione);
        CalendarView myCalendar = findViewById(R.id.cvCalendario2);

        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/MontserratAlternates-Regular.ttf");
        Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/MontserratAlternates-Bold.ttf");


        CalendarView.OnDateChangeListener myCalendarListener = new CalendarView.OnDateChangeListener(){

            public void onSelectedDayChange(CalendarView view, int year, int month, int day){

                // add one because month starts at 0
                // output to log cat **not sure how to format year to two places here**
                Sdia = day;
                Smes = month;
                Sano = year;

            }
        };
        myCalendar.setOnDateChangeListener(myCalendarListener);
        dbconection = new SQLControlador(this);
        dbconection.abrirBaseDeDatos();

        bAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = etInfo.getText().toString();
                String ndoctor= etNoD.getText().toString();
                String nhospital = etNoH.getText().toString();
                if(info=="" || Sano==0 || Shora==0 || ndoctor == ""|| nhospital == ""){
                    Toast.makeText(agregar_cita.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    int dia = Sdia, mes = Smes+1, ano = Sano, min = Smin, hour = Shora;

                    dbconection.insertarDatosC(info,ndoctor,nhospital, dia, mes, ano, min, hour);
                    Toast.makeText(agregar_cita.this, "Evento agregado correctamente", Toast.LENGTH_SHORT).show();
                    Intent main = new Intent(agregar_cita.this, citas.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main);
                }

            }
        });

        //Widget EditText donde se mostrara la hora obtenida

    }
    public void obtenerHora(View view){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                Smin = Integer.parseInt(minutoFormateado);
                Shora = Integer.parseInt(horaFormateada);
                tvHora.setText(horaFormateada);
                tvMinuto.setText(minutoFormateado);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
    }

}