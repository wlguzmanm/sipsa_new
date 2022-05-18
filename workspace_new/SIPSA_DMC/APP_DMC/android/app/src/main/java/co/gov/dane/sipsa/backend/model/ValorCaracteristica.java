package co.gov.dane.sipsa.backend.model;

import java.io.Serializable;

/**
 * Created by hdblanco on 25/09/17.
 */

public class ValorCaracteristica implements Serializable {

    private Long vapeId;
    private String vapeDescripcion;
    private Long caraId;
    private String caraDescripcion;
    private String artiNombre;

    public Long getVapeId() {
        return vapeId;
    }

    public void setVapeId(Long vapeId) {
        this.vapeId = vapeId;
    }

    public String getVapeDescripcion() {
        return vapeDescripcion;
    }

    public void setVapeDescripcion(String vapeDescripcion) {
        this.vapeDescripcion = vapeDescripcion;
    }

    public Long getCaraId() {
        return caraId;
    }

    public void setCaraId(Long caraId) {
        this.caraId = caraId;
    }

    public String getCaraDescripcion() {
        return caraDescripcion;
    }

    public void setCaraDescripcion(String caraDescripcion) {
        this.caraDescripcion = caraDescripcion;
    }

    public String getArtiNombre() {
        return artiNombre;
    }

    public void setArtiNombre(String artiNombre) {
        this.artiNombre = artiNombre;
    }
}
