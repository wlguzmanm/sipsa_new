package gov.dane.sipsa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import gov.dane.sipsa.R;
import android.view.View;
import android.widget.Toast;


/**
 * Created by hdblanco on 4/07/17.
 */

public class EscogerAplicacionActivity extends Activity {

    Spinner insumo;
    String[] aplicaciones ={"Escoger Recolección ", "Insumos", "Insumos01"};

    @Override
    protected  void  onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insumo_insumo01);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        toolbar.setTitle("SIPSA");


        insumo = (Spinner)findViewById(R.id.listaAplicaciones);

        ArrayAdapter <String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, aplicaciones);
        insumo.setAdapter(adaptador);

        insumo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 1:
                        Toast t1 = Toast.makeText(getApplicationContext(),aplicaciones[i],Toast.LENGTH_LONG);
                        t1.show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case 2:
                        Toast t2 = Toast.makeText(getApplicationContext(),aplicaciones[i],Toast.LENGTH_LONG);
                        t2.show();
                        intent = new Intent(getApplicationContext(), MainActivity01.class);
                        startActivity(intent);
                        finish();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }



}
