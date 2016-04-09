package epiphany_soft.wtw.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import epiphany_soft.wtw.Activities.Series.ActivityConsultarSerie;
import epiphany_soft.wtw.Negocio.Sesion;
import epiphany_soft.wtw.R;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void onClickConsultar(View v){
        Intent i = new Intent(this, ActivityConsultarPelicula.class);
        startActivity(i);
    }

    public void onClickConsultarSerie(View v){
        Intent i = new Intent(this, ActivityConsultarSerie.class);
        startActivity(i);
    }

    public void onClickAgregarGenero(View v){
        Intent i = new Intent(this, ActivityAgregarGenero.class);
        startActivity(i);
    }

    public void onClickAgregarPrograma(View v){
        Intent i = new Intent(this, ActivityRegistrarPrograma.class);
        startActivity(i);
    }

    public void onClickBtnEliminarGenero(View v){
        Intent i = new Intent(this,ActivityEliminarGenero.class);
        startActivity(i);
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
    }

    public void onClickModificarUsuario(View v){
        Intent i = new Intent(this, ActivityModificarUsuario.class);
        startActivity(i);
    }
}
