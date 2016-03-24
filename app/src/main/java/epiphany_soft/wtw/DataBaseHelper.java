package epiphany_soft.wtw;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Camilo on 23/03/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    //Si se cambia el esquema de la base de datos entonces se debe incrementar la version
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "NombrePorDefinir.db";//TODO: Definir nombre de la BD

    /*Ejemplo Creación*/
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String NOT_NULL = "NOT_NULL";
    public static final String SQL_CREATE_USUARIOS =
                "CREATE TABLE " + DataBaseContract.UsuarioContract.TABLE_NAME + " (" +
                    DataBaseContract.UsuarioContract._ID + " INTEGER" + COMMA_SEP +
                    DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_ID + TEXT_TYPE + NOT_NULL+ COMMA_SEP +
                    DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_NOMBRE + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_PASSWORD + TEXT_TYPE + COMMA_SEP +
                    "PRIMARY KEY("+DataBaseContract.UsuarioContract.COLUMN_NAME_USUARIO_ID+")"+
            " )";
    private static final String SQL_DELETE_USUARIOS = "DROP TABLE IF EXISTS " + DataBaseContract.UsuarioContract.TABLE_NAME;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Esta base de datos es solo una cache para datos online, así que su política de upgrade
        // es simplemente descargar los datos y empezar nuevamente
        db.execSQL(SQL_DELETE_USUARIOS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
