package gov.dane.indices.model.transmision;

import java.io.Serializable;

public class CategoriaElemento implements Serializable{

	private Long idCategoriaElemento;
	private String nombre;
	private Long idCanasta;
	/**
	 * @return the idCategoriaElemento
	 */
	public Long getIdCategoriaElemento() {
		return idCategoriaElemento;
	}
	/**
	 * @param idCategoriaElemento the idCategoriaElemento to set
	 */
	public void setIdCategoriaElemento(Long idCategoriaElemento) {
		this.idCategoriaElemento = idCategoriaElemento;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the idCanasta
	 */
	public Long getIdCanasta() {
		return idCanasta;
	}
	/**
	 * @param idCanasta the idCanasta to set
	 */
	public void setIdCanasta(Long idCanasta) {
		this.idCanasta = idCanasta;
	}


}
