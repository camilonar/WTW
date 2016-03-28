package epiphany_soft.wtw.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

/**
 * Created by Camilo on 23/03/2016.
 */
public class DataBaseConnection {
    DataBaseHelper miDBHelper;
    public DataBaseConnection (Context context){
        miDBHelper = new DataBaseHelper(context);
    }
    public long insertPrueba(int id, String nombre)
    {
        SQLiteDatabase db = miDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_ID, id);
        values.put(DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_NOMBRE, nombre);
        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                DataBaseContract.GeneroContract.TABLE_NAME,
                null,
                values);
        return newRowId;
    }

    public String pruebaInsercionGenero(int id_gen){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }

        if(miDBHelper.checkDataBase()){
            long rowId=insertPrueba(id_gen,"comedia");
            miDBHelper.close();

            if (rowId<0) return null;
            return Long.toString(rowId);
        }else{
            return null;
        }
    }

    public Cursor consultarAllGeneros(){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " +DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_ID+","+
                            DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_NOMBRE+" ";
            query+= "FROM "+ DataBaseContract.GeneroContract.TABLE_NAME;
            Cursor c=db.rawQuery(query,null);
            return c;
        }
        return null;
    }
    public Cursor consultarPeliculaLikeNombre(String nombre){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + "," +
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "," +
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS + " ";
            query +=
                    "FROM " + DataBaseContract.ProgramaContract.TABLE_NAME + " JOIN " +
                            DataBaseContract.PeliculaContract.TABLE_NAME +
                            " ON " + DataBaseContract.PeliculaContract.COLUMN_NAME_PELICULA_ID + "="
                            + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
           query +=
                    "WHERE " + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + " LIKE \'%"+nombre+"%\'";
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
                    "SELECT " + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + "," +
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE + "," +
                            DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS +","+
                            DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_NOMBRE+" ";
            query +=
                    "FROM " + DataBaseContract.ProgramaContract.TABLE_NAME+" NATURAL JOIN "+
                            DataBaseContract.GeneroContract.TABLE_NAME+ " JOIN " +
                            DataBaseContract.PeliculaContract.TABLE_NAME +
                            " ON " + DataBaseContract.PeliculaContract.COLUMN_NAME_PELICULA_ID + "="
                            + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID + " ";
            query +=
                    "WHERE " + DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE +"=\'"+nombre+"\'";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }

    public boolean insertarGenero(String nombre){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_NOMBRE, nombre);
            long rowid=db.insert(DataBaseContract.GeneroContract.TABLE_NAME, null, values);
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
                    "SELECT "+ DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_ID+","+
                            DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_NOMBRE+" ";
            query+=
                    "FROM "+ DataBaseContract.GeneroContract.TABLE_NAME+" ";
            query+=
                    "WHERE "+DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_ID + " NOT IN ("+
                            "SELECT "+ DataBaseContract.ProgramaContract.COLUMN_NAME_GENERO_ID+" FROM "+
                            DataBaseContract.ProgramaContract.TABLE_NAME+")";
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
            String query= DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_ID+"=?";
            int numDel=db.delete(DataBaseContract.GeneroContract.TABLE_NAME,query,new String[]{Integer.toString(id)});
            if (numDel>0) return true;
        }
        return false;
    }
}
