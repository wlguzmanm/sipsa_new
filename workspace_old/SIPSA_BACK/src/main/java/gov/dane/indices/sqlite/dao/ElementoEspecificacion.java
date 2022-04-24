package gov.dane.indices.sqlite.dao;




public class ElementoEspecificacion {

    
    private Long idElementoEspecificacion;
    private Long idTipoEspecificacion;
    private Integer idArgumento;
    private Integer orden;
    private String nombreElemento;
    private long idElemento;
    public ElementoEspecificacion() {
    }

    public ElementoEspecificacion(Long idElementoEspecificacion) {
        this.idElementoEspecificacion = idElementoEspecificacion;
    }

    public ElementoEspecificacion(Long idElementoEspecificacion, Long idTipoEspecificacion, Integer idArgumento, Integer orden, String nombreElemento, long idElemento) {
        this.idElementoEspecificacion = idElementoEspecificacion;
        this.idTipoEspecificacion = idTipoEspecificacion;
        this.idArgumento = idArgumento;
        this.orden = orden;
        this.nombreElemento = nombreElemento;
        this.idElemento = idElemento;
    }

    public Long getIdElementoEspecificacion() {
        return idElementoEspecificacion;
    }

    public void setIdElementoEspecificacion(Long idElementoEspecificacion) {
        this.idElementoEspecificacion = idElementoEspecificacion;
    }

    public Long getIdTipoEspecificacion() {
        return idTipoEspecificacion;
    }

    public void setIdTipoEspecificacion(Long idTipoEspecificacion) {
        this.idTipoEspecificacion = idTipoEspecificacion;
    }

    public Integer getIdArgumento() {
        return idArgumento;
    }

    public void setIdArgumento(Integer idArgumento) {
        this.idArgumento = idArgumento;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getNombreElemento() {
        return nombreElemento;
    }

    public void setNombreElemento(String nombreElemento) {
        this.nombreElemento = nombreElemento;
    }

    public long getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(long idElemento) {
        this.idElemento = idElemento;
    }
}
