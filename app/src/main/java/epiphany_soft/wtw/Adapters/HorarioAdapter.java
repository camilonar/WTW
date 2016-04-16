package epiphany_soft.wtw.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import epiphany_soft.wtw.Activities.ActivityDetallePelicula;
import epiphany_soft.wtw.Activities.Series.ActivityDetalleSerie;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.Fonts.RobotoFont;
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
        public CardView mCardView;
        public TextView mTextView;
        public ImageButton mButton;
        public Horario mHorario;
        public int idPrograma;
        public ViewHolder(View v) {
            super(v);
            mCardView = (CardView)v.findViewById(R.id.cv);
            mTextView = (TextView)v.findViewById(R.id.textCard);
            mButton = (ImageButton)v.findViewById(R.id.imageButton);
            mButton.setOnClickListener(this);
            mTextView.setTypeface(RobotoFont.getInstance(v.getContext()).getTypeFace());
        }

        @Override
        public void onClick(View v) {
            boolean success;
            DataBaseConnection db = new DataBaseConnection(v.getContext());
            if (mHorario.getId()!=0){
                success=db.eliminarHorario(mHorario.getId());
                if (success) {
                    mButton.setBackgroundResource(R.drawable.ic_remove);
                    mHorario.setId(0);
                    mHorario.setIdPrograma(0);
                    ActivityDetalleSerie.actualizado=true;
                    ActivityDetallePelicula.actualizado=true;
                }
            } else {
                mHorario.setIdPrograma(idPrograma);
                success=db.insertarHorario(mHorario);
                if (success) {
                    mButton.setBackgroundResource(R.drawable.ic_add);
                    mHorario.setId(db.getHorarioId(mHorario.getIdPrograma(),mHorario.getNombreCanal()));
                    ActivityDetalleSerie.actualizado=true;
                    ActivityDetallePelicula.actualizado=true;
                }
            }
            mButton.refreshDrawableState();
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
        holder.mButton.setBackgroundResource(R.drawable.ic_remove);
        if (mDataset[position].getId()==0){
            holder.mButton.setBackgroundResource(R.drawable.ic_add);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}