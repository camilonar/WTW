package epiphany_soft.wtw.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import epiphany_soft.wtw.Activities.ActivityDetallePelicula;
import epiphany_soft.wtw.Activities.Series.ActivityDetalleSerie;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Fonts.SpecialFont;
import epiphany_soft.wtw.Negocio.Horario;
import epiphany_soft.wtw.R;
// parece q ya esta bn.

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.ViewHolder> {
    private Horario[] mDataset;
    private int idPrograma;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        RecyclerView mRecyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;

        public CardView mCardView;
        public TextView mTextView;
        public LinearLayout mLayout;
        public CheckBox ck;
        public Horario mHorario;
        public int idPrograma;
        public ViewHolder(View v) {
            super(v);
            mCardView = (CardView)v.findViewById(R.id.cv);
            mTextView = (TextView)v.findViewById(R.id.textCard);
            mLayout = (LinearLayout)v.findViewById(R.id.layoutExterno);
            ck = (CheckBox)v.findViewById(R.id.textCardCK);
            ck.setOnClickListener(this);
            crearRecyclerViewDias(v);
            setSpecialFonts(v);
            mTextView.setTypeface(RobotoFont.getInstance(v.getContext()).getTypeFace());
        }

        @Override
        public void onClick(View v) {
            boolean success;
            DataBaseConnection db = new DataBaseConnection(v.getContext());
            if (mHorario.getId()!=0){
                success=db.eliminarHorario(mHorario.getId());
                if (success) {
                    ck.setChecked(false);
                    mLayout.setVisibility(View.GONE);
                    mHorario.setId(0);
                    mHorario.setIdPrograma(0);
                    ActivityDetalleSerie.actualizado=true;
                    ActivityDetallePelicula.actualizado=true;
                }
            } else {
                mHorario.setIdPrograma(idPrograma);
                success=db.insertarHorario(mHorario);
                if (success) {
                   ck.setChecked(true);
                    mHorario.setId(db.getHorarioId(mHorario.getIdPrograma(), mHorario.getNombreCanal()));
                    mLayout.setVisibility(View.VISIBLE);
                    ActivityDetalleSerie.actualizado=true;
                    ActivityDetallePelicula.actualizado=true;
                }
            }
        }

        private void crearRecyclerViewDias(View v) {
            String contenido[] = new String[]{"D", "L", "Ma", "Mi", "J", "V", "S"};
            LinearLayout layoutRV = (LinearLayout) v.findViewById(R.id.layoutHorarioRV);
            Float height = v.getResources().getDimension(R.dimen.size_dia)*(contenido.length);
            TableRow.LayoutParams params = new TableRow.LayoutParams(1200, height.intValue());
            layoutRV.setLayoutParams(params);
            mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_horario);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL,false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            if (contenido!=null) {
                mAdapter = new DiaAdapter(contenido);
                mRecyclerView.setAdapter(mAdapter);
            }
        }

        private void setSpecialFonts(View v){
            TextView horaLabel=(TextView) v.findViewById(R.id.lblHora);
            horaLabel.setTypeface(SpecialFont.getInstance(v.getContext()).getTypeFace());
            TextView diasLabel=(TextView) v.findViewById(R.id.lblDias);
            diasLabel.setTypeface(SpecialFont.getInstance(v.getContext()).getTypeFace());
            //Los textos
            EditText hora=(EditText) v.findViewById(R.id.txtHora);
            hora.setTypeface(RobotoFont.getInstance(v.getContext()).getTypeFace());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HorarioAdapter(Horario[] myDataset, int idPrograma) {
        mDataset = myDataset;
        this.idPrograma = idPrograma;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HorarioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tv_horario, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position].toString());
        holder.mHorario = mDataset[position];
        holder.idPrograma = idPrograma;
        holder.ck.setChecked(false);
        if (mDataset[position].getId()!=0){
            holder.ck.setChecked(true);
            holder.mLayout.setVisibility(View.VISIBLE);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}