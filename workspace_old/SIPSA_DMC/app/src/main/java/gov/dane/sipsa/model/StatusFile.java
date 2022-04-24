package gov.dane.sipsa.model;

import java.io.InputStream;

/**
 * Created by andreslopera on 11/5/16.
 */
public class StatusFile extends Status {
    private StringBuilder stringFile;

    private InputStream inputStream;


    public StringBuilder getStringFile() {
        return stringFile;
    }

    public void setStringFile(StringBuilder stringFile) {
        this.stringFile = stringFile;
    }


    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}


