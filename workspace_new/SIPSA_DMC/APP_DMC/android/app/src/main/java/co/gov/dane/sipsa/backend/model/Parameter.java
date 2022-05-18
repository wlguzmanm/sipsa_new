package co.gov.dane.sipsa.backend.model;

import java.util.List;

import co.gov.dane.sipsa.backend.dao.Ciudad;
import co.gov.dane.sipsa.backend.dao.Clase;
import co.gov.dane.sipsa.backend.dao.ElementoEspecificacion;
import co.gov.dane.sipsa.backend.dao.Fuente;
import co.gov.dane.sipsa.backend.dao.FuenteElementoRestriccion;
import co.gov.dane.sipsa.backend.dao.IndiceElemento;
import co.gov.dane.sipsa.backend.dao.Observacion;


/**
 * Created by andreslopera on 4/19/16.
 */
    public class Parameter {
        private List<Clase> clase;
        private List<Ciudad> ciudad;
        private List<ElementoEspecificacion> elementoEspecificacion;
         private List<FuenteElementoRestriccion> fuenteElementoRestriccion;
        private List<Fuente> fuente;
        private List<Observacion> observacion;
         private List<IndiceElemento> indiceElemento;

    public List<Clase> getClase() {
        return clase;
    }

    public List<Ciudad> getCiudad() {
        return ciudad;
    }


    public List<ElementoEspecificacion> getElementoEspecificacion() {
        return elementoEspecificacion;
    }


    public List<FuenteElementoRestriccion> getFuenteElementoRestriccion() {
        return fuenteElementoRestriccion;
    }


    public List<Fuente> getFuente() {
        return fuente;
    }

    public List<Observacion> getObservacion() {
        return observacion;
    }


    public List<IndiceElemento> getIndiceElemento() {
        return indiceElemento;
    }

    public void setClase(List<Clase> clase) {
        this.clase = clase;
    }

    public void setCiudad(List<Ciudad> ciudad) {
        this.ciudad = ciudad;
    }


    public void setElementoEspecificacion(List<ElementoEspecificacion> elementoEspecificacion) {
        this.elementoEspecificacion = elementoEspecificacion;
    }


    public void setFuenteElementoRestriccion(List<FuenteElementoRestriccion> fuenteElementoRestriccion) {
        this.fuenteElementoRestriccion = fuenteElementoRestriccion;
    }


    public void setFuente(List<Fuente> fuente) {
        this.fuente = fuente;
    }

    public void setObservacion(List<Observacion> observacion) {
        this.observacion = observacion;
    }

 public void setIndiceElemento(List<IndiceElemento> indiceElemento) {
        this.indiceElemento = indiceElemento;
    }
}
