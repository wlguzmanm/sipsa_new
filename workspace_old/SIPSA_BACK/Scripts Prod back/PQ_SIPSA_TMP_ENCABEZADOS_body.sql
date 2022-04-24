create or replace PACKAGE BODY PQ_SIPSA_TMP_ENCABEZADOS IS

PROCEDURE pr_InsertarM
/********************************************************************************
 descripcion   : método para incorporar información sobre la tabla SIPSA_TMP_ENCA_MAYORISTA

 parametros    :
 in            : p_pers_id_persona
                 p_pere_id_periodo_recoleccion
                 p_fuel_id_fuente_elemento
                 p_fuel_id_fuente_elemen_nuevo
                 p_fuco_id_fuente_complementa
                 p_fuco_id_fuente_complem_nueva
                 p_obse_id_observacion
                 p_observacion
                 p_novedad
                 p_precio_base
                 p_precio_recolectado
                 p_cantidad
                 p_precio_base_anterior
                 p_precio_recolectado_anterior
                 p_cantidad_anterior
                 p_tipo_recoleccion
                 p_fecha_recoleccion
                 p_codigo_error
                 p_mensaje

 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 realizado por : Julián Andrés Rodríguez Rodríguez

 fecha creacion: 11/12/2008

 modificado por: Julián Andrés Rodríguez Rodríguez
 fecha modifica: 26/04/2010
 cambios       : Adicionar parametro p_novedad_anterior
*******************************************************************************/
(p_futi_id                       IN SIPSA_TMP_ENCA_MAYORISTA.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_TMP_ENCA_MAYORISTA.uspe_id%TYPE,
 p_enma_ronda                      IN SIPSA_TMP_ENCA_MAYORISTA.enma_ronda%TYPE,
 p_prre_fecha_programada                      IN SIPSA_TMP_ENCA_MAYORISTA.prre_fecha_programada%TYPE,
 p_enma_hi                      IN SIPSA_TMP_ENCA_MAYORISTA.enma_hora_inicial%TYPE,
 p_enma_hf                      IN SIPSA_TMP_ENCA_MAYORISTA.enma_hora_final%TYPE,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   INSERT INTO SIPSA_TMP_ENCA_MAYORISTA(futi_id,uspe_id,enma_ronda,prre_fecha_programada,enma_hora_inicial,enma_hora_final)
   VALUES (p_futi_id,p_uspe_id,p_enma_ronda,p_prre_fecha_programada,p_enma_hi,p_enma_hf);

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_ENCA_MAYORISTA se presento '||SQLERRM;
END pr_InsertarM;
PROCEDURE pr_InsertarA
/********************************************************************************
 descripcion   : método para incorporar información sobre la tabla SIPSA_TMP_ENCA_MAYORISTA

 parametros    :
 in            : p_pers_id_persona
                 p_pere_id_periodo_recoleccion
                 p_fuel_id_fuente_elemento
                 p_fuel_id_fuente_elemen_nuevo
                 p_fuco_id_fuente_complementa
                 p_fuco_id_fuente_complem_nueva
                 p_obse_id_observacion
                 p_observacion
                 p_novedad
                 p_precio_base
                 p_precio_recolectado
                 p_cantidad
                 p_precio_base_anterior
                 p_precio_recolectado_anterior
                 p_cantidad_anterior
                 p_tipo_recoleccion
                 p_fecha_recoleccion
                 p_codigo_error
                 p_mensaje

 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 realizado por : Julián Andrés Rodríguez Rodríguez

 fecha creacion: 11/12/2008

 modificado por: Julián Andrés Rodríguez Rodríguez
 fecha modifica: 26/04/2010
 cambios       : Adicionar parametro p_novedad_anterior
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   INSERT INTO SIPSA_TMP_ENCA_ABASTECIMIENTO(futi_id,uspe_id,prre_fecha_programada,enab_hora,tive_id,tive_cual_vehiculo,enab_placa,
                                                                               enab_ubicacion,enab_procedencia,enab_destino,time_id,time_cual_mercado,enab_basc_desc,enab_observaciones)
   VALUES (p_futi_id,p_uspe_id,  to_date(p_enab_fecha, 'dd-mm-yy') ,p_enab_hora,p_tive_id,p_tive_cual,p_enab_placa,
                 p_enab_ubicacion,p_enab_procedencia,p_enab_destino,p_time_id,p_time_cual,p_enab_basc_desc,p_enab_observaciones);

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_ENCA_ABASTECIMIENTO se presento '||SQLERRM;
END pr_InsertarA;

PROCEDURE pr_InsertarI2
/********************************************************************************
 descripcion   : método para incorporar información sobre la tabla SIPSA_TMP_ENCA_INSUMOS2

 parametros    :
 in            : p_pers_id_persona
                 p_pere_id_periodo_recoleccion
                 p_fuel_id_fuente_elemento
                 p_fuel_id_fuente_elemen_nuevo
                 p_fuco_id_fuente_complementa
                 p_fuco_id_fuente_complem_nueva
                 p_obse_id_observacion
                 p_observacion
                 p_novedad
                 p_precio_base
                 p_precio_recolectado
                 p_cantidad
                 p_precio_base_anterior
                 p_precio_recolectado_anterior
                 p_cantidad_anterior
                 p_tipo_recoleccion
                 p_fecha_recoleccion
                 p_codigo_error
                 p_mensaje

 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 10/04/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   INSERT INTO SIPSA_TMP_ENCA_INSUMOS2(futi_id,uspe_id,arti_id,grin2_id,prre_fecha_programada,enin2_arti_nombre,
                                                                    enin2_precio_prom_anterior,enin2_novedad,enin2_ftes_capturadas,obse_id,enin2_observacion)
   VALUES (p_futi_id, p_uspe_id,p_arti_id, p_grin2_id, p_prre_fecha_programada,p_arti_nombre,
                 p_precio_prom_anterior, p_novedad, p_ftes_capturadas,p_obse_id, p_observacion);

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_ENCA_INSUMOS2 se presento '||SQLERRM;
END pr_InsertarI2;

PROCEDURE pr_InsertarTmpAPGRIN2_VACA
/********************************************************************************
 Descripcion   : método para incorporar información sobre la tabla SIPSA_TMP_GRIN2_VACA

 parametros    :
 in            :     p_futi_id
                     p_uspe_id
                     p_apgrin2_id
                     p_tire_id
                     p_cara_id
                     p_vape_id
 
 out           :    p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                     p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 15/04/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
( p_futi_id               IN  SIPSA_TMP_GRIN2_VACA.futi_id%TYPE,
  p_uspe_id   IN  SIPSA_TMP_GRIN2_VACA.uspe_id%TYPE,
  p_grin2_id IN SIPSA_TMP_GRIN2_VACA.grin2_id%TYPE,
  p_tire_id IN SIPSA_TMP_GRIN2_VACA.tire_id%TYPE,
  p_cara_id IN SIPSA_TMP_GRIN2_VACA.cara_id%TYPE,  
  p_vape_id IN SIPSA_TMP_GRIN2_VACA.vape_id%TYPE,
  p_valor IN SIPSA_TMP_GRIN2_VACA.valor%TYPE,
  p_codigo_error                  OUT NUMBER,
  p_mensaje                       OUT VARCHAR2) IS

--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   
   --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
   INSERT INTO    SIPSA_TMP_GRIN2_VACA (futi_id,uspe_id,grin2_id,tire_id,cara_id,vape_id,valor)
   VALUES(p_futi_id,p_uspe_id,p_grin2_id,p_tire_id,p_cara_id,p_vape_id,p_valor);     
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_GRIN2_VACA se presento '||SQLERRM;                                                                                   
END pr_InsertarTmpAPGRIN2_VACA;


PROCEDURE pr_InsertarI2_DR
/********************************************************************************
 descripcion   : método para incorporar información sobre la tabla SIPSA_TMP_ENCA_INSUMOS2

 parametros    :
 in            : p_pers_id_persona
                 p_pere_id_periodo_recoleccion
                 p_fuel_id_fuente_elemento
                 p_fuel_id_fuente_elemen_nuevo
                 p_fuco_id_fuente_complementa
                 p_fuco_id_fuente_complem_nueva
                 p_obse_id_observacion
                 p_observacion
                 p_novedad
                 p_precio_base
                 p_precio_recolectado
                 p_cantidad
                 p_precio_base_anterior
                 p_precio_recolectado_anterior
                 p_cantidad_anterior
                 p_tipo_recoleccion
                 p_fecha_recoleccion
                 p_codigo_error
                 p_mensaje

 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 10/04/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   INSERT INTO SIPSA_TMP_ENCA_INSUMOS2(futi_id,uspe_id,arti_id,grin2_id,prre_fecha_programada,enin2_arti_nombre,
                                                                    enin2_precio_prom_anterior,enin2_novedad,enin2_ftes_capturadas,obse_id,enin2_observacion)
   VALUES (p_futi_id, p_uspe_id,p_arti_id, p_grin2_id, p_prre_fecha_programada,p_arti_nombre,
                 p_precio_prom_anterior, p_novedad, p_ftes_capturadas,p_obse_id, p_observacion);

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_ENCA_INSUMOS2 se presento '||SQLERRM;
END pr_InsertarI2_DR;


PROCEDURE pr_InsertarTmpAPGRIN2_VACA_DR
/********************************************************************************
 Descripcion   : método para incorporar información sobre la tabla SIPSA_TMP_GRIN2_VACA

 parametros    :
 in            :     p_futi_id
                     p_uspe_id
                     p_apgrin2_id
                     p_tire_id
                     p_cara_id
                     p_vape_id
 
 out           :    p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                     p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 15/04/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
( p_futi_id               IN  SIPSA_TMP_GRIN2_VACA.futi_id%TYPE,
  p_uspe_id   IN  SIPSA_TMP_GRIN2_VACA.uspe_id%TYPE,
  p_grin2_id IN SIPSA_TMP_GRIN2_VACA.grin2_id%TYPE,
  p_tire_id IN SIPSA_TMP_GRIN2_VACA.tire_id%TYPE,
  p_cara_id IN SIPSA_TMP_GRIN2_VACA.cara_id%TYPE,  
  p_vape_id IN SIPSA_TMP_GRIN2_VACA.vape_id%TYPE,
  p_valor IN SIPSA_TMP_GRIN2_VACA.valor%TYPE,
  p_codigo_error                  OUT NUMBER,
  p_mensaje                       OUT VARCHAR2) IS

--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   
   --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
   INSERT INTO    SIPSA_TMP_GRIN2_VACA (futi_id,uspe_id,grin2_id,tire_id,cara_id,vape_id,valor)
   VALUES(p_futi_id,p_uspe_id,p_grin2_id,p_tire_id,p_cara_id,p_vape_id,p_valor);     
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_GRIN2_VACA se presento '||SQLERRM;                                                                                   
END pr_InsertarTmpAPGRIN2_VACA_DR;


PROCEDURE pr_InsertarI2_D
/********************************************************************************
 descripcion   : método para incorporar información sobre la tabla SIPSA_TMP_ENCA_INSUMOS_D
 
 Realizado por : Marco Guzman
 Fecha Creacion: 19/06/2019 
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   INSERT INTO SIPSA_TMP_ENCA_INSUMOS_D(FUTI_ID,USPE_ID,ARTI_ID,GRIN_ID,ENIN_FECHA_PROGRAMADA,ENIN_ARTI_NOMBRE,
                                        ENIN_PRECIO_PROM_ANTERIOR,ENIN_NOVEDAD,OBSE_ID,ENIN_OBSERVACION,
                                        ENIN_NOMBRE_FUENTE,ENIN_MUNICIPIO,ENIN_MUNI_ID,ENIN_DIRECCION,ENIN_TELEFONO,
                                        ENIN_EMAIL,ENIN_INFORMANTE,ENIN_TEL_INFORMANTE)
   VALUES (p_futi_id, p_uspe_id,p_arti_id, p_grin2_id, p_prre_fecha_programada,p_arti_nombre,
                 p_precio_prom_anterior, p_novedad, p_obse_id, p_observacion,
                 p_nom_fuente,p_municipio,p_muni_id,p_direccion,p_telefono,p_email,p_informante,p_tel_informante);

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_TMP_ENCA_INSUMOS_D se presento '||SQLERRM;
END pr_InsertarI2_D;






END PQ_SIPSA_TMP_ENCABEZADOS;
