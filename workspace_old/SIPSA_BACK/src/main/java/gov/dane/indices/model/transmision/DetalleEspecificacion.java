package gov.dane.indices.model.transmision;

import java.io.Serializable;
/** 
 * Clase que almacena el tipo de una especificación tales como (numerico, alfanumerico, lista seleccionable)
 * **/

public class DetalleEspecificacion implements Serializable{
	
	public Long idTipoEspecificacion;
	public Long idEspecificacionDetalle;
	public String nombreElemento;
	public String tipo; // Posibles valores  -- N numerico, T texto, L lista
	public String validacion; //  Posibles valores R rango , L lista , D doble digitación 
	public String usuarioCreacion;
	public String usuarioModificacion;
	public String lista; // Nombre de la lista seleccionable que se va a consultar en el modelo ListaEspecificaciones, trae valores si tipo == L
	public Double valorMinimo; // Valor minimo para la validación rango
	public Double valorMaximo; // Valor maximo para la validación rango
	
	
	/**
	 * @return the idTipoEspecificacion
	 */
	public Long getIdTipoEspecificacion() {
		return idTipoEspecificacion;
	}
	/**
	 * @param idTipoEspecificacion the idTipoEspecificacion to set
	 */
	public void setIdTipoEspecificacion(Long idTipoEspecificacion) {
		this.idTipoEspecificacion = idTipoEspecificacion;
	}
	/**
	 * @return the idEspecificacionDetalle
	 */
	public Long getIdEspecificacionDetalle() {
		return idEspecificacionDetalle;
	}
	/**
	 * @param idEspecificacionDetalle the idEspecificacionDetalle to set
	 */
	public void setIdEspecificacionDetalle(Long idEspecificacionDetalle) {
		this.idEspecificacionDetalle = idEspecificacionDetalle;
	}
	
	
	
	/**
	 * @return the nombreElemento
	 */
	public String getNombreElemento() {
		return nombreElemento;
	}
	/**
	 * @param nombreElemento the nombreElemento to set
	 */
	public void setNombreElemento(String nombreElemento) {
		this.nombreElemento = nombreElemento;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the validacion
	 */
	public String getValidacion() {
		return validacion;
	}
	/**
	 * @param validacion the validacion to set
	 */
	public void setValidacion(String validacion) {
		this.validacion = validacion;
	}
	/**
	 * @return the usuarioCreacion
	 */
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}
	/**
	 * @param usuarioCreacion the usuarioCreacion to set
	 */
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	/**
	 * @return the usuarioModificacion
	 */
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}
	/**
	 * @param usuarioModificacion the usuarioModificacion to set
	 */
	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
	/**
	 * @return the lista
	 */
	public String getLista() {
		return lista;
	}
	/**
	 * @param lista the lista to set
	 */
	public void setLista(String lista) {
		this.lista = lista;
	}
	/**
	 * @return the valorMinimo
	 */
	public Double getValorMinimo() {
		return valorMinimo;
	}
	/**
	 * @param valorMinimo the valorMinimo to set
	 */
	public void setValorMinimo(Double valorMinimo) {
		this.valorMinimo = valorMinimo;
	}
	/**
	 * @return the valorMaximo
	 */
	public Double getValorMaximo() {
		return valorMaximo;
	}
	/**
	 * @param valorMaximo the valorMaximo to set
	 */
	public void setValorMaximo(Double valorMaximo) {
		this.valorMaximo = valorMaximo;
	}
	
	
}
