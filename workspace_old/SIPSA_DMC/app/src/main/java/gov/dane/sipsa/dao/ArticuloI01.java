package gov.dane.sipsa.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import java.io.Serializable;

/**
 * Entity mapped to table "ARTICULO_I01".
 */
public class ArticuloI01 implements Serializable {

    private Long id;
    private Long grin2Id;
    private Long artiId;
    private String artiNombre;
    private String nombreCompleto;
    private Long tireId;

    public ArticuloI01() {
    }

    public ArticuloI01(Long id) {
        this.id = id;
    }

    public ArticuloI01(Long id, Long grin2Id, Long artiId, String artiNombre, String nombreCompleto, Long tireId) {
        this.id = id;
        this.grin2Id = grin2Id;
        this.artiId = artiId;
        this.artiNombre = artiNombre;
        this.nombreCompleto = nombreCompleto;
        this.tireId = tireId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGrin2Id() {
        return grin2Id;
    }

    public void setGrin2Id(Long grin2Id) {
        this.grin2Id = grin2Id;
    }

    public Long getArtiId() {
        return artiId;
    }

    public void setArtiId(Long artiId) {
        this.artiId = artiId;
    }

    public String getArtiNombre() {
        return artiNombre;
    }

    public void setArtiNombre(String artiNombre) {
        this.artiNombre = artiNombre;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Long getTireId() {
        return tireId;
    }

    public void setTireId(Long tireId) {
        this.tireId = tireId;
    }

    @Override
    public String toString() {
        return artiNombre ;
    }
}
