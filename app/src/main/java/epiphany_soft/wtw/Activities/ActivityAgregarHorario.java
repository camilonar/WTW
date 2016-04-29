package epiphany_soft.wtw.Activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Adapters.DiaAdapter;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Fonts.SpecialFont;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 28/04/2016.
 */
public class ActivityAgregarHorario extends ActivityBase {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_horario);
        setTitle("AGREGAR HORARIO");

        crearRecyclerViewDias();
        setSpecialFonts();
    }

    private void crearRecyclerViewDias() {
        String contenido[] = new String[]{"D", "L", "Ma", "Mi", "J", "V", "S"};
        LinearLayout layoutRV = (LinearLayout) findViewById(R.id.layoutHorarioRV);
        Float height = getResources().getDimension(R.dimen.size_dia)*(contenido.length);
        TableRow.LayoutParams params = new TableRow.LayoutParams(1200, height.intValue());
        layoutRV.setLayoutParams(params);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_horario);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (contenido!=null) {
            mAdapter = new DiaAdapter(contenido);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private void setSpecialFonts(){
        TextView horaLabel=(TextView) findViewById(R.id.lblHora);
        horaLabel.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        TextView diasLabel=(TextView) findViewById(R.id.lblDias);
        diasLabel.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        //Los textos
        EditText hora=(EditText) findViewById(R.id.txtHora);
        hora.setTypeface(RobotoFont.getInstance(this).getTypeFace());
    }
}
