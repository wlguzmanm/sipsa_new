package co.gov.dane.sipsa.utils;


import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * An object for reading from a JSON resource file and constructing an object from that resource file using Gson.
 */
public class JSONResourceReader {

    // === [ Private Data Members ] ============================================

    // Our JSON, in string form.
    private String jsonString;
    private static final String LOGTAG = JSONResourceReader.class.getSimpleName();

    // === [ Public API ] ======================================================

    /**
     * Read from a resources file and create a {@link JSONResourceReader} object that will allow the creation of other
     * objects from this resource.
     *
     * @param resources An application {@link Resources} object.
     * @param id The id for the resource to load, typically held in the raw/ folder.
     */
    public JSONResourceReader(Resources resources, int id) {
        InputStream resourceReader = resources.openRawResource(id);
        Writer writer = new StringWriter();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceReader, "UTF-8"));
            String line = reader.readLine();
            while (line != null) {
                writer.write(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            Log.e(LOGTAG, "Unhandled exception while using JSONResourceReader", e);
        } finally {
            try {
                resourceReader.close();
            } catch (Exception e) {
                Log.e(LOGTAG, "Unhandled exception while using JSONResourceReader", e);
            }
        }

        jsonString = writer.toString();
    }

    public JSONResourceReader(String PATH, String FILENAME) {
            File file = new File(PATH, FILENAME);
        //Read text from file
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        jsonString = text.toString();
    }





    /**
     * Build an object from the specified JSON resource using Gson.
     *
     * @param type The type of the object to build.
     *
     * @return An object of type T, with member fields populated using Gson.
     */
    public <T> T constructUsingGson(Class<T> type, String formatDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);
        Gson gson = new GsonBuilder().setDateFormat(sdf.toPattern()).create();
        return gson.fromJson(jsonString, type);
    }

    /**
     * Build an object from the specified JSON resource using Gson.
     *
     * @param type The type of the object to build.
     *
     * @return An object of type T, with member fields populated using Gson.
     */
    public <T> T constructUsingGson(Class<T> type) {
       return constructUsingGson(type,"MM/dd/yyyy hh:mm:ss a");
    }

}

