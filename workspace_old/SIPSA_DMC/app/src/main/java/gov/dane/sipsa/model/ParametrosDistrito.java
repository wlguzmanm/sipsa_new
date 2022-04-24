package gov.dane.sipsa.model;

import java.util.List;

import gov.dane.sipsa.dao.FuenteElementoRestriccion;

/**
 * Created by Marco Guzman on 03/29/19.
 */

public class ParametrosDistrito {


    public List<gov.dane.sipsa.dao.ArticuloDistrito> Articulo;
    //public List<gov.dane.sipsa.dao.CasaComercial> CasaComercial;
    public List<gov.dane.sipsa.dao.FuenteDistrito> Fuente;
    public List<gov.dane.sipsa.dao.Usuario> Usuario;
    public List<gov.dane.sipsa.dao.FuenteArticuloDistrito> FuenteArticulo;
    public List<gov.dane.sipsa.dao.ObservacionDistrito> Observacion;
    //public List<gov.dane.sipsa.dao.Grupo> Grupo;
    //public List<gov.dane.sipsa.dao.TipoRecoleccion> TipoRecoleccion;
    //public List<gov.dane.sipsa.dao.UnidadMedida> UnidadMedida;
    public List<gov.dane.sipsa.dao.RecoleccionDistrito> Principal;
    public List<gov.dane.sipsa.dao.CaracteristicaDistrito> CaracteristicaD;

    private Status status;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
