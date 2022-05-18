package co.gov.dane.sipsa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.gov.dane.sipsa.adapter.ObservacionesRecyclerAdapter;
import co.gov.dane.sipsa.backend.Database;
import co.gov.dane.sipsa.backend.dao.Fuente;
import co.gov.dane.sipsa.backend.dao.Observacion;
import co.gov.dane.sipsa.backend.dao.ObservacionDao;
import co.gov.dane.sipsa.backend.model.ObservacionElem;
import co.gov.dane.sipsa.interfaces.OnItemClick;

public class NuevaObservacionActivity extends AppCompatActivity  implements OnItemClick {

    public String novedad;
    public Fuente fuente;

    public RadioButton rbRelativas;
    public RadioButton rbExistentes;
    public RadioButton rbTodas;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    public ObservacionesRecyclerAdapter observacionAdapter;
    private EditText etNuevaObservacion;
    private String currentFactor;

    private ImageView atrasNuevaObservaciones;
    private Mensajes msj;
    private TextView title, subtitle;
    private LinearLayout guardarObservacion;
    public static Database databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_observacion);

        atrasNuevaObservaciones = (ImageView) findViewById(R.id.atrasNuevaObservaciones);
        guardarObservacion = (LinearLayout) findViewById(R.id.guardarObservacion);
        title = (TextView) findViewById(R.id.title);
        subtitle = (TextView) findViewById(R.id.subtitle);

        databaseManager = new Database(NuevaObservacionActivity.this);
        msj = new Mensajes(NuevaObservacionActivity.this);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {

                if (extras.getSerializable("fuente")!=null) {
                    fuente = (Fuente) extras.getSerializable("fuente");

                }
                if (extras.getSerializable("factor")!=null) {
                    currentFactor = (String)extras.getSerializable("factor");
                }

            } else {
                novedad = extras.getString("novedad");
                fuente = (Fuente) extras.getSerializable("fuente");
                currentFactor = (String)extras.getSerializable("factor");

            }
        } else {
            novedad = savedInstanceState.getString("novedad");
            fuente= (Fuente) savedInstanceState.getSerializable("fuente");
            currentFactor = (String)savedInstanceState.getSerializable("factor");
        }

        final List<ObservacionElem> observaciones = databaseManager.listaObservaciones(novedad);
        observacionAdapter = new ObservacionesRecyclerAdapter(observaciones);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listaNuevaObervaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(observacionAdapter);

        observacionAdapter.setItemClick(this);
        etNuevaObservacion = (EditText)findViewById(R.id.etNuevaObservacion);

        observacionAdapter.filterDataNovedad(novedad);



        guardarObservacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNuevaObservacion.getText().toString().trim().length()>0){
                    Observacion observacion = new Observacion();
                    observacion.setObseDescripcion(etNuevaObservacion.getText().toString());
                    observacion.setNovedad(novedad);

                    ContentValues values = new ContentValues();
                    values.put(ObservacionDao.Properties.Novedad.columnName,observacion.getNovedad());
                    values.put(ObservacionDao.Properties.ObseDescripcion.columnName,observacion.getObseDescripcion());
                    values.put(ObservacionDao.Properties.Novedad.columnName,observacion.getNovedad());

                    databaseManager.save("OBSERVACION", values, true, null);
                    Toast.makeText(getApplicationContext(),"Observación agregada exitosamente",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    msj.dialogoMensajeError("Por favor ingrese una observación");

                }

            }
        });

        title.setText(fuente.getFuenNombre());
        subtitle.setText(currentFactor);

        atrasNuevaObservaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onItemClicked(Object observacion) {
        etNuevaObservacion.setText(((ObservacionElem) observacion).getObseDescripcion());
    }



}