package co.gov.dane.sipsa.backend.model;

import java.util.List;

import co.gov.dane.sipsa.backend.dao.FuenteElementoRestriccion;

/**
 * Created by andreslopera on 4/19/16.
 */

public class ParametrosInsumos01 {


    public List<co.gov.dane.sipsa.backend.dao.ArtiCaraValoresI01> ArtiCaraValoresI01;
    public List<co.gov.dane.sipsa.backend.dao.ArticuloI01> ArticuloI01;
    public List<co.gov.dane.sipsa.backend.dao.CaracteristicaI01> CaracteristicaI01;
    public List<co.gov.dane.sipsa.backend.dao.FuenteTireI01> FuenteTireI01;
    public List<co.gov.dane.sipsa.backend.dao.InformadorI01> InformadorI01;
    public List<co.gov.dane.sipsa.backend.dao.ObservacionI01> ObservacionI01;
    public List<co.gov.dane.sipsa.backend.dao.PrincipalI01> PrincipalI01;
    public List<co.gov.dane.sipsa.backend.dao.Usuario> Usuario;
    public List<co.gov.dane.sipsa.backend.dao.ValcarapermitidosI01> ValcarapermitidosI01;

    public List<FuenteElementoRestriccion> FuenteElementoRestriccion;


    private Status status;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
