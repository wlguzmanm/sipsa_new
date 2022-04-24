package co.gov.dane.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ParameterMode;
import org.eclipse.persistence.annotations.*;


@NamedStoredProcedureQuery(name="PQ_IND_RECOLECCION.pr_cargardmcmensual3", procedureName="PQ_IND_RECOLECCION.pr_cargardmcmensual3",  
	parameters={		
		@StoredProcedureParameter(queryParameter="p_tipovia", name="p_tipovia", mode=ParameterMode.REF_CURSOR,type=void.class),	
		@StoredProcedureParameter(queryParameter="p_letra", name="p_letra", mode=ParameterMode.REF_CURSOR,type=void.class),	
		@StoredProcedureParameter(queryParameter="p_prefijo", name="p_prefijo", mode=ParameterMode.REF_CURSOR,type=void.class),		
		@StoredProcedureParameter(queryParameter="p_cuadrante", name="p_cuadrante", mode=ParameterMode.REF_CURSOR,type=void.class),	
		@StoredProcedureParameter(queryParameter="p_letraprefijo", name="p_letraprefijo", mode=ParameterMode.REF_CURSOR,type=void.class),	
		@StoredProcedureParameter(queryParameter=" p_complemento1", name="p_complemento1", mode=ParameterMode.REF_CURSOR,type=void.class),	
		@StoredProcedureParameter(queryParameter=" p_complemento2", name="p_complemento2", mode=ParameterMode.REF_CURSOR,type=void.class),	
		@StoredProcedureParameter(queryParameter=" p_complemento3", name="p_complemento3", mode=ParameterMode.REF_CURSOR,type=void.class),	
		@StoredProcedureParameter(queryParameter=" p_complemento4", name="p_complemento4", mode=ParameterMode.REF_CURSOR,type=void.class),	
		@StoredProcedureParameter(queryParameter=" p_complemento5", name="p_complemento5", mode=ParameterMode.REF_CURSOR,type=void.class),	
		@StoredProcedureParameter(queryParameter="p_codigo_error", name="p_codigo_error",mode=ParameterMode.OUT,type=Number.class),
		@StoredProcedureParameter(queryParameter="p_mensaje", name="p_mensaje",mode=ParameterMode.OUT,type=String.class)
	})
	

@Entity
public class DireccionEstandarMensual {
	@Id
	private String SID;


	}
