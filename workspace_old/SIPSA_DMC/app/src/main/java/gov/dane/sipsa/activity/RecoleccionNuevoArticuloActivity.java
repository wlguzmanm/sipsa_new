package gov.dane.sipsa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.dane.sipsa.R;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.Articulo;
import gov.dane.sipsa.dao.CasaComercial;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.Recoleccion;
import gov.dane.sipsa.dao.UnidadMedida;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.model.Elemento;
import gov.dane.sipsa.model.ObservacionElem;
import gov.dane.sipsa.model.Presentacion;
import gov.dane.sipsa.model.Status;
import gov.dane.sipsa.utils.MoneyTextWatcher;


/**
 * Created by hdblanco on 4/07/17.
 */

public class RecoleccionNuevoArticuloActivity extends App {

    public Elemento elemento;
    public Fuente fuente;
    private TextView tvCasaComercial;
    private EditText etArticulo,etIca;
    private Spinner spPresentacion;
    private Spinner spUnidad;
    public ArrayAdapter<Presentacion> adapterUnidadMedida;
    public  List<Presentacion> presentacion = new ArrayList<>();
    public ArrayAdapter<UnidadMedida> adapterUnidad;
    public  List<UnidadMedida> unidad = new ArrayList<>();
    public static DatabaseManager databaseManager;
    private AutoCompleteTextView actvCasaComercial;
    private CheckBox cbNd,cbPr,cbIs,cbIN;
    private EditText etPrecioRecoleccion1;
    private FloatingActionButton fabGuardar;
    private AwesomeValidation mAwesomeValidation;
    private Recoleccion recoleccion;
    private Integer LISTA_OBSERVACIONES = 12;
    private ObservacionElem currentObservacion;
    private FuenteArticulo factor;
    private CasaComercial currentCasaComercial ;


    @Override
    protected  void  onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elemento_nuevo_recoleccion);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras.getSerializable("fuente") != null) {
                fuente = (Fuente) extras.getSerializable("fuente");
                factor = (FuenteArticulo)extras.getSerializable("factor");
            }
        } else {
            fuente = (Fuente) savedInstanceState.getSerializable("fuente");
            factor = (FuenteArticulo) savedInstanceState.getSerializable("factor");
        }


        // tvCasaComercial = (TextView) findViewById(R.id.tvCasaComercial);
        etArticulo = (EditText) findViewById(R.id.etArticulo);
        etIca = (EditText) findViewById(R.id.etIca);
        spPresentacion = (Spinner) findViewById(R.id.spPresentacion);
        spUnidad = (Spinner) findViewById(R.id.spUnidad);
        actvCasaComercial = (AutoCompleteTextView) findViewById(R.id.actvCasaComercial);
        cbNd = (CheckBox) findViewById(R.id.cbNd);
        cbPr = (CheckBox) findViewById(R.id.cbPr);
        cbIs = (CheckBox) findViewById(R.id.cbIs);
        cbIN = (CheckBox) findViewById(R.id.cbIN);
        etPrecioRecoleccion1 = (EditText) findViewById(R.id.etPrecioRecoleccion1);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);


        databaseManager = DatabaseManager.getInstance(this);

        presentacion = databaseManager.listaPresentacionArticulo();
        adapterUnidadMedida = new ArrayAdapter<Presentacion>(this, R.layout.spinner_black_item, presentacion);
        adapterUnidadMedida.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPresentacion.setAdapter(adapterUnidadMedida);

        List<CasaComercial> casaComercial = databaseManager.listaCasaComercial();


        ArrayAdapter<CasaComercial> adapter = new ArrayAdapter<CasaComercial>
                (this,android.R.layout.simple_list_item_1,casaComercial);
        actvCasaComercial.setAdapter(adapter);

        spPresentacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unidad = databaseManager.listaUnidadMedidaByPresentacionId(((Presentacion)adapterView.getSelectedItem()).getTiprId());
                adapterUnidad = new ArrayAdapter<UnidadMedida>(view.getContext(), R.layout.spinner_black_item, unidad);
                adapterUnidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spUnidad.setAdapter(adapterUnidad);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        toolbar.setTitle(fuente.getFuenNombre());
        toolbar.setSubtitle(factor.getTireNombre());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        cbIN.setEnabled(false);
        cbIN.setChecked(true);
        cbIs.setEnabled(false);
        cbNd.setEnabled(false);
        cbPr.setEnabled(false);

        actvCasaComercial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                if (item instanceof CasaComercial){
                    currentCasaComercial=(CasaComercial) item;
                }
            }
        });


        etPrecioRecoleccion1.addTextChangedListener(new MoneyTextWatcher(etPrecioRecoleccion1, 4));

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
                mAwesomeValidation.setContext(RecoleccionNuevoArticuloActivity.this);
                mAwesomeValidation.addValidation(RecoleccionNuevoArticuloActivity.this, R.id.etPrecioRecoleccion1, ".+", R.string.invalido);
                mAwesomeValidation.addValidation(RecoleccionNuevoArticuloActivity.this, R.id.etArticulo, ".+", R.string.invalido);
                mAwesomeValidation.addValidation(RecoleccionNuevoArticuloActivity.this, R.id.etIca, ".+", R.string.invalido);

                if (mAwesomeValidation.validate()) {

                    if(currentCasaComercial==null){
                        Toast.makeText(getApplicationContext(),"Por favor seleccione una casa comercial valida",Toast.LENGTH_LONG).show();
                        return;
                    }
                    recoleccion = new Recoleccion();

                    if(factor !=null){
                        recoleccion.setTireId(factor.getTireId());
                        recoleccion.setPrreFechaProgramada(factor.getPrreFechaProgramada());
                    } else{
                        recoleccion.setPrreFechaProgramada(new Date());
                    }


                    recoleccion.setArtiNombre(etArticulo.getText().toString());
                    recoleccion.setCacoNombre(currentCasaComercial.getCacoNombre());
                    recoleccion.setCacoId(currentCasaComercial.getCacoId());
                    recoleccion.setArticacoRegicaLinea(etIca.getText().toString());

                    recoleccion.setUnmeId(((UnidadMedida) spUnidad.getSelectedItem()).getUnmeId());
                    recoleccion.setUnmeNombrePpal(((UnidadMedida) spUnidad.getSelectedItem()).getUnmeNombrePpal());
                    recoleccion.setUnmeCantidadPpal(((UnidadMedida) spUnidad.getSelectedItem()).getUnmeCantidadPpal());
                    recoleccion.setUnmeNombre2(((UnidadMedida) spUnidad.getSelectedItem()).getUnmeNombre2());
                    recoleccion.setUnmeCantidad2(((UnidadMedida) spUnidad.getSelectedItem()).getUnmeCantidad2());

                    //@TO-DO mostrar todos los grupos en la nueva recoleccion ->     recoleccion.setGrupNombre(sp);


                    Articulo articulo = new Articulo();
                    Long articacoId = databaseManager.save(articulo);
                    articulo.setArtiNombre(etArticulo.getText().toString());
                    articulo.setCacoNombre(currentCasaComercial.getCacoNombre());
                    articulo.setArtiId(articacoId);
                    articulo.setCacoId(currentCasaComercial.getCacoId());
                    articulo.setArticacoRegicaLinea(etIca.getText().toString());

                    if(factor !=null){
                        articulo.setTireId(factor.getTireId());
                        articulo.setTireNombre(factor.getTireNombre());
                    }
                    databaseManager.save(articulo);

                    recoleccion.setArtiId(articulo.getArtiId());
                    recoleccion.setArticacoId(articulo.getArticacoId());
                    recoleccion.setCacoNombre(articulo.getCacoNombre());
                    recoleccion.setFechaRecoleccion(new Date());


                    recoleccion.setFuenId(fuente.getFuenId());
                    recoleccion.setFuenNombre(fuente.getFuenNombre());
                    recoleccion.setMuniId(fuente.getMuniId());
                    recoleccion.setTiprId(((Presentacion) spPresentacion.getSelectedItem()).getTiprId());
                    List<FuenteArticulo> fuenteArticulo =null;

                    fuenteArticulo = databaseManager.obtenerFuenteArticulo(fuente.getFuenId(),factor.getTireId());

                    if (fuenteArticulo != null && !fuenteArticulo.isEmpty())
                        recoleccion.setFutiId(fuenteArticulo.get(0).getFutiId());
                    recoleccion.setNovedad("IN");
                    recoleccion.setPrecio(Double.parseDouble(etPrecioRecoleccion1.getText().toString().replaceAll(",", "")));

                    recoleccion.setTiprNombre(((Presentacion) spPresentacion.getSelectedItem()).getTiprNombre());

                    long nuevo = 22;
                    recoleccion.setObservacion("Insumo Nuevo");
                    recoleccion.setIdObservacion(nuevo);
                    recoleccion.setEstadoRecoleccion(true);
                    databaseManager.save(recoleccion);
                    Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        });

    }

    @Override
    protected void onPostExecute(Status result) {

    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {

        if (requestCode == LISTA_OBSERVACIONES) {
            if (resultCode == RESULT_OK) {
                boolean saveOnResult = data.getBooleanExtra("saveOnResult", false);
                currentObservacion = (ObservacionElem) data.getSerializableExtra("observacion");
                recoleccion.setObservacion(currentObservacion.getObseDescripcion());
                recoleccion.setIdObservacion(currentObservacion.getObseId());
                if (saveOnResult) {
                    recoleccion.setEstadoRecoleccion(true);
                    databaseManager.save(recoleccion);
                    Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
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
        }
        return super.onOptionsItemSelected(item);
    }



}
