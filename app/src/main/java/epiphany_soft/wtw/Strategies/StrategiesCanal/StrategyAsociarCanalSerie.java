package epiphany_soft.wtw.Strategies.StrategiesCanal;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import epiphany_soft.wtw.Activities.Canal.ActivityAsociarCanal;
import epiphany_soft.wtw.Activities.ActivityDetallePelicula;
import epiphany_soft.wtw.Activities.Series.ActivityDetalleSerie;
import epiphany_soft.wtw.Adapters.DiaAdapter;
import epiphany_soft.wtw.Adapters.HorarioAdapter;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.Negocio.Horario;

/**
 * Created by Camilo on 22/05/2016.
 */
public class StrategyAsociarCanalSerie implements StrategyAsociarCanal {
    @Override
    public boolean registrarInfoHorario(ActivityAsociarCanal context) {
        HorarioAdapter e = (HorarioAdapter) context.getmAdapter();
        ArrayList<HorarioAdapter.ViewHolder> listHolder = e.getMisViewHolder();
        DataBaseConnection db = new DataBaseConnection(context.getBaseContext());
        int idPrograma;
        for (int i=0;i<listHolder.size();i++){
            HorarioAdapter.ViewHolder ev = listHolder.get(i);
            if (ev.isChecked) {
                idPrograma = ev.idPrograma;
                ev.mHorario.setIdPrograma(idPrograma);

                if (ev.mHorario.getId() != 0) {
                    // esto es para el actualizar, que por el momento no se va a realizar
                    db.eliminarHorario(ev.mHorario.getId());//TODO: buscar una mejor forma
                }
                boolean success;
                success = db.insertarHorario(ev.mHorario);
                if (success) {
                    ev.ck.setChecked(true);
                    ev.mHorario.setId(db.getHorarioId(ev.mHorario.getIdPrograma(), ev.mHorario.getNombreCanal()));
                    ActivityDetalleSerie.actualizado = true;
                    ActivityDetallePelicula.actualizado = true;

                    //aqui puse lo de insertar el dia a la relacion diaHorario
                    DiaAdapter d = (DiaAdapter) ev.mAdapter;
                    ArrayList<DiaAdapter.ViewHolder> listadias = d.getMisViewHolder();
                    for (int j = 0; j < listadias.size(); j++) {
                        DiaAdapter.ViewHolder dia = listadias.get(j);
                        if (dia.isChecked) {
                            db.insertarRelacionDiaHorario(ev.mHorario.getId(), j + 1); //(j+1) porque se almacenan los id de los dias

                        }
                    }
                }
            }
            else{
                boolean success;
                if (ev.mHorario.getId() != 0) {
                    success = db.eliminarHorario(ev.mHorario.getId());
                    if (success) {ev.ck.setChecked(false);
                        ev.mHorario.setId(0);
                        ev.mHorario.setIdPrograma(0);
                        ActivityDetalleSerie.actualizado = true;
                        ActivityDetallePelicula.actualizado = true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public RecyclerView.Adapter createAdapter(ActivityAsociarCanal context, Horario[] contenido, int idPrograma) {
        return new HorarioAdapter(context,contenido,idPrograma);
    }
}
