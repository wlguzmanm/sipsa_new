package gov.dane.sipsa.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import gov.dane.sipsa.R;
import gov.dane.sipsa.adapter.ObservacionesRecyclerAdapter;
import gov.dane.sipsa.adapter.ObservacionesRecyclerDistritoAdapter;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteDistrito;
import gov.dane.sipsa.dao.Observacion;
import gov.dane.sipsa.dao.ObservacionDistrito;
import gov.dane.sipsa.interfaces.OnItemClick;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.manager.IDatabaseManager;
import gov.dane.sipsa.model.ObservacionElem;

/**
 * Created by mguzman on 13/05/2019.
 */
public class NuevaObservacionDistritoActivity extends AppCompatActivity implements OnItemClick {

    public String novedad;
    public FuenteDistrito fuente;
    public static IDatabaseManager databaseManager;

    public RadioButton rbRelativas;
    public RadioButton rbExistentes;
    public RadioButton rbTodas;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    public ObservacionesRecyclerDistritoAdapter observacionAdapter;
    private EditText etNuevaObservacion;
    private String currentFactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_observacion_distrito);


        databaseManager = DatabaseManager.getInstance(this);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {

                if (extras.getSerializable("fuente")!=null) {
                    fuente = (FuenteDistrito) extras.getSerializable("fuente");

                }
                if (extras.getSerializable("factor")!=null) {
                    currentFactor = (String)extras.getSerializable("factor");
                }

            } else {
                novedad = extras.getString("novedad");
                fuente = (FuenteDistrito) extras.getSerializable("fuente");
                currentFactor = (String)extras.getSerializable("factor");

            }
        } else {
            novedad = savedInstanceState.getString("novedad");
            fuente= (FuenteDistrito) savedInstanceState.getSerializable("fuente");
            currentFactor = (String)savedInstanceState.getSerializable("factor");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        toolbar.setTitle(fuente.getFuenNombre());
        toolbar.setSubtitle(currentFactor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final List<ObservacionElem> observaciones = databaseManager.listaObservacionesDistrito(novedad);
        observacionAdapter = new ObservacionesRecyclerDistritoAdapter(observaciones);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listaObervacionesDistritoNew);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(observacionAdapter);

        observacionAdapter.setItemClick(this);
        etNuevaObservacion = (EditText)findViewById(R.id.etNuevaObservacionDistrito);

        observacionAdapter.filterDataNovedad(novedad,"ES");

        FloatingActionButton fabGuardarNuevaObservacion =(FloatingActionButton) findViewById(R.id.fabGuardarNuevaObservacionDistrito);

        fabGuardarNuevaObservacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNuevaObservacion.getText().toString().trim().length()>0){
                    ObservacionDistrito observacion = new ObservacionDistrito();
                    observacion.setObseDescripcion(etNuevaObservacion.getText().toString());
                    observacion.setNovedad(novedad);
                    //observacion.setObseTipo("R");
                    databaseManager.save(observacion);
                    Toast.makeText(getApplicationContext(),"Observación agregada exitosamenteDIS",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Por favor ingrese una observaciónDIS",Toast.LENGTH_LONG).show();
                }

            }
        });


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
            case R.id.action_clear:
                etNuevaObservacion.setText("");
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
    public void onItemClicked(Object observacion) {
        etNuevaObservacion.setText(((ObservacionElem) observacion).getObseDescripcion());
    }
}
