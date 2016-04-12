package epiphany_soft.wtw.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Adapters.EmisoraAdapter;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Fonts.SpecialFont;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 11/04/2016.
 */
public class ActivityAgregarCanal extends ActivityBase {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_canal);

        setTitle("AGREGAR CANAL");

        setSpecialFonts();
        llenarRecyclerOnCreate();
    }

    private void setSpecialFonts(){
        TextView nombreLabel=(TextView) findViewById(R.id.lblNombreCanal);
        nombreLabel.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        TextView numeroLabel=(TextView) findViewById(R.id.lblNumeroCanal);
        numeroLabel.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        //Los textos
        TextView nombreTxt=(TextView) findViewById(R.id.txtNombreCanal);
        nombreTxt.setTypeface(RobotoFont.getInstance(this).getTypeFace());
    }

    public void onClickRegistrarCanal(View v) {

    }

    private void crearRecycledView(String[] contenido){
        LinearLayout layoutRV = (LinearLayout) findViewById(R.id.layoutCanalRV);
        Float height = getResources().getDimension(R.dimen.size_emisora)*(contenido.length);
        TableRow.LayoutParams params = new TableRow.LayoutParams(200, height.intValue());
        layoutRV.setLayoutParams(params);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_consulta_emisoras);
        // Se usa un layout manager lineal para el recycler view
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Se especifica el adaptador
        if (contenido!=null) {
            mAdapter = new EmisoraAdapter(contenido);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private void llenarRecyclerOnCreate(){
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        Cursor c=db.consultarAllEmisoras();
        if (c!=null) {
            String[] nombres=new String[c.getCount()];
            int i=0;
            while (c.moveToNext()) {
                nombres[i] = c.getString(c.getColumnIndex(DataBaseContract.EmisoraContract.COLUMN_NAME_EMISORA_NOMBRE));
                i++;
            }
            this.crearRecycledView(nombres);
        } else this.crearRecycledView(null);
    }
}
