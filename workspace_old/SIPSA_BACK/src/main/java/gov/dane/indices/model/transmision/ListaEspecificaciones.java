package gov.dane.indices.model.transmision;

/*
 * Clase para almacenar las Listas de una Especificacion
 *  */
public class ListaEspecificaciones  {

	public Long idValorArgumento;
	public String categoria;
	public String abreviatura;
	public String descripcion;
	public Integer orden;
    private Long id_padre;
    private String adicional;
    
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
	public Integer getOrden() {
		return orden;
	}
	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	/**
	 * @return the id_padre
	 */
	public Long getId_padre() {
		return id_padre;
	}
	/**
	 * @param id_padre the id_padre to set
	 */
	public void setId_padre(Long id_padre) {
		this.id_padre = id_padre;
	}
	/**
	 * @return the adicional
	 */
	public String getAdicional() {
		return adicional;
	}
	/**
	 * @param adicional the adicional to set
	 */
	public void setAdicional(String adicional) {
		this.adicional = adicional;
	}
	
	
	
	
}
