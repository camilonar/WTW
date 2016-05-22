package epiphany_soft.wtw.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;


public class DataBaseUsuarioInicioSesion {

    DataBaseHelper miDBHelper;



    public DataBaseUsuarioInicioSesion(Context context) {

        miDBHelper = new DataBaseHelper(context);

    }


    public Cursor consultarSesion(String nombreUsu, String password){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query = "SELECT " + DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_ID +","+
                    DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_NOMBRE+" ";
            query +=
                    "FROM " + DataBaseContract.UsuarioContract.TABLE_NAME+" ";
            query +=
                    "WHERE "+ DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_NOMBRE+"=? AND "
                            + DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_PASSWORD+"=?";
            Cursor c = db.rawQuery(query, new String[]{nombreUsu, password});
            return c;
        }
        else return null;
    }

    public boolean AgregarUsuario(String nombre, String contrasenia){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_NOMBRE, nombre);
            values.put(DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_PASSWORD, contrasenia);
            long rowid=db.insert(DataBaseContract.UsuarioContract.TABLE_NAME, null, values);
            if (rowid>0) return true;
        }
        return false;
    }


    public boolean actualizarUsuario(String nombre, String contrasenia){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_NOMBRE, nombre);
            values.put(DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_PASSWORD,contrasenia);
            String query= DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_NOMBRE+"=?";
            int rowid=db.update(DataBaseContract.UsuarioContract.TABLE_NAME, values, query, new String[]{nombre});
            if (rowid>0) return true;
        }
        return false;
    }
}