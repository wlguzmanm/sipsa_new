package gov.dane.sipsa.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.bonuspack.overlays.InfoWindow;
import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Marker.OnMarkerDragListener;
import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.bonuspack.overlays.Polygon;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.MapTileProviderArray;
import org.osmdroid.tileprovider.modules.IArchiveFile;
import org.osmdroid.tileprovider.modules.MBTilesFileArchive;
import org.osmdroid.tileprovider.modules.MapTileFileArchiveProvider;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import gov.dane.sipsa.R;
import gov.dane.sipsa.app.App;
import gov.dane.sipsa.dao.Coordenada;
import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.manager.DatabaseManager;

import gov.dane.sipsa.model.PolygonUnidadCobertura;
import gov.dane.sipsa.model.Status;
import gov.dane.sipsa.utils.ExternalStorage;
import gov.dane.sipsa.utils.General;


public class MapaActivity  extends App implements MapEventsReceiver, LocationListener, GpsStatus.Listener {

    private String DMC_DATABASE_PATH;
    private MapView map;
    private KmlDocument mKmlDocument;
    private List<Polygon> polygonLista = new ArrayList<Polygon>();
    private List<PolygonUnidadCobertura> polygonUnidadCoberturaList = new ArrayList<PolygonUnidadCobertura>();
    private ProgressDialog pgMensaje;
    private ActionBar abActivity;
    private ListView lvUnidades;
    private TextView tvSeleccion;
    private PolygonUnidadCobertura poligonUnidad;
    private String sPolygon = null;
    private LocationManager locationManager;
    private int horaInicial;
    private int horaFinal;
    private int pargpsId;
    private long MIN_TIME_BW_UPDATES = 300;
    private long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private String provider;
    private GeoPoint gpGPS;
    private Location locationGPS;
    private static final int REQUEST_LOCATION = 2;
    private Marker gpsMarker = null;
    private final int TIME_INTERVAL = 1000 * 10 * 1;  //cambiar a 1000*2*60 para 2 minutos.
    private Timer timer = new Timer();
    private boolean pruebaGPS = false;
    private boolean usarGPS = false;
    private Marker touchMarker = null;//posicion manual marker
    private boolean toqueManual = false;
    private Double altitude;
    private ImageButton buttonGPS;
    private Drawable satok;
    private Drawable satnot;

    private TextView distancia;
    private TextView coordenadas;

    private GpsStatus mStatus;

    private int mlocationCount = 0;
    private Location locationCached;

    private Double latitude =4.674856;
    private Double longitude =   -74.111636;

    private Fuente fuente;

    public static DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras.getSerializable("factor") != null) {
                fuente = (Fuente) extras.getSerializable("factor");
            }
        } else {
            fuente = (Fuente) savedInstanceState.getSerializable("factor");
        }

        databaseManager = DatabaseManager.getInstance(this);

        satok = ContextCompat.getDrawable(getApplicationContext(), R.drawable.satok);
        satnot = ContextCompat.getDrawable(getApplicationContext(), R.drawable.satnot);

        //Modo Estricto
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);

        //Partes de la actividad
     /*   abActivity = getSupportActionBar();
        abActivity.setDisplayHomeAsUpEnabled(true);
        abActivity.setHomeButtonEnabled(true);
        abActivity.setDisplayShowTitleEnabled(true);
        abActivity.setTitle("Determinar ubicación");*/

        map = (MapView) findViewById(R.id.map);



        //Un progress Bar?
        pgMensaje = new ProgressDialog(this);
        pgMensaje.setMessage("Cargando mapa y ubicación");
        pgMensaje.show();
        //pgMensaje.setCancelable(false);

        //GPS : Usando Location_service
        if (displayGpsStatus()) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            //Parametros GPS
            long[] parametroGps = {1, 2, 3, 4};
            horaInicial = (int) parametroGps[1];
            horaFinal = (int) parametroGps[2];
            pargpsId = (int) parametroGps[3];
            // The minimum time between updates in milliseconds
            //MIN_TIME_BW_UPDATES = parametroGps[0]; // In miliseconds
            // The minimum distance to change Updates in meters
            //MIN_DISTANCE_CHANGE_FOR_UPDATES = 3; // 1 meters
            Criteria criteria = new Criteria();
            int currentapiVersion = Build.VERSION.SDK_INT;
            if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
                criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                criteria.setAltitudeRequired(true);
                criteria.setBearingRequired(true);
                criteria.setSpeedRequired(true);
            }
            provider = locationManager.getBestProvider(criteria, true);

            ///CARGA DEL MAPA POR TILES
            String[] urls = {"http://example.org/"};
            XYTileSource MBTILESRENDER = new XYTileSource("mbtiles", 0, 20, 256, ".png", urls);
            DefaultResourceProxyImpl mResourceProxy = new DefaultResourceProxyImpl(this.getApplicationContext());
            SimpleRegisterReceiver simpleReceiver = new SimpleRegisterReceiver(this);

            File[] roots = ExternalStorage.getPaths();
            String PATH = General.rutaAlistamiento(this);

            File f = new File(PATH + "/mbtiles.mbtiles");
            //File f = new File(Environment.getExternalStorageDirectory(), "mbtiles.mbtiles");
            System.out.println("FILE : " + f.getPath() + "NAME:" + f.getName());
            if (!f.exists()) {
                System.out.println("NO EXISTE DBTILES");
                Toast.makeText(this, "No se encontro archivo \n " + PATH + "/mbtiles.mbtiles \n Por favor verificar instalación", Toast.LENGTH_SHORT).show();
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
                finish();
            }

            IArchiveFile[] files = {MBTilesFileArchive.getDatabaseFileArchive(f)};
            MapTileModuleProviderBase moduleProvider = new MapTileFileArchiveProvider(simpleReceiver, MBTILESRENDER, files);
            MapTileProviderArray mProvider = new MapTileProviderArray(MBTILESRENDER, null, new MapTileModuleProviderBase[]{moduleProvider});


            //this.map = new MapView(this, mResourceProxy, mProvider);
            //setContentView(map);
            map.setTileProvider(mProvider);

            gpsMarker = new Marker(map);
            Drawable gpsimage = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_gps_fixed_black);
            gpsMarker.setIcon(gpsimage);


            map.setBuiltInZoomControls(true);
            map.setMultiTouchControls(true);
            GeoPoint startPoint = new GeoPoint(latitude,longitude);

            IMapController mapController = map.getController();
            mapController.setZoom(17);
            mapController.setCenter(startPoint);

        /*Marker startMarker = new Marker(map);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setTitle("Start point");
        startMarker.setDraggable(true);
        startMarker.setOnMarkerDragListener(new OnMarkerDragListenerDrawer());*/
            //map.getOverlays().add(startMarker);


            touchMarker = new Marker(map);

            touchMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            touchMarker.setTitle("Posicionamiento Manual");
            touchMarker.setDraggable(false);
            //touchMarker.setOnMarkerDragListener(new OnMarkerDragListenerDrawer());


            final Button buttonContinuar = (Button) findViewById(R.id.continuar);
            buttonContinuar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if (toqueManual) {
                        lanzarEntFormulario();
                    } else {
                        dialogoErrorContinuar();

                    }
                }
            });


            buttonGPS = (ImageButton) findViewById(R.id.gps);
            buttonGPS.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    actualizarCoordenada();
                }
            });

            ImageButton buttonIrGPS = (ImageButton) findViewById(R.id.irgps);
            buttonIrGPS.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    irGPS();
                }
            });

            final ImageButton buttonIrPoligono = (ImageButton) findViewById(R.id.iruc);
            buttonIrPoligono.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    // irPoligono();
                }
            });

            gpsMarker.setTitle("Encuestador");
            map.getOverlays().add(gpsMarker);


            map.setMapListener(new MapListener() {
                public boolean onZoom(ZoomEvent arg0) {
                    System.out.println("Ha cambiado el zoom: " + map.getZoomLevel());

                    return false;
                }

                public boolean onScroll(ScrollEvent arg0) {

                    return false;
                }
            });

            //16. Handling Map events
            MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this, this);
            map.getOverlays().add(0, mapEventsOverlay); //inserted at the "bottom" of all overlays

            Bundle bundle = getIntent().getExtras();
            sPolygon = null;


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Check Permissions Now
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION);
            } else {

                System.out.println("PROVIDER -" + provider + "-");


                System.out.println("PROVIDER PRE -" + locationGPS);

                provider = LocationManager.GPS_PROVIDER;

                if (provider != null) {
                    locationManager.requestLocationUpdates(provider, 0, 0, this);
                    //locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    System.out.println("LOCATION GPS" + locationGPS);


                    coordenadas = (TextView) findViewById(R.id.coordenadas);
                    if (locationGPS != null) {
                        System.out.println("PROVIDER POST-" + locationGPS.getLatitude() + locationGPS.getLongitude());

                        coordenadas.setText(textoCoordenadas(locationGPS) );
                    } else {
                        coordenadas.setText("No hay información del GPS");
                        changeNoSat();
                        locationGPS = null;
                        gpGPS = null;
                    }

                    //Ubicacion GPS.
                    if (locationGPS != null) {
                        gpGPS = new GeoPoint(locationGPS.getLatitude(), locationGPS.getLongitude());
                        altitude = locationGPS.getAltitude();
                        gpsMarker.setPosition(gpGPS);
                        coordenadas.setText(textoCoordenadas(locationGPS) );
                        buttonGPS.setImageDrawable(satok);

                        pgMensaje.dismiss();
                        pgMensaje.hide();
                    } else {

                        buttonGPS.setImageDrawable(satnot);
                        locationManager.requestLocationUpdates(provider, 0, 0, this);
                    }
                } else {
                    coordenadas.setText("No hay información del GPS");
                    locationGPS = null;
                    changeNoSat();
                    gpGPS = null;
                }
            }
            map.invalidate();

        } else {
            Toast.makeText(this, "Debe habilitar el GPS", Toast.LENGTH_LONG).show();
            startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
            finish();
        }

        //Prueba de los 2 minutos del GPS.
        if (pruebaGPS == false) {

            timer.scheduleAtFixedRate(new TimerTask() {

                @SuppressLint("DefaultLocale")
                @TargetApi(Build.VERSION_CODES.GINGERBREAD)
                @Override
                public void run() {

                    if (locationGPS != null) {


                        System.out.println("*************COORDENADA 2 MIN*********");
                        System.out.println("lat------ " + locationGPS.getLatitude());
                        System.out.println("lng-------- " + locationGPS.getLongitude());

                        if (locationGPS.getLatitude() != 0) {
                            usarGPS = true;

                            //changeSat();
                        } else {

                            // changeNoSat();
                            usarGPS = false;

                        }

                    } else {
                        usarGPS = false;
                        //changeNoSat();
                    }

                    if (pruebaGPS == true) {

                        timer.cancel();
                    }

                    pruebaGPS = true;

                    System.out.println("*PruebaGPS-" + pruebaGPS + "*usarGPS-" + usarGPS);

                }
            }, 0, TIME_INTERVAL);
        }

    }

    @Override
    protected void onPostExecute(Status result) {

    }

    public void changeNoSat() {
        buttonGPS = (ImageButton) findViewById(R.id.gps);
        satnot = ContextCompat.getDrawable(getApplicationContext(), R.drawable.satnot);
        buttonGPS.setImageDrawable(satnot);
    }

    public void changeSat() {
        buttonGPS = (ImageButton) findViewById(R.id.gps);
        satok = ContextCompat.getDrawable(getApplicationContext(), R.drawable.satok);
        buttonGPS.setImageDrawable(satok);
    }


    public void irGPS() {
        if (gpGPS != null) {
            map.getController().setCenter(gpGPS);
        }
    }

    public void irPoligono() {
        if (poligonUnidad.getPoligono() != null) {
            map.getController().setCenter(poligonUnidad.getPoligono().getPoints().get(0));
            map.getController().setZoom(17);
            map.invalidate();
        }
    }


    public String calcularDistancia() {

        if (locationGPS != null && gpGPS != null && poligonUnidad != null) {

            double lat1 = poligonUnidad.getPoligono().getPoints().get(0).getLatitude();
            double lon1 = poligonUnidad.getPoligono().getPoints().get(0).getLongitude();

            double latGPS = locationGPS.getLatitude();
            double lon1GPS = locationGPS.getLongitude();

            double diferencialat = (Math.abs(lat1) - Math.abs(latGPS)) * 30;
            double diferencialon = (Math.abs(lon1) - Math.abs(latGPS)) * 30;

            double hcuadrado = Math.pow(Math.abs(diferencialat), 2) + Math.pow(Math.abs(diferencialon), 2);
            double hcalculado = Math.pow(hcuadrado, (0.5));


            String salida = String.valueOf(distanciaCoord(lat1, lon1, latGPS, lon1GPS));
            salida = salida.substring(0, 8);
            return "Distancia " + salida + " m";
        } else {

            System.out.println("No info");

        }

        return " ";

    }

    public static double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {
        //double radioTierra = 3958.75;//en millas
        double radioTierra = 6371;//en kilómetros
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        double distancia = radioTierra * va2;


        distancia = distancia * 1000;

        return distancia;
    }


    public void lanzarEntFormulario() {
        Coordenada coordenada = new Coordenada();
        if (gpGPS != null) {
            Log.d("posicion", String.valueOf(gpGPS.getLatitude()) + " " + String.valueOf(gpGPS.getLongitude()));
            coordenada.setP_altitud_gps((double) gpGPS.getAltitude());
            coordenada.setP_latitud_gps(gpGPS.getLatitude());
            coordenada.setP_longitud_gps(gpGPS.getLongitude());


        }
            Log.d("posicion touch",String.valueOf(touchMarker.getPosition().getLatitude())+" " + String.valueOf(touchMarker.getPosition().getLongitude()) );




        coordenada.setP_id_fuente(fuente.getFuenId());
        coordenada.setP_altitud_dmc((double) touchMarker.getPosition().getAltitude());
        coordenada.setP_latitud_dmc(touchMarker.getPosition().getLatitude());
        coordenada.setP_longitud_dmc(touchMarker.getPosition().getLongitude());

        Long l = databaseManager.save(coordenada);


        Intent intent =new Intent(this, FuenteRecoleccionActivity.class);
        intent.putExtra("factor",fuente);
        startActivity(intent);
        finish();

    }

    public String textoCoordenadas(Location locationGPS) {
        String salida = "";
        if (locationGPS != null) {

            String latitud = String.valueOf(locationGPS.getLatitude());
            String longitud = String.valueOf(locationGPS.getLongitude());
            latitud = latitud.substring(0, 8);
            longitud = longitud.substring(0, 8);
            salida = "Lat " + latitud + " Lon " + longitud;
        }

        return salida;

    }

    public void actualizarCoordenada() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        Criteria criteria = new Criteria();
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(true);
            criteria.setBearingRequired(true);
            criteria.setSpeedRequired(true);
        }

        provider = locationManager.getBestProvider(criteria, true);
        provider = LocationManager.GPS_PROVIDER;


        if (provider != null) {


            //locationGPS = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 0, 0, this);


            //locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            System.out.println("LOCATION GPS" + locationGPS);


            //locationGPS = locationManager.getLastKnownLocation(provider);

            coordenadas = (TextView) findViewById(R.id.coordenadas);
            if (locationGPS != null) {
                coordenadas.setText(textoCoordenadas(locationGPS) );
            } else {
                coordenadas.setText("No hay información del GPS");
                locationGPS = null;
                changeNoSat();
            }
            if (locationGPS != null) {
                buttonGPS.setImageDrawable(satok);
                gpGPS = new GeoPoint(locationGPS.getLatitude(), locationGPS.getLongitude());
                altitude = locationGPS.getAltitude();
                gpsMarker.setPosition(gpGPS);
                map.invalidate();


                //Toast.makeText(this, "gps long:" + gpGPS.getLongitude() + " lat:" + gpGPS.getLatitude(), Toast.LENGTH_SHORT).show();


                pgMensaje.dismiss();
                pgMensaje.hide();
            } else {

                buttonGPS.setImageDrawable(satnot);
            }
        } else {
            coordenadas.setText("No hay información del GPS");
            locationGPS = null;
            changeNoSat();
            gpGPS = null;
        }
    }


    @Override
    public void onGpsStatusChanged(int status) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mStatus = locationManager.getGpsStatus(mStatus);
        System.out.println("Status:::::::" + status + "MStatus " + mStatus);
        if(status==GpsStatus.GPS_EVENT_STOPPED){
            changeNoSat();
            //Toast.makeText(this, "GPS DETENIDO", Toast.LENGTH_SHORT).show();
        }


        if(status==GpsStatus.GPS_EVENT_STARTED){

            //Toast.makeText(this, "GPS CONTINUA", Toast.LENGTH_SHORT).show();
        }
        if (status==GpsStatus.GPS_EVENT_FIRST_FIX){

            System.out.println("GPS STATUS FIRST FIX ");
        }
    }



    public void dialogoErrorContinuar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Por favor, selecciona un punto en el mapa para continuar.")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onLocationChanged(Location location) {


        locationGPS = location;

        if(mlocationCount == 0 ){
            locationCached = locationGPS;
            locationGPS = null;

            //Toast.makeText(this, "location N:"  + mlocationCount, Toast.LENGTH_SHORT).show();
            mlocationCount++;
        }
        else {

            if (locationGPS != null) {
                if(locationCached==locationGPS){

                    locationGPS=null;

                }
                else {
                    buttonGPS.setImageDrawable(satok);
                    gpGPS = new GeoPoint(locationGPS.getLatitude(), locationGPS.getLongitude());
                    coordenadas.setText("Lat " + locationGPS.getLatitude() + " Lon " + locationGPS.getLongitude() );
                    altitude = locationGPS.getAltitude();
                    gpsMarker.setPosition(gpGPS);
                    map.invalidate();

                    //Toast.makeText(this, "gps long:"+ gpGPS.getLongitude() +" lat:" + gpGPS.getLatitude(), Toast.LENGTH_SHORT).show();


                    pgMensaje.dismiss();
                    pgMensaje.hide();
                }
            } else {
                buttonGPS.setImageDrawable(satnot);
                coordenadas.setText("No hay información del GPS");
                changeNoSat();
            }

            //Toast.makeText(this, "location  NUUL?N:"  + locationGPS, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

        actualizarCoordenada();

        if(status==GpsStatus.GPS_EVENT_STOPPED){
            changeNoSat();
            locationGPS = null;
            gpGPS= null;
            //Toast.makeText(this, "GPS DETENIDO", Toast.LENGTH_SHORT).show();
        }
        if(status==GpsStatus.GPS_EVENT_STARTED){
            //Toast.makeText(this, "GPS CONTINUA", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProviderEnabled(String provider) {

        actualizarCoordenada();

        System.out.println("PROVIDER ENABLED");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "No se puede calcular posición, GPS deshabilitado", Toast.LENGTH_SHORT).show();
        //gpGPS = null;
        locationGPS=null;
        gpGPS= null;
        coordenadas = (TextView) findViewById(R.id.coordenadas);
        coordenadas.setText("No hay información del GPS");
        changeNoSat();
        pgMensaje.dismiss();
        pgMensaje.hide();
    }

    //0. Using the Marker and Polyline overlays - advanced options
    class OnMarkerDragListenerDrawer implements OnMarkerDragListener {
        ArrayList<GeoPoint> mTrace;
        Polyline mPolyline;

        OnMarkerDragListenerDrawer() {

        }

        @Override public void onMarkerDrag(Marker marker) {
            //mTrace.add(marker.getPosition());
        }

        @Override public void onMarkerDragEnd(Marker marker) {
            //mTrace.add(marker.getPosition());
            //mPolyline.setPoints(mTrace);
            map.invalidate();

        }

        @Override public void onMarkerDragStart(Marker marker) {
            //mTrace.add(marker.getPosition());
        }
    }

    //7. Customizing the bubble behaviour
    class CustomInfoWindow extends MarkerInfoWindow {
        POI mSelectedPoi;

        public CustomInfoWindow(MapView mapView) {
            super(org.osmdroid.bonuspack.R.layout.bonuspack_bubble, mapView);
            Button btn = (Button) (mView.findViewById(org.osmdroid.bonuspack.R.id.bubble_moreinfo));
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (mSelectedPoi.mUrl != null) {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mSelectedPoi.mUrl));
                        view.getContext().startActivity(myIntent);
                    } else {
                        Toast.makeText(view.getContext(), "Button clicked", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        @Override
        public void onOpen(Object item) {
            super.onOpen(item);
            mView.findViewById(org.osmdroid.bonuspack.R.id.bubble_moreinfo).setVisibility(View.VISIBLE);
            Marker marker = (Marker) item;
            mSelectedPoi = (POI) marker.getRelatedObject();

            //8. put thumbnail image in bubble, fetching the thumbnail in background:
            if (mSelectedPoi.mThumbnailPath != null) {
                ImageView imageView = (ImageView) mView.findViewById(org.osmdroid.bonuspack.R.id.bubble_image);
                mSelectedPoi.fetchThumbnailOnThread(imageView);
            }
        }
    }


    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    //16. Handling Map events
    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        GeoPoint point = new GeoPoint(p.getLatitude(), p.getLongitude());
        touchMarker.setPosition(point);
        map.getOverlays().add(touchMarker);
        toqueManual = true;
        map.invalidate();

        InfoWindow.closeAllInfoWindowsOn(map);
        return true;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        map.invalidate();
        return true;
    }


    @Override
    public void onBackPressed() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }

}