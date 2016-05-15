package epiphany_soft.wtw.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Negocio.Programa;
import epiphany_soft.wtw.Negocio.Sesion;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 14/05/2016.
 */
public abstract class FragmentConsultarPrograma extends Fragment implements View.OnClickListener{

    protected RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected Spinner spinner2;
    protected EditText txtBuscar;

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
        txtBuscar = (EditText) getView().findViewById(R.id.txtBuscar);
        getView().findViewById(R.id.btnBuscar).setOnClickListener(this);
        getView().findViewById(R.id.btnConfigurar).setOnClickListener(this);
        llenarRecyclerOnCreate();
        setSpecialFonts();
    }

    public void onClickConfigurar(View v){
        spinner2 = (Spinner) v.findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("list 1");
        list.add("list 2");
        list.add("list 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
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

    protected void setSpecialFonts(){
        txtBuscar.setTypeface(RobotoFont.getInstance(this.getActivity()).getTypeFace());
    }
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnBuscar){
            onClickBuscar(v);
        } else if (v.getId()==R.id.btnConfigurar)
            onClickConfigurar(v);
    }
}
