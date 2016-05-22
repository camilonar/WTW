package epiphany_soft.wtw.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;


public class DataBaseCanal {

    DataBaseHelper miDBHelper;



    public DataBaseCanal(Context context) {

        miDBHelper = new DataBaseHelper(context);

    }

    public boolean insertarCanal(String nombreCanal){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DataBaseContract.CanalContract.COLUMN_NAME_CANAL_ID,nombreCanal);
            long rowid=db.insert(DataBaseContract.CanalContract.TABLE_NAME, null, values);
            if (rowid>0) return true;
        }
        return false;
    }


    public Cursor consultarCanalesDePrograma(int idPrograma){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query = "SELECT " + DataBaseContract.HorarioContract.COLUMN_NAME_CANAL_ID+" ";
            query +=
                    "FROM " + DataBaseContract.HorarioContract.TABLE_NAME+" ";
            query +=
                    "WHERE " + DataBaseContract.HorarioContract.COLUMN_NAME_PROGRAMA_ID+"=?";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idPrograma)});
            return c;
        }
        else return null;
    }


    public Cursor consultarCanalLikeNombre(String nombre){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + DataBaseContract.CanalContract.COLUMN_NAME_CANAL_ID + " ";
            query +=
                    "FROM " + DataBaseContract.CanalContract.TABLE_NAME + " ";
            query +=
                    "WHERE " + DataBaseContract.CanalContract.COLUMN_NAME_CANAL_ID + " LIKE \'%"+nombre+"%\'";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }


    public boolean eliminarCanal(String  NombreCanal){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            String query= DataBaseContract.CanalContract.COLUMN_NAME_CANAL_ID+"=?";
            int numDel=db.delete(DataBaseContract.CanalContract.TABLE_NAME, query, new String[]{(NombreCanal)});
            if (numDel>0) return true;
        }
        return false;
    }


    public boolean actualizarCanal(String  NombreCanal_old, String NombreCanal_new){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DataBaseContract.CanalContract.COLUMN_NAME_CANAL_ID, NombreCanal_new) ;

            String query= DataBaseContract.CanalContract.COLUMN_NAME_CANAL_ID+"=?";
            String[] compare = new String[]{(NombreCanal_old)};
            try{
                int rowid=db.update(DataBaseContract.CanalContract.TABLE_NAME, values, query, compare);
                if (rowid>0) return true;
            } catch (Exception e){
                //No se hace nada, y luego retorna falso
            }
        }
        return false;
    }


    public Cursor consultarPeliculaPorCanal(String canal){
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
                    "FROM " + DataBaseContract.HorarioContract.TABLE_NAME+" NATURAL JOIN "+
                            DataBaseContract.ProgramaContract.TABLE_NAME + " JOIN " +
                            DataBaseContract.PeliculaContract.TABLE_NAME +
                            " ON " + DataBaseContract.PeliculaContract.COLUMN_NAME_PELICULA_ID + "="
                            + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
            query +=
                    "WHERE " + DataBaseContract.HorarioContract.COLUMN_NAME_CANAL_ID +  " LIKE \'%"+canal+"%\'";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }

    public Cursor consultarPeliculasAndFavoritosPorCanal(String canal, int idUsuario){
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
                            + DataBaseContract.ProgramaContract.TABLE_NAME+"."+ DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " LEFT OUTER JOIN "+
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

    public Cursor consultarSeriePorCanal(String canal){
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
                    "FROM " + DataBaseContract.HorarioContract.TABLE_NAME+" NATURAL JOIN "+
                            DataBaseContract.ProgramaContract.TABLE_NAME + " JOIN " +
                            DataBaseContract.SerieContract.TABLE_NAME +
                            " ON " + DataBaseContract.PeliculaContract.COLUMN_NAME_PELICULA_ID + "="
                            + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
            query +=
                    "WHERE " + DataBaseContract.HorarioContract.COLUMN_NAME_CANAL_ID +  " LIKE \'%"+canal+"%\'";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }
    public Cursor consultarSeriesAndFavoritosPorCanal(String canal, int idUsuario){
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
                            + DataBaseContract.ProgramaContract.TABLE_NAME+"."+ DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " LEFT OUTER JOIN "+
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