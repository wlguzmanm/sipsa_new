package gov.dane.sipsa.model;

import java.util.List;

import gov.dane.sipsa.dao.FuenteElementoRestriccion;

/**
 * Created by andreslopera on 4/19/16.
 */

public class ParametrosInsumos01 {


    public List<gov.dane.sipsa.dao.ArtiCaraValoresI01> ArtiCaraValoresI01;
    public List<gov.dane.sipsa.dao.ArticuloI01> ArticuloI01;
    public List<gov.dane.sipsa.dao.CaracteristicaI01> CaracteristicaI01;
    public List<gov.dane.sipsa.dao.FuenteTireI01> FuenteTireI01;
    public List<gov.dane.sipsa.dao.InformadorI01> InformadorI01;
    public List<gov.dane.sipsa.dao.ObservacionI01> ObservacionI01;
    public List<gov.dane.sipsa.dao.PrincipalI01> PrincipalI01;
    public List<gov.dane.sipsa.dao.Usuario> Usuario;
    public List<gov.dane.sipsa.dao.ValcarapermitidosI01> ValcarapermitidosI01;

    public List<FuenteElementoRestriccion> FuenteElementoRestriccion;


    private Status status;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
