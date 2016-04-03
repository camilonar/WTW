package epiphany_soft.wtw.Activities.Series;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Fonts.SpecialFont;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 2/04/2016.
 */
public class ActivityAgregarCapitulo extends ActivityBase {

    private EditText nombre,numero;
    private int idSerie, idTemporada;
    private String nombreSerie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_capitulo);

        nombre = (EditText) findViewById(R.id.txtNombreCapitulo);
        numero = (EditText) findViewById(R.id.txtNumeroCapitulo);

        Bundle b = getIntent().getExtras();
        idSerie = b.getInt(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID);
        idTemporada = b.getInt(DataBaseContract.TemporadaContract.COLUMN_NAME_TEMPORADA_ID);
        nombreSerie = b.getString(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE);
        setTitle(nombreSerie+": Temporada "+Integer.toString(idTemporada));

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
}
