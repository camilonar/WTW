package epiphany_soft.wtw.Fragments;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Adapters.SerieAdapter;
import epiphany_soft.wtw.Negocio.Programa;

/**
 * Created by Camilo on 24/04/2016.
 */
public class FragmentConsultarSeriesAgenda extends FragmentConsultarPrograma{

    @Override
         protected RecyclerView.Adapter createAdapter(Programa[] contenido) {
        SerieAdapter s = new SerieAdapter(contenido);
        s.setParent("Agenda");
        return s;
    }

    public void onClickBuscar(View v) {
        String text = txtBuscar.getText().toString();
        if (!text.equals("")){
            Cursor c;
            c=strategy.consultarSeriesAgenda(this);
            if (c!=null) {
                this.crearRecycledView(llenarPrograma(c));
                if (c.getCount()==0) ((ActivityBase)this.getActivity()).createToast("No se encontraron resultados");
            }
        }
    }

    protected void llenarRecyclerOnCreate(){
        Cursor c;
        c = strategy.consultarSeriesAgenda(this);
        if (c != null) {
            this.crearRecycledView(llenarPrograma(c));
        } else this.crearRecycledView(null);
    }

}
