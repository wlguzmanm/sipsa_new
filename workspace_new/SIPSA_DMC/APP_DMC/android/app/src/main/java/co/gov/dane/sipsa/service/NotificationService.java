package co.gov.dane.sipsa.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import co.gov.dane.sipsa.Helper.RetrofitClientInstance;
import co.gov.dane.sipsa.MainActivity;
import co.gov.dane.sipsa.R;
import co.gov.dane.sipsa.Session;
import co.gov.dane.sipsa.backend.Database;
import co.gov.dane.sipsa.interfaces.IAuthentication;
import co.gov.dane.sipsa.login;
import co.gov.dane.sipsa.backend.model.JwtViewModel;
import co.gov.dane.sipsa.backend.model.RequestAuthentication;
import co.gov.dane.sipsa.backend.model.RequestStatusViewModel;
import co.gov.dane.sipsa.backend.model.ResponseToken;
import co.gov.dane.sipsa.backend.model.UserTokenViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends Service {

    public static final long NOTIFY_INTERVAL = 60 * 1000; // 60 seconds
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    private String token;
    private String username;
    private String rol;
    private String clave;

    private Session session;
    private Database db;

    public String channelId = "MYAPP_SIPSA_DANE";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle extras = intent.getExtras();
        token = (String) extras.get("token");
        username = (String) extras.get("username");
        clave = (String) extras.get("clave");
        rol = (String) extras.get("rol");
        token = "Bearer " + token;

        session = new Session(NotificationService.this);
        db = new Database(NotificationService.this);

        if (mTimer != null)
            mTimer.cancel();
        mTimer = new Timer();

        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask (token, username, clave), 0, NOTIFY_INTERVAL);
        return super.onStartCommand(intent, flags, startId);
    }

    class TimeDisplayTimerTask extends TimerTask {
        private UserTokenViewModel vc;
        private String token;
        private String username;
        private String password;

        public TimeDisplayTimerTask(String token, String username, String password) {
            this.token = token;
            this.password = password;
            this.username = username;
        }

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //callConsultaStatus();
                   // callActualizarLogin();
                }
            });
        }

        /**
         * Metodo que actualiza el token con el usuario y clave
         */
        private void callActualizarLogin() {
            try{
                if(session!= null && session.getFechaExp()!= null && !session.getFechaExp().equals("")){
                    long fechaExp = 0;
                    long yourmilliseconds = System.currentTimeMillis();
                    if(isNumeric(session.getFechaExp())){
                        fechaExp = Long.valueOf(session.getFechaExp());
                    }else{
                        fechaExp = System.currentTimeMillis();
                    }

                    if(fechaExp <= yourmilliseconds){
                        RequestAuthentication authentication = new RequestAuthentication();
                        authentication.setUsername(session.getusename());
                        authentication.setPassword(session.getPassword());

                        IAuthentication service = RetrofitClientInstance.getRetrofitInstanceAuth().create(IAuthentication.class);
                        Call<ResponseToken> call = service.login(
                                "password","danemonitoreo",
                                authentication.getUsername(), authentication.getPassword()
                        );
                        call.enqueue(new Callback<ResponseToken>() {
                            @Override
                            public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                                if(response.errorBody()!= null){
                                    if(response.code()!=200){
                                        Toast.makeText(getApplicationContext(), "Para la actualización del token, el server generó error, se cerrará la sesion automaticamente...", Toast.LENGTH_LONG).show();
                                        session.borrarSession();
                                        Intent mainIntent = new Intent(NotificationService.this, login.class);
                                        startActivity(mainIntent);
                                    }
                                }else{
                                    JwtViewModel jwtViewModel =  getDecodedJwt(response.body().getAccess_token());
                                    session.setTokenVigencia(response.body().getAccess_token(), jwtViewModel.getExp()+"000");
                                    setActualizarDbToken(response.body().getAccess_token(), jwtViewModel.getExp()+"000",session.getusename(),session.getPassword());
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseToken> call, Throwable t) {
                                Log.w("error proyecto AAAAAAA:" , t.getMessage().toString());
                                Toast.makeText(getApplicationContext(), "Error en el servidor : "+t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }catch (Exception e){
            }
        }

        /**
         * Metodo que dice si es numerico o
         *
         * @param cadena
         * @return
         */
        private boolean isNumeric(String cadena){
            try {
                Long.valueOf(cadena);
                return true;
            } catch (NumberFormatException nfe){
                return false;
            }
        }

        /**
         * Metodo que se actualiza el token y la vigencia
         *
         * @param token
         * @param exp
         * @param username
         * @param clave
         */
        private void setActualizarDbToken(String token, String exp, String username, String clave){
            try{
                Database dbDatabase = new Database(NotificationService.this);

                SQLiteDatabase spLiteDatabase=dbDatabase.getWritableDatabase();
                ContentValues values = new ContentValues();
                /*values.put(Usuario.TOKEN,token);
                values.put(Usuario.VIGENCIA,exp);

                spLiteDatabase.update(Usuario.TABLE_NAME, values,
                        Usuario.USUARIO+"= ? AND "+
                                Usuario.CLAVE+"= ? "
                        , new String[]{username,clave});*/
            }catch (Exception e){
            }
        }


        /**
         * Creacion de la notificacion
         *
         * @param title
         * @param text
         */
        public void createNotification(String title, String text, String canal, int notificationId) {
            createNotificationChannel(channelId + canal);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationService.this);
            NotificationCompat.Builder builder =  new NotificationCompat.Builder(NotificationService.this, channelId + canal)
                    .setSmallIcon(R.drawable.ic_databse)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(text))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            Intent notificationIntent = new Intent(NotificationService.this, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent intent = PendingIntent.getActivity(NotificationService.this, 0,
                    notificationIntent, 0);
            builder.setContentIntent(intent);
            builder.setAutoCancel(true);
            notificationManager.notify(notificationId, builder.build());
        }

        /**
         * Creacion del canal
         */
        private void createNotificationChannel(String canal) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "channel";
                String description = "channel DANE-SIPSA";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                //IMPORTANCE_MAX MUESTRA LA NOTIFICACIÓN ANIMADA
                NotificationChannel channel = new NotificationChannel(canal, name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
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

        /**
         * Metodo que actualiza el decode
         *
         * @param jwt
         * @return
         */
        public JwtViewModel getDecodedJwt(String jwt){
            String result = "";
            String[] parts = jwt.split("[.]");
            try
            {
                result += decoded(parts[1]);
                Gson gson = new GsonBuilder().create();
                JwtViewModel jwtViewModel = gson.fromJson(result, JwtViewModel.class);
                return jwtViewModel;
            }
            catch(Exception e)
            {
                JwtViewModel jwtViewModel = new JwtViewModel();
                return jwtViewModel;
            }
        }

        public String decoded(String JWTEncoded) throws Exception {
            try {
                String[] split = JWTEncoded.split("\\.");
                return  getJson(JWTEncoded);
            } catch (UnsupportedEncodingException e) {
                //Error
            }
            return "";
        }

        private String getJson(String strEncoded) throws UnsupportedEncodingException{
            byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
            return new String(decodedBytes, "UTF-8");
        }

    }
}
