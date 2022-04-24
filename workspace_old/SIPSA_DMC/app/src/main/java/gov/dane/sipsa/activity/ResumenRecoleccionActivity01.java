package gov.dane.sipsa.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gov.dane.sipsa.R;
import gov.dane.sipsa.adapter.ProductosRecyclerAdapter01;
import gov.dane.sipsa.adapter.ProductosRecyclerResumenAdapter01;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.ArticuloI01;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.Grupo;
import gov.dane.sipsa.dao.PrincipalI01;
import gov.dane.sipsa.dao.RecoleccionI01;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.manager.IDatabaseManager;
import gov.dane.sipsa.model.Elemento01;
import gov.dane.sipsa.model.FactorI01;
import gov.dane.sipsa.model.Municipio;
import gov.dane.sipsa.model.NovedadRecoleccion;
import gov.dane.sipsa.model.Resumen01;
import gov.dane.sipsa.model.Status;

/**
 * Created by andres on 16/02/2016.
 */
public class ResumenRecoleccionActivity01 extends App {

    public static IDatabaseManager databaseManager;
    public Resumen01 resumen01;
    public List<Resumen01> resumen01s;
    public List<Resumen01> resumen01sFilter;
    public Elemento01 elemento;
    public List<Elemento01> elementos;
    public List<Elemento01> elementosFilter;
    private ProductosRecyclerResumenAdapter01 productoAdapter01;
    private TextView tvNombreArticulo;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    private MenuItem mSearchAction;
    public List<FuenteArticulo> fuenteArticulos = new ArrayList<>();
    private ArrayAdapter<FuenteArticulo> factorArrayAdapter;
    public List<Grupo> grupoList = new ArrayList<>();
    private ArrayAdapter<Grupo> grupoArrayAdapter;
    private Integer LISTA_OBSERVACIONES = 12;
    private NovedadRecoleccion novedadRecoleccion;
    private Button btnEliminarRecoleccion;
    Municipio currentMunicipio = new Municipio();
    FactorI01 currentFactorI01 = new FactorI01();


    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_recoleccion01);



        databaseManager = DatabaseManager.getInstance(this);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras.getSerializable("recoleccion") != null) {
                elemento =(Elemento01) extras.getSerializable("recoleccion");
            }
            if(extras.getSerializable("novedad") != null){
                novedadRecoleccion =(NovedadRecoleccion) extras.getSerializable("novedad");
            }
        } else {
            elemento = (Elemento01) savedInstanceState.getSerializable("recoleccion");
            novedadRecoleccion =(NovedadRecoleccion) savedInstanceState.getSerializable("novedad");
        }

        tvNombreArticulo = (TextView) findViewById(R.id.tvNombreArticulo);
        btnEliminarRecoleccion = (Button) findViewById(R.id.btnEliminarRecoleccion);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        toolbar.setTitle(elemento.getMuniNombre());
        toolbar.setSubtitle(elemento.getTireNombre());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseManager = DatabaseManager.getInstance(this);

        if (elemento != null ){

            if (novedadRecoleccion.equals(NovedadRecoleccion.ADICIONADA) || novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)){

                btnEliminarRecoleccion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(ResumenRecoleccionActivity01.this)
                                .setTitle("")
                                .setMessage("¿Realmente desea eliminar toda la Recolección del Articulo ?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                List<RecoleccionI01> recoleccion = databaseManager.getRecoleccionByArtiId01(elemento.getArtiId(), elemento.getGrupoInsumoId());
                                for (RecoleccionI01 r : recoleccion) {
                                    databaseManager.delete(r);
                                }

                                List<PrincipalI01> principal = databaseManager.getPrincipalById01(elemento.getArtiId(), elemento.getGrupoInsumoId());
                                for (PrincipalI01 r : principal) {
                                    databaseManager.delete(r);
                                }

                                if (novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)){
                                    List<ArticuloI01> articulos = databaseManager.getArticuloByArtiId01(elemento.getArtiId(), elemento.getGrupoInsumoId());
                                    for (ArticuloI01 a : articulos) {
                                        databaseManager.delete(a);
                                    }
                                }

                                Toast.makeText(getApplicationContext(), "Articulo eliminado exitosamente.", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }).show();
                    }
                });


            }else {
                btnEliminarRecoleccion.setEnabled(false);
            }

        }

        //fuenteArticulos = databaseManager.listaTipoRecoleccionByFuenteId(fuente.getFuenId());


        //elementos = databaseManager.listaRecoleccionPrincipal01(currentFactorI01.getTireId(),currentMunicipio.getMuniId());

        resumen01s = databaseManager.listaResumenRecoleccion(elemento.getTireId(),elemento.getArtiId(),elemento.getGrupoInsumoId(),elemento.getMuniId(), elemento.getFutiId());
        resumen01sFilter = resumen01s;
        productoAdapter01 = new ProductosRecyclerResumenAdapter01(resumen01sFilter,elemento.getTireId(),elemento.getMuniId());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listaProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productoAdapter01);


        tvNombreArticulo.setText(elemento.getNombreArtiCompleto());

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onPostExecute(Status result) {

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
        resumen01s = databaseManager.listaResumenRecoleccion(elemento.getTireId(),elemento.getArtiId(),elemento.getGrupoInsumoId(),elemento.getMuniId(), elemento.getFutiId());
        resumen01sFilter = resumen01s;
        productoAdapter01.swap(resumen01sFilter);

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

    public void obtenerEstados() {
        Integer pendientes = 0;
        Integer completadas = 0;
        for (Elemento01 elemento : elementos) {
            /*if (elemento.getEstadoRecoleccion().equals("1")) {
                completadas += 1;
            } else {
                pendientes += 1;
            }*/
        }
        //       etPendientes.setText(pendientes.toString());
        //     etCompletadas.setText(completadas.toString());
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {

        if ( requestCode == 10 && resultCode == RESULT_OK) {
            boolean result = data.getBooleanExtra("retornarAlMain", false);
            if (result) {
                finish();
            }
        } else if (requestCode == LISTA_OBSERVACIONES) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Se ha aplicado la novedad en todos los elementos de la factor.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        actualizarListadoElementos();
        super.onResume();
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    private void doSearch(String s) {
        productoAdapter01.filterData( s);

    }

    @Override
    public void onBackPressed() {
        if (isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }



}