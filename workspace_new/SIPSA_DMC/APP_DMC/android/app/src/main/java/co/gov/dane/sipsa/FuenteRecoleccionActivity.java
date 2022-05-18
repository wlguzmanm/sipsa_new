package co.gov.dane.sipsa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.gov.dane.sipsa.adapter.AdapterFuente;
import co.gov.dane.sipsa.adapter.ProductosRecyclerAdapter;
import co.gov.dane.sipsa.backend.Database;
import co.gov.dane.sipsa.backend.dao.Fuente;
import co.gov.dane.sipsa.backend.dao.FuenteArticulo;
import co.gov.dane.sipsa.backend.dao.Grupo;
import co.gov.dane.sipsa.backend.model.Municipio;
import co.gov.dane.sipsa.backend.model.RecoleccionPrincipal;

public class FuenteRecoleccionActivity extends AppCompatActivity {

    public Fuente fuente;
    public static Database databaseManager;
    public Spinner spFactorFuente;
    public Spinner spGrupo;
    public LinearLayout llFactor;
    public LinearLayout llGrupo;
    public List<RecoleccionPrincipal> elementos;
    public List<RecoleccionPrincipal> elementosFilter;
    private ProductosRecyclerAdapter productoAdapter;
    public List<FuenteArticulo> fuenteArticulos = new ArrayList<>();
    private ArrayAdapter<FuenteArticulo> factorArrayAdapter;
    public List<Grupo> grupoList = new ArrayList<>();
    private ArrayAdapter<Grupo> grupoArrayAdapter;
    private Integer LISTA_OBSERVACIONES = 12;
    private CheckBox cbPendientes;
    private TextView etPendientes;
    private TextView etCompletadas;
    private FuenteArticulo factor;

    private LinearLayout agregarProducto, nuevoProducto;


    private ImageView atrasRecolectar;
    private Mensajes msj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuente_recoleccion);
        databaseManager = new Database(FuenteRecoleccionActivity.this);
        msj = new Mensajes(FuenteRecoleccionActivity.this);

        spFactorFuente = (Spinner) findViewById(R.id.spFactorFuente);
        spGrupo = (Spinner) findViewById(R.id.spGrupo);
        llFactor = (LinearLayout) findViewById(R.id.llFactor);
        llGrupo = (LinearLayout) findViewById(R.id.llGrupo);
        cbPendientes = (CheckBox) findViewById(R.id.cbPendientes);
        etPendientes = (TextView) findViewById(R.id.etPendientes);
        etCompletadas = (TextView) findViewById(R.id.etCompletadas);

        agregarProducto = (LinearLayout) findViewById(R.id.agregarProducto);
        nuevoProducto = (LinearLayout) findViewById(R.id.nuevoProducto);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras.getSerializable("factor") != null) {
                fuente = (Fuente) extras.getSerializable("factor");
            }
        } else {
            fuente = (Fuente) savedInstanceState.getSerializable("factor");
        }

        fuenteArticulos = databaseManager.listaTipoRecoleccionByFuenteId(fuente.getFuenId());
        factorArrayAdapter = new ArrayAdapter<FuenteArticulo>(this, R.layout.simple_spinner_item, fuenteArticulos);
        factorArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spFactorFuente.setAdapter(factorArrayAdapter);

        spFactorFuente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                factor = ((FuenteArticulo) adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        factor=(FuenteArticulo)spFactorFuente.getSelectedItem();

        elementos = databaseManager.listaRecoleccionPricipal(((FuenteArticulo)spFactorFuente.getSelectedItem()).getTireId(),fuente.getFuenId());
        elementosFilter = elementos;
        productoAdapter = new ProductosRecyclerAdapter(elementos, fuente);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listaProdutos);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(productoAdapter);

        if (fuenteArticulos != null) {
            Grupo g = new Grupo();
            g.setGrupNombre("TODOS");
            grupoList.add(g);
        }
        grupoArrayAdapter = new ArrayAdapter<Grupo>(this, R.layout.simple_spinner_item, grupoList);
        grupoArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spGrupo.setAdapter(grupoArrayAdapter);

        agregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, FuenteAddElementoActivity.class);
                intent.putExtra("fuente", (Serializable) fuente);
                intent.putExtra("factor", (Serializable) spFactorFuente.getSelectedItem());
                context.startActivity(intent);
            }
        });
        nuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, RecoleccionNuevoArticuloActivity.class);
                intent.putExtra("fuente", (Serializable) fuente);
                intent.putExtra("factor", (Serializable) spFactorFuente.getSelectedItem());
                context.startActivity(intent);
            }
        });


        spFactorFuente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(grupoList!=null) {
                    grupoList.clear();
                    if (fuenteArticulos != null) {
                        Grupo g = new Grupo();
                        g.setGrupNombre("TODOS");
                        grupoList.add(g);
                    }
                    grupoList.addAll(databaseManager.listaGrupoByTireId(((FuenteArticulo) adapterView.getItemAtPosition(i)).getTireId()));
                    grupoArrayAdapter.notifyDataSetChanged();
                    actualizarListadoElementos();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spGrupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String nombreGrupo = ((Grupo) adapterView.getItemAtPosition(i)).getGrupNombre();
                filtrarElementos(nombreGrupo);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        elementosFilter = filterDataPendiente(elementos, cbPendientes.isChecked());
        productoAdapter.swap(elementosFilter);

        cbPendientes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                actualizarListadoElementos();
            }
        });

        SearchView searchView = (SearchView) findViewById(R.id.svSearch);
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                actualizarListadoElementos();
                filtrarElementos(((Grupo) spGrupo.getSelectedItem()).getGrupNombre());
                grupoArrayAdapter.notifyDataSetChanged();

                productoAdapter.getList(databaseManager.listaRecoleccionPricipal(((FuenteArticulo)spFactorFuente.getSelectedItem()).getTireId(),fuente.getFuenId()));
                productoAdapter.getFilter().filter(newText);
                productoAdapter.notifyDataSetChanged();
                return false;
            }
        });












        atrasRecolectar = findViewById(R.id.atrasRecolectar);
        atrasRecolectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(FuenteRecoleccionActivity.this,MainActivity.class);
        startActivity(mainIntent);
        finish();
    }





    /**
     *
     * @param nombreGrupo
     */
    public void filtrarElementos(String nombreGrupo) {
        String filtro = "";
        /*if (edtSeach != null) {
            filtro = edtSeach.getText().toString();
        }*/
        productoAdapter.filterData(nombreGrupo, filtro);
    }

    /**
     * Metodo que actualiza la lista
     */
    public void actualizarListadoElementos() {
        elementos = databaseManager.listaRecoleccionPricipal(((FuenteArticulo)spFactorFuente.getSelectedItem()).getTireId(),fuente.getFuenId());
        elementosFilter = new ArrayList<>();
        elementosFilter = filterDataPendiente(elementos, cbPendientes.isChecked());

        productoAdapter.notifyDataSetChanged();
        productoAdapter.swap(elementosFilter);
    }

    /**
     * Metodo qe filtra los data pendientes
     *
     * @param mItems
     * @param isPendiente
     * @return
     */
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