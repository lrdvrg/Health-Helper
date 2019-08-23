package com.santodomingostudios.healthhelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class RutinaActivity extends AppCompatActivity {
    CardView card1, card2, card3, card4;
    TextView tv1, tv2, tv3, tv4;
    ImageView img1, img2, img3, img4;

    SQLiteDatabase bd;


    //para los alert
    String[] tituloAlert, mensajeAlert;
    int[] imagenAlert;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina);

        //los cardviews
        card1 = (CardView) findViewById(R.id.card1);
        card2 = (CardView) findViewById(R.id.card2);
        card3 = (CardView) findViewById(R.id.card3);
        card4 = (CardView) findViewById(R.id.card4);

        //Los textViews de los nombres de los usuarios
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);

        //las imagenes
        img1 = (ImageView) findViewById(R.id.imgv1);
        img2 = (ImageView) findViewById(R.id.imgv2);
        img3 = (ImageView) findViewById(R.id.imgv3);
        img4 = (ImageView) findViewById(R.id.imgv4);

        tituloAlert = new String[4];
        mensajeAlert = new String[4];
        imagenAlert = new int[4];

        BaseDatos baseDatos = new BaseDatos(this, "usuario", null, 1);

        bd = baseDatos.getWritableDatabase();

//        bd.execSQL("drop table if exists rutina_dia");
//        bd.execSQL("create table rutina_dia(nom_rutina_dia text, desc_rutina_dia text, img_rutina_dia, fecha date)");
//        bd.execSQL("insert into rutina_dia(nom_rutina_dia, desc_rutina_dia, img_rutina_dia, fecha) values('Pechadas',' 3 series de 15 repeticiones. Al realizar pechadas, la parte baja de tu espalda debe estar recta, es decir, no debe estar hundida ni arqueada. Además, debes tener los pies separados al ancho de los hombros.',"+R.drawable.rutina_pechadas+", Date('now'))," +
//                "('Sentadillas', '3 series de 10 repeticiones. Si eres principiante puedes realizarlo con ayuda de una silla delante tuyo. Los pies deben estar separados del ancho de la cadera y debemos bajar la cola, procurando que la rodilla nunca pase de la punta del pie. ',"+R.drawable.rutina_sentadilla+", Date('now'))," +
//                        "('Zancada', '2 series de 14 repeticiones. En este ejercicio recuerda que la rodilla flexionada no debe pasar la punta del pie.', "+R.drawable.rutina_zancada+", Date('now'))," +
//                        "('Plancha frontal', '2 series de 45 segundos. En este ejercicio se fomenta la resistencia del centro de gravedad del cuerpo, logrando así desarrollar el abdomen.', "+R.drawable.rutina_plancha_frontal+", Date('now'))");

        checkPorRutinasHoy();

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarAlert(tituloAlert[0],mensajeAlert[0],imagenAlert[0]);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarAlert(tituloAlert[1],mensajeAlert[1],imagenAlert[1]);

            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarAlert(tituloAlert[2],mensajeAlert[2],imagenAlert[2]);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarAlert(tituloAlert[3],mensajeAlert[3],imagenAlert[3]);

            }
        });

    }

    //genera la rutina aleatoria
    private void cargarRutinasNuevas(){
        Cursor c1 = bd.rawQuery("select * from rutina order by RANDOM() limit 4", null);
        TextView[] tvs = {tv1, tv2, tv3, tv4};
        ImageView[] imgs = {img1, img2, img3, img4};



        int i= 0;
        while(c1.moveToNext()){
            tvs[i].setText(c1.getString(1));
            imgs[i].setImageResource(c1.getInt(3));

            //variables para los alerts.

            tituloAlert[i] = c1.getString(1);
            mensajeAlert[i] = c1.getString(2);
            imagenAlert[i] = c1.getInt(3);


            //insertamos la rutina nueva a la tabla de rutina del dia
            bd.execSQL("insert into rutina_dia(nom_rutina_dia, desc_rutina_dia, img_rutina_dia, fecha) values('"+tituloAlert[i]+"','"+mensajeAlert[i]+"',"+imagenAlert[i]+", date('now') )");


            i++;
        }






    }

    private void generarAlert(String titulo, String mensaje, int imagen){

        final AlertDialog.Builder alerta = new AlertDialog.Builder(this);

        LayoutInflater layoutInflater = LayoutInflater.from(this);

        View v = layoutInflater.inflate(R.layout.dialogo_personalizado, null);
        alerta.setView(v);

//        final AlertDialog dialog  =alerta.create();

        TextView tv_titulo = (TextView) v.findViewById(R.id.titulo);
        tv_titulo.setText(titulo);
        ImageView im = (ImageView) v.findViewById(R.id.imagenview);
        im.setImageResource(imagen);
        TextView tv_mensaje = (TextView) v.findViewById(R.id.mensaje);
        tv_mensaje.setText(mensaje);





        alerta.setPositiveButton("Listo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alerta.show().getWindow().setLayout(1000,1300);
    }

    //revisa por la existencia en la tabla rutina_dia por alguna ya registrada
    private void checkPorRutinasHoy(){
        Cursor c = bd.rawQuery("select * from rutina_dia where fecha = date('now')", null);

        TextView[] tvs = {tv1, tv2, tv3, tv4};
        ImageView[] imgs = {img1, img2, img3, img4};

        int i =0;
        while(c.moveToNext()){

            tvs[i].setText(c.getString(0));
            imgs[i].setImageResource(c.getInt(2));

            //variables para los alerts.

            tituloAlert[i] = c.getString(0);
            mensajeAlert[i] = c.getString(1);
            imagenAlert[i] = c.getInt(2);

            i++;


        }
        if (i < 3){
            cargarRutinasNuevas();
        }

    }
}
