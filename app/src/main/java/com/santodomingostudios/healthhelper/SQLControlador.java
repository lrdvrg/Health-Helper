package com.santodomingostudios.healthhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLControlador {

    private DBhelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;
    int cont = 0;

    public SQLControlador(Context c) {
        ourcontext = c;
    }

    public SQLControlador abrirBaseDeDatos() throws SQLException {
        dbhelper = new DBhelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        dbhelper.close();
    }

    public void insertarDatos(String name, int dia, int mes, int ano, int min, int hora) {
        ContentValues cv = new ContentValues();
        cv.put(DBhelper.c_nombre, name);
        cv.put(DBhelper.c_dia, dia);
        cv.put(DBhelper.c_mes, mes);
        cv.put(DBhelper.c_ano, ano);
        cv.put(DBhelper.c_minuto, min);
        cv.put(DBhelper.c_hora, hora);

        database.insert(DBhelper.TABLE_NAME_P, null, cv);

    }

    public void insertarDatosC(String info, String ndoctor, String nhospital, int dia, int mes, int ano, int min, int hora) {
        ContentValues cv2 = new ContentValues();
        cv2.put(DBhelper.p_info, info);
        cv2.put(DBhelper.p_nombreD, ndoctor);
        cv2.put(DBhelper.p_nombreH, nhospital);
        cv2.put(DBhelper.p_dia, dia);
        cv2.put(DBhelper.p_mes, mes);
        cv2.put(DBhelper.p_ano, ano);
        cv2.put(DBhelper.p_minuto, min);
        cv2.put(DBhelper.p_hora, hora);

        database.insert(DBhelper.TABLE_NAME_C, null, cv2);

    }

    public Cursor leerDatos() {
        String[] todasLasColumnas = new String[] {
                DBhelper.c_id, DBhelper.c_nombre, DBhelper.c_dia, DBhelper.c_mes, DBhelper.c_ano, DBhelper.c_minuto, DBhelper.c_hora
        };
        Cursor c = database.query(DBhelper.TABLE_NAME_P, todasLasColumnas, null,
                null, null, null, null);
        if (c != null) {
            c.moveToNext();
        }
        return c;
    }

    public Cursor compararFechas(int diahoy, int meshoy, int anohoy){
        String[] todasLasColumnas = new String[] {
                DBhelper.c_dia, DBhelper.c_mes, DBhelper.c_ano
        };
        Cursor cur = database.rawQuery("select * from PRESC where dia = "+diahoy+" & mes = "+meshoy+" & ano = "+anohoy+"", null);

        if (cur != null) {
            cur.moveToNext();
        }
        return cur;
    }

    public Cursor leerDatosC() {
        String[] todasLasColumnas = new String[] {
                DBhelper.p_id, DBhelper.p_info,DBhelper.p_nombreD, DBhelper.p_nombreH, DBhelper.p_dia, DBhelper.p_mes, DBhelper.p_ano, DBhelper.p_minuto, DBhelper.p_hora
        };
        Cursor d = database.query(DBhelper.TABLE_NAME_C, todasLasColumnas, null,
                null, null, null, null);
        if (d != null) {
            d.moveToNext();
        }
        return d;
    }

    public int actualizarDatos(long cID, String cName, int cDay, int cMonth, int cYear, int cMinute, int cHour) {
        ContentValues cvActualizar = new ContentValues();
        cvActualizar.put(DBhelper.c_nombre, cName);
        cvActualizar.put(DBhelper.c_dia, cDay);
        cvActualizar.put(DBhelper.c_mes, cMonth);
        cvActualizar.put(DBhelper.c_ano, cYear);
        cvActualizar.put(DBhelper.c_minuto, cMinute);
        cvActualizar.put(DBhelper.c_hora, cHour);


        int i = database.update(DBhelper.TABLE_NAME_P, cvActualizar,
                DBhelper.c_id + " = " + cID, null);
        return i;
    }

    public int actualizarDatosC(long pID, String pInfo,String pDname,String pHname, int pDay, int pMonth, int pYear, int pMinute, int pHour) {
        ContentValues cv2Actualizar = new ContentValues();
        cv2Actualizar.put(DBhelper.p_info, pInfo);
        cv2Actualizar.put(DBhelper.p_nombreD, pDname);
        cv2Actualizar.put(DBhelper.p_nombreH, pHname);
        cv2Actualizar.put(DBhelper.p_dia, pDay);
        cv2Actualizar.put(DBhelper.p_mes, pMonth);
        cv2Actualizar.put(DBhelper.p_ano, pYear);
        cv2Actualizar.put(DBhelper.p_minuto, pMinute);
        cv2Actualizar.put(DBhelper.p_hora, pHour);


        int i = database.update(DBhelper.TABLE_NAME_C, cv2Actualizar,
                DBhelper.c_id + " = " + pID, null);
        return i;
    }


    public void deleteData(long cid) {
        database.delete(DBhelper.TABLE_NAME_P, DBhelper.c_id + "="
                + cid, null);
    }

    public void deleteDataC(long pid) {
        database.delete(DBhelper.TABLE_NAME_C, DBhelper.p_id + "="
                + pid, null);
    }


}

