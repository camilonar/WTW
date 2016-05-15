package epiphany_soft.wtw.Fragments;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Adapters.PeliculaAdapter;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.Negocio.Programa;
import epiphany_soft.wtw.Negocio.Sesion;

/**
 * Created by Camilo on 24/04/2016.
 */
public class FragmentConsultarPeliculasAgenda extends FragmentConsultarPrograma {

    @Override
    protected RecyclerView.Adapter createAdapter(Programa[] contenido) {
        return new PeliculaAdapter(contenido);
    }

    public void onClickBuscar(View v) {
        String text = txtBuscar.getText().toString();
        //TODO: Revisar si es mejor usar v.getContext()
        DataBaseConnection db=new DataBaseConnection(this.getActivity().getBaseContext());
        if (!text.equals("")){
            Cursor c;
            if (Sesion.getInstance().isActiva()){
                c=db.consultarPeliculasDeAgenda(text, Sesion.getInstance().getIdUsuario());
                if (c!=null) {
                    this.crearRecycledView(llenarPrograma(c));
                    if (c.getCount()==0) ((ActivityBase)this.getActivity()).createToast("No se encontraron resultados");
                }

            }
        }
    }
    protected void llenarRecyclerOnCreate() {
        DataBaseConnection db = new DataBaseConnection(this.getActivity().getBaseContext());
        Cursor c;
        if (Sesion.getInstance().isActiva()) {
            c = db.consultarPeliculasDeAgenda("", Sesion.getInstance().getIdUsuario());

            if (c != null) {
                this.crearRecycledView(llenarPrograma(c));

            } else this.crearRecycledView(null);
        }
    }
}
