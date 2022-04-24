create or replace PACKAGE PQ_SIPSA_TMP_RECOLECCION AUTHID CURRENT_USER IS

/********************************************************************************
 DESCRIPCION   : Contiene los métodos sobre las tablas SIPSA_TMP_RECO_MAYORISTAS,SIPSA_TMP_RECO_ABASTECIMIENTO,SIPSA_TMP_RECO_INSUMOS,
                                                                                 SIPSA_TMP_FUENTES_INFORMANTES,SIPSA_TMP_RECO_INSUMOS2

 REALIZADO POR : Vitaliano Corredor

 FECHA CREACION: 12/07/2012

 MODIFICADO POR:
 FECHA MODIFICA:
 CAMBIOS       :

 *******************************************************************************/
PROCEDURE pr_InsertarM
( p_futi_id               IN OUT SIPSA_TMP_RECO_MAYORISTAS.futi_id%TYPE,
 p_uspe_id   IN  SIPSA_TMP_RECO_MAYORISTAS.uspe_id%TYPE,
 p_arti_id       IN OUT SIPSA_TMP_RECO_MAYORISTAS.arti_id%TYPE,
 p_unme_id    IN OUT SIPSA_TMP_RECO_MAYORISTAS.unme_id%TYPE,
 p_prre_fecha_programada IN OUT SIPSA_TMP_RECO_MAYORISTAS.prre_fecha_programada%TYPE,
 p_enma_ronda                   IN OUT SIPSA_TMP_RECO_MAYORISTAS.enma_ronda%TYPE,
 p_rema_precio_prom_anterior            IN SIPSA_TMP_RECO_MAYORISTAS.rema_precio_prom_anterior%TYPE,
 p_rema_toma1               IN SIPSA_TMP_RECO_MAYORISTAS.rema_toma1%TYPE,
 p_rema_toma2                  IN SIPSA_TMP_RECO_MAYORISTAS.rema_toma2%TYPE,
 p_rema_toma3              IN SIPSA_TMP_RECO_MAYORISTAS.rema_toma3%TYPE,
 p_rema_toma4             IN SIPSA_TMP_RECO_MAYORISTAS.rema_toma4%TYPE,
 p_rema_observacion_ronda           IN SIPSA_TMP_RECO_MAYORISTAS.rema_observacion_ronda%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);
 
PROCEDURE pr_InsertarA
(p_futi_id                       IN OUT SIPSA_TMP_RECO_ABASTECIMIENTO.futi_id%TYPE,
 p_uspe_id                      IN OUT SIPSA_TMP_RECO_ABASTECIMIENTO.uspe_id%TYPE,
 p_enab_placa                      IN OUT SIPSA_TMP_RECO_ABASTECIMIENTO.enab_placa%TYPE,
 p_enab_fecha                      IN OUT SIPSA_TMP_RECO_ABASTECIMIENTO.prre_fecha_programada%TYPE,
 p_enab_hora                      IN OUT SIPSA_TMP_RECO_ABASTECIMIENTO.enab_hora%TYPE,
  --p_enab_fecha in varchar2,
 p_arti_id                      IN SIPSA_TMP_RECO_ABASTECIMIENTO.arti_id%TYPE,
 p_reab_cantidad                      IN SIPSA_TMP_RECO_ABASTECIMIENTO.reab_cantidad%TYPE,
 p_tipr_id                      IN SIPSA_TMP_RECO_ABASTECIMIENTO.tipr_id%TYPE,
 p_unme_id                      IN SIPSA_TMP_RECO_ABASTECIMIENTO.unme_id%TYPE,
 p_reab_cantidad_pr                      IN SIPSA_TMP_RECO_ABASTECIMIENTO.reab_cantidad_pr%TYPE,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);
 
 PROCEDURE pr_InsertarI
( p_fuen_id               IN  SIPSA_TMP_RECO_INSUMOS.fuen_id%TYPE, 
p_futi_id               IN  SIPSA_TMP_RECO_INSUMOS.futi_id%TYPE,
p_tire_id               IN  SIPSA_TMP_RECO_INSUMOS.tire_id%TYPE,
 p_uspe_id   IN  SIPSA_TMP_RECO_INSUMOS.uspe_id%TYPE,
 p_prre_fecha_programada IN SIPSA_TMP_RECO_INSUMOS.prre_fecha_programada%TYPE,
 p_articaco_id       IN  SIPSA_TMP_RECO_INSUMOS.articaco_id%TYPE,
 p_arti_id       IN  SIPSA_TMP_RECO_INSUMOS.arti_id%TYPE,
 p_arti_nombre IN  SIPSA_TMP_RECO_INSUMOS.arti_nombre%TYPE,
  p_caco_id       IN  SIPSA_TMP_RECO_INSUMOS.caco_id%TYPE, 
 p_casa_comercial IN  SIPSA_TMP_RECO_INSUMOS.REIN_CASA_COMERCIAL%TYPE,
  p_regicalinea IN  SIPSA_TMP_RECO_INSUMOS.regica_linea%TYPE,
 p_unme_id    IN  SIPSA_TMP_RECO_INSUMOS.unme_id%TYPE,
 p_rein_precio_anterior            IN SIPSA_TMP_RECO_INSUMOS.rein_precio_anterior%TYPE,
 p_rein_novedad_anterior            IN SIPSA_TMP_RECO_INSUMOS.rein_novedad_anterior%TYPE,
 p_rein_precio_recolectado            IN SIPSA_TMP_RECO_INSUMOS.rein_precio_recolectado%TYPE,
 p_rein_novedad IN SIPSA_TMP_RECO_INSUMOS.rein_novedad%TYPE,
 p_obse_id IN SIPSA_TMP_RECO_INSUMOS.obse_id%TYPE,
 p_rein_observacion           IN SIPSA_TMP_RECO_INSUMOS.rein_observacion%TYPE,
  p_fecha_recoleccion IN SIPSA_TMP_RECO_INSUMOS.rein_fecha_recoleccion%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);


PROCEDURE pr_InsertarFUIN
( p_fuen_id               IN  SIPSA_TMP_FUENTES_INFORMANTES.fuen_id%TYPE, 
  p_futi_id               IN  SIPSA_TMP_FUENTES_INFORMANTES.futi_id%TYPE,
  p_muni_id IN  SIPSA_TMP_FUENTES_INFORMANTES.muni_id%TYPE,
  p_tire_id               IN  SIPSA_TMP_FUENTES_INFORMANTES.tire_id%TYPE,
 p_uspe_id   IN  SIPSA_TMP_FUENTES_INFORMANTES.uspe_id%TYPE,
 p_fuen_nombre IN SIPSA_TMP_FUENTES_INFORMANTES.fuen_nombre%TYPE,
 p_fuen_direccion IN SIPSA_TMP_FUENTES_INFORMANTES.fuen_direccion%TYPE,
 p_fuen_telefono IN SIPSA_TMP_FUENTES_INFORMANTES.fuen_telefono%TYPE,
 p_fuen_email IN SIPSA_TMP_FUENTES_INFORMANTES.fuen_email%TYPE,
 p_info_nombre IN SIPSA_TMP_FUENTES_INFORMANTES.info_nombre%TYPE,
 p_info_cargo IN SIPSA_TMP_FUENTES_INFORMANTES.info_cargo%TYPE,
 p_info_telefono IN SIPSA_TMP_FUENTES_INFORMANTES.info_telefono%TYPE,
 p_info_email IN SIPSA_TMP_FUENTES_INFORMANTES.info_email%TYPE,
 p_fecha_visita IN SIPSA_TMP_FUENTES_INFORMANTES.fecha_visita%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);

PROCEDURE pr_InsertarI2
(p_futi_id                       IN SIPSA_TMP_RECO_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_TMP_RECO_INSUMOS2.uspe_id%TYPE,
 p_arti_id                      IN SIPSA_TMP_RECO_INSUMOS2.arti_id%TYPE,
 p_grin2_id                    IN SIPSA_TMP_RECO_INSUMOS2.grin2_id%TYPE,
 p_prre_fecha_programada     IN SIPSA_TMP_RECO_INSUMOS2.prre_fecha_programada%TYPE,
 p_novedad_gral IN SIPSA_TMP_RECO_INSUMOS2.rein2_novedad_gral%TYPE,
 p_id_informante     IN SIPSA_TMP_RECO_INSUMOS2.rein2_id_informante%TYPE,
 p_nom_informante           IN SIPSA_TMP_RECO_INSUMOS2.rein2_nom_informante%TYPE,
 p_tel_informante           IN SIPSA_TMP_RECO_INSUMOS2.rein2_tel_informante%TYPE,
 p_precio_recolectado                      IN SIPSA_TMP_RECO_INSUMOS2.rein2_precio_recolectado%TYPE,
 p_novedad   IN SIPSA_TMP_RECO_INSUMOS2.rein2_novedad%TYPE,
 p_obse_id                      IN  SIPSA_TMP_RECO_INSUMOS2.obse_id%TYPE,
 p_rein2_observacion              IN SIPSA_TMP_RECO_INSUMOS2.rein2_observacion%TYPE,
 p_fecha_recoleccion  IN SIPSA_TMP_RECO_INSUMOS2.rein2_fecha_recoleccion%TYPE,    
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

 PROCEDURE pr_InsertarI_DR
( p_fuen_id               IN  SIPSA_TMP_RECO_INSUMOS.fuen_id%TYPE, 
p_futi_id               IN  SIPSA_TMP_RECO_INSUMOS.futi_id%TYPE,
p_tire_id               IN  SIPSA_TMP_RECO_INSUMOS.tire_id%TYPE,
 p_uspe_id   IN  SIPSA_TMP_RECO_INSUMOS.uspe_id%TYPE,
 p_prre_fecha_programada IN SIPSA_TMP_RECO_INSUMOS.prre_fecha_programada%TYPE,
 p_articaco_id       IN  SIPSA_TMP_RECO_INSUMOS.articaco_id%TYPE,
 p_arti_id       IN  SIPSA_TMP_RECO_INSUMOS.arti_id%TYPE,
 p_arti_nombre IN  SIPSA_TMP_RECO_INSUMOS.arti_nombre%TYPE,
  p_caco_id       IN  SIPSA_TMP_RECO_INSUMOS.caco_id%TYPE, 
 p_casa_comercial IN  SIPSA_TMP_RECO_INSUMOS.REIN_CASA_COMERCIAL%TYPE,
  p_regicalinea IN  SIPSA_TMP_RECO_INSUMOS.regica_linea%TYPE,
 p_unme_id    IN  SIPSA_TMP_RECO_INSUMOS.unme_id%TYPE,
 p_rein_precio_anterior            IN SIPSA_TMP_RECO_INSUMOS.rein_precio_anterior%TYPE,
 p_rein_novedad_anterior            IN SIPSA_TMP_RECO_INSUMOS.rein_novedad_anterior%TYPE,
 p_rein_precio_recolectado            IN SIPSA_TMP_RECO_INSUMOS.rein_precio_recolectado%TYPE,
 p_rein_novedad IN SIPSA_TMP_RECO_INSUMOS.rein_novedad%TYPE,
 p_obse_id IN SIPSA_TMP_RECO_INSUMOS.obse_id%TYPE,
 p_rein_observacion           IN SIPSA_TMP_RECO_INSUMOS.rein_observacion%TYPE,
  p_fecha_recoleccion IN SIPSA_TMP_RECO_INSUMOS.rein_fecha_recoleccion%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);
 
 PROCEDURE pr_InsertarFUIN_DR
( p_fuen_id               IN  SIPSA_TMP_FUENTES_INFORMANTES.fuen_id%TYPE, 
  p_futi_id               IN  SIPSA_TMP_FUENTES_INFORMANTES.futi_id%TYPE,
  p_muni_id IN  SIPSA_TMP_FUENTES_INFORMANTES.muni_id%TYPE,
  p_tire_id               IN  SIPSA_TMP_FUENTES_INFORMANTES.tire_id%TYPE,
 p_uspe_id   IN  SIPSA_TMP_FUENTES_INFORMANTES.uspe_id%TYPE,
 p_fuen_nombre IN SIPSA_TMP_FUENTES_INFORMANTES.fuen_nombre%TYPE,
 p_fuen_direccion IN SIPSA_TMP_FUENTES_INFORMANTES.fuen_direccion%TYPE,
 p_fuen_telefono IN SIPSA_TMP_FUENTES_INFORMANTES.fuen_telefono%TYPE,
 p_fuen_email IN SIPSA_TMP_FUENTES_INFORMANTES.fuen_email%TYPE,
 p_info_nombre IN SIPSA_TMP_FUENTES_INFORMANTES.info_nombre%TYPE,
 p_info_cargo IN SIPSA_TMP_FUENTES_INFORMANTES.info_cargo%TYPE,
 p_info_telefono IN SIPSA_TMP_FUENTES_INFORMANTES.info_telefono%TYPE,
 p_info_email IN SIPSA_TMP_FUENTES_INFORMANTES.info_email%TYPE,
 p_fecha_visita IN SIPSA_TMP_FUENTES_INFORMANTES.fecha_visita%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2); 
 
 PROCEDURE pr_InsertarI2_DR
(p_futi_id                       IN SIPSA_TMP_RECO_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_TMP_RECO_INSUMOS2.uspe_id%TYPE,
 p_arti_id                      IN SIPSA_TMP_RECO_INSUMOS2.arti_id%TYPE,
 p_grin2_id                    IN SIPSA_TMP_RECO_INSUMOS2.grin2_id%TYPE,
 p_prre_fecha_programada     IN SIPSA_TMP_RECO_INSUMOS2.prre_fecha_programada%TYPE,
 p_novedad_gral IN SIPSA_TMP_RECO_INSUMOS2.rein2_novedad_gral%TYPE,
 p_id_informante     IN SIPSA_TMP_RECO_INSUMOS2.rein2_id_informante%TYPE,
 p_nom_informante           IN SIPSA_TMP_RECO_INSUMOS2.rein2_nom_informante%TYPE,
 p_tel_informante           IN SIPSA_TMP_RECO_INSUMOS2.rein2_tel_informante%TYPE,
 p_precio_recolectado                      IN SIPSA_TMP_RECO_INSUMOS2.rein2_precio_recolectado%TYPE,
 p_novedad   IN SIPSA_TMP_RECO_INSUMOS2.rein2_novedad%TYPE,
 p_obse_id                      IN  SIPSA_TMP_RECO_INSUMOS2.obse_id%TYPE,
 p_rein2_observacion              IN SIPSA_TMP_RECO_INSUMOS2.rein2_observacion%TYPE,
 p_fecha_recoleccion  IN SIPSA_TMP_RECO_INSUMOS2.rein2_fecha_recoleccion%TYPE,    
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2); 

PROCEDURE pr_InsertarI2_D
(p_futi_id                       IN SIPSA_TMP_RECO_INSUMOS_D.FUTI_ID%TYPE,
 p_uspe_id                      IN SIPSA_TMP_RECO_INSUMOS_D.USPE_ID%TYPE,
 p_arti_id                      IN SIPSA_TMP_RECO_INSUMOS_D.ARTI_ID%TYPE,
 p_grin2_id                    IN SIPSA_TMP_RECO_INSUMOS_D.GRIN_ID%TYPE,
 p_prre_fecha_programada     IN SIPSA_TMP_RECO_INSUMOS_D.ENIN_FECHA_PROGRAMADA%TYPE,
 p_id_informante     		IN SIPSA_TMP_RECO_INSUMOS_D.REIN_ID_INFORMANTE%TYPE,
 p_nom_informante           IN SIPSA_TMP_RECO_INSUMOS_D.REIN_NOM_INFORMANTE%TYPE,
 p_tel_informante           IN SIPSA_TMP_RECO_INSUMOS_D.REIN_TEL_INFORMANTE%TYPE,
 p_cod_informante           IN SIPSA_TMP_RECO_INSUMOS_D.REIN_COD_INFORMANTE%TYPE,
 p_precio_recolectado        IN SIPSA_TMP_RECO_INSUMOS_D.REIN_PRECIO_RECOLECTADO%TYPE,
 p_novedad   					IN SIPSA_TMP_RECO_INSUMOS_D.REIN_NOVEDAD%TYPE,
 p_obse_id                      IN  SIPSA_TMP_RECO_INSUMOS_D.OBSE_ID%TYPE,
 p_rein2_observacion              IN SIPSA_TMP_RECO_INSUMOS_D.REIN_OBSERVACION%TYPE,
 p_fecha_recoleccion  IN SIPSA_TMP_RECO_INSUMOS_D.REIN_FECHA_RECOLECCION%TYPE,    
 p_tipo  IN SIPSA_TMP_RECO_INSUMOS_D.REIN_TIPO%TYPE,   
 p_frecuencia  IN SIPSA_TMP_RECO_INSUMOS_D.REIN_FRECUENCIA%TYPE,   
 p_observacion_producto  IN SIPSA_TMP_RECO_INSUMOS_D.REIN_OBSERVACION_PRODUCTO%TYPE,   
 p_unidad_medida_otro  IN SIPSA_TMP_RECO_INSUMOS_D.REIN_UNIDAD_MEDIDA_OTRO%TYPE,   
 p_unidad_id  IN SIPSA_TMP_RECO_INSUMOS_D.REIN_UNIDAD_ID%TYPE,   
 p_unidad_medida  IN SIPSA_TMP_RECO_INSUMOS_D.REIN_UNIDAD_MEDIDA%TYPE,  
 p_arti_nombre         IN SIPSA_TMP_RECO_INSUMOS_D.ARTI_NOMBRE%TYPE, 
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

PROCEDURE pr_Usuario_obtener
 (p_usuario                   IN SIPSA_RECOLECCION_INSUMOS_D.REIN_USUARIO_MODIFICACION%TYPE,
  p_codigo_error              OUT NUMBER,
  p_mensaje                   OUT VARCHAR2);
 

END PQ_SIPSA_TMP_RECOLECCION;