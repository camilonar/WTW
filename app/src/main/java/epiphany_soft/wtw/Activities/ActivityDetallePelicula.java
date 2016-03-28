package epiphany_soft.wtw.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 26/03/2016.
 */
public class ActivityDetallePelicula extends AppCompatActivity {
    String nombre;
    String sinopsis;
    String genero;
    public static boolean actualizado=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pelicula);
        //Se recibe el nombre del programa
        Bundle b = getIntent().getExtras();
        String nombrePelicula = b.getString(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE);
        this.llenarInfo(nombrePelicula);
    }


    @Override
    public void onResume(){
        super.onResume();
        if (actualizado) this.recreate();
        actualizado=false;
    }

    private void llenarInfo(String nombrePelicula){
        //TODO: Revisar si es mejor usar v.getContext()
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        Cursor c=db.consultarPeliculaPorNombre(nombrePelicula);
        c.moveToNext();
        nombre = c.getString(c.getColumnIndex(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE));
        sinopsis = c.getString(c.getColumnIndex(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS));
        genero = c.getString(c.getColumnIndex(DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_NOMBRE));
        if (nombre!=null) ((TextView) findViewById(R.id.txtNombrePel)).setText(nombre);
        else ((TextView) findViewById(R.id.txtNombrePel)).setText("Película sin nombre");
        if (sinopsis!=null) ((TextView) findViewById(R.id.txtSinopsisPel)).setText(sinopsis);
        else ((TextView) findViewById(R.id.txtSinopsisPel)).setText("Película sin sinopsis");
        if (genero!=null) ((TextView) findViewById(R.id.txtGeneroPel)).setText(genero);
        else ((TextView) findViewById(R.id.txtGeneroPel)).setText("Película sin genero");
    }

    public void onClickActualizarPelicula(View v){
        Intent i = new Intent(this, ActivityActualizarPelicula.class);
        Bundle b = new Bundle();
        b.putString(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE,nombre);
        b.putString(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS,sinopsis);
        b.putString(DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_NOMBRE, genero);
        i.putExtras(b);
        startActivity(i);
    }
}
