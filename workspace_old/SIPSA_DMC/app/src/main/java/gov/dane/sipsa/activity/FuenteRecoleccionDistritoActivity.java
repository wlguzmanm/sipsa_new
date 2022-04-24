package gov.dane.sipsa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import gov.dane.sipsa.adapter.ProductosRecyclerAdapter;
import gov.dane.sipsa.adapter.ProductosRecyclerAdapterDistrito;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.FuenteArticuloDistrito;
import gov.dane.sipsa.dao.FuenteDistrito;
import gov.dane.sipsa.dao.Grupo;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.manager.IDatabaseManager;
import gov.dane.sipsa.model.Municipio;
import gov.dane.sipsa.model.RecoleccionPrincipal;
import gov.dane.sipsa.model.Status;

/**
 * Created by mguzman on 08/04/2019.
 */
public class FuenteRecoleccionDistritoActivity extends App {
    public FuenteDistrito fuente;
    public static IDatabaseManager databaseManager;
    public List<RecoleccionPrincipal> elementos;
    public List<RecoleccionPrincipal> elementosFilter;
    private ProductosRecyclerAdapterDistrito productoAdapter;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    private MenuItem mSearchAction;
    public List<FuenteArticuloDistrito> fuenteArticulos = new ArrayList<>();
    private ArrayAdapter<FuenteArticuloDistrito> factorArrayAdapter;
    private Municipio municipio = new Municipio();


    private Integer LISTA_OBSERVACIONES = 12;
    private CheckBox cbPendientes;
    private TextView etPendientes;
    private TextView etCompletadas;
    private FuenteArticulo factor;


    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuente_recoleccion_distrito);
        cbPendientes = (CheckBox) findViewById(R.id.cbPendientes);
        etPendientes = (TextView) findViewById(R.id.etPendientes);
        etCompletadas = (TextView) findViewById(R.id.etCompletadas);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras.getSerializable("factor") != null) {
                fuente = (FuenteDistrito) extras.getSerializable("factor");
            }
            if (extras.getSerializable("factor") != null) {
                municipio = (Municipio) extras.getSerializable("municipio");
            }
        } else {
            fuente = (FuenteDistrito) savedInstanceState.getSerializable("factor");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        toolbar.setTitle((fuente.getFuenNombre()!= null)?fuente.getFuenNombre().toString():null);
        toolbar.setSubtitle((fuente.getMuniNombre()!= null)? fuente.getMuniNombre().toString():null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseManager = DatabaseManager.getInstance(this);

        fuenteArticulos = databaseManager.listaTipoRecoleccionDistritoByFuenteId(fuente.getFuenId());
        factorArrayAdapter = new ArrayAdapter<FuenteArticuloDistrito>(this, R.layout.spinner_item, fuenteArticulos);
        factorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(fuenteArticulos==null){
            fuenteArticulos = new ArrayList<>();
        }



        elementos = databaseManager.listaRecoleccionDistritoPricipal(13L,fuente.getFuenId());
        elementosFilter = elementos;
        productoAdapter = new ProductosRecyclerAdapterDistrito(elementosFilter, fuente);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listaProdutosFuenteDistrito);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productoAdapter);

     /*   if (fuenteArticulos != null) {
            Grupo g = new Grupo();
            g.setGrupNombre("TODOS");
            //grupoList.add(g);
        }*/


        final FloatingActionMenu fam= (FloatingActionMenu) findViewById(R.id.famOpcionesDistrito);

        final FloatingActionButton fabAddProducto = (FloatingActionButton) findViewById(R.id.addProductoDistrito);


        fabAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, FuenteAddElementoDistritoActivity.class);
                intent.putExtra("fuente", (Serializable) fuente);
                if(fuenteArticulos.size()==0){
                    FuenteArticulo ad =   new FuenteArticulo() ;
                    ad.setTireId(13L);
                    ad.setTireNombre("DISTRITOS DE RIEGO");
                    intent.putExtra("factor", ad);
                }else{
                    intent.putExtra("factor", (Serializable) fuenteArticulos.get(0));
                }
                context.startActivity(intent);
            }
        });

        final FloatingActionButton fabNuevoElemento = (FloatingActionButton) findViewById(R.id.fabNuevoElementoDistrito);


        fabNuevoElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, RecoleccionNuevoArticuloDistritoActivity.class);
                if(fuenteArticulos.size()==0){
                    FuenteArticuloDistrito ad =   new FuenteArticuloDistrito() ;
                    ad.setTireId(13L);
                    ad.setFuenId(fuente.getFuenId());
                    ad.setMuniId(fuente.getMuniId());
                    ad.setTireNombre("DISTRITOS DE RIEGO");
                    intent.putExtra("factor", ad);
                }else{
                    if(fuenteArticulos.get(0)!= null && fuenteArticulos.get(0).getFuenId()==null){
                        fuenteArticulos.get(0).setFuenId(fuente.getFuenId());
                        fuenteArticulos.get(0).setTireId(fuente.getTireId());
                        fuenteArticulos.get(0).setMuniId(fuente.getMuniId());
                    }
                    intent.putExtra("factor", (Serializable) fuenteArticulos.get(0));
                }
                intent.putExtra("fuente", (Serializable) fuente);
                context.startActivity(intent);
            }
        });



/*
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx,int dy){
                super.onScrolled(recyclerView, dx, dy);

                if (dy >0) {
                    // Scroll Down
                    if (fam.isShown()) {
                        fam.setVisibility(View.GONE);
                    }
                } else if (dy <0) {
                    // Scroll Up
                    if (!fam.isShown()) {
                        fam.setVisibility(View.VISIBLE);
                    }
                }
            }
        });*/


        cbPendientes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                actualizarListadoElementos();
            }
        });

        elementosFilter = filterDataPendiente(elementos, cbPendientes.isChecked());
        productoAdapter.swap(elementosFilter);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onPostExecute(Status result) {

    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable("factor",factor);
        super.onSaveInstanceState(savedInstanceState);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    public void actualizarListadoElementos() {


        elementos = databaseManager.listaRecoleccionDistritoPricipal(13L,fuente.getFuenId());
        elementosFilter = filterDataPendiente(elementos, cbPendientes.isChecked());
        productoAdapter.swap(elementosFilter);

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
        productoAdapter.filterData(nombreGrupo);
    }

    public void obtenerEstados() {
        Integer pendientes = 0;
        Integer completadas = 0;
        for (RecoleccionPrincipal elemento : elementos) {
            if (elemento.getEstadoRecoleccion().equals("1")) {
                completadas += 1;
            } else {
                pendientes += 1;
            }
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
                Toast.makeText(getApplicationContext(), "Se ha aplicado la novedad en todos los elementos de la factor. DISTRITO", Toast.LENGTH_LONG).show();
                finish();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        actualizarListadoElementos();
        //filtrarElementos(((Grupo) spGrupo.getSelectedItem()).getGrupNombre());
        //grupoArrayAdapter.notifyDataSetChanged();
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
        productoAdapter.filterData(s);

    }

    @Override
    public void onBackPressed() {
        if (isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }


    public List<RecoleccionPrincipal> filterDataPendiente(List<RecoleccionPrincipal> mItems, boolean isPendiente) {
        ArrayList<RecoleccionPrincipal> myList = new ArrayList<RecoleccionPrincipal>();
        int pendientes = 0;
        int completadas = 0;
        if (mItems == null) {
            etCompletadas.setText("0");
            etPendientes.setText("0");
        } else {

            for (RecoleccionPrincipal elementoCheck : mItems) {
                if (isPendiente && elementoCheck.getEstadoRecoleccion() !=null && !elementoCheck.getEstadoRecoleccion()) {
                    myList.add(elementoCheck);
                } else if (!isPendiente && elementoCheck.getEstadoRecoleccion() !=null && elementoCheck.getEstadoRecoleccion()) {
                    myList.add(elementoCheck);
                }

                if (elementoCheck.getEstadoRecoleccion()!=null && elementoCheck.getEstadoRecoleccion()) {
                    completadas++;
                } else {
                    pendientes++;
                }
            }
            etCompletadas.setText("" + completadas);
            etPendientes.setText("" + pendientes);

            return myList;
        }
        return null;
    }

}
