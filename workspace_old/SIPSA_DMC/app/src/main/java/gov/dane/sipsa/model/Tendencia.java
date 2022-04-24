package gov.dane.sipsa.model;

import java.io.Serializable;

/**
 * Created by tramusoft on 17/05/16.
 */
public class Tendencia implements Serializable {

    private static final long serialVersionUID = 1L;
    public Long id;
    public String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
