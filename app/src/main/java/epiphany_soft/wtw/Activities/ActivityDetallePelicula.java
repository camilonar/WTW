package epiphany_soft.wtw.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Fonts.SpecialFont;
import epiphany_soft.wtw.Negocio.Sesion;
import epiphany_soft.wtw.R;

import static epiphany_soft.wtw.DataBase.DataBaseContract.GeneroContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.ProgramaContract;

/**
 * Created by Camilo on 26/03/2016.
 */
public class ActivityDetallePelicula extends ActivityBase {
    private String nombre,sinopsis,genero,pais;
    private int anio,idPrograma;
    private boolean calificado;
    public static boolean actualizado=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pelicula);
        //Se recibe el nombre del programa
        Bundle b = getIntent().getExtras();
        String nombrePelicula = b.getString(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE);
        setTitle(nombrePelicula);
        this.llenarInfo(nombrePelicula);
        this.configurarRatingBar();
        this.setSpecialFonts();
    }

   private void setSpecialFonts(){
        TextView nombreLabel=(TextView) findViewById(R.id.lblNombrePrograma);
        nombreLabel.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        TextView sinopsisLabel=(TextView) findViewById(R.id.lblSinopsis);
        sinopsisLabel.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        TextView genero=(TextView) findViewById(R.id.lblGenero);
        genero.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        TextView lblAnio=(TextView) findViewById(R.id.lblAnioEstreno);
        lblAnio.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        TextView lblPais=(TextView) findViewById(R.id.lblPaisOrigen);
        lblPais.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        TextView lblCalificacion=(TextView) findViewById(R.id.lblCalificacion);
        lblCalificacion.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        //Los textos
        TextView nombreTxt=(TextView) findViewById(R.id.txtNombrePelicula);
        nombreTxt.setTypeface(RobotoFont.getInstance(this).getTypeFace());
        TextView sinopsisTxt=(TextView) findViewById(R.id.txtSinopsis);
        sinopsisTxt.setTypeface(RobotoFont.getInstance(this).getTypeFace());
        TextView anioTxt=(TextView) findViewById(R.id.txtAnioEstreno);
        anioTxt.setTypeface(RobotoFont.getInstance(this).getTypeFace());
        TextView paisTxt=(TextView) findViewById(R.id.txtPaisOrigen);
        paisTxt.setTypeface(RobotoFont.getInstance(this).getTypeFace());
        TextView generoTxt=(TextView) findViewById(R.id.txtGenero);
        generoTxt.setTypeface(RobotoFont.getInstance(this).getTypeFace());

    }

    @Override
    public void onResume(){
        super.onResume();
        if (actualizado) this.recreate();
        actualizado=false;
    }

    private void llenarInfo(String nombrePelicula){
        //TODO: Revisar si es mejor usar v.getContext()
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        Cursor c=db.consultarPeliculaPorNombre(nombrePelicula);
        c.moveToNext();
        nombre = c.getString(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE));
        sinopsis = c.getString(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS));
        genero = c.getString(c.getColumnIndex(GeneroContract.COLUMN_NAME_GENERO_NOMBRE));
        anio = c.getInt(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO));
        pais = c.getString(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN));
        idPrograma = c.getInt(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_ID));
        if (!nombre.equals("")) ((TextView) findViewById(R.id.txtNombrePelicula)).setText(nombre);
        else ((TextView) findViewById(R.id.txtNombrePelicula)).setText("Película sin nombre");
        if (!sinopsis.equals("")) ((TextView) findViewById(R.id.txtSinopsis)).setText(sinopsis);
        else ((TextView) findViewById(R.id.txtSinopsis)).setText("Película sin sinopsis");
        if (!genero.equals("")) ((TextView) findViewById(R.id.txtGenero)).setText(genero);
        else ((TextView) findViewById(R.id.txtGenero)).setText("Película sin genero");
        if (anio!=0) ((TextView) findViewById(R.id.txtAnioEstreno)).setText(Integer.toString(anio));
        else ((TextView) findViewById(R.id.txtAnioEstreno)).setText("Película sin año registrado");
        if (!pais.equals("")) ((TextView) findViewById(R.id.txtPaisOrigen)).setText(pais);
        else ((TextView) findViewById(R.id.txtPaisOrigen)).setText("Película sin país registrado");
    }

    public void onClickActualizarPelicula(View v){
        Intent i = new Intent(this, ActivityActualizarPelicula.class);
        Bundle b = new Bundle();
        b.putString(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE, nombre);
        b.putString(ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS, sinopsis);
        b.putString(GeneroContract.COLUMN_NAME_GENERO_NOMBRE, genero);
        b.putString(ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN,pais);
        b.putInt(ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO,anio);
        i.putExtras(b);
        startActivity(i);
    }

    private void configurarRatingBar(){
        final RatingBar calificacion = (RatingBar) findViewById(R.id.ratingBar);
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        Cursor c=db.consultarCalificacion(Sesion.getInstance().getIdUsuario(),idPrograma);
        calificado=false;
        if (c!=null && c.getCount()==1){
            c.moveToNext();
            float rating = c.getFloat(c.getColumnIndex(DataBaseContract.CalificacionContract.COLUMN_NAME_VALOR_CALIFICACION));
            calificacion.setRating(rating);
            calificado=true;
        }
        calificacion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                DataBaseConnection db=new DataBaseConnection(ratingBar.getContext());
                if (calificado){
                    db.actualizarCalificacion(Sesion.getInstance().getIdUsuario(), idPrograma, calificacion.getRating());
                } else {
                    db.insertarCalificacion(Sesion.getInstance().getIdUsuario(),idPrograma,calificacion.getRating());
                }
            }
        });
    }


    protected void hideWhenNoSession(){
        if (!Sesion.getInstance().isActiva()){
            FloatingActionButton b = (FloatingActionButton) findViewById(R.id.fab);
            hide(b);
            RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
            hide(rb);
            TextView txt = (TextView) findViewById(R.id.lblCalificacion);
            hide(txt);
            View v = (View) findViewById(R.id.v1);
            hide(v);
            v = (View) findViewById(R.id.v2);
            hide(v);
        }
    }

    protected void showWhenSession(){
        if (Sesion.getInstance().isActiva()){
            FloatingActionButton b = (FloatingActionButton) findViewById(R.id.fab);
            show(b);
            RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
            show(rb);
            TextView txt = (TextView) findViewById(R.id.lblCalificacion);
            show(txt);
            View v = (View) findViewById(R.id.v1);
            show(v);
            v = (View) findViewById(R.id.v2);
            show(v);
        }
    }
}
