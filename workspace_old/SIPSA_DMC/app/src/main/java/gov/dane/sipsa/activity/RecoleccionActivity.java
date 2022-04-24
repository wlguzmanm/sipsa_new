package gov.dane.sipsa.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
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
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.Articulo;
import gov.dane.sipsa.dao.Recoleccion;
import gov.dane.sipsa.dao.UnidadMedida;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.model.Elemento;
import gov.dane.sipsa.model.NovedadRecoleccion;
import gov.dane.sipsa.model.ObservacionElem;
import gov.dane.sipsa.model.Presentacion;
import gov.dane.sipsa.model.RecoleccionPrincipal;
import gov.dane.sipsa.model.Status;
import gov.dane.sipsa.utils.MoneyTextWatcher;
import gov.dane.sipsa.utils.Util;


/**
 * Created by hdblanco on 4/07/17.
 */

public class RecoleccionActivity extends App {

    private RecoleccionPrincipal recoleccionSeleccionada;
    private List<RecoleccionPrincipal> recolecciones;

    public Elemento elemento;
    public Fuente fuente;
    private FuenteArticulo factor;
    private TextView tvCasaComercial;
    private TextView tvArticulo;
    private TextView tvUnidadMedida;
    private TextView textUnidad;
    private TextView tvRegistroIca;
    private Spinner spPresentacion;
    private Spinner spUnidad;
    public ArrayAdapter<Presentacion> adapterUnidadMedida;
    public List<Presentacion> presentacion = new ArrayList<>();
    public ArrayAdapter<UnidadMedida> adapterUnidad;
    public List<UnidadMedida> unidad = new ArrayList<>();
    public static DatabaseManager databaseManager;
    private NovedadRecoleccion novedadRecoleccion;
    private CheckBox cbNd, cbPr, cbIs, cbIA, cbIN;
    private EditText etPrecioRecoleccion;
    private FloatingActionButton fabGuardar, fabEliminar;
    private AwesomeValidation mAwesomeValidation;
    private Integer LISTA_OBSERVACIONES = 12;
    private Recoleccion recoleccion;
    private String currentFactor;


    private ObservacionElem currentObservacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elemento_recoleccion);
        tvCasaComercial = (TextView) findViewById(R.id.tvCasaComercial);
        tvArticulo = (TextView) findViewById(R.id.tvArticulo);
        spPresentacion = (Spinner) findViewById(R.id.spPresentacion);
        spUnidad = (Spinner) findViewById(R.id.spUnidad);
        tvUnidadMedida = (TextView) findViewById(R.id.tvUnidadMedida);
        textUnidad = (TextView) findViewById(R.id.TextUnidad);
        tvRegistroIca = (TextView) findViewById(R.id.tvRegistroIca);
        cbNd = (CheckBox) findViewById(R.id.cbNd);
        cbPr = (CheckBox) findViewById(R.id.cbPr);
        cbIs = (CheckBox) findViewById(R.id.cbIs);
        cbIA = (CheckBox) findViewById(R.id.cbIA);
        cbIN = (CheckBox) findViewById(R.id.cbIN);
        etPrecioRecoleccion = (EditText) findViewById(R.id.etPrecioRecoleccion1);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);
        fabEliminar = (FloatingActionButton) findViewById(R.id.fabEliminar);

        databaseManager = DatabaseManager.getInstance(this);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras.getSerializable("elemento") != null) {
                elemento = (Elemento) extras.getSerializable("elemento");
                fuente = (Fuente) extras.getSerializable("fuente");
                novedadRecoleccion = (NovedadRecoleccion) extras.getSerializable("novedad");
                factor = (FuenteArticulo) extras.getSerializable("factor");


            }else if(extras.getSerializable("recoleccion")!=null){
                fuente = (Fuente) extras.getSerializable("fuente");
                recoleccionSeleccionada = (RecoleccionPrincipal) extras.getSerializable("recoleccion");
                if(recoleccionSeleccionada.getNovedad()==null){
                    novedadRecoleccion = NovedadRecoleccion.PROGRAMADA;
                }else if (recoleccionSeleccionada.getNovedad().equals("P")){
                    novedadRecoleccion = NovedadRecoleccion.ESTABLE;
                }else if(recoleccionSeleccionada.getNovedad().equals("IA")){
                    novedadRecoleccion = NovedadRecoleccion.ADICIONADA;
                }else if(recoleccionSeleccionada.getNovedad().equals("IN")){
                    novedadRecoleccion = NovedadRecoleccion.NUEVO;
                }else if(recoleccionSeleccionada.getNovedad().equals("B")){
                    novedadRecoleccion = NovedadRecoleccion.BAJA;
                }else if(recoleccionSeleccionada.getNovedad().equals("A")){
                    novedadRecoleccion = NovedadRecoleccion.ALTA;
                }else if(recoleccionSeleccionada.getNovedad().equals("PR")){
                    novedadRecoleccion = NovedadRecoleccion.PROMOCION;
                }else if(recoleccionSeleccionada.getNovedad().equals("ND")){
                    novedadRecoleccion = NovedadRecoleccion.NO_DISPONIBLE;
                }else if(recoleccionSeleccionada.getNovedad().equals("IS")){
                    novedadRecoleccion = NovedadRecoleccion.INSUMO_SALE;
                }
                //factor = (FuenteArticulo) extras.getSerializable("factor");
                currentFactor = (String) extras.getSerializable("factor");
            }
        } else {
            elemento = (Elemento) savedInstanceState.getSerializable("elemento");
            fuente = (Fuente) savedInstanceState.getSerializable("fuente");
            novedadRecoleccion = (NovedadRecoleccion) savedInstanceState.getSerializable("novedad");
            factor = (FuenteArticulo) savedInstanceState.getSerializable("factor");
            //currentFactor = (String) savedInstanceState.getSerializable("factor");
        }

        if (elemento != null) {
            if (novedadRecoleccion.equals(NovedadRecoleccion.ADICIONADA)) {
                cbIA.setEnabled(false);
                cbIA.setChecked(true);
                cbIN.setEnabled(false);
                cbNd.setEnabled(false);
                cbPr.setEnabled(false);
                cbIs.setEnabled(false);

            }else if (novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)) {
                cbIA.setEnabled(false);
                cbIN.setEnabled(false);
                cbIN.setChecked(true);
                cbNd.setEnabled(false);
                cbPr.setEnabled(false);
                cbIs.setEnabled(false);

            }else {
                cbIA.setVisibility(View.GONE);
                cbIN.setVisibility(View.GONE);
                fabEliminar.setEnabled(false);
            }
        }

        if (recoleccionSeleccionada != null ){


            if (novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)){
                cbIA.setEnabled(false);
                cbIN.setEnabled(false);
                cbIN.setChecked(true);
                cbIs.setEnabled(false);
                cbNd.setEnabled(false);
                cbPr.setEnabled(false);
                spUnidad.setEnabled(true);
                spPresentacion.setEnabled(true);

            } else if (novedadRecoleccion.equals(NovedadRecoleccion.ADICIONADA)) {
                cbIA.setEnabled(false);
                cbIA.setChecked(true);
                cbIN.setEnabled(false);
                cbIs.setEnabled(false);
                cbNd.setEnabled(false);
                cbPr.setEnabled(false);

                tvUnidadMedida.setVisibility(View.GONE);
                textUnidad.setVisibility(View.GONE);
                spUnidad.setEnabled(true);
                spPresentacion.setEnabled(true);

            }else if(novedadRecoleccion.equals(NovedadRecoleccion.PROGRAMADA)){
                cbIA.setVisibility(View.GONE);
                cbIN.setVisibility(View.GONE);
                fabEliminar.setEnabled(false);
                spUnidad.setEnabled(false);
                spPresentacion.setEnabled(false);


            }else if(novedadRecoleccion.equals(NovedadRecoleccion.INSUMO_SALE)){
                cbIA.setVisibility(View.GONE);
                cbIN.setVisibility(View.GONE);
                fabEliminar.setEnabled(false);
                cbIs.setChecked(true);
                etPrecioRecoleccion.setEnabled(false);
                etPrecioRecoleccion.setText("0.0");
                spUnidad.setEnabled(false);
                spPresentacion.setEnabled(false);

            }else if(novedadRecoleccion.equals(NovedadRecoleccion.NO_DISPONIBLE)){
                cbIA.setVisibility(View.GONE);
                cbIN.setVisibility(View.GONE);
                fabEliminar.setEnabled(false);
                cbNd.setChecked(true);
                etPrecioRecoleccion.setText("0.0");
                etPrecioRecoleccion.setEnabled(false);
                spUnidad.setEnabled(false);
                spPresentacion.setEnabled(false);

            }else if(novedadRecoleccion.equals(NovedadRecoleccion.PROMOCION)){
                cbIA.setVisibility(View.GONE);
                cbIN.setVisibility(View.GONE);
                fabEliminar.setEnabled(false);
                cbPr.setChecked(true);
                spUnidad.setEnabled(false);
                spPresentacion.setEnabled(false);

            }else {
                cbIA.setVisibility(View.GONE);
                cbIN.setVisibility(View.GONE);
                fabEliminar.setEnabled(false);
            }

        }





        cbIs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cbNd.setChecked(false);
                    cbPr.setChecked(false);
                    etPrecioRecoleccion.setEnabled(false);
                    etPrecioRecoleccion.setText("0.0");
                }else {
                    etPrecioRecoleccion.setEnabled(true);
                }
                novedadRecoleccion = NovedadRecoleccion.INSUMO_SALE;
            }
        });

        cbNd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cbIs.setChecked(false);
                    cbPr.setChecked(false);
                    etPrecioRecoleccion.setText("0.0");
                    etPrecioRecoleccion.setEnabled(false);
                }else{
                    etPrecioRecoleccion.setEnabled(true);
                }
                novedadRecoleccion = NovedadRecoleccion.NO_DISPONIBLE;
            }
        });

        cbPr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cbIs.setChecked(false);
                    cbNd.setChecked(false);
                    etPrecioRecoleccion.setEnabled(true);
                }

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        toolbar.setTitle(fuente.getFuenNombre());
        if (factor != null){
            toolbar.setSubtitle(factor.getTireNombre());
        }else{
            toolbar.setSubtitle(currentFactor);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(elemento!=null){
            tvArticulo.setText(elemento.getArtiNombre());
            tvCasaComercial.setText(elemento.getCacoNombre());
            tvRegistroIca.setText(elemento.getArticacoRegicaLinea());

            presentacion = databaseManager.listaPresentacionArticulo();
            adapterUnidadMedida = new ArrayAdapter<Presentacion>(this, R.layout.spinner_black_item, presentacion);
            adapterUnidadMedida.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spPresentacion.setAdapter(adapterUnidadMedida);



            spPresentacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(elemento!=null){
                        unidad = databaseManager.listaUnidadMedidaArticulo(((Presentacion) adapterView.getSelectedItem()).getTiprId(), elemento.getArtiId(), fuente.getFuenId(), elemento.getCacoId(), elemento.getArticacoRegicaLinea());
                    }else if(recoleccionSeleccionada!=null){
                        unidad = databaseManager.listaUnidadMedidaArticulo(((Presentacion) adapterView.getSelectedItem()).getTiprId(), recoleccionSeleccionada.getArticuloId(), fuente.getFuenId(), recoleccionSeleccionada.getCasaComercialId(), recoleccionSeleccionada.getRegistroIcaId());
                    }
                    adapterUnidad = new ArrayAdapter<UnidadMedida>(view.getContext(), R.layout.spinner_black_item, unidad);
                    adapterUnidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    if (unidad != null) {
                        spUnidad.setAdapter(adapterUnidad);
                    } else {
                        Toast.makeText(getApplicationContext(), "El Producto ya fue Recolectado.", Toast.LENGTH_LONG).show();
                        finish();
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });



        }else if(recoleccionSeleccionada!=null){
            tvArticulo.setText(recoleccionSeleccionada.getArticuloNombre());
            tvCasaComercial.setText(recoleccionSeleccionada.getCasaComercialNombre());
            String nombrePpal = " de "+ recoleccionSeleccionada.getUnmeCantidadPpal() + " " + recoleccionSeleccionada.getUnidadMedidaNombre();
            String nombreSec = recoleccionSeleccionada.getUnmeCantidad2()!=0? " de " + recoleccionSeleccionada.getUnmeCantidad2()+ " "+ recoleccionSeleccionada.getUnmeNombre2() + " c/u":"";
            tvUnidadMedida.setText(nombrePpal + nombreSec);
            tvRegistroIca.setText(recoleccionSeleccionada.getRegistroIcaId());


            etPrecioRecoleccion.setText(recoleccionSeleccionada.getPrecio());

            List<Presentacion> presentacionList = new ArrayList<>();
            Presentacion presentacionSeleccionada = new Presentacion();
            presentacionSeleccionada.setTiprNombre(recoleccionSeleccionada.getTiprNombre());
            presentacionSeleccionada.setTiprId(recoleccionSeleccionada.getTiprId());
            presentacionList.add(presentacionSeleccionada);

            adapterUnidadMedida = new ArrayAdapter<Presentacion>(this, R.layout.spinner_black_item, presentacionList);
            adapterUnidadMedida.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spPresentacion.setAdapter(adapterUnidadMedida);

            List<UnidadMedida> unidadMedidaList = new ArrayList<>();
            UnidadMedida unidadMedidaSeleccionada = new UnidadMedida();
            unidadMedidaSeleccionada.setTiprNombre(recoleccionSeleccionada.getUnidadMedidaNombre());
            unidadMedidaSeleccionada.setTiprId(recoleccionSeleccionada.getUnidadMedidaId());
            unidadMedidaSeleccionada.setUnmeCantidad2(recoleccionSeleccionada.getUnmeCantidad2());
            unidadMedidaSeleccionada.setUnmeCantidadPpal(recoleccionSeleccionada.getUnmeCantidadPpal());
            unidadMedidaSeleccionada.setUnmeNombrePpal(recoleccionSeleccionada.getUnidadMedidaNombre());
            unidadMedidaSeleccionada.setUnmeNombre2(recoleccionSeleccionada.getUnmeNombre2());
            unidadMedidaSeleccionada.setUnmeId(recoleccionSeleccionada.getUnidadMedidaId());
            unidadMedidaList.add(unidadMedidaSeleccionada);

            adapterUnidad = new ArrayAdapter<UnidadMedida>(this, R.layout.spinner_black_item, unidadMedidaList);
            adapterUnidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spUnidad.setAdapter(adapterUnidad);
        }


        etPrecioRecoleccion.addTextChangedListener(new MoneyTextWatcher(etPrecioRecoleccion, 4));

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(RecoleccionActivity.this)
                        .setTitle("")
                        .setMessage("¿Realmente desea eliminar el articulo?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        List<Recoleccion> recoleccion = databaseManager.getRecoleccionByRecoId(recoleccionSeleccionada.getId());
                        for (Recoleccion r : recoleccion) {
                            databaseManager.delete(r);
                        }

                        if (novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)){
                            List<Articulo> articulos = databaseManager.getArticuloByArtiId(recoleccionSeleccionada.getArticuloId());
                            for (Articulo a : articulos) {
                                databaseManager.delete(a);
                            }
                        }

                        Toast.makeText(getApplicationContext(), "Elemento eliminado exitosamente.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }).show();
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
                mAwesomeValidation.setContext(RecoleccionActivity.this);
                mAwesomeValidation.addValidation(RecoleccionActivity.this, R.id.etPrecioRecoleccion1, ".+", R.string.invalido);
                boolean validateNovedad = true;

                if (cbPr.isChecked()){
                    novedadRecoleccion = NovedadRecoleccion.PROMOCION;
                }else if (cbIs.isChecked()){
                    novedadRecoleccion = NovedadRecoleccion.INSUMO_SALE;
                }else if (cbNd.isChecked()){
                    novedadRecoleccion = NovedadRecoleccion.NO_DISPONIBLE;
                }else if (novedadRecoleccion != NovedadRecoleccion.ADICIONADA && novedadRecoleccion != NovedadRecoleccion.NUEVO){
                    novedadRecoleccion = NovedadRecoleccion.PROGRAMADA;
                }

                if (mAwesomeValidation.validate() && validateNovedad) {
                    recoleccion = new Recoleccion();
                    Double precioRecolectado =Double.parseDouble(etPrecioRecoleccion.getText().toString().replaceAll(",",""));

                    if (precioRecolectado <= 0.0 && !cbNd.isChecked() && !cbIs.isChecked()){
                        Toast.makeText(getApplicationContext(), "El precio recolectado debe ser mayor a 0.0 o debe cambiar la novedad", Toast.LENGTH_LONG).show();
                        validateNovedad = false;
                    }else {

                        if (elemento != null) {
                            recoleccion.setArticacoId(elemento.getArticacoId());
                            recoleccion.setArtiNombre(elemento.getArtiNombre());
                            recoleccion.setCacoId(elemento.getCacoId());
                            recoleccion.setCacoNombre(elemento.getCacoNombre());
                            recoleccion.setArticacoRegicaLinea(elemento.getArticacoRegicaLinea());
                            recoleccion.setTireId(elemento.getTireId());
                            recoleccion.setGrupNombre(elemento.getGrupNombre());
                            recoleccion.setArtiId(elemento.getArtiId());
                            recoleccion.setPromAntDiario(0.0);
                            if (factor != null){
                                recoleccion.setPrreFechaProgramada(factor.getPrreFechaProgramada());
                            } else {
                                recoleccion.setPrreFechaProgramada(new Date());
                            }

                        } else if (recoleccionSeleccionada != null) {
                            recoleccion.setId(recoleccionSeleccionada.getId());
                            recoleccion.setArticacoId(recoleccionSeleccionada.getArticacoId());
                            recoleccion.setArtiNombre(recoleccionSeleccionada.getArticuloNombre());
                            recoleccion.setCacoId(recoleccionSeleccionada.getCasaComercialId());
                            recoleccion.setCacoNombre(recoleccionSeleccionada.getCasaComercialNombre());
                            recoleccion.setArticacoRegicaLinea(recoleccionSeleccionada.getRegistroIcaId());
                            recoleccion.setTireId(recoleccionSeleccionada.getTireId());
                            recoleccion.setGrupNombre(recoleccionSeleccionada.getGrupoNombre());
                            recoleccion.setArtiId(recoleccionSeleccionada.getArticuloId());
                            recoleccion.setPromAntDiario(recoleccionSeleccionada.getPromAntDiario());
                            recoleccion.setPrreFechaProgramada(recoleccionSeleccionada.getFechaProgramada());
                            recoleccion.setNovedadAnterior(recoleccionSeleccionada.getNovedadAnterior());
                            recoleccion.setSubgNombre(recoleccionSeleccionada.getSubgNombre());
                            recoleccion.setArtiVlrMinDiasm(recoleccionSeleccionada.getArtiVlrMinDiasM());
                            recoleccion.setArtiVlrMaxDiasm(recoleccionSeleccionada.getArtiVlrMaxDiasM());
                            recoleccion.setArtiVlrMinTomas(recoleccionSeleccionada.getArtiVlrMinTomas());
                            recoleccion.setArtiVlrMaxTomas(recoleccionSeleccionada.getArtiVlrMaxTomas());
                            recoleccion.setArtiVlrMinRondas(recoleccionSeleccionada.getArtiVlrMinRondas());
                            recoleccion.setArtiVlrMaxRondas(recoleccionSeleccionada.getArtiVlrMaxRondas());
                        }

                        recoleccion.setFechaRecoleccion(new Date());

                        recoleccion.setUnmeId(((UnidadMedida) spUnidad.getSelectedItem()).getUnmeId());
                        recoleccion.setUnmeNombrePpal(((UnidadMedida) spUnidad.getSelectedItem()).getUnmeNombrePpal());
                        recoleccion.setUnmeCantidadPpal(((UnidadMedida) spUnidad.getSelectedItem()).getUnmeCantidadPpal());
                        recoleccion.setUnmeNombre2(((UnidadMedida) spUnidad.getSelectedItem()).getUnmeNombre2());
                        recoleccion.setUnmeCantidad2(((UnidadMedida) spUnidad.getSelectedItem()).getUnmeCantidad2());
                        recoleccion.setFuenId(fuente.getFuenId());
                        recoleccion.setFuenNombre(fuente.getFuenNombre());
                        recoleccion.setMuniId(fuente.getMuniId());
                        recoleccion.setTiprId(((Presentacion) spPresentacion.getSelectedItem()).getTiprId());
                        List<FuenteArticulo> fuenteArticulo = null;
                        Double precioAnterior = 0.0;
                        if (recoleccionSeleccionada != null) {
                            fuenteArticulo = databaseManager.obtenerFuenteArticulo(fuente.getFuenId(), recoleccionSeleccionada.getTireId());
                            precioAnterior = recoleccionSeleccionada.getPromAntDiario();
                        } else {
                            fuenteArticulo = databaseManager.obtenerFuenteArticulo(fuente.getFuenId(), elemento.getTireId());

                        }

                        Double desviacion = 0.0;


                        if (precioAnterior != null && precioAnterior != 0.0) {
                            desviacion = ((precioRecolectado / precioAnterior) - 1) * 100;
                        }

                        if (fuenteArticulo != null && !fuenteArticulo.isEmpty()) {
                            recoleccion.setFutiId(fuenteArticulo.get(0).getFutiId());
                        }


                        recoleccion.setPrecio(Double.parseDouble(etPrecioRecoleccion.getText().toString().replaceAll(",", "")));
                        recoleccion.setTiprNombre(((Presentacion) spPresentacion.getSelectedItem()).getTiprNombre());

                        String tipoNovedad = "";
                        Double variacionPorcentual = 0.0;
                        String titulo = "";
                        DecimalFormat formatPrecio = new DecimalFormat("#.##");

                        if (desviacion < -5) {
                            tipoNovedad = "B";
                            titulo = "Variación negativa: " + formatPrecio.format(desviacion) + "% ";
                        } else if (desviacion > 5) {
                            titulo = "Variación positiva: " + formatPrecio.format(desviacion) + "% ";
                            tipoNovedad = "A";
                        } else if (novedadRecoleccion.equals(NovedadRecoleccion.ADICIONADA)) {
                            titulo = "Articulo Adicionado";
                            tipoNovedad = "IA";
                        } else if (novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)) {
                            titulo = "Articulo Nuevo";
                            tipoNovedad = "IN";
                        } else if (novedadRecoleccion.equals(NovedadRecoleccion.PROMOCION)) {
                            titulo = "Articulo Promoción";
                            tipoNovedad = "PR";
                        } else if (novedadRecoleccion.equals(NovedadRecoleccion.NO_DISPONIBLE)) {
                            titulo = "Articulo No Disponible";
                            tipoNovedad = "ND";
                        } else if (novedadRecoleccion.equals(NovedadRecoleccion.INSUMO_SALE)) {
                            titulo = "Articulo Insumo Sale";
                            tipoNovedad = "IS";
                        } else {
                            titulo = "Variación en el Rango";
                            tipoNovedad = "P";
                        }


                        // HashMap result = calcularResumen(desviacion,precioRecolectado);
                        Toast.makeText(getApplicationContext(), titulo, Toast.LENGTH_LONG).show();


                        if (cbPr.isChecked()) {
                            recoleccion.setNovedad("PR");
                        } else if (cbNd.isChecked()) {
                            recoleccion.setNovedad("ND");
                        } else if (cbIs.isChecked()) {
                            recoleccion.setNovedad("IS");
                        } else {
                            recoleccion.setNovedad(tipoNovedad);
                            if (novedadRecoleccion.equals(NovedadRecoleccion.ADICIONADA)) {
                                recoleccion.setNovedad("IA");
                            } else if (novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)) {
                                recoleccion.setNovedad("IN");
                            }
                        }

                        Intent intent = new Intent(getApplicationContext(), ListaObservacionActivity.class);
                        intent.putExtra("saveOnResult", true);
                        intent.putExtra("tipoNovedad", recoleccion.getNovedad());
                        intent.putExtra("fuente", fuente);
                        if (factor != null){
                            intent.putExtra("factor", factor.getTireNombre());
                        }else{
                            intent.putExtra("factor", currentFactor);
                        }

                        if (tipoNovedad != "P" && tipoNovedad != "IA" && tipoNovedad != "IN") {
                            (RecoleccionActivity.this).startActivityForResult(intent, LISTA_OBSERVACIONES);
                        } else if (tipoNovedad.equals("P")) {
                            /*long estable = 20;
                            recoleccion.setObservacion("Estable");
                            recoleccion.setIdObservacion(estable);
                            recoleccion.setEstadoRecoleccion(true);
                            databaseManager.save(recoleccion);
                            Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_LONG).show();
                            finish();*/
                            (RecoleccionActivity.this).startActivityForResult(intent, LISTA_OBSERVACIONES);
                        } else if (tipoNovedad.equals("IA")) {
                            long adicionado = 21;
                            recoleccion.setObservacion("Insumo Adicionado");
                            recoleccion.setIdObservacion(adicionado);
                            recoleccion.setEstadoRecoleccion(true);
                            databaseManager.save(recoleccion);
                            Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_LONG).show();
                            finish();
                        } else if (tipoNovedad.equals("IN")) {
                            long nuevo = 22;
                            recoleccion.setObservacion("Insumo Nuevo");
                            recoleccion.setIdObservacion(nuevo);
                            recoleccion.setEstadoRecoleccion(true);
                            databaseManager.save(recoleccion);
                            Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_LONG).show();
                            finish();
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


    public HashMap calcularResumen(Double variacionPorcentual,Double precioRecolectado) {

        HashMap result = new HashMap();
        String titulo = "";
        String mensaje = "";
        String tipoNovedad = "";

        DecimalFormat formatCantidad = new DecimalFormat("#.###");
        DecimalFormat formatPrecio = new DecimalFormat("#.##");

        mensaje = "Precio  Recolectado:  " + Util.formatMoney(precioRecolectado);
        mensaje +="\n---------------------------------\n" ;


        if ( variacionPorcentual < -5) {
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
