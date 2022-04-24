package gov.dane.sipsa.model;

import java.io.Serializable;

/**
 * Created by andreslopera on 6/17/16.
 */
public class FactorI01 implements Serializable {
    private Long tireId;
    private String tireNombre;


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


    @Override
    public String toString() {
        return tireNombre;
    }
}
