package epiphany_soft.wtw;

import android.content.ContentValues;
import android.content.Context;
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

}
