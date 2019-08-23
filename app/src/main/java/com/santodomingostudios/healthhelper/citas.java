package com.santodomingostudios.healthhelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class citas extends Activity {
    SQLControlador dbconeccion;
    TextView cID, cInfo,cNoD,cNoh, cDia, cMes, cAno, cMinuto, cHora;
    ListView LV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citas);
        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();


        TextView tvNota = findViewById(R.id.txtNota);
        Button FAB = findViewById(R.id.fab);
        LV = findViewById(R.id.lvLista);
        cID = findViewById(R.id.res_id);
        cInfo= findViewById(R.id.res_info);
        cNoD= findViewById(R.id.res_nombreD);
        cNoh= findViewById(R.id.res_nombreH);
        cDia = findViewById(R.id.res_dia);
        cMes = findViewById(R.id.res_mes);
        cAno = findViewById(R.id.res_ano);
        cMinuto = findViewById(R.id.res_minuto);
        cHora = findViewById(R.id.res_hora);

        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/MontserratAlternates-Regular.ttf");
        Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/MontserratAlternates-Bold.ttf");

        tvNota.setTypeface(face);



        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent siguiente= new Intent(citas.this, agregar_cita.class);
                startActivity(siguiente);

            }
        });

        Cursor cursor = dbconeccion.leerDatosC();

        String[] from = new String[] {
                DBhelper.p_id, DBhelper.p_info,DBhelper.p_nombreD,DBhelper.p_nombreH, DBhelper.p_dia, DBhelper.p_mes, DBhelper.p_ano, DBhelper.p_minuto, DBhelper.p_hora

        };
        int[] to = new int[] {
                R.id.res_id, R.id.res_info,R.id.res_nombreD,R.id.res_nombreH, R.id.res_dia, R.id.res_mes, R.id.res_ano, R.id.res_minuto, R.id.res_hora

        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                citas.this, R.layout.formato_fila_cita, cursor, from, to,0);

        adapter.notifyDataSetChanged();
        LV.setAdapter(adapter);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                cID = view.findViewById(R.id.res_id);
                cInfo = view.findViewById(R.id.res_info);
                cNoD = view.findViewById(R.id.res_nombreD);
                cNoh = view.findViewById(R.id.res_nombreH);
                cDia = view.findViewById(R.id.res_dia);
                cMes = view.findViewById(R.id.res_mes);
                cAno = view.findViewById(R.id.res_ano);
                cMinuto = view.findViewById(R.id.res_minuto);
                cHora = view.findViewById(R.id.res_hora);

                String aux_miembroId = cID.getText().toString();
                String aux_miembroInfo = cInfo.getText().toString();
                String aux_miembroNombreD = cNoD.getText().toString();
                String aux_miembroNombreH = cNoh.getText().toString();
                String aux_miembroDia = cDia.getText().toString();
                String aux_miembroMes = cMes.getText().toString();
                String aux_miembroAno = cAno.getText().toString();
                String aux_miembroMin = cMinuto.getText().toString();
                String aux_miembroHora = cHora.getText().toString();

                Intent siguiente2 = new Intent(citas.this, ModificarCita.class);
                siguiente2.putExtra("cipId", aux_miembroId);
                siguiente2.putExtra("cipInfo", aux_miembroInfo);
                siguiente2.putExtra("cipNombreD", aux_miembroNombreD);
                siguiente2.putExtra("cipNombreH", aux_miembroNombreH);
                siguiente2.putExtra("cipDia", aux_miembroDia);
                siguiente2.putExtra("cipMes", aux_miembroMes);
                siguiente2.putExtra("cipAno", aux_miembroAno);
                siguiente2.putExtra("cipMin", aux_miembroMin);
                siguiente2.putExtra("cipHora", aux_miembroHora);

                startActivity(siguiente2);
            }
        });
    }
}
