package gov.dane.indices.sqlite.dao;


public class Usuario {

    private Long id;
    private String usuario;
    private String nombreUsuario;
    private Integer isUsuario;
    private Integer perfil;
    private String nombrePerfil;
    private Integer ciudad;
    private Integer zona;
    private String fechaDesde;
    private String fechaHasta;
    private Integer periodoActivo;
    private Integer estado;
    private String claveUsuario;
    private Long idPersona;

    public Usuario() {
    }

    public Usuario(Long id) {
        this.id = id;
    }

    public Usuario(Long id, String usuario, String nombreUsuario, Integer isUsuario, Integer perfil, String nombrePerfil, Integer ciudad, Integer zona, String fechaDesde, String fechaHasta, Integer periodoActivo, Integer estado, String claveUsuario, Long idPersona) {
        this.id = id;
        this.usuario = usuario;
        this.nombreUsuario = nombreUsuario;
        this.isUsuario = isUsuario;
        this.perfil = perfil;
        this.nombrePerfil = nombrePerfil;
        this.ciudad = ciudad;
        this.zona = zona;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.periodoActivo = periodoActivo;
        this.estado = estado;
        this.claveUsuario = claveUsuario;
        this.idPersona = idPersona;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getIsUsuario() {
        return isUsuario;
    }

    public void setIsUsuario(Integer isUsuario) {
        this.isUsuario = isUsuario;
    }

    public Integer getPerfil() {
        return perfil;
    }

    public void setPerfil(Integer perfil) {
        this.perfil = perfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public Integer getCiudad() {
        return ciudad;
    }

    public void setCiudad(Integer ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getZona() {
        return zona;
    }

    public void setZona(Integer zona) {
        this.zona = zona;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Integer getPeriodoActivo() {
        return periodoActivo;
    }

    public void setPeriodoActivo(Integer periodoActivo) {
        this.periodoActivo = periodoActivo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

}
