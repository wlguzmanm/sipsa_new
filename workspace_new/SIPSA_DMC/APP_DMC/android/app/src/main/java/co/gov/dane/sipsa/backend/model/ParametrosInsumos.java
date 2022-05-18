package co.gov.dane.sipsa.backend.model;

import java.util.List;

import co.gov.dane.sipsa.backend.dao.FuenteElementoRestriccion;

/**
 * Created by andreslopera on 4/19/16.
 */

public class ParametrosInsumos {

    public List<co.gov.dane.sipsa.backend.dao.Articulo> Articulo;
    public List<co.gov.dane.sipsa.backend.dao.CasaComercial> CasaComercial;
    public List<co.gov.dane.sipsa.backend.dao.Fuente> Fuente;
    public List<co.gov.dane.sipsa.backend.dao.Usuario> Usuario;
    public List<co.gov.dane.sipsa.backend.dao.FuenteArticulo> FuenteArticulo;
    public List<co.gov.dane.sipsa.backend.dao.Observacion> Observacion;
    public List<co.gov.dane.sipsa.backend.dao.Grupo> Grupo;
    public List<co.gov.dane.sipsa.backend.dao.TipoRecoleccion> TipoRecoleccion;
    public List<co.gov.dane.sipsa.backend.dao.UnidadMedida> UnidadMedida;
    public List<co.gov.dane.sipsa.backend.dao.Recoleccion> Principal;



    public List<FuenteElementoRestriccion> FuenteElementoRestriccion;

    private Status status;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
