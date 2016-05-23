package epiphany_soft.wtw.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import epiphany_soft.wtw.Negocio.Horario;


public class DataBaseHorario {

    DataBaseHelper miDBHelper;



    public DataBaseHorario(Context context) {

        miDBHelper = new DataBaseHelper(context);

    }


    public boolean insertarHorario(Horario h){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DataBaseContract.HorarioContract.COLUMN_NAME_CANAL_ID,h.getNombreCanal());
            values.put(DataBaseContract.HorarioContract.COLUMN_NAME_PROGRAMA_ID,h.getIdPrograma());
            values.put(DataBaseContract.HorarioContract.COLUMN_NAME_RELACION_HORA,h.getHora());
            if (h.getFecha()!=null)
                values.put(DataBaseContract.HorarioContract.COLUMN_NAME_RELACION_FECHA,h.getFecha());
            long rowid=db.insert(DataBaseContract.HorarioContract.TABLE_NAME, null, values);
            if (rowid>0) return true;
        }
        return false;
    }

    public int getHorarioId(int idPrograma, String idCanal){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query = "SELECT " + DataBaseContract.HorarioContract.COLUMN_NAME_RELACION_ID+" ";
            query +=
                    "FROM " + DataBaseContract.HorarioContract.TABLE_NAME+" ";
            query +=
                    "WHERE " + DataBaseContract.HorarioContract.COLUMN_NAME_PROGRAMA_ID+"=? AND "+
                            DataBaseContract.HorarioContract.COLUMN_NAME_CANAL_ID+"=?";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idPrograma),idCanal});
            int count = c.getCount();
            if (count>0) {
                c.moveToNext();
                return c.getInt(c.getColumnIndex(DataBaseContract.HorarioContract.COLUMN_NAME_RELACION_ID));
            }
        }
        return 0;
    }

    public boolean eliminarHorario(int idHorario){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            String query= DataBaseContract.HorarioContract.COLUMN_NAME_RELACION_ID+"=?";
            int numDel=db.delete(DataBaseContract.HorarioContract.TABLE_NAME, query, new String[]{Integer.toString(idHorario)});
            if (numDel>0) return true;
        }
        return false;
    }

    public Cursor getHorariosPrograma(int idPrograma){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + DataBaseContract.HorarioContract.TABLE_NAME+"."+ DataBaseContract.HorarioContract.COLUMN_NAME_PROGRAMA_ID+","+
                            DataBaseContract.HorarioContract.TABLE_NAME+"."+ DataBaseContract.HorarioContract.COLUMN_NAME_RELACION_ID+","+
                            DataBaseContract.HorarioContract.TABLE_NAME+"."+ DataBaseContract.HorarioContract.COLUMN_NAME_RELACION_HORA+","+
                            DataBaseContract.HorarioContract.TABLE_NAME+"."+ DataBaseContract.HorarioContract.COLUMN_NAME_RELACION_FECHA+","+
                            DataBaseContract.CanalContract.TABLE_NAME+"."+ DataBaseContract.CanalContract.COLUMN_NAME_CANAL_ID+" ";
            query +=
                    "FROM " + DataBaseContract.CanalContract.TABLE_NAME + " LEFT OUTER JOIN "+
                            DataBaseContract.HorarioContract.TABLE_NAME+" ON "+ DataBaseContract.CanalContract.TABLE_NAME+"."+
                            DataBaseContract.CanalContract.COLUMN_NAME_CANAL_ID+"="+
                            DataBaseContract.HorarioContract.TABLE_NAME+"."+ DataBaseContract.HorarioContract.COLUMN_NAME_CANAL_ID+
                            " AND "+ DataBaseContract.HorarioContract.TABLE_NAME+"."+ DataBaseContract.HorarioContract.COLUMN_NAME_PROGRAMA_ID + "=?";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idPrograma)});
            return c;
        }
        else return null;
    }

    public Cursor getHorariosProgramaCanal(int idPrograma,String canal){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + DataBaseContract.HorarioContract.TABLE_NAME+"."+ DataBaseContract.HorarioContract.COLUMN_NAME_PROGRAMA_ID+","+
                            DataBaseContract.HorarioContract.TABLE_NAME+"."+ DataBaseContract.HorarioContract.COLUMN_NAME_RELACION_ID+","+
                            DataBaseContract.HorarioContract.TABLE_NAME+"."+ DataBaseContract.HorarioContract.COLUMN_NAME_RELACION_HORA+","+
                            DataBaseContract.HorarioContract.TABLE_NAME+"."+ DataBaseContract.HorarioContract.COLUMN_NAME_RELACION_FECHA+","+
                            DataBaseContract.CanalContract.TABLE_NAME+"."+ DataBaseContract.CanalContract.COLUMN_NAME_CANAL_ID+" ";
            query +=
                    "FROM " + DataBaseContract.CanalContract.TABLE_NAME + " LEFT OUTER JOIN "+
                            DataBaseContract.HorarioContract.TABLE_NAME+" ON "+ DataBaseContract.CanalContract.TABLE_NAME+"."+
                            DataBaseContract.CanalContract.COLUMN_NAME_CANAL_ID+"="+
                            DataBaseContract.HorarioContract.TABLE_NAME+"."+ DataBaseContract.HorarioContract.COLUMN_NAME_CANAL_ID+
                            " AND "+ DataBaseContract.HorarioContract.TABLE_NAME+"."+ DataBaseContract.HorarioContract.COLUMN_NAME_PROGRAMA_ID + "=? ";
            query +=
                    "WHERE "+ DataBaseContract.HorarioContract.TABLE_NAME+"."+ DataBaseContract.HorarioContract.COLUMN_NAME_CANAL_ID+"=? ";
            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idPrograma),canal});
            return c;
        }
        else return null;
    }


    public boolean insertarRelacionDiaHorario(int rel_id, int dia){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DataBaseContract.DiaHorarioContract.COLUMN_NAME_RELACION_ID, rel_id);
            values.put(DataBaseContract.DiaHorarioContract.COLUMN_NAME_DIA_ID, dia);
            long rowid=db.insert(DataBaseContract.DiaHorarioContract.TABLE_NAME, null, values);
            if (rowid>0) return true;
        }
        return false;
    }
    // actualizar Horario , por ahora no lo voy a hacer.

    public boolean actualizarHorario(String nombrecanal_old, String nombrecanal_new, int idEmisor, Integer numCanal){
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

    public Cursor getDia(){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " +  DataBaseContract.DiaContract.COLUMN_NAME_ID_DIA+" ";
            query +=
                    "FROM " +DataBaseContract.DiaContract.TABLE_NAME+ "";

          /*  query +=
                    "WHERE " + ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE +"=\'"+nombre+"\'";*/
            Cursor c = db.rawQuery(query, null);
            return c;
        }
        else return null;
    }


    public Cursor consultarHorarioDia(int idHorario){
        try {
            miDBHelper.createDataBase();
        } catch (IOException e) {
        }
        if(miDBHelper.checkDataBase()) {
            SQLiteDatabase db = miDBHelper.getReadableDatabase();
            String query =
                    "SELECT " + DataBaseContract.DiaContract.TABLE_NAME+"."+ DataBaseContract.DiaContract.COLUMN_NAME_ID_DIA+","+
                            DataBaseContract.DiaHorarioContract.TABLE_NAME+"."+ DataBaseContract.DiaHorarioContract.COLUMN_NAME_RELACION_ID+" ";
            query +=
                    "FROM " + DataBaseContract.DiaContract.TABLE_NAME+" LEFT OUTER JOIN "+ DataBaseContract.DiaHorarioContract.TABLE_NAME +
                            " ON "+ DataBaseContract.DiaContract.TABLE_NAME+"."+ DataBaseContract.DiaContract.COLUMN_NAME_ID_DIA
                            +"="+ DataBaseContract.DiaHorarioContract.TABLE_NAME+"."+ DataBaseContract.DiaHorarioContract.COLUMN_NAME_DIA_ID+" AND "
                            + DataBaseContract.DiaHorarioContract.TABLE_NAME+"."+ DataBaseContract.DiaHorarioContract.COLUMN_NAME_RELACION_ID +"=? ";
            query+=
                    "ORDER BY "+ DataBaseContract.DiaContract.TABLE_NAME+"."+ DataBaseContract.DiaContract.COLUMN_NAME_ID_DIA;

            Cursor c = db.rawQuery(query, new String[]{Integer.toString(idHorario)});
            return c;
        }
        else return null;
    }




}