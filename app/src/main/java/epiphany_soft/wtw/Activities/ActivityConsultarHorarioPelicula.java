package epiphany_soft.wtw.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Adapters.CanalAdapter;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 22/05/2016.
 */
public class ActivityConsultarHorarioPelicula extends ActivityBase {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private int idPrograma;
    private String nombreCanal;

    public static boolean actualizado = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_horario_pelicula);
        Bundle b = getIntent().getExtras();
        idPrograma = b.getInt(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID);
        nombreCanal = b.getString(DataBaseContract.CanalContract.COLUMN_NAME_CANAL_ID);
        setTitle("HORARIOS");
        crearRecyclerViewHorarios();
    }
    @Override
    public void onResume(){
        super.onResume();
        if (actualizado){
            actualizado=false;
            this.crearRecyclerViewHorarios();
        }
    }

    public void onClickAgregarHorario(View v){
        Intent i = new Intent(this,ActivityAgregarHorarioPelicula.class);
        Bundle b = new Bundle();
        b.putInt(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID, idPrograma);
        b.putString(DataBaseContract.CanalContract.COLUMN_NAME_CANAL_ID,nombreCanal);
        i.putExtras(b);
        startActivity(i);
    }

    private void crearRecyclerViewHorarios(){
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        Cursor c=db.getHorariosProgramaCanal(idPrograma, nombreCanal);
        if (c!=null) {
            String[] horarios=new String[c.getCount()];
            int i=0;
            while (c.moveToNext()){
                String hora = c.getString(c.getColumnIndex(DataBaseContract.HorarioContract.COLUMN_NAME_RELACION_HORA));
                horarios[i]="Fecha: ";
                horarios[i]+=c.getString(c.getColumnIndex(DataBaseContract.HorarioContract.COLUMN_NAME_RELACION_FECHA));
                horarios[i]+="     Hora: ";
                horarios[i]+=((hora==null))? "ND":hora;
                i++;
            }
            this.crearRecyclerViewHorarios(horarios);
        }
    }

    protected void hideWhenNoSession(){
        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.fab);
        hide(btn);
    }

    protected void showWhenSession(){
        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.fab);
        show(btn);
    }


    private void crearRecyclerViewHorarios(String[] contenido){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_horario);
        // Se usa cuando se sabe que cambios en el contenido no cambian el tamaño del layout
        mRecyclerView.setHasFixedSize(false);
        // Se usa un layout manager lineal para el recycler view
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Se especifica el adaptador
        if (contenido!=null) {
            mAdapter = new CanalAdapter(contenido);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
