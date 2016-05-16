package epiphany_soft.wtw.Fragments;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Adapters.PeliculaAdapter;
import epiphany_soft.wtw.Negocio.Programa;

/**
 * Created by Camilo on 24/04/2016.
 */
public class FragmentConsultarPeliculas extends FragmentConsultarPrograma {

    @Override
    protected RecyclerView.Adapter createAdapter(Programa[] contenido) {
        return new PeliculaAdapter(contenido);
    }

    public void onClickBuscar(View v) {
        String text = txtBuscar.getText().toString();
        if (!text.equals("")){
            Cursor c;
            c = strategy.consultarPeliculas(this);
            this.crearRecycledView(llenarPrograma(c));
            if (c.getCount()==0) ((ActivityBase)this.getActivity()).createToast("No se encontraron resultados");
        }
    }

    protected void llenarRecyclerOnCreate(){
        Cursor c;
        c=strategy.consultarPeliculas(this);
        if (c!=null) {
            this.crearRecycledView(llenarPrograma(c));
        } else this.crearRecycledView(null);
    }
}
