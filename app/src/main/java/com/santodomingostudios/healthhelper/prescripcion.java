package com.santodomingostudios.healthhelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class prescripcion extends Activity {
        SQLControlador dbconeccion;
    TextView prID, prNombre, prDia, prMes, prAno, prMinuto, prHora;
    ListView LV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescripcion);
        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();


        TextView tvNota = findViewById(R.id.txtNota);
        Button FAB = findViewById(R.id.fab);
        LV = findViewById(R.id.lvLista);
        prID = findViewById(R.id.pres_id);
        prNombre = findViewById(R.id.pres_nombre);
        prDia = findViewById(R.id.pres_dia);
        prMes = findViewById(R.id.pres_mes);
        prAno = findViewById(R.id.pres_ano);

        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/MontserratAlternates-Regular.ttf");
        Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/MontserratAlternates-Bold.ttf");

        tvNota.setTypeface(face);


        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent siguiente= new Intent(prescripcion.this, agregar_prescripcion.class);
                startActivity(siguiente);

            }
        });

        Cursor cursor = dbconeccion.leerDatos();

        String[] from = new String[] {
                DBhelper.c_id, DBhelper.c_nombre, DBhelper.c_dia, DBhelper.c_mes, DBhelper.c_ano, DBhelper.c_minuto, DBhelper.c_hora

        };
        int[] to = new int[] {
                R.id.pres_id, R.id.pres_nombre, R.id.pres_dia, R.id.pres_mes, R.id.pres_ano

        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                prescripcion.this, R.layout.formato_fila, cursor, from, to,0);

        adapter.notifyDataSetChanged();
        LV.setAdapter(adapter);

        /*LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                prID = findViewById(R.id.pres_id);
                prNombre = findViewById(R.id.pres_nombre);
                prDia = findViewById(R.id.pres_dia);
                prMes = findViewById(R.id.pres_mes);
                prAno = findViewById(R.id.pres_ano);

                String aux_miembroId = prID.getText().toString();
                String aux_miembroNombre = prNombre.getText().toString();
                String aux_miembroDia = prDia.getText().toString();
                String aux_miembroMes = prMes.getText().toString();
                String aux_miembroAno = prAno.getText().toString();

                Intent siguiente2 = new Intent(prescripcion.this, ModificarMiembro.class);
                siguiente2.putExtra("miembroId", aux_miembroId);
                siguiente2.putExtra("miembroNombre", aux_miembroNombre);
                siguiente2.putExtra("miembroDia", aux_miembroDia);
                siguiente2.putExtra("miembroMes", aux_miembroMes);
                siguiente2.putExtra("miembroAno", aux_miembroAno);

                startActivity(siguiente2);
            }
        });*/
    }
}
