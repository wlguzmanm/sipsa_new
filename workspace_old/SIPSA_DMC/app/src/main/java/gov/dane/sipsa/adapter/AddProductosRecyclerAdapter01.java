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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import gov.dane.sipsa.R;
import gov.dane.sipsa.activity.AdicionarArticuloActivity01;
import gov.dane.sipsa.activity.FactorRecoleccionActivity01;
import gov.dane.sipsa.activity.RecoleccionActivity01;
import gov.dane.sipsa.activity.ResumenRecoleccionActivity01;
import gov.dane.sipsa.dao.Articulo;
import gov.dane.sipsa.dao.ArticuloI01;
import gov.dane.sipsa.model.Elemento01;
import gov.dane.sipsa.model.NovedadRecoleccion;

public class AddProductosRecyclerAdapter01 extends RecyclerView.Adapter<AddProductosRecyclerAdapter01.ViewHolder> {

    private List<ArticuloI01> mItems;
    private List<ArticuloI01> mItemsFiltered;
    private ArrayList<ArticuloI01> myList = new ArrayList<ArticuloI01>();


    private String municipio;
    private String muniId;
    private String factorI01;
    private Date fechaProgramada;
    private Long futiId;
    private Long fuenId;
    public Typeface font;
    Integer white_color;
    Integer success_color;
    Integer default_color;
    Context mContext;


    public AddProductosRecyclerAdapter01(List<ArticuloI01> mItems, String tireNombre, String muniNombre, String muniId, Long futiId, Date fechaProgramada, Long fuenId) {
        this.mItems = mItems;
        this.mItemsFiltered = mItems;
        this.factorI01 = tireNombre;
        this.municipio = muniNombre;
        this.muniId = muniId;
        this.futiId = futiId;
        this.fechaProgramada=fechaProgramada;
        this.fuenId=fuenId;
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
            for (ArticuloI01 rp : mItems )
            {

                    if (rp.getNombreCompleto().toLowerCase(Locale.US).contains(filter.toLowerCase(Locale.US))) {
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
            for (ArticuloI01 elementoCheck : mItems )
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_adicionar_elemento01, viewGroup, false);
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


        final ArticuloI01 articulo = mItemsFiltered.get(position);
        final ArrayList<ArticuloI01> articulos = new ArrayList<>();
        articulos.addAll(mItemsFiltered);


        viewHolder.etNombreArticulo.setText(articulo.getNombreCompleto());
        viewHolder.btnRecolectar.setVisibility(View.VISIBLE);


        viewHolder.btnRecolectar.setOnClickListener(null);
        viewHolder.btnRecolectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, RecoleccionActivity01.class);

                //selected_position = position;
                //notifyItemChanged(selected_position);
                intent.putExtra("adicionado", articulo);
                intent.putExtra("municipio", municipio);
                intent.putExtra("municipioId", muniId);
                intent.putExtra("factor",factorI01);
                intent.putExtra("novedad", NovedadRecoleccion.ADICIONADA);
                intent.putExtra("futiId", futiId);
                intent.putExtra("FechaProgramada", fechaProgramada);
                intent.putExtra("FuenId", fuenId );
                context.startActivity(intent);
                //((AdicionarArticuloActivity01) mContext).startActivityForResult(intent, 10);
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
        public TextView etNombreArticulo;
        public Button btnRecolectar;



        ViewHolder(View v) {
            super(v);
            etNombreArticulo = (TextView) v.findViewById(R.id.etNombreArticulo);
            btnRecolectar = (Button) v.findViewById(R.id.btnRecolectar);

        }
    }

    public void swap(List list){
        mItems = list;
        mItemsFiltered = list;
        this.notifyDataSetChanged();
    }




}
