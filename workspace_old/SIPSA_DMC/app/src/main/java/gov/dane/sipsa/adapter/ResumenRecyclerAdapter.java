package gov.dane.sipsa.adapter;

import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import gov.dane.sipsa.R;
import gov.dane.sipsa.model.IndiceResumen;

public class ResumenRecyclerAdapter extends RecyclerView.Adapter<ResumenRecyclerAdapter.ViewHolder> {

    private List<IndiceResumen> mItems;

    public ResumenRecyclerAdapter(List<IndiceResumen> items) {
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_resumen_indice, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        IndiceResumen item = mItems.get(i);
        viewHolder.tvNombreIndice.setText("RESUMEN FUENTES");
        viewHolder.tvCompletas.setText(item.getCompletas().toString());
        viewHolder.tvIncompletas.setText(item.getIncompletas().toString());
        viewHolder.tvPendientes.setText(item.getPendientes().toString());
        viewHolder.tvTotal.setText(item.getTotal().toString());
        Double progress = 0.0;
        if (item.getCompletas() != 0) {
            progress = (item.getCompletas().doubleValue() / item.getTotal().doubleValue()) * 100;
        }
        viewHolder.progressBar.setProgress(progress.intValue());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNombreIndice;
        public TextView tvCompletas;
        public TextView tvIncompletas;
        public TextView tvPendientes;
        public TextView tvTotal;
        public ProgressBar progressBar;


        ViewHolder(View v) {
            super(v);

            tvNombreIndice = (TextView) v.findViewById(R.id.tvNombreIndice);
            tvCompletas = (TextView) v.findViewById(R.id.tvCompletas);
            tvIncompletas = (TextView) v.findViewById(R.id.tvIncompletas);
            tvPendientes = (TextView) v.findViewById(R.id.tvPendientes);
            tvTotal = (TextView) v.findViewById(R.id.tvTotal);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            progressBar.getProgressDrawable().setColorFilter(v.getResources().getColor(R.color.accent), PorterDuff.Mode.SRC_IN);

        }
    }

}
