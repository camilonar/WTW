package epiphany_soft.wtw.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Adapters.HorarioAdapter;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract.ProgramaContract;
import epiphany_soft.wtw.Negocio.Horario;
import epiphany_soft.wtw.R;

import static epiphany_soft.wtw.DataBase.DataBaseContract.CanalContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.HorarioContract;

/**
 * Created by Camilo on 15/04/2016.
 */
public class ActivityAsociarCanal extends ActivityBase {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String nombrePrograma;
    int idPrograma;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asociar_canal);
        setTitle("ASOCIAR CANAL");

        Bundle b = getIntent().getExtras();
        nombrePrograma = b.getString(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE);
        idPrograma = b.getInt(ProgramaContract.COLUMN_NAME_PROGRAMA_ID);

        crearRecyclerViewCanales();
    }

    private void crearRecyclerViewCanales(){
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        Cursor c=db.getHorariosPrograma(idPrograma);
        if (c!=null) {
            Horario[] horarios=new Horario[c.getCount()];
            int i=0;
            while (c.moveToNext()) {
                String nombreCanal = c.getString(c.getColumnIndex(CanalContract.COLUMN_NAME_CANAL_ID));
                int idPrograma = c.getInt(c.getColumnIndex(HorarioContract.COLUMN_NAME_PROGRAMA_ID));
                int idRel = c.getInt(c.getColumnIndex(HorarioContract.COLUMN_NAME_RELACION_ID));
                horarios[i] = new Horario(idRel,nombreCanal,idPrograma);
                i++;
            }
            this.crearRecyclerViewCanales(horarios);
        } else this.crearRecyclerViewCanales(null);
    }

    private void crearRecyclerViewCanales(Horario[] contenido){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_consulta_canal);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (contenido!=null) {
            mAdapter = new HorarioAdapter(contenido,idPrograma);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
