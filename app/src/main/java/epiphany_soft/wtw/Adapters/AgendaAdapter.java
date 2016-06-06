package epiphany_soft.wtw.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Negocio.Dia;
import epiphany_soft.wtw.R;
// parece q ya esta bn.

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ViewHolder> {
    private Dia[] mDataset;
    private int idPrograma;
    private Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        RecyclerView mRecyclerView;
       public  RecyclerView.Adapter mAdapter;//adaptador de la lista de los dias
        RecyclerView.LayoutManager mLayoutManager;

        public CardView mCardView;
        public TextView mTextView;
        public LinearLayout mLayout;
        public Dia mDia;

        public Context c;

        public ViewHolder(View v) {
            super(v);
            mCardView = (CardView)v.findViewById(R.id.cv);
            mTextView = (TextView)v.findViewById(R.id.textCard);
            mLayout = (LinearLayout)v.findViewById(R.id.layoutExterno);
            mTextView.setTypeface(RobotoFont.getInstance(v.getContext()).getTypeFace());

            mRecyclerView = (RecyclerView) v.findViewById(R.id.rv);
            mRecyclerView.setHasFixedSize(false);
            mLayoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL,false);
            mRecyclerView.setLayoutManager(mLayoutManager);
        }

        private void crearRecyclerViewDias() {
            Dia dias[]= new Dia[7];
            String contenido[] = new String[]{"D", "L", "Ma", "Mi", "J", "V", "S"}; // aqui...

            Float height = this.c.getResources().getDimension(R.dimen.size_dia)*(contenido.length);
            TableRow.LayoutParams params = new TableRow.LayoutParams(1200, height.intValue());
            mLayout.setLayoutParams(params);

            if (contenido!=null) {
                mAdapter = new DiaAdapter(dias,c); // se le pasa el dia
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AgendaAdapter(Context c, Dia[] myDataset) {
        mDataset = myDataset;
        this.idPrograma = idPrograma;
        this.context = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AgendaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tv_agenda, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position].getNombre());
        holder.mDia = mDataset[position];
        holder.c=context;
        holder.crearRecyclerViewDias();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}