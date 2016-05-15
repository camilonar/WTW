package epiphany_soft.wtw.Strategies;

import epiphany_soft.wtw.Fragments.FragmentConsultarPrograma;

/**
 * Created by Camilo on 14/05/2016.
 */
public class StrategyConsultaGenero implements StrategyConsulta {
    @Override
    public void prepare(FragmentConsultarPrograma context) {

    }

    @Override
    public void query(FragmentConsultarPrograma context) {

    }

    @Override
    public String getType() {
        return "Genero";
    }
}
