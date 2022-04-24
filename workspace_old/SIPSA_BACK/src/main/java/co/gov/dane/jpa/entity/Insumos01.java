package co.gov.dane.jpa.entity;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ParameterMode;

import org.eclipse.persistence.annotations.*;

@NamedStoredProcedureQuery(name="PQ_SIPSA_RECOLECCION.Pr_CargarDMCRecoleccionI2",
						   procedureName="PQ_SIPSA_RECOLECCION.Pr_CargarDMCRecoleccionI2",  
						   parameters={
		@StoredProcedureParameter(queryParameter="p_uspe_id", name="p_uspe_id",mode=ParameterMode.IN,type=Number.class),
		@StoredProcedureParameter(queryParameter="p_Principal", name="p_Principal", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="P_INFORMADORES", name="p_Fuentes", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="P_FUENTESTIRE", name="p_FuentesArt", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="P_PERSONA", name="p_Persona", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="P_ARTICULOS", name="p_Articulos", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="P_CARACTERISTICAS", name="p_Unidades", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="P_VALCARAPERMITIDOS", name="p_CasasComerciales", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="P_ARTICARAVALORES", name="p_Grupos", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="P_OBSERVACIONES", name="p_Observaciones", mode=ParameterMode.REF_CURSOR,type=void.class),
		@StoredProcedureParameter(queryParameter="p_codigo_error", name="p_codigo_error",mode=ParameterMode.OUT,type=Number.class),
		@StoredProcedureParameter(queryParameter="p_mensaje", name="p_mensaje",mode=ParameterMode.OUT,type=String.class)
		
	})
	
@Entity
public class Insumos01 {
	@Id
	private String SID;
}
