package co.gov.dane.sipsa;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class Mensajes {

    Context context;

    public Mensajes(Context context){
        this.context=context;
    }

    public void generarToast(String mensaje){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate( R.layout.toast, null );

        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(mensaje);

        android.widget.Toast toast = new android.widget.Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(android.widget.Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();

    }

    public void generarToast(String mensaje,String tipo){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View view = inflater.inflate( R.layout.toast, null );
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(mensaje);

        if(tipo.equals("error")){
            ImageView imageView6=(ImageView) view.findViewById(R.id.imageView6);
            imageView6.setImageResource(R.drawable.ic_close);
            text.setTextColor(context.getResources().getColor(R.color.rojo));
        }

        if(tipo.equals("warning")){
            ImageView imageView6=(ImageView) view.findViewById(R.id.imageView6);
            imageView6.setImageResource(R.drawable.baseline_warning_18);
            text.setTextColor(context.getResources().getColor(R.color.rojo));
        }

        android.widget.Toast toast = new android.widget.Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(android.widget.Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public void generarToastMapa(String mensaje){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View view = inflater.inflate( R.layout.toast, null );
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(mensaje);

        android.widget.Toast toast = new android.widget.Toast(context);
        toast.setGravity(Gravity.CENTER|Gravity.LEFT, 0, -50);
        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }


    /**
     * Metodo que cargar el dialo
     * @param descripcion
     */
    public void dialogoMensaje(String descripcion){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        final View mView = inflater.inflate(R.layout.dialog_informativo_general, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        LinearLayout dialog_confirmacion = (LinearLayout) mView.findViewById(R.id.dialog_aceptar_confirmacion_mensaje_informativo_general);
        TextView textoV = (TextView) mView.findViewById(R.id.text_select_mensaje_informativo_general);
        textoV.setText("\n"+descripcion);

        dialog_confirmacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    public void dialogoMensajeError(String descripcion){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        final View mView = inflater.inflate(R.layout.dialog_error_general, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        LinearLayout dialog_confirmacion = (LinearLayout) mView.findViewById(R.id.dialog_aceptar_confirmacion_mensaje_error_general);
        TextView textoV = (TextView) mView.findViewById(R.id.text_select_mensaje_error_general);
        textoV.setText("\nEl sistema generó el siguiente error: \n\n"+descripcion);

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
