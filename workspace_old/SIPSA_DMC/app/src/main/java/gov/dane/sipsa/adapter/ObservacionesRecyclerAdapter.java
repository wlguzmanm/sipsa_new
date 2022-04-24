package gov.dane.sipsa.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gov.dane.sipsa.R;
import gov.dane.sipsa.dao.Observacion;
import gov.dane.sipsa.interfaces.OnItemClick;
import gov.dane.sipsa.model.ObservacionElem;

public class ObservacionesRecyclerAdapter extends RecyclerView.Adapter<ObservacionesRecyclerAdapter.ViewHolder> {

    public List<ObservacionElem> mItems;
    public List<ObservacionElem> mItemsFiltered;
    public Observacion currentObservacion = null;
    private ArrayList<ObservacionElem> myList = new ArrayList<ObservacionElem>();
    private EditText etNuevaObservacion;
    private OnItemClick itemClick;

    public ObservacionesRecyclerAdapter(List<ObservacionElem> items) {
        mItems = items;
        mItemsFiltered = mItems;

    }


    public void filterDataNovedad(String filter)
    {
        filter = filter == null?"":filter;
        if ( "".equals( filter ) )
        {
            mItemsFiltered = mItems;
            myList.clear();
        }
        else
        {
            myList.clear();
            mItemsFiltered =null;
            for (ObservacionElem observacion : mItems )
            {
                if ( observacion.getNovedad().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) ) )
                {
                    myList.add(observacion);
                }
            }
            mItemsFiltered = myList;
        }
        this.notifyDataSetChanged();
    }

    public void filterData(String filter)
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
            for (ObservacionElem observacion : mItems )
            {
                if ( observacion.getObseDescripcion().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) ) )
                {
                    myList.add(observacion);
                }
            }
            mItemsFiltered = myList;
        }
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.observacion_item, viewGroup, false);
        return new ViewHolder(v);
    }

    private int selected_position = 0;

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final ObservacionElem item = mItemsFiltered.get(position);

        viewHolder.etObsevacion.setText(mItemsFiltered.get(position).getObseDescripcion());
        viewHolder.itemView.setOnClickListener(null);


        if(item.isChecked() != null && item.isChecked()){
            // Here I am just highlighting the background
            viewHolder.itemView.setBackgroundColor((viewHolder.itemView.getResources().getColor(R.color.primary)));
            itemClick.onItemClicked(item);
        }else{
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int i = 0;
                for(ObservacionElem o : mItemsFiltered){
                    o.setChecked(false);
                    notifyItemChanged(i);
                    i++;
                }

                item.setChecked(item.isChecked()==null?true:!item.isChecked());
                selected_position = position;
                notifyItemChanged(selected_position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemsFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView etObsevacion;
        ViewHolder(View v) {
            super(v);
            etObsevacion = (TextView) v.findViewById(R.id.etObservacion);

        }

    }

    public OnItemClick getItemClick() {
        return itemClick;
    }

    public void setItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public void swap(List list){
        mItems = list;
        mItemsFiltered = list;
        notifyDataSetChanged();
    }

}
