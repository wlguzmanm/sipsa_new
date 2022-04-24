package gov.dane.sipsa.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import java.io.Serializable;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import gov.dane.sipsa.R;
import gov.dane.sipsa.adapter.FuentesRecyclerAdapterDistrito;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.Configuracion;
import gov.dane.sipsa.dao.FuenteDistrito;
import gov.dane.sipsa.dao.Usuario;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.model.Municipio;
import gov.dane.sipsa.model.ParametrosDistrito;
import gov.dane.sipsa.model.Status;
import gov.dane.sipsa.model.StatusFile;
import gov.dane.sipsa.model.Zona;
import gov.dane.sipsa.service.RestServices;
import gov.dane.sipsa.utils.Util;

/**
 * Created by mguzman on 20/03/2019.
 */
public class MainActivity02 extends App {

    private DrawerLayout mDrawerLayout;
    private EditText edtSeach;
    private MenuItem mSearchAction;
    private ProgressDialog pdialog;
    private boolean isSearchOpened = false;
    public static DatabaseManager databaseManager;
    public RecyclerView recyclerView;
    public FuentesRecyclerAdapterDistrito fuenteAdapter;
    public Spinner spPeriodo;
    public Spinner spZona;
    public Spinner spMunicipio;
    public Date ahora = new Date();
    public SimpleDateFormat formateador = new SimpleDateFormat("MM-yyyy");
    public String [] anoMes={formateador.format(ahora)};

    public Spinner spIndice;
    ArrayList<FuenteDistrito> fuentes;
    ArrayList<FuenteDistrito> tempFuentes;
    public ArrayAdapter<Zona> zonaAdapter;
    public ArrayList<Zona> zonas = new ArrayList<>();
    public ArrayAdapter<Municipio> municipioAdapter;
    public List<Municipio> municipios= new ArrayList<>();

    public TextView tvCompletas;
    public TextView tvPendientes;
    public TextView tvTotal;
    public ProgressBar progressBar;
    private FloatingActionButton fabCrearFuente;
    private final String SALIR = "SALIR";
    private final String CARGA_RECOLECCION = "CARGA A DMC";
    private final String ENVIO_DIARIO_DB = "ENVIO DIARIO A DB";
    private final String SETTINGS = "CONFIGURACIONES";
    AsyncTask<Void, Void, Status> task = null;

    static final Integer FUENTE_CREADA = 99;
    static final Integer TRANSMISION = 10;
    static final Integer SETTINGS_ACTIVITY = 12;
    private boolean transmisionLocal;
    Usuario usuario = new Usuario();

    private String currentMunicipioId="";
    private String currentMunicipio="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main02);


        tvCompletas = (TextView) findViewById(R.id.tvCompletas);
        tvPendientes = (TextView) findViewById(R.id.tvPendientes);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        spMunicipio = (Spinner) findViewById(R.id.spMunicipio);

        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.accent), PorterDuff.Mode.SRC_IN);

        databaseManager = DatabaseManager.getInstance(this);
        usuario = databaseManager.getUsuario();
        calcularEstadoFuentes();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.textToolbar);
        setSupportActionBar(toolbar);
        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        final Menu menu = navigationView.getMenu();

        menu.add(CARGA_RECOLECCION);
        menu.add(ENVIO_DIARIO_DB);
        menu.add(SETTINGS);
        menu.add(SALIR);

        //Datos pruebas para validar acceso.se debe quitar cuando funcione la db
        Usuario usuario = new Usuario();
        usuario.setClave("prueba");
        usuario.setNombrePerfil("SUPERVISOR");
        usuario.setPerfID(1L);
        usuario.setUspeID(1L);
        usuario.setUspeID(1L);
        usuario.setUsuaId(1L);
        usuario.setUsuario("Wilhelm");

        //currentUsuario = databaseManager.getUsuario();
        currentUsuario = usuario;


        TextView tvNombreUsuario = (TextView) mDrawerLayout.findViewById(R.id.tvNombreUsuario);
        TextView tvNombrePerfil = (TextView) mDrawerLayout.findViewById(R.id.tvNombrePerfil);

        tvNombreUsuario.setText(currentUsuario.getUsuario().toString());
        tvNombrePerfil.setText("RECOLECTOR");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                showActivity(menuItem.getTitle().toString());
                return true;
            }
        });

        fabCrearFuente = (FloatingActionButton) findViewById(R.id.crearFuenteDistrito);

        fabCrearFuente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, FuenteDetalleDistritoActivity.class);
                intent.putExtra("municipio", (Serializable) spMunicipio.getSelectedItem());
                context.startActivity(intent);
            }
        });

        databaseManager = DatabaseManager.getInstance(this);
        fuenteAdapter = new FuentesRecyclerAdapterDistrito(fuentes);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewDistrito);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(fuenteAdapter);

        Spinner periodo = (Spinner) mDrawerLayout.findViewById(R.id.spPeriodo);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.spinner_item, anoMes);
        periodo.setAdapter(adaptador);

        municipios = databaseManager.listaMunicipioFuenteDistrito();
        if(municipios!=null){
            fabCrearFuente.setEnabled(true);
            municipioAdapter = new ArrayAdapter<Municipio>(this, R.layout.spinner_item_white, municipios);
            municipioAdapter.setDropDownViewResource(R.layout.spinner_item);

            spMunicipio.setAdapter(municipioAdapter);
            spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    actualizarFuentes(((Municipio)spMunicipio.getSelectedItem()).getMuniId());
                    currentMunicipioId=((Municipio)spMunicipio.getSelectedItem()).getMuniId().toString();
                    currentMunicipio=((Municipio)spMunicipio.getSelectedItem()).getMuniNombre().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }

            });

        }else{
            fabCrearFuente.setEnabled(false);
        }
    }

    @Override
    protected void onPostExecute(Status result) {

    }


    public void calcularEstadoFuentes() {

            Integer completas = databaseManager.getElementosByEstadosDistrito("C");
            Integer total = databaseManager.getElementosByEstadosDistrito("T");
            Integer pendientes = total - completas;

            tvCompletas.setText(completas.toString());
            tvPendientes.setText(pendientes.toString());
            tvTotal.setText(total.toString());

            Double progress = 0.0;
            if (completas != 0) {
                progress = (completas.doubleValue() / total.doubleValue()) * 100;
            }
            progressBar.setProgress(progress.intValue());

    }

//TODO: TENER ESTE PENDIENTE
    public void showActivity(String selectItem) {
        Intent intent;
        Context context = getApplicationContext();
        switch (selectItem) {
            case CARGA_RECOLECCION:
                cargaDistrito();
                break;
            case ENVIO_DIARIO_DB:
                intent = new Intent(context, ResumenEnvioDistritoActivity.class);
                (MainActivity02.this).startActivityForResult(intent, TRANSMISION);  //TODO:HABILITAR

               /* new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("")
                        .setMessage("DESHABILITADA TEMPORALMENTE")
                        .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();*/
               /*intent = new Intent(this, ResumenFuentesDistritoActivity.class);
                startActivity(intent);*/
                break;
            case SETTINGS:
                intent = new Intent(context, SettingsActivityDistrito.class);
                (MainActivity02.this).startActivityForResult(intent, SETTINGS_ACTIVITY);
                break;
            case SALIR:
                onBackPressed();
                break;

            default:
                break;
        }

    }


    public void taskCargarDistrito() {
        task = new AsyncTask<Void, Void, Status>() {
            @Override
            protected void onPreExecute() {
                pdialog = ProgressDialog.show(MainActivity02.this, "",
                        "Actualizando Base de Datos, espere un momento..");
                super.onPreExecute();
            }

            @Override
            protected gov.dane.sipsa.model.Status doInBackground(Void... params) {
                gov.dane.sipsa.model.Status status = new gov.dane.sipsa.model.Status();

                if (transmisionLocal == true) {
                    gov.dane.sipsa.model.Status status1 = new gov.dane.sipsa.model.Status();
                    File fileIndices = null;
                    String[] files = new File(getReceptionPath()).list();

                    if (status1.getType().equals(gov.dane.sipsa.model.Status.StatusType.OK)) {

                        File fileRecoleccion = null;
                        files = new File(getReceptionPath()).list();

                        for (String nameFile : files) {
                            if (nameFile.split("_")[0].equals("R") &&
                                    nameFile.split("_")[1].equals("IN01")) {
                                fileRecoleccion = new File(getReceptionPath() + "/" + nameFile);
                                break;
                            }
                        }
                        status1 = procesarArchivos(fileRecoleccion);
                    }


                    status = status1;
                }  else {
                    if (!Util.isNetworkAvailable(getApplicationContext())) {
                        status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                        status.setDescription("No tiene acceso a internet, verifique su conexión");
                        return status;
                    }

                    if (currentUsuario != null) {
                        ParametrosDistrito parametrosDistrito = new ParametrosDistrito();


                        parametrosDistrito = RestServices.getInstance().obtenerDistrito(currentUsuario.getUspeID().toString());

                        if (parametrosDistrito.getStatus().getType().equals(gov.dane.sipsa.model.Status.StatusType.OK)) {
                            databaseManager.dropDatabaseDistrito();
                            databaseManager.fillParametrosDistrito(parametrosDistrito);
                        }
                        status = parametrosDistrito.getStatus();

                    } else {
                        status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                        status.setDescription("Usuario invalido");
                    }
                }
                return status;
            }

            @Override
            protected void onPostExecute(gov.dane.sipsa.model.Status result) {
                pdialog.dismiss();

                if (result.getType().equals(gov.dane.sipsa.model.Status.StatusType.OK)) {
                    municipios = databaseManager.listaMunicipioFuenteDistrito();
                    actualizarMunicipio();


                }

                showAlert(result.getDescription());
                super.onPostExecute(result);
            }
        };
        task.execute();
    }


    public void actualizarMunicipio() {

        if (municipios !=null) {

            municipios.clear();
            List<Municipio> indiceList = databaseManager.listaMunicipioFuenteDistrito();

            if (indiceList != null) {
                fabCrearFuente.setEnabled(true);
                if (municipioAdapter == null) {
                    municipioAdapter = new ArrayAdapter<Municipio>(this, R.layout.spinner_item_white, municipios);
                    municipioAdapter.setDropDownViewResource(R.layout.spinner_item);
                    municipioAdapter.notifyDataSetChanged();
                    spMunicipio.setAdapter(municipioAdapter);
                }

                municipioAdapter.clear();
                municipioAdapter.addAll(indiceList);
                municipioAdapter.notifyDataSetChanged();
                if(((Municipio)spMunicipio.getSelectedItem())!=null){
                    actualizarFuentes(((Municipio)spMunicipio.getSelectedItem()).getMuniId());
                }
            }else{
                municipioAdapter.clear();
                fabCrearFuente.setEnabled(false);
            }
            spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    actualizarFuentes(((Municipio)spMunicipio.getSelectedItem()).getMuniId());
                    fuenteAdapter.actualizarMunicipio((Municipio)spMunicipio.getSelectedItem());
                }


                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }

            });

        }
    }

    public void actualizarFuentes(String idMunicipio) {

            fuentes = (ArrayList<FuenteDistrito>) databaseManager.listaFuenteArticuloDistrito(idMunicipio);
            tempFuentes = fuentes;
            fuenteAdapter.clear();
            fuenteAdapter.swap(fuentes);
            zonas.clear();

    }

    public void cargaDistrito() {

        new AlertDialog.Builder(MainActivity02.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("")
                .setMessage("¿Esta seguro de obtener su recolección, podria perder datos recolectados?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskCargarDistrito();
                    }

                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "De acuerdo no se actualizará.",Toast.LENGTH_LONG).show();
                    }

                })
                .show();
    }



    public Status procesarArchivos(File fileRecoleccion) {
        Status status = new Status();
        ParametrosDistrito parametrosDistrito = new ParametrosDistrito();
        String[] files = null;

        String insumo = "";

        if (fileRecoleccion == null) {
            status.setType(Status.StatusType.ERROR);
            status.setDescription("No se encuentra archivo Recoleccion para ser cargado.");
        } else {

            String folderName = fileRecoleccion.getParent()  + "/" + Calendar.getInstance().getTime().getTime();
            File path = new File(folderName);
            path.getParentFile().mkdirs();
            StatusFile statusFile = Util.zipExtracAll(fileRecoleccion, getFilePassword(), folderName);

            if (statusFile.getType().equals(Status.StatusType.OK)) {
                files = new File(folderName).list();

                for (String nameFile : files) {
                    String[] nombre = nameFile.split("_");
                    if (nombre[0].equals("DISTRITO" + currentUsuario.getUspeID().toString())) {

                        final File file = new File(folderName + "/" + nameFile);
                        try {
                            InputStream is = new FileInputStream(file);

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
                            if(nombre[0].equals("DISTRITO" + currentUsuario.getUspeID().toString())){
                                parametrosDistrito = gson.fromJson(responseStrBuilder.toString(), ParametrosDistrito.class);
                                insumo = "DISTRITO";
                            }

                        } catch (IOException e) {
                            status.setType(Status.StatusType.ERROR);
                            status.setDescription("Ocurrió un error leyendo el archivo.");
                            break;
                        }
                    }
                }
                if(!insumo.equals("")){
                     if(insumo.equals("DISTRITO")){
                        databaseManager.fillParametrosDistrito(parametrosDistrito); //cargar recoleccion en la db
                    }
                }
                status.setType(statusFile.getType());
                status.setDescription("Carga DMC exitosa.");
            }
            File tmp_folder = new File(folderName);
            Util.deleteFolderRecursive(tmp_folder);
        }
        return status;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }


    protected void handleMenuSearch() {
        ActionBar action = getSupportActionBar(); //get the actionbar

        if (isSearchOpened) { //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            //action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(android.R.drawable.ic_menu_search);

            isSearchOpened = false;
            doSearch("");

        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    doSearch(edtSeach.getText().toString());

                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        return true;
                    }
                    return false;
                }
            });

            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);
            //add the close icon
            mSearchAction.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
            isSearchOpened = true;
        }
    }

    private void doSearch(String s) {
        fuenteAdapter.filterData(s);
    }

    @Override
    public void onBackPressed() {
        if (isSearchOpened) {
            handleMenuSearch();
            return;
        }
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("")
                .setMessage("¿Realmente desea salir de la aplicación?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    protected void onResume() {
        if(((Municipio)spMunicipio.getSelectedItem())!= null){
            actualizarFuentes(((Municipio)spMunicipio.getSelectedItem()).getMuniId());
        }
        actualizarMunicipio();

        calcularEstadoFuentes();
        Configuracion configuracion = databaseManager.getConfiguracion();
        if (configuracion == null) {
            configuracion = new Configuracion();
            configuracion.setTransmisionLocal(false);
            databaseManager.save(configuracion);
        }
        transmisionLocal = configuracion.getTransmisionLocal();
        super.onResume();
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

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FUENTE_CREADA && resultCode == RESULT_OK) {
            FuenteDistrito fuente = (FuenteDistrito) data.getSerializableExtra("fuente");
            actualizarFuentes(((Municipio)spMunicipio.getSelectedItem()).getMuniId());

        } else if (requestCode == TRANSMISION && resultCode == RESULT_OK) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Envio de información exitosa.", Snackbar.LENGTH_LONG)
                    .show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
