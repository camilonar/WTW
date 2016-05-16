package epiphany_soft.wtw.Strategies;

import android.database.Cursor;

import epiphany_soft.wtw.Fragments.FragmentConsultarPrograma;

/**
 * Created by Camilo on 14/05/2016.
 */
public interface StrategyConsulta {
    public void prepare(FragmentConsultarPrograma context);
    public Cursor consultarPeliculas(FragmentConsultarPrograma context);
    public Cursor consultarSeries(FragmentConsultarPrograma context);
    public Cursor consultarSeriesAgenda(FragmentConsultarPrograma context);
    public Cursor consultarPeliculasAgenda(FragmentConsultarPrograma context);
    public String getType();
}
