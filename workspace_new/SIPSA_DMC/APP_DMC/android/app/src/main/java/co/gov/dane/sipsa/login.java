package co.gov.dane.sipsa;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import co.gov.dane.sipsa.assets.utilidades.ViewComponent;
import co.gov.dane.sipsa.backend.Database;
import co.gov.dane.sipsa.backend.model.JwtViewModel;
import co.gov.dane.sipsa.backend.model.Offline;

public class login extends AppCompatActivity {

    private Session session;
    private EditText username,password;
    ViewComponent viewComponent = new ViewComponent(this,"LOGIN",null);
    private String idDevice;
    private Controlador controlador;

    private Database sconsultas;
    private Offline offline = new Offline();

    private String usernameStr;
    private String passwordStr;

    private String clave = null;
    private String usuario = null;
    private String nombre= null;
    private String rol= null;
    private String token= null;
    private String exp= null;
    private String tipoFormulario= null;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.login);
        idDevice = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        controlador = new Controlador(this);
        sconsultas = new Database(this);

        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);

        username.setText("aicastellanosm");
        password.setText("1090502182");
        //username.setText("consulta");
        //password.setText("123456");
        //username.setText("jezorah");
        //password.setText("123456");
        //username.setText("asrodriguezb");
        //password.setText("52157679");
        //username.setText("ymenesesr");
        //password.setText("34340581");
        //username.setText("yvalderrama");
        //password.setText("1128024471");
        //username.setText("mduranq");
        //password.setText("43831884");
        //username.setText("admin.monitoreo");
        //password.setText("P4c1f1c0");
        //username.setText("jdmartinezm");
        //password.setText("123456");
        //username.setText("rdguzmanm");
        //password.setText("123456");

        Button btn_login= (Button) findViewById(R.id.btn_login);

       btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MDQwNTk1NDIsImlhdCI6MTYzMDQ2NTU0MiwianRpIjoiMjk4N2Q0ODktMzk2Yi00ZTY4LTk1YWUtZWQ4NzYxYzljZTYwIiwiaXNzIjoiaHR0cDovL2FwcHMuZGFuZS5nb3YuY28vYXV0aC9yZWFsbXMvQXBwTW9uaXRvcmVvIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjdjMDA1OWMyLTYyOGMtNGYwYi05MWE4LWM2Mzg4ODM2MjEwMCIsInR5cCI6IkJlYXJlciIsImF6cCI6ImRhbmVtb25pdG9yZW8iLCJzZXNzaW9uX3N0YXRlIjoiYTI0OTgyZmQtZDI3ZC00ZGZjLTg1ODctZjk2MTFmNWYxNTExIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIiwiZGVmYXVsdC1yb2xlcy1hcHBtb25pdG9yZW8iXX0sInJlc291cmNlX2FjY2VzcyI6eyJkYW5lbW9uaXRvcmVvIjp7InJvbGVzIjpbIlN1cGVydmlzb3IiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJKRUZFSUQiOjMsIm5hbWUiOiJSQUZBRUwgREUgSkVTVVMgR1VaTUFOIE1BUlRJTkVaIiwiSkVGRSI6IkpVQU4gREFWSUQgTUFSVElORVogTU9SQUxFUyIsInByZWZlcnJlZF91c2VybmFtZSI6InJkZ3V6bWFubSIsImdpdmVuX25hbWUiOiJSQUZBRUwgREUgSkVTVVMiLCJmYW1pbHlfbmFtZSI6IkdVWk1BTiBNQVJUSU5FWiIsImVtYWlsIjoicmRndXptYW5tQGRhbmUuZ292LmNvIn0.IUaJfTfcKuAzmRb6Na3XNkswnyuVwbuh5iyj882Qwrw";

                JWT  decode =new JWT(token);
                JwtViewModel jwtViewModel =  getDecodedJwt(token);

                String rol = null;
                try {
                    String user = decode.getClaim("preferred_username").asString();
                    Claim resource_access = decode.getClaim("resource_access");
                    Gson resourceJson = new Gson();
                    String resourceAccessStr  = resourceJson.toJson(resource_access);
                    JSONObject jsonValue = new JSONObject(resourceAccessStr);
                    String value = jsonValue.getString("value");
                    JSONObject jsonDane = new JSONObject(value);
                    String danemonitoreo = jsonDane.getString("danemonitoreo");
                    JSONObject jsonRoles = new JSONObject(danemonitoreo);
                    String roles = jsonRoles.getString("roles");
                    JSONArray jsonRol = new JSONArray(roles);
                    rol = (String) jsonRol.get(0);
                }catch (Exception e){
                    e.printStackTrace();
                }

                session = new Session(login.this);
                session.setusename(username.getText().toString(),
                        decode.getClaim("name").asString(),
                        rol,
                        token,
                        password.getText().toString(),
                        jwtViewModel.getExp()+"000",
                        tipoFormulario);

                Intent mainIntent = new Intent(login.this,Entrada.class);
                login.this.startActivity(mainIntent);
                login.this.finish();
                viewComponent.progressBarProcess(R.id.loading,false);
            }
        });




       /* btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameStr = username.getText().toString();
                passwordStr =  password.getText().toString();
                Mensajes mitoast =new Mensajes(controlador.context);

                if(usernameStr.equals("") || passwordStr.equals("")){
                    Toast.makeText(login.this, "Digitar los datos de Usuario y Contraseña", Toast.LENGTH_LONG).show();
                    viewComponent.progressBarProcess(R.id.loading,false);
                }else{
                    offline = sconsultas.getOffline("OFFLINE", username.getText().toString());
                    viewComponent.progressBarProcess(R.id.loading, true);
                    if(offline!= null && offline.isActivo()){  //MODO OFFLINE ACTIVO
                        if(!usernameStr.equals("") || !passwordStr.equals("")){
                            if(sconsultas.validarUserPassword(usernameStr, passwordStr)){

                                Database db=new Database(login.this);
                                SQLiteDatabase sp=db.getWritableDatabase();
                                String columns[] = new String[]{Usuario.CLAVE,Usuario.USUARIO,Usuario.NOMBRE,Usuario.ROL,Usuario.TOKEN,Usuario.VIGENCIA};
                                String selection = Usuario.USUARIO + " LIKE ?"; // WHERE id LIKE ?
                                String selectionArgs[] = new String[]{username.getText().toString()};
                                Cursor cursor = null;
                                try{
                                    cursor = sp.query( Usuario.TABLE_NAME,columns,selection,selectionArgs,null,null,null);
                                }catch (Exception e){
                                }
                                if (cursor != null && cursor.getCount() > 0) {
                                    while(cursor.moveToNext()){
                                        try{
                                            clave = cursor.getString(cursor.getColumnIndex(Usuario.CLAVE));
                                            usuario = cursor.getString(cursor.getColumnIndex(Usuario.USUARIO));
                                            nombre= cursor.getString(cursor.getColumnIndex(Usuario.NOMBRE));
                                            rol= cursor.getString(cursor.getColumnIndex(Usuario.ROL));
                                            token= cursor.getString(cursor.getColumnIndex(Usuario.TOKEN));
                                            exp= cursor.getString(cursor.getColumnIndex(Usuario.VIGENCIA));

                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                                            String fechaInicio = sdf.format(new Date());

                                            Date date =new Date(Long.valueOf(exp));
                                            String fechaFin = sdf.format(date);

                                            //int resta = Long.valueOf("20200101").intValue() - Long.valueOf("20200101").intValue();
                                            int resta = Long.valueOf(fechaFin).intValue() - Long.valueOf(fechaInicio).intValue();
                                            if(resta > 0 && resta <= 3 ){
                                                mitoast.dialogoMensajeError("Error Acceso","El sistema ha detectado que el Token que está usando, debe ser renovado por uno nuevo, por favor, cambiar la configuración local a Internet, cerrar la sesión y garantizar que el dispositivo tenga acceso a Internet, para la renovación del Token.");
                                            }

                                            if(resta == 0 ){
                                                mitoast.dialogoMensajeError("Error Acceso", "El Token se encuentra vencido. Por favor, cambiar la configuración local a Internet, cerrar la sesión y garantizar que el dispositivo tenga acceso a Internet, para la renovación del Token. ");
                                                viewComponent.progressBarProcess(R.id.loading,false);
                                            }else{
                                                if(clave.equals(password.getText().toString())){
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            try{
                                                                session = new Session(login.this);
                                                                session.setusename(usuario,nombre,rol,token, clave, exp, tipoFormulario);
                                                                Intent mainIntent = new Intent(login.this,Entrada.class);
                                                                login.this.startActivity(mainIntent);
                                                                login.this.finish();
                                                                viewComponent.progressBarProcess(R.id.loading,false);
                                                            }catch (Exception e){
                                                            }
                                                        }
                                                    },1000);
                                                }else{
                                                    Toast.makeText(login.this, "Datos incorrectos", Toast.LENGTH_LONG).show();
                                                    viewComponent.progressBarProcess(R.id.loading,false);
                                                }
                                            }

                                        }catch (Exception e){
                                        }
                                    }
                                }else{
                                    Toast.makeText(login.this, "Usuario y/o Contraseña inválidos", Toast.LENGTH_LONG).show();
                                    viewComponent.progressBarProcess(R.id.loading,false);
                                }
                                sp.close();
                            }else{
                                Boolean hay_internet=controlador.isNetworkAvailable();
                                if(hay_internet){
                                    RequestAuthentication authentication = new RequestAuthentication();
                                    authentication.setUsername(usernameStr);
                                    authentication.setPassword(passwordStr);

                                    IAuthentication service = RetrofitClientInstance.getRetrofitInstanceAuth().create(IAuthentication.class);
                                    Call<ResponseToken> call = service.login(
                                            "password","danemonitoreo",
                                            authentication.getUsername(), authentication.getPassword()
                                            );
                                    call.enqueue(new Callback<ResponseToken>() {
                                        @Override
                                        public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                                            if(response.errorBody()!= null){
                                                Toast.makeText(getApplicationContext(), "Usuario/clave Incorrectos...", Toast.LENGTH_LONG).show();
                                                viewComponent.progressBarProcess(R.id.loading,false);
                                            }else{
                                                try {
                                                    Database db=new Database(login.this);
                                                    SQLiteDatabase sp=db.getWritableDatabase();
                                                    ContentValues values = new ContentValues();

                                                    if(sconsultas.validarUserPassword(usernameStr, passwordStr)){
                                                        //borra solo el usuario
                                                        sp.delete(Usuario.TABLE_NAME,   Usuario.USUARIO  +"=?  AND "+Usuario.CLAVE+"=? ",
                                                                new String[]{usernameStr,passwordStr});
                                                    }

                                                    if(sconsultas.validarUser(usernameStr)){
                                                        //borra solo el usuario si en tal caso existiera
                                                        sp.delete(Usuario.TABLE_NAME,   Usuario.USUARIO  +"=? ",
                                                                new String[]{usernameStr});
                                                    }

                                                    JWT  decode =new JWT(response.body().getAccess_token());
                                                    Map<String, Claim> allClaims = decode.getClaims();
                                                    String rol = null;
                                                    try {
                                                        String user = decode.getClaim("preferred_username").asString();
                                                        Claim resource_access = decode.getClaim("resource_access");
                                                        Gson resourceJson = new Gson();
                                                        String resourceAccessStr  = resourceJson.toJson(resource_access);
                                                        JSONObject jsonValue = new JSONObject(resourceAccessStr);
                                                        String value = jsonValue.getString("value");
                                                        JSONObject jsonDane = new JSONObject(value);
                                                        String danemonitoreo = jsonDane.getString("danemonitoreo");
                                                        JSONObject jsonRoles = new JSONObject(danemonitoreo);
                                                        String roles = jsonRoles.getString("roles");
                                                        JSONArray jsonRol = new JSONArray(roles);
                                                        rol = (String) jsonRol.get(0);
                                                    }catch (Exception e){
                                                        e.printStackTrace();
                                                    }

                                                    JwtViewModel jwtViewModel =  getDecodedJwt(response.body().getAccess_token());
                                                   //values.put(Usuario.ID, decode.getClaim("email").asString());
                                                    values.put(Usuario.USUARIO,  decode.getClaim("preferred_username").asString());
                                                    values.put(Usuario.CLAVE, password.getText().toString());
                                                    values.put(Usuario.NOMBRE,  decode.getClaim("name").asString());
                                                    values.put(Usuario.IMEI, idDevice);
                                                    values.put(Usuario.ROL, rol);
                                                    values.put(Usuario.VIGENCIA, jwtViewModel.getExp()+"000");
                                                    //TODO: Pendiente
                                                    values.put(Usuario.ID_SUPERVISOR, "PENDIENTE");
                                                    values.put(Usuario.TOKEN, response.body().getAccess_token());

                                                    sp.insert(Usuario.TABLE_NAME, null, values);
                                                    sp.close();
                                                }catch (Exception e){
                                                }

                                                try{
                                                    JWT  decode =new JWT(response.body().getAccess_token());
                                                    JwtViewModel jwtViewModel =  getDecodedJwt(response.body().getAccess_token());
                                                    session = new Session(login.this);
                                                    session.setusename(username.getText().toString(),
                                                            decode.getClaim("name").asString(),
                                                            rol,
                                                            response.body().getAccess_token(),
                                                            password.getText().toString(),
                                                            jwtViewModel.getExp()+"000",
                                                            tipoFormulario);

                                                    Intent mainIntent = new Intent(login.this,Entrada.class);
                                                    login.this.startActivity(mainIntent);
                                                    login.this.finish();
                                                    viewComponent.progressBarProcess(R.id.loading,false);
                                                }catch (Exception e){
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseToken> call, Throwable t) {
                                            Log.w("error proyecto BBBBBBB:" , t.getMessage().toString());
                                            Toast.makeText(getApplicationContext(), "Error en el servidor : "+t.getMessage(), Toast.LENGTH_LONG).show();
                                            viewComponent.progressBarProcess(R.id.loading,false);
                                        }
                                    });
                                }else{
                                    ///No hay internet
                                    mitoast.dialogoMensajeError("Error Acceso","No se puede autenticar, el sistema no tiene Internet. El sistema también intento validar una sesión guardada anteriormente.");
                                    viewComponent.progressBarProcess(R.id.loading,false);
                                }
                            }
                        }else{
                            Toast.makeText(login.this, "Digitar los datos de Usuario y Contraseña", Toast.LENGTH_LONG).show();
                            viewComponent.progressBarProcess(R.id.loading,false);
                        }

                    }else{
                        viewComponent.progressBarProcess(R.id.loading,true);
                        if(!usernameStr.equals("") || !passwordStr.equals("")){
                            Boolean hay_internet=controlador.isNetworkAvailable();
                            if(hay_internet){
                                RequestAuthentication authentication = new RequestAuthentication();
                                authentication.setUsername(usernameStr);
                                authentication.setPassword(passwordStr);

                                Log.d("TAG_LOGIN", "usernameStr-----"+usernameStr);
                                Log.d("TAG_LOGIN", "passwordStr-----"+passwordStr);
                                IAuthentication service = RetrofitClientInstance.getRetrofitInstanceAuth().create(IAuthentication.class);
                                Call<ResponseToken> call = service.login(
                                        "password","danemonitoreo",
                                        authentication.getUsername(), authentication.getPassword()
                                );
                                call.enqueue(new Callback<ResponseToken>() {
                                    @Override
                                    public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                                        if(response.errorBody()!= null){
                                            Toast.makeText(getApplicationContext(), "Usuario/clave Incorrectos...", Toast.LENGTH_LONG).show();
                                            viewComponent.progressBarProcess(R.id.loading,false);
                                        }else{
                                            Log.d("TAG_LOGIN", "response-----"+response.message());
                                            try {
                                                Database db=new Database(login.this);
                                                SQLiteDatabase sp=db.getWritableDatabase();
                                                ContentValues values = new ContentValues();

                                                if(sconsultas.validarUserPassword(usernameStr, passwordStr)){
                                                    //borra solo el usuario
                                                    sp.delete(Usuario.TABLE_NAME,   Usuario.USUARIO  +"=?  AND "+Usuario.CLAVE+"=? ",
                                                            new String[]{usernameStr,passwordStr});
                                                }

                                                if(sconsultas.validarUser(usernameStr)){
                                                    //borra solo el usuario si en tal caso existiera
                                                    sp.delete(Usuario.TABLE_NAME,   Usuario.USUARIO  +"=? ",
                                                            new String[]{usernameStr});
                                                }

                                                JWT  decode =new JWT(response.body().getAccess_token());
                                                Map<String, Claim> allClaims = decode.getClaims();

                                                try {
                                                    String user = decode.getClaim("preferred_username").asString();
                                                    Claim resource_access = decode.getClaim("resource_access");
                                                    Gson resourceJson = new Gson();
                                                    String resourceAccessStr  = resourceJson.toJson(resource_access);
                                                    JSONObject jsonValue = new JSONObject(resourceAccessStr);
                                                    String value = jsonValue.getString("value");
                                                    JSONObject jsonDane = new JSONObject(value);
                                                    String danemonitoreo = jsonDane.getString("danemonitoreo");
                                                    JSONObject jsonRoles = new JSONObject(danemonitoreo);
                                                    String roles = jsonRoles.getString("roles");
                                                    JSONArray jsonRol = new JSONArray(roles);
                                                    rol = (String) jsonRol.get(0);
                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                }

                                                JwtViewModel jwtViewModel =  getDecodedJwt(response.body().getAccess_token());
                                                //values.put(Usuario.ID, decode.getClaim("email").asString());
                                                values.put(Usuario.USUARIO,  decode.getClaim("preferred_username").asString());
                                                values.put(Usuario.CLAVE, password.getText().toString());
                                                values.put(Usuario.NOMBRE,  decode.getClaim("name").asString());
                                                values.put(Usuario.IMEI, idDevice);
                                                values.put(Usuario.ROL, rol);
                                                values.put(Usuario.VIGENCIA, jwtViewModel.getExp()+"000");
                                                //TODO: Pendiente
                                                values.put(Usuario.ID_SUPERVISOR, "PENDIENTE");
                                                values.put(Usuario.TOKEN, response.body().getAccess_token());

                                                sp.insert(Usuario.TABLE_NAME, null, values);
                                                sp.close();

                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }

                                            try{
                                                JWT  decode =new JWT(response.body().getAccess_token());
                                                JwtViewModel jwtViewModel =  getDecodedJwt(response.body().getAccess_token());
                                                session = new Session(login.this);
                                                session.setusename(username.getText().toString(),
                                                        decode.getClaim("name").asString(),
                                                        rol,
                                                        response.body().getAccess_token(),
                                                        password.getText().toString(),
                                                        jwtViewModel.getExp()+"000",
                                                        tipoFormulario);

                                                Intent mainIntent = new Intent(login.this,Entrada.class);
                                                login.this.startActivity(mainIntent);
                                                login.this.finish();
                                                viewComponent.progressBarProcess(R.id.loading,false);
                                            }catch (Exception e){
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseToken> call, Throwable t) {
                                        Log.w("error proyecto AAAAAAA:" , t.getMessage().toString());
                                        Toast.makeText(getApplicationContext(), "Error en el servidor : "+t.getMessage(), Toast.LENGTH_LONG).show();
                                        viewComponent.progressBarProcess(R.id.loading,false);
                                    }
                                });
                            }else{

                                if(!usernameStr.equals("") || !passwordStr.equals("")){
                                    if(sconsultas.validarUserPassword(usernameStr, passwordStr)){

                                        Database db=new Database(login.this);
                                        SQLiteDatabase sp=db.getWritableDatabase();
                                        String columns[] = new String[]{Usuario.CLAVE,Usuario.USUARIO,Usuario.NOMBRE,Usuario.ROL,Usuario.TOKEN,Usuario.VIGENCIA};
                                        String selection = Usuario.USUARIO + " LIKE ?"; // WHERE id LIKE ?
                                        String selectionArgs[] = new String[]{username.getText().toString()};
                                        Cursor cursor = null;
                                        try{
                                            cursor = sp.query( Usuario.TABLE_NAME,columns,selection,selectionArgs,null,null,null);
                                        }catch (Exception e){
                                        }
                                        if (cursor != null && cursor.getCount() > 0) {
                                            while(cursor.moveToNext()){
                                                try{
                                                    clave = cursor.getString(cursor.getColumnIndex(Usuario.CLAVE));
                                                    usuario = cursor.getString(cursor.getColumnIndex(Usuario.USUARIO));
                                                    nombre= cursor.getString(cursor.getColumnIndex(Usuario.NOMBRE));
                                                    rol= cursor.getString(cursor.getColumnIndex(Usuario.ROL));
                                                    token= cursor.getString(cursor.getColumnIndex(Usuario.TOKEN));
                                                    exp= cursor.getString(cursor.getColumnIndex(Usuario.VIGENCIA));

                                                    if(clave.equals(password.getText().toString())){
                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                try{
                                                                    session = new Session(login.this);
                                                                    session.setusename(usuario,nombre,rol,token, clave, exp, tipoFormulario);
                                                                    Intent mainIntent = new Intent(login.this,Entrada.class);
                                                                    login.this.startActivity(mainIntent);
                                                                    login.this.finish();
                                                                    mitoast.generarToast("No hay conexión a internet, pero el sistema detecto un token activo");
                                                                    viewComponent.progressBarProcess(R.id.loading,false);
                                                                }catch (Exception e){
                                                                }
                                                            }
                                                        },1000);
                                                    }else{
                                                        Toast.makeText(login.this, "Datos incorrectos", Toast.LENGTH_LONG).show();
                                                        viewComponent.progressBarProcess(R.id.loading,false);
                                                    }
                                                }catch (Exception e){
                                                }
                                            }
                                        }else{
                                            Toast.makeText(login.this, "Usuario y/o Contraseña inválidos", Toast.LENGTH_LONG).show();
                                            viewComponent.progressBarProcess(R.id.loading,false);
                                        }
                                        sp.close();
                                    }else{
                                        mitoast.dialogoMensajeError("Error Acceso","El sistema actualmente no cuenta con acceso a Internet. El sistema validó otra alternativa de acceso por medio de un token activo y no se encontró uno activo  ó las credenciales de Usuario y/o Contraseña son inválidos.");

                                        viewComponent.progressBarProcess(R.id.loading,false);
                                    }
                                }else{
                                    Toast.makeText(login.this, "Digitar los datos de Usuario y Contraseña", Toast.LENGTH_LONG).show();
                                    viewComponent.progressBarProcess(R.id.loading,false);
                                }



                            }
                        }else{
                            Toast.makeText(login.this, "Digitar los datos de Usuario y Contraseña", Toast.LENGTH_LONG).show();
                            viewComponent.progressBarProcess(R.id.loading,false);
                        }
                    }
                }
            }
        });*/
    }


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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(login.this, login.class);
        startActivity(intent);
        this.finish();
    }

}