package epiphany_soft.wtw.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.PeliculaAdapter;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 22/03/2016.
 */
public class ActivityConsultarPelicula extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_peliculas);
        setTitle("CONSULTAR PELÍCULA");
        crearRecycledView(new String[]{""});
    }

    private void crearRecycledView(String[] contenido){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_consulta_pelicula);
        // Se usa cuando se sabe que cambios en el contenido no cambian el tamaño del layout
        mRecyclerView.setHasFixedSize(true);
        // Se usa un layout manager lineal para el recycler view
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Se especifica el adaptador
        mAdapter = new PeliculaAdapter(contenido);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void onClickBuscar(View v) {
        EditText txtBuscar = (EditText) findViewById(R.id.txtBuscar);
        String text = txtBuscar.getText().toString();
        //TODO: Revisar si es mejor usar v.getContext()
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        if (text!=null){
            Cursor c=db.consultarPeliculaLikeNombre(text);
            if (c!=null) {
                String[] nombres=new String[c.getCount()];
                int i=0;
                while (c.moveToNext()) {
                    nombres[i] = c.getString(c.getColumnIndex(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE));
                    i++;
                }
                this.crearRecycledView(nombres);
            }
        }
    }
}
