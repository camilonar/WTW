package epiphany_soft.wtw.Strategies.StrategiesCanal;

import android.support.v7.widget.RecyclerView;

import epiphany_soft.wtw.Activities.Canal.ActivityAsociarCanal;
import epiphany_soft.wtw.Negocio.Horario;

/**
 * Created by Camilo on 22/05/2016.
 */public interface StrategyAsociarCanal {
    public boolean registrarInfoHorario(ActivityAsociarCanal context);
    public RecyclerView.Adapter createAdapter(ActivityAsociarCanal context, Horario[] contenido, int idPrograma);
}
