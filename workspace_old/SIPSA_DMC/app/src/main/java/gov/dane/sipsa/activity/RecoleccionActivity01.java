package gov.dane.sipsa.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import android.support.design.widget.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import gov.dane.sipsa.R;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.ArticuloI01;
import gov.dane.sipsa.dao.CaracteristicaI01;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.InformadorI01;
import gov.dane.sipsa.dao.Principal;
import gov.dane.sipsa.dao.PrincipalI01;
import gov.dane.sipsa.dao.RecoleccionI01;
import gov.dane.sipsa.dao.UnidadMedida;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.model.Elemento01;
import gov.dane.sipsa.model.NovedadRecoleccion;
import gov.dane.sipsa.model.ObservacionElem;
import gov.dane.sipsa.model.Presentacion;
import gov.dane.sipsa.model.RecoleccionPrincipal;
import gov.dane.sipsa.model.Resumen01;
import gov.dane.sipsa.model.Status;
import gov.dane.sipsa.model.ValorCaracteristica;
import gov.dane.sipsa.utils.MoneyTextWatcher;
import gov.dane.sipsa.utils.Util;


/**
 * Created by hdblanco on 4/07/17.
 */

public class RecoleccionActivity01 extends App {

    private RecoleccionPrincipal recoleccionSeleccionada;
    private List<RecoleccionPrincipal> recolecciones;

    public Elemento01 elemento;
    public Resumen01 resumen;
    public ArticuloI01 elementoAdicionado;
    private TextView tvCodigoArticulo;
    private TextView tvArticulo;
    private AutoCompleteTextView actvInformante;
    private EditText etTelefonoInformante;
    private InformadorI01 currentInformante ;
    public List<ValorCaracteristica> listValorCaracteristica = new ArrayList<>();
    public ArrayAdapter<InformadorI01> adapterInformadorI01;
    public List<InformadorI01> informadorI01 =new ArrayList<>();
    public ArrayAdapter<Presentacion> adapterUnidadMedida;
    public List<Presentacion> presentacion = new ArrayList<>();
    public ArrayAdapter<UnidadMedida> adapterUnidad;
    public List<UnidadMedida> unidad = new ArrayList<>();
    public static DatabaseManager databaseManager;
    private NovedadRecoleccion novedadRecoleccion;
    private CheckBox cbNd, cbPr, cbIA, cbIS, cbIN;
    private EditText c;
    private FloatingActionButton fabGuardar, fabEliminar;
    private AwesomeValidation mAwesomeValidation;
    private Integer LISTA_OBSERVACIONES = 12;
    private RecoleccionI01 recoleccion;
    private LinearLayout llCaracteristicas;
    private EditText etPrecioRecoleccion;
    private String currentMunicipio;
    private String currentMunicipioId;
    private String currentFactorI01;
    private String currentTelefono;
    private String currentNombre;
    private Long currentFutiId;
    private Long currentFuenId;
    public PrincipalI01 principal;
    private PrincipalI01 noFuente;
    private Date fechaProgramada;
    private Long currentInfoId;

    private ObservacionElem currentObservacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoleccion01);

        currentTelefono="";
        currentNombre="";

        tvArticulo = (TextView) findViewById(R.id.tvArticulo);
        actvInformante = (AutoCompleteTextView) findViewById(R.id.actvInformante);
        etTelefonoInformante = (EditText) findViewById(R.id.etTelefonoInformante);

        cbNd = (CheckBox) findViewById(R.id.cbNd);
        cbPr = (CheckBox) findViewById(R.id.cbPr);
        cbIA = (CheckBox) findViewById(R.id.cbIA);
        cbIS = (CheckBox) findViewById(R.id.cbIs);
        cbIN = (CheckBox) findViewById(R.id.cbIN);
        currentInformante = new InformadorI01();
        principal = new PrincipalI01();

        etPrecioRecoleccion = (EditText) findViewById(R.id.etPrecioRecoleccion1);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);
        fabEliminar = (FloatingActionButton) findViewById(R.id.fabEliminar);
        llCaracteristicas =(LinearLayout) findViewById(R.id.llCaracteristicas);
        recoleccion = new RecoleccionI01();

        databaseManager = DatabaseManager.getInstance(this);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras.getSerializable("recoleccion") != null) {
                elemento =(Elemento01) extras.getSerializable("recoleccion");
                if(elemento.getNovedad()==null){
                    novedadRecoleccion = NovedadRecoleccion.PROGRAMADA;
                }else if (elemento.getNovedad().equals("P")){
                    novedadRecoleccion = NovedadRecoleccion.ESTABLE;
                }else if(elemento.getNovedad().equals("IA")){
                    novedadRecoleccion = NovedadRecoleccion.ADICIONADA;
                }else if(elemento.getNovedad().equals("IN")){
                    novedadRecoleccion = NovedadRecoleccion.NUEVO;
                }else if(elemento.getNovedad().equals("B")){
                    novedadRecoleccion = NovedadRecoleccion.BAJA;
                }else if(elemento.getNovedad().equals("A")){
                    novedadRecoleccion = NovedadRecoleccion.ALTA;
                }else if(elemento.getNovedad().equals("PR")){
                    novedadRecoleccion = NovedadRecoleccion.PROMOCION;
                }else if(elemento.getNovedad().equals("ND")){
                    novedadRecoleccion = NovedadRecoleccion.NO_DISPONIBLE;
                }else if(elemento
                        .getNovedad().equals("IS")){
                    novedadRecoleccion = NovedadRecoleccion.INSUMO_SALE;
                }
            }
            if (extras.getSerializable("resumen") != null) {
                resumen = (Resumen01) extras.getSerializable("resumen");
                recoleccion = databaseManager.getRecoleccionI01ById(resumen.getRecoleccionId());
                if(recoleccion.getNovedad()==null){
                    novedadRecoleccion = NovedadRecoleccion.PROGRAMADA;
                }else if (recoleccion.getNovedad().equals("P")){
                    novedadRecoleccion = NovedadRecoleccion.ESTABLE;
                }else if(recoleccion.getNovedad().equals("IA")){
                    novedadRecoleccion = NovedadRecoleccion.ADICIONADA;
                }else if(recoleccion.getNovedad().equals("IN")){
                    novedadRecoleccion = NovedadRecoleccion.NUEVO;
                }else if(recoleccion.getNovedad().equals("B")){
                    novedadRecoleccion = NovedadRecoleccion.BAJA;
                }else if(recoleccion.getNovedad().equals("A")){
                    novedadRecoleccion = NovedadRecoleccion.ALTA;
                }else if(recoleccion.getNovedad().equals("PR")){
                    novedadRecoleccion = NovedadRecoleccion.PROMOCION;
                }else if(recoleccion.getNovedad().equals("ND")){
                    novedadRecoleccion = NovedadRecoleccion.NO_DISPONIBLE;
                }else if(recoleccion.getNovedad().equals("IS")){
                    novedadRecoleccion = NovedadRecoleccion.INSUMO_SALE;
                }
            }
            if(extras.getSerializable("adicionado")!= null){
                elementoAdicionado =(ArticuloI01) extras.getSerializable("adicionado");
            }
            if(extras.getSerializable("novedad") != null && elementoAdicionado != null){
                novedadRecoleccion =(NovedadRecoleccion) extras.getSerializable("novedad");
            }
            if (extras.getSerializable("municipio")!=null){
                currentMunicipio = extras.getString("municipio");
            }
            if (extras.getSerializable("factor")!=null){
                currentFactorI01 = extras.getString("factor");
            }
            if (extras.getSerializable("municipioId") !=null){
                currentMunicipioId = extras.getString("municipioId");
            }
            if (extras.getSerializable("futiId")!=null){
                currentFutiId = extras.getLong("futiId");
            }
            if (extras.getSerializable("FechaProgramada")!=null){
                fechaProgramada = (Date) this.getIntent().getExtras().get("FechaProgramada");
            }
            if (extras.getSerializable("FuenId")!=null){
                currentFuenId = extras.getLong("FuenId");
            }

        } else {
            elemento = (Elemento01) savedInstanceState.getSerializable("recoleccion");
            elementoAdicionado = (ArticuloI01) savedInstanceState.getSerializable("adicionado");
            novedadRecoleccion =(NovedadRecoleccion) savedInstanceState.getSerializable("novedad");
            resumen = (Resumen01) savedInstanceState.getSerializable("resumen");

        }

        if (elemento != null ) {

            informadorI01 = databaseManager.listaInformadorI01ByElementos(elemento.getMuniId(),elemento.getGrupoInsumoId(),elemento.getArtiId());
            final List<InformadorI01> informadorI01 = databaseManager.listaInformadorI01ByElementos(elemento.getMuniId(),elemento.getGrupoInsumoId(),elemento.getArtiId());
            ArrayAdapter<InformadorI01> adapter = new ArrayAdapter<InformadorI01>(this,android.R.layout.simple_list_item_1,informadorI01);
            actvInformante.setAdapter(adapter);
            currentFactorI01=elemento.getTireNombre();
            currentMunicipio=elemento.getMuniNombre();


        }else if (resumen != null) {
            actvInformante.setEnabled(false);
            etTelefonoInformante.setEnabled(false);
            etTelefonoInformante.setText(resumen.getInfoTelefono());
            actvInformante.setText(resumen.getInfoNombre());
            currentFactorI01=resumen.getTireNombre();
            currentMunicipio=resumen.getMuniNombre();


        }else if (elementoAdicionado != null && currentMunicipioId != null){

            informadorI01 = databaseManager.listaInformadorI01(currentMunicipioId);
            final List<InformadorI01> informadorI01 = databaseManager.listaInformadorI01(currentMunicipioId);
            ArrayAdapter<InformadorI01> adapter = new ArrayAdapter<InformadorI01>(this,android.R.layout.simple_list_item_1,informadorI01);
            actvInformante.setAdapter(adapter);


        }

        actvInformante.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                if (item instanceof InformadorI01){
                    currentInformante=(InformadorI01) item;
                    currentInfoId = currentInformante.getInfoId();
                    currentNombre = currentInformante.getInfoNombre().toString();
                    if (currentInformante.getInfoTelefono() != null){
                        currentTelefono= currentInformante.getInfoTelefono().toString();
                        etTelefonoInformante.setText(currentInformante.getInfoTelefono().toString());
                    }

                }
            }
        });


        if (elemento != null){

            if (novedadRecoleccion.equals(NovedadRecoleccion.ADICIONADA)) {
                cbIA.setEnabled(false);
                cbIA.setChecked(true);
                cbIN.setEnabled(false);
                cbNd.setEnabled(false);
                cbPr.setEnabled(false);
                cbIS.setEnabled(false);

            }else if (novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)) {
                cbIA.setEnabled(false);
                cbIN.setEnabled(false);
                cbIN.setChecked(true);
                cbNd.setEnabled(false);
                cbPr.setEnabled(false);
                cbIS.setEnabled(false);

            }else {
                cbIA.setVisibility(View.GONE);
                cbIN.setVisibility(View.GONE);
                fabEliminar.setEnabled(false);
            }
        }


        if (elementoAdicionado != null || resumen != null) {

            if (novedadRecoleccion.equals(NovedadRecoleccion.ADICIONADA)) {
                cbIA.setEnabled(false);
                cbIA.setChecked(true);
                cbIN.setEnabled(false);
                cbNd.setEnabled(false);
                cbPr.setEnabled(false);
                cbIS.setEnabled(false);

            } else if (novedadRecoleccion.equals(NovedadRecoleccion.PROGRAMADA) || novedadRecoleccion.equals(NovedadRecoleccion.ALTA) || novedadRecoleccion.equals(NovedadRecoleccion.BAJA) || novedadRecoleccion.equals(NovedadRecoleccion.ESTABLE)) {
                cbIA.setVisibility(View.GONE);
                cbIN.setVisibility(View.GONE);
                fabEliminar.setEnabled(false);

            } else if (novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)) {
                cbIA.setEnabled(false);
                cbIN.setEnabled(false);
                cbIN.setChecked(true);
                cbNd.setEnabled(false);
                cbPr.setEnabled(false);
                cbIS.setEnabled(false);

            } else if (novedadRecoleccion.equals(NovedadRecoleccion.INSUMO_SALE)) {
                cbIA.setVisibility(View.GONE);
                cbIN.setVisibility(View.GONE);
                fabEliminar.setEnabled(false);
                cbIS.setChecked(true);
                etPrecioRecoleccion.setEnabled(false);

            } else if (novedadRecoleccion.equals(NovedadRecoleccion.NO_DISPONIBLE)) {
                cbIA.setVisibility(View.GONE);
                cbIN.setVisibility(View.GONE);
                fabEliminar.setEnabled(false);
                cbNd.setChecked(true);
                etPrecioRecoleccion.setEnabled(false);

            } else if (novedadRecoleccion.equals(NovedadRecoleccion.PROMOCION)) {
                cbIA.setVisibility(View.GONE);
                cbIN.setVisibility(View.GONE);
                fabEliminar.setEnabled(false);
                cbPr.setChecked(true);
            }
        } else if (elemento == null){
            cbIA.setVisibility(View.GONE);
            cbIN.setVisibility(View.GONE);
            fabEliminar.setEnabled(false);
        }


        cbIS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cbNd.setChecked(false);
                    cbNd.setEnabled(false);
                    cbPr.setChecked(false);
                    cbPr.setEnabled(false);
                    etPrecioRecoleccion.setText("0.0");
                    etPrecioRecoleccion.setEnabled(false);
                }else{
                    etPrecioRecoleccion.setEnabled(true);
                    cbNd.setEnabled(true);
                    cbPr.setEnabled(true);
                }

            }
        });

        cbNd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cbIS.setChecked(false);
                    cbIS.setEnabled(false);
                    cbPr.setChecked(false);
                    cbPr.setEnabled(false);
                    etPrecioRecoleccion.setText("0.0");
                    etPrecioRecoleccion.setEnabled(false);
                }else{
                    etPrecioRecoleccion.setEnabled(true);
                    cbIS.setEnabled(true);
                    cbPr.setEnabled(true);
                }

            }
        });


        cbPr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cbNd.setChecked(false);
                    cbNd.setEnabled(false);
                    cbIS.setChecked(false);
                    cbIS.setEnabled(false);
                    etPrecioRecoleccion.setEnabled(true);
                }else {
                    cbIS.setEnabled(true);
                    cbNd.setEnabled(true);


                }

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);

        if (elementoAdicionado != null){

            toolbar.setTitle(currentMunicipio);
            toolbar.setSubtitle(currentFactorI01);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            tvArticulo.setText(elementoAdicionado.getArtiNombre());

            listValorCaracteristica = databaseManager.listaValorCaracteristicaAdd(elementoAdicionado.getTireId(), elementoAdicionado.getGrin2Id(), elementoAdicionado.getArtiId(),currentMunicipioId);
        }

        if(elemento!=null) {

            toolbar.setTitle(elemento.getMuniNombre());
            toolbar.setSubtitle(elemento.getTireNombre());
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            tvArticulo.setText(elemento.getArtiNombre());

            listValorCaracteristica = databaseManager.listaValorCaracteristica(elemento.getTireId(), elemento.getGrupoInsumoId(), elemento.getArtiId(), elemento.getMuniId());


        } else if (resumen!=null) {

            toolbar.setTitle(resumen.getMuniNombre());
            toolbar.setSubtitle(resumen.getTireNombre());
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            tvArticulo.setText(resumen.getArtiNombre());
            etPrecioRecoleccion.setText(resumen.getPrecioActual().toString());

            listValorCaracteristica = databaseManager.listaValorCaracteristica(resumen.getTireId(), resumen.getGrupoInsumoId(), resumen.getArtiId(), resumen.getMuniId());

        }

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 125, getResources().getDisplayMetrics());


        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                width,
                ActionBar.LayoutParams.WRAP_CONTENT
        );

        if (listValorCaracteristica != null) {
            for (ValorCaracteristica vc : listValorCaracteristica) {
                LinearLayout llCaracteristica = new LinearLayout(getApplicationContext());
                llCaracteristica.setOrientation(LinearLayout.HORIZONTAL);
                TextView nombreCaracteristica = new TextView(getApplicationContext());
                nombreCaracteristica.setText(vc.getCaraDescripcion());
                nombreCaracteristica.setLayoutParams(lp);
                //nombreCaracteristica.setTextColor(Color.BLACK);
                TextView descripcionCaracteristica = new TextView(getApplicationContext());
                descripcionCaracteristica.setText(vc.getVapeDescripcion());
                //descripcionCaracteristica.setTextColor(Color.BLACK);
                llCaracteristica.addView(nombreCaracteristica);
                llCaracteristica.addView(descripcionCaracteristica);
                llCaracteristicas.addView(llCaracteristica);
            }
        }

        /*if (elementoAdicionado!=null || elemento!=null) {
            etPrecioRecoleccion.setText("0.0");
        }*/

        etPrecioRecoleccion.addTextChangedListener(new MoneyTextWatcher(etPrecioRecoleccion, 4));

        if (resumen != null) {
            if (novedadRecoleccion.equals(NovedadRecoleccion.ADICIONADA) || novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)) {

                fabEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(RecoleccionActivity01.this)
                                .setTitle("")
                                .setMessage("¿Realmente desea eliminar la Recolección ?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                List<RecoleccionI01> recoleccion = databaseManager.getRecoleccionByRecoId01(resumen.getRecoleccionId());
                                for (RecoleccionI01 r : recoleccion) {
                                    databaseManager.delete(r);
                                }

                                Toast.makeText(getApplicationContext(), "Recolección eliminada exitosamente.", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }).show();
                    }
                });
            }
            else{
                fabEliminar.setEnabled(false);
            }
        }else {
            fabEliminar.setEnabled(false);
        }



        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
                mAwesomeValidation.setContext(RecoleccionActivity01.this);
                mAwesomeValidation.addValidation(RecoleccionActivity01.this, R.id.actvInformante, ".+" , R.string.invalido);
                mAwesomeValidation.addValidation(RecoleccionActivity01.this, R.id.etTelefonoInformante, ".+" , R.string.invalido);
                mAwesomeValidation.addValidation(RecoleccionActivity01.this, R.id.etPrecioRecoleccion1, ".+", R.string.invalido);


                if (cbPr.isChecked()){
                    novedadRecoleccion = NovedadRecoleccion.PROMOCION;
                }else if (cbIS.isChecked()){
                    novedadRecoleccion = NovedadRecoleccion.INSUMO_SALE;
                }else if (cbNd.isChecked()){
                    novedadRecoleccion = NovedadRecoleccion.NO_DISPONIBLE;
                }else if (novedadRecoleccion != NovedadRecoleccion.ADICIONADA && novedadRecoleccion != NovedadRecoleccion.NUEVO){
                    novedadRecoleccion = NovedadRecoleccion.PROGRAMADA;
                }


                Double precioRecolectado =0.0;
                String obtenerPrecio=etPrecioRecoleccion.getText().toString();

                if (obtenerPrecio.equals("") ){

                    Snackbar.make(findViewById(android.R.id.content),
                            " El precio Recolectado debe ser mayor a 0.0 (Cero)", Snackbar.LENGTH_LONG)
                            .show();

                }else {
                    precioRecolectado = Double.parseDouble(etPrecioRecoleccion.getText().toString().replaceAll(",", ""));

                    if (cbPr.isChecked() && precioRecolectado < 1.0){
                        Snackbar.make(findViewById(android.R.id.content),
                                " El precio Recolectado debe ser mayor a 0.0 (Cero)", Snackbar.LENGTH_LONG)
                                .show();
                    }else if (elementoAdicionado != null && precioRecolectado != null && precioRecolectado < 1.0) {
                        Snackbar.make(findViewById(android.R.id.content),
                                " El precio Recolectado debe ser mayor a 0.0 (Cero)", Snackbar.LENGTH_LONG)
                                .show();
                    } else {
                        if (!cbIS.isChecked() && !cbNd.isChecked() && elemento != null && precioRecolectado != null && precioRecolectado < 1.0) {
                            Snackbar.make(findViewById(android.R.id.content),
                                    " El precio Recolectado debe ser mayor a 0.0 (Cero)", Snackbar.LENGTH_LONG)
                                    .show();
                        } else {
                            if (mAwesomeValidation.validate()) {

                                Double precioAnterior = 0.0;

                                if (elemento != null) {

                                    recoleccion.setGrin2Id(elemento.getGrupoInsumoId());
                                    recoleccion.setFutiId(elemento.getFutiId());
                                    recoleccion.setArtiId(elemento.getArtiId());
                                    recoleccion.setFechaProgramada(elemento.getFechaProgramada());
                                    recoleccion.setPrecioAnterior(elemento.getPrecioAnterior());
                                    recoleccion.setFutiId(elemento.getFutiId());
                                    List<PrincipalI01> f = databaseManager.noFuentes(elemento.getArtiId(), elemento.getGrupoInsumoId(), elemento.getFutiId());
                                    noFuente = f != null ? f.get(0) : null;
                                    Integer a = noFuente.getFuentesCapturadas();
                                    List<PrincipalI01> p = databaseManager.listaPrincipalI01ByArticulo(elemento.getArtiId(), elemento.getGrupoInsumoId(), elemento.getFutiId());
                                    principal = p != null ? p.get(0) : null;
                                    if (noFuente != null && a > 0) {
                                        principal.setFuentesCapturadas(a + 1);
                                    } else {
                                        principal.setFuentesCapturadas(1);
                                    }

                                    currentInformante.setMuniId(elemento.getMuniId());

                                    if (currentInfoId == null || currentNombre.equals("")) {
                                        currentInformante.setInfoNombre(actvInformante.getText().toString());
                                        currentInformante.setInfoTelefono(etTelefonoInformante.getText().toString());
                                    }


                                } else if (elementoAdicionado != null) {


                                    recoleccion.setGrin2Id(elementoAdicionado.getGrin2Id());
                                    recoleccion.setArtiId(elementoAdicionado.getArtiId());
                                    recoleccion.setInfoId(currentInformante.getInfoId());
                                    recoleccion.setInfoNombre(actvInformante.getText().toString());
                                    recoleccion.setPrecioAnterior(0.0);
                                    recoleccion.setFutiId(currentFutiId);
                                    recoleccion.setFechaProgramada(fechaProgramada);
                                    principal.setArtiId(elementoAdicionado.getArtiId());
                                    principal.setFutiId(currentFutiId);
                                    principal.setGrin2Id(elementoAdicionado.getGrin2Id());
                                    principal.setTireId(elementoAdicionado.getTireId());
                                    principal.setNombreComplemento(elementoAdicionado.getNombreCompleto());
                                    principal.setArtiNombre(elementoAdicionado.getArtiNombre());
                                    principal.setPrreFechaProgramada(fechaProgramada);
                                    principal.setFuenNombre(currentMunicipio);
                                    principal.setFuenId(currentFuenId);
                                    principal.setFuentesCapturadas(1);


                                    if (currentMunicipioId != null) {
                                        currentInformante.setMuniId(currentMunicipioId);
                                        principal.setMuniId(currentMunicipioId);
                                    }

                                }


                                if (resumen == null) {
                                    if (currentNombre.equals(actvInformante.getText().toString())) {
                                        recoleccion.setInfoNombre(currentInformante.getInfoNombre());
                                        recoleccion.setInfoId(currentInformante.getInfoId());
                                    } else {
                                        recoleccion.setInfoNombre(actvInformante.getText().toString());
                                        currentInfoId = null;
                                        currentInformante.setInfoNombre(actvInformante.getText().toString());
                                        currentInformante.setInfoId(null);

                                    }

                                    if (currentTelefono.equals("") || !currentTelefono.equals(etTelefonoInformante.getText().toString())) {
                                        currentInformante.setInfoTelefono(etTelefonoInformante.getText().toString());
                                        currentTelefono = "";
                                    }
                                }

                                recoleccion.setInfoTelefono(etTelefonoInformante.getText().toString());
                                recoleccion.setFechaRecoleccion(new Date());

                                List<FuenteArticulo> fuenteArticulo = null;

                                Double desviacion = 0.0;
                                recoleccion.setPrecioActual(precioRecolectado);


                                if (recoleccion.getPrecioAnterior() != null && recoleccion.getPrecioAnterior() != 0.0) {
                                    desviacion = ((precioRecolectado / recoleccion.getPrecioAnterior()) - 1) * 100;
                                }

                                if (fuenteArticulo != null && !fuenteArticulo.isEmpty()) {
                                    recoleccion.setFutiId(fuenteArticulo.get(0).getFutiId());
                                }


                                recoleccion.setPrecioActual(Double.parseDouble(etPrecioRecoleccion.getText().toString().replaceAll(",", "")));
                                recoleccion.setDesviacion(desviacion.toString());

                                String tipoNovedad = "";
                                Double variacionPorcentual = 0.0;
                                String titulo = "";
                                DecimalFormat formatPrecio = new DecimalFormat("#.##");


                                if ( desviacion < -5) {
                                    tipoNovedad = "B";
                                    titulo = "Variación negativa: " + formatPrecio.format(desviacion) + "% ";
                                } else if (desviacion > 5) {
                                    titulo = "Variación positiva: " + formatPrecio.format(desviacion) + "% ";
                                    tipoNovedad = "A";
                                } else if (novedadRecoleccion.equals(NovedadRecoleccion.ADICIONADA)){
                                    titulo = "Articulo Adicionado";
                                    tipoNovedad = "IA";
                                } else if (novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)){
                                    titulo = "Articulo Nuevo";
                                    tipoNovedad = "IN";
                                } else if (novedadRecoleccion.equals(NovedadRecoleccion.PROMOCION)){
                                    titulo = "En Promocion";
                                    tipoNovedad = "PR";
                                } else if (novedadRecoleccion.equals(NovedadRecoleccion.NO_DISPONIBLE)){
                                    titulo = "No Disponible";
                                    tipoNovedad = "ND";
                                }  else if (novedadRecoleccion.equals(NovedadRecoleccion.INSUMO_SALE)){
                                    titulo = "Insumo Sale";
                                    tipoNovedad = "IS";
                                }  else {
                                    titulo = "Variación en el Rango";
                                    tipoNovedad = "P";
                                }


                                //HashMap result = calcularResumen(desviacion, precioRecolectado);
                                Toast.makeText(getApplicationContext(), titulo, Toast.LENGTH_SHORT).show();


                                if (cbPr.isChecked()) {
                                    recoleccion.setNovedad("PR");

                                } else if (cbNd.isChecked()) {
                                    recoleccion.setNovedad("ND");

                                } else if (cbIS.isChecked()) {
                                    recoleccion.setNovedad("IS");

                                } else {
                                    recoleccion.setNovedad(tipoNovedad);
                                    if (elementoAdicionado != null || resumen != null){
                                        if (novedadRecoleccion.equals(NovedadRecoleccion.ADICIONADA)) {
                                            recoleccion.setNovedad("IA");
                                        } else if (novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)) {
                                            recoleccion.setNovedad("IN");
                                        }
                                    }
                                }

                                Intent intent = new Intent(getApplicationContext(), ListaObservacionActivity01.class);
                                intent.putExtra("Factor", currentFactorI01);
                                intent.putExtra("Municipio", currentMunicipio);
                                intent.putExtra("saveOnResult", true);
                                intent.putExtra("tipoNovedad", recoleccion.getNovedad());

                                if (tipoNovedad != "P" && tipoNovedad!="IA" && tipoNovedad!="IN") {

                                    (RecoleccionActivity01.this).startActivityForResult(intent, LISTA_OBSERVACIONES);

                                } else if (tipoNovedad.equals("P")){
                                    long estable = 20;
                                    recoleccion.setObservaciones("Estable");
                                    recoleccion.setObservacionId(estable);
                                    if ((currentInfoId== null || currentNombre.equals("") || currentTelefono.equals("")) && resumen == null) {
                                        databaseManager.save(currentInformante);
                                        recoleccion.setInfoId(currentInformante.getInfoId());
                                        recoleccion.setInfoNombre(currentInformante.getInfoNombre());
                                    }
                                    if (elementoAdicionado != null || elemento != null) {
                                        databaseManager.save(principal);
                                    }
                                    databaseManager.save(recoleccion);
                                    Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_LONG).show();
                                    finish();
                                } else if (tipoNovedad.equals("IA")){
                                    long adicionado = 21;
                                    recoleccion.setObservaciones("Insumo Adicionado");
                                    recoleccion.setObservacionId(adicionado);
                                    if ((currentInfoId== null || currentNombre.equals("") || currentTelefono.equals("")) && resumen == null) {
                                        databaseManager.save(currentInformante);
                                        recoleccion.setInfoId(currentInformante.getInfoId());
                                        recoleccion.setInfoNombre(currentInformante.getInfoNombre());
                                    }
                                    if (elementoAdicionado != null || elemento != null) {
                                        databaseManager.save(principal);
                                    }
                                    databaseManager.save(recoleccion);
                                    Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                                else if (tipoNovedad.equals("IN")){
                                    long nuevo = 22;
                                    recoleccion.setObservaciones("Insumo Nuevo");
                                    recoleccion.setObservacionId(nuevo);
                                    if ((currentInfoId== null || currentNombre.equals("") || currentTelefono.equals("")) && resumen == null) {
                                        databaseManager.save(currentInformante);
                                        recoleccion.setInfoId(currentInformante.getInfoId());
                                        recoleccion.setInfoNombre(currentInformante.getInfoNombre());
                                    }
                                    if (elementoAdicionado != null || elemento != null) {
                                        databaseManager.save(principal);
                                    }
                                    databaseManager.save(recoleccion);
                                    Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_LONG).show();
                                    finish();
                                }

                            }
                        }
                    }
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
                recoleccion.setObservaciones(currentObservacion.getObseDescripcion());
                recoleccion.setObservacionId(currentObservacion.getObseId());
                if (saveOnResult) {
                    if ((currentInfoId==null || currentNombre.equals("") || currentTelefono.equals(""))&& resumen==null) {
                        databaseManager.save(currentInformante);
                        recoleccion.setInfoId(currentInformante.getInfoId());
                        recoleccion.setInfoNombre(currentInformante.getInfoNombre());
                    }
                    if (elementoAdicionado != null || elemento!=null){
                        databaseManager.save(principal);
                    }
                    databaseManager.save(recoleccion);
                    Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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


        if (variacionPorcentual < -5) {
            titulo = "Variación negativa: " + formatPrecio.format(variacionPorcentual) + "% ";
            tipoNovedad = "B";
        } else if (variacionPorcentual > 5) {
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
