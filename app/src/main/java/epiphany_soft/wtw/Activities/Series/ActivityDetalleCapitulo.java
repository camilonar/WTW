package epiphany_soft.wtw.Activities.Series;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.R;

import static epiphany_soft.wtw.DataBase.DataBaseContract.CapituloContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.ProgramaContract;

/**
 * Created by Camilo on 2/04/2016.
 */
public class ActivityDetalleCapitulo extends ActivityBase {
    private EditText nombre,numero;
    private int idSerie, idTemporada,idCapitulo;
    private String nombreSerie,nombreCapitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_capitulo);

        Bundle b = getIntent().getExtras();
        idSerie = b.getInt(CapituloContract.COLUMN_NAME_SERIE_ID);
        idTemporada = b.getInt(CapituloContract.COLUMN_NAME_TEMPORADA_ID);
        nombreSerie = b.getString(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE);
        idCapitulo = b.getInt(CapituloContract.COLUMN_NAME_CAPITULO_ID);
        nombreCapitulo = b.getString(CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE);

        setTitle(nombreSerie+": Temporada "+Integer.toString(idTemporada));
    }

    public void onClickActualizarCapitulo(View v){
        Intent i = new Intent(this, ActivityActualizarCapitulo.class);
        Bundle b = new Bundle();
        b.putInt(CapituloContract.COLUMN_NAME_TEMPORADA_ID, idTemporada);
        b.putInt(CapituloContract.COLUMN_NAME_SERIE_ID,idSerie);
        b.putString(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE, nombreSerie);
        b.putInt(CapituloContract.COLUMN_NAME_CAPITULO_ID, idCapitulo);
        b.putString(CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE,nombreCapitulo);
        i.putExtras(b);
        startActivity(i);
    }
}
