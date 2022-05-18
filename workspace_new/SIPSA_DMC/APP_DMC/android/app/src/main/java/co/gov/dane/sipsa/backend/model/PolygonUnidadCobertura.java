package co.gov.dane.sipsa.backend.model;

import org.osmdroid.util.GeoPoint;

public class PolygonUnidadCobertura {

    //Poligono
    private PolygonTap poligono;
    //Bounds
    private double latitudMin;
    private double latitudMax;
    private double longitudMin;
    private double longitudMax;
    //"Centro"
    private GeoPoint insidePoint;
    //Codigo de unidad
    private String codigoUnidadCobertura;


    public PolygonTap getPoligono() {
        return poligono;
    }

    public void setPoligono(PolygonTap poligono) {
        this.poligono = poligono;
    }

    public double getLatitudMin() {
        return latitudMin;
    }

    public void setLatitudMin(double latitudMin) {
        this.latitudMin = latitudMin;
    }

    public double getLatitudMax() {
        return latitudMax;
    }

    public void setLatitudMax(double latitudMax) {
        this.latitudMax = latitudMax;
    }

    public double getLongitudMin() {
        return longitudMin;
    }

    public void setLongitudMin(double longitudMin) {
        this.longitudMin = longitudMin;
    }

    public double getLongitudMax() {
        return longitudMax;
    }

    public void setLongitudMax(double longitudMax) {
        this.longitudMax = longitudMax;
    }



    public String getCodigoUnidadCobertura() {
        return codigoUnidadCobertura;
    }

    public void setCodigoUnidadCobertura(String codigoUnidadCobertura) {
        this.codigoUnidadCobertura = codigoUnidadCobertura;
    }

    public GeoPoint getInsidePoint() {
        return insidePoint;
    }

    public void setInsidePoint(GeoPoint insidePoint) {
        this.insidePoint = insidePoint;
    }
}
