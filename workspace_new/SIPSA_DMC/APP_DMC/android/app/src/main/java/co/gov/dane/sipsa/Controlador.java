package co.gov.dane.sipsa;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import co.gov.dane.sipsa.Helper.RetrofitClientInstance;
import co.gov.dane.sipsa.backend.Database;
import co.gov.dane.sipsa.backend.model.EnvioFormularioViewModel;
import co.gov.dane.sipsa.backend.model.Offline;
import co.gov.dane.sipsa.interfaces.IAuthentication;
import retrofit2.Call;
import retrofit2.Callback;


public class Controlador {
    public Context context;
    private Database db;
    public Controlador(Context context){
        this.context=context;
    }

    private Offline offline = new Offline();

    public void uploadData(final EnvioFormularioViewModel envio, final VolleyCallBack callBack){

        db=new Database(context);

        Boolean hay_internet=isNetworkAvailable();
        if(hay_internet){
            Session session=new Session(context);
            final String usuario = session.getusename();
            ProgressDialog barProgressDialog = new ProgressDialog(context);
            barProgressDialog.setTitle("Subiendo Información ...");
            barProgressDialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
            barProgressDialog.setProgress(0);

            try {
                barProgressDialog.show();
               // if(envio.getEsquemaManzana().size() > 0 ){
                    Gson gson = new GsonBuilder().create();
                    String json = gson.toJson(envio);
                    /*Log.d("json:", "Json------"+json);
                    IAuthentication service = RetrofitClientInstance.getRetrofitInstance().create(IAuthentication.class);
                    Call<ResponseEnvioManzanaViewModel> call = service.sincronizarFormulario("application/json;charset=UTF-8","Bearer  "+session.getToken(), envio);
                    //Call<ResponseEnvioManzanaViewModel> call = service.sincronizarFormulario("application/json;charset=UTF-8", envio);
                    call.enqueue(new Callback<ResponseEnvioManzanaViewModel>() {
                        @Override
                        public void onResponse(Call<ResponseEnvioManzanaViewModel> call, retrofit2.Response<ResponseEnvioManzanaViewModel> response) {
                            Mensajes mitoast =new Mensajes(context);
                            if(response.errorBody()!= null){
                                Gson gson = new Gson();
                                String jsonNew = gson.toJson(response.errorBody());
                                Log.d("TAG_CSINCRON", jsonNew);
                                mitoast.dialogoMensajeError("Error","Error en el servidor al sincronizar: "+jsonNew);
                                barProgressDialog.dismiss();
                            }else{
                                String mensaje = "";
                                if(response.body().getValidar()){
                                    mitoast.generarToast("Datos Enviados");
                                    barProgressDialog.dismiss();
                                }else{
                                    mitoast.generarToast("Datos Enviados, pero generaron error en la cola del servidor.","error");
                                    barProgressDialog.dismiss();
                                }
                            }
                            callBack.onSuccess();
                        }
                        @Override
                        public void onFailure(Call<ResponseEnvioManzanaViewModel> call, Throwable t) {
                            Log.d("Error:", t.getMessage());
                            callBack.onSuccess();
                        }
                    });*/
                //}

            } catch (SQLiteConstraintException e) {
                Mensajes mitoast =new Mensajes(context);
                mitoast.generarToast("Error al subir información");
                callBack.onSuccess();
            }
        }else{
            Mensajes mitoast =new Mensajes(context);
            mitoast.generarToast("No hay conexión a internet");
        }
    }

    /**
     * Metodo que valida conectividad con internet
     *
     * @return
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     * Metodo que devuelve la fecha
     * @return
     */
    public String getFechaActual(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

}
