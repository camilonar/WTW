package epiphany_soft.wtw.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import static epiphany_soft.wtw.DataBase.DataBaseContract.GeneroContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.PeliculaContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.ProgramaContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.SerieContract;


public class DataBaseGenero {

    DataBaseHelper miDBHelper;

    public DataBaseGenero(Context context){

        miDBHelper = new DataBaseHelper(context);
    }


    public Cursor consultarAllGeneros(){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + GeneroContract.COLUMN_NAME_GENERO_ID+","+
                            GeneroContract.COLUMN_NAME_GENERO_NOMBRE+" ";
            query+= "FROM "+ GeneroContract.TABLE_NAME;
            Cursor c=db.rawQuery(query,null);
            return c;
        }
        return null;
    }

    public boolean insertarGenero(String nombre){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(GeneroContract.COLUMN_NAME_GENERO_NOMBRE, nombre);
            long rowid=db.insert(GeneroContract.TABLE_NAME, null, values);
            if (rowid>0) return true;
        }
        return false;
    }

    public Cursor getGenerosNoUsados(){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT "+ GeneroContract.COLUMN_NAME_GENERO_ID+","+
                            GeneroContract.COLUMN_NAME_GENERO_NOMBRE+" ";
            query+=
                    "FROM "+ GeneroContract.TABLE_NAME+" ";
            query+=
                    "WHERE "+ GeneroContract.COLUMN_NAME_GENERO_ID + " NOT IN ("+
                            "SELECT "+ ProgramaContract.COLUMN_NAME_GENERO_ID+" FROM "+
                            ProgramaContract.TABLE_NAME+")";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }

    public boolean eliminarGenero(int id){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            String query= GeneroContract.COLUMN_NAME_GENERO_ID+"=?";
            int numDel=db.delete(GeneroContract.TABLE_NAME, query, new String[]{Integer.toString(id)});
            if (numDel>0) return true;
        }
        return false;
    }



    public Cursor consultarPeliculaPorGenero(String genero){
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
                    "FROM " +GeneroContract.TABLE_NAME+" NATURAL JOIN "+
                            ProgramaContract.TABLE_NAME + " JOIN " +
                            PeliculaContract.TABLE_NAME +
                            " ON " + PeliculaContract.COLUMN_NAME_PELICULA_ID + "="
                            + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
            query +=
                    "WHERE " + GeneroContract.COLUMN_NAME_GENERO_NOMBRE + " LIKE \'%"+genero+"%\'";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }

    public Cursor consultarSeriePorGenero(String genero){
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
                    "FROM " +GeneroContract.TABLE_NAME+" NATURAL JOIN "+
                            ProgramaContract.TABLE_NAME + " JOIN " +
                            SerieContract.TABLE_NAME +
                            " ON " + SerieContract.COLUMN_NAME_SERIE_ID + "="
                            + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
            query +=
                    "WHERE " + GeneroContract.COLUMN_NAME_GENERO_NOMBRE + " LIKE \'%"+genero+"%\'";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }


    public Cursor consultarPeliculasAndFavoritosPorGenero(String genero, int idUsuario){
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
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO + "," +
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN + ","+
                            DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID+" ";
            query +=
                    "FROM " + DataBaseContract.GeneroContract.TABLE_NAME+" NATURAL JOIN "+
                            DataBaseContract.ProgramaContract.TABLE_NAME + " JOIN " +
                            DataBaseContract.PeliculaContract.TABLE_NAME +
                            " ON " + DataBaseContract.PeliculaContract.COLUMN_NAME_PELICULA_ID + "="
                            + DataBaseContract.ProgramaContract.TABLE_NAME+"."+ DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " LEFT OUTER JOIN "+
                            DataBaseContract.AgendaContract.TABLE_NAME+" ON "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_PROGRAMA_ID+"="+ DataBaseContract.ProgramaContract.TABLE_NAME+"."+
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID+" AND "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID+"=? ";
            query +=
                    "WHERE " + DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_NOMBRE + " LIKE \'%"+genero+"%\'";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idUsuario)});
            return c;
        }
        else return null;
    }


    public Cursor consultarSeriesAndFavoritosPorGenero(String genero, int idUsuario){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + ProgramaContract.TABLE_NAME+"."+ProgramaContract.COLUMN_NAME_PROGRAMA_ID + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS + ","+
                            ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN + ","+
                            DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID+" ";
            query +=
                    "FROM " + GeneroContract.TABLE_NAME+" NATURAL JOIN "+
                            ProgramaContract.TABLE_NAME + " JOIN " +
                            SerieContract.TABLE_NAME +
                            " ON " + SerieContract.COLUMN_NAME_SERIE_ID + "="
                            + ProgramaContract.TABLE_NAME+"."+ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " LEFT OUTER JOIN "+
                            DataBaseContract.AgendaContract.TABLE_NAME+" ON "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_PROGRAMA_ID+"="+ ProgramaContract.TABLE_NAME+"."+
                            ProgramaContract.COLUMN_NAME_PROGRAMA_ID+" AND "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID+"=? ";
            query +=
                    "WHERE " + GeneroContract.COLUMN_NAME_GENERO_NOMBRE + " LIKE \'%"+genero+"%\'";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idUsuario)});
            return c;
        }
        else return null;
    }









}
