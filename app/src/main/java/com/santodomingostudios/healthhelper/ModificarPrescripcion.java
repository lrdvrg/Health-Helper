package com.santodomingostudios.healthhelper;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ModificarPrescripcion extends Activity implements OnClickListener {
    int Adia, Ames, Aano, Amin, Ahora;
    EditText et;
    Button btnActualizar, btnEliminar;
    TextView tvHora, tvMinuto, tvSeparador, msgSeleccioneH;
    public final Calendar c = Calendar.getInstance();
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    private static final String CERO = "0";
    long cID;

    SQLControlador dbcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_prescripcion);

        Intent i = getIntent();
        String Pid = i.getStringExtra("ipId");
        String Pnombre = i.getStringExtra("ipNombre");
        String Pdia = i.getStringExtra("ipDia");
        String Pmes = i.getStringExtra("ipMes");
        String Pano = i.getStringExtra("ipAno");
        String Pmin = i.getStringExtra("ipMin");
        String Phora = i.getStringExtra("ipHora");
        dbcon = new SQLControlador(this);
        dbcon.abrirBaseDeDatos();

        et = findViewById(R.id.etEvento);

        CalendarView myCalendar = findViewById(R.id.cvCalendario);
        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        tvMinuto= findViewById(R.id.tvMinuto);
        tvHora= findViewById(R.id.tvHora);
        tvSeparador= findViewById(R.id.separador);
        msgSeleccioneH = findViewById(R.id.msgSeleccioneH);


        int day = Integer.parseInt(Pdia);
        int month = Integer.parseInt(Pmes);
        int year = Integer.parseInt(Pano);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);


        long milliTime = calendar.getTimeInMillis();

        myCalendar.setDate(milliTime);
        cID = Long.parseLong(Pid);
        et.setText(Pnombre);

        tvMinuto.setText(Pmin);
        tvHora.setText(Phora);
        Amin = Integer.valueOf(Pmin);
        Ahora = Integer.valueOf(Phora);
        Adia = Integer.valueOf(Pdia);
        Ames = Integer.valueOf(Pmes);
        Aano = Integer.valueOf(Pano);


        btnActualizar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);

        CalendarView.OnDateChangeListener myCalendarListener = new CalendarView.OnDateChangeListener(){

            public void onSelectedDayChange(CalendarView view, int year, int Mmonth, int day){

                // add one because month starts at 0
                // output to log cat **not sure how to format year to two places here**
                Adia = day;
                Ames = Mmonth+1;
                Aano = year;

            }
        };
        myCalendar.setOnDateChangeListener(myCalendarListener);
        Log.v("MIRALA AQUI ISAI", et.getText().toString());

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnActualizar:
                String primero = et.getText().toString();
                Log.v("MIRALA AQUI ISAI", primero);
                String cName = primero;
                Log.v("MIRALA AQUI ISAI", cName);
                int cDay = Adia;
                int cMonth = Ames;
                int cYear = Aano;
                int cMin = Amin;
                int cHour = Ahora;

                dbcon.actualizarDatos(cID, cName, cDay, cMonth, cYear, cMin, cHour);
                Toast.makeText(this, "Prescripcion actualizada correctamente", Toast.LENGTH_SHORT).show();
                Intent main = new Intent(ModificarPrescripcion.this, prescripcion.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);                break;

            case R.id.btnEliminar:
                dbcon.deleteData(cID);
                Toast.makeText(this, "Prescripcion eliminada correctamente", Toast.LENGTH_SHORT).show();
                Intent mai2 = new Intent(ModificarPrescripcion.this, prescripcion.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mai2);
                break;
        }
    }

    public void returnHome() {

        Intent home_intent = new Intent(getApplicationContext(),
                MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(home_intent);
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
                Amin = Integer.parseInt(minutoFormateado);
                Ahora = Integer.parseInt(horaFormateada);
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