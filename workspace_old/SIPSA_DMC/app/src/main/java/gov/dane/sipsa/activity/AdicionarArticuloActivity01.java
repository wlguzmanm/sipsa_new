package gov.dane.sipsa.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import gov.dane.sipsa.R;
import gov.dane.sipsa.adapter.AddProductosRecyclerAdapter01;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.ArticuloI01;
import gov.dane.sipsa.dao.FuenteTireI01;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.manager.IDatabaseManager;
import gov.dane.sipsa.model.FactorI01;
import gov.dane.sipsa.model.Municipio;
import gov.dane.sipsa.model.Status;

/**
 * Created by hdblanco on 28/09/17.
 */

public class AdicionarArticuloActivity01 extends App{

    private Municipio currentMunicipio;
    private FactorI01 currentFactorI01;
    private ArticuloI01 articulo;
    private AddProductosRecyclerAdapter01 addProductoAdapter;
    public List<ArticuloI01> articulos;
    public List<ArticuloI01> articulosFilter;
    private EditText edtSeach;
    private boolean isSearchOpened = false;
    private MenuItem mSearchAction;
    private FuenteTireI01 fuenteTire;
    private Long currentFutiId;
    private Date fechaProgramada;
    AsyncTask<Void, Void, Status> taskCargarArticulos = null;
    private ProgressDialog pdialog;



    public static IDatabaseManager databaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionado_elemento_recoleccion01);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras.getSerializable("Municipio") != null) {
                currentMunicipio = (Municipio) extras.getSerializable("Municipio");
            }
            if (extras.getSerializable("FactorI01") != null) {
                currentFactorI01 = (FactorI01) extras.getSerializable("FactorI01");
            }
            if (extras.getSerializable("FuenTire") != null){
                fuenteTire = (FuenteTireI01) extras.getSerializable("FuenTire");
            }
            /*if (extras.getSerializable("FechaProgramada")!=null){
                fechaProgramada = (Date) this.getIntent().getExtras().get("FechaProgramada");
            }*/
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        toolbar.setTitle(currentMunicipio.getMuniNombre());
        toolbar.setSubtitle(currentFactorI01.getTireNombre());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseManager = DatabaseManager.getInstance(this);

        taskCargarArticulos = new AsyncTask<Void, Void, Status>() {
            @Override
            protected void onPreExecute() {
                pdialog = ProgressDialog.show(AdicionarArticuloActivity01.this, "",
                        "Obteniendo los elementos, espere un momento..");
                super.onPreExecute();
            }

            @Override
            protected gov.dane.sipsa.model.Status doInBackground(Void... params) {
                gov.dane.sipsa.model.Status status = new gov.dane.sipsa.model.Status();
                articulos = databaseManager.listaArticulosAdicionado(currentFactorI01.getTireId(), currentMunicipio.getMuniId());
                return status;
            }

            @Override
            protected void onPostExecute(gov.dane.sipsa.model.Status result) {
                if (articulos != null) {
                    articulos = databaseManager.listaArticulosAdicionado(currentFactorI01.getTireId(), currentMunicipio.getMuniId());
                    articulosFilter = articulos;
                    addProductoAdapter = new AddProductosRecyclerAdapter01(articulosFilter, currentFactorI01.getTireNombre(), currentMunicipio.getMuniNombre(),currentMunicipio.getMuniId(),fuenteTire.getFutiId(),fuenteTire.getPrreFechaProgramada(), fuenteTire.getFuenId());
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listaProdutos);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(addProductoAdapter);

                }
                pdialog.dismiss();
                super.onPostExecute(result);
            }
        };
        taskCargarArticulos.execute();

        articulos = databaseManager.listaArticulosAdicionado(currentFactorI01.getTireId(), currentMunicipio.getMuniId());
            articulosFilter = articulos;
            addProductoAdapter = new AddProductosRecyclerAdapter01(articulosFilter, currentFactorI01.getTireNombre(), currentMunicipio.getMuniNombre(),currentMunicipio.getMuniId(),fuenteTire.getFutiId(),fuenteTire.getPrreFechaProgramada(), fuenteTire.getFuenId());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listaProdutos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(addProductoAdapter);

    }

    @Override
    protected void onPostExecute(Status result) {
        actualizarListadoElementos();
    }

    @Override
    protected void onResume() {
        if (taskCargarArticulos.getStatus().equals(AsyncTask.Status.FINISHED)) {
            finish();
            actualizarListadoElementos();

        }
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //  savedInstanceState.putSerializable("factor",factor);
        super.onSaveInstanceState(savedInstanceState);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    public void actualizarListadoElementos() {
        articulos = databaseManager.listaArticulosAdicionado(currentFactorI01.getTireId(), currentMunicipio.getMuniId());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void filtrarElementos(String nombreGrupo) {
        String filtro = "";
        if (edtSeach != null) {
            filtro = edtSeach.getText().toString();
        }
        //productoAdapter.filterData(nombreGrupo, filtro);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    private void doSearch(String s) {
        addProductoAdapter.filterData( s);

    }

    @Override
    public void onBackPressed() {
        if (isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }

    protected void handleMenuSearch() {
        ActionBar action = getSupportActionBar(); //get the actionbar

        if (isSearchOpened) { //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(android.R.drawable.ic_menu_search);

            isSearchOpened = false;
            doSearch("");
            edtSeach.setText("");
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor

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

}
