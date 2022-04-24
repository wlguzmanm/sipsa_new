package gov.dane.sipsa.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by andreslopera on 4/23/16.
 */
public class RecoleccionPrincipal implements Serializable {


    private Long id;
    private Long articuloId;
    private String articuloNombre;

    private Long casaComercialId;
    private String casaComercialNombre;
    private Long unidadMedidaId;
    private String unidadMedidaNombre;
    private String registroIcaId;
    private Long tireId;
    private String grupoNombre;
    private Boolean estadoRecoleccion;
    private String novedad;
    private Long fuenId;
    private Long futiId;
    /**
     * DISTRITO DE RIEGO  frecuencia - tipo - unidadMedidaNombre - observacion - precio
     */
    private String frecuencia;
    private String observacionArticulo;
    private String unidadMedidaOtroNombre;

    private String precio;
    private Long articacoId;
    private String fuenNombre;
    private String muniId;
    private Double unmeCantidad2;
    private Double unmeCantidadPpal;
    private String tipo;
    private String observacion;
    private String desviacion;
    private String tiprNombre;
    private Long tiprId;
    private String unmeNombre2;
    private Double promAntDiario;
    private Boolean transmitido;
    private String novedadAnterior;
    private Date fechaProgramada;
    private String subgNombre;
    private Long artiVlrMinDiasM;
    private Long artiVlrMaxDiasM;
    private Long artiVlrMinTomas;
    private Long artiVlrMaxTomas;
    private Long artiVlrMinRondas;
    private Long artiVlrMaxRondas;

    public Long getFutiId() {
        return futiId;
    }

    public void setFutiId(Long futiId) {
        this.futiId = futiId;
    }

    public Long getArtiVlrMinDiasM() {
        return artiVlrMinDiasM;
    }

    public void setArtiVlrMinDiasM(Long artiVlrMinDiasM) {
        this.artiVlrMinDiasM = artiVlrMinDiasM;
    }

    public Long getArtiVlrMaxDiasM() {
        return artiVlrMaxDiasM;
    }

    public void setArtiVlrMaxDiasM(Long artiVlrMaxDiasM) {
        this.artiVlrMaxDiasM = artiVlrMaxDiasM;
    }

    public Long getArtiVlrMinTomas() {
        return artiVlrMinTomas;
    }

    public void setArtiVlrMinTomas(Long artiVlrMinTomas) {
        this.artiVlrMinTomas = artiVlrMinTomas;
    }

    public Long getArtiVlrMaxTomas() {
        return artiVlrMaxTomas;
    }

    public void setArtiVlrMaxTomas(Long artiVlrMaxTomas) {
        this.artiVlrMaxTomas = artiVlrMaxTomas;
    }

    public Long getArtiVlrMinRondas() {
        return artiVlrMinRondas;
    }

    public void setArtiVlrMinRondas(Long artiVlrMinRondas) {
        this.artiVlrMinRondas = artiVlrMinRondas;
    }

    public Long getArtiVlrMaxRondas() {
        return artiVlrMaxRondas;
    }

    public void setArtiVlrMaxRondas(Long artiVlrMaxRondas) {
        this.artiVlrMaxRondas = artiVlrMaxRondas;
    }

    public String getSubgNombre() {
        return subgNombre;
    }

    public void setSubgNombre(String subgNombre) {
        this.subgNombre = subgNombre;
    }

    public String getTireNombre() {
        return tireNombre;
    }

    public void setTireNombre(String tireNombre) {
        this.tireNombre = tireNombre;
    }

    private String tireNombre;

    public String getGrupoNombre() {
        return grupoNombre;
    }

    public void setGrupoNombre(String grupoNombre) {
        this.grupoNombre = grupoNombre;
    }

    public Long getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Long articuloId) {
        this.articuloId = articuloId;
    }

    public String getArticuloNombre() {
        return articuloNombre;
    }

    public void setArticuloNombre(String articuloNombre) {
        this.articuloNombre = articuloNombre;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Long getCasaComercialId() {
        return casaComercialId;
    }

    public void setCasaComercialId(Long casaComercialId) {
        this.casaComercialId = casaComercialId;
    }

    public String getCasaComercialNombre() {
        return casaComercialNombre;
    }

    public void setCasaComercialNombre(String casaComercialNombre) {
        this.casaComercialNombre = casaComercialNombre;
    }

    public Long getUnidadMedidaId() {
        return unidadMedidaId;
    }

    public void setUnidadMedidaId(Long unidadMedidaId) {
        this.unidadMedidaId = unidadMedidaId;
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

    public String getRegistroIcaId() {
        return registroIcaId;
    }

    public void setRegistroIcaId(String registroIcaId) {
        this.registroIcaId = registroIcaId;
    }

    public Long getTireId() {
        return tireId;
    }

    public void setTireId(Long tireId) {
        this.tireId = tireId;
    }


    public Boolean getEstadoRecoleccion() {
        return estadoRecoleccion;
    }

    public void setEstadoRecoleccion(Boolean estadoRecoleccion) {
        this.estadoRecoleccion = estadoRecoleccion;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }



    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public Long getFuenId() {
        return fuenId;
    }

    public void setFuenId(Long fuenId) {
        this.fuenId = fuenId;
    }

    public Long getArticacoId() {
        return articacoId;
    }

    public void setArticacoId(Long articacoId) {
        this.articacoId = articacoId;
    }

    public String getFuenNombre() {
        return fuenNombre;
    }

    public void setFuenNombre(String fuenNombre) {
        this.fuenNombre = fuenNombre;
    }

    public String getMuniId() {
        return muniId;
    }

    public void setMuniId(String muniId) {
        this.muniId = muniId;
    }

    public Double getUnmeCantidad2() {
        return unmeCantidad2;
    }

    public void setUnmeCantidad2(Double unmeCantidad2) {
        this.unmeCantidad2 = unmeCantidad2;
    }

    public Double getUnmeCantidadPpal() {
        return unmeCantidadPpal;
    }

    public void setUnmeCantidadPpal(Double unmeCantidadPpal) {
        this.unmeCantidadPpal = unmeCantidadPpal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacionArticulo(String observacionArticulo) {
        this.observacionArticulo = observacionArticulo;
    }

    public String getObservacionArticulo() {
        return observacionArticulo;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDesviacion() {
        return desviacion;
    }

    public void setDesviacion(String desviacion) {
        this.desviacion = desviacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTiprNombre() {
        return tiprNombre;
    }

    public void setTiprNombre(String tiprNombre) {
        this.tiprNombre = tiprNombre;
    }

    public Long getTiprId() {
        return tiprId;
    }

    public void setTiprId(Long tiprId) {
        this.tiprId = tiprId;
    }

    public String getUnmeNombre2() {
        return unmeNombre2;
    }

    public void setUnmeNombre2(String unmeNombre2) {
        this.unmeNombre2 = unmeNombre2;
    }

    public Double getPromAntDiario() {
        return promAntDiario;
    }

    public void setPromAntDiario(Double promAntDiario) {
        this.promAntDiario = promAntDiario;
    }

    public Boolean getTransmitido() {
        return transmitido;
    }

    public void setTransmitido(Boolean transmitido) {
        this.transmitido = transmitido;
    }

    public String getNovedadAnterior() {
        return novedadAnterior;
    }

    public void setNovedadAnterior(String novedadAnterior) {
        this.novedadAnterior = novedadAnterior;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }
}
