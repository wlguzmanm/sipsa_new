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
import gov.dane.sipsa.activity.RecoleccionActivity01;
import gov.dane.sipsa.activity.ResumenRecoleccionActivity01;
import gov.dane.sipsa.model.Elemento01;
import gov.dane.sipsa.model.NovedadRecoleccion;
import gov.dane.sipsa.model.Resumen01;

public class ProductosRecyclerResumenAdapter01 extends RecyclerView.Adapter<ProductosRecyclerResumenAdapter01.ViewHolder> {

    private List<Resumen01> mItems;
    private List<Resumen01> mItemsFiltered;
    private ArrayList<Resumen01> myList = new ArrayList<Resumen01>();



    private String municipio;
    private Long factorI01;
    public Typeface font;
    Integer white_color;
    Integer success_color;
    Integer default_color;
    Context mContext;


    public ProductosRecyclerResumenAdapter01(List<Resumen01> mItems, Long tireId, String muniId) {
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
            for (Resumen01 rp : mItems )
            {

                    if ( rp.getInfoNombre().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) ))
                    {
                        myList.add(rp);
                    }

            }
            mItemsFiltered = myList;
        }
        this.notifyDataSetChanged();
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,final int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_resumen_01, viewGroup, false);
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


        final Resumen01 resumen = mItemsFiltered.get(position);
        final ArrayList<Resumen01> resumenI01s = new ArrayList<>();
        resumenI01s.addAll(mItemsFiltered);


        viewHolder.etNombreFuente.setText(resumen.getInfoNombre());
        viewHolder.btnEditarRecoleccion.setVisibility(View.VISIBLE);

        viewHolder.btnEditarRecoleccion.setOnClickListener(null);
        viewHolder.btnEditarRecoleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, RecoleccionActivity01.class);

                selected_position = position;
                notifyItemChanged(selected_position);
                intent.putExtra("resumen", resumen);
                if (resumen.getNovedad()!=null && resumen.getNovedad().equals("IA")) {
                    intent.putExtra("novedad", NovedadRecoleccion.ADICIONADA);
                }else if (resumen.getNovedad()!=null && resumen.getNovedad().equals("IN")){
                    intent.putExtra("novedad", NovedadRecoleccion.NUEVO);
                }else if (resumen.getNovedad()!=null && resumen.getNovedad().equals("ND")) {
                    intent.putExtra("novedad", NovedadRecoleccion.NO_DISPONIBLE);
                }else if (resumen.getNovedad()!=null && resumen.getNovedad().equals("IS")) {
                    intent.putExtra("novedad", NovedadRecoleccion.INSUMO_SALE);
                }else if (resumen.getNovedad()!=null && resumen.getNovedad().equals("PR")) {
                    intent.putExtra("novedad", NovedadRecoleccion.PROMOCION);
                }else {
                    intent.putExtra("novedad", NovedadRecoleccion.PROGRAMADA);
                }
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
        public TextView etNombreFuente;
        public Button btnEditarRecoleccion;


        ViewHolder(View v) {
            super(v);
            etNombreFuente = (TextView) v.findViewById(R.id.etNombreFuente);
            btnEditarRecoleccion = (Button) v.findViewById(R.id.btnEditarRecoleccion);
        }
    }

    public void swap(List list){
        mItems = list;
        mItemsFiltered = list;
        this.notifyDataSetChanged();
    }




}
