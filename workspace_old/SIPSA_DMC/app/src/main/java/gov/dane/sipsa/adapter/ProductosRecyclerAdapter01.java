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
import gov.dane.sipsa.activity.FactorRecoleccionActivity01;
import gov.dane.sipsa.activity.FuenteRecoleccionActivity;
import gov.dane.sipsa.activity.RecoleccionActivity;
import gov.dane.sipsa.activity.RecoleccionActivity01;
import gov.dane.sipsa.activity.ResumenRecoleccionActivity01;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.model.Elemento;
import gov.dane.sipsa.model.Elemento01;
import gov.dane.sipsa.model.FactorI01;
import gov.dane.sipsa.model.Municipio;
import gov.dane.sipsa.model.NovedadRecoleccion;
import gov.dane.sipsa.model.RecoleccionPrincipal;
import gov.dane.sipsa.model.RecoleccionPrincipal01;

public class ProductosRecyclerAdapter01 extends RecyclerView.Adapter<ProductosRecyclerAdapter01.ViewHolder> {

    private List<Elemento01> mItems;
    private List<Elemento01> mItemsFiltered;
    private ArrayList<Elemento01> myList = new ArrayList<Elemento01>();



    private String municipio;
    private Long factorI01;
    public Typeface font;
    Integer white_color;
    Integer success_color;
    Integer default_color;
    Context mContext;


    public ProductosRecyclerAdapter01(List<Elemento01> mItems, Long tireId, String muniId) {
        this.mItems = mItems;
        this.mItemsFiltered = mItems;
        this.factorI01 = tireId;
        this.municipio = muniId;
    }


    public void filterData( String filter )
    {
        if (mItemsFiltered == null) {
            return;
        }
        if ( "".equals( filter ) )
        {
            mItemsFiltered = mItems;
            myList.clear();
        }
        else
        {
            myList.clear();
            mItemsFiltered =null;
            for (Elemento01 rp : mItems )
            {

                    if ( rp.getNombreArtiCompleto().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) ))
                    {
                        myList.add(rp);
                    }

            }
            mItemsFiltered = myList;
        }
        this.notifyDataSetChanged();
    }





    public void filterDataPendiente(boolean isPendiente)
    {
        myList.clear();
        mItemsFiltered =null;
        if (mItems != null) {
            for (Elemento01 elementoCheck : mItems )
            {
               /* if (isPendiente && !elementoCheck.getEstadoRecoleccion()) {
                    myList.add(elementoCheck);
                }else if(!isPendiente && elementoCheck.getEstadoRecoleccion()) {
                    myList.add(elementoCheck);*/

            }
        }
        mItemsFiltered = myList;
        this.notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,final int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_elemento01, viewGroup, false);
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


        final Elemento01 elemento = mItemsFiltered.get(position);
        final ArrayList<Elemento01> elementos = new ArrayList<>();
        elementos.addAll(mItemsFiltered);



        viewHolder.etNombreArticulo.setText(elemento.getNombreArtiCompleto());
        viewHolder.etNoRecolecciones.setText(elemento.getNoRecoleccion().toString());
        viewHolder.btnRecolectar.setVisibility(View.VISIBLE);
        viewHolder.btnEditarRecoleccion.setVisibility(View.VISIBLE);
        if(elemento.getNoRecoleccion()!=null && elemento.getNoRecoleccion()>5){
            viewHolder.btnRecolectar.setVisibility(View.GONE);
        }


        viewHolder.btnRecolectar.setOnClickListener(null);
        viewHolder.btnRecolectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, RecoleccionActivity01.class);

                selected_position = position;
                notifyItemChanged(selected_position);
                intent.putExtra("recoleccion", elemento);
                if (elemento != null) {
                    if (elemento.getNovedad()!=null && elemento.getNovedad().equals("IA")) {
                        intent.putExtra("novedad", NovedadRecoleccion.ADICIONADA);
                    } else if (elemento.getNovedad()!=null && elemento.getNovedad().equals("IN")) {
                        intent.putExtra("novedad", NovedadRecoleccion.NUEVO);
                    } else {
                        intent.putExtra("novedad", NovedadRecoleccion.PROGRAMADA);
                    }
                }
                ((FactorRecoleccionActivity01) mContext).startActivityForResult(intent, 10);
            }
        });

        if(elemento.getNoRecoleccion()!=null &&elemento.getNoRecoleccion()==0){
            viewHolder.btnEditarRecoleccion.setVisibility(View.GONE);
        }

        viewHolder.btnEditarRecoleccion.setOnClickListener(null);
        viewHolder.btnEditarRecoleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ResumenRecoleccionActivity01.class);
                selected_position = position;
                notifyItemChanged(selected_position);
                intent.putExtra("recoleccion", elemento);
                if (elemento != null) {
                    if (elemento.getNovedad()!=null && elemento.getNovedad().equals("IA")) {
                        intent.putExtra("novedad", NovedadRecoleccion.ADICIONADA);
                    } else if (elemento.getNovedad()!=null && elemento.getNovedad().equals("IN")) {
                        intent.putExtra("novedad", NovedadRecoleccion.NUEVO);
                    } else {
                        intent.putExtra("novedad", NovedadRecoleccion.PROGRAMADA);
                    }
                }
                ((FactorRecoleccionActivity01) mContext).startActivityForResult(intent, 10);
            }
        });

        viewHolder.iconStatus.setTypeface(font);


        if (elemento.getNoRecoleccion() != null && elemento.getNoRecoleccion()>=3){
            viewHolder.iconStatus.setTextColor(success_color);
        } else {
            viewHolder.iconStatus.setTextColor(default_color);
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
        public TextView etNoRecolecciones;
        public Button iconStatus;
        public Button iconObservacion;
        public Button btnRecolectar;
        public Button btnEditarRecoleccion;


        ViewHolder(View v) {
            super(v);
            etNombreArticulo = (TextView) v.findViewById(R.id.etNombreArticulo);
            etNoRecolecciones = (TextView) v.findViewById(R.id.etNoRecolecciones);
            iconStatus = (Button) v.findViewById(R.id.iconStatus);
            iconObservacion = (Button) v.findViewById(R.id.iconObservacion);
            btnRecolectar = (Button) v.findViewById(R.id.btnRecolectar);
            btnEditarRecoleccion = (Button) v.findViewById(R.id.btnEditarRecoleccion);
        }
    }

    public void swap(List list){
        mItems = list;
        mItemsFiltered = list;
        this.notifyDataSetChanged();
    }




}
