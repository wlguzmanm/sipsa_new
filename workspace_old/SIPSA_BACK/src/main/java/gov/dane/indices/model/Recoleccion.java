package gov.dane.indices.model;

import java.io.Serializable;

public class Recoleccion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Long idElemento;
	public Long idFuente;
	public Long idFuenteElemento;
	public Long idObservacion;
	public Long idPeriodoRecoleccion;	
	public Double cantidadBase;
	public Double cantidadHistoricaCOP;
	public Double cantidadHistorico;
	public Double cantidadMaxima;
	public Double cantidadMinima;
	public Double cantidadRecoleccion;
	public Double cantidadRECxREC;
	public Long ctrlCaptura;
	public Long fuenteComplementaria;
	public Long fuenteComplementariaANT;
	public Long fuenteComplementariaCOP;
	public Double precioBase;
	public Double precioBaseAntCOP;
	public Double precioBaseAnterior;
	public Double precioHistorico;
	public Double precioHistoricoCOP;
	public Double precioRecoleccion;
	public Double precioRECxREC;
	public Double valorMaximo;
	public Double valorMinimo;
	public Double variacion;	
	public String alertaObservacion;
	public String codigosTematicos;
	public String direccion;
	public String estadoFuente;
	public String estadoRecoleccion;
	public String fechaCreacion;
	public String fechaRecolectar;
	public String fechaUltimaModificacion;
	public String mensualObservado;
	public String nombreElemento;
	public String nombrefuente;
	public String nombreFuenteComplementaria;
	public String nombreTipo;
	public String novedad;
	public String novedadAnterior;
	public String novedadxREC;
	public String observacionAnal;
	public String observacionAnt;
	public String observacionNueva;
	public String perfil;
	public String tipo;
	public String tipoFuente;
	public String tipoRecoleccion;
	public String unidadBase;
	public String unidadMedidaBase;

}
