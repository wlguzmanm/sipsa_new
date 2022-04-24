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
import gov.dane.sipsa.adapter.FuenteResumenRecyclerAdapter;
import gov.dane.sipsa.adapter.FuenteResumenRecyclerAdapterDistrito;
import gov.dane.sipsa.adapter.ResumenEnvioRecyclerAdapter01;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.ArticuloDistrito;
import gov.dane.sipsa.dao.ArticuloI01;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.FuenteArticuloDistrito;
import gov.dane.sipsa.dao.FuenteDistrito;
import gov.dane.sipsa.dao.Principal;
import gov.dane.sipsa.dao.PrincipalI01;
import gov.dane.sipsa.dao.Recoleccion;
import gov.dane.sipsa.dao.RecoleccionDistrito;
import gov.dane.sipsa.dao.RecoleccionI01;
import gov.dane.sipsa.dao.Usuario;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.manager.IDatabaseManager;
import gov.dane.sipsa.model.EnvioRecoleccion;
import gov.dane.sipsa.model.EnvioRecoleccion01;
import gov.dane.sipsa.model.EnvioRecoleccion02;
import gov.dane.sipsa.model.Status;
import gov.dane.sipsa.service.RestServices;
import gov.dane.sipsa.utils.Util;

/**
 * Created by Marco Guzman on 12/06/19.
 */
public class ResumenEnvioDistritoActivity extends App {


    private static final String TAG = DatabaseManager.class.getCanonicalName();

    private IDatabaseManager databaseManager;
    private List<ArticuloDistrito> articulos = new ArrayList<>();
    private List<FuenteDistrito> fuentes = new ArrayList<FuenteDistrito>();
    FuenteResumenRecyclerAdapterDistrito fuenteAdapter;
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
        setContentView(R.layout.activity_resumen_fuentes_distrito);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("COMUNICACIÓN DISTRITO");
        toolbar.setSubtitle("ENVIO DIARIO A DB DISTRITO");
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabTransmitir = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fabTransmitirDistrito);
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
        fuentes  = databaseManager.listaFuentesByTransmitidoEstadoDistrito(false);

        if(databaseManager.getConfiguracion()!=null){
            transmisionLocal = databaseManager.getConfiguracion().getTransmisionLocal();
        }
        fuenteAdapter = new FuenteResumenRecyclerAdapterDistrito(fuentes);
        rvListaFuentes = (RecyclerView) findViewById(R.id.rvListaFuentes);
        rvListaFuentes.setLayoutManager(new LinearLayoutManager(this));
        rvListaFuentes.setAdapter(fuenteAdapter);
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
                                    pdialog = ProgressDialog.show(ResumenEnvioDistritoActivity.this, "",
                                            "Enviando Recolección, espere un momento..");
                                    super.onPreExecute();
                                }

                                @Override
                                protected gov.dane.sipsa.model.Status doInBackground(Void... params) {
                                    gov.dane.sipsa.model.Status status = null;
                                    Usuario usuario = databaseManager.getUsuario();
                                    if (usuario != null) {
                                        EnvioRecoleccion02 envioRecoleccion = new EnvioRecoleccion02();
                                        envioRecoleccion.idPersona = (usuario.getUspeID().toString());
                                        envioRecoleccion.Fuente = (databaseManager.listaFuenteArticuloByTransmitidoEstadoDistrito());
                                        List<RecoleccionDistrito> listado = databaseManager.listaRecoleccionTransmitirDistrito();
                                        envioRecoleccion.Recoleccion = (listado);

                                        if(listado!= null && listado.size()>0){

                                            List<FuenteDistrito> fuenteValidar = new ArrayList<FuenteDistrito>();

                                            System.out.println(toJson(envioRecoleccion.Fuente));
                                            for(FuenteArticuloDistrito fuentes: envioRecoleccion.Fuente){
                                                FuenteDistrito tmp =  new FuenteDistrito();//databaseManager.getFuenteDistritoById(nuevo.getFuenId());
                                                tmp.setFuenId(fuentes.getFuenId());
                                                tmp.setFuenNombre(fuentes.getFuenNombre());
                                                tmp.setMuniId(fuentes.getMuniId());
                                                tmp.setMuniNombre(fuentes.getMuniNombre());
                                                tmp.setTireId(fuentes.getTireId());
                                                tmp.setFuenTelefonoInformante(fuentes.getFuenTelefonoInformante());
                                                tmp.setFuenTelefono(fuentes.getFuenTelefono());
                                                tmp.setFuenDireccion(fuentes.getFuenDireccion());
                                                tmp.setFuenEmail(fuentes.getFuenEmail());
                                                tmp.setFuenNombreInformante(fuentes.getFuenNombreInformante());
                                                fuenteValidar.add(tmp);
                                                Log.d(TAG, "fuenteAll tmp-->" + tmp.getFuenId());
                                            }


                                            Boolean validacion = false;

                                            for (FuenteDistrito lista : fuenteValidar) {
                                                System.out.println(toJson(lista));
                                                if(lista.getFuenNombre()==null){
                                                    validacion = true;
                                                }else{
                                                    if(lista.getFuenNombre().equals("")){
                                                        validacion = true;
                                                    }
                                                }
                                                if(lista.getFuenDireccion()==null){
                                                    validacion = true;
                                                }else{
                                                    if(lista.getFuenDireccion().equals("")){
                                                        validacion = true;
                                                    }
                                                }
                                                if(lista.getFuenTelefonoInformante()==null ){
                                                    validacion = true;
                                                }else{
                                                    if(lista.getFuenTelefonoInformante().equals("")){
                                                        validacion = true;
                                                    }else{
                                                        if(!isNumeric(lista.getFuenTelefonoInformante())){
                                                            validacion = true;
                                                        }
                                                    }
                                                }
                                                if(lista.getFuenTelefono()==null){
                                                    validacion = true;
                                                }else{
                                                    if(lista.getFuenTelefono().equals("")){
                                                        validacion = true;
                                                    }else{
                                                        if(!isNumeric(lista.getFuenTelefono())){
                                                            validacion = true;
                                                        }
                                                    }
                                                }
                                                if(lista.getFuenEmail()==null){
                                                    validacion = true;
                                                }else{
                                                    if(lista.getFuenEmail().equals("")){
                                                        validacion = true;
                                                    }
                                                }
                                                if(lista.getFuenNombreInformante()==null){
                                                    validacion = true;
                                                }else{
                                                    if(lista.getFuenNombreInformante().equals("")){
                                                        validacion = true;
                                                    }
                                                }
                                            }

                                            if(validacion){
                                                status = new gov.dane.sipsa.model.Status();
                                                status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                                                status.setDescription("Debe diligenciar correctamente todas las fuentes a enviar. Ejemplo : teléfono solo números, nombre solo alfanumérico");
                                                return status;
                                            }else{
                                                gov.dane.sipsa.model.Status _status = RestServices.getInstance().enviarRecoleccionDistrito(envioRecoleccion, Integer.parseInt(usuario.getUspeID().toString()));
                                                //gov.dane.sipsa.model.Status _status = null;
                                                if(_status==null){
                                                    status = new gov.dane.sipsa.model.Status();
                                                    status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                                                    status.setDescription("Cargue invalido");
                                                    return status;
                                                }

                                                if (_status.getType().equals(gov.dane.sipsa.model.Status.StatusType.OK)) {
                                                    List<FuenteDistrito> fuenteAll = new ArrayList<FuenteDistrito>();
                                                    //Se recorre toda las recolecciones por fuente
                                                    for (RecoleccionDistrito recoleccion : envioRecoleccion.Recoleccion) {
                                                        //Se listan las fuentes que se estan enviandoparcial o completas
                                                        FuenteDistrito nuevo = new FuenteDistrito();
                                                        nuevo.setFuenId(recoleccion.getFuenId());
                                                        if(!fuenteAll.contains(nuevo)){
                                                            Log.d(TAG, "fuenteAll getFuenId-->" + nuevo.getFuenId());
                                                            FuenteDistrito tmp =  databaseManager.getFuenteDistritoById(nuevo.getFuenId());
                                                            Log.d(TAG, "fuenteAll tmp-->" + tmp.getFuenId());
                                                            fuenteAll.add(tmp);
                                                        }
                                                        //Se guarda la transmision para no mostrarlas
                                                        recoleccion.setTransmitido(true);
                                                        databaseManager.save(recoleccion);
                                                    }

                                                    for (FuenteDistrito lista : fuenteAll) {
                                                        List<RecoleccionDistrito> allRecolecciones = databaseManager.listaRecoleccionAllDistrito(lista.getFuenId());
                                                        Log.d(TAG, "allRecolecciones-->" + allRecolecciones.size());
                                                        Boolean faltaEnviar = false;

                                                        for (RecoleccionDistrito recoleccion :allRecolecciones) {
                                                            Log.d(TAG, "allRecolecciones-->" + recoleccion.getFuenId()+", trasmi-->"+recoleccion.getTransmitido()
                                                                    +", Futi_id --->"+recoleccion.getFutiId());
                                                            if(recoleccion.getTransmitido()== null){
                                                                faltaEnviar = true;
                                                            }else{
                                                                if (!recoleccion.getTransmitido()){
                                                                    faltaEnviar = true;
                                                                }
                                                            }
                                                        }
                                                        if(!faltaEnviar){
                                                            databaseManager.delete(lista);
                                                            for (RecoleccionDistrito recoleccion :allRecolecciones) {
                                                                Log.d(TAG, "SE ELIMINARA-->" + recoleccion.getFuenId()+", TRANSMISION-->"+recoleccion.getTransmitido()
                                                                        +", FUTI_ID --->"+recoleccion.getFutiId()+", ID--> "+recoleccion.getId());
                                                                databaseManager.delete(recoleccion);
                                                            }
                                                            databaseManager.delete(lista);
                                                        }
                                                    }

                                                    String stringFile = toJson(envioRecoleccion);
                                                    guardarBackup(stringFile);
                                                }
                                                return _status;
                                            }
                                        }else{
                                            status = new gov.dane.sipsa.model.Status();
                                            status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                                            status.setDescription("Listado vacio para envio, por favor recolectar...");
                                            return status;
                                        }
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

                                        if (result.getType().equals(gov.dane.sipsa.model.Status.StatusType.OK)) {

                                            Intent resultData = new Intent();
                                            setResult(RESULT_OK, resultData);
                                            finish();
                                        }else{
                                            showAlert(result.getDescription());
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

    private static boolean isNumeric(String cadena){
        try {
            Long.parseLong(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
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
                                pdialog = ProgressDialog.show(ResumenEnvioDistritoActivity.this, "",
                                        "Creando archivo, espere un momento..");
                                super.onPreExecute();
                            }

                            @Override
                            protected gov.dane.sipsa.model.Status doInBackground(Void... params) {
                                gov.dane.sipsa.model.Status status = null;
                                Usuario usuario = databaseManager.getUsuario();
                                if (usuario != null) {
                                    EnvioRecoleccion02 envioRecoleccion = new EnvioRecoleccion02();
                                    envioRecoleccion.idPersona = (usuario.getUspeID().toString());
                                    envioRecoleccion.Fuente = (databaseManager.listaFuenteArticuloByTransmitidoEstadoDistrito());

                                    envioRecoleccion.Recoleccion = (databaseManager.listaRecoleccionTransmitirDistrito());
                                    String stringFile = toJson(envioRecoleccion);

                                    LocalDateTime Fecha = new LocalDateTime();
                                    int year = Fecha.getYear();
                                    int month = Fecha.getMonthOfYear();
                                    int day = Fecha.getDayOfMonth();
                                    String FILE_NAME = "T_IN_D_"+usuario.getUsuario()+"_"+year+"_"+month+"_"+day;

                                    //Long FILE_NAME = Calendar.getInstance().getTimeInMillis();
                                    File path = new File(getTransmisionPath(), FILE_NAME.toString());
                                    path.getParentFile().mkdirs();

                                    OutputStream out = null;

                                    gov.dane.sipsa.model.Status _status = new gov.dane.sipsa.model.Status();
                                    try {
                                        String newFileName = getTransmisionPath() + "/" + FILE_NAME;
                                        out = new FileOutputStream(newFileName);
                                        out.write(stringFile.getBytes());

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

                                    if (_status.getType().equals(gov.dane.sipsa.model.Status.StatusType.OK)) {
                                        List<FuenteArticuloDistrito> fuentes = envioRecoleccion.Fuente;

                                        for (RecoleccionDistrito recoleccion : envioRecoleccion.Recoleccion) {
                                            recoleccion.setTransmitido(true);
                                            databaseManager.save(recoleccion);
                                        }
                                    }
                                    return _status;
                                } else

                                {
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
                       // finish();
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
