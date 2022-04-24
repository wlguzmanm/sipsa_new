package gov.dane.sipsa.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import gov.dane.sipsa.R;
import gov.dane.sipsa.adapter.ResumenEnvioRecyclerAdapter01;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.ArticuloI01;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.PrincipalI01;
import gov.dane.sipsa.dao.Recoleccion;
import gov.dane.sipsa.dao.RecoleccionI01;
import gov.dane.sipsa.dao.Usuario;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.manager.IDatabaseManager;
import gov.dane.sipsa.model.EnvioRecoleccion;
import gov.dane.sipsa.model.EnvioRecoleccion01;
import gov.dane.sipsa.model.Status;
import gov.dane.sipsa.service.RestServices;
import gov.dane.sipsa.utils.Util;

/**
 * Created by andreslopera on 6/19/16.
 */
public class ResumenEnvioActivity01 extends App {
    private IDatabaseManager databaseManager;
    private List<ArticuloI01> articulos = new ArrayList<>();
    private List<Fuente> fuentes = new ArrayList<Fuente>();
    ResumenEnvioRecyclerAdapter01 ArticuloAdapter;
    private RecyclerView rvListaFuentes;
    private android.support.design.widget.FloatingActionButton fabTransmitir;
    AsyncTask<Void, Void, Status> task_transmitirHttp =null;
    AsyncTask<Void, Void, Status> task_descargar =null;
    private ProgressDialog pdialog;
    private boolean transmisionLocal;
    private String currentMunicipio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_fuentes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("COMUNICACIÓN");
        toolbar.setSubtitle("ENVIO DIARIO A DB");
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabTransmitir = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fabTransmitir);
        fabTransmitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transmisionLocal == true) {
                    transmitirLocal(v.getContext());
                } else {
                    transmitirHttp(v.getContext());
                }
            }
        });


        databaseManager = DatabaseManager.getInstance(this);
        articulos  = databaseManager.listaElementoByTransmitidoEstado01(false);


        transmisionLocal = databaseManager.getConfiguracion().getTransmisionLocal();

        ArticuloAdapter = new ResumenEnvioRecyclerAdapter01(articulos);
        rvListaFuentes = (RecyclerView) findViewById(R.id.rvListaFuentes);
        rvListaFuentes.setLayoutManager(new LinearLayoutManager(this));
        rvListaFuentes.setAdapter(ArticuloAdapter);
    }

    @Override
    protected void onPostExecute(Status result) {

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    private void transmitirHttp(Context context) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("")
                .setMessage("¿ Realmente desea Transmitir las fuentes ?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Util.isNetworkAvailable(getApplicationContext())) {
                            task_transmitirHttp = new AsyncTask<Void, Void, Status>() {
                                @Override
                                protected void onPreExecute() {
                                    pdialog = ProgressDialog.show(ResumenEnvioActivity01.this, "",
                                            "Enviando Recolección, espere un momento..");
                                    super.onPreExecute();
                                }

                                @Override
                                protected gov.dane.sipsa.model.Status doInBackground(Void... params) {
                                    gov.dane.sipsa.model.Status status = null;
                                    Usuario usuario = databaseManager.getUsuario();
                                    if (usuario != null) {
                                        EnvioRecoleccion01 envioRecoleccion = new EnvioRecoleccion01();
                                        envioRecoleccion.setIdPersona(usuario.getUspeID().toString());
                                        envioRecoleccion.setPrincipal(databaseManager.getPrincipalI01());
                                        envioRecoleccion.setRecoleccion(databaseManager.getRecoleccionI01());
                                        envioRecoleccion.setArtiCaraValores(databaseManager.getArtiCaraValoresI01());

                                        //Nueva tabla

                                        for (RecoleccionI01 recoleccion : envioRecoleccion.getRecoleccion()) {
                                            if(recoleccion.getInfoId()==null){
                                                recoleccion.setInfoId(9000000001L);
                                            }
                                            System.out.println("Id---   "+recoleccion.getInfoId()+"  Nombre ---"+recoleccion.getInfoNombre());
                                        }

                                        gov.dane.sipsa.model.Status _status = RestServices.getInstance().enviarRecoleccion01(envioRecoleccion, Integer.parseInt(usuario.getUspeID().toString()));

                                        if(_status==null){
                                            status = new gov.dane.sipsa.model.Status();
                                            status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                                            status.setDescription("Cargue invalido");
                                            return status;
                                        }


                                        if (_status.getType().equals(gov.dane.sipsa.model.Status.StatusType.OK)) {

                                            for (RecoleccionI01 recoleccion : envioRecoleccion.getRecoleccion()) {
                                                recoleccion.setEstado_transmitido(1);
                                                databaseManager.save(recoleccion);
                                            }

                                            if(envioRecoleccion.getPrincipal()!= null){
                                                for (PrincipalI01 principal : envioRecoleccion.getPrincipal()) {
                                                    principal.setEstado_trasmitido(1);
                                                    databaseManager.save(principal);
                                                }

                                                String stringFile = toJson(envioRecoleccion);
                                                guardarBackup(stringFile);
                                            }

                                        }
                                        return _status;
                                    } else {
                                        status = new gov.dane.sipsa.model.Status();
                                        status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                                        status.setDescription("Usuario invalido");
                                        return status;
                                    }
                                }

                                @Override
                                protected void onPostExecute(gov.dane.sipsa.model.Status result) {
                                    pdialog.dismiss();
                                    if (result != null) {
                                        showAlert(result.getDescription());
                                        if (result.getType().equals(gov.dane.sipsa.model.Status.StatusType.OK)) {

                                            Intent resultData = new Intent();
                                            setResult(RESULT_OK, resultData);
                                            finish();
                                        }
                                    }
                                    super.onPostExecute(result);
                                }
                            };

                            task_transmitirHttp.execute();
                        } else {
                            Snackbar.make(findViewById(android.R.id.content),
                                    "No tiene acceso a internet, verifique su conexión", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();

    }



    private void transmitirLocal(Context context) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("")
                .setMessage("¿ Realmente desea Transmitir las fuentes, los datos seran descargados en el telefono para su transmisión ?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        task_transmitirHttp = new AsyncTask<Void, Void, Status>() {
                            @Override
                            protected void onPreExecute() {
                                pdialog = ProgressDialog.show(ResumenEnvioActivity01.this, "",
                                        "Creando archivo, espere un momento..");
                                super.onPreExecute();
                            }

                            @Override
                            protected gov.dane.sipsa.model.Status doInBackground(Void... params) {
                                gov.dane.sipsa.model.Status status = null;
                                Usuario usuario = databaseManager.getUsuario();
                                if (usuario != null) {
                                    EnvioRecoleccion01 envioRecoleccion = new EnvioRecoleccion01();
                                    envioRecoleccion.setIdPersona(usuario.getUspeID().toString());
                                    envioRecoleccion.setPrincipal(databaseManager.getPrincipalI01());
                                    envioRecoleccion.setRecoleccion(databaseManager.getRecoleccionI01());
                                    envioRecoleccion.setArtiCaraValores(databaseManager.getArtiCaraValoresI01());
                                    String stringFile01 = toJson(envioRecoleccion);
                                    LocalDateTime Fecha = new LocalDateTime();
                                    int year = Fecha.getYear();
                                    int month = Fecha.getMonthOfYear();
                                    int day = Fecha.getDayOfMonth();
                                    String FILE_NAME = "T_IN01_"+usuario.getUsuario()+"_"+year+"_"+month+"_"+day;
                                    //Long FILE_NAME = Calendar.getInstance().getTimeInMillis();
                                    File path = new File(getTransmisionPath(), FILE_NAME.toString());
                                    path.getParentFile().mkdirs();

                                    OutputStream out = null;

                                    gov.dane.sipsa.model.Status _status = new gov.dane.sipsa.model.Status();
                                    try {
                                        String newFileName = getTransmisionPath() + "/" + FILE_NAME;
                                        out = new FileOutputStream(newFileName);
                                        out.write(stringFile01.getBytes());

                                        String[] files = new String[1];
                                        files[0] = newFileName;
                                        Util.zipOnlyFile(newFileName, newFileName + ".zip", getFilePassword() );
                                        new File(getTransmisionPath() + "/" + FILE_NAME.toString()).delete();
                                        _status.setType(gov.dane.sipsa.model.Status.StatusType.OK);
                                        _status.setDescription("Archivo de transmisión creado exitosamente.");
                                    } catch (Exception e) {
                                        _status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                                        _status.setDescription("Ocurrió un error en la creación del archivo.");
                                    }

                                    if(_status==null){
                                        status = new gov.dane.sipsa.model.Status();
                                        status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                                        status.setDescription("Cargue invalido");
                                        return status;
                                    }


                                    if (_status.getType().equals(gov.dane.sipsa.model.Status.StatusType.OK)) {

                                        for (RecoleccionI01 recoleccion : envioRecoleccion.getRecoleccion()) {
                                            recoleccion.setEstado_transmitido(1);
                                            databaseManager.save(recoleccion);
                                        }


                                        for (PrincipalI01 principal : envioRecoleccion.getPrincipal()) {

                                            principal.setEstado_trasmitido(1);
                                            databaseManager.save(principal);
                                        }

                                    }
                                    return _status;
                                } else {
                                    status = new gov.dane.sipsa.model.Status();
                                    status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                                    status.setDescription("Usuario invalido");
                                    return status;
                                }


                            }

                            @Override
                            protected void onPostExecute(gov.dane.sipsa.model.Status result) {
                                pdialog.dismiss();
                                if (result != null) {
                                    showAlert(result.getDescription());
                                    if (result.getType().equals(gov.dane.sipsa.model.Status.StatusType.OK)) {

                                        Intent resultData = new Intent();
                                        setResult(RESULT_OK, resultData);
                                        finish();
                                    }
                                }
                                super.onPostExecute(result);
                            }
                        }

                        ;

                        task_transmitirHttp.execute();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case 16908332:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void guardarBackup(String stringFile) {
        Long FILE_NAME = Calendar.getInstance().getTimeInMillis();
        Integer month = Calendar.getInstance().get(Calendar.MONTH);
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        String FOLDER_NAME = year.toString() + month.toString();

        File path = new File(getLogPath() + "/" + FOLDER_NAME, FILE_NAME.toString());
        path.getParentFile().mkdirs();

        OutputStream out = null;
        try {
            String newFileName  = getLogPath() + "/" + FOLDER_NAME +"/" + FILE_NAME;
            out = new FileOutputStream(newFileName);
            out.write(stringFile.getBytes());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    public static String toJson(Object jsonObject) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S", Locale.US);
        Gson gson = new GsonBuilder().setDateFormat(sdf.toPattern()).create();
        return gson.toJson(jsonObject);
    }

    public void showAlert(String message) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("")
                .setMessage(message)
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }

                }).show();
    }

    public void confirmDescarga(String message) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("")
                .setMessage(message)
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                }).show();
    }
}
