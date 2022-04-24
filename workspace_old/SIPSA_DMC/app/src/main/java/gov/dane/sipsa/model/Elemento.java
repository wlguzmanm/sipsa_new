package gov.dane.sipsa.model;

import java.io.Serializable;

public class Elemento implements Serializable{


    private Long articacoId;
    private Long artiId;
    private String artiNombre;
    private String cacoNombre;
    private String tireNombre;
    private String grupNombre;
    private String articacoRegicaLinea;
    private Long tireId;
    private Long cacoId;
    private Boolean check;

    /**
     * DISTRITO DE RIEGO  frecuencia - tipo - unidadMedidaNombre - observacion - precio
     */
    private String frecuencia;
    private String tipo;
    private String unidadMedidaNombre;
    private String unidadMedidaOtroNombre;
    private String observacion;
    private String precio;


    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUnidadMedidaNombre() {
        return unidadMedidaNombre;
    }

    public void setUnidadMedidaNombre(String unidadMedidaNombre) {
        this.unidadMedidaNombre = unidadMedidaNombre;
    }

    public String getUnidadMedidaOtroNombre() {
        return unidadMedidaOtroNombre;
    }

    public void setUnidadMedidaOtroNombre(String unidadMedidaOtroNombre) {
        this.unidadMedidaOtroNombre = unidadMedidaOtroNombre;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public Long getArtiId() {
        return artiId;
    }

    public void setArtiId(Long artiId) {
        this.artiId = artiId;
    }

    public Long getArticacoId() {
        return articacoId;
    }

    public void setArticacoId(Long articacoId) {
        this.articacoId = articacoId;
    }

    public String getArtiNombre() {
        return artiNombre;
    }

    public void setArtiNombre(String artiNombre) {
        this.artiNombre = artiNombre;
    }

    public String getCacoNombre() {
        return cacoNombre;
    }

    public void setCacoNombre(String cacoNombre) {
        this.cacoNombre = cacoNombre;
    }

    public String getTireNombre() {
        return tireNombre;
    }

    public void setTireNombre(String tireNombre) {
        this.tireNombre = tireNombre;
    }

    public String getGrupNombre() {
        return grupNombre;
    }

    public void setGrupNombre(String grupNombre) {
        this.grupNombre = grupNombre;
    }

    public String getArticacoRegicaLinea() {
        return articacoRegicaLinea;
    }

    public void setArticacoRegicaLinea(String articacoRegicaLinea) {
        this.articacoRegicaLinea = articacoRegicaLinea;
    }

    public Long getTireId() {
        return tireId;
    }

    public void setTireId(Long tireId) {
        this.tireId = tireId;
    }

    public Long getCacoId() {
        return cacoId;
    }

    public void setCacoId(Long cacoId) {
        this.cacoId = cacoId;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }


}
