package gov.dane.indices.model.transmision;

import java.io.Serializable;

public class EspecificacionCategoria implements Serializable{
	 private Long idTipoEspecificacion;
	    private Long categoria;
	    private String especificacion;
	    private String nombreCategoria;
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
		 * @return the categoria
		 */
		public Long getCategoria() {
			return categoria;
		}
		/**
		 * @param categoria the categoria to set
		 */
		public void setCategoria(Long categoria) {
			this.categoria = categoria;
		}
		/**
		 * @return the especificacion
		 */
		public String getEspecificacion() {
			return especificacion;
		}
		/**
		 * @param especificacion the especificacion to set
		 */
		public void setEspecificacion(String especificacion) {
			this.especificacion = especificacion;
		}
		/**
		 * @return the nombreCategoria
		 */
		public String getNombreCategoria() {
			return nombreCategoria;
		}
		/**
		 * @param nombreCategoria the nombreCategoria to set
		 */
		public void setNombreCategoria(String nombreCategoria) {
			this.nombreCategoria = nombreCategoria;
		}
	    
	    

}
