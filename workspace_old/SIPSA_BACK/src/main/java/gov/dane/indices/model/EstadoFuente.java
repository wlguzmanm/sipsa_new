package gov.dane.indices.model;

import java.io.Serializable;
import java.util.Date;

public class EstadoFuente implements Serializable {
	private Long idFuente;
	private Long idEstadoFuente;
	private Long visita;
	private String estado;
	private String observacion;
	private Date fechaVisita;
	/**
	 * @return the idFuente
	 */
	public Long getIdFuente() {
		return idFuente;
	}
	/**
	 * @param idFuente the idFuente to set
	 */
	public void setIdFuente(Long idFuente) {
		this.idFuente = idFuente;
	}
	/**
	 * @return the idEstadoFuente
	 */
	public Long getIdEstadoFuente() {
		return idEstadoFuente;
	}
	/**
	 * @param idEstadoFuente the idEstadoFuente to set
	 */
	public void setIdEstadoFuente(Long idEstadoFuente) {
		this.idEstadoFuente = idEstadoFuente;
	}
	/**
	 * @return the visita
	 */
	public Long getVisita() {
		return visita;
	}
	/**
	 * @param visita the visita to set
	 */
	public void setVisita(Long visita) {
		this.visita = visita;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}
	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	/**
	 * @return the fechaVisita
	 */
	public Date getFechaVisita() {
		return fechaVisita;
	}
	/**
	 * @param fechaVisita the fechaVisita to set
	 */
	public void setFechaVisita(Date fechaVisita) {
		this.fechaVisita = fechaVisita;
	}
	
	

}
