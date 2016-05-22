package epiphany_soft.wtw.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import epiphany_soft.wtw.DataBase.DataBaseContract.AgendaContract;
import epiphany_soft.wtw.DataBase.DataBaseContract.HorarioContract;
import epiphany_soft.wtw.Negocio.Horario;

import static epiphany_soft.wtw.DataBase.DataBaseContract.CalificacionContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.CanalContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.CapituloContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.DiaContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.DiaHorarioContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.EmisoraContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.EmiteContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.GeneroContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.PeliculaContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.ProgramaContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.SerieContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.TemporadaContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.UsuarioContract;




public class DataBaseProgramaPelicula {

    DataBaseHelper miDBHelper;



    public DataBaseProgramaPelicula(Context context) {

        miDBHelper = new DataBaseHelper(context);

    }

    public Cursor consultarPeliculaLikeNombre(String nombre){
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
                            PeliculaContract.TABLE_NAME +
                            " ON " + PeliculaContract.COLUMN_NAME_PELICULA_ID + "="
                            + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
            query +=
                    "WHERE " + ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + " LIKE \'%"+nombre+"%\'";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }
    /**@param nombre es el nombre con el que se realizará la búsqueda de la película
     * @return la pelicula cuyo nombre es nombre*/
    public Cursor consultarPeliculaPorNombre(String nombre){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " +ProgramaContract.TABLE_NAME+"."+ProgramaContract.COLUMN_NAME_PROGRAMA_ID + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS + ","+
                            GeneroContract.COLUMN_NAME_GENERO_NOMBRE +","+
                            ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO + "," +
                            ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN + "," +
                            AgendaContract.COLUMN_NAME_USUARIO_ID+" ";
            query +=
                    "FROM " + ProgramaContract.TABLE_NAME+" NATURAL JOIN "+
                            GeneroContract.TABLE_NAME+ " JOIN " +
                            PeliculaContract.TABLE_NAME +
                            " ON " + PeliculaContract.COLUMN_NAME_PELICULA_ID + "="
                            +ProgramaContract.TABLE_NAME+"."+ProgramaContract.COLUMN_NAME_PROGRAMA_ID +
                            " LEFT OUTER JOIN "+
                            AgendaContract.TABLE_NAME +" ON "+ProgramaContract.TABLE_NAME+"."+
                            ProgramaContract.COLUMN_NAME_PROGRAMA_ID+"="+AgendaContract.TABLE_NAME+"."+
                            AgendaContract.COLUMN_NAME_PROGRAMA_ID+" ";
            query +=
                    "WHERE " + ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE +"=\'"+nombre+"\'";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }




    public int consultarId_Programa (String nombre ) {
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + ProgramaContract.COLUMN_NAME_PROGRAMA_ID +" ";
            query +=
                    "FROM " + ProgramaContract.TABLE_NAME+" ";

            query +=
                    "WHERE " + ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "=\'" + nombre + "\'";
            Cursor c = db.rawQuery(query, null);
            if (c.moveToNext()){
                return c.getInt(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_ID));
            }
        }
        return -1;
    }

    public boolean insertarPrograma( int id_gen, String nombre, String sinopsis, int anio, String pais) {
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ProgramaContract.COLUMN_NAME_GENERO_ID, id_gen);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE, nombre);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS, sinopsis);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO, anio);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN, pais);
            long rowide= db.insert(ProgramaContract.TABLE_NAME, null, values);
            if (rowide > 0) return true;
        }
        return false;

    }

    public boolean insertarPelicula(int  id) {
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PeliculaContract.COLUMN_NAME_PELICULA_ID, id);
            long rowide= db.insert(PeliculaContract.TABLE_NAME, null, values);
            if (rowide > 0) return true;
        }
        return false;
    }


    public boolean actualizarPrograma(int id_gen, String nombre, String sinopsis, int anio, String pais){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ProgramaContract.COLUMN_NAME_GENERO_ID, id_gen);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE,nombre);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS, sinopsis);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO, anio);
            values.put(ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN, pais);

            String query= ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE+"=?";
            int rowid=db.update(ProgramaContract.TABLE_NAME, values, query, new String[]{nombre});
            if (rowid>0) return true;
        }
        return false;
    }

    public Cursor consultarPeliculaPorDia(int idDia){
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
                    "FROM " +DiaHorarioContract.TABLE_NAME+" NATURAL JOIN "+
                            HorarioContract.TABLE_NAME+" NATURAL JOIN "+
                            ProgramaContract.TABLE_NAME + " JOIN " +
                            PeliculaContract.TABLE_NAME +
                            " ON " + PeliculaContract.COLUMN_NAME_PELICULA_ID + "="
                            + ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
            query +=
                    "WHERE " + DiaHorarioContract.COLUMN_NAME_DIA_ID + "=?";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idDia)});
            return c;
        }
        else return null;
    }





}