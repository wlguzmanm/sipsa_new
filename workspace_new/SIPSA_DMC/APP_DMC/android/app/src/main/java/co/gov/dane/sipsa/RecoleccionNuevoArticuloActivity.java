package co.gov.dane.sipsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.gov.dane.sipsa.backend.Database;
import co.gov.dane.sipsa.backend.dao.Articulo;
import co.gov.dane.sipsa.backend.dao.ArticuloDao;
import co.gov.dane.sipsa.backend.dao.CasaComercial;
import co.gov.dane.sipsa.backend.dao.Fuente;
import co.gov.dane.sipsa.backend.dao.FuenteArticulo;
import co.gov.dane.sipsa.backend.dao.Recoleccion;
import co.gov.dane.sipsa.backend.dao.UnidadMedida;
import co.gov.dane.sipsa.backend.model.Elemento;
import co.gov.dane.sipsa.backend.model.ObservacionElem;
import co.gov.dane.sipsa.backend.model.Presentacion;
import co.gov.dane.sipsa.utils.MoneyTextWatcher;

public class RecoleccionNuevoArticuloActivity extends AppCompatActivity {

    public Elemento elemento;
    public Fuente fuente;
    private TextView tvCasaComercial;
    private EditText etArticulo,etIca;
    private Spinner spPresentacion;
    private Spinner spUnidad;
    public ArrayAdapter<Presentacion> adapterUnidadMedida;
    public List<Presentacion> presentacion = new ArrayList<>();
    public ArrayAdapter<UnidadMedida> adapterUnidad;
    public  List<UnidadMedida> unidad = new ArrayList<>();
    private AutoCompleteTextView actvCasaComercial;


    private CheckBox cbNd,cbPr,cbIs,cbIN;
    private EditText etPrecioRecoleccion1;

    private Recoleccion recoleccion;
    private Integer LISTA_OBSERVACIONES = 12;
    private ObservacionElem currentObservacion;
    private FuenteArticulo factor;
    private CasaComercial currentCasaComercial ;

    public static Database databaseManager;
    private ImageView atrasRecolectarArticuloNuevo;
    private Mensajes msj;
    private TextView title, subtitle;
    private LinearLayout fabGuardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elemento_nuevo_recoleccion);

        atrasRecolectarArticuloNuevo = (ImageView) findViewById(R.id.atrasRecolectarArticuloNuevo);
        fabGuardar = (LinearLayout) findViewById(R.id.fabGuardarNuevoArticulo);
        title = (TextView) findViewById(R.id.title);
        subtitle = (TextView) findViewById(R.id.subtitle);

        databaseManager = new Database(RecoleccionNuevoArticuloActivity.this);
        msj = new Mensajes(RecoleccionNuevoArticuloActivity.this);

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
        spPresentacion = (Spinner) findViewById(R.id.spPresentacionNuevo);
        spUnidad = (Spinner) findViewById(R.id.spUnidadNuevo);
        actvCasaComercial = (AutoCompleteTextView) findViewById(R.id.actvCasaComercial);
        cbNd = (CheckBox) findViewById(R.id.cbNdNuevo);
        cbPr = (CheckBox) findViewById(R.id.cbPrNuevo);
        cbIs = (CheckBox) findViewById(R.id.cbIsNuevo);
        cbIN = (CheckBox) findViewById(R.id.cbINNuevo);
        etPrecioRecoleccion1 = (EditText) findViewById(R.id.etPrecioRecoleccionNuevo);

        presentacion = databaseManager.listaPresentacionArticulo();
        adapterUnidadMedida = new ArrayAdapter<Presentacion>(this, R.layout.simple_spinner_item, presentacion);
        adapterUnidadMedida.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spPresentacion.setAdapter(adapterUnidadMedida);

        List<CasaComercial> casaComercial = databaseManager.listaCasaComercial();


        ArrayAdapter<CasaComercial> adapter = new ArrayAdapter<CasaComercial>
                (this,android.R.layout.simple_list_item_1,casaComercial);
        actvCasaComercial.setAdapter(adapter);

        spPresentacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unidad = databaseManager.listaUnidadMedidaByPresentacionId(((Presentacion)adapterView.getSelectedItem()).getTiprId());
                adapterUnidad = new ArrayAdapter<UnidadMedida>(view.getContext(), R.layout.simple_spinner_item, unidad);
                adapterUnidad.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spUnidad.setAdapter(adapterUnidad);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

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
                Boolean paso = true;
                if(etPrecioRecoleccion1.getText().toString().isEmpty()){
                    paso = false;
                }
                if(etArticulo.getText().toString().isEmpty()){
                    paso = false;
                }
                if(etIca.getText().toString().isEmpty()){
                    paso = false;
                }
                if (paso) {
                    if(currentCasaComercial==null){
                        msj.dialogoMensajeError("Por favor seleccione una casa comercial valida");
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

                    Articulo articulo = new Articulo();
                    ContentValues values = new ContentValues();
                    Long articacoId = databaseManager.save("ARTICULO", values, true, null);
                    articulo.setArtiNombre(etArticulo.getText().toString());
                    articulo.setCacoNombre(currentCasaComercial.getCacoNombre());
                    articulo.setArtiId(articacoId);
                    articulo.setCacoId(currentCasaComercial.getCacoId());
                    articulo.setArticacoRegicaLinea(etIca.getText().toString());
                    if(factor !=null){
                        articulo.setTireId(factor.getTireId());
                        articulo.setTireNombre(factor.getTireNombre());
                    }

                    values = new ContentValues();
                    values.put(ArticuloDao.Properties.ArtiNombre.columnName,articulo.getArtiNombre());
                    values.put(ArticuloDao.Properties.CacoNombre.columnName,articulo.getCacoNombre());
                    values.put(ArticuloDao.Properties.CacoId.columnName,articulo.getCacoId());
                    values.put(ArticuloDao.Properties.ArticacoRegicaLinea.columnName,articulo.getArticacoRegicaLinea());
                    values.put(ArticuloDao.Properties.TireId.columnName,articulo.getTireId());
                    values.put(ArticuloDao.Properties.TireNombre.columnName,articulo.getTireNombre());
                    databaseManager.save("ARTICULO", values, false, " ARTICACO_ID = "+articulo.getArticacoId());

                    recoleccion.setArtiId(articacoId);
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

                    values = new ContentValues();
                    values.put("OBSERVACION",recoleccion.getObservacion());
                    values.put("ID_OBSERVACION",recoleccion.getIdObservacion());
                    values.put("ESTADO_RECOLECCION",recoleccion.getEstadoRecoleccion());
                    values.put("TIPR_NOMBRE",recoleccion.getTiprNombre());
                    values.put("FUTI_ID",recoleccion.getFutiId());

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

                    databaseManager.save("RECOLECCION", values, true, null);
                    Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    msj.dialogoMensajeError("Por favor, diligenciar todos los campos.");
                }
            }
        });


        title.setText(fuente.getFuenNombre());
        subtitle.setText(factor.getTireNombre());

        atrasRecolectarArticuloNuevo.setOnClickListener(new View.OnClickListener() {
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

                    databaseManager.save("RECOLECCION", values, true, null);
                    Toast.makeText(getApplicationContext(), "Recoleccion guardada exitosamente", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}