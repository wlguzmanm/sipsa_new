package gov.dane.sipsa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gov.dane.sipsa.R;
import gov.dane.sipsa.activity.FuenteAddElementoDistritoActivity;
import gov.dane.sipsa.activity.RecoleccionActivity;
import gov.dane.sipsa.activity.RecoleccionDistritoActivity;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.FuenteArticuloDistrito;
import gov.dane.sipsa.dao.FuenteDistrito;
import gov.dane.sipsa.model.Elemento;
import gov.dane.sipsa.model.NovedadRecoleccion;

public class AddElementoRecyclerAdapterDistrito extends RecyclerView.Adapter<AddElementoRecyclerAdapterDistrito.ViewHolder> {
    public List<Elemento> mItems;
    private List<Elemento> mItemsFiltered;
    private ArrayList<Elemento> myList = new ArrayList<Elemento>();
    private FuenteDistrito fuente;
    private FuenteArticuloDistrito factor;

    public AddElementoRecyclerAdapterDistrito(List<Elemento> mItems, FuenteDistrito fuente, FuenteArticuloDistrito factor) {
        this.mItems = mItems;
        this.mItemsFiltered = mItems;
        this.fuente = fuente;
        this.factor = factor;
    }

    public void filterData(String nivel, String filter)
    {
        if ( "".equals( filter ) )
        {
            mItemsFiltered = mItems;
            myList.clear();
        }
        else
        {
           myList.clear();
            mItemsFiltered =null;
            for (Elemento elementoCheck : mItems )
            {
                    if ( elementoCheck.getArtiNombre().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) ) ||
                            elementoCheck.getTipo().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) ) ||
                            elementoCheck.getFrecuencia().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) ) ||
                            elementoCheck.getUnidadMedidaNombre().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) ) ||
                            elementoCheck.getObservacion().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) ))
                    {
                        myList.add(elementoCheck);
                    }
            }
            mItemsFiltered = myList;
        }
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_add_producto_distrito, viewGroup, false);
        return new ViewHolder(v);
    }


    public void setChecked(boolean check) {
        for (Elemento e: mItemsFiltered) {
            e.setCheck(check);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final Elemento item = mItemsFiltered.get(i);
        viewHolder.cbProducto.setText(item.getArtiNombre()!=null?item.getArtiNombre().toString():"");
        viewHolder.cbProducto.setTag(item.getArticacoId().toString());
        viewHolder.etTipoArticulo.setText(item.getTipo()!=null?item.getTipo().toString():"");
        viewHolder.etFrecuenciaArticulo.setText(item.getFrecuencia()!=null?item.getFrecuencia().toString():"");
        viewHolder.etUnidadArticulo.setText(item.getUnidadMedidaNombre()!=null?item.getUnidadMedidaNombre().toString():"");
        viewHolder.etObservacionArticulo.setText(item.getObservacion()!=null?item.getObservacion().toString():"");
        viewHolder.cbProducto.setOnCheckedChangeListener(null);
        //if true, your checkbox will be selected, else unselected
        viewHolder.cbProducto.setChecked(item.getCheck()==null?false:item.getCheck());

        viewHolder.cbProducto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                item.setCheck(isChecked);
                if(isChecked){
                    Context context =buttonView.getContext();
                    Intent intent=  new Intent(context, RecoleccionDistritoActivity.class);
                    intent.putExtra("elemento",item);
                    intent.putExtra("fuente", (Serializable) fuente);
                    intent.putExtra("novedad", NovedadRecoleccion.ADICIONADA);
                    intent.putExtra("factor", (Serializable) factor.getTireNombre());
                    intent.putExtra("factorObject", (Serializable) factor);
                    context.startActivity(intent);
                     ((FuenteAddElementoDistritoActivity)context).finish();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemsFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox cbProducto;
        public TextView etTipoArticulo;
        public TextView etFrecuenciaArticulo;
        public TextView etUnidadArticulo;
        public TextView etObservacionArticulo;

        public View view;

        ViewHolder(View v) {
            super(v);
            view = v;
            cbProducto = (CheckBox) v.findViewById(R.id.cbProducto);
            etTipoArticulo = (TextView) v.findViewById(R.id.etTipoArticulo);
            etFrecuenciaArticulo = (TextView) v.findViewById(R.id.etFrecuenciaArticulo);
            etUnidadArticulo = (TextView) v.findViewById(R.id.etUnidadArticulo);
            etObservacionArticulo = (TextView) v.findViewById(R.id.etObservacionArticulo);
        }
    }
}



