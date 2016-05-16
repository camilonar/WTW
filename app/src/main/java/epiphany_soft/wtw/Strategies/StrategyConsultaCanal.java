package epiphany_soft.wtw.Strategies;

import android.database.Cursor;

import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.Fragments.FragmentConsultarPrograma;
import epiphany_soft.wtw.Negocio.Sesion;

/**
 * Created by Camilo on 14/05/2016.
 */
public class StrategyConsultaCanal implements StrategyConsulta {
    @Override
    public void prepare(FragmentConsultarPrograma context) {
        context.getTxtBuscar().setText("");
        context.getTxtBuscar().setHint("Consulte por Canal");
    }

    @Override
    public Cursor consultarPeliculas(FragmentConsultarPrograma context) {
        DataBaseConnection db=new DataBaseConnection(context.getActivity().getBaseContext());
        String text = context.getTxtBuscar().getText().toString();
        if (Sesion.getInstance().isActiva()){
            return db.consultarPeliculasAndFavoritosPorCanal(text, Sesion.getInstance().getIdUsuario());
        } else {
            return db.consultarPeliculaPorCanal(text);
        }
    }

    @Override
    public Cursor consultarSeries(FragmentConsultarPrograma context) {
        DataBaseConnection db=new DataBaseConnection(context.getActivity().getBaseContext());
        String text = context.getTxtBuscar().getText().toString();
        if (Sesion.getInstance().isActiva()){
            return db.consultarSeriesAndFavoritosPorCanal(text, Sesion.getInstance().getIdUsuario());
        } else {
            return db.consultarSeriePorCanal(text);
        }
    }

    @Override
    public Cursor consultarSeriesAgenda(FragmentConsultarPrograma context) {
        DataBaseConnection db=new DataBaseConnection(context.getActivity().getBaseContext());
        String text = context.getTxtBuscar().getText().toString();
        if (Sesion.getInstance().isActiva()){
            return db.consultarSeriesDeAgendaPorCanal(text, Sesion.getInstance().getIdUsuario());
        }
        return null;
    }


    @Override
    public Cursor consultarPeliculasAgenda(FragmentConsultarPrograma context) {
        DataBaseConnection db=new DataBaseConnection(context.getActivity().getBaseContext());
        String text = context.getTxtBuscar().getText().toString();
        if (Sesion.getInstance().isActiva()){
            return db.consultarPeliculasDeAgendaPorCanal(text, Sesion.getInstance().getIdUsuario());
        }
        return null;
    }

    @Override
    public String getType() {
        return "Canal";
    }
}
