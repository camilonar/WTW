package epiphany_soft.wtw.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Adapters.TemporadaAdapter;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Fonts.SpecialFont;
import epiphany_soft.wtw.R;

import static epiphany_soft.wtw.DataBase.DataBaseContract.GeneroContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.ProgramaContract;

// se supone que esta clase con ss metodos ya esta bn .. :)
public class ActivityDetalleSerie extends ActivityBase {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String nombre,sinopsis,genero,pais;
    private int anio, idSerie;
    public static boolean actualizado=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_serie);

        Bundle b = getIntent().getExtras();
        String nombreSerie = b.getString(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE);
        setTitle(nombreSerie);
        this.llenarInfo(nombreSerie);
        crearRecycledView(getContenidoTemporadas());
        setSpecialFonts();
    }

    private void setSpecialFonts(){
        TextView nombreLabel=(TextView) findViewById(R.id.lblNombrePrograma);
        nombreLabel.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        TextView sinopsisLabel=(TextView) findViewById(R.id.lblSinopsis);
        sinopsisLabel.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        TextView lblTemporadas=(TextView) findViewById(R.id.lblTemporadas);
        lblTemporadas.setTypeface(SpecialFont.getInstance(this).getTypeFace());
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

    private String[] getContenidoTemporadas(){
        DataBaseConnection db = new DataBaseConnection(this);
        Cursor c=db.getTemporadasDeSerie(idSerie);
            if (c!=null) {
                String[] nombres = new String[c.getCount()];
                int i = 0;
                while (c.moveToNext()) {
                    nombres[i] = c.getString(c.getColumnIndex(DataBaseContract.TemporadaContract.COLUMN_NAME_TEMPORADA_ID));
                    i++;
                }
                return nombres;
            } else return null;
    }

    private void crearRecycledView(String[] contenido){
        LinearLayout layoutRV = (LinearLayout) findViewById(R.id.layoutTempRV);
        Float height = getResources().getDimension(R.dimen.size_temporada)*(contenido.length);
        TableRow.LayoutParams params = new TableRow.LayoutParams(200, height.intValue());
        layoutRV.setLayoutParams(params);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_temporadas);
        // Se usa cuando se sabe que cambios en el contenido no cambian el tamaño del layout
        mRecyclerView.setHasFixedSize(false);
        // Se usa un layout manager lineal para el recycler view
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Se especifica el adaptador
        if (contenido!=null) {
            mAdapter = new TemporadaAdapter(contenido);
            mRecyclerView.setAdapter(mAdapter);
        }
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
        idSerie =  c.getInt(c.getColumnIndex(ProgramaContract.COLUMN_NAME_PROGRAMA_ID));
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
        b.putString(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE, nombre);
        b.putString(ProgramaContract.COLUMN_NAME_PROGRAMA_SINOPSIS,sinopsis);
        b.putString(GeneroContract.COLUMN_NAME_GENERO_NOMBRE, genero);
        b.putString(ProgramaContract.COLUMN_NAME_PROGRAMA_PAIS_ORIGEN,pais);
        b.putInt(ProgramaContract.COLUMN_NAME_PROGRAMA_ANIO_ESTRENO,anio);
        i.putExtras(b);
        startActivity(i);
    }

    public int getIdSerie(){
        return idSerie;
    }

    public String getNombreSerie(){
        return nombre;
    }
}
