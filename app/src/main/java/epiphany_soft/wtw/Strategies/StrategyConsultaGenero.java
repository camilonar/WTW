package epiphany_soft.wtw.Strategies;

import android.database.Cursor;

import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.Fragments.FragmentConsultarPrograma;
import epiphany_soft.wtw.Negocio.Sesion;

/**
 * Created by Camilo on 14/05/2016.
 */
public class StrategyConsultaGenero implements StrategyConsulta {
    @Override
    public void prepare(FragmentConsultarPrograma context) {
        context.getTxtBuscar().setText("");
        context.getTxtBuscar().setHint("Consulte por Género");
    }

    @Override
    public Cursor consultarPeliculas(FragmentConsultarPrograma context) {
        DataBaseConnection db=new DataBaseConnection(context.getActivity().getBaseContext());
        String text = context.getTxtBuscar().getText().toString();
        if (Sesion.getInstance().isActiva()){
            return db.consultarPeliculasAndFavoritosPorGenero(text, Sesion.getInstance().getIdUsuario());
        } else {
            return db.consultarPeliculaPorGenero(text);
        }
    }

    @Override
    public Cursor consultarSeries(FragmentConsultarPrograma context) {
        DataBaseConnection db=new DataBaseConnection(context.getActivity().getBaseContext());
        String text = context.getTxtBuscar().getText().toString();
        if (Sesion.getInstance().isActiva()){
            return db.consultarSeriesAndFavoritosPorGenero(text, Sesion.getInstance().getIdUsuario());
        } else {
            return db.consultarSeriePorGenero(text);
        }
    }

    @Override
    public Cursor consultarSeriesAgenda(FragmentConsultarPrograma context) {
        DataBaseConnection db=new DataBaseConnection(context.getActivity().getBaseContext());
        String text = context.getTxtBuscar().getText().toString();
        if (Sesion.getInstance().isActiva()){
            return db.consultarSeriesDeAgendaPorGenero(text, Sesion.getInstance().getIdUsuario());
        }
        return null;
    }


    @Override
    public Cursor consultarPeliculasAgenda(FragmentConsultarPrograma context) {
        DataBaseConnection db=new DataBaseConnection(context.getActivity().getBaseContext());
        String text = context.getTxtBuscar().getText().toString();
        if (Sesion.getInstance().isActiva()){
            return db.consultarPeliculasDeAgendaPorGenero(text, Sesion.getInstance().getIdUsuario());
        }
        return null;
    }


    @Override
    public String getType() {
        return "Genero";
    }
}
