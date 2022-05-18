package co.gov.dane.sipsa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.gov.dane.sipsa.adapter.ObservacionesRecyclerAdapter;
import co.gov.dane.sipsa.backend.Database;
import co.gov.dane.sipsa.backend.dao.Fuente;
import co.gov.dane.sipsa.backend.model.ObservacionElem;
import co.gov.dane.sipsa.interfaces.OnItemClick;

public class ListaObservacionActivity extends AppCompatActivity implements OnItemClick {

    public RadioButton rbRelativas;
    public RadioButton rbExistentes;
    public RadioButton rbTodas;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    public Fuente fuente;
    public  String currentFactor;

    public ObservacionesRecyclerAdapter observacionAdapter;

    public boolean saveOnResult = false;
    private List<ObservacionElem> observaciones;
    private String tipoNovedad;

    private ImageView atrasListaObservaciones;
    private Mensajes msj;
    private TextView title, subtitle;
    private LinearLayout agregarObservacion, nuevoObservacion;
    public static Database databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_observacion);


        atrasListaObservaciones = (ImageView) findViewById(R.id.atrasListaObservaciones);
        agregarObservacion = (LinearLayout) findViewById(R.id.agregarObservacion);
        nuevoObservacion = (LinearLayout) findViewById(R.id.nuevoObservacion);
        title = (TextView) findViewById(R.id.title);
        subtitle = (TextView) findViewById(R.id.subtitle);

        databaseManager = new Database(ListaObservacionActivity.this);
        msj = new Mensajes(ListaObservacionActivity.this);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {

                if (extras.getString("tipoNovedad") != null) {
                    tipoNovedad = extras.getString("tipoNovedad");
                    fuente = (Fuente) extras.getSerializable("fuente");
                    currentFactor = (String)extras.getSerializable("factor");
                }
                if (extras.getBoolean("saveOnResult")) {
                    saveOnResult = extras.getBoolean("saveOnResult");
                    fuente = (Fuente) extras.getSerializable("fuente");
                    currentFactor = (String)extras.getSerializable("factor");
                }
            }
        } else {


            if (savedInstanceState.getBoolean("saveOnResult")) {
                saveOnResult = savedInstanceState.getBoolean("saveOnResult");
            }
        }

        title.setText(fuente.getFuenNombre());
        subtitle.setText(currentFactor);

        observaciones = databaseManager.listaObservaciones(tipoNovedad);
        observacionAdapter = new ObservacionesRecyclerAdapter(observaciones);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listaObervaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(observacionAdapter);

        observacionAdapter.setItemClick(this);

        rbExistentes = (RadioButton) findViewById(R.id.rbExistentes);
        rbRelativas = (RadioButton) findViewById(R.id.rbRelativas);
        rbTodas  = (RadioButton) findViewById(R.id.rbTodas);

        rbExistentes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    observacionAdapter.swap(observaciones);
                    observacionAdapter.filterDataNovedad("");
                }

            }
        });

        rbTodas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    observaciones = databaseManager.listaObservaciones(tipoNovedad);
                    observacionAdapter.swap(observaciones);
                    observacionAdapter.filterDataNovedad("");
                }
            }
        });


        agregarObservacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean swObservacion = false;
                ObservacionElem currentObservacion = null;
                for (ObservacionElem o : observacionAdapter.mItems) {
                    if (o.isChecked() !=null && o.isChecked()) {
                        currentObservacion = o;
                        swObservacion = true;
                        break;
                    }
                }
                if (!swObservacion) {
                    msj.dialogoMensajeError("Seleccione Una ArtiCaraValores");
                    return;
                }
                Intent resultData = new Intent();
                resultData.putExtra("vinculado", true);
                resultData.putExtra("observacion", currentObservacion);
                resultData.putExtra("saveOnResult", saveOnResult);
                setResult(RESULT_OK, resultData);

                finish();
            }
        });

        nuevoObservacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, NuevaObservacionActivity.class);
                intent.putExtra("novedad",tipoNovedad);
                intent.putExtra("fuente",fuente);
                intent.putExtra("factor", currentFactor);
                context.startActivity(intent);
            }
        });

        atrasListaObservaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    @Override
    public void onItemClicked(Object o) {

    }

    @Override
    protected void onResume() {
        observacionAdapter.swap(databaseManager.listaObservaciones(tipoNovedad));

        super.onResume();
    }

}