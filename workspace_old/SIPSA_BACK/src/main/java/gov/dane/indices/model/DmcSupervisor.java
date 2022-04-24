package gov.dane.indices.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DmcSupervisor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<Periodo> Periodo;
	public ArrayList<Recoleccion> Recoleccion;
	public ArrayList<ValorEspecificacion> ValorEspecificacion;
	public ArrayList<FuenteCom> FuenteCom;
	public ArrayList<Fuente> Fuente;	
	public ArrayList<EstadoFuente> EstadoFuente;
	public ArrayList<Usuario> Usuario;
	
}
