package gov.dane.sipsa.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import gov.dane.sipsa.R;
import gov.dane.sipsa.adapter.ResumenRecyclerAdapter;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.manager.IDatabaseManager;
import gov.dane.sipsa.model.ResumenFuente;
import gov.dane.sipsa.model.IndiceResumen;
import gov.dane.sipsa.model.Status;

/**
 * Created by andreslopera on 4/12/16.
 */
public class ResumenActivity extends App {

    public static IDatabaseManager databaseManager;
    public List<ResumenFuente> resumenFuentes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resumen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("RESUMEN POR INDICE");
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        databaseManager = DatabaseManager.getInstance(this);
        resumenFuentes = databaseManager.obtenerResumenFuente();

        if (resumenFuentes != null) {
            IndiceResumen indiceResumen = new IndiceResumen();
            List<IndiceResumen> resumen = new ArrayList<IndiceResumen>();

            for (ResumenFuente resumenFuente : resumenFuentes) {
                if (resumenFuente.getTotalElementos() != 0) {

                        if (indiceResumen.getIdIndice() != null) {
                            resumen.add(indiceResumen);
                        }
                        indiceResumen = new IndiceResumen();
                        indiceResumen.setNombre(resumenFuente.getNombreIndice());
                        if (resumenFuente.getTotalElementos() == resumenFuente.getRecolectados()) {
                            indiceResumen.setCompletas(1);
                            indiceResumen.setIncompletas(0);
                            indiceResumen.setPendientes(0);
                        } else if (resumenFuente.getRecolectados() != 0) {
                            indiceResumen.setCompletas(0);
                            indiceResumen.setIncompletas(1);
                            indiceResumen.setPendientes(0);
                        } else {
                            indiceResumen.setCompletas(0);
                            indiceResumen.setIncompletas(0);
                            indiceResumen.setPendientes(1);
                        }
                        indiceResumen.setTotal(1);
                }
            }

            resumen.add(indiceResumen);

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter (new ResumenRecyclerAdapter(resumen));
        }
    }

    @Override
    protected void onPostExecute(Status result) {

    }

    ;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case 16908332:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
