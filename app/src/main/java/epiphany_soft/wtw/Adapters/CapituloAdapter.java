package epiphany_soft.wtw.Adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import epiphany_soft.wtw.Activities.Series.ActivityDetalleCapitulo;
import epiphany_soft.wtw.Activities.Series.ActivityDetalleTemporada;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Negocio.Capitulo;
import epiphany_soft.wtw.Negocio.Sesion;
import epiphany_soft.wtw.R;

import static epiphany_soft.wtw.DataBase.DataBaseContract.CapituloContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.ProgramaContract;
// parece q ya esta bn.

public class CapituloAdapter extends RecyclerView.Adapter<CapituloAdapter.ViewHolder> {
    private String[] numCaps;
    private String[] nombreCaps;

    private Capitulo[] mDataset;
    private String parent;
    private ArrayList<ViewHolder> misViewHolder;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public CardView mCardView;
        public TextView numCapTextView, nombreCapTextView;
        public ImageButton btnImg;
        public Capitulo miCapitulo;
        private String parent;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            mCardView = (CardView)v.findViewById(R.id.cv);
            numCapTextView = (TextView)v.findViewById(R.id.textCardNumCapitulo);
            nombreCapTextView = (TextView)v.findViewById(R.id.textCardNombreCapitulo);
            numCapTextView.setTypeface(RobotoFont.getInstance(v.getContext()).getTypeFace());
            nombreCapTextView.setTypeface(RobotoFont.getInstance(v.getContext()).getTypeFace());
            btnImg = (ImageButton)v.findViewById(R.id.btnImg);
            if (!Sesion.getInstance().isActiva()) btnImg.setVisibility(View.GONE);
        }


        public void configurarImageButton(){

            if (Sesion.getInstance().isActiva()) {

                btnImg.setBackgroundColor(mCardView.getSolidColor());
                if (miCapitulo.isCapitulo_visto())
                    btnImg.setImageResource(R.drawable.visto);
                else
                    btnImg.setImageResource(R.drawable.no_visto);
                btnImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int idSerie = ((ActivityDetalleTemporada)v.getContext()).getIdSerie();
                        int idTemporada = ((ActivityDetalleTemporada)v.getContext()).getIdTemporada();

                        DataBaseConnection db = new DataBaseConnection(v.getContext());

                        if (miCapitulo.isCapitulo_visto()) {

                           if (db.eliminarCapituloVisto(Sesion.getInstance().getIdUsuario(), miCapitulo.getNumeroCapitilo(), idTemporada, idSerie)) {
                               btnImg.setImageResource(R.drawable.no_visto);
                                miCapitulo.setCapitulo_visto(false);
                                //if (parent.equals("Agenda")){
                               // mCardView.removeAllViews();
                                //}

                           }
                        } else {
                           if (db.insertarCapituloVisto(Sesion.getInstance().getIdUsuario(), miCapitulo.getNumeroCapitilo(),idTemporada,idSerie)) {
                                btnImg.setImageResource(R.drawable.visto);
                                miCapitulo.setCapitulo_visto(true);
                               ActivityDetalleTemporada.actualizado=true;
                            }

                        }
                    }
                });
            } else {
                btnImg.setVisibility(View.GONE);
            }
        }


        @Override
        public void onClick(View v) {
            if (numCapTextView.getText()!="") {
                Intent i = new Intent(v.getContext(), ActivityDetalleCapitulo.class);
                Bundle b = new Bundle();
                int idSerie = ((ActivityDetalleTemporada)v.getContext()).getIdSerie();
                int idTemporada = ((ActivityDetalleTemporada)v.getContext()).getIdTemporada();
                String nombreSerie = ((ActivityDetalleTemporada)v.getContext()).getNombreSerie();
                b.putInt(CapituloContract.COLUMN_NAME_SERIE_ID,idSerie);
                b.putInt(CapituloContract.COLUMN_NAME_TEMPORADA_ID,idTemporada);
                b.putInt(CapituloContract.COLUMN_NAME_CAPITULO_ID, Integer.parseInt(numCapTextView.getText().toString()));
                b.putString(CapituloContract.COLUMN_NAME_CAPITULO_NOMBRE, nombreCapTextView.getText().toString());
                b.putString(ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE,nombreSerie);
                i.putExtras(b);
                v.getContext().startActivity(i);
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CapituloAdapter(Capitulo[] myDataset) {
        mDataset = myDataset;
        parent="";
        misViewHolder = new ArrayList<ViewHolder>();
     }

    public void setParent(String parent){
        this.parent=parent;
    }

    /*
    public CapituloAdapter(String[] numCaps,String[] nombreCaps) {
        this.numCaps = numCaps;
        this.nombreCaps = nombreCaps;
    }
    */
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tv_capitulo, parent, false);
        v.setClickable(true);
        ViewHolder vh = new ViewHolder(v);
        //cambio
        misViewHolder.add(vh);
        // termina cambio
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.numCapTextView.setText(numCaps[position]);
        //holder.nombreCapTextView.setText(nombreCaps[position]);

        holder.numCapTextView.setText(Integer.toString(mDataset[position].getNumeroCapitilo()));
        holder.nombreCapTextView.setText(mDataset[position].getNombreCapitilo());
        holder.miCapitulo=mDataset[position];
        holder.parent=this.parent;
        holder.configurarImageButton();




    }



    // Return the size of your dataset (invoked by the layout manager)


    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public ArrayList<ViewHolder> getMisViewHolder(){
        return misViewHolder;
    }

}