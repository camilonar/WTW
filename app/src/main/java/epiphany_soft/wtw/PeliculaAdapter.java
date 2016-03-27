package epiphany_soft.wtw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import epiphany_soft.wtw.Activities.ActivityDetallePelicula;
import epiphany_soft.wtw.DataBase.DataBaseContract;

/**
 * Created by Camilo on 26/03/2016.
 */
public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.ViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            v.setOnClickListener(this);
            mTextView = v;
        }


        @Override
        public void onClick(View v) {
            System.out.println(mTextView.getText() + " " + getAdapterPosition());
            Intent i = new Intent(v.getContext(), ActivityDetallePelicula.class);
            //Se manda el nombre del programa para saber que información debe mostrarse
            Bundle b = new Bundle();
            b.putString(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_NOMBRE,mTextView.getText().toString());
            i.putExtras(b);
            v.getContext().startActivity(i);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PeliculaAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PeliculaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tv_pelicula, parent, false);
        // set the view's size, margins, paddings and layout parameters
        v.layout(50,0,0,0);
        v.setClickable(true);
        ViewHolder vh = new ViewHolder((TextView)v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}