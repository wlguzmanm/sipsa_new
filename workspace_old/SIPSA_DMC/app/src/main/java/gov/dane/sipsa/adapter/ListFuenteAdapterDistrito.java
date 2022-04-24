package gov.dane.sipsa.adapter;

import android.app.Activity;
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
import gov.dane.sipsa.dao.Fuente;


public class ListFuenteAdapterDistrito extends RecyclerView.Adapter<ListFuenteAdapterDistrito.ViewHolder> {

    List<Fuente> fuentes;

    private List<Fuente> vehicleFiltered;
    private Activity context;
    ArrayList<Fuente> myList = new ArrayList<Fuente>();

    public ListFuenteAdapterDistrito(Activity context, List<Fuente> fuentes) {
        this.fuentes = fuentes;
        vehicleFiltered = fuentes;
        this.context = context;
    }

    public void filterData( String filter )
    {
        if ( "".equals( filter ) )
        {
            vehicleFiltered = fuentes;
            myList.clear();

        }
        else
        {
            myList.clear();
            vehicleFiltered =null;
            for (Fuente fuente : fuentes )
            {
                if ( fuente.getFuenNombre().toLowerCase(Locale.US).contains( filter.toLowerCase(Locale.US) ) )
                {
                    myList.add(fuente);

                }
            }

            vehicleFiltered = myList;
        }
        this.notifyDataSetChanged();
    }


    @Override
    public ListFuenteAdapterDistrito.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        //create view and viewholder
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_fuente_distrito, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

    }

    // class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public Button button;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);


        }
    }

    // Returns the size of the fruitsData
    @Override
    public int getItemCount() {
        return vehicleFiltered.size();
    }
}