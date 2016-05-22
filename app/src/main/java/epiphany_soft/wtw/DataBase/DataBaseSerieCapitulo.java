package epiphany_soft.wtw.DataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;


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
            values.put(DataBaseContract.SerieContract.COLUMN_NAME_SERIE_ID, id);
            long rowide= db.insert(DataBaseContract.SerieContract.TABLE_NAME, null, values);
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
                    "SELECT " + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + "," +
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "," +
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS + ","+
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO + "," +
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN + " ";
            query +=
                    "FROM " + DataBaseContract.ProgramaContract.TABLE_NAME + " JOIN " +
                            DataBaseContract.SerieContract.TABLE_NAME +
                            " ON " + DataBaseContract.SerieContract.COLUMN_NAME_SERIE_ID + "="
                            + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
            query +=
                    "WHERE " + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + " LIKE \'%"+nombre+"%\'";
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
                    "SELECT " + DataBaseContract.ProgramaContract.TABLE_NAME+"."+ DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + "," +
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "," +
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS + ","+
                            DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_NOMBRE +","+
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO + "," +
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN + ","+
                            DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID+" ";
            query +=
                    "FROM " + DataBaseContract.ProgramaContract.TABLE_NAME+" LEFT OUTER JOIN "+
                            DataBaseContract.AgendaContract.TABLE_NAME +" ON "+ DataBaseContract.ProgramaContract.TABLE_NAME+"."+
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID+"="+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_PROGRAMA_ID+" NATURAL JOIN "+
                            DataBaseContract.GeneroContract.TABLE_NAME+ " JOIN " +
                            DataBaseContract.SerieContract.TABLE_NAME +
                            " ON " + DataBaseContract.SerieContract.COLUMN_NAME_SERIE_ID + "="
                            + DataBaseContract.ProgramaContract.TABLE_NAME+"."+ DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID +" ";
            ;
            query +=
                    "WHERE " + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE +"=\'"+nombre+"\'";
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
                    "SELECT " + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + "," +
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "," +
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS + ","+
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO + "," +
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN + " ";
            query +=
                    "FROM " + DataBaseContract.DiaHorarioContract.TABLE_NAME+" NATURAL JOIN "+
                            DataBaseContract.HorarioContract.TABLE_NAME+" NATURAL JOIN "+
                            DataBaseContract.ProgramaContract.TABLE_NAME + " JOIN " +
                            DataBaseContract.PeliculaContract.TABLE_NAME +
                            " ON " + DataBaseContract.SerieContract.COLUMN_NAME_SERIE_ID + "="
                            + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
            query +=
                    "WHERE " + DataBaseContract.DiaHorarioContract.COLUMN_NAME_DIA_ID + "=?";
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
            values.put(DataBaseContract.CapituloContract.COLUMN_NAME_CAPITULO_ID, id_cap);
            values.put(DataBaseContract.CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE,nombreCap);
            values.put(DataBaseContract.CapituloContract.COLUMN_NAME_TEMPORADA_ID, id_temp);
            values.put(DataBaseContract.CapituloContract.COLUMN_NAME_SERIE_ID, id_ser);

            long rowide= db.insert(DataBaseContract.CapituloContract.TABLE_NAME, null, values);
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
                    "SELECT " + DataBaseContract.TemporadaContract.TABLE_NAME+"."+ DataBaseContract.TemporadaContract.COLUMN_NAME_TEMPORADA_ID + ","
                            +"COUNT("+ DataBaseContract.CapituloContract.TABLE_NAME+"."+ DataBaseContract.CapituloContract.COLUMN_NAME_TEMPORADA_ID+") AS cuenta ";
            query +=
                    "FROM " + DataBaseContract.TemporadaContract.TABLE_NAME+" LEFT OUTER JOIN "+ DataBaseContract.CapituloContract.TABLE_NAME +
                            " ON "+ DataBaseContract.TemporadaContract.TABLE_NAME+"."+ DataBaseContract.TemporadaContract.COLUMN_NAME_TEMPORADA_ID
                            +"="+ DataBaseContract.CapituloContract.TABLE_NAME+"."+ DataBaseContract.CapituloContract.COLUMN_NAME_TEMPORADA_ID+" AND "
                            + DataBaseContract.TemporadaContract.TABLE_NAME+"."+ DataBaseContract.TemporadaContract.COLUMN_NAME_PROGRAMA_ID+"="
                            + DataBaseContract.CapituloContract.TABLE_NAME+"."+ DataBaseContract.CapituloContract.COLUMN_NAME_SERIE_ID+" ";
            query +=
                    "WHERE " + DataBaseContract.TemporadaContract.TABLE_NAME+"."+ DataBaseContract.TemporadaContract.COLUMN_NAME_PROGRAMA_ID +"=? ";
            query+=
                    "GROUP BY "+ DataBaseContract.TemporadaContract.TABLE_NAME+"."+ DataBaseContract.TemporadaContract.COLUMN_NAME_TEMPORADA_ID;
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
            values.put(DataBaseContract.CapituloContract.COLUMN_NAME_CAPITULO_ID, id_cap_new);
            values.put(DataBaseContract.CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE,nombreCap);
            values.put(DataBaseContract.CapituloContract.COLUMN_NAME_TEMPORADA_ID, id_temp);
            values.put(DataBaseContract.CapituloContract.COLUMN_NAME_SERIE_ID, id_ser);

            String query= DataBaseContract.CapituloContract.COLUMN_NAME_SERIE_ID+"=? AND "
                    + DataBaseContract.CapituloContract.COLUMN_NAME_TEMPORADA_ID+"=? AND "
                    + DataBaseContract.CapituloContract.COLUMN_NAME_CAPITULO_ID+"=?";
            String[] compare = new String[]{Integer.toString(id_ser),Integer.toString(id_temp),Integer.toString(id_cap_old)};
            try{
                int rowid=db.update(DataBaseContract.CapituloContract.TABLE_NAME, values, query, compare);
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
            String query = "SELECT " + DataBaseContract.CapituloContract.COLUMN_NAME_CAPITULO_ID +","+
                    DataBaseContract.CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE+" ";
            query +=
                    "FROM " + DataBaseContract.CapituloContract.TABLE_NAME+" ";
            query +=
                    "WHERE "+ DataBaseContract.CapituloContract.COLUMN_NAME_TEMPORADA_ID+"=? AND " + DataBaseContract.CapituloContract.COLUMN_NAME_SERIE_ID +"=? ";
            query += "ORDER BY "+ DataBaseContract.CapituloContract.COLUMN_NAME_CAPITULO_ID;

            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idTemporada),Integer.toString(idSerie)});
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
            String query = "SELECT " + DataBaseContract.CapituloContract.COLUMN_NAME_CAPITULO_ID +","+
                    DataBaseContract.CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE+" ";
            query +=
                    "FROM " + DataBaseContract.CapituloContract.TABLE_NAME+" ";
            query +=
                    "WHERE "+ DataBaseContract.CapituloContract.COLUMN_NAME_TEMPORADA_ID+"=? AND "
                            + DataBaseContract.CapituloContract.COLUMN_NAME_SERIE_ID +"=? AND "
                            + DataBaseContract.CapituloContract.COLUMN_NAME_CAPITULO_ID+"=?";
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
            values.put(DataBaseContract.TemporadaContract.COLUMN_NAME_PROGRAMA_ID, id_serie);
            values.put(DataBaseContract.TemporadaContract.COLUMN_NAME_TEMPORADA_ID, idTemporada);

            long rowide= db.insert(DataBaseContract.TemporadaContract.TABLE_NAME, null, values);
            if (rowide > 0) return true;
        }
        return false;
    }

}