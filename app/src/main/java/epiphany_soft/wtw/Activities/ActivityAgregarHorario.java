package epiphany_soft.wtw.Activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Adapters.DiaAdapter;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 28/04/2016.
 */
public class ActivityAgregarHorario extends ActivityBase {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_horario);
        setTitle("AGREGAR HORARIO");

        crearRecyclerViewDias();
    }

    private void crearRecyclerViewDias() {
        String contenido[] = new String[]{"D", "L", "Ma", "Mi", "J", "V", "S"};
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_horario);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (contenido!=null) {
            mAdapter = new DiaAdapter(contenido);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
