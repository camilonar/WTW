package epiphany_soft.wtw.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;


public class DataBaseCalificacion {

    DataBaseHelper miDBHelper;

    public DataBaseCalificacion(Context context) {
        miDBHelper = new DataBaseHelper(context);
    }


    public Cursor consultarCalificacion(int idUsuario, int idPrograma){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query = "SELECT " + DataBaseContract.CalificacionContract.COLUMN_NAME_VALOR_CALIFICACION+" ";
            query +=
                    "FROM " + DataBaseContract.CalificacionContract.TABLE_NAME+" ";
            query +=
                    "WHERE "+ DataBaseContract.CalificacionContract.COLUMN_NAME_USUARIO_ID+"=? AND "
                            + DataBaseContract.CalificacionContract.COLUMN_NAME_PROGRAMA_ID +"=? ";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idUsuario), Integer.toString(idPrograma)});
            return c;
        }
        else return null;
    }

    public boolean insertarCalificacion(int idUsuario, int idPrograma, float calificacion){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DataBaseContract.CalificacionContract.COLUMN_NAME_USUARIO_ID, idUsuario);
            values.put(DataBaseContract.CalificacionContract.COLUMN_NAME_PROGRAMA_ID, idPrograma);
            values.put(DataBaseContract.CalificacionContract.COLUMN_NAME_VALOR_CALIFICACION,calificacion);
            long rowid=db.insert(DataBaseContract.CalificacionContract.TABLE_NAME, null, values);
            if (rowid>0) return true;
        }
        return false;
    }

    public boolean actualizarCalificacion(int idUsuario, int idPrograma, float calificacion){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DataBaseContract.CalificacionContract.COLUMN_NAME_USUARIO_ID, idUsuario);
            values.put(DataBaseContract.CalificacionContract.COLUMN_NAME_PROGRAMA_ID, idPrograma);
            values.put(DataBaseContract.CalificacionContract.COLUMN_NAME_VALOR_CALIFICACION, calificacion);

            String query = DataBaseContract.CalificacionContract.COLUMN_NAME_USUARIO_ID+"=? AND "
                    + DataBaseContract.CalificacionContract.COLUMN_NAME_PROGRAMA_ID +"=? ";
            String[] compare = new String[]{Integer.toString(idUsuario), Integer.toString(idPrograma)};
            try {
                int rowid = db.update(DataBaseContract.CalificacionContract.TABLE_NAME, values, query, compare);
                if (rowid > 0) return true;
            } catch (Exception e) {
                //No se hace nada, y luego retorna falso
            }
        }
        return false;
    }


    public float consultarCalificacionPromedio(int idPrograma){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query = "SELECT AVG(" + DataBaseContract.CalificacionContract.COLUMN_NAME_VALOR_CALIFICACION+") AS "+
                    DataBaseContract.CalificacionContract.COLUMN_NAME_VALOR_CALIFICACION+","+
                    "COUNT(" + DataBaseContract.CalificacionContract.COLUMN_NAME_USUARIO_ID+") AS cuenta ";
            query +=
                    "FROM " + DataBaseContract.CalificacionContract.TABLE_NAME+" ";
            query +=
                    "WHERE " + DataBaseContract.CalificacionContract.COLUMN_NAME_PROGRAMA_ID +"=? ";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idPrograma)});
            c.moveToNext();
            if (c.getInt(c.getColumnIndex("cuenta"))!=0);
            return c.getFloat(c.getColumnIndex(DataBaseContract.CalificacionContract.COLUMN_NAME_VALOR_CALIFICACION));
        }
        return 0;
    }

}