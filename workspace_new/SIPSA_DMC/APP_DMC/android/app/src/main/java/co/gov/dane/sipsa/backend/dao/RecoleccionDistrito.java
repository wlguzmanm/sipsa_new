package co.gov.dane.sipsa.backend.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "RECOLECCION_DISTRITO".
 */
public class RecoleccionDistrito {

    private Long id;
    private Long artiVlrMinDiasm;
    private Long futiId;
    private String novedadAnterior;
    private Long articacoId;
    private String subgNombre;
    private String unmeNombre2;
    private String cacoNombre;
    private String fuenNombre;
    private String articacoRegicaLinea;
    private Long tiprId;
    private String artiNombre;
    private Long artiVlrMaxTomas;
    private String tiprNombre;
    private String unmeNombrePpal;
    private Long artiVlrMinRondas;
    private Double promAntDiario;
    private Long artiVlrMinTomas;
    private Long artiVlrMaxRondas;
    private java.util.Date prreFechaProgramada;
    private String muniId;
    private Double unmeCantidad2;
    private String grupNombre;
    private Double unmeCantidadPpal;
    private Long artiVlrMaxDiasm;
    private Double precio;
    private String novedad;
    private String tipo;
    private java.util.Date fechaDescarga;
    private String observacion;
    private Long idObservacion;
    private String desviacion;
    private Boolean estadoRecoleccion;
    private Boolean transmitido;
    private java.util.Date fechaRecoleccion;
    private Long unmeId;
    private Long tireId;
    private Long fuenId;
    private Long artiId;
    private Long cacoId;


    /**
     * Distrito de Riego  frecuencia - tipo - unidadMedidaNombre - observacion - precio
     *                    frecuencia - tipo - unmeNombrePpal - observacion - precio
     */
    private Long tipoId;
    private Long frecuenciaId;
    private String observacionProducto;
    private String frecuencia;
    private String idInformante;
    private String nombreInformate;
    private String telefonoInformante;
    private Long grinId;
    private String unidadMedidaOtroNombre;


    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }

    public Long getFrecuenciaId() {
        return frecuenciaId;
    }

    public void setFrecuenciaId(Long frecuenciaId) {
        this.futiId = frecuenciaId;
    }

    public String getObservacionProducto() {
        return observacionProducto;
    }

    public void setObservacionProducto(String observacionProducto) {
        this.observacionProducto = observacionProducto;
    }

    public String getTelefonoInformante() {
        return telefonoInformante;
    }

    public void setTelefonoInformante(String telefonoInformante) {
        this.telefonoInformante = telefonoInformante;
    }

    public String getNombreInformate() {
        return nombreInformate;
    }

    public void setNombreInformate(String nombreInformate) {
        this.nombreInformate = nombreInformate;
    }

    public String getIdInformante() {
        return idInformante;
    }

    public void setIdInformante(String idInformante) {
        this.idInformante = idInformante;
    }

    public String getUnidadMedidaOtroNombre() {
        return unidadMedidaOtroNombre;
    }

    public void setUnidadMedidaOtroNombre(String unidadMedidaOtroNombre) {
        this.unidadMedidaOtroNombre = unidadMedidaOtroNombre;
    }

    /***
     *
     */

    public RecoleccionDistrito() {
    }

    public RecoleccionDistrito(Long id) {
        this.id = id;
    }

    public RecoleccionDistrito(Long id, Long artiVlrMinDiasm, Long futiId, String novedadAnterior, Long articacoId, String subgNombre, String unmeNombre2,
                               String cacoNombre, String fuenNombre, String articacoRegicaLinea, Long tiprId, String artiNombre, Long artiVlrMaxTomas,
                               String tiprNombre, String unmeNombrePpal, Long artiVlrMinRondas, Double promAntDiario, Long artiVlrMinTomas,
                               Long artiVlrMaxRondas, java.util.Date prreFechaProgramada, String muniId, Double unmeCantidad2, String grupNombre,
                               Double unmeCantidadPpal, Long artiVlrMaxDiasm, Double precio, String novedad, String tipo, java.util.Date fechaDescarga,
                               String observacion, Long idObservacion, String desviacion, Boolean estadoRecoleccion, Boolean transmitido, java.util.Date fechaRecoleccion,
                               Long unmeId, Long tireId, Long fuenId, Long artiId, Long cacoId) {
        this.id = id;
        this.artiVlrMinDiasm = artiVlrMinDiasm;
        this.futiId = futiId;
        this.novedadAnterior = novedadAnterior;
        this.articacoId = articacoId;
        this.subgNombre = subgNombre;
        this.unmeNombre2 = unmeNombre2;
        this.cacoNombre = cacoNombre;
        this.fuenNombre = fuenNombre;
        this.articacoRegicaLinea = articacoRegicaLinea;
        this.tiprId = tiprId;
        this.artiNombre = artiNombre;
        this.artiVlrMaxTomas = artiVlrMaxTomas;
        this.tiprNombre = tiprNombre;
        this.unmeNombrePpal = unmeNombrePpal;
        this.artiVlrMinRondas = artiVlrMinRondas;
        this.promAntDiario = promAntDiario;
        this.artiVlrMinTomas = artiVlrMinTomas;
        this.artiVlrMaxRondas = artiVlrMaxRondas;
        this.prreFechaProgramada = prreFechaProgramada;
        this.muniId = muniId;
        this.unmeCantidad2 = unmeCantidad2;
        this.grupNombre = grupNombre;
        this.unmeCantidadPpal = unmeCantidadPpal;
        this.artiVlrMaxDiasm = artiVlrMaxDiasm;
        this.precio = precio;
        this.novedad = novedad;
        this.tipo = tipo;
        this.fechaDescarga = fechaDescarga;
        this.observacion = observacion;
        this.idObservacion = idObservacion;
        this.desviacion = desviacion;
        this.estadoRecoleccion = estadoRecoleccion;
        this.transmitido = transmitido;
        this.fechaRecoleccion = fechaRecoleccion;
        this.unmeId = unmeId;
        this.tireId = tireId;
        this.fuenId = fuenId;
        this.artiId = artiId;
        this.cacoId = cacoId;
    }

    public RecoleccionDistrito(Long id, Long artiVlrMinDiasm, Long futiId, String novedadAnterior, Long articacoId, String subgNombre, String unmeNombre2,
                               String cacoNombre, String fuenNombre, String articacoRegicaLinea, Long tiprId, String artiNombre, Long artiVlrMaxTomas,
                               String tiprNombre, String unmeNombrePpal, Long artiVlrMinRondas, Double promAntDiario, Long artiVlrMinTomas,
                               Long artiVlrMaxRondas, java.util.Date prreFechaProgramada, String muniId, Double unmeCantidad2, String grupNombre,
                               Double unmeCantidadPpal, Long artiVlrMaxDiasm, Double precio, String novedad, String tipo, java.util.Date fechaDescarga,
                               String observacion, Long idObservacion, String desviacion, Boolean estadoRecoleccion, Boolean transmitido, java.util.Date fechaRecoleccion,
                               Long unmeId, Long tireId, Long fuenId, Long artiId, Long cacoId, String observacionProducto, String frecuencia, String otro) {
        this.id = id;
        this.artiVlrMinDiasm = artiVlrMinDiasm;
        this.futiId = futiId;
        this.novedadAnterior = novedadAnterior;
        this.articacoId = articacoId;
        this.subgNombre = subgNombre;
        this.unmeNombre2 = unmeNombre2;
        this.cacoNombre = cacoNombre;
        this.fuenNombre = fuenNombre;
        this.articacoRegicaLinea = articacoRegicaLinea;
        this.tiprId = tiprId;
        this.artiNombre = artiNombre;
        this.artiVlrMaxTomas = artiVlrMaxTomas;
        this.tiprNombre = tiprNombre;
        this.unmeNombrePpal = unmeNombrePpal;
        this.artiVlrMinRondas = artiVlrMinRondas;
        this.promAntDiario = promAntDiario;
        this.artiVlrMinTomas = artiVlrMinTomas;
        this.artiVlrMaxRondas = artiVlrMaxRondas;
        this.prreFechaProgramada = prreFechaProgramada;
        this.muniId = muniId;
        this.unmeCantidad2 = unmeCantidad2;
        this.grupNombre = grupNombre;
        this.unmeCantidadPpal = unmeCantidadPpal;
        this.artiVlrMaxDiasm = artiVlrMaxDiasm;
        this.precio = precio;
        this.novedad = novedad;
        this.tipo = tipo;
        this.fechaDescarga = fechaDescarga;
        this.observacion = observacion;
        this.idObservacion = idObservacion;
        this.desviacion = desviacion;
        this.estadoRecoleccion = estadoRecoleccion;
        this.transmitido = transmitido;
        this.fechaRecoleccion = fechaRecoleccion;
        this.unmeId = unmeId;
        this.tireId = tireId;
        this.fuenId = fuenId;
        this.artiId = artiId;
        this.cacoId = cacoId;
        this.observacionProducto = observacionProducto;
        this.frecuencia = frecuencia;
        this.unidadMedidaOtroNombre = otro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArtiVlrMinDiasm() {
        return artiVlrMinDiasm;
    }

    public void setArtiVlrMinDiasm(Long artiVlrMinDiasm) {
        this.artiVlrMinDiasm = artiVlrMinDiasm;
    }

    public Long getFutiId() {
        return futiId;
    }

    public void setFutiId(Long futiId) {
        this.futiId = futiId;
    }

    public Long getGrinId() {
        return grinId;
    }

    public void setGrinId(Long grinId) {
        this.grinId = grinId;
    }

    public String getNovedadAnterior() {
        return novedadAnterior;
    }

    public Long getIdObservacion() {
        return idObservacion;
    }

    public void setIdObservacion(Long idObservacion) {
        this.idObservacion = idObservacion;
    }

    public void setNovedadAnterior(String novedadAnterior) {

        this.novedadAnterior = novedadAnterior;
    }

    public Long getArticacoId() {
        return articacoId;
    }

    public void setArticacoId(Long articacoId) {
        this.articacoId = articacoId;
    }

    public String getSubgNombre() {
        return subgNombre;
    }

    public void setSubgNombre(String subgNombre) {
        this.subgNombre = subgNombre;
    }

    public String getUnmeNombre2() {
        return unmeNombre2;
    }

    public void setUnmeNombre2(String unmeNombre2) {
        this.unmeNombre2 = unmeNombre2;
    }

    public String getCacoNombre() {
        return cacoNombre;
    }

    public void setCacoNombre(String cacoNombre) {
        this.cacoNombre = cacoNombre;
    }

    public String getFuenNombre() {
        return fuenNombre;
    }

    public void setFuenNombre(String fuenNombre) {
        this.fuenNombre = fuenNombre;
    }


    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getArticacoRegicaLinea() {
        return articacoRegicaLinea;
    }

    public void setArticacoRegicaLinea(String articacoRegicaLinea) {
        this.articacoRegicaLinea = articacoRegicaLinea;
    }

    public Long getTiprId() {
        return tiprId;
    }

    public void setTiprId(Long tiprId) {
        this.tiprId = tiprId;
    }

    public String getArtiNombre() {
        return artiNombre;
    }

    public void setArtiNombre(String artiNombre) {
        this.artiNombre = artiNombre;
    }

    public Long getArtiVlrMaxTomas() {
        return artiVlrMaxTomas;
    }

    public void setArtiVlrMaxTomas(Long artiVlrMaxTomas) {
        this.artiVlrMaxTomas = artiVlrMaxTomas;
    }

    public String getTiprNombre() {
        return tiprNombre;
    }

    public void setTiprNombre(String tiprNombre) {
        this.tiprNombre = tiprNombre;
    }

    public String getUnmeNombrePpal() {
        return unmeNombrePpal;
    }

    public void setUnmeNombrePpal(String unmeNombrePpal) {
        this.unmeNombrePpal = unmeNombrePpal;
    }

    public Long getArtiVlrMinRondas() {
        return artiVlrMinRondas;
    }

    public void setArtiVlrMinRondas(Long artiVlrMinRondas) {
        this.artiVlrMinRondas = artiVlrMinRondas;
    }

    public Double getPromAntDiario() {
        return promAntDiario;
    }

    public void setPromAntDiario(Double promAntDiario) {
        this.promAntDiario = promAntDiario;
    }

    public Long getArtiVlrMinTomas() {
        return artiVlrMinTomas;
    }

    public void setArtiVlrMinTomas(Long artiVlrMinTomas) {
        this.artiVlrMinTomas = artiVlrMinTomas;
    }

    public Long getArtiVlrMaxRondas() {
        return artiVlrMaxRondas;
    }

    public void setArtiVlrMaxRondas(Long artiVlrMaxRondas) {
        this.artiVlrMaxRondas = artiVlrMaxRondas;
    }

    public java.util.Date getPrreFechaProgramada() {
        return prreFechaProgramada;
    }

    public void setPrreFechaProgramada(java.util.Date prreFechaProgramada) {
        this.prreFechaProgramada = prreFechaProgramada;
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

    public String getGrupNombre() {
        return grupNombre;
    }

    public void setGrupNombre(String grupNombre) {
        this.grupNombre = grupNombre;
    }

    public Double getUnmeCantidadPpal() {
        return unmeCantidadPpal;
    }

    public void setUnmeCantidadPpal(Double unmeCantidadPpal) {
        this.unmeCantidadPpal = unmeCantidadPpal;
    }

    public Long getArtiVlrMaxDiasm() {
        return artiVlrMaxDiasm;
    }

    public void setArtiVlrMaxDiasm(Long artiVlrMaxDiasm) {
        this.artiVlrMaxDiasm = artiVlrMaxDiasm;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public java.util.Date getFechaDescarga() {
        return fechaDescarga;
    }

    public void setFechaDescarga(java.util.Date fechaDescarga) {
        this.fechaDescarga = fechaDescarga;
    }

    public String getObservacion() {
        return observacion;
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

    public Boolean getEstadoRecoleccion() {
        return estadoRecoleccion;
    }

    public void setEstadoRecoleccion(Boolean estadoRecoleccion) {
        this.estadoRecoleccion = estadoRecoleccion;
    }

    public Boolean getTransmitido() {
        return transmitido;
    }

    public void setTransmitido(Boolean transmitido) {
        this.transmitido = transmitido;
    }

    public java.util.Date getFechaRecoleccion() {
        return fechaRecoleccion;
    }

    public void setFechaRecoleccion(java.util.Date fechaRecoleccion) {
        this.fechaRecoleccion = fechaRecoleccion;
    }

    public Long getUnmeId() {
        return unmeId;
    }

    public void setUnmeId(Long unmeId) {
        this.unmeId = unmeId;
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

    public Long getArtiId() {
        return artiId;
    }

    public void setArtiId(Long artiId) {
        this.artiId = artiId;
    }

    public Long getCacoId() {
        return cacoId;
    }

    public void setCacoId(Long cacoId) {
        this.cacoId = cacoId;
    }

}
