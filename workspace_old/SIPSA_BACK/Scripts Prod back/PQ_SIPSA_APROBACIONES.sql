create or replace PACKAGE PQ_SIPSA_APROBACIONES AUTHID CURRENT_USER IS

/********************************************************************************
 DESCRIPCION   : Contiene los metodos sobre las tablas SIPSA_APROBA_ENCA_INSUMOS2
                                                                                SIPSA_APROBA_FUEN_INFO
                                                                                SIPSA_APROBA_GRIN2
                                                                                SIPSA_APROBA_GRIN2_VACA
                                                                                SIPSA_APROBA_INSUMOS
                                                                                SIPSA_APROBA_RECO_INSUMOS2

 REALIZADO POR : Vitaliano Corredor

 FECHA CREACION: 13/04/2013

 MODIFICADO POR: 
 FECHA MODIFICA: 
 CAMBIOS       : 
 *******************************************************************************/

PROCEDURE pr_InsertarIA
(p_fuen_id               IN SIPSA_APROBA_INSUMOS.apin_fuen_id%TYPE, 
 p_futi_id               IN OUT SIPSA_APROBA_INSUMOS.apin_futi_id%TYPE,
   p_tire_id   IN  SIPSA_APROBA_INSUMOS.tire_id%TYPE,
 p_uspe_id   IN  SIPSA_APROBA_INSUMOS.uspe_id%TYPE,
 p_prre_fecha_programada IN SIPSA_APROBA_INSUMOS.apin_prre_fecha_programada%TYPE,
 p_articaco_id IN SIPSA_APROBA_INSUMOS.apin_articaco_id%TYPE,
 p_arti_id       IN  SIPSA_APROBA_INSUMOS.apin_arti_id%TYPE,
 p_arti_nombre       IN  SIPSA_APROBA_INSUMOS.apin_arti_nombre%TYPE,
 p_caco_id       IN  SIPSA_APROBA_INSUMOS.apin_caco_id%TYPE,
 p_casa_comercial       IN  SIPSA_APROBA_INSUMOS.apin_casa_comercial%TYPE,
 p_regica_linea       IN  SIPSA_APROBA_INSUMOS.apin_regica_linea%TYPE,
 p_unme_id    IN SIPSA_APROBA_INSUMOS.unme_id%TYPE,
 p_precio_recolectado            IN SIPSA_APROBA_INSUMOS.apin_precio_recolectado%TYPE,
 p_novedad            IN SIPSA_APROBA_INSUMOS.apin_novedad%TYPE,
 p_obse_id            IN SIPSA_APROBA_INSUMOS.obse_id%TYPE,
 p_observacion           IN SIPSA_APROBA_INSUMOS.apin_observacion%TYPE,
 p_esta_id           IN SIPSA_APROBA_INSUMOS.esta_id%TYPE,
 p_fecha_recoleccion IN SIPSA_APROBA_INSUMOS.apin_fecha_recoleccion%TYPE,
 p_usuario                       IN SIPSA_APROBA_INSUMOS.apin_usuario_creacion%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);


PROCEDURE pr_ActualizarIA
(p_fuen_id               IN SIPSA_APROBA_INSUMOS.apin_fuen_id%TYPE, 
 p_futi_id               IN OUT SIPSA_APROBA_INSUMOS.apin_futi_id%TYPE,
   p_tire_id   IN  SIPSA_APROBA_INSUMOS.tire_id%TYPE,
 p_uspe_id   IN  SIPSA_APROBA_INSUMOS.uspe_id%TYPE,
 p_prre_fecha_programada IN SIPSA_APROBA_INSUMOS.apin_prre_fecha_programada%TYPE,
 p_articaco_id IN SIPSA_APROBA_INSUMOS.apin_articaco_id%TYPE,
 p_arti_id       IN  SIPSA_APROBA_INSUMOS.apin_arti_id%TYPE,
 p_arti_nombre       IN  SIPSA_APROBA_INSUMOS.apin_arti_nombre%TYPE,
 p_caco_id       IN  SIPSA_APROBA_INSUMOS.apin_caco_id%TYPE,
 p_casa_comercial       IN  SIPSA_APROBA_INSUMOS.apin_casa_comercial%TYPE,
 p_regica_linea       IN  SIPSA_APROBA_INSUMOS.apin_regica_linea%TYPE,
 p_unme_id    IN SIPSA_APROBA_INSUMOS.unme_id%TYPE,
 p_precio_recolectado            IN SIPSA_APROBA_INSUMOS.apin_precio_recolectado%TYPE,
 p_novedad            IN SIPSA_APROBA_INSUMOS.apin_novedad%TYPE,
 p_obse_id            IN SIPSA_APROBA_INSUMOS.obse_id%TYPE,
 p_observacion           IN SIPSA_APROBA_INSUMOS.apin_observacion%TYPE,
 p_esta_id           IN SIPSA_APROBA_INSUMOS.esta_id%TYPE,
 p_fecha_recoleccion IN SIPSA_APROBA_INSUMOS.apin_fecha_recoleccion%TYPE,
 p_usuario                       IN SIPSA_APROBA_INSUMOS.apin_usuario_modificacion%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);


PROCEDURE pr_InsertarIAFI
( 
  p_fuen_id               IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_id%TYPE,
  p_futi_id               IN  SIPSA_APROBA_FUEN_INFO.apfuin_futi_id%TYPE,
  p_muni_id IN SIPSA_APROBA_FUEN_INFO.muni_id%TYPE,
  p_tire_id IN SIPSA_APROBA_FUEN_INFO.tire_id%TYPE,
  p_uspe_id   IN  SIPSA_APROBA_FUEN_INFO.uspe_id%TYPE,
  p_fecha_visita  IN SIPSA_APROBA_FUEN_INFO.apfuin_fecha_visita%TYPE,
  p_fuen_nombre       IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_nombre%TYPE,
  p_fuen_direccion       IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_direccion%TYPE,
  p_fuen_telefono       IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_telefono%TYPE,
  p_fuen_email       IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_email%TYPE,
  p_info_nombre       IN SIPSA_APROBA_FUEN_INFO.apfuin_info_nombre%TYPE,
  p_info_cargo       IN SIPSA_APROBA_FUEN_INFO.apfuin_info_cargo%TYPE,
  p_info_telefono       IN SIPSA_APROBA_FUEN_INFO.apfuin_info_telefono%TYPE,
  p_info_email       IN SIPSA_APROBA_FUEN_INFO.apfuin_info_email%TYPE,
  p_usuario                       IN SIPSA_APROBA_FUEN_INFO.apfuin_usuario_creacion%TYPE,
  p_codigo_error                  OUT NUMBER,
  p_mensaje                       OUT VARCHAR2);
  
  

PROCEDURE pr_ActualizarIAFI
( 
  p_fuen_id               IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_id%TYPE,
  p_futi_id               IN  SIPSA_APROBA_FUEN_INFO.apfuin_futi_id%TYPE,
  p_muni_id IN SIPSA_APROBA_FUEN_INFO.muni_id%TYPE,
  p_tire_id IN SIPSA_APROBA_FUEN_INFO.tire_id%TYPE,
  p_uspe_id   IN  SIPSA_APROBA_FUEN_INFO.uspe_id%TYPE,
  p_fecha_visita  IN SIPSA_APROBA_FUEN_INFO.apfuin_fecha_visita%TYPE,
  p_fuen_nombre       IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_nombre%TYPE,
  p_fuen_direccion       IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_direccion%TYPE,
  p_fuen_telefono       IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_telefono%TYPE,
  p_fuen_email       IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_email%TYPE,
  p_info_nombre       IN SIPSA_APROBA_FUEN_INFO.apfuin_info_nombre%TYPE,
  p_info_cargo       IN SIPSA_APROBA_FUEN_INFO.apfuin_info_cargo%TYPE,
  p_info_telefono       IN SIPSA_APROBA_FUEN_INFO.apfuin_info_telefono%TYPE,
  p_info_email       IN SIPSA_APROBA_FUEN_INFO.apfuin_info_email%TYPE,
  p_usuario                       IN SIPSA_APROBA_FUEN_INFO.apfuin_usuario_modificacion%TYPE,
  p_codigo_error                  OUT NUMBER,
  p_mensaje                       OUT VARCHAR2);  
  
PROCEDURE pr_InsertarAPGRIN2( 
  p_futi_id               IN  SIPSA_APROBA_GRIN2.futi_id%TYPE,
  p_uspe_id   IN  SIPSA_APROBA_GRIN2.uspe_id%TYPE,
  p_apgrin2_id IN SIPSA_APROBA_GRIN2.apgrin2_id%TYPE,
  p_arti_id IN SIPSA_APROBA_GRIN2.arti_id%TYPE,
  p_usuario                       IN SIPSA_APROBA_GRIN2.apgrin2_usuario_creacion%TYPE,
  p_codigo_error                  OUT NUMBER,
  p_mensaje                       OUT VARCHAR2);
  
  PROCEDURE pr_ActualizarAPGRIN2( 
  p_futi_id               IN  SIPSA_APROBA_GRIN2.futi_id%TYPE,
  p_uspe_id             IN  SIPSA_APROBA_GRIN2.uspe_id%TYPE,
  p_apgrin2_id         IN SIPSA_APROBA_GRIN2.apgrin2_id%TYPE,
  p_arti_id               IN SIPSA_APROBA_GRIN2.arti_id%TYPE,
  p_usuario              IN SIPSA_APROBA_GRIN2.apgrin2_usuario_modificacion%TYPE,
  p_codigo_error      OUT NUMBER,
  p_mensaje            OUT VARCHAR2);
  
PROCEDURE pr_InsertarAPGRIN2_VACA( 
  p_futi_id               IN  SIPSA_APROBA_GRIN2_VACA.futi_id%TYPE,
  p_uspe_id   IN  SIPSA_APROBA_GRIN2_VACA.uspe_id%TYPE,
  p_apgrin2_id IN SIPSA_APROBA_GRIN2_VACA.apgrin2_id%TYPE,
  p_cara_id IN SIPSA_APROBA_GRIN2_VACA.cara_id%TYPE,  
  p_vape_id IN SIPSA_APROBA_GRIN2_VACA.vape_id%TYPE,
  p_valor IN SIPSA_APROBA_GRIN2_VACA.valor%TYPE,
  p_usuario                       IN SIPSA_APROBA_GRIN2_VACA.apgrin2vaca_usua_creacion%TYPE,
  p_codigo_error                  OUT NUMBER,
  p_mensaje                       OUT VARCHAR2);

PROCEDURE pr_ActualizarAPGRIN2_VACA( 
  p_futi_id               IN  SIPSA_APROBA_GRIN2_VACA.futi_id%TYPE,
  p_uspe_id   IN  SIPSA_APROBA_GRIN2_VACA.uspe_id%TYPE,
  p_apgrin2_id IN SIPSA_APROBA_GRIN2_VACA.apgrin2_id%TYPE,
  p_cara_id IN SIPSA_APROBA_GRIN2_VACA.cara_id%TYPE,  
  p_vape_id IN SIPSA_APROBA_GRIN2_VACA.vape_id%TYPE,
  p_valor IN SIPSA_APROBA_GRIN2_VACA.valor%TYPE,
  p_usuario                       IN SIPSA_APROBA_GRIN2_VACA.apgrin2vaca_usua_modificacion%TYPE,
  p_codigo_error                  OUT NUMBER,
  p_mensaje                       OUT VARCHAR2);
  
PROCEDURE pr_InsertarAPENCI2
(p_futi_id                       IN SIPSA_APROBA_ENCA_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_APROBA_ENCA_INSUMOS2.uspe_id%TYPE,
 p_arti_id                      IN SIPSA_APROBA_ENCA_INSUMOS2.arti_id%TYPE,
 p_grin2_id                    IN SIPSA_APROBA_ENCA_INSUMOS2.apgrin2_id%TYPE,
 p_prre_fecha_programada     IN SIPSA_APROBA_ENCA_INSUMOS2.prre_fecha_programada%TYPE,
 p_arti_nombre                   IN SIPSA_TMP_ENCA_INSUMOS2.enin2_arti_nombre%TYPE,
 p_novedad                      IN SIPSA_APROBA_ENCA_INSUMOS2.apenin2_novedad%TYPE,
 p_ftes_capturadas        IN SIPSA_APROBA_ENCA_INSUMOS2.apenin2_ftes_capturadas%TYPE,    
 p_obse_id                      IN  SIPSA_APROBA_ENCA_INSUMOS2.obse_id%TYPE,
 p_observacion              IN SIPSA_APROBA_ENCA_INSUMOS2.apenin2_observacion%TYPE,      
  p_usuario                       IN SIPSA_APROBA_ENCA_INSUMOS2.apenin2_usuario_creacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

PROCEDURE pr_ActualizarAPENCI2
(p_futi_id                       IN SIPSA_APROBA_ENCA_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_APROBA_ENCA_INSUMOS2.uspe_id%TYPE,
 p_arti_id                      IN SIPSA_APROBA_ENCA_INSUMOS2.arti_id%TYPE,
 p_grin2_id                    IN SIPSA_APROBA_ENCA_INSUMOS2.apgrin2_id%TYPE,
 p_prre_fecha_programada     IN SIPSA_APROBA_ENCA_INSUMOS2.prre_fecha_programada%TYPE,
 p_arti_nombre                   IN SIPSA_TMP_ENCA_INSUMOS2.enin2_arti_nombre%TYPE,
 p_novedad                      IN SIPSA_APROBA_ENCA_INSUMOS2.apenin2_novedad%TYPE,
 p_ftes_capturadas        IN SIPSA_APROBA_ENCA_INSUMOS2.apenin2_ftes_capturadas%TYPE,    
 p_obse_id                      IN  SIPSA_APROBA_ENCA_INSUMOS2.obse_id%TYPE,
 p_observacion              IN SIPSA_APROBA_ENCA_INSUMOS2.apenin2_observacion%TYPE,      
  p_usuario                       IN SIPSA_APROBA_ENCA_INSUMOS2.apenin2_usuario_modificacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);


 
PROCEDURE pr_InsertarAPRECI2
(p_futi_id                       IN SIPSA_APROBA_RECO_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_APROBA_RECO_INSUMOS2.uspe_id%TYPE,
 p_arti_id                      IN SIPSA_APROBA_RECO_INSUMOS2.arti_id%TYPE,
 p_grin2_id                    IN SIPSA_APROBA_RECO_INSUMOS2.apgrin2_id%TYPE,
 p_prre_fecha_programada     IN SIPSA_APROBA_RECO_INSUMOS2.prre_fecha_programada%TYPE,
 p_novedad_gral  IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_novedad_gral%TYPE,
 p_id_informante     IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_id_informante%TYPE,
 p_nom_informante           IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_nom_informante%TYPE,
 p_tel_informante           IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_tel_informante%TYPE,
 p_precio_recolectado                      IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_precio_recolectado%TYPE,
 p_novedad  IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_novedad%TYPE,
 p_obse_id                      IN  SIPSA_APROBA_RECO_INSUMOS2.obse_id%TYPE,
 p_rein2_observacion              IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_observacion%TYPE,
 p_fecha_recoleccion  IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_fecha_recoleccion%TYPE,
 p_esta_id           IN SIPSA_APROBA_RECO_INSUMOS2.esta_id%TYPE,
 p_usuario                       IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_usuario_creacion%TYPE,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);
 
 PROCEDURE pr_ActualizarAPRECI2
(p_futi_id                       IN SIPSA_APROBA_RECO_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_APROBA_RECO_INSUMOS2.uspe_id%TYPE,
 p_arti_id                      IN SIPSA_APROBA_RECO_INSUMOS2.arti_id%TYPE,
 p_grin2_id                    IN SIPSA_APROBA_RECO_INSUMOS2.apgrin2_id%TYPE,
 p_prre_fecha_programada     IN SIPSA_APROBA_RECO_INSUMOS2.prre_fecha_programada%TYPE,
 p_novedad_gral  IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_novedad_gral%TYPE,
 p_id_informante     IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_id_informante%TYPE,
 p_nom_informante           IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_nom_informante%TYPE,
 p_tel_informante           IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_tel_informante%TYPE,
 p_precio_recolectado                      IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_precio_recolectado%TYPE,
 p_novedad  IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_novedad%TYPE,
 p_obse_id                      IN  SIPSA_APROBA_RECO_INSUMOS2.obse_id%TYPE,
 p_rein2_observacion              IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_observacion%TYPE,
 p_fecha_recoleccion  IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_fecha_recoleccion%TYPE,
 p_esta_id           IN SIPSA_APROBA_RECO_INSUMOS2.esta_id%TYPE,
 p_usuario                       IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_usuario_modificacion%TYPE,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);
 
 PROCEDURE pr_ConsultarAprobaInsumos
(
p_prre_fecha_programada_ini IN VARCHAR2,
p_prre_fecha_programada_fin IN VARCHAR2,
p_tire_id                   IN sipsa_tipo_recolecciones.tire_id%TYPE,
p_resultado                 OUT Pq_Gen_General.ty_cursor,
p_codigo_error              OUT NUMBER,
p_mensaje                   OUT VARCHAR2
); 

PROCEDURE pr_AprobarInsumos1
(

p_futi_id                   IN sipsa_aproba_fuen_info.apfuin_futi_id%TYPE,
p_fuen_id                   IN sipsa_aproba_fuen_info.apfuin_fuen_id%TYPE,
p_tire_id                   IN sipsa_aproba_fuen_info.tire_id%TYPE,
p_muni_id                   IN sipsa_aproba_fuen_info.muni_id%TYPE,
p_uspe_id                   IN sipsa_aproba_fuen_info.uspe_id%TYPE,
p_fecha_visita              IN sipsa_aproba_fuen_info.apfuin_fecha_visita%TYPE,
p_fuen_nombre               IN sipsa_aproba_fuen_info.apfuin_fuen_nombre%TYPE,
p_fuen_cod_interno          IN sipsa_fuentes.fuen_cod_interno%TYPE,
p_fuen_direccion            IN sipsa_aproba_fuen_info.apfuin_fuen_direccion%TYPE,
p_fuen_telefono             IN sipsa_aproba_fuen_info.apfuin_fuen_telefono%TYPE,
p_fuen_email                IN sipsa_aproba_fuen_info.apfuin_fuen_email%TYPE,
p_fuen_info_nombre          IN sipsa_aproba_fuen_info.apfuin_info_nombre%TYPE,
p_fuen_info_cargo           IN sipsa_aproba_fuen_info.apfuin_info_cargo%TYPE,
p_fuen_info_telefono        IN sipsa_aproba_fuen_info.apfuin_info_telefono%TYPE,
p_fuen_info_email           IN sipsa_aproba_fuen_info.apfuin_info_email%TYPE,
p_usuario_creacion          IN sipsa_aproba_fuen_info.apfuin_usuario_creacion%TYPE,
p_prre_fecha_programada     IN sipsa_aproba_insumos.apin_prre_fecha_programada%TYPE,
p_articaco_id               IN sipsa_aproba_insumos.apin_articaco_id%TYPE,
p_grupo_id                  IN sipsa_articulos.grup_id%TYPE,
p_subgrupo_id               IN sipsa_articulos.subg_id%TYPE,
p_arti_cod_interno          IN sipsa_articulos.arti_cod_interno%TYPE,
p_arti_id                   IN sipsa_aproba_insumos.apin_arti_id%TYPE,
p_arti_nombre               IN sipsa_aproba_insumos.apin_arti_nombre%TYPE,
p_caco_id                   IN sipsa_aproba_insumos.apin_caco_id%TYPE,
p_casa_comercial            IN sipsa_aproba_insumos.apin_casa_comercial%TYPE,
p_regica_linea              IN sipsa_aproba_insumos.apin_regica_linea%TYPE,
p_unme_id                   IN sipsa_aproba_insumos.unme_id%TYPE,
p_tipr_id                   IN SIPSA_UNIDADES_MEDIDA.TIPR_ID%TYPE,
p_unme_nombre_ppal          IN SIPSA_UNIDADES_MEDIDA.UNME_NOMBRE_PPAL%TYPE,
p_unme_cantidad_ppal        IN SIPSA_UNIDADES_MEDIDA.UNME_CANTIDAD_PPAL%TYPE,
p_unme_nombre_2             IN SIPSA_UNIDADES_MEDIDA.UNME_NOMBRE_2%TYPE,
p_unme_cantidad_2           IN SIPSA_UNIDADES_MEDIDA.UNME_CANTIDAD_2%TYPE,
p_precio_recolectado        IN sipsa_aproba_insumos.apin_precio_recolectado%TYPE,
p_novedad                   IN sipsa_aproba_insumos.apin_novedad%TYPE,
p_obse_id                   IN sipsa_aproba_insumos.obse_id%TYPE,
p_observacion               IN sipsa_aproba_insumos.apin_observacion%TYPE,
p_fecha_recoleccion         IN sipsa_aproba_insumos.apin_fecha_recoleccion%TYPE,
p_usuario                   IN sipsa_aproba_insumos.apin_usuario_creacion%TYPE,
p_codigo_error              OUT NUMBER,
p_mensaje                   OUT VARCHAR2
);

PROCEDURE pr_EliminarInsumos1
(

p_futi_id                   IN sipsa_aproba_fuen_info.apfuin_futi_id%TYPE,
p_fuen_id                   IN sipsa_aproba_fuen_info.apfuin_fuen_id%TYPE,
p_tire_id                   IN sipsa_aproba_fuen_info.tire_id%TYPE,
p_muni_id                   IN sipsa_aproba_fuen_info.muni_id%TYPE,
p_uspe_id                   IN sipsa_aproba_fuen_info.uspe_id%TYPE,
p_fecha_visita              IN sipsa_aproba_fuen_info.apfuin_fecha_visita%TYPE,
p_prre_fecha_programada     IN sipsa_aproba_insumos.apin_prre_fecha_programada%TYPE,
p_caco_id                   IN sipsa_aproba_insumos.apin_caco_id%TYPE,
p_articaco_id               IN sipsa_aproba_insumos.apin_articaco_id%TYPE,
p_arti_id                   IN sipsa_aproba_insumos.apin_arti_id%TYPE,
p_unme_id                   IN sipsa_aproba_insumos.unme_id%TYPE,
p_usuario                   IN sipsa_aproba_insumos.apin_usuario_creacion%TYPE,
p_codigo_error              OUT NUMBER,
p_mensaje                   OUT VARCHAR2
);

PROCEDURE pr_AprobarInsumos2
(

p_futi_id                   IN sipsa_aproba_reco_insumos2.futi_id%TYPE,
p_fuen_id                   IN sipsa_fuentes.fuen_id%TYPE,
p_muni_id                   IN sipsa_municipios.muni_id%TYPE,
p_tire_id                   IN sipsa_fuentes_tipo_recoleccion.tire_id%TYPE,
p_uspe_id                   IN sipsa_aproba_reco_insumos2.uspe_id%TYPE,
p_usuario_creacion          IN sipsa_aproba_reco_insumos2.aprein2_usuario_creacion%TYPE,
p_prre_fecha_programada     IN sipsa_aproba_reco_insumos2.prre_fecha_programada%TYPE,
p_id_informante             IN sipsa_aproba_reco_insumos2.aprein2_id_informante%TYPE,
p_nom_informante            IN sipsa_recoleccion_insumos2.rein2_nom_informante%TYPE,
p_tel_informante            IN sipsa_recoleccion_insumos2.rein2_tel_informante%TYPE,
p_grupo_id                  IN sipsa_articulos.grup_id%TYPE,
p_subgrupo_id               IN sipsa_articulos.subg_id%TYPE,
p_arti_cod_interno          IN sipsa_articulos.arti_cod_interno%TYPE,
p_arti_id_old               IN sipsa_aproba_reco_insumos2.arti_id%TYPE,
p_arti_id                   IN sipsa_aproba_reco_insumos2.arti_id%TYPE,
p_arti_nombre               IN sipsa_aproba_enca_insumos2.apenin2_arti_nombre%TYPE,
p_grin2_id_old              IN sipsa_aproba_reco_insumos2.apgrin2_id%TYPE,
p_grin2_id                  IN sipsa_aproba_reco_insumos2.apgrin2_id%TYPE,
p_precio_recolectado        IN sipsa_aproba_reco_insumos2.aprein2_precio_recolectado%TYPE,
p_novedad                   IN sipsa_aproba_reco_insumos2.aprein2_novedad%TYPE,
p_obse_id                   IN sipsa_aproba_reco_insumos2.obse_id%TYPE,
p_observacion               IN sipsa_aproba_reco_insumos2.aprein2_observacion%TYPE,
p_fecha_recoleccion         IN sipsa_aproba_reco_insumos2.aprein2_fecha_recoleccion%TYPE,
p_caracteristicas           IN VARCHAR2,
p_usuario                   IN sipsa_aproba_reco_insumos2.aprein2_usuario_creacion%TYPE,
p_codigo_error              OUT NUMBER,
p_mensaje                   OUT VARCHAR2
);
 
PROCEDURE pr_EliminarInsumos2
(

p_futi_id                   IN sipsa_aproba_reco_insumos2.futi_id%TYPE,
p_uspe_id                   IN sipsa_aproba_reco_insumos2.uspe_id%TYPE,
p_prre_fecha_programada     IN sipsa_aproba_reco_insumos2.prre_fecha_programada%TYPE,
p_id_informante             IN sipsa_aproba_reco_insumos2.aprein2_id_informante%TYPE,
p_arti_id                   IN sipsa_aproba_reco_insumos2.arti_id%TYPE,
p_grin2_id                  IN sipsa_aproba_reco_insumos2.apgrin2_id%TYPE,
p_usuario                   IN sipsa_aproba_insumos.apin_usuario_creacion%TYPE,
p_codigo_error              OUT NUMBER,
p_mensaje                   OUT VARCHAR2
);

PROCEDURE pr_InsertarIA_DISTRITO
(p_fuen_id               IN SIPSA_APROBA_INSUMOS.apin_fuen_id%TYPE, 
 p_futi_id               IN OUT SIPSA_APROBA_INSUMOS.apin_futi_id%TYPE,
   p_tire_id   IN  SIPSA_APROBA_INSUMOS.tire_id%TYPE,
 p_uspe_id   IN  SIPSA_APROBA_INSUMOS.uspe_id%TYPE,
 p_prre_fecha_programada IN SIPSA_APROBA_INSUMOS.apin_prre_fecha_programada%TYPE,
 p_articaco_id IN SIPSA_APROBA_INSUMOS.apin_articaco_id%TYPE,
 p_arti_id       IN  SIPSA_APROBA_INSUMOS.apin_arti_id%TYPE,
 p_arti_nombre       IN  SIPSA_APROBA_INSUMOS.apin_arti_nombre%TYPE,
 p_caco_id       IN  SIPSA_APROBA_INSUMOS.apin_caco_id%TYPE,
 p_casa_comercial       IN  SIPSA_APROBA_INSUMOS.apin_casa_comercial%TYPE,
 p_regica_linea       IN  SIPSA_APROBA_INSUMOS.apin_regica_linea%TYPE,
 p_unme_id    IN SIPSA_APROBA_INSUMOS.unme_id%TYPE,
 p_precio_recolectado            IN SIPSA_APROBA_INSUMOS.apin_precio_recolectado%TYPE,
 p_novedad            IN SIPSA_APROBA_INSUMOS.apin_novedad%TYPE,
 p_obse_id            IN SIPSA_APROBA_INSUMOS.obse_id%TYPE,
 p_observacion           IN SIPSA_APROBA_INSUMOS.apin_observacion%TYPE,
 p_esta_id           IN SIPSA_APROBA_INSUMOS.esta_id%TYPE,
 p_fecha_recoleccion IN SIPSA_APROBA_INSUMOS.apin_fecha_recoleccion%TYPE,
 p_usuario                       IN SIPSA_APROBA_INSUMOS.apin_usuario_creacion%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);
 
 
PROCEDURE pr_InsertarIAFI_DISTRITO
( 
  p_fuen_id               IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_id%TYPE,
  p_futi_id               IN  SIPSA_APROBA_FUEN_INFO.apfuin_futi_id%TYPE,
  p_muni_id IN SIPSA_APROBA_FUEN_INFO.muni_id%TYPE,
  p_tire_id IN SIPSA_APROBA_FUEN_INFO.tire_id%TYPE,
  p_uspe_id   IN  SIPSA_APROBA_FUEN_INFO.uspe_id%TYPE,
  p_fecha_visita  IN SIPSA_APROBA_FUEN_INFO.apfuin_fecha_visita%TYPE,
  p_fuen_nombre       IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_nombre%TYPE,
  p_fuen_direccion       IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_direccion%TYPE,
  p_fuen_telefono       IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_telefono%TYPE,
  p_fuen_email       IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_email%TYPE,
  p_info_nombre       IN SIPSA_APROBA_FUEN_INFO.apfuin_info_nombre%TYPE,
  p_info_cargo       IN SIPSA_APROBA_FUEN_INFO.apfuin_info_cargo%TYPE,
  p_info_telefono       IN SIPSA_APROBA_FUEN_INFO.apfuin_info_telefono%TYPE,
  p_info_email       IN SIPSA_APROBA_FUEN_INFO.apfuin_info_email%TYPE,
  p_usuario                       IN SIPSA_APROBA_FUEN_INFO.apfuin_usuario_creacion%TYPE,
  p_codigo_error                  OUT NUMBER,
  p_mensaje                       OUT VARCHAR2);

PROCEDURE pr_InsertarAPENIN_D
 (p_futi_id                       IN SIPSA_APROBA_ENCA_INSUMOS_D.FUTI_ID%TYPE,
 p_uspe_id                      IN SIPSA_APROBA_ENCA_INSUMOS_D.USPE_ID%TYPE,
 p_arti_id                      IN SIPSA_APROBA_ENCA_INSUMOS_D.ARTI_ID%TYPE,
 p_grin2_id                    IN SIPSA_APROBA_ENCA_INSUMOS_D.GRIN_ID%TYPE,
 p_prre_fecha_programada     IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_FECHA_PROGRAMADA%TYPE,
 p_arti_nombre                   IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_ARTI_NOMBRE%TYPE,
 p_novedad                      IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_NOVEDAD%TYPE,
 p_obse_id                      IN  SIPSA_APROBA_ENCA_INSUMOS_D.OBSE_ID%TYPE,
 p_observacion              IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_OBSERVACION%TYPE,      
 p_usuario                       IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_USUARIO_CREACION%TYPE,  

 p_nom_fuente               IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_NOMBRE_FUENTE%TYPE,    
 p_municipio                IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_MUNICIPIO%TYPE,    
 p_muni_id                  IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_MUNI_ID%TYPE,    
 p_direccion                IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_DIRECCION%TYPE,    
 p_telefono                 IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_TELEFONO%TYPE,    
 p_email                    IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_EMAIL%TYPE,    
 p_informante               IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_INFORMANTE%TYPE,    
 p_tel_informante           IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_TEL_INFORMANTE%TYPE, 
 
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

PROCEDURE pr_ActualizarAPENIN_D
(p_futi_id                       IN SIPSA_APROBA_ENCA_INSUMOS_D.FUTI_ID%TYPE,
 p_uspe_id                      IN SIPSA_APROBA_ENCA_INSUMOS_D.USPE_ID%TYPE,
 p_arti_id                      IN SIPSA_APROBA_ENCA_INSUMOS_D.ARTI_ID%TYPE,
 p_grin2_id                    IN SIPSA_APROBA_ENCA_INSUMOS_D.GRIN_ID%TYPE,
 p_prre_fecha_programada     IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_FECHA_PROGRAMADA%TYPE,
 p_arti_nombre                   IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_ARTI_NOMBRE%TYPE,
 p_novedad                      IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_NOVEDAD%TYPE,  
 p_obse_id                      IN  SIPSA_APROBA_ENCA_INSUMOS_D.OBSE_ID%TYPE,
 p_observacion              IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_OBSERVACION%TYPE,      
 p_usuario                       IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_USUARIO_modificacion%TYPE,  

 p_nom_fuente               IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_NOMBRE_FUENTE%TYPE,    
 p_municipio                IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_MUNICIPIO%TYPE,    
 p_muni_id                  IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_MUNI_ID%TYPE,    
 p_direccion                IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_DIRECCION%TYPE,    
 p_telefono                 IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_TELEFONO%TYPE,    
 p_email                    IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_EMAIL%TYPE,    
 p_informante               IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_INFORMANTE%TYPE,    
 p_tel_informante           IN SIPSA_APROBA_ENCA_INSUMOS_D.ENIN_TEL_INFORMANTE%TYPE, 
 
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);
 
PROCEDURE pr_InsertarAPRECI2_D
(p_futi_id                  IN SIPSA_APROBA_RECO_INSUMOS_D.FUTI_ID%TYPE,
 p_uspe_id                  IN SIPSA_APROBA_RECO_INSUMOS_D.USPE_ID%TYPE,
 p_arti_id                  IN SIPSA_APROBA_RECO_INSUMOS_D.ARTI_ID%TYPE,
 p_grin2_id                 IN SIPSA_APROBA_RECO_INSUMOS_D.GRIN_ID%TYPE,
 p_prre_fecha_programada    IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_FECHA_PROGRAMADA%TYPE,
 p_id_informante            IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_ID_INFORMANTE%TYPE,
 p_nom_informante           IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_NOM_INFORMANTE%TYPE,
 p_tel_informante           IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_TEL_INFORMANTE%TYPE,
 p_precio_recolectado       IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_PRECIO_RECOLECTADO%TYPE,
 p_novedad  				        IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_NOVEDAD%TYPE,
 p_obse_id                  IN  SIPSA_APROBA_RECO_INSUMOS_D.OBSE_ID%TYPE,
 p_rein2_observacion        IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_OBSERVACION%TYPE,
 p_fecha_recoleccion 		    IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_FECHA_RECOLECCION%TYPE,
 p_esta_id 				          IN SIPSA_APROBA_RECO_INSUMOS_D.ESTA_ID%TYPE,
 p_usuario                  IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_USUARIO_CREACION%TYPE,
 
 p_nom_fuente               IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_NOMBRE_FUENTE%TYPE,    
 p_municipio                IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_MUNICIPIO%TYPE,    
 p_muni_id                  IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_MUNI_ID%TYPE,    
 p_direccion                IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_DIRECCION%TYPE,    
 p_telefono                 IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_TELEFONO%TYPE,    
 p_email                    IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_EMAIL%TYPE,    
 p_informante               IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_INFORMANTE%TYPE,    
 
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

 PROCEDURE pr_ActualizarAPRECI2_D
(p_futi_id                  IN SIPSA_APROBA_RECO_INSUMOS_D.FUTI_ID%TYPE,
 p_uspe_id                  IN SIPSA_APROBA_RECO_INSUMOS_D.USPE_ID%TYPE,
 p_arti_id                  IN SIPSA_APROBA_RECO_INSUMOS_D.ARTI_ID%TYPE,
 p_grin2_id                 IN SIPSA_APROBA_RECO_INSUMOS_D.GRIN_ID%TYPE,
 p_prre_fecha_programada    IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_FECHA_PROGRAMADA%TYPE,
 p_id_informante     		    IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_ID_INFORMANTE%TYPE,
 p_nom_informante           IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_NOM_INFORMANTE%TYPE,
 p_tel_informante           IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_TEL_INFORMANTE%TYPE,
 p_precio_recolectado       IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_PRECIO_RECOLECTADO%TYPE,
 p_novedad  				        IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_NOVEDAD%TYPE,
 p_obse_id                  IN  SIPSA_APROBA_RECO_INSUMOS_D.OBSE_ID%TYPE,
 p_rein2_observacion        IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_OBSERVACION%TYPE,
 p_fecha_recoleccion  	    IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_FECHA_RECOLECCION%TYPE,
 p_esta_id           	      IN SIPSA_APROBA_RECO_INSUMOS_D.ESTA_ID%TYPE,
 p_usuario                  IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_USUARIO_MODIFICACION%TYPE,
 
 p_nom_fuente               IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_NOMBRE_FUENTE%TYPE,    
 p_municipio                IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_MUNICIPIO%TYPE,    
 p_muni_id                  IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_MUNI_ID%TYPE,    
 p_direccion                IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_DIRECCION%TYPE,    
 p_telefono                 IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_TELEFONO%TYPE,    
 p_email                    IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_EMAIL%TYPE,    
 p_informante               IN SIPSA_APROBA_RECO_INSUMOS_D.APRE_INFORMANTE%TYPE,    
  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

  
 
END PQ_SIPSA_APROBACIONES;