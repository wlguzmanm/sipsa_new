create or replace PACKAGE BODY            PQ_SIPSA_ENCABEZADOS IS

PROCEDURE pr_InsertarM 
/********************************************************************************
 DESCRIPCION   : Metodo para incorporar informacion sobre la tabla SIPSA_ENCA_MAYORISTA

 PARAMETROS    :
 IN            : p_sistema                        Sistema al que pertenece la tabla para generar la secuencia
                 p_nombre
                 p_periodicidad
                 p_sector
                 p_mensual_observado
                 p_tipo
                 p_tipo_fuente
                 p_cantidad_base
                 p_unidad_medida_base
                 p_cantidad_minima_recoleccion
                 p_cantidad_maxima_recoleccion
                 p_usuario

 OUT           : p_id                Valor del identificador del registro a crear
                 p_codigo_error      Valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           Mensaje del error ocurrido

 REALIZADO POR : Julian Andres Rodriguez Rodriguez

 FECHA CREACION: 09/07/2008

 MODIFICADO POR: Julian Andres Rodriguez Rodriguez
 FECHA MODIFICA: 28/04/2009
 CAMBIOS       :
*******************************************************************************/
(p_futi_id                       IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_MAYORISTA.uspe_id%TYPE,
 p_enma_ronda                      IN SIPSA_ENCA_MAYORISTA.enma_ronda%TYPE,
 p_prre_fecha_programada                      IN SIPSA_ENCA_MAYORISTA.prre_fecha_programada%TYPE,
 p_enma_hi                      IN SIPSA_ENCA_MAYORISTA.enma_hora_inicial%TYPE,
 p_enma_hf                      IN SIPSA_ENCA_MAYORISTA.enma_hora_final%TYPE,
 p_usuario                       IN SIPSA_ENCA_MAYORISTA.enma_usuario_creacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2) IS

contador NUMBER;

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador := NULL;
   --p_id    := Pq_Gen_Tablas_Secuencias.fn_ObtenerLlave(p_sistema,'SIPSA_ENCA_INSUMOS2');
   
   SELECT COUNT(1)  INTO contador
   FROM SIPSA_ENCA_MAYORISTA
   WHERE futi_id = p_futi_id
   AND uspe_id = p_uspe_id
   AND enma_ronda = p_enma_ronda
   AND prre_fecha_programada = p_prre_fecha_programada;
   
    IF contador < 1  THEN
            INSERT INTO SIPSA_ENCA_MAYORISTA(futi_id,uspe_id,enma_ronda,prre_fecha_programada,enma_hora_inicial,enma_hora_final,enma_usuario_creacion)
            VALUES (p_futi_id,p_uspe_id,p_enma_ronda,p_prre_fecha_programada,p_enma_hi,p_enma_hf,UPPER(p_usuario));
    ELSE
            PQ_SIPSA_ENCABEZADOS.pr_ActualizarM(p_futi_id,p_uspe_id,p_enma_ronda,p_prre_fecha_programada,p_enma_hi,p_enma_hf,UPPER(p_usuario),p_codigo_error,p_mensaje);
    END IF;
EXCEPTION
       WHEN OTHERS THEN
            IF SQLCODE = -1 THEN
                --p_codigo_error := 1;
                --p_mensaje      := 'YA EXISTE LA FUENTE: '||p_futi_id ||' Y EL USUARIO:' ||p_uspe_id ||' EN LA RONDA:' ||p_enma_ronda ||' DEL :' ||p_prre_fecha_programada ;
            --ELSE
                p_codigo_error := 1;
                p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_ENCA_MAYORISTA SE PRESENTO '||SQLERRM;
            END IF;
END pr_InsertarM;

PROCEDURE pr_EliminarM
/********************************************************************************
 DESCRIPCION   : Metodo para borrar informacion de la tabla SIPSA_ENCA_MAYORISTA

 PARAMETROS    :
 IN            : p_futi_id               Valor del identificador del registro a eliminar

 OUT           : p_codigo_error    Valor que indica la ocurrencia o no de error en el proceso
                 p_mensaje         Mensaje del error ocurrido

 REALIZADO POR : Julian Andres Rodriguez Rodriguez

 FECHA CREACION: 09/07/2008

 MODIFICADO POR:
 FECHA MODIFICA:
 CAMBIOS       :
*******************************************************************************/
( p_futi_id                       IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_MAYORISTA.uspe_id%TYPE,
 p_enma_ronda                      IN SIPSA_ENCA_MAYORISTA.enma_ronda%TYPE,
 p_prre_fecha_programada                      IN SIPSA_ENCA_MAYORISTA.prre_fecha_programada%TYPE,
 p_usuario                       IN SIPSA_ENCA_MAYORISTA.enma_usuario_modificacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2)  IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   DELETE SIPSA_ENCA_MAYORISTA enma
    WHERE enma.futi_id = p_futi_id
    AND     enma.uspe_id= p_uspe_id
    AND     enma.enma_ronda= p_enma_ronda
    AND     enma.prre_fecha_programada= p_prre_fecha_programada;

   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ELIMINAR DE LA TABLA SIPSA_ENCA_MAYORISTA CON EL IDENTIFICADOR DADO ';
   END IF;

EXCEPTION
       WHEN OTHERS THEN
           IF SQLCODE = -2292 THEN
            p_codigo_error := 1;
            p_mensaje      := 'NO SE PUEDE ELIMINAR LA FUENTE, EL USUARIO, LA RONDA Y LA FECHA,  DEBIDO A QUE ESTA SE ENCUENTRAN REGISTRADOS EN OTRAS TABLAS';
           ELSE
            p_codigo_error := 1;
            p_mensaje      := 'AL ELIMINAR DE LA TABLA SIPSA_ENCA_MAYORISTA SE PRESENTO '||SQLERRM;
           END IF;
END pr_EliminarM;

PROCEDURE pr_ActualizarM
/********************************************************************************
 DESCRIPCION   : Metodo para borrar informacion de la tabla SIPSA_ENCA_MAYORISTA

 PARAMETROS    :
 IN            : p_futi_id               Valor del identificador del registro a eliminar

 OUT           : p_codigo_error    Valor que indica la ocurrencia o no de error en el proceso
                 p_mensaje         Mensaje del error ocurrido

 REALIZADO POR : Julian Andres Rodriguez Rodriguez

 FECHA CREACION: 09/07/2008

 MODIFICADO POR:
 FECHA MODIFICA:
 CAMBIOS       :
*******************************************************************************/
( p_futi_id                       IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_MAYORISTA.uspe_id%TYPE,
 p_enma_ronda                      IN SIPSA_ENCA_MAYORISTA.enma_ronda%TYPE,
 p_prre_fecha_programada                      IN SIPSA_ENCA_MAYORISTA.prre_fecha_programada%TYPE,
 p_enma_hi                      IN SIPSA_ENCA_MAYORISTA.enma_hora_inicial%TYPE,
 p_enma_hf                      IN SIPSA_ENCA_MAYORISTA.enma_hora_final%TYPE,
 p_usuario                       IN SIPSA_ENCA_MAYORISTA.enma_usuario_modificacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2)  IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   UPDATE SIPSA_ENCA_MAYORISTA enma
   SET enma_hora_inicial= p_enma_hi,
          enma_hora_final= p_enma_hf
    WHERE enma.futi_id = p_futi_id
    AND     enma.uspe_id= p_uspe_id
    AND     enma.enma_ronda= p_enma_ronda
    AND     enma.prre_fecha_programada= p_prre_fecha_programada;


   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ACTUALIZAR EN LA TABLA SIPSA_ENCA_MAYORISTA CON EL IDENTIFICADOR DADO ';
   END IF;

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL ACTUALIZAR DE LA TABLA SIPSA_ENCA_MAYORISTA SE PRESENTO '||SQLERRM;
END pr_ActualizarM;

PROCEDURE pr_ConsultarM
/********************************************************************************
 DESCRIPCION   : Metodo para consultar solo la informacion de un registro de la tabla
                 SIPSA_ENCA_MAYORISTA

 PARAMETROS    :
 IN            : p_futi_id            Valor del identificador del registro a consultar

 OUT           : p_resultado    Cursor con la informacion de la tabla
                 p_codigo_error Valor que indica la ocurrencia de error en el proceso
                 p_mensaje      Mensaje del error ocurrido

 REALIZADO POR : Julian Andres Rodriguez Rodriguez

 FECHA CREACION: 09/07/2008

 MMODIFICADO POR: Julian Andres Rodriguez Rodriguez
 FECHA MODIFICA: 25/07/2008
                 21/10/2009
 CAMBIOS       : Adicionar parametro p_fuente_detalle
*******************************************************************************/
( p_futi_id                       IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_MAYORISTA.uspe_id%TYPE,
 p_prre_fecha_programada                      IN SIPSA_ENCA_MAYORISTA.prre_fecha_programada%TYPE,
 p_resultado         OUT Pq_Gen_General.ty_cursor,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   OPEN p_resultado FOR
  SELECT enma.futi_id, PQ_SIPSA_FUENTES.fn_ObtenerNombre(enma.futi_id) Fuente,
                      usua.usua_id, PQ_SIPSA_USUARIOS.fn_ObtenerNombre(usua.usua_id) Usuario,
                      enma.enma_ronda,                      
                      enma.enma_hora_inicial,
                      enma.enma_hora_final,
                      enma.enma_fecha_creacion,
                      enma.enma_usuario_creacion,
                      enma.enma_fecha_modificacion,
                      enma.enma_usuario_modificacion
        FROM SIPSA_ENCA_MAYORISTA enma,SIPSA_USUARIOS usua,SIPSA_USUARIOS_PERFILES  uspe               
        WHERE enma.futi_id = p_futi_id
        AND     enma.uspe_id= uspe.uspe_id
        AND     enma.uspe_id= p_uspe_id
        AND     uspe.usua_id=usua.usua_id
        AND     enma.prre_fecha_programada = p_prre_fecha_programada;

EXCEPTION
    WHEN OTHERS THEN
      p_codigo_error := 1;
      p_mensaje      := 'AL CONSULTAR LA TABLA SIPSA_FUENTES SE PRESENTO '||SQLERRM;

END pr_ConsultarM;

PROCEDURE pr_InsertarA
/********************************************************************************
 DESCRIPCION   : Metodo para incorporar informacion sobre la tabla SIPSA_ENCA_ABASTECIMIENTO

 PARAMETROS    :
 IN            : p_sistema                        Sistema al que pertenece la tabla para generar la secuencia
                 p_nombre
                 p_periodicidad
                 p_sector
                 p_mensual_observado
                 p_tipo
                 p_tipo_fuente
                 p_cantidad_base
                 p_unidad_medida_base
                 p_cantidad_minima_recoleccion
                 p_cantidad_maxima_recoleccion
                 p_usuario

 OUT           : p_id                Valor del identificador del registro a crear
                 p_codigo_error      Valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           Mensaje del error ocurrido

 REALIZADO POR : Julian Andres Rodriguez Rodriguez

 FECHA CREACION: 09/07/2008

 MODIFICADO POR: Julian Andres Rodriguez Rodriguez
 FECHA MODIFICA: 28/04/2009
 CAMBIOS       :
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   --p_id    := Pq_Gen_Tablas_Secuencias.fn_ObtenerLlave(p_sistema,'SIPSA_ENCA_ABASTECIMIENTO');

   INSERT INTO SIPSA_ENCA_ABASTECIMIENTO(futi_id,uspe_id,prre_fecha_programada,enab_placa,enab_hora,tive_id,
                                                                      tive_cual_vehiculo,enab_ubicacion,enab_procedencia,enab_destino,
                                                                      time_id,time_cual_mercado,enab_basc_desc,enab_observaciones,enab_usuario_creacion)
   VALUES (p_futi_id,p_uspe_id,p_prre_fecha_programada,p_enab_hora,p_tive_id,
                 p_tive_cual,p_enab_placa,p_enab_ubicacion,p_enab_procedencia,p_enab_destino,
                 p_time_id,p_time_cual,p_enab_basc_desc,p_enab_observaciones,UPPER(p_usuario));

EXCEPTION
       WHEN OTHERS THEN
            IF SQLCODE = -1 THEN
             p_codigo_error := 1;
             p_mensaje      := 'YA EXISTE LA FUENTE: '||p_futi_id ||' Y EL USUARIO:' ||p_uspe_id ||' EN LA FECHA:' ||p_prre_fecha_programada ;
            ELSE
             p_codigo_error := 1;
             p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_ENCA_ABASTECIMIENTO SE PRESENTO '||SQLERRM;
            END IF;
END pr_InsertarA;

PROCEDURE pr_EliminarA
/********************************************************************************
 DESCRIPCION   : Metodo para borrar informacion de la tabla SIPSA_ENCA_ABASTECIMIENTO

 PARAMETROS    :
 IN            : p_futi_id               Valor del identificador del registro a eliminar

 OUT           : p_codigo_error    Valor que indica la ocurrencia o no de error en el proceso
                 p_mensaje         Mensaje del error ocurrido

 REALIZADO POR : Julian Andres Rodriguez Rodriguez

 FECHA CREACION: 09/07/2008

 MODIFICADO POR:
 FECHA MODIFICA:
 CAMBIOS       :
*******************************************************************************/
(p_futi_id                       IN SIPSA_ENCA_ABASTECIMIENTO.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_ABASTECIMIENTO.uspe_id%TYPE,
 p_prre_fecha_programada                      IN SIPSA_ENCA_ABASTECIMIENTO.prre_fecha_programada%TYPE,
 p_enab_placa                      IN SIPSA_ENCA_ABASTECIMIENTO.enab_placa%TYPE,
 p_usuario                       IN SIPSA_ENCA_ABASTECIMIENTO.enab_usuario_modificacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2)  IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   DELETE SIPSA_ENCA_ABASTECIMIENTO enab
    WHERE enab.futi_id = p_futi_id
    AND     enab.uspe_id= p_uspe_id
    AND     enab.prre_fecha_programada= p_prre_fecha_programada
    AND     enab.enab_placa= p_enab_placa;

   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ELIMINAR DE LA TABLA SIPSA_ENCA_ABASTECIMIENTO CON EL IDENTIFICADOR DADO ';
   END IF;

EXCEPTION
       WHEN OTHERS THEN
           IF SQLCODE = -2292 THEN
            p_codigo_error := 1;
            p_mensaje      := 'NO SE PUEDE ELIMINAR LA FUENTE, EL USUARIO Y LA FECHA,  DEBIDO A QUE ESTA SE ENCUENTRAN REGISTRADOS EN OTRAS TABLAS';
           ELSE
            p_codigo_error := 1;
            p_mensaje      := 'AL ELIMINAR DE LA TABLA SIPSA_ENCA_ABASTECIMIENTO SE PRESENTO '||SQLERRM;
           END IF;
END pr_EliminarA;



PROCEDURE pr_InsertarEncaAbas
/********************************************************************************
 DESCRIPCION   : Metodo para incorporar o actualizar informacion sobre la tabla SIPSA_ENCA_ABASTECIMIENTO

 PARAMETROS    :
 IN            : p_sistema                        Sistema al que pertenece la tabla para generar la secuencia
                 p_nombre
                 p_periodicidad
                 p_sector
                 p_mensual_observado
                 p_tipo
                 p_tipo_fuente
                 p_cantidad_base
                 p_unidad_medida_base
                 p_cantidad_minima_recoleccion
                 p_cantidad_maxima_recoleccion
                 p_usuario

 OUT           : p_id                Valor del identificador del registro a crear
                 p_codigo_error      Valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           Mensaje del error ocurrido

 REALIZADO POR : AMM - Adonay Mantilla

 FECHA CREACION: 04/08/2016
*******************************************************************************/
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
 p_mensaje                      OUT VARCHAR2) IS

v_reg_enc_abas NUMBER(18,0);

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   
    begin
      SELECT COUNT(*)  INTO v_reg_enc_abas  FROM SIPSA_ENCA_ABASTECIMIENTO enc
      WHERE   enc.futi_id = p_futi_id
      AND enc.prre_fecha_programada = p_prre_fecha_programada
      AND enc.enab_placa = p_enab_placa
      AND enc.enab_hora = p_enab_hora;
    end;
    
     IF(v_reg_enc_abas > 0) THEN
        UPDATE SIPSA_ENCA_ABASTECIMIENTO enc1
                    SET enc1.futi_id = p_futi_id,
                        enc1.uspe_id = p_uspe_id,
                        enc1.tive_id = p_tive_id,
                        enc1.tive_cual_vehiculo = p_tive_cual,
                        enc1.enab_ubicacion = p_enab_ubicacion,
                        enc1.enab_procedencia = p_enab_procedencia,
                        enc1.enab_destino = p_enab_destino,
                        enc1.time_id = p_time_id,
                        enc1.time_cual_mercado = p_time_cual,
                        enc1.enab_basc_desc = p_enab_basc_desc,
                        enc1.enab_observaciones = p_enab_observaciones,
                        enc1.enab_usuario_modificacion = UPPER(p_usuario)
                                     
                WHERE   enc1.futi_id = p_futi_id
                        AND enc1.prre_fecha_programada = p_prre_fecha_programada
                        AND enc1.enab_placa = p_enab_placa
                        AND enc1.enab_hora = p_enab_hora;
    ELSE
         INSERT INTO SIPSA_ENCA_ABASTECIMIENTO(futi_id,uspe_id,prre_fecha_programada,enab_placa,enab_hora,tive_id,
                                                                            tive_cual_vehiculo,enab_ubicacion,enab_procedencia,enab_destino,
                                                                            time_id,time_cual_mercado,enab_basc_desc,enab_observaciones,enab_usuario_creacion)
         VALUES (p_futi_id,p_uspe_id,p_prre_fecha_programada ,p_enab_placa,p_enab_hora,p_tive_id,
                       p_tive_cual,p_enab_ubicacion,p_enab_procedencia,p_enab_destino,
                       p_time_id,p_time_cual,p_enab_basc_desc,p_enab_observaciones,UPPER(p_usuario));
    END IF;

EXCEPTION
       WHEN OTHERS THEN
            IF SQLCODE = -1 THEN
             p_codigo_error := 1;
             p_mensaje      := 'YA EXISTE LA FUENTE: '||p_futi_id ||' Y EL USUARIO:' ||p_uspe_id ||' EN LA FECHA:' ||p_prre_fecha_programada ;
            ELSE
             p_codigo_error := 1;
             p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_ENCA_ABASTECIMIENTO SE PRESENTO '||SQLERRM;
            END IF;
END pr_InsertarEncaAbas;



PROCEDURE pr_EliminarEncaAbas
/********************************************************************************
 DESCRIPCION   : Metodo para borrar informacion del encabezado de la tabla SIPSA_ENCA_ABASTECIMIENTO

 PARAMETROS    :
 IN            : p_futi_id               Valor del identificador del registro a eliminar

 OUT           : p_codigo_error    Valor que indica la ocurrencia o no de error en el proceso
                 p_mensaje         Mensaje del error ocurrido

 REALIZADO POR : AMM - Adonay Mantilla

 FECHA CREACION: 04/08/2016

 MODIFICADO POR:
 FECHA MODIFICA:
 CAMBIOS       :
*******************************************************************************/
(p_futi_id                      IN SIPSA_ENCA_ABASTECIMIENTO.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_ABASTECIMIENTO.uspe_id%TYPE,
 p_prre_fecha_programada        IN SIPSA_ENCA_ABASTECIMIENTO.prre_fecha_programada%TYPE,
 p_enab_placa                   IN SIPSA_ENCA_ABASTECIMIENTO.enab_placa%TYPE,
 p_enab_hora                    IN SIPSA_ENCA_ABASTECIMIENTO.enab_hora%TYPE,
 p_codigo_error                 OUT NUMBER,
 p_mensaje                      OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   DELETE SIPSA_ENCA_ABASTECIMIENTO enab
    WHERE   enab.futi_id = p_futi_id
    AND     enab.uspe_id= p_uspe_id
    AND     enab.prre_fecha_programada= p_prre_fecha_programada
    AND     enab.enab_placa= p_enab_placa
    AND     enab.ENAB_HORA = p_enab_hora;

   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ELIMINAR DE LA TABLA SIPSA_ENCA_ABASTECIMIENTO CON EL IDENTIFICADOR DADO ';
   END IF;

EXCEPTION
       WHEN OTHERS THEN
           IF SQLCODE = -2292 THEN
            p_codigo_error := 1;
            p_mensaje      := 'NO SE PUEDE ELIMINAR LA FUENTE, EL USUARIO Y LA FECHA,  DEBIDO A QUE ESTA SE ENCUENTRAN REGISTRADOS EN OTRAS TABLAS';
           ELSE
            p_codigo_error := 1;
            p_mensaje      := 'AL ELIMINAR DE LA TABLA SIPSA_ENCA_ABASTECIMIENTO SE PRESENTO '||SQLERRM;
           END IF;
END pr_EliminarEncaAbas;


PROCEDURE pr_ConsultarEncaAbas
/********************************************************************************
 DESCRIPCION   : Metodo para consultar solo la informacion de un registro de la tabla
                 SIPSA_ENCA_ABASTECIMIENTO

 PARAMETROS    :
 IN            : p_futi_id            Valor del identificador del registro a consultar

 OUT           : p_resultado    Cursor con la informacion de la tabla
                 p_codigo_error Valor que indica la ocurrencia de error en el proceso
                 p_mensaje      Mensaje del error ocurrido

 REALIZADO POR : AMM - Adonay Mantilla

 FECHA CREACION: 31/08/2016
******************************************************************************/
(p_futi_id                      IN SIPSA_ENCA_ABASTECIMIENTO.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_ABASTECIMIENTO.uspe_id%TYPE,
 p_prre_fecha_programada        IN SIPSA_ENCA_ABASTECIMIENTO.prre_fecha_programada%TYPE,
 p_enab_placa                   IN SIPSA_ENCA_ABASTECIMIENTO.enab_placa%TYPE,
 p_enab_hora                    IN SIPSA_ENCA_ABASTECIMIENTO.enab_hora%TYPE,
 p_resultado                    OUT Pq_Gen_General.ty_cursor,
 p_codigo_error                 OUT NUMBER,
 p_mensaje                      OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   OPEN p_resultado FOR
    SELECT      enab.futi_id, PQ_SIPSA_FUENTES.fn_ObtenerNombre(enab.futi_id) Fuente,
                usua.usua_id, PQ_SIPSA_USUARIOS.fn_ObtenerNombre(usua.usua_id) Usuario,
                enab.prre_fecha_programada,
                enab.enab_placa,
                enab.enab_hora,
                enab.tive_id,              
                enab.enab_ubicacion,      
                enab.enab_observaciones,
                enab.enab_fecha_creacion,
                enab.enab_usuario_creacion,
                enab.enab_fecha_modificacion,
                enab.enab_usuario_modificacion
    FROM SIPSA_ENCA_ABASTECIMIENTO enab,SIPSA_USUARIOS usua,SIPSA_USUARIOS_PERFILES  uspe               
    WHERE enab.futi_id = p_futi_id
    AND     enab.uspe_id= uspe.uspe_id
    --AND     enab.uspe_id= p_uspe_id
    AND     uspe.usua_id=usua.usua_id
    AND     enab.prre_fecha_programada = p_prre_fecha_programada
    AND     enab.ENAB_PLACA = p_enab_placa
    AND     enab.ENAB_HORA = p_enab_hora;

EXCEPTION
    WHEN OTHERS THEN
      p_codigo_error := 1;
      p_mensaje      := 'AL CONSULTAR LA TABLA SIPSA_ENCA_ABASTECIMIENTO SE PRESENTO '||SQLERRM;

END pr_ConsultarEncaAbas;

PROCEDURE pr_ActualizarA
/********************************************************************************
 DESCRIPCION   : Metodo para borrar informacion de la tabla SIPSA_ENCA_ABASTECIMIENTO

 PARAMETROS    :
 IN            : p_futi_id               Valor del identificador del registro a eliminar

 OUT           : p_codigo_error    Valor que indica la ocurrencia o no de error en el proceso
                 p_mensaje         Mensaje del error ocurrido

 REALIZADO POR : Julian Andres Rodriguez Rodriguez

 FECHA CREACION: 09/07/2008

 MODIFICADO POR:
 FECHA MODIFICA:
 CAMBIOS       :
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   UPDATE SIPSA_ENCA_ABASTECIMIENTO enab
   SET  enab.enab_placa= p_enab_placa,
           enab.enab_hora=p_enab_hora,
           enab.tive_id=p_tive_id,
           enab.tive_cual_vehiculo=p_tive_cual,
           enab.enab_procedencia=p_enab_procedencia,
           enab.enab_destino=p_enab_destino,
           enab.time_id=p_time_id,
           enab.time_cual_mercado=p_time_cual,
           enab.enab_basc_desc=p_enab_basc_desc,
           enab.enab_observaciones=p_enab_observaciones,
           enab.enab_usuario_modificacion=UPPER(p_usuario)           
    WHERE enab.futi_id = p_futi_id
    AND     enab.uspe_id= p_uspe_id
    AND     enab.prre_fecha_programada= p_prre_fecha_programada;

   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ACTUALIZAR EN LA TABLA SIPSA_ENCA_ABASTECIMIENTO CON EL IDENTIFICADOR DADO ';
   END IF;

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL ACTUALIZAR DE LA TABLA SIPSA_ENCA_ABASTECIMIENTO SE PRESENTO '||SQLERRM;
END pr_ActualizarA;

PROCEDURE pr_ConsultarA
/********************************************************************************
 DESCRIPCION   : Metodo para consultar solo la informacion de un registro de la tabla
                 SIPSA_ENCA_ABASTECIMIENTO

 PARAMETROS    :
 IN            : p_futi_id            Valor del identificador del registro a consultar

 OUT           : p_resultado    Cursor con la informacion de la tabla
                 p_codigo_error Valor que indica la ocurrencia de error en el proceso
                 p_mensaje      Mensaje del error ocurrido

 REALIZADO POR : Julian Andres Rodriguez Rodriguez

 FECHA CREACION: 09/07/2008

 MMODIFICADO POR: Julian Andres Rodriguez Rodriguez
 FECHA MODIFICA: 25/07/2008
                 21/10/2009
 CAMBIOS       : Adicionar parametro p_fuente_detalle
*******************************************************************************/
( p_futi_id              IN SIPSA_ENCA_ABASTECIMIENTO.futi_id%TYPE,
 p_uspe_id             IN SIPSA_ENCA_ABASTECIMIENTO.uspe_id%TYPE,
 p_prre_fecha_programada        IN SIPSA_ENCA_ABASTECIMIENTO.prre_fecha_programada%TYPE,
 p_resultado            OUT Pq_Gen_General.ty_cursor,
 p_codigo_error       OUT NUMBER,
 p_mensaje             OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   OPEN p_resultado FOR
    SELECT enab.futi_id, PQ_SIPSA_FUENTES.fn_ObtenerNombre(enab.futi_id) Fuente,
                usua.usua_id, PQ_SIPSA_USUARIOS.fn_ObtenerNombre(usua.usua_id) Usuario,
                enab.prre_fecha_programada,
                enab.enab_placa,
                enab.enab_hora,
                enab.tive_id,
                enab.tive_cual_vehiculo,
                enab.enab_ubicacion,
                enab.enab_procedencia,
                enab.enab_destino,
                enab.time_id,
                enab.time_cual_mercado,
                enab.enab_basc_desc,
                enab.enab_observaciones,
                enab.enab_fecha_creacion,
                enab.enab_usuario_creacion,
                enab.enab_fecha_modificacion,
                enab.enab_usuario_modificacion
    FROM SIPSA_ENCA_ABASTECIMIENTO enab,SIPSA_USUARIOS usua,SIPSA_USUARIOS_PERFILES  uspe               
    WHERE enab.futi_id = p_futi_id
    AND     enab.uspe_id= uspe.uspe_id
    AND     enab.uspe_id= p_uspe_id
    AND     uspe.usua_id=usua.usua_id
    AND     enab.prre_fecha_programada = p_prre_fecha_programada;

EXCEPTION
    WHEN OTHERS THEN
      p_codigo_error := 1;
      p_mensaje      := 'AL CONSULTAR LA TABLA SIPSA_ENCA_ABASTECIMIENTO SE PRESENTO '||SQLERRM;

END pr_ConsultarA;

PROCEDURE pr_InsertarI2
/********************************************************************************
 DESCRIPCION   : Metodo para incorporar informacion sobre la tabla SIPSA_ENCA_INSUMOS2

 PARAMETROS    :
 IN            : p_sistema                        Sistema al que pertenece la tabla para generar la secuencia
                 p_nombre
                 p_periodicidad
                 p_sector
                 p_mensual_observado
                 p_tipo
                 p_tipo_fuente
                 p_cantidad_base
                 p_unidad_medida_base
                 p_cantidad_minima_recoleccion
                 p_cantidad_maxima_recoleccion
                 p_usuario

 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 10/04/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS


contador NUMBER;

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador := NULL;
   --p_id    := Pq_Gen_Tablas_Secuencias.fn_ObtenerLlave(p_sistema,'SIPSA_ENCA_INSUMOS2');
   
   SELECT COUNT(1)  INTO contador
   FROM SIPSA_ENCA_INSUMOS2
   WHERE futi_id = p_futi_id
   AND uspe_id = p_uspe_id
   AND grin2_id = p_grin2_id
   AND prre_fecha_programada = p_prre_fecha_programada;
   
   IF contador < 1  THEN
        INSERT INTO SIPSA_ENCA_INSUMOS2(futi_id,uspe_id,grin2_id,prre_fecha_programada,enin2_precio_prom_anterior,enin2_novedad,enin2_ftes_capturadas,obse_id,enin2_observacion,enin2_usuario_creacion)
        VALUES (p_futi_id, p_uspe_id, p_grin2_id, p_prre_fecha_programada,p_precio_prom_anterior, p_novedad, p_ftes_capturadas,p_obse_id, p_observacion,UPPER(p_usuario));
   ELSE
        PQ_SIPSA_ENCABEZADOS.pr_ActualizarI2(p_futi_id, p_uspe_id, p_grin2_id, p_prre_fecha_programada,p_precio_prom_anterior, p_novedad, 
                                                                 p_ftes_capturadas,p_obse_id, p_observacion,UPPER(p_usuario),p_codigo_error,p_mensaje);
   END IF;                                             
EXCEPTION
       WHEN OTHERS THEN
            --IF SQLCODE = -1 THEN
            -- p_codigo_error := 1;
             --p_mensaje      := 'YA EXISTE LA FUENTE: '||p_futi_id ||' Y EL USUARIO:' ||p_uspe_id ||' EN LA FECHA:' ||p_prre_fecha_programada ;
            --ELSE
             p_codigo_error := 1;
             p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_ENCA_INSUMOS2 SE PRESENTO '||SQLERRM;
            --END IF;
END pr_InsertarI2;


PROCEDURE pr_EliminarI2
/********************************************************************************
 DESCRIPCION   : Metodo para incorporar informacion sobre la tabla SIPSA_ENCA_INSUMOS2

 PARAMETROS    :
 IN            : p_sistema                        Sistema al que pertenece la tabla para generar la secuencia
                 p_nombre
                 p_periodicidad
                 p_sector
                 p_mensual_observado
                 p_tipo
                 p_tipo_fuente
                 p_cantidad_base
                 p_unidad_medida_base
                 p_cantidad_minima_recoleccion
                 p_cantidad_maxima_recoleccion
                 p_usuario

 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 10/04/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
(p_futi_id                       IN SIPSA_ENCA_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_INSUMOS2.uspe_id%TYPE,
 p_grin2_id                    IN SIPSA_ENCA_INSUMOS2.grin2_id%TYPE,
 p_prre_fecha_programada     IN SIPSA_ENCA_INSUMOS2.prre_fecha_programada%TYPE,
 p_usuario                       IN SIPSA_ENCA_INSUMOS2.enin2_usuario_modificacion%TYPE,  
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   --p_id    := Pq_Gen_Tablas_Secuencias.fn_ObtenerLlave(p_sistema,'SIPSA_ENCA_INSUMOS2');

   DELETE SIPSA_ENCA_INSUMOS2 enin2
    WHERE enin2.futi_id = p_futi_id
    AND     enin2.uspe_id= p_uspe_id
    AND     enin2.grin2_id= p_grin2_id
    AND     enin2.prre_fecha_programada= p_prre_fecha_programada;
    

   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ELIMINAR DE LA TABLA SIPSA_ENCA_INSUMOS2 CON EL IDENTIFICADOR DADO ';
   END IF;

EXCEPTION
       WHEN OTHERS THEN
           IF SQLCODE = -2292 THEN
            p_codigo_error := 1;
            p_mensaje      := 'NO SE PUEDE ELIMINAR LA FUENTE, EL USUARIO Y LA FECHA,  DEBIDO A QUE ESTA SE ENCUENTRAN REGISTRADOS EN OTRAS TABLAS';
           ELSE
            p_codigo_error := 1;
            p_mensaje      := 'AL ELIMINAR DE LA TABLA SIPSA_ENCA_INSUMOS2 SE PRESENTO '||SQLERRM;
           END IF;

END pr_EliminarI2;

PROCEDURE pr_ActualizarI2
/********************************************************************************
 DESCRIPCION   : Metodo para borrar informacion de la tabla SIPSA_ENCA_INSUMOS2

 PARAMETROS    :
 IN            : p_futi_id               Valor del identificador del registro a eliminar

 OUT           : p_codigo_error    Valor que indica la ocurrencia o no de error en el proceso
                 p_mensaje         Mensaje del error ocurrido

 REALIZADO POR : Julian Andres Rodriguez Rodriguez

 FECHA CREACION: 09/07/2008

 MODIFICADO POR:
 FECHA MODIFICA:
 CAMBIOS       :
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   UPDATE SIPSA_ENCA_INSUMOS2 enin2
   SET  enin2.enin2_novedad=p_novedad,
           enin2.enin2_ftes_capturadas=p_ftes_capturadas,
           enin2.obse_id=p_obse_id,
           enin2.enin2_observacion=p_observacion,
           enin2.enin2_usuario_modificacion=UPPER(p_usuario)           
    WHERE enin2.futi_id = p_futi_id
    AND     enin2.uspe_id= p_uspe_id
    AND     enin2.grin2_id= p_grin2_id
    AND     enin2.prre_fecha_programada= p_prre_fecha_programada;

   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ACTUALIZAR EN LA TABLA SIPSA_ENCA_INSUMOS2 CON EL IDENTIFICADOR DADO ';
   END IF;

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL ACTUALIZAR DE LA TABLA SIPSA_ENCA_INSUMOS2 SE PRESENTO '||SQLERRM;
END pr_ActualizarI2;

PROCEDURE pr_ConsultarI2
/********************************************************************************
 DESCRIPCION   : Metodo para consultar solo la informacion de un registro de la tabla
                 SIPSA_ENCA_INSUMOS2

 PARAMETROS    :
 IN            : p_futi_id            Valor del identificador del registro a consultar

 OUT           : p_resultado    Cursor con la informacion de la tabla
                 p_codigo_error Valor que indica la ocurrencia de error en el proceso
                 p_mensaje      Mensaje del error ocurrido

 REALIZADO POR : Julian Andres Rodriguez Rodriguez

 FECHA CREACION: 09/07/2008

 MMODIFICADO POR: Julian Andres Rodriguez Rodriguez
 FECHA MODIFICA: 25/07/2008
                 21/10/2009
 CAMBIOS       : Adicionar parametro p_fuente_detalle
*******************************************************************************/
( p_futi_id                       IN SIPSA_ENCA_INSUMOS2.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_INSUMOS2.uspe_id%TYPE,
 p_prre_fecha_programada                      IN SIPSA_ENCA_INSUMOS2.prre_fecha_programada%TYPE,
 p_resultado         OUT Pq_Gen_General.ty_cursor,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2) IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   OPEN p_resultado FOR
    SELECT enin2.futi_id, PQ_SIPSA_FUENTES.fn_ObtenerNombre(enin2.futi_id) Fuente,
                usua.usua_id, PQ_SIPSA_USUARIOS.fn_ObtenerNombre(usua.usua_id) Usuario,
                arti.arti_id,
                PQ_SIPSA_ARTICULOS.fn_ObtenerNombre(arti.arti_id) Articulo,
                enin2.grin2_id,
                enin2.prre_fecha_programada,
                enin2.enin2_precio_prom_anterior,
                enin2.enin2_novedad,
                enin2.enin2_ftes_capturadas,
                enin2.obse_id,
                enin2.enin2_observacion,
                enin2.enin2_fecha_creacion,
                enin2.enin2_usuario_creacion,
                enin2.enin2_fecha_modificacion,
                enin2.enin2_usuario_modificacion
    FROM SIPSA_ENCA_INSUMOS2 enin2,SIPSA_USUARIOS usua,SIPSA_USUARIOS_PERFILES  uspe , 
             SIPSA_ARTICULOS arti,  SIPSA_GRUPO_INSUMOS2 grin2            
    WHERE enin2.futi_id = p_futi_id
    AND     enin2.uspe_id = p_uspe_id
    AND     enin2.grin2_id = grin2.grin2_id
    AND     grin2.arti_id = arti.arti_id
    AND     enin2.prre_fecha_programada= p_prre_fecha_programada;

EXCEPTION
    WHEN OTHERS THEN
      p_codigo_error := 1;
      p_mensaje      := 'AL CONSULTAR LA TABLA SIPSA_ENCA_INSUMOS2 SE PRESENTO '||SQLERRM;

END pr_ConsultarI2;



PROCEDURE pr_ConsultarUltimoEncReg
 /********************************************************************************
 DESCRIPCION   : Metodo que permite consultar la diferencia con la fecha del ultimo registro, que equivale al ultimo ingreso de un vehiculo

 REALIZADO POR : AMM - Adonay Mantilla

 FECHA CREACION: 13/07/2016
 *******************************************************************************/
(p_enab_placa                   IN VARCHAR2,
 p_resultado                    OUT Pq_Gen_General.ty_cursor,
 p_codigo_error                 OUT NUMBER,
 p_mensaje                      OUT VARCHAR2)IS
 
 BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   OPEN p_resultado FOR
    SELECT  floor(abs(dur))                         AS "dias_completos" 
           ,floor(((dur) - floor(abs(dur))) * 24)   AS "horas_completas"
           ,floor((((dur) - floor(abs(dur))) * 24 - floor(((dur) - floor(abs(dur))) * 24)) * 60)  AS "minutos"
     
    FROM (SELECT ((SYSDATE) - (enc.ENAB_FECHA_CREACION) ) AS dur 
             FROM SIPSA_ENCA_ABASTECIMIENTO enc WHERE 
              enc.ENAB_FECHA_CREACION = (SELECT MAX(enc2.ENAB_FECHA_CREACION) FROM SIPSA_ENCA_ABASTECIMIENTO enc2 WHERE enc2.ENAB_PLACA = p_enab_placa) AND ROWNUM = 1);  

EXCEPTION
    WHEN OTHERS THEN
      p_codigo_error := 1;
      p_mensaje      := 'AL CONSULTAR LA TABLA SIPSA_ENCA_ABASTECIMIENTO SE PRESENTO '||SQLERRM;
 
 END pr_ConsultarUltimoEncReg;

  PROCEDURE pr_ConsultarEncaMayorXRonda
   /********************************************************************************
 DESCRIPCION   : Metodo que permite consultar un encabezado para una ronda especifica

 REALIZADO POR : AMM - Adonay Mantilla

 FECHA CREACION: 13/12/2016
 *******************************************************************************/
( p_futi_id                     IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_uspe_id                      IN SIPSA_ENCA_MAYORISTA.uspe_id%TYPE,
 p_prre_fecha_programada        IN SIPSA_ENCA_MAYORISTA.prre_fecha_programada%TYPE,
 p_enma_ronda                   IN SIPSA_ENCA_MAYORISTA.ENMA_RONDA%TYPE,
 p_resultado                    OUT Pq_Gen_General.ty_cursor,
 p_codigo_error                 OUT NUMBER,
 p_mensaje                      OUT VARCHAR2) AS
  BEGIN
    p_codigo_error := 0;
   p_mensaje      := NULL;

   OPEN p_resultado FOR
   SELECT enma.futi_id, PQ_SIPSA_FUENTES.fn_ObtenerNombre(enma.futi_id) Fuente,
                      usua.usua_id, PQ_SIPSA_USUARIOS.fn_ObtenerNombre(usua.usua_id) Usuario,
                      enma.enma_ronda,                      
                      enma.enma_hora_inicial,
                      enma.enma_hora_final,
                      enma.enma_fecha_creacion,
                      enma.enma_usuario_creacion,
                      enma.enma_fecha_modificacion,
                      enma.enma_usuario_modificacion
        FROM SIPSA_ENCA_MAYORISTA enma,SIPSA_USUARIOS usua,SIPSA_USUARIOS_PERFILES  uspe               
        WHERE   enma.futi_id = p_futi_id
        AND     enma.uspe_id= uspe.uspe_id
        AND     enma.uspe_id= p_uspe_id
        AND     uspe.usua_id=usua.usua_id
        AND     enma.prre_fecha_programada = p_prre_fecha_programada
        AND     enma.ENMA_RONDA = nvl(p_enma_ronda, enma.ENMA_RONDA)
        ;

EXCEPTION
    WHEN OTHERS THEN
      p_codigo_error := 1;
      p_mensaje      := 'AL CONSULTAR LA TABLA SIPSA_FUENTES SE PRESENTO '||SQLERRM;
  END pr_ConsultarEncaMayorXRonda;

  PROCEDURE pr_ConsultarEncaMayorExiste
    /********************************************************************************
 DESCRIPCION   : Metodo que permite consultar un encabezado existe

 REALIZADO POR : AMM - Adonay Mantilla

 FECHA CREACION: 20/09/2017
 *******************************************************************************/
( p_futi_id                     IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_prre_fecha_programada        IN SIPSA_ENCA_MAYORISTA.prre_fecha_programada%TYPE,
 p_enma_ronda                   IN SIPSA_ENCA_MAYORISTA.ENMA_RONDA%TYPE,
 p_resultado                    OUT Pq_Gen_General.ty_cursor,
 p_codigo_error                 OUT NUMBER,
 p_mensaje                      OUT VARCHAR2) AS
  BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   OPEN p_resultado FOR
   SELECT enma.futi_id, PQ_SIPSA_FUENTES.fn_ObtenerNombre(enma.futi_id) Fuente,                      
                      enma.enma_ronda,                      
                      enma.enma_hora_inicial,
                      enma.enma_hora_final,
                      enma.enma_fecha_creacion,
                      enma.enma_usuario_creacion,
                      enma.enma_fecha_modificacion,
                      enma.enma_usuario_modificacion,
                      enma.USPE_ID
        FROM SIPSA_ENCA_MAYORISTA enma   
        WHERE   enma.futi_id = p_futi_id
        AND     enma.prre_fecha_programada = p_prre_fecha_programada
        AND     enma.ENMA_RONDA = nvl(p_enma_ronda, enma.ENMA_RONDA)
        ;

EXCEPTION
    WHEN OTHERS THEN
      p_codigo_error := 1;
      p_mensaje      := 'AL CONSULTAR LA TABLA SIPSA_FUENTES SE PRESENTO '||SQLERRM;
  END pr_ConsultarEncaMayorExiste;

PROCEDURE pr_ConsultarPorFuenBolMayor
/********************************************************************************
 DESCRIPCION   : Metodo para consultar el boletin para el proceso de mayoristas,
                donde se proyectan los articulos, grupo, subgrupo, presentacion,
                unidad y valores maximos y minimos de cada Ronda.

 PARAMETROS    :
 IN            : p_futi_id                   Id Fuente
                 p_prre_fecha_programacion   Fecha de la que se desea consultar el boletin

 OUT           : p_resultado    Cursor con la informacion de la tabla
                 p_codigo_error Valor que indica la ocurrencia de error en el proceso
                 p_mensaje      Mensaje del error ocurrido

 REALIZADO POR :  Carlos Alberto Manrique Palacios

 FECHA CREACION: 12/02/2018

 MODIFICADO POR: 
 FECHA MODIFICA: 
 CAMBIOS       : 
*******************************************************************************/
(p_futi_id         IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_prre_fecha_programacion IN sipsa_programacion_recoleccion.prre_fecha_programada%TYPE,
 p_resultado      OUT Pq_Gen_General.ty_cursor,
 p_codigo_error   OUT NUMBER,
 p_mensaje        OUT VARCHAR2)IS
 
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
     
   OPEN p_resultado FOR
WITH UNO AS
			(SELECT enma.futi_id,
                      enma.prre_fecha_programada,    
                      reco.ARTI_ID,
                      reco.UNME_ID,
                      NVL(LEAST(COALESCE(reco.rema_toma1,reco.rema_toma1,reco.rema_toma3,reco.rema_toma4),
                                COALESCE(reco.rema_toma2,reco.rema_toma1,reco.rema_toma3,reco.rema_toma4),
                                COALESCE(reco.rema_toma3,reco.rema_toma2,reco.rema_toma1,reco.rema_toma4),
                                COALESCE(reco.rema_toma1,reco.rema_toma2,reco.rema_toma3,reco.rema_toma1)),0) min_r1,
                      GREATEST(NVL(reco.rema_toma1,0) ,NVL(reco.rema_toma2,0) ,NVL(reco.rema_toma3,0) ,NVL(reco.rema_toma4,0)) max_r1
                      FROM SIPSA_ENCA_MAYORISTA enma
                      INNER JOIN sipsa_recoleccion_mayoristas reco  
                        ON reco.futi_id = enma.futi_id 
                        AND reco.prre_fecha_programada = enma.prre_fecha_programada 
                        AND reco.enma_ronda=enma.enma_ronda
                    where  enma.futi_id = p_futi_id
                    AND     enma.prre_fecha_programada = p_prre_fecha_programacion
                    AND     enma.ENMA_RONDA=1
                    AND (reco.rema_toma1 > 0 or reco.rema_toma2 > 0 or reco.rema_toma3 > 0 or reco.rema_toma4 > 0)),
    DOS AS
			(SELECT enma.futi_id,
                      enma.prre_fecha_programada,    
                      reco.ARTI_ID,
                      reco.UNME_ID,
                      NVL(LEAST(COALESCE(reco.rema_toma1,reco.rema_toma1,reco.rema_toma3,reco.rema_toma4),
                                COALESCE(reco.rema_toma2,reco.rema_toma1,reco.rema_toma3,reco.rema_toma4),
                                COALESCE(reco.rema_toma3,reco.rema_toma2,reco.rema_toma1,reco.rema_toma4),
                                COALESCE(reco.rema_toma1,reco.rema_toma2,reco.rema_toma3,reco.rema_toma1)),0) min_r2,
                      GREATEST(NVL(reco.rema_toma1,0) ,NVL(reco.rema_toma2,0) ,NVL(reco.rema_toma3,0) ,NVL(reco.rema_toma4,0)) max_r2
                      FROM SIPSA_ENCA_MAYORISTA enma
                      INNER JOIN sipsa_recoleccion_mayoristas reco  
                        ON reco.futi_id = enma.futi_id 
                        AND reco.prre_fecha_programada = enma.prre_fecha_programada 
                        AND reco.enma_ronda=enma.enma_ronda
                    where  enma.futi_id = p_futi_id
                    AND     enma.prre_fecha_programada = p_prre_fecha_programacion
                    AND     enma.ENMA_RONDA=2
                    AND (reco.rema_toma1 > 0 or reco.rema_toma2 > 0 or reco.rema_toma3 > 0 or reco.rema_toma4 > 0)),
    COMB AS  
			(SELECT   
                case when UNO.futi_id is null then DOS.futi_id else UNO.futi_id end  futi_id,
                case when UNO.prre_fecha_programada is null then DOS.prre_fecha_programada else UNO.prre_fecha_programada  end prre_fecha_programada,
                case when UNO.ARTI_ID is null then DOS.ARTI_ID else UNO.ARTI_ID end ARTI_ID,
                case when UNO.UNME_ID  is null then DOS.UNME_ID else UNO.UNME_ID end UNME_ID,
                NVL(MIN_R1,0)MIN_R1, NVL(MAX_R1,0) MAX_R1,NVL(MIN_R2,0)MIN_R2,NVL(MAX_R2,0)MAX_R2
			FROM UNO FULL JOIN DOS 
				ON (UNO.futi_id=DOS.futi_id 
				and uno.PRRE_FECHA_PROGRAMADA=dos.PRRE_FECHA_PROGRAMADA
				and uno.ARTI_ID=dos.ARTI_ID
				and uno.UNME_ID=dos.UNME_ID)
			)   
    SELECT 
        arti.grup_id, 
        INITCAP(grup.grup_nombre) grup_nombre,
        sgrup.subg_id,
        INITCAP(sgrup.subg_nombre)subg_nombre,
        COMB.arti_id,
        arti.arti_nombre producto, 
        INITCAP(tipr.tipr_nombre) presentacion,
        INITCAP(unme.unme_cantidad_ppal || ' ' || unme.unme_nombre_ppal || (case when nvl(unme.unme_cantidad_2,0)>0 then  INITCAP(' '||unme.unme_cantidad_2 || ' ' || unme.unme_nombre_2) else '' end    )) unidades,
        COMB.unme_id,
        COMB.futi_id,
        min_r1,
        max_r1,
        min_r2,
        max_r2,
        LAG(arti.grup_id, 1, 0) OVER ( ORDER BY grup.grup_nombre asc,sgrup.SUBG_ID asc, arti.arti_nombre) AS grup_prev,
        LAG(sgrup.SUBG_ID, 1, 0) OVER ( ORDER BY grup.grup_nombre asc,sgrup.SUBG_ID asc, arti.arti_nombre ) AS sgrup_prev
      FROM 
        sipsa_articulos arti inner join comb  ON(arti.arti_id=COMB.arti_id)
        INNER JOIN sipsa_grupos grup ON arti.grup_id = grup.grup_id
        INNER JOIN sipsa_subgrupos sgrup ON (arti.subg_id = sgrup.subg_id AND arti.grup_id = sgrup.grup_id)
        INNER JOIN sipsa_unidades_medida unme ON COMB.unme_id = unme.unme_id 
        INNER JOIN sipsa_tipo_presentaciones tipr ON tipr.tipr_id = unme.tipr_id 
      ORDER BY grup.grup_nombre asc,sgrup.SUBG_ID asc, arti.arti_nombre;
        


EXCEPTION
    WHEN OTHERS THEN
      p_codigo_error := 1;
      p_mensaje      := 'AL CONSULTAR LA TABLA SIPSA_ENCA_MAYORISTA SE PRESENTO '||SQLERRM;

END pr_ConsultarPorFuenBolMayor;

PROCEDURE pr_ConsultarHorasM
/********************************************************************************
 DESCRIPCION   : Metodo para consultar en un solo registro las fechas de los horas
                de las rondas para la fuente y fecha ingresadas por parametro

 PARAMETROS    :
 IN            : p_futi_id                   Id Fuente
                 p_prre_fecha_programacion   Fecha de la que se desea consultar

 OUT           : p_resultado    Cursor con la informacion de la tabla
                 p_codigo_error Valor que indica la ocurrencia de error en el proceso
                 p_mensaje      Mensaje del error ocurrido

 REALIZADO POR :  Carlos Alberto Manrique Palacios

 FECHA CREACION: 14/02/2018

 MODIFICADO POR: 
 FECHA MODIFICA: 
 CAMBIOS       : 
*******************************************************************************/
(p_futi_id         IN SIPSA_ENCA_MAYORISTA.futi_id%TYPE,
 p_prre_fecha_programacion IN sipsa_programacion_recoleccion.prre_fecha_programada%TYPE,
 p_resultado      OUT Pq_Gen_General.ty_cursor,
 p_codigo_error   OUT NUMBER,
 p_mensaje        OUT VARCHAR2)IS
 
  BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
     
   OPEN p_resultado FOR
   
   WITH UNO AS(    
    SELECT enma.futi_id,
                      enma.prre_fecha_programada,    
                       enma.ENMA_RONDA,
                      enma.enma_hora_inicial,
                      enma.enma_hora_final
                      FROM SIPSA_ENCA_MAYORISTA enma
        where  enma.futi_id = p_futi_id
        AND     enma.prre_fecha_programada = p_prre_fecha_programacion
        AND     enma.ENMA_RONDA=1),
        DOS AS
        (SELECT enma.futi_id,
                      enma.prre_fecha_programada,    
                       enma.ENMA_RONDA,
                      enma.enma_hora_inicial,
                      enma.enma_hora_final
                      FROM SIPSA_ENCA_MAYORISTA enma
    where  enma.futi_id = p_futi_id
        AND     enma.prre_fecha_programada = p_prre_fecha_programacion
        AND     enma.ENMA_RONDA=2)
     SELECT   UNO.enma_hora_inicial HORA_INICIAL_RONDA1,
              UNO.enma_hora_final HORA_FINAL_RONDA1,
              DOS.enma_hora_inicial HORA_INICIAL_RONDA2,
              DOS.enma_hora_final HORA_FINAL_RONDA2
    FROM 
    UNO FULL JOIN DOS ON (UNO.futi_id=DOS.futi_id AND
                     	UNO.prre_fecha_programada=DOS.prre_fecha_programada  );
 
 EXCEPTION
    WHEN OTHERS THEN
      p_codigo_error := 1;
      p_mensaje      := 'AL CONSULTAR LA TABLA SIPSA_ENCA_MAYORISTA SE PRESENTO '||SQLERRM;
 END pr_ConsultarHorasM;
 
 
PROCEDURE pr_InsertarI2_D
/********************************************************************************
 DESCRIPCION   : Metodo para incorporar informacion sobre la tabla SIPSA_ENCA_INSUMOS_D

 Realizado por : Marco Guzman
 Fecha Creacion: 19/06/2019
*******************************************************************************/
(p_futi_id                  IN SIPSA_ENCA_INSUMOS_D.FUTI_ID%TYPE,
 p_uspe_id                  IN SIPSA_ENCA_INSUMOS_D.USPE_ID%TYPE,
 p_grin2_id                 IN SIPSA_ENCA_INSUMOS_D.GRIN_ID%TYPE,
 p_prre_fecha_programada    IN SIPSA_ENCA_INSUMOS_D.ENIN_FECHA_PROGRAMADA%TYPE,
 p_precio_prom_anterior     IN SIPSA_ENCA_INSUMOS_D.ENIN_PRECIO_PROM_ANTERIOR%TYPE,
 p_novedad                  IN SIPSA_ENCA_INSUMOS_D.ENIN_NOVEDAD%TYPE,    
 p_obse_id                  IN  SIPSA_ENCA_INSUMOS_D.OBSE_ID%TYPE,
 p_observacion              IN SIPSA_ENCA_INSUMOS_D.ENIN_OBSERVACION%TYPE,      
 p_usuario                 IN SIPSA_ENCA_INSUMOS_D.ENIN_USUARIO_CREACION%TYPE,  
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
 p_codigo_error             OUT NUMBER,
 p_mensaje                  OUT VARCHAR2) IS
contador NUMBER;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador := NULL;
   --p_id    := Pq_Gen_Tablas_Secuencias.fn_ObtenerLlave(p_sistema,'SIPSA_ENCA_INSUMOS_D');
   
   SELECT COUNT(1)  INTO contador
   FROM SIPSA_ENCA_INSUMOS_D
   WHERE FUTI_ID = p_futi_id
   AND USPE_ID = p_uspe_id
   --AND GRIN_ID = p_grin2_id
   AND ENIN_NOMBRE_FUENTE = p_nom_fuente 
   AND ENIN_MUNI_ID = p_muni_id
   AND ENIN_MUNICIPIO = p_municipio
   AND ENIN_FECHA_PROGRAMADA = p_prre_fecha_programada;
   
   IF contador = 0  THEN
        INSERT INTO SIPSA_ENCA_INSUMOS_D(FUTI_ID,USPE_ID,GRIN_ID,ENIN_FECHA_PROGRAMADA,ENIN_PRECIO_PROM_ANTERIOR,ENIN_NOVEDAD,
        OBSE_ID,ENIN_OBSERVACION,ENIN_USUARIO_CREACION,ENIN_FECHA_CREACION,ARTI_ID,ARTI_NOMBRE,
        ENIN_NOMBRE_FUENTE,ENIN_MUNICIPIO,ENIN_MUNI_ID,ENIN_DIRECCION,ENIN_TELEFONO,ENIN_EMAIL,ENIN_INFORMANTE,ENIN_TEL_INFORMANTE)
        VALUES (p_futi_id, p_uspe_id, p_grin2_id, p_prre_fecha_programada,p_precio_prom_anterior, p_novedad, p_obse_id, p_observacion,UPPER(p_usuario),sysdate,p_arti_id,p_arti_nombre,
        p_nom_fuente,p_municipio,p_muni_id,p_direccion,p_telefono,p_email,p_informante,p_tel_informante);
   ELSE
        PQ_SIPSA_ENCABEZADOS.pr_ActualizarI2_D(p_futi_id, p_uspe_id, p_grin2_id, p_prre_fecha_programada,p_precio_prom_anterior, p_novedad, 
                                                                 p_obse_id, p_observacion,UPPER(p_usuario),p_arti_id,p_arti_nombre,
                                                                 p_nom_fuente,p_municipio,p_muni_id,p_direccion,p_telefono,p_email,p_informante,p_tel_informante,p_codigo_error,p_mensaje);
   END IF;                                             
EXCEPTION
       WHEN OTHERS THEN
            --IF SQLCODE = -1 THEN
            -- p_codigo_error := 1;
             --p_mensaje      := 'YA EXISTE LA FUENTE: '||p_futi_id ||' Y EL USUARIO:' ||p_uspe_id ||' EN LA FECHA:' ||p_prre_fecha_programada ;
            --ELSE
             p_codigo_error := 1;
             p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_ENCA_INSUMOS_D SE PRESENTO '||SQLERRM;
            --END IF;
END pr_InsertarI2_D;


PROCEDURE pr_ActualizarI2_D
/********************************************************************************
 DESCRIPCION   : Metodo para borrar informacion de la tabla SIPSA_ENCA_INSUMOS_D

 Realizado por : Marco Guzman
 Fecha Creacion: 19/06/2019
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS
 contador NUMBER;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
    contador := NULL;

 SELECT COUNT(1) INTO contador FROM SIPSA_ENCA_INSUMOS_D enin2
 WHERE enin2.FUTI_ID = p_futi_id
    AND     enin2.USPE_ID= p_uspe_id
    AND     enin2.GRIN_ID= p_grin2_id
    AND     enin2.ENIN_NOMBRE_FUENTE = p_nom_fuente
    AND     enin2.ENIN_MUNI_ID = p_muni_id
    AND     enin2.ENIN_MUNICIPIO = p_municipio
    AND     enin2.ENIN_FECHA_PROGRAMADA= p_prre_fecha_programada;

     IF contador = 1  THEN
       UPDATE SIPSA_ENCA_INSUMOS_D enin2
       SET  enin2.ENIN_NOVEDAD=p_novedad,
               enin2.OBSE_ID=p_obse_id,
               enin2.ENIN_OBSERVACION=p_observacion,
               enin2.ENIN_USUARIO_MODIFICACION=UPPER(p_usuario),
               ENIN_FECHA_MODIFICACION = sysdate
        WHERE enin2.FUTI_ID = p_futi_id
        AND     enin2.USPE_ID= p_uspe_id
        AND     enin2.GRIN_ID= p_grin2_id
        AND     enin2.ENIN_NOMBRE_FUENTE = p_nom_fuente
        AND     enin2.ENIN_MUNI_ID = p_muni_id
        AND     enin2.ENIN_MUNICIPIO = p_municipio
        AND     enin2.ENIN_FECHA_PROGRAMADA= p_prre_fecha_programada;
      
         IF SQL%NOTFOUND THEN
            p_codigo_error := 1;
            p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ACTUALIZAR EN LA TABLA SIPSA_ENCA_INSUMOS_D CON EL IDENTIFICADOR DADO ';
         END IF;
      
      END IF; 



EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL ACTUALIZAR DE LA TABLA SIPSA_ENCA_INSUMOS_D SE PRESENTO '||SQLERRM;
END pr_ActualizarI2_D;


END PQ_SIPSA_ENCABEZADOS;