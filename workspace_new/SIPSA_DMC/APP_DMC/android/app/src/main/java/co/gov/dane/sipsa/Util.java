package co.gov.dane.sipsa;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;

/**
 * Clase que contiene utilidades
 */
public class    Util {

    private static List<String> lstCiiuObligatoriosDAUTOR = new ArrayList<>();
    private static List<String> lstCiiuObligatoriosINGTUR = new ArrayList<>();
    private static List<String> lstCiiuObligatoriosARTE = new ArrayList<>();

    private static List<String> lstCiiuObligatoriosInf = new ArrayList<>();
    private static List<String> lstCiiuObligatoriosEnfm = new ArrayList<>();


    public String getFechaActual(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    /**
     * Metodo con formato yyyy-MM-dd
     * @return
     */
    public static String getFechaActualFormato(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public String puntoWKT(Marker marker){

        Coordinate punto=new Coordinate(marker.getPosition().longitude,marker.getPosition().latitude);

        GeometryFactory gf = new GeometryFactory();

        Point p= gf.createPoint(punto);

        return String.valueOf(p);
    }

    public String PoligonoWKT(Polygon pol){

        Coordinate[] puntos=new Coordinate[pol.getPoints().size()];

        for(int i=0;i<pol.getPoints().size();i++){

            puntos[i]=new Coordinate(pol.getPoints().get(i).longitude,pol.getPoints().get(i).latitude);
        }
        GeometryFactory gf = new GeometryFactory();


        org.locationtech.jts.geom.Polygon p=gf.createPolygon(puntos);

        return String.valueOf(p);

    }

    /**
     * Metodo que retorna true si es diferente de vacio y null
     * @param valor
     * @return
     */
    public static boolean stringNullEmptys(String valor){
        if(valor != null && !valor.equals("")){
            return true;
        }
        return false;
    }

    /**
     * Metodo que solo deja los acronimos de la direccion.
     *
     * @param datoDireccion
     * @return
     */
    public static String getLimpiarAcronimoDireccion(String datoDireccion){
        if(stringNullEmptys(datoDireccion)){
            if(datoDireccion.contains("(") && datoDireccion.contains(")")){
                String derecho[] = datoDireccion.split(" ");
                return derecho[0];
            }
        }
        return datoDireccion;
    }


    /**
     * Metodo que realiza em mensaje informativo
     * @param activity
     * @param context
     * @param texto
     */
    public static  void getMensajeInformativo(Activity activity, Context context, String texto){
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        final View mView = inflater.inflate(R.layout.dialog_informativo_general, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        LinearLayout dialog_confirmacion = (LinearLayout) mView.findViewById(R.id.dialog_aceptar_confirmacion_mensaje_informativo_general);
        TextView textoV = (TextView) mView.findViewById(R.id.text_select_mensaje_informativo_general);
        textoV.setText(texto);

        dialog_confirmacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    public static  void getMensajeError(Activity activity, Context context, String texto){
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        final View mView = inflater.inflate(R.layout.dialog_error_general, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        LinearLayout dialog_confirmacion = (LinearLayout) mView.findViewById(R.id.dialog_aceptar_confirmacion_mensaje_error_general);
        TextView textoV = (TextView) mView.findViewById(R.id.text_select_mensaje_error_general);
        textoV.setText(texto);

        dialog_confirmacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * metodo que carga la informacion
     */
    private static void cargarDatos(){
        lstCiiuObligatoriosInf = new ArrayList<>();
        lstCiiuObligatoriosInf.add("8511");
        lstCiiuObligatoriosInf.add("8512");
        lstCiiuObligatoriosInf.add("8891");

        lstCiiuObligatoriosEnfm = new ArrayList<>();
        lstCiiuObligatoriosEnfm.add("8710");
        lstCiiuObligatoriosEnfm.add("8720");
        lstCiiuObligatoriosEnfm.add("8730");
        lstCiiuObligatoriosEnfm.add("8790");
        lstCiiuObligatoriosEnfm.add("8810");
        lstCiiuObligatoriosEnfm.add("8899");


        lstCiiuObligatoriosDAUTOR = new ArrayList<>();
        lstCiiuObligatoriosDAUTOR.add("1811");
        lstCiiuObligatoriosDAUTOR.add("1812");
        lstCiiuObligatoriosDAUTOR.add("1820");
        lstCiiuObligatoriosDAUTOR.add("5811");
        lstCiiuObligatoriosDAUTOR.add("5813");
        lstCiiuObligatoriosDAUTOR.add("5819");
        lstCiiuObligatoriosDAUTOR.add("5820");
        lstCiiuObligatoriosDAUTOR.add("5911");
        lstCiiuObligatoriosDAUTOR.add("5912");
        lstCiiuObligatoriosDAUTOR.add("5913");
        lstCiiuObligatoriosDAUTOR.add("5914");
        lstCiiuObligatoriosDAUTOR.add("5920");
        lstCiiuObligatoriosDAUTOR.add("6010");
        lstCiiuObligatoriosDAUTOR.add("6020");
        lstCiiuObligatoriosDAUTOR.add("6201");
        lstCiiuObligatoriosDAUTOR.add("6202");
        lstCiiuObligatoriosDAUTOR.add("6311");
        lstCiiuObligatoriosDAUTOR.add("6312");
        lstCiiuObligatoriosDAUTOR.add("6391");
        lstCiiuObligatoriosDAUTOR.add("6399");
        lstCiiuObligatoriosDAUTOR.add("7111");
        lstCiiuObligatoriosDAUTOR.add("7210");
        lstCiiuObligatoriosDAUTOR.add("7220");
        lstCiiuObligatoriosDAUTOR.add("7310");
        lstCiiuObligatoriosDAUTOR.add("7410");
        lstCiiuObligatoriosDAUTOR.add("7420");
        lstCiiuObligatoriosDAUTOR.add("7490");
        lstCiiuObligatoriosDAUTOR.add("9001");
        lstCiiuObligatoriosDAUTOR.add("9002");
        lstCiiuObligatoriosDAUTOR.add("9003");
        lstCiiuObligatoriosDAUTOR.add("9004");
        lstCiiuObligatoriosDAUTOR.add("9005");
        lstCiiuObligatoriosDAUTOR.add("9006");
        lstCiiuObligatoriosDAUTOR.add("9007");
        lstCiiuObligatoriosDAUTOR.add("9008");//DAUTOR

        lstCiiuObligatoriosINGTUR = new ArrayList<>();
        lstCiiuObligatoriosINGTUR.add("4911");
        lstCiiuObligatoriosINGTUR.add("4921");
        lstCiiuObligatoriosINGTUR.add("5021");
        lstCiiuObligatoriosINGTUR.add("5511");
        lstCiiuObligatoriosINGTUR.add("5512");
        lstCiiuObligatoriosINGTUR.add("5513");
        lstCiiuObligatoriosINGTUR.add("5514");
        lstCiiuObligatoriosINGTUR.add("5519");
        lstCiiuObligatoriosINGTUR.add("5520");
        lstCiiuObligatoriosINGTUR.add("5611");
        lstCiiuObligatoriosINGTUR.add("5612");
        lstCiiuObligatoriosINGTUR.add("5613");
        lstCiiuObligatoriosINGTUR.add("5619");
        lstCiiuObligatoriosINGTUR.add("5630");
        lstCiiuObligatoriosINGTUR.add("7911");
        lstCiiuObligatoriosINGTUR.add("7990");//INGTUR

        lstCiiuObligatoriosARTE = new ArrayList<>();
        lstCiiuObligatoriosARTE.add("1103");
        lstCiiuObligatoriosARTE.add("1312");
        lstCiiuObligatoriosARTE.add("1313");
        lstCiiuObligatoriosARTE.add("1391");
        lstCiiuObligatoriosARTE.add("1392");
        lstCiiuObligatoriosARTE.add("1393");
        lstCiiuObligatoriosARTE.add("1399");
        lstCiiuObligatoriosARTE.add("1410");
        lstCiiuObligatoriosARTE.add("1420");
        lstCiiuObligatoriosARTE.add("1430");
        lstCiiuObligatoriosARTE.add("1512");
        lstCiiuObligatoriosARTE.add("1521");
        lstCiiuObligatoriosARTE.add("1522");
        lstCiiuObligatoriosARTE.add("1640");
        lstCiiuObligatoriosARTE.add("1690");
        lstCiiuObligatoriosARTE.add("2310");
        lstCiiuObligatoriosARTE.add("2393");
        lstCiiuObligatoriosARTE.add("2396");
        lstCiiuObligatoriosARTE.add("2591");
        lstCiiuObligatoriosARTE.add("2599");
        lstCiiuObligatoriosARTE.add("3110");
        lstCiiuObligatoriosARTE.add("3210");
        lstCiiuObligatoriosARTE.add("3220");
        lstCiiuObligatoriosARTE.add("3240");//ARTE
    }


    /**
     * Metodo que valida el paso a capitulo12 a  HCUIDADO_INF
     *
     * @param ciiu
     * @return
     */
    public static boolean getValidarPasarCapitulo12Inf(String ciiu){
        Boolean retorno  = false;
        cargarDatos();
        Log.d("CAP9","getValidarPasarCapitulo12Inf ciiu-----------"+ciiu);
        Log.d("CAP9","lstCiiuObligatoriosInf-----------"+lstCiiuObligatoriosInf.size());

        for (String lst : lstCiiuObligatoriosInf) {
            if (lst.equals(ciiu)) {
                retorno = true;
                break;
            }
        }
        return  retorno;
    }

    /**
     * Metodo que valida el paso a capitulo12 a  CUIDADO_ENFM
     *
     * @param ciiu
     * @return
     */
    public static boolean getValidarPasarCapitulo12Enfm(String ciiu){
        Boolean retorno  = false;
        cargarDatos();

        Log.d("CAP9","getValidarPasarCapitulo12Enfm ciiu-----------"+ciiu);
        Log.d("CAP9","lstCiiuObligatoriosInf-----------"+lstCiiuObligatoriosInf.size());

        for (String lst : lstCiiuObligatoriosEnfm) {
            Log.d("CAP9","lstCiiuObligatoriosInf----1-------"+lst);
            if (lst.equals(ciiu)) {
                Log.d("CAP9","ENCONTROOOOOOOO----1-------"+lst);
                retorno = true;
                break;
            }
        }
        return  retorno;
    }

    /**
     * Metodo que valida el paso a capitulo13 a  ARTE
     *
     * @param ciiu
     * @param ubica
     * @return
     */
    public static boolean getValidarPasarCapitulo13ARTE(String ciiu, String ubica){
        Boolean retorno  = false;
        cargarDatos();

        for (String lst : lstCiiuObligatoriosARTE) {
            if (lst.equals(ciiu) && ubica.equals("Establecimiento semifijo (Puesto anclado al piso, caseta, kioscos, islas)")) {
                retorno = true;
                break;
            }
        }
        return  retorno;
    }

    /**
     * Metodo que valida el paso a capitulo13 a  ARTE
     *
     * @param ciiu
     * @return
     */
    public static boolean getValidarPasarCapitulo13ARTE(String ciiu){
        Boolean retorno  = false;
        cargarDatos();

        for (String lst : lstCiiuObligatoriosARTE) {
            if (lst.equals(ciiu)) {
                retorno = true;
                break;
            }
        }
        return  retorno;
    }

    /**
     * Metodo que valida el paso a capitulo13 a  DAUTOR
     *
     * @param ciiu
     * @param ubica
     * @return
     */
    public static boolean getValidarPasarCapitulo13DAUTOR(String ciiu, String ubica){
        Boolean retorno  = false;
        cargarDatos();

        for (String lst : lstCiiuObligatoriosDAUTOR) {
            if (lst.equals(ciiu) && ubica.equals("Establecimiento semifijo (Puesto anclado al piso, caseta, kioscos, islas)")) {
                retorno = true;
                break;
            }
        }
        return  retorno;
    }

    /**
     * Metodo que valida el paso a capitulo13 a  DAUTOR
     *
     * @param ciiu
     * @return
     */
    public static boolean getValidarPasarCapitulo13DAUTOR(String ciiu){
        Boolean retorno  = false;
        cargarDatos();

        for (String lst : lstCiiuObligatoriosDAUTOR) {
            if (lst.equals(ciiu)) {
                retorno = true;
                break;
            }
        }
        return  retorno;
    }

    /**
     * Metodo que valida el paso a capitulo13 a  INGTUR
     *
     * @param ciiu
     * @param ubica
     * @return
     */
    public static boolean getValidarPasarCapitulo13INGTUR(String ciiu, String ubica){
        Boolean retorno  = false;
        cargarDatos();

        for (String lst : lstCiiuObligatoriosINGTUR) {
            if (lst.equals(ciiu) && ubica.equals("Establecimiento semifijo (Puesto anclado al piso, caseta, kioscos, islas)")) {
                retorno = true;
                break;
            }
        }
        return  retorno;
    }

    /**
     * Metodo que valida el paso a capitulo13 a  INGTUR
     *
     * @param ciiu
     * @return
     */
    public static boolean getValidarPasarCapitulo13INGTUR(String ciiu){
        Boolean retorno  = false;
        cargarDatos();

        for (String lst : lstCiiuObligatoriosINGTUR) {
            if (lst.equals(ciiu) ) {
                retorno = true;
                break;
            }
        }
        return  retorno;
    }

}
