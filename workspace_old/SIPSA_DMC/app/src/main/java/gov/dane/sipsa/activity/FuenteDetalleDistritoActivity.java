package gov.dane.sipsa.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.github.clans.fab.FloatingActionMenu;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.dane.sipsa.R;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.FuenteArticuloDistrito;
import gov.dane.sipsa.dao.FuenteDistrito;
import gov.dane.sipsa.dao.Recoleccion;
import gov.dane.sipsa.dao.TipoRecoleccion;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.manager.IDatabaseManager;
import gov.dane.sipsa.model.Municipio;


/**
 * Created by mguzman on 02/04/2019.
 */
public class FuenteDetalleDistritoActivity extends AppCompatActivity {
    public FuenteDistrito fuente;
    public EditText etNombre;
    //public EditText etCodigoInterno;
    public EditText etDireccion;
    public EditText etInformante;
    public Spinner spMunicipio;
    public TextView tvMunicipio;
    public EditText etEmail;
    public EditText etTelefono;
    public EditText etTelefonoInformante;
    public static IDatabaseManager databaseManager;
    private AwesomeValidation mAwesomeValidation;
    public com.github.clans.fab.FloatingActionButton fabEliminar;
    public com.github.clans.fab.FloatingActionButton fabGuardar;
    public FloatingActionMenu famOpciones;

    public Boolean isEditing =false;
    public HashMap<Long,TipoRecoleccion> listTipoRecoleccion;
    private List<FuenteArticuloDistrito> fuenteArticulos ;
    private Municipio municipio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuente_detalle_distrito);

        databaseManager = DatabaseManager.getInstance(this);
        listTipoRecoleccion = new HashMap<>();
        fabEliminar = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.GONE);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras.getSerializable("municipio") != null) {
                municipio = (Municipio) extras.getSerializable("municipio");
            }
            if(extras == null) {
                if ( fuente == null) {
                    fabEliminar.setVisibility(View.GONE);
                    fuente = new FuenteDistrito();
                    isEditing = false;
                }else {

                    if(fuente.getFuenId() > 9000000000L){
                        fabEliminar.setVisibility(View.VISIBLE);
                     }else{
                        fabEliminar.setVisibility(View.GONE);
                     }
                }
            } else {

                if (extras.getSerializable("fuente") != null) {
                    fuente = (FuenteDistrito) extras.getSerializable("fuente");
                    isEditing  =true;

                    if(fuente.getFuenId() > 9000000000L){
                        fabEliminar.setVisibility(View.VISIBLE);
                    }else{
                        fabEliminar.setVisibility(View.GONE);
                    }
                }
            }
        } else {
            fuente= (FuenteDistrito) savedInstanceState.getSerializable("fuente");
            municipio= (Municipio) savedInstanceState.getSerializable("municipio");
        }

        etNombre = (EditText) findViewById(R.id.etNombre);
       //etCodigoInterno = (EditText) findViewById(R.id.etCodigoInterno);
        etDireccion = (EditText) findViewById(R.id.etDireccion);
        etInformante = (EditText) findViewById(R.id.etInformante);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etTelefono = (EditText) findViewById(R.id.etTelefono);
        etTelefonoInformante = (EditText) findViewById(R.id.etTelefonoInformante);
        tvMunicipio =(TextView) findViewById(R.id.tvMunicipio);

        if (fuente != null) {
            etNombre.setText(fuente.getFuenNombre());
            //etCodigoInterno.setText(fuente.getFuenCodigoInterno());
            etDireccion.setText(fuente.getFuenDireccion());
            etInformante.setText(fuente.getFuenNombreInformante());
            etEmail.setText(fuente.getFuenEmail());
            etTelefono.setText(fuente.getFuenTelefono());
            etTelefonoInformante.setText(fuente.getFuenTelefonoInformante());
            tvMunicipio.setText(fuente.getMuniNombre());
        }

        if (municipio != null) {
            tvMunicipio.setText(municipio.getMuniNombre());
        }

        if(isEditing){
            etNombre.setEnabled(false);
        }

        if (fuente != null) {

            fuenteArticulos = databaseManager.getFuenteArticuloDistritoByFuenteId(fuente.getFuenId());

            List<TipoRecoleccion> tipoRecoleccionList = databaseManager.listaTipoRecoleccion();
            LinearLayout llTipoRecoleccion = new LinearLayout(getApplicationContext());
            llTipoRecoleccion.setOrientation(LinearLayout.VERTICAL);
            for (final TipoRecoleccion t : tipoRecoleccionList) {

                if (!isAsignadoFuenteArticuloByTireId(fuenteArticulos, t.getTireId())) {
                    CheckBox cb = new CheckBox(getApplicationContext());
                    cb.setTextColor(Color.BLACK);
                    cb.setTag(t);
                    cb.setText(t.getTireNombre());
                    llTipoRecoleccion.addView(cb);

                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                            if (b) {
                                TipoRecoleccion t = (TipoRecoleccion) compoundButton.getTag();
                                listTipoRecoleccion.put(t.getTireId(), t);
                            } else {
                                listTipoRecoleccion.remove(t.getTireId());
                            }

                        }
                    });
                }


            }
           // llFuenteDetalle.addView(llTipoRecoleccion);
        }else {
            List<TipoRecoleccion> tipoRecoleccionList = databaseManager.listaTipoRecoleccion();
            LinearLayout llTipoRecoleccion = new LinearLayout(getApplicationContext());
            llTipoRecoleccion.setOrientation(LinearLayout.VERTICAL);
            for (final TipoRecoleccion t : tipoRecoleccionList) {
                CheckBox cb = new CheckBox(getApplicationContext());
                cb.setTextColor(Color.BLACK);
                cb.setTag(t);
                cb.setText(t.getTireNombre());
                llTipoRecoleccion.addView(cb);

                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                        if (b) {
                            TipoRecoleccion t = (TipoRecoleccion) compoundButton.getTag();
                            listTipoRecoleccion.put(t.getTireId(), t);
                        } else {
                            listTipoRecoleccion.remove(t.getTireId());
                        }

                    }
                });
            }
           // llFuenteDetalle.addView(llTipoRecoleccion);

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fabGuardar = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabGuardar);
        famOpciones = (FloatingActionMenu) findViewById(R.id.famOpciones);

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAwesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
                mAwesomeValidation.setContext(FuenteDetalleDistritoActivity.this);
                mAwesomeValidation.addValidation(FuenteDetalleDistritoActivity.this, R.id.etNombre, ".+", R.string.invalido);
                //mAwesomeValidation.addValidation(FuenteDetalleDistritoActivity.this, R.id.etCodigoInterno, ".+", R.string.invalido);
                mAwesomeValidation.addValidation(FuenteDetalleDistritoActivity.this, R.id.etDireccion, ".+", R.string.invalido);
                mAwesomeValidation.addValidation(FuenteDetalleDistritoActivity.this, R.id.etEmail, ".+", R.string.invalido);
                mAwesomeValidation.addValidation(FuenteDetalleDistritoActivity.this, R.id.etInformante, ".+", R.string.invalido);

                if (mAwesomeValidation.validate()) {

                    if (fuente != null) {

                        fuente.setFuenNombre(etNombre.getText().toString());
                        fuente.setFuenDireccion(etDireccion.getText().toString());
                        fuente.setFuenNombreInformante(etInformante.getText().toString());
                        //fuente.setFuenCodigoInterno(etCodigoInterno.getText().toString());
                        fuente.setFuenTelefonoInformante(etTelefonoInformante.getText().toString());
                        fuente.setFuenTelefono(etTelefono.getText().toString());
                        fuente.setFuenEmail(etEmail.getText().toString());
                        databaseManager.save(fuente);
                        if(!fuenteArticulos.isEmpty() && fuenteArticulos.size()==1){
                            FuenteArticuloDistrito fuenteArt = fuenteArticulos.get(0);
                            fuenteArt.setFuenTelefonoInformante(fuente.getFuenTelefonoInformante());
                            fuenteArt.setFuenTelefono(fuente.getFuenTelefono());
                            fuenteArt.setFuenNombre(fuente.getFuenNombre());
                            fuenteArt.setFuenDireccion(fuente.getFuenDireccion());
                            fuenteArt.setFuenEmail(fuente.getFuenEmail());
                            fuenteArt.setFuenNombreInformante(fuente.getFuenNombreInformante());
                            databaseManager.save(fuenteArt);
                        }


                        for (Map.Entry<Long, TipoRecoleccion> entry : listTipoRecoleccion.entrySet()) {
                            FuenteArticuloDistrito fa = new FuenteArticuloDistrito();
                            fa.setFuenDireccion(etDireccion.getText().toString());
                            fa.setFuenEmail(etEmail.getText().toString());
                            fa.setFuenNombreInformante(etInformante.getText().toString());
                            fa.setFuenNombre(etNombre.getText().toString());
                            fa.setFuenId(fuente.getFuenId());
                            fa.setFuenTelefono(etTelefono.getText().toString());
                            fa.setFuenTelefonoInformante(etTelefonoInformante.getText().toString());
                            //fa.setFuenCodigoInterno(etCodigoInterno.getText().toString());
                            fa.setMuniId(fuente.getMuniId());
                            fa.setMuniNombre(fuente.getMuniNombre());
                            fa.setTireId(entry.getValue().getTireId());
                            fa.setTireNombre(entry.getValue().getTireNombre());
                            fa.setPrreFechaProgramada(new Date());
                            databaseManager.save(fa);
                        }
                        Toast.makeText(getApplicationContext(), "Fuente Editada", Toast.LENGTH_SHORT).show();
                        famOpciones.close(true);
                    }else{

                        if (fuente == null) {
                            fuente = new FuenteDistrito();
                            Long idFuente = databaseManager.save(fuente);
                            fuente.setFuenNombre(etNombre.getText().toString());
                            fuente.setFuenDireccion(etDireccion.getText().toString());
                            fuente.setFuenNombreInformante(etInformante.getText().toString());
                            //fuente.setFuenCodigoInterno(etCodigoInterno.getText().toString());
                            fuente.setFuenTelefonoInformante(etTelefonoInformante.getText().toString());
                            fuente.setFuenTelefono(etTelefono.getText().toString());
                            fuente.setFuenEmail(etEmail.getText().toString());
                            fuente.setMuniNombre(municipio.getMuniNombre().toString());
                            fuente.setMuniId(municipio.getMuniId().toString());
                            fuente.setTireId(13L);
                            databaseManager.save(fuente);
                            if(listTipoRecoleccion!= null && listTipoRecoleccion.size()==0){
                                FuenteArticuloDistrito fa = new FuenteArticuloDistrito();
                                fa.setFuenDireccion(etDireccion.getText().toString());
                                fa.setFuenEmail(etEmail.getText().toString());
                                fa.setFuenNombreInformante(etInformante.getText().toString());
                                fa.setFuenNombre(etNombre.getText().toString());
                                fa.setFuenId(idFuente);
                                fa.setFuenTelefono(etTelefono.getText().toString());
                                fa.setFuenTelefonoInformante(etTelefonoInformante.getText().toString());
                                //fa.setFuenCodigoInterno(etCodigoInterno.getText().toString());
                                fa.setMuniId(municipio.getMuniId());
                                fa.setMuniNombre(municipio.getMuniNombre());
                                fa.setTireId(13L);
                                fa.setTireNombre("DISTRITO DE RIEGO");
                                fa.setPrreFechaProgramada(new Date());
                                databaseManager.save(fa);
                            }
                            for (Map.Entry<Long, TipoRecoleccion> entry : listTipoRecoleccion.entrySet()) {
                                FuenteArticuloDistrito fa = new FuenteArticuloDistrito();
                                fa.setFuenDireccion(etDireccion.getText().toString());
                                fa.setFuenEmail(etEmail.getText().toString());
                                fa.setFuenNombreInformante(etInformante.getText().toString());
                                fa.setFuenNombre(etNombre.getText().toString());
                                fa.setFuenId(idFuente);
                                fa.setFuenTelefono(etTelefono.getText().toString());
                                fa.setFuenTelefonoInformante(etTelefonoInformante.getText().toString());
                                //fa.setFuenCodigoInterno(etCodigoInterno.getText().toString());
                                fa.setMuniId(municipio.getMuniId());
                                fa.setMuniNombre(municipio.getMuniNombre());
                                fa.setTireId(entry.getValue().getTireId());
                                fa.setTireNombre(entry.getValue().getTireNombre());
                                fa.setPrreFechaProgramada(new Date());
                                databaseManager.save(fa);
                                Intent resultData = new Intent();
                                resultData.putExtra("fuente", (Serializable) databaseManager.getFuenteById(idFuente));
                                setResult(RESULT_OK, resultData);
                            }

                            Toast.makeText(getApplicationContext(), "Fuente Creada", Toast.LENGTH_SHORT).show();
                            famOpciones.close(true);
                        }
                    }

                    finish();
                }
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(FuenteDetalleDistritoActivity.this)
                        .setTitle("")
                        .setMessage("¿Realmente desea eliminar la fuente?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<Recoleccion> recoleccion = databaseManager.getRecoleccionByFuenteId(fuente.getFuenId());
                        for (Recoleccion r : recoleccion) {
                            databaseManager.delete(r);
                        }

                        List<FuenteArticulo> fuenteArticulos = databaseManager.getFuenteArticuloByFuenteId(fuente.getFuenId());
                        for (FuenteArticulo fa : fuenteArticulos) {
                            databaseManager.delete(fa);
                        }
                        databaseManager.delete(fuente);
                        Toast.makeText(getApplicationContext(), "Elemento eliminado exitosamente.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }).show();
            }
        });

        famOpciones.setClosedOnTouchOutside(true);

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

    private void fillMunicipio() {
        List<Municipio> municipios = databaseManager.listaMunicipioFuente();
        if(municipios != null){
            ArrayAdapter<Municipio> dataAdapter = new ArrayAdapter<Municipio>(this,
                    android.R.layout.simple_spinner_item, municipios);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spMunicipio.setAdapter(dataAdapter);
        }else{
            Toast.makeText(getApplication().getApplicationContext(), "No se encontraron ciudades cargadas", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean isAsignadoFuenteArticuloByTireId(List<FuenteArticuloDistrito> fuenteArticulo, Long tireId){
        if(fuenteArticulo==null){
            return false;
        }
        for(FuenteArticuloDistrito f: fuenteArticulo){
            if(f.getTireId().equals(tireId)){
                return true;
            }
        }
        return false;
    }
}
