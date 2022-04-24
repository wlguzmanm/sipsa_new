package gov.dane.sipsa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.dane.sipsa.R;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.Articulo;
import gov.dane.sipsa.dao.ArticuloDistrito;
import gov.dane.sipsa.dao.CaracteristicaDistrito;
import gov.dane.sipsa.dao.CasaComercial;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.FuenteArticuloDistrito;
import gov.dane.sipsa.dao.FuenteDistrito;
import gov.dane.sipsa.dao.Recoleccion;
import gov.dane.sipsa.dao.RecoleccionDistrito;
import gov.dane.sipsa.dao.UnidadMedida;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.model.Elemento;
import gov.dane.sipsa.model.ObservacionElem;
import gov.dane.sipsa.model.Presentacion;
import gov.dane.sipsa.model.Status;
import gov.dane.sipsa.utils.MoneyTextWatcher;


/**
 * Created by mguzman on 3/05/18.
 */

public class RecoleccionNuevoArticuloDistritoActivity extends App {

    public Elemento elemento;
    public FuenteDistrito fuente;
    private Spinner spProducto;
    private Spinner spFrecuencia;
    private Spinner spTipo;
    private Spinner spUnidad;
    private LinearLayout layoutOtra;

    public ArrayAdapter<ArticuloDistrito> adapterProducto;
    public  List<ArticuloDistrito> articulo = new ArrayList<>();
    public ArrayAdapter<CaracteristicaDistrito> adapterFrecuencia;
    public  List<CaracteristicaDistrito> frecuencia = new ArrayList<>();
    public ArrayAdapter<CaracteristicaDistrito> adapterTipo;
    public  List<CaracteristicaDistrito> tipo = new ArrayList<>();
    public ArrayAdapter<CaracteristicaDistrito> adapterUnidad;
    public  List<CaracteristicaDistrito> unidad = new ArrayList<>();
    public static DatabaseManager databaseManager;

    private CheckBox cbNd,cbIs,cbIN;
    private EditText tvObservacionNewRecoleccion;
    private EditText tvOtro;
    private EditText etPrecioNewRecoleccion;
    private FloatingActionButton fabGuardar;
    private AwesomeValidation mAwesomeValidation;
    private RecoleccionDistrito recoleccion;
    private Integer LISTA_OBSERVACIONES = 12;
    private ObservacionElem currentObservacion;
    private FuenteArticuloDistrito factor;
    private CasaComercial currentCasaComercial ;

    private Boolean otro = Boolean.FALSE;


    @Override
    protected  void  onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elemento_nuevo_recoleccion_distrito);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras.getSerializable("fuente") != null) {
                fuente = (FuenteDistrito) extras.getSerializable("fuente");
                factor = (FuenteArticuloDistrito) extras.getSerializable("factor");
            }
        } else {
            fuente = (FuenteDistrito) savedInstanceState.getSerializable("fuente");
            factor = (FuenteArticuloDistrito) savedInstanceState.getSerializable("factor");
        }

        spProducto = (Spinner) findViewById(R.id.spProducto);
        spFrecuencia = (Spinner) findViewById(R.id.spFrecuencia);
        spTipo = (Spinner) findViewById(R.id.spTipo);
        spUnidad = (Spinner) findViewById(R.id.spUnidad);
        cbNd = (CheckBox) findViewById(R.id.cbNd);
        cbIs = (CheckBox) findViewById(R.id.cbIs);
        cbIN = (CheckBox) findViewById(R.id.cbIN);
        tvObservacionNewRecoleccion = (EditText) findViewById(R.id.tvObservacionNewRecoleccion);
        tvOtro = (EditText) findViewById(R.id.tvOtro);


        etPrecioNewRecoleccion = (EditText) findViewById(R.id.etPrecioNewRecoleccion);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardarRecoleccionDistrito);
        layoutOtra = (LinearLayout) findViewById(R.id.LYUnidadOtra);
        layoutOtra.setVisibility(View.GONE);

        databaseManager = DatabaseManager.getInstance(this);

        tipo = databaseManager.listaUnidadMedidaByPresentacionIdDistrito(20L);
        adapterTipo = new ArrayAdapter<CaracteristicaDistrito>(this, R.layout.spinner_black_item, tipo);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(adapterTipo);

        frecuencia = databaseManager.listaUnidadMedidaByPresentacionIdDistrito(21L);
        adapterFrecuencia = new ArrayAdapter<CaracteristicaDistrito>(this, R.layout.spinner_black_item, frecuencia);
        adapterFrecuencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFrecuencia.setAdapter(adapterFrecuencia);

        unidad = databaseManager.listaUnidadMedidaByPresentacionIdDistrito(22L);
        CaracteristicaDistrito nuevo = new CaracteristicaDistrito();
        nuevo.setVapeDescripcion("OTRO");
        nuevo.setVapeId(9000000001L);
        unidad.add(nuevo);


        adapterUnidad = new ArrayAdapter<CaracteristicaDistrito>(this, R.layout.spinner_black_item, unidad);
        adapterUnidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUnidad.setAdapter(adapterUnidad);

        articulo = databaseManager.listaArticuloDistrito(13L);
        adapterProducto = new ArrayAdapter<ArticuloDistrito>(this, R.layout.spinner_black_item, articulo);
        adapterProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProducto.setAdapter(adapterProducto);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        toolbar.setTitle(fuente.getFuenNombre());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cbIN.setEnabled(false);
        cbIN.setChecked(true);
        cbIs.setEnabled(false);
        cbNd.setEnabled(false);

        etPrecioNewRecoleccion.addTextChangedListener(new MoneyTextWatcher(etPrecioNewRecoleccion, 4));

       spUnidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = parent.getSelectedItemPosition();
                if(index==8){
                    layoutOtra.setVisibility(View.VISIBLE);
                    otro = Boolean.TRUE;
                }else{
                    layoutOtra.setVisibility(View.GONE);
                    tvOtro.setText("");
                    otro = Boolean.FALSE;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(RecoleccionNuevoArticuloDistritoActivity.this,"Please Select the policy type !!", Toast.LENGTH_LONG).show();
                return;
                // sometimes you need nothing here
            }
        });


        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
                mAwesomeValidation.setContext(RecoleccionNuevoArticuloDistritoActivity.this);
                String TextDept = spTipo.getSelectedItem().toString();

                /*mAwesomeValidation.addValidation(RecoleccionNuevoArticuloDistritoActivity.this, R.id.spTipo, ".+", R.string.invalido);
                mAwesomeValidation.addValidation(RecoleccionNuevoArticuloDistritoActivity.this, R.id.spFrecuencia, ".+", R.string.invalido);
                mAwesomeValidation.addValidation(RecoleccionNuevoArticuloDistritoActivity.this, R.id.spUnidad, ".+", R.string.invalido);*/
                mAwesomeValidation.addValidation(RecoleccionNuevoArticuloDistritoActivity.this, R.id.etPrecioNewRecoleccion, ".+", R.string.invalido);
                mAwesomeValidation.addValidation(RecoleccionNuevoArticuloDistritoActivity.this, R.id.tvObservacionNewRecoleccion, ".+", R.string.invalido);

                if(otro){
                    mAwesomeValidation.addValidation(RecoleccionNuevoArticuloDistritoActivity.this, R.id.tvOtro, ".+", R.string.invalido);
                }

                Boolean error = false;
                if(((CaracteristicaDistrito)spFrecuencia.getSelectedItem()).getCaraId()== null ||
                        ((CaracteristicaDistrito)spTipo.getSelectedItem()).getCaraId()== null ||
                        ((CaracteristicaDistrito)spUnidad.getSelectedItem()).getCaraId()== null){

                    View selectedView =  spFrecuencia.getSelectedView();
                    if (selectedView != null && selectedView instanceof TextView) {
                        TextView selectedTextView = (TextView) selectedView;
                        if (selectedTextView.getText().equals("Seleccione...")) {
                            selectedTextView.setError("Error de select");
                            error = true;
                        }
                    }

                    selectedView =  spTipo.getSelectedView();
                    if (selectedView != null && selectedView instanceof TextView) {
                        TextView selectedTextView = (TextView) selectedView;
                        if (selectedTextView.getText().equals("Seleccione...")) {
                            selectedTextView.setError("Error de select");
                            error = true;
                        }
                    }

                    selectedView =  spUnidad.getSelectedView();
                    if (selectedView != null && selectedView instanceof TextView) {
                        TextView selectedTextView = (TextView) selectedView;
                        if (selectedTextView.getText().equals("Seleccione...")) {
                            selectedTextView.setError("Error de select");
                            error = true;
                        }
                    }
                }

                if (mAwesomeValidation.validate() && !error) {
                    recoleccion = new RecoleccionDistrito();

                    if(factor !=null){
                        recoleccion.setTireId(factor.getTireId());
                    }
                    recoleccion.setUnidadMedidaOtroNombre(tvOtro.getText().toString());
                    recoleccion.setFrecuencia(((CaracteristicaDistrito)spFrecuencia.getSelectedItem()).getVapeDescripcion());
                    recoleccion.setTipo(((CaracteristicaDistrito)spTipo.getSelectedItem()).getVapeDescripcion());
                    recoleccion.setUnmeNombre2(((CaracteristicaDistrito)spUnidad.getSelectedItem()).getVapeDescripcion());
                    recoleccion.setUnmeId(((CaracteristicaDistrito)spUnidad.getSelectedItem()).getVapeId());
                    recoleccion.setFrecuenciaId(((CaracteristicaDistrito)spFrecuencia.getSelectedItem()).getVapeId());
                    recoleccion.setTipoId(((CaracteristicaDistrito)spTipo.getSelectedItem()).getVapeId());
                    recoleccion.setObservacionProducto(tvObservacionNewRecoleccion.getText().toString());
                    recoleccion.setArtiNombre(((ArticuloDistrito) spProducto.getSelectedItem()).getArtiNombre());
                    recoleccion.setArtiId(((ArticuloDistrito) spProducto.getSelectedItem()).getArtiId());

                    /*Articulo articulo = new Articulo();
                    Long articacoId = databaseManager.save(articulo);
                    //articulo.setArtiNombre(etArticuloNewRecoleccion.getText().toString());
                    articulo.setArtiId(articacoId);
                    //articulo.setCacoId(currentCasaComercial.getCacoId());

                    if(factor !=null){
                        articulo.setTireId(factor.getTireId());
                        articulo.setTireNombre(factor.getTireNombre());
                    }
                    databaseManager.save(articulo);*/

                    recoleccion.setFechaRecoleccion(new Date());
                    recoleccion.setPrreFechaProgramada(new Date());
                    recoleccion.setFuenId(fuente.getFuenId());
                    recoleccion.setFuenNombre(fuente.getFuenNombre());
                    recoleccion.setMuniId(fuente.getMuniId());
                    recoleccion.setTireId(13L);

                    List<FuenteArticuloDistrito> fuenteArticulo =null;
                    fuenteArticulo = databaseManager.obtenerFuenteArticuloDistrito(fuente.getFuenId(),13L);

                    if(fuenteArticulo==null){
                        FuenteArticuloDistrito fuenteArt = new FuenteArticuloDistrito();
                        fuenteArt.setFuenId(factor.getFuenId());
                        fuenteArt.setMuniId(factor.getMuniId());
                        fuenteArt.setFutiId(factor.getFutiId());
                        fuenteArt.setMuniNombre(factor.getMuniNombre());
                        fuenteArt.setTireNombre(factor.getTireNombre());
                        fuenteArt.setTireId(factor.getTireId());
                        fuenteArt.setFuenNombre(factor.getFuenNombre());
                        databaseManager.save(fuenteArt);

                        fuenteArticulo = databaseManager.obtenerFuenteArticuloDistrito(factor.getFuenId(),13L);
                    }

                    if (fuenteArticulo != null && !fuenteArticulo.isEmpty())
                        recoleccion.setFutiId(fuenteArticulo.get(0).getFutiId());

                    recoleccion.setNovedad("IN");
                    recoleccion.setPrecio(Double.parseDouble(etPrecioNewRecoleccion.getText().toString().replaceAll(",", "")));

                    long nuevo = 22;
                    recoleccion.setObservacion("Recoleccion Nuevo");
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
