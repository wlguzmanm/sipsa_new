package co.gov.dane.sipsa.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import co.gov.dane.sipsa.R;

public final class General {

    public static String getVersionDMC(Context ctx){
        String version = null;
        try {
            version = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }finally {
            return version;
        }
    }

    public static String rutaAlistamiento(Context ctx){
        String pathAlistamiento;
        File[] roots = ExternalStorage.getPaths();
        if(roots.length != 0){
            pathAlistamiento = roots[0] + "/" + ctx.getString(R.string.DB_PATH);
        }else{
            pathAlistamiento = Environment.getExternalStorageDirectory() + "/" + ctx.getString(R.string.DB_PATH);
        }
        return pathAlistamiento;
    }

    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public static String fechaActual(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(c.getTime());
    }

    public static String fechaActualAnioMesDia(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c.getTime());
    }

    public static String fechaFormatActual(String format){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(c.getTime());
    }

    public static String rellenarCadenaIzquierda(String cadOriginal, Integer longitud, String relleno){
        String cadResultado = "";

        cadResultado = cadOriginal;
        while(cadResultado.length() < longitud ){
            cadResultado = relleno + cadResultado;
        }

        return cadResultado;
    }

    public static int CalcularEdad(int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        m++;
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        /*if(a < 0)
            throw new IllegalArgumentException("Age < 0");*/
        return a;
    }

    public static boolean isValidDate2(String input, String format) {
        boolean valid = false;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            String output = dateFormat.parse(input).toString();
            valid = input.equals(output);
        } catch (Exception ignore) {}

        return valid;
    }

    public static boolean isValidDate(String input, String format) {
        boolean valid = false;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date = formatter.parse(input);
            valid = true;
            System.out.println(date);
            System.out.println(formatter.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
            valid = false;
        }
        return valid;
    }


    public static String JoinString(String delimitor, String[] subkeys) {
        String joinBuffer = null;
        if(null!=subkeys && subkeys.length>0) {
            joinBuffer = subkeys[0];
            for(int idx=1;idx<subkeys.length;idx++) {
                joinBuffer = joinBuffer + delimitor  + subkeys[idx];
            }
        }
        return joinBuffer;
    }

    public static String JoinArrayString(String delimitor, ArrayList<String> subkeys) {
        String joinBuffer = null;
        if(null!=subkeys && subkeys.size()>0) {
            joinBuffer = subkeys.get(0);
            for(int idx=1;idx<subkeys.size();idx++) {
                joinBuffer = joinBuffer + delimitor  + subkeys.get(idx);
            }
        }
        return joinBuffer;
    }

    public static void showSoftKeyboard(Context ctx, View view){
        if(view.requestFocus()){
            InputMethodManager imm =(InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static void hideSoftKeyboard(Context ctx, View view){
        InputMethodManager imm =(InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
