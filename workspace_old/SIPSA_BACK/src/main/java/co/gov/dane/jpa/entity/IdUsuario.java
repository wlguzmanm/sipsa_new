package co.gov.dane.jpa.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ParameterMode;
import org.eclipse.persistence.annotations.*;


@NamedStoredProcedureQuery(name="pq_sipsa_usuarios_perfiles.pr_consultarusuarioDmc", 
						   procedureName="pq_sipsa_usuarios_perfiles.pr_consultarusuarioDmc",  
	parameters={
		@StoredProcedureParameter(queryParameter="p_usua_usuario", name="p_usua_usuario",mode=ParameterMode.IN,type=Number.class),		
		@StoredProcedureParameter(queryParameter="p_clave", name="p_clave",mode=ParameterMode.IN,type=Number.class),		
		@StoredProcedureParameter(queryParameter="p_uspe_id", name="p_uspe_id", mode=ParameterMode.OUT,type=Number.class),		
		@StoredProcedureParameter(queryParameter="p_nombre_perfil", name="p_nombre_perfil", mode=ParameterMode.OUT,type=String.class),				
		@StoredProcedureParameter(queryParameter="p_codigo_error", name="p_codigo_error",mode=ParameterMode.OUT,type=Number.class),
		@StoredProcedureParameter(queryParameter="p_mensaje", name="p_mensaje",mode=ParameterMode.OUT,type=String.class)
		
	})
	

@Entity
public class IdUsuario {
	@Id
	private String SID;

	
	}
