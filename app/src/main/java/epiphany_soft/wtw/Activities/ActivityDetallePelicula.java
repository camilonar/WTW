package epiphany_soft.wtw.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.R;

import static epiphany_soft.wtw.DataBase.DataBaseContract.GeneroContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.ProgramaContract;

/**
 * Created by Camilo on 26/03/2016.
 */
public class ActivityDetallePelicula extends AppCompatActivity {
    String nombre,sinopsis,genero,pais;
    int anio;
    public static boolean actualizado=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pelicula);
        //Se recibe el nombre del programa
        Bundle b = getIntent().getExtras();
        String nombrePelicula = b.getString(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE);
        setTitle(nombrePelicula);
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
        nombre = c.getString(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE));
        sinopsis = c.getString(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS));
        genero = c.getString(c.getColumnIndex(GeneroContract.COLUMN_NAME_GENERO_NOMBRE));
        anio = c.getInt(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO));
        pais = c.getString(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN));
        if (!nombre.equals("")) ((TextView) findViewById(R.id.txtNombrePel)).setText(nombre);
        else ((TextView) findViewById(R.id.txtNombrePel)).setText("Película sin nombre");
        if (!sinopsis.equals("")) ((TextView) findViewById(R.id.txtSinopsisPel)).setText(sinopsis);
        else ((TextView) findViewById(R.id.txtSinopsisPel)).setText("Película sin sinopsis");
        if (!genero.equals("")) ((TextView) findViewById(R.id.txtGeneroPel)).setText(genero);
        else ((TextView) findViewById(R.id.txtGeneroPel)).setText("Película sin genero");
    }

    public void onClickActualizarPelicula(View v){
        Intent i = new Intent(this, ActivityActualizarPelicula.class);
        Bundle b = new Bundle();
        b.putString(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE,nombre);
        b.putString(ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS,sinopsis);
        b.putString(GeneroContract.COLUMN_NAME_GENERO_NOMBRE, genero);
        b.putString(ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN,pais);
        b.putInt(ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO,anio);
        i.putExtras(b);
        startActivity(i);
    }
}
