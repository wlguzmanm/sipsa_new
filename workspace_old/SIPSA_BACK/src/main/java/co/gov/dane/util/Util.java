package co.gov.dane.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

import net.lingala.zip4j.io.ZipInputStream;

public final class Util {
	
	public static File convert(MultipartFile file) throws IOException
	{    
	    File convFile = new File(file.getOriginalFilename());
	   
	    //convFile.createNewFile();
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}


    public static void closeFileHandlers(ZipInputStream is, OutputStream os)
            throws IOException {
        // Close output stream
        if (os != null) {
            os.close();
            os = null;
        }

        if (is != null) {
//            is.close();
            boolean skipCRCCheck = true;
            is.close(skipCRCCheck);
            is = null;
        }
    }
    
    
    public static String convertirCamelCaseToUnderScore(String s){
    	String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return s.replaceAll(regex, replacement).toUpperCase();
    }
    
    
    
}
