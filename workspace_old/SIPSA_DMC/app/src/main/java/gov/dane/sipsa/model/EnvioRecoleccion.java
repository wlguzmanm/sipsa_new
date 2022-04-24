package gov.dane.sipsa.model;

import java.util.List;

import gov.dane.sipsa.dao.Fuente;
import gov.dane.sipsa.dao.FuenteArticulo;
import gov.dane.sipsa.dao.Recoleccion;


/**
 * Created by andreslopera on 4/19/16.
 */

public class EnvioRecoleccion {

    public List<FuenteArticulo> Fuente;


    public List<Recoleccion> Recoleccion;

    public String idPersona;

    public String getIdPersona() {
        return idPersona;
    }

    public List<gov.dane.sipsa.dao.FuenteArticulo> getFuente() {
        return Fuente;
    }


    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public void setFuente(List<gov.dane.sipsa.dao.FuenteArticulo> fuente) {
        Fuente = fuente;
    }


    public List<gov.dane.sipsa.dao.Recoleccion> getRecoleccion() {
        return Recoleccion;
    }

    public void setRecoleccion(List<gov.dane.sipsa.dao.Recoleccion> recoleccion) {
        Recoleccion = recoleccion;
    }
}
