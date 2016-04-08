package epiphany_soft.wtw.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 7/04/2016.
 */
public class ActivityInicioSesion extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        this.getSupportActionBar().hide();
    }

    public void onClickInicioSesion(View v){

    }
}
