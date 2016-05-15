package epiphany_soft.wtw.Strategies;

import epiphany_soft.wtw.Fragments.FragmentConsultarPrograma;

/**
 * Created by Camilo on 14/05/2016.
 */
public interface StrategyConsulta {
    public void prepare(FragmentConsultarPrograma context);
    public void query(FragmentConsultarPrograma context);
    public String getType();
}
