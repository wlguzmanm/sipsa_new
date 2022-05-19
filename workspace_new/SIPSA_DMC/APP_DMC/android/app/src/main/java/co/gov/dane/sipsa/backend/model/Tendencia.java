package co.gov.dane.sipsa.backend.model;

import java.io.Serializable;

/**
 * Created by wlguzmanm on 05/22.
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
