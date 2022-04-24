create or replace PACKAGE            PQ_SIPSA_ENCABEZADOS AUTHID CURRENT_USER IS

/********************************************************************************
 DESCRIPCION   : Contiene los metodos sobre las tablas SIPSA_ENCA_MAYORISTA, SIPSA_ENCA_ABASTECIMIENTO

 REALIZADO POR : Vitaliano Corredor

 FECHA CREACION: 12/07/2012

 MODIFICADO POR: 
 FECHA MODIFICA: 
 CAMBIOS       :
 *******************************************************************************/

PROCEDURE pr_InsertarM
( p_futi_id                       IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_MAYORISTA.uspe_id%TYPE,
 p_enma_ronda                      IN SIPSA_ENCA_MAYORISTA.enma_ronda%TYPE,
 p_prre_fecha_programada                      IN SIPSA_ENCA_MAYORISTA.prre_fecha_programada%TYPE,
 p_enma_hi                      IN SIPSA_ENCA_MAYORISTA.enma_hora_inicial%TYPE,
 p_enma_hf                      IN SIPSA_ENCA_MAYORISTA.enma_hora_final%TYPE,
 p_usuario                       IN SIPSA_ENCA_MAYORISTA.enma_usuario_creacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2) ;

PROCEDURE pr_EliminarM
( p_futi_id                       IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_MAYORISTA.uspe_id%TYPE,
 p_enma_ronda                      IN SIPSA_ENCA_MAYORISTA.enma_ronda%TYPE,
 p_prre_fecha_programada                      IN SIPSA_ENCA_MAYORISTA.prre_fecha_programada%TYPE,
 p_usuario                       IN SIPSA_ENCA_MAYORISTA.enma_usuario_modificacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2) ;

PROCEDURE pr_ActualizarM
( p_futi_id                       IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_MAYORISTA.uspe_id%TYPE,
 p_enma_ronda                      IN SIPSA_ENCA_MAYORISTA.enma_ronda%TYPE,
 p_prre_fecha_programada                      IN SIPSA_ENCA_MAYORISTA.prre_fecha_programada%TYPE,
 p_enma_hi                      IN SIPSA_ENCA_MAYORISTA.enma_hora_inicial%TYPE,
 p_enma_hf                      IN SIPSA_ENCA_MAYORISTA.enma_hora_final%TYPE,
 p_usuario                       IN SIPSA_ENCA_MAYORISTA.enma_usuario_modificacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

PROCEDURE pr_ConsultarM
( p_futi_id                       IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_MAYORISTA.uspe_id%TYPE,
 p_prre_fecha_programada                      IN SIPSA_ENCA_MAYORISTA.prre_fecha_programada%TYPE,
 p_resultado         OUT Pq_Gen_General.ty_cursor,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);
 
 PROCEDURE pr_ConsultarEncaMayorXRonda
( p_futi_id                     IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_MAYORISTA.uspe_id%TYPE,
 p_prre_fecha_programada        IN SIPSA_ENCA_MAYORISTA.prre_fecha_programada%TYPE,
 p_enma_ronda                   IN SIPSA_ENCA_MAYORISTA.ENMA_RONDA%TYPE,
 p_resultado                    OUT Pq_Gen_General.ty_cursor,
 p_codigo_error                 OUT NUMBER,
 p_mensaje                      OUT VARCHAR2);
 
  PROCEDURE pr_ConsultarEncaMayorExiste
( p_futi_id                     IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_prre_fecha_programada        IN SIPSA_ENCA_MAYORISTA.prre_fecha_programada%TYPE,
 p_enma_ronda                   IN SIPSA_ENCA_MAYORISTA.ENMA_RONDA%TYPE,
 p_resultado                    OUT Pq_Gen_General.ty_cursor,
 p_codigo_error                 OUT NUMBER,
 p_mensaje                      OUT VARCHAR2);

PROCEDURE pr_InsertarA
(p_futi_id                       IN SIPSA_ENCA_ABASTECIMIENTO.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_ABASTECIMIENTO.uspe_id%TYPE,
 p_prre_fecha_programada                      IN SIPSA_ENCA_ABASTECIMIENTO.prre_fecha_programada%TYPE,
 p_enab_placa                      IN SIPSA_ENCA_ABASTECIMIENTO.enab_placa%TYPE, 
 p_enab_hora                     IN SIPSA_ENCA_ABASTECIMIENTO.enab_hora%TYPE,
 p_tive_id                      IN SIPSA_ENCA_ABASTECIMIENTO.tive_id%TYPE,
 p_tive_cual                      IN SIPSA_ENCA_ABASTECIMIENTO.tive_cual_vehiculo%TYPE,
 p_enab_ubicacion                      IN SIPSA_ENCA_ABASTECIMIENTO.enab_ubicacion%TYPE,
 p_enab_procedencia                      IN SIPSA_ENCA_ABASTECIMIENTO.enab_procedencia%TYPE,
 p_enab_destino                      IN SIPSA_ENCA_ABASTECIMIENTO.enab_destino%TYPE,
 p_time_id                   IN SIPSA_ENCA_ABASTECIMIENTO.time_id%TYPE,
 p_time_cual                      IN SIPSA_ENCA_ABASTECIMIENTO.time_cual_mercado%TYPE,
 p_enab_basc_desc                     IN SIPSA_ENCA_ABASTECIMIENTO.enab_basc_desc%TYPE,
 p_enab_observaciones                     IN SIPSA_ENCA_ABASTECIMIENTO.enab_observaciones%TYPE,
 p_usuario                       IN SIPSA_ENCA_ABASTECIMIENTO.enab_usuario_creacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2) ;

PROCEDURE pr_EliminarA
(p_futi_id                       IN SIPSA_ENCA_ABASTECIMIENTO.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_ABASTECIMIENTO.uspe_id%TYPE,
 p_prre_fecha_programada                      IN SIPSA_ENCA_ABASTECIMIENTO.prre_fecha_programada%TYPE,
 p_enab_placa                      IN SIPSA_ENCA_ABASTECIMIENTO.enab_placa%TYPE,
 p_usuario                       IN SIPSA_ENCA_ABASTECIMIENTO.enab_usuario_modificacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2) ;
 
PROCEDURE pr_InsertarEncaAbas
(p_futi_id                      IN SIPSA_ENCA_ABASTECIMIENTO.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_ABASTECIMIENTO.uspe_id%TYPE,
 p_prre_fecha_programada        IN SIPSA_ENCA_ABASTECIMIENTO.prre_fecha_programada%TYPE,
 p_enab_placa                   IN SIPSA_ENCA_ABASTECIMIENTO.enab_placa%TYPE, 
 p_enab_hora                    IN SIPSA_ENCA_ABASTECIMIENTO.enab_hora%TYPE,
 p_tive_id                      IN SIPSA_ENCA_ABASTECIMIENTO.tive_id%TYPE,
 p_tive_cual                    IN SIPSA_ENCA_ABASTECIMIENTO.tive_cual_vehiculo%TYPE,
 p_enab_ubicacion               IN SIPSA_ENCA_ABASTECIMIENTO.enab_ubicacion%TYPE,
 p_enab_procedencia             IN SIPSA_ENCA_ABASTECIMIENTO.enab_procedencia%TYPE,
 p_enab_destino                 IN SIPSA_ENCA_ABASTECIMIENTO.enab_destino%TYPE,
 p_time_id                      IN SIPSA_ENCA_ABASTECIMIENTO.time_id%TYPE,
 p_time_cual                    IN SIPSA_ENCA_ABASTECIMIENTO.time_cual_mercado%TYPE,
 p_enab_basc_desc               IN SIPSA_ENCA_ABASTECIMIENTO.enab_basc_desc%TYPE,
 p_enab_observaciones           IN SIPSA_ENCA_ABASTECIMIENTO.enab_observaciones%TYPE,
 p_usuario                      IN SIPSA_ENCA_ABASTECIMIENTO.enab_usuario_creacion%TYPE,  
 p_codigo_error                 OUT NUMBER,
 p_mensaje                      OUT VARCHAR2) ; 
 
 
PROCEDURE pr_EliminarEncaAbas
(p_futi_id                      IN SIPSA_ENCA_ABASTECIMIENTO.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_ABASTECIMIENTO.uspe_id%TYPE,
 p_prre_fecha_programada        IN SIPSA_ENCA_ABASTECIMIENTO.prre_fecha_programada%TYPE,
 p_enab_placa                   IN SIPSA_ENCA_ABASTECIMIENTO.enab_placa%TYPE,
 p_enab_hora                    IN SIPSA_ENCA_ABASTECIMIENTO.enab_hora%TYPE,
 p_codigo_error                 OUT NUMBER,
 p_mensaje                      OUT VARCHAR2) ;
 
PROCEDURE pr_ConsultarEncaAbas
(p_futi_id                      IN SIPSA_ENCA_ABASTECIMIENTO.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_ABASTECIMIENTO.uspe_id%TYPE,
 p_prre_fecha_programada        IN SIPSA_ENCA_ABASTECIMIENTO.prre_fecha_programada%TYPE,
 p_enab_placa                   IN SIPSA_ENCA_ABASTECIMIENTO.enab_placa%TYPE,
 p_enab_hora                    IN SIPSA_ENCA_ABASTECIMIENTO.enab_hora%TYPE,
 p_resultado                    OUT Pq_Gen_General.ty_cursor,
 p_codigo_error                 OUT NUMBER,
 p_mensaje                      OUT VARCHAR2) ; 

PROCEDURE pr_ActualizarA
(p_futi_id                       IN SIPSA_ENCA_ABASTECIMIENTO.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_ABASTECIMIENTO.uspe_id%TYPE,
 p_prre_fecha_programada                      IN SIPSA_ENCA_ABASTECIMIENTO.prre_fecha_programada%TYPE,
 p_enab_placa                      IN SIPSA_ENCA_ABASTECIMIENTO.enab_placa%TYPE, 
 p_enab_hora                     IN SIPSA_ENCA_ABASTECIMIENTO.enab_hora%TYPE,
 p_tive_id                      IN SIPSA_ENCA_ABASTECIMIENTO.tive_id%TYPE,
 p_tive_cual                      IN SIPSA_ENCA_ABASTECIMIENTO.tive_cual_vehiculo%TYPE,
 p_enab_ubicacion                      IN SIPSA_ENCA_ABASTECIMIENTO.enab_ubicacion%TYPE,
 p_enab_procedencia                      IN SIPSA_ENCA_ABASTECIMIENTO.enab_procedencia%TYPE,
 p_enab_destino                      IN SIPSA_ENCA_ABASTECIMIENTO.enab_destino%TYPE,
 p_time_id                   IN SIPSA_ENCA_ABASTECIMIENTO.time_id%TYPE,
 p_time_cual                      IN SIPSA_ENCA_ABASTECIMIENTO.time_cual_mercado%TYPE,
 p_enab_basc_desc                     IN SIPSA_ENCA_ABASTECIMIENTO.enab_basc_desc%TYPE,
 p_enab_observaciones                     IN SIPSA_ENCA_ABASTECIMIENTO.enab_observaciones%TYPE,
 p_usuario                       IN SIPSA_ENCA_ABASTECIMIENTO.enab_usuario_modificacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

PROCEDURE pr_ConsultarA
( p_futi_id                       IN SIPSA_ENCA_ABASTECIMIENTO.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_ABASTECIMIENTO.uspe_id%TYPE,
 p_prre_fecha_programada                      IN SIPSA_ENCA_ABASTECIMIENTO.prre_fecha_programada%TYPE,
 p_resultado         OUT Pq_Gen_General.ty_cursor,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

PROCEDURE pr_InsertarI2
(p_futi_id                       IN SIPSA_ENCA_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_INSUMOS2.uspe_id%TYPE,
 p_grin2_id                    IN SIPSA_ENCA_INSUMOS2.grin2_id%TYPE,
 p_prre_fecha_programada     IN SIPSA_ENCA_INSUMOS2.prre_fecha_programada%TYPE,
 p_precio_prom_anterior     IN SIPSA_ENCA_INSUMOS2.enin2_precio_prom_anterior%TYPE,
 p_novedad                      IN SIPSA_ENCA_INSUMOS2.enin2_novedad%TYPE,
 p_ftes_capturadas        IN SIPSA_ENCA_INSUMOS2.enin2_ftes_capturadas%TYPE,    
 p_obse_id                      IN  SIPSA_ENCA_INSUMOS2.obse_id%TYPE,
 p_observacion              IN SIPSA_ENCA_INSUMOS2.enin2_observacion%TYPE,      
  p_usuario                       IN SIPSA_ENCA_INSUMOS2.enin2_usuario_creacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

PROCEDURE pr_EliminarI2
(p_futi_id                       IN SIPSA_ENCA_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_INSUMOS2.uspe_id%TYPE,
 p_grin2_id                    IN SIPSA_ENCA_INSUMOS2.grin2_id%TYPE,
 p_prre_fecha_programada     IN SIPSA_ENCA_INSUMOS2.prre_fecha_programada%TYPE,
 p_usuario                       IN SIPSA_ENCA_INSUMOS2.enin2_usuario_modificacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2) ;

PROCEDURE pr_ActualizarI2
(p_futi_id                       IN SIPSA_ENCA_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_INSUMOS2.uspe_id%TYPE,
 p_grin2_id                    IN SIPSA_ENCA_INSUMOS2.grin2_id%TYPE,
 p_prre_fecha_programada     IN SIPSA_ENCA_INSUMOS2.prre_fecha_programada%TYPE,
 p_precio_prom_anterior     IN SIPSA_ENCA_INSUMOS2.enin2_precio_prom_anterior%TYPE,
 p_novedad                      IN SIPSA_ENCA_INSUMOS2.enin2_novedad%TYPE,
 p_ftes_capturadas        IN SIPSA_ENCA_INSUMOS2.enin2_ftes_capturadas%TYPE,    
 p_obse_id                      IN  SIPSA_ENCA_INSUMOS2.obse_id%TYPE,
 p_observacion              IN SIPSA_ENCA_INSUMOS2.enin2_observacion%TYPE,      
  p_usuario                       IN SIPSA_ENCA_INSUMOS2.enin2_usuario_modificacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);


PROCEDURE pr_ConsultarI2
( p_futi_id                       IN SIPSA_ENCA_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_INSUMOS2.uspe_id%TYPE,
 p_prre_fecha_programada                      IN SIPSA_ENCA_INSUMOS2.prre_fecha_programada%TYPE,
 p_resultado         OUT Pq_Gen_General.ty_cursor,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);
 
 
 
  PROCEDURE pr_ConsultarUltimoEncReg
 /********************************************************************************
 DESCRIPCION   : Metodo que permite consultar la diferencia con la fecha del ultimo registro, que equivale al ultimo ingreso de un vehiculo

 REALIZADO POR : AMM - Adonay Mantilla

 FECHA CREACION: 13/07/2016
 *******************************************************************************/
(p_enab_placa                  IN VARCHAR2,
 p_resultado                    OUT Pq_Gen_General.ty_cursor,
 p_codigo_error                 OUT NUMBER,
 p_mensaje                      OUT VARCHAR2);

/*
PROCEDURE pr_Eliminar
(p_grup_id            IN SIPSA_ENCA_MAYORISTA.grup_id%TYPE,
 p_usuario       IN SIPSA_ENCA_MAYORISTA.grup_usuario_modificacion%TYPE,
 p_codigo_error  OUT NUMBER,
 p_mensaje       OUT VARCHAR2);

PROCEDURE pr_Actualizar
(p_grup_id                            IN SIPSA_ENCA_MAYORISTA.grup_id%TYPE,
 p_grup_nombre                  IN SIPSA_ENCA_MAYORISTA.grup_nombre%TYPE,
 p_usuario                       IN SIPSA_ENCA_MAYORISTA.grup_usuario_modificacion%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);

PROCEDURE pr_Consultar
(p_grup_id                            IN SIPSA_ENCA_MAYORISTA.grup_id%TYPE,
 p_resultado      OUT Pq_Gen_General.ty_cursor,
 p_codigo_error   OUT NUMBER,
 p_mensaje        OUT VARCHAR2);

FUNCTION fn_ObtenerNombre (p_grup_id IN NUMBER)
 RETURN VARCHAR2 ;*/
 
 PROCEDURE pr_ConsultarPorFuenBolMayor
(p_futi_id        IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_prre_fecha_programacion IN sipsa_programacion_recoleccion.prre_fecha_programada%TYPE,
 p_resultado      OUT Pq_Gen_General.ty_cursor,
 p_codigo_error   OUT NUMBER,
 p_mensaje        OUT VARCHAR2);
 
 PROCEDURE pr_ConsultarHorasM
(p_futi_id         IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_prre_fecha_programacion IN sipsa_programacion_recoleccion.prre_fecha_programada%TYPE,
 p_resultado      OUT Pq_Gen_General.ty_cursor,
 p_codigo_error   OUT NUMBER,
 p_mensaje        OUT VARCHAR2);
 
 
PROCEDURE pr_InsertarI2_D
(p_futi_id                       IN SIPSA_ENCA_INSUMOS_D.FUTI_ID%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_INSUMOS_D.USPE_ID%TYPE,
 p_grin2_id                    IN SIPSA_ENCA_INSUMOS_D.GRIN_ID%TYPE,
 p_prre_fecha_programada     IN SIPSA_ENCA_INSUMOS_D.ENIN_FECHA_PROGRAMADA%TYPE,
 p_precio_prom_anterior     IN SIPSA_ENCA_INSUMOS_D.ENIN_PRECIO_PROM_ANTERIOR%TYPE,
 p_novedad                      IN SIPSA_ENCA_INSUMOS_D.ENIN_NOVEDAD%TYPE,   
 p_obse_id                      IN  SIPSA_ENCA_INSUMOS_D.OBSE_ID%TYPE,
 p_observacion              IN SIPSA_ENCA_INSUMOS_D.ENIN_OBSERVACION%TYPE,      
 p_usuario                       IN SIPSA_ENCA_INSUMOS_D.ENIN_USUARIO_CREACION%TYPE,  
 p_arti_id                  IN  SIPSA_ENCA_INSUMOS_D.ARTI_ID%TYPE,
 p_arti_nombre              IN SIPSA_ENCA_INSUMOS_D.ARTI_NOMBRE%TYPE,  
 p_nom_fuente               IN SIPSA_ENCA_INSUMOS_D.ENIN_NOMBRE_FUENTE%TYPE,    
 p_municipio                IN SIPSA_ENCA_INSUMOS_D.ENIN_MUNICIPIO%TYPE,    
 p_muni_id                  IN SIPSA_ENCA_INSUMOS_D.ENIN_MUNI_ID%TYPE,    
 p_direccion                IN SIPSA_ENCA_INSUMOS_D.ENIN_DIRECCION%TYPE,    
 p_telefono                 IN SIPSA_ENCA_INSUMOS_D.ENIN_TELEFONO%TYPE,    
 p_email                    IN SIPSA_ENCA_INSUMOS_D.ENIN_EMAIL%TYPE,    
 p_informante               IN SIPSA_ENCA_INSUMOS_D.ENIN_INFORMANTE%TYPE,    
 p_tel_informante           IN SIPSA_ENCA_INSUMOS_D.ENIN_TEL_INFORMANTE%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

PROCEDURE pr_ActualizarI2_D
(p_futi_id                       IN SIPSA_ENCA_INSUMOS_D.FUTI_ID%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_INSUMOS_D.USPE_ID%TYPE,
 p_grin2_id                    IN SIPSA_ENCA_INSUMOS_D.GRIN_ID%TYPE,
 p_prre_fecha_programada     IN SIPSA_ENCA_INSUMOS_D.ENIN_FECHA_PROGRAMADA%TYPE,
 p_precio_prom_anterior     IN SIPSA_ENCA_INSUMOS_D.ENIN_PRECIO_PROM_ANTERIOR%TYPE,
 p_novedad                      IN SIPSA_ENCA_INSUMOS_D.ENIN_NOVEDAD%TYPE,   
 p_obse_id                      IN  SIPSA_ENCA_INSUMOS_D.OBSE_ID%TYPE,
 p_observacion              IN SIPSA_ENCA_INSUMOS_D.ENIN_OBSERVACION%TYPE,      
 p_usuario                       IN SIPSA_ENCA_INSUMOS_D.ENIN_USUARIO_MODIFICACION%TYPE,  
 p_arti_id                  IN  SIPSA_ENCA_INSUMOS_D.ARTI_ID%TYPE,
 p_arti_nombre              IN SIPSA_ENCA_INSUMOS_D.ARTI_NOMBRE%TYPE, 
 p_nom_fuente               IN SIPSA_ENCA_INSUMOS_D.ENIN_NOMBRE_FUENTE%TYPE,    
 p_municipio                IN SIPSA_ENCA_INSUMOS_D.ENIN_MUNICIPIO%TYPE,    
 p_muni_id                  IN SIPSA_ENCA_INSUMOS_D.ENIN_MUNI_ID%TYPE,    
 p_direccion                IN SIPSA_ENCA_INSUMOS_D.ENIN_DIRECCION%TYPE,    
 p_telefono                 IN SIPSA_ENCA_INSUMOS_D.ENIN_TELEFONO%TYPE,    
 p_email                    IN SIPSA_ENCA_INSUMOS_D.ENIN_EMAIL%TYPE,    
 p_informante               IN SIPSA_ENCA_INSUMOS_D.ENIN_INFORMANTE%TYPE,    
 p_tel_informante           IN SIPSA_ENCA_INSUMOS_D.ENIN_TEL_INFORMANTE%TYPE,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);
 
END PQ_SIPSA_ENCABEZADOS;