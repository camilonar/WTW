package epiphany_soft.wtw.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Negocio.Genero;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 27/03/2016.
 */
public class ActivityActualizarPelicula extends AppCompatActivity {
    private EditText name,sinopsis;
    private Spinner spnGenero;
    String nombrePelicula;
    String sinopsisText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_pelicula);
        name=(EditText)findViewById(R.id.txtNombrePelicula);
        name.setKeyListener(null);
        sinopsis=(EditText)findViewById(R.id.txtSinopsis);
        Bundle b = getIntent().getExtras();
        nombrePelicula = b.getString(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE);
        sinopsisText = b.getString(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS);
        sinopsis.setText(sinopsisText);
        name.setText(nombrePelicula);
        crearSpinnerGeneros();
    }

    public void onClickActualizarPelicula(View v){
        System.out.println("OK");
    }
    public void crearSpinnerGeneros(){
        spnGenero = (Spinner) findViewById(R.id.spnGeneroAct);
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        Cursor c=db.consultarAllGeneros();
        ArrayList<Genero> generos = new ArrayList<Genero>();
        while (c.moveToNext()){
            Genero g=
                    new Genero(c.getInt(c.getColumnIndex(DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_ID)),
                            c.getString(c.getColumnIndex(DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_NOMBRE)));
            generos.add(g);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,generos);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnGenero.setAdapter(adapter);
    }
}
