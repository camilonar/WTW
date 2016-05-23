package epiphany_soft.wtw.Strategies.StrategiesCanal;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;

import epiphany_soft.wtw.Activities.Canal.ActivityAsociarCanal;
import epiphany_soft.wtw.Adapters.CanalPeliculaAdapter;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Negocio.Horario;

/**
 * Created by Camilo on 22/05/2016.
 */
public class StrategyAsociarCanalPelicula implements StrategyAsociarCanal {
    @Override
    public boolean registrarInfoHorario(ActivityAsociarCanal context) {
        return false; //No es necesario
    }

    @Override
    public RecyclerView.Adapter createAdapter(ActivityAsociarCanal context, Horario[] contenido, int idPrograma) {
        return new CanalPeliculaAdapter(contenido,idPrograma);
    }

    @Override
    public Horario[] consultarHorario(ActivityAsociarCanal context) {
        DataBaseConnection db=new DataBaseConnection(context.getBaseContext());
        Cursor c=db.getAllCanalesWithHorario(context.idPrograma);
        if (c != null) {
            Horario[] horarios = new Horario[c.getCount()];
            int i = 0;
            while (c.moveToNext()) {
                String nombreCanal = c.getString(c.getColumnIndex(DataBaseContract.CanalContract.COLUMN_NAME_CANAL_ID));
                int idPrograma = c.getInt(c.getColumnIndex(DataBaseContract.HorarioContract.COLUMN_NAME_PROGRAMA_ID));
                int idRel = -1;
                String Hora = "";
                horarios[i] = new Horario(idRel, nombreCanal, idPrograma, Hora);
                i++;
            }
            return horarios;
        }
        return null;
    }
}
