package co.gov.dane.sipsa;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.gov.dane.sipsa.backend.Database;
import co.gov.dane.sipsa.backend.dao.Fuente;
import co.gov.dane.sipsa.backend.dao.FuenteArticulo;
import co.gov.dane.sipsa.backend.dao.TipoRecoleccion;
import co.gov.dane.sipsa.backend.model.Municipio;

public class FuenteDetalleActivity extends AppCompatActivity {

    public HashMap<Long, TipoRecoleccion> listTipoRecoleccion;
    private List<FuenteArticulo> fuenteArticulos ;
    private Municipio municipio;
    public Boolean isEditing =false;

    private ImageView atrasFuente;
    public LinearLayout llFuenteDetalle, guardar_formulario_fuente;

    private Database db;
    private Util util;
    private Mensajes msj;
    private Session session;

    public Fuente fuente;
    public EditText etNombre, etDireccion, etInformante,  etEmail, etTelefono ;
    public TextView tvMunicipio, tvListadoRecoleccionEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuente_detalle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new Database(FuenteDetalleActivity.this);
        util=new Util();
        msj=new Mensajes(FuenteDetalleActivity.this);
        session=new Session(FuenteDetalleActivity.this);

        atrasFuente = findViewById(R.id.atrasFuente);

        atrasFuente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        listTipoRecoleccion = new HashMap<>();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras.getSerializable("municipio") != null) {
                municipio = (Municipio) extras.getSerializable("municipio");
            }
            if(extras == null) {
                if ( fuente == null) {
                    fuente = new Fuente();
                    isEditing = false;
                }
            } else {

                if (extras.getSerializable("fuente") != null) {
                    fuente = (Fuente) extras.getSerializable("fuente");
                    isEditing  =true;
                }
            }
        } else {
            fuente= (Fuente) savedInstanceState.getSerializable("fuente");
            municipio= (Municipio) savedInstanceState.getSerializable("municipio");
        }

        llFuenteDetalle = (LinearLayout) findViewById(R.id.llFuenteDetalle);
        guardar_formulario_fuente = (LinearLayout) findViewById(R.id.guardar_formulario_fuente);

        etNombre = (EditText) findViewById(R.id.etNombre);
        etDireccion = (EditText) findViewById(R.id.etDireccion);
        etInformante = (EditText) findViewById(R.id.etInformante);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etTelefono = (EditText) findViewById(R.id.etTelefono);
        tvMunicipio =(TextView) findViewById(R.id.tvMunicipio);
        tvListadoRecoleccionEmpty =(TextView) findViewById(R.id.tvListadoRecoleccionEmpty);
        tvListadoRecoleccionEmpty.setVisibility(View.GONE);

        if (fuente != null) {
            etNombre.setText(fuente.getFuenNombre());
            etDireccion.setText(fuente.getFuenDireccion());
            etInformante.setText(fuente.getFuenNombreInformante());
            etEmail.setText(fuente.getFuenEmail());
            etTelefono.setText(fuente.getFuenTelefono());
            tvMunicipio.setText(fuente.getMuniNombre());
        }

        if (municipio != null) {
            tvMunicipio.setText(municipio.getMuniNombre());
        }

        if(isEditing){
            etNombre.setEnabled(false);
        }

        if (fuente != null) {
            fuenteArticulos = db.getFuenteArticuloByFuenteId(fuente.getFuenId());

            List<TipoRecoleccion> tipoRecoleccionList = db.listaTipoRecoleccion();
            LinearLayout llTipoRecoleccion = new LinearLayout(getApplicationContext());
            llTipoRecoleccion.setOrientation(LinearLayout.VERTICAL);
            Boolean entro = false;
            for (final TipoRecoleccion t : tipoRecoleccionList) {
                if (!isAsignadoFuenteArticuloByTireId(fuenteArticulos, t.getTireId())) {
                    CheckBox cb = new CheckBox(getApplicationContext());
                    cb.setTextColor(Color.BLACK);
                    cb.setTag(t);
                    cb.setText(t.getTireNombre());
                    llTipoRecoleccion.addView(cb);
                    entro = true;

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
                if(!entro){
                    tvListadoRecoleccionEmpty.setVisibility(View.VISIBLE);
                }else{
                    tvListadoRecoleccionEmpty.setVisibility(View.GONE);
                }
            }
            if(!entro){
                tvListadoRecoleccionEmpty.setVisibility(View.VISIBLE);
            }
            llFuenteDetalle.addView(llTipoRecoleccion);
        }else {
            List<TipoRecoleccion> tipoRecoleccionList = db.listaTipoRecoleccion();
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
            tvListadoRecoleccionEmpty.setVisibility(View.GONE);
            llFuenteDetalle.addView(llTipoRecoleccion);
        }

        guardar_formulario_fuente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean error = false;

                String nombre = etNombre.getText().toString();
                String direccion = etDireccion.getText().toString();
                String informante = etInformante.getText().toString();

                if(nombre == "" || nombre == null){
                    error = true;
                }

                if (error) {
                    msj.dialogoMensajeError(getString(R.string.error_validacion));
                }else{
                    ContentValues values = new ContentValues();
                    if (fuente != null) {
                        values = new ContentValues();
                        values.put("FUEN_NOMBRE",etNombre.getText().toString());
                        values.put("FUEN_DIRECCION",etDireccion.getText().toString());
                        values.put("FUEN_NOMBRE_INFORMANTE",etInformante.getText().toString());
                        values.put("FUEN_TELEFONO",etTelefono.getText().toString());
                        values.put("FUEN_EMAIL",etEmail.getText().toString()  );
                        db.save("FUENTE", values, false, " FUEN_ID = "+fuente.getFuenId());


                        for (Map.Entry<Long, TipoRecoleccion> entry : listTipoRecoleccion.entrySet()) {
                            values = new ContentValues();
                            values.put("FUEN_DIRECCION",etDireccion.getText().toString());
                            values.put("FUEN_EMAIL",etEmail.getText().toString());
                            values.put("FUEN_NOMBRE_INFORMANTE",etInformante.getText().toString());
                            values.put("FUEN_NOMBRE",etNombre.getText().toString());
                            values.put("FUEN_ID",fuente.getFuenId().toString()  );
                            values.put("FUEN_TELEFONO",etTelefono.getText().toString()  );
                            values.put("MUNI_ID",fuente.getMuniId().toString()  );
                            values.put("MUNI_NOMBRE",fuente.getMuniNombre().toString()  );
                            values.put("TIRE_ID",entry.getValue().getTireId()  );
                            values.put("TIRE_NOMBRE",entry.getValue().getTireNombre()  );
                            values.put("PRRE_FECHA_PROGRAMADA",new Date().getTime() );

                            db.save("FUENTE_ARTICULO", values, true, null);
                        }
                        msj.dialogoMensaje("Fuente Editada");
                        onBackPressed();
                    }else{
                        if (fuente == null) {
                            if (listTipoRecoleccion.size() == 0 && !isEditing) {
                                Toast.makeText(getApplicationContext(), "Por favor seleccione al menos un factor", Toast.LENGTH_LONG).show();
                                return;
                            }
                            values = new ContentValues();
                            values.put("FUEN_NOMBRE",etNombre.getText().toString());
                            values.put("FUEN_DIRECCION",etDireccion.getText().toString());
                            values.put("FUEN_NOMBRE_INFORMANTE",etInformante.getText().toString());
                            values.put("FUEN_TELEFONO",etTelefono.getText().toString());
                            values.put("FUEN_EMAIL",etEmail.getText().toString()  );
                            values.put("MUNI_NOMBRE",municipio.getMuniNombre().toString()  );
                            values.put("MUNI_ID",municipio.getMuniId().toString()  );
                            Long idFuente = db.save("FUENTE",values, true, null);

                            for (Map.Entry<Long, TipoRecoleccion> entry : listTipoRecoleccion.entrySet()) {
                                values = new ContentValues();
                                values = new ContentValues();
                                values.put("FUEN_DIRECCION",etDireccion.getText().toString());
                                values.put("FUEN_EMAIL",etEmail.getText().toString());
                                values.put("FUEN_NOMBRE_INFORMANTE",etInformante.getText().toString());
                                values.put("FUEN_NOMBRE",etNombre.getText().toString());
                                values.put("FUEN_ID",idFuente  );
                                values.put("FUEN_TELEFONO",etTelefono.getText().toString()  );
                                values.put("MUNI_ID",municipio.getMuniId().toString()  );
                                values.put("MUNI_NOMBRE",municipio.getMuniNombre().toString()  );
                                values.put("TIRE_ID",entry.getValue().getTireId()  );
                                values.put("TIRE_NOMBRE",entry.getValue().getTireNombre()  );
                                values.put("PRRE_FECHA_PROGRAMADA",new Date().getTime() );

                                db.save("FUENTE_ARTICULO", values, true, null);
                            }
                            msj.dialogoMensaje( "Fuente Creada");
                            onBackPressed();
                        }
                    }
                }
            }
        });


    }


    /**
     * Metodo que busc alas fuentes asignadas
     * @param fuenteArticulo
     * @param tireId
     * @return
     */
    private Boolean isAsignadoFuenteArticuloByTireId(List<FuenteArticulo> fuenteArticulo, Long tireId){
        if(fuenteArticulo==null){
            return false;
        }
        for(FuenteArticulo f: fuenteArticulo){
            if(f.getTireId().equals(tireId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(FuenteDetalleActivity.this,MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

}