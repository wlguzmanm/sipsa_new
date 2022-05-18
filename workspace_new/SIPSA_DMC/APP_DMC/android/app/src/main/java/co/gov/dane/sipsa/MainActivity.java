package co.gov.dane.sipsa;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import co.gov.dane.sipsa.Helper.RetrofitClientInstance;
import co.gov.dane.sipsa.adapter.AdapterFuente;
import co.gov.dane.sipsa.adapter.RestoreBackupAdapter;
import co.gov.dane.sipsa.backend.Database;
import co.gov.dane.sipsa.backend.dao.Fuente;
import co.gov.dane.sipsa.backend.model.EnvioFormularioViewModel;
import co.gov.dane.sipsa.backend.model.Municipio;
import co.gov.dane.sipsa.backend.model.Offline;
import co.gov.dane.sipsa.interfaces.IAuthentication;
import co.gov.dane.sipsa.backend.model.ResponseFile;
import co.gov.dane.sipsa.backend.model.RestoreBackup;
import ir.mahdi.mzip.zip.ZipArchive;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout adicionarFuente;

    private List<Fuente> fuentes = new ArrayList<>();
    private ArrayList<Fuente> tempFuentes;
    private List<Municipio> municipios= new ArrayList<>();
    Spinner spMunicipio;

    private RecyclerView recyclerView;
    private AdapterFuente mAdapter;
    private Database db;
    private AlertDialog dialog;
    private ImageView info_leyenda_opciones_fuentes;

    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 101;

    String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private Session session;
    private Mensajes msg;

    private Offline offline = new Offline();
    private String currentMunicipioId="";
    private String currentMunicipio="";

    static final Integer FUENTE_CREADA = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_ACCOUNTS);
        }
        msg = new Mensajes(MainActivity.this);
        session = new Session(MainActivity.this);
        db = new Database(MainActivity.this);

        spMunicipio = (Spinner) findViewById(R.id.spMunicipio);

        municipios = db.listaMunicipioFuente();
        if(municipios!=null){
            //Cargue de los municipio
            List<Municipio> municipio = db.listaMunicipioFuente();
            ArrayAdapter<Municipio> adapterMunicipios = new ArrayAdapter<Municipio>(this, R.layout.simple_spinner_item, municipio);
            adapterMunicipios.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spMunicipio.setAdapter(adapterMunicipios);

            spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    prepararDatos(((Municipio)spMunicipio.getSelectedItem()).getMuniId());
                    currentMunicipioId=((Municipio)spMunicipio.getSelectedItem()).getMuniId().toString();
                    currentMunicipio=((Municipio)spMunicipio.getSelectedItem()).getMuniNombre().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }
            });
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new AdapterFuente(fuentes, MainActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        info_leyenda_opciones_fuentes = (ImageView) findViewById((R.id.info_leyenda_opciones_fuentes));

        adicionarFuente = (LinearLayout) findViewById(R.id.adicionarFuente);

        SearchView searchView = (SearchView) findViewById(R.id.svSearch);
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getList(db.listaFuenteArticulo(currentMunicipioId));
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        Menu menu = navigationView.getMenu();
        MenuItem nav_camara = menu.findItem(R.id.nav_usuario);
        nav_camara.setTitle(session.getnombre());

        MenuItem nav_user = menu.findItem(R.id.nav_user);
        nav_user.setTitle(session.getusename());


        info_leyenda_opciones_fuentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(MainActivity.this.LAYOUT_INFLATER_SERVICE);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setView(inflater.inflate(R.layout.info_leyenda_opciones, null))
                        .setPositiveButton(R.string.title_aceptar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                final AlertDialog dialog = mBuilder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

        adicionarFuente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, FuenteDetalleActivity.class);
                intent.putExtra("municipio", (Serializable) spMunicipio.getSelectedItem());
                context.startActivity(intent);
                (MainActivity.this).startActivityForResult(intent, FUENTE_CREADA);
            }
        });

    }

    /**
     * Metodo que devuelve la fecha
     *
     * @return
     */
    public String getFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    /**
     * Para las opciones del menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.verTotales) {
            return true;
        } else if (id == R.id.salirModulo) {

        } else if (id == R.id.salirApp) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Confirmación");
            builder.setMessage("¿ Desea salir del aplicativo ?");
            builder.setIcon(R.drawable.ic_menu_salir);
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    offline = db.getOffline("OFFLINE", session.getusename());
                    if(offline!= null && !offline.isActivo()){
                        Mensajes mitoast =new Mensajes(MainActivity.this);
                        mitoast.generarToast("Recuerde que está en modo ONLINE y requiere nuevamente autenticación.");
                        session.borrarSession();
                    }
                    Intent mainIntent = new Intent(MainActivity.this,login.class);
                    startActivity(mainIntent);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            try{
                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }catch (Exception e){
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_backup) {
            backup_db();
        } else if (id == R.id.nav_restore) {
            restore_backup_db();
        } else if (id == R.id.nav_offline) {
            offline();
        } else if (id == R.id.nav_acerca) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("            \nInformación");
            builder.setMessage("Aplicación Desarrollada por la Coordinación de Sistema del DANE");
            builder.setIcon(R.drawable.ic_logo_dane);
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            try{
                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }catch (Exception e){
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Metodo que prepara los datos dependiendo del municipio
     * @param idMunicipio
     */
    private void prepararDatos(String idMunicipio) {
        fuentes.addAll(db.listaFuenteArticulo(idMunicipio));
        mAdapter.notifyDataSetChanged();
    }


    public boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (hasAllPermissionsGranted(grantResults)) {
        } else {
            Mensajes mensaje = new Mensajes(this);
            mensaje.generarToast("Debe aceptar todos los permisos!");
        }
    }

    /**
     * Metodo que carga la pantalla del offline
     */
    public void offline() {
        Intent activity = new Intent(MainActivity.this, ConfiguracionLocal.class);
        startActivity(activity);
        MainActivity.this.finish();
    }


    /**
     * Metodo que restaura el backup
     */
    public void restore_backup_db() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final View mView = inflater.inflate(R.layout.dialog_restore_back_up, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();

        ArrayList<RestoreBackup> restores = new ArrayList<>();
        ArrayList<RestoreBackup> listado_restore = new ArrayList<>();
        String ruta = Environment.getExternalStorageDirectory() + File.separator + "SIPSA" + File.separator + "backup";
        File dir = new File(ruta);
        if (dir.exists()) {
            String[] files = dir.list(
                    new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.endsWith(".zip");
                        }
                    });
            for (String s : files) {
                String nombre = s.split("\\.")[0];
                listado_restore.add(new RestoreBackup(0, nombre, ruta + "/" + s, false));
            }
        }

        if (listado_restore.size() > 0) {
            RestoreBackupAdapter listAdapter = new RestoreBackupAdapter(this, listado_restore);
            ListView listView = (ListView) mView.findViewById(R.id.listview_restore);
            listView.setAdapter(listAdapter);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            listAdapter.notifyDataSetChanged();
            Button btn_cerrar = (Button) mView.findViewById(R.id.cerrar_dialog_restore);
            btn_cerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        } else {
            Mensajes mensaje = new Mensajes(this);
            mensaje.generarToast("Por favor generar un backup en la aplicación.", "error");
        }
    }

    /**
     * Metodo que realiza el back-up
     */
    public void backup_db() {
        String path_backup = Environment.getExternalStorageDirectory() + File.separator + "SIPSA"
                + File.separator + "backup" + File.separator;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());

            final String inFileName = MainActivity.this.getDatabasePath(Database.DATABASE_NAME).getPath();

            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);
            String salida = currentDateandTime + ".db";
            String outFileName = path_backup + salida;
            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);
            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();

            output.close();
            fis.close();

            ZipArchive zipArchive = new ZipArchive();
            zipArchive.zip(outFileName, path_backup + currentDateandTime + ".zip", "bf81f34965eddf8f3c291848ae64015f");

            File file = new File(outFileName);
            boolean deleted = file.delete();

            Mensajes mensaje = new Mensajes(this);
            mensaje.generarToast("Backup Realizado");

        } catch (IOException e) {
            Mensajes mensaje = new Mensajes(this);
            mensaje.generarToast("Fallo al hacer Backup", "error");
            e.printStackTrace();
        }
    }



    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

}
