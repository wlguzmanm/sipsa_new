package gov.dane.sipsa.model;

import java.util.List;

import gov.dane.sipsa.dao.FuenteElementoRestriccion;

/**
 * Created by andreslopera on 4/19/16.
 */

public class ParametrosInsumos {

    public List<gov.dane.sipsa.dao.Articulo> Articulo;
    public List<gov.dane.sipsa.dao.CasaComercial> CasaComercial;
    public List<gov.dane.sipsa.dao.Fuente> Fuente;
    public List<gov.dane.sipsa.dao.Usuario> Usuario;
    public List<gov.dane.sipsa.dao.FuenteArticulo> FuenteArticulo;
    public List<gov.dane.sipsa.dao.Observacion> Observacion;
    public List<gov.dane.sipsa.dao.Grupo> Grupo;
    public List<gov.dane.sipsa.dao.TipoRecoleccion> TipoRecoleccion;
    public List<gov.dane.sipsa.dao.UnidadMedida> UnidadMedida;
    public List<gov.dane.sipsa.dao.Recoleccion> Principal;



    public List<FuenteElementoRestriccion> FuenteElementoRestriccion;

    private Status status;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
