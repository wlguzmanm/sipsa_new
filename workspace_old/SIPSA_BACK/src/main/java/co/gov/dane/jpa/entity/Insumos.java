package co.gov.dane.jpa.entity;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ParameterMode;

import org.eclipse.persistence.annotations.*;

@NamedStoredProcedureQuery(name="PQ_SIPSA_RECOLECCION.Pr_CargarDMCRecoleccionI",
						   procedureName="PQ_SIPSA_RECOLECCION.Pr_CargarDMCRecoleccionI",  
						   parameters={
		@StoredProcedureParameter(queryParameter="p_uspe_id", name="p_uspe_id",mode=ParameterMode.IN,type=Number.class),
		@StoredProcedureParameter(queryParameter="p_TiposRecolecciones", name="p_TiposRecolecciones", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="p_Principal", name="p_Principal", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="p_Fuentes", name="p_Fuentes", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="p_FuentesArt", name="p_FuentesArt", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="p_Persona", name="p_Persona", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="p_Articulos", name="p_Articulos", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="p_Unidades", name="p_Unidades", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="p_CasasComerciales", name="p_CasasComerciales", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="p_Grupos", name="p_Grupos", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="p_Observaciones", name="p_Observaciones", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="p_codigo_error", name="p_codigo_error",mode=ParameterMode.OUT,type=Number.class),
		@StoredProcedureParameter(queryParameter="p_mensaje", name="p_mensaje",mode=ParameterMode.OUT,type=String.class)
		
	})
	
@Entity
public class Insumos {
	@Id
	private String SID;
}
