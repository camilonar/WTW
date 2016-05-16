package epiphany_soft.wtw.Fragments;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Adapters.PeliculaAdapter;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.Negocio.Programa;

/**
 * Created by Camilo on 24/04/2016.
 */
public class FragmentConsultarPeliculasAgenda extends FragmentConsultarPrograma {

    @Override
    protected RecyclerView.Adapter createAdapter(Programa[] contenido) {
        PeliculaAdapter p = new PeliculaAdapter(contenido);
        p.setParent("Agenda");
        return p;
    }

    public void onClickBuscar(View v) {
        String text = txtBuscar.getText().toString();
        //TODO: Revisar si es mejor usar v.getContext()
        DataBaseConnection db=new DataBaseConnection(this.getActivity().getBaseContext());
        if (!text.equals("")){
            Cursor c;
            c = strategy.consultarPeliculasAgenda(this);
            if (c!=null) {
                this.crearRecycledView(llenarPrograma(c));
                if (c.getCount()==0) ((ActivityBase)this.getActivity()).createToast("No se encontraron resultados");
            }
        }
    }
    protected void llenarRecyclerOnCreate() {
        DataBaseConnection db = new DataBaseConnection(this.getActivity().getBaseContext());
        Cursor c;
        c = strategy.consultarPeliculasAgenda(this);
        if (c != null) {
            this.crearRecycledView(llenarPrograma(c));
        } else this.crearRecycledView(null);
    }
}
