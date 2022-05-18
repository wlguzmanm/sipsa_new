package co.gov.dane.sipsa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.IconMarginSpan;
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
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.gov.dane.sipsa.backend.Database;
import co.gov.dane.sipsa.backend.dao.Articulo;
import co.gov.dane.sipsa.backend.dao.Fuente;
import co.gov.dane.sipsa.backend.dao.FuenteArticulo;
import co.gov.dane.sipsa.backend.model.Presentacion;
import co.gov.dane.sipsa.backend.dao.Recoleccion;
import co.gov.dane.sipsa.backend.dao.UnidadMedida;
import co.gov.dane.sipsa.backend.model.Elemento;
import co.gov.dane.sipsa.backend.model.NovedadRecoleccion;
import co.gov.dane.sipsa.backend.model.ObservacionElem;
import co.gov.dane.sipsa.backend.model.RecoleccionPrincipal;
import co.gov.dane.sipsa.utils.MoneyTextWatcher;

public class RecoleccionActivity extends AppCompatActivity {

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

    private NovedadRecoleccion novedadRecoleccion;
    private CheckBox cbNd, cbPr, cbIs, cbIA, cbIN;
    private EditText etPrecioRecoleccion;
    private Integer LISTA_OBSERVACIONES = 12;
    private Recoleccion recoleccion;
    private String currentFactor;

    private ObservacionElem currentObservacion;

    private ImageView atrasRecolectarArticulo;
    private Mensajes msj;
    private TextView title, subtitle;
    private LinearLayout fabEliminar, fabGuardar;
    public static Database databaseManager;

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

        atrasRecolectarArticulo = (ImageView) findViewById(R.id.atrasRecolectarArticulo);
        fabEliminar = (LinearLayout) findViewById(R.id.fabEliminarRecolectarArticulo);
        fabGuardar = (LinearLayout) findViewById(R.id.fabGuardarRecolectarArticulo);
        title = (TextView) findViewById(R.id.title);
        subtitle = (TextView) findViewById(R.id.subtitle);

        databaseManager = new Database(RecoleccionActivity.this);
        msj = new Mensajes(RecoleccionActivity.this);


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
        title.setText(fuente.getFuenNombre());

        if (factor != null){
            subtitle.setText(factor.getTireNombre());
        }else{
            subtitle.setText(currentFactor);
        }

        if(elemento!=null){
            tvArticulo.setText(elemento.getArtiNombre());
            tvCasaComercial.setText(elemento.getCacoNombre());
            tvRegistroIca.setText(elemento.getArticacoRegicaLinea());

            presentacion = databaseManager.listaPresentacionArticulo();
            adapterUnidadMedida = new ArrayAdapter<Presentacion>(this, R.layout.simple_spinner_item, presentacion);
            adapterUnidadMedida.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spPresentacion.setAdapter(adapterUnidadMedida);

            spPresentacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(elemento!=null){
                        unidad = databaseManager.listaUnidadMedidaArticulo(((Presentacion) adapterView.getSelectedItem()).getTiprId(), elemento.getArtiId(), fuente.getFuenId(), elemento.getCacoId(), elemento.getArticacoRegicaLinea());
                    }else if(recoleccionSeleccionada!=null){
                        unidad = databaseManager.listaUnidadMedidaArticulo(((Presentacion) adapterView.getSelectedItem()).getTiprId(), recoleccionSeleccionada.getArticuloId(), fuente.getFuenId(), recoleccionSeleccionada.getCasaComercialId(), recoleccionSeleccionada.getRegistroIcaId());
                    }
                    adapterUnidad = new ArrayAdapter<UnidadMedida>(view.getContext(), R.layout.simple_spinner_item, unidad);
                    adapterUnidad.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    if (unidad != null) {
                        spUnidad.setAdapter(adapterUnidad);
                    } else {
                        msj.dialogoMensaje("El Producto ya fue Recolectado.");
                        finish();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) { }
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

            adapterUnidadMedida = new ArrayAdapter<Presentacion>(this, R.layout.simple_spinner_item, presentacionList);
            adapterUnidadMedida.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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

            adapterUnidad = new ArrayAdapter<UnidadMedida>(this, R.layout.simple_spinner_item, unidadMedidaList);
            adapterUnidad.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
                            databaseManager.delete(r);//TODO : AJUSTAR
                        }

                        if (novedadRecoleccion.equals(NovedadRecoleccion.NUEVO)){
                            List<Articulo> articulos = databaseManager.getArticuloByArtiId(recoleccionSeleccionada.getArticuloId());
                            for (Articulo a : articulos) {
                                databaseManager.delete(a);  //TODO : AJUSTAR
                            }
                        }
                        msj.dialogoMensaje("Elemento eliminado exitosamente.");
                        finish();
                    }
                }).show();
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validateNovedad = true;
                if(etPrecioRecoleccion.getText().toString().isEmpty()){
                    validateNovedad = false;
                }

                if (cbPr.isChecked()){
                    novedadRecoleccion = NovedadRecoleccion.PROMOCION;
                }else if (cbIs.isChecked()){
                    novedadRecoleccion = NovedadRecoleccion.INSUMO_SALE;
                }else if (cbNd.isChecked()){
                    novedadRecoleccion = NovedadRecoleccion.NO_DISPONIBLE;
                }else if (novedadRecoleccion != NovedadRecoleccion.ADICIONADA && novedadRecoleccion != NovedadRecoleccion.NUEVO){
                    novedadRecoleccion = NovedadRecoleccion.PROGRAMADA;
                }

                if (validateNovedad) {
                    recoleccion = new Recoleccion();
                    Double precioRecolectado =Double.parseDouble(etPrecioRecoleccion.getText().toString().replaceAll(",",""));

                    if (precioRecolectado <= 0.0 && !cbNd.isChecked() && !cbIs.isChecked()){
                        msj.dialogoMensajeError("El precio recolectado debe ser mayor a 0.0 o debe cambiar la novedad");
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
                        //msj.dialogoMensaje(titulo);

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
                            (RecoleccionActivity.this).startActivityForResult(intent, LISTA_OBSERVACIONES);
                        } else if (tipoNovedad.equals("IA")) {
                            long adicionado = 21;
                            recoleccion.setObservacion("Insumo Adicionado");
                            recoleccion.setIdObservacion(adicionado);
                            recoleccion.setEstadoRecoleccion(true);

                            ContentValues values = new ContentValues();

                            values.put("OBSERVACION",recoleccion.getObservacion());
                            values.put("ID_OBSERVACION",recoleccion.getIdObservacion());
                            values.put("ESTADO_RECOLECCION",recoleccion.getEstadoRecoleccion());

                            values.put("_id",recoleccion.getId());
                            values.put("ARTICACO_ID",recoleccion.getArticacoId());
                            values.put("ARTI_NOMBRE",recoleccion.getArtiNombre());
                            values.put("CACO_ID",recoleccion.getCacoId());
                            values.put("CACO_NOMBRE",recoleccion.getCacoNombre());
                            values.put("ARTICACO_REGICA_LINEA",recoleccion.getArticacoRegicaLinea());
                            values.put("TIRE_ID",recoleccion.getTireId());
                            values.put("GRUP_NOMBRE",recoleccion.getGrupNombre());
                            values.put("ARTI_ID",recoleccion.getArtiId());
                            values.put("PROM_ANT_DIARIO",recoleccion.getPromAntDiario());
                            values.put("PRRE_FECHA_PROGRAMADA",recoleccion.getPrreFechaProgramada().getTime() );
                            values.put("NOVEDAD_ANTERIOR",recoleccion.getNovedadAnterior());
                            values.put("SUBG_NOMBRE",recoleccion.getSubgNombre());
                            values.put("ARTI_VLR_MIN_DIASM",recoleccion.getArtiVlrMinDiasm());
                            values.put("ARTI_VLR_MAX_DIASM",recoleccion.getArtiVlrMaxDiasm());
                            values.put("ARTI_VLR_MIN_TOMAS",recoleccion.getArtiVlrMinTomas());
                            values.put("ARTI_VLR_MAX_TOMAS",recoleccion.getArtiVlrMaxTomas());
                            values.put("ARTI_VLR_MIN_RONDAS",recoleccion.getArtiVlrMinRondas());
                            values.put("ARTI_VLR_MAX_RONDAS",recoleccion.getArtiVlrMaxRondas());
                            values.put("FECHA_RECOLECCION",recoleccion.getFechaRecoleccion().getTime());
                            values.put("UNME_ID",recoleccion.getUnmeId());
                            values.put("UNME_NOMBRE_PPAL",recoleccion.getUnmeNombrePpal());
                            values.put("UNME_CANTIDAD_PPAL",recoleccion.getUnmeCantidadPpal());
                            values.put("UNME_NOMBRE2",recoleccion.getUnmeNombre2());
                            values.put("UNME_CANTIDAD2",recoleccion.getUnmeCantidad2());
                            values.put("FUEN_ID",recoleccion.getFuenId());
                            values.put("FUEN_NOMBRE",recoleccion.getFuenNombre());
                            values.put("MUNI_ID",recoleccion.getMuniId());
                            values.put("TIPR_ID",recoleccion.getTiprId());
                            values.put("PRECIO",recoleccion.getPrecio());
                            values.put("NOVEDAD",recoleccion.getNovedad());

                            databaseManager.save("RECOLECCION", values, false, " ID = "+recoleccion.getId());
                            Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (tipoNovedad.equals("IN")) {
                            long nuevo = 22;
                            recoleccion.setObservacion("Insumo Nuevo");
                            recoleccion.setIdObservacion(nuevo);
                            recoleccion.setEstadoRecoleccion(true);

                            ContentValues values = new ContentValues();

                            values.put("OBSERVACION",recoleccion.getObservacion());
                            values.put("ID_OBSERVACION",recoleccion.getIdObservacion());
                            values.put("ESTADO_RECOLECCION",recoleccion.getEstadoRecoleccion());

                            values.put("_id",recoleccion.getId());
                            values.put("ARTICACO_ID",recoleccion.getArticacoId());
                            values.put("ARTI_NOMBRE",recoleccion.getArtiNombre());
                            values.put("CACO_ID",recoleccion.getCacoId());
                            values.put("CACO_NOMBRE",recoleccion.getCacoNombre());
                            values.put("ARTICACO_REGICA_LINEA",recoleccion.getArticacoRegicaLinea());
                            values.put("TIRE_ID",recoleccion.getTireId());
                            values.put("GRUP_NOMBRE",recoleccion.getGrupNombre());
                            values.put("ARTI_ID",recoleccion.getArtiId());
                            values.put("PROM_ANT_DIARIO",recoleccion.getPromAntDiario());
                            values.put("PRRE_FECHA_PROGRAMADA",recoleccion.getPrreFechaProgramada().getTime() );
                            values.put("NOVEDAD_ANTERIOR",recoleccion.getNovedadAnterior());
                            values.put("SUBG_NOMBRE",recoleccion.getSubgNombre());
                            values.put("ARTI_VLR_MIN_DIASM",recoleccion.getArtiVlrMinDiasm());
                            values.put("ARTI_VLR_MAX_DIASM",recoleccion.getArtiVlrMaxDiasm());
                            values.put("ARTI_VLR_MIN_TOMAS",recoleccion.getArtiVlrMinTomas());
                            values.put("ARTI_VLR_MAX_TOMAS",recoleccion.getArtiVlrMaxTomas());
                            values.put("ARTI_VLR_MIN_RONDAS",recoleccion.getArtiVlrMinRondas());
                            values.put("ARTI_VLR_MAX_RONDAS",recoleccion.getArtiVlrMaxRondas());
                            values.put("FECHA_RECOLECCION",recoleccion.getFechaRecoleccion().getTime());
                            values.put("UNME_ID",recoleccion.getUnmeId());
                            values.put("UNME_NOMBRE_PPAL",recoleccion.getUnmeNombrePpal());
                            values.put("UNME_CANTIDAD_PPAL",recoleccion.getUnmeCantidadPpal());
                            values.put("UNME_NOMBRE2",recoleccion.getUnmeNombre2());
                            values.put("UNME_CANTIDAD2",recoleccion.getUnmeCantidad2());
                            values.put("FUEN_ID",recoleccion.getFuenId());
                            values.put("FUEN_NOMBRE",recoleccion.getFuenNombre());
                            values.put("MUNI_ID",recoleccion.getMuniId());
                            values.put("TIPR_ID",recoleccion.getTiprId());
                            values.put("PRECIO",recoleccion.getPrecio());
                            values.put("NOVEDAD",recoleccion.getNovedad());

                            databaseManager.save("RECOLECCION", values, false, " ID = "+recoleccion.getId());
                            msj.dialogoMensaje("Recoleccion guardada exitosamente");
                            finish();
                        }

                    }
                }else{
                    msj.dialogoMensajeError("Digite un valor");
                }
            }
        });








        atrasRecolectarArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



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
                    ContentValues values = new ContentValues();
                    values.put("OBSERVACION",recoleccion.getObservacion());
                    values.put("ID_OBSERVACION",recoleccion.getIdObservacion());
                    values.put("ESTADO_RECOLECCION",recoleccion.getEstadoRecoleccion());
                    values.put("PRECIO",recoleccion.getPrecio());
                    values.put("NOVEDAD",recoleccion.getNovedad());

                    databaseManager.save("RECOLECCION", values, false, " _ID = "+recoleccion.getId());
                    Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}