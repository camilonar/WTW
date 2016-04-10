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
        hideWhenNoSesion();
        showWhenSesion();
    }

    public void hideWhenNoSesion(){
        if (!Sesion.getInstance().isActiva()){
            Button b = (Button) findViewById(R.id.btnGenero);
            hide(b);
            b= (Button) findViewById(R.id.btnEliminarGenero);
            hide(b);
            b = (Button) findViewById(R.id.btnAgregarPrograma);
            hide(b);
        }
    }

    public void showWhenSesion(){
        if (Sesion.getInstance().isActiva()){
            Button b = (Button) findViewById(R.id.btnGenero);
            show(b);
            b= (Button) findViewById(R.id.btnEliminarGenero);
            show(b);
            b = (Button) findViewById(R.id.btnAgregarPrograma);
            show(b);
        }
    }

    private void hide(View v){
        v.setEnabled(false);
        ((Button)v).setTextColor(Color.GRAY);
    }

    private void show(View v){
        v.setEnabled(true);
        ((Button)v).setTextColor(getResources().getColor(R.color.colorButtonText));
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

    public void onClickGestionUsuario(View v){
        Intent i = new Intent(this, ActivityGestionUsuario.class);
        startActivity(i);
    }
}
