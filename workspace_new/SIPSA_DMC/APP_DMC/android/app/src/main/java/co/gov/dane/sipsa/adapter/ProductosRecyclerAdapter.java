package co.gov.dane.sipsa.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.gov.dane.sipsa.FuenteRecoleccionActivity;
import co.gov.dane.sipsa.R;
import co.gov.dane.sipsa.RecoleccionActivity;
import co.gov.dane.sipsa.backend.dao.Fuente;
import co.gov.dane.sipsa.backend.model.NovedadRecoleccion;
import co.gov.dane.sipsa.backend.model.RecoleccionPrincipal;

public class ProductosRecyclerAdapter extends RecyclerView.Adapter<ProductosRecyclerAdapter.ViewHolder> {

    private List<RecoleccionPrincipal> mItems;
    private List<RecoleccionPrincipal> mItemsFiltered;
    private ArrayList<RecoleccionPrincipal> myList = new ArrayList<RecoleccionPrincipal>();

    private Fuente fuente;
    public Typeface font;
    Integer white_color;
    Integer success_color;
    Integer default_color;
    Context mContext;

    public ProductosRecyclerAdapter(List<RecoleccionPrincipal> mItems, Fuente fuente) {
        this.mItems = mItems;
        this.mItemsFiltered = mItems;
        this.fuente = fuente;
    }

    public void filterData(String nombreGrupo, String filter )
    {
        if (mItemsFiltered == null) {
            return;
        }
        if ( "".equals( filter ) )
        {
            filterNombreGrupo(nombreGrupo);
        }
        else
        {
            myList.clear();
            mItemsFiltered =null;
            for (RecoleccionPrincipal rp : mItems )
            {
                if (rp.getGrupoNombre() != null) {
                    if (rp.getGrupoNombre().toLowerCase(Locale.US).contains(nombreGrupo.toLowerCase(Locale.US)) ||
                            "".equals(nombreGrupo) || "TODOS".equals(nombreGrupo)) {
                        if (rp.getArticuloNombre().toLowerCase(Locale.US).contains(filter.toLowerCase(Locale.US)) ||
                                rp.getRegistroIcaId().toLowerCase(Locale.US).contains(filter.toLowerCase(Locale.US))) {
                            myList.add(rp);
                        }
                    }
                }
            }
            mItemsFiltered = myList;
        }
        this.notifyDataSetChanged();
    }

    /**
     * Metoodo qye filtra los grupos
     * @param filter
     */
    public void filterNombreGrupo(String filter)
    {
        if(filter != null){
            if ( "".equals( filter ) )
            {
                mItemsFiltered = mItems;
                myList.clear();
            }
            else if (filter.equals("TODOS")) {
                mItemsFiltered = mItems;
                myList.clear();
            }
            else
            {
                myList.clear();
                mItemsFiltered =null;
                for (RecoleccionPrincipal rp : mItems )
                {
                    if ( rp.getGrupoNombre()!=null && rp.getGrupoNombre().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) ) || "TODOS".equals(filter) )
                    {
                        myList.add(rp);
                    }
                }
                mItemsFiltered = myList;
            }
            this.notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,final int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.formulario_recolectar, viewGroup, false);
        mContext = viewGroup.getContext();
        font = Typeface.createFromAsset(v.getContext().getAssets(), "fontawesome-webfont.ttf");
        white_color = v.getContext().getResources().getColor(R.color.white);
        success_color = v.getContext().getResources().getColor(R.color.success);
        default_color = v.getContext().getResources().getColor(R.color.gray_light);

        return new ViewHolder(v);
    }

    private int selected_position = 0;

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {
        final RecoleccionPrincipal elemento = mItemsFiltered.get(position);
        final ArrayList<RecoleccionPrincipal> elementos = new ArrayList<>();
        elementos.addAll(mItemsFiltered);

        viewHolder.etNombreArticulo.setText(elemento.getArticuloNombre());
        viewHolder.etRegistroIca.setText(elemento.getRegistroIcaId());

        String nombrePpal = " de "+ elemento.getUnmeCantidadPpal() + " " + elemento.getUnidadMedidaNombre();
        String nombreSec = elemento.getUnmeCantidad2()!=0? " de " + elemento.getUnmeCantidad2()+ " "+ elemento.getUnmeNombre2() + " c/u":"";

        viewHolder.etUnidadMedida.setText(nombrePpal + nombreSec);
        viewHolder.etCasaComercial.setText(elemento.getCasaComercialNombre());

        viewHolder.btnRecolectar.setOnClickListener(null);
        viewHolder.btnRecolectar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, RecoleccionActivity.class);

                selected_position = position;
                notifyItemChanged(selected_position);
                intent.putExtra("recolecciones", elementos);
                intent.putExtra("recoleccion", elemento);
                intent.putExtra("fuente", (Serializable) fuente);
                intent.putExtra("factor", (Serializable) elemento.getTireNombre());

                if (elemento.getNovedad()!=null && elemento.getNovedad().equals("IA")) {
                    intent.putExtra("novedad", NovedadRecoleccion.ADICIONADA);
                }else if (elemento.getNovedad()!=null && elemento.getNovedad().equals("IN")){
                    intent.putExtra("novedad", NovedadRecoleccion.NUEVO);
                } else if (elemento.getNovedad()!=null && elemento.getNovedad().equals("IS")){
                    intent.putExtra("novedad", NovedadRecoleccion.INSUMO_SALE);
                } else if (elemento.getNovedad()!=null && elemento.getNovedad().equals("ND")){
                    intent.putExtra("novedad", NovedadRecoleccion.NO_DISPONIBLE);
                }else if (elemento.getNovedad()!=null && elemento.getNovedad().equals("PR")){
                    intent.putExtra("novedad", NovedadRecoleccion.PROMOCION);
                }else {
                    intent.putExtra("novedad", NovedadRecoleccion.PROGRAMADA);
                }
                ((FuenteRecoleccionActivity) mContext).startActivityForResult(intent, 10);
            }
        });

        if (elemento.getEstadoRecoleccion() != null && elemento.getEstadoRecoleccion() == true) {
            viewHolder.iconStatus.setImageResource(R.drawable.ic_checkmark_verde);
        } else {
            viewHolder.iconStatus.setColorFilter(default_color);
        }

        if (elemento.getNovedad() == null && elemento.getObservacion() == null) {
            viewHolder.iconObservacion.setVisibility(View.VISIBLE);
            viewHolder.iconObservacion.setTextColor(white_color);
            viewHolder.iconObservacion.setText("P");
        } else {
            viewHolder.iconObservacion.setVisibility(View.VISIBLE);
            viewHolder.iconObservacion.setTextColor(white_color);
            if (elemento.getNovedad() != null) {
                viewHolder.iconObservacion.setText(elemento.getNovedad().toString());
                if (elemento.getObservacion() != null && !elemento.getObservacion().equals("")) {
                    viewHolder.iconObservacion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Snackbar snackbar = Snackbar.make(v, elemento.getObservacion().toString(),
                                    Snackbar.LENGTH_LONG);
                            View snackBarView = snackbar.getView();

                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            //lp.setMargins(50, 0, 0, 0);
                            snackBarView.setLayoutParams(lp);

                            snackBarView.setBackgroundColor(v.getContext().getResources().getColor(R.color.info));
                            snackbar.show();
                        }
                    });
                }
            } else if (elemento.getObservacion() != null && !elemento.getObservacion().equals("")) {

                viewHolder.iconObservacion.setTypeface(font);
                viewHolder.iconObservacion.setText(R.string.icon_notification);
                viewHolder.iconObservacion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar snackbar = Snackbar.make(v, elemento.getObservacion().toString(),
                                Snackbar.LENGTH_LONG);
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(v.getContext().getResources().getColor(R.color.info));
                        snackbar.show();
                    }
                });

            } else {
                viewHolder.iconObservacion.setEnabled(false);
                viewHolder.iconObservacion.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mItemsFiltered == null) {
            return 0;
        } else {
            return mItemsFiltered.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView etNombreArticulo;
        public TextView etRegistroIca;
        public TextView etUnidadMedida;
        public TextView etCasaComercial;
        public ImageView iconStatus;
        public Button iconObservacion;
        public ImageView btnRecolectar;

        ViewHolder(View v) {
            super(v);
            etNombreArticulo = (TextView) v.findViewById(R.id.etNombreArticulo);
            etRegistroIca = (TextView) v.findViewById(R.id.etRegistroIca);
            etUnidadMedida = (TextView) v.findViewById(R.id.etUnidadMedida);
            etCasaComercial = (TextView) v.findViewById(R.id.etCasaComercial);
            iconStatus = (ImageView) v.findViewById(R.id.iconStatus);
            iconObservacion = (Button) v.findViewById(R.id.iconObservacion);
            btnRecolectar = (ImageView) v.findViewById(R.id.imageViewBtnRecolectar);
        }
    }

    public void swap(List list){
        mItems = list;
        mItemsFiltered = list;
        this.notifyDataSetChanged();
    }


    ///METODOS PARA EL FILTRADO
    public void getList(List<RecoleccionPrincipal> temp) {
        this.mItemsFiltered = temp;
    }

    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        // Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<RecoleccionPrincipal> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.equals("") || charSequence.length() == 0) {
                filteredList.addAll(mItemsFiltered);
            } else {
                for (RecoleccionPrincipal event : mItems) {
                    if (event.getArticuloNombre().toLowerCase().contains(charSequence.toString().toLowerCase())) {
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
            mItems = new ArrayList<>();
            mItems.addAll((List<RecoleccionPrincipal>) filterResults.values);
            swap(mItems);
            notifyDataSetChanged();
        }
    };

}


