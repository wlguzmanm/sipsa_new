package gov.dane.sipsa.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.greenrobot.dao.async.AsyncOperation;
import de.greenrobot.dao.async.AsyncOperationListener;
import de.greenrobot.dao.async.AsyncSession;
import gov.dane.sipsa.dao.Articulo;
import gov.dane.sipsa.dao.ArticuloDistrito;
import gov.dane.sipsa.dao.ArticuloDistritoDao;
import gov.dane.sipsa.dao.ArticuloI01Dao;
import gov.dane.sipsa.dao.CaracteristicaDistrito;
import gov.dane.sipsa.dao.CaracteristicaDistritoDao;
import gov.dane.sipsa.dao.CasaComercial;
import gov.dane.sipsa.dao.CasaComercialDao;
import gov.dane.sipsa.dao.Configuracion;
import gov.dane.sipsa.dao.ConfiguracionDao;
import gov.dane.sipsa.dao.DaoMaster;
import gov.dane.sipsa.dao.DaoSession;
import gov.dane.sipsa.dao.EnvioArtiCaraValoresI01;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.ArticuloDao;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.FuenteArticuloDao;
import gov.dane.sipsa.dao.FuenteArticuloDistrito;
import gov.dane.sipsa.dao.FuenteDao;
import gov.dane.sipsa.dao.FuenteDistrito;
import gov.dane.sipsa.dao.FuenteDistritoDao;
import gov.dane.sipsa.dao.Grupo;
import gov.dane.sipsa.dao.GrupoDao;
import gov.dane.sipsa.dao.GrupoInsumoI01;
import gov.dane.sipsa.dao.InformadorI01;
import gov.dane.sipsa.dao.Informante;
import gov.dane.sipsa.dao.Observacion;
import gov.dane.sipsa.dao.ObservacionDao;
import gov.dane.sipsa.dao.ObservacionDistrito;
import gov.dane.sipsa.dao.Recoleccion;
import gov.dane.sipsa.dao.RecoleccionDao;
import gov.dane.sipsa.dao.RecoleccionDistrito;
import gov.dane.sipsa.dao.RecoleccionDistritoDao;
import gov.dane.sipsa.dao.RecoleccionI01;
import gov.dane.sipsa.dao.RecoleccionI01Dao;
import gov.dane.sipsa.dao.PrincipalI01;
import gov.dane.sipsa.dao.PrincipalI01Dao;
import gov.dane.sipsa.dao.RecoleccionInsumo;
import gov.dane.sipsa.dao.RecoleccionInsumoDao;
import gov.dane.sipsa.dao.TipoRecoleccion;
import gov.dane.sipsa.dao.TipoRecoleccionDao;
import gov.dane.sipsa.dao.UnidadMedida;
import gov.dane.sipsa.dao.UnidadMedidaDao;
import gov.dane.sipsa.dao.Usuario;
import gov.dane.sipsa.dao.UsuarioDao;
import gov.dane.sipsa.model.Elemento;
import gov.dane.sipsa.model.Elemento01;
import gov.dane.sipsa.model.Factor;
import gov.dane.sipsa.model.FactorI01;
import gov.dane.sipsa.model.GrupoFuente;
import gov.dane.sipsa.model.ParametrosDistrito;
import gov.dane.sipsa.model.RecoleccionPrincipal01;
import gov.dane.sipsa.model.Resumen01;
import gov.dane.sipsa.model.ResumenFuente;
import gov.dane.sipsa.model.Municipio;
import gov.dane.sipsa.model.ObservacionElem;
import gov.dane.sipsa.model.ParametrosInsumos;
import gov.dane.sipsa.model.ParametrosInsumos01;
import gov.dane.sipsa.model.Presentacion;
import gov.dane.sipsa.model.RecoleccionPrincipal;
import gov.dane.sipsa.model.ValorCaracteristica;
import gov.dane.sipsa.utils.ExternalStorage;
import gov.dane.sipsa.dao.ValcarapermitidosI01;
import gov.dane.sipsa.dao.ObservacionI01;
import gov.dane.sipsa.dao.CaracteristicaI01;
import gov.dane.sipsa.dao.ArtiCaraValoresI01;
import gov.dane.sipsa.dao.ArticuloI01;
import gov.dane.sipsa.dao.FuenteTireI01;


/**
 * @author Octa
 */
public class DatabaseManager implements IDatabaseManager, AsyncOperationListener {

    /**
     * Class tag. Used for debug.
     */
    private static final String TAG = DatabaseManager.class.getCanonicalName();
    private static final String DB_NAME = "sipsadb.db";
    private String PATH = "";
    /**
     * Instance of DatabaseManager
     */
    private static DatabaseManager instance;
    /**
     * The Android Activity reference for access to DfatabaseManager.
     */
    private Context context;
    //    private DatabaseOpenHelper mHelper;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    public DaoSession daoSession;
    private AsyncSession asyncSession;
    private List<AsyncOperation> completedOperations;
    private static final int REQUEST_WRITE_STORAGE = 112;
    /**
     * Constructs a new DatabaseManager with the specified arguments.
     *
     * @param context The Android {@link Context}.
     */
    public DatabaseManager(final Context context) {
        this.context = context;

        File[] roots = ExternalStorage.getPaths();
        if (roots.length == 0) {

            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File Root = Environment.getExternalStorageDirectory();
                File folder = new File(Root.getAbsolutePath() + "/SIPSA/");
                if (!folder.exists()) {
                    folder.mkdirs();
                }
            }
            PATH = Environment.getExternalStorageDirectory().getPath()+"/SIPSA";
            //  PATH = "/mnt/sdcard/INDICES";
        } else {
            PATH = roots[0] + "/SIPSA";
        }
        File path = new File(PATH, DB_NAME);
        // path.getParentFile().mkdirs();

        File targetFile = new File(PATH +"/"+ DB_NAME);
        File parent = targetFile.getParentFile();
        if(!parent.exists() && !parent.mkdirs()){
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }

//        this.context = context;
//        mHelper = new DaoMaster.DevOpenHelper(this.context, "indicesdb", null);

        database = SQLiteDatabase.openOrCreateDatabase(path, null);
        DaoMaster.createAllTables(database, true);
//        mHelper = new DatabaseOpenHelper(context, PATH, DB_NAME, null);
//        mHelper = new DaoMaster.DevOpenHelper(this.context, DB_NAME, null);
        completedOperations = new CopyOnWriteArrayList<AsyncOperation>();
    }

    /**
     * @param context The Android {@link Context}.
     * @return this.instance
     */
    public static DatabaseManager getInstance(Context context) {

        if (instance == null) {
            instance = new DatabaseManager(context);
        }

        return instance;
    }

    @Override
    public void onAsyncOperationCompleted(AsyncOperation operation) {
        completedOperations.add(operation);
    }

    private void assertWaitForCompletion1Sec() {
        asyncSession.waitForCompletion(1000);
        asyncSession.isCompleted();
    }

    /**
     * Query for readable DB
     */
    public void openReadableDb() throws SQLiteException {
        //database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    /**
     * Query for writable DB
     */
    public void openWritableDb() throws SQLiteException {
//        database = mHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    @Override
    public void closeDbConnections() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (instance != null) {
            instance = null;
        }
    }

    @Override
    public synchronized void dropDatabaseDistrito() {
        try {
            openWritableDb();
            DaoMaster.dropAllTablesDistrito(database, true); // drops all tables
            DaoMaster.createAllTablesDistrito(database, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public synchronized void dropDatabase() {
        try {
            openWritableDb();
            DaoMaster.dropAllTables(database, true); // drops all tables
            mHelper.onCreate(database);              // creates the tables
            /*
            asyncSession.deleteAll(ClaseVehiculo.class);    // clear all elements from a table
            asyncSession.deleteAll(InfoVehiculo.class);
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void fillParametrosInsumos(ParametrosInsumos parametrosInsumos) {
        try {
            openWritableDb();

            daoSession.deleteAll(RecoleccionInsumo.class);
            daoSession.deleteAll(Fuente.class);
            daoSession.deleteAll(Recoleccion.class);
            daoSession.deleteAll(CasaComercial.class);
            daoSession.deleteAll(Articulo.class);
            daoSession.deleteAll(UnidadMedida.class);
            daoSession.deleteAll(Grupo.class);
            daoSession.deleteAll(TipoRecoleccion.class);
            daoSession.deleteAll(FuenteArticulo.class);
          //  daoSession.deleteAll(Usuario.class);
            daoSession.deleteAll(Observacion.class);

            Log.d(TAG, "Inicial Descarga Recoleccion");

            if(parametrosInsumos!=null) {
                database.beginTransaction();
                if (parametrosInsumos.Fuente != null) {
                    Log.d(TAG, "Inicial Principal");
                    for (Fuente fuente : parametrosInsumos.Fuente) {
                        Log.d(TAG, "fuenId" + fuente.getFuenId());
                        daoSession.insertOrReplace(fuente);
                    }
                }
                int i=1;
                if (parametrosInsumos.Articulo != null){
                    Log.d(TAG, "Inicial Articulo");
                    for (Articulo articulo : parametrosInsumos.Articulo){
                        Log.d(TAG, "articacoId" + articulo.getArticacoId() + " cuenta: " + i++);
                        daoSession.insertOrReplace(articulo);
                    }
                }

                if (parametrosInsumos.Principal != null){
                    Log.d(TAG, "Inicial Principal");
                    for (Recoleccion recoleccion : parametrosInsumos.Principal){
                        Log.d(TAG, "id" + recoleccion.getId());
                        daoSession.insertOrReplace(recoleccion);
                    }
                }

                if (parametrosInsumos.CasaComercial != null){
                    Log.d(TAG, "Inicial Casa Comercial");
                    for (CasaComercial casaComercial : parametrosInsumos.CasaComercial){
                        Log.d(TAG, "cacoId" + casaComercial.getCacoId());
                        daoSession.insertOrReplace(casaComercial);
                    }
                }

                if (parametrosInsumos.Grupo != null){
                    Log.d(TAG, "Inicial Grupo");
                    for (Grupo grupo : parametrosInsumos.Grupo){
                        Log.d(TAG, "grupId" + grupo.getGrupId());
                        daoSession.insertOrReplace(grupo);
                    }
                }

                if (parametrosInsumos.UnidadMedida != null){
                    Log.d(TAG, "Inicial Unidad Medida");
                    for (UnidadMedida unidadMedida : parametrosInsumos.UnidadMedida){
                        Log.d(TAG, "unmeId" + unidadMedida.getUnmeId());
                        daoSession.insertOrReplace(unidadMedida);
                    }
                }

                if (parametrosInsumos.TipoRecoleccion != null){
                    Log.d(TAG, "Inicial Tipo Recoleccion");
                    for (TipoRecoleccion tipoRecoleccion : parametrosInsumos.TipoRecoleccion){
                        Log.d(TAG, "tireId" + tipoRecoleccion.getTireId());
                        daoSession.insertOrReplace(tipoRecoleccion);
                    }
                }

                if (parametrosInsumos.FuenteArticulo != null){
                    Log.d(TAG, "Inicial Principal Articulo");
                    for (FuenteArticulo fuenteArticulo : parametrosInsumos.FuenteArticulo){
                        Log.d(TAG, "id" + fuenteArticulo.getFutiId());
                        daoSession.insertOrReplace(fuenteArticulo);
                    }
                }

               /* if (parametrosInsumos.Usuario != null){
                    Log.d(TAG, "Inicial Usuario");
                    for (Usuario usuario : parametrosInsumos.Usuario){
                        Log.d(TAG, "usuaId" + usuario.getUsuaId());
                        daoSession.insertOrReplace(usuario);
                    }
                }
*/
                if (parametrosInsumos.Observacion != null){
                    Log.d(TAG, "Inicial ArtiCaraValores");
                    for (Observacion observacion : parametrosInsumos.Observacion){
                        Log.d(TAG, "obseId" + observacion.getObseId());
                        daoSession.insertOrReplace(observacion);
                    }
                }



                database.setTransactionSuccessful();
            }

            updateSecuencias();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            daoSession.clear();
        }
    }

    @Override
    public synchronized void fillParametrosInsumos01(ParametrosInsumos01 parametrosInsumos01) {
        try {
            openWritableDb();

            daoSession.deleteAll(ArtiCaraValoresI01.class);
            daoSession.deleteAll(FuenteTireI01.class);
            daoSession.deleteAll(PrincipalI01.class);
            daoSession.deleteAll(CaracteristicaI01.class);
            daoSession.deleteAll(ArticuloI01.class);
            daoSession.deleteAll(ValcarapermitidosI01.class);
            daoSession.deleteAll(InformadorI01.class);
            //daoSession.deleteAll(TipoRecoleccion.class);
            //daoSession.deleteAll(FuenteArticulo.class);
            //  daoSession.deleteAll(Usuario.class);
            daoSession.deleteAll(ObservacionI01.class);

            Log.d(TAG, "Inicial Descarga Recoleccion");

            if(parametrosInsumos01!=null) {
                database.beginTransaction();
                if (parametrosInsumos01.ArtiCaraValoresI01 != null) {
                    Log.d(TAG, "Inicial ArtiCaraValoresI01");
                    for (ArtiCaraValoresI01 artiCaraValoresI01 : parametrosInsumos01.ArtiCaraValoresI01) {
                        Log.d(TAG, "id" + artiCaraValoresI01.getId());
                        daoSession.insertOrReplace(artiCaraValoresI01);
                    }
                }
                int i=1;
                if (parametrosInsumos01.ArticuloI01 != null){
                    Log.d(TAG, "Inicial ArticuloI01");
                    for (ArticuloI01 articuloI01 : parametrosInsumos01.ArticuloI01){
                        Log.d(TAG, "artiId" + articuloI01.getArtiId() + " cuenta: " + i++);
                        daoSession.insertOrReplace(articuloI01);
                    }
                }

                if (parametrosInsumos01.PrincipalI01 != null){
                    Log.d(TAG, "Inicial PrincipalI01");
                    for (PrincipalI01 principalI01 : parametrosInsumos01.PrincipalI01){
                        Log.d(TAG, "id" + principalI01.getId());
                        daoSession.insertOrReplace(principalI01);
                    }
                }

                if (parametrosInsumos01.CaracteristicaI01 != null){
                    Log.d(TAG, "Inicial CaracteristicaI01");
                    for (CaracteristicaI01 caracteristicaI01 : parametrosInsumos01.CaracteristicaI01){
                        Log.d(TAG, "caraId" + caracteristicaI01.getCaraId());
                        daoSession.insertOrReplace(caracteristicaI01);
                    }
                }

                if (parametrosInsumos01.FuenteTireI01 != null){
                    Log.d(TAG, "Inicial FuenteTire");
                    for (FuenteTireI01 fuenteTireI01 : parametrosInsumos01.FuenteTireI01){
                        Log.d(TAG, "fuenId" + fuenteTireI01.getFuenId());
                        daoSession.insertOrReplace(fuenteTireI01);
                    }
                }

                if (parametrosInsumos01.InformadorI01 != null){
                    Log.d(TAG, "Inicial Informador");
                    for (InformadorI01 informadorI01 : parametrosInsumos01.InformadorI01){
                        Log.d(TAG, "infoId" + informadorI01.getInfoId());
                        daoSession.insertOrReplace(informadorI01);
                    }
                }


                if (parametrosInsumos01.ObservacionI01 != null){
                    Log.d(TAG, "Inicial ObservacionI01");
                    for (ObservacionI01 observacionI01 : parametrosInsumos01.ObservacionI01){
                        Log.d(TAG, "obseId" + observacionI01.getObseId());
                        daoSession.insertOrReplace(observacionI01);
                    }
                }

                if (parametrosInsumos01.ValcarapermitidosI01 != null){
                    Log.d(TAG, "Inicial ValcarapermitidosI01");
                    for ( ValcarapermitidosI01 valcarapermitidosI01 : parametrosInsumos01.ValcarapermitidosI01){
                        Log.d(TAG, "vapeId" + valcarapermitidosI01.getVapeId());
                        daoSession.insertOrReplace(valcarapermitidosI01);
                    }
                }



                database.setTransactionSuccessful();
            }

            updateSecuencias();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            daoSession.clear();
        }
    }


    @Override
    public synchronized void fillParametrosDistrito(ParametrosDistrito parametrosDistrito) {
        try {
            openWritableDb();
            daoSession.deleteAll(RecoleccionDistrito.class);
            daoSession.deleteAll(FuenteDistrito.class);
            daoSession.deleteAll(RecoleccionDistrito.class);
            daoSession.deleteAll(ArticuloDistrito.class);
            daoSession.deleteAll(FuenteArticuloDistrito.class);
            //  daoSession.deleteAll(Usuario.class);
            daoSession.deleteAll(ObservacionDistrito.class);
            daoSession.deleteAll(CaracteristicaDistrito.class);

            Log.d(TAG, "Inicial Descarga Recoleccion");

            if(parametrosDistrito!=null) {
                database.beginTransaction();
                if (parametrosDistrito.Fuente != null) {
                    Log.d(TAG, "Inicial Principal Distrito");
                    for (FuenteDistrito fuente : parametrosDistrito.Fuente) {
                        Log.d(TAG, "fuenId" + fuente.getFuenId());
                        daoSession.insertOrReplace(fuente);
                    }
                }
                int i=1;
                if (parametrosDistrito.Articulo != null){
                    Log.d(TAG, "Inicial Articulo Distrito");
                    for (ArticuloDistrito articulo : parametrosDistrito.Articulo){
                        Log.d(TAG, "articacoId" + articulo.getArticacoId() + " cuenta: " + i++);
                        daoSession.insertOrReplace(articulo);
                    }
                }

                if (parametrosDistrito.Principal != null){
                    Log.d(TAG, "Inicial Principal Distrito");
                    for (RecoleccionDistrito recoleccion : parametrosDistrito.Principal){
                        Log.d(TAG, "id" + recoleccion.getId());
                        daoSession.insertOrReplace(recoleccion);
                    }
                }

               /* if (parametrosDistrito.CasaComercial != null){
                    Log.d(TAG, "Inicial Casa Comercial Distrito");
                    for (CasaComercial casaComercial : parametrosDistrito.CasaComercial){
                        Log.d(TAG, "cacoId" + casaComercial.getCacoId());
                        daoSession.insertOrReplace(casaComercial);
                    }
                }

                if (parametrosDistrito.Grupo != null){
                    Log.d(TAG, "Inicial Grupo Distrito");
                    for (Grupo grupo : parametrosDistrito.Grupo){
                        Log.d(TAG, "grupId" + grupo.getGrupId());
                        daoSession.insertOrReplace(grupo);
                    }
                }

                if (parametrosDistrito.UnidadMedida != null){
                    Log.d(TAG, "Inicial Unidad Medida Distrito");
                    for (UnidadMedida unidadMedida : parametrosDistrito.UnidadMedida){
                        Log.d(TAG, "unmeId" + unidadMedida.getUnmeId());
                        daoSession.insertOrReplace(unidadMedida);
                    }
                }

                if (parametrosDistrito.TipoRecoleccion != null){
                    Log.d(TAG, "Inicial Tipo Recoleccion Distrito");
                    for (TipoRecoleccion tipoRecoleccion : parametrosDistrito.TipoRecoleccion){
                        Log.d(TAG, "tireId" + tipoRecoleccion.getTireId());
                        daoSession.insertOrReplace(tipoRecoleccion);
                    }
                }*/

                if (parametrosDistrito.FuenteArticulo != null){
                    Log.d(TAG, "Inicial Principal Articulo Distrito");
                    for (FuenteArticuloDistrito fuenteArticulo : parametrosDistrito.FuenteArticulo){
                        Log.d(TAG, "id" + fuenteArticulo.getFutiId());
                        daoSession.insertOrReplace(fuenteArticulo);
                    }
                }

               /* if (parametrosInsumos.Usuario != null){
                    Log.d(TAG, "Inicial Usuario");
                    for (Usuario usuario : parametrosInsumos.Usuario){
                        Log.d(TAG, "usuaId" + usuario.getUsuaId());
                        daoSession.insertOrReplace(usuario);
                    }
                }
*/
                if (parametrosDistrito.Observacion != null){
                    Log.d(TAG, "Inicial ArtiCaraValores Distrito");
                    for (ObservacionDistrito observacion : parametrosDistrito.Observacion){
                        Log.d(TAG, "obseId" + observacion.getObseId());
                        daoSession.insertOrReplace(observacion);
                    }
                }

                if (parametrosDistrito.CaracteristicaD != null){
                    Log.d(TAG, "Inicial Caracteristicas Distrito");
                    for (CaracteristicaDistrito cara : parametrosDistrito.CaracteristicaD){
                        Log.d(TAG, "obseId" + cara.getCaraId());
                        daoSession.insertOrReplace(cara);
                    }
                }

                database.setTransactionSuccessful();
            }

            updateSecuenciasDistrito();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            daoSession.clear();
        }
    }


    @Override
    public synchronized void deleteAllTables() {
        try {
            openWritableDb();

            daoSession.deleteAll(Articulo.class);
            daoSession.deleteAll(CasaComercial.class);
            daoSession.deleteAll(FuenteArticulo.class);
            daoSession.deleteAll(Fuente.class);
            daoSession.deleteAll(Grupo.class);
            daoSession.deleteAll(Informante.class);
            daoSession.deleteAll(Presentacion.class);
            daoSession.deleteAll(Recoleccion.class);
            daoSession.deleteAll(RecoleccionInsumo.class);
            daoSession.deleteAll(RecoleccionDistrito.class);
            daoSession.deleteAll(TipoRecoleccion.class);
            daoSession.deleteAll(UnidadMedida.class);
            daoSession.deleteAll(Usuario.class);

            daoSession.deleteAll(ArtiCaraValoresI01.class);
            daoSession.deleteAll(FuenteTireI01.class);
            daoSession.deleteAll(PrincipalI01.class);
            daoSession.deleteAll(CaracteristicaI01.class);
            daoSession.deleteAll(ArticuloI01.class);
            daoSession.deleteAll(ValcarapermitidosI01.class);
            daoSession.deleteAll(InformadorI01.class);
            daoSession.deleteAll(ObservacionI01.class);
            daoSession.deleteAll(CaracteristicaDistrito.class);

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void deleteAllDistritoTables() {
        try {
            openWritableDb();

            daoSession.deleteAll(ArticuloDistrito.class);
            daoSession.deleteAll(FuenteArticuloDistrito.class);
            daoSession.deleteAll(FuenteDistrito.class);
            daoSession.deleteAll(RecoleccionDistrito.class);
            daoSession.deleteAll(Usuario.class);
            daoSession.deleteAll(ObservacionDistrito.class);
            daoSession.deleteAll(CaracteristicaDistrito.class);

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized Configuracion getConfiguracion() {

        Configuracion configuracion = null;
        try {
            openReadableDb();
            ConfiguracionDao configuracionDao = daoSession.getConfiguracionDao();
            if(!configuracionDao.loadAll().isEmpty()){
                configuracion = configuracionDao.loadAll().get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return configuracion;
    }

    @Override
    public synchronized List<Municipio> listaMunicipioFuente() {
        List<Municipio> listMunicipio = null;

        openWritableDb();

        String query = "SELECT DISTINCT MUNI_ID, MUNI_NOMBRE FROM FUENTE";


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listMunicipio = new ArrayList<Municipio>();
                do {
                    Municipio municipio  = new Municipio();
                    municipio.setMuniId(c.getString(0));
                    municipio.setMuniNombre(c.getString(1));
                    listMunicipio.add(municipio);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listMunicipio;
    }

    @Override
    public synchronized List<Municipio> listaMunicipioI01FuenteTireI01() {
        List<Municipio> listMunicipio = null;

        openWritableDb();

        String query = "select distinct muni_id, muni_nombre from fuente_Tire_I01";


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listMunicipio = new ArrayList<Municipio>();
                do {
                    Municipio municipio  = new Municipio();
                    municipio.setMuniId(c.getString(0));
                    municipio.setMuniNombre(c.getString(1));
                    listMunicipio.add(municipio);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listMunicipio;
    }

    public synchronized List<Municipio> listaMunicipioFuenteDistrito() {
        List<Municipio> listMunicipio = null;

        openWritableDb();

        String query = "SELECT DISTINCT MUNI_ID, MUNI_NOMBRE FROM FUENTE_DISTRITO " +
               "WHERE TIRE_ID = 13";


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listMunicipio = new ArrayList<Municipio>();
                do {
                    Municipio municipio  = new Municipio();
                    municipio.setMuniId(c.getString(0));
                    municipio.setMuniNombre(c.getString(1));
                    listMunicipio.add(municipio);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listMunicipio;
    }

    @Override
    public synchronized List<FuenteArticulo> obtenerFuenteArticulo(Long fuenteId, Long tireId) {
        List<FuenteArticulo> fuenteArticulos = null;

        openWritableDb();

        String query = "SELECT DISTINCT FUTI_ID FROM FUENTE_ARTICULO WHERE FUEN_ID = " +fuenteId + " AND TIRE_ID = " + tireId ;


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                fuenteArticulos = new ArrayList<FuenteArticulo>();
                do {
                    FuenteArticulo fuenteArticulo = new FuenteArticulo();
                    fuenteArticulo.setFutiId(c.getLong(0));
                    fuenteArticulos.add(fuenteArticulo);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return fuenteArticulos;
    }

    @Override
    public synchronized List<FuenteArticuloDistrito> obtenerFuenteArticuloDistrito(Long fuenteId, Long tireId) {
        List<FuenteArticuloDistrito> fuenteArticulos = null;

        openWritableDb();

        String query = "SELECT DISTINCT FUTI_ID FROM FUENTE_ARTICULO_DISTRITO WHERE FUEN_ID = " +fuenteId + " AND TIRE_ID = " + tireId ;


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                fuenteArticulos = new ArrayList<FuenteArticuloDistrito>();
                do {
                    FuenteArticuloDistrito fuenteArticulo = new FuenteArticuloDistrito();
                    fuenteArticulo.setFutiId(c.getLong(0));
                    fuenteArticulo.setTireId(tireId);
                    fuenteArticulos.add(fuenteArticulo);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return fuenteArticulos;
    }


    @Override
    public List<UnidadMedida> listaUnidadMedidaArticulo(Long idPresentacion, Long idArticulo, Long idFuente, Long idCaco, String icaId) {
        List<UnidadMedida> listUnidad =null;

        openReadableDb();

        String query ="SELECT DISTINCT UNME_ID, UNME_NOMBRE_PPAL, UNME_CANTIDAD_PPAL, UNME_NOMBRE2, UNME_CANTIDAD2" +
                " FROM UNIDAD_MEDIDA WHERE " +
                "TIPR_ID = "+ idPresentacion + " AND NOT EXISTS (SELECT * FROM RECOLECCION WHERE TIPR_ID = " + idPresentacion + " AND ARTI_ID= " + idArticulo +
                " AND RECOLECCION.UNME_ID = UNIDAD_MEDIDA.UNME_ID AND FUEN_ID="+idFuente+" AND CACO_ID="+idCaco+" AND ARTICACO_REGICA_LINEA='"+ icaId +"' ) ORDER BY UNME_NOMBRE_PPAL, UNME_CANTIDAD_PPAL";

        Cursor c = daoSession.getDatabase().rawQuery(query, null);

        try {
            if (c.moveToFirst()){
                listUnidad = new ArrayList<UnidadMedida>();
                do {
                    UnidadMedida unidad = new UnidadMedida();
                    unidad.setUnmeId(c.getLong(0));
                    unidad.setUnmeNombrePpal(c.getString(1));
                    unidad.setUnmeCantidadPpal(c.getDouble(2));
                    unidad.setUnmeNombre2(c.getString(3));
                    unidad.setUnmeCantidad2(c.getDouble(4));
                    listUnidad.add(unidad);

                } while (c.moveToNext());
            }
        }catch ( Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }

        return  listUnidad;
    }


    @Override
    public  synchronized  List<InformadorI01> listaInformadorI01ByElementos(String idmunicipio, Long grin2Id, Long artiId){
        List<InformadorI01> listInformadorI01= null;

        openReadableDb();

        String query = "SELECT DISTINCT INFO_ID, INFO_NOMBRE, INFO_TELEFONO "+
                "FROM INFORMADOR_I01 I WHERE MUNI_ID='" + idmunicipio+"' "+
                "AND NOT EXISTS (SELECT * FROM RECOLECCION_I01 R WHERE R.INFO_ID= I.INFO_ID AND " +
                "R.GRIN2_ID="+ grin2Id +" AND R.ARTI_ID=" + artiId + " )";

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listInformadorI01 = new ArrayList<InformadorI01>();
                do {
                    InformadorI01 informadorI01  = new InformadorI01();
                    informadorI01.setInfoId(c.getLong(0));
                    informadorI01.setInfoNombre(c.getString(1));
                    informadorI01.setInfoTelefono(c.getString(2));
                    listInformadorI01.add(informadorI01);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }


        return  listInformadorI01;
    }

    @Override
    public  synchronized  List<InformadorI01> listaInformadorI01(String idmunicipio){
        List<InformadorI01> listInformadorI01= null;

        openReadableDb();

        String query = "SELECT DISTINCT INFO_ID, INFO_NOMBRE, INFO_TELEFONO "+
                "FROM INFORMADOR_I01 WHERE MUNI_ID='" + idmunicipio+"'";

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listInformadorI01 = new ArrayList<InformadorI01>();
                do {
                    InformadorI01 informadorI01  = new InformadorI01();
                    informadorI01.setInfoId(c.getLong(0));
                    informadorI01.setInfoNombre(c.getString(1));
                    informadorI01.setInfoTelefono(c.getString(2));
                    listInformadorI01.add(informadorI01);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }


        return  listInformadorI01;
    }

    @Override
    public  synchronized  List<ArticuloI01> listaArticuloI01(Long tireId){
        List<ArticuloI01> listArticuloI01= null;

        openReadableDb();

        String query = "SELECT DISTINCT ARTI_ID, ARTI_NOMBRE, TIRE_ID "+
                "FROM ARTICULO_I01 WHERE TIRE_ID=" + tireId ;

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listArticuloI01 = new ArrayList<ArticuloI01>();
                do {
                    ArticuloI01 articuloI01  = new ArticuloI01();
                    articuloI01.setArtiId(c.getLong(0));
                    articuloI01.setArtiNombre(c.getString(1));
                    articuloI01.setTireId(c.getLong(2));
                    listArticuloI01.add(articuloI01);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }


        return  listArticuloI01;
    }

    @Override
    public  synchronized  List<ArticuloDistrito> listaArticuloDistrito(Long tireId){
        List<ArticuloDistrito> listArticulo= null;

        openReadableDb();

        String query = "SELECT DISTINCT ARTI_ID, ARTI_NOMBRE, TIRE_ID "+
                "FROM ARTICULO_DISTRITO WHERE TIRE_ID=" + tireId ;

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listArticulo = new ArrayList<ArticuloDistrito>();
                do {
                    ArticuloDistrito articulo  = new ArticuloDistrito();
                    articulo.setArtiId(c.getLong(0));
                    articulo.setArtiNombre(c.getString(1));
                    articulo.setTireId(c.getLong(2));
                    listArticulo.add(articulo);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }


        return  listArticulo;
    }

    @Override
    public  synchronized  List<CaracteristicaI01> listaCarateristicaI01(Long tireId){
        List<CaracteristicaI01> listCaraI01= null;

        openReadableDb();

        String query = "SELECT CARA_ID, CARA_DESCRIPCION FROM CARACTERISTICA_I01 " +
                "WHERE TIRE_ID=" + tireId + " ORDER BY CARA_ORDEN ";

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listCaraI01 = new ArrayList<CaracteristicaI01>();
                do {
                    CaracteristicaI01 caraI01  = new CaracteristicaI01();
                    caraI01.setCaraId(c.getLong(0));
                    caraI01.setCaraDescripcion(c.getString(1));
                    listCaraI01.add(caraI01);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }


        return  listCaraI01;
    }

    @Override
    public  synchronized  List<ValcarapermitidosI01> listaValCaraI01(Long tireId, Long caraId){
        List<ValcarapermitidosI01> listaValCaraI01= null;

        openReadableDb();

        String query = "SELECT VAPE_ID, VAPE_DESCRIPCION from VALCARAPERMITIDOS_I01 WHERE TIRE_ID=" + tireId +
                " AND CARA_ID=" + caraId + " ORDER BY VAPE_ID, VAPE_DESCRIPCION ";

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listaValCaraI01 = new ArrayList<ValcarapermitidosI01>();
                do {
                    ValcarapermitidosI01 valcaraI01  = new ValcarapermitidosI01();
                    valcaraI01.setVapeId(c.getLong(0));
                    valcaraI01.setVapeDescripcion(c.getString(1));
                    listaValCaraI01.add(valcaraI01);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }

        return  listaValCaraI01;
    }


    @Override
    public synchronized List<Presentacion> listaPresentacionArticulo() {
        List<Presentacion> listUnidadMedida = null;

        openWritableDb();

        String query = "SELECT DISTINCT TIPR_ID, TIPR_NOMBRE FROM UNIDAD_MEDIDA ORDER BY TIPR_NOMBRE";


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listUnidadMedida = new ArrayList<Presentacion>();
                do {
                    Presentacion unidadMedida  = new Presentacion();
                    unidadMedida.setTiprId(c.getLong(0));
                    unidadMedida.setTiprNombre(c.getString(1));
                    listUnidadMedida.add(unidadMedida);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listUnidadMedida;
    }

    @Override
    public synchronized List<Factor> listaFactorByIdMunicipio(Long muniID) {
        List<Factor> listFactor = null;

        openWritableDb();

        String query = "SELECT DISTINCT TIRE_ID, TIRE_NOMBRE, FUEN_ID FROM FUENTE_ARTICULO WHERE MUNI_ID = " + muniID ;


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listFactor = new ArrayList<Factor>();
                do {
                    Factor factor  = new Factor();
                    factor.setTireId(c.getLong(0));
                    factor.setTireNombre(c.getString(1));
                    factor.setFuenteId(c.getLong(2));
                    listFactor.add(factor);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listFactor;
    }

    @Override
    public synchronized List<FactorI01> listaFactorByIdMunicipioI01(String muniID) {
        List<FactorI01> listFactorI01 = null;

        openWritableDb();

        String query = "SELECT DISTINCT TIRE_ID, TIRE_NOMBRE FROM FUENTE_TIRE_I01 WHERE MUNI_ID = '" + muniID+"'";


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listFactorI01 = new ArrayList<FactorI01>();
                do {
                    FactorI01 factorI01  = new FactorI01();
                    factorI01.setTireId(c.getLong(0));
                    factorI01.setTireNombre(c.getString(1));
                    listFactorI01.add(factorI01);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listFactorI01;
    }

    @Override
    public synchronized List<FuenteArticulo> listaTipoRecoleccion(Long fuenteId) {
        List<FuenteArticulo> listaFuenteArticulo = null;

        openWritableDb();

        String query = "SELECT DISTINCT TIRE_ID, TIRE_NOMBRE FROM FUENTE_ARTICULO WHERE FUEN_ID = " + fuenteId ;


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listaFuenteArticulo = new ArrayList<FuenteArticulo>();
                do {
                    FuenteArticulo fuenteArticulo  = new FuenteArticulo();
                    fuenteArticulo.setTireId(c.getLong(0));
                    fuenteArticulo.setTireNombre(c.getString(1));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listaFuenteArticulo;

    }

    @Override
    public synchronized List<FuenteArticulo> listaTipoRecoleccionByFuenteId(Long fuenteId) {
        List<FuenteArticulo> listaFuenteArticulo = null;

        openWritableDb();

        String query = "SELECT DISTINCT TIRE_ID, TIRE_NOMBRE, PRRE_FECHA_PROGRAMADA FROM FUENTE_ARTICULO WHERE FUEN_ID = " + fuenteId ;


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listaFuenteArticulo = new ArrayList<FuenteArticulo>();
                do {
                    FuenteArticulo fuenteArticulo  = new FuenteArticulo();
                    fuenteArticulo.setTireId(c.getLong(0));
                    fuenteArticulo.setTireNombre(c.getString(1));
                    fuenteArticulo.setPrreFechaProgramada(new Date(c.getLong(2)));
                    listaFuenteArticulo.add(fuenteArticulo);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listaFuenteArticulo;

    }

    @Override
    public synchronized List<FuenteArticuloDistrito> listaTipoRecoleccionDistritoByFuenteId(Long fuenteId) {
        List<FuenteArticuloDistrito> listaFuenteArticulo = null;

        openWritableDb();

        String query = "SELECT DISTINCT TIRE_ID, TIRE_NOMBRE FROM FUENTE_ARTICULO_DISTRITO WHERE TIRE_ID = 13 AND FUEN_ID = " + fuenteId ;


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listaFuenteArticulo = new ArrayList<FuenteArticuloDistrito>();
                do {
                    FuenteArticuloDistrito fuenteArticulo  = new FuenteArticuloDistrito();
                    fuenteArticulo.setTireId(c.getLong(0));
                    fuenteArticulo.setTireNombre(c.getString(1));
                    listaFuenteArticulo.add(fuenteArticulo);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listaFuenteArticulo;

    }


    @Override
    public  synchronized List<Elemento01> listaRecoleccionPrincipal01(Long tireId, String muniId, Long futiId){
        List<RecoleccionPrincipal01> listaRecoleccionI01 = null;


        String query = "SELECT R.ARTI_ID, R.ARTI_NOMBRE, R.MUNI_ID, R.TIRE_ID, F.MUNI_NOMBRE, F.TIRE_NOMBRE, R.GRIN2_ID,  A.VAPE_DESCRIPCION, A.CARA_ID, " +
                " R.FUTI_ID, R.PRRE_FECHA_PROGRAMADA, R.PROM_ANTERIOR,  (SELECT COUNT(*) " +
                "FROM RECOLECCION_I01 F WHERE F.ARTI_ID=R.ARTI_ID AND F.GRIN2_ID=R.GRIN2_ID AND F.FUTI_ID=R.FUTI_ID AND F.ESTADO_TRANSMITIDO IS NULL) NORECOLECCION, (SELECT NOVEDAD FROM RECOLECCION_I01 F WHERE F.ARTI_ID=R.ARTI_ID AND F.GRIN2_ID=R.GRIN2_ID AND F.FUTI_ID=R.FUTI_ID AND ESTADO_TRANSMITIDO IS NULL) NOVEDAD " +
                "FROM PRINCIPAL_I01 R, FUENTE_TIRE_I01 F, ARTI_CARA_VALORES_I01 A " +
                " WHERE R.TIRE_ID=F.TIRE_ID AND R.MUNI_ID = F.MUNI_ID AND R.FUTI_ID=F.FUTI_ID AND R.ARTI_ID=A.ARTI_ID AND R.GRIN2_ID=A.GRIN2_ID AND " +
                " R.ESTADO_TRASMITIDO IS NULL AND R.TIRE_ID=" + tireId + " AND R.FUTI_ID="+futiId+
                " AND R.MUNI_ID='" + muniId + "' ORDER BY R.ARTI_ID, R.GRIN2_ID, A.CARA_ID";

        String producto="";
        List<Elemento01> productos = new ArrayList<>();
        int i = 0;
        String artiid_grin2Id="";
        Elemento01 elemento01 = new Elemento01();
        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {

                do {


                    if(i==0){
                        artiid_grin2Id = "" +(c.getLong(0) +"-"+ c.getLong(6));
                    }else{
                        if(!artiid_grin2Id.equals("" +(c.getLong(0) +"-"+  c.getLong(6)))){
                            elemento01.setNombreArtiCompleto(producto);
                            productos.add(elemento01);
                            producto = "";
                            i = 0;
                            artiid_grin2Id = "" +(c.getLong(0) +"-"+ c.getLong(6));
                            elemento01 = new Elemento01();
                        }

                    }


                    elemento01.setArtiId(c.getLong(0));
                    elemento01.setArtiNombre(c.getString(1));
                    elemento01.setMuniId(c.getString(2));
                    elemento01.setTireId(c.getLong(3));
                    elemento01.setMuniNombre(c.getString(4));
                    elemento01.setTireNombre(c.getString(5));
                    elemento01.setGrupoInsumoId(c.getLong(6));
                    elemento01.setFutiId(c.getLong(9));
                    elemento01.setFechaProgramada(new Date(c.getLong(10)));
                    elemento01.setPrecioAnterior(c.getDouble(11));
                    elemento01.setNoRecoleccion(c.getInt(12));
                    elemento01.setNovedad(c.getString(13));


                    producto += i==0? c.getString(1) + "-" + c.getString(7)  : "-" + c.getString(7);
                    i++;

                    if(c.isLast()){
                        elemento01.setNombreArtiCompleto(producto);
                        productos.add(elemento01);
                    }


                } while (c.moveToNext());
}
        } catch (Exception e) {
                e.printStackTrace();
                }finally {
                daoSession.clear();
                c.close();
                }
        return productos;
    }

    @Override
    public synchronized  List<Resumen01> listaResumenRecoleccion(Long tireId, Long artiId, Long grin2Id, String muniId, Long futiId){
        List<Resumen01> listaResumen  = new ArrayList<>();

        openReadableDb();

        String query="SELECT DISTINCT R.ARTI_ID, T.ARTI_NOMBRE, F.MUNI_ID, F.TIRE_ID, F.MUNI_NOMBRE, F.TIRE_NOMBRE, R.GRIN2_ID, R.INFO_ID, R.INFO_NOMBRE, " +
                "R.FUTI_ID, R.FECHA_PROGRAMADA, P.PROM_ANTERIOR , R.PRECIO_ACTUAL, R._ID, R.INFO_TELEFONO, " +
                "(SELECT NOVEDAD FROM RECOLECCION_I01 R1 WHERE R.ARTI_ID = R1.ARTI_ID AND R.GRIN2_ID=R1.GRIN2_ID AND R1.NOVEDAD IN ('IA','IN','ND', 'PR', 'IS')) NOVEDAD " +
                "FROM RECOLECCION_I01 R, ARTICULO_I01 T, FUENTE_TIRE_I01 F, PRINCIPAL_I01 P " +
                "WHERE  T.ARTI_ID = R.ARTI_ID AND T.GRIN2_ID=R.GRIN2_ID AND " +
                "P.ARTI_ID=R.ARTI_ID AND P.GRIN2_ID=R.GRIN2_ID AND P.MUNI_ID=F.MUNI_ID AND F.TIRE_ID=P.TIRE_ID AND " +
                "F.TIRE_ID=" + tireId + " AND P.MUNI_ID='" + muniId + "' AND P.FUTI_ID='" + futiId +"' AND R.ARTI_ID=" + artiId + " AND  R.GRIN2_ID=" + grin2Id +
                " AND R.FUTI_ID=P.FUTI_ID ORDER BY R.INFO_NOMBRE";

        Cursor c = daoSession.getDatabase().rawQuery(query, null);

        try {
            if (c.moveToFirst()) {
                do {
                            Resumen01 e = new Resumen01();
                            e.setArtiId(c.getLong(0));
                            e.setArtiNombre(c.getString(1));
                            e.setMuniId(c.getString(2));
                            e.setTireId(c.getLong(3));
                            e.setMuniNombre(c.getString(4));
                            e.setTireNombre(c.getString(5));
                            e.setGrupoInsumoId(c.getLong(6));
                            e.setInfoId(c.getLong(7));
                            e.setInfoNombre(c.getString(8));
                            e.setFutiId(c.getLong(9));
                            e.setFechaProgramada(new Date(c.getLong(10)));
                            e.setPrecioAnterior(c.getDouble(11));
                            e.setPrecioActual(c.getDouble(12));
                            e.setRecoleccionId(c.getLong(13));
                            e.setInfoTelefono(c.getString(14));
                            e.setNovedad(c.getString(15));

                            listaResumen.add(e);


                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }


     return listaResumen;
    }

    @Override
    public synchronized  List<ValorCaracteristica> listaValorCaracteristica(Long tireId, Long grin2Id, Long artiId, String muniId){
        List<ValorCaracteristica> listaCaracteristicas = null;

        openReadableDb();

        String query ="SELECT  R.ARTI_NOMBRE, A.CARA_ID, C.CARA_DESCRIPCION, A.VAPE_ID, A.VAPE_DESCRIPCION " +
        "FROM PRINCIPAL_I01 R, FUENTE_TIRE_I01 F, ARTI_CARA_VALORES_I01 A , CARACTERISTICA_I01 C " +
        "WHERE F.MUNI_ID = R.MUNI_ID AND R.ARTI_ID=A.ARTI_ID AND R.GRIN2_ID=A.GRIN2_ID AND A.CARA_ID=C.CARA_ID AND " +
                "R.ESTADO_TRASMITIDO IS NULL AND F.TIRE_ID=" +
                tireId + " AND R.MUNI_ID='" + muniId + "' AND R.ARTI_ID=" + artiId + " AND R.GRIN2_ID=" + grin2Id ;

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listaCaracteristicas = new ArrayList<ValorCaracteristica>();
                do {
                    ValorCaracteristica valorCaracteristica = new ValorCaracteristica();

                    valorCaracteristica.setArtiNombre(c.getString(0));
                    valorCaracteristica.setCaraId(c.getLong(1));
                    valorCaracteristica.setCaraDescripcion(c.getString(2));
                    valorCaracteristica.setVapeId(c.getLong(3));
                    valorCaracteristica.setVapeDescripcion(c.getString(4));

                    listaCaracteristicas.add(valorCaracteristica);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listaCaracteristicas;

    }

    @Override
    public synchronized  List<ValorCaracteristica> listaValorCaracteristicaAdd(Long tireId, Long grin2Id, Long artiId, String muniId){
        List<ValorCaracteristica> listaCaracteristicas = null;

        openReadableDb();

        String query ="SELECT  R.ARTI_NOMBRE, A.CARA_ID, C.CARA_DESCRIPCION, A.VAPE_ID, A.VAPE_DESCRIPCION  " +
                "FROM ARTICULO_I01 R, FUENTE_TIRE_I01 F, ARTI_CARA_VALORES_I01 A , CARACTERISTICA_I01 C " +
                "WHERE R.ARTI_ID=A.ARTI_ID AND R.GRIN2_ID=A.GRIN2_ID AND A.CARA_ID=C.CARA_ID " +
                "AND F.TIRE_ID=" + tireId + " AND F.MUNI_ID='" + muniId + "' AND R.ARTI_ID=" + artiId + " AND R.GRIN2_ID=" + grin2Id;

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listaCaracteristicas = new ArrayList<ValorCaracteristica>();
                do {
                    ValorCaracteristica valorCaracteristica = new ValorCaracteristica();

                    valorCaracteristica.setArtiNombre(c.getString(0));
                    valorCaracteristica.setCaraId(c.getLong(1));
                    valorCaracteristica.setCaraDescripcion(c.getString(2));
                    valorCaracteristica.setVapeId(c.getLong(3));
                    valorCaracteristica.setVapeDescripcion(c.getString(4));

                    listaCaracteristicas.add(valorCaracteristica);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listaCaracteristicas;

    }

    @Override
    public synchronized  List<ArticuloI01> listaArticulosAdicionado(Long tireId, String muniId){
        List<ArticuloI01> listaArticulosAdicionado = null;

        openReadableDb();

        String query ="SELECT DISTINCT A.ARTI_ID, A.GRIN2_ID, A.NOMBRE_COMPLETO, A.ARTI_NOMBRE, A.TIRE_ID FROM ARTICULO_I01 A " +
                "WHERE NOT EXISTS (SELECT * FROM PRINCIPAL_I01 P WHERE P.ARTI_ID=A.ARTI_ID AND P.GRIN2_ID=A.GRIN2_ID AND P.MUNI_ID="+ muniId+") AND " +
                "A.TIRE_ID=" +tireId;

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listaArticulosAdicionado = new ArrayList<ArticuloI01>();
                do {
                    ArticuloI01 ArtiAdicionado = new ArticuloI01();


                    ArtiAdicionado.setArtiId(c.getLong(0));
                    ArtiAdicionado.setGrin2Id(c.getLong(1));
                    ArtiAdicionado.setNombreCompleto(c.getString(2));
                    ArtiAdicionado.setArtiNombre(c.getString(3));
                    ArtiAdicionado.setTireId(c.getLong(4));


                    listaArticulosAdicionado.add(ArtiAdicionado);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listaArticulosAdicionado;

    }


    @Override
    public  synchronized  List<RecoleccionPrincipal> listaRecoleccionPricipal (Long tireId, Long fuenId) {
      List <RecoleccionPrincipal> listaRecoleccion =null;

        openReadableDb();

        String query = "SELECT  R.ARTI_ID, R.ARTI_NOMBRE, R.CACO_ID, R.CACO_NOMBRE, R.UNME_ID, R.UNME_NOMBRE_PPAL, R.ARTICACO_REGICA_LINEA,  " +
                " R.GRUP_NOMBRE, R.ARTICACO_ID, R.FUEN_NOMBRE, R.MUNI_ID, R.UNME_CANTIDAD2, R.UNME_CANTIDAD_PPAL, " +
                " R.PRECIO, R.NOVEDAD, R.TIPO, R.OBSERVACION, R.DESVIACION, R.TIRE_ID, R.FUEN_ID, R._id, R.TIPR_NOMBRE, " +
                " R.TIPR_ID, R.UNME_NOMBRE2, R.PROM_ANT_DIARIO, R.ESTADO_RECOLECCION, R.TRANSMITIDO, R.NOVEDAD_ANTERIOR," +
                " R.PRRE_FECHA_PROGRAMADA, F.TIRE_NOMBRE, R.SUBG_NOMBRE, R.ARTI_VLR_MIN_DIASM, R.ARTI_VLR_MAX_DIASM," +
                " R.ARTI_VLR_MIN_TOMAS, R.ARTI_VLR_MAX_TOMAS, R.ARTI_VLR_MIN_RONDAS, R.ARTI_VLR_MAX_RONDAS, R.FUTI_ID" +
                " FROM RECOLECCION R, FUENTE_ARTICULO F " +
                " WHERE R.FUTI_ID=F.FUTI_ID AND R.TIRE_ID=" + tireId + " AND R.FUEN_ID ="+ fuenId +" AND R.MUNI_ID=F.MUNI_ID AND TRANSMITIDO IS NULL ORDER BY R.ARTI_NOMBRE ";

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listaRecoleccion = new ArrayList<RecoleccionPrincipal>();
                do {
                    RecoleccionPrincipal recoleccionPrincipal  = new RecoleccionPrincipal();
                    recoleccionPrincipal.setArticuloId(c.getLong(0));
                    recoleccionPrincipal.setArticuloNombre(c.getString(1));
                    recoleccionPrincipal.setCasaComercialId(c.getLong(2));
                    recoleccionPrincipal.setCasaComercialNombre(c.getString(3));
                    recoleccionPrincipal.setUnidadMedidaId(c.getLong(4));
                    recoleccionPrincipal.setUnidadMedidaNombre(c.getString(5));
                    recoleccionPrincipal.setRegistroIcaId(c.getString(6));
                    recoleccionPrincipal.setGrupoNombre(c.getString(7));
                    recoleccionPrincipal.setArticacoId(c.getLong(8));
                    recoleccionPrincipal.setFuenNombre(c.getString(9));
                    recoleccionPrincipal.setMuniId(c.getString(10));

                    recoleccionPrincipal.setUnmeCantidad2(c.getDouble(11));
                    recoleccionPrincipal.setUnmeCantidadPpal(c.getDouble(12));
                    recoleccionPrincipal.setPrecio(c.getString(13));
                    recoleccionPrincipal.setNovedad(c.getString(14));
                    recoleccionPrincipal.setTipo(c.getString(15));
                    recoleccionPrincipal.setObservacion(c.getString(16));
                    recoleccionPrincipal.setDesviacion(c.getString(17));
                    recoleccionPrincipal.setTireId(c.getLong(18));
                    recoleccionPrincipal.setFuenId(c.getLong(19));
                    recoleccionPrincipal.setId(c.getLong(20));
                    recoleccionPrincipal.setTiprNombre(c.getString(21));
                    recoleccionPrincipal.setTiprId(c.getLong(22));
                    recoleccionPrincipal.setUnmeNombre2(c.getString(23));
                    recoleccionPrincipal.setPromAntDiario(c.getDouble(24));
                    recoleccionPrincipal.setEstadoRecoleccion(c.getInt(25)==0?false:true);
                    recoleccionPrincipal.setTransmitido(c.getInt(26)==0 ?false:true);
                    recoleccionPrincipal.setNovedadAnterior(c.getString(27));
                    recoleccionPrincipal.setFechaProgramada(new Date(c.getLong(28)));
                    recoleccionPrincipal.setTireNombre(c.getString(29));
                    recoleccionPrincipal.setSubgNombre(c.getString(30));
                    recoleccionPrincipal.setArtiVlrMinDiasM(c.getLong(31));
                    recoleccionPrincipal.setArtiVlrMaxDiasM(c.getLong(32));
                    recoleccionPrincipal.setArtiVlrMinTomas(c.getLong(33));
                    recoleccionPrincipal.setArtiVlrMaxTomas(c.getLong(34));
                    recoleccionPrincipal.setArtiVlrMinRondas(c.getLong(35));
                    recoleccionPrincipal.setArtiVlrMaxRondas(c.getLong(36));
                    recoleccionPrincipal.setFutiId(c.getLong(37));



                    listaRecoleccion.add(recoleccionPrincipal);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listaRecoleccion;


    }

    public  synchronized  List<RecoleccionPrincipal> listaRecoleccionDistritoPricipal (Long tireId, Long fuenId) {
        List <RecoleccionPrincipal> listaRecoleccion =null;

        openReadableDb();

        String query = "SELECT  R.ARTI_ID, R.ARTI_NOMBRE, R.CACO_ID, R.CACO_NOMBRE, R.UNME_ID, R.UNME_NOMBRE_PPAL, R.ARTICACO_REGICA_LINEA,  " +
                " R.GRUP_NOMBRE, R.ARTICACO_ID, R.FUEN_NOMBRE, R.MUNI_ID, R.UNME_CANTIDAD2, R.UNME_CANTIDAD_PPAL, " +
                " R.PRECIO, R.NOVEDAD, R.TIPO, R.OBSERVACION, R.DESVIACION, R.TIRE_ID, R.FUEN_ID, R._id, R.TIPR_NOMBRE, " +
                " R.TIPR_ID, R.UNME_NOMBRE2, R.PROM_ANT_DIARIO, R.ESTADO_RECOLECCION, R.TRANSMITIDO, R.NOVEDAD_ANTERIOR," +
                " R.PRRE_FECHA_PROGRAMADA, F.TIRE_NOMBRE, R.SUBG_NOMBRE, R.ARTI_VLR_MIN_DIASM, R.ARTI_VLR_MAX_DIASM," +
                " R.ARTI_VLR_MIN_TOMAS, R.ARTI_VLR_MAX_TOMAS, R.ARTI_VLR_MIN_RONDAS, R.ARTI_VLR_MAX_RONDAS, R.FUTI_ID,  " +
                " FRECUENCIA, OBSERVACION_ARTICULO, R.UNME_NOMBRE_OTRO "+
                " FROM RECOLECCION_DISTRITO R, FUENTE_ARTICULO_DISTRITO F  " +
                " WHERE R.FUTI_ID=F.FUTI_ID AND R.TIRE_ID=" + tireId + " AND R.FUEN_ID ="+ fuenId +" AND R.MUNI_ID=F.MUNI_ID AND (TRANSMITIDO IS NULL OR TRANSMITIDO =0) " +
                "  ORDER BY R.ARTI_NOMBRE ";

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listaRecoleccion = new ArrayList<RecoleccionPrincipal>();
                do {
                    RecoleccionPrincipal recoleccionPrincipal  = new RecoleccionPrincipal();
                    recoleccionPrincipal.setArticuloId(c.getLong(0));
                    recoleccionPrincipal.setArticuloNombre(c.getString(1));
                    recoleccionPrincipal.setCasaComercialId(c.getLong(2));
                    recoleccionPrincipal.setCasaComercialNombre(c.getString(3));
                    recoleccionPrincipal.setUnidadMedidaId(c.getLong(4));
                    recoleccionPrincipal.setUnidadMedidaNombre(c.getString(5));
                    recoleccionPrincipal.setRegistroIcaId(c.getString(6));
                    recoleccionPrincipal.setGrupoNombre(c.getString(7));
                    recoleccionPrincipal.setArticacoId(c.getLong(8));
                    recoleccionPrincipal.setFuenNombre(c.getString(9));
                    recoleccionPrincipal.setMuniId(c.getString(10));

                    recoleccionPrincipal.setUnmeCantidad2(c.getDouble(11));
                    recoleccionPrincipal.setUnmeCantidadPpal(c.getDouble(12));
                    recoleccionPrincipal.setPrecio(c.getString(13));
                    recoleccionPrincipal.setNovedad(c.getString(14));
                    recoleccionPrincipal.setTipo(c.getString(15));


                    recoleccionPrincipal.setObservacion(c.getString(16));
                    recoleccionPrincipal.setDesviacion(c.getString(17));
                    recoleccionPrincipal.setTireId(c.getLong(18));
                    recoleccionPrincipal.setFuenId(c.getLong(19));
                    recoleccionPrincipal.setId(c.getLong(20));
                    recoleccionPrincipal.setTiprNombre(c.getString(21));
                    recoleccionPrincipal.setTiprId(c.getLong(22));
                    recoleccionPrincipal.setUnmeNombre2(c.getString(23));
                    recoleccionPrincipal.setUnidadMedidaNombre(c.getString(23));
                    recoleccionPrincipal.setPromAntDiario(c.getDouble(24));
                    recoleccionPrincipal.setEstadoRecoleccion(c.getInt(25)==0?false:true);
                    recoleccionPrincipal.setTransmitido(c.getInt(26)==0 ?false:true);
                    recoleccionPrincipal.setNovedadAnterior(c.getString(27));
                    recoleccionPrincipal.setFechaProgramada(new Date(c.getLong(28)));
                    recoleccionPrincipal.setTireNombre(c.getString(29));
                    recoleccionPrincipal.setSubgNombre(c.getString(30));
                    recoleccionPrincipal.setArtiVlrMinDiasM(c.getLong(31));
                    recoleccionPrincipal.setArtiVlrMaxDiasM(c.getLong(32));
                    recoleccionPrincipal.setArtiVlrMinTomas(c.getLong(33));
                    recoleccionPrincipal.setArtiVlrMaxTomas(c.getLong(34));
                    recoleccionPrincipal.setArtiVlrMinRondas(c.getLong(35));
                    recoleccionPrincipal.setArtiVlrMaxRondas(c.getLong(36));
                    recoleccionPrincipal.setFutiId(c.getLong(37));

                    recoleccionPrincipal.setFrecuencia(c.getString(38));
                    recoleccionPrincipal.setObservacionArticulo(c.getString(39));
                    recoleccionPrincipal.setUnidadMedidaOtroNombre(c.getString(40));




                    listaRecoleccion.add(recoleccionPrincipal);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listaRecoleccion;


    }


    @Override
    public List<Elemento> listaElementoDistritoByTireId(Long tireId, Long fuenId, String muniId) {
        List<Elemento> listaElmento = null;
        openReadableDb();

        String query = "SELECT  A.ARTI_ID, A.ARTICACO_ID, A.ARTI_NOMBRE, A.CACO_ID, A.CACO_NOMBRE, A.TIRE_ID, A.TIRE_NOMBRE, A.GRUP_NOMBRE, A.ARTICACO_REGICA_LINEA " +
                ", A.TIPO, A.FRECUENCIA, A.UNIDAD, A.OBSERVACION  " +
                " FROM ARTICULO_DISTRITO A WHERE A.TIRE_ID=" + tireId ;

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listaElmento = new ArrayList<Elemento>();
                do {
                    Elemento elemento  = new Elemento();
                    elemento.setArtiId(c.getLong(0));
                    elemento.setArticacoId(c.getLong(1));
                    elemento.setArtiNombre(c.getString(2));
                    elemento.setCacoId(c.getLong(3));
                    elemento.setCacoNombre(c.getString(4));
                    elemento.setTireId(c.getLong(5));
                    elemento.setTireNombre(c.getString(6));
                    elemento.setGrupNombre(c.getString(7));
                    elemento.setArticacoRegicaLinea(c.getString(8));

                    if(tireId==13){
                        elemento.setTipo(c.getString(9));
                        elemento.setFrecuencia(c.getString(10));
                        elemento.setUnidadMedidaNombre(c.getString(11));
                        elemento.setObservacion(c.getString(12));
                    }

                    listaElmento.add(elemento);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listaElmento;
    }

    public List<Elemento> listaElementoByTireId(Long tireId, Long fuenId, String muniId) {
        List<Elemento> listaElmento = null;
        openReadableDb();

        String query = "SELECT  A.ARTI_ID, A.ARTICACO_ID, A.ARTI_NOMBRE, A.CACO_ID, A.CACO_NOMBRE, A.TIRE_ID, A.TIRE_NOMBRE, A.GRUP_NOMBRE, A.ARTICACO_REGICA_LINEA " +
                ", A.TIPO, A.FRECUENCIA, A.UNIDAD, A.OBSERVACION  " +
                " FROM ARTICULO A WHERE A.TIRE_ID=" + tireId ;

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listaElmento = new ArrayList<Elemento>();
                do {
                    Elemento elemento  = new Elemento();
                    elemento.setArtiId(c.getLong(0));
                    elemento.setArticacoId(c.getLong(1));
                    elemento.setArtiNombre(c.getString(2));
                    elemento.setCacoId(c.getLong(3));
                    elemento.setCacoNombre(c.getString(4));
                    elemento.setTireId(c.getLong(5));
                    elemento.setTireNombre(c.getString(6));
                    elemento.setGrupNombre(c.getString(7));
                    elemento.setArticacoRegicaLinea(c.getString(8));

                    if(tireId==13){
                        elemento.setTipo(c.getString(9));
                        elemento.setFrecuencia(c.getString(10));
                        elemento.setUnidadMedidaNombre(c.getString(11));
                        elemento.setObservacion(c.getString(12));
                    }

                    listaElmento.add(elemento);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listaElmento;
    }



    @Override
    public synchronized List<Grupo> listaGrupoByTireId(Long tireId) {
        List<Grupo> grupos = null;
        try {
            openReadableDb();
            GrupoDao grupoDao = daoSession.getGrupoDao();
            grupos = grupoDao.queryBuilder()
                    .where(GrupoDao.Properties.TireId.eq(tireId)).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return grupos;
    }




    @Override
    public synchronized List<Fuente> listaFuenteArticulo (String idMunicipio) {
        List<Fuente> listFuente = null;

        openWritableDb();

        String query = "SELECT DISTINCT FUEN_ID, FUEN_NOMBRE, FUEN_DIRECCION, " +
                        "FUEN_TELEFONO, FUEN_NOMBRE_INFORMANTE, FUEN_EMAIL, MUNI_ID, MUNI_NOMBRE FROM FUENTE WHERE MUNI_ID = '"
                 + idMunicipio + "'" + " ORDER BY FUEN_NOMBRE";


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listFuente = new ArrayList<Fuente>();
                do {
                    Fuente fuente  = new Fuente();
                    fuente.setFuenId(c.getLong(0));
                    fuente.setFuenNombre(c.getString(1));
                    fuente.setFuenDireccion(c.getString(2));
                    fuente.setFuenTelefono(c.getString(3));
                    fuente.setFuenNombreInformante(c.getString(4));
                    fuente.setFuenEmail(c.getString(5));
                    fuente.setMuniId(c.getString(6));
                    fuente.setMuniNombre(c.getString(7));
                    listFuente.add(fuente);

                    //Log.d(TAG, fuente.getFuenNombre()+"cargar fuentes--->" + fuente.getFuenNombreInformante());
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listFuente;
    }

    public synchronized List<FuenteDistrito> listaFuenteArticuloDistrito (String idMunicipio) {
        List<FuenteDistrito> listFuente = null;

        openWritableDb();

        String query = "SELECT DISTINCT F.FUEN_ID, F.FUEN_NOMBRE, FA.FUEN_DIRECCION, " +
                "FA.FUEN_TELEFONO, FA.FUEN_NOMBRE_INFORMANTE, FA.FUEN_EMAIL, F.MUNI_ID, " +
                "F.MUNI_NOMBRE, FA.FUEN_TELEFONO_INFORMANTE, FA.FUEN_CODIGO_INTERNO, F.TIRE_ID  " +
                "FROM FUENTE_DISTRITO F, FUENTE_ARTICULO_DISTRITO FA WHERE F.MUNI_ID = '"
                 + idMunicipio + "'" + "AND F.TIRE_ID = 13 AND FA.FUEN_ID=F.FUEN_ID " +
                "AND F.TIRE_ID = FA.TIRE_ID " +
                "ORDER BY F.FUEN_NOMBRE";


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listFuente = new ArrayList<FuenteDistrito>();
                do {
                    FuenteDistrito fuente  = new FuenteDistrito();
                    fuente.setFuenId(c.getLong(0));
                    fuente.setFuenNombre(c.getString(1));
                    fuente.setFuenDireccion(c.getString(2));
                    fuente.setFuenTelefono(c.getString(3));
                    fuente.setFuenNombreInformante(c.getString(4));
                    fuente.setFuenEmail(c.getString(5));
                    fuente.setMuniId(c.getString(6));
                    fuente.setMuniNombre(c.getString(7));
                    fuente.setFuenTelefonoInformante(c.getString(8));
                    //fuente.setFuenCodigoInterno(c.getString(9));
                    fuente.setTireId(c.getLong(10));
                    listFuente.add(fuente);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listFuente;
    }

    @Override
    public synchronized List<TipoRecoleccion> listaTipoRecoleccion() {
        List<TipoRecoleccion> tipoRecoleccions = null;
        try {
            openReadableDb();
            TipoRecoleccionDao tipoRecoleccionDao = daoSession.getTipoRecoleccionDao();
            tipoRecoleccions = tipoRecoleccionDao.loadAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return tipoRecoleccions;
    }


    @Override
    public synchronized List<CasaComercial> listaCasaComercial() {
        List<CasaComercial> casaComercials = null;
        try {
            openReadableDb();
            CasaComercialDao casaComercialDao = daoSession.getCasaComercialDao();
            casaComercials = casaComercialDao.loadAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return casaComercials;
    }





    @Override
    public synchronized List<UnidadMedida> listaUnidadMedidaByPresentacionId(Long idPresentacion) {
        List<UnidadMedida> unidadMedidaList = null;
        try {
            openReadableDb();
            UnidadMedidaDao unidadMedidaDao = daoSession.getUnidadMedidaDao();
            unidadMedidaList = unidadMedidaDao.queryBuilder()
                    .where(UnidadMedidaDao.Properties.TiprId.eq(idPresentacion)).orderAsc(UnidadMedidaDao.Properties.UnmeNombrePpal,UnidadMedidaDao.Properties.UnmeCantidadPpal).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return unidadMedidaList;
    }

    @Override
    public synchronized List<CaracteristicaDistrito> listaUnidadMedidaByPresentacionIdDistrito(Long idValor) {
        List<CaracteristicaDistrito> caraTemp = null;
        List<CaracteristicaDistrito> caraList = new ArrayList<CaracteristicaDistrito>();
        CaracteristicaDistrito select = new CaracteristicaDistrito();
        select.setVapeDescripcion("Seleccione...");
        select.setVapeId(0L);
        caraList.add(select);
        try {
            openReadableDb();
            CaracteristicaDistritoDao caractericiticas = daoSession.getCaracteristicaDistritoDao();
            Long datos = caractericiticas.count();
            caraTemp = caractericiticas.queryBuilder().where(CaracteristicaDistritoDao.Properties.CaraId.eq(idValor)).orderAsc(CaracteristicaDistritoDao.Properties.VapeDescripcion).list();

            for(CaracteristicaDistrito lista : caraTemp){
                CaracteristicaDistrito nuevo = new CaracteristicaDistrito();
                nuevo.setVapeId(lista.getVapeId());
                nuevo.setId(lista.getId());
                nuevo.setCaraId(lista.getCaraId());
                nuevo.setTireId(lista.getTireId());
                nuevo.setVapeDescripcion(lista.getVapeDescripcion());
                nuevo.setCaraDescripcion(lista.getCaraDescripcion());
                caraList.add(nuevo);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return caraList;
    }

    @Override
    public synchronized List<PrincipalI01> listaPrincipalI01ByArticulo(Long artiId, Long grin2Id, Long futiId) {
        List<PrincipalI01> principalI01List = null;
        try {
            openReadableDb();
            PrincipalI01Dao principalI01Dao = daoSession.getPrincipalI01Dao();
            principalI01List = principalI01Dao.queryBuilder()
                    .where(PrincipalI01Dao.Properties.ArtiId.eq(artiId)).
                            where(PrincipalI01Dao.Properties.Grin2Id.eq(grin2Id)).
                                where(PrincipalI01Dao.Properties.FutiId.eq(futiId)).
                                    where (PrincipalI01Dao.Properties.Estado_trasmitido.isNull()).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return principalI01List;
    }



    @Override
    public synchronized List<Fuente> listaFuentes() {
        List<Fuente> fuentes = null;
        try {
            openReadableDb();
            FuenteDao fuentesDao = daoSession.getFuenteDao();
            fuentes = fuentesDao.loadAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return fuentes;
    }


    @Override
    public synchronized List<Fuente> listaFuentesByTransmitidoEstado(boolean transmitido) {
        List<Fuente> fuentes = null;
        try {
            openReadableDb();
            FuenteDao fuenteDao = daoSession.getFuenteDao();
            if (transmitido) {
                fuentes = fuenteDao.queryRawCreate(" , RECOLECCION R WHERE R.TRANSMITIDO = 1 AND R.ESTADO_RECOLECCION = 1 AND R.FUEN_ID = F.FUEN_ID GROUP BY T.FUEN_ID ").list();
            } else {
                fuentes = fuenteDao.queryRawCreate(" , RECOLECCION R WHERE R.ESTADO_RECOLECCION = 1 AND  R.TRANSMITIDO IS NULL AND R.FUEN_ID = T.FUEN_ID GROUP BY T.FUEN_ID ").list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return fuentes;
    }

    @Override
    public synchronized List<FuenteDistrito> listaFuentesByTransmitidoEstadoDistrito(boolean transmitido) {

        List<FuenteDistrito> listFuente = null;
        String query = "";
        openWritableDb();

        if (transmitido) {
            query = "SELECT DISTINCT F.FUEN_ID, F.FUEN_NOMBRE, FA.FUEN_DIRECCION, " +
                    "FA.FUEN_TELEFONO, FA.FUEN_NOMBRE_INFORMANTE, FA.FUEN_EMAIL, F.MUNI_ID, " +
                    "F.MUNI_NOMBRE, FA.FUEN_TELEFONO_INFORMANTE, FA.FUEN_CODIGO_INTERNO, F.TIRE_ID  " +
                    "FROM FUENTE_DISTRITO F, FUENTE_ARTICULO_DISTRITO FA,  RECOLECCION_DISTRITO R  WHERE F.TIRE_ID = 13 AND FA.FUEN_ID=F.FUEN_ID " +
                    "AND F.TIRE_ID = FA.TIRE_ID AND R.TRANSMITIDO = 1 AND R.ESTADO_RECOLECCION = 1 " +
                    "AND R.FUEN_ID = F.FUEN_ID " +
                    "ORDER BY F.FUEN_NOMBRE";
        }else{
            query = "SELECT DISTINCT F.FUEN_ID, F.FUEN_NOMBRE, FA.FUEN_DIRECCION, " +
                    "FA.FUEN_TELEFONO, FA.FUEN_NOMBRE_INFORMANTE, FA.FUEN_EMAIL, F.MUNI_ID, " +
                    "F.MUNI_NOMBRE, FA.FUEN_TELEFONO_INFORMANTE, FA.FUEN_CODIGO_INTERNO, F.TIRE_ID  " +
                    "FROM FUENTE_DISTRITO F, FUENTE_ARTICULO_DISTRITO FA,  RECOLECCION_DISTRITO R  WHERE F.TIRE_ID = 13 AND FA.FUEN_ID=F.FUEN_ID " +
                    "AND F.TIRE_ID = FA.TIRE_ID AND R.TRANSMITIDO IS NULL AND R.ESTADO_RECOLECCION = 1 " +
                    "AND R.FUEN_ID = F.FUEN_ID " +
                    "ORDER BY F.FUEN_NOMBRE";
        }

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listFuente = new ArrayList<FuenteDistrito>();
                do {
                    FuenteDistrito fuente  = new FuenteDistrito();
                    fuente.setFuenId(c.getLong(0));
                    fuente.setFuenNombre(c.getString(1));
                    fuente.setFuenDireccion(c.getString(2));
                    fuente.setFuenTelefono(c.getString(3));
                    fuente.setFuenNombreInformante(c.getString(4));
                    fuente.setFuenEmail(c.getString(5));
                    fuente.setMuniId(c.getString(6));
                    fuente.setMuniNombre(c.getString(7));
                    fuente.setFuenTelefonoInformante(c.getString(8));
                    //fuente.setFuenCodigoInterno(c.getString(9));
                    fuente.setTireId(c.getLong(10));
                    listFuente.add(fuente);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listFuente;

    }




    @Override
    public synchronized List<PrincipalI01> getPrincipalI01() {
        List<PrincipalI01> ListaPrincipal = null;

        openReadableDb();
        String query ="SELECT _id, MUNI_ID, TIRE_ID, NOMBRE_COMPLEMENTO, ARTI_ID,GRIN2_ID, ARTI_NOMBRE, FUENTES_CAPTURADAS, " +
                " PROM_ANTERIOR, FUTI_ID,PRRE_FECHA_PROGRAMADA " +
                " FROM PRINCIPAL_I01 WHERE ESTADO_TRASMITIDO IS NULL AND FUENTES_CAPTURADAS>0";

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                ListaPrincipal = new ArrayList<PrincipalI01>();
                do {
                    PrincipalI01 principalI01  = new PrincipalI01();
                    principalI01.setId(c.getLong(0));
                    principalI01.setMuniId(c.getString(1));
                    principalI01.setTireId(c.getLong(2));
                    principalI01.setNombreComplemento(c.getString(3));
                    principalI01.setArtiId(c.getLong(4));
                    principalI01.setGrin2Id(c.getLong(5));
                    principalI01.setArtiNombre(c.getString(6));
                    principalI01.setFuentesCapturadas(c.getInt(7));
                    principalI01.setPromAnterior(c.getDouble(8));
                    principalI01.setFutiId(c.getLong(9));
                    principalI01.setPrreFechaProgramada(new Date(c.getLong(10)));

                    ListaPrincipal.add(principalI01);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return ListaPrincipal;
    }

    @Override
    public synchronized List<PrincipalI01> noFuentes(Long artiId, Long grin2Id, Long futiId) {
        List<PrincipalI01> ListaPrincipal = null;
        //String query = "SELECT COUNT(*) FROM RECOLECCION_I01  WHERE GRIN2_ID="+grin2Id+" AND ARTI_ID="+artiId+ "AND FUTI_ID="+futiId;
        String query = "SELECT FUENTES_CAPTURADAS FROM PRINCIPAL_I01  WHERE GRIN2_ID="+grin2Id+" AND ARTI_ID="+artiId+ " AND FUTI_ID="+futiId;

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                ListaPrincipal = new ArrayList<PrincipalI01>();
                do {
                    PrincipalI01 principalI01  = new PrincipalI01();
                    principalI01.setFuentesCapturadas(c.getInt(0));

                    ListaPrincipal.add(principalI01);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return ListaPrincipal;
    }

    public synchronized FuenteTireI01 obtenerFuenTire(Long tireId, String muniId) {
        FuenteTireI01 fuenteTireI01 = null;
        String query = "SELECT FUTI_ID, PRRE_FECHA_PROGRAMADA, FUEN_ID FROM FUENTE_TIRE_I01 WHERE TIRE_ID="+tireId+" AND MUNI_ID='"+muniId+"'";

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                fuenteTireI01 = new FuenteTireI01();
                do {
                    fuenteTireI01.setFutiId(c.getLong(0));
                    fuenteTireI01.setPrreFechaProgramada(new Date(c.getLong(1)));
                    fuenteTireI01.setFuenId(c.getLong(2));

                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return fuenteTireI01;
    }


    @Override
    public synchronized List<ArticuloI01> listaElementoByTransmitidoEstado01(boolean transmitido) {
        List<ArticuloI01> listaArticuloI01s = null;

        openReadableDb();
        String query ="";

        if (!transmitido) {
             query = "SELECT R.ARTI_ID, R.GRIN2_ID, A.ARTI_NOMBRE, A.NOMBRE_COMPLETO, A.TIRE_ID " +
                    "FROM RECOLECCION_I01 R, ARTICULO_I01 A " +
                    "WHERE R.ARTI_ID=A.ARTI_ID AND R.GRIN2_ID=A.GRIN2_ID AND R.ESTADO_TRANSMITIDO IS NULL AND A.TIRE_ID != 13 " +
                    "GROUP BY R.ARTI_ID, R.GRIN2_ID, A.ARTI_NOMBRE, A.NOMBRE_COMPLETO, A.TIRE_ID ";
        } else {
             query = "SELECT R.ARTI_ID, R.GRIN2_ID, A.ARTI_NOMBRE, A.NOMBRE_COMPLETO, A.TIRE_ID " +
                    "FROM RECOLECCION_I01 R, ARTICULO_I01 A " +
                    "WHERE R.ARTI_ID=A.ARTI_ID AND R.GRIN2_ID=A.GRIN2_ID AND R.ESTADO_TRANSMITIDO=1 AND A.TIRE_ID != 13 " +
                    "GROUP BY R.ARTI_ID, R.GRIN2_ID, A.ARTI_NOMBRE, A.NOMBRE_COMPLETO, A.TIRE_ID ";
        }

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listaArticuloI01s = new ArrayList<ArticuloI01>();
                do {
                    ArticuloI01 articuloI01  = new ArticuloI01();
                    articuloI01.setArtiId(c.getLong(0));
                    articuloI01.setGrin2Id(c.getLong(1));
                    articuloI01.setArtiNombre(c.getString(2));
                    articuloI01.setNombreCompleto(c.getString(3));
                    articuloI01.setTireId(c.getLong(4));

                    listaArticuloI01s.add(articuloI01);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listaArticuloI01s;
    }


    @Override
    public synchronized List<FuenteArticulo> listaFuenteArticuloByTransmitidoEstado() {
        List<FuenteArticulo> listFuenteArticulo = null;

        openWritableDb();

        String query = "SELECT DISTINCT F.FUEN_ID, F.FUEN_NOMBRE, F.FUEN_DIRECCION, F.FUEN_TELEFONO, " +
                "F.FUEN_EMAIL, F.MUNI_ID, F.MUNI_NOMBRE , FA.FUTI_ID, FA.TIRE_ID,  FA.TIRE_NOMBRE, F.FUEN_NOMBRE_INFORMANTE, FA.PRRE_FECHA_PROGRAMADA  " +
                       " FROM FUENTE F, FUENTE_ARTICULO FA, RECOLECCION R WHERE  FA.FUEN_ID=F.FUEN_ID AND FA.FUEN_ID=R.FUEN_ID";


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listFuenteArticulo = new ArrayList<FuenteArticulo>();
                do {
                    FuenteArticulo fuenteArticulo  = new FuenteArticulo();
                    fuenteArticulo.setFuenId(c.getLong(0));
                    fuenteArticulo.setFuenNombre(c.getString(1));
                    fuenteArticulo.setFuenDireccion(c.getString(2));
                    fuenteArticulo.setFuenTelefono(c.getString(3));
                    fuenteArticulo.setFuenEmail(c.getString(4));
                    fuenteArticulo.setMuniId(c.getString(5));
                    fuenteArticulo.setMuniNombre(c.getString(6));
                    fuenteArticulo.setFutiId(c.getLong(7));
                    fuenteArticulo.setTireId(c.getLong(8));
                    fuenteArticulo.setTireNombre(c.getString(9));
                    fuenteArticulo.setFuenNombreInformante(c.getString(10));
                    fuenteArticulo.setPrreFechaProgramada(new Date(c.getLong(11)));


                    listFuenteArticulo.add(fuenteArticulo);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listFuenteArticulo;
    }

    @Override
    public synchronized List<FuenteArticuloDistrito> listaFuenteArticuloByTransmitidoEstadoDistrito() {
        List<FuenteArticuloDistrito> listFuenteArticulo = null;

        openWritableDb();

        String query = "SELECT DISTINCT F.FUEN_ID, F.FUEN_NOMBRE, FA.FUEN_DIRECCION, FA.FUEN_TELEFONO, " +
                "FA.FUEN_EMAIL, F.MUNI_ID, F.MUNI_NOMBRE , FA.FUTI_ID, FA.TIRE_ID,  FA.TIRE_NOMBRE, FA.FUEN_NOMBRE_INFORMANTE , FA.FUEN_CODIGO_INTERNO " +
                ", FA   .FUEN_TELEFONO_INFORMANTE "+
                " FROM FUENTE_DISTRITO F, FUENTE_ARTICULO_DISTRITO FA, RECOLECCION_DISTRITO R WHERE  FA.FUEN_ID=F.FUEN_ID AND FA.FUEN_ID=R.FUEN_ID " +
                " AND R.ESTADO_RECOLECCION = 1 AND R.TRANSMITIDO IS NULL OR R.TRANSMITIDO =0 ";


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                listFuenteArticulo = new ArrayList<FuenteArticuloDistrito>();
                do {
                    FuenteArticuloDistrito fuenteArticulo  = new FuenteArticuloDistrito();
                    fuenteArticulo.setFuenId(c.getLong(0));
                    fuenteArticulo.setFuenNombre(c.getString(1));
                    fuenteArticulo.setFuenDireccion(c.getString(2));
                    fuenteArticulo.setFuenTelefono(c.getString(3));
                    fuenteArticulo.setFuenEmail(c.getString(4));
                    fuenteArticulo.setMuniId(c.getString(5));
                    fuenteArticulo.setMuniNombre(c.getString(6));
                    fuenteArticulo.setFutiId(c.getLong(7));
                    fuenteArticulo.setTireId(c.getLong(8));
                    fuenteArticulo.setTireNombre(c.getString(9));
                    fuenteArticulo.setFuenNombreInformante(c.getString(10));
                    //fuenteArticulo.setFuenCodigoInterno(c.getString(11));
                    fuenteArticulo.setFuenTelefonoInformante(c.getString(12));


                    listFuenteArticulo.add(fuenteArticulo);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return listFuenteArticulo;
    }


    @Override
    public synchronized List<ObservacionElem> listaObservaciones(String novedad) {
        List<ObservacionElem> observacionElems =  new ArrayList<ObservacionElem>();
        openWritableDb();
        String query = "SELECT * FROM OBSERVACION WHERE NOVEDAD is null";
        if(novedad!=null){
             query = "SELECT * FROM OBSERVACION WHERE NOVEDAD = '" + novedad + "'";
        }

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {

                do {
                    ObservacionElem observacionElem  = new ObservacionElem();
                    observacionElem.setObseId(c.getLong(0));
                    observacionElem.setObseTipo(c.getString(1));
                    observacionElem.setNovedad(c.getString(2));
                    observacionElem.setObseDescripcion(c.getString(3));
                    observacionElems.add(observacionElem);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return observacionElems;
    }

    @Override
    public synchronized List<ObservacionElem> listaObservacionesDistrito(String novedad) {
        List<ObservacionElem> observacionElems =  new ArrayList<ObservacionElem>();
        openWritableDb();
        String query = "SELECT * FROM OBSERVACION_DISTRITO WHERE NOVEDAD is null";
        if(novedad!=null){
            query = "SELECT * FROM OBSERVACION_DISTRITO WHERE NOVEDAD = '" + novedad + "'";
        }

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {

                do {
                    ObservacionElem observacionElem  = new ObservacionElem();
                    observacionElem.setObseId(c.getLong(0));
                    observacionElem.setObseTipo(c.getString(1));
                    observacionElem.setNovedad(c.getString(2));
                    observacionElem.setObseDescripcion(c.getString(3));
                    observacionElems.add(observacionElem);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return observacionElems;
    }

    @Override
    public synchronized List<ObservacionElem> listaObservacionesTodas01() {
        List<ObservacionElem> observacionElems =  new ArrayList<ObservacionElem>();
        openWritableDb();
        String query = "SELECT * FROM OBSERVACION_I01";

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {

                do {
                    ObservacionElem observacionElem  = new ObservacionElem();
                    observacionElem.setObseId(c.getLong(0));
                    observacionElem.setObseTipo(c.getString(1));
                    observacionElem.setNovedad(c.getString(2));
                    observacionElem.setObseDescripcion(c.getString(3));
                    observacionElems.add(observacionElem);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return observacionElems;
    }

    @Override
    public synchronized List<ObservacionElem> listaObservacionesExistentes01() {
        List<ObservacionElem> observacionElems =  new ArrayList<ObservacionElem>();
        openWritableDb();
        String query = "SELECT * FROM OBSERVACION_I01 WHERE OBSE_ID>9000000000";

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {

                do {
                    ObservacionElem observacionElem  = new ObservacionElem();
                    observacionElem.setObseId(c.getLong(0));
                    observacionElem.setObseTipo(c.getString(1));
                    observacionElem.setNovedad(c.getString(2));
                    observacionElem.setObseDescripcion(c.getString(3));
                    observacionElems.add(observacionElem);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return observacionElems;
    }

    @Override
    public synchronized List<ObservacionElem> listaObservaciones01(String novedad) {
        List<ObservacionElem> observacionElems =  new ArrayList<ObservacionElem>();
        openWritableDb();
        String query = "SELECT * FROM OBSERVACION_I01 WHERE NOVEDAD is null";
        if(novedad!=null){
            query = "SELECT * FROM OBSERVACION_I01 WHERE NOVEDAD = '" + novedad + "'";
        }

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {

                do {
                    ObservacionElem observacionElem  = new ObservacionElem();
                    observacionElem.setObseId(c.getLong(0));
                    observacionElem.setObseTipo(c.getString(1));
                    observacionElem.setNovedad(c.getString(2));
                    observacionElem.setObseDescripcion(c.getString(3));
                    observacionElems.add(observacionElem);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return observacionElems;
    }

    @Override
    public synchronized List<Observacion> listaObservacionExistentes(Long idFuente) {
        List<Observacion> observaciones = null;
        try {
            openReadableDb();
            ObservacionDao observacionDao = daoSession.getObservacionDao();
            observaciones = observacionDao.queryRawCreate(" WHERE T.ID_OBSERVACION IN (SELECT DISTINCT ID_OBSERVACION FROM RECOLECCION WHERE ID_OBSERVACION != -1 AND ID_FUENTE = " + idFuente + ")").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return observaciones;
    }


    @Override
    public synchronized List<GrupoFuente> listGrupoFuente() {
        List<GrupoFuente> grupoFuente =null;
        openWritableDb();
        Cursor c = daoSession.getDatabase().rawQuery("SELECT DISTINCT TIPO_FUENTE,GRUPO FROM ESTRATO  ", null);
        ArrayList<String> result = new ArrayList<String>();
        try {
            if (c.moveToFirst()) {
                grupoFuente= new ArrayList<GrupoFuente>();
                do {
                    GrupoFuente gf = new GrupoFuente();
                    gf.setId(Long.parseLong(c.getString(0)));
                    gf.setNombre(c.getString(1));

                    grupoFuente.add(gf);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            c.close();
            daoSession.clear();
        }
        return grupoFuente;
    }






    @Override
    public synchronized Long save(Object object) {
        Long id = null;
        try {
            openWritableDb();
            id = daoSession.insertOrReplace(object);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return id;
    }

    @Override
    public synchronized void delete(Object object) {
        if(object!=null) {
            Long id = null;
            try {
                openWritableDb();
                daoSession.delete(object);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                daoSession.clear();
            }
        }
    }

    @Override
    public synchronized void deleteAll(Class clazz) {

        try {
            openWritableDb();
            daoSession.deleteAll(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
    }


    @Override
    public synchronized RecoleccionInsumo getRecoleccionById(Long recoleccionId) {
        RecoleccionInsumo recoleccion = null;
        try {
            openReadableDb();
            RecoleccionInsumoDao recoleccionDao = daoSession.getRecoleccionInsumoDao();
            recoleccion = recoleccionDao.loadDeep(recoleccionId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return recoleccion;
    }

    @Override
    public synchronized RecoleccionI01 getRecoleccionI01ById(Long recoleccionId) {
        RecoleccionI01 recoleccion = null;
        try {
            openReadableDb();
            RecoleccionI01Dao recoleccionDao = daoSession.getRecoleccionI01Dao();
            recoleccion = recoleccionDao.loadByRowId(recoleccionId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return recoleccion;
    }

    @Override
    public synchronized List<RecoleccionI01> getRecoleccionI01() {
        List<RecoleccionI01> recolecciones= null;
        try {
            openReadableDb();
            recolecciones = daoSession.getRecoleccionI01Dao().queryRawCreate(" WHERE ESTADO_TRANSMITIDO IS NULL").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return recolecciones;
    }

    @Override
    public synchronized List<EnvioArtiCaraValoresI01> getArtiCaraValoresI01(){
        List<EnvioArtiCaraValoresI01> artiCara = new ArrayList<>();

        String query = " SELECT  A.ARTI_ID, A.GRIN2_ID, P.FUTI_ID, A.TIRE_ID, A.VAPE_ID, A.CARA_ID, A.VAPE_DESCRIPCION " +
                " FROM ARTI_CARA_VALORES_I01 A, PRINCIPAL_I01 P " +
                " WHERE A.ARTI_ID=P.ARTI_ID AND A.GRIN2_ID=P.GRIN2_ID AND P.ESTADO_TRASMITIDO IS NULL AND A.GRIN2_ID>9000000000 ";


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {

                do {
                    EnvioArtiCaraValoresI01 artiCaraValoresI01  = new EnvioArtiCaraValoresI01();
                    artiCaraValoresI01.setArtiId(c.getLong(0));
                    artiCaraValoresI01.setGrin2Id(c.getLong(1));
                    artiCaraValoresI01.setFutiId(c.getLong(2));
                    artiCaraValoresI01.setTireId(c.getLong(3));
                    artiCaraValoresI01.setVapeId(c.getLong(4));
                    artiCaraValoresI01.setCaraId(c.getLong(5));
                    artiCaraValoresI01.setVapeDescripcion(c.getString(6));


                    artiCara.add(artiCaraValoresI01);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }


      return artiCara;
    }



    @Override
    public synchronized void updateRecoleccion(RecoleccionInsumo recoleccion) {
        try {
            if (recoleccion != null) {
                openWritableDb();
                daoSession.update(recoleccion);
                Log.d(TAG, "Updated recoleccion con idRecoleccion : " + recoleccion.getId() + " from the schema.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
    }

    @Override
    public synchronized void updateSecuencias() {
        try {

            openWritableDb();
            FuenteArticulo f = new FuenteArticulo();
            Long idfuti = save(f);

            GrupoInsumoI01 g = new GrupoInsumoI01();
            Long id = save(g);

            ObservacionI01 o = new ObservacionI01();
            Long idObservacion = save(o);

            InformadorI01 p = new InformadorI01();
            Long infoId =save(p);

            o.setObseId(idObservacion);
            f.setFutiId(idfuti);
            g.setId(id);
            p.setInfoId(infoId);

            delete(o);
            delete(f);
            delete(g);
            delete(p);

            daoSession.getDatabase().execSQL("UPDATE SQLITE_SEQUENCE SET seq = 9000000000");
            Log.d(TAG, "Actualizaron las secuencias");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
    }

    public synchronized void updateSecuenciasDistrito() {
        try {

            openWritableDb();
            FuenteArticuloDistrito f = new FuenteArticuloDistrito();
            Long idfuti = save(f);

            ObservacionDistrito o = new ObservacionDistrito();
            Long idObservacion = save(o);

            FuenteDistrito p = new FuenteDistrito();
            Long infoId =save(p);

            o.setObseId(idObservacion);
            f.setFutiId(idfuti);

            p.setFuenId(infoId);

            delete(o);
            delete(f);
            delete(p);

            daoSession.getDatabase().execSQL("UPDATE SQLITE_SEQUENCE SET seq = 9000000000");
            Log.d(TAG, "Actualizaron las secuencias Distrito");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
    }


    @Override
    public synchronized List<ResumenFuente> obtenerResumenFuente() {
        List<ResumenFuente> fuentesIndices = null;
        openWritableDb();

        String query = "SELECT DISTINCT FUEN_NOMBRE, FUEN_ID, (SELECT COUNT(*) FROM RECOLECCION R2 WHERE R2.FUEN_ID=R1.FUEN_ID) TOTAL," +
                "(SELECT COUNT(*) FROM RECOLECCION R3 WHERE R3.FUEN_ID=R1.FUEN_ID AND ESTADO_RECOLECCION = 1) COMPLETAS "  +
                "FROM RECOLECCION R1";

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                fuentesIndices = new ArrayList<ResumenFuente>();
                do {
                    ResumenFuente resumenFuente = new ResumenFuente();
                    resumenFuente.setNombreIndice(c.getString(0));
                    resumenFuente.setIdFuente(c.getLong(1));
                    resumenFuente.setTotalElementos(c.getInt(2));
                    resumenFuente.setRecolectados(c.getInt(3));
                    fuentesIndices.add(resumenFuente);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
            c.close();
        }
        return fuentesIndices;
    }


    @Override
    public synchronized Integer cantidadFuentesSinRecoleccion() {
        //obtenerSecuencia();
        String query = "SELECT DISTINCT F._ID, (SELECT COUNT(*) FROM RECOLECCION R WHERE R.ID_FUENTE = F._ID) CANT " +
                "FROM FUENTE F INNER JOIN RECOLECCION R ON  R.ID_FUENTE <> F._ID " +
                "GROUP BY F._ID HAVING CANT = 0";

        Integer count = 0;
        try {
            openWritableDb();
            count = daoSession.getDatabase().rawQuery(query, null).getCount();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return count;
    }

    @Override
    public synchronized void obtenerSecuencia() {
        openWritableDb();
        String query = "";
        query = "SELECT * FROM SQLITE_SEQUENCE  WHERE name = 'FUENTE'";
        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {

                do {
                    c.getLong(1);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
            daoSession.clear();
        }
    }

    @Override
    public synchronized Usuario getUsuario(String nombreUsuario, String clave) {
        Usuario usuario = null;
        try {
            openReadableDb();
            UsuarioDao usuarioDao = daoSession.getUsuarioDao();
            usuario = usuarioDao.queryBuilder()
                    .where(UsuarioDao.Properties.Usuario.eq(nombreUsuario),
                            UsuarioDao.Properties.Clave.eq(clave)).unique();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return usuario;
    }

    @Override
    public synchronized Usuario getUsuario(String nombreUsuario) {
        Usuario usuario = null;
        try {
            openReadableDb();
            UsuarioDao usuarioDao = daoSession.getUsuarioDao();
            usuario = usuarioDao.queryBuilder()
                    .where(UsuarioDao.Properties.NombrePerfil.eq(nombreUsuario)).unique();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
        }
        return usuario;
    }

    @Override
    public synchronized Usuario getUsuario() {
        Usuario usuario = null;
        try {
            openReadableDb();
            UsuarioDao usuarioDao = daoSession.getUsuarioDao();
            usuario = usuarioDao.queryBuilder()
                    .unique();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            daoSession.clear();
        }
        return usuario;
    }




    public synchronized Integer getFuentesByEstado(String estado) {
        Integer count = 0;
        String query = "SELECT DISTINCT F.FUEN_ID, " +
                "(SELECT COUNT(R.FUEN_ID) " +
                "FROM RECOLECCION R " +
                "WHERE R.FUEN_ID = F.FUEN_ID AND R.TRANSMITIDO IS NULL OR R.TRANSMITIDO = 0  ) TODAS,  " +

                "(SELECT COUNT(R.FUEN_ID) " +
                "FROM RECOLECCION R " +
                "WHERE R.FUEN_ID = F.FUEN_ID  AND R.ESTADO_RECOLECCION = 1 AND R.TRANSMITIDO IS NULL OR R.TRANSMITIDO =0)  COMPLETAS " +

                "FROM FUENTE F " +
                "WHERE F.FUEN_ID IN (SELECT R.FUEN_ID FROM RECOLECCION R) " +
                "GROUP BY F.FUEN_ID ";

        if (estado.equals("C")) {
            query += " HAVING COMPLETAS = TODAS AND COMPLETAS != 0";
        } else if (estado.equals("I")) {
            query += " HAVING COMPLETAS != TODAS AND COMPLETAS != 0";
        } else if (estado.equals("")) {
            query += " HAVING COMPLETAS != TODAS AND COMPLETAS = 0";
        }
        try {
            openWritableDb();
            count = daoSession.getDatabase().rawQuery(query, null).getCount();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return count;
    }

    @Override
    public synchronized Integer getElementosByEstados01(String estado) {
        Integer result = 0;
        Integer todas = 0;
        Integer completas = 0;
        String query="";

            query ="SELECT COUNT (*) COMPLETA, (SELECT  COUNT(*)  FROM PRINCIPAL_I01) PENDIENTE " +
                    "FROM (SELECT FUTI_ID, ARTI_ID, GRIN2_ID, COUNT(*) AS COMPLETA " +
                    "FROM RECOLECCION_I01 " +
                    "GROUP BY FUTI_ID, ARTI_ID, GRIN2_ID) R " +
                    "WHERE R.COMPLETA >=3";


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                do {
                    completas = c.getInt(0);
                    todas = c.getInt(1);

                } while (c.moveToNext());
            }
            if (estado.equals("T")) {
                result = todas;
            } else if (estado.equals("C")) {
                result = completas;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
            daoSession.clear();
        }
        return result;
    }

    @Override
    public synchronized Integer getElementosByEstadosDistrito(String estado) {
        Integer result = 0;
        String query = "SELECT DISTINCT F.FUEN_ID, " +
                "(SELECT COUNT(R.FUEN_ID) " +
                "FROM RECOLECCION_DISTRITO R " +
                "WHERE R.FUEN_ID = F.FUEN_ID AND R.TRANSMITIDO IS NULL OR R.TRANSMITIDO = 0  ) TODAS,  " +

                "(SELECT COUNT(R.FUEN_ID) " +
                "FROM RECOLECCION_DISTRITO R " +
                "WHERE R.FUEN_ID = F.FUEN_ID  AND R.ESTADO_RECOLECCION = 1 AND R.TRANSMITIDO IS NULL OR R.TRANSMITIDO =0)  COMPLETAS " +

                "FROM FUENTE_ARTICULO_DISTRITO F " +
                "WHERE F.FUEN_ID IN (SELECT R.FUEN_ID FROM RECOLECCION_DISTRITO R) " +
                "GROUP BY F.FUEN_ID ";

        if (estado.equals("C")) {
            query += " HAVING COMPLETAS = TODAS AND COMPLETAS != 0";
        } else if (estado.equals("I")) {
            query += " HAVING COMPLETAS != TODAS AND COMPLETAS != 0";
        } else if (estado.equals("")) {
            query += " HAVING COMPLETAS != TODAS AND COMPLETAS = 0";
        }
        try {
            openWritableDb();
            result = daoSession.getDatabase().rawQuery(query, null).getCount();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }

        /*
        Integer result = 0;
        Integer todas = 0;
        Integer completas = 0;
        String query="";

        query ="SELECT COUNT (*) COMPLETA, (SELECT  COUNT(*)  FROM RECOLECCION_DISTRITO WHERE " +
                //"(ESTADO_RECOLECCION IS NULL OR ESTADO_RECOLECCION = 0) " +
                //" (TRANSMITIDO IS NULL OR TRANSMITIDO = 0) " +
                " TIRE_ID=13) PENDIENTE " +
                "FROM (SELECT FUTI_ID, ARTI_ID, TIRE_ID, COUNT(*) AS COMPLETA " +
                "FROM RECOLECCION_DISTRITO WHERE  TIRE_ID=13 "+//AND ESTADO_RECOLECCION = 1" +
                "GROUP BY FUTI_ID, ARTI_ID, TIRE_ID) R ";


        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                do {
                    completas = c.getInt(0);
                    todas = c.getInt(1);

                } while (c.moveToNext());
            }
            if (estado.equals("T")) {
                result = todas;
            } else if (estado.equals("C")) {
                result = completas;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
            daoSession.clear();
        }*/
        return result;
    }

    @Override
    public synchronized String getEstadoFuente(Long idFuente, Long periodo) {
        openWritableDb();
        String result = "";
        Integer todas = 0;
        Integer completas = 0;

        String query = "SELECT DISTINCT F._ID, " +
                "(SELECT COUNT(R._ID) " +
                "FROM RECOLECCION R " +
                "WHERE R.ID_FUENTE = F._ID AND R.TRANSMITIDO IS NULL OR R.TRANSMITIDO = 0 AND R.ESTADO_RECOLECCION IS NULL OR R.ESTADO_RECOLECCION = 0 AND R.ID_PERIODO_RECOLECCION = " + periodo +
                " ) TODAS, " +
                "(SELECT COUNT(R._ID) " +
                "FROM RECOLECCION R " +
                "WHERE R.ID_FUENTE = F._ID  AND R.ESTADO_RECOLECCION = 1 AND R.ID_PERIODO_RECOLECCION = " + periodo +
                " AND R.TRANSMITIDO IS NULL OR R.TRANSMITIDO = 0)  COMPLETAS " +
                "FROM FUENTE F " +
                "WHERE F._ID = " + idFuente;

        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        try {
            if (c.moveToFirst()) {
                do {
                    todas = c.getInt(1);
                    completas = c.getInt(2);
                } while (c.moveToNext());
            }
            if (todas == 0) {
                result = "";
            } else if (todas == completas) {
                result = "C";
            } else if (todas != completas && completas != 0) {
                result = "I";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
            daoSession.clear();
        }
        return result;
    }

    public synchronized Fuente getFuenteById(Long fuenteId) {
        Fuente fuente = null;
        openWritableDb();
        try {

            fuente = daoSession.getFuenteDao().queryBuilder().where(FuenteDao.Properties.FuenId.eq(fuenteId)).unique();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return fuente;
    }


    @Override
    public synchronized List<FuenteArticulo> getFuenteArticuloByFuenteId(Long fuenteId) {
        List<FuenteArticulo> fuente = null;
        openWritableDb();
        try {
            fuente = daoSession.getFuenteArticuloDao().queryBuilder().where(FuenteArticuloDao.Properties.FuenId.eq(fuenteId)).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return fuente;
    }

    @Override
    public synchronized List<FuenteArticuloDistrito> getFuenteArticuloDistritoByFuenteId(Long fuenteId) {
        List<FuenteArticuloDistrito> fuente = null;
        openWritableDb();
        try {
            fuente = daoSession.getFuenteArticuloDistritoDao().queryBuilder().where(FuenteArticuloDao.Properties.FuenId.eq(fuenteId)).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return fuente;
    }

    @Override
    public synchronized List<ArticuloI01> getArticuloByArtiId01(Long artiId, Long grupoInsumo) {
        List<ArticuloI01> articulos = null;
        openWritableDb();
        try {
            articulos = daoSession.getArticuloI01Dao().queryBuilder().where(
                    ArticuloI01Dao.Properties.ArtiId.eq(artiId),ArticuloI01Dao.Properties.Grin2Id.eq(grupoInsumo)).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return articulos;
    }

    @Override
    public synchronized List<PrincipalI01> getPrincipalById01(Long artiId, Long grupoInsumo) {
        List<PrincipalI01> principal = null;
        openWritableDb();
        try {
            principal = daoSession.getPrincipalI01Dao().queryBuilder().where(
                    PrincipalI01Dao.Properties.ArtiId.eq(artiId), PrincipalI01Dao.Properties.Grin2Id.eq(grupoInsumo)).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return principal;
    }

    @Override
    public synchronized List<Articulo> getArticuloByArtiId(Long artiId) {
        List<Articulo> articulos = null;
        openWritableDb();
        try {
            articulos = daoSession.getArticuloDao().queryBuilder().where(ArticuloDao.Properties.ArtiId.eq(artiId)).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return articulos;
    }

    @Override
    public synchronized List<Recoleccion> getRecoleccionByFuenteId(Long fuenteId) {
        List<Recoleccion> fuente = null;
        openWritableDb();
        try {

            fuente = daoSession.getRecoleccionDao().queryBuilder().where(RecoleccionDao.Properties.FuenId.eq(fuenteId)).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return fuente;
    }

    @Override
    public synchronized List<RecoleccionI01> getRecoleccionByArtiId01(Long artiId, Long grupoInsumo) {
        List<RecoleccionI01> fuente = null;
        openWritableDb();
        try {
            fuente = daoSession.getRecoleccionI01Dao().queryBuilder().where(
                    RecoleccionI01Dao.Properties.ArtiId.eq(artiId), RecoleccionI01Dao.Properties.Grin2Id.eq(grupoInsumo)).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return fuente;
    }

    @Override
    public synchronized List<RecoleccionI01> getRecoleccionByRecoId01(Long recoId) {
        List<RecoleccionI01> fuente = null;
        openWritableDb();
        try {
            fuente = daoSession.getRecoleccionI01Dao().queryBuilder().where(RecoleccionI01Dao.Properties.Id.eq(recoId)).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return fuente;
    }

    public synchronized List<Recoleccion> getRecoleccionByRecoId(Long recoId) {
        List<Recoleccion> fuente = null;
        openWritableDb();
        try {

            fuente = daoSession.getRecoleccionDao().queryBuilder().where(RecoleccionDao.Properties.Id.eq(recoId)).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return fuente;
    }


    public synchronized List<RecoleccionDistrito> getRecoleccionDistritoByRecoId(Long recoId) {
        List<RecoleccionDistrito> fuente = null;
        openWritableDb();
        try {

            fuente = daoSession.getRecoleccionDistritoDao().queryBuilder().where(RecoleccionDistritoDao.Properties.Id.eq(recoId)).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return fuente;
    }


    @Override
    public synchronized List<Recoleccion> listaRecoleccionTransmitir() {
        List<Recoleccion> recolecciones= null;
        try {
            openReadableDb();
            recolecciones = daoSession.getRecoleccionDao().queryRawCreate(" WHERE TRANSMITIDO IS NULL AND ESTADO_RECOLECCION = 1 ").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return recolecciones;
    }


    @Override
    public synchronized List<RecoleccionDistrito> listaRecoleccionTransmitirDistrito() {
        List<RecoleccionDistrito> recolecciones= null;
        try {
            openReadableDb();
            recolecciones = daoSession.getRecoleccionDistritoDao().queryRawCreate(" WHERE TRANSMITIDO IS NULL AND ESTADO_RECOLECCION = 1 AND TIRE_ID = 13 ").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return recolecciones;
    }


    @Override
    public synchronized List<RecoleccionDistrito> listaRecoleccionAllDistrito(Long idFuente) {
        List<RecoleccionDistrito> recolecciones= null;
        try {
            openReadableDb();
            recolecciones = daoSession.getRecoleccionDistritoDao().queryRawCreate(" WHERE TIRE_ID = 13 AND FUEN_ID = "+idFuente ).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return recolecciones;
    }

    @Override
    public synchronized FuenteDistrito getFuenteDistritoById(Long fuenteId) {
        FuenteDistrito fuente = null;
        openWritableDb();
        try {

            fuente = daoSession.getFuenteDistritoDao().queryBuilder().where(FuenteDistritoDao.Properties.FuenId.eq(fuenteId)).unique();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return fuente;
    }



    @Override
    public  synchronized ArticuloDistrito getArticuloDistrito(Elemento elemento){
        ArticuloDistrito articulo = null;
        openWritableDb();
        try {

            articulo = daoSession.getArticuloDistrtioDao().queryBuilder().where(ArticuloDistritoDao.Properties.ArtiId.eq(elemento.getArtiId()),
                    ArticuloDistritoDao.Properties.tipo.eq(elemento.getTipo()),
                    ArticuloDistritoDao.Properties.frecuencia.eq(elemento.getFrecuencia()),
                    ArticuloDistritoDao.Properties.unidad.eq(elemento.getUnidadMedidaNombre()),
                    ArticuloDistritoDao.Properties.TireId.eq(elemento.getTireId()),
                    ArticuloDistritoDao.Properties.ArtiNombre.eq(elemento.getArtiNombre()),
                    ArticuloDistritoDao.Properties.observacion.eq(elemento.getObservacion()) ).unique();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoSession.clear();
        }
        return articulo;
    }





}

