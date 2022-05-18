package co.gov.dane.sipsa.backend.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

import java.io.Serializable;

/**
 * Entity mapped to table "FUENTE_ARTICULO".
 */
public class FuenteArticulo implements Serializable {

    private Long futiId;
    private String fuenTelefono;
    private String fuenDireccion;
    private String fuenEmail;
    private String fuenNombre;
    private java.util.Date prreFechaProgramada;
    private String muniNombre;
    private String fuenNombreInformante;
    private String tireNombre;
    private String muniId;
    private Long tireId;
    private Long fuenId;

    public FuenteArticulo() {
    }

    public FuenteArticulo(Long futiId) {
        this.futiId = futiId;
    }

    public FuenteArticulo(Long futiId, String fuenTelefono, String fuenDireccion, String fuenEmail, String fuenNombre, java.util.Date prreFechaProgramada, String muniNombre, String fuenNombreInformante, String tireNombre, String muniId, Long tireId, Long fuenId) {
        this.futiId = futiId;
        this.fuenTelefono = fuenTelefono;
        this.fuenDireccion = fuenDireccion;
        this.fuenEmail = fuenEmail;
        this.fuenNombre = fuenNombre;
        this.prreFechaProgramada = prreFechaProgramada;
        this.muniNombre = muniNombre;
        this.fuenNombreInformante = fuenNombreInformante;
        this.tireNombre = tireNombre;
        this.muniId = muniId;
        this.tireId = tireId;
        this.fuenId = fuenId;
    }

    public Long getFutiId() {
        return futiId;
    }

    public void setFutiId(Long futiId) {
        this.futiId = futiId;
    }

    public String getFuenTelefono() {
        return fuenTelefono;
    }

    public void setFuenTelefono(String fuenTelefono) {
        this.fuenTelefono = fuenTelefono;
    }

    public String getFuenDireccion() {
        return fuenDireccion;
    }

    public void setFuenDireccion(String fuenDireccion) {
        this.fuenDireccion = fuenDireccion;
    }

    public String getFuenEmail() {
        return fuenEmail;
    }

    public void setFuenEmail(String fuenEmail) {
        this.fuenEmail = fuenEmail;
    }

    public String getFuenNombre() {
        return fuenNombre;
    }

    public void setFuenNombre(String fuenNombre) {
        this.fuenNombre = fuenNombre;
    }

    public java.util.Date getPrreFechaProgramada() {
        return prreFechaProgramada;
    }

    public void setPrreFechaProgramada(java.util.Date prreFechaProgramada) {
        this.prreFechaProgramada = prreFechaProgramada;
    }

    public String getMuniNombre() {
        return muniNombre;
    }

    public void setMuniNombre(String muniNombre) {
        this.muniNombre = muniNombre;
    }

    public String getFuenNombreInformante() {
        return fuenNombreInformante;
    }

    public void setFuenNombreInformante(String fuenNombreInformante) {
        this.fuenNombreInformante = fuenNombreInformante;
    }

    public String getTireNombre() {
        return tireNombre;
    }

    public void setTireNombre(String tireNombre) {
        this.tireNombre = tireNombre;
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

    public Long getFuenId() {
        return fuenId;
    }

    public void setFuenId(Long fuenId) {
        this.fuenId = fuenId;
    }

    @Override
    public String toString() {
        return tireNombre;
    }
}