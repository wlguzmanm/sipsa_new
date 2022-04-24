package gov.dane.sipsa.app;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

import gov.dane.sipsa.R;
import gov.dane.sipsa.dao.Usuario;
import gov.dane.sipsa.utils.ExternalStorage;

/**
 * Created by andreslopera on 6/11/16.
 */
public abstract class App extends AppCompatActivity {


    private static String ROOT_PATH = "";
    private static String LOG_PATH = "";
    private static String TRANSMISION_PATH = "";
    private static String RECEPTION_PATH = "";
    private static String FILE_PASSWORD =  "2bca613460d148106b085147a93699cd";


    private static String novedadRecoleccion;


    public static Usuario currentUsuario;

    public static String VERSION = "SIPSA PRODUCTIVO 1.3";

//    public static String VERSION = "SIPSA PRUEBAS 1.3";


    public static String getFilePassword() {
        return FILE_PASSWORD;
    }

    public static String getReceptionPath() {
        return RECEPTION_PATH;
    }

    public static String getRootPath() {
        return ROOT_PATH;
    }

    public static String getLogPath() {
        return LOG_PATH;
    }

    public static String getTransmisionPath() {
        return TRANSMISION_PATH;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        File[] roots = ExternalStorage.getPaths();
        if (roots.length == 0) {
            ExternalStorage.getSdCardPath();
            ROOT_PATH = "/mnt/sdcard/SIPSA";
        } else {
            ROOT_PATH = roots[0] + "/SIPSA";
        }
        LOG_PATH = ROOT_PATH + "/LOG";
        TRANSMISION_PATH = ROOT_PATH + "/TRANSMISION";
        RECEPTION_PATH = ROOT_PATH + "/RECEPCION";


        super.onCreate(savedInstanceState);
    }


    public void showMessage(String type, String message, View view) {
        int color = 0;
        if (type.equals("INFO")) {
            color = getApplicationContext().getResources().getColor(R.color.info);
        } else if (type.equals("DANGER")) {
            color = getApplicationContext().getResources().getColor(R.color.danger);
        } else if (type.equals("WARNING")) {
            color = getApplicationContext().getResources().getColor(R.color.warning);
        } else if (type.equals("SUCCESS")) {
            color = getApplicationContext().getResources().getColor(R.color.success);
        }
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(color);
        snackbar.show();
    }

    protected abstract void onPostExecute(gov.dane.sipsa.model.Status result);
}
