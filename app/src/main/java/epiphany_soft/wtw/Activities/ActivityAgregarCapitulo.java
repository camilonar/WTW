package epiphany_soft.wtw.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Fonts.SpecialFont;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 2/04/2016.
 */
public class ActivityAgregarCapitulo extends AppCompatActivity {

    private EditText nombre,numero;
    private int idSerie, idTemporada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_capitulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nombre = (EditText) findViewById(R.id.txtNombreCapitulo);
        numero = (EditText) findViewById(R.id.txtNumeroCapitulo);

        Bundle b = getIntent().getExtras();
        idSerie = b.getInt(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID);
        idTemporada = b.getInt(DataBaseContract.TemporadaContract.COLUMN_NAME_TEMPORADA_ID);

        setSpecialFonts();
    }

    private void setSpecialFonts(){
        TextView nombreLabel=(TextView) findViewById(R.id.lblNombreCapitulo);
        nombreLabel.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        TextView numeroLabel=(TextView) findViewById(R.id.lblNumeroCapitulo);
        numeroLabel.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        //Los textos
        nombre.setTypeface(RobotoFont.getInstance(this).getTypeFace());
        numero.setTypeface(RobotoFont.getInstance(this).getTypeFace());
    }

    public void onClickAgregarCapitulo(View v) {
        if (nombre.getText().toString().equals("")) {
            createToast("Introduzca un nombre");
            return;
        }
        if (numero.getText().toString().equals("")){
            createToast("Introduzca un numero");
            return;
        }
        String nombreCap=nombre.getText().toString();
        int numeroCap=Integer.parseInt(numero.getText().toString());
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        boolean success=db.insertarCapitulo(numeroCap,nombreCap,idTemporada,idSerie);
        if (success) createToast("Capítulo creado");
        else createToast("El capítulo ya existe");
    }

    public void createToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    /**Esta funcion sirve para poner el botón de regresar a la anterior actividad
     *
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
