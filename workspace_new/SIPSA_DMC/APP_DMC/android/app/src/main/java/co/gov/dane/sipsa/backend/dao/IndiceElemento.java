package co.gov.dane.sipsa.backend.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "INDICE_ELEMENTO".
 */
public class IndiceElemento {

    private Long id;
    private long idIndice;
    private long idElemento;

    public IndiceElemento() {
    }

    public IndiceElemento(Long id) {
        this.id = id;
    }

    public IndiceElemento(Long id, long idIndice, long idElemento) {
        this.id = id;
        this.idIndice = idIndice;
        this.idElemento = idElemento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdIndice() {
        return idIndice;
    }

    public void setIdIndice(long idIndice) {
        this.idIndice = idIndice;
    }

    public long getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(long idElemento) {
        this.idElemento = idElemento;
    }

}