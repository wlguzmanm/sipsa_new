package gov.dane.indices.sqlite.dao;


public class PuntoVenta{

    private String id;
    private String descripcion;

    public PuntoVenta() {
    }

    public PuntoVenta(String id) {
        this.id = id;
    }

    public PuntoVenta(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
