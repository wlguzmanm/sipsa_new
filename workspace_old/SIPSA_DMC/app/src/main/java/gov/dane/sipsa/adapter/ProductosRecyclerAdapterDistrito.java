package gov.dane.sipsa.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gov.dane.sipsa.R;
import gov.dane.sipsa.activity.FuenteRecoleccionDistritoActivity;
import gov.dane.sipsa.activity.RecoleccionActivity;
import gov.dane.sipsa.activity.RecoleccionDistritoActivity;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteDistrito;
import gov.dane.sipsa.model.NovedadRecoleccion;
import gov.dane.sipsa.model.RecoleccionPrincipal;

public class ProductosRecyclerAdapterDistrito extends RecyclerView.Adapter<ProductosRecyclerAdapterDistrito.ViewHolder> {

    private List<RecoleccionPrincipal> mItems;
    private List<RecoleccionPrincipal> mItemsFiltered;
    private ArrayList<RecoleccionPrincipal> myList = new ArrayList<RecoleccionPrincipal>();


    private FuenteDistrito fuente;
    public Typeface font;
    Integer white_color;
    Integer success_color;
    Integer default_color;
    Context mContext;

    private String frecuencia = null;
    private String tipo = null;
    private String unidad = null;
    private String observacion = null;
    private String precio = null;



    public ProductosRecyclerAdapterDistrito(List<RecoleccionPrincipal> mItems, FuenteDistrito fuente) {
        this.mItems = mItems;
        this.mItemsFiltered = mItems;
        this.fuente = fuente;
    }




    public void filterData(String filter )
    {
        if (mItemsFiltered == null) {
            return;
        }
        if ( "".equals( filter ) )
        {
            filterNombreGrupo(filter);
        }
        else
        {
            myList.clear();
            mItemsFiltered =null;
            for (RecoleccionPrincipal rp : mItems )
            {
                if (rp.getArticuloNombre().toLowerCase(Locale.US).contains(filter.toLowerCase(Locale.US))) {
                    myList.add(rp);
                }
            }
            mItemsFiltered = myList;
        }
        this.notifyDataSetChanged();
    }


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


    public void filterDataPendiente(boolean isPendiente)
    {
        myList.clear();
        mItemsFiltered =null;
        if (mItems != null) {
            for (RecoleccionPrincipal elementoCheck : mItems )
            {
               /* if (isPendiente && !elementoCheck.getEstadoRecoleccion()) {
                    myList.add(elementoCheck);
                }else if(!isPendiente && elementoCheck.getEstadoRecoleccion()) {
                    myList.add(elementoCheck);
                }*/
            }
        }
        mItemsFiltered = myList;
        this.notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,final int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_elemento_distrito, viewGroup, false);
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

        viewHolder.etNombreArticuloRecoleccion.setText(elemento.getArticuloNombre());
        viewHolder.etTipoRecoleccion.setText(elemento.getTipo());
        viewHolder.etFrecuenciaRecoleccion.setText(elemento.getFrecuencia());
        viewHolder.etUnidadMedidaRecoleccion.setText(elemento.getUnidadMedidaNombre());
        viewHolder.etObservacionRecoleccion.setText(elemento.getObservacionArticulo());

        /*tipo = viewHolder.etTipoRecoleccion.getText().toString();
        frecuencia = viewHolder.etFrecuenciaRecoleccion.getText().toString();
        unidad = viewHolder.etUnidadMedidaRecoleccion.getText().toString();
        observacion = viewHolder.etObservacionRecoleccion.getText().toString();*/

        viewHolder.btnRecolectar.setOnClickListener(null);

        viewHolder.btnRecolectar.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, RecoleccionDistritoActivity.class);

                selected_position = position;
                notifyItemChanged(selected_position);
                intent.putExtra("recolecciones", elementos);
                intent.putExtra("recoleccion", elemento);
                intent.putExtra("fuente", (Serializable) fuente);
                intent.putExtra("factor", (Serializable) elemento.getTireNombre());

                if (elemento.getNovedad()!=null && elemento.getNovedad().equals("IN")){
                    intent.putExtra("novedad", NovedadRecoleccion.NUEVO);
                } else if (elemento.getNovedad()!=null && elemento.getNovedad().equals("IS")){
                intent.putExtra("novedad", NovedadRecoleccion.INSUMO_SALE);
                } else if (elemento.getNovedad()!=null && elemento.getNovedad().equals("ND")){
                    intent.putExtra("novedad", NovedadRecoleccion.NO_DISPONIBLE);
                }else {
                    intent.putExtra("novedad", NovedadRecoleccion.PROGRAMADA);
                }
                ((FuenteRecoleccionDistritoActivity) mContext).startActivityForResult(intent, 10);
            }
        });

        viewHolder.iconStatus.setTypeface(font);

        if (elemento.getEstadoRecoleccion() != null && elemento.getEstadoRecoleccion() == true) {
            viewHolder.iconStatus.setTextColor(success_color);
        } else {
            viewHolder.iconStatus.setTextColor(default_color);
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
        public TextView etNombreArticuloRecoleccion;
        public TextView etTipoRecoleccion;
        public TextView etFrecuenciaRecoleccion;
        public TextView etUnidadMedidaRecoleccion;
        public TextView etObservacionRecoleccion;
        public Button iconStatus;
        public Button iconObservacion;
        public Button btnRecolectar;



        ViewHolder(View v) {
            super(v);
            etNombreArticuloRecoleccion = (TextView) v.findViewById(R.id.etNombreArticuloRecoleccion);
            etTipoRecoleccion = (TextView) v.findViewById(R.id.etTipoRecoleccion);
            etFrecuenciaRecoleccion = (TextView) v.findViewById(R.id.etFrecuenciaRecoleccion);
            etUnidadMedidaRecoleccion = (TextView) v.findViewById(R.id.etUnidadMedidaRecoleccion);
            etObservacionRecoleccion = (TextView) v.findViewById(R.id.etObservacionesRecoleccion);
            iconStatus = (Button) v.findViewById(R.id.iconStatus);
            iconObservacion = (Button) v.findViewById(R.id.iconObservacion);
            btnRecolectar = (Button) v.findViewById(R.id.btnRecolectarDistrito);
        }
    }

    public void swap(List list){
        mItems = list;
        mItemsFiltered = list;
        this.notifyDataSetChanged();
    }




}
