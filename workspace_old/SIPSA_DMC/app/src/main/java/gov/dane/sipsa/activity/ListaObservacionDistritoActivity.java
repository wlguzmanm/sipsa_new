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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import gov.dane.sipsa.R;
import gov.dane.sipsa.adapter.ObservacionesRecyclerAdapter;
import gov.dane.sipsa.adapter.ObservacionesRecyclerDistritoAdapter;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteDistrito;
import gov.dane.sipsa.interfaces.OnItemClick;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.manager.IDatabaseManager;
import gov.dane.sipsa.model.ObservacionElem;
import gov.dane.sipsa.model.Status;

/**
 * Created by mguzman on 13/05/2019.
 */
public class ListaObservacionDistritoActivity extends App implements OnItemClick {


    public static IDatabaseManager databaseManager;

    public RadioButton rbRelativas;
    public RadioButton rbExistentes;
    public RadioButton rbTodas;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    public FuenteDistrito fuente;
    public  String currentFactor;

    private EditText edtSeach;
    public ObservacionesRecyclerDistritoAdapter observacionAdapter;


    public FloatingActionMenu famObservacion;
    public boolean saveOnResult = false;
    private List<ObservacionElem> observaciones;
    private String tipoNovedad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_observacion_distrito);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {

                if (extras.getString("tipoNovedad") != null) {
                    tipoNovedad = extras.getString("tipoNovedad");
                    fuente = (FuenteDistrito) extras.getSerializable("fuente");
                    currentFactor = (String)extras.getSerializable("factor");
                }
                if (extras.getBoolean("saveOnResult")) {
                    saveOnResult = extras.getBoolean("saveOnResult");
                    fuente = (FuenteDistrito) extras.getSerializable("fuente");
                    currentFactor = (String)extras.getSerializable("factor");
                }
            }
        } else {


            if (savedInstanceState.getBoolean("saveOnResult")) {
                saveOnResult = savedInstanceState.getBoolean("saveOnResult");
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        toolbar.setTitle(fuente.getFuenNombre());
        toolbar.setSubtitle(currentFactor);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseManager = DatabaseManager.getInstance(this);


        observaciones = databaseManager.listaObservacionesDistrito(tipoNovedad);
        observacionAdapter = new ObservacionesRecyclerDistritoAdapter(observaciones);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listaObervacionesDistrito);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(observacionAdapter);

        observacionAdapter.setItemClick(this);

        final FloatingActionButton fabGuardarObservacion = (FloatingActionButton) findViewById(R.id.fabGuardarObservacionDistrito);
        final FloatingActionButton fabAgregarObservacion = (FloatingActionButton) findViewById(R.id.fabAgregarObservacionDistrito);

        famObservacion = (FloatingActionMenu) findViewById(R.id.famObservacionDistrito);

        rbExistentes = (RadioButton) findViewById(R.id.rbExistentesDistrito);
        rbRelativas = (RadioButton) findViewById(R.id.rbRelativasDistrito);
        rbTodas  = (RadioButton) findViewById(R.id.rbTodasDistrito);

        rbExistentes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    observaciones = databaseManager.listaObservacionesDistrito("P");
                    observacionAdapter.swap(observaciones);
                    observacionAdapter.filterDataNovedad("P","ES");
                }
            }
        });

        rbRelativas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    observaciones = databaseManager.listaObservacionesDistrito("P");
                    observacionAdapter.swap(observaciones);
                    observacionAdapter.filterDataNovedad("P",null);
                }
            }
        });


        rbTodas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    observaciones = databaseManager.listaObservacionesDistrito("P");
                    observacionAdapter.swap(observaciones);
                    observacionAdapter.filterDataNovedad(null, null);
                }
            }
        });


        fabGuardarObservacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean swObservacion = false;
                ObservacionElem currentObservacion = null;
                for (ObservacionElem o : observacionAdapter.mItems) {
                    if (o.isChecked() !=null && o.isChecked()) {
                        currentObservacion = o;
                        swObservacion = true;
                        break;
                    }
                }

                if (!swObservacion) {
                    Toast.makeText(getApplicationContext(), "Seleccione una Observación", Toast.LENGTH_LONG).show();
                    famObservacion.close(true);
                    return;
                }


                Intent resultData = new Intent();
                resultData.putExtra("vinculado", true);
                resultData.putExtra("observacion", currentObservacion);
                resultData.putExtra("saveOnResult", saveOnResult);
                setResult(RESULT_OK, resultData);

                finish();
            }
        });

        fabAgregarObservacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, NuevaObservacionDistritoActivity.class);
                intent.putExtra("novedad",tipoNovedad);
                intent.putExtra("fuente",fuente);
                intent.putExtra("factor", currentFactor);
                context.startActivity(intent);
                famObservacion.close(true);
            }
        });

        famObservacion.setClosedOnTouchOutside(true);

    }

    @Override
    protected void onPostExecute(Status result) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        observacionAdapter.filterData(s);
    }

    @Override
    public void onBackPressed() {
        if(isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onItemClicked(Object o) {

    }

    @Override
    protected void onResume() {
        observacionAdapter.swap(databaseManager.listaObservacionesDistrito(tipoNovedad));

        super.onResume();
    }
}
