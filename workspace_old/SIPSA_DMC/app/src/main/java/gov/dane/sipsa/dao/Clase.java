package gov.dane.sipsa.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import java.io.Serializable;

/**
 * Entity mapped to table "CLASE".
 */
public class Clase implements Serializable{

    private static final long serialVersionUID = 1L;
    private String id;
    private String descripcion;

    public Clase() {
    }

    public Clase(String id) {
        this.id = id;
    }

    public Clase(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }

}
