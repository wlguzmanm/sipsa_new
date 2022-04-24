package gov.dane.indices.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DmcRecoleccion implements Serializable
{
	
	public ArrayList<Periodo> Periodo;
	public ArrayList<Recoleccion> Recoleccion;
	public ArrayList<ValorEspecificacion> ValorEspecificacion;
	public ArrayList<Fuente> Fuente;
	public ArrayList<Usuario> Usuario;
	public ArrayList<FuenteElementoRestriccion> FuenteElementoRestriccion;
	public ArrayList<FuenteCom> FuenteCom;
	public ArrayList<EstadoFuente> EstadoFuente;

}
