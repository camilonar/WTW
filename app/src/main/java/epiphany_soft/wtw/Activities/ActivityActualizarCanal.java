package epiphany_soft.wtw.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Adapters.DetalleCanalAdapter;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Fonts.SpecialFont;
import epiphany_soft.wtw.Negocio.Emisora;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 11/04/2016.
 */
public class ActivityActualizarCanal extends ActivityBase {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    TextView nombreTxt;
   private  EditText name;
    private String nombreCanal;
    private int id_canal, id_emisora;
    public static boolean actualizado=false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_canal);

        setTitle("INFORMACIÒN CANAL");
        name=(EditText)findViewById(R.id.txtNombreCanal);

        Bundle b = getIntent().getExtras();
        nombreCanal = b.getString(DataBaseContract.CanalContract.COLUMN_NAME_CANAL_ID);
        setTitle(nombreCanal);
        name.setText(nombreCanal);
        //((TextView) findViewById(R.id.txtNombreCanal)).setText(nombreCanal);

        this.setSpecialFonts();
      // this.crearRecyclerViewEmisora();
     }

    private void setSpecialFonts(){
        TextView nombreLabel=(TextView) findViewById(R.id.lblNombreCanal);
        nombreLabel.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        TextView emisorasLabel=(TextView) findViewById(R.id.lblEmisoras);
        emisorasLabel.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        //Los textos
        nombreTxt=(TextView) findViewById(R.id.txtNombreCanal);
        nombreTxt.setTypeface(RobotoFont.getInstance(this).getTypeFace());
    }

    @Override
    public void onResume(){
        super.onResume();
        if (actualizado) this.recreate();
        actualizado=false;
    }



    private void crearRecyclerViewEmisora(){
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        Cursor c=db.consultarNombreEmisoras(nombreCanal);
        if (c!=null) {
            Emisora[] emisoras=new Emisora[c.getCount()];
            int i=0;
            while (c.moveToNext()){
                 String nombreemisoras=c.getString(c.getColumnIndex(DataBaseContract.EmisoraContract.COLUMN_NAME_EMISORA_NOMBRE));
                int numerocanal=c.getInt(c.getColumnIndex(DataBaseContract.EmiteContract.COLUMN_NAME_CANAL_NUMERO));
                emisoras[i] = new Emisora(numerocanal,nombreemisoras);
                i++;
            }
            this.crearRecyclerViewEmisora(emisoras);
        }
    }

    private void crearRecyclerViewEmisora(Emisora[] contenido){
        LinearLayout layoutRV = (LinearLayout) findViewById(R.id.layoutCanalRV);
        Float height = getResources().getDimension(R.dimen.size_emisora)*(contenido.length);
        TableRow.LayoutParams params = new TableRow.LayoutParams(200, height.intValue());
        layoutRV.setLayoutParams(params);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_consulta_emisoras);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (contenido!=null) {
            mAdapter = new DetalleCanalAdapter(contenido);
            mRecyclerView.setAdapter(mAdapter);
        }
    }







    /*
    private void crearRecyclerViewEmisora(){
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
    Cursor c=db.consultarNombreEmisoras(nombreCanal);
    if (c!=null) {
        String[] nombreemisoras=new String[c.getCount()];
        String[] numerocanal=new String[c.getCount()];
        int i=0;
        while (c.moveToNext()){
            nombreemisoras[i]=c.getString(c.getColumnIndex(DataBaseContract.EmisoraContract.COLUMN_NAME_EMISORA_NOMBRE));
            numerocanal[i]="#Canal: "+c.getString(c.getColumnIndex(DataBaseContract.EmiteContract.COLUMN_NAME_CANAL_NUMERO));
            i++;
        }
        this.crearRecyclerViewEmisora(nombreemisoras, numerocanal);
    }
}*/

 /* esto es para  los metodos actualizar de la clase ActivityActualizarCanal
    public void onClickActualizarCanal(View v) {
            if (nombreTxt.getText().toString().trim().equals("")) {
                nombreTxt.setError("Introduzca el nombre del canal");
                return;
            }
            this.ActualizarInfoCanal();
        }

        private void ActualizarInfoCanal(){
            DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
            // el actualizar esta mal , toca cambiar el parametro para nuevo y viejo ojo.. por ahora lo ppongo asi
            boolean success=db.actualizarCanal(nombreTxt.getText().toString(), nombreTxt.getText().toString() );
            if (success) {
                ActualizarEmite();
                createToast("Canal Actualizado");
            }
            else createToast("El Canal No se Puede Actualizar");
        }

        private boolean ActualizarEmite(){
            EmisoraAdapter e = (EmisoraAdapter) mAdapter;
            ArrayList<EmisoraAdapter.ViewHolder> listHolder = e.getMisViewHolder();
            String nombreCanal = nombreTxt.getText().toString();
            int idEmisora;
            Integer numCanal;
            for (int i=0;i<listHolder.size();i++){
                EmisoraAdapter.ViewHolder ev = listHolder.get(i);
                if (ev.ck.isChecked()){
                    idEmisora = ev.idEmisora;
                    if (!ev.numCanalEdit.getText().toString().equals(""))
                        numCanal = new Integer(ev.numCanalEdit.getText().toString());
                    else
                        numCanal = null;
                    DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
                    // esto esta mal toca actualizar el viejo  y el nuevo
                    db.actualizarEmite(nombreCanal,nombreCanal,idEmisora,numCanal);
                }
            }
            return true;
        }
aqui termina lo de actualizar canal*/

}
