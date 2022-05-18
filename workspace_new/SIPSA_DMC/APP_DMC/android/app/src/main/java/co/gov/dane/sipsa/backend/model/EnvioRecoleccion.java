package co.gov.dane.sipsa.backend.model;

import java.util.List;

import co.gov.dane.sipsa.backend.dao.FuenteArticulo;
import co.gov.dane.sipsa.backend.dao.Recoleccion;


public class EnvioRecoleccion {

    public List<FuenteArticulo> Fuente;


    public List<Recoleccion> Recoleccion;

    public String idPersona;

    public String getIdPersona() {
        return idPersona;
    }

    public List<co.gov.dane.sipsa.backend.dao.FuenteArticulo> getFuente() {
        return Fuente;
    }


    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public void setFuente(List<co.gov.dane.sipsa.backend.dao.FuenteArticulo> fuente) {
        Fuente = fuente;
    }


    public List<co.gov.dane.sipsa.backend.dao.Recoleccion> getRecoleccion() {
        return Recoleccion;
    }

    public void setRecoleccion(List<co.gov.dane.sipsa.backend.dao.Recoleccion> recoleccion) {
        Recoleccion = recoleccion;
    }
}
