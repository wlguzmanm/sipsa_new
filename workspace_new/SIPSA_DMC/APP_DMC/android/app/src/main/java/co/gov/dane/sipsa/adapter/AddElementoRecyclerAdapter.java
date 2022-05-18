package co.gov.dane.sipsa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.gov.dane.sipsa.R;
import co.gov.dane.sipsa.RecoleccionActivity;
import co.gov.dane.sipsa.backend.dao.Fuente;
import co.gov.dane.sipsa.backend.dao.FuenteArticulo;
import co.gov.dane.sipsa.backend.model.Elemento;
import co.gov.dane.sipsa.backend.model.NovedadRecoleccion;

public class AddElementoRecyclerAdapter extends RecyclerView.Adapter<AddElementoRecyclerAdapter.ViewHolder> {
    public List<Elemento> mItems;
    private List<Elemento> mItemsFiltered;
    private ArrayList<Elemento> myList = new ArrayList<Elemento>();
    private Fuente fuente;
    private FuenteArticulo factor;

    public AddElementoRecyclerAdapter(List<Elemento> mItems, Fuente fuente, FuenteArticulo factor) {
        this.mItems = mItems;
        this.mItemsFiltered = mItems;
        this.fuente = fuente;
        this.factor = factor;
    }

    public void filterData(String nivel, String filter)
    {
        if ( "".equals( filter ) )
        {
            filterDataNovedad(nivel);
            /*
            mItemsFiltered = mItems;
            myList.clear();
            */
        }
        else
        {
            myList.clear();
            mItemsFiltered =null;
            for (Elemento elementoCheck : mItems )
            {
                if ( elementoCheck.getArtiNombre().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) ) ||
                        elementoCheck.getArticacoRegicaLinea().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) )  )
                {
                    myList.add(elementoCheck);
                }
            }
            mItemsFiltered = myList;
        }
        this.notifyDataSetChanged();
    }


    public void filterDataNovedad(String filter)
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
                if(elementoCheck.getGrupNombre()!=null){
                    if ( elementoCheck.getGrupNombre().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) )
                            || "TODOS".equals(filter))
                    {
                        myList.add(elementoCheck);
                    }
                }

            }
            mItemsFiltered = myList;
        }
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_add_producto, viewGroup, false);
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
        viewHolder.etCasaComercial.setText(item.getCacoNombre()!=null?item.getCacoNombre().toString():"");
        viewHolder.etIcaArticulo.setText(item.getArticacoRegicaLinea()!=null?item.getArticacoRegicaLinea().toString():"");
        viewHolder.cbProducto.setOnCheckedChangeListener(null);
        //if true, your checkbox will be selected, else unselected
        viewHolder.cbProducto.setChecked(item.getCheck()==null?false:item.getCheck());

        viewHolder.cbProducto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                item.setCheck(isChecked);
                Context context =buttonView.getContext();
                Intent intent=  new Intent(context, RecoleccionActivity.class);
                intent.putExtra("elemento",item);
                intent.putExtra("fuente", (Serializable) fuente);
                intent.putExtra("novedad", NovedadRecoleccion.ADICIONADA);
                intent.putExtra("factor", (Serializable) factor);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemsFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox cbProducto;
        public TextView etIcaArticulo;
        public TextView etCasaComercial;

        public View view;

        ViewHolder(View v) {
            super(v);
            view = v;
            cbProducto = (CheckBox) v.findViewById(R.id.cbProducto);
            etIcaArticulo = (TextView) v.findViewById(R.id.etIcaArticulo);
            etCasaComercial = (TextView) v.findViewById(R.id.etCasaComercial);

        }
    }
}
