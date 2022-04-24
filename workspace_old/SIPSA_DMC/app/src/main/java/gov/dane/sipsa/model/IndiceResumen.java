package gov.dane.sipsa.model;

/**
 * Created by andreslopera on 6/17/16.
 */
public class IndiceResumen {
    private Integer idIndice;
    private String nombre;
    private Integer completas;
    private Integer incompletas;
    private Integer pendientes;
    private Integer total;

    public Integer getIdIndice() {
        return idIndice;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCompletas() {
        return completas;
    }

    public Integer getIncompletas() {
        return incompletas;
    }

    public Integer getPendientes() {
        return pendientes;
    }

    public Integer getTotal() {
        return total;
    }

    public void setIdIndice(Integer idIndice) {
        this.idIndice = idIndice;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCompletas(Integer completas) {
        this.completas = completas;
    }

    public void setIncompletas(Integer incompletas) {
        this.incompletas = incompletas;
    }

    public void setPendientes(Integer pendientes) {
        this.pendientes = pendientes;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
