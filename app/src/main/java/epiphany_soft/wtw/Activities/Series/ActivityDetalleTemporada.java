package epiphany_soft.wtw.Activities.Series;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 2/04/2016.
 */
public class ActivityDetalleTemporada extends ActivityBase {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    int idSerie, idTemporada;
    String nombreSerie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_temporada);

        Bundle b = getIntent().getExtras();
        idSerie = b.getInt(DataBaseContract.TemporadaContract.COLUMN_NAME_PROGRAMA_ID);
        idTemporada = b.getInt(DataBaseContract.TemporadaContract.COLUMN_NAME_TEMPORADA_ID);
        nombreSerie = b.getString(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE);

        setTitle(nombreSerie+": Temporada "+Integer.toString(idTemporada));

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_consultar_capitulo);
    }

    public void onClickAgregarCapitulo(View v){
        Intent i = new Intent(this, ActivityAgregarCapitulo.class);
        Bundle b = new Bundle();
        b.putInt(DataBaseContract.CapituloContract.COLUMN_NAME_TEMPORADA_ID, idTemporada);
        b.putInt(DataBaseContract.CapituloContract.COLUMN_NAME_SERIE_ID,idSerie);
        b.putString(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE,nombreSerie);
        i.putExtras(b);
        startActivity(i);
    }
}
