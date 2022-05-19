package co.gov.dane.sipsa;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;

/**
 * Clase que contiene utilidades
 */
public class    Util {

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


}
