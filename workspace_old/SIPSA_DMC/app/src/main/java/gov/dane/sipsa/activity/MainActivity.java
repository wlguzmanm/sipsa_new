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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import gov.dane.sipsa.R;
import gov.dane.sipsa.adapter.FuentesRecyclerAdapter;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.Configuracion;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.Usuario;
import gov.dane.sipsa.manager.DatabaseManager;
import gov.dane.sipsa.model.Municipio;
import gov.dane.sipsa.model.ParametrosInsumos;
import gov.dane.sipsa.model.ParametrosInsumos01;
import gov.dane.sipsa.model.Status;
import gov.dane.sipsa.model.StatusFile;
import gov.dane.sipsa.model.Zona;
import gov.dane.sipsa.service.RestServices;
import gov.dane.sipsa.utils.Util;


public class MainActivity extends App {

    private DrawerLayout mDrawerLayout;
    private EditText edtSeach;
    private MenuItem mSearchAction;
    private ProgressDialog pdialog;
    private boolean isSearchOpened = false;
    public static DatabaseManager databaseManager;
    public RecyclerView recyclerView;
    public FuentesRecyclerAdapter fuenteAdapter;
    public Spinner spPeriodo;
    public Spinner spZona;
    public Spinner spMunicipio;
    public Date ahora = new Date();
    public SimpleDateFormat formateador = new SimpleDateFormat("MM-yyyy");
    public String [] anoMes={formateador.format(ahora)};

    public Spinner spIndice;
    ArrayList<Fuente> fuentes;
    ArrayList<Fuente> tempFuentes;
    public ArrayAdapter<Zona> zonaAdapter;
    public ArrayList<Zona> zonas = new ArrayList<>();
    public ArrayAdapter<Municipio> municipioAdapter;
    public List<Municipio> municipios= new ArrayList<>();

    public TextView tvCompletas;
    public TextView tvIncompletas;
    public TextView tvPendientes;
    public TextView tvTotal;
    public ProgressBar progressBar;
    private FloatingActionButton fabCrearFuente;
    private final String SALIR = "SALIR";

    private final String RESUMEN = "RESUMEN";
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
        setContentView(R.layout.activity_main);

        tvCompletas = (TextView) findViewById(R.id.tvCompletas);
        tvIncompletas = (TextView) findViewById(R.id.tvIncompletas);
        tvPendientes = (TextView) findViewById(R.id.tvPendientes);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        spMunicipio = (Spinner) findViewById(R.id.spMunicipio);
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.accent), PorterDuff.Mode.SRC_IN);

        databaseManager = DatabaseManager.getInstance(this);
        usuario = databaseManager.getUsuario();
        currentUsuario = usuario;
        calcularEstadoFuentes();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        final Menu menu = navigationView.getMenu();

//        menu.add(RESUMEN);
        menu.add(CARGA_RECOLECCION);
        menu.add(ENVIO_DIARIO_DB);
        menu.add(SETTINGS);
        menu.add(SALIR);



        TextView tvNombreUsuario = (TextView) mDrawerLayout.findViewById(R.id.tvNombreUsuario);
        TextView tvNombrePerfil = (TextView) mDrawerLayout.findViewById(R.id.tvNombrePerfil);

        tvNombreUsuario.setText(currentUsuario.getUsuario().toString());
        tvNombrePerfil.setText("RECOLECTOR");
//      tvNombrePerfil.setText(currentUsuario.getNombrePerfil().toString());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                showActivity(menuItem.getTitle().toString());
                return true;
            }
        });

        fabCrearFuente = (FloatingActionButton) findViewById(R.id.crearFuente);

        fabCrearFuente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, FuenteDetalleActivity.class);
                intent.putExtra("municipio", (Serializable) spMunicipio.getSelectedItem());
                context.startActivity(intent);
                (MainActivity.this).startActivityForResult(intent, FUENTE_CREADA);
            }
        });

        databaseManager = DatabaseManager.getInstance(this);
        fuenteAdapter = new FuentesRecyclerAdapter(fuentes);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(fuenteAdapter);

        Spinner periodo = (Spinner) mDrawerLayout.findViewById(R.id.spPeriodo);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.spinner_item, anoMes);
        periodo.setAdapter(adaptador);

        municipios = databaseManager.listaMunicipioFuente();
        if(municipios!=null){

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

        }
    }

    @Override
    protected void onPostExecute(Status result) {

    }

    public void calcularEstadoFuentes() {

            Integer completas = databaseManager.getFuentesByEstado("C");
            Integer incompletas = databaseManager.getFuentesByEstado("I");
            Integer pendientes = databaseManager.getFuentesByEstado("");
            Integer total = completas + incompletas + pendientes;

            tvCompletas.setText(completas.toString());
            tvIncompletas.setText(incompletas.toString());
            tvPendientes.setText(pendientes.toString());
            tvTotal.setText(total.toString());

            Double progress = 0.0;
            if (completas != 0) {
                progress = (completas.doubleValue() / total.doubleValue()) * 100;
            }
            progressBar.setProgress(progress.intValue());

    }





    public void showActivity(String selectItem) {
        Intent intent;
        switch (selectItem) {
            case RESUMEN:
                intent = new Intent(this, ResumenActivity.class);
                startActivity(intent);
                break;
            case CARGA_RECOLECCION:
                cargaInsumos();

                break;
            case ENVIO_DIARIO_DB:
                Context context = getApplicationContext();
                intent = new Intent(context, ResumenFuentesActivity.class);
                (MainActivity.this).startActivityForResult(intent, TRANSMISION);
//                intent = new Intent(this, ResumenFuentesActivity.class);
//                startActivity(intent);
                break;
            case SETTINGS:
                context = getApplicationContext();
                intent = new Intent(context, SettingsActivity.class);
                (MainActivity.this).startActivityForResult(intent, SETTINGS_ACTIVITY);
                break;
            case SALIR:
                onBackPressed();
                break;

            default:
                break;
        }

    }


    public void taskCargarInsumo() {
        task = new AsyncTask<Void, Void, Status>() {
            @Override
            protected void onPreExecute() {
                pdialog = ProgressDialog.show(MainActivity.this, "",
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
                                        nameFile.split("_")[1].equals("IN")) {
                                    fileRecoleccion = new File(getReceptionPath() + "/" + nameFile);
                                    break;
                                }
                            }
                             status1 = procesarArchivos(fileRecoleccion);
                    }


                    status = status1;
                } else {
                    if (!Util.isNetworkAvailable(getApplicationContext())) {
                        status.setType(gov.dane.sipsa.model.Status.StatusType.ERROR);
                        status.setDescription("No tiene acceso a internet, verifique su conexión");
                        return status;
                    }

                    if (currentUsuario != null) {
                        ParametrosInsumos parametrosInsumos = new ParametrosInsumos();

                        if (currentUsuario.getNombrePerfil().equals("SUPERVISOR")) {
                            //parametrosInsumos = RestServices.getInstance().obtenerSupervision(currentUsuario.getUsuaId().toString());
                        } else {
                            parametrosInsumos = RestServices.getInstance().obtenerInsumo(currentUsuario.getUspeID().toString());
                        }
                        if (parametrosInsumos.getStatus().getType().equals(gov.dane.sipsa.model.Status.StatusType.OK)) {
                            databaseManager.fillParametrosInsumos(parametrosInsumos);
                        }
                        status = parametrosInsumos.getStatus();

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
                    municipios = databaseManager.listaMunicipioFuente();

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
            List<Municipio> indiceList = databaseManager.listaMunicipioFuente();

            if (indiceList != null) {


                if(municipioAdapter==null){
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

            }
            spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    actualizarFuentes(((Municipio)spMunicipio.getSelectedItem()).getMuniId());
                }


                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }

            });

        }



    }


    public Status procesarArchivos(File fileRecoleccion) {
        Status status = new Status();
        ParametrosInsumos parametrosInsumos = new ParametrosInsumos();
        ParametrosInsumos01 parametrosInsumos01 = new ParametrosInsumos01();
        String[] files = null;

       String insumo = "";

        if (fileRecoleccion == null) {
            status.setType(Status.StatusType.ERROR);
            status.setDescription("No se encuentra archivo Recoleccion para ser cargado.");
        } else {

            String folderName = fileRecoleccion.getParent() + "/" + Calendar.getInstance().getTime().getTime();
            File path = new File(folderName);
            path.getParentFile().mkdirs();
            StatusFile statusFile = Util.zipExtracAll(fileRecoleccion, getFilePassword(), folderName);

            if (statusFile.getType().equals(Status.StatusType.OK)) {
                files = new File(folderName).list();

                for (String nameFile : files) {
                    String[] nombre = nameFile.split("_");
                    if (nombre[0].equals("INSUMOS" + currentUsuario.getUspeID().toString()) || nombre[0].equals("INSUMOS01" + currentUsuario.getUspeID().toString())) {

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
                            if(nombre[0].equals("INSUMOS" + currentUsuario.getUspeID().toString())){
                                parametrosInsumos = gson.fromJson(responseStrBuilder.toString(), ParametrosInsumos.class);
                                insumo = "INSUMOS";
                            }else if(nombre[0].equals("INSUMOS01" + currentUsuario.getUspeID().toString())){
                                parametrosInsumos01 =  gson.fromJson(responseStrBuilder.toString(), ParametrosInsumos01.class);
                                insumo = "INSUMOS01";
                            }

                        } catch (IOException e) {
                            status.setType(Status.StatusType.ERROR);
                            status.setDescription("Ocurrió un error leyendo el archivo.");
                            break;
                        }
                    }
                }
                if(!insumo.equals("")){
                    if(insumo.equals("INSUMOS")){
                        databaseManager.fillParametrosInsumos(parametrosInsumos); //cargar recoleccion en la db
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

    public void actualizarFuentes(String idMunicipio) {

            fuenteAdapter.clear();
            fuentes = (ArrayList<Fuente>) databaseManager.listaFuenteArticulo(idMunicipio);
            tempFuentes = fuentes;
            fuenteAdapter.swap(fuentes);
            zonas.clear();

    }

    public void cargaInsumos() {

        new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("")
                .setMessage("¿Esta seguro de obtener su recolección, podria perder datos recolectados?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskCargarInsumo();
                    }

                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showMessage("INFO", "De acuerdo no se actualizará.", recyclerView.getRootView());
                    }

                })
                .show();
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
            Fuente fuente = (Fuente) data.getSerializableExtra("fuente");
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
