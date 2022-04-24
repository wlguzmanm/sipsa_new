package gov.dane.sipsa.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.github.clans.fab.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import gov.dane.sipsa.R;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.ArtiCaraValoresI01;
import gov.dane.sipsa.dao.Articulo;
import gov.dane.sipsa.dao.ArticuloI01;
import gov.dane.sipsa.dao.CaracteristicaI01;
import gov.dane.sipsa.dao.CasaComercial;
import gov.dane.sipsa.dao.EnvioArtiCaraValoresI01;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.FuenteTireI01;
import gov.dane.sipsa.dao.GrupoInsumoI01;
import gov.dane.sipsa.dao.InformadorI01;
import gov.dane.sipsa.dao.PrincipalI01;
import gov.dane.sipsa.dao.Recoleccion;
import gov.dane.sipsa.dao.RecoleccionI01;
import gov.dane.sipsa.dao.UnidadMedida;
import gov.dane.sipsa.dao.ValcarapermitidosI01;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.model.Elemento;
import gov.dane.sipsa.model.FactorI01;
import gov.dane.sipsa.model.Municipio;
import gov.dane.sipsa.model.NovedadRecoleccion;
import gov.dane.sipsa.model.ObservacionElem;
import gov.dane.sipsa.model.Presentacion;
import gov.dane.sipsa.model.Status;
import gov.dane.sipsa.utils.MoneyTextWatcher;
import gov.dane.sipsa.utils.Util;


/**
 * Created by hdblanco on 4/07/17.
 */

public class RecoleccionNuevoArticuloActivity01 extends App {

    public Elemento elemento;
    public Fuente fuente;
    public CaracteristicaI01 caracteristica;
    public ArticuloI01 articuloI01;
    private RecoleccionI01 recoleccion;
    private TextView tvCasaComercial;
    private EditText etArticulo,etIca;
    private Spinner spArticulo;
    private Spinner spUnidad;
    public ArrayAdapter<ArticuloI01> adapterArticulo;
    public  List<ArticuloI01> articulos = new ArrayList<>();
    public ArrayAdapter<UnidadMedida> adapterUnidad;
    public  List<CaracteristicaI01> caracteristicas = new ArrayList<>();
    public  List<ValcarapermitidosI01> valCara = new ArrayList<>();
    public  List<UnidadMedida> unidad = new ArrayList<>();
    public static DatabaseManager databaseManager;
    private AutoCompleteTextView actvCasaComercial;
    private CheckBox cbNd,cbPr,cbIs,cbIN;
    private FloatingActionButton fabGuardar;
    private AwesomeValidation mAwesomeValidation;
    private Integer LISTA_OBSERVACIONES = 12;
    private ObservacionElem currentObservacion;
    private NovedadRecoleccion novedadRecoleccion;
    private FuenteArticulo factor;
    private CasaComercial currentCasaComercial ;
    private Municipio currentMunicipio = new Municipio();
    private FactorI01 currentFactorI01 = new FactorI01();
    private TextView tvArticulo;
    private AutoCompleteTextView actvInformante;
    private EditText etTelefonoInformante;
    private EditText etPrecioRecoleccion;
    private LinearLayout llCaracteristicas;
    private InformadorI01 currentInformante;
    private ArticuloI01 currentArticulo;
    public List<InformadorI01> informadorI01 =new ArrayList<>();
    private String currentTelefono;
    private String currentNombre;
    private Long currentFutiId;
    private FuenteTireI01 fuenteTire;
    public PrincipalI01 principal;
    private Date fechaProgramada;


    @Override
    protected  void  onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elemento_nuevo_recoleccion01);

        tvArticulo = (TextView) findViewById(R.id.tvArticulo);
        actvInformante = (AutoCompleteTextView) findViewById(R.id.actvInformante);
        etTelefonoInformante = (EditText) findViewById(R.id.etTelefonoInformante);
        spArticulo = (Spinner) findViewById(R.id.spArticulo);

        currentTelefono="";
        currentNombre="";
        currentFutiId=0l;

        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);
        llCaracteristicas =(LinearLayout) findViewById(R.id.llCaracteristicas);
        currentInformante = new InformadorI01();
        principal = new PrincipalI01();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras.getSerializable("Municipio") != null) {
                currentMunicipio = (Municipio) extras.getSerializable("Municipio");
            }
            if (extras.getSerializable("FactorI01") != null) {
                currentFactorI01 = (FactorI01) extras.getSerializable("FactorI01");
            }
            if (extras.getSerializable("FuenTire")!=null){
                fuenteTire = (FuenteTireI01) extras.getSerializable("FuenTire");
            }
            if (extras.getSerializable("Novedad")!=null){
                novedadRecoleccion = (NovedadRecoleccion) extras.getSerializable("Novedad");
            }
            /*if (extras.getSerializable("FechaProgramada") !=null){
                fechaProgramada = (Date) this.getIntent().getExtras().get("FechaProgramada");
            }*/

        }else {
            currentMunicipio = (Municipio) savedInstanceState.getSerializable("Municipio");
            currentFactorI01 = (FactorI01) savedInstanceState.getSerializable("FactorI01");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        toolbar.setTitle(currentMunicipio.getMuniNombre());
        toolbar.setSubtitle(currentFactorI01.getTireNombre());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cbNd = (CheckBox) findViewById(R.id.cbNd);
        cbPr = (CheckBox) findViewById(R.id.cbPr);
        cbIN = (CheckBox) findViewById(R.id.cbIN);
        cbIs = (CheckBox) findViewById(R.id.cbIs);

        tvCasaComercial = (TextView) findViewById(R.id.tvCasaComercial);
        etArticulo = (EditText) findViewById(R.id.etArticulo);
        etIca = (EditText) findViewById(R.id.etIca);

        spUnidad = (Spinner) findViewById(R.id.spUnidad);
        actvCasaComercial = (AutoCompleteTextView) findViewById(R.id.actvCasaComercial);
        etPrecioRecoleccion = (EditText) findViewById(R.id.etPrecioRecoleccion);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);

        RecoleccionNuevoArticuloActivity01 activity = new RecoleccionNuevoArticuloActivity01() {
            @Override
            protected void onPostExecute(Status result) {

            }
        };
        databaseManager = DatabaseManager.getInstance(activity.getParent());

        databaseManager = DatabaseManager.getInstance(this);


        informadorI01 = databaseManager.listaInformadorI01(currentMunicipio.getMuniId());
        final List<InformadorI01> informadorI01 = databaseManager.listaInformadorI01(currentMunicipio.getMuniId());
        ArrayAdapter<InformadorI01> adapter = new ArrayAdapter<InformadorI01>(this,android.R.layout.simple_list_item_1,informadorI01);
        actvInformante.setAdapter(adapter);

        actvInformante.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                if (item instanceof InformadorI01){
                    currentInformante=(InformadorI01) item;
                    currentNombre = currentInformante.getInfoNombre().toString();
                    currentTelefono= currentInformante.getInfoTelefono().toString();
                    etTelefonoInformante.setText(currentInformante.getInfoTelefono().toString());
                }
            }
        });

        articulos = databaseManager.listaArticuloI01(currentFactorI01.getTireId());
        adapterArticulo = new ArrayAdapter<ArticuloI01>(this, R.layout.spinner_black_item, articulos);
        adapterArticulo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spArticulo.setAdapter(adapterArticulo);

        spArticulo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                currentArticulo = (ArticuloI01) parentView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        caracteristicas = databaseManager.listaCarateristicaI01(currentFactorI01.getTireId());


        if(caracteristicas!=null) {

            for(CaracteristicaI01 c : caracteristicas){
                valCara = databaseManager.listaValCaraI01(currentFactorI01.getTireId(),c.getCaraId());
                TextView tvDescripcion = new TextView(getApplicationContext());
                tvDescripcion.setText(c.getCaraDescripcion());
                tvDescripcion.setTextSize(10);
                tvDescripcion.setTextColor(Color.GRAY);
                llCaracteristicas.addView(tvDescripcion);
                Spinner spListaCara = new Spinner(getApplicationContext());
                spListaCara.setTag(c.getCaraId());
                ArrayAdapter<ValcarapermitidosI01> valCaraArrayAdapter = new ArrayAdapter<ValcarapermitidosI01>(getApplicationContext(),
                        R.layout.spinner_black_item, valCara);
                valCaraArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spListaCara.setAdapter(valCaraArrayAdapter);
                llCaracteristicas.addView(spListaCara);
                spListaCara.setVisibility(View.VISIBLE);
            }

        }

        etPrecioRecoleccion.setText("0.0");
        etPrecioRecoleccion.addTextChangedListener(new MoneyTextWatcher(etPrecioRecoleccion, 4));

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
                mAwesomeValidation.setContext(RecoleccionNuevoArticuloActivity01.this);
                mAwesomeValidation.addValidation(RecoleccionNuevoArticuloActivity01.this, R.id.actvInformante, ".+" , R.string.invalido);
                mAwesomeValidation.addValidation(RecoleccionNuevoArticuloActivity01.this, R.id.etTelefonoInformante, ".+" , R.string.invalido);
                mAwesomeValidation.addValidation(RecoleccionNuevoArticuloActivity01.this, R.id.etPrecioRecoleccion, ".+", R.string.invalido);

                Double precioRecolectado = Double.parseDouble(etPrecioRecoleccion.getText().toString().replaceAll(",", ""));
                if (precioRecolectado==null){
                    precioRecolectado=0.0;
                }

                if (precioRecolectado!=null && precioRecolectado < 1.0) {
                    Snackbar.make(findViewById(android.R.id.content),
                            " El precio Recolectado debe ser mayor a 0.0 (Cero)", Snackbar.LENGTH_LONG)
                            .show();
                }else {

                    if (mAwesomeValidation.validate()) {

                        recoleccion = new RecoleccionI01();
                        Double precioAnterior = 0.0;

                        if (currentArticulo != null) {

                            recoleccion.setArtiId(currentArticulo.getArtiId());

                        }

                        if (currentNombre.equals("")) {
                            currentInformante.setInfoNombre(actvInformante.getText().toString());
                            currentInformante.setInfoTelefono(etTelefonoInformante.getText().toString());
                        }

                        if (currentNombre.equals(actvInformante.getText().toString())) {
                            recoleccion.setInfoNombre(currentInformante.getInfoNombre());
                            recoleccion.setInfoId(currentInformante.getInfoId());
                        } else {
                            recoleccion.setInfoNombre(currentInformante.getInfoNombre());
                            currentNombre = "";
                        }

                        if (currentTelefono.equals("") || !currentTelefono.equals(etTelefonoInformante.getText().toString())) {
                            currentInformante.setInfoTelefono(etTelefonoInformante.getText().toString());
                            currentTelefono = "";
                        }

                        currentInformante.setMuniId(currentMunicipio.getMuniId());
                        recoleccion.setInfoTelefono(etTelefonoInformante.getText().toString());
                        recoleccion.setFechaRecoleccion(new Date());
                        recoleccion.setFechaProgramada(fuenteTire.getPrreFechaProgramada());
                        recoleccion.setNovedad("IN");
                        recoleccion.setFutiId(fuenteTire.getFutiId());

                        principal.setArtiId(currentArticulo.getArtiId());
                        principal.setFutiId(fuenteTire.getFutiId());
                        principal.setTireId(currentArticulo.getTireId());
                        principal.setNombreComplemento(currentArticulo.getNombreCompleto());
                        principal.setPromAnterior(0.0);
                        principal.setArtiNombre(currentArticulo.getArtiNombre());
                        principal.setFuenId(fuenteTire.getFuenId());
                        principal.setFuenNombre(currentMunicipio.getMuniNombre());
                        principal.setPrreFechaProgramada(fuenteTire.getPrreFechaProgramada());
                        principal.setFuentesCapturadas(1);
                        principal.setMuniId(currentMunicipio.getMuniId());

                        recoleccion.setPrecioActual(Double.parseDouble(etPrecioRecoleccion.getText().toString().replaceAll(",", "")));
                        recoleccion.setPrecioAnterior(0.0);
                        recoleccion.setDesviacion("0.0");

                        long nuevo = 22;
                        recoleccion.setObservaciones("Insumo Nuevo");
                        recoleccion.setObservacionId(nuevo);

                        Long grin2Id = saveCaracteristicas();
                        recoleccion.setGrin2Id(grin2Id);
                        principal.setGrin2Id(grin2Id);
                        databaseManager.save(principal);
                        databaseManager.save(recoleccion);
                        Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });

    }

    @Override
    protected void onPostExecute(Status result) {

    }

    public HashMap calcularResumen(Double variacionPorcentual,Double precioRecolectado) {

        HashMap result = new HashMap();
        String titulo = "";
        String mensaje = "";
        String tipoNovedad = "";

        DecimalFormat formatCantidad = new DecimalFormat("#.###");
        DecimalFormat formatPrecio = new DecimalFormat("#.##");

        mensaje = "Precio  Recolectado:  " + Util.formatMoney(precioRecolectado);
        mensaje +="\n---------------------------------\n" ;


        if (variacionPorcentual < 0) {
            titulo = "Variación negativa: " + formatPrecio.format(variacionPorcentual) + "% ";
            tipoNovedad = "B";
        } else if (variacionPorcentual > 0) {
            titulo = "Variación positiva: " + formatPrecio.format(variacionPorcentual) + "% ";
            tipoNovedad = "A";
        } else {
            titulo = "Variación en el Rango";
            tipoNovedad = "P";
        }

        result.put("titulo", titulo);
        result.put("mensaje", mensaje);
        result.put("tipoNovedad", tipoNovedad);
        return result;
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {

        if (requestCode == LISTA_OBSERVACIONES) {
            if (resultCode == RESULT_OK) {
                boolean saveOnResult = data.getBooleanExtra("saveOnResult", false);
                currentObservacion = (ObservacionElem) data.getSerializableExtra("observacion");
                recoleccion.setObservaciones(currentObservacion.getObseDescripcion());
                recoleccion.setObservacionId(currentObservacion.getObseId());
                if (saveOnResult) {
                    if ((currentNombre.equals("") || currentTelefono.equals(""))) {
                        databaseManager.save(currentInformante);
                        recoleccion.setInfoId(currentInformante.getInfoId());
                        recoleccion.setInfoNombre(currentInformante.getInfoNombre());
                    }
                    Long grin2Id = saveCaracteristicas();
                    recoleccion.setGrin2Id(grin2Id);
                    principal.setGrin2Id(grin2Id);
                    databaseManager.save(principal);
                    databaseManager.save(recoleccion);
                    Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private long saveCaracteristicas() {
        GrupoInsumoI01 grin2Id = new GrupoInsumoI01() ;
        // TODO :
        // grind2Id = databamanager.save(grupoInsumo).getId();
        List list = Util.getAllViews(llCaracteristicas.getRootView());
        Integer m=0;
        Integer k =0;
        String nombreCompleto ="";
        Long grupoInsumo = databaseManager.save(grin2Id);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof Spinner) {
                m=i;
                Spinner sp = ( (Spinner) list.get(i));
                Boolean arti = false;

                for (int j = 0; j < caracteristicas.size(); j++) {
                    if (caracteristicas.get(j).getCaraId().equals(sp.getTag())) {
                        ArtiCaraValoresI01 artiCaraValoresI01 = new ArtiCaraValoresI01();
                        ValcarapermitidosI01 valorCaraSeleccionado = new ValcarapermitidosI01();
                        valorCaraSeleccionado = ( (ValcarapermitidosI01) sp.getSelectedItem());
                        artiCaraValoresI01.setVapeId( valorCaraSeleccionado.getVapeId());
                        artiCaraValoresI01.setVapeDescripcion( valorCaraSeleccionado.getVapeDescripcion());
                        artiCaraValoresI01.setCaraId(caracteristicas.get(j).getCaraId());
                        artiCaraValoresI01.setGrin2Id(grupoInsumo);
                        artiCaraValoresI01.setArtiId(currentArticulo.getArtiId());
                        artiCaraValoresI01.setTireId(currentFactorI01.getTireId());
                        if (k==0){
                            nombreCompleto = ""+(currentArticulo.getArtiNombre().toString());
                        }
                        nombreCompleto =""+(nombreCompleto.toString()+"-"+valorCaraSeleccionado.getVapeDescripcion().toString());
                        k=j;
                        databaseManager.save(artiCaraValoresI01);

                    }
                }

            }
        }

        if (k>0 && m>0){
            ArticuloI01 articuloI01 = new ArticuloI01();
            articuloI01.setArtiId(currentArticulo.getArtiId());
            articuloI01.setGrin2Id(grupoInsumo);
            articuloI01.setNombreCompleto(nombreCompleto);
            articuloI01.setArtiNombre(currentArticulo.getArtiNombre());
            articuloI01.setTireId(currentArticulo.getTireId());
            databaseManager.save(articuloI01);
        }

        return grupoInsumo;
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
