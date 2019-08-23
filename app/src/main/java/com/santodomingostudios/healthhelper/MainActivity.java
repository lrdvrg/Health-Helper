package com.santodomingostudios.healthhelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseDatos BD = new BaseDatos(this, "usuario", null, 1);
        db = BD.getWritableDatabase();

        TextView tvMenuNombre = findViewById(R.id.tvNombre);
        TextView tvMenuActividad = findViewById(R.id.tvMenuActividad);
        TextView tvMenuMedicina = findViewById(R.id.tvMenuMedicina);
        TextView tvMenuResumen = findViewById(R.id.tvMenuResumen);
        TextView tvMenuRutinas = findViewById(R.id.tvMenuRutinas);
        TextView tvMenuPerfiles = findViewById(R.id.tvMenuPerfiles);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/MontserratAlternates-Regular.ttf");
        Typeface face2 = Typeface.createFromAsset(getAssets(), "fonts/MontserratAlternates-Bold.ttf");

        tvMenuNombre.setTypeface(face2);
        tvMenuActividad.setTypeface(face);
        tvMenuMedicina.setTypeface(face);
        tvMenuResumen.setTypeface(face);
        tvMenuRutinas.setTypeface(face);
        tvMenuPerfiles.setTypeface(face);

    }
    public void inicioActividad(View view) {
        Intent itnInicioActividad = new Intent(MainActivity.this, inicio_actividades.class);
        Intent service = new Intent (MainActivity.this, MyService.class);
        startService(service);
        startActivity(itnInicioActividad);
    }

    public void inicioCuidado(View view) {
        Intent itnInicioCuidado = new Intent(MainActivity.this, inicio_medicas.class);
        startActivity(itnInicioCuidado);
    }

    public void inicioRutina(View view) {
        Intent itnInicioRutina = new Intent(MainActivity.this, RutinaActivity.class);
        startActivity(itnInicioRutina);
    }

    public void inicioResumen(View view) {
        Intent itnInicioResumen = new Intent(MainActivity.this, ResumenActivity.class);
        startActivity(itnInicioResumen);
    }

    public void inicioPerfil(View view) {
        Intent itnInicioPerfil = new Intent(MainActivity.this, perfiles.class);
        startActivity(itnInicioPerfil);
    }

    public void revisarPerfil (){
            db.execSQL("create table perfiles(id INTEGER PRIMARY KEY, sexo text not null, edad int not null, peso int not null, altura int not null)");
            db.execSQL("drop table if exists perfiles");
            Cursor c = db.rawQuery("select * from perfiles", null);
        if (!c.moveToNext()) {
            Intent itnInicioCuidado = new Intent(MainActivity.this, crear_perfil.class);
            startActivity(itnInicioCuidado);
        }
    }/

}



