package gov.dane.sipsa.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gov.dane.sipsa.R;
import gov.dane.sipsa.adapter.AddElementoRecyclerAdapter;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.ElementoEspecificacion;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.Grupo;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.manager.IDatabaseManager;
import gov.dane.sipsa.model.Elemento;
import gov.dane.sipsa.model.Factor;
import gov.dane.sipsa.model.Nivel;
import gov.dane.sipsa.model.Status;

/**
 * Created by andreslopera on 4/12/16.
 */
public class FuenteAddElementoActivity extends App {
    public FuenteArticulo factor;
    public Fuente fuente;
    public static IDatabaseManager databaseManager;
    private EditText edtSeach;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fuente_add_producto);
        spFactorArticulo = (Spinner) findViewById(R.id.spFactorArticulo);
        spGrupoArticulo = (Spinner) findViewById(R.id.spGrupoArticulo);
        llPrimerNivel = (LinearLayout) findViewById(R.id.llPrimerNivel);
        llSegundoNivel = (LinearLayout) findViewById(R.id.llSegundoNivel);



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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        toolbar.setTitle(factor.getFuenNombre());

        toolbar.setSubtitle(factor.getFuenNombre());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseManager = DatabaseManager.getInstance(this);

        taskCargarElementos = new AsyncTask<Void, Void, Status>() {
            @Override
            protected void onPreExecute() {
                pdialog = ProgressDialog.show(FuenteAddElementoActivity.this, "",
                        "Obteniendo los elementos, espere un momento..");
                super.onPreExecute();
            }

            @Override
            protected gov.dane.sipsa.model.Status doInBackground(Void... params) {
                gov.dane.sipsa.model.Status status = new gov.dane.sipsa.model.Status();
                elementos = databaseManager.listaElementoByTireId(factor.getTireId(), fuente.getFuenId(), fuente.getMuniId());
                return status;
            }

            @Override
            protected void onPostExecute(gov.dane.sipsa.model.Status result) {
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

        factorArrayAdapter = new ArrayAdapter<FuenteArticulo>(this, R.layout.spinner_item, listFactor);
        factorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFactorArticulo.setAdapter(factorArrayAdapter);

        Grupo g = new Grupo();
        g.setGrupNombre("TODOS");
        grupoList.add(g);
        grupoList.addAll( databaseManager.listaGrupoByTireId(((FuenteArticulo)spFactorArticulo.getSelectedItem()).getTireId()));

        dataAdapterGrupoArticulo = new ArrayAdapter<Grupo>(this, R.layout.spinner_item, grupoList);
        dataAdapterGrupoArticulo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGrupoArticulo.setAdapter(dataAdapterGrupoArticulo);

        FloatingActionButton fabVincularProducto = (FloatingActionButton) findViewById(R.id.fabVincularProducto);
        fabVincularProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procesoAgregarElementos();
            }
        });

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
    }

    @Override
    protected void onPostExecute(Status result) {

    }

    ;

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
            protected gov.dane.sipsa.model.Status doInBackground (Void...params)
            {

                gov.dane.sipsa.model.Status status = new gov.dane.sipsa.model.Status();
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

                    /*String estadoFuente = databaseManager.getEstadoFuente(factor.getId(), currentPeriodo.getIdPeriodo());
                    factor.setEstado(estadoFuente == null ? "" : estadoFuente);
                    databaseManager.save(factor);*/


                    status.setType(gov.dane.sipsa.model.Status.StatusType.OK);
                    status.setDescription("Articulos Agregados Exitosamente a la factor.");
                } else {
                    status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                    status.setDescription("Debe seleccionar al menos un elemento para agregar a la factor.");
                }
                return  status;
            }

            @Override
            protected void onPostExecute (gov.dane.sipsa.model.Status result)
            {
                pdialog.dismiss();
                super.onPostExecute(result);
                if (result.getType().equals(gov.dane.sipsa.model.Status.StatusType.OK)) {
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.home:
                onBackPressed();
                break;
            case R.id.homeAsUp:
                onBackPressed();
                break;
            case 16908332:
                onBackPressed();
                break;
            case R.id.action_settings:
                break;
            case R.id.action_search:
                handleMenuSearch();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }


    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(android.R.drawable.ic_menu_search);


            isSearchOpened = false;
            doSearch("");
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor
            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    doSearch(edtSeach.getText().toString());

                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        return true;
                    }
                    return false;
                }
            });

            edtSeach.requestFocus();
            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);
            //add the close icon
            mSearchAction.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
            isSearchOpened = true;
        }
    }

    private void doSearch(String s) {
        edtSeach.setText(s);
        if (((Grupo) spGrupoArticulo.getSelectedItem()).getGrupNombre().equals("TODOS")) {
            productAdapter.filterData("TODOS", s);
        } else {
            productAdapter.filterData( ((Grupo) spGrupoArticulo.getSelectedItem()).getGrupNombre(),  s);
        }
    }

    @Override
    public void onBackPressed() {
        if(isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
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