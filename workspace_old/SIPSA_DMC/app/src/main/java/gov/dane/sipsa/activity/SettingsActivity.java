package gov.dane.sipsa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import gov.dane.sipsa.R;
import gov.dane.sipsa.dao.Configuracion;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.manager.IDatabaseManager;

public class SettingsActivity extends AppCompatActivity {

    public static IDatabaseManager databaseManager;
    public Configuracion configuracion;
    public Switch swTransmisionLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Configuraciones");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseManager = DatabaseManager.getInstance(this);
        configuracion = databaseManager.getConfiguracion();

        swTransmisionLocal = (Switch) findViewById(R.id.swTransmisionLocal);
        swTransmisionLocal.setChecked(configuracion.getTransmisionLocal());
        swTransmisionLocal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                configuracion.setTransmisionLocal(isChecked);
                databaseManager.save(configuracion);
            }
        });

        swTransmisionLocal.setEnabled(false); //desabilitado para no cambiar la configuración

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.home:
                onBackPressed();
                break;
            case R.id.homeAsUp:
                onBackPressed();
                break;
            case 16908332:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
