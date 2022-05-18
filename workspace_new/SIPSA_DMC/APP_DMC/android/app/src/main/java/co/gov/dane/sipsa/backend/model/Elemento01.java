package co.gov.dane.sipsa.backend.model;

import java.io.Serializable;
import java.util.Date;

public class Elemento01 implements Serializable {


    private Long artiId;
    private String nombreArtiCompleto;
    private Long grupoInsumoId;
    private String muniNombre;
    private String tireNombre;
    private String artiNombre;
    private String muniId;
    private Long tireId;
    private Long futiId;
    private Date fechaProgramada;
    private Double precioAnterior;
    private Integer noRecoleccion;
    private String novedad;

    public Long getArtiId() {
        return artiId;
    }

    public void setArtiId(Long artiId) {
        this.artiId = artiId;
    }

    public String getNombreArtiCompleto() {
        return nombreArtiCompleto;
    }

    public void setNombreArtiCompleto(String nombreArtiCompleto) {
        this.nombreArtiCompleto = nombreArtiCompleto;
    }

    public Long getGrupoInsumoId() {
        return grupoInsumoId;
    }

    public void setGrupoInsumoId(Long grupoInsumoId) {
        this.grupoInsumoId = grupoInsumoId;
    }

    public String getMuniNombre() {
        return muniNombre;
    }

    public void setMuniNombre(String muniNombre) {
        this.muniNombre = muniNombre;
    }

    public String getTireNombre() {
        return tireNombre;
    }

    public void setTireNombre(String tireNombre) {
        this.tireNombre = tireNombre;
    }

    public String getArtiNombre() {
        return artiNombre;
    }

    public void setArtiNombre(String artiNombre) {
        this.artiNombre = artiNombre;
    }

    public String getMuniId() {
        return muniId;
    }

    public void setMuniId(String muniId) {
        this.muniId = muniId;
    }

    public Long getTireId() {
        return tireId;
    }

    public void setTireId(Long tireId) {
        this.tireId = tireId;
    }

    public Long getFutiId() {
        return futiId;
    }

    public void setFutiId(Long futiId) {
        this.futiId = futiId;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public Double getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(Double precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public Integer getNoRecoleccion() {
        return noRecoleccion;
    }

    public void setNoRecoleccion(Integer noRecoleccion) {
        this.noRecoleccion = noRecoleccion;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }

    @Override
    public String toString() {
        return nombreArtiCompleto;
    }
}
