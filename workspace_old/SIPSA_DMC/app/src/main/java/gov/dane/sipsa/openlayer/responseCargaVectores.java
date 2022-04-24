package gov.dane.sipsa.openlayer;

import java.util.List;

import gov.dane.sipsa.model.PolygonUnidadCobertura;

/**
 * Created by jperezp on 02/03/2016.
 */
public interface responseCargaVectores {
    public void cargaFinalizada(List<PolygonUnidadCobertura> polygonUnidadCoberturaListCargado);
}
