package epiphany_soft.wtw.Strategies;

import epiphany_soft.wtw.Fragments.FragmentConsultarPrograma;

/**
 * Created by Camilo on 14/05/2016.
 */
public abstract class StrategyConsulta {
    public abstract void prepare(FragmentConsultarPrograma context);
    public abstract void query(FragmentConsultarPrograma context);
}
