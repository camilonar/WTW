package epiphany_soft.wtw.Activities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
    String generoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_pelicula);
        setTitle("ACTUALIZAR PELICULA");

        name=(EditText)findViewById(R.id.txtNombrePelicula);
        name.setKeyListener(null);
        sinopsis=(EditText)findViewById(R.id.txtSinopsis);
        Bundle b = getIntent().getExtras();
        nombrePelicula = b.getString(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE);
        sinopsisText = b.getString(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS);
        generoText = b.getString(DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_NOMBRE);
        sinopsis.setText(sinopsisText);
        name.setText(nombrePelicula);
        crearSpinnerGeneros();
    }

    public void onClickActualizarPelicula(View v){
        String sinopsisS=sinopsis.getText().toString();
        int idGen=((Genero)spnGenero.getSelectedItem()).getId();
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        boolean success=db.actualizarPrograma(idGen,nombrePelicula,sinopsisS);
        if (success) {
            createToast("Película actualizada");
            ActivityDetallePelicula.actualizado=true;
            this.finish();
        }
        else createToast("Ocurrió un error");
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
            if (c.getString(c.getColumnIndex(DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_NOMBRE)).equals(generoText)){
                generos.add(0,g);
            } else generos.add(g);

        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,generos);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnGenero.setAdapter(adapter);
    }
    public void createToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
