package co.gov.dane.sipsa.backend.model;

import java.io.Serializable;
import java.util.Date;

public class Resumen01 implements Serializable {


    private Long recoleccionId;
    private Long artiId;
    private String artiNombre;
    private String nombreArtiCompleto;
    private String muniId;
    private Long tireId;
    private String muniNombre;
    private String tireNombre;
    private Long grupoInsumoId;
    private Long infoId;
    private String infoNombre;
    private String infoTelefono;
    private Long futiId;
    private Date fechaProgramada;
    private Double precioAnterior;
    private Double precioActual;
    private String novedad;

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

    public String getNombreArtiCompleto() {
        return nombreArtiCompleto;
    }

    public void setNombreArtiCompleto(String nombreArtiCompleto) {
        this.nombreArtiCompleto = nombreArtiCompleto;
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

    public Long getGrupoInsumoId() {
        return grupoInsumoId;
    }

    public void setGrupoInsumoId(Long grupoInsumoId) {
        this.grupoInsumoId = grupoInsumoId;
    }

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public String getInfoNombre() {
        return infoNombre;
    }

    public void setInfoNombre(String infoNombre) {
        this.infoNombre = infoNombre;
    }

    public Long getFutiId() {
        return futiId;
    }

    public void setFutiId(Long futiId) {
        this.futiId = futiId;
    }

    public String getInfoTelefono() {
        return infoTelefono;
    }

    public void setInfoTelefono(String infoTelefono) {
        this.infoTelefono = infoTelefono;
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

    public void setPrecioAnterior(Double precioAnterio) {
        this.precioAnterior = precioAnterio;
    }

    public Double getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(Double precioActual) {
        this.precioActual = precioActual;
    }

    public Long getRecoleccionId() {
        return recoleccionId;
    }

    public void setRecoleccionId(Long recoleccionId) {
        this.recoleccionId = recoleccionId;
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
