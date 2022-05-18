package co.gov.dane.sipsa.backend.model;

import java.util.List;

/**
 * Created by Marco Guzman on 03/29/19.
 */

public class ParametrosDistrito {


    public List<co.gov.dane.sipsa.backend.dao.ArticuloDistrito> Articulo;
    //public List<co.gov.dane.sipsa.backend.dao.CasaComercial> CasaComercial;
    public List<co.gov.dane.sipsa.backend.dao.FuenteDistrito> Fuente;
    public List<co.gov.dane.sipsa.backend.dao.Usuario> Usuario;
    public List<co.gov.dane.sipsa.backend.dao.FuenteArticuloDistrito> FuenteArticulo;
    public List<co.gov.dane.sipsa.backend.dao.ObservacionDistrito> Observacion;
    //public List<co.gov.dane.sipsa.backend.dao.Grupo> Grupo;
    //public List<co.gov.dane.sipsa.backend.dao.TipoRecoleccion> TipoRecoleccion;
    //public List<co.gov.dane.sipsa.backend.dao.UnidadMedida> UnidadMedida;
    public List<co.gov.dane.sipsa.backend.dao.RecoleccionDistrito> Principal;
    public List<co.gov.dane.sipsa.backend.dao.CaracteristicaDistrito> CaracteristicaD;

    private Status status;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
