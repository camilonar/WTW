package epiphany_soft.wtw.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Negocio.Sesion;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 9/04/2016.
 */
public class ActivityGestionUsuario extends ActivityBase {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_usuario);
        setTitle("GESTION DE USUARIO");
    }

    @Override
    public void onResume(){
        super.onResume();
        if (Sesion.getInstance().isActiva()) {
            Button b = (Button) findViewById(R.id.btnInicioSesion);
            b.setEnabled(false);
            b.setTextColor(Color.GRAY);
            b = (Button) findViewById(R.id.btnCerrarSesion);
            b.setEnabled(true);
            b.setTextColor(this.getResources().getColor(R.color.colorButtonText));
            b= (Button) findViewById(R.id.btnModificarUsuario);
            b.setEnabled(true);
            b.setTextColor(this.getResources().getColor(R.color.colorButtonText));
        }
    }

    public void onClickIniciarSesion(View v){
        Intent i = new Intent(this, ActivityInicioSesion.class);
        startActivity(i);
    }

    public void onClickCerrarSesion(View v){
        Sesion.getInstance().refresh();
        Button b = (Button) findViewById(R.id.btnInicioSesion);
        b.setEnabled(true);
        b.setTextColor(this.getResources().getColor(R.color.colorButtonText));
        b = (Button) findViewById(R.id.btnCerrarSesion);
        b.setEnabled(false);
        b.setTextColor(Color.GRAY);
        b = (Button) findViewById(R.id.btnModificarUsuario);
        b.setEnabled(false);
        b.setTextColor(Color.GRAY);
    }

    public void onClickModificarUsuario(View v){
        Intent i = new Intent(this, ActivityModificarUsuario.class);
        startActivity(i);
    }
}
