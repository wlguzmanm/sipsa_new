package gov.dane.sipsa.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.io.output.NullOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import gov.dane.sipsa.model.Status;
import gov.dane.sipsa.model.StatusFile;

/**
 * Created by tramusoft on 21/05/16.
 */
public  class Util {

    private enum TIPO_ARCHIVO{ RECOLECCION, SUPERVISION, MENSUAL }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static List<View> getAllViews(View v) {
        if (!(v instanceof ViewGroup) || ((ViewGroup) v).getChildCount() == 0) // It's a leaf
        { List<View> r = new ArrayList<View>(); r.add(v); return r; }
        else {
            List<View> list = new ArrayList<View>(); list.add(v); // If it's an internal node add itself
            int children = ((ViewGroup) v).getChildCount();
            for (int i=0;i<children;++i) {
                list.addAll(getAllViews(((ViewGroup) v).getChildAt(i)));
            }
            return list;
        }
    }

    public static String getString(Object string){
        if(string !=null){
            return string.toString();
        }
        return "";
    }


    public static int getString(String sTexto, String sTextoBuscado){
        int contador = 0;
        while (sTexto.indexOf(sTextoBuscado) > -1) {
            sTexto = sTexto.substring(sTexto.indexOf(
                    sTextoBuscado)+sTextoBuscado.length(),sTexto.length());
            contador++;
        }
        return contador;
    }

    public static String formatMoney(double money){


        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(money);


        return moneyString;
    }


    public static String formatMoneyText(String str, int decimalPosicion) {
        String result = "";

        char decSeparator = ',';
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        decSeparator = dfs.getDecimalSeparator();

        String decimalPart = "";
        String integerPart = "";

        if (str.contains(""  + decSeparator)) {
            decimalPart = str.substring(str.indexOf(""  + decSeparator) + 1, str.length());
            integerPart = str.substring(0, str.indexOf(""  + decSeparator));
        } else {
            integerPart = str;
        }

            if( (integerPart.length() - 1)/3>0){
                int pos = (integerPart.length()-1)/3;
                for(int i = 0 ;i < pos;i++){
                    integerPart = new StringBuffer(integerPart).insert(integerPart.length()-((i+1)*3)-i, ",").toString();
                }
            }

//        decimalPart = decimalPart.equals("")?"0":decimalPart;
        if (Integer.parseInt(decimalPart) == 0) {
            result = integerPart;
        } else {
            if ( decimalPart.length() > decimalPosicion) {
                result =  integerPart + "." + decimalPart.substring(0, decimalPosicion);
            } else {
                    result = integerPart + "." + decimalPart;
            }
        }
        return result;
    }



    public static void zip(String[] files, String zipFile) throws IOException {
        final int BUFFER_SIZE = 2048;
        BufferedInputStream origin = null;
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        try {
            byte data[] = new byte[BUFFER_SIZE];

            for (int i = 0; i < files.length; i++) {
                FileInputStream fi = new FileInputStream(files[i]);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);
                try {
                    ZipEntry entry = new ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1));
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                        out.write(data, 0, count);
                    }
                }
                finally {
                    origin.close();
                }
            }
        }
        finally {
            out.close();
        }
    }

    public static StatusFile zipExtracAll(File file, String passwordZip, String Destination) {
        StatusFile result = new StatusFile();
        try {
            ZipFile zipFile = new ZipFile(file);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(passwordZip);
            }
            zipFile.extractAll(Destination);
            result.setDescription("Archivo extraido correctamente.");
            result.setType(Status.StatusType.OK);
        } catch (ZipException e) {
            System.out.println(e);
            result.setDescription("Error descomprimiendo el Zip");
            result.setType(Status.StatusType.ERROR);
            return result;
        } catch (Exception e) {
            System.out.println(e);
            result.setDescription("Ha ocurrido un error al momento de leer el archivo.");
            result.setType(Status.StatusType.ERROR);
            return result;
        }
        return result;
    }


    public static StatusFile unzipFile(File file, String passwordZip){

        StatusFile result = new StatusFile();
        if (file.exists()) {
            ZipInputStream is = null;
            OutputStream os = new NullOutputStream();   // org.apache.commons.io.output.NullOutputStream

/* Buffered IO */

            BufferedInputStream bis = null;
            BufferedOutputStream bos = new BufferedOutputStream(os);

            try{
                ZipFile zipFile = new ZipFile(file);
                if (zipFile.isEncrypted()) {
                    zipFile.setPassword(passwordZip);
                }
                List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
                for (FileHeader fileHeader : fileHeaderList) {

                    if (fileHeader != null) {


                        is = zipFile.getInputStream(fileHeader);
                     //   result.setInputStream((InputStream) is);


                        bis = new BufferedInputStream(is);
                        BufferedReader r = new BufferedReader(
                                new InputStreamReader(bis, Charset.forName("UTF-8"))
                        );
                        StringBuilder responseStrBuilder = new StringBuilder();
                        String inputStr;

                        int c;
                        while((c = r.read()) != -1) {
                            char character = (char) c;
                            responseStrBuilder.append(character);
                        }


                        result.setStringFile(responseStrBuilder);
                        result.setDescription("");
                        result.setType(Status.StatusType.OK);
                        Util.closeFileHandlers(is, os);
                    }
                }

            } catch (ZipException e) {
                System.out.println(e);
                result.setDescription("Error descomprimiendo el Zip");
                result.setType(Status.StatusType.ERROR);
                return result;
                /*
            } catch (FileNotFoundException e) {
                System.out.println(e);
                result.setDescription("Archivo no encontrado.");
                result.setType(Status.StatusType.ERROR);
                return result;
            } catch (IOException e) {
                System.out.println(e.toString());
                result.setDescription("Obtener un nuevo archivo, el actual no puede ser leido.");
                result.setType(Status.StatusType.ERROR);
                return result;
                */
            } catch (Exception e) {
                System.out.println(e);
                result.setDescription("Ha ocurrido un error al momento de leer el archivo.");
                result.setType(Status.StatusType.ERROR);
                return result;
            }

        } else {
            result.setDescription("No se encuentra ningún archivo para ser extraido.");
            result.setType(Status.StatusType.ERROR);
            return result;
        }

        return result;
    }



    public static void zipOnlyFile(String urlCompressFile, String zipFileName, String filePassword)  throws ZipException {
            //This is name and path of zip file to be created
            ZipFile zipFile = new ZipFile(zipFileName);

            //Add files to be archived into zip file
            ArrayList<File> filesToAdd = new ArrayList<File>();
            filesToAdd.add(new File(urlCompressFile));

            //Initiate Zip Parameters which define various properties
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression

            //DEFLATE_LEVEL_FASTEST     - Lowest compression level but higher speed of compression
            //DEFLATE_LEVEL_FAST        - Low compression level but higher speed of compression
            //DEFLATE_LEVEL_NORMAL  - Optimal balance between compression level/speed
            //DEFLATE_LEVEL_MAXIMUM     - High compression level with a compromise of speed
            //DEFLATE_LEVEL_ULTRA       - Highest compression level but low speed
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            //Set the encryption flag to true
            parameters.setEncryptFiles(true);

            //Set the encryption method to AES Zip Encryption
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);

            //AES_STRENGTH_128 - For both encryption and decryption
            //AES_STRENGTH_192 - For decryption only
            //AES_STRENGTH_256 - For both encryption and decryption
            //Key strength 192 cannot be used for encryption. But if a zip file already has a
            //file encrypted with key strength of 192, then Zip4j can decrypt this file
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);

            //Set password
            parameters.setPassword(filePassword);

            //Now add files to the zip file
            zipFile.addFiles(filesToAdd, parameters);
    }


    public static void closeFileHandlers(ZipInputStream is, OutputStream os)

            throws IOException {

        // Close output stream

        if (os != null) {
            os.close();
            os = null;
        }

        if (is != null) {
            boolean skipCRCCheck = true;
            is.close(skipCRCCheck);
            is = null;
        }
    }



    public static void copyFile(String inputPath, String inputFile,String outputFileName, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + outputFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        }  catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }



    public static void deleteFile(String inputPath, String inputFile) {
        try {
            new File(inputPath + inputFile).delete();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public static void deleteFolderRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteFolderRecursive(child);
        fileOrDirectory.delete();
    }



    public static void closeFileHandlers(InputStream is)

            throws IOException {

        if (is != null) {
            is.close();
            is = null;
        }
    }




}
