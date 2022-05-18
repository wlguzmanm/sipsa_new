package co.gov.dane.sipsa.backend.model;

/**
 * Created by andreslopera on 6/17/16.
 */
public class ResumenFuente {
    private String nombreIndice;
    private Long idFuente;
    private Integer totalElementos;
    private Integer recolectados;




    public String getNombreIndice() {
        return nombreIndice;
    }

    public Long getIdFuente() {
        return idFuente;
    }

    public Integer getTotalElementos() {
        return totalElementos;
    }

    public Integer getRecolectados() {
        return recolectados;
    }


    public void setNombreIndice(String nombreIndice) {
        this.nombreIndice = nombreIndice;
    }

    public void setIdFuente(Long idFuente) {
        this.idFuente = idFuente;
    }

    public void setTotalElementos(Integer totalElementos) {
        this.totalElementos = totalElementos;
    }

    public void setRecolectados(Integer recolectados) {
        this.recolectados = recolectados;
    }
}
