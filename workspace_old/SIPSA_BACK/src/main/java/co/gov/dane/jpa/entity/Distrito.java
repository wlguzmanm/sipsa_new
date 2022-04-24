package co.gov.dane.jpa.entity;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ParameterMode;

import org.eclipse.persistence.annotations.*;

@NamedStoredProcedureQuery(name="PQ_SIPSA_RECOLECCION.Pr_CargarDMCRecoleccionDIS",
						   procedureName="PQ_SIPSA_RECOLECCION.Pr_CargarDMCRecoleccionDIS",  
						   parameters={
		    @StoredProcedureParameter(queryParameter="p_uspe_id", name="p_uspe_id",mode=ParameterMode.IN,type=Number.class),
			@StoredProcedureParameter(queryParameter="p_Principal", name="p_Principal", mode=ParameterMode.REF_CURSOR,type=void.class),
			@StoredProcedureParameter(queryParameter="p_Informadores", name="p_Informadores", mode=ParameterMode.REF_CURSOR,type=void.class),
			@StoredProcedureParameter(queryParameter="p_FuentesTire", name="p_FuentesTire", mode=ParameterMode.REF_CURSOR,type=void.class),
			@StoredProcedureParameter(queryParameter="p_FuentesArt", name="p_FuentesArt", mode=ParameterMode.REF_CURSOR,type=void.class),
			@StoredProcedureParameter(queryParameter="p_Persona", name="p_Persona", mode=ParameterMode.REF_CURSOR,type=void.class),
			@StoredProcedureParameter(queryParameter="p_Articulos", name="p_Articulos", mode=ParameterMode.REF_CURSOR,type=void.class),
			@StoredProcedureParameter(queryParameter="p_Unidades", name="p_Unidades", mode=ParameterMode.REF_CURSOR,type=void.class),
			@StoredProcedureParameter(queryParameter="p_CasasComerciales", name="p_CasasComerciales", mode=ParameterMode.REF_CURSOR,type=void.class),
			@StoredProcedureParameter(queryParameter="p_Grupos", name="p_Grupos", mode=ParameterMode.REF_CURSOR,type=void.class),
			@StoredProcedureParameter(queryParameter="p_Caracteristicas", name="p_Caracteristicas", mode=ParameterMode.REF_CURSOR,type=void.class),
			@StoredProcedureParameter(queryParameter="p_ValCaraPermitidos", name="p_ValCaraPermitidos", mode=ParameterMode.REF_CURSOR,type=void.class),
			@StoredProcedureParameter(queryParameter="p_ArtiCaraValores", name="p_ArtiCaraValores", mode=ParameterMode.REF_CURSOR,type=void.class),
			@StoredProcedureParameter(queryParameter="p_Observaciones", name="p_Observaciones", mode=ParameterMode.REF_CURSOR,type=void.class),
			@StoredProcedureParameter(queryParameter="p_codigo_error", name="p_codigo_error",mode=ParameterMode.OUT,type=Number.class),
			@StoredProcedureParameter(queryParameter="p_mensaje", name="p_mensaje",mode=ParameterMode.OUT,type=String.class)
		
	})
	
@Entity
public class Distrito {
	@Id
	private String SID;
}
