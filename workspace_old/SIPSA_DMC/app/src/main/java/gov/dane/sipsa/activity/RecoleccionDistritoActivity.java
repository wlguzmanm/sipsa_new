package gov.dane.sipsa.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import gov.dane.sipsa.R;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.Articulo;
import gov.dane.sipsa.dao.ArticuloDistrito;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.FuenteArticuloDistrito;
import gov.dane.sipsa.dao.FuenteDistrito;
import gov.dane.sipsa.dao.Recoleccion;
import gov.dane.sipsa.dao.RecoleccionDistrito;
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
 * Created by mguzman on 04/05/19.
 */

public class RecoleccionDistritoActivity extends App {

    private RecoleccionPrincipal recoleccionSeleccionada;
    private List<RecoleccionPrincipal> recolecciones;
    private LinearLayout layoutOtra;

    public Elemento elemento;
    public FuenteDistrito fuente;
    private FuenteArticuloDistrito factor;
    private TextView tvTipoRecolectar;
    private TextView tvArticuloRecolectar;
    private TextView tvUnidadMedidaRecolectar;
    private TextView tvFrecuenciaRecolectar;
    private EditText tvObservacionRecolectar;
    private EditText tvOtroView;

    public ArrayAdapter<Presentacion> adapterUnidadMedida;
    public List<Presentacion> presentacion = new ArrayList<>();
    public ArrayAdapter<UnidadMedida> adapterUnidad;
    public List<UnidadMedida> unidad = new ArrayList<>();
    public static DatabaseManager databaseManager;
    private NovedadRecoleccion novedadRecoleccion;
    private CheckBox cbNd, cbIs, cbIN;
    private EditText etPrecioRecoleccionRecolectar;
    private FloatingActionButton fabGuardar, fabEliminar;
    private AwesomeValidation mAwesomeValidation;
    private Integer LISTA_OBSERVACIONES = 12;
    private RecoleccionDistrito recoleccion;
    private String currentFactor;
    private Boolean pasar  = Boolean.FALSE;
    private Boolean mostrar  = Boolean.FALSE;

    private ObservacionElem currentObservacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elemento_recoleccion_distrito);
        tvTipoRecolectar = (TextView) findViewById(R.id.tvFijoReolectar);
        tvArticuloRecolectar = (TextView) findViewById(R.id.etArticuloRecolectar);
        tvUnidadMedidaRecolectar = (TextView) findViewById(R.id.tvUnidadMedidaRecolectar);
        tvFrecuenciaRecolectar = (TextView) findViewById(R.id.tvFrecuenciaRecolectar);
        tvObservacionRecolectar = (EditText) findViewById(R.id.tvObservacionRecolectar);
        tvOtroView = (EditText) findViewById(R.id.tvOtroView);

        layoutOtra = (LinearLayout) findViewById(R.id.UnidadOtraView);
        layoutOtra.setVisibility(View.GONE);

        cbNd = (CheckBox) findViewById(R.id.cbNd);
        cbIs = (CheckBox) findViewById(R.id.cbIs);
        cbIN = (CheckBox) findViewById(R.id.cbIN);
        etPrecioRecoleccionRecolectar = (EditText) findViewById(R.id.etPrecioRecoleccionRecolectar);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardarDistrito);
        fabEliminar = (FloatingActionButton) findViewById(R.id.fabEliminarDistrito);

        databaseManager = DatabaseManager.getInstance(this);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras.getSerializable("elemento") != null) {
                elemento = (Elemento) extras.getSerializable("elemento");
                fuente = (FuenteDistrito) extras.getSerializable("fuente");
                novedadRecoleccion = (NovedadRecoleccion) extras.getSerializable("novedad");
                currentFactor = (String) extras.getSerializable("factor");
                factor = (FuenteArticuloDistrito) extras.getSerializable("factorObject");
                if(elemento.getUnidadMedidaOtroNombre()!= null){
                    layoutOtra.setVisibility(View.VISIBLE);
                }else{
                    layoutOtra.setVisibility(View.GONE);
                }


                }else if(extras.getSerializable("recoleccion")!=null){
                fuente = (FuenteDistrito) extras.getSerializable("fuente");
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
                currentFactor = (String) extras.getSerializable("factor");
            }
        } else {
            elemento = (Elemento) savedInstanceState.getSerializable("elemento");
            fuente = (FuenteDistrito) savedInstanceState.getSerializable("fuente");
            novedadRecoleccion = (NovedadRecoleccion) savedInstanceState.getSerializable("novedad");
            currentFactor = (String) savedInstanceState.getSerializable("factor");
            factor = (FuenteArticuloDistrito) savedInstanceState.getSerializable("factorObject");
        }

        if (elemento != null) {
            if (novedadRecoleccion.equals(NovedadRecoleccion.ADICIONADA)) {
                cbIN.setEnabled(false);
                cbNd.setEnabled(false);
                cbIs.setEnabled(false);

            }else if (novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)) {
                cbIN.setEnabled(false);
                cbIN.setChecked(true);
                cbNd.setEnabled(false);
                cbIs.setEnabled(false);

            }else {
                cbIN.setVisibility(View.GONE);
                fabEliminar.setEnabled(false);
            }
        }

        if (recoleccionSeleccionada != null ){

            if(recoleccionSeleccionada.getUnidadMedidaNombre().equals("OTRO")){
                layoutOtra.setVisibility(View.VISIBLE);
            }else{
                layoutOtra.setVisibility(View.GONE);
            }

            Boolean encontro = false;
            if (novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)){
                cbIN.setEnabled(false);
                cbIN.setChecked(true);
                cbIs.setEnabled(false);
                cbNd.setEnabled(false);
                encontro = true;

            } else if (novedadRecoleccion.equals(NovedadRecoleccion.ADICIONADA)) {
                cbIN.setEnabled(false);
                cbIs.setEnabled(false);
                cbNd.setEnabled(false);
                fabEliminar.setEnabled(true);
                encontro = true;

            }else if(novedadRecoleccion.equals(NovedadRecoleccion.PROGRAMADA)){
                cbIN.setVisibility(View.GONE);
                pasar = true;

            }else if(novedadRecoleccion.equals(NovedadRecoleccion.INSUMO_SALE)){
                cbIN.setVisibility(View.GONE);
                pasar = true;
                cbIs.setChecked(true);
                etPrecioRecoleccionRecolectar.setEnabled(false);
                etPrecioRecoleccionRecolectar.setText("0.0");

            }else if(novedadRecoleccion.equals(NovedadRecoleccion.NO_DISPONIBLE)){
                cbIN.setVisibility(View.GONE);
                pasar = true;
                cbNd.setChecked(true);
                etPrecioRecoleccionRecolectar.setText("0.0");
                etPrecioRecoleccionRecolectar.setEnabled(false);

            }else if(novedadRecoleccion.equals(NovedadRecoleccion.PROMOCION)){
                cbIN.setVisibility(View.GONE);
                pasar = true;

            }
            if(encontro){
                fabEliminar.setEnabled(true);
            }else{
                fabEliminar.setEnabled(false);
            }

        }


        cbIs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(b){
                   cbNd.setChecked(false);
                   etPrecioRecoleccionRecolectar.setEnabled(false);
                   etPrecioRecoleccionRecolectar.setText("0.0");
               }else {
                   etPrecioRecoleccionRecolectar.setEnabled(true);
               }
                novedadRecoleccion = NovedadRecoleccion.INSUMO_SALE;
            }
        });

        cbNd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cbIs.setChecked(false);
                    etPrecioRecoleccionRecolectar.setText("0.0");
                    etPrecioRecoleccionRecolectar.setEnabled(false);
                }else{
                    etPrecioRecoleccionRecolectar.setEnabled(true);
                }
                novedadRecoleccion = NovedadRecoleccion.NO_DISPONIBLE;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        toolbar.setTitle(fuente.getFuenNombre());
        toolbar.setSubtitle(currentFactor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(elemento!=null){
            tvArticuloRecolectar.setText(elemento.getArtiNombre());
            tvTipoRecolectar.setText(elemento.getTipo());
            tvFrecuenciaRecolectar.setText(elemento.getFrecuencia());
            tvUnidadMedidaRecolectar.setText(elemento.getUnidadMedidaNombre());
            tvObservacionRecolectar.setText(elemento.getObservacion());
            tvOtroView.setText(elemento.getUnidadMedidaOtroNombre());
            etPrecioRecoleccionRecolectar.setText(elemento.getPrecio());
        }else if(recoleccionSeleccionada!=null){
            tvArticuloRecolectar.setText(recoleccionSeleccionada.getArticuloNombre());
            tvTipoRecolectar.setText(recoleccionSeleccionada.getTipo());
            tvUnidadMedidaRecolectar.setText(recoleccionSeleccionada.getUnidadMedidaNombre());
            tvFrecuenciaRecolectar.setText(recoleccionSeleccionada.getFrecuencia());
            tvObservacionRecolectar.setText(recoleccionSeleccionada.getObservacionArticulo());
            tvOtroView.setText(recoleccionSeleccionada.getUnidadMedidaOtroNombre());
            etPrecioRecoleccionRecolectar.setText(recoleccionSeleccionada.getPrecio());
        }

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                if(!pasar){
                    new AlertDialog.Builder(RecoleccionDistritoActivity.this)
                            .setTitle("")
                            .setMessage("¿Realmente desea eliminar la fuente?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Long idEliminar = 0L;

                            Context context = RecoleccionDistritoActivity.this;

                            if(recoleccionSeleccionada!= null){
                               // List<RecoleccionDistrito> recoleccion = databaseManager.getRecoleccionDistritoByRecoId(idEliminar);
                                //for (RecoleccionDistrito r : recoleccion) {

                                  //  databaseManager.delete(r);
                                //}
                                Toast.makeText(getApplicationContext(), "Elemento eliminado exitosamente.", Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                               ArticuloDistrito articuloEliminar =  databaseManager.getArticuloDistrito(elemento);
                               databaseManager.delete(articuloEliminar);
                               Toast.makeText(getApplicationContext(), "Elemento eliminado exitosamente.", Toast.LENGTH_LONG).show();
                               finish();

                                Intent intent = new Intent(context, FuenteAddElementoDistritoActivity.class);
                                intent.putExtra("fuente", (Serializable) fuente);
                                intent.putExtra("factor", (Serializable) factor);
                                context.startActivity(intent);
                            }
                        }
                    }).show();
                } else{
                    finish();
                }
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
                mAwesomeValidation.setContext(RecoleccionDistritoActivity.this);
                mAwesomeValidation.addValidation(RecoleccionDistritoActivity.this, R.id.etPrecioRecoleccionRecolectar, ".+", R.string.invalido);
                mAwesomeValidation.addValidation(RecoleccionDistritoActivity.this, R.id.tvObservacionRecolectar, ".+", R.string.invalido);
                boolean validateNovedad = true;

                if (cbIs.isChecked()){
                    novedadRecoleccion = NovedadRecoleccion.INSUMO_SALE;
                }else if (cbNd.isChecked()){
                    novedadRecoleccion = NovedadRecoleccion.NO_DISPONIBLE;
                }else if (novedadRecoleccion != NovedadRecoleccion.ADICIONADA && novedadRecoleccion != NovedadRecoleccion.NUEVO){
                    novedadRecoleccion = NovedadRecoleccion.PROGRAMADA;
                }

                if (mAwesomeValidation.validate()) {
                   recoleccion = new RecoleccionDistrito();
                    Double precioRecolectado =Double.parseDouble(etPrecioRecoleccionRecolectar.getText().toString().replaceAll(",",""));

                    if (precioRecolectado <= 0.0 && !cbNd.isChecked() && !cbIs.isChecked()){
                        Toast.makeText(getApplicationContext(), "El precio recolectado debe ser mayor a 0.0 o debe cambiar la novedad", Toast.LENGTH_SHORT).show();
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
                            recoleccion.setPrreFechaProgramada(new Date());

                             recoleccion.setFrecuencia(elemento.getFrecuencia());
                             recoleccion.setTipo(elemento.getTipo());
                             recoleccion.setUnmeNombrePpal(elemento.getUnidadMedidaNombre());
                             recoleccion.setUnmeNombre2(elemento.getUnidadMedidaNombre());
                             recoleccion.setObservacionProducto(elemento.getObservacion());

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
                             /**
                              * Distrito de Riego  frecuencia - tipo - unmeNombrePpal - observacion - precio
                              */
                             recoleccion.setFrecuencia(recoleccionSeleccionada.getFrecuencia());
                             recoleccion.setTipo(recoleccionSeleccionada.getTipo());
                             recoleccion.setUnmeNombrePpal(recoleccionSeleccionada.getUnidadMedidaNombre());
                             recoleccion.setUnmeNombre2(recoleccionSeleccionada.getUnidadMedidaNombre());
                             recoleccion.setObservacionProducto(recoleccionSeleccionada.getObservacionArticulo());
                             recoleccion.setPrecio(Double.parseDouble(etPrecioRecoleccionRecolectar.getText().toString().replaceAll(",", "")));


                        }

                        recoleccion.setFechaRecoleccion(new Date());

                        /*recoleccion.setUnmeId(((UnidadMedida) spUnidad.getSelectedItem()).getUnmeId());
                        recoleccion.setUnmeNombrePpal(((UnidadMedida) spUnidad.getSelectedItem()).getUnmeNombrePpal());
                        recoleccion.setUnmeCantidadPpal(((UnidadMedida) spUnidad.getSelectedItem()).getUnmeCantidadPpal());
                        recoleccion.setUnmeNombre2(((UnidadMedida) spUnidad.getSelectedItem()).getUnmeNombre2());
                        recoleccion.setUnmeCantidad2(((UnidadMedida) spUnidad.getSelectedItem()).getUnmeCantidad2());
                         recoleccion.setTiprId(((Presentacion) spPresentacion.getSelectedItem()).getTiprId());*/
                        recoleccion.setFuenId(fuente.getFuenId());
                        recoleccion.setFuenNombre(fuente.getFuenNombre());
                        recoleccion.setMuniId(fuente.getMuniId());

                        List<FuenteArticuloDistrito> fuenteArticulo = null;
                        Double precioAnterior = 0.0;
                        if (recoleccionSeleccionada != null) {
                            fuenteArticulo = databaseManager.obtenerFuenteArticuloDistrito(fuente.getFuenId(), recoleccionSeleccionada.getTireId());
                            precioAnterior = recoleccionSeleccionada.getPromAntDiario();
                        } else {
                            fuenteArticulo = databaseManager.obtenerFuenteArticuloDistrito(fuente.getFuenId(), elemento.getTireId());

                        }

                        Double desviacion = 0.0;


                        if (precioAnterior != null && precioAnterior != 0.0) {
                            desviacion = ((precioRecolectado / precioAnterior) - 1) * 100;
                        }
                        recoleccion.setDesviacion(desviacion.toString());
                        if (fuenteArticulo != null && !fuenteArticulo.isEmpty()) {
                            recoleccion.setFutiId(fuenteArticulo.get(0).getFutiId());
                        }


                        recoleccion.setPrecio(Double.parseDouble(etPrecioRecoleccionRecolectar.getText().toString().replaceAll(",", "")));

                        String tipoNovedad = "";
                        Double variacionPorcentual = 0.0;
                        String titulo = "";
                        DecimalFormat formatPrecio = new DecimalFormat("#.##");

                        if (desviacion < -5) {
                            tipoNovedad = "B";
                            titulo = "Variación negativa: " + formatPrecio.format(desviacion) + "% ";
                            mostrar = true;
                        } else if (desviacion > 5) {
                            titulo = "Variación positiva: " + formatPrecio.format(desviacion) + "% ";
                            tipoNovedad = "A";
                            mostrar = true;
                        } else if (novedadRecoleccion.equals(NovedadRecoleccion.ADICIONADA)) {
                            titulo = "Articulo Adicionado";
                            tipoNovedad = "IA";
                            mostrar = false;
                        } else if (novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)) {
                            titulo = "Articulo Nuevo";
                            tipoNovedad = "IN";
                            mostrar = false;
                        } else if (novedadRecoleccion.equals(NovedadRecoleccion.PROMOCION)) {
                            titulo = "Articulo Promoción";
                            tipoNovedad = "PR";
                            mostrar = false;
                        } else if (novedadRecoleccion.equals(NovedadRecoleccion.NO_DISPONIBLE)) {
                            titulo = "Articulo No Disponible";
                            tipoNovedad = "ND";
                            mostrar = false;
                        } else if (novedadRecoleccion.equals(NovedadRecoleccion.INSUMO_SALE)) {
                            titulo = "Articulo Insumo Sale";
                            tipoNovedad = "IS";
                            mostrar = false;
                        } else {
                            titulo = "Variación en el Rango";
                            tipoNovedad = "P";
                            mostrar = false;
                        }


                        HashMap result = calcularResumen(desviacion,precioRecolectado);
                        //Toast.makeText(getApplicationContext(), titulo, Toast.LENGTH_SHORT).show();
                        recoleccion.setNovedad(result.get("tipoNovedad").toString());

                        if (cbNd.isChecked()) {
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

                        Intent intent = new Intent(getApplicationContext(), ListaObservacionDistritoActivity.class);
                        intent.putExtra("saveOnResult", true);
                        intent.putExtra("tipoNovedad", recoleccion.getNovedad());
                        intent.putExtra("fuente", fuente);
                        intent.putExtra("factor", currentFactor);

                       if (tipoNovedad != "P" && tipoNovedad != "IA" && tipoNovedad != "IN") {
                            (RecoleccionDistritoActivity.this).startActivityForResult(intent, LISTA_OBSERVACIONES);
                        } else if (tipoNovedad.equals("P")) {
                            long estable = 20;
                            recoleccion.setObservacion("Estable");
                            recoleccion.setIdObservacion(estable);
                            recoleccion.setEstadoRecoleccion(true);
                        } else if (tipoNovedad.equals("IA")) {
                               long adicionado = 21;
                               recoleccion.setObservacion("Insumo Adicionado");
                               recoleccion.setIdObservacion(adicionado);
                               recoleccion.setEstadoRecoleccion(true);
                               mostrar = false;
                        } else if (tipoNovedad.equals("IN")) {
                               long nuevo = 20;
                               recoleccion.setObservacion("Estable");
                               recoleccion.setIdObservacion(nuevo);
                               recoleccion.setEstadoRecoleccion(true);
                               mostrar = false;
                        }


                        new AlertDialog.Builder(RecoleccionDistritoActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("")
                                .setMessage(result.get("titulo")+ "\n"+ result.get("mensaje"))
                                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(mostrar){
                                    Intent intent = new Intent(getApplicationContext(), ListaObservacionDistritoActivity.class);
                                    intent.putExtra("saveOnResult", true);
                                    intent.putExtra("tipoNovedad", recoleccion.getNovedad());
                                    intent.putExtra("fuente", fuente);
                                    intent.putExtra("factor", currentFactor);
                                    (RecoleccionDistritoActivity.this).startActivityForResult(intent, LISTA_OBSERVACIONES);

                                /*
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
                                finish();*/
                                }else{
                                    databaseManager.save(recoleccion);
                                    Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        })
                                .show();
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
                    Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_SHORT).show();
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
        mensaje +="\n---------------------------------------------------\n" ;


        if ( variacionPorcentual < -5) {
            titulo = "Variación negativa:  " + formatPrecio.format(variacionPorcentual) + "% ";
            tipoNovedad = "B";
        } else if (variacionPorcentual > 5) {
            titulo = "Variación positiva:  " + formatPrecio.format(variacionPorcentual) + "% ";
            tipoNovedad = "A";
        } else {
            titulo = "Variación en el Rango  ";
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
