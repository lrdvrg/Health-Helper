package com.santodomingostudios.healthhelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

public class perfiles extends Activity {
    String pernombre,persexo,peredad,peraltura,perpeso;
    TextView tvsexo, tvpeso, tvedad,tvaltura;
    SQLiteDatabase db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfiles);
        BaseDatos bd = new BaseDatos(this, "usuario", null, 1);
        db = bd.getWritableDatabase();

        tvsexo = (TextView) findViewById(R.id.sexo);
        tvedad = (TextView) findViewById(R.id.edad);
        tvpeso = (TextView) findViewById(R.id.peso);
        tvaltura = (TextView) findViewById(R.id.altura);


        Cursor cu = db.rawQuery("select * from perfiles", null);
        while(cu.moveToNext()){
            persexo = cu.getString(1);
            peredad = cu.getString(2)+" años";
            perpeso = cu.getString(3)+" lbs";
            peraltura = cu.getString(4)+" cm";
        }
        tvsexo.setText(persexo);
        tvedad.setText(peredad);
        tvpeso.setText(perpeso);
        tvaltura.setText(peraltura);

    }

    public void ModificarPerfil(View view) {
        Intent modificar  = new Intent (perfiles.this, modificar_perfil.class);
        modificar.putExtra("sexo", persexo);
        modificar.putExtra("edad", peredad);
        modificar.putExtra("altura", peraltura);
        modificar.putExtra("peso", perpeso);

        startActivity(modificar);
    }


}
