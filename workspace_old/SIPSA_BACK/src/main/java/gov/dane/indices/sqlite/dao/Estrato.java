package gov.dane.indices.sqlite.dao;


public class Estrato {

    private Integer grupoFuente;
    private String estratoAsignado;
    private Long id;
    private String tipoFuente;
    private String grupo;

    public Estrato() {
    }

    public Estrato(Long id) {
        this.id = id;
    }

    public Estrato(Integer grupoFuente, String estratoAsignado, Long id, String tipoFuente, String grupo) {
        this.grupoFuente = grupoFuente;
        this.estratoAsignado = estratoAsignado;
        this.id = id;
        this.tipoFuente = tipoFuente;
        this.grupo = grupo;
    }

    public Integer getGrupoFuente() {
        return grupoFuente;
    }

    public void setGrupoFuente(Integer grupoFuente) {
        this.grupoFuente = grupoFuente;
    }

    public String getEstratoAsignado() {
        return estratoAsignado;
    }

    public void setEstratoAsignado(String estratoAsignado) {
        this.estratoAsignado = estratoAsignado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoFuente() {
        return tipoFuente;
    }

    public void setTipoFuente(String tipoFuente) {
        this.tipoFuente = tipoFuente;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

}
