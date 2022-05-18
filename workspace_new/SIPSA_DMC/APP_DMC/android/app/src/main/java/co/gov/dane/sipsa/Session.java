package co.gov.dane.sipsa;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusename(String usename,String nombre,String rol, String token, String clave, String exp, String tipoFormulario) {
        prefs.edit().putString("username", usename).commit();
        prefs.edit().putString("nombre", nombre).commit();
        prefs.edit().putString("rol", rol).commit();
        prefs.edit().putString("token", token).commit();
        prefs.edit().putString("clave", clave).commit();
        prefs.edit().putString("exp", exp).commit();
        prefs.edit().putString("tipoFormulario", tipoFormulario).commit();
    }

    public void setTipoFormulario(String tipoFormulario) {
        prefs.edit().putString("tipoFormulario", tipoFormulario).commit();
    }

    public String getTipoFormulario() {
        String formulario = prefs.getString("tipoFormulario","");
        return formulario;
    }

    public void setTokenVigencia(String token, String exp){
        prefs.edit().putString("token", token).commit();
        prefs.edit().putString("exp", exp).commit();
    }

    public String getFechaExp() {
        String exp = prefs.getString("exp","");
        return exp;
    }
    public String getusename() {
        String usename = prefs.getString("username","");
        return usename;
    }
    public String getnombre() {
        String nombre = prefs.getString("nombre","");
        return nombre;
    }

    public String getToken() {
        String token = prefs.getString("token","");
        return token;
    }

    public String getPassword() {
        String clave = prefs.getString("clave","");
        return clave;
    }

    public String getrol() {
        String rol = prefs.getString("rol","");
        return rol;
    }

    public void setCIIU(String ciiu){
        prefs.edit().putString("ciiu", ciiu).commit();
    }

    public String getCIIU() {
        String ciiu = prefs.getString("ciiu","");
        return ciiu;
    }

    public void setSinclasificarMatrix(Boolean flag){
        prefs.edit().putBoolean("sinClasificarMatrix", flag).commit();
    }

    public Boolean getSinclasificarMatrix() {
        Boolean flag = prefs.getBoolean("sinClasificarMatrix",false);
        return flag;
    }

    public void setUuidEncuestaTempMatrix(String uuidEncuesta) {
        prefs.edit().putString("uuidEncuestaTempMatrix", uuidEncuesta).commit();
    }

    public String getUuidEncuestaTempMatrix() {
        String uuidEncuesta = prefs.getString("uuidEncuestaTempMatrix","");
        return uuidEncuesta;
    }

    public void setFechaInicialMatrix(String fechaInicialMatrix) {
        prefs.edit().putString("fechaInicialMatrix", fechaInicialMatrix).commit();
    }

    public String getFechaInicialMatrix() {
        String fechaInicialMatrix = prefs.getString("fechaInicialMatrix","");
        return fechaInicialMatrix;
    }

    public void setIsCapituloEntrevista(Boolean isCapituloEntrevista) {
        prefs.edit().putBoolean("isCapituloEntrevista", isCapituloEntrevista).commit();
    }

    public Boolean getIsCapituloEntrevista() {
        Boolean isCapituloEntrevista = prefs.getBoolean("isCapituloEntrevista",false);
        return isCapituloEntrevista;
    }



    public void borrarSession(){
        prefs.edit().clear().commit();
    }
}
