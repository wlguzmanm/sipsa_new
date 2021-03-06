package co.gov.dane.sipsa.backend.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "USUARIO".
 */
public class Usuario {

    private Long uspeID;
    private Long usuaId;
    private Long perfID;
    private String usuario;
    private String clave;
    private String nombrePersona;
    private String nombrePerfil;
    private String uspeUsuarioModificacion;
    private java.util.Date uspeFechaModificacion;
    private String uspeUsuarioCreacion;
    private java.util.Date uspeFechaCreacion;
    private java.util.Date uspeFechaDesde;
    private java.util.Date uspeFechaHasta;

    public Usuario() {
    }

    public Usuario(Long uspeID) {
        this.uspeID = uspeID;
    }

    public Usuario(Long uspeID, Long usuaId, Long perfID, String usuario, String clave, String nombrePersona, String nombrePerfil, String uspeUsuarioModificacion, java.util.Date uspeFechaModificacion, String uspeUsuarioCreacion, java.util.Date uspeFechaCreacion, java.util.Date uspeFechaDesde, java.util.Date uspeFechaHasta) {
        this.uspeID = uspeID;
        this.usuaId = usuaId;
        this.perfID = perfID;
        this.usuario = usuario;
        this.clave = clave;
        this.nombrePersona = nombrePersona;
        this.nombrePerfil = nombrePerfil;
        this.uspeUsuarioModificacion = uspeUsuarioModificacion;
        this.uspeFechaModificacion = uspeFechaModificacion;
        this.uspeUsuarioCreacion = uspeUsuarioCreacion;
        this.uspeFechaCreacion = uspeFechaCreacion;
        this.uspeFechaDesde = uspeFechaDesde;
        this.uspeFechaHasta = uspeFechaHasta;
    }

    public Long getUspeID() {
        return uspeID;
    }

    public void setUspeID(Long uspeID) {
        this.uspeID = uspeID;
    }

    public Long getUsuaId() {
        return usuaId;
    }

    public void setUsuaId(Long usuaId) {
        this.usuaId = usuaId;
    }

    public Long getPerfID() {
        return perfID;
    }

    public void setPerfID(Long perfID) {
        this.perfID = perfID;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public String getUspeUsuarioModificacion() {
        return uspeUsuarioModificacion;
    }

    public void setUspeUsuarioModificacion(String uspeUsuarioModificacion) {
        this.uspeUsuarioModificacion = uspeUsuarioModificacion;
    }

    public java.util.Date getUspeFechaModificacion() {
        return uspeFechaModificacion;
    }

    public void setUspeFechaModificacion(java.util.Date uspeFechaModificacion) {
        this.uspeFechaModificacion = uspeFechaModificacion;
    }

    public String getUspeUsuarioCreacion() {
        return uspeUsuarioCreacion;
    }

    public void setUspeUsuarioCreacion(String uspeUsuarioCreacion) {
        this.uspeUsuarioCreacion = uspeUsuarioCreacion;
    }

    public java.util.Date getUspeFechaCreacion() {
        return uspeFechaCreacion;
    }

    public void setUspeFechaCreacion(java.util.Date uspeFechaCreacion) {
        this.uspeFechaCreacion = uspeFechaCreacion;
    }

    public java.util.Date getUspeFechaDesde() {
        return uspeFechaDesde;
    }

    public void setUspeFechaDesde(java.util.Date uspeFechaDesde) {
        this.uspeFechaDesde = uspeFechaDesde;
    }

    public java.util.Date getUspeFechaHasta() {
        return uspeFechaHasta;
    }

    public void setUspeFechaHasta(java.util.Date uspeFechaHasta) {
        this.uspeFechaHasta = uspeFechaHasta;
    }

}
