package epiphany_soft.wtw.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Negocio.Programa;
import epiphany_soft.wtw.Negocio.Sesion;
import epiphany_soft.wtw.R;
import epiphany_soft.wtw.Strategies.StrategiesConsulta.StrategyConsulta;
import epiphany_soft.wtw.Strategies.StrategiesConsulta.StrategyConsultaCanal;
import epiphany_soft.wtw.Strategies.StrategiesConsulta.StrategyConsultaGenero;
import epiphany_soft.wtw.Strategies.StrategiesConsulta.StrategyConsultaHorario;
import epiphany_soft.wtw.Strategies.StrategiesConsulta.StrategyConsultaNombre;

/**
 * Created by Camilo on 14/05/2016.
 */
public abstract class FragmentConsultarPrograma extends Fragment implements View.OnClickListener{

    protected RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected EditText txtBuscar;
    protected CharSequence[] cs = new CharSequence[]{"Nombre","Genero","Canal"};
    protected StrategyConsulta strategy;

    protected abstract void llenarRecyclerOnCreate();
    protected abstract RecyclerView.Adapter createAdapter(Programa[] contenido);
    public abstract void onClickBuscar(View v);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_consultar_series, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.strategy = new StrategyConsultaNombre();//POR DEFECTO LA CONSULTA ES POR NOMBRE
        txtBuscar = (EditText) getView().findViewById(R.id.txtBuscar);
        getView().findViewById(R.id.btnBuscar).setOnClickListener(this);
        getView().findViewById(R.id.btnConfigurar).setOnClickListener(this);
        llenarRecyclerOnCreate();
        setSpecialFonts();
    }

    protected void crearRecycledView(Programa[] contenido){
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.rv_consulta_programa);
        // Se usa cuando se sabe que cambios en el contenido no cambian el tamaño del layout
        mRecyclerView.setHasFixedSize(true);
        // Se usa un layout manager lineal para el recycler view
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Se especifica el adaptador
        if (contenido!=null) {
            mAdapter = createAdapter(contenido);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    protected Programa[] llenarPrograma(Cursor c){
        if (c!=null) {
            Programa[] programas = new Programa[c.getCount()];
            int i = 0;
            while (c.moveToNext()) {
                programas[i] = new Programa();
                String nombre = c.getString(c.getColumnIndex(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE));
                int idPrograma = c.getInt(c.getColumnIndex(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID));
                if (Sesion.getInstance().isActiva()) {
                    boolean isFavorito = c.getInt(c.getColumnIndex(DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID)) != 0;
                    programas[i].setFavorito(isFavorito);
                }
                programas[i].setNombre(nombre);
                programas[i].setIdPrograma(idPrograma);
                i++;
            }
            return programas;
        }
        return null;
    }

    public void onClickConfigurar(View v){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        DialogoConfirmacion dialogo = new DialogoConfirmacion();
        dialogo.show(fragmentManager, "tagConfirmacion");
    }
    @SuppressLint("ValidFragment")
    public class DialogoConfirmacion extends DialogFragment {
        private int pos=0;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            for (int i = 0; i < cs.length; i++) {
                if (cs[i].equals(strategy.getType())){
                    pos = i;
                    break;
                }
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setSingleChoiceItems(cs, pos, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pos = which;
                }
            }).setTitle("Opciones de consulta").setPositiveButton("Aceptar", new DialogInterface.OnClickListener()  {
                public void onClick(DialogInterface dialog, int id) {
                    ((ActivityBase)getActivity()).createToast("Acción aplicada");
                    setStrategy(pos);
                    dialog.cancel();
                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            return builder.create();
        }

    }
    protected void setStrategy(int i){
        //TODO: implementar la estrategia
        if (cs[i].equals(strategy.getType())){
          return;
        } else if (cs[i].equals("Nombre")){
            strategy = new StrategyConsultaNombre();
        } else if (cs[i].equals("Genero")){
            strategy = new StrategyConsultaGenero();
        } else if (cs[i].equals("Horario")){
            strategy = new StrategyConsultaHorario();
        } else if (cs[i].equals("Canal")){
            strategy = new StrategyConsultaCanal();
        }
        strategy.prepare(this);
    }

    protected void setSpecialFonts(){
        txtBuscar.setTypeface(RobotoFont.getInstance(this.getActivity()).getTypeFace());
    }

    public EditText getTxtBuscar(){
        return txtBuscar;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnBuscar){
            onClickBuscar(v);
        } else if (v.getId()==R.id.btnConfigurar)
            onClickConfigurar(v);
    }
}
