package gov.dane.sipsa.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gov.dane.sipsa.R;
import gov.dane.sipsa.activity.FactorRecoleccionActivity01;
import gov.dane.sipsa.activity.FuenteRecoleccionActivity;
import gov.dane.sipsa.dao.Articulo;
import gov.dane.sipsa.dao.ArticuloI01;
import gov.dane.sipsa.dao.Fuente;

public class ResumenEnvioRecyclerAdapter01 extends RecyclerView.Adapter<ResumenEnvioRecyclerAdapter01.ViewHolder> {

    private List<ArticuloI01> mItems;
    private List<ArticuloI01> mItemsFiltered;
    private ArrayList<ArticuloI01> myList = new ArrayList<ArticuloI01>();
    Integer success_color;
    Integer warning_color;
    public Typeface font;
    private String currentMunicipio;

    public ResumenEnvioRecyclerAdapter01(List<ArticuloI01> items) {
        mItems = items;
        mItemsFiltered = items;
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
            for (ArticuloI01 articuloI01 : mItems )
            {
                if ( articuloI01.getNombreCompleto().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) ) )
                {
                    myList.add(articuloI01);

                }
            }
            mItemsFiltered = myList;
        }
        this.notifyDataSetChanged();
    }

    public void swap(List list){
        mItems = list;
      mItemsFiltered = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_elemento_resumen01, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        viewHolder.tvNombreFuente.setText(mItemsFiltered.get(i).getNombreCompleto());
        viewHolder.iconStatus.setTypeface(font);
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

        public  TextView tvNombreFuente;
        public Button iconStatus;


        ViewHolder(View v) {
            super(v);
            font = Typeface.createFromAsset(v.getContext().getAssets(), "fontawesome-webfont.ttf");
            success_color = v.getContext().getResources().getColor(R.color.success);
            warning_color = v.getContext().getResources().getColor(R.color.warning);
            tvNombreFuente = (TextView)v.findViewById(R.id.tvNombreFuente);
            iconStatus = (Button) v.findViewById(R.id.iconStatus);

        }
    }

}
