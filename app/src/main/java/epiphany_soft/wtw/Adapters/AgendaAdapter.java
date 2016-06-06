package epiphany_soft.wtw.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Fonts.SpecialFont;
import epiphany_soft.wtw.Negocio.Dia;
import epiphany_soft.wtw.Negocio.Programa;
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
            mTextView.setTypeface(SpecialFont.getInstance(v.getContext()).getTypeFace());

            mRecyclerView = (RecyclerView) v.findViewById(R.id.rv);
            mRecyclerView.setHasFixedSize(false);
            mLayoutManager = new LinearLayoutManager(v.getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
        }

        private void crearRecyclerViewDias() {
            DataBaseConnection db = new DataBaseConnection(this.c);
            Cursor c=db.consultarSeriePorDia(mDia.getId());
            if (c!=null) {
                Programa[] programas = new Programa[c.getCount()];
                int i = 0;
                while (c.moveToNext()) {
                    programas[i] = new Programa();
                    String nombre = c.getString(c.getColumnIndex(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE));
                    int idPrograma = c.getInt(c.getColumnIndex(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID));
                   /* if (Sesion.getInstance().isActiva()) {
                        boolean isFavorito = c.getInt(c.getColumnIndex(DataBaseContract.AgendaContract.COLUMN_NAME_USUARIO_ID)) != 0;
                        programas[i].setFavorito(isFavorito);
                    }*/
                    programas[i].setNombre(nombre);
                    programas[i].setIdPrograma(idPrograma);
                    i++;
                }
                Float height = this.c.getResources().getDimension(R.dimen.size_programa) * (programas.length);
                TableRow.LayoutParams params = new TableRow.LayoutParams(1200, height.intValue());
                mLayout.setLayoutParams(params);

                if (programas != null) {
                    mAdapter = new SerieAdapter(programas); // se le pasa el dia
                    mRecyclerView.setAdapter(mAdapter);
                }
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