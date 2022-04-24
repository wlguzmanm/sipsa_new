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
import java.util.Date;
import java.util.List;

import gov.dane.sipsa.R;
import gov.dane.sipsa.adapter.ProductosRecyclerAdapter01;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.FuenteTireI01;
import gov.dane.sipsa.dao.Grupo;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.manager.IDatabaseManager;
import gov.dane.sipsa.model.Elemento01;
import gov.dane.sipsa.model.FactorI01;
import gov.dane.sipsa.model.Municipio;
import gov.dane.sipsa.model.NovedadRecoleccion;
import gov.dane.sipsa.model.RecoleccionPrincipal01;
import gov.dane.sipsa.model.Status;

/**
 * Created by andres on 16/02/2016.
 */
public class FactorRecoleccionActivity01 extends App {
    public Fuente fuente;
    public static IDatabaseManager databaseManager;
    public List<Elemento01> elementos;
    public List<Elemento01> elementosFilter;
    private ProductosRecyclerAdapter01 productoAdapter01;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    private MenuItem mSearchAction;
    public List<FuenteArticulo> fuenteArticulos = new ArrayList<>();
    private ArrayAdapter<FuenteArticulo> factorArrayAdapter;
    public List<Grupo> grupoList = new ArrayList<>();
    private ArrayAdapter<Grupo> grupoArrayAdapter;
    private Integer LISTA_OBSERVACIONES = 12;
    private CheckBox cbPendientes;
    private TextView etPendientes;
    private TextView etCompletadas;
    Municipio currentMunicipio = new Municipio();
    FactorI01 currentFactorI01 = new FactorI01();
    private Long currentFutiId;
    private NovedadRecoleccion novedadRecoleccion;
    private Date fechaProgramada;
    private FuenteTireI01 fuenteTire;


    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factor_recoleccion01);
        cbPendientes = (CheckBox) findViewById(R.id.cbPendientes);
        etPendientes = (TextView) findViewById(R.id.etPendientes);
        etCompletadas = (TextView) findViewById(R.id.etCompletadas);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras.getSerializable("Municipio") != null) {
                currentMunicipio = (Municipio) extras.getSerializable("Municipio");
            }
            if (extras.getSerializable("FactorI01") != null) {
                currentFactorI01 = (FactorI01) extras.getSerializable("FactorI01");
            }
            if (extras.getSerializable("FuenTire") != null){
                fuenteTire = (FuenteTireI01) extras.getSerializable ("FuenTire");
            }
            /*if (extras.getSerializable("FechaProgramada") != null){
                fechaProgramada = (Date) this.getIntent().getExtras().get("FechaProgramada");
            }*/
        } else {
            fuente = (Fuente) savedInstanceState.getSerializable("factor");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        toolbar.setTitle(currentMunicipio.getMuniNombre());
        toolbar.setSubtitle(currentFactorI01.getTireNombre());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseManager = DatabaseManager.getInstance(this);

        //fuenteArticulos = databaseManager.listaTipoRecoleccionByFuenteId(fuente.getFuenId());


        elementos = databaseManager.listaRecoleccionPrincipal01(currentFactorI01.getTireId(),currentMunicipio.getMuniId(), fuenteTire.getFutiId());

            elementosFilter = elementos;
            productoAdapter01 = new ProductosRecyclerAdapter01(elementosFilter, currentFactorI01.getTireId(),currentMunicipio.getMuniId());

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listaProdutos);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(productoAdapter01);



        if (fuenteArticulos != null) {
            Grupo g = new Grupo();
            g.setGrupNombre("TODOS");
            grupoList.add(g);
        }


        final FloatingActionMenu fam= (FloatingActionMenu) findViewById(R.id.famOpciones);

        final FloatingActionButton fabAddProducto = (FloatingActionButton) findViewById(R.id.addProducto);


        fabAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, AdicionarArticuloActivity01.class);
                intent.putExtra("Municipio", currentMunicipio);
                intent.putExtra("FactorI01", currentFactorI01);
                intent.putExtra("FuenTire", (Serializable) fuenteTire);
                //intent.putExtra("FechaProgramada", fechaProgramada);
                context.startActivity(intent);
            }
        });

        final FloatingActionButton fabNuevoElemento = (FloatingActionButton) findViewById(R.id.fabNuevoElemento);


        fabNuevoElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent intent = new Intent(context, RecoleccionNuevoArticuloActivity01.class);
                intent.putExtra("Municipio", currentMunicipio);
                intent.putExtra("FactorI01", currentFactorI01);
                intent.putExtra("FuenTire", (Serializable) fuenteTire);
                intent.putExtra("Novedad",novedadRecoleccion.NUEVO );
                //intent.putExtra("FechaProgramada", fechaProgramada);
                context.startActivity(intent);
            }
        });


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
        });




        cbPendientes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                actualizarListadoElementos();
            }
        });

        elementosFilter = filterDataPendiente(elementos, cbPendientes.isChecked());
        productoAdapter01.swap(elementosFilter);

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
       elementos = databaseManager.listaRecoleccionPrincipal01(currentFactorI01.getTireId(),currentMunicipio.getMuniId(), fuenteTire.getFutiId());
        elementosFilter = filterDataPendiente(elementos, cbPendientes.isChecked());
        productoAdapter01.swap(elementosFilter);

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


    public List<Elemento01> filterDataPendiente(List<Elemento01> mItems, boolean isPendiente) {
        ArrayList<Elemento01> myList = new ArrayList<Elemento01>();
        int pendientes = 0;
        int completadas = 0;
        if (mItems == null) {
            etCompletadas.setText("0");
            etPendientes.setText("0");
        } else {

            for (Elemento01 elementoCheck : mItems) {
                if (isPendiente && elementoCheck.getNoRecoleccion() !=null && elementoCheck.getNoRecoleccion()<3) {
                    myList.add(elementoCheck);
                } else if (!isPendiente && elementoCheck.getNoRecoleccion() >= 3) {
                    myList.add(elementoCheck);

                }

                if (elementoCheck.getNoRecoleccion() !=null && elementoCheck.getNoRecoleccion() >= 3) {
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
