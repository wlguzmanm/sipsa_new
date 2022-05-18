package co.gov.dane.sipsa.backend.model;

/**
 * Created by andreslopera on 4/12/16.
 */
public class Zona implements Comparable<Zona> {

    private Integer idZona;
    private String nombreZona;

    public Integer getId() {
        return idZona;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setId(Integer idZona) {
        this.idZona = idZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    @Override
    public String toString() {
        return nombreZona;
    }


    public int compareTo(Zona other) {
        return nombreZona.compareTo(other.nombreZona);
    }
}
