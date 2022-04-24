package gov.dane.sipsa.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import gov.dane.sipsa.R;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.manager.IDatabaseManager;
import gov.dane.sipsa.model.Status;
import gov.dane.sipsa.model.StatusFile;
import gov.dane.sipsa.model.Usuario;
import gov.dane.sipsa.service.RestServices;
import gov.dane.sipsa.utils.Util;

/**
 * Created by andreslopera on 4/12/16.
 */
public class LoginActivity  extends App {

    public static IDatabaseManager databaseManager;
    AsyncTask<String, String, Status> task=null;
    private ProgressDialog pdialog;

    public EditText etNombreUsuario;
    public EditText etClave;
    public Button btnEntrar;
    public Spinner insumos;
    public String[] aplicaciones ={"Seleccione...", "Distrito de Riego","Insumos", "Insumos01"};
    public String recoleccion;

    public gov.dane.sipsa.model.Usuario usuario = new gov.dane.sipsa.model.Usuario();
    gov.dane.sipsa.dao.Usuario userdb =new gov.dane.sipsa.dao.Usuario();
    private String nombreUsuario = "";
    private String clave = "";



    private static final int REQUEST_WRITE_STORAGE = 112;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }*/
        databaseManager = DatabaseManager.getInstance(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String folderName = getRootPath();
        File path = new File(folderName);
        path.mkdirs();

        String transmision_path = getTransmisionPath();
        File path_transmition = new File(transmision_path);
        path_transmition.mkdir();

        String reception_path = getReceptionPath();
        File path_reception = new File(reception_path);
        path_reception.mkdir();



        setContentView(R.layout.login_layout);

        etNombreUsuario = (EditText) findViewById(R.id.etNombreUsuario);
        userdb=databaseManager.getUsuario();
        etClave = (EditText) findViewById(R.id.etClave);
        //etClave.setText("1110478239");
        //etClave.setText("30051355");
        etClave.setText("");

        if(userdb != null){
            etNombreUsuario.setText(userdb.getUsuario());
            //TODO: QUITAR esto
            etClave.setText("32180978");
        }
        else {
            //etNombreUsuario.setText("");
            //TODO: QUITAR esto
            etNombreUsuario.setText("EMECHEVERRYP");
            etClave.setText("32180978");
        }

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        TextView tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvVersion.setText("Versión " + VERSION);


        insumos = (Spinner)findViewById(R.id.spListaRecoleccion);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, aplicaciones);
        insumos.setAdapter(adaptador);

        insumos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        recoleccion = aplicaciones[i].toString();

                    case 1:
                        recoleccion = aplicaciones[i].toString();
                        break;

                    case 2:
                        recoleccion = aplicaciones[i].toString();
                        break;

                    case 3:
                        recoleccion = aplicaciones[i].toString();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        final Button btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombreUsuario = etNombreUsuario.getText().toString().toUpperCase();
                clave = etClave.getText().toString();




                task = new AsyncTask<String, String, Status>() {

                    @Override
                    protected void onPreExecute() {
                        pdialog = ProgressDialog.show(LoginActivity.this, "",
                                "Verificando Usuario, espere un momento..");
                        super.onPreExecute();
                    }

                    @Override
                    protected gov.dane.sipsa.model.Status doInBackground(String... params) {
                        gov.dane.sipsa.model.Status status = new gov.dane.sipsa.model.Status();
                        status.setType(gov.dane.sipsa.model.Status.StatusType.OK);
                        status.setDescription("LOGIN_SQLITE");
                        return  status;
                    }

/*                    @Override
                    protected gov.dane.sipsa.model.Status doInBackground(String... params) {

                        gov.dane.sipsa.model.Status status = new gov.dane.sipsa.model.Status();
                        String nombreUsuario = params[0];
                        String clave = params[1];


                        if (nombreUsuario.replaceAll(" ", "").equals("") || clave.replaceAll(" ", "").equals("")) {
                            status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                            status.setDescription("Por favor ingrese datos validos");
                        } else {
                            gov.dane.sipsa.dao.Usuario loginUsuario = databaseManager.getUsuario(nombreUsuario, clave);
                            if (loginUsuario != null) {
                                status.setType(gov.dane.sipsa.model.Status.StatusType.OK);
                                status.setDescription("LOGIN_SQLITE");
                            } else  if (databaseManager.getUsuario(nombreUsuario) != null) {
                                status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                                status.setDescription("Contraseña Invalida");
                            }else {
                                String[] files = new File(getReceptionPath()).list();
                                File fileAccessKey = null;

                                for (String nameFile : files) {
                                    if (nameFile.replace(".zip", "").equals("ACCESO")) {
                                        fileAccessKey = new File(getReceptionPath() + "/" + nameFile);
                                        break;
                                    }
                                }


                                if (fileAccessKey != null) {
                                    String folderName = fileAccessKey.getParent() + "/" + Calendar.getInstance().getTime().getTime();
                                    File path = new File(folderName);
                                    path.getParentFile().mkdirs();
                                    StatusFile statusFile = Util.zipExtracAll(fileAccessKey, getFilePassword(), folderName);
                                    if (statusFile.getType().equals(gov.dane.sipsa.model.Status.StatusType.OK)) {
                                        files = new File(folderName).list();

                                        try {
                                            InputStream is = new FileInputStream(folderName + "/" + files[0]);

                                            BufferedInputStream bis = null;
                                            bis = new BufferedInputStream(is);
                                            BufferedReader r = new BufferedReader(
                                                    new InputStreamReader(bis, Charset.forName("UTF-8"))
                                            );
                                            StringBuilder responseStrBuilder = new StringBuilder();
                                            int c;
                                            while ((c = r.read()) != -1) {
                                                char character = (char) c;
                                                responseStrBuilder.append(character);
                                            }

                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S", Locale.US);
                                            Gson gson = new GsonBuilder().setDateFormat(sdf.toPattern()).create();

                                            gov.dane.sipsa.dao.Usuario u = gson.fromJson(responseStrBuilder.toString(), gov.dane.sipsa.dao.Usuario.class);
                                            databaseManager.deleteAll(gov.dane.sipsa.dao.Usuario.class);
                                            databaseManager.save(u);
                                            loginUsuario = databaseManager.getUsuario(nombreUsuario, clave);
                                            if (loginUsuario != null) {
                                                usuario.setNombrePerfil(loginUsuario.getNombrePerfil());
                                                usuario.setIdPersona(loginUsuario.getUspeID());
                                                status.setType(gov.dane.sipsa.model.Status.StatusType.OK);
                                                status.setDescription("LOGIN_FILE");
                                            } else {
                                                status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                                                status.setDescription("ERROR_ACCESO_FILE");
                                            }

                                        } catch (IOException e) {
                                            status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                                            status.setDescription("Ocurrió un error leyendo el archivo.");
                                        } finally {
                                            File tmp_folder = new File(folderName);
                                            Util.deleteFolderRecursive(tmp_folder);
                                        }
                                    }


                                } else if (!Util.isNetworkAvailable(getApplicationContext())) {
                                    status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                                    status.setDescription("No tiene acceso a internet, verifique su conexión");
                                    return status;
                                } else {
                                    usuario = RestServices.getInstance().obtenerUsuario(nombreUsuario, clave);
                                    if (usuario.getIdPersona() != null) {
                                        status.setType(gov.dane.sipsa.model.Status.StatusType.OK);
                                        status.setDescription("LOGIN_SERVICE");
                                    } else {
                                        if (usuario != null && usuario.getMensaje() != null) {
                                            status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                                            status.setDescription(usuario.getMensaje().toString());
                                        } else {
                                            status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                                            status.setDescription("Usuario o Clave invalida22222");
                                        }
                                    }
                                }
                            }
                        }
                        return status;
                    }
*/


                    @Override
                    protected void onPostExecute (gov.dane.sipsa.model.Status status) {
                            pdialog.dismiss();

                            if (status.getType().equals(gov.dane.sipsa.model.Status.StatusType.OK)) {
                                if (status.getDescription().equals("LOGIN_SQLITE")) {

                                    if (recoleccion.equals("Insumos")){
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }else if (recoleccion.equals("Insumos01")){
                                        Intent intent = new Intent(getApplicationContext(), MainActivity01.class);
                                        startActivity(intent);
                                        finish();
                                    }else if (recoleccion.equals("Distrito de Riego")){
                                        //confirmMessage("Selecciono el correcto");
                                        Intent intent = new Intent(getApplicationContext(), MainActivity02.class);
                                        startActivity(intent);
                                        finish();
                                    }else if (recoleccion.equals("Seleccione...")){
                                        confirmMessage("Se debe escoger el tipo de recolección");
                                    }
                                    //Intent intent = new Intent(getApplicationContext(), EscogerAplicacionActivity.class);
                                    //startActivity(intent);
                                } else if (status.getDescription().equals("LOGIN_SERVICE") || status.getDescription().equals("LOGIN_FILE")) {
                                    confirmMessage("Al ingresar con un usuario nuevo los datos existentes seran borrados, asegurese de haber transmitido. ");
                                }
                            } else {
                                if ("ERROR_ACCESO_FILE".equals(status.getDescription().toString())) {
                                    confirmDeleteAccesoFile();
                                } else {
                                    Snackbar.make(findViewById(android.R.id.content)
                                            , status.getDescription().toString()
                                            , Snackbar.LENGTH_LONG)
                                            .show();
                                }
                            }
                            super.onPostExecute(status);
                            try {
                                this.finalize();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }

                };

                task.execute(nombreUsuario,clave);
        }
    });

    }

    @Override
    protected void onPostExecute(Status result) {

    }


    ;

    public void confirmDeleteAccesoFile() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("")
                .setMessage("Esta intentando realizar una validación local, verifique usuario y contraseña, desea borrar " +
                "la validación local?")
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Util.deleteFile(getReceptionPath(), "/ACCESO.zip");
                    }
                }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    public void confirmMessage(String message) {

        if (recoleccion.equals("Insumos")){
            databaseManager.deleteAll(gov.dane.sipsa.dao.Usuario.class);
            databaseManager.deleteAllTables();
            gov.dane.sipsa.dao.Usuario usuario2 = new gov.dane.sipsa.dao.Usuario();
            usuario2.setUsuario(nombreUsuario);
            usuario2.setClave(clave);
            usuario2.setNombrePerfil(usuario.getNombrePerfil());
            usuario2.setUspeID(usuario.getIdPersona());
            databaseManager.save(usuario2);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }else if (recoleccion.equals("Insumos01")) {
            databaseManager.deleteAll(gov.dane.sipsa.dao.Usuario.class);
            databaseManager.deleteAllTables();
            gov.dane.sipsa.dao.Usuario usuario2 = new gov.dane.sipsa.dao.Usuario();
            usuario2.setUsuario(nombreUsuario);
            usuario2.setClave(clave);
            usuario2.setNombrePerfil(usuario.getNombrePerfil());
            usuario2.setUspeID(usuario.getIdPersona());
            databaseManager.save(usuario2);
            Intent intent = new Intent(getApplicationContext(), MainActivity01.class);
            startActivity(intent);

        }else if (recoleccion.equals("Distrito de Riego")) {
            //databaseManager.dropDatabaseDistrito();
            databaseManager.deleteAll(gov.dane.sipsa.dao.Usuario.class);
            databaseManager.deleteAllDistritoTables();
            gov.dane.sipsa.dao.Usuario usuario2 = new gov.dane.sipsa.dao.Usuario();
            usuario2.setUsuario(nombreUsuario);
            usuario2.setClave(clave);
            usuario2.setNombrePerfil(usuario.getNombrePerfil());
            usuario2.setUspeID(usuario.getIdPersona());
            databaseManager.save(usuario2);
            Intent intent = new Intent(getApplicationContext(), MainActivity02.class);
            startActivity(intent);


            /*new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("")
                    .setMessage(message)
                    .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();*/

        }else if (recoleccion.equals("Seleccione...")){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("")
                    .setMessage(message)
                    .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }



    }

}
