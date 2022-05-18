package co.gov.dane.sipsa.backend.model;

import java.io.Serializable;

/**
 * Created by andreslopera on 6/17/16.
 */
public class Factor implements Serializable {
    private Long tireId;
    private String tireNombre;
    private Long fuenteId;


    public Long getTireId() {
        return tireId;
    }

    public void setTireId(Long tireId) {
        this.tireId = tireId;
    }

    public String getTireNombre() {
        return tireNombre;
    }

    public void setTireNombre(String tireNombre) {
        this.tireNombre = tireNombre;
    }

    public Long getFuenteId() {
        return fuenteId;
    }

    public void setFuenteId(Long fuenteId) {
        this.fuenteId = fuenteId;
    }

    @Override
    public String toString() {
        return tireNombre;
    }
}
