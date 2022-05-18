package co.gov.dane.sipsa;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import co.gov.dane.sipsa.backend.Database;
import co.gov.dane.sipsa.backend.model.Offline;
import co.gov.dane.sipsa.service.NotificationService;
import co.gov.dane.sipsa.service.SincronizacionService;

public class Entrada extends AppCompatActivity {

    private LinearLayout insumos, insumos01, distrito,salir_app;
    private Session session;
    private Database db;
    private Offline offline = new Offline();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrada);

        insumos=(LinearLayout) findViewById(R.id.insumos);
        insumos01=(LinearLayout) findViewById(R.id.insumos01);
        distrito=(LinearLayout) findViewById(R.id.distrito);
        salir_app=(LinearLayout) findViewById(R.id.salir_app);

        session=new Session(Entrada.this);
        db = new Database(Entrada.this);


        if(session.getusename()==null){
            session.borrarSession();

            Intent mainIntent = new Intent(Entrada.this,login.class);
            startActivity(mainIntent);
            finish();
        }

        Bundle extras = getIntent().getExtras();
        Intent instenService = new Intent(this, NotificationService.class);
        instenService.putExtra("token",session.getToken());
        instenService.putExtra("username",session.getusename());
        instenService.putExtra("clave",session.getPassword());
        instenService.putExtra("rol",session.getrol());
        startService(instenService);

        Intent instenServiceSincronizacion = new Intent(this, SincronizacionService.class);
        instenServiceSincronizacion.putExtra("token",session.getToken());
        instenServiceSincronizacion.putExtra("username",session.getusename());
        instenServiceSincronizacion.putExtra("clave",session.getPassword());
        instenServiceSincronizacion.putExtra("rol",session.getrol());
        startService(instenServiceSincronizacion);

        insumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mensajes mitoast =new Mensajes(Entrada.this);
                if(session.getFechaExp()!= null && !session.getFechaExp().isEmpty()){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                    String fechaInicio = sdf.format(new Date());

                    Date date =new Date(Long.valueOf(session.getFechaExp()));
                    String fechaFin = sdf.format(date);

                    int resta = Long.valueOf(fechaFin).intValue() - Long.valueOf(fechaInicio).intValue();
                    if(resta > 0 && resta <= 3 ){
                        mitoast.dialogoMensajeError("El sistema ha detectado que el Token que está usando, debe ser renovado por uno nuevo, por favor, cambiar la configuración local a Internet, cerrar la sesión y garantizar que el dispositivo tenga acceso a Internet, para la renovación del Token.");
                    }

                    if(resta == 0 ){
                        mitoast.dialogoMensajeError("El Token se encuentra vencido. Por favor, cambiar la configuración local a Internet, cerrar la sesión y garantizar que el dispositivo tenga acceso a Internet, para la renovación del Token. ");
                    }
                    session.setTipoFormulario("Insumos");
                    Intent mainIntent = new Intent(Entrada.this, MainActivity.class);
                    Entrada.this.startActivity(mainIntent);

                }else{
                    session.setTipoFormulario("Insumos");
                    Intent mainIntent = new Intent(Entrada.this, MainActivity.class);
                    Entrada.this.startActivity(mainIntent);
                }
            }
        });

        insumos01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mensajes mitoast =new Mensajes(Entrada.this);
                if(session.getFechaExp()!= null && !session.getFechaExp().isEmpty()){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                    String fechaInicio = sdf.format(new Date());

                    Date date =new Date(Long.valueOf(session.getFechaExp()));
                    String fechaFin = sdf.format(date);

                    int resta = Long.valueOf(fechaFin).intValue() - Long.valueOf(fechaInicio).intValue();
                    if(resta > 0 && resta <= 3 ){
                        mitoast.dialogoMensajeError("El sistema ha detectado que el Token que está usando, debe ser renovado por uno nuevo, por favor, cambiar la configuración local a Internet, cerrar la sesión y garantizar que el dispositivo tenga acceso a Internet, para la renovación del Token.");
                    }

                    if(resta == 0 ){
                        mitoast.dialogoMensajeError("El Token se encuentra vencido. Por favor, cambiar la configuración local a Internet, cerrar la sesión y garantizar que el dispositivo tenga acceso a Internet, para la renovación del Token. ");
                    }
                    session.setTipoFormulario("Insumos01");
                    Intent mainIntent = new Intent(Entrada.this, MainActivity.class);
                    Entrada.this.startActivity(mainIntent);
                }else{
                    session.setTipoFormulario("Insumos01");
                    Intent mainIntent = new Intent(Entrada.this, MainActivity.class);
                    Entrada.this.startActivity(mainIntent);
                }
            }
        });


        distrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //TODO: PONER DISTRITO
            }
        });

        salir_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Entrada.this);
                builder.setTitle("Confirmación");
                builder.setMessage("¿ Desea salir del aplicativo ?");
                builder.setIcon(R.drawable.ic_menu_salir);
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        offline = db.getOffline("OFFLINE", session.getusename());
                        if(offline!= null && !offline.isActivo()){
                            Mensajes mitoast =new Mensajes(Entrada.this);
                            mitoast.generarToast("Recuerde que está en modo ONLINE y requiere nuevamente autenticación.");
                            session.borrarSession();
                        }
                        Intent mainIntent = new Intent(Entrada.this,login.class);
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
                    alert.show();
                }catch (Exception e){
                }
            }
        });

    }
}