create or replace PACKAGE            PQ_SIPSA_RECOLECCION AUTHID CURRENT_USER IS

/********************************************************************************
 DESCRIPCION   : Contiene los metodos sobre las tablas SIPSA_RECOLECCION_MAYORISTAS,SIPSA_RECOLECCION_ABASTE

 REALIZADO POR : Vitaliano Corredor

 FECHA CREACION: 22/07/2012

 MODIFICADO POR: 
 FECHA MODIFICA: 
 CAMBIOS       : 
 *******************************************************************************/

 --cn_activo                   CONSTANT VARCHAR2(10) := 'ACTIVO';
 --cn_calculo                  CONSTANT CHAR         := 'C';
 --cn_recoleccion              CONSTANT CHAR         := 'R';
 --cn_cambio_referencia        CONSTANT VARCHAR2(10) := 'CR';
 --cn_insumo_sale              CONSTANT VARCHAR2(10) := 'IS';
 --cn_insumo_nuevo             CONSTANT VARCHAR2(10) := 'IN';
 --cn_disponible_para_calculo  CONSTANT VARCHAR2(50) := 'DISPONIBLE PARA CALCULO';
 --cn_periodo_espera           CONSTANT VARCHAR2(10) := 'PE';
 --cn_grupo_fuente_1           CONSTANT VARCHAR2(10) := '1';
 --cn_grupo_fuente_2           CONSTANT VARCHAR2(10) := '2';
 --cn_grupo_fuente_3           CONSTANT VARCHAR2(10) := '3';
 --cn_recolectado              CONSTANT VARCHAR2(50) := 'RECOLECTADO';
 --cn_supervisado              CONSTANT VARCHAR2(50) := 'SUPERVISADO';
 --cn_control_calidad_local    CONSTANT VARCHAR2(50) := 'CONTROL DE CALIDAD LOCAL';
 --cn_para_super_desde_local   CONSTANT VARCHAR2(50) := 'PARA SUPERVISION DESDE LOCAL';
 --cn_analizado_local          CONSTANT VARCHAR2(50) := 'ANALIZADO LOCAL';
 --cn_dispo_para_nivel_central CONSTANT VARCHAR2(50) := 'DISPONIBLE PARA NIVEL CENTRAL';
 --cn_control_de_calid_central CONSTANT VARCHAR2(50) := 'CONTROL DE CALIDAD CENTRAL';
 --cn_para_super_desde_central CONSTANT VARCHAR2(50) := 'PARA SUPERVISION DESDE CENTRAL';
 --cn_analizado_central        CONSTANT VARCHAR2(50) := 'ANALIZADO CENTRAL';
 --cn_en_super_desde_local     CONSTANT VARCHAR2(50) := 'EN SUPERVISION DESDE LOCAL';
 --cn_para_veri_nivel_central  CONSTANT VARCHAR2(50) := 'PARA VERIFICAR NIVEL CENTRAL';
 --cn_inactivo                 CONSTANT VARCHAR2(50) := 'INACTIVO';
 --cn_en_recoleccion           CONSTANT VARCHAR2(50) := 'EN RECOLECCION';
 --cn_revi_central             CONSTANT VARCHAR2(50) := 'REVISADO CENTRAL';
 --cn_pendiente                CONSTANT VARCHAR2(10) := 'PENDIENTE';
 --cn_enviado                  CONSTANT VARCHAR2(10) := 'ENVIADO';
 --cn_no_entra_calculo         CONSTANT VARCHAR2(50) := 'NO ENTRA AL CALCULO';
 --cn_operativo                CONSTANT VARCHAR2(50) := 'OPERATIVO';

 

 PROCEDURE pr_autenticar
(p_usua_usuario     IN SIPSA_USUARIOS.usua_usuario%TYPE,
 p_usua_id     OUT SIPSA_USUARIOS.usua_id%TYPE,
 p_nombre         OUT VARCHAR2,
 p_clave          OUT SIPSA_USUARIOS.usua_password%TYPE,
 p_fechas         OUT PQ_GEN_GENERAL.ty_cursor,
 p_codigo_error   OUT NUMBER,
 p_mensaje        OUT VARCHAR2);

PROCEDURE pr_InsertarM
( p_futi_id               IN OUT SIPSA_RECOLECCION_MAYORISTAS.futi_id%TYPE,
 p_uspe_id   IN  SIPSA_RECOLECCION_MAYORISTAS.uspe_id%TYPE,
 p_arti_id       IN OUT SIPSA_RECOLECCION_MAYORISTAS.arti_id%TYPE,
 p_unme_id    IN OUT SIPSA_RECOLECCION_MAYORISTAS.unme_id%TYPE,
 p_prre_fecha_programada IN OUT SIPSA_RECOLECCION_MAYORISTAS.prre_fecha_programada%TYPE,
 p_enma_ronda                   IN OUT SIPSA_RECOLECCION_MAYORISTAS.enma_ronda%TYPE,
 p_rema_precio_prom_anterior            IN SIPSA_RECOLECCION_MAYORISTAS.rema_precio_prom_anterior%TYPE,
 p_rema_toma1               IN SIPSA_RECOLECCION_MAYORISTAS.rema_toma1%TYPE,
 p_rema_toma2                  IN SIPSA_RECOLECCION_MAYORISTAS.rema_toma2%TYPE,
 p_rema_toma3              IN SIPSA_RECOLECCION_MAYORISTAS.rema_toma3%TYPE,
 p_rema_toma4             IN SIPSA_RECOLECCION_MAYORISTAS.rema_toma4%TYPE,
 p_rema_observacion_ronda           IN SIPSA_RECOLECCION_MAYORISTAS.rema_observacion_ronda%TYPE,
 p_esta_id                          IN SIPSA_RECOLECCION_MAYORISTAS.esta_id%TYPE, 
 p_usuario                       IN SIPSA_RECOLECCION_MAYORISTAS.rema_usuario_creacion%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);

PROCEDURE pr_ActualizarM
( p_futi_id               IN OUT SIPSA_RECOLECCION_MAYORISTAS.futi_id%TYPE,
 p_uspe_id   IN  SIPSA_RECOLECCION_MAYORISTAS.uspe_id%TYPE,
 p_arti_id       IN OUT SIPSA_RECOLECCION_MAYORISTAS.arti_id%TYPE,
 p_unme_id    IN OUT SIPSA_RECOLECCION_MAYORISTAS.unme_id%TYPE,
 p_prre_fecha_programada IN OUT SIPSA_RECOLECCION_MAYORISTAS.prre_fecha_programada%TYPE,
 p_enma_ronda                   IN OUT SIPSA_RECOLECCION_MAYORISTAS.enma_ronda%TYPE,
 p_rema_precio_prom_anterior            IN SIPSA_RECOLECCION_MAYORISTAS.rema_precio_prom_anterior%TYPE,
 p_rema_toma1               IN SIPSA_RECOLECCION_MAYORISTAS.rema_toma1%TYPE,
 p_rema_toma2                  IN SIPSA_RECOLECCION_MAYORISTAS.rema_toma2%TYPE,
 p_rema_toma3              IN SIPSA_RECOLECCION_MAYORISTAS.rema_toma3%TYPE,
 p_rema_toma4             IN SIPSA_RECOLECCION_MAYORISTAS.rema_toma4%TYPE,
 p_rema_observacion_ronda           IN SIPSA_RECOLECCION_MAYORISTAS.rema_observacion_ronda%TYPE,
 p_esta_id                          IN SIPSA_RECOLECCION_MAYORISTAS.esta_id%TYPE,
 p_usuario                       IN SIPSA_RECOLECCION_MAYORISTAS.rema_usuario_modificacion%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);

PROCEDURE Pr_CargarDMCRecoleccionM
( p_uspe_id IN NUMBER,
  --p_id_usuario         IN NUMBER,
  --p_periodo                   OUT PQ_GEN_GENERAL.ty_cursor,
  p_Principal               OUT PQ_GEN_GENERAL.ty_cursor,
  p_Fuentes                OUT PQ_GEN_GENERAL.ty_cursor,
  p_Persona                OUT PQ_GEN_GENERAL.ty_cursor,
  --p_ciudades                  OUT PQ_GEN_GENERAL.ty_cursor,
  --p_tipo_recoleccion                  OUT PQ_GEN_GENERAL.ty_cursor,
  p_codigo_error              OUT NUMBER,
  p_mensaje                   OUT VARCHAR2);

PROCEDURE Pr_IncorporarDMCM
(--p_sistema                   IN VARCHAR2,
 p_usuario                   IN SIPSA_RECOLECCION_MAYORISTAS.rema_usuario_modificacion%TYPE,
 --p_fuen_id                   IN SIPSA_RECOLECCION_MAYORISTAS.fuen_id%TYPE,
 p_codigo_error              OUT NUMBER,
 p_mensaje                   OUT VARCHAR2);

PROCEDURE pr_InsertarA
(p_futi_id                       IN SIPSA_RECOLECCION_ABASTE.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_RECOLECCION_ABASTE.uspe_id%TYPE,
 p_prre_fecha_programada                      IN SIPSA_RECOLECCION_ABASTE.prre_fecha_programada%TYPE,
 p_enab_placa                      IN SIPSA_RECOLECCION_ABASTE.enab_placa%TYPE,
 p_enab_hora                      IN SIPSA_RECOLECCION_ABASTE.enab_hora%TYPE,
 p_arti_id                      IN SIPSA_RECOLECCION_ABASTE.arti_id%TYPE,
 p_reab_cantidad                      IN SIPSA_RECOLECCION_ABASTE.reab_cantidad%TYPE,
 p_tipr_id                      IN SIPSA_RECOLECCION_ABASTE.tipr_id%TYPE,
 p_unme_id                      IN SIPSA_RECOLECCION_ABASTE.unme_id%TYPE,
 p_reab_cantidad_pr                      IN SIPSA_RECOLECCION_ABASTE.reab_cantidad_pr%TYPE,
 p_esta_id                          IN SIPSA_RECOLECCION_ABASTE.esta_id%TYPE, 
 p_usuario                       IN SIPSA_RECOLECCION_ABASTE.reab_usuario_creacion%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);

PROCEDURE pr_ActualizarA
(p_futi_id                       IN SIPSA_RECOLECCION_ABASTE.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_RECOLECCION_ABASTE.uspe_id%TYPE,
 p_prre_fecha_programada                      IN SIPSA_RECOLECCION_ABASTE.prre_fecha_programada%TYPE,
 p_enab_placa                      IN SIPSA_RECOLECCION_ABASTE.enab_placa%TYPE,
 p_enab_hora                      IN SIPSA_RECOLECCION_ABASTE.enab_hora%TYPE,
 p_arti_id                      IN SIPSA_RECOLECCION_ABASTE.arti_id%TYPE,
 p_reab_cantidad                      IN SIPSA_RECOLECCION_ABASTE.reab_cantidad%TYPE,
 p_tipr_id                      IN SIPSA_RECOLECCION_ABASTE.tipr_id%TYPE,
 p_unme_id                      IN SIPSA_RECOLECCION_ABASTE.unme_id%TYPE,
 p_reab_cantidad_pr                      IN SIPSA_RECOLECCION_ABASTE.reab_cantidad_pr%TYPE,
 p_esta_id                          IN SIPSA_RECOLECCION_ABASTE.esta_id%TYPE,  
 p_usuario                       IN SIPSA_RECOLECCION_ABASTE.reab_usuario_modificacion%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);

PROCEDURE Pr_CargarDMCRecoleccionA
( p_uspe_id IN NUMBER,
  --p_id_usuario         IN NUMBER,
  --p_periodo                   OUT PQ_GEN_GENERAL.ty_cursor,
  p_Fuentes                OUT PQ_GEN_GENERAL.ty_cursor,
  p_Persona                OUT PQ_GEN_GENERAL.ty_cursor,
  p_Articulos               OUT PQ_GEN_GENERAL.ty_cursor,
  p_Unidades               OUT PQ_GEN_GENERAL.ty_cursor,
  p_TiposVehiculo        OUT PQ_GEN_GENERAL.ty_cursor, 
  p_Municipios             OUT PQ_GEN_GENERAL.ty_cursor,
  p_TiposMercado        OUT PQ_GEN_GENERAL.ty_cursor, 
  --p_ciudades                  OUT PQ_GEN_GENERAL.ty_cursor,
  --p_tipo_recoleccion                  OUT PQ_GEN_GENERAL.ty_cursor,
  p_codigo_error              OUT NUMBER,
  p_mensaje                   OUT VARCHAR2);

 PROCEDURE Pr_IncorporarDMCA
(--p_sistema                   IN VARCHAR2,
 p_usuario                   IN SIPSA_RECOLECCION_ABASTE.reab_usuario_modificacion%TYPE,
 --p_fuen_id                   IN SIPSA_RECOLECCION_ABASTE.fuen_id%TYPE,
 p_codigo_error              OUT NUMBER,
 p_mensaje                   OUT VARCHAR2);

PROCEDURE pr_InsertarI
( p_futi_id               IN OUT SIPSA_RECOLECCION_INSUMOS.futi_id%TYPE,
 p_uspe_id   IN  SIPSA_RECOLECCION_INSUMOS.uspe_id%TYPE,
 p_prre_fecha_programada IN OUT SIPSA_RECOLECCION_INSUMOS.prre_fecha_programada%TYPE,
 p_articaco_id       IN OUT SIPSA_RECOLECCION_INSUMOS.articaco_id%TYPE,
 p_unme_id    IN OUT SIPSA_RECOLECCION_INSUMOS.unme_id%TYPE,
 p_rein_precio_anterior            IN SIPSA_RECOLECCION_INSUMOS.rein_precio_anterior%TYPE,
 p_rein_novedad_anterior            IN SIPSA_RECOLECCION_INSUMOS.rein_novedad_anterior%TYPE,
 p_rein_precio_recolectado            IN SIPSA_RECOLECCION_INSUMOS.rein_precio_recolectado%TYPE,
 p_rein_novedad            IN SIPSA_RECOLECCION_INSUMOS.rein_novedad%TYPE,
 p_obse_id            IN SIPSA_RECOLECCION_INSUMOS.obse_id%TYPE,
 p_rein_observacion           IN SIPSA_RECOLECCION_INSUMOS.rein_observacion%TYPE,
 p_esta_id            IN SIPSA_RECOLECCION_INSUMOS.esta_id%TYPE,
 p_fecha_recoleccion IN SIPSA_RECOLECCION_INSUMOS.rein_fecha_recoleccion%TYPE,
 p_usuario                       IN SIPSA_RECOLECCION_INSUMOS.rein_usuario_creacion%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);

PROCEDURE pr_ActualizarI
( p_futi_id               IN OUT SIPSA_RECOLECCION_INSUMOS.futi_id%TYPE,
 p_uspe_id   IN  SIPSA_RECOLECCION_INSUMOS.uspe_id%TYPE,
 p_prre_fecha_programada IN OUT SIPSA_RECOLECCION_INSUMOS.prre_fecha_programada%TYPE,
 p_articaco_id       IN OUT SIPSA_RECOLECCION_INSUMOS.articaco_id%TYPE,
 p_unme_id    IN OUT SIPSA_RECOLECCION_INSUMOS.unme_id%TYPE,
 p_rein_precio_anterior            IN SIPSA_RECOLECCION_INSUMOS.rein_precio_anterior%TYPE,
 p_rein_novedad_anterior            IN SIPSA_RECOLECCION_INSUMOS.rein_novedad_anterior%TYPE,
 p_rein_precio_recolectado            IN SIPSA_RECOLECCION_INSUMOS.rein_precio_recolectado%TYPE,
 p_rein_novedad            IN SIPSA_RECOLECCION_INSUMOS.rein_novedad%TYPE,
 p_obse_id            IN SIPSA_RECOLECCION_INSUMOS.obse_id%TYPE,
 p_rein_observacion           IN SIPSA_RECOLECCION_INSUMOS.rein_observacion%TYPE,
 p_esta_id            IN SIPSA_RECOLECCION_INSUMOS.esta_id%TYPE,
 p_fecha_recoleccion IN SIPSA_RECOLECCION_INSUMOS.rein_fecha_recoleccion%TYPE,
 p_usuario                       IN SIPSA_RECOLECCION_INSUMOS.rein_usuario_modificacion%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);

PROCEDURE Pr_CargarDMCRecoleccionI
( --p_id_usuario         IN NUMBER,
  p_uspe_id IN NUMBER,
  --p_periodo                   OUT Pq_Gen_General.ty_cursor,
  p_TiposRecolecciones OUT PQ_GEN_GENERAL.ty_cursor,
  p_Principal               OUT PQ_GEN_GENERAL.ty_cursor,
  p_Fuentes                OUT PQ_GEN_GENERAL.ty_cursor,
  p_FuentesArt            OUT PQ_GEN_GENERAL.ty_cursor,
  p_Persona                OUT PQ_GEN_GENERAL.ty_cursor,
  p_Articulos               OUT PQ_GEN_GENERAL.ty_cursor,
  p_Unidades               OUT PQ_GEN_GENERAL.ty_cursor,
  p_CasasComerciales   OUT PQ_GEN_GENERAL.ty_cursor,
  p_Grupos                   OUT PQ_GEN_GENERAL.ty_cursor,
  p_Observaciones             OUT Pq_Gen_General.ty_cursor,
  --p_Municipios             OUT PQ_GEN_GENERAL.ty_cursor, 
  p_codigo_error              OUT NUMBER,
  p_mensaje                   OUT VARCHAR2);

PROCEDURE Pr_IncorporarDMCI
(--p_sistema                   IN VARCHAR2,
 p_usuario                   IN SIPSA_RECOLECCION_INSUMOS.rein_usuario_modificacion%TYPE,
 --p_fuen_id                   IN SIPSA_RECOLECCION_MAYORISTAS.fuen_id%TYPE,
 p_codigo_error              OUT NUMBER,
 p_mensaje                   OUT VARCHAR2);

PROCEDURE pr_InsertarI2
(p_futi_id                       IN SIPSA_RECOLECCION_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_RECOLECCION_INSUMOS2.uspe_id%TYPE,
 p_grin2_id                    IN SIPSA_RECOLECCION_INSUMOS2.grin2_id%TYPE,
 p_prre_fecha_programada     IN SIPSA_RECOLECCION_INSUMOS2.prre_fecha_programada%TYPE,
 p_novedad_gral  IN SIPSA_RECOLECCION_INSUMOS2.rein2_novedad_gral%TYPE,
 p_id_informante     IN SIPSA_RECOLECCION_INSUMOS2.rein2_id_informante%TYPE,
 p_nom_informante           IN SIPSA_RECOLECCION_INSUMOS2.rein2_nom_informante%TYPE,
 p_tel_informante           IN SIPSA_RECOLECCION_INSUMOS2.rein2_tel_informante%TYPE,
 p_precio_recolectado                      IN SIPSA_RECOLECCION_INSUMOS2.rein2_precio_recolectado%TYPE,
 p_novedad  IN SIPSA_RECOLECCION_INSUMOS2.rein2_novedad%TYPE,
 p_obse_id                      IN  SIPSA_RECOLECCION_INSUMOS2.obse_id%TYPE,
 p_rein2_observacion              IN SIPSA_RECOLECCION_INSUMOS2.rein2_observacion%TYPE,
 p_fecha_recoleccion  IN SIPSA_RECOLECCION_INSUMOS2.rein2_fecha_recoleccion%TYPE,
 p_esta_id            IN SIPSA_RECOLECCION_INSUMOS2.esta_id%TYPE,  
 p_usuario                       IN SIPSA_RECOLECCION_INSUMOS2.rein2_usuario_modificacion%TYPE,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);

PROCEDURE pr_ActualizarI2
(p_futi_id                       IN SIPSA_RECOLECCION_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_RECOLECCION_INSUMOS2.uspe_id%TYPE,
 p_grin2_id                    IN SIPSA_RECOLECCION_INSUMOS2.grin2_id%TYPE,
 p_prre_fecha_programada     IN SIPSA_RECOLECCION_INSUMOS2.prre_fecha_programada%TYPE,
 p_novedad_gral  IN SIPSA_RECOLECCION_INSUMOS2.rein2_novedad_gral%TYPE,
 p_id_informante     IN SIPSA_RECOLECCION_INSUMOS2.rein2_id_informante%TYPE,
 p_nom_informante           IN SIPSA_RECOLECCION_INSUMOS2.rein2_nom_informante%TYPE,
 p_tel_informante           IN SIPSA_RECOLECCION_INSUMOS2.rein2_tel_informante%TYPE,
 p_precio_recolectado                      IN SIPSA_RECOLECCION_INSUMOS2.rein2_precio_recolectado%TYPE,
 p_novedad  IN SIPSA_RECOLECCION_INSUMOS2.rein2_novedad%TYPE,
 p_obse_id                      IN  SIPSA_RECOLECCION_INSUMOS2.obse_id%TYPE,
 p_rein2_observacion              IN SIPSA_RECOLECCION_INSUMOS2.rein2_observacion%TYPE,
 p_fecha_recoleccion  IN SIPSA_RECOLECCION_INSUMOS2.rein2_fecha_recoleccion%TYPE,
 p_esta_id            IN SIPSA_RECOLECCION_INSUMOS2.esta_id%TYPE,  
 p_usuario                       IN SIPSA_RECOLECCION_INSUMOS2.rein2_usuario_modificacion%TYPE,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);


PROCEDURE Pr_CargarDMCRecoleccionI2
( --p_id_usuario         IN NUMBER,
  p_uspe_id IN NUMBER,
  p_Principal               OUT PQ_GEN_GENERAL.ty_cursor,
  p_Informadores                OUT PQ_GEN_GENERAL.ty_cursor,
  p_FuentesTire                OUT PQ_GEN_GENERAL.ty_cursor,
  p_Persona                OUT PQ_GEN_GENERAL.ty_cursor,
  p_Articulos               OUT PQ_GEN_GENERAL.ty_cursor, 
  p_Caracteristicas OUT PQ_GEN_GENERAL.ty_cursor,
  p_ValCaraPermitidos OUT PQ_GEN_GENERAL.ty_cursor,
  p_ArtiCaraValores OUT PQ_GEN_GENERAL.ty_cursor,
  p_Observaciones OUT PQ_GEN_GENERAL.ty_cursor,
  p_codigo_error              OUT NUMBER,
  p_mensaje                   OUT VARCHAR2);
PROCEDURE Pr_IncorporarDMCI2
(--p_sistema                   IN VARCHAR2,
 p_usuario                   IN SIPSA_RECOLECCION_INSUMOS2.rein2_usuario_modificacion%TYPE,
 --p_fuen_id                   IN SIPSA_RECOLECCION_MAYORISTAS.fuen_id%TYPE,
 p_codigo_error              OUT NUMBER,
 p_mensaje                   OUT VARCHAR2);
 
 PROCEDURE pr_IncorporarDMCI2_DISTRITO
(
 p_usuario                   IN SIPSA_RECOLECCION_INSUMOS_D.REIN_USUARIO_MODIFICACION%TYPE,
 p_codigo_error              OUT NUMBER,
 p_mensaje                   OUT VARCHAR2);

 FUNCTION fn_ObtenerDescripcionProducto(p_grin2_id IN NUMBER)
 RETURN VARCHAR2 ;
 
 FUNCTION fn_ObtenerDescripcionProducto1(p_grin2_id IN NUMBER,p_tire_id IN NUMBER, p_articulo IN VARCHAR2)
 RETURN VARCHAR2 ;
 
  FUNCTION fn_ObtenerDescripcionProducto2(p_grin2_id IN NUMBER,p_tire_id IN NUMBER, p_articulo IN VARCHAR2,tipo IN NUMBER)
 RETURN VARCHAR2 ;
 
PROCEDURE pr_ActualizarPreEstaI2
(p_futi_id                       IN SIPSA_RECOLECCION_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_RECOLECCION_INSUMOS2.uspe_id%TYPE,
 p_grin2_id                    IN SIPSA_RECOLECCION_INSUMOS2.grin2_id%TYPE,
 p_prre_fecha_programada     IN SIPSA_RECOLECCION_INSUMOS2.prre_fecha_programada%TYPE,
 p_id_informante     IN SIPSA_RECOLECCION_INSUMOS2.rein2_id_informante%TYPE,
 p_novedad           IN SIPSA_RECOLECCION_INSUMOS2.REIN2_NOVEDAD%TYPE,
 p_precio_recolectado                      IN SIPSA_RECOLECCION_INSUMOS2.rein2_precio_recolectado%TYPE,
 p_obse_id            IN SIPSA_RECOLECCION_INSUMOS2.OBSE_ID%TYPE,
 p_observacion        IN SIPSA_RECOLECCION_INSUMOS2.REIN2_OBSERVACION%TYPE,
 p_esta_id            IN SIPSA_RECOLECCION_INSUMOS2.esta_id%TYPE,  
 p_usuario                       IN SIPSA_RECOLECCION_INSUMOS2.rein2_usuario_modificacion%TYPE,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);
 
PROCEDURE pr_ActualizarPreEstaI
(p_futi_id                       IN SIPSA_RECOLECCION_INSUMOS.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_RECOLECCION_INSUMOS.uspe_id%TYPE,
 p_articaco_id                      IN SIPSA_RECOLECCION_INSUMOS.articaco_id%TYPE,
 p_unme_id                    IN SIPSA_RECOLECCION_INSUMOS.Unme_Id%TYPE,
 p_prre_fecha_programada     IN SIPSA_RECOLECCION_INSUMOS.prre_fecha_programada%TYPE,
 p_novedad           IN SIPSA_RECOLECCION_INSUMOS.REIN_NOVEDAD%TYPE,
 p_precio_recolectado           IN SIPSA_RECOLECCION_INSUMOS.Rein_Precio_Recolectado%TYPE,
 p_obse_id            IN SIPSA_RECOLECCION_INSUMOS.OBSE_ID%TYPE,
 p_observacion        IN SIPSA_RECOLECCION_INSUMOS.REIN_OBSERVACION%TYPE,
 p_esta_id            IN SIPSA_RECOLECCION_INSUMOS.esta_id%TYPE,  
 p_usuario                       IN SIPSA_RECOLECCION_INSUMOS.Rein_Usuario_Modificacion%TYPE,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);
 
  PROCEDURE pr_aprueba_rein
(p_futi_id                       IN VARCHAR2,
 p_uspe_id                       IN VARCHAR2,
 p_articaco_id                   IN VARCHAR2,
 p_unme_id                       IN VARCHAR2,
 p_prre_fecha_programada         IN VARCHAR2,
 p_usuario                       IN VARCHAR2,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);
 
  PROCEDURE pr_aprueba_rein2
(p_futi_id                       IN VARCHAR2,
 p_uspe_id                       IN VARCHAR2,
 p_grin2_id                      IN VARCHAR2,
 p_rein_informante_id            IN VARCHAR2,
 p_prre_fecha_programada         IN VARCHAR2,
 p_usuario                       IN VARCHAR2,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2);
 
 
 
PROCEDURE pr_InsertarI2_D
(p_futi_id                       IN SIPSA_RECOLECCION_INSUMOS_D.FUTI_ID%TYPE,
 p_uspe_id                      IN SIPSA_RECOLECCION_INSUMOS_D.USPE_ID%TYPE,
 p_grin2_id                    IN SIPSA_RECOLECCION_INSUMOS_D.GRIN_ID%TYPE,
 p_prre_fecha_programada     IN SIPSA_RECOLECCION_INSUMOS_D.REIN_FECHA_PROGRAMADA%TYPE,
 p_id_informante     IN SIPSA_RECOLECCION_INSUMOS_D.REIN_ID_INFORMANTE%TYPE,
 p_nom_informante           IN SIPSA_RECOLECCION_INSUMOS_D.REIN_NOM_INFORMANTE%TYPE,
 p_tel_informante           IN SIPSA_RECOLECCION_INSUMOS_D.REIN_TEL_INFORMANTE%TYPE,
 p_precio_recolectado       IN SIPSA_RECOLECCION_INSUMOS_D.REIN_PRECIO_RECOLECTADO%TYPE,
 p_novedad  				IN SIPSA_RECOLECCION_INSUMOS_D.REIN_NOVEDAD%TYPE,
 p_obse_id                      IN  SIPSA_RECOLECCION_INSUMOS_D.OBSE_ID%TYPE,
 p_rein2_observacion              IN SIPSA_RECOLECCION_INSUMOS_D.REIN_OBSERVACION%TYPE,
 p_fecha_recoleccion  IN SIPSA_RECOLECCION_INSUMOS_D.REIN_FECHA_RECOLECCION%TYPE,
 p_esta_id            IN SIPSA_RECOLECCION_INSUMOS_D.ESTA_ID%TYPE, 
 p_usuario                  IN SIPSA_RECOLECCION_INSUMOS_D.REIN_USUARIO_MODIFICACION%TYPE,
 p_tipo  IN SIPSA_RECOLECCION_INSUMOS_D.REIN_TIPO%TYPE,   
 p_frecuencia  IN SIPSA_RECOLECCION_INSUMOS_D.REIN_FRECUENCIA%TYPE,   
 p_observacion_producto  IN SIPSA_RECOLECCION_INSUMOS_D.REIN_OBSERVACION_PRODUCTO%TYPE,   
 p_unidad_medida_otro  IN SIPSA_RECOLECCION_INSUMOS_D.REIN_UNIDAD_MEDIDA_OTRO%TYPE,   
 p_unidad_id  IN SIPSA_RECOLECCION_INSUMOS_D.REIN_UNIDAD_ID%TYPE,   
 p_unidad_medida  IN SIPSA_RECOLECCION_INSUMOS_D.REIN_UNIDAD_MEDIDA%TYPE,  
 p_arti_id  IN SIPSA_RECOLECCION_INSUMOS_D.ARTI_ID%TYPE,   
 p_arti_nombre  IN SIPSA_RECOLECCION_INSUMOS_D.ARTI_NOMBRE%TYPE,  
  p_municipio                IN SIPSA_RECOLECCION_INSUMOS_D.REIN_MUNICIPIO%TYPE,    
 p_muni_id                  IN SIPSA_RECOLECCION_INSUMOS_D.REIN_MUNI_ID%TYPE,    
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2);
 
 
PROCEDURE Pr_ActualizarI2_D
(p_futi_id                      IN SIPSA_RECOLECCION_INSUMOS_D.FUTI_ID%TYPE,
 p_uspe_id                      IN SIPSA_RECOLECCION_INSUMOS_D.USPE_ID%TYPE,
 p_grin2_id                     IN SIPSA_RECOLECCION_INSUMOS_D.GRIN_ID%TYPE,
 p_prre_fecha_programada        IN SIPSA_RECOLECCION_INSUMOS_D.REIN_FECHA_PROGRAMADA%TYPE,
 p_id_informante                IN SIPSA_RECOLECCION_INSUMOS_D.REIN_ID_INFORMANTE%TYPE,
 p_nom_informante               IN SIPSA_RECOLECCION_INSUMOS_D.REIN_NOM_INFORMANTE%TYPE,
 p_tel_informante               IN SIPSA_RECOLECCION_INSUMOS_D.REIN_TEL_INFORMANTE%TYPE,
 p_precio_recolectado           IN SIPSA_RECOLECCION_INSUMOS_D.REIN_PRECIO_RECOLECTADO%TYPE,
 p_novedad  				            IN SIPSA_RECOLECCION_INSUMOS_D.REIN_NOVEDAD%TYPE,
 p_obse_id                      IN  SIPSA_RECOLECCION_INSUMOS_D.OBSE_ID%TYPE,
 p_rein2_observacion            IN SIPSA_RECOLECCION_INSUMOS_D.REIN_OBSERVACION%TYPE,
 p_fecha_recoleccion            IN SIPSA_RECOLECCION_INSUMOS_D.REIN_FECHA_RECOLECCION%TYPE,
 p_esta_id                      IN SIPSA_RECOLECCION_INSUMOS_D.ESTA_ID%TYPE,  
 p_usuario                      IN SIPSA_RECOLECCION_INSUMOS_D.REIN_USUARIO_MODIFICACION%TYPE,
 p_tipo                         IN SIPSA_RECOLECCION_INSUMOS_D.REIN_TIPO%TYPE,   
 p_frecuencia                   IN SIPSA_RECOLECCION_INSUMOS_D.REIN_FRECUENCIA%TYPE,   
 p_observacion_producto         IN SIPSA_RECOLECCION_INSUMOS_D.REIN_OBSERVACION_PRODUCTO%TYPE,   
 p_unidad_medida_otro           IN SIPSA_RECOLECCION_INSUMOS_D.REIN_UNIDAD_MEDIDA_OTRO%TYPE,   
 p_unidad_id                    IN SIPSA_RECOLECCION_INSUMOS_D.REIN_UNIDAD_ID%TYPE,   
 p_unidad_medida                IN SIPSA_RECOLECCION_INSUMOS_D.REIN_UNIDAD_MEDIDA%TYPE, 
 p_municipio                    IN SIPSA_RECOLECCION_INSUMOS_D.REIN_MUNICIPIO%TYPE,    
 p_muni_id                      IN SIPSA_RECOLECCION_INSUMOS_D.REIN_MUNI_ID%TYPE,  
 p_codigo_error                 OUT NUMBER,
 p_mensaje                      OUT VARCHAR2) ;
 
 
 PROCEDURE Pr_CargarDMCRecoleccionDIS
( --p_id_usuario         IN NUMBER,
  p_uspe_id IN NUMBER,
  p_Principal               OUT PQ_GEN_GENERAL.ty_cursor,
  p_Informadores                OUT PQ_GEN_GENERAL.ty_cursor,
  p_FuentesTire                OUT PQ_GEN_GENERAL.ty_cursor,
  p_FuentesArt                OUT PQ_GEN_GENERAL.ty_cursor,
  p_Persona                OUT PQ_GEN_GENERAL.ty_cursor,
  p_Articulos               OUT PQ_GEN_GENERAL.ty_cursor, 
  p_Unidades               OUT PQ_GEN_GENERAL.ty_cursor,
  p_CasasComerciales   OUT PQ_GEN_GENERAL.ty_cursor,
  p_Grupos                   OUT PQ_GEN_GENERAL.ty_cursor,
  p_Caracteristicas OUT PQ_GEN_GENERAL.ty_cursor,
  p_ValCaraPermitidos OUT PQ_GEN_GENERAL.ty_cursor,
  p_ArtiCaraValores OUT PQ_GEN_GENERAL.ty_cursor,
  p_Observaciones OUT PQ_GEN_GENERAL.ty_cursor,
  p_codigo_error              OUT NUMBER,
  p_mensaje                   OUT VARCHAR2);
 
 
END PQ_SIPSA_RECOLECCION;