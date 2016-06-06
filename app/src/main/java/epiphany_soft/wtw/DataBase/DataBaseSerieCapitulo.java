package epiphany_soft.wtw.DataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import epiphany_soft.wtw.DataBase.DataBaseContract.CapituloContract;
import epiphany_soft.wtw.Negocio.Sesion;

import static epiphany_soft.wtw.DataBase.DataBaseContract.*;


public class DataBaseSerieCapitulo {

    DataBaseHelper miDBHelper;



    public DataBaseSerieCapitulo(Context context) {

        miDBHelper = new DataBaseHelper(context);

    }

    public boolean insertarSerie(int  id) {
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(SerieContract.COLUMN_NAME_SERIE_ID, id);
            long rowide= db.insert(SerieContract.TABLE_NAME, null, values);
            if (rowide > 0) return true;
        }
        return false;
    }



    public Cursor consultarSerieLikeNombre(String nombre){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS + ","+
                            ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN + " ";
            query +=
                    "FROM " + ProgramaContract.TABLE_NAME + " JOIN " +
                            SerieContract.TABLE_NAME +
                            " ON " + SerieContract.COLUMN_NAME_SERIE_ID + "="
                            + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
            query +=
                    "WHERE " + ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + " LIKE \'%"+nombre+"%\'";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }

    public Cursor consultarSeriePorNombre(String nombre){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + ProgramaContract.TABLE_NAME+"."+ ProgramaContract.COLUMN_NAME_PROGRAMA_ID + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS + ","+
                            GeneroContract.COLUMN_NAME_GENERO_NOMBRE +","+
                            ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN + ","+
                            AgendaContract.COLUMN_NAME_USUARIO_ID+" ";
            query +=
                    "FROM " + ProgramaContract.TABLE_NAME+" LEFT OUTER JOIN "+
                            AgendaContract.TABLE_NAME +" ON "+ ProgramaContract.TABLE_NAME+"."+
                            ProgramaContract.COLUMN_NAME_PROGRAMA_ID+"="+ AgendaContract.TABLE_NAME+"."+
                            AgendaContract.COLUMN_NAME_PROGRAMA_ID+" NATURAL JOIN "+
                            GeneroContract.TABLE_NAME+ " JOIN " +
                            SerieContract.TABLE_NAME +
                            " ON " + SerieContract.COLUMN_NAME_SERIE_ID + "="
                            + ProgramaContract.TABLE_NAME+"."+ ProgramaContract.COLUMN_NAME_PROGRAMA_ID +" ";
            ;
            query +=
                    "WHERE " + ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE +"=\'"+nombre+"\'";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }



    public Cursor consultarSeriePorDia(int idDia){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS + ","+
                            ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN + " ";
            query +=
                    "FROM " + DiaHorarioContract.TABLE_NAME+" NATURAL JOIN "+
                            HorarioContract.TABLE_NAME+" NATURAL JOIN "+
                            ProgramaContract.TABLE_NAME + " JOIN " +
                            SerieContract.TABLE_NAME +
                            " ON " + SerieContract.COLUMN_NAME_SERIE_ID + "="
                            + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
            query +=
                    "WHERE " + DiaHorarioContract.COLUMN_NAME_DIA_ID + "=?";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idDia)});
            return c;
        }
        else return null;
    }

    public boolean insertarCapitulo(int id_cap, String nombreCap, int id_temp, int id_ser){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CapituloContract.COLUMN_NAME_CAPITULO_ID, id_cap);
            values.put(CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE,nombreCap);
            values.put(CapituloContract.COLUMN_NAME_TEMPORADA_ID, id_temp);
            values.put(CapituloContract.COLUMN_NAME_SERIE_ID, id_ser);

            long rowide= db.insert(CapituloContract.TABLE_NAME, null, values);
            if (rowide > 0) return true;
        }
        return false;
    }
/*
    public Cursor getTemporadasDeSerie(int idSerie){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + TemporadaContract.COLUMN_NAME_TEMPORADA_ID + " ";
            query +=
                    "FROM " + TemporadaContract.TABLE_NAME+" ";
            query +=
                    "WHERE " + TemporadaContract.COLUMN_NAME_PROGRAMA_ID +"=? ";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idSerie)});
            return c;
        }
        else return null;
    }*/

    public Cursor getTemporadasDeSerie(int idSerie){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + TemporadaContract.TABLE_NAME+"."+ TemporadaContract.COLUMN_NAME_TEMPORADA_ID + ","
                            +"COUNT("+ CapituloContract.TABLE_NAME+"."+ CapituloContract.COLUMN_NAME_TEMPORADA_ID+") AS cuenta ";
            query +=
                    "FROM " + TemporadaContract.TABLE_NAME+" LEFT OUTER JOIN "+ CapituloContract.TABLE_NAME +
                            " ON "+ TemporadaContract.TABLE_NAME+"."+ TemporadaContract.COLUMN_NAME_TEMPORADA_ID
                            +"="+ CapituloContract.TABLE_NAME+"."+ CapituloContract.COLUMN_NAME_TEMPORADA_ID+" AND "
                            + TemporadaContract.TABLE_NAME+"."+ TemporadaContract.COLUMN_NAME_PROGRAMA_ID+"="
                            + CapituloContract.TABLE_NAME+"."+ CapituloContract.COLUMN_NAME_SERIE_ID+" ";
            query +=
                    "WHERE " + TemporadaContract.TABLE_NAME+"."+ TemporadaContract.COLUMN_NAME_PROGRAMA_ID +"=? ";
            query+=
                    "GROUP BY "+ TemporadaContract.TABLE_NAME+"."+ TemporadaContract.COLUMN_NAME_TEMPORADA_ID;
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idSerie)});
            return c;
        }
        else return null;
    }


    public boolean actualizarCapitulo(int id_cap_old, int id_cap_new, String nombreCap, int id_temp, int id_ser){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CapituloContract.COLUMN_NAME_CAPITULO_ID, id_cap_new);
            values.put(CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE,nombreCap);
            values.put(CapituloContract.COLUMN_NAME_TEMPORADA_ID, id_temp);
            values.put(CapituloContract.COLUMN_NAME_SERIE_ID, id_ser);

            String query= CapituloContract.COLUMN_NAME_SERIE_ID+"=? AND "
                    + CapituloContract.COLUMN_NAME_TEMPORADA_ID+"=? AND "
                    + CapituloContract.COLUMN_NAME_CAPITULO_ID+"=?";
            String[] compare = new String[]{Integer.toString(id_ser),Integer.toString(id_temp),Integer.toString(id_cap_old)};
            try{
                int rowid=db.update(CapituloContract.TABLE_NAME, values, query, compare);
                if (rowid>0) return true;
            } catch (Exception e){
                //No se hace nada, y luego retorna falso
            }
        }
        return false;
    }

    public Cursor consultarCapitulosPorTemporada(int idTemporada, int idSerie){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query = "SELECT " + CapituloContract.COLUMN_NAME_CAPITULO_ID +","+
                    CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE+" ";
            query +=
                    "FROM " + CapituloContract.TABLE_NAME+" ";
            query +=
                    "WHERE "+ CapituloContract.COLUMN_NAME_TEMPORADA_ID+"=? AND " + CapituloContract.COLUMN_NAME_SERIE_ID +"=? ";
            query += "ORDER BY "+ CapituloContract.COLUMN_NAME_CAPITULO_ID;

            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idTemporada),Integer.toString(idSerie)});
            return c;
        }
        else return null;
    }

    public Cursor consultarCapitulosVistos(int idTemporada, int idSerie){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query = "SELECT " + CapituloContract.TABLE_NAME+"."+
                    CapituloContract.COLUMN_NAME_CAPITULO_ID +","+
                    CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE+","+
                    CapitulosVistosContract.COLUMN_NAME_USUARIO_ID+" ";
            query +=
                    "FROM " + CapituloContract.TABLE_NAME+" LEFT OUTER JOIN "+
                    CapitulosVistosContract.TABLE_NAME +" ON "+ CapitulosVistosContract.TABLE_NAME+"."+
                    CapitulosVistosContract.COLUMN_NAME_CAPITULO_ID+"="+ CapituloContract.TABLE_NAME+"."+
                    CapituloContract.COLUMN_NAME_CAPITULO_ID+" AND " + CapitulosVistosContract.TABLE_NAME+"."+ CapitulosVistosContract.COLUMN_NAME_TEMPORADA_ID+"="
                    + CapituloContract.TABLE_NAME+"."+ CapituloContract.COLUMN_NAME_TEMPORADA_ID+" AND " + CapitulosVistosContract.TABLE_NAME+"."
                            + CapitulosVistosContract.COLUMN_NAME_SERIE_ID+"=" + CapituloContract.TABLE_NAME+"."+ CapituloContract.COLUMN_NAME_SERIE_ID+" AND "
                    + CapitulosVistosContract.COLUMN_NAME_USUARIO_ID+"=?";

            query +=
                    "WHERE "+ CapituloContract.TABLE_NAME+"."+ CapituloContract.COLUMN_NAME_TEMPORADA_ID+"=? AND "
                            + CapituloContract.TABLE_NAME+"."+ CapituloContract.COLUMN_NAME_SERIE_ID +"=? ";
            query += "ORDER BY 1";

            Cursor c = db.rawQuery(query, new String[]{Integer.toString(Sesion.getInstance().getIdUsuario()),
                    Integer.toString(idTemporada),Integer.toString(idSerie)});
            return c;
        }
        else return null;
    }




    public Cursor consultarInfoCapitulo(int idTemporada, int idSerie, int idCap){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query = "SELECT " + CapituloContract.COLUMN_NAME_CAPITULO_ID +","+
                    CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE+" ";
            query +=
                    "FROM " + CapituloContract.TABLE_NAME+" ";
            query +=
                    "WHERE "+ CapituloContract.COLUMN_NAME_TEMPORADA_ID+"=? AND "
                            + CapituloContract.COLUMN_NAME_SERIE_ID +"=? AND "
                            + CapituloContract.COLUMN_NAME_CAPITULO_ID+"=?";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idTemporada),Integer.toString(idSerie),
                    Integer.toString(idCap)});
            return c;
        }
        else return null;
    }

    public boolean insertarTemporada(int id_serie, int  idTemporada){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TemporadaContract.COLUMN_NAME_PROGRAMA_ID, id_serie);
            values.put(TemporadaContract.COLUMN_NAME_TEMPORADA_ID, idTemporada);

            long rowide= db.insert(TemporadaContract.TABLE_NAME, null, values);
            if (rowide > 0) return true;
        }
        return false;
    }

}