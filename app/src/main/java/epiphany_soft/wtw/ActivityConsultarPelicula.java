package epiphany_soft.wtw;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
/**
 * Created by Camilo on 22/03/2016.
 */
public class ActivityConsultarPelicula extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_peliculas);
    }

    public void onClickBuscar(View v) {
        EditText txtBuscar = (EditText) findViewById(R.id.txtBuscar);
        String text = txtBuscar.getText().toString();
        //TODO: Revisar si es mejor usar v.getContext()
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        if (text!=null){
            Cursor c=db.consultarPeliculaNombre(text);
            if (c!=null) {
                while (c.moveToNext()) {
                    String nombre = c.getString(c.getColumnIndex(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE));
                    System.out.println(nombre);
                }
            }
        }

    }
}
