package co.gov.dane.sipsa.backend.model;

import java.io.Serializable;

/**
 * Created by andreslopera on 6/17/16.
 */
public class Municipio implements Serializable {
    private String muniId;
    private String muniNombre;

    public void setMuniId(String muniId) {
        this.muniId = muniId;
    }

    public void setMuniNombre(String muniNombre) {
        this.muniNombre = muniNombre;
    }

    public String getMuniId() {
        return muniId;
    }

    public String getMuniNombre() {
        return muniNombre;
    }

    @Override
    public String toString() {
        return muniNombre;
    }
}
