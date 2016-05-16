package epiphany_soft.wtw.Strategies;

import android.database.Cursor;

import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.Fragments.FragmentConsultarPrograma;
import epiphany_soft.wtw.Negocio.Sesion;

/**
 * Created by Camilo on 14/05/2016.
 */
public class StrategyConsultaNombre implements StrategyConsulta {
    @Override
    public void prepare(FragmentConsultarPrograma context) {
        context.getTxtBuscar().setText("");
        context.getTxtBuscar().setHint("Consulte por Nombre");
    }

    @Override
    public Cursor consultarPeliculas(FragmentConsultarPrograma context) {
        DataBaseConnection db=new DataBaseConnection(context.getActivity().getBaseContext());
        String text = context.getTxtBuscar().getText().toString();
        if (Sesion.getInstance().isActiva()){
            return db.consultarPeliculasAndFavoritos(text, Sesion.getInstance().getIdUsuario());
        } else {
            return db.consultarPeliculaLikeNombre(text);
        }
    }

    @Override
    public Cursor consultarSeries(FragmentConsultarPrograma context) {
        DataBaseConnection db=new DataBaseConnection(context.getActivity().getBaseContext());
        String text = context.getTxtBuscar().getText().toString();
        if (Sesion.getInstance().isActiva()){
            return db.consultarSeriesAndFavoritos(text, Sesion.getInstance().getIdUsuario());
        } else {
            return db.consultarSerieLikeNombre(text);
        }
    }

    @Override
    public Cursor consultarSeriesAgenda(FragmentConsultarPrograma context) {
        DataBaseConnection db=new DataBaseConnection(context.getActivity().getBaseContext());
        String text = context.getTxtBuscar().getText().toString();
        if (Sesion.getInstance().isActiva()){
            return db.consultarSeriesDeAgenda(text, Sesion.getInstance().getIdUsuario());
        }
        return null;
    }


    @Override
    public Cursor consultarPeliculasAgenda(FragmentConsultarPrograma context) {
        DataBaseConnection db=new DataBaseConnection(context.getActivity().getBaseContext());
        String text = context.getTxtBuscar().getText().toString();
        if (Sesion.getInstance().isActiva()){
            return db.consultarPeliculasDeAgenda(text, Sesion.getInstance().getIdUsuario());
        }
        return null;
    }

    @Override
    public String getType() {
        return "Nombre";
    }
}
