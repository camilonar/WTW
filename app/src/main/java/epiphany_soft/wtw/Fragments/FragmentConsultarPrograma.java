package epiphany_soft.wtw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 14/05/2016.
 */
public abstract class FragmentConsultarPrograma extends Fragment implements View.OnClickListener{

    protected Spinner spinner2;
    protected EditText txtBuscar;

    protected abstract void llenarRecyclerOnCreate();
    protected abstract void setSpecialFonts();
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

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnBuscar){
            onClickBuscar(v);
        } else if (v.getId()==R.id.btnConfigurar)
            onClickConfigurar(v);
    }
}
