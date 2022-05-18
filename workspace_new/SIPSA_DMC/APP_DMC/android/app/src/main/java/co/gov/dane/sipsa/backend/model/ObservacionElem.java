package co.gov.dane.sipsa.backend.model;

import java.io.Serializable;

/**
 * Created by hdblanco on 21/07/17.
 */

public class ObservacionElem  implements Serializable {

    private Long obseId;
    private String obseTipo;
    private String novedad;
    private String obseDescripcion;
    private Boolean isChecked;

    public Boolean isChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public Long getObseId() {
        return obseId;
    }

    public void setObseId(Long obseId) {
        this.obseId = obseId;
    }

    public String getObseTipo() {
        return obseTipo;
    }

    public void setObseTipo(String obseTipo) {
        this.obseTipo = obseTipo;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }

    public String getObseDescripcion() {
        return obseDescripcion;
    }

    public void setObseDescripcion(String obseDescripcion) {
        this.obseDescripcion = obseDescripcion;
    }
}

