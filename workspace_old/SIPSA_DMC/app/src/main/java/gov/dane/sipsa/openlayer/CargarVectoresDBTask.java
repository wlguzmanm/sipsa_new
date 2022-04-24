package gov.dane.sipsa.openlayer;

import android.content.Context;

import org.osmdroid.bonuspack.overlays.Polygon;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

import gov.dane.sipsa.model.PolygonUnidadCobertura;


public class CargarVectoresDBTask  {

	private Context context_;
	private List<Polygon> polygonLista = new ArrayList<Polygon>();
	private List<PolygonUnidadCobertura> polygonUnidadCoberturaList = new ArrayList<PolygonUnidadCobertura>();
	private responseCargaVectores callback;
	private List<OverlayItem> lsOverlays;
	private String AO, UC;

	public List<Polygon> getPolygonLista() {
		return polygonLista;
	}

	public void setPolygonLista(List<Polygon> polygonLista) {
		this.polygonLista = polygonLista;
	}

	public List<PolygonUnidadCobertura> getPolygonUnidadCoberturaList() {
		return polygonUnidadCoberturaList;
	}

	public void setPolygonUnidadCoberturaList(List<PolygonUnidadCobertura> polygonUnidadCoberturaList) {
		this.polygonUnidadCoberturaList = polygonUnidadCoberturaList;
	}
}
