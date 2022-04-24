package co.gov.dane.persistence;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.springframework.context.annotation.PropertySource;

import co.gov.dane.inmodel.FuenteDistrito;
import co.gov.dane.inmodel.ArtiCaraValoresI01;
import co.gov.dane.inmodel.FuenteIN;
import co.gov.dane.inmodel.PrincipalI01;
import co.gov.dane.inmodel.RecoleccionI01;
import co.gov.dane.inmodel.RecoleccionIN;
import co.gov.dane.inmodel.SrvRecoleccion;
import co.gov.dane.inmodel.SrvRecoleccion01;
import co.gov.dane.inmodel.RecoleccionDistrito;
import co.gov.dane.inmodel.RecoleccionIN;
import co.gov.dane.inmodel.SrvRecoleccion;
import co.gov.dane.inmodel.SrvRecoleccionDistrito;
import gov.dane.indices.model.Status;
import gov.dane.indices.model.Status.StatusType;
import oracle.jdbc.OracleTypes;
import scala.collection.mutable.StringBuilder;

@PropertySource("classpath:general.properties")
public class DataInput {


	private String databaseUser;
	private String databasePassword;
	private String databaseHost;
	private String databasePort;
	private String databaseName;



	public DataInput(String databaseUser, String databasePassword, String databaseHost, String databasePort,
			String databaseName) {
		super();
		this.databaseUser = databaseUser;
		this.databasePassword = databasePassword;
		this.databaseHost = databaseHost;
		this.databasePort = databasePort;
		this.databaseName = databaseName;
	}


	public Status setRecoleccion(int id_persona,SrvRecoleccion encuesta) {
		Status s = new Status();
		try {
			Connection conn = null;
			Properties connectionProps = new Properties();
			connectionProps.put("user", databaseUser);
			connectionProps.put("password", databasePassword);
			conn = DriverManager.getConnection("jdbc:oracle:thin:@"+databaseHost+":"+databasePort+"/"+databaseName, connectionProps);
			Object error = null,mensaje=null;
			CallableStatement cstFuente = null;
			CallableStatement cstRecoleccion = null;
			CallableStatement cstIncorporar = null;
			CallableStatement cstPersona = null;		
			CallableStatement cstTempRecoleccion = null;
			CallableStatement cstCoordenadas = null;

			cstFuente=conn.prepareCall("{call PQ_SIPSA_TMP_RECOLECCION.pr_insertarFUIN(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstRecoleccion=conn.prepareCall("{call PQ_SIPSA_TMP_RECOLECCION.pr_insertarI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");	
			//cstCoordenadas = conn.prepareCall("{call PQ_IND_TMP_coor_FUENTES.pr_Insertar(?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstIncorporar=conn.prepareCall("{call pq_sipsa_recoleccion.pr_incorporardmcI(?,?,?)}");
				
			cstTempRecoleccion = conn.prepareCall("select * from SIPSA_TMP_FUENTES_INFORMANTES");
			SimpleDateFormat sp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S");

			

			if(encuesta.Recoleccion.size()>0){
				for(RecoleccionIN r: encuesta.Recoleccion){
					cstRecoleccion.setLong(1, r.fuenId);
					cstRecoleccion.setLong(2, r.futiId);
					cstRecoleccion.setLong(3, r.tireId);
					cstRecoleccion.setLong(4, id_persona);
					
					java.sql.Date d = new java.sql.Date(new Date().getTime());
					if(r.prreFechaProgramada!=null){
						 d = new java.sql.Date(r.prreFechaProgramada.getTime()); 
					}
					
					
					cstRecoleccion.setDate(5, d);//r.prreFechaProgramada
					cstRecoleccion.setLong(6, r.articacoId);
					cstRecoleccion.setLong(7, r.articacoId);

					if(r.articacoId<9000000000L){
						cstRecoleccion.setString(8, r.artiNombre);
						cstRecoleccion.setLong(9, r.cacoId);
						cstRecoleccion.setString(11, r.articacoRegicaLinea);
						if (r.cacoId<9000000000L){
							cstRecoleccion.setString(10, r.cacoNombre);
						}else{
							cstRecoleccion.setString(10, null);
						}
					}else{
						cstRecoleccion.setString(8, r.artiNombre);
						cstRecoleccion.setLong(9, r.cacoId);
						cstRecoleccion.setString(10, null);
						cstRecoleccion.setString(11, r.articacoRegicaLinea);
					}

					cstRecoleccion.setLong(12, r.unmeId);
					cstRecoleccion.setDouble(13, r.promAntDiario == null?0:r.promAntDiario);
					cstRecoleccion.setString(14, r.novedadAnterior);
					cstRecoleccion.setDouble(15, r.precio);
					cstRecoleccion.setString(16, r.novedad);
					cstRecoleccion.setString(17, null); //observacion id
					cstRecoleccion.setString(18, r.observacion);
					
					d = new java.sql.Date(new Date().getTime());
					if(r.fechaRecoleccion!=null){
						 d = new java.sql.Date(r.fechaRecoleccion.getTime());
					}
					
					cstRecoleccion.setDate(19, d); //fecha de recoleccion
					
					cstRecoleccion.registerOutParameter(20, OracleTypes.NUMBER);
					cstRecoleccion.registerOutParameter(21, OracleTypes.VARCHAR);

					cstRecoleccion.execute();
					error=cstRecoleccion.getObject(20);
					mensaje =  cstRecoleccion.getObject(21);
					if(Integer.valueOf(error.toString())>0){
						s.setDescription("Error insertando recoleccion " + mensaje);
						s.setType(StatusType.ERROR);
						return s;
					}
				}
			}

			if(encuesta.Fuente.size()>0){
				for(FuenteIN f: encuesta.Fuente){						
					cstFuente.setLong(1, f.fuenId);
					cstFuente.setLong(2, f.futiId);
					cstFuente.setString(3, f.muniId);
					cstFuente.setLong(4,f.tireId);				

					cstFuente.setInt(5,id_persona);
					cstFuente.setString(6,f.fuenNombre);
					cstFuente.setString(7,f.fuenDireccion);
					cstFuente.setLong(8,(f.fuenTelefono!=null) ? Long.valueOf(f.fuenTelefono): 0L);
					cstFuente.setString(9,f.fuenEmail);
					cstFuente.setString(10,f.fuenNombreInformante);		
					cstFuente.setString(11,"");//cargo informante
					cstFuente.setString(12,"");//telefono informante
					cstFuente.setString(13,""); //email informante
					
					java.sql.Date d = new java.sql.Date(new Date().getTime());
					if(f.prreFechaProgramada!=null){
						 d = new java.sql.Date(f.prreFechaProgramada.getTime());
					}
					
					
					cstFuente.setDate(14,d);
					cstFuente.registerOutParameter(15, OracleTypes.NUMBER);
					cstFuente.registerOutParameter(16, OracleTypes.VARCHAR);
					cstFuente.execute();
					error=cstFuente.getObject(15);
					mensaje =  cstFuente.getObject(16);
					if(Integer.valueOf(error.toString())>0){
						s.setDescription("Error insertando fuente " + mensaje);
						s.setType(StatusType.ERROR);
						return s;	
					}
				}
			}		

			ResultSet rstmp =  cstTempRecoleccion.executeQuery();
			while(rstmp.next()){
				rstmp.getString(2);
			}
			

		/*NOOOO
			if(encuesta.Coordenada.size()>0){
				for(CoordenadasIN f: encuesta.Coordenada){
					cstCoordenadas.setLong(1, f.p_id_fuente);
					cstCoordenadas.setLong(2, f.id);
					cstCoordenadas.setDouble(3,f.p_longitud_gps==null?0.0:f.p_longitud_gps);
					cstCoordenadas.setDouble(4,f.p_latitud_gps==null?0.0:f.p_latitud_gps);
					cstCoordenadas.setDouble(5,f.p_altitud_gps==null?0.0:f.p_altitud_gps);
					cstCoordenadas.setDouble(6,f.p_precision_gps==null?0.0:f.p_precision_gps);
					cstCoordenadas.setDouble(7,f.p_longitud_dmc==null?0.0:f.p_longitud_dmc);
					cstCoordenadas.setDouble(8,f.p_latitud_dmc==null?0.0:f.p_latitud_dmc);		
					cstCoordenadas.setDouble(9,f.p_altitud_dmc==null?0.0:f.p_altitud_dmc);
					cstCoordenadas.setDouble(10,f.p_precision_dmc==null?0.0:f.p_precision_dmc);

					cstCoordenadas.registerOutParameter(11, OracleTypes.NUMBER);
					cstCoordenadas.registerOutParameter(12, OracleTypes.VARCHAR);
					cstCoordenadas.execute();
					error=cstCoordenadas.getObject(11);
					mensaje =  cstCoordenadas.getObject(12);

					if(Integer.valueOf(error.toString())>0){
						s.setDescription("Error insertando coordenada " + mensaje);
						s.setType(StatusType.ERROR);
						return s;	
					}
				}
			}*/
		
	
		cstIncorporar.setLong(1, id_persona);
		cstIncorporar.registerOutParameter(2, OracleTypes.NUMBER);
		cstIncorporar.registerOutParameter(3, OracleTypes.VARCHAR);
		cstIncorporar.execute();
		error=cstIncorporar.getObject(2);
		mensaje =  cstIncorporar.getObject(3);

		if(Integer.valueOf(error.toString())>0){
			s.setDescription("Error ejecución Incoporar DMC " + mensaje);
			s.setType(StatusType.ERROR);
			return s;
		}
		s.setDescription("Ejecución satisfactoria");
		s.setType(StatusType.OK);
		return s;
	} catch (SQLException e) {			
		s.setDescription(e.getMessage());
		s.setType(StatusType.ERROR);
		return s;
	} 
}
	
	/**
	 * Metodo que carga la informacion de distrito.
	 * 
	 * @param id_persona
	 * @param encuesta
	 * @return
	 */
	public Status setRecoleccionDistrito(int id_persona,SrvRecoleccionDistrito encuesta) {
		Status s = new Status();
		String usuario = "";
		try {
			Connection conn = null;
			Properties connectionProps = new Properties();
			connectionProps.put("user", databaseUser);
			connectionProps.put("password", databasePassword);
			conn = DriverManager.getConnection("jdbc:oracle:thin:@"+databaseHost+":"+databasePort+"/"+databaseName, connectionProps);
			Object error = null,mensaje=null;
			CallableStatement cstUsuario = null;	
			
			cstUsuario=conn.prepareCall("{call PQ_SIPSA_TMP_RECOLECCION.pr_Usuario_obtener(?,?,?)}"); 
			cstUsuario.setLong(1, id_persona);
			cstUsuario.registerOutParameter(2, OracleTypes.NUMBER);
			cstUsuario.registerOutParameter(3, OracleTypes.VARCHAR);
			cstUsuario.execute();
			error=cstUsuario.getObject(2);
			mensaje =  cstUsuario.getObject(3);
			if(Integer.valueOf(error.toString())>0){				
				error = null;
			}else {
				error = null;
				usuario = mensaje.toString();
				mensaje = null;
			}
				
			SimpleDateFormat sp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S");
						
			if(encuesta!= null && encuesta.Fuente!= null && encuesta.Fuente.size()>0){
				for(FuenteDistrito f: encuesta.Fuente){	
					if(f.fuenId>=9000000000L) {
						String idFuenteExistente = validarExistenciaFuente(f,conn);
						if(idFuenteExistente!= null) {
							 Long idTemp = f.fuenId; 
						      f.fuenId = Long.valueOf(idFuenteExistente);						      
						      if(encuesta.Recoleccion.size()>0){
								for(RecoleccionDistrito r: encuesta.Recoleccion){
									if((long)r.fuenId == (long)idTemp) {
										r.fuenId = f.fuenId;
									}
								}
						      }
						}else {
							/*
							String insertFuente = "insert into SIPSA_FUENTES (MUNI_ID,FUEN_COD_INTERNO,"
									+ "FUEN_NOMBRE,FUEN_DIRECCION,FUEN_TELEFONO,FUEN_EMAIL,FUEN_NOMBRE_INFORMANTE,"
									+ "FUEN_TELEFONO_INFORMANTE,FUEN_EMAIL_INFORMANTE,FUEN_FECHA_CREACION,FUEN_USUARIO_CREACION,FUEN_ID) values "
									+ "  (?,?,?,?,?,?,?,?,?,?,?,?)";
							
							java.sql.Date d = new java.sql.Date(new Date().getTime());
							try {
								
								  PreparedStatement preparedStmt = conn.prepareStatement(insertFuente);
							      preparedStmt.setString (1, f.muniId);
							      if(f.fuenCodigoInterno!= null) {
										 preparedStmt.setString(2, f.fuenCodigoInterno);
									}else {
										 preparedStmt.setString(2, null);
									}	
							      preparedStmt.setString(3,f.fuenNombre);
							      preparedStmt.setString(4,f.fuenDireccion);
							      
							      if(f.fuenTelefono!= null) {
								      preparedStmt.setInt(5,f.fuenTelefono);
								    }else {
								      preparedStmt.setInt(5,0);
								    }	
							      preparedStmt.setString(6,f.fuenEmail);
							      preparedStmt.setString(7,f.fuenNombreInformante);
							      if(f.fuenTelefonoInformante!= null) {						    	 
							    	  preparedStmt.setInt(8,f.fuenTelefonoInformante);
							      }else {
							    	  preparedStmt.setInt(8,0);
							      }
							      preparedStmt.setString(9,f.fuenEmail);
							      preparedStmt.setDate   (10, d);
							      preparedStmt.setString   (11,usuario);
							      
							      Statement  stmtIdFuente = conn.createStatement();
							      ResultSet rs = stmtIdFuente.executeQuery("select max(fuen_id)+1 as FUEN_ID from SIPSA_FUENTES");
							      String id = null;
							      while (rs.next()) {
							    	  id = rs.getString("FUEN_ID");
							    	  preparedStmt.setString(12,rs.getString("FUEN_ID"));
							      }
							      preparedStmt.execute();
							      
							      Long idTemp = f.fuenId; 
							      f.fuenId = Long.valueOf(id);						      
							      if(encuesta.Recoleccion.size()>0){
									for(RecoleccionIN r: encuesta.Recoleccion){
										if((long)r.fuenId == (long)idTemp) {
											r.fuenId = f.fuenId;
										}
									}
							      }
							}catch (Exception e) {
								s.setDescription("Error ejecución Incoporar DMC --Insert Fuente" + e.getMessage());
								s.setType(StatusType.ERROR);
								return s;
							}	
							*/
						}
						
						
					}else {
						java.sql.Date d = new java.sql.Date(new Date().getTime());
						String actualizarFuente = "update  SIPSA_FUENTES set  "
								+ "FUEN_NOMBRE = ?, FUEN_DIRECCION = ?, FUEN_TELEFONO = ?, FUEN_EMAIL = ?, FUEN_NOMBRE_INFORMANTE = ?, "
								+ "FUEN_TELEFONO_INFORMANTE = ?, FUEN_EMAIL_INFORMANTE = ?, FUEN_FECHA_MODIFICACION = ?, FUEN_USUARIO_MODIFICACION = ? where FUEN_ID  = ? ";
						try {
							PreparedStatement preparedStmt = conn.prepareStatement(actualizarFuente);
							preparedStmt.setString(1,f.fuenNombre);
							preparedStmt.setString(2,f.fuenDireccion);
						    if(f.fuenTelefono!= null) {
						      preparedStmt.setString(3,(f.fuenTelefono!= null && f.fuenTelefono!= "")?f.fuenTelefono:"");
						    }else {
						      preparedStmt.setString(3,null);
						    }						      
						      preparedStmt.setString(4,f.fuenEmail);
						      preparedStmt.setString(5,f.fuenNombreInformante);
						      if(f.fuenTelefonoInformante!= null) {						    	 
						    	  preparedStmt.setString(6,(f.fuenTelefonoInformante!= null && f.fuenTelefonoInformante!= "" )? f.fuenTelefonoInformante:"");
						      }else {
						    	  preparedStmt.setString(6,null);
						      }
						     
						      preparedStmt.setString(7,f.fuenEmail);
						      preparedStmt.setDate   (8, d);
						      preparedStmt.setString   (9, usuario);
						      preparedStmt.setLong(10,f.fuenId);
						      preparedStmt.executeUpdate();
						}catch (Exception e) {
							s.setDescription("Error ejecución Incoporar DMC --Insert Fuente: " + e.getMessage() +"--->"+e.getCause());
							s.setType(StatusType.ERROR);
							return s;
						}							
					}
				}
			}
			//TODO: FIN CREACION Y ACTUALIZACION DE FUENTES
			
			CallableStatement cstTmpOne = null;
			CallableStatement cstTmpTwo = null;		
			cstTmpOne = conn.prepareCall("{call PQ_SIPSA_TMP_ENCABEZADOS.pr_InsertarI2_D(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstTmpTwo = conn.prepareCall("{call PQ_SIPSA_TMP_RECOLECCION.pr_InsertarI2_D(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"); //se agregan
	
			
			//TODO: CARGUE DE TABLAS TEMPORALES
			if(encuesta.Recoleccion.size()>0){
				for(RecoleccionDistrito r: encuesta.Recoleccion){
					
					FuenteDistrito fuente = new FuenteDistrito();
					for(FuenteDistrito f: encuesta.Fuente){	
						if((long)f.fuenId == (long)r.fuenId) {
							fuente = f;
						}
					}
					
					try {
						
						java.sql.Date d = new java.sql.Date(new Date().getTime());
						if(r.prreFechaProgramada!=null){
							 d = new java.sql.Date(r.prreFechaProgramada.getTime()); 
						}
						cstTmpOne.setLong(1, r.futiId);
						cstTmpOne.setLong(2, id_persona);
						cstTmpOne.setLong(3, r.artiId);
						cstTmpOne.setLong(4, r.id);
						cstTmpOne.setDate(5, d);
						cstTmpOne.setString(6, r.artiNombre);
						cstTmpOne.setLong(7, (r.promAntDiario!= null)? (new Double(r.promAntDiario)).longValue(): 0L);
						cstTmpOne.setString(8, r.novedad);
						if(r.idObservacion== null) {
							cstTmpOne.setLong(9, 0L);
						}else {
							cstTmpOne.setLong(9, r.idObservacion);
						}
						
						cstTmpOne.setString(10, r.observacion);
						
						cstTmpOne.setString(11, fuente.fuenNombre);
						cstTmpOne.setString(12, fuente.muniNombre);
						cstTmpOne.setLong(13, (fuente.muniId!= null) ? new Double(fuente.muniId).longValue() :0L);
						cstTmpOne.setString(14, fuente.fuenDireccion);
						cstTmpOne.setString(15, (fuente.fuenTelefono!= null) ? fuente.fuenTelefono.toString():null);
						cstTmpOne.setString(16, fuente.fuenEmail);
						cstTmpOne.setString(17, fuente.fuenNombreInformante);
						cstTmpOne.setString(18, (fuente.fuenTelefonoInformante!= null) ? fuente.fuenTelefonoInformante.toString():null);
						
						
						cstTmpOne.registerOutParameter(19, OracleTypes.NUMBER);
						cstTmpOne.registerOutParameter(20, OracleTypes.VARCHAR);
						cstTmpOne.execute();
						error=cstTmpOne.getObject(19);
						mensaje =  cstTmpOne.getObject(20);
					}catch (Exception e) {
						s.setDescription("Error ejecución Incoporar DMC --Insert TMP " + mensaje);
						s.setType(StatusType.ERROR);
						return s;
					}
					
					try {
						java.sql.Date d = new java.sql.Date(new Date().getTime());
						if(r.prreFechaProgramada!=null){
							 d = new java.sql.Date(r.prreFechaProgramada.getTime()); 
						}
						
						cstTmpTwo.setLong(1, r.futiId);
						cstTmpTwo.setLong(2, id_persona);
						cstTmpTwo.setLong(3, r.artiId);
						cstTmpTwo.setLong(4, r.id);
						cstTmpTwo.setDate(5, d);
						cstTmpTwo.setString(6, null);
						cstTmpTwo.setString(7, fuente.fuenNombreInformante);
						if( fuente.fuenTelefonoInformante!= null) {
							cstTmpTwo.setString(8, fuente.fuenTelefonoInformante.toString());
						}else {
							cstTmpTwo.setString(8,null);
						}
						
						cstTmpTwo.setString(9, fuente.fuenCodigoInterno);
						cstTmpTwo.setLong(10, (r.precio!= null)?(new Double(r.precio)).longValue():0L);
						cstTmpTwo.setString(11, r.novedad);
						
						if(r.idObservacion== null) {
							cstTmpTwo.setLong(12, 0);
						}else {
							cstTmpTwo.setLong(12, r.idObservacion);
						}
						cstTmpTwo.setString(13, r.observacion);
						java.sql.Date d2 = new java.sql.Date(new Date().getTime());
						cstTmpTwo.setDate(14, d2);
						
						cstTmpTwo.setString(15, r.tipo);
						cstTmpTwo.setString(16, r.frecuencia);
						cstTmpTwo.setString(17, r.observacionProducto);
						cstTmpTwo.setString(18, r.unidadMedidaOtroNombre);
						cstTmpTwo.setLong(19, (r.unmeId!= null)?r.unmeId:0L);
						cstTmpTwo.setString(20, r.unmeNombre2);
						cstTmpTwo.setString(21, r.artiNombre);
						cstTmpTwo.setString(22,(r.promAntDiario!=null) ? r.promAntDiario.toString():"0");						
						cstTmpTwo.registerOutParameter(23, OracleTypes.NUMBER);
						cstTmpTwo.registerOutParameter(24, OracleTypes.VARCHAR);
						cstTmpTwo.execute();
						error=cstTmpTwo.getObject(23);
						mensaje =  cstTmpTwo.getObject(24);
					}catch (Exception e) {
						s.setDescription("Error ejecución Incoporar DMC --Insert TMP2 " + mensaje);
						s.setType(StatusType.ERROR);
						return s;
					}
					
				}
			}
			//TODO: FIN CARGUE DE TABLAS TEMPORALES
			
			try {
				CallableStatement cstIncorporar = null;	
				cstIncorporar=conn.prepareCall("{call pq_sipsa_recoleccion.pr_IncorporarDMCI2_DISTRITO(?,?,?)}"); 
				
				cstIncorporar.setLong(1, id_persona);
				cstIncorporar.registerOutParameter(2, OracleTypes.NUMBER);
				cstIncorporar.registerOutParameter(3, OracleTypes.VARCHAR);
				cstIncorporar.execute();
				error=cstIncorporar.getObject(2);
				mensaje =  cstIncorporar.getObject(3);
			
			}catch (Exception e) {
				s.setDescription("Error ejecución pr_IncorporarDMCI2_DISTRITO  " + mensaje);
				s.setType(StatusType.ERROR);
				return s;
			}
	
			if(Integer.valueOf(error.toString())>0){
				s.setDescription("Error ejecución Incoporar DMC " + mensaje);
				s.setType(StatusType.ERROR);
				return s;
			}
			s.setDescription("Ejecución satisfactoria");
			s.setType(StatusType.OK);
			return s;
		} catch (SQLException e) {			
			s.setDescription(e.getMessage());
			s.setType(StatusType.ERROR);
			return s;
		} 
	}


	/**
	 * Metodo que valida si existe la fuente
	 * 
	 * @param f
	 * @param conn 
	 * @return
	 * @throws SQLException 
	 */
	private String validarExistenciaFuente(FuenteDistrito f, Connection conn) throws SQLException {
		String retorno = null;
		 Statement  stmtIdFuente = conn.createStatement();
		 StringBuilder query = new StringBuilder();
		 query.append("SELECT FUEN_ID FROM SIPSA_FUENTES WHERE ");
		 query.append("MUNI_ID = "+f.muniId+" AND ");
		 query.append("FUEN_COD_INTERNO = '"+f.fuenCodigoInterno+"' AND ");
		 query.append("FUEN_NOMBRE = '"+f.fuenNombre+"' AND ");
		 query.append("FUEN_DIRECCION = '"+f.fuenDireccion+"' AND ");
		 query.append("FUEN_TELEFONO = "+((f.fuenTelefono!=null) ? f.fuenTelefono.toString(): "") +" AND ");
		 query.append(" FUEN_EMAIL = '"+f.fuenEmail+"' AND ");
		 query.append("FUEN_NOMBRE_INFORMANTE = '"+f.fuenNombreInformante+"' AND ");
		 query.append("FUEN_TELEFONO_INFORMANTE  = "+f.fuenTelefonoInformante+" AND ");
		 query.append("FUEN_EMAIL_INFORMANTE = '"+f.fuenEmail+"' ");		 
		 
		 
	      ResultSet rs = stmtIdFuente.executeQuery(query.toString());
	      String id = null;
	      while (rs.next()) {
	    	  retorno = rs.getString("FUEN_ID");
	      }

	      return retorno;
	}
	
	
	
	public Status setRecoleccionInsumo(int id_persona,SrvRecoleccion encuesta) {
		Status s = new Status();
		try {
			Connection conn = null;
			Properties connectionProps = new Properties();
			connectionProps.put("user", databaseUser);
			connectionProps.put("password", databasePassword);
			conn = DriverManager.getConnection("jdbc:oracle:thin:@"+databaseHost+":"+databasePort+"/"+databaseName, connectionProps);
			Object error = null,mensaje=null;
			CallableStatement cstFuente = null;
			CallableStatement cstRecoleccion = null;
			CallableStatement cstIncorporar = null;
			CallableStatement cstPersona = null;		
			CallableStatement cstTempRecoleccion = null;
			CallableStatement cstCoordenadas = null;

			cstFuente=conn.prepareCall("{call PQ_SIPSA_TMP_RECOLECCION.pr_insertarFUIN(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstRecoleccion=conn.prepareCall("{call PQ_SIPSA_TMP_RECOLECCION.pr_insertarI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");	
			//cstCoordenadas = conn.prepareCall("{call PQ_IND_TMP_coor_FUENTES.pr_Insertar(?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstIncorporar=conn.prepareCall("{call pq_sipsa_recoleccion.pr_incorporardmcI(?,?,?)}");
				
			cstTempRecoleccion = conn.prepareCall("select * from SIPSA_TMP_FUENTES_INFORMANTES");
			SimpleDateFormat sp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S");

			

			if(encuesta.Recoleccion.size()>0){
				for(RecoleccionIN r: encuesta.Recoleccion){
					cstRecoleccion.setLong(1, r.fuenId);
					cstRecoleccion.setLong(2, r.futiId);
					cstRecoleccion.setLong(3, r.tireId);
					cstRecoleccion.setLong(4, id_persona);
					
					java.sql.Date d = new java.sql.Date(new Date().getTime());
					if(r.prreFechaProgramada!=null){
						 d = new java.sql.Date(r.prreFechaProgramada.getTime()); 
					}
					
					
					cstRecoleccion.setDate(5, d);//r.prreFechaProgramada
					cstRecoleccion.setLong(6, r.articacoId);
					cstRecoleccion.setLong(7, r.artiId);

					if(r.articacoId<9000000000L){
						cstRecoleccion.setString(8, r.artiNombre);
						cstRecoleccion.setLong(9, r.cacoId);
						cstRecoleccion.setString(11, r.articacoRegicaLinea);
						if (r.cacoId<9000000000L){
							cstRecoleccion.setString(10, r.cacoNombre);
						}else{
							cstRecoleccion.setString(10, null);
						}
					}else{
						cstRecoleccion.setString(8, r.artiNombre);
						cstRecoleccion.setLong(9, r.cacoId);
						cstRecoleccion.setString(10, null);
						cstRecoleccion.setString(11, r.articacoRegicaLinea);
					}

					cstRecoleccion.setLong(12, r.unmeId);
					cstRecoleccion.setDouble(13, r.promAntDiario == null?0:r.promAntDiario);
					cstRecoleccion.setString(14, r.novedadAnterior);
					cstRecoleccion.setDouble(15, r.precio);
					cstRecoleccion.setString(16, r.novedad);
					cstRecoleccion.setLong(17, r.idObservacion);
					cstRecoleccion.setString(18, r.observacion);
					
					d = new java.sql.Date(new Date().getTime());
					if(r.fechaRecoleccion!=null){
						 d = new java.sql.Date(r.fechaRecoleccion.getTime());
					}
					
					cstRecoleccion.setDate(19, d); //fecha de recoleccion
					
					cstRecoleccion.registerOutParameter(20, OracleTypes.NUMBER);
					cstRecoleccion.registerOutParameter(21, OracleTypes.VARCHAR);

					cstRecoleccion.execute();
					error=cstRecoleccion.getObject(20);
					mensaje =  cstRecoleccion.getObject(21);
					if(Integer.valueOf(error.toString())>0){
						s.setDescription("Error insertando recoleccion " + mensaje);
						s.setType(StatusType.ERROR);
						return s;
					}
				}
			}

			if(encuesta.Fuente.size()>0){
				for(FuenteIN f: encuesta.Fuente){						
					cstFuente.setLong(1, f.fuenId);
					cstFuente.setLong(2, f.futiId);
					cstFuente.setString(3, f.muniId);
					cstFuente.setLong(4,f.tireId);				

					cstFuente.setInt(5,id_persona);
					cstFuente.setString(6,f.fuenNombre);
					cstFuente.setString(7,f.fuenDireccion);
					cstFuente.setString(8,f.fuenTelefono==null?"":f.fuenTelefono.toString());
					cstFuente.setString(9,f.fuenEmail);
					cstFuente.setString(10,f.fuenNombreInformante);		
					cstFuente.setString(11,"");//cargo informante
					cstFuente.setString(12,"");//telefono informante
					cstFuente.setString(13,""); //email informante
					
					java.sql.Date d = new java.sql.Date(new Date().getTime());
					if(f.prreFechaProgramada!=null){
						 d = new java.sql.Date(f.prreFechaProgramada.getTime());
					}
					
					
					cstFuente.setDate(14,d);
					cstFuente.registerOutParameter(15, OracleTypes.NUMBER);
					cstFuente.registerOutParameter(16, OracleTypes.VARCHAR);
					cstFuente.execute();
					error=cstFuente.getObject(15);
					mensaje =  cstFuente.getObject(16);
					if(Integer.valueOf(error.toString())>0){
						s.setDescription("Error insertando fuente " + mensaje);
						s.setType(StatusType.ERROR);
						return s;	
					}
				}
			}		

			ResultSet rstmp =  cstTempRecoleccion.executeQuery();
			while(rstmp.next()){
				rstmp.getString(2);
			}
			

		/*NOOOOO
			if(encuesta.Coordenada.size()>0){
				for(CoordenadasIN f: encuesta.Coordenada){
					cstCoordenadas.setLong(1, f.p_id_fuente);
					cstCoordenadas.setLong(2, f.id);
					cstCoordenadas.setDouble(3,f.p_longitud_gps==null?0.0:f.p_longitud_gps);
					cstCoordenadas.setDouble(4,f.p_latitud_gps==null?0.0:f.p_latitud_gps);
					cstCoordenadas.setDouble(5,f.p_altitud_gps==null?0.0:f.p_altitud_gps);
					cstCoordenadas.setDouble(6,f.p_precision_gps==null?0.0:f.p_precision_gps);
					cstCoordenadas.setDouble(7,f.p_longitud_dmc==null?0.0:f.p_longitud_dmc);
					cstCoordenadas.setDouble(8,f.p_latitud_dmc==null?0.0:f.p_latitud_dmc);		
					cstCoordenadas.setDouble(9,f.p_altitud_dmc==null?0.0:f.p_altitud_dmc);
					cstCoordenadas.setDouble(10,f.p_precision_dmc==null?0.0:f.p_precision_dmc);

					cstCoordenadas.registerOutParameter(11, OracleTypes.NUMBER);
					cstCoordenadas.registerOutParameter(12, OracleTypes.VARCHAR);
					cstCoordenadas.execute();
					error=cstCoordenadas.getObject(11);
					mensaje =  cstCoordenadas.getObject(12);

					if(Integer.valueOf(error.toString())>0){
						s.setDescription("Error insertando coordenada " + mensaje);
						s.setType(StatusType.ERROR);
						return s;	
					}
				}
			}*/
		
	
		cstIncorporar.setLong(1, id_persona);
		cstIncorporar.registerOutParameter(2, OracleTypes.NUMBER);
		cstIncorporar.registerOutParameter(3, OracleTypes.VARCHAR);
		cstIncorporar.execute();
		error=cstIncorporar.getObject(2);
		mensaje =  cstIncorporar.getObject(3);

		if(Integer.valueOf(error.toString())>0){
			s.setDescription("Error ejecución Incoporar DMC " + mensaje);
			s.setType(StatusType.ERROR);
			return s;
		}
		s.setDescription("Ejecución satisfactoria");
		s.setType(StatusType.OK);
		return s;
	} catch (SQLException e) {			
		s.setDescription(e.getMessage());
		s.setType(StatusType.ERROR);
		return s;
	} 
}
	
	public Status setRecoleccionInsumo01(int id_persona, SrvRecoleccion01 encuesta) {
		Status s = new Status();
		try {
			Connection conn = null;
			Properties connectionProps = new Properties();
			connectionProps.put("user", databaseUser);
			connectionProps.put("password", databasePassword);
			conn = DriverManager.getConnection("jdbc:oracle:thin:@"+databaseHost+":"+databasePort+"/"+databaseName, connectionProps);
			Object error = null,mensaje=null;
			CallableStatement cstFuente = null;
			CallableStatement cstPrincipal = null;
			CallableStatement cstIncorporar = null;
			
			CallableStatement cstTempRecoleccion = null;
			CallableStatement cstCoordenadas = null;

			cstPrincipal=conn.prepareCall("{call PQ_SIPSA_TMP_ENCABEZADOS.pr_insertarI2(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstFuente=conn.prepareCall("{call PQ_SIPSA_TMP_RECOLECCION.pr_insertari2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");	
			cstCoordenadas = conn.prepareCall("{call PQ_SIPSA_TMP_ENCABEZADOS.pr_InsertarTmpAPGRIN2_VACA(?,?,?,?,?,?,?,?,?)}");
			
			cstIncorporar=conn.prepareCall("{call pq_sipsa_recoleccion.pr_incorporardmcI2(?,?,?)}");
				
			cstTempRecoleccion = conn.prepareCall("select * from SIPSA_TMP_FUENTES_INFORMANTES");
			SimpleDateFormat sp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S");

			if(encuesta.Principal==null) {
				s.setDescription("Error se debe seleccionar una recoleccion ");
				s.setType(StatusType.ERROR);
				return s;
			}
			
			if(encuesta.Principal==null) {
				s.setDescription("Error se debe seleccionar una recoleccion ");
				s.setType(StatusType.ERROR);
				return s;
			}
			
			if(encuesta.Principal!=null && encuesta.Principal.size()>0){
				for(PrincipalI01 r: encuesta.Principal){
					cstPrincipal.setLong(1, r.getFutiId());
					cstPrincipal.setLong(2, id_persona);
					cstPrincipal.setLong(3, r.getArtiId());
					cstPrincipal.setLong(4, r.getGrin2Id());
					java.sql.Date d = new java.sql.Date(new Date().getTime());
					if(r.getPrreFechaProgramada()!=null){
						 d = new java.sql.Date(r.getPrreFechaProgramada().getTime()); 
					}
					cstPrincipal.setDate(5, d);//r.prreFechaProgramada
					cstPrincipal.setString(6, r.getArtiNombre());
					cstPrincipal.setDouble(7, r.getPromAnterior());
					cstPrincipal.setString(8, "");
					cstPrincipal.setInt(9, r.getFuentesCapturadas());
					cstPrincipal.setInt(10, 0);
					cstPrincipal.setString(11, "");
					cstPrincipal.registerOutParameter(12, OracleTypes.NUMBER);
					cstPrincipal.registerOutParameter(13, OracleTypes.VARCHAR);
					cstPrincipal.execute();
					error=cstPrincipal.getObject(12);
					mensaje =  cstPrincipal.getObject(13);
					if(Integer.valueOf(error.toString())>0){
						s.setDescription("Error insertando recoleccion " + mensaje);
						s.setType(StatusType.ERROR);
						return s;
					}
				}
			}

			if(encuesta.Recoleccion.size()>0){
				for(RecoleccionI01 f: encuesta.Recoleccion){						
					cstFuente.setLong(1, f.getFutiId());
					cstFuente.setLong(2, id_persona);
					cstFuente.setLong(3, f.getArtiId());
					cstFuente.setLong(4,f.getGrin2Id());	
					
					java.sql.Date d = new java.sql.Date(new Date().getTime());
					if(f.getFechaProgramada()!=null){
						 d = new java.sql.Date(f.getFechaProgramada().getTime());
					}
					
					
					cstFuente.setDate(5,d);
					cstFuente.setString(6, "");

					cstFuente.setLong(7,f.getInfoId());
					cstFuente.setString(8,f.getInfoNombre());
					cstFuente.setString(9,f.getInfoTelefono());
					cstFuente.setDouble(10,f.getPrecioActual());
					cstFuente.setString(11,f.getNovedad());
					cstFuente.setLong(12,f.getObservacionId());		
					cstFuente.setString(13,f.getObservaciones());
					
					
					
					if(f.getFechaRecoleccion()!=null){
						 d = new java.sql.Date(f.getFechaRecoleccion().getTime());
					}	
					
					cstFuente.setDate(14,d);
					cstFuente.registerOutParameter(15, OracleTypes.NUMBER);
					cstFuente.registerOutParameter(16, OracleTypes.VARCHAR);
					cstFuente.execute();
					error=cstFuente.getObject(15);
					mensaje =  cstFuente.getObject(16);
					if(Integer.valueOf(error.toString())>0){
						s.setDescription("Error insertando fuente " + mensaje);
						s.setType(StatusType.ERROR);
						return s;	
					}
				}
			}		

			ResultSet rstmp =  cstTempRecoleccion.executeQuery();
			while(rstmp.next()){
				rstmp.getString(2);
			}
			

		
			if(encuesta.ArtiCaraValores.size()>0){
				for(ArtiCaraValoresI01 f: encuesta.ArtiCaraValores){
					cstCoordenadas.setLong(1, f.getFutiId() );
					cstCoordenadas.setLong(2, id_persona);
					cstCoordenadas.setLong(3,f.getGrin2Id());
					cstCoordenadas.setLong(4,f.getTireId());
					cstCoordenadas.setLong(5,f.getCaraId());
					cstCoordenadas.setLong(6,f.getVapeId());
					cstCoordenadas.setString(7,f.getVapeDescripcion());

					cstCoordenadas.registerOutParameter(8, OracleTypes.NUMBER);
					cstCoordenadas.registerOutParameter(9, OracleTypes.VARCHAR);
					cstCoordenadas.execute();
					error=cstCoordenadas.getObject(8);
					mensaje =  cstCoordenadas.getObject(9);

					if(Integer.valueOf(error.toString())>0){
						s.setDescription("Error insertando coordenada " + mensaje);
						s.setType(StatusType.ERROR);
						return s;	
					}
				}
			}
		
	
			cstIncorporar.setLong(1, id_persona);
			cstIncorporar.registerOutParameter(2, OracleTypes.NUMBER);
			cstIncorporar.registerOutParameter(3, OracleTypes.VARCHAR);
			cstIncorporar.execute();
			error=cstIncorporar.getObject(2);
			mensaje =  cstIncorporar.getObject(3);
	
			if(Integer.valueOf(error.toString())>0){
				s.setDescription("Error ejecución Incoporar DMC " + mensaje);
				s.setType(StatusType.ERROR);
				return s;
			}
			s.setDescription("Ejecución satisfactoria");
			s.setType(StatusType.OK);
			return s;
		} catch (SQLException e) {			
			s.setDescription(e.getMessage());
			s.setType(StatusType.ERROR);
			return s;
		} 
	}
	
	
	
	
	
}
