package epiphany_soft.wtw.Strategies;

import android.database.Cursor;

import epiphany_soft.wtw.Fragments.FragmentConsultarPrograma;

/**
 * Created by Camilo on 14/05/2016.
 */
public class StrategyConsultaHorario implements StrategyConsulta {
    @Override
    public void prepare(FragmentConsultarPrograma context) {

    }

    @Override
    public Cursor consultarPeliculas(FragmentConsultarPrograma context) {
        return null;
    }

    @Override
    public Cursor consultarSeries(FragmentConsultarPrograma context) {
        return null;
    }

    @Override
    public Cursor consultarSeriesAgenda(FragmentConsultarPrograma context) {
        return null;
    }

    @Override
    public Cursor consultarPeliculasAgenda(FragmentConsultarPrograma context) {
        return null;
    }

    @Override
    public String getType() {
        return "Horario";
    }
}
