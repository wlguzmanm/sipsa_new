package co.gov.dane.sipsa.backend.model;

import java.util.List;

import co.gov.dane.sipsa.backend.dao.EnvioArtiCaraValoresI01;
import co.gov.dane.sipsa.backend.dao.PrincipalI01;
import co.gov.dane.sipsa.backend.dao.RecoleccionI01;


/**
 * Created by andreslopera on 4/19/16.
 */

public class EnvioRecoleccion01 {

    public List<PrincipalI01> Principal;
    public List<EnvioArtiCaraValoresI01> ArtiCaraValores;

    public List<RecoleccionI01> Recoleccion;

    public String idPersona;

    public List<PrincipalI01> getPrincipal() {
        return Principal;
    }

    public void setPrincipal(List<PrincipalI01> principal) {
        Principal = principal;
    }

    public List<EnvioArtiCaraValoresI01> getArtiCaraValores() {
        return ArtiCaraValores;
    }

    public void setArtiCaraValores(List<EnvioArtiCaraValoresI01> artiCaraValores) {
        ArtiCaraValores = artiCaraValores;
    }

    public List<RecoleccionI01> getRecoleccion() {
        return Recoleccion;
    }

    public void setRecoleccion(List<RecoleccionI01> recoleccion) {
        Recoleccion = recoleccion;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }
}
