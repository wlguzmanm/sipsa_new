package co.gov.dane.sipsa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.gov.dane.sipsa.adapter.AddElementoRecyclerAdapter;
import co.gov.dane.sipsa.backend.Database;
import co.gov.dane.sipsa.backend.dao.Fuente;
import co.gov.dane.sipsa.backend.dao.FuenteArticulo;
import co.gov.dane.sipsa.backend.dao.Grupo;
import co.gov.dane.sipsa.backend.model.Elemento;
import co.gov.dane.sipsa.backend.model.Status;

public class FuenteAddElementoActivity extends AppCompatActivity {

    public FuenteArticulo factor;
    public Fuente fuente;
    private EditText edtSeach;
    public Spinner spFactorArticulo;
    public Spinner spGrupoArticulo;
    public AddElementoRecyclerAdapter productAdapter;
    public List<Elemento> elementos = new ArrayList<>();
    public List<FuenteArticulo> listFactor = new ArrayList<>();
    private ArrayAdapter<FuenteArticulo> factorArrayAdapter;
    public List<Grupo> grupoList = new ArrayList<>();
    private ArrayAdapter<Grupo> dataAdapterGrupoArticulo;
    public LinearLayout llPrimerNivel;
    public LinearLayout llSegundoNivel;
    AsyncTask<Void, Void, Status> taskCargarElementos = null;
    private ProgressDialog pdialog;


    private ImageView atrasAgregarFuenteRecoleccion;
    private Mensajes msj;
    private TextView title, subtitle;
    private LinearLayout fabGuardar;
    public static Database databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuente_add_producto);

        spFactorArticulo = (Spinner) findViewById(R.id.spFactorArticulo);
        spGrupoArticulo = (Spinner) findViewById(R.id.spGrupoArticulo);
        llPrimerNivel = (LinearLayout) findViewById(R.id.llPrimerNivel);
        llSegundoNivel = (LinearLayout) findViewById(R.id.llSegundoNivel);

        atrasAgregarFuenteRecoleccion = (ImageView) findViewById(R.id.atrasAgregarFuenteRecoleccion);
        fabGuardar = (LinearLayout) findViewById(R.id.fabVincularProducto);
        title = (TextView) findViewById(R.id.title);
        subtitle = (TextView) findViewById(R.id.subtitle);

        databaseManager = new Database(FuenteAddElementoActivity.this);
        msj = new Mensajes(FuenteAddElementoActivity.this);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras.getSerializable("factor") != null) {
                factor = (FuenteArticulo) extras.getSerializable("factor");
                fuente = (Fuente) extras.getSerializable("fuente");
            }
        } else {
            factor = (FuenteArticulo) savedInstanceState.getSerializable("factor");
            fuente = (Fuente) savedInstanceState.getSerializable("fuente");
        }

        title.setText(fuente.getFuenNombre());
        subtitle.setText(factor.getTireNombre());


        taskCargarElementos = new AsyncTask<Void, Void, Status>() {
            @Override
            protected void onPreExecute() {
                pdialog = ProgressDialog.show(FuenteAddElementoActivity.this, "",
                        "Obteniendo los elementos, espere un momento..");
                super.onPreExecute();
            }

            @Override
            protected co.gov.dane.sipsa.backend.model.Status doInBackground(Void... params) {
                co.gov.dane.sipsa.backend.model.Status status = new co.gov.dane.sipsa.backend.model.Status();
                elementos = databaseManager.listaElementoByTireId(factor.getTireId(), fuente.getFuenId(), fuente.getMuniId());
                return status;
            }

            @Override
            protected void onPostExecute(co.gov.dane.sipsa.backend.model.Status result) {
                if (elementos != null) {

                    productAdapter =  new AddElementoRecyclerAdapter(elementos,fuente,factor);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listaProdutos);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(productAdapter);

                }
                pdialog.dismiss();
                super.onPostExecute(result);
            }
        };
        taskCargarElementos.execute();






        listFactor.add(factor);

        factorArrayAdapter = new ArrayAdapter<FuenteArticulo>(this, R.layout.simple_spinner_item, listFactor);
        factorArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spFactorArticulo.setAdapter(factorArrayAdapter);

        Grupo g = new Grupo();
        g.setGrupNombre("TODOS");
        grupoList.add(g);
        grupoList.addAll( databaseManager.listaGrupoByTireId(((FuenteArticulo)spFactorArticulo.getSelectedItem()).getTireId()));

        dataAdapterGrupoArticulo = new ArrayAdapter<Grupo>(this, R.layout.simple_spinner_item, grupoList);
        dataAdapterGrupoArticulo.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spGrupoArticulo.setAdapter(dataAdapterGrupoArticulo);

        spGrupoArticulo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String grupo= ((Grupo) adapterView.getItemAtPosition(i)).getGrupNombre();
                filtrarElementos(grupo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        atrasAgregarFuenteRecoleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



    public void procesoAgregarElementos() {
        AsyncTask<Void, Void, Status> task = null;
        task=new AsyncTask<Void, Void, Status>() {
            @Override
            protected void onPreExecute ()
            {
                pdialog = ProgressDialog.show(FuenteAddElementoActivity.this, "",
                        "Agregando elementos a la factor, espere un momento..");
                super.onPreExecute();
            }

            @Override
            protected co.gov.dane.sipsa.backend.model.Status doInBackground (Void...params)
            {

                co.gov.dane.sipsa.backend.model.Status status = new co.gov.dane.sipsa.backend.model.Status();
                List<Elemento> elementos= productAdapter.mItems;
                List<Elemento> elementosSeleccionados = new ArrayList<Elemento>();
                boolean  isChecked = false;
                for(Elemento el: elementos){
                    if(el.getCheck()!=null && el.getCheck()){
                        isChecked = true;
                        elementosSeleccionados.add(el);
                        //  Principal p = new Principal();
                        // p.setArticacoId(el.getId());

                    }
                }

                if (isChecked) {
                    status.setType(co.gov.dane.sipsa.backend.model.Status.StatusType.OK);
                    status.setDescription("Articulos Agregados Exitosamente a la factor.");
                } else {
                    status.setType(co.gov.dane.sipsa.backend.model.Status.StatusType.ERROR);
                    status.setDescription("Debe seleccionar al menos un elemento para agregar a la factor.");
                }
                return  status;
            }

            @Override
            protected void onPostExecute (co.gov.dane.sipsa.backend.model.Status result)
            {
                pdialog.dismiss();
                super.onPostExecute(result);
                if (result.getType().equals(co.gov.dane.sipsa.backend.model.Status.StatusType.OK)) {
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), result.getDescription().toString(), Toast.LENGTH_LONG).show();
                }
                try {
                    this.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        };
        task.execute();
    }

    private int getIndex(Spinner spinner, String myString){
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equals(myString)){
                index = i;
            }
        }
        return index;
    }
    public void filtrarElementos(String grupo) {
        if(productAdapter!=null){
            String filtro = "";
            if (edtSeach != null) {
                filtro = edtSeach.getText().toString();
            }
            productAdapter.filterData(grupo, filtro);
        }

    }

}