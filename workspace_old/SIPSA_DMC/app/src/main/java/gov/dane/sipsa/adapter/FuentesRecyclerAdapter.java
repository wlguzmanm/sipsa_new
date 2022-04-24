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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gov.dane.sipsa.R;
import gov.dane.sipsa.activity.FuenteDetalleActivity;
import gov.dane.sipsa.activity.FuenteRecoleccionActivity;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.manager.IDatabaseManager;

public class FuentesRecyclerAdapter extends RecyclerView.Adapter<FuentesRecyclerAdapter.ViewHolder> {

    private List<Fuente> mItems;
    private List<Fuente> mItemsFiltered;
    private ArrayList<Fuente> myList = new ArrayList<Fuente>();
    public Typeface font;
    Integer success_color;
    Integer warning_color;
    Integer default_color;

    private IDatabaseManager databaseManager;
    public FuentesRecyclerAdapter(List<Fuente> items) {

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
            for (Fuente fuente : mItems )
            {
                if ( fuente.getFuenNombre().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) ) )
                {
                    myList.add(fuente);

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

    public void clear(){
        mItems = new ArrayList<>();
        mItemsFiltered = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_fuente, viewGroup, false);

        font = Typeface.createFromAsset(v.getContext().getAssets(), "fontawesome-webfont.ttf");
        success_color = v.getContext().getResources().getColor(R.color.success);
        warning_color = v.getContext().getResources().getColor(R.color.warning);
        default_color = v.getContext().getResources().getColor(R.color.gray_light);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        viewHolder.tvNombreFuente.setText(mItemsFiltered.get(i).getFuenNombre());
        viewHolder.tvDireccion.setText(mItemsFiltered.get(i).getFuenDireccion());

        if (mItemsFiltered.get(i).getMuniId() == null) {
            viewHolder.tvCiudad.setVisibility(View.GONE);
        } else {
                viewHolder.tvCiudad.setVisibility(View.VISIBLE);
                viewHolder.tvCiudad.setText(mItemsFiltered.get(i).getMuniNombre());
        }
        viewHolder.tvTelefono.setText(mItemsFiltered.get(i).getFuenTelefono());
        viewHolder.tvInformante.setText(mItemsFiltered.get(i).getFuenNombreInformante());

        viewHolder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent =new Intent(context, FuenteDetalleActivity.class);

                intent.putExtra("fuente", (Serializable) mItemsFiltered.get(i));
                context.startActivity(intent);
            }
        });


        viewHolder.btnRecolectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Context context = view.getContext();
                Intent intent =new Intent(context, FuenteRecoleccionActivity.class);
                intent.putExtra("factor", (Serializable) mItemsFiltered.get(i));
                intent.putExtra("municipio", (Serializable) mItemsFiltered.get(i));
                context.startActivity(intent);
            }
        });



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
        public  TextView tvDireccion;
        public TextView tvCiudad;
        public  TextView tvTelefono;
        public  TextView tvInformante;
        public Button iconStatus;
        public Button btnEditar;
        public Button btnRecolectar;

        ViewHolder(View v) {
            super(v);
            Typeface font = Typeface.createFromAsset(v.getContext().getAssets(), "fontawesome-webfont.ttf");
            tvNombreFuente = (TextView)v.findViewById(R.id.tvNombreFuente);
            tvDireccion = (TextView)v.findViewById(R.id.tvDireccion);
            tvCiudad = (TextView) v.findViewById(R.id.tvCiudad);
            tvTelefono = (TextView)v.findViewById(R.id.tvTelefono);
            tvInformante = (TextView)v.findViewById(R.id.tvInformante);

            iconStatus = (Button) v.findViewById(R.id.iconStatus);
            //iconStatus.setTextColor(v.getContext().getResources().getColor(R.color.success));
            //iconStatus.setTypeface(font);
            btnEditar = (Button) v.findViewById(R.id.btnEditarFuente);
            btnRecolectar = (Button) v.findViewById(R.id.btnRecolectarFuente);
        }
    }

}
