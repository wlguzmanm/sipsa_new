package gov.dane.indices.sqlite.dao;


public class Observacion  {

    private Long idObservacion;
    private String descripcion;
    private String novedad;
    private Integer tendencia;
    private Integer idFuente;
    private java.util.Date fechaCreacion;
    private Integer estado;
    private java.util.Date fechaModificacion;
    private String tipo;
    private Long ciudIdCiudad;

    public Observacion() {
    }

    public Observacion(Long idObservacion) {
        this.idObservacion = idObservacion;
    }

    public Observacion(Long idObservacion, String descripcion, String novedad, Integer tendencia, Integer idFuente, java.util.Date fechaCreacion, Integer estado, java.util.Date fechaModificacion, String tipo, Long ciudIdCiudad) {
        this.idObservacion = idObservacion;
        this.descripcion = descripcion;
        this.novedad = novedad;
        this.tendencia = tendencia;
        this.idFuente = idFuente;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.fechaModificacion = fechaModificacion;
        this.tipo = tipo;
        this.ciudIdCiudad = ciudIdCiudad;
    }

    public Long getIdObservacion() {
        return idObservacion;
    }

    public void setIdObservacion(Long idObservacion) {
        this.idObservacion = idObservacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }

    public Integer getTendencia() {
        return tendencia;
    }

    public void setTendencia(Integer tendencia) {
        this.tendencia = tendencia;
    }

    public Integer getIdFuente() {
        return idFuente;
    }

    public void setIdFuente(Integer idFuente) {
        this.idFuente = idFuente;
    }

    public java.util.Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(java.util.Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public java.util.Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(java.util.Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getCiudIdCiudad() {
        return ciudIdCiudad;
    }

    public void setCiudIdCiudad(Long ciudIdCiudad) {
        this.ciudIdCiudad = ciudIdCiudad;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
