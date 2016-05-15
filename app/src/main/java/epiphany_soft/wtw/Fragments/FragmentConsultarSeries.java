package epiphany_soft.wtw.Fragments;

import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Adapters.SerieAdapter;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Negocio.Programa;
import epiphany_soft.wtw.Negocio.Sesion;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 24/04/2016.
 */
public class FragmentConsultarSeries extends FragmentConsultarPrograma{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void setSpecialFonts(){
        txtBuscar.setTypeface(RobotoFont.getInstance(this.getActivity()).getTypeFace());
    }

    private void crearRecycledView(Programa[] contenido){
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.rv_consulta_programa);
        // Se usa cuando se sabe que cambios en el contenido no cambian el tamaño del layout
        mRecyclerView.setHasFixedSize(true);
        // Se usa un layout manager lineal para el recycler view
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Se especifica el adaptador
        if (contenido!=null) {
            mAdapter = new SerieAdapter(contenido);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
    public void onClickConfigurar(View v){
        super.onClickConfigurar(v);
    }
    public void onClickBuscar(View v) {
        String text = txtBuscar.getText().toString();
        //TODO: Revisar si es mejor usar v.getContext()
        DataBaseConnection db=new DataBaseConnection(this.getActivity().getBaseContext());
        if (!text.equals("")){
            Cursor c;
            if (Sesion.getInstance().isActiva()){
                c=db.consultarSeriesAndFavoritos(text, Sesion.getInstance().getIdUsuario());
            }
            else {
                c = db.consultarSerieLikeNombre(text);
            }
            if (c!=null) {
                Programa[] programas=new Programa[c.getCount()];
                int i=0;
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
                this.crearRecycledView(programas);
                if (c.getCount()==0) ((ActivityBase)this.getActivity()).createToast("No se encontraron resultados");
            }
        }
    }

    protected void llenarRecyclerOnCreate(){
        DataBaseConnection db=new DataBaseConnection(this.getActivity().getBaseContext());
        Cursor c;
        if (Sesion.getInstance().isActiva()){
            c=db.consultarSeriesAndFavoritos("", Sesion.getInstance().getIdUsuario());
        }
        else {
            c = db.consultarSerieLikeNombre("");
        }
        if (c!=null) {
            Programa[] programas=new Programa[c.getCount()];
            int i=0;
            while (c.moveToNext()) {
                programas[i] = new Programa();
                String nombre = c.getString(c.getColumnIndex(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE));
                int idPrograma = c.getInt(c.getColumnIndex(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID));
                if (Sesion.getInstance().isActiva()) {
                    boolean isFavorito = c.getInt(c.getColumnIndex(DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID))!=0;
                    programas[i].setFavorito(isFavorito);
                }
                programas[i].setNombre(nombre);
                programas[i].setIdPrograma(idPrograma);
                i++;
            }
            this.crearRecycledView(programas);
        } else this.crearRecycledView(null);
    }
}