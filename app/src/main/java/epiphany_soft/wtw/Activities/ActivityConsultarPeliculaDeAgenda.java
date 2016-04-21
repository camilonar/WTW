package epiphany_soft.wtw.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Adapters.PeliculaAdapter;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Negocio.Canal;
import epiphany_soft.wtw.Negocio.Programa;
import epiphany_soft.wtw.Negocio.Sesion;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 22/03/2016.
 */
public class ActivityConsultarPeliculaDeAgenda extends ActivityBase {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText txtBuscar;
    public static boolean actualizado=false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_peliculas_de_agenda);
        txtBuscar = (EditText) findViewById(R.id.txtBuscarPelicula);
        setTitle("CONSULTAR PELÍCULAS DE MI AGENDA");
        llenarRecyclerOnCreate();
        setSpecialFonts();
    }

    private void setSpecialFonts(){
        txtBuscar.setTypeface(RobotoFont.getInstance(this).getTypeFace());
    }
    @Override
    public void onResume(){
        super.onResume();
        if (actualizado) this.recreate();
        actualizado=false;
    }


    private void crearRecycledView(Programa[] contenido){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_consulta_pelicula);
        // Se usa cuando se sabe que cambios en el contenido no cambian el tamaño del layout
        mRecyclerView.setHasFixedSize(true);
        // Se usa un layout manager lineal para el recycler view
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Se especifica el adaptador
        if (contenido!=null) {
            mAdapter = new PeliculaAdapter(contenido);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void onClickBuscarPeliculaAgenda(View v) {
        String text = txtBuscar.getText().toString();
        //TODO: Revisar si es mejor usar v.getContext()
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        if (!text.equals("")){
            Cursor c;
            if (Sesion.getInstance().isActiva()){
                c=db.consultarPeliculasDeAgenda(text, Sesion.getInstance().getIdUsuario());
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

    private void llenarRecyclerOnCreate() {
        DataBaseConnection db = new DataBaseConnection(this.getBaseContext());
        Cursor c;
        if (Sesion.getInstance().isActiva()) {
            c = db.consultarPeliculasDeAgenda("", Sesion.getInstance().getIdUsuario());

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
