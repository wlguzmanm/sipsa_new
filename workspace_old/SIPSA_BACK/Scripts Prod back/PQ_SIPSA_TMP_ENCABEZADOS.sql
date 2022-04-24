create or replace PACKAGE PQ_SIPSA_TMP_ENCABEZADOS AUTHID CURRENT_USER IS

/********************************************************************************
 DESCRIPCION   : Contiene los métodos sobres las tablas SIPSA_ENCA_ABASTECIMIENTO, SIPSA_ENCA_MAYORISTA

 REALIZADO POR : Vitaliano Corredor

 FECHA CREACION: 11/12/2008

 MODIFICADO POR:
 FECHA MODIFICA:
 CAMBIOS       :

 *******************************************************************************/
PROCEDURE pr_InsertarM
(p_futi_id                       IN SIPSA_TMP_ENCA_MAYORISTA.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_TMP_ENCA_MAYORISTA.uspe_id%TYPE,
 p_enma_ronda                      IN SIPSA_TMP_ENCA_MAYORISTA.enma_ronda%TYPE,
 p_prre_fecha_programada                      IN SIPSA_TMP_ENCA_MAYORISTA.prre_fecha_programada%TYPE,
 p_enma_hi                      IN SIPSA_TMP_ENCA_MAYORISTA.enma_hora_inicial%TYPE,
 p_enma_hf                      IN SIPSA_TMP_ENCA_MAYORISTA.enma_hora_final%TYPE,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

PROCEDURE pr_InsertarA
(p_futi_id                       IN SIPSA_TMP_ENCA_ABASTECIMIENTO.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_TMP_ENCA_ABASTECIMIENTO.uspe_id%TYPE,
 p_enab_fecha                      IN SIPSA_TMP_ENCA_ABASTECIMIENTO.prre_fecha_programada%TYPE,
 --p_enab_fecha in varchar2,
 p_enab_hora                     IN SIPSA_TMP_ENCA_ABASTECIMIENTO.enab_hora%TYPE,
 p_tive_id                      IN SIPSA_TMP_ENCA_ABASTECIMIENTO.tive_id%TYPE,
 p_tive_cual                      IN SIPSA_TMP_ENCA_ABASTECIMIENTO.tive_cual_vehiculo%TYPE,
 p_enab_placa                      IN SIPSA_TMP_ENCA_ABASTECIMIENTO.enab_placa%TYPE,
 p_enab_ubicacion                      IN SIPSA_TMP_ENCA_ABASTECIMIENTO.enab_ubicacion%TYPE,
 p_enab_procedencia                      IN SIPSA_TMP_ENCA_ABASTECIMIENTO.enab_procedencia%TYPE,
 p_enab_destino                      IN SIPSA_TMP_ENCA_ABASTECIMIENTO.enab_destino%TYPE,
 p_time_id                   IN SIPSA_TMP_ENCA_ABASTECIMIENTO.time_id%TYPE,
 p_time_cual                      IN SIPSA_TMP_ENCA_ABASTECIMIENTO.time_cual_mercado%TYPE,
 p_enab_basc_desc                     IN SIPSA_TMP_ENCA_ABASTECIMIENTO.enab_basc_desc%TYPE,
 p_enab_observaciones                     IN SIPSA_TMP_ENCA_ABASTECIMIENTO.enab_observaciones%TYPE,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

PROCEDURE pr_InsertarI2
(p_futi_id                       IN SIPSA_TMP_ENCA_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_TMP_ENCA_INSUMOS2.uspe_id%TYPE,
 p_arti_id                      IN SIPSA_TMP_ENCA_INSUMOS2.arti_id%TYPE,
 p_grin2_id                    IN SIPSA_TMP_ENCA_INSUMOS2.grin2_id%TYPE,
 p_prre_fecha_programada     IN SIPSA_TMP_ENCA_INSUMOS2.prre_fecha_programada%TYPE,
 p_arti_nombre                   IN SIPSA_TMP_ENCA_INSUMOS2.enin2_arti_nombre%TYPE,
 p_precio_prom_anterior     IN SIPSA_TMP_ENCA_INSUMOS2.enin2_precio_prom_anterior%TYPE,
 p_novedad                      IN SIPSA_TMP_ENCA_INSUMOS2.enin2_novedad%TYPE,
 p_ftes_capturadas        IN SIPSA_TMP_ENCA_INSUMOS2.enin2_ftes_capturadas%TYPE,      
 p_obse_id                      IN  SIPSA_TMP_ENCA_INSUMOS2.obse_id%TYPE,
 p_observacion              IN SIPSA_TMP_ENCA_INSUMOS2.enin2_observacion%TYPE,    
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

PROCEDURE pr_InsertarTmpAPGRIN2_VACA
( p_futi_id               IN  SIPSA_TMP_GRIN2_VACA.futi_id%TYPE,
  p_uspe_id   IN  SIPSA_TMP_GRIN2_VACA.uspe_id%TYPE,
  p_grin2_id IN SIPSA_TMP_GRIN2_VACA.grin2_id%TYPE,
  p_tire_id IN SIPSA_TMP_GRIN2_VACA.tire_id%TYPE,
  p_cara_id IN SIPSA_TMP_GRIN2_VACA.cara_id%TYPE,  
  p_vape_id IN SIPSA_TMP_GRIN2_VACA.vape_id%TYPE,
  p_valor IN SIPSA_TMP_GRIN2_VACA.valor%TYPE,
  p_codigo_error                  OUT NUMBER,
  p_mensaje                       OUT VARCHAR2);
  
PROCEDURE pr_InsertarI2_DR
(p_futi_id                       IN SIPSA_TMP_ENCA_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_TMP_ENCA_INSUMOS2.uspe_id%TYPE,
 p_arti_id                      IN SIPSA_TMP_ENCA_INSUMOS2.arti_id%TYPE,
 p_grin2_id                    IN SIPSA_TMP_ENCA_INSUMOS2.grin2_id%TYPE,
 p_prre_fecha_programada     IN SIPSA_TMP_ENCA_INSUMOS2.prre_fecha_programada%TYPE,
 p_arti_nombre                   IN SIPSA_TMP_ENCA_INSUMOS2.enin2_arti_nombre%TYPE,
 p_precio_prom_anterior     IN SIPSA_TMP_ENCA_INSUMOS2.enin2_precio_prom_anterior%TYPE,
 p_novedad                      IN SIPSA_TMP_ENCA_INSUMOS2.enin2_novedad%TYPE,
 p_ftes_capturadas        IN SIPSA_TMP_ENCA_INSUMOS2.enin2_ftes_capturadas%TYPE,      
 p_obse_id                      IN  SIPSA_TMP_ENCA_INSUMOS2.obse_id%TYPE,
 p_observacion              IN SIPSA_TMP_ENCA_INSUMOS2.enin2_observacion%TYPE,    
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2); 
 
PROCEDURE pr_InsertarTmpAPGRIN2_VACA_DR
( p_futi_id               IN  SIPSA_TMP_GRIN2_VACA.futi_id%TYPE,
  p_uspe_id   IN  SIPSA_TMP_GRIN2_VACA.uspe_id%TYPE,
  p_grin2_id IN SIPSA_TMP_GRIN2_VACA.grin2_id%TYPE,
  p_tire_id IN SIPSA_TMP_GRIN2_VACA.tire_id%TYPE,
  p_cara_id IN SIPSA_TMP_GRIN2_VACA.cara_id%TYPE,  
  p_vape_id IN SIPSA_TMP_GRIN2_VACA.vape_id%TYPE,
  p_valor IN SIPSA_TMP_GRIN2_VACA.valor%TYPE,
  p_codigo_error                  OUT NUMBER,
  p_mensaje                       OUT VARCHAR2);

PROCEDURE pr_InsertarI2_D
(p_futi_id                       IN SIPSA_TMP_ENCA_INSUMOS_D.FUTI_ID%TYPE,
 p_uspe_id                      IN SIPSA_TMP_ENCA_INSUMOS_D.USPE_ID%TYPE,
 p_arti_id                      IN SIPSA_TMP_ENCA_INSUMOS_D.ARTI_ID%TYPE,
 p_grin2_id                    IN SIPSA_TMP_ENCA_INSUMOS_D.GRIN_ID%TYPE,
 p_prre_fecha_programada     IN SIPSA_TMP_ENCA_INSUMOS_D.ENIN_FECHA_PROGRAMADA%TYPE,
 p_arti_nombre                   IN SIPSA_TMP_ENCA_INSUMOS_D.ENIN_ARTI_NOMBRE%TYPE,
 p_precio_prom_anterior     IN SIPSA_TMP_ENCA_INSUMOS_D.ENIN_PRECIO_PROM_ANTERIOR%TYPE,
 p_novedad                      IN SIPSA_TMP_ENCA_INSUMOS_D.ENIN_NOVEDAD%TYPE,      
 p_obse_id                      IN  SIPSA_TMP_ENCA_INSUMOS_D.OBSE_ID%TYPE,
 p_observacion              IN SIPSA_TMP_ENCA_INSUMOS_D.ENIN_OBSERVACION%TYPE,    
 p_nom_fuente               IN SIPSA_TMP_ENCA_INSUMOS_D.ENIN_NOMBRE_FUENTE%TYPE,    
 p_municipio                IN SIPSA_TMP_ENCA_INSUMOS_D.ENIN_MUNICIPIO%TYPE,    
 p_muni_id                  IN SIPSA_TMP_ENCA_INSUMOS_D.ENIN_MUNI_ID%TYPE,    
 p_direccion                IN SIPSA_TMP_ENCA_INSUMOS_D.ENIN_DIRECCION%TYPE,    
 p_telefono                 IN SIPSA_TMP_ENCA_INSUMOS_D.ENIN_TELEFONO%TYPE,    
 p_email                    IN SIPSA_TMP_ENCA_INSUMOS_D.ENIN_EMAIL%TYPE,    
 p_informante               IN SIPSA_TMP_ENCA_INSUMOS_D.ENIN_INFORMANTE%TYPE,    
 p_tel_informante           IN SIPSA_TMP_ENCA_INSUMOS_D.ENIN_TEL_INFORMANTE%TYPE,    
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

 
 
 


END PQ_SIPSA_TMP_ENCABEZADOS;
