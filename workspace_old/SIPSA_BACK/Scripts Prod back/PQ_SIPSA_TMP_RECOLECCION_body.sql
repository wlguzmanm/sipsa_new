create or replace PACKAGE BODY PQ_SIPSA_TMP_RECOLECCION IS

PROCEDURE pr_InsertarM
/********************************************************************************
 descripcion   : método para incorporar información sobre la tabla SIPSA_TMP_RECO_MAYORISTAS

 parametros    :
 in            : p_futi_id
                  p_uspe_id
                  p_arti_id
                  p_unme_id
                  p_prre_fecha_programada
                  p_enma_ronda
                  p_rema_precio_prom_anterior
                  p_rema_toma1
                  p_rema_toma2
                  p_rema_toma3
                  p_rema_toma4
                  p_rema_observacion_ronda

 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 realizado por : Vitaliano Corredor
 fecha creacion: 11/12/2012

 modificado por:
 fecha modifica:
 cambios       : 
*******************************************************************************/

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
 p_mensaje                       OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   INSERT INTO SIPSA_TMP_RECO_MAYORISTAS (futi_id,uspe_id,arti_id,unme_id,prre_fecha_programada,enma_ronda,rema_precio_prom_anterior,
                                                                          rema_toma1,rema_toma2,rema_toma3,rema_toma4,rema_observacion_ronda)
   VALUES(p_futi_id,p_uspe_id,p_arti_id,p_unme_id,p_prre_fecha_programada,p_enma_ronda,p_rema_precio_prom_anterior,
                p_rema_toma1,p_rema_toma2,p_rema_toma3,p_rema_toma4,p_rema_observacion_ronda);

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_RECO_MAYORISTAS se presento '||SQLERRM;
END pr_InsertarM;
PROCEDURE pr_InsertarA
/********************************************************************************
 descripcion   : método para incorporar información sobre la tabla SIPSA_TMP_RECO_ABASTE

 parametros    :
 in            :  p_futi_id
                  p_uspe_id
                  p_enab_placa
                  p_enab_fecha
                  p_enab_hora
                  p_arti_id
                  p_reab_cantidad
                  p_tipr_id
                  p_unme_id
                  p_reab_cantidad_pr
                  
                  
 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

  realizado por : Vitaliano Corredor
 fecha creacion: 11/12/2012

 modificado por:
 fecha modifica:
 cambios       : 
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS


BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   INSERT INTO SIPSA_TMP_RECO_ABASTECIMIENTO(futi_id,uspe_id,enab_placa,prre_fecha_programada,enab_hora,arti_id,reab_cantidad,tipr_id,unme_id,reab_cantidad_pr)
   VALUES (p_futi_id,p_uspe_id,p_enab_placa,to_date(p_enab_fecha, 'dd-mm-yy'),p_enab_hora,p_arti_id,p_reab_cantidad,p_tipr_id,p_unme_id,p_reab_cantidad_pr);

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_RECO_ABASTECIMIENTO se presento '||SQLERRM;
END pr_InsertarA;

PROCEDURE pr_InsertarI
/********************************************************************************
 descripcion   : método para incorporar información sobre la tabla SIPSA_TMP_RECO_MAYORISTAS

 parametros    :
 in            : p_fuen_id
                 p_futi_id
                 p_tire_id
                 p_uspe_id
                 p_prre_fecha_programada
                 p_arti_id,p_arti_nombre
                 p_ica,p_caco_id
                 p_casa_comercial
                 p_unme_id
                 p_rein_precio_anterior
                 p_rein_novedad_anterior
                 p_rein_precio_recolectado
                 p_rein_novedad
                 p_obse_id
                 p_rein_observacion
                 
                 
 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 realizado por : Vitaliano Corredor
 fecha creacion: 11/12/2012

 modificado por:
 fecha modifica:
 cambios       : 
*******************************************************************************/
(  p_fuen_id               IN  SIPSA_TMP_RECO_INSUMOS.fuen_id%TYPE, 
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
 p_mensaje                       OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;


  /*  INSERT_PRUEBA('pr_InsertarI:     p_fuen_id=>' || p_fuen_id||',	'||
'p_futi_id=>' || p_futi_id||',	'||
'p_tire_id=>' || p_tire_id||',	'||
'p_uspe_id=>' || p_uspe_id||',	'||
'p_prre_fecha_programada=>' || p_prre_fecha_programada||',	'||
'p_articaco_id=>' || p_articaco_id||',	'||
'p_arti_id=>' || p_arti_id||',	'||
'p_arti_nombre=>' || p_arti_nombre||',	'||
'p_caco_id=>' || p_caco_id||',	'||
'p_casa_comercial=>' || p_casa_comercial||',	'||
'p_regicalinea=>' || p_regicalinea||',	'||
'p_unme_id=>' || p_unme_id||',	'||
'p_rein_precio_anterior=>' || p_rein_precio_anterior||',	'||
'p_rein_novedad_anterior=>' || p_rein_novedad_anterior||',	'||
'p_rein_precio_recolectado=>' || p_rein_precio_recolectado||',	'||
'p_rein_novedad=>' || p_rein_novedad||',	'||
'p_obse_id=>' || p_obse_id||',	'||
'INITCAP(p_rein_observacion)=>' || INITCAP(p_rein_observacion)||',	'||
'p_fecha_recoleccion=>' || p_fecha_recoleccion);*/

   INSERT INTO SIPSA_TMP_RECO_INSUMOS (fuen_id,futi_id,tire_id,uspe_id,prre_fecha_programada,articaco_id,arti_id,arti_nombre,caco_id,rein_casa_comercial,regica_linea,unme_id,rein_precio_anterior,
                                                                   rein_novedad_anterior,rein_precio_recolectado,rein_novedad,obse_id,rein_observacion,rein_fecha_recoleccion)
   VALUES(p_fuen_id,p_futi_id,p_tire_id,p_uspe_id,p_prre_fecha_programada,p_articaco_id,p_arti_id,p_arti_nombre,p_caco_id,p_casa_comercial,p_regicalinea,p_unme_id,p_rein_precio_anterior,
                p_rein_novedad_anterior,p_rein_precio_recolectado,p_rein_novedad,p_obse_id,INITCAP(p_rein_observacion),p_fecha_recoleccion);

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_RECO_INSUMOS se presento '||SQLERRM;
END pr_InsertarI;


PROCEDURE pr_InsertarFUIN
/********************************************************************************
 descripcion   : método para incorporar información sobre la tabla SIPSA_TMP_RECO_MAYORISTAS

 parametros    :
 in            : p_fuen_id
                 p_futi_id
                 p_muni_id
                 p_tire_id
                 p_uspe_id
                 p_fuen_nombre
                 p_fuen_direccion
                 p_fuen_telefono
                 p_fuen_email
                 p_info_nombre
                 p_info_cargo
                 p_info_telefono
                 p_info_email
                 p_fecha_visita

 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

  realizado por : Vitaliano Corredor
 fecha creacion: 11/12/2012

 modificado por:
 fecha modifica:
 cambios       : 
*******************************************************************************/
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
 p_mensaje                       OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

--BORRAR
/*    INSERT_PRUEBA('pr_InsertarFUIN:   p_fuen_id=> '||p_fuen_id||',	' ||
'p_futi_id=> '||p_futi_id||',	' ||
'p_muni_id=> '||p_muni_id||',	' ||
'p_tire_id=> '||p_tire_id||',	' ||
'p_uspe_id=> '||p_uspe_id||',	' ||
'p_fuen_nombre=> '||p_fuen_nombre||',	' ||
'p_fuen_direccion=> '||p_fuen_direccion||',	' ||
'p_fuen_telefono=> '||p_fuen_telefono||',	' ||
'p_fuen_email=> '||p_fuen_email||',	' ||
'p_info_nombre=> '||p_info_nombre||',	' ||
'p_info_cargo=> '||p_info_cargo||',	' ||
'p_info_telefono=> '||p_info_telefono||',	' ||
'p_info_email=> '||p_info_email||',	' ||
'p_fecha_visita=> '||p_fecha_visita);
--/BORRAR
*/
   INSERT INTO SIPSA_TMP_FUENTES_INFORMANTES (fuen_id,futi_id,muni_id,tire_id,uspe_id,fuen_nombre,fuen_direccion,fuen_telefono,fuen_email,info_nombre,info_cargo,info_telefono,info_email,fecha_visita)
   VALUES(p_fuen_id,p_futi_id,p_muni_id,p_tire_id,p_uspe_id,p_fuen_nombre,p_fuen_direccion,p_fuen_telefono,p_fuen_email,p_info_nombre,p_info_cargo,p_info_telefono,p_info_email,p_fecha_visita);

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_FUENTES_INFORMANTES se presento '||SQLERRM;
END pr_InsertarFUIN;

PROCEDURE pr_InsertarI2
/********************************************************************************
 descripcion   : método para incorporar información sobre la tabla SIPSA_TMP_RECO_INSUMOS2

 parametros    :
 in            : p_futi_id
                 p_uspe_id
                 p_arti_id
                p_grin2_id
                p_prre_fecha_programada
                p_id_informante
                p_nom_informante
                p_tel_informante
                p_rein2_precio_recolectado
                p_obse_id
                p_rein2_observacion

 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 realizado por : Vitaliano Corredor
 fecha creacion: 10/04/2013

 modificado por:
 fecha modifica:
 cambios       : 
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   INSERT INTO SIPSA_TMP_RECO_INSUMOS2 (futi_id,uspe_id,arti_id,grin2_id,prre_fecha_programada,rein2_novedad_gral,rein2_id_informante,rein2_nom_informante,rein2_tel_informante,rein2_precio_recolectado,rein2_novedad,obse_id,rein2_observacion,rein2_fecha_recoleccion)
   VALUES(p_futi_id,p_uspe_id,p_arti_id,p_grin2_id,p_prre_fecha_programada,p_novedad_gral,p_id_informante,INITCAP(p_nom_informante),p_tel_informante,p_precio_recolectado,p_novedad,p_obse_id,INITCAP(p_rein2_observacion),p_fecha_recoleccion);

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_RECO_INSUMOS2 se presento '||SQLERRM;
END pr_InsertarI2;



PROCEDURE pr_InsertarI_DR
/********************************************************************************
 descripcion   : método para incorporar información

 parametros    :
 in            : p_fuen_id
                 p_futi_id
                 p_tire_id
                 p_uspe_id
                 p_prre_fecha_programada
                 p_arti_id,p_arti_nombre
                 p_ica,p_caco_id
                 p_casa_comercial
                 p_unme_id
                 p_rein_precio_anterior
                 p_rein_novedad_anterior
                 p_rein_precio_recolectado
                 p_rein_novedad
                 p_obse_id
                 p_rein_observacion
                 
                 
 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 realizado por : Marco Guzman
 fecha creacion: 20/06/2018


*******************************************************************************/
(  p_fuen_id               IN  SIPSA_TMP_RECO_INSUMOS.fuen_id%TYPE, 
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
 p_mensaje                       OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   INSERT INTO SIPSA_TMP_RECO_INSUMOS (fuen_id,futi_id,tire_id,uspe_id,prre_fecha_programada,articaco_id,arti_id,arti_nombre,caco_id,rein_casa_comercial,regica_linea,unme_id,rein_precio_anterior,
                                                                   rein_novedad_anterior,rein_precio_recolectado,rein_novedad,obse_id,rein_observacion,rein_fecha_recoleccion)
   VALUES(p_fuen_id,p_futi_id,p_tire_id,p_uspe_id,p_prre_fecha_programada,p_articaco_id,p_arti_id,p_arti_nombre,p_caco_id,p_casa_comercial,p_regicalinea,p_unme_id,p_rein_precio_anterior,
                p_rein_novedad_anterior,p_rein_precio_recolectado,p_rein_novedad,p_obse_id,INITCAP(p_rein_observacion),p_fecha_recoleccion);

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_RECO_INSUMOS se presento '||SQLERRM;
END pr_InsertarI_DR;


PROCEDURE pr_InsertarFUIN_DR
/********************************************************************************
 descripcion   : método para incorporar información sobre la tabla SIPSA_TMP_RECO_MAYORISTAS

 parametros    :
 in            : p_fuen_id
                 p_futi_id
                 p_muni_id
                 p_tire_id
                 p_uspe_id
                 p_fuen_nombre
                 p_fuen_direccion
                 p_fuen_telefono
                 p_fuen_email
                 p_info_nombre
                 p_info_cargo
                 p_info_telefono
                 p_info_email
                 p_fecha_visita

 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

  realizado por : Marco Guzman
 fecha creacion: 20/06/2018

 modificado por:
 fecha modifica:
 cambios       : 
*******************************************************************************/
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
 p_mensaje                       OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   INSERT INTO SIPSA_TMP_FUENTES_INFORMANTES (fuen_id,futi_id,muni_id,tire_id,uspe_id,fuen_nombre,fuen_direccion,fuen_telefono,fuen_email,info_nombre,info_cargo,info_telefono,info_email,fecha_visita)
   VALUES(p_fuen_id,p_futi_id,p_muni_id,p_tire_id,p_uspe_id,p_fuen_nombre,p_fuen_direccion,p_fuen_telefono,p_fuen_email,p_info_nombre,p_info_cargo,p_info_telefono,p_info_email,p_fecha_visita);

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_FUENTES_INFORMANTES se presento '||SQLERRM;
END pr_InsertarFUIN_DR;

PROCEDURE pr_InsertarI2_DR
/********************************************************************************
 descripcion   : método para incorporar información sobre la tabla SIPSA_TMP_RECO_INSUMOS2

 parametros    :
 in            : p_futi_id
                 p_uspe_id
                 p_arti_id
                p_grin2_id
                p_prre_fecha_programada
                p_id_informante
                p_nom_informante
                p_tel_informante
                p_rein2_precio_recolectado
                p_obse_id
                p_rein2_observacion

 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 realizado por : Vitaliano Corredor
 fecha creacion: 10/04/2013

 modificado por:
 fecha modifica:
 cambios       : 
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   INSERT INTO SIPSA_TMP_RECO_INSUMOS2 (futi_id,uspe_id,arti_id,grin2_id,prre_fecha_programada,rein2_novedad_gral,rein2_id_informante,rein2_nom_informante,rein2_tel_informante,rein2_precio_recolectado,rein2_novedad,obse_id,rein2_observacion,rein2_fecha_recoleccion)
   VALUES(p_futi_id,p_uspe_id,p_arti_id,p_grin2_id,p_prre_fecha_programada,p_novedad_gral,p_id_informante,INITCAP(p_nom_informante),p_tel_informante,p_precio_recolectado,p_novedad,p_obse_id,INITCAP(p_rein2_observacion),p_fecha_recoleccion);

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_RECO_INSUMOS2 se presento '||SQLERRM;
END pr_InsertarI2_DR;


PROCEDURE pr_InsertarI2_D 
/********************************************************************************
 descripcion   : método para incorporar información sobre la tabla SIPSA_TMP_RECO_INSUMOS_D

 Realizado por : Marco Guzman
 Fecha Creacion: 19/06/2019 
*******************************************************************************/
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
 p_fecha_recoleccion      IN SIPSA_TMP_RECO_INSUMOS_D.REIN_FECHA_RECOLECCION%TYPE,     
 p_tipo         IN SIPSA_TMP_RECO_INSUMOS_D.REIN_TIPO%TYPE,   
 p_frecuencia         IN SIPSA_TMP_RECO_INSUMOS_D.REIN_FRECUENCIA%TYPE,   
 p_observacion_producto     IN SIPSA_TMP_RECO_INSUMOS_D.REIN_OBSERVACION_PRODUCTO%TYPE,   
 p_unidad_medida_otro     IN SIPSA_TMP_RECO_INSUMOS_D.REIN_UNIDAD_MEDIDA_OTRO%TYPE,   
 p_unidad_id           IN SIPSA_TMP_RECO_INSUMOS_D.REIN_UNIDAD_ID%TYPE,   
 p_unidad_medida        IN SIPSA_TMP_RECO_INSUMOS_D.REIN_UNIDAD_MEDIDA%TYPE,  
 p_arti_nombre         IN SIPSA_TMP_RECO_INSUMOS_D.ARTI_NOMBRE%TYPE, 
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2) IS 
 
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   INSERT INTO SIPSA_TMP_RECO_INSUMOS_D (FUTI_ID,USPE_ID,ARTI_ID,GRIN_ID,ENIN_FECHA_PROGRAMADA,REIN_ID_INFORMANTE,REIN_NOM_INFORMANTE,REIN_TEL_INFORMANTE, 
   REIN_PRECIO_RECOLECTADO,REIN_NOVEDAD,OBSE_ID,REIN_OBSERVACION,REIN_FECHA_RECOLECCION,
   REIN_TIPO,REIN_FRECUENCIA,REIN_OBSERVACION_PRODUCTO,REIN_UNIDAD_MEDIDA_OTRO,REIN_UNIDAD_ID,REIN_UNIDAD_MEDIDA,ARTI_NOMBRE)
   VALUES(p_futi_id,p_uspe_id,p_arti_id,p_grin2_id,p_prre_fecha_programada,p_id_informante,INITCAP(p_nom_informante),
   p_tel_informante,p_precio_recolectado,p_novedad,p_obse_id,INITCAP(p_rein2_observacion),p_fecha_recoleccion, 
   p_tipo,p_frecuencia,p_observacion_producto,p_unidad_medida_otro,p_unidad_id,p_unidad_medida,p_arti_nombre);

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_RECO_INSUMOS_D se presento '||SQLERRM;
END pr_InsertarI2_D;


PROCEDURE pr_Usuario_obtener 
 /********************************************************************************
 Descripcion   : traer usuario

 Realizado por : Marco Guzman
 Fecha Creacion: 25/06/2019
*******************************************************************************/
 (p_usuario                   IN SIPSA_RECOLECCION_INSUMOS_D.REIN_USUARIO_MODIFICACION%TYPE,
  p_codigo_error              OUT NUMBER,
  p_mensaje                   OUT VARCHAR2) IS

v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;

BEGIN
    p_codigo_error := 0;
    p_mensaje      := NULL;

    v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
    p_mensaje := v_usuario;    
END pr_Usuario_obtener;


END PQ_SIPSA_TMP_RECOLECCION;