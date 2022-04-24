package gov.dane.sipsa.model;

public class Presentacion {

    private Long tiprId;
    private String tiprNombre;

    public Long getTiprId() {
        return tiprId;
    }

    public void setTiprId(Long tiprId) {
        this.tiprId = tiprId;
    }

    public String getTiprNombre() {
        return tiprNombre;
    }

    public void setTiprNombre(String tiprNombre) {
        this.tiprNombre = tiprNombre;
    }

    @Override
    public String toString() {
        return tiprNombre;
    }
}
