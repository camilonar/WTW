package epiphany_soft.wtw.Activities.Canal;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.DataBase.DataBaseContract.ProgramaContract;
import epiphany_soft.wtw.Negocio.Horario;
import epiphany_soft.wtw.R;
import epiphany_soft.wtw.Strategies.StrategiesCanal.StrategyAsociarCanal;
import epiphany_soft.wtw.Strategies.StrategiesCanal.StrategyAsociarCanalPelicula;
import epiphany_soft.wtw.Strategies.StrategiesCanal.StrategyAsociarCanalSerie;

/**
 * Created by Camilo on 15/04/2016.
 */
public class ActivityAsociarCanal extends ActivityBase {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private StrategyAsociarCanal strategy;
    public String nombrePrograma;
    public int idPrograma;

    public static boolean actualizado=false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asociar_canal);
        setTitle("ASOCIAR CANAL");

        Bundle b = getIntent().getExtras();
        nombrePrograma = b.getString(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE);
        idPrograma = b.getInt(ProgramaContract.COLUMN_NAME_PROGRAMA_ID);
        String tipo = b.getString("Tipo");
        setStrategy(tipo);
       // id_dia= b.getInt(DataBaseContract.DiaContract.COLUMN_NAME_ID_DIA);

        crearRecyclerViewCanales();
      //  setSpecialFonts();
    }

    public RecyclerView.Adapter getmAdapter(){
        return mAdapter;
    }

    public void setStrategy(String tipo){
        if (tipo.equals("Pelicula")) {
            this.strategy = new StrategyAsociarCanalPelicula();
            findViewById(R.id.fab).setVisibility(View.GONE);
        }
        else if (tipo.equals("Serie"))
            this.strategy = new StrategyAsociarCanalSerie();
    }

    private void crearRecyclerViewCanales(){
        Horario[] horarios = strategy.consultarHorario(this);
        this.crearRecyclerViewCanales(horarios);
    }

    private void crearRecyclerViewCanales(Horario[] contenido){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_consulta_canal);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (contenido!=null) {
            mAdapter = strategy.createAdapter(this, contenido, idPrograma);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (actualizado){
            actualizado=false;
            this.crearRecyclerViewCanales();
        }
    }


    public void onClickRegistrarHorario(View v) {
       boolean success = strategy.registrarInfoHorario(this);
        if (success) {
            createToast("Cambios Realizados");
            this.finish();
        }
    }

}
