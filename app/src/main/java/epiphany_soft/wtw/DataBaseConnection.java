package epiphany_soft.wtw;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Camilo on 23/03/2016.
 */
public class DataBaseConnection {
    DataBaseHelper miDBHelper;
    public DataBaseConnection (Context context){
        miDBHelper = new DataBaseHelper(context);
    }
    public void insertPrueba(String id, String nombre, String password)
    {
        SQLiteDatabase db = miDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_ID, id);
        values.put(DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_NOMBRE, nombre);
        values.put(DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_PASSWORD, password);
        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                DataBaseContract.UsuarioContract.TABLE_NAME,
                null,
                values);
        System.out.println(newRowId);
    }
}
