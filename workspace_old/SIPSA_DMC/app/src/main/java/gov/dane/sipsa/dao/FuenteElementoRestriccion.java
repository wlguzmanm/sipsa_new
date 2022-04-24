package gov.dane.sipsa.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "FUENTE_ELEMENTO_RESTRICCION".
 */
public class FuenteElementoRestriccion {

    private Long idFunteElemento;
    private Integer idFuente;
    private Integer idElemento;
    private String nombreElemento;

    public FuenteElementoRestriccion() {
    }

    public FuenteElementoRestriccion(Long idFunteElemento) {
        this.idFunteElemento = idFunteElemento;
    }

    public FuenteElementoRestriccion(Long idFunteElemento, Integer idFuente, Integer idElemento, String nombreElemento) {
        this.idFunteElemento = idFunteElemento;
        this.idFuente = idFuente;
        this.idElemento = idElemento;
        this.nombreElemento = nombreElemento;
    }

    public Long getIdFunteElemento() {
        return idFunteElemento;
    }

    public void setIdFunteElemento(Long idFunteElemento) {
        this.idFunteElemento = idFunteElemento;
    }

    public Integer getIdFuente() {
        return idFuente;
    }

    public void setIdFuente(Integer idFuente) {
        this.idFuente = idFuente;
    }

    public Integer getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(Integer idElemento) {
        this.idElemento = idElemento;
    }

    public String getNombreElemento() {
        return nombreElemento;
    }

    public void setNombreElemento(String nombreElemento) {
        this.nombreElemento = nombreElemento;
    }

}
