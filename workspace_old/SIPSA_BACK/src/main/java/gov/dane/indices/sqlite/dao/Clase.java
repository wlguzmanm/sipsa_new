package gov.dane.indices.sqlite.dao;


public class Clase {

    private String id;
    private String descripcion;

    public Clase() {
    }

    public Clase(String id) {
        this.id = id;
    }

    public Clase(String id, String descripcion) {
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
