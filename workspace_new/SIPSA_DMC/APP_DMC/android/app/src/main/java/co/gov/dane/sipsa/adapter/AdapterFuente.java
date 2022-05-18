package co.gov.dane.sipsa.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.gov.dane.sipsa.FuenteDetalleActivity;
import co.gov.dane.sipsa.FuenteRecoleccionActivity;
import co.gov.dane.sipsa.MainActivity;
import co.gov.dane.sipsa.R;
import co.gov.dane.sipsa.backend.dao.Fuente;

public class AdapterFuente extends RecyclerView.Adapter<AdapterFuente.MyViewHolder> implements
        ItemTouchHelperAdapter {

    private List<Fuente> fuentes;
    private List<Fuente> temp;

    private Boolean click_long = false;
    private MainActivity main;

    public class MyViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        public TextView  tvNombreFuente, tvDireccion, tvCiudad, tvTelefono, tvInformante;
        public LinearLayout linear_manzana, linear_item;
        public ImageView imageView_editar_insumo, imageView_listado_recolectar;

        public MyViewHolder(View view) {
            super(view);
            tvNombreFuente = (TextView) view.findViewById(R.id.tvNombreFuente);
            tvDireccion = (TextView) view.findViewById(R.id.tvDireccion);
            tvCiudad = (TextView) view.findViewById(R.id.tvCiudad);
            tvTelefono = (TextView) view.findViewById(R.id.tvTelefono);
            tvInformante = (TextView) view.findViewById(R.id.tvInformante);
            linear_item = (LinearLayout) view.findViewById(R.id.linear_manzana);

            imageView_editar_insumo = (ImageView) view.findViewById(R.id.imageView_editar_insumo);
            imageView_listado_recolectar = (ImageView) view.findViewById(R.id.imageView_listado_recolectar);
        }

        @Override
        public void onItemSelected() {
        }

        @Override
        public void onItemClear() {
        }
    }

    public Filter getFilter() {
        return myFilter;
    }

    public void getList(List<Fuente> temp) {
        this.temp = temp;
    }

    Filter myFilter = new Filter() {
        // Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Fuente> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.equals("") || charSequence.length() == 0) {
                filteredList.addAll(temp);
            } else {
                for (Fuente event : fuentes) {
                    if (event.getFuenNombre().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(event);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        // Automatic on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            fuentes.clear();
            fuentes.addAll((List<Fuente>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public AdapterFuente(List<Fuente> moviesList, MainActivity main) {
        this.fuentes = moviesList;
        this.main=main;
    }

    public List<Fuente> getFuentes() {
        return fuentes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.formulario_listado, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Fuente fuente = fuentes.get(position);

        holder.tvNombreFuente.setText(fuente.getFuenNombre());  /// EL UUID_MANZANA
        holder.tvDireccion.setText(fuente.getFuenDireccion());
        holder.tvCiudad.setText(fuente.getFuenDireccion());
        holder.tvTelefono.setText(fuente.getFuenTelefono());
        holder.tvInformante.setText(fuente.getFuenNombreInformante());

        holder.imageView_editar_insumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = position;
                Fuente item = fuentes.get(itemPosition);
                Context context = v.getContext();
                Intent intent =new Intent(context, FuenteDetalleActivity.class);
                intent.putExtra("fuente", (Serializable) item);
                context.startActivity(intent);
            }
        });

        holder.imageView_listado_recolectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!click_long) {
                    int itemPosition = position;
                    Fuente item = fuentes.get(itemPosition);
                    Context context = v.getContext();
                    Intent intent =new Intent(context, FuenteRecoleccionActivity.class);
                    intent.putExtra("factor", (Serializable) item);
                    intent.putExtra("municipio", (Serializable) item);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fuentes.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(fuentes, fromPosition, toPosition);
        Fuente targetUser = fuentes.get(fromPosition);
        fuentes.remove(fromPosition);
        fuentes.add(toPosition, targetUser);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
    }
}


