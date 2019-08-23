package com.santodomingostudios.healthhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    // Información de la tabla
    public static final String TABLE_NAME_P = "PRESC";
    public static final String c_id = "_id";
    public static final String c_nombre = "nombre";
    public static final String c_dia = "dia";
    public static final String c_mes = "mes";
    public static final String c_ano = "ano";
    public static final String c_hora = "hora";
    public static final String c_minuto = "minuto";

    public static final String TABLE_NAME_C = "PRESP";
    public static final String p_id = "_id";
    public static final String p_info = "info";
    public static final String p_nombreD = "nombre_doctor";
    public static final String p_nombreH = "nombre_hospital";
    public static final String p_dia = "dia";
    public static final String p_mes = "mes";
    public static final String p_ano = "ano";
    public static final String p_hora = "hora";
    public static final String p_minuto = "minuto";



    // información del a base de datos
    static final String DB_NAME = "DBAGENDA";
    static final int DB_VERSION = 1;

    // Información de la base de datos

    private static final String CREATE_PRESCRIPCION = "create table PRESC ("+c_id+"  INTEGER PRIMARY KEY AUTOINCREMENT, "+c_nombre+" TEXT NOT NULL,"+c_dia+" INTEGER NOT NULL, "+c_mes+" INTEGER NOT NULL, "+c_ano+" INTEGER NOT NULL, "+c_hora+" INTEGER NOT NULL, "+c_minuto+" INTEGER NOT NULL);";
    private static final String CREATE_CITA = "create table PRESP ("+p_id+"  INTEGER PRIMARY KEY AUTOINCREMENT, "+p_info+" TEXT NOT NULL, " +p_nombreD+" TEXT NOT NULL," +p_nombreH+" TEXT NOT NULL,"+p_dia+" INTEGER NOT NULL,  "+p_mes+" INTEGER NOT NULL, "+p_ano+" INTEGER NOT NULL, "+p_hora+" INTEGER NOT NULL, "+p_minuto+" INTEGER NOT NULL);";

    public DBhelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRESCRIPCION);
        db.execSQL(CREATE_CITA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_P);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_C);
        onCreate(db);
    }
}
