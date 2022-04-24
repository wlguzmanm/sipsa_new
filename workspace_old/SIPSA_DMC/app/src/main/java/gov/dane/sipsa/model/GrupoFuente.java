package gov.dane.sipsa.model;

/**
 * Created by andreslopera on 4/22/16.
 */
public class GrupoFuente {
    Long id;
    String nombre;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
