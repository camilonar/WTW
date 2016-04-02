package epiphany_soft.wtw.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Fonts.SpecialFont;
import epiphany_soft.wtw.R;

import static epiphany_soft.wtw.DataBase.DataBaseContract.GeneroContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.ProgramaContract;

// se supone que esta clase con ss metodos ya esta bn .. :)
public class ActivityDetalleSerie extends AppCompatActivity {
    String nombre,sinopsis,genero,pais;
    int anio;
    public static boolean actualizado=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_serie);
        Bundle b = getIntent().getExtras();
        String nombreSerie = b.getString(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE);
        setTitle(nombreSerie);
        this.llenarInfo(nombreSerie);
        setSpecialFonts();
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
        //Los textos
        TextView nombreTxt=(TextView) findViewById(R.id.txtNombreSe);
        nombreTxt.setTypeface(RobotoFont.getInstance(this).getTypeFace());
        TextView sinopsisTxt=(TextView) findViewById(R.id.txtSinopsisSe);
        sinopsisTxt.setTypeface(RobotoFont.getInstance(this).getTypeFace());
        TextView anioTxt=(TextView) findViewById(R.id.txtAnioEstreno);
        anioTxt.setTypeface(RobotoFont.getInstance(this).getTypeFace());
        TextView paisTxt=(TextView) findViewById(R.id.txtPaisOrigen);
        paisTxt.setTypeface(RobotoFont.getInstance(this).getTypeFace());
        TextView generoTxt=(TextView) findViewById(R.id.txtGeneroSe);
        generoTxt.setTypeface(RobotoFont.getInstance(this).getTypeFace());

    }


    @Override
    public void onResume(){
        super.onResume();
        if (actualizado) this.recreate();
        actualizado=false;
    }

    private void llenarInfo(String nombreSerie){

        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        Cursor c=db.consultarSeriePorNombre(nombreSerie);
        c.moveToNext();
        nombre = c.getString(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE));
        sinopsis = c.getString(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS));
        genero = c.getString(c.getColumnIndex(GeneroContract.COLUMN_NAME_GENERO_NOMBRE));
        anio = c.getInt(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO));
        pais = c.getString(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN));
        if (!nombre.equals("")) ((TextView) findViewById(R.id.txtNombreSe)).setText(nombre);
        else ((TextView) findViewById(R.id.txtNombreSe)).setText("Serie sin nombre");
        if (!sinopsis.equals("")) ((TextView) findViewById(R.id.txtSinopsisSe)).setText(sinopsis);
        else ((TextView) findViewById(R.id.txtSinopsisSe)).setText("Serie sin sinopsis");
        if (!genero.equals("")) ((TextView) findViewById(R.id.txtGeneroSe)).setText(genero);
        else ((TextView) findViewById(R.id.txtGeneroSe)).setText("Serie sin genero");
        if (anio!=0) ((TextView) findViewById(R.id.txtAnioEstreno)).setText(Integer.toString(anio));
        else ((TextView) findViewById(R.id.txtAnioEstreno)).setText("Serie sin año registrado");
        if (!pais.equals("")) ((TextView) findViewById(R.id.txtPaisOrigen)).setText(pais);
        else ((TextView) findViewById(R.id.txtPaisOrigen)).setText("Serie sin país registrado");
    }

    public void onClickActualizar(View v){
        Intent i = new Intent(this, ActivityActualizarSerie.class);
        Bundle b = new Bundle();
        b.putString(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE,nombre);
        b.putString(ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS,sinopsis);
        b.putString(GeneroContract.COLUMN_NAME_GENERO_NOMBRE, genero);
        b.putString(ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN,pais);
        b.putInt(ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO,anio);
        i.putExtras(b);
        startActivity(i);
    }
}
