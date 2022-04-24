package gov.dane.indices.model.transmision;

import java.io.Serializable;

public class EstadoFuenteInfo implements Serializable{
	public Long idValorArgumento;
	public Long idArgumento;
	public String id_padre;
	public String categoria;
	public String abreviatura;
	public String descripcion;
	public Long orden;
	/**
	 * @return the idValorArgumento
	 */
	public Long getIdValorArgumento() {
		return idValorArgumento;
	}
	/**
	 * @param idValorArgumento the idValorArgumento to set
	 */
	public void setIdValorArgumento(Long idValorArgumento) {
		this.idValorArgumento = idValorArgumento;
	}
	/**
	 * @return the idArgumento
	 */
	public Long getIdArgumento() {
		return idArgumento;
	}
	/**
	 * @param idArgumento the idArgumento to set
	 */
	public void setIdArgumento(Long idArgumento) {
		this.idArgumento = idArgumento;
	}
	/**
	 * @return the id_padre
	 */
	public String getId_padre() {
		return id_padre;
	}
	/**
	 * @param id_padre the id_padre to set
	 */
	public void setId_padre(String id_padre) {
		this.id_padre = id_padre;
	}
	/**
	 * @return the categoria
	 */
	public String getCategoria() {
		return categoria;
	}
	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	/**
	 * @return the abreviatura
	 */
	public String getAbreviatura() {
		return abreviatura;
	}
	/**
	 * @param abreviatura the abreviatura to set
	 */
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the orden
	 */
	public Long getOrden() {
		return orden;
	}
	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Long orden) {
		this.orden = orden;
	}

	
	



}
