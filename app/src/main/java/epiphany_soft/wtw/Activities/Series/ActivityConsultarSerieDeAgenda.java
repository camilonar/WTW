package epiphany_soft.wtw.Activities.Series;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Adapters.SerieAdapter;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Negocio.Programa;
import epiphany_soft.wtw.Negocio.Sesion;
import epiphany_soft.wtw.R;


public class ActivityConsultarSerieDeAgenda extends ActivityBase {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText txtBuscar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_series_de_agenda);
        txtBuscar = (EditText) findViewById(R.id.txtBuscar);
        setTitle("CONSULTAR SERIES DE MI AGENDA ");
        llenarRecyclerOnCreate();
        setSpecialFonts();
    }

    private void setSpecialFonts(){
        txtBuscar.setTypeface(RobotoFont.getInstance(this).getTypeFace());
    }

    private void crearRecycledView(Programa[] contenido){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_consulta_serie);
        // Se usa cuando se sabe que cambios en el contenido no cambian el tamaño del layout
        mRecyclerView.setHasFixedSize(true);
        // Se usa un layout manager lineal para el recycler view
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Se especifica el adaptador
        if (contenido!=null) {
            mAdapter = new SerieAdapter(contenido);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void onClickBuscarSerieAgenda(View v) {
        String text = txtBuscar.getText().toString();
        //TODO: Revisar si es mejor usar v.getContext()
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        if (!text.equals("")){
            Cursor c;
            if (Sesion.getInstance().isActiva()){
                c=db.consultarSeriesDeAgenda(text, Sesion.getInstance().getIdUsuario());

                if (c!=null) {
                Programa[] programas=new Programa[c.getCount()];
                int i=0;
                while (c.moveToNext()) {
                    programas[i] = new Programa();
                    String nombre = c.getString(c.getColumnIndex(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE));
                    int idPrograma = c.getInt(c.getColumnIndex(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID));
                    boolean isFavorito = c.getInt(c.getColumnIndex(DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID))!=0;
                    programas[i].setNombre(nombre);
                    programas[i].setIdPrograma(idPrograma);
                    programas[i].setFavorito(isFavorito);
                    i++;
                }
                this.crearRecycledView(programas);
                if (c.getCount()==0) createToast("No se encontraron resultados");
            }
            }
        }
    }

    private void llenarRecyclerOnCreate(){
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        Cursor c;
        if (Sesion.getInstance().isActiva()) {
            c = db.consultarSeriesDeAgenda("", Sesion.getInstance().getIdUsuario());


            if (c != null) {
                Programa[] programas = new Programa[c.getCount()];
                int i = 0;
                while (c.moveToNext()) {
                    programas[i] = new Programa();
                    String nombre = c.getString(c.getColumnIndex(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE));
                    int idPrograma = c.getInt(c.getColumnIndex(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID));
                    if (Sesion.getInstance().isActiva()) {
                        boolean isFavorito = c.getInt(c.getColumnIndex(DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID)) != 0;
                        programas[i].setFavorito(isFavorito);
                    }
                    programas[i].setNombre(nombre);
                    programas[i].setIdPrograma(idPrograma);
                    i++;
                }
                this.crearRecycledView(programas);
            } else this.crearRecycledView(null);
        }
    }
}
