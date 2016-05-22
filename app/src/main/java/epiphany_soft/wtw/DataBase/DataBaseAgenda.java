package epiphany_soft.wtw.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;


public class DataBaseAgenda {

    DataBaseHelper miDBHelper;



    public DataBaseAgenda(Context context) {

        miDBHelper = new DataBaseHelper(context);
    }

    public boolean insertarFavorito(int idUsuario, int idPrograma){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID, idUsuario);
            values.put(DataBaseContract.AgendaContract.COLUMN_NAME_PROGRAMA_ID, idPrograma);
            long rowid=db.insert(DataBaseContract.AgendaContract.TABLE_NAME, null, values);
            if (rowid>0) return true;
        }
        return false;
    }

    public boolean eliminarFavorito(int idUsuario, int idPrograma){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            String query= DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID+"=? AND "+
                    DataBaseContract.AgendaContract.COLUMN_NAME_PROGRAMA_ID+"=?";
            int numDel=db.delete(DataBaseContract.AgendaContract.TABLE_NAME, query,
                    new String[]{Integer.toString(idUsuario), Integer.toString(idPrograma)});
            if (numDel>0) return true;
        }
        return false;
    }

    public Cursor consultarPeliculasAndFavoritos(String nombre, int idUsuario){
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
                    "FROM " + DataBaseContract.ProgramaContract.TABLE_NAME + " JOIN " +
                            DataBaseContract.PeliculaContract.TABLE_NAME +
                            " ON " + DataBaseContract.PeliculaContract.COLUMN_NAME_PELICULA_ID + "="
                            + DataBaseContract.ProgramaContract.TABLE_NAME+"."+ DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " LEFT OUTER JOIN "+
                            DataBaseContract.AgendaContract.TABLE_NAME+" ON "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_PROGRAMA_ID+"="+ DataBaseContract.ProgramaContract.TABLE_NAME+"."+
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID+" AND "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID+"=? ";
            query +=
                    "WHERE " + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + " LIKE \'%"+nombre+"%\'";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idUsuario)});
            return c;
        }
        else return null;
    }

    public Cursor consultarSeriesAndFavoritos(String nombre, int idUsuario){
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
                    "FROM " + DataBaseContract.ProgramaContract.TABLE_NAME + " JOIN " +
                            DataBaseContract.SerieContract.TABLE_NAME +
                            " ON " + DataBaseContract.SerieContract.COLUMN_NAME_SERIE_ID + "="
                            + DataBaseContract.ProgramaContract.TABLE_NAME+"."+ DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " LEFT OUTER JOIN "+
                            DataBaseContract.AgendaContract.TABLE_NAME+" ON "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_PROGRAMA_ID+"="+ DataBaseContract.ProgramaContract.TABLE_NAME+"."+
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID+" AND "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID+"=? ";
            query +=
                    "WHERE " + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + " LIKE \'%"+nombre+"%\'";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idUsuario)});
            return c;
        }
        else return null;
    }


    public Cursor consultarPeliculasDeAgenda(String nombre, int idUsuario){
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
                    "FROM " + DataBaseContract.ProgramaContract.TABLE_NAME + " JOIN " +
                            DataBaseContract.PeliculaContract.TABLE_NAME +
                            " ON " + DataBaseContract.PeliculaContract.COLUMN_NAME_PELICULA_ID + "="
                            + DataBaseContract.ProgramaContract.TABLE_NAME+"."+ DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " JOIN "+
                            DataBaseContract.AgendaContract.TABLE_NAME+" ON "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_PROGRAMA_ID+"="+ DataBaseContract.ProgramaContract.TABLE_NAME+"."+
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID+" AND "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID+"=? ";
            query +=
                    "WHERE " + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + " LIKE \'%"+nombre+"%\'";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idUsuario)});
            return c;
        }
        else return null;
    }

    public Cursor consultarSeriesDeAgenda(String nombre, int idUsuario){
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
                    "FROM " + DataBaseContract.ProgramaContract.TABLE_NAME + " JOIN " +
                            DataBaseContract.SerieContract.TABLE_NAME +
                            " ON " + DataBaseContract.SerieContract.COLUMN_NAME_SERIE_ID + "="
                            + DataBaseContract.ProgramaContract.TABLE_NAME+"."+ DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " JOIN "+
                            DataBaseContract.AgendaContract.TABLE_NAME+" ON "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_PROGRAMA_ID+"="+ DataBaseContract.ProgramaContract.TABLE_NAME+"."+
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID+" AND "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID+"=? ";
            query +=
                    "WHERE " + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + " LIKE \'%"+nombre+"%\'";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idUsuario)});
            return c;
        }
        else return null;
    }


// informcion de la agenda

    public Cursor consultarPeliculasDeAgendaPorGenero(String genero, int idUsuario){
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
                            + DataBaseContract.ProgramaContract.TABLE_NAME+"."+ DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " JOIN "+
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

    public Cursor consultarSeriesDeAgendaPorGenero(String genero, int idUsuario){
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
                            DataBaseContract.SerieContract.TABLE_NAME +
                            " ON " + DataBaseContract.SerieContract.COLUMN_NAME_SERIE_ID + "="
                            + DataBaseContract.ProgramaContract.TABLE_NAME+"."+ DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " JOIN "+
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

    public Cursor consultarPeliculasDeAgendaPorCanal(String canal, int idUsuario){
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
                    "FROM " + DataBaseContract.HorarioContract.TABLE_NAME+" NATURAL JOIN "+
                            DataBaseContract.ProgramaContract.TABLE_NAME + " JOIN " +
                            DataBaseContract.PeliculaContract.TABLE_NAME +
                            " ON " + DataBaseContract.PeliculaContract.COLUMN_NAME_PELICULA_ID + "="
                            + DataBaseContract.ProgramaContract.TABLE_NAME+"."+ DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " JOIN "+
                            DataBaseContract.AgendaContract.TABLE_NAME+" ON "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_PROGRAMA_ID+"="+ DataBaseContract.ProgramaContract.TABLE_NAME+"."+
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID+" AND "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID+"=? ";
            query +=
                    "WHERE " + DataBaseContract.HorarioContract.COLUMN_NAME_CANAL_ID + " LIKE \'%"+canal+"%\'";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idUsuario)});
            return c;
        }
        else return null;
    }

    public Cursor consultarSeriesDeAgendaPorCanal(String canal, int idUsuario){
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
                    "FROM " + DataBaseContract.HorarioContract.TABLE_NAME+" NATURAL JOIN "+
                            DataBaseContract.ProgramaContract.TABLE_NAME + " JOIN " +
                            DataBaseContract.SerieContract.TABLE_NAME +
                            " ON " + DataBaseContract.SerieContract.COLUMN_NAME_SERIE_ID + "="
                            + DataBaseContract.ProgramaContract.TABLE_NAME+"."+ DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " JOIN "+
                            DataBaseContract.AgendaContract.TABLE_NAME+" ON "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_PROGRAMA_ID+"="+ DataBaseContract.ProgramaContract.TABLE_NAME+"."+
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID+" AND "+ DataBaseContract.AgendaContract.TABLE_NAME+"."+
                            DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID+"=? ";
            query +=
                    "WHERE " + DataBaseContract.HorarioContract.COLUMN_NAME_CANAL_ID + " LIKE \'%"+canal+"%\'";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idUsuario)});
            return c;
        }
        else return null;
    }



}