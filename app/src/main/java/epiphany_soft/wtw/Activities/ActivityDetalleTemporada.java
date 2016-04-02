package epiphany_soft.wtw.Activities;

import android.os.Bundle;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 2/04/2016.
 */
public class ActivityDetalleTemporada extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_temporada);
        setTitle("TEMPORADA");//TODO revisar el título
    }
}
