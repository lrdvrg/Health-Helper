package com.santodomingostudios.healthhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {
    private String crear = "create table rutina(id integer primary key autoincrement, nom_rutina text, desc_rutina text, img_rutina integer)";
    private String insertarInfo = "insert into rutina(nom_rutina,desc_rutina, img_rutina) values ('Pechadas',' 3 series de 15 repeticiones. Al realizar pechadas, la parte baja de tu espalda debe estar recta, es decir, no debe estar hundida ni arqueada. Además, debes tener los pies separados al ancho de los hombros.',"+R.drawable.rutina_pechadas+")," +
            "('Sentadillas', '3 series de 10 repeticiones. Si eres principiante puedes realizarlo con ayuda de una silla delante tuyo. Los pies deben estar separados del ancho de la cadera y debemos bajar la cola, procurando que la rodilla nunca pase de la punta del pie. ',"+R.drawable.rutina_sentadilla+")," +
            "('Zancada', '2 series de 14 repeticiones. En este ejercicio recuerda que la rodilla flexionada no debe pasar la punta del pie.', "+R.drawable.rutina_zancada+")," +
            "('Plancha frontal', '2 series de 45 segundos. En este ejercicio se fomenta la resistencia del centro de gravedad del cuerpo, logrando así desarrollar el abdomen.', "+R.drawable.rutina_plancha_frontal+")," +
            "('Abdominales', '2 series de 20 repeticiones. En el piso, sobre una manta o alfombra, debemos levantar el torso sin forzar el cuello e intentar unir simultáneamente codo con las rodillas.', "+R.drawable.rutina_abdominales+")," +
            "('Skipping', '4 series de 30 repeticiones. Este ejercicio se utiliza mucho en los calentamientos para activar la circulación, como ejercicio de coordinación y para fortalecer el tren inferior donde la musculatura extensora del tobillo y la flexora de la cadera trabajan con gran intensidad.', "+R.drawable.rutina_skipping+")," +
            "('Salto tijera', '2 series de 5 repeticiones. Para iniciar el movimiento, ponte en cuclillas hasta la mitad y realiza un movimiento de explosión de nuevo hacia arriba lo más alto posible. Extiende por completo todo tu cuerpo, extendiendo las piernas y los brazos lejos del cuerpo.', "+R.drawable.rutina_salto_tijera+")," +
            "('Flexiones triceps', '3 series de 4 repeticiones. Este ejercicio es el más utilizado para aislar y ejercitar el tríceps. Es muy sencillo y se puede realizar tanto en el gimnasio como en casa. Solo se necesita una colchoneta para apoyarse en el suelo y no se necesita ningún tipo de peso, ya que se usará el del propio cuerpo.', "+R.drawable.rutina_flexiones_triceps+")," +
            "('Box jumping', '4 series de 10 repeticiones. Se debe estar de pie frente a la caja en posición recta. Llevando las manos hacia delante, con los codos flexionados, se desciende como para realizar una sentadilla y luego se salta con toda la potencia aterrizando en el borde de la caja.', "+R.drawable.rutina_box_jumping+")," +
            "('Butt kicks', '2 series de 15 repeticiones. \n" +
            "Este movimiento cardiovascular fortalece los isquiotibiales, que es lo que ayuda a aumentar la aceleración y la velocidad. Aunque no solo funciona con los isquiotibiales, sino que también es una excelente manera de trabajar los glúteos y estirar los patios para prepararse para el movimiento. ', "+R.drawable.rutina_butt_kicks+")";

    private String crearRutinaDia = "create table rutina_dia(nom_rutina_dia text, desc_rutina text, img_rutina_dia, fecha datetime)";
    private String perfil = "create table perfiles(id INTEGER PRIMARY KEY,nombre text not null, sexo text not null, edad int not null, peso int not null, altura int not null)";
    private String mmmgto = "delete from perfiles where edad = 59";

    public BaseDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists perfiles");
        db.execSQL(crear);
        db.execSQL(insertarInfo);
        db.execSQL(crearRutinaDia);
        db.execSQL(mmmgto);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists rutina");
        db.execSQL(crear);
        db.execSQL(insertarInfo);
        db.execSQL(mmmgto);

    }
}
