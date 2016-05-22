package epiphany_soft.wtw.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;


public class DataBaseEmisoraEmite {

    DataBaseHelper miDBHelper;



    public DataBaseEmisoraEmite(Context context) {

        miDBHelper = new DataBaseHelper(context);

    }

    public Cursor consultarAllEmisoras(){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query = "SELECT " + DataBaseContract.EmisoraContract.COLUMN_NAME_EMISORA_NOMBRE+","
                    + DataBaseContract.EmisoraContract.COLUMN_NAME_EMISORA_ID+" ";
            query +=
                    "FROM " + DataBaseContract.EmisoraContract.TABLE_NAME+" ";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }

    public boolean insertarEmite(String nombreCanal,int idEmisor, Integer numCanal){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DataBaseContract.EmiteContract.COLUMN_NAME_CANAL_ID,nombreCanal);
            values.put(DataBaseContract.EmiteContract.COLUMN_NAME_EMISORA_ID,idEmisor);
            values.put(DataBaseContract.EmiteContract.COLUMN_NAME_CANAL_NUMERO,numCanal);
            long rowid=db.insert(DataBaseContract.EmiteContract.TABLE_NAME, null, values);
            if (rowid>0) return true;
        }
        return false;
    }



    public Cursor consultarNombreEmisoras(String nombre_canal){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + DataBaseContract.EmiteContract.COLUMN_NAME_EMISORA_ID + "," +
                            DataBaseContract.EmiteContract.COLUMN_NAME_CANAL_ID + "," +
                            DataBaseContract.EmiteContract.COLUMN_NAME_CANAL_NUMERO+ ","+
                            DataBaseContract.EmisoraContract.COLUMN_NAME_EMISORA_NOMBRE+" ";
            query +=
                    "FROM " + DataBaseContract.EmiteContract.TABLE_NAME+" NATURAL JOIN "+
                            DataBaseContract.EmisoraContract.TABLE_NAME+ " ";

            query +=
                    "WHERE " + DataBaseContract.EmiteContract.COLUMN_NAME_CANAL_ID +"=\'"+nombre_canal+"\'";
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }

    public Cursor consultarEmisorasDeCanal(String  nombre_canal){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + DataBaseContract.EmisoraContract.TABLE_NAME+"."+ DataBaseContract.EmiteContract.COLUMN_NAME_EMISORA_ID + ","
                            + DataBaseContract.EmiteContract.TABLE_NAME+"."+ DataBaseContract.EmiteContract.COLUMN_NAME_CANAL_ID + ","
                            + DataBaseContract.EmiteContract.TABLE_NAME+"."+ DataBaseContract.EmiteContract.COLUMN_NAME_CANAL_NUMERO+ ","
                            + DataBaseContract.EmisoraContract.TABLE_NAME+"."+ DataBaseContract.EmisoraContract.COLUMN_NAME_EMISORA_NOMBRE+" ";
            query +=
                    "FROM " + DataBaseContract.EmisoraContract.TABLE_NAME+"  LEFT OUTER  JOIN "+ DataBaseContract.EmiteContract.TABLE_NAME+
                            " ON "+ DataBaseContract.EmisoraContract.TABLE_NAME+"."+ DataBaseContract.EmisoraContract.COLUMN_NAME_EMISORA_ID
                            +"="+ DataBaseContract.EmiteContract.TABLE_NAME+"."+ DataBaseContract.EmiteContract.COLUMN_NAME_EMISORA_ID+" AND "+
                            DataBaseContract.EmiteContract.TABLE_NAME+"."+ DataBaseContract.EmiteContract.COLUMN_NAME_CANAL_ID +"=? ";
            Cursor c = db.rawQuery(query, new String[]{nombre_canal});
            return c;
        }
        else return null;
    }

    public boolean eliminarEmite(String  nombreCanal, int idEmisora){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            String query= DataBaseContract.EmiteContract.COLUMN_NAME_CANAL_ID+"=? AND "+
                    DataBaseContract.EmiteContract.COLUMN_NAME_EMISORA_ID+"=?";
            int numDel=db.delete(DataBaseContract.EmiteContract.TABLE_NAME, query, new String[]{nombreCanal, Integer.toString(idEmisora)});
            if (numDel>0) return true;
        }
        return false;
    }

    public boolean actualizarEmite(String nombrecanal_old, String nombrecanal_new, int idEmisor, Integer numCanal){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if (miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DataBaseContract.EmiteContract.COLUMN_NAME_CANAL_ID,nombrecanal_new);
            values.put(DataBaseContract.EmiteContract.COLUMN_NAME_EMISORA_ID,idEmisor);
            values.put(DataBaseContract.EmiteContract.COLUMN_NAME_CANAL_NUMERO,numCanal);

            String query= DataBaseContract.EmiteContract.COLUMN_NAME_CANAL_ID+"=? AND "
                    + DataBaseContract.EmiteContract.COLUMN_NAME_EMISORA_ID+"=? ";
            String[] compare = new String[]{(nombrecanal_old),Integer.toString(idEmisor)};
            try{
                int rowid=db.update(DataBaseContract.EmiteContract.TABLE_NAME, values, query, compare);
                if (rowid>0) return true;
            } catch (Exception e){
                //No se hace nada, y luego retorna falso
            }
        }
        return false;
    }

}