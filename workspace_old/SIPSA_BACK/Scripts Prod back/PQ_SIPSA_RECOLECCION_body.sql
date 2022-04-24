create or replace PACKAGE BODY            PQ_SIPSA_RECOLECCION IS

PROCEDURE pr_autenticar
/********************************************************************************
     Descripcion   : método para consultar un usuario y su programacion de la recoleccion

 parametros    :
 in            : p_usua_usuario        usuario del sistema



 out           : p_usua_id     id del usuario dentro del sistema
                 p_nombre         nombre del ususario
                 p_clave         password del usuario
                 p_fechas    cursor con la información de la programación
                 p_codigo_error valor que indioca la ocurrencia de error en el proceso
                 p_mensaje      mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/08/2012

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
(p_usua_usuario     IN SIPSA_USUARIOS.usua_usuario%TYPE,
 p_usua_id     OUT SIPSA_USUARIOS.usua_id%TYPE,
 p_nombre         OUT VARCHAR2,
 p_clave          OUT SIPSA_USUARIOS.usua_password%TYPE,
 p_fechas         OUT Pq_Gen_General.ty_cursor,
 p_codigo_error   OUT NUMBER,
 p_mensaje        OUT VARCHAR2)IS

 v_resultado    pq_gen_general.ty_cursor;

 v_usua_id   SIPSA_USUARIOS.usua_usuario%TYPE;

 v_nombre1      VARCHAR2(4000);
 v_nombre2      VARCHAR2(4000);
 v_apellido1    VARCHAR2(4000);
 v_apellido2    VARCHAR2(4000);
 v_clave        SIPSA_USUARIOS.usua_password%TYPE;
 v_uspe_id    SIPSA_USUARIOS_PERFILES.uspe_id%TYPE;
 v_valorc                       VARCHAR2(4000);

 BEGIN

   p_codigo_error := 0;
   p_mensaje      := NULL;
 
 PQ_SIPSA_USUARIOS. pr_ObtenerDatos (p_usua_usuario   => p_usua_usuario,
                                                             p_resultado        => v_resultado,
                                                             p_codigo_error     => p_codigo_error,
                                                             p_mensaje          => p_mensaje);

   LOOP

    FETCH v_resultado
              INTO p_usua_id,
                v_valorc,
                v_valorc,
                v_valorc,
                v_nombre1,
                v_nombre2,
                v_apellido1,
                v_apellido2,
                v_valorc,
                v_valorc,
                v_valorc,
                v_valorc,
                v_valorc,
                p_clave,
                v_valorc,
                v_valorc,
                v_valorc,
                v_valorc;

             EXIT WHEN v_resultado%NOTFOUND;
         p_nombre   := v_nombre1||' '||v_nombre2||' '||v_apellido1||' '||v_apellido2;
         v_uspe_id :=  PQ_SIPSA_USUARIOS_PERFILES.fn_obtenerid(p_usua_id, 1);  -- actualizar
         p_usua_id := v_uspe_id;

    END LOOP;

        PQ_SIPSA_PROG_RECOLECCION.pr_consultarProgramacion( p_uspe_id               => v_uspe_id,
                                                                                                p_resultado            => p_fechas,
                                                                                                p_codigo_error       => p_codigo_error,
                                                                                                p_mensaje             => p_mensaje);
 EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL AUTENTICAR SE PRESENTO'||SQLERRM;

 END pr_autenticar;

PROCEDURE Pr_InsertarM
/********************************************************************************
 Descripcion   : método para incorporar información sobre la tabla SIPSA_RECOLECCION_MAYORISTAS

 parametros    :
 in            : p_futi_id
                 p_uspe_id
                 p_arti_id
                 p_unme_id
                 p_prre_fecha_programada
                 p_enma_ronda
                 p_rema_precio_prom_anterior,
                p_rema_toma1
                p_rema_toma2
                p_rema_toma3
                p_rema_toma4
                p_rema_observacion_ronda
                p_usuario

 out           : p_futi_id                valor del identificador del registro a crear
                 p_arti_id
                 p_unme_id
                 p_prre_fecha_programada
                 p_enma_ronda
                 p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

  Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/08/2012

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
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
 p_mensaje                       OUT VARCHAR2) IS

   --v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
 
contador NUMBER;
--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador         := NULL;
   
   SELECT COUNT(1) INTO contador FROM SIPSA_RECOLECCION_MAYORISTAS
   WHERE futi_id = p_futi_id
   AND uspe_id = p_uspe_id
   AND arti_id = p_arti_id
   AND unme_id = p_unme_id
   AND prre_fecha_programada = p_prre_fecha_programada
   AND enma_ronda = p_enma_ronda;
    
    IF contador = 0 THEN
        --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
        INSERT INTO    SIPSA_RECOLECCION_MAYORISTAS (futi_id,uspe_id,arti_id,unme_id,prre_fecha_programada,enma_ronda,rema_precio_prom_anterior,
                                                                                      rema_toma1,rema_toma2,rema_toma3,rema_toma4,rema_observacion_ronda,esta_id,rema_usuario_creacion)
        VALUES(p_futi_id,p_uspe_id,p_arti_id,p_unme_id,p_prre_fecha_programada,p_enma_ronda,p_rema_precio_prom_anterior,
                    p_rema_toma1,p_rema_toma2,p_rema_toma3,p_rema_toma4,p_rema_observacion_ronda,p_esta_id,UPPER(p_usuario));
    ELSE
            PQ_SIPSA_RECOLECCION.pr_ActualizarM(p_futi_id,p_uspe_id,p_arti_id,p_unme_id,p_prre_fecha_programada,p_enma_ronda,p_rema_precio_prom_anterior,
                    p_rema_toma1,p_rema_toma2,p_rema_toma3,p_rema_toma4,p_rema_observacion_ronda,p_esta_id,UPPER(p_usuario),p_codigo_error,p_mensaje);
    END IF;    
    
                       
EXCEPTION
       WHEN OTHERS THEN
              IF SQLCODE = -1 THEN
                    --p_codigo_error := 1;
                    --p_mensaje      := 'YA EXISTE LA FUENTE: '||p_futi_id ||' Y EL USUARIO:' ||p_uspe_id ||' EN LA RONDA:' ||p_enma_ronda ||' DEL :' ||p_prre_fecha_programada ;
            --ELSE
                       p_codigo_error := 1;
                       p_mensaje      := 'al insertar en la tabla SIPSA_RECOLECCION_MAYORISTAS se presento '||SQLERRM;
              END IF;                                                                                            
END Pr_InsertarM;

PROCEDURE pr_ActualizarM
/********************************************************************************
 Descripcion   : método para actualizar información sobre la tabla SIPSA_RECOLECCION_MAYORISTAS

 parametros    :
 in            : p_futi_id
                 p_uspe_id
                 p_arti_id
                 p_unme_id
                 p_prre_fecha_programada
                 p_enma_ronda
                 p_rema_precio_prom_anterior,
                p_rema_toma1
                p_rema_toma2
                p_rema_toma3
                p_rema_toma4
                p_rema_observacion_ronda
                p_usuario

 out           : p_futi_id                valor del identificador del registro a crear
                 p_arti_id
                 p_unme_id
                 p_prre_fecha_programada
                 p_enma_ronda
                 p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/08/2012

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
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
 p_mensaje                       OUT VARCHAR2) IS

   --v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
 
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
   
   UPDATE SIPSA_RECOLECCION_MAYORISTAS
       SET 
              uspe_id = p_uspe_id,              
              unme_id = p_unme_id, 
              prre_fecha_programada = p_prre_fecha_programada, 
              enma_ronda = p_enma_ronda,
              rema_precio_prom_anterior = p_rema_precio_prom_anterior,
              rema_toma1 = p_rema_toma1,
              rema_toma2 = p_rema_toma2,
              rema_toma3 = p_rema_toma3,
              rema_toma4 = p_rema_toma4,
              rema_observacion_ronda = p_rema_observacion_ronda,
              esta_id=p_esta_id,
              rema_usuario_modificacion = UPPER(p_usuario)
   WHERE   futi_id = p_futi_id
   AND        arti_id = p_arti_id;     

   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'no se encontro ningun registro para actualizar en la tabla SIPSA_RECOLECCION_MAYORISTAS con los identificadores dados ';
   END IF;

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al actualizar la tabla SIPSA_RECOLECCION_MAYORISTAS se presento '||SQLERRM;                                                                                   
END pr_ActualizarM;

PROCEDURE Pr_CargarDMCRecoleccionM
 /********************************************************************************
 Descripcion   : metodo para consultar las estructuras relacionadas con la recoleccion
                 para ser llamado varias veces en un periodo

 parametros    :
 in            : p_uspe_id  



 out           : p_principal         
                 p_Fuentes      
                 p_Persona
                 p_codigo_error valor que indioca la ocurrencia de error en el proceso
                 p_mensaje      mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/08/2012

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
( --p_id_usuario         IN NUMBER,
  p_uspe_id IN NUMBER,
  --p_periodo                   OUT Pq_Gen_General.ty_cursor,
  p_Principal               OUT PQ_GEN_GENERAL.ty_cursor,
  p_Fuentes                OUT PQ_GEN_GENERAL.ty_cursor,
  p_Persona                OUT PQ_GEN_GENERAL.ty_cursor,
  --p_Articulos               OUT PQ_GEN_GENERAL.ty_cursor,
  --p_Unidades               OUT PQ_GEN_GENERAL.ty_cursor,
  --p_TiposVehiculo        OUT PQ_GEN_GENERAL.ty_cursor,   
  --p_Municipios             OUT PQ_GEN_GENERAL.ty_cursor, 
  --p_TiposMercado        OUT PQ_GEN_GENERAL.ty_cursor,  
  --p_ciudades                  OUT Pq_Gen_General.ty_cursor,
  --p_tipo_recoleccion                  OUT Pq_Gen_General.ty_cursor,
  p_codigo_error              OUT NUMBER,
  p_mensaje                   OUT VARCHAR2) IS

v_tire_id INTEGER;
m_fecha_ini date;
BEGIN

     -- Inserta los datos necesarios para la consulta en la tabla temporal.
     SELECT MIN (prre1.prre_fecha_programada) into m_fecha_ini FROM SIPSA_PROGRAMACION_RECOLECCION prre1
                                                                                                        WHERE--prre1.futi_id = futi.futi_id
                                                                                                        --AND    
                                                                                                         prre1.prre_estado = 19
                                                                                                        AND     TO_DATE(SYSDATE, 'dd-mm-yy') <= TO_DATE(prre1.prre_fecha_programada, 'dd-mm-yy');
     
     PQ_SIPSA_TMP_PROMEDIOS_MAYORIS.PR_INSERTAR_RECO(m_fecha_ini - 5, to_date(sysdate() + 10));
      
        --SELECT DISTINCT futi.tire_id INTO v_tire_id
        --FROM SIPSA_FUENTES_TIPO_RECOLECCION futi, 
         --         SIPSA_FUENTES_USUARIO_PERFILES fuus, 
         --         SIPSA_USUARIOS_PERFILES uspe
       -- WHERE  uspe.uspe_id=fuus.uspe_id
        --AND      fuus.futi_id=futi.futi_id
        --AND      uspe.uspe_id=p_uspe_id;

--        IF v_tire_id=1 THEN --MAYORISTAS
            /**************************
                        SERVICIO PRINCIPAL
            **************************/            
               OPEN p_Principal FOR
                SELECT     futiar.futi_id,fuen.fuen_nombre,fuen_direccion,
                                futiar.arti_id,arti.arti_nombre,unme.tipr_id,tipr.tipr_nombre,futiar.unme_id,unme_cantidad_ppal,unme.unme_nombre_ppal,unme_cantidad_2,unme.unme_nombre_2,
                                grup.grup_nombre,subg.subg_nombre,
                                arti.arti_vlr_min_tomas,arti.arti_vlr_max_tomas,arti.arti_vlr_min_rondas,arti.arti_vlr_max_rondas,arti.arti_vlr_min_diasm,arti.arti_vlr_max_diasm,prre.prre_fecha_programada,
            --                    (select  prom_ronda1 from SIPSA_PROMEDIOS pm where futi.futi_id = pm.futi_id and futi.fuen_id=futiar.fuen_id and futiar.arti_id = pm.arti_id and futiar.unme_id = pm.unme_id and prre_fecha_programada =  (select max(prre_fecha_programada) from SIPSA_PROMEDIOS pm1 where pm1.futi_id = pm.futi_id and pm1.arti_id = pm.arti_id and pm1.unme_id = pm.unme_id and PROM_DIARIO>0 and prre_fecha_programada < trunc(PRRE.PRRE_FECHA_PROGRAMADA) )) prom_ant_ronda1
            --                    (select  prom_ronda2 from SIPSA_PROMEDIOS pm where futi.futi_id = pm.futi_id and futi.fuen_id=futiar.fuen_id and futiar.arti_id = pm.arti_id and futiar.unme_id = pm.unme_id and prre_fecha_programada =  (select max(prre_fecha_programada) from SIPSA_PROMEDIOS pm1 where pm1.futi_id = pm.futi_id and pm1.arti_id = pm.arti_id and pm1.unme_id = pm.unme_id and PROM_DIARIO>0 and prre_fecha_programada < trunc(PRRE.PRRE_FECHA_PROGRAMADA) )) prom_ant_ronda2,  
                               NVL((SELECT  avg(promedio) FROM SIPSA_PROMEDIO_DIARIO_V pm 
                                       WHERE futi.futi_id = pm.futi_id 
                                       AND futi.futi_id=futiar.futi_id
                                       AND futi.fuen_id=fuen.fuen_id 
                                       AND futiar.arti_id = pm.arti_id 
                                       AND futiar.unme_id = pm.unme_id 
                                       AND pm.prre_fecha_programada =  (SELECT MAX(pm1.prre_fecha_programada) 
                                                                        FROM SIPSA_PROMEDIO_DIARIO_V pm1 
                                                                        WHERE pm1.futi_id = pm.futi_id 
                                                                        AND pm1.arti_id = pm.arti_id 
                                                                        AND pm1.unme_id = pm.unme_id 
                                                                        --AND pm1.promedio>0 
                                                                        AND pm1.prre_fecha_programada < TRUNC(prre.prre_fecha_programada))),1000) prom_ant_diario
                    FROM SIPSA_FUENTES fuen, SIPSA_ARTICULOS arti,
                             SIPSA_GRUPOS grup,SIPSA_SUBGRUPOS subg,
                             SIPSA_FUENTE_ARTICULOS futiar,SIPSA_UNIDADES_MEDIDA unme,
                             SIPSA_TIPO_PRESENTACIONES tipr, SIPSA_PROGRAMACION_RECOLECCION prre,
                             SIPSA_FUENTES_USUARIO_PERFILES fuus,SIPSA_USUARIOS usua,
                             SIPSA_USUARIOS_PERFILES uspe,SIPSA_FUENTES_TIPO_RECOLECCION futi,
                             SIPSA_TIPO_RECOLECCIONES tire
                    WHERE futiar.futi_id=futi.futi_id
                    AND futi.fuen_id=fuen.fuen_id
                    AND fuen.fuen_id=futi.fuen_id
                    AND futiar.arti_id=arti.arti_id
                    AND fuus.futi_id=futi.futi_id
                    AND futi.tire_id=tire.tire_id
                    AND futi.futi_id=prre.futi_id
                    AND arti.grup_id=subg.grup_id
                    AND arti.subg_id=subg.subg_id
                    AND subg.grup_id=grup.grup_id
                    AND futiar.unme_id=unme.unme_id
                    AND unme.tipr_id=tipr.tipr_id
                    AND tire.tire_id=1
                    AND futiar.futiar_estado=1
                    AND prre.prre_estado=19--ACTIVO
                    AND uspe.uspe_id=fuus.uspe_id
                    AND usua.usua_id=uspe.usua_id
                    AND uspe.uspe_id=p_uspe_id
                    AND fuus.fuus_fecha_hasta >=SYSDATE
                    AND TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') = ( SELECT MIN (prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1
                                                                                                        WHERE--prre1.futi_id = futi.futi_id
                                                                                                        --AND    
                                                                                                         prre1.prre_estado = 19
                                                                                                        AND     TO_DATE(SYSDATE, 'dd-mm-yy') <= TO_DATE(prre1.prre_fecha_programada, 'dd-mm-yy'))         
                    ORDER BY fuen.fuen_id,arti.arti_id,tipr.tipr_id,unme.unme_id;     
--        END IF;

                                                                                                        
        /**************************
                    SERVICIO FUENTES
        **************************/          
        OPEN p_Fuentes FOR
          SELECT futi.futi_id,fuen.fuen_id,fuen.fuen_nombre,fuen.fuen_direccion,fuen.fuen_telefono,fuen.fuen_email,
                      fuen.fuen_nombre_informante,fuen.fuen_cargo_informante,fuen.fuen_telefono_informante,fuen.fuen_email_informante,
                      futi.tire_id,
                      tire.tire_nombre,
                      fuen.muni_id,muni.muni_nombre,prre.prre_fecha_programada
          FROM SIPSA_FUENTES fuen,
                   SIPSA_TIPO_RECOLECCIONES tire,SIPSA_MUNICIPIOS muni,
                   SIPSA_PROGRAMACION_RECOLECCION prre,
                   SIPSA_FUENTES_USUARIO_PERFILES fuus,SIPSA_USUARIOS usua,
                   SIPSA_USUARIOS_PERFILES uspe,SIPSA_FUENTES_TIPO_RECOLECCION futi
          WHERE fuen.fuen_id=futi.fuen_id
          AND     fuus.futi_id=futi.futi_id
          AND     fuen.fuen_id=futi.fuen_id
          AND     futi.tire_id = tire.tire_id
          AND     fuen.muni_id=muni.muni_id
          AND     futi.futi_id=prre.futi_id
          AND     uspe.uspe_id=fuus.uspe_id
          AND     usua.usua_id=uspe.usua_id
          AND     prre.prre_estado='20'
          AND     tire.tire_id=1
          AND     uspe.uspe_id=p_uspe_id
          AND fuus.fuus_fecha_hasta >=SYSDATE
          AND     TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') = ( SELECT MIN (prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1
                                                                                                        WHERE prre1.futi_id = futi.futi_id
                                                                                                        AND     prre1.prre_estado = '20'
                                                                                                        AND     TO_DATE(SYSDATE, 'dd-mm-yy') <= TO_DATE(prre1.prre_fecha_programada, 'dd-mm-yy'))         
          ORDER BY fuen.fuen_id;   

          --AND    usua.usua_id=p_id_usuario;    
    
        /**************************
                    SERVICIO PERSONAS
        **************************/        
         PQ_SIPSA_USUARIOS_PERFILES.pr_consultar(p_uspe_id  => p_uspe_id,--v_usua_id_perfil,
                                                p_resultado      => p_Persona,
                                                p_codigo_error   => p_codigo_error,
                                                p_mensaje        => p_mensaje); 
   
       --NO SE ESTA USANDO EN SIPSA-DMC YA QUE NO SE NECESITA POR QUE LOS DATOS SE ENVIAN EN EL SERVICIO CONSULTAR_USUARIO
      /*  OPEN p_Persona FOR   
      SELECT uspe.uspe_id,
                         uspe.usua_id,
                         uspe.perf_id,
                         PQ_SIPSA_PERFILES.fn_ObtenerNombre(uspe.perf_id) perfil,
                         PQ_SIPSA_USUARIOS.fn_ObtenerNombre(uspe.usua_id) nombre_persona,
                         PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(uspe.usua_id) usuario,
                        uspe.uspe_fecha_creacion,
                        uspe.uspe_usuario_creacion,
                        uspe.uspe_fecha_modificacion,
                        uspe.uspe_usuario_modificacion
               FROM SIPSA_USUARIOS_PERFILES uspe,SIPSA_USUARIOS usua,SIPSA_PERFILES perf
              WHERE     usua.usua_id = uspe.usua_id
              AND        uspe.perf_id = perf.perf_id  
              --AND        usua.usua_id = p_id_usuario
               AND     uspe.uspe_id=p_uspe_id;   */


EXCEPTION
    WHEN OTHERS THEN
    p_codigo_error := 1;
    p_mensaje:=p_mensaje;
      
/***********************************
  --  servicio fuentes

     Pq_Ind_Detalles_Fuente.pr_consultarGea( p_id             => NULL,
                                             p_ciudad         => v_ciudad,
                                             p_resultado      => p_fuentes,
                                             p_codigo_error   => p_codigo_error,
                                             p_mensaje        => p_mensaje);

***********************************/               

END Pr_CargarDMCRecoleccionM;

PROCEDURE pr_IncorporarDMCM
 /********************************************************************************
 Descripcion   : metodo para incorporar la informacion obtenida en la DMC en una
                 Recoleccion Mayoristas

 parametros    :
 in            : p_usuario        usuario del sistema

 out           : p_codigo_error valor que indica la ocurrencia de error en el proceso
                 p_mensaje      mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/08/2012

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
 (--p_sistema                   IN VARCHAR2,    
  p_usuario                   IN SIPSA_RECOLECCION_MAYORISTAS.rema_usuario_modificacion%TYPE,
  --p_fuen_id                   IN SIPSA_RECOLECCION_MAYORISTAS.fuen_id%TYPE
  p_codigo_error              OUT NUMBER,
  p_mensaje                   OUT VARCHAR2) IS

CURSOR c_recoleccion IS
SELECT tmenma.futi_id, tmenma.uspe_id,tmenma.prre_fecha_programada,tmenma.enma_ronda,tmenma.enma_hora_inicial,tmenma.enma_hora_final,
            tmrema.arti_id,tmrema.unme_id,tmrema.rema_precio_prom_anterior,tmrema.rema_toma1,tmrema.rema_toma2,tmrema.rema_toma3,
            tmrema.rema_toma4,tmrema.rema_observacion_ronda 
FROM SIPSA_TMP_ENCA_MAYORISTA tmenma, SIPSA_TMP_RECO_MAYORISTAS tmrema
WHERE tmenma.futi_id = tmrema.futi_id
AND     tmenma.uspe_id = tmrema.uspe_id
AND     tmenma.prre_fecha_programada = tmrema.prre_fecha_programada
AND     tmenma.enma_ronda = tmrema.enma_ronda 
AND 0 =  ( SELECT COUNT (1) FROM SIPSA_ENCA_MAYORISTA enma, SIPSA_RECOLECCION_MAYORISTAS rema
                 WHERE enma.futi_id = rema.futi_id
                 AND enma.uspe_id =rema.uspe_id
                 AND enma.prre_fecha_programada = rema.prre_fecha_programada
                 AND enma.enma_ronda = rema.enma_ronda
                 AND enma.futi_id = tmenma.futi_id
                 AND enma.uspe_id =tmenma.uspe_id
                 AND enma.prre_fecha_programada = tmenma.prre_fecha_programada
                 AND enma.enma_ronda = tmenma.enma_ronda
                 AND rema.arti_id = tmrema.arti_id
                 AND rema.unme_id = tmrema.unme_id
                 AND rema.rema_toma1 = tmrema.rema_toma1
                 AND rema.rema_toma2 = tmrema.rema_toma2
                 AND rema.rema_toma3 = tmrema.rema_toma3
                 AND rema.rema_toma4 = tmrema.rema_toma4)
ORDER BY 1,2,3,4,5,6; 

v_uspe_id     NUMBER;
v_futi_id     NUMBER;
v_tire_id     NUMBER;
v_idsession   NUMBER;
v_cantidad NUMBER:=0;
v_futi_id1    NUMBER;
v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;      
ve_uspe_id    EXCEPTION;
ve_futi_id    EXCEPTION;
ve_Fuente     EXCEPTION;  

BEGIN

    p_codigo_error := 0;
    p_mensaje      := NULL;

    v_uspe_id:= PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario));
    v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
    
      
    IF v_uspe_id IS NULL THEN  
         p_mensaje      := 'NO HAY UN PERFIL ASOCIADO AL USUARIO'||SQLERRM;
         RAISE ve_uspe_id;          
    END IF;

    v_futi_id:= PQ_SIPSA_FUEN_USUA_PERF.Fn_ObtenerId(v_uspe_id);
   
    IF v_futi_id IS NULL THEN   
        p_mensaje      := 'NO HAY UNA FUENTE ASOCIADA AL USUARIO'||SQLERRM;
        RAISE ve_futi_id;          
    END IF;

        --INSERTAMOS EN LA TABLA DEFINITIVA DE SIPSA_ENCA_MAYORISTA
        FOR v_mayorista IN c_recoleccion LOOP
            
            --INSERTAMOS EN LA TABLA DEFINITIVA LA RECOLECCION DE MAYORISTAS
            PQ_SIPSA_ENCABEZADOS.pr_InsertarM( p_futi_id            =>      v_mayorista.futi_id,
                                                                       p_uspe_id           =>      v_mayorista.uspe_id,
                                                                       p_enma_ronda    =>      v_mayorista.enma_ronda,
                                                                       p_prre_fecha_programada    =>      v_mayorista.prre_fecha_programada,
                                                                       p_enma_hi         =>      v_mayorista.enma_hora_inicial,
                                                                       p_enma_hf         =>      v_mayorista.enma_hora_final,
                                                                       p_usuario           =>      UPPER(v_usuario),  
                                                                       p_codigo_error   =>      p_codigo_error,
                                                                       p_mensaje        =>       p_mensaje);
            IF p_codigo_error > 0 THEN
                p_mensaje      := 'AL INSERTAR ENCABEZADO DE MAYORISTAS EN INCORPORAR DMC SE PRESENTO '||p_mensaje;
                RAISE ve_Fuente;
            END IF;

            PQ_SIPSA_RECOLECCION.pr_InsertarM (  p_futi_id                                   =>      v_mayorista.futi_id,
                                                                         p_uspe_id                                  =>      v_mayorista.uspe_id,
                                                                         p_arti_id                                    =>      v_mayorista.arti_id,
                                                                         p_unme_id                                =>      v_mayorista.unme_id,
                                                                         p_enma_ronda                           =>      v_mayorista.enma_ronda,
                                                                         p_prre_fecha_programada          =>      v_mayorista.prre_fecha_programada,
                                                                         p_rema_precio_prom_anterior    =>      v_mayorista.rema_precio_prom_anterior,
                                                                         p_rema_toma1                          =>      v_mayorista.rema_toma1, 
                                                                         p_rema_toma2                          =>      v_mayorista.rema_toma2,
                                                                         p_rema_toma3                          =>      v_mayorista.rema_toma3,
                                                                         p_rema_toma4                          =>      v_mayorista.rema_toma4,
                                                                         p_rema_observacion_ronda        =>      v_mayorista.rema_observacion_ronda,
                                                                         p_esta_id                                  => 1,
                                                                         p_usuario           =>      UPPER(v_usuario),  
                                                                         p_codigo_error   =>      p_codigo_error,
                                                                         p_mensaje        =>       p_mensaje);
                                                                                                                                
            IF p_codigo_error > 0 THEN
                p_mensaje      := 'AL INSERTAR RECOLECCION MAYORISTAS EN INCORPORAR DMC SE PRESENTO '||p_mensaje;
                RAISE ve_Fuente;
            END IF;
        END LOOP;
        
        FOR i IN (  SELECT datos.futi_id,datos.prre_fecha_programada,datos.registros
                                FROM ( SELECT futiar.futi_id, prre.prre_fecha_programada,COUNT(1) registros
                                                FROM SIPSA_FUENTE_ARTICULOS futiar,--SIPSA_FUENTES_USUARIO_PERFILES fuus, SIPSA_USUARIOS_PERFILES uspe,
                                                          SIPSA_PROGRAMACION_RECOLECCION prre
                                                WHERE futiar.futiar_estado=1
                                                AND futiar.futi_id = prre.futi_id
                                                --AND uspe.uspe_id = fuus.uspe_id
                                                --AND uspe.perf_id = 8
                                                AND prre.prre_estado = 19
                                                AND TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') <= ( SELECT MAX(prre1.prre_fecha_programada)
                                                                                                                                            FROM SIPSA_PROGRAMACION_RECOLECCION prre1
                                                                                                                                            WHERE prre1.futi_id = futiar.futi_id
                                                                                                                                            AND     prre1.prre_estado = 19        )
                                                GROUP BY futiar.futi_id, prre.prre_fecha_programada --ORDER BY 1,2
                                            ) datos
                            WHERE registros  >  ( SELECT COUNT(1) /2 FROM SIPSA_RECOLECCION_MAYORISTAS rema
                                                             WHERE rema.futi_id = datos.futi_id AND rema.prre_fecha_programada = datos.prre_fecha_programada )
                            ORDER BY 1,2  
                     ) LOOP
      
            BEGIN
                SELECT -- rein.futi_id, prre.prre_fecha_programada, COUNT(1) INTO v_futi_id1, v_fecha , v_cantidad
                 COUNT(1) INTO v_cantidad
                FROM SIPSA_ENCA_MAYORISTA enma, SIPSA_PROGRAMACION_RECOLECCION prre
                WHERE enma.futi_id = i.futi_id
                   AND prre.prre_fecha_programada = i.prre_fecha_programada
                GROUP BY enma.futi_id, prre.prre_fecha_programada;
            EXCEPTION WHEN OTHERS THEN NULL;
            END;

        
            IF i.registros <> v_cantidad THEN
                /*UPDATE SIPSA_PROGRAMACION_RECOLECCION prre
                SET prre.prre_estado = 19
                WHERE  prre.futi_id = i.futi_id
                AND prre.prre_fecha_programada = i.prre_fecha_programada; */
                PQ_SIPSA_PROG_RECOLECCION.pr_Actualizar (    p_futi_id                   =>      i.futi_id,                                                                         
                                                                                         p_prre_fecha            =>      i.prre_fecha_programada,
                                                                                         p_prre_estado          =>      19,
                                                                                         p_usuario                 =>      v_usuario,  
                                                                                         p_codigo_error         =>      p_codigo_error,
                                                                                         p_mensaje               =>       p_mensaje);
            ELSE
                /*UPDATE SIPSA_PROGRAMACION_RECOLECCION prre
                SET prre.prre_estado = 20
                WHERE  prre.futi_id = i.futi_id
                AND prre.prre_fecha_programada = i.prre_fecha_programada;*/
                
                PQ_SIPSA_PROG_RECOLECCION.pr_Actualizar (    p_futi_id                   =>      i.futi_id,                                                                         
                                                                                         p_prre_fecha            =>      i.prre_fecha_programada,
                                                                                         p_prre_estado          =>      20,
                                                                                         p_usuario                 =>      v_usuario,  
                                                                                         p_codigo_error         =>      p_codigo_error,
                                                                                         p_mensaje               =>       p_mensaje);
            END IF;

        END LOOP;

        --*********************
        SELECT SYS_CONTEXT('USERENV','SID') INTO v_idsession FROM DUAL;      ---PREGUNTAR SI VA ACA         
        --*********************        


     DELETE FROM SIPSA_TMP_ENCA_MAYORISTA;
     DELETE FROM SIPSA_TMP_RECO_MAYORISTAS;

     EXCEPTION
          WHEN OTHERS THEN
               ROLLBACK;
                DELETE FROM SIPSA_TMP_ENCA_MAYORISTA;
                DELETE FROM SIPSA_TMP_RECO_MAYORISTAS;
               p_codigo_error := 1;
               p_mensaje := p_mensaje||SQLERRM;

           
END pr_IncorporarDMCM;

PROCEDURE Pr_InsertarA
/********************************************************************************
 Descripcion   : método para incorporar información sobre la tabla SIPSA_RECOLECCION_ABASTE

 parametros    :
 in            : p_futi_id
                 p_uspe_id
                 p_enab_placa
                 p_prre_fecha_programada
                 p_enab_hora
                 p_arti_id
                 p_reab_cantidad
                 p_tipr_id
                 p_unme_id
                 p_reab_cantidad_pr
                 p_usuario

 out     :     p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                p_mensaje           mensaje del error ocurrido

  Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/08/2012

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
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
 p_mensaje                       OUT VARCHAR2) IS
 
--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;  
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   
   --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
   
   INSERT INTO    SIPSA_RECOLECCION_ABASTE (futi_id,uspe_id,prre_fecha_programada,enab_placa,enab_hora,arti_id,reab_cantidad,tipr_id,unme_id,reab_cantidad_pr,esta_id,reab_usuario_creacion)
   VALUES(p_futi_id,p_uspe_id,p_prre_fecha_programada,p_enab_placa,p_enab_hora,p_arti_id,p_reab_cantidad,p_tipr_id,p_unme_id,p_reab_cantidad_pr,p_esta_id,UPPER(p_usuario));     
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_RECOLECCION_ABASTE se presento '||SQLERRM;                                                                                   
END Pr_InsertarA;

PROCEDURE Pr_ActualizarA
/********************************************************************************
 Descripcion   : método para incorporar información sobre la tabla SIPSA_RECOLECCION_ABASTE

 parametros    :
 in            : p_futi_id
                 p_uspe_id
                 p_enab_placa
                 p_prre_fecha_programada
                 p_enab_hora
                 p_arti_id
                 p_reab_cantidad
                 p_tipr_id
                 p_unme_id
                 p_reab_cantidad_pr
                 p_usuario

 out     :     p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/08/2012

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
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
 p_mensaje                       OUT VARCHAR2) IS

--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;  
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   
   --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
   UPDATE SIPSA_RECOLECCION_ABASTE 
      SET uspe_id = p_uspe_id,
             arti_id = p_arti_id,
             reab_cantidad = p_reab_cantidad,
             tipr_id = p_tipr_id,
             unme_id = p_unme_id,
             reab_cantidad_pr = p_reab_cantidad_pr,
             esta_id=p_esta_id,
             reab_usuario_modificacion = UPPER(p_usuario)
   WHERE  futi_id = p_futi_id
   AND      enab_placa = p_enab_placa
   AND      enab_hora = p_enab_hora
   AND      prre_fecha_programada = p_prre_fecha_programada;        

   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'no se encontro ningun registro para actualizar en la tabla SIPSA_RECOLECCION_ABASTE con los identificadores dados ';
   END IF;
     
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al actualizar en la tabla SIPSA_RECOLECCION_ABASTE se presento '||SQLERRM;                                                                                   
END Pr_ActualizarA;

PROCEDURE Pr_CargarDMCRecoleccionA
 /********************************************************************************
 Descripcion   : metodo para consultar las estructuras relacionadas con la recoleccion
                 para ser llamado varias veces en un periodo

 parametros    :
 in            : p_uspe_id  



 out           :  p_Fuentes
                   p_Persona
                   p_Articulos
                   p_Unidades
                   p_TiposVehiculo
                   p_Municipios
                   p_TiposMercado
                   p_codigo_error valor que indioca la ocurrencia de error en el proceso
                   p_mensaje      mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/08/2012

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
( --p_id_usuario         IN NUMBER,
  p_uspe_id IN NUMBER,
  --p_periodo                   OUT Pq_Gen_General.ty_cursor,
  p_Fuentes                OUT PQ_GEN_GENERAL.ty_cursor,
  p_Persona                OUT PQ_GEN_GENERAL.ty_cursor,
  p_Articulos               OUT PQ_GEN_GENERAL.ty_cursor,
  p_Unidades               OUT PQ_GEN_GENERAL.ty_cursor,
  p_TiposVehiculo        OUT PQ_GEN_GENERAL.ty_cursor,   
  p_Municipios             OUT PQ_GEN_GENERAL.ty_cursor, 
  p_TiposMercado        OUT PQ_GEN_GENERAL.ty_cursor,  
  --p_ciudades                  OUT Pq_Gen_General.ty_cursor,
  --p_tipo_recoleccion                  OUT Pq_Gen_General.ty_cursor,
  p_codigo_error              OUT NUMBER,
  p_mensaje                   OUT VARCHAR2) IS

v_tire_id INTEGER;

BEGIN
    
        /**************************
                    SERVICIO FUENTES
        **************************/          
        OPEN p_Fuentes FOR
          SELECT fuen.muni_id,muni.muni_nombre,fuen.fuen_id,futi.futi_id,fuen.fuen_nombre,fuen.fuen_direccion,fuen.fuen_telefono,fuen.fuen_email,
                    fuen.fuen_nombre_informante,fuen.fuen_cargo_informante,fuen.fuen_telefono_informante,fuen.fuen_email_informante,
                    futi.tire_id,
                    tire.tire_nombre,
                    prre.prre_fecha_programada
          FROM SIPSA_FUENTES fuen,
                   SIPSA_TIPO_RECOLECCIONES tire,SIPSA_MUNICIPIOS muni,
                   SIPSA_PROGRAMACION_RECOLECCION prre,
                   SIPSA_FUENTES_USUARIO_PERFILES fuus,SIPSA_USUARIOS usua,
                   SIPSA_USUARIOS_PERFILES uspe,SIPSA_FUENTES_TIPO_RECOLECCION futi
          WHERE fuen.fuen_id=futi.fuen_id
          AND     fuus.futi_id=futi.futi_id
          AND     fuen.fuen_id=futi.fuen_id
          AND     futi.tire_id = tire.tire_id
          AND     fuen.muni_id=muni.muni_id
          AND     futi.futi_id=prre.futi_id
          AND     uspe.uspe_id=fuus.uspe_id
          AND     usua.usua_id=uspe.usua_id
          AND     prre.prre_estado='20'
          AND     futi.tire_id in (2,3)
          AND     uspe.uspe_id=p_uspe_id
          AND     fuus.fuus_fecha_hasta >=SYSDATE
          AND     TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') = ( SELECT MIN (prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1
                                                                                                        WHERE prre1.futi_id = futi.futi_id
                                                                                                        AND     prre1.prre_estado = '20'
                                                                                                        AND     TO_DATE(SYSDATE, 'dd-mm-yy') <= TO_DATE(prre1.prre_fecha_programada, 'dd-mm-yy'))         
          ORDER BY fuen.fuen_id;   
          --AND    usua.usua_id=p_id_usuario;    
    
        /**************************
                    SERVICIO PERSONAS
        **************************/        
         PQ_SIPSA_USUARIOS_PERFILES.pr_consultar(p_uspe_id  => p_uspe_id,--v_usua_id_perfil,
                                                p_resultado      => p_Persona,
                                                p_codigo_error   => p_codigo_error,
                                                p_mensaje        => p_mensaje); 
   
       --NO SE ESTA USANDO EN SIPSA-DMC YA QUE NO SE NECESITA POR QUE LOS DATOS SE ENVIAN EN EL SERVICIO CONSULTAR_USUARIO
      /*  OPEN p_Persona FOR   
      SELECT uspe.uspe_id,
                         uspe.usua_id,
                         uspe.perf_id,
                         PQ_SIPSA_PERFILES.fn_ObtenerNombre(uspe.perf_id) perfil,
                         PQ_SIPSA_USUARIOS.fn_ObtenerNombre(uspe.usua_id) nombre_persona,
                         PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(uspe.usua_id) usuario,
                        uspe.uspe_fecha_creacion,
                        uspe.uspe_usuario_creacion,
                        uspe.uspe_fecha_modificacion,
                        uspe.uspe_usuario_modificacion
               FROM SIPSA_USUARIOS_PERFILES uspe,SIPSA_USUARIOS usua,SIPSA_PERFILES perf
              WHERE     usua.usua_id = uspe.usua_id
              AND        uspe.perf_id = perf.perf_id  
              --AND        usua.usua_id = p_id_usuario
               AND     uspe.uspe_id=p_uspe_id;   */

              /**************************
                    SERVICIO ARTICULOS
        **************************/          
        OPEN p_Articulos FOR
          SELECT arti.arti_id,arti.arti_nombre,grup.grup_nombre 
          FROM SIPSA_ARTICULOS arti, SIPSA_GRUPOS grup, SIPSA_ARTI_TIRE artitire
          WHERE arti.grup_id = grup.grup_id
          AND artitire.arti_id=arti.arti_id
          AND artitire.tire_id IN (2,3)
          ORDER BY arti.arti_id;  

        /**************************
                    SERVICIO UNIDADES
        **************************/          
        OPEN p_Unidades FOR
          SELECT DISTINCT unmetire.tire_id,tire.tire_nombre,unme.unme_id,tipr.tipr_id,tipr.tipr_nombre,unme.unme_cantidad_ppal,unme.unme_nombre_ppal,
                      unme.unme_cantidad_2,unme.unme_nombre_2  
          FROM SIPSA_UNIDADES_MEDIDA unme,SIPSA_TIPO_PRESENTACIONES tipr, SIPSA_UNME_TIRE unmetire,SIPSA_TIPO_RECOLECCIONES tire
          WHERE unme.tipr_id=tipr.tipr_id
          AND unmetire.tire_id = tire.tire_id
          AND unme.unme_id = unmetire.unme_id 
          AND unmetire.tire_id IN (2,3)
          ORDER BY  unme.unme_id;   

        /**************************
                    SERVICIO TIPOS VEHICULO
        **************************/          
        OPEN p_TiposVehiculo FOR
          SELECT tive.tive_id,tive.tive_descripcion,tive.tive_capacidad_min,tive.tive_capacidad_max
          FROM SIPSA_TIPO_VEHICULOS tive
          WHERE tive.tive_id<>34
          ORDER BY  tive.tive_id;   

        /**************************
                    SERVICIO MUNICIPIOS
        **************************/          
        OPEN p_Municipios FOR
          SELECT muni.dept_id,dept.dept_nombre,muni.muni_id,muni.muni_nombre
          FROM SIPSA_DEPARTAMENTOS dept,SIPSA_MUNICIPIOS muni
          WHERE  muni.dept_id=dept.dept_id
          ORDER BY   muni.dept_id;

        /**************************
        SERVICIO TIPOS MERCADO
        **************************/          
        OPEN p_TiposMercado FOR
          SELECT time.time_id,time.time_descripcion
          FROM SIPSA_TIPO_MERCADOS time
          ORDER BY  time.time_id;   

EXCEPTION
    WHEN OTHERS THEN
    p_codigo_error := 1;
    p_mensaje:=p_mensaje;
      
/***********************************
  --  servicio fuentes

     Pq_Ind_Detalles_Fuente.pr_consultarGea( p_id             => NULL,
                                             p_ciudad         => v_ciudad,
                                             p_resultado      => p_fuentes,
                                             p_codigo_error   => p_codigo_error,
                                             p_mensaje        => p_mensaje);

***********************************/               

END Pr_CargarDMCRecoleccionA;


PROCEDURE pr_IncorporarDMCA
 /********************************************************************************
 Descripcion   : metodo para incorporar la informacion obtenida en la DMC en una
                 Recoleccion Abastecimiento

 parametros    :
 in            : p_usuario        usuario del sistema
                 

 out           : p_codigo_error valor que indica la ocurrencia de error en el proceso
                 p_mensaje      mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/08/2012

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
(--p_sistema                   IN VARCHAR2,
 p_usuario                   IN SIPSA_RECOLECCION_ABASTE.reab_usuario_modificacion%TYPE,
 --p_fuen_id                   IN SIPSA_RECOLECCION_ABASTE.fuen_id%TYPE,
 p_codigo_error              OUT NUMBER,
 p_mensaje                   OUT VARCHAR2) IS


CURSOR c_encabezado IS
SELECT  tmenab.futi_id,tmenab.uspe_id,tmenab.prre_fecha_programada,tmenab.enab_hora,tmenab.tive_id,
             tmenab.tive_cual_vehiculo,tmenab.enab_placa,tmenab.enab_ubicacion,tmenab.enab_procedencia,tmenab.enab_destino,
             tmenab.time_id,tmenab.time_cual_mercado,tmenab.enab_basc_desc,tmenab.enab_observaciones  
FROM SIPSA_TMP_ENCA_ABASTECIMIENTO tmenab;
    
CURSOR c_recoleccion (p_futi IN NUMBER, p_uspe IN NUMBER, p_fecha IN DATE, p_placa VARCHAR2, p_hora VARCHAR2) IS
SELECT tmreab.futi_id, tmreab.uspe_id,tmreab.enab_placa,tmreab.prre_fecha_programada,
            tmreab.enab_hora,tmreab.arti_id,tmreab.reab_cantidad,tmreab.tipr_id,
            tmreab.unme_id,tmreab.reab_cantidad_pr 
FROM SIPSA_TMP_RECO_ABASTECIMIENTO tmreab
WHERE tmreab.futi_id=p_futi
    AND tmreab.uspe_id=p_uspe
    AND tmreab.prre_fecha_programada=p_fecha
    AND tmreab.enab_placa=p_placa
    AND tmreab.enab_hora=p_hora;
    
    
v_uspe_id     NUMBER;
v_futi_id     NUMBER;
v_tire_id     NUMBER;
v_idsession   NUMBER;
v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;  
ve_uspe_id    EXCEPTION;
ve_futi_id    EXCEPTION;
ve_Fuente     EXCEPTION;  

BEGIN

    p_codigo_error := 0;
    p_mensaje      := NULL;

    v_uspe_id:= PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario));
    v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
      
    IF v_uspe_id IS NULL THEN  
         p_mensaje      := 'NO HAY UN PERFIL ASOCIADO AL USUARIO'||SQLERRM;
         RAISE ve_uspe_id;          
    END IF;

    v_futi_id:= PQ_SIPSA_FUEN_USUA_PERF.Fn_ObtenerId(v_uspe_id);
   
    IF v_futi_id IS NULL THEN   
        p_mensaje      := 'NO HAY UNA FUENTE ASOCIADA AL USUARIO'||SQLERRM;
        RAISE ve_futi_id;          
    END IF;



        FOR v_enca_abastecimiento IN c_encabezado LOOP

             -- ACTUALIZAMOS ESTADO A 20 DIGITADO EN LA TABLA PROGRAMACION
             PQ_SIPSA_PROG_RECOLECCION.pr_Actualizar (    p_futi_id                   =>      v_enca_abastecimiento.futi_id,                                                                         
                                                                                         p_prre_fecha            =>      v_enca_abastecimiento.prre_fecha_programada,
                                                                                         p_prre_estado          =>      20,
                                                                                         p_usuario                 =>      v_usuario,  
                                                                                         p_codigo_error         =>      p_codigo_error,
                                                                                         p_mensaje               =>       p_mensaje);
            
            --INSERTAMOS EN LA TABLA DEFINITIVA DE SIPSA_ENCA_ABASTECIMIENTO
            PQ_SIPSA_ENCABEZADOS.pr_InsertarA( p_futi_id            =>      v_enca_abastecimiento.futi_id,
                                                                       p_uspe_id           =>      v_enca_abastecimiento.uspe_id,
                                                                       p_prre_fecha_programada    =>      v_enca_abastecimiento.prre_fecha_programada,
                                                                       p_enab_placa         =>      v_enca_abastecimiento.enab_placa,
                                                                       p_enab_hora    =>      v_enca_abastecimiento.enab_hora,
                                                                       p_tive_id         =>      v_enca_abastecimiento.tive_id,
                                                                       p_tive_cual         =>      v_enca_abastecimiento.tive_cual_vehiculo,
                                                                       p_enab_ubicacion         =>      v_enca_abastecimiento.enab_ubicacion,
                                                                       p_enab_procedencia         =>      v_enca_abastecimiento.enab_procedencia,
                                                                       p_enab_destino         =>      v_enca_abastecimiento.enab_destino,                                                                       
                                                                       p_time_id         =>      v_enca_abastecimiento.time_id,
                                                                       p_time_cual         =>      v_enca_abastecimiento.time_cual_mercado,
                                                                       p_enab_basc_desc         =>      v_enca_abastecimiento.enab_basc_desc,
                                                                       p_enab_observaciones         =>      v_enca_abastecimiento.enab_observaciones,
                                                                       p_usuario           =>      UPPER(v_usuario),  
                                                                       p_codigo_error   =>      p_codigo_error,
                                                                       p_mensaje        =>       p_mensaje);
            
            --INSERTAMOS EN LA TABLA DEFINITIVA DE SIPSA_RECOLECCION_ABASTE
            FOR v_recoleccion_abastecimientos IN c_recoleccion (v_enca_abastecimiento.futi_id, v_enca_abastecimiento.uspe_id, v_enca_abastecimiento.prre_fecha_programada, v_enca_abastecimiento.enab_placa, v_enca_abastecimiento.enab_hora) LOOP

                PQ_SIPSA_RECOLECCION.pr_InsertarA (  p_futi_id                                   =>      v_recoleccion_abastecimientos.futi_id,
                                                                             p_uspe_id                                =>      v_recoleccion_abastecimientos.uspe_id,
                                                                             p_prre_fecha_programada         =>      v_recoleccion_abastecimientos.prre_fecha_programada,
                                                                             p_enab_placa                           =>      v_recoleccion_abastecimientos.enab_placa,
                                                                             p_enab_hora                            =>      v_recoleccion_abastecimientos.enab_hora,
                                                                             p_arti_id                                   =>      v_recoleccion_abastecimientos.arti_id,
                                                                             p_reab_cantidad                       =>      v_recoleccion_abastecimientos.reab_cantidad,
                                                                             p_tipr_id                                   =>      v_recoleccion_abastecimientos.tipr_id,
                                                                             p_unme_id                                =>      v_recoleccion_abastecimientos.unme_id,
                                                                             p_reab_cantidad_pr                    =>      v_recoleccion_abastecimientos.reab_cantidad_pr,
                                                                             p_esta_id                                  =>      1,
                                                                             p_usuario           =>      UPPER(v_usuario),  
                                                                             p_codigo_error   =>      p_codigo_error,
                                                                             p_mensaje        =>       p_mensaje);
                                                                                                                                
                IF p_codigo_error > 0 THEN
                    p_mensaje      := 'AL INSERTAR RECOLECCION ABASTECIMIENTOS EN INCORPORAR DMC SE PRESENTO '||p_mensaje;
                    RAISE ve_Fuente;
                END IF;
            
            END LOOP;
            
            
            IF p_codigo_error > 0 THEN
                p_mensaje      := 'AL INSERTAR ENCABEZADO DE ABASTECIMIENTOS EN INCORPORAR DMC SE PRESENTO '||p_mensaje;
                RAISE ve_Fuente;
            END IF;

            SELECT SYS_CONTEXT('USERENV','SID') INTO v_idsession FROM DUAL;
        END LOOP;
     
     DELETE FROM SIPSA_TMP_ENCA_ABASTECIMIENTO;
     DELETE FROM SIPSA_TMP_RECO_ABASTECIMIENTO;
     EXCEPTION
          WHEN OTHERS THEN
               ROLLBACK;
                DELETE FROM SIPSA_TMP_ENCA_ABASTECIMIENTO;
                DELETE FROM SIPSA_TMP_RECO_ABASTECIMIENTO;
               p_codigo_error := 1;
               p_mensaje := p_mensaje||SQLERRM;
           
END pr_IncorporarDMCA;

PROCEDURE Pr_InsertarI
/********************************************************************************
 Descripcion   : método para incorporar información sobre la tabla SIPSA_RECOLECCION_INSUMOS

 parametros    :
 in            : p_futi_id
                 p_uspe_id
                 p_prre_fecha_programada
                 p_arti_id
                 p_caco_id
                 p_unme_id
                 p_rein_precio_anterior
                 p_rein_novedad_anterior
                p_rein_precio_recolectado
                p_rein_novedad
                p_obse_id
                p_rein_observacion
                p_usuario

 out     :      p_futi_id
                 p_prre_fecha_programada
                 p_arti_id
                 p_caco_id
                 p_unme_id
                 p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/02/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/

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
 p_mensaje                       OUT VARCHAR2) IS

contador NUMBER;
--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
       p_codigo_error := 0;
       p_mensaje      := NULL;
       contador         := NULL;
       
       SELECT COUNT(1) INTO contador FROM SIPSA_RECOLECCION_INSUMOS
       WHERE futi_id = p_futi_id
       AND uspe_id = p_uspe_id
       AND prre_fecha_programada = p_prre_fecha_programada
       AND articaco_id = p_articaco_id
       AND unme_id = p_unme_id ;
       
       IF contador = 0 THEN
            --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
            INSERT INTO    SIPSA_RECOLECCION_INSUMOS (futi_id,uspe_id,prre_fecha_programada,articaco_id,unme_id,rein_precio_anterior,rein_novedad_anterior,
                                                                           rein_precio_recolectado,rein_novedad,obse_id, rein_observacion,esta_id,rein_fecha_recoleccion,rein_usuario_creacion)
            VALUES(p_futi_id,p_uspe_id,p_prre_fecha_programada,p_articaco_id,p_unme_id,p_rein_precio_anterior,p_rein_novedad_anterior,
                p_rein_precio_recolectado,p_rein_novedad,p_obse_id,p_rein_observacion,p_esta_id,p_fecha_recoleccion,UPPER(p_usuario));
       END IF;
                  
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_RECOLECCION_INSUMOS se presento '||SQLERRM;                                                                                   
END Pr_InsertarI;

PROCEDURE Pr_ActualizarI
/********************************************************************************
 Descripcion   : método para actualizar información sobre la tabla SIPSA_RECOLECCION_INSUMOS

 parametros    :
 in            : p_futi_id
                 p_uspe_id
                 p_prre_fecha_programada
                 p_arti_id
                 p_caco_id
                 p_unme_id
                 p_rein_precio_anterior
                 p_rein_novedad_anterior
                p_rein_precio_recolectado
                p_rein_novedad
                p_obse_id
                p_rein_observacion
                p_usuario

 out     :      p_futi_id
                 p_prre_fecha_programada
                 p_arti_id
                 p_caco_id
                 p_unme_id
                 p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/02/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
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
 p_mensaje                       OUT VARCHAR2)  IS

   v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
 
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));

   UPDATE SIPSA_RECOLECCION_INSUMOS
   SET  rein_precio_recolectado = p_rein_precio_recolectado,
           rein_novedad = p_rein_novedad,
           obse_id = p_obse_id,
           rein_observacion = p_rein_observacion,
           esta_id = p_esta_id,
           rein_fecha_recoleccion = p_fecha_recoleccion,
           rein_usuario_modificacion=UPPER(p_usuario)
    WHERE futi_id = p_futi_id
    AND     uspe_id = p_uspe_id
    AND     prre_fecha_programada = p_prre_fecha_programada
    AND     articaco_id = p_articaco_id
    AND     unme_id = p_unme_id;
   
   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'no se encontro ningun registro para actualizar en la tabla SIPSA_RECOLECCION_INSUMOS con los identificadores dados ';
   END IF;

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al actualizar la tabla SIPSA_RECOLECCION_INSUMOS se presento '||SQLERRM;                                                                                   
END Pr_ActualizarI;

PROCEDURE Pr_CargarDMCRecoleccionI
 /********************************************************************************
 Descripcion   : metodo para consultar las estructuras relacionadas con la recoleccion
                 para ser llamado varias veces en un periodo

 parametros    :
 in            : p_uspe_id  



 out           :     p_TiposRecolecciones
                      p_Principal
                      p_Fuentes
                      p_FuentesArt
                      p_Persona
                      p_Articulos
                      p_Unidades
                      p_CasasComerciales
                      p_Grupos
                      p_Observaciones
                      p_codigo_error valor que indioca la ocurrencia de error en el proceso
                      p_mensaje      mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/02/2013

 Modificado por: Carlos Alberto López N.
 Fecha Modificacion: 17/02/2014
 Cambios       : Se adiciona al query la validacion de fechas de vencimientos para fuente, 
                 articulo, unidad de media y casa comercial.
*******************************************************************************/
( --p_id_usuario         IN NUMBER,
  p_uspe_id IN NUMBER,
  --p_periodo                   OUT Pq_Gen_General.ty_cursor,
  p_TiposRecolecciones OUT PQ_GEN_GENERAL.ty_cursor,
  p_Principal               OUT PQ_GEN_GENERAL.ty_cursor,
  p_Fuentes                OUT PQ_GEN_GENERAL.ty_cursor,
  p_FuentesArt             OUT PQ_GEN_GENERAL.ty_cursor,
  p_Persona                OUT PQ_GEN_GENERAL.ty_cursor,
  p_Articulos               OUT PQ_GEN_GENERAL.ty_cursor,
  p_Unidades               OUT PQ_GEN_GENERAL.ty_cursor,
  p_CasasComerciales   OUT PQ_GEN_GENERAL.ty_cursor,
  p_Grupos                   OUT PQ_GEN_GENERAL.ty_cursor,
  p_Observaciones             OUT Pq_Gen_General.ty_cursor,
  --p_Municipios             OUT PQ_GEN_GENERAL.ty_cursor, 
  p_codigo_error              OUT NUMBER,
  p_mensaje                   OUT VARCHAR2) IS

v_tire_id INTEGER;

BEGIN




         /**************************
           SERVICIO TIPOS RECOLECCIONES
        **************************/          
        OPEN p_TiposRecolecciones FOR
          SELECT tire.tire_id,tire.tire_nombre
          FROM SIPSA_TIPO_RECOLECCIONES tire
          WHERE tire.tire_id IN (4,5,6,7)
          ORDER BY tire.tire_id;  



            /**************************
                        SERVICIO PRINCIPAL
            **************************/            
               OPEN p_Principal FOR
                SELECT    futi.tire_id,
                                --tire.tire_nombre,
                                futi.futi_id,
                                fuen.muni_id,
                                fuen.fuen_id,fuen.fuen_nombre,--fuen.fuen_direccion,                     
                                arti.arti_id,arti.arti_nombre,articaco_regica_linea,
                                futiar1.articaco_id,caco.caco_id,caco.caco_nombre, --REVISAR
                               unme.tipr_id,tipr.tipr_nombre,futiar1.unme_id,unme_cantidad_ppal,unme.unme_nombre_ppal,unme_cantidad_2,unme.unme_nombre_2,
                               grup.grup_nombre,subg.subg_nombre,
                               arti.arti_vlr_min_tomas,arti.arti_vlr_max_tomas,arti.arti_vlr_min_rondas,arti.arti_vlr_max_rondas,arti.arti_vlr_min_diasm,arti.arti_vlr_max_diasm,prre.prre_fecha_programada,
                               NVL((SELECT DISTINCT pm.rein_precio_recolectado FROM SIPSA_RECOLECCION_INSUMOS pm 
                                       WHERE futi.futi_id = pm.futi_id    
                                       AND futi.futi_id=futiar1.futi_id
                                       AND futi.fuen_id=fuen.fuen_id 
                                       AND futiar1.articaco_id =pm.articaco_id
                                       AND futiar1.unme_id = pm.unme_id
                                       AND articaco.arti_id = arti.arti_id
                                       AND pm.prre_fecha_programada =  (SELECT MAX(pm1.prre_fecha_programada) FROM SIPSA_RECOLECCION_INSUMOS pm1 
                                                                                            WHERE pm1.futi_id = pm.futi_id 
                                                                                            AND pm1.articaco_id = pm.articaco_id 
                                                                                            AND pm1.unme_id = pm.unme_id 
                                                                                            --AND pm1.promedio>0 
                                                                                            AND pm1.prre_fecha_programada < TRUNC(prre.prre_fecha_programada))),1000) prom_ant_diario,                                                                                                                                 
                               (SELECT DISTINCT pm.rein_novedad FROM SIPSA_RECOLECCION_INSUMOS pm 
                                       WHERE futi.futi_id = pm.futi_id 
                                       AND futi.futi_id=futiar1.futi_id
                                       AND futi.fuen_id=fuen.fuen_id 
                                       AND futiar1.articaco_id = pm.articaco_id 
                                       AND futiar1.unme_id = pm.unme_id 
                                       AND pm.prre_fecha_programada =  (SELECT MAX(pm1.prre_fecha_programada) FROM SIPSA_RECOLECCION_INSUMOS pm1 
                                                                                            WHERE pm1.futi_id = pm.futi_id 
                                                                                            AND pm1.articaco_id = pm.articaco_id 
                                                                                            AND pm1.unme_id = pm.unme_id 
                                                                                            AND pm1.prre_fecha_programada < TRUNC(prre.prre_fecha_programada))) novedad_anterior                                                                                                                                          
                    FROM SIPSA_FUENTES fuen, SIPSA_ARTICULOS arti,
                             SIPSA_GRUPOS grup,SIPSA_SUBGRUPOS subg,
                             SIPSA_FUENTE_ARTICULOS1 futiar1,SIPSA_UNIDADES_MEDIDA unme,
                             SIPSA_TIPO_PRESENTACIONES tipr, SIPSA_PROGRAMACION_RECOLECCION prre,
                             SIPSA_FUENTES_USUARIO_PERFILES fuus,SIPSA_USUARIOS usua,
                             SIPSA_USUARIOS_PERFILES uspe,SIPSA_FUENTES_TIPO_RECOLECCION futi,
                             SIPSA_TIPO_RECOLECCIONES tire,
                             SIPSA_CASAS_COMERCIALES caco,
                             SIPSA_ARTI_CACO articaco,
                             SIPSA_UNME_TIRE unti,
                             SIPSA_ARTI_TIRE arre
                    WHERE  futiar1.futi_id=futi.futi_id
                    AND futi.fuen_id=fuen.fuen_id
                    AND fuen.fuen_id=futi.fuen_id
                    AND articaco.arti_id=arti.arti_id
                    AND arti.arti_id=articaco.arti_id
                    AND fuus.futi_id=futi.futi_id
                    AND futi.tire_id=tire.tire_id
                    AND futi.futi_id=prre.futi_id
                    AND futiar1.articaco_id=articaco.articaco_id
                    AND articaco.caco_id=caco.caco_id
                    AND caco.caco_id=articaco.caco_id
                    AND arti.grup_id=subg.grup_id
                    AND arti.subg_id=subg.subg_id
                    AND subg.grup_id=grup.grup_id
                    AND futiar1.unme_id=unme.unme_id
                    AND unme.tipr_id=tipr.tipr_id
                    AND arti.arti_id = arre.arti_id
                    AND tire.tire_id = arre.tire_id
                    AND arre.artitire_fecha_hasta >= SYSDATE
                    AND unme.unme_id = unti.unme_id
                    AND tire.tire_id = unti.tire_id 
                    AND unti.unmetire_fecha_hasta >= SYSDATE
                    AND futi.futi_fecha_hasta >= SYSDATE
                    AND articaco.articaco_fecha_hasta >= SYSDATE
                    AND futi.tire_id IN (4,5,6,7)
                    AND futiar1.futiar1_estado=1
                    AND prre.prre_estado=19--ACTIVO
                    AND uspe.uspe_id=fuus.uspe_id
                    AND usua.usua_id=uspe.usua_id
                    AND uspe.uspe_id=p_uspe_id
                    AND fuus.fuus_fecha_hasta >=SYSDATE
                    AND TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') <= ( SELECT MAX (prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1 -- '30/09/2013'( SELECT MIN 
                                                                                                                WHERE prre1.futi_id = futi.futi_id
                                                                                                                AND     prre1.prre_estado = 19
                                                                                                                --AND     TO_DATE(SYSDATE, 'dd-mm-yy') <= TO_DATE(prre1.prre_fecha_programada, 'dd-mm-yy')
                                                                                                               ) 
                     AND 0 = (SELECT COUNT(1) FROM SIPSA_RECOLECCION_INSUMOS rein
                                    WHERE  rein.futi_id = futi.futi_id
                                    AND rein.uspe_id = uspe.uspe_id
                                    AND rein.prre_fecha_programada =   prre.prre_fecha_programada
                                    AND rein.articaco_id =   futiar1.articaco_id
                                    AND rein.unme_id = unme.unme_id)  
                    ORDER BY fuen.fuen_id,arti.arti_id,tipr.tipr_id,unme.unme_id;     
    

        /**************************
                    SERVICIO FUENTES
        **************************/          
        OPEN p_Fuentes FOR
          SELECT DISTINCT fuen.fuen_id,fuen.fuen_nombre,fuen.fuen_direccion,fuen.fuen_telefono,fuen.fuen_email,
                    fuen.fuen_nombre_informante,fuen.fuen_cargo_informante,fuen.fuen_telefono_informante,fuen.fuen_email_informante,
                    fuen.muni_id,muni.muni_nombre--,prre.prre_fecha_programada
          FROM SIPSA_FUENTES fuen, 
                   SIPSA_TIPO_RECOLECCIONES tire,SIPSA_MUNICIPIOS muni,
                   SIPSA_PROGRAMACION_RECOLECCION prre,
                   SIPSA_FUENTES_USUARIO_PERFILES fuus,SIPSA_USUARIOS usua,
                   SIPSA_USUARIOS_PERFILES uspe,SIPSA_FUENTES_TIPO_RECOLECCION futi
          WHERE fuen.fuen_id=futi.fuen_id
          AND     fuus.futi_id=futi.futi_id
          AND     fuen.fuen_id=futi.fuen_id
          AND     futi.tire_id = tire.tire_id
          AND     fuen.muni_id=muni.muni_id
          AND     futi.futi_id=prre.futi_id
          AND     uspe.uspe_id=fuus.uspe_id
          AND     usua.usua_id=uspe.usua_id
          AND     futi.futi_fecha_hasta >= SYSDATE
          AND     futi.tire_id IN (4,5,6,7)
          AND     prre.prre_estado=19
          AND     uspe.uspe_id=p_uspe_id
          AND     fuus.fuus_fecha_hasta >=SYSDATE
                    AND TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') <= ( SELECT MAX (prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1 -- '30/09/2013'( SELECT MIN 
                                                                                                                WHERE prre1.futi_id = futi.futi_id
                                                                                                                AND     prre1.prre_estado = 19
                                                                                                                --AND     TO_DATE(SYSDATE, 'dd-mm-yy') <= TO_DATE(prre1.prre_fecha_programada, 'dd-mm-yy')
                                                                                                               ) 
          ORDER BY fuen.fuen_id;   

        /**************************
                    SERVICIO FUENTE ARTICULOS
        **************************/          
        OPEN p_FuentesArt FOR
          SELECT futi.futi_id,fuen.fuen_id,fuen.fuen_nombre,fuen.fuen_direccion,fuen.fuen_telefono,fuen.fuen_email,
                   fuen.fuen_nombre_informante,fuen.fuen_cargo_informante,fuen.fuen_telefono_informante,fuen.fuen_email_informante,
                    futi.tire_id,
                    tire.tire_nombre,
                    fuen.muni_id,muni.muni_nombre,prre.prre_fecha_programada
          FROM SIPSA_FUENTES fuen, 
                   SIPSA_TIPO_RECOLECCIONES tire,SIPSA_MUNICIPIOS muni,
                   SIPSA_PROGRAMACION_RECOLECCION prre,
                   SIPSA_FUENTES_USUARIO_PERFILES fuus,SIPSA_USUARIOS usua,
                   SIPSA_USUARIOS_PERFILES uspe,SIPSA_FUENTES_TIPO_RECOLECCION futi
          WHERE fuen.fuen_id=futi.fuen_id
          AND     fuus.futi_id=futi.futi_id
          AND     fuen.fuen_id=futi.fuen_id
          AND     futi.tire_id = tire.tire_id
          AND     fuen.muni_id=muni.muni_id
          AND     futi.futi_id=prre.futi_id
          AND     uspe.uspe_id=fuus.uspe_id
          AND     usua.usua_id=uspe.usua_id
          AND     futi.futi_fecha_hasta >= SYSDATE
          AND     futi.tire_id IN (4,5,6,7)
          AND     prre.prre_estado=19
          AND     uspe.uspe_id=p_uspe_id
          AND     fuus.fuus_fecha_hasta >=SYSDATE
        AND TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') <= ( SELECT MAX (prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1 -- '30/09/2013'( SELECT MIN 
                                                                                                    WHERE prre1.futi_id = futi.futi_id
                                                                                                    AND     prre1.prre_estado = 19
                                                                                                    --AND     TO_DATE(SYSDATE, 'dd-mm-yy') <= TO_DATE(prre1.prre_fecha_programada, 'dd-mm-yy')
                                                                                                   ) 
          ORDER BY fuen.fuen_id;   


 
         /**************************
                    SERVICIO ARTICULOS
        ***************************/         
        OPEN p_Articulos FOR
          SELECT articaco.articaco_id,arti.arti_id,arti.arti_nombre,articaco.caco_id,caco.caco_nombre,articaco.articaco_regica_linea,grup.grup_nombre,tire.tire_id,tire.tire_nombre
          FROM SIPSA_ARTICULOS arti, SIPSA_GRUPOS grup, SIPSA_TIPO_RECOLECCIONES tire,SIPSA_CASAS_COMERCIALES caco, SIPSA_ARTI_TIRE artitire, SIPSA_ARTI_CACO articaco
          WHERE arti.grup_id = grup.grup_id
          AND articaco.caco_id=caco.caco_id
          AND articaco.arti_id = arti.arti_id
          AND artitire.arti_id = arti.arti_id
          AND artitire.tire_id = tire.tire_id
          AND artitire.artitire_fecha_hasta >= SYSDATE
          AND articaco.articaco_fecha_hasta >= SYSDATE
          AND artitire.tire_id IN (4,5,6,7)
          ORDER BY tire.tire_id,arti.arti_nombre,caco.caco_nombre;

        /**************************
                    SERVICIO UNIDADES
        **************************/          
        OPEN p_Unidades FOR
          SELECT DISTINCT unmetire.tire_id,tire.tire_nombre,unme.unme_id,tipr.tipr_id,tipr.tipr_nombre,unme.unme_cantidad_ppal,unme.unme_nombre_ppal,
                      unme.unme_cantidad_2,unme.unme_nombre_2  
          FROM SIPSA_UNIDADES_MEDIDA unme,SIPSA_TIPO_PRESENTACIONES tipr, SIPSA_UNME_TIRE unmetire,SIPSA_TIPO_RECOLECCIONES tire
          WHERE unme.tipr_id=tipr.tipr_id
          AND unmetire.tire_id = tire.tire_id
          AND unme.unme_id = unmetire.unme_id 
          AND unmetire.unmetire_fecha_hasta >= SYSDATE
          AND unmetire.tire_id IN (4,5,6,7)
          ORDER BY  unme.unme_id;   

        /**************************
                    SERVICIO CASAS COMERCIALES
        **************************/          
        OPEN p_CasasComerciales FOR
          /*SELECT caco.caco_id,caco.caco_nombre
          FROM SIPSA_CASAS_COMERCIALES caco         
          ORDER BY caco.caco_id;   */
          SELECT caco.caco_id,caco.caco_nombre
          FROM SIPSA_CASAS_COMERCIALES caco, SIPSA_ARTI_CACO arca
          WHERE 1=1
          AND caco.caco_id = arca.caco_id
          AND arca.articaco_fecha_hasta >= sysdate
          GROUP BY caco.caco_id,caco.caco_nombre        
          ORDER BY caco.caco_id;


         /**************************
           SERVICIO GRUPOS
        **************************/          
        OPEN p_Grupos FOR
          /*SELECT grup.grup_id,grup.grup_nombre
          FROM SIPSA_GRUPOS grup
          WHERE grup.grup_id >=90
          ORDER BY grup.grup_id;  */
          
          SELECT DISTINCT grup.grup_id,grup.grup_nombre,tire.tire_id
          FROM SIPSA_ARTICULOS arti, SIPSA_GRUPOS grup, SIPSA_TIPO_RECOLECCIONES tire,SIPSA_CASAS_COMERCIALES caco, SIPSA_ARTI_TIRE artitire, SIPSA_ARTI_CACO articaco
          WHERE arti.grup_id = grup.grup_id
          AND articaco.caco_id=caco.caco_id
          AND articaco.arti_id = arti.arti_id
          AND artitire.arti_id = arti.arti_id
          AND artitire.tire_id = tire.tire_id
          AND artitire.artitire_fecha_hasta >= SYSDATE
          AND articaco.articaco_fecha_hasta >= SYSDATE
          AND artitire.tire_id IN (4,5,6,7)
          ORDER BY 1,3;

         /**************************
           SERVICIO OBSERVACIONES
        **************************/          

        PQ_SIPSA_OBSERVACIONES.pr_Consultar(p_obse_id                => NULL,
                                                                      p_uspe_id => p_uspe_id,
                                                                      p_resultado         => p_observaciones,
                                                                      p_codigo_error      => p_codigo_error,
                                                                      p_mensaje           => p_mensaje);

   
        /**************************
                    SERVICIO PERSONAS
        **************************/        
         PQ_SIPSA_USUARIOS_PERFILES.pr_Consultar(p_uspe_id  => p_uspe_id,--v_usua_id_perfil,
                                                                            p_resultado      => p_Persona,
                                                                            p_codigo_error   => p_codigo_error,
                                                                            p_mensaje        => p_mensaje); 
   
       --NO SE ESTA USANDO EN SIPSA-DMC YA QUE NO SE NECESITA POR QUE LOS DATOS SE ENVIAN EN EL SERVICIO CONSULTAR_USUARIO
      /*  OPEN p_Persona FOR   
      SELECT uspe.uspe_id,
                         uspe.usua_id,
                         uspe.perf_id,
                         PQ_SIPSA_PERFILES.fn_ObtenerNombre(uspe.perf_id) perfil,
                         PQ_SIPSA_USUARIOS.fn_ObtenerNombre(uspe.usua_id) nombre_persona,
                         PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(uspe.usua_id) usuario,
                        uspe.uspe_fecha_creacion,
                        uspe.uspe_usuario_creacion,
                        uspe.uspe_fecha_modificacion,
                        uspe.uspe_usuario_modificacion
               FROM SIPSA_USUARIOS_PERFILES uspe,SIPSA_USUARIOS usua,SIPSA_PERFILES perf
              WHERE     usua.usua_id = uspe.usua_id
              AND        uspe.perf_id = perf.perf_id  
              --AND        usua.usua_id = p_id_usuario
               AND     uspe.uspe_id=p_uspe_id;   */


EXCEPTION
    WHEN OTHERS THEN
    p_codigo_error := 1;
    p_mensaje:=p_mensaje;
      
/***********************************
  --  servicio fuentes

     Pq_Ind_Detalles_Fuente.pr_consultarGea( p_id             => NULL,
                                             p_ciudad         => v_ciudad,
                                             p_resultado      => p_fuentes,
                                             p_codigo_error   => p_codigo_error,
                                             p_mensaje        => p_mensaje);

***********************************/               

END Pr_CargarDMCRecoleccionI;

PROCEDURE pr_IncorporarDMCI
 /********************************************************************************
 Descripcion   : metodo para incorporar la informacion obtenida en la DMC en una
                 Recoleccion de Insumos

 parametros    :
 in            :   p_usuario        usuario del sistema


 out           : p_codigo_error valor que indica la ocurrencia de error en el proceso
                 p_mensaje      mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/02/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
 (--p_sistema                   IN VARCHAR2,    
  p_usuario                   IN SIPSA_RECOLECCION_INSUMOS.rein_usuario_modificacion%TYPE,
  --p_fuen_id                   IN SIPSA_RECOLECCION_INSUMOS.fuen_id%TYPE
  p_codigo_error              OUT NUMBER,
  p_mensaje                   OUT VARCHAR2) IS

CURSOR  c_fuente(p_futi IN NUMBER) IS
SELECT c.futi_id,b.fuen_id--,a.info_nombre,a.info_cargo,a.info_email,a.info_fecha_visita,
FROM SIPSA_FUENTES b, SIPSA_FUENTES_TIPO_RECOLECCION c    
WHERE b.fuen_id=c.fuen_id
AND c.futi_id=p_futi;

CURSOR c_articulo (p_futi IN NUMBER, p_arti IN NUMBER) IS
SELECT ARTI_ID 
FROM SIPSA_FUENTE_ARTICULOS
WHERE FUTI_ID=p_futi
AND ARTI_ID=p_arti;

CURSOR c_recoleccion IS
SELECT tmrein.fuen_id,tmrein.futi_id,tmrein.tire_id,tmrein.uspe_id,tmrein.prre_fecha_programada,tmrein.articaco_id,tmrein.arti_id,
            tmrein.arti_nombre,tmrein.caco_id,tmrein.rein_casa_comercial,tmrein.regica_linea,tmrein.unme_id,tmrein.rein_precio_anterior,
            tmrein.rein_novedad_anterior,tmrein.rein_precio_recolectado,tmrein.rein_novedad,tmrein.obse_id,tmrein.rein_observacion,tmrein.rein_fecha_recoleccion
FROM SIPSA_TMP_RECO_INSUMOS tmrein
WHERE 0 =  ( SELECT COUNT (1) FROM SIPSA_RECOLECCION_INSUMOS rein
                 WHERE rein.futi_id = tmrein.futi_id
                 AND rein.uspe_id =tmrein.uspe_id
                 AND rein.prre_fecha_programada = tmrein.prre_fecha_programada
                 AND rein.articaco_id = tmrein.articaco_id
                 AND rein.unme_id = tmrein.unme_id
                 AND rein.rein_precio_recolectado = tmrein.rein_precio_recolectado)
AND 0 =  ( SELECT COUNT (1) FROM SIPSA_APROBA_INSUMOS apin
                 WHERE apin.apin_futi_id = tmrein.futi_id
                 AND apin.apin_fuen_id =tmrein.fuen_id
                 AND apin.tire_id =tmrein.tire_id
                 AND apin.uspe_id =tmrein.uspe_id
                 AND apin.apin_prre_fecha_programada = tmrein.prre_fecha_programada
                 AND apin.apin_articaco_id=tmrein.articaco_id
                 AND apin.apin_arti_id =tmrein.arti_id
                 AND apin.apin_caco_id =tmrein.caco_id
                 AND apin.unme_id =tmrein.unme_id
                 AND apin.apin_precio_recolectado = tmrein.rein_precio_recolectado);             


CURSOR c_fte_informantes IS
SELECT  tminfo.fuen_id,tminfo.futi_id,tminfo.muni_id,tminfo.tire_id,tminfo.uspe_id,tminfo.fuen_nombre,tminfo.fuen_direccion,
             tminfo.fuen_telefono,tminfo.fuen_email,tminfo.info_nombre,tminfo.info_cargo,tminfo.info_telefono,tminfo.info_email,
             tminfo.fecha_visita
FROM SIPSA_TMP_FUENTES_INFORMANTES tminfo ---SIPSA_APROBA_FUEN_INFO
WHERE 0 =  ( SELECT COUNT (1) FROM SIPSA_APROBA_FUEN_INFO apfuin
                     WHERE apfuin.apfuin_futi_id = tminfo.futi_id
                     AND apfuin.apfuin_fuen_id = tminfo.fuen_id 
                     AND apfuin.tire_id = tminfo.tire_id
                     AND apfuin.muni_id = tminfo.muni_id
                     AND apfuin.uspe_id = tminfo.uspe_id
                     AND apfuin.apfuin_fecha_visita = tminfo.fecha_visita);

v_uspe_id     NUMBER;
v_futi_id     NUMBER;
v_fuen_id     NUMBER;
v_info_id     NUMBER;
v_tire_id     NUMBER;
v_idsession   NUMBER;
v_arti_id NUMBER;
c_fuar NUMBER;--Constante que me indica que la FUENTE y el ARTICULO SON NUEVOS.
v_obse_id              SIPSA_OBSERVACIONES.obse_id%TYPE;
v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
v_cantidad NUMBER:=0;
v_registros NUMBER:=0;
v_fecha_programada DATE;
v_futi_id1    NUMBER;
      
ve_uspe_id    EXCEPTION;
ve_futi_id    EXCEPTION;
ve_Fuente     EXCEPTION;  


BEGIN

    p_codigo_error := 0;
    p_mensaje      := NULL;

    v_uspe_id:= PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario));
    v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
      
    IF v_uspe_id IS NULL THEN  
         p_mensaje      := 'NO HAY UN PERFIL ASOCIADO AL USUARIO'||SQLERRM;
         RAISE ve_uspe_id;          
    END IF;

    v_futi_id:= PQ_SIPSA_FUEN_USUA_PERF.Fn_ObtenerId(v_uspe_id);
   
    IF v_futi_id IS NULL THEN   
        p_mensaje      := 'NO HAY UNA FUENTE ASOCIADA AL USUARIO'||SQLERRM;
        RAISE ve_futi_id;          
    END IF;
/*
                                    (SELECT  tminfo.fuen_id,tminfo.futi_id,tminfo.muni_id,tminfo.tire_id,tminfo.uspe_id,tminfo.fuen_nombre,
                                                   tminfo.fuen_direccion,tminfo.fuen_telefono,tminfo.fuen_email,tminfo.info_nombre,
                                                   tminfo.info_cargo,tminfo.info_telefono,tminfo.info_email,tminfo.fecha_visita
                                     FROM SIPSA_TMP_FUENTES_INFORMANTES tminfo)

*/
        --ACTUALIZAR DATOS DE UNA FUENTE Y DEL INFORMANTE
        FOR v_fueninfo IN c_fte_informantes  LOOP
             
             v_futi_id:=null;
             v_fuen_id:=null;
             c_fuar:= 9000000000;                         
             
             OPEN c_fuente (v_fueninfo.futi_id);
             FETCH c_fuente INTO v_futi_id,v_fuen_id;
             CLOSE  c_fuente;

               IF v_futi_id IS NULL  OR  v_fuen_id  IS NULL THEN

                       PQ_SIPSA_APROBACIONES.pr_InsertarIAFI  (p_fuen_id =>     v_fueninfo.fuen_id,
                                                                                      p_futi_id             =>     v_fueninfo.futi_id,
                                                                                      p_muni_id      =>      v_fueninfo.muni_id,
                                                                                      p_tire_id      =>      v_fueninfo.tire_id,                      
                                                                                      p_uspe_id             =>     v_fueninfo.uspe_id,
                                                                                      p_fecha_visita      =>      v_fueninfo.fecha_visita,
                                                                                      p_fuen_nombre    =>      v_fueninfo.fuen_nombre,
                                                                                      p_fuen_direccion    =>      v_fueninfo.fuen_direccion,
                                                                                      p_fuen_telefono    =>      v_fueninfo.fuen_telefono,
                                                                                      p_fuen_email         =>      v_fueninfo.fuen_email,
                                                                                      p_info_nombre    =>      v_fueninfo.info_nombre,
                                                                                      p_info_cargo       =>      v_fueninfo.info_cargo,
                                                                                      p_info_telefono    =>      v_fueninfo.info_telefono,
                                                                                      p_info_email         =>      v_fueninfo.info_email,
                                                                                      p_usuario           =>      UPPER(v_usuario),  
                                                                                      p_codigo_error   =>      p_codigo_error,
                                                                                      p_mensaje        =>       p_mensaje); 

                    IF p_codigo_error > 0 THEN
                            p_mensaje      := 'AL INSERTAR INFORMACION DE LA FUENTE  Y DEL INFORMANTE EN INCORPORAR DMC SE PRESENTO - '||p_mensaje;
                            RAISE ve_Fuente;
                    END IF;         

             ELSE
                            PQ_SIPSA_FUENTES.pr_ActualizarDmc   ( p_fuen_id             =>      v_fuen_id,
                                                                                         p_fuen_nombre    =>      v_fueninfo.fuen_nombre,
                                                                                         p_fuen_direccion    =>      v_fueninfo.fuen_direccion,
                                                                                         p_fuen_telefono    =>      v_fueninfo.fuen_telefono,
                                                                                         p_fuen_email         =>      v_fueninfo.fuen_email,
                                                                                         p_info_nombre    =>      v_fueninfo.info_nombre,
                                                                                         p_info_cargo    =>      v_fueninfo.info_cargo,
                                                                                         p_info_telefono    =>      v_fueninfo.info_telefono,
                                                                                         p_info_email         =>      v_fueninfo.info_email,
                                                                                         p_usuario           =>      UPPER(v_usuario),  
                                                                                         p_codigo_error   =>      p_codigo_error,
                                                                                         p_mensaje        =>       p_mensaje); 
                            IF p_codigo_error > 0 THEN
                                    p_mensaje      := 'AL ACTUALIZAR INFORMACION DE LA FUENTE  Y DEL INFORMANTE EN INCORPORAR DMC SE PRESENTO - '||p_mensaje;
                                    RAISE ve_Fuente;
                            END IF;                        
               END IF;

        END LOOP;


        --INSERTAMOS EN LA TABLA DEFINITIVA DE SIPSA_RECOLECCION_INSUMOS
        /* (SELECT tmrein.fuen_id,tmrein.futi_id,tmrein.tire_id,tmrein.uspe_id,tmrein.prre_fecha_programada,tmrein.articaco_id,tmrein.arti_id,tmrein.arti_nombre,
                                                                  tmrein.caco_id,tmrein.rein_casa_comercial,tmrein.regica_linea,
                                                                  tmrein.unme_id,tmrein.rein_precio_anterior,tmrein.rein_novedad_anterior,tmrein.rein_precio_recolectado,tmrein.rein_novedad, 
                                                                  tmrein.obse_id,tmrein.rein_observacion
                                                              FROM SIPSA_TMP_RECO_INSUMOS tmrein)*/
        FOR v_recoleccion_insumos IN c_recoleccion LOOP
             v_futi_id:=null;
             v_fuen_id:=null;
             c_fuar:= 9000000000;                           
             OPEN c_fuente (v_recoleccion_insumos.futi_id);
              fetch c_fuente into v_futi_id,v_fuen_id;
             close  c_fuente;
             
             v_arti_id:=NULL;
             
             open c_articulo(v_recoleccion_insumos.futi_id,v_recoleccion_insumos.arti_id);
             fetch c_articulo into v_arti_id;
             close c_articulo; 
 

            IF v_recoleccion_insumos.obse_id < 1 OR v_recoleccion_insumos.obse_id > 9000000000 THEN
                    v_obse_id := NULL;
            ELSE
                    v_obse_id := v_recoleccion_insumos.obse_id;
            END IF;

        
        
           --IF v_futi_id > 9000000000 OR v_arti_id > 9000000000 OR v_recoleccion_insumos.rein_novedad <>'P' THEN
           IF v_futi_id > 9000000000  OR v_recoleccion_insumos.rein_novedad IN ('IA','IN') OR v_recoleccion_insumos.articaco_id > 9000000000 THEN

            -- INSERTAMOS EN LA TABLA DE APROBACION DE RECOLECCION DE INSUMOS    
             PQ_SIPSA_APROBACIONES.pr_InsertarIA (  p_fuen_id                             =>      v_recoleccion_insumos.fuen_id,
                                                                             p_futi_id                               =>      v_recoleccion_insumos.futi_id,
                                                                             p_tire_id                               =>      v_recoleccion_insumos.tire_id,                         
                                                                             p_uspe_id                             =>      v_recoleccion_insumos.uspe_id,
                                                                             p_prre_fecha_programada        =>      v_recoleccion_insumos.prre_fecha_programada,
                                                                             p_articaco_id                      =>      v_recoleccion_insumos.articaco_id,
                                                                             p_arti_id                                  =>      v_recoleccion_insumos.arti_id,
                                                                             p_arti_nombre                         =>      v_recoleccion_insumos.arti_nombre,
                                                                             p_regica_linea                                =>      v_recoleccion_insumos.regica_linea,
                                                                             p_caco_id                                  =>      v_recoleccion_insumos.caco_id,
                                                                             p_casa_comercial               =>      v_recoleccion_insumos.rein_casa_comercial,
                                                                             p_unme_id                               =>      v_recoleccion_insumos.unme_id,
                                                                             p_precio_recolectado                =>      v_recoleccion_insumos.rein_precio_recolectado,
                                                                             p_novedad                              =>      v_recoleccion_insumos.rein_novedad,
                                                                             p_obse_id                             => v_obse_id,
                                                                             p_observacion                          =>      v_recoleccion_insumos.rein_observacion,
                                                                             p_esta_id                              => 1,
                                                                             p_fecha_recoleccion       => v_recoleccion_insumos.rein_fecha_recoleccion,
                                                                             p_usuario                                        =>      UPPER(v_usuario),  
                                                                             p_codigo_error                                =>      p_codigo_error,
                                                                             p_mensaje                                      =>       p_mensaje);
                                                                                                                                    
                IF p_codigo_error > 0 THEN
                    p_mensaje      := 'AL INSERTAR APROBA DE INSUMOS EN INCORPORAR DMC SE PRESENTO - '||p_mensaje;
                    RAISE ve_Fuente;
                END IF;           
            
           ELSE         
                -- INSERTAMOS EN LA TABLA DEFINITIVA DE RECOLECCION DE INSUMOS          
                PQ_SIPSA_RECOLECCION.pr_InsertarI (  p_futi_id                                   =>      v_recoleccion_insumos.futi_id,
                                                                             p_uspe_id                                =>      v_recoleccion_insumos.uspe_id,
                                                                             p_prre_fecha_programada        =>      v_recoleccion_insumos.prre_fecha_programada,
                                                                             p_articaco_id                                  =>      v_recoleccion_insumos.articaco_id,
                                                                             p_unme_id                               =>      v_recoleccion_insumos.unme_id,
                                                                             p_rein_precio_anterior              =>      v_recoleccion_insumos.rein_precio_anterior,
                                                                             p_rein_novedad_anterior              =>      v_recoleccion_insumos.rein_novedad_anterior,
                                                                             p_rein_precio_recolectado         =>      v_recoleccion_insumos.rein_precio_recolectado,
                                                                             p_rein_novedad              =>      v_recoleccion_insumos.rein_novedad, 
                                                                             p_obse_id                               =>      v_obse_id,
                                                                             p_rein_observacion        =>      v_recoleccion_insumos.rein_observacion,
                                                                             p_esta_id                      =>      1,
                                                                             p_fecha_recoleccion       => v_recoleccion_insumos.rein_fecha_recoleccion,
                                                                             p_usuario           =>      UPPER(v_usuario),  
                                                                             p_codigo_error   =>      p_codigo_error,
                                                                             p_mensaje        =>       p_mensaje);
                                                                                                                                    
                IF p_codigo_error > 0 THEN
                    p_mensaje      := 'AL INSERTAR RECOLECCION DE INSUMOS EN INCORPORAR DMC SE PRESENTO - '||p_mensaje;
                    RAISE ve_Fuente;
                END IF;                         

               
           END IF;

     END LOOP;

    FOR i IN (  SELECT futiar1.futi_id futi_id , prre.prre_fecha_programada,COUNT(1) registros 
                    FROM SIPSA_FUENTE_ARTICULOS1 futiar1,SIPSA_FUENTES_USUARIO_PERFILES fuus, SIPSA_USUARIOS_PERFILES uspe, SIPSA_PROGRAMACION_RECOLECCION prre
                    WHERE futiar1.futiar1_estado=1
                    AND futiar1.futi_id = fuus.futi_id
                    AND futiar1.futi_id = prre.futi_id 
                    AND uspe.uspe_id = fuus.uspe_id
                    AND uspe.uspe_id = v_uspe_id
                    AND uspe.perf_id = 8
                    AND prre.prre_estado = 19
                    AND futiar1.futiar1_estado =1
                    AND fuus.fuus_fecha_hasta >= SYSDATE
                    AND TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') <= ( SELECT MAX(prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1
                                                                                                                 WHERE prre1.futi_id = futiar1.futi_id
                                                                                                                 AND     prre1.prre_estado = 19
                                                                                                                --AND TO_DATE(SYSDATE, 'dd-mm-yy') <= TO_DATE(prre1.prre_fecha_programada, 'dd-mm-yy')
                                                                                                        ) 
                    GROUP BY futiar1.futi_id, prre.prre_fecha_programada
                    ORDER BY futiar1.futi_id, prre.prre_fecha_programada
    ) LOOP
  
        BEGIN
            SELECT -- rein.futi_id, prre.prre_fecha_programada, COUNT(1) INTO v_futi_id1, v_fecha , v_cantidad
             COUNT(1) INTO v_cantidad
            FROM SIPSA_RECOLECCION_INSUMOS rein, SIPSA_PROGRAMACION_RECOLECCION prre
            WHERE rein.futi_id = prre.futi_id
            AND rein.prre_fecha_programada = prre.prre_fecha_programada
            AND rein.futi_id = i.futi_id
            AND rein.uspe_id = v_uspe_id
            AND prre.prre_fecha_programada = i.prre_fecha_programada
            GROUP BY rein.futi_id, prre.prre_fecha_programada;
        EXCEPTION WHEN OTHERS THEN NULL;
        END;

    
        IF i.registros <> v_cantidad THEN
            PQ_SIPSA_PROG_RECOLECCION.pr_Actualizar (    p_futi_id                   =>      i.futi_id,                                                                         
                                                                                     p_prre_fecha            =>      i.prre_fecha_programada,
                                                                                     p_prre_estado          =>      19,
                                                                                     p_usuario                 =>      v_usuario,  
                                                                                     p_codigo_error         =>      p_codigo_error,
                                                                                     p_mensaje               =>       p_mensaje);
        ELSE
            PQ_SIPSA_PROG_RECOLECCION.pr_Actualizar (    p_futi_id                   =>      i.futi_id,                                                                         
                                                                                     p_prre_fecha            =>      i.prre_fecha_programada,
                                                                                     p_prre_estado          =>      20,
                                                                                     p_usuario                 =>      v_usuario,  
                                                                                     p_codigo_error         =>      p_codigo_error,
                                                                                     p_mensaje               =>       p_mensaje);
        END IF;

    END LOOP;
    /*BEGIN
        SELECT prre.prre_fecha_programada,COUNT(1) INTO v_fecha_programada,v_registros 
        FROM SIPSA_FUENTE_ARTICULOS1 futiar1,SIPSA_FUENTES_USUARIO_PERFILES fuus, SIPSA_USUARIOS_PERFILES uspe, SIPSA_PROGRAMACION_RECOLECCION prre
        WHERE futiar1.futiar1_estado=1
        AND futiar1.futi_id = fuus.futi_id
        AND futiar1.futi_id = prre.futi_id 
        AND uspe.uspe_id = fuus.uspe_id
        AND fuus.futi_id = v_futi_id
        AND fuus.uspe_id = v_uspe_id
        AND uspe.perf_id = 8
        AND prre.prre_estado = 19
        AND TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') <= ( SELECT MAX(prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1
                                                                 WHERE prre1.futi_id = futiar1.futi_id
                                                                 AND     prre1.prre_estado = 19
                                                               --AND TO_DATE(SYSDATE, 'dd-mm-yy') <= TO_DATE(prre1.prre_fecha_programada, 'dd-mm-yy')
                                                                ) 
        GROUP BY futiar1.futi_id,fuus.uspe_id, prre.prre_fecha_programada
        ORDER BY futiar1.futi_id, prre.prre_fecha_programada
        ;
            
        SELECT -- rein.futi_id, prre.prre_fecha_programada, COUNT(1) INTO v_futi_id1, v_fecha , v_cantidad
        COUNT(1) INTO v_cantidad
        FROM SIPSA_RECOLECCION_INSUMOS rein, SIPSA_PROGRAMACION_RECOLECCION prre
        WHERE rein.futi_id = prre.futi_id
        AND rein.prre_fecha_programada = prre.prre_fecha_programada
        AND rein.futi_id = v_futi_id
        AND prre.prre_fecha_programada = v_fecha_programada
        GROUP BY rein.futi_id, prre.prre_fecha_programada;
        
        EXCEPTION WHEN OTHERS THEN NULL;
    END;
        
    IF (v_registros = v_cantidad) THEN
           PQ_SIPSA_PROG_RECOLECCION.pr_Actualizar (p_futi_id      =>  v_futi_id,                                                                         
                                                    p_prre_fecha   =>  v_fecha_programada,
                                                    p_prre_estado  =>  20,
                                                    p_usuario      =>  v_usuario,  
                                                    p_codigo_error =>  p_codigo_error,
                                                    p_mensaje      =>  p_mensaje);  
    END IF;*/

    ------ REVISAR-------- CODIGO SE PASO A UN TRIGGER
        
     SELECT SYS_CONTEXT('USERENV','SID') INTO v_idsession FROM DUAL;

     DELETE FROM SIPSA_TMP_RECO_INSUMOS;
     DELETE FROM SIPSA_TMP_FUENTES_INFORMANTES;

     EXCEPTION
          WHEN OTHERS THEN
                 ROLLBACK;
                 DELETE FROM SIPSA_TMP_RECO_INSUMOS;
                 DELETE FROM SIPSA_TMP_FUENTES_INFORMANTES;
               p_codigo_error := 1;
               p_mensaje := p_mensaje||SQLERRM;

           
END pr_IncorporarDMCI;

PROCEDURE Pr_InsertarI2
/********************************************************************************
 Descripcion   : método para incorporar información sobre la tabla SIPSA_RECOLECCION_INSUMOS2

 parametros    :
 in            : p_futi_id
                 p_uspe_id
                 p_prre_fecha_programada
                 p_arti_id
                 p_caco_id
                 p_unme_id
                 p_rein_precio_anterior
                 p_rein_novedad_anterior
                p_rein_precio_recolectado
                p_rein_novedad
                p_obse_id
                p_rein_observacion
                p_usuario

 out     :      p_futi_id
                 p_prre_fecha_programada
                 p_arti_id
                 p_caco_id
                 p_unme_id
                 p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/02/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS

contador NUMBER;
--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador         := NULL;
   
   SELECT COUNT(1) INTO contador FROM SIPSA_RECOLECCION_INSUMOS2
   WHERE futi_id = p_futi_id
   AND uspe_id = p_uspe_id
   AND grin2_id = p_grin2_id
   AND prre_fecha_programada = p_prre_fecha_programada
   AND rein2_id_informante = p_id_informante
   AND rein2_nom_informante =p_nom_informante
   AND rein2_tel_informante = p_tel_informante
   ;
   
   IF contador = 0 THEN
        --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
        INSERT INTO    SIPSA_RECOLECCION_INSUMOS2 (futi_id,uspe_id,grin2_id,prre_fecha_programada,rein2_novedad_gral,rein2_id_informante,rein2_nom_informante,rein2_tel_informante,
                                                                             rein2_precio_recolectado,rein2_novedad,obse_id,rein2_observacion,rein2_fecha_recoleccion,esta_id,rein2_usuario_creacion)
        VALUES(p_futi_id,p_uspe_id,p_grin2_id,p_prre_fecha_programada,p_novedad_gral,p_id_informante,p_nom_informante,p_tel_informante,
                     p_precio_recolectado,p_novedad,p_obse_id,p_rein2_observacion,p_fecha_recoleccion,p_esta_id,UPPER(p_usuario)); 
   ELSE
        
        PQ_SIPSA_RECOLECCION.pr_ActualizarI2(p_futi_id,p_uspe_id,p_grin2_id,p_prre_fecha_programada,p_novedad_gral,p_id_informante,p_nom_informante,p_tel_informante,
                     p_precio_recolectado,p_novedad,p_obse_id,p_rein2_observacion,p_fecha_recoleccion,p_esta_id,UPPER(p_usuario),p_codigo_error,p_mensaje); 
   END IF;                      
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_RECOLECCION_INSUMOS2 se presento '||SQLERRM;                                                                                   
END Pr_InsertarI2;

PROCEDURE Pr_ActualizarI2
/********************************************************************************
 Descripcion   : método para actualizar información sobre la tabla SIPSA_RECOLECCION_INSUMOS

 parametros    :
 in            : p_futi_id
                 p_uspe_id
                 p_prre_fecha_programada
                 p_arti_id
                 p_caco_id
                 p_unme_id
                 p_rein_precio_anterior
                 p_rein_novedad_anterior
                p_rein_precio_recolectado
                p_rein_novedad
                p_obse_id
                p_rein_observacion
                p_usuario

 out     :      p_futi_id
                 p_prre_fecha_programada
                 p_arti_id
                 p_caco_id
                 p_unme_id
                 p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/02/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2)  IS

   --v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
 
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));

   UPDATE SIPSA_RECOLECCION_INSUMOS2
   SET  rein2_precio_recolectado = p_precio_recolectado,
           rein2_novedad = p_novedad,
           obse_id = p_obse_id,
           rein2_observacion = p_rein2_observacion,
           esta_id = p_esta_id,
           rein2_usuario_modificacion=UPPER(p_usuario)
    WHERE futi_id = p_futi_id
    AND     uspe_id = p_uspe_id
    AND     grin2_id = p_grin2_id
    AND     prre_fecha_programada = p_prre_fecha_programada
    AND     rein2_id_informante = p_id_informante
    AND     esta_id = 1;--agregue
    
       
   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'no se encontro ningun registro para actualizar en la tabla SIPSA_RECOLECCION_INSUMOS2 con los identificadores dados ';
   END IF;

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al actualizar la tabla SIPSA_RECOLECCION_INSUMOS2 se presento '||SQLERRM;                                                                                   
END Pr_ActualizarI2;

PROCEDURE Pr_CargarDMCRecoleccionI2
 /********************************************************************************
 Descripcion   : metodo para consultar las estructuras relacionadas con la recoleccion
                 para ser llamado varias veces en un periodo

 parametros    :
 in            : p_uspe_id  



 out           :  p_TiposRecolecciones
                   p_Principal
                   p_Fuentes
                   p_Informadores
                   p_FuentesTire
                   p_Persona
                   p_Articulos
                   p_Caracteristicas
                   p_ArtiCara
                   p_Observaciones
                   p_codigo_error valor que indioca la ocurrencia de error en el proceso
                   p_mensaje      mensaje del error ocurrido

  Realizado por : Vitaliano Corredor
 Fecha Creacion: 10/03/2013

 Modificado por: Carlos Alberto López N.
 Fecha Modificacion: 17/02/2014
 Cambios       : Se adiciona al query la validacion de fechas de vencimientos para fuente, 
                 articulo, unidad de media y casa comercial. 
 Modificado por: Hernán Darío Blanco M.
 Fecha Modificacion: 12/06/2018
 Cambios       : Se excluye el factor distrito de riego (tire_id=13).
*******************************************************************************/
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
  p_mensaje                   OUT VARCHAR2) IS

v_tire_id INTEGER;

BEGIN



            /**************************
                        SERVICIO PRINCIPAL
            **************************/           
               OPEN p_Principal FOR
                SELECT    futi.tire_id,
                                --tire.tire_nombre,
                                futi.futi_id,
                                fuen.muni_id,
                                fuen.fuen_id,fuen.fuen_nombre,--fuen.fuen_direccion,                     
                                arti.arti_id,arti.arti_nombre,--arti.arti_reg_ica,
                                futiar2.grin2_id, 
                                DECODE( futi.tire_id,9,                                     PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto1(futiar2.grin2_id, futi.tire_id, arti.arti_nombre), 
                                                            8, arti.arti_nombre||' en '||PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto1(futiar2.grin2_id,futi.tire_id, arti.arti_nombre),                                
                                                                arti.arti_nombre||' -- '||PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto1(futiar2.grin2_id,futi.tire_id, arti.arti_nombre)) nombre_completo,

                                --DECODE( futi.tire_id,9,                                     PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto1(futiar2.grin2_id, futi.tire_id, arti.arti_nombre), 
                                --                            8, arti.arti_nombre||' en '||PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto(futiar2.grin2_id),                                
                                --     
                                --REVISAR
                                --unme.tipr_id,tipr.tipr_nombre,futiar1.unme_id,unme_cantidad_ppal,unme.unme_nombre_ppal,unme_cantidad_2,unme.unme_nombre_2,
                                --grup.grup_nombre,subg.subg_nombre,
                                arti.arti_vlr_min_tomas,arti.arti_vlr_max_tomas,arti.arti_vlr_min_rondas,arti.arti_vlr_max_rondas,arti.arti_vlr_min_diasm,arti.arti_vlr_max_diasm,prre.prre_fecha_programada,
                               NVL((SELECT ROUND(avg(pm.rein2_precio_recolectado),2) FROM SIPSA_RECOLECCION_INSUMOS2 pm 
                                       WHERE futi.futi_id = pm.futi_id 
                                       AND futi.futi_id=futiar2.futi_id
                                       AND futi.fuen_id=fuen.fuen_id 
                                       --AND futiar2.arti_id = pm.arti_id 
                                       AND futiar2.grin2_id = pm.grin2_id
                                       AND pm.prre_fecha_programada =  (SELECT MAX(pm1.prre_fecha_programada) 
                                                                        FROM SIPSA_RECOLECCION_INSUMOS2 pm1 
                                                                        WHERE pm1.futi_id = pm.futi_id
                                                                        AND pm1.futi_id=pm.futi_id 
                                                                        --AND pm1.arti_id = pm.arti_id 
                                                                        AND pm1.grin2_id = pm.grin2_id 
                                                                        AND pm1.prre_fecha_programada < TRUNC(prre.prre_fecha_programada))),1000) prom_anterior
                              /* (SELECT  pm.rein_novedad FROM SIPSA_RECOLECCION_INSUMOS2 pm 
                                       WHERE futi.futi_id = pm.futi_id 
                                       AND futi.futi_id=futiar2.futi_id
                                       AND futi.fuen_id=fuen.fuen_id 
                                       AND futiar2.arti_id = pm.arti_id 
                                       AND futiar2.grin2_id = pm.grin2_id 
                                       AND pm.prre_fecha_programada =  (SELECT MAX(pm1.prre_fecha_programada) 
                                                                        FROM SIPSA_RECOLECCION_INSUMOS2 pm1 
                                                                        WHERE pm1.futi_id = pm.futi_id 
                                                                        AND pm1.arti_id = pm.arti_id 
                                                                        AND pm1.grin2_id = pm.grin2_id 
                                                                        AND pm1.prre_fecha_programada < TRUNC(prre.prre_fecha_programada))) novedad_anterior  */                                                                      
                    FROM SIPSA_FUENTES fuen, SIPSA_ARTICULOS arti,
                             --SIPSA_GRUPOS grup,SIPSA_SUBGRUPOS subg,
                             SIPSA_FUENTE_ARTICULOS2 futiar2,--SIPSA_UNIDADES_MEDIDA unme,
                             --SIPSA_TIPO_PRESENTACIONES tipr, 
                             SIPSA_PROGRAMACION_RECOLECCION prre,
                             SIPSA_FUENTES_USUARIO_PERFILES fuus,SIPSA_USUARIOS usua,
                             SIPSA_USUARIOS_PERFILES uspe,SIPSA_FUENTES_TIPO_RECOLECCION futi,
                             --SIPSA_TIPO_RECOLECCIONES tire,
                             SIPSA_GRUPO_INSUMOS2 grin2,
                             SIPSA_ARTI_TIRE arre
                    WHERE  futiar2.futi_id=futi.futi_id
                    AND futi.fuen_id=fuen.fuen_id
                    AND fuen.fuen_id=futi.fuen_id
                    AND futiar2.grin2_id=grin2.grin2_id
                    AND arti.arti_id=grin2.arti_id
                    AND fuus.futi_id=futi.futi_id
                    --AND futi.tire_id=tire.tire_id
                    AND futi.futi_id=prre.futi_id
                    AND futiar2.grin2_id=grin2.grin2_id
                    --AND arti.grup_id=subg.grup_id
                    --AND arti.subg_id=subg.subg_id
                    --AND subg.grup_id=grup.grup_id
                    AND futi.tire_id > 7
                    AND futi.tire_id < 13
                    AND futiar2.futiar2_estado=1
                    AND prre.prre_estado='19'--ACTIVO
                    AND uspe.uspe_id=fuus.uspe_id
                    AND usua.usua_id=uspe.usua_id
                    AND uspe.uspe_id=p_uspe_id
                    AND fuus.fuus_fecha_hasta >=SYSDATE
                    AND futi.futi_fecha_hasta >= SYSDATE
                    AND arti.arti_id = arre.arti_id
                    AND futi.tire_id = arre.tire_id
                    AND arre.artitire_fecha_hasta >= SYSDATE
                    AND grin2.grin2_fecha_hasta >= SYSDATE
                    AND TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') <= ( SELECT MAX (prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1 -- '30/09/2013'( SELECT MIN 
                                                                                                                WHERE prre1.futi_id = futi.futi_id
                                                                                                                AND     prre1.prre_estado = 19
                                                                                                                --AND     TO_DATE(SYSDATE, 'dd-mm-yy') <= TO_DATE(prre1.prre_fecha_programada, 'dd-mm-yy')
                                                                                                               ) 
                     AND 0 = (SELECT COUNT(1) FROM SIPSA_ENCA_INSUMOS2 enin2
                                    WHERE  enin2.futi_id = futi.futi_id
                                    AND enin2.uspe_id = uspe.uspe_id
                                    AND enin2.prre_fecha_programada =   prre.prre_fecha_programada
                                    AND enin2.grin2_id =   futiar2.grin2_id)                                                                      
                   ORDER BY fuen.fuen_id,futi.tire_id,futiar2.grin2_id, arti.arti_id;     
    

         /**************************
           SERVICIO INFORMADORES
        **************************/         
        OPEN p_Informadores FOR
          SELECT DISTINCT info.muni_id,info.info_id,info.info_nombre,info.info_telefono
          FROM SIPSA_INFORMANTES info, SIPSA_FUENTES fuen, SIPSA_FUENTES_TIPO_RECOLECCION futi, SIPSA_FUENTES_USUARIO_PERFILES fuus
          WHERE fuen.fuen_id = futi.fuen_id      
          AND info.muni_id = fuen.muni_id
          AND futi.futi_id = fuus.futi_id
          AND fuus.uspe_id=p_uspe_id
          AND info.info_fecha_hasta >= SYSDATE
          --AND info.info_nombre not in ('Informante No Disponible') ---Temporal hasta que realice desarrollo de fecha de vencimiento para informantes
          ORDER BY 1,2;
        
          --SELECT DISTINCT rein2.futi_id,rein2.rein2_Id_informante,rein2.rein2_nom_informante,rein2.rein2_tel_informante
          /*SELECT  rein2.futi_id,rein2.rein2_Id_informante,rein2.rein2_nom_informante,MAX(rein2.rein2_tel_informante) as rein2_tel_informante 
          FROM SIPSA_RECOLECCION_INSUMOS2 rein2
          WHERE rein2.uspe_id=p_uspe_id
          GROUP BY rein2.futi_id,rein2.rein2_Id_informante,rein2.rein2_nom_informante
          ORDER BY rein2.futi_id,rein2.rein2_nom_informante;  */

         /***************************************
                    SERVICIO FUENTES TIPO RECOLECION
        **************************************/       
        OPEN p_FuentesTire FOR
          SELECT futi.futi_id,fuen.fuen_id, tire.tire_id,tire.tire_nombre,fuen.fuen_nombre,fuen.muni_id,muni.muni_nombre,prre.prre_fecha_programada
          FROM SIPSA_FUENTES fuen, 
                   SIPSA_TIPO_RECOLECCIONES tire,
                   SIPSA_MUNICIPIOS muni,
                   SIPSA_PROGRAMACION_RECOLECCION prre,
                   SIPSA_FUENTES_USUARIO_PERFILES fuus,SIPSA_USUARIOS usua,
                   SIPSA_USUARIOS_PERFILES uspe,SIPSA_FUENTES_TIPO_RECOLECCION futi
          WHERE fuen.fuen_id=futi.fuen_id
          AND     fuus.futi_id=futi.futi_id
          AND     fuen.fuen_id=futi.fuen_id
          AND     futi.tire_id = tire.tire_id
          AND     fuen.muni_id=muni.muni_id
          AND     futi.futi_id=prre.futi_id
          AND     uspe.uspe_id=fuus.uspe_id
          AND     usua.usua_id=uspe.usua_id
          AND     futi.tire_id>7
          AND     futi.tire_id< 13
          AND     prre.prre_estado='19'
          AND     uspe.uspe_id=p_uspe_id
          AND fuus.fuus_fecha_hasta >=SYSDATE
          AND futi.futi_fecha_hasta >= SYSDATE
          AND TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') <= ( SELECT MAX (prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1 -- '30/09/2013'( SELECT MIN 
                                                                                                    WHERE prre1.futi_id = futi.futi_id
                                                                                                    AND     prre1.prre_estado = 19
                                                                                                    --AND     TO_DATE(SYSDATE, 'dd-mm-yy') <= TO_DATE(prre1.prre_fecha_programada, 'dd-mm-yy')
                                                                                                    )
          ORDER BY tire.tire_id,futi.futi_id,fuen.fuen_id;   


         /**************************
                    SERVICIO GRUPO INSUMOS2
        ***************************/      
        OPEN p_Articulos FOR
          SELECT grin2.grin2_id,arti.arti_id,arti.arti_nombre,
          DECODE( tire.tire_id,9,                                     PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto1(grin2.grin2_id, tire.tire_id, arti.arti_nombre), 
                                      8, arti.arti_nombre||' en '||PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto1(grin2.grin2_id, tire.tire_id, arti.arti_nombre),                                
                                          arti.arti_nombre||' -- ' ||PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto1(grin2.grin2_id, tire.tire_id, arti.arti_nombre)) nombre_completo,
                                          tire.tire_id--,tire.tire_nombre
          FROM SIPSA_ARTICULOS arti, SIPSA_GRUPO_INSUMOS2 grin2, SIPSA_TIPO_RECOLECCIONES tire, SIPSA_ARTI_TIRE artitire
          WHERE arti.arti_id = grin2.arti_id
          AND artitire.arti_id = arti.arti_id
          AND artitire.tire_id = tire.tire_id
          AND grin2.grin2_fecha_hasta >= SYSDATE
          AND artitire.artitire_fecha_hasta >= SYSDATE
          AND artitire.tire_id >7
          AND artitire.tire_id < 13
          ORDER BY tire.tire_id,arti.arti_nombre;



         /**************************
           SERVICIO CARACTERISTICAS
        **************************/         
        OPEN p_Caracteristicas FOR
          SELECT cara.cara_id,cara.cara_descripcion,cara.tire_id,cara.cara_orden
          FROM SIPSA_CARACTERISTICAS cara
          WHERE cara.tire_id >7
          AND cara.tire_id < 13
          ORDER BY cara.tire_id,cara.cara_id;  

          
        /**************************
                    SERVICIO UNIDADES
        **************************/       
        --OPEN p_Unidades FOR
          --SELECT unme.unme_id,tipr.tipr_id,tipr.tipr_nombre,unme.unme_cantidad_ppal,unme.unme_nombre_ppal,
                      --unme.unme_cantidad_2,unme.unme_nombre_2  
          --FROM SIPSA_UNIDADES_MEDIDA unme,SIPSA_TIPO_PRESENTACIONES tipr
          --WHERE unme.tipr_id=tipr.tipr_id
          ----AND     unme.unme_componente LIKE '%6789'
          --ORDER BY  unme.unme_id;   

        /***********************************************
           SERVICIO  VALORES CARACTERISTICAS PERMITIDAS
        ***********************************************/         
        OPEN p_ValCaraPermitidos FOR
          SELECT tire.tire_id,cara.cara_id,cara.cara_descripcion,vape.vape_id,vape.vape_descripcion
          FROM  SIPSA_TIPO_RECOLECCIONES tire, SIPSA_VAPE_CARA vape,SIPSA_CARACTERISTICAS cara
          WHERE cara.tire_id = tire.tire_id
          AND vape.cara_id = cara.cara_id      
          ORDER BY tire.tire_id,cara.cara_id;
 


         /***********************************************
           SERVICIO  VALORES CARACTERISTICAS PRODUCTOS
        ***********************************************/         
        OPEN p_ArtiCaraValores FOR
          SELECT  artitire.tire_id,grin2vaca.cara_id,
          arti.arti_id,grin2.grin2_id,grin2vaca.vape_id,vape.vape_descripcion 
          FROM SIPSA_ARTICULOS arti,SIPSA_ARTI_TIRE artitire,SIPSA_GRUPO_INSUMOS2 grin2,SIPSA_GRIN2_VACA grin2vaca, SIPSA_VAPE_CARA vape
          WHERE arti.arti_id      = artitire.arti_id 
          AND   artitire.tire_id  > 7 
          AND   artitire.tire_id  < 13 
          AND   grin2.arti_id     = arti.arti_id 
          AND   grin2.grin2_id    = grin2vaca.grin2_id
          --AND   grin2vaca.tire_id = vape.tire_id
          AND   grin2vaca.Cara_Id = vape.cara_id
          and   grin2vaca.vape_id = vape.vape_id;




         /**************************
           SERVICIO OBSERVACIONES
        **************************/          

        PQ_SIPSA_OBSERVACIONES.pr_Consultar(p_obse_id                => NULL,
                                                                      p_uspe_id => p_uspe_id,
                                                                      p_resultado         => p_observaciones,
                                                                      p_codigo_error      => p_codigo_error,
                                                                      p_mensaje           => p_mensaje);

  
        /**************************
                    SERVICIO PERSONAS
        **************************/     
         PQ_SIPSA_USUARIOS_PERFILES.pr_consultar(p_uspe_id  => p_uspe_id,--v_usua_id_perfil,
                                                p_resultado      => p_Persona,
                                                p_codigo_error   => p_codigo_error,
                                                p_mensaje        => p_mensaje); 
   
       --NO SE ESTA USANDO EN SIPSA-DMC YA QUE NO SE NECESITA POR QUE LOS DATOS SE ENVIAN EN EL SERVICIO CONSULTAR_USUARIO
      /*  OPEN p_Persona FOR   
      SELECT uspe.uspe_id,
                         uspe.usua_id,
                         uspe.perf_id,
                         PQ_SIPSA_PERFILES.fn_ObtenerNombre(uspe.perf_id) perfil,
                         PQ_SIPSA_USUARIOS.fn_ObtenerNombre(uspe.usua_id) nombre_persona,
                         PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(uspe.usua_id) usuario,
                        uspe.uspe_fecha_creacion,
                        uspe.uspe_usuario_creacion,
                        uspe.uspe_fecha_modificacion,
                        uspe.uspe_usuario_modificacion
               FROM SIPSA_USUARIOS_PERFILES uspe,SIPSA_USUARIOS usua,SIPSA_PERFILES perf
              WHERE     usua.usua_id = uspe.usua_id
              AND        uspe.perf_id = perf.perf_id  
              --AND        usua.usua_id = p_id_usuario
               AND     uspe.uspe_id=p_uspe_id;   */


EXCEPTION
    WHEN OTHERS THEN
    p_codigo_error := 1;
    p_mensaje:=p_mensaje;
      
/***********************************
  --  servicio fuentes

     Pq_Ind_Detalles_Fuente.pr_consultarGea( p_id             => NULL,
                                             p_ciudad         => v_ciudad,
                                             p_resultado      => p_fuentes,
                                             p_codigo_error   => p_codigo_error,
                                             p_mensaje        => p_mensaje);

***********************************/               

END Pr_CargarDMCRecoleccionI2;

PROCEDURE pr_IncorporarDMCI2
 /********************************************************************************
 Descripcion   : metodo para incorporar la informacion obtenida en la DMC en una
                 Recoleccion de Insumos 2

 parametros    :
 in            :   p_usuario        usuario del sistema


 out           : p_codigo_error valor que indica la ocurrencia de error en el proceso
                 p_mensaje      mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 10/04/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
 (--p_sistema                   IN VARCHAR2,    
  p_usuario                   IN SIPSA_RECOLECCION_INSUMOS2.rein2_usuario_modificacion%TYPE,
  --p_fuen_id                   IN SIPSA_RECOLECCION_INSUMOS.fuen_id%TYPE
  p_codigo_error              OUT NUMBER,
  p_mensaje                   OUT VARCHAR2) IS

/*
CURSOR  c_fuente(p_futi IN NUMBER) IS
SELECT c.futi_id,b.fuen_id--,a.info_nombre,a.info_cargo,a.info_email,a.info_fecha_visita,
FROM SIPSA_FUENTES b, SIPSA_FUENTES_TIPO_RECOLECCION c    
WHERE b.fuen_id=c.fuen_id
AND c.futi_id=p_futi;

CURSOR c_articulo (p_futi IN NUMBER, p_arti IN NUMBER,p_grin2_id IN NUMBER) IS
SELECT ARTI_ID 
FROM SIPSA_FUENTE_ARTICULOS2
WHERE FUTI_ID=p_futi
AND ARTI_ID=p_arti
AND GRIN2_ID=p_grin2_id;
*/

CURSOR c_recoleccion2 IS
SELECT  tmenin2.futi_id,tmenin2.uspe_id,tmenin2.arti_id,tmenin2.grin2_id,tmenin2.prre_fecha_programada,tmenin2.enin2_arti_nombre,
             tmenin2.enin2_precio_prom_anterior,tmenin2.enin2_novedad,tmenin2.enin2_ftes_capturadas,tmenin2.obse_id,tmenin2.enin2_observacion,
             tmrein2.rein2_novedad_gral,tmrein2.rein2_id_informante,tmrein2.rein2_nom_informante,tmrein2.rein2_tel_informante,tmrein2.rein2_precio_recolectado,
             tmrein2.rein2_novedad,tmrein2.obse_id AS obse_id_reco,tmrein2.rein2_observacion,tmrein2.rein2_fecha_recoleccion
FROM SIPSA_TMP_ENCA_INSUMOS2 tmenin2, SIPSA_TMP_RECO_INSUMOS2 tmrein2
WHERE tmenin2.futi_id = tmrein2.futi_id 
AND tmenin2.uspe_id =tmrein2.uspe_id
AND tmenin2.arti_id = tmrein2.arti_id
AND tmenin2.grin2_id = tmrein2.grin2_id
AND tmenin2.prre_fecha_programada = tmrein2.prre_fecha_programada
AND 0 =  ( SELECT COUNT (1) FROM SIPSA_ENCA_INSUMOS2 enin2, SIPSA_RECOLECCION_INSUMOS2 rein2
                 WHERE enin2.futi_id = rein2.futi_id
                 AND enin2.uspe_id =rein2.uspe_id
                 AND enin2.grin2_id = rein2.grin2_id
                 AND enin2.prre_fecha_programada = rein2.prre_fecha_programada
                 AND enin2.futi_id = tmenin2.futi_id
                 AND enin2.uspe_id =tmenin2.uspe_id
                 AND enin2.grin2_id = tmenin2.grin2_id
                 AND enin2.prre_fecha_programada = tmenin2.prre_fecha_programada
                 AND enin2.enin2_ftes_capturadas = tmenin2.enin2_ftes_capturadas
                 AND rein2.rein2_precio_recolectado = tmrein2.rein2_precio_recolectado)
AND 0 =  ( SELECT COUNT (1) FROM SIPSA_APROBA_ENCA_INSUMOS2 apenin2, SIPSA_APROBA_RECO_INSUMOS2 aprein2
                 WHERE apenin2.futi_id = aprein2.futi_id
                 AND apenin2.uspe_id = aprein2.uspe_id
                 AND apenin2.arti_id = aprein2.arti_id
                 AND apenin2.apgrin2_id = aprein2.apgrin2_id
                 AND apenin2.prre_fecha_programada = aprein2.prre_fecha_programada
                 AND apenin2.futi_id = tmenin2.futi_id
                 AND apenin2.uspe_id =tmenin2.uspe_id
                 AND apenin2.apgrin2_id = tmenin2.grin2_id
                 AND apenin2.prre_fecha_programada = tmenin2.prre_fecha_programada
                 AND aprein2.aprein2_id_informante = tmrein2.rein2_id_informante
                 AND apenin2.apenin2_ftes_capturadas = tmenin2.enin2_ftes_capturadas
                 AND aprein2.aprein2_precio_recolectado = tmrein2.rein2_precio_recolectado)                  
ORDER BY 1,2,3,4,5,11;          

CURSOR c_grin2vaca (p_futi IN NUMBER, p_uspe IN NUMBER, p_grin2 IN NUMBER) IS
SELECT  tmgrin2vaca.futi_id,tmgrin2vaca.uspe_id,tmgrin2vaca.grin2_id,tmgrin2vaca.tire_id,tmgrin2vaca.cara_id,tmgrin2vaca.vape_id,tmgrin2vaca.valor
FROM SIPSA_TMP_GRIN2_VACA tmgrin2vaca
WHERE tmgrin2vaca.futi_id = p_futi
AND tmgrin2vaca.uspe_id=p_uspe
AND tmgrin2vaca.grin2_id = p_grin2;

v_uspe_id     NUMBER;
v_futi_id     NUMBER;
v_idsession   NUMBER;
--c_fuar NUMBER;--Constante que me indica que la FUENTE y el ARTICULO SON NUEVOS.
v_obse_id              SIPSA_OBSERVACIONES.obse_id%TYPE;
v_valor                  SIPSA_APROBA_GRIN2_VACA.valor%TYPE; 
v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
v_cantidad NUMBER:=0;
v_registros   NUMBER:=0;
v_fecha_programada DATE;
v_futi_id1    NUMBER;

      
ve_uspe_id    EXCEPTION;
ve_futi_id    EXCEPTION;
ve_Fuente     EXCEPTION;  


BEGIN

    p_codigo_error := 0;
    p_mensaje      := NULL;

    v_uspe_id:= PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario));
    v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));

      
    IF v_uspe_id IS NULL THEN  
         p_mensaje      := 'NO HAY UN PERFIL ASOCIADO AL USUARIO'||SQLERRM;
         RAISE ve_uspe_id;          
    END IF;

    v_futi_id:= PQ_SIPSA_FUEN_USUA_PERF.Fn_ObtenerId(v_uspe_id);
   
    IF v_futi_id IS NULL THEN   
        p_mensaje      := 'NO HAY UNA FUENTE ASOCIADA AL USUARIO'||SQLERRM;
        RAISE ve_futi_id;          
    END IF;

        --INSERTAMOS EN LA TABLA DEFINITIVA DE SIPSA_ENCA_INSUMOS2
        FOR v_recoleccion2 IN c_recoleccion2 LOOP

            IF v_recoleccion2.obse_id < 1 OR v_recoleccion2.obse_id > 900000000 THEN
                    v_obse_id := NULL;
            ELSE
                    v_obse_id := v_recoleccion2.obse_id;
            END IF;


            IF v_recoleccion2.obse_id_reco < 1 OR v_recoleccion2.obse_id_reco > 900000000 THEN
                    v_obse_id := NULL;
            ELSE
                v_obse_id := v_recoleccion2.obse_id_reco;
            END IF;


             --v_arti_id:=NULL;             
             --open c_articulo(v_enca_insumos2.futi_id,v_enca_insumos2.arti_id,v_enca_insumos2.grin2_id);
             --fetch c_articulo into v_arti_id;
             --close c_articulo; 

            IF v_recoleccion2.rein2_novedad IN ('IN') THEN
                    --INSERTAMOS EN LA TABLA  DE APROBACION SIPSA_APROBA_ENCA_INSUMOS2 SIPSA_APROBA_GRIN2_VACA SIPSA_APROBA_INSUMOS SIPSA_APROBA_RECO_INSUMOS2
                            PQ_SIPSA_APROBACIONES.pr_InsertarAPGRIN2 ( p_futi_id                               =>   v_recoleccion2.futi_id,
                                                                                                     p_uspe_id                            =>    v_recoleccion2.uspe_id,
                                                                                                     p_apgrin2_id                            =>    v_recoleccion2.grin2_id,                                                                              
                                                                                                     p_arti_id                              =>    v_recoleccion2.arti_id,
                                                                                                     p_usuario                             =>      v_usuario,  
                                                                                                     p_codigo_error                     =>      p_codigo_error,
                                                                                                     p_mensaje                           =>      p_mensaje);

                                    IF p_codigo_error > 0 THEN
                                        p_mensaje      := 'AL INSERTAR SIPSA_APROBA_GRIN2 EN INCORPORAR DMC SE PRESENTO - '||p_mensaje;
                                        RAISE ve_Fuente;
                                    END IF;

                             FOR v_grin2vaca IN c_grin2vaca (v_recoleccion2.futi_id,v_recoleccion2.uspe_id,v_recoleccion2.grin2_id) LOOP
                             
                                                IF v_grin2vaca.vape_id < 1 OR v_grin2vaca.vape_id > 900000000 THEN
                                                        --v_valor := NULL;
                                                        v_valor := v_grin2vaca.valor;
                                                ELSE
                                                        v_valor := NULL;
                                                        --v_valor := v_grin2vaca.valor;
                                                END IF;
                             
                                    PQ_SIPSA_APROBACIONES.pr_InsertarAPGRIN2_VACA ( p_futi_id                               =>   v_grin2vaca.futi_id,
                                                                                                                     p_uspe_id                            =>    v_grin2vaca.uspe_id,
                                                                                                                     p_apgrin2_id                            =>    v_grin2vaca.grin2_id,                                                                              
                                                                                                                     p_cara_id                              =>    v_grin2vaca.cara_id,
                                                                                                                     p_vape_id                              =>    v_grin2vaca.vape_id,
                                                                                                                     p_valor                                =>    v_valor,
                                                                                                                     p_usuario                             =>      v_usuario,  
                                                                                                                     p_codigo_error                     =>      p_codigo_error,
                                                                                                                     p_mensaje                           =>      p_mensaje);

                                    IF p_codigo_error > 0 THEN
                                        p_mensaje      := 'AL INSERTAR RECOLECCION DE SIPSA_APROBA_GRIN2_VACA EN INCORPORAR DMC SE PRESENTO - '||p_mensaje;
                                        RAISE ve_Fuente;
                                    END IF;
                              END LOOP ;                              

                            PQ_SIPSA_APROBACIONES.pr_InsertarAPENCI2 ( p_futi_id                                  =>   v_recoleccion2.futi_id,
                                                                                                     p_uspe_id                               =>    v_recoleccion2.uspe_id,
                                                                                                     p_arti_id                                 =>    v_recoleccion2.arti_id,
                                                                                                     p_grin2_id                           =>    v_recoleccion2.grin2_id,                                                                              
                                                                                                     p_prre_fecha_programada        =>    v_recoleccion2.prre_fecha_programada,
                                                                                                     p_arti_nombre                   =>    v_recoleccion2.enin2_arti_nombre,
                                                                                                     p_novedad                               =>    v_recoleccion2.rein2_novedad,
                                                                                                     p_ftes_capturadas                    =>    v_recoleccion2.enin2_ftes_capturadas,
                                                                                                     p_obse_id                               =>  v_recoleccion2.obse_id,
                                                                                                     p_observacion                         =>  v_recoleccion2.enin2_observacion,
                                                                                                     p_usuario                               =>      v_usuario,  
                                                                                                     p_codigo_error                       =>      p_codigo_error,
                                                                                                     p_mensaje                              =>      p_mensaje);

                        IF p_codigo_error > 0 THEN
                            p_mensaje      := 'AL INSERTAR RECOLECCION DE SIPSA_APROBA_ENCA_INSUMOS2 EN INCORPORAR DMC SE PRESENTO PRODUCTO NUEVO - '||p_mensaje;
                            RAISE ve_Fuente;
                        END IF; 
             
                                    PQ_SIPSA_APROBACIONES.pr_InsertarAPRECI2 (  p_futi_id                                   =>      v_recoleccion2.futi_id,
                                                                                                             p_uspe_id                                =>      v_recoleccion2.uspe_id,
                                                                                                             p_arti_id                                  =>      v_recoleccion2.arti_id,
                                                                                                             p_grin2_id                                =>      v_recoleccion2.grin2_id,                                                                             
                                                                                                             p_prre_fecha_programada        =>      v_recoleccion2.prre_fecha_programada,
                                                                                                             p_novedad_gral                        =>      v_recoleccion2.rein2_novedad_gral,
                                                                                                             p_id_informante                       =>      v_recoleccion2.rein2_id_informante,
                                                                                                             p_nom_informante                   =>      v_recoleccion2.rein2_nom_informante,
                                                                                                             p_tel_informante                      =>       v_recoleccion2.rein2_tel_informante,
                                                                                                             p_precio_recolectado                =>      v_recoleccion2.rein2_precio_recolectado,
                                                                                                             p_novedad                               =>      v_recoleccion2.rein2_novedad,
                                                                                                             p_obse_id                               =>      v_obse_id,
                                                                                                             p_rein2_observacion                =>      v_recoleccion2.rein2_observacion,
                                                                                                             p_fecha_recoleccion                 =>      v_recoleccion2.rein2_fecha_recoleccion,
                                                                                                             p_esta_id                              => 1,
                                                                                                             p_usuario                               =>      v_usuario,  
                                                                                                             p_codigo_error                        =>      p_codigo_error,
                                                                                                             p_mensaje                              =>       p_mensaje);
                                                                                                                                    
                                    IF p_codigo_error > 0 THEN
                                        p_mensaje      := 'AL INSERTAR RECOLECCION DE SIPSA_APROBA_RECO_INSUMOS2 EN INCORPORAR DMC SE PRESENTO PRODUCTO NUEVO - '||p_mensaje;
                                        RAISE ve_Fuente;
                                    END IF;                         

            ELSE
                    IF v_recoleccion2.rein2_novedad IN ('IA') OR v_recoleccion2.rein2_id_informante >= 900000000 THEN 
                            PQ_SIPSA_APROBACIONES.pr_InsertarAPENCI2 ( p_futi_id                                  =>   v_recoleccion2.futi_id,
                                                                                                     p_uspe_id                               =>    v_recoleccion2.uspe_id,
                                                                                                     p_arti_id                                 =>    v_recoleccion2.arti_id,
                                                                                                     p_grin2_id                           =>    v_recoleccion2.grin2_id,                                                                              
                                                                                                     p_prre_fecha_programada        =>    v_recoleccion2.prre_fecha_programada,
                                                                                                     p_arti_nombre                   =>    v_recoleccion2.enin2_arti_nombre,
                                                                                                     p_novedad                               =>    v_recoleccion2.rein2_novedad,
                                                                                                     p_ftes_capturadas                    =>    v_recoleccion2.enin2_ftes_capturadas,
                                                                                                     p_obse_id                               =>  v_recoleccion2.obse_id,
                                                                                                     p_observacion                         =>  v_recoleccion2.enin2_observacion,
                                                                                                     p_usuario                               =>      v_usuario,  
                                                                                                     p_codigo_error                       =>      p_codigo_error,
                                                                                                     p_mensaje                              =>      p_mensaje);
                                                                                                     

                            IF p_codigo_error > 0 THEN
                                p_mensaje      := 'AL INSERTAR RECOLECCION DE SIPSA_APROBA_ENCA_INSUMOS2 EN INCORPORAR DMC SE PRESENTO PRODUCTO ADICIONADO - '||p_mensaje;
                                RAISE ve_Fuente;
                            END IF;  
                            
                            PQ_SIPSA_APROBACIONES.pr_InsertarAPRECI2 (  p_futi_id                                   =>      v_recoleccion2.futi_id,
                                                                                                     p_uspe_id                                =>      v_recoleccion2.uspe_id,
                                                                                                     p_arti_id                                  =>      v_recoleccion2.arti_id,
                                                                                                     p_grin2_id                                =>      v_recoleccion2.grin2_id,                                                                             
                                                                                                     p_prre_fecha_programada        =>      v_recoleccion2.prre_fecha_programada,
                                                                                                     p_novedad_gral                        =>      v_recoleccion2.rein2_novedad_gral,
                                                                                                     p_id_informante                       =>      v_recoleccion2.rein2_id_informante,
                                                                                                     p_nom_informante                   =>      v_recoleccion2.rein2_nom_informante,
                                                                                                     p_tel_informante                      =>       v_recoleccion2.rein2_tel_informante,
                                                                                                     p_precio_recolectado                =>      v_recoleccion2.rein2_precio_recolectado,
                                                                                                     p_novedad                               =>      v_recoleccion2.rein2_novedad,
                                                                                                     p_obse_id                               =>      v_obse_id,
                                                                                                     p_rein2_observacion                =>      v_recoleccion2.rein2_observacion,
                                                                                                     p_fecha_recoleccion                 =>      v_recoleccion2.rein2_fecha_recoleccion,
                                                                                                     p_esta_id                              => 1,
                                                                                                     p_usuario                               =>      v_usuario,  
                                                                                                     p_codigo_error                        =>      p_codigo_error,
                                                                                                     p_mensaje                              =>       p_mensaje);
                                                                                                                                    
                                IF p_codigo_error > 0 THEN
                                    p_mensaje      := 'AL INSERTAR RECOLECCION DE SIPSA_APROBA_RECO_INSUMOS2 EN INCORPORAR DMC SE PRESENTO PRODUCTO ADICIONADO - '||p_mensaje;
                                    RAISE ve_Fuente;
                                END IF;            
                                             
                    ELSE                                                                                                                                 
                            --INSERTAMOS EN LA TABLA DEFINITIVA DE SIPSA_ENC_INSUMOS2 
                            PQ_SIPSA_ENCABEZADOS.pr_InsertarI2 ( p_futi_id                               =>   v_recoleccion2.futi_id,
                                                                                         p_uspe_id                            =>    v_recoleccion2.uspe_id,
                                                                                         p_grin2_id                            =>    v_recoleccion2.grin2_id,                                                                              
                                                                                         p_prre_fecha_programada     =>    v_recoleccion2.prre_fecha_programada,
                                                                                         p_precio_prom_anterior         =>    v_recoleccion2.enin2_precio_prom_anterior,
                                                                                         p_novedad                            =>     v_recoleccion2.rein2_novedad,
                                                                                         p_ftes_capturadas                 =>    v_recoleccion2.enin2_ftes_capturadas,
                                                                                         p_obse_id                             =>     v_obse_id,
                                                                                         p_observacion                      =>      v_recoleccion2.enin2_observacion,
                                                                                         p_usuario                             =>      v_usuario,  
                                                                                         p_codigo_error                     =>      p_codigo_error,
                                                                                         p_mensaje                           =>      p_mensaje);
                            IF p_codigo_error > 0 THEN
                                    p_mensaje      := 'AL INSERTAR ENCABEZADO DE INSUMOS2 EN INCORPORAR DMC SE PRESENTO PRODUCTO PROGRAMADO - '||p_mensaje;
                                    RAISE ve_Fuente;
                            END IF;
                            --INSERTAMOS EN LA TABLA DEFINITIVA DE SIPSA_RECO_INSUMOS2                                                            
                                    PQ_SIPSA_RECOLECCION.pr_InsertarI2 (  p_futi_id                                   =>      v_recoleccion2.futi_id,
                                                                                                 p_uspe_id                                =>      v_recoleccion2.uspe_id,
                                                                                                 p_grin2_id                                =>      v_recoleccion2.grin2_id,                                                                             
                                                                                                 p_prre_fecha_programada        =>      v_recoleccion2.prre_fecha_programada,
                                                                                                 p_novedad_gral                        =>      v_recoleccion2.rein2_novedad_gral,
                                                                                                 p_id_informante                       =>      v_recoleccion2.rein2_id_informante,
                                                                                                 p_nom_informante                   =>      v_recoleccion2.rein2_nom_informante,
                                                                                                 p_tel_informante                      =>       v_recoleccion2.rein2_tel_informante,
                                                                                                 p_precio_recolectado                =>      v_recoleccion2.rein2_precio_recolectado,
                                                                                                 p_novedad                               =>      v_recoleccion2.rein2_novedad,
                                                                                                 p_obse_id                               =>      v_obse_id,
                                                                                                 p_rein2_observacion                =>      v_recoleccion2.rein2_observacion,
                                                                                                 p_fecha_recoleccion                 =>      v_recoleccion2.rein2_fecha_recoleccion,
                                                                                                 p_esta_id                              =>      1,
                                                                                                 p_usuario                               =>      v_usuario,  
                                                                                                 p_codigo_error                        =>      p_codigo_error,
                                                                                                 p_mensaje                              =>       p_mensaje);
                                                                                                                                    
                                    IF p_codigo_error > 0 THEN
                                        p_mensaje      := 'AL INSERTAR RECOLECCION DE INSUMOS2 EN INCORPORAR DMC SE PRESENTO PRODUCTO PROGRAMADO - '||p_mensaje;
                                        RAISE ve_Fuente;
                                    END IF;                         
                    END IF;                   
            END IF;
        END LOOP;

        FOR i IN (  SELECT futiar2.futi_id, prre.prre_fecha_programada,COUNT(1) registros 
                        FROM SIPSA_FUENTE_ARTICULOS2 futiar2,SIPSA_FUENTES_USUARIO_PERFILES fuus, SIPSA_USUARIOS_PERFILES uspe, SIPSA_PROGRAMACION_RECOLECCION prre
                        WHERE futiar2.futiar2_estado=1
                        AND futiar2.futi_id = fuus.futi_id
                        AND futiar2.futi_id = prre.futi_id 
                        AND uspe.uspe_id = fuus.uspe_id
                        AND uspe.uspe_id = v_uspe_id
                        AND uspe.perf_id = 8
                        AND prre.prre_estado = 19
                        AND TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') <= ( SELECT MAX(prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1
                                                                                                                    WHERE prre1.futi_id = futiar2.futi_id
                                                                                                                    AND     prre1.prre_estado = 19
                                                                                                                    --AND TO_DATE(SYSDATE, 'dd-mm-yy') <= TO_DATE(prre1.prre_fecha_programada, 'dd-mm-yy')
                                                                                                            ) 
                        GROUP BY futiar2.futi_id, prre.prre_fecha_programada
                        ORDER BY  futiar2.futi_id, prre.prre_fecha_programada
        ) LOOP
      
            BEGIN
                SELECT COUNT(1) INTO v_cantidad
                FROM SIPSA_ENCA_INSUMOS2 enin2, SIPSA_PROGRAMACION_RECOLECCION prre
                WHERE enin2.futi_id = prre.futi_id
                AND prre.prre_fecha_programada = enin2.prre_fecha_programada
                AND enin2.futi_id = i.futi_id
                AND enin2.uspe_id = v_uspe_id
                AND prre.prre_fecha_programada = i.prre_fecha_programada
                GROUP BY enin2.futi_id, prre.prre_fecha_programada
                ORDER BY enin2.futi_id, prre.prre_fecha_programada;
            EXCEPTION WHEN OTHERS THEN NULL;
            END;

        
            IF i.registros <> v_cantidad THEN
                PQ_SIPSA_PROG_RECOLECCION.pr_Actualizar (    p_futi_id                   =>      i.futi_id,                                                                         
                                                                                         p_prre_fecha            =>      i.prre_fecha_programada,
                                                                                         p_prre_estado          =>      19,
                                                                                         p_usuario                 =>      v_usuario,  
                                                                                         p_codigo_error         =>      p_codigo_error,
                                                                                         p_mensaje               =>       p_mensaje);
            ELSE 
                PQ_SIPSA_PROG_RECOLECCION.pr_Actualizar (    p_futi_id                   =>      i.futi_id,                                                                         
                                                                                         p_prre_fecha            =>      i.prre_fecha_programada,
                                                                                         p_prre_estado          =>      20,
                                                                                         p_usuario                 =>      v_usuario,  
                                                                                         p_codigo_error         =>      p_codigo_error,
                                                                                         p_mensaje               =>       p_mensaje);
             END IF;

        END LOOP;
       /* BEGIN
                SELECT prre.prre_fecha_programada,COUNT(1) INTO v_fecha_programada,v_registros 
                FROM SIPSA_FUENTE_ARTICULOS2 futiar2,SIPSA_FUENTES_USUARIO_PERFILES fuus, SIPSA_USUARIOS_PERFILES uspe, SIPSA_PROGRAMACION_RECOLECCION prre
                WHERE futiar2.futiar2_estado=1
                AND futiar2.futi_id = fuus.futi_id
                AND futiar2.futi_id = prre.futi_id 
                AND uspe.uspe_id = fuus.uspe_id
                AND fuus.futi_id = v_futi_id
                AND fuus.uspe_id = v_uspe_id
                AND uspe.perf_id = 8
                AND prre.prre_estado = 19
                AND TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') <= ( SELECT MAX(prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1
                                                        WHERE prre1.futi_id = futiar2.futi_id
                                                        AND     prre1.prre_estado = 19
                                                        --AND TO_DATE(SYSDATE, 'dd-mm-yy') <= TO_DATE(prre1.prre_fecha_programada, 'dd-mm-yy')
                                                        ) 
                GROUP BY futiar2.futi_id, prre.prre_fecha_programada
                ORDER BY  futiar2.futi_id,prre.prre_fecha_programada
                ;

                
                SELECT COUNT(1) INTO v_cantidad
                FROM SIPSA_ENCA_INSUMOS2 enin2, SIPSA_PROGRAMACION_RECOLECCION prre
                WHERE enin2.futi_id = prre.futi_id
                AND prre.prre_fecha_programada = enin2.prre_fecha_programada
                AND enin2.futi_id = v_futi_id
                AND enin2.uspe_id = v_uspe_id
                AND prre.prre_fecha_programada = v_fecha_programada
                GROUP BY enin2.futi_id, prre.prre_fecha_programada
                ORDER BY enin2.futi_id, prre.prre_fecha_programada;
            EXCEPTION WHEN OTHERS THEN NULL;
        END;
        
        IF (v_registros = v_cantidad ) THEN
           PQ_SIPSA_PROG_RECOLECCION.pr_Actualizar (    p_futi_id       =>  v_futi_id,                                                                         
                                                        p_prre_fecha    =>  v_fecha_programada,
                                                        p_prre_estado   =>  20,
                                                        p_usuario       =>  v_usuario,  
                                                        p_codigo_error  =>  p_codigo_error,
                                                        p_mensaje       =>  p_mensaje);               
        END IF;   */ 

        --*********************
        SELECT SYS_CONTEXT('USERENV','SID') INTO v_idsession FROM DUAL;      ---PREGUNTAR SI VA ACA         
        --*********************        
        
      DELETE FROM SIPSA_TMP_GRIN2_VACA;
      DELETE FROM SIPSA_TMP_ENCA_INSUMOS2;
      DELETE FROM SIPSA_TMP_RECO_INSUMOS2;
     EXCEPTION
          WHEN OTHERS THEN
                 ROLLBACK;
                 DELETE FROM SIPSA_TMP_GRIN2_VACA;
                 DELETE FROM SIPSA_TMP_ENCA_INSUMOS2;
                 DELETE FROM SIPSA_TMP_RECO_INSUMOS2;
               p_codigo_error := 1;
               p_mensaje := p_mensaje||SQLERRM;

          
END pr_IncorporarDMCI2;


PROCEDURE pr_IncorporarDMCI2_DISTRITO
 /********************************************************************************
 Descripcion   : metodo para incorporar la informacion obtenida en la DMC en una
                 Recoleccion de Insumos de Distrito

 Realizado por : Marco Guzman
 Fecha Creacion: 25/06/2019
*******************************************************************************/
 (p_usuario                   IN SIPSA_RECOLECCION_INSUMOS_D.REIN_USUARIO_MODIFICACION%TYPE,
  p_codigo_error              OUT NUMBER,
  p_mensaje                   OUT VARCHAR2) IS

CURSOR c_recoleccion2 IS
SELECT  tmenin2.futi_id,tmenin2.uspe_id,tmenin2.arti_id,tmenin2.grin_id,tmenin2.ENIN_FECHA_PROGRAMADA,tmenin2.enin_arti_nombre,
             tmenin2.enin_precio_prom_anterior,tmenin2.enin_novedad,tmenin2.obse_id,tmenin2.enin_observacion,
             tmrein2.rein_id_informante,tmrein2.rein_nom_informante,tmrein2.rein_tel_informante,tmrein2.rein_precio_recolectado, tmrein2.rein_cod_informante,
             tmrein2.rein_novedad,tmrein2.obse_id AS obse_id_reco,tmrein2.rein_observacion,tmrein2.rein_fecha_recoleccion,
             tmrein2.rein_tipo,  tmrein2.rein_frecuencia, tmrein2.REIN_OBSERVACION_PRODUCTO,  tmrein2.REIN_UNIDAD_MEDIDA_OTRO,
             tmrein2.REIN_UNIDAD_ID,  tmrein2.REIN_UNIDAD_MEDIDA,
             tmenin2.ENIN_NOMBRE_FUENTE, tmenin2.ENIN_MUNICIPIO, tmenin2.ENIN_MUNI_ID, tmenin2.ENIN_DIRECCION, tmenin2.ENIN_TELEFONO,
             tmenin2.ENIN_EMAIL, tmenin2.ENIN_INFORMANTE, tmenin2.ENIN_TEL_INFORMANTE
FROM SIPSA_TMP_ENCA_INSUMOS_D tmenin2, SIPSA_TMP_RECO_INSUMOS_D tmrein2
WHERE tmenin2.futi_id = tmrein2.futi_id 
AND tmenin2.uspe_id =tmrein2.uspe_id
AND tmenin2.arti_id = tmrein2.arti_id
AND tmenin2.grin_id = tmrein2.grin_id
AND tmenin2.ENIN_FECHA_PROGRAMADA = tmrein2.ENIN_FECHA_PROGRAMADA
AND 0 =  ( SELECT COUNT (1) FROM SIPSA_ENCA_INSUMOS_D enin2, SIPSA_RECOLECCION_INSUMOS_D rein2
                 WHERE enin2.futi_id = rein2.futi_id
                 AND enin2.uspe_id =rein2.uspe_id
                 AND enin2.grin_id = rein2.grin_id
                 AND enin2.ENIN_FECHA_PROGRAMADA = rein2.REIN_FECHA_PROGRAMADA
                 AND enin2.futi_id = tmenin2.futi_id
                 AND enin2.uspe_id =tmenin2.uspe_id
                 AND enin2.grin_id = tmenin2.grin_id
                 AND enin2.ENIN_FECHA_PROGRAMADA = tmenin2.ENIN_FECHA_PROGRAMADA
                 AND rein2.REIN_PRECIO_RECOLECTADO = tmrein2.rein_precio_recolectado)
AND 0 =  ( SELECT COUNT (1) FROM SIPSA_APROBA_ENCA_INSUMOS_D apenin2, SIPSA_APROBA_RECO_INSUMOS_D aprein2
                 WHERE apenin2.futi_id = aprein2.futi_id
                 AND apenin2.uspe_id = aprein2.uspe_id
                 AND apenin2.arti_id = aprein2.arti_id
                 AND apenin2.grin_id = aprein2.grin_id
                 AND apenin2.ENIN_FECHA_PROGRAMADA = aprein2.APRE_FECHA_PROGRAMADA
                 AND apenin2.futi_id = tmenin2.futi_id
                 AND apenin2.uspe_id =tmenin2.uspe_id
                 AND apenin2.grin_id = tmenin2.grin_id
                 AND apenin2.ENIN_FECHA_PROGRAMADA = tmenin2.ENIN_FECHA_PROGRAMADA
                 AND aprein2.APRE_id_informante = tmrein2.rein_id_informante
                 AND aprein2.APRE_PRECIO_RECOLECTADO = tmrein2.rein_precio_recolectado)                  
ORDER BY 1,2,3,4,5,11;                  

v_uspe_id     NUMBER;
v_futi_id     NUMBER;
v_idsession   NUMBER;
--c_fuar NUMBER;--Constante que me indica que la FUENTE y el ARTICULO SON NUEVOS.
v_obse_id              SIPSA_OBSERVACIONES.obse_id%TYPE;
v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
v_cantidad NUMBER:=0;
v_registros   NUMBER:=0;
v_fecha_programada DATE;
v_futi_id1    NUMBER;

      
ve_uspe_id    EXCEPTION;
ve_futi_id    EXCEPTION;
ve_Fuente     EXCEPTION;  


BEGIN

    p_codigo_error := 0;
    p_mensaje      := NULL;

    v_uspe_id:= PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario));
    v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));

      
    IF v_uspe_id IS NULL THEN  
         p_mensaje      := 'NO HAY UN PERFIL ASOCIADO AL USUARIO'||SQLERRM;
         RAISE ve_uspe_id;          
    END IF;

    v_futi_id:= PQ_SIPSA_FUEN_USUA_PERF.Fn_ObtenerId(v_uspe_id);
   
    IF v_futi_id IS NULL THEN   
        p_mensaje      := 'NO HAY UNA FUENTE ASOCIADA AL USUARIO'||SQLERRM;
        RAISE ve_futi_id;          
    END IF;

        --INSERTAMOS EN LA TABLA DEFINITIVA DE SIPSA_ENCA_INSUMOS2
        FOR v_recoleccion2 IN c_recoleccion2 LOOP

            IF v_recoleccion2.obse_id < 1 OR v_recoleccion2.obse_id > 900000000 THEN
                    v_obse_id := NULL;
            ELSE
                    v_obse_id := v_recoleccion2.obse_id;
            END IF;


            IF v_recoleccion2.obse_id_reco < 1 OR v_recoleccion2.obse_id_reco > 900000000 THEN
                    v_obse_id := NULL;
            ELSE
                v_obse_id := v_recoleccion2.obse_id_reco;
            END IF;

            IF v_recoleccion2.rein_novedad IN ('IN') THEN
                          ---SIPSA_APROBA_ENCA_INSUMOS_D
                          PQ_SIPSA_APROBACIONES.pr_InsertarAPENIN_D ( p_futi_id               =>   v_recoleccion2.futi_id,
                                                                       p_uspe_id                               =>    v_recoleccion2.uspe_id,
                                                                       p_arti_id                                 =>    v_recoleccion2.arti_id,
                                                                       p_grin2_id                           =>    v_recoleccion2.grin_id,                                                                              
                                                                       p_prre_fecha_programada        =>    v_recoleccion2.ENIN_FECHA_PROGRAMADA,
                                                                       p_arti_nombre                   =>    v_recoleccion2.enin_arti_nombre,
                                                                       p_novedad                               =>    v_recoleccion2.rein_novedad,
                                                                       p_obse_id                               =>  v_recoleccion2.obse_id,
                                                                       p_observacion                         =>  v_recoleccion2.enin_observacion,
                                                                       p_usuario                               =>      v_usuario,  
                     
                                                                       p_nom_fuente                      =>      v_recoleccion2.ENIN_NOMBRE_FUENTE,
                                                                       p_municipio                      =>      v_recoleccion2.ENIN_MUNICIPIO,
                                                                       p_muni_id                      =>      v_recoleccion2.ENIN_MUNI_ID,
                                                                       p_direccion                      =>      v_recoleccion2.ENIN_DIRECCION,
                                                                       p_telefono                      =>      v_recoleccion2.ENIN_TELEFONO,
                                                                       p_email                      =>      v_recoleccion2.ENIN_EMAIL,
                                                                       p_informante                      =>      v_recoleccion2.ENIN_INFORMANTE,
                                                                       p_tel_informante                      =>      v_recoleccion2.ENIN_TEL_INFORMANTE,
                     
                                                                       p_codigo_error                       =>      p_codigo_error,
                                                                       p_mensaje                              =>      p_mensaje);

                          IF p_codigo_error > 0 THEN
                              p_mensaje      := 'AL INSERTAR RECOLECCION DE SIPSA_APROBA_ENCA_INSUMOS_D EN INCORPORAR DMC SE PRESENTO PRODUCTO NUEVO - '||p_mensaje;
                              RAISE ve_Fuente;
                          END IF; 
                          ---SIPSA_APROBA_RECO_INSUMOS_D
                          PQ_SIPSA_APROBACIONES.pr_InsertarAPRECI2_D (  p_futi_id                                   =>      v_recoleccion2.futi_id,
                                             p_uspe_id                                =>      v_recoleccion2.uspe_id,
                                             p_arti_id                                  =>      v_recoleccion2.arti_id,
                                             p_grin2_id                                =>      v_recoleccion2.grin_id,                                                                             
                                             p_prre_fecha_programada        =>      v_recoleccion2.ENIN_FECHA_PROGRAMADA,
                                             p_id_informante                       =>      v_recoleccion2.rein_id_informante,
                                             p_nom_informante                   =>      v_recoleccion2.rein_nom_informante,
                                             p_tel_informante                      =>       v_recoleccion2.rein_tel_informante,
                                             p_precio_recolectado                =>      v_recoleccion2.rein_precio_recolectado,
                                             p_novedad                               =>      v_recoleccion2.rein_novedad,
                                             p_obse_id                               =>      v_obse_id,
                                             p_rein2_observacion                =>      v_recoleccion2.rein_observacion,
                                             p_fecha_recoleccion                 =>      v_recoleccion2.rein_fecha_recoleccion,
                                             p_esta_id                              => 1,
                                             p_usuario                               =>      v_usuario,  
                                             
                                             p_nom_fuente                      =>      v_recoleccion2.ENIN_NOMBRE_FUENTE,
                                             p_municipio                      =>      v_recoleccion2.ENIN_MUNICIPIO,
                                             p_muni_id                      =>      v_recoleccion2.ENIN_MUNI_ID,
                                             p_direccion                      =>      v_recoleccion2.ENIN_DIRECCION,
                                             p_telefono                      =>      v_recoleccion2.ENIN_TELEFONO,
                                             p_email                      =>      v_recoleccion2.ENIN_EMAIL,
                                             p_informante                      =>      v_recoleccion2.ENIN_INFORMANTE,
                                             
                                             p_codigo_error                        =>      p_codigo_error,
                                             p_mensaje                              =>       p_mensaje);
                                                                                                                                    
                            IF p_codigo_error > 0 THEN
                                p_mensaje      := 'AL INSERTAR RECOLECCION DE SIPSA_APROBA_RECO_INSUMOS_D EN INCORPORAR DMC SE PRESENTO PRODUCTO NUEVO - '||p_mensaje;
                                RAISE ve_Fuente;
                            END IF;                         

            ELSE
                    IF v_recoleccion2.rein_novedad IN ('IA') OR v_recoleccion2.futi_id >= 900000000 THEN 
                            --SIPSA_APROBA_ENCA_INSUMOS_D
                            PQ_SIPSA_APROBACIONES.pr_InsertarAPENIN_D ( p_futi_id                                  =>   v_recoleccion2.futi_id,
                                                                                                     p_uspe_id                               =>    v_recoleccion2.uspe_id,
                                                                                                     p_arti_id                                 =>    v_recoleccion2.arti_id,
                                                                                                     p_grin2_id                           =>    v_recoleccion2.grin_id,                                                                              
                                                                                                     p_prre_fecha_programada        =>    v_recoleccion2.ENIN_FECHA_PROGRAMADA,
                                                                                                     p_arti_nombre                   =>    v_recoleccion2.enin_arti_nombre,
                                                                                                     p_novedad                               =>    v_recoleccion2.rein_novedad,
                                                                                                     p_obse_id                               =>  v_recoleccion2.obse_id,
                                                                                                     p_observacion                         =>  v_recoleccion2.enin_observacion,
                                                                                                     p_usuario                               =>      v_usuario,  
																									 
                                                                                                     p_nom_fuente                      =>      v_recoleccion2.ENIN_NOMBRE_FUENTE,
                                                                                                     p_municipio                      =>      v_recoleccion2.ENIN_MUNICIPIO,
                                                                                                     p_muni_id                      =>      v_recoleccion2.ENIN_MUNI_ID,
                                                                                                     p_direccion                      =>      v_recoleccion2.ENIN_DIRECCION,
                                                                                                     p_telefono                      =>      v_recoleccion2.ENIN_TELEFONO,
                                                                                                     p_email                      =>      v_recoleccion2.ENIN_EMAIL,
                                                                                                     p_informante                      =>      v_recoleccion2.ENIN_INFORMANTE,
                                                                                                     p_tel_informante                      =>      v_recoleccion2.ENIN_TEL_INFORMANTE,
																									 
                                                                                                     p_codigo_error                       =>      p_codigo_error,
                                                                                                     p_mensaje                              =>      p_mensaje);
                                                                                                     

                            IF p_codigo_error > 0 THEN
                                p_mensaje      := 'AL INSERTAR RECOLECCION DE SIPSA_APROBA_ENCA_INSUMOS_D EN INCORPORAR DMC SE PRESENTO PRODUCTO ADICIONADO - '||p_mensaje;
                                RAISE ve_Fuente;
                            END IF;  
                            --SIPSA_APROBA_RECO_INSUMOS_D
                            PQ_SIPSA_APROBACIONES.pr_InsertarAPRECI2_D (  p_futi_id                                   =>      v_recoleccion2.futi_id,
                                                                                                     p_uspe_id                                =>      v_recoleccion2.uspe_id,
                                                                                                     p_arti_id                                  =>      v_recoleccion2.arti_id,
                                                                                                     p_grin2_id                                =>      v_recoleccion2.grin_id,                                                                             
                                                                                                     p_prre_fecha_programada        =>      v_recoleccion2.ENIN_FECHA_PROGRAMADA,
                                                                                                     p_id_informante                       =>      v_recoleccion2.rein_id_informante,
                                                                                                     p_nom_informante                   =>      v_recoleccion2.rein_nom_informante,
                                                                                                     p_tel_informante                      =>       v_recoleccion2.rein_tel_informante,
                                                                                                     p_precio_recolectado                =>      v_recoleccion2.rein_precio_recolectado,
                                                                                                     p_novedad                               =>      v_recoleccion2.rein_novedad,
                                                                                                     p_obse_id                               =>      v_obse_id,
                                                                                                     p_rein2_observacion                =>      v_recoleccion2.rein_observacion,
                                                                                                     p_fecha_recoleccion                 =>      v_recoleccion2.rein_fecha_recoleccion,
                                                                                                     p_esta_id                              => 1,
                                                                                                     p_usuario                               =>      v_usuario,

                                                                                                     p_nom_fuente                      =>      v_recoleccion2.ENIN_NOMBRE_FUENTE,
                                                                                                     p_municipio                      =>      v_recoleccion2.ENIN_MUNICIPIO,
                                                                                                     p_muni_id                      =>      v_recoleccion2.ENIN_MUNI_ID,
                                                                                                     p_direccion                      =>      v_recoleccion2.ENIN_DIRECCION,
                                                                                                     p_telefono                      =>      v_recoleccion2.ENIN_TELEFONO,
                                                                                                     p_email                      =>      v_recoleccion2.ENIN_EMAIL,
                                                                                                     p_informante                      =>      v_recoleccion2.ENIN_INFORMANTE,
																									 
                                                                                                     p_codigo_error                        =>      p_codigo_error,
                                                                                                     p_mensaje                              =>       p_mensaje);
                                                                                                                                    
                                IF p_codigo_error > 0 THEN
                                    p_mensaje      := 'AL INSERTAR RECOLECCION DE SIPSA_APROBA_RECO_INSUMOS_D EN INCORPORAR DMC SE PRESENTO PRODUCTO ADICIONADO - '||p_mensaje;
                                    RAISE ve_Fuente;
                                END IF;            
                                             
                    ELSE                                                                                                                                 
                            --INSERTAMOS EN LA TABLA DEFINITIVA DE SIPSA_ENC_INSUMOS2 
                              PQ_SIPSA_ENCABEZADOS.pr_InsertarI2_D ( p_futi_id                    =>   v_recoleccion2.futi_id,
                                                     p_uspe_id                            =>    v_recoleccion2.uspe_id,
                                                     p_grin2_id                            =>    v_recoleccion2.grin_id,                                                                              
                                                     p_prre_fecha_programada              =>    v_recoleccion2.ENIN_FECHA_PROGRAMADA,
                                                     p_precio_prom_anterior               =>    v_recoleccion2.enin_precio_prom_anterior,
                                                     p_novedad                            =>     v_recoleccion2.rein_novedad,
                                                     p_obse_id                             =>     v_obse_id,
                                                     p_observacion                      =>      v_recoleccion2.enin_observacion,
                                                     p_arti_id                             =>   v_recoleccion2.arti_id,
                                                     p_arti_nombre                      =>      v_recoleccion2.enin_arti_nombre,
                                                     p_nom_fuente                      =>      v_recoleccion2.ENIN_NOMBRE_FUENTE,
                                                     p_municipio                      =>      v_recoleccion2.ENIN_MUNICIPIO,
                                                     p_muni_id                      =>      v_recoleccion2.ENIN_MUNI_ID,
                                                     p_direccion                      =>      v_recoleccion2.ENIN_DIRECCION,
                                                     p_telefono                      =>      v_recoleccion2.ENIN_TELEFONO,
                                                     p_email                      =>      v_recoleccion2.ENIN_EMAIL,
                                                     p_informante                      =>      v_recoleccion2.ENIN_INFORMANTE,
                                                     p_tel_informante                      =>      v_recoleccion2.ENIN_TEL_INFORMANTE,
                                                     p_usuario                             =>      v_usuario,  
                                                     p_codigo_error                     =>      p_codigo_error,
                                                     p_mensaje                           =>      p_mensaje);
                              IF p_codigo_error > 0 THEN
                                p_mensaje      := 'AL INSERTAR ENCABEZADO DE INSUMOS D EN INCORPORAR DMC SE PRESENTO PRODUCTO PROGRAMADO - '||p_mensaje;
                                RAISE ve_Fuente;
                              END IF;
                              --INSERTAMOS EN LA TABLA DEFINITIVA DE SIPSA_RECO_INSUMOS2                                                            
                              PQ_SIPSA_RECOLECCION.pr_InsertarI2_D (    p_futi_id                   =>      v_recoleccion2.futi_id,
                                                       p_uspe_id                    =>      v_recoleccion2.uspe_id,
                                                       p_grin2_id                   =>      v_recoleccion2.grin_id,                                                                             
                                                       p_prre_fecha_programada      =>      v_recoleccion2.ENIN_FECHA_PROGRAMADA,
                                                       p_id_informante              =>      v_recoleccion2.rein_id_informante,
                                                       p_nom_informante             =>      v_recoleccion2.ENIN_INFORMANTE,
                                                       p_tel_informante             =>       v_recoleccion2.rein_tel_informante,
                                                       p_precio_recolectado         =>      v_recoleccion2.rein_precio_recolectado,
                                                       p_novedad                    =>      v_recoleccion2.rein_novedad,
                                                       p_obse_id                    =>      v_obse_id,
                                                       p_rein2_observacion          =>      v_recoleccion2.rein_observacion,
                                                       p_fecha_recoleccion          =>      v_recoleccion2.rein_fecha_recoleccion,
                                                       p_esta_id                    =>      1,
                                                       p_usuario                    =>      v_usuario,  
                                                       p_tipo                       =>      v_recoleccion2.rein_tipo,
                                                       p_frecuencia                 =>      v_recoleccion2.rein_frecuencia,
                                                       p_observacion_producto       =>      v_recoleccion2.REIN_OBSERVACION_PRODUCTO,
                                                       p_unidad_medida_otro         =>      v_recoleccion2.REIN_UNIDAD_MEDIDA_OTRO,
                                                       p_unidad_id                  =>      v_recoleccion2.REIN_UNIDAD_ID,
                                                       p_unidad_medida              =>      v_recoleccion2.REIN_UNIDAD_MEDIDA,
                                                       p_arti_id                             =>   v_recoleccion2.arti_id,
                                                       p_arti_nombre                      =>      v_recoleccion2.enin_arti_nombre,
                                                       p_municipio                      =>      v_recoleccion2.ENIN_MUNICIPIO,
                                                       p_muni_id                      =>      v_recoleccion2.ENIN_MUNI_ID,
                                                       p_codigo_error               =>      p_codigo_error,
                                                       p_mensaje                    =>       p_mensaje);
																				
							IF p_codigo_error > 0 THEN
							  p_mensaje      := 'AL INSERTAR RECOLECCION DE INSUMOS D EN INCORPORAR DMC SE PRESENTO PRODUCTO PROGRAMADO - '||p_mensaje;
							  RAISE ve_Fuente;
							END IF;                          
                    END IF;                   
            END IF;
        END LOOP;

        FOR i IN (  SELECT futiar2.futi_id, prre.prre_fecha_programada,COUNT(1) registros 
                        FROM SIPSA_FUENTE_ARTICULOS2 futiar2,SIPSA_FUENTES_USUARIO_PERFILES fuus, SIPSA_USUARIOS_PERFILES uspe, SIPSA_PROGRAMACION_RECOLECCION prre
                        WHERE futiar2.futiar2_estado=1
                        AND futiar2.futi_id = fuus.futi_id
                        AND futiar2.futi_id = prre.futi_id 
                        AND uspe.uspe_id = fuus.uspe_id
                        AND uspe.uspe_id = v_uspe_id
                        --AND uspe.perf_id = 8
                        AND prre.prre_estado = 19
                        AND TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') <= ( SELECT MAX(prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1
                                                                                                                    WHERE prre1.futi_id = futiar2.futi_id
                                                                                                                    AND     prre1.prre_estado = 19
                                                                                                                    --AND TO_DATE(SYSDATE, 'dd-mm-yy') <= TO_DATE(prre1.prre_fecha_programada, 'dd-mm-yy')
                                                                                                            ) 
                        GROUP BY futiar2.futi_id, prre.prre_fecha_programada
                        ORDER BY  futiar2.futi_id, prre.prre_fecha_programada
        ) LOOP
      
            BEGIN
                SELECT COUNT(1) INTO v_cantidad
                FROM SIPSA_ENCA_INSUMOS_D enin2, SIPSA_PROGRAMACION_RECOLECCION prre
                WHERE enin2.futi_id = prre.futi_id
                AND prre.prre_fecha_programada = enin2.ENIN_FECHA_PROGRAMADA
                AND enin2.futi_id = i.futi_id
                AND enin2.uspe_id = v_uspe_id
                AND prre.prre_fecha_programada = i.prre_fecha_programada
                GROUP BY enin2.futi_id, prre.prre_fecha_programada
                ORDER BY enin2.futi_id, prre.prre_fecha_programada;
            EXCEPTION WHEN OTHERS THEN NULL;
            END;

        
            --IF i.registros <> v_cantidad THEN -- Se tiene en pruebas 08/06/2018
            IF v_cantidad < i.registros  THEN
                PQ_SIPSA_PROG_RECOLECCION.pr_Actualizar (    p_futi_id                   =>      i.futi_id,                                                                         
                                                                                         p_prre_fecha            =>      i.prre_fecha_programada,
                                                                                         p_prre_estado          =>      19,
                                                                                         p_usuario                 =>      v_usuario,  
                                                                                         p_codigo_error         =>      p_codigo_error,
                                                                                         p_mensaje               =>       p_mensaje);
            ELSE 
                PQ_SIPSA_PROG_RECOLECCION.pr_Actualizar (    p_futi_id                   =>      i.futi_id,                                                                         
                                                                                         p_prre_fecha            =>      i.prre_fecha_programada,
                                                                                         p_prre_estado          =>      20,
                                                                                         p_usuario                 =>      v_usuario,  
                                                                                         p_codigo_error         =>      p_codigo_error,
                                                                                         p_mensaje               =>       p_mensaje);
             END IF;

        END LOOP;
       
        --*********************
        SELECT SYS_CONTEXT('USERENV','SID') INTO v_idsession FROM DUAL;      ---PREGUNTAR SI VA ACA         
        --*********************        
        
      DELETE FROM SIPSA_TMP_ENCA_INSUMOS_D;
      DELETE FROM SIPSA_TMP_RECO_INSUMOS_D;
     EXCEPTION
          WHEN OTHERS THEN
                 ROLLBACK;
                 DELETE FROM SIPSA_TMP_ENCA_INSUMOS_D;
                 DELETE FROM SIPSA_TMP_RECO_INSUMOS_D;
               p_codigo_error := 1;
               p_mensaje := p_mensaje||SQLERRM;

          
END pr_IncorporarDMCI2_DISTRITO;

FUNCTION fn_ObtenerDescripcionProducto (p_grin2_id IN NUMBER)
/********************************************************************************
 DESCRIPCION   : Metodo para obtener completa la descripcion de un producto a traves de sus caracteristicas

 PARAMETROS    :
 IN            : p_grin2_id         Valor del identificador del  grupo insumo
                 
 OUT           : v_descripcion    Retorna el nombre del elemento

 REALIZADO POR : Vitaliano Corredor E
 FECHA CREACION: 05/04/2013

 MODIFICADO POR:
 FECHA MODIFICA:
 CAMBIOS       :
*******************************************************************************/
  RETURN VARCHAR2 IS

 CURSOR c_desc_producto IS
    SELECT vape_descripcion  
        FROM sipsa_vape_cara v, sipsa_grin2_vaca p, sipsa_caracteristicas c
   WHERE v.cara_id = p.cara_id
   AND v.vape_id = p.vape_id
   AND v.cara_id = c.cara_id
   AND p.grin2_id = p_grin2_id
   ORDER BY c.cara_orden;

 v_descripcion VARCHAR2(2000);
 v VARCHAR2(1);

BEGIN
    v:='';
    FOR x in c_desc_producto LOOP
        v_descripcion:=v_descripcion||v||x.vape_descripcion;
        v:='-';
    END LOOP;

    RETURN v_descripcion;

END fn_ObtenerDescripcionProducto;
FUNCTION fn_ObtenerDescripcionProducto1(p_grin2_id IN NUMBER,p_tire_id IN NUMBER, p_articulo IN VARCHAR2)
/********************************************************************************
 DESCRIPCION   : Metodo para obtener completa la descripcion de un producto a traves de sus caracteristicas

 PARAMETROS    :
 IN            : p_grin2_id         Valor del identificador del  grupo insumo
                 
 OUT           : v_descripcion    Retorna el nombre del elemento

 REALIZADO POR : Vitaliano Corredor E
 FECHA CREACION: 05/04/2013

 MODIFICADO POR:
 FECHA MODIFICA:
 CAMBIOS       :
*******************************************************************************/
  RETURN VARCHAR2 IS

 CURSOR c_desc_producto IS
    SELECT vape_descripcion , c.cara_orden 
        FROM sipsa_vape_cara v, sipsa_grin2_vaca p, sipsa_caracteristicas c
   WHERE v.cara_id = p.cara_id
   AND v.vape_id = p.vape_id
   AND v.cara_id = c.cara_id
   AND p.grin2_id = p_grin2_id
   ORDER BY c.cara_orden;

 v_descripcion VARCHAR2(2000);
 v VARCHAR2(1);

BEGIN
    v:='';
    FOR x in c_desc_producto LOOP
       IF p_tire_id = 9 AND x.cara_orden = 1 THEN
           V_DESCRIPCION := v_descripcion||v||SUBSTR(x.vape_descripcion,1,2)||p_articulo||' ';
      ELSE
        v_descripcion:=v_descripcion||v||x.vape_descripcion;
       END IF; 
        v:='-';
    END LOOP;

    RETURN v_descripcion;

END fn_ObtenerDescripcionProducto1;
PROCEDURE Pr_ActualizarPreEstaI2
/********************************************************************************
 Descripcion   : método para actualizar información sobre la tabla SIPSA_RECOLECCION_INSUMOS

 parametros    :
 in            : p_futi_id
                 p_uspe_id
                 p_prre_fecha_programada
                 p_arti_id
                 p_caco_id
                 p_unme_id
                
                p_usuario

 out     :     
                 p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 Realizado por : Carlos Alberto López N.
 Fecha Creacion: 16/05/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2)  IS


 
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   

   UPDATE SIPSA_RECOLECCION_INSUMOS2
   SET  rein2_precio_recolectado = p_precio_recolectado,
           esta_id = p_esta_id,
           rein2_novedad = p_novedad,
           obse_id = p_obse_id,
           rein2_observacion = p_observacion,
           rein2_usuario_modificacion=UPPER(p_usuario),
           rein2_fecha_modificacion = SYSDATE
    WHERE futi_id = p_futi_id
    --AND     uspe_id = p_uspe_id
    AND     grin2_id = p_grin2_id
    AND     rein2_id_informante= p_id_informante
    AND     prre_fecha_programada = p_prre_fecha_programada;
       
   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'no se encontro ningun registro para actualizar en la tabla SIPSA_RECOLECCION_INSUMOS2 con los identificadores dados ';
   END IF;

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al actualizar la tabla SIPSA_RECOLECCION_INSUMOS2 se presento '||SQLERRM;                                                                                   
END Pr_ActualizarPreEstaI2;

    PROCEDURE Pr_ActualizarPreEstaI
/********************************************************************************
 Descripcion   : metodo para actualizar informacion sobre la tabla SIPSA_RECOLECCION_INSUMOS

 parametros    :
 in            : p_futi_id
                 p_uspe_id
                 p_prre_fecha_programada
                 p_arti_id
                 p_caco_id
                 p_unme_id
                
                p_usuario

 out     :     
                 p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 Realizado por : Carlos Alberto Lopez N.
 Fecha Creacion: 16/05/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2)  IS



fecha_progr_rein_siguiente  DATE;
rein_siguiente_count        NUMBER;
 
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   fecha_progr_rein_siguiente :=null;

   UPDATE SIPSA_RECOLECCION_INSUMOS
   SET  Rein_Precio_Recolectado = p_precio_recolectado,
           esta_id = p_esta_id,
           rein_novedad = p_novedad,
           obse_id = p_obse_id,
           rein_observacion = p_observacion,
           Rein_Usuario_Modificacion=UPPER(p_usuario),
           rein_fecha_modificacion = SYSDATE
    WHERE futi_id = p_futi_id
   -- AND     uspe_id = p_uspe_id
    AND     unme_id = p_unme_id
    AND     articaco_id= p_articaco_id
    AND     prre_fecha_programada = p_prre_fecha_programada;
    
    DBMS_OUTPUT.PUT(fecha_progr_rein_siguiente);
       
    -- se consulta si existe un rein posterior al que se acaba de modificar
    select count(*)  into rein_siguiente_count
     from(select  rein.PRRE_FECHA_PROGRAMADA
    from SIPSA_RECOLECCION_INSUMOS rein
    where 1=1
    and   futi_id = p_futi_id
    AND     uspe_id = p_uspe_id
    AND     unme_id = p_unme_id
    AND     articaco_id= p_articaco_id
    and     trunc(rein.prre_fecha_programada) > trunc(p_prre_fecha_programada)
   order by rein.PRRE_FECHA_PROGRAMADA 
   ) consulta
   where rownum =1;
   
   IF (rein_siguiente_count >0) THEN
      select consulta.*  into fecha_progr_rein_siguiente
      from(select  rein.PRRE_FECHA_PROGRAMADA
      from SIPSA_RECOLECCION_INSUMOS rein
      where 1=1
      and   futi_id = p_futi_id
      AND     uspe_id = p_uspe_id
      AND     unme_id = p_unme_id
      AND     articaco_id= p_articaco_id
      and     trunc(rein.prre_fecha_programada) > trunc(p_prre_fecha_programada)
      order by rein.PRRE_FECHA_PROGRAMADA 
      ) consulta
      where rownum =1;
   
    DBMS_OUTPUT.PUT(fecha_progr_rein_siguiente);
   END IF;    
   
  -- si si esxite un rein posterior se actualiza el campo REIN_PRECIO_ANTERIOR
   if(fecha_progr_rein_siguiente  is not null) then
      UPDATE SIPSA_RECOLECCION_INSUMOS
       SET  REIN_PRECIO_ANTERIOR=p_precio_recolectado,
             Rein_Usuario_Modificacion=UPPER(p_usuario),
             rein_fecha_modificacion = SYSDATE
      WHERE futi_id = p_futi_id
      AND     uspe_id = p_uspe_id
      AND     unme_id = p_unme_id
      AND     articaco_id= p_articaco_id
      AND    prre_fecha_programada = fecha_progr_rein_siguiente;
   
     END IF;
   
   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'no se encontro ningun registro para actualizar en la tabla SIPSA_RECOLECCION_INSUMOS con los identificadores dados ';
   END IF;

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al actualizar la tabla SIPSA_RECOLECCION_INSUMOS se presento '||SQLERRM;                                                                                   
END Pr_ActualizarPreEstaI;

PROCEDURE pr_aprueba_rein
/********************************************************************************
 Descripcion   : metodo para aprobar masivamente SIPSA_RECOLECCION_INSUMOS

 parametros    :
 in            : p_futi_id
                 p_uspe_id
                 p_articaco_id
                 p_unme_id
                 p_prre_fecha_programada
                 p_usuario               

 out     :     
                 p_codigo_error      valor que indica la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 Realizado por : William Garnica
 Fecha Creacion: 22/02/2016

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
(p_futi_id                       IN VARCHAR2,
 p_uspe_id                       IN VARCHAR2,
 p_articaco_id                   IN VARCHAR2,
 p_unme_id                       IN VARCHAR2,
 p_prre_fecha_programada         IN VARCHAR2,
 p_usuario                       IN VARCHAR2,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2)  IS


consulta varchar2(2000);
fecha_progr_rein_siguiente  DATE;
 
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   fecha_progr_rein_siguiente :=null;

   consulta:= ' UPDATE SIPSA_RECOLECCION_INSUMOS
   SET     esta_id = 5,
           Rein_Usuario_Modificacion=UPPER('''||p_usuario||'''),
           rein_fecha_modificacion = '''||SYSDATE||'''
    WHERE futi_id in ( '||p_futi_id||')
    
    AND     unme_id in ( '||p_unme_id||')
    AND     esta_id != 5
    AND     articaco_id in ( '||p_articaco_id||')
    AND     to_number(to_char( prre_fecha_programada, ''yyyyMMdd'')) in ( '||p_prre_fecha_programada||')';  
   
   dbms_output.put_line (consulta);
   execute immediate consulta;
   
   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'no se encontro ningun registro para aprobar SIPSA_RECOLECCION_INSUMOS con los identificadores dados ';
   END IF;

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := ' error en pr_aprueba_rein se presento '||SQLERRM;                                                                                   
END pr_aprueba_rein;

PROCEDURE pr_aprueba_rein2
/********************************************************************************
 Descripcion   : metodo para aprobar masivamente SIPSA_RECOLECCION_INSUMOS2

 parametros    :
 in            : p_futi_id
                 p_uspe_id
                 p_grin2_id
                 p_rein_informante_id
                 p_prre_fecha_programada
                 p_usuario              

 out     :     
                 p_codigo_error      valor que indica la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 Realizado por : William Garnica
 Fecha Creacion: 22/02/2016

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
(p_futi_id                       IN VARCHAR2,
 p_uspe_id                       IN VARCHAR2,
 p_grin2_id                      IN VARCHAR2,
 p_rein_informante_id            IN VARCHAR2,
 p_prre_fecha_programada         IN VARCHAR2,
 p_usuario                       IN VARCHAR2,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2)  IS
 
 consulta varchar2 (2000);
 
BEGIN

    
   p_codigo_error := 0;
   p_mensaje      := NULL;

  consulta:= ' UPDATE SIPSA_RECOLECCION_INSUMOS2
   SET     esta_id = 5,
           rein2_usuario_modificacion=UPPER( '''||p_usuario||'''),
           rein2_fecha_modificacion = '''||SYSDATE||'''
    WHERE   futi_id in ( '||p_futi_id||' )
    
    AND     grin2_id in ( '||p_grin2_id||')
    AND     esta_id != 5
    AND     rein2_id_informante in ( '||p_rein_informante_id||')
    AND     to_number(to_char( prre_fecha_programada, ''yyyyMMdd'')) in ('||p_prre_fecha_programada||') ';
       
       dbms_output.put_line (consulta);
       execute immediate consulta;
       
   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'no se encontro ningun registro para aprobar SIPSA_RECOLECCION_INSUMOS2 con los identificadores dados ';
   END IF;

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := ' error en pr pr_aprueba_rein2 se presento '||SQLERRM;                                                                                   
END pr_aprueba_rein2;


PROCEDURE Pr_InsertarI2_D
/********************************************************************************
 Descripcion   : método para incorporar información sobre la tabla SIPSA_RECOLECCION_INSUMOS_D

 Realizado por : Marco Guzman
 Fecha Creacion: 19/06/2019
*******************************************************************************/
(p_futi_id                    IN SIPSA_RECOLECCION_INSUMOS_D.FUTI_ID%TYPE,
 p_uspe_id                    IN SIPSA_RECOLECCION_INSUMOS_D.USPE_ID%TYPE,
 p_grin2_id                   IN SIPSA_RECOLECCION_INSUMOS_D.GRIN_ID%TYPE,
 p_prre_fecha_programada      IN SIPSA_RECOLECCION_INSUMOS_D.REIN_FECHA_PROGRAMADA%TYPE,
 p_id_informante              IN SIPSA_RECOLECCION_INSUMOS_D.REIN_ID_INFORMANTE%TYPE,
 p_nom_informante             IN SIPSA_RECOLECCION_INSUMOS_D.REIN_NOM_INFORMANTE%TYPE,
 p_tel_informante             IN SIPSA_RECOLECCION_INSUMOS_D.REIN_TEL_INFORMANTE%TYPE,
 p_precio_recolectado         IN SIPSA_RECOLECCION_INSUMOS_D.REIN_PRECIO_RECOLECTADO%TYPE,
 p_novedad  				          IN SIPSA_RECOLECCION_INSUMOS_D.REIN_NOVEDAD%TYPE,
 p_obse_id                    IN  SIPSA_RECOLECCION_INSUMOS_D.OBSE_ID%TYPE,
 p_rein2_observacion          IN SIPSA_RECOLECCION_INSUMOS_D.REIN_OBSERVACION%TYPE,
 p_fecha_recoleccion          IN SIPSA_RECOLECCION_INSUMOS_D.REIN_FECHA_RECOLECCION%TYPE,
 p_esta_id                    IN SIPSA_RECOLECCION_INSUMOS_D.ESTA_ID%TYPE, 
 p_usuario                    IN SIPSA_RECOLECCION_INSUMOS_D.REIN_USUARIO_MODIFICACION%TYPE,
 p_tipo                       IN SIPSA_RECOLECCION_INSUMOS_D.REIN_TIPO%TYPE,   
 p_frecuencia                 IN SIPSA_RECOLECCION_INSUMOS_D.REIN_FRECUENCIA%TYPE,   
 p_observacion_producto       IN SIPSA_RECOLECCION_INSUMOS_D.REIN_OBSERVACION_PRODUCTO%TYPE,   
 p_unidad_medida_otro         IN SIPSA_RECOLECCION_INSUMOS_D.REIN_UNIDAD_MEDIDA_OTRO%TYPE,   
 p_unidad_id                  IN SIPSA_RECOLECCION_INSUMOS_D.REIN_UNIDAD_ID%TYPE,   
 p_unidad_medida              IN SIPSA_RECOLECCION_INSUMOS_D.REIN_UNIDAD_MEDIDA%TYPE,  
 p_arti_id                    IN SIPSA_RECOLECCION_INSUMOS_D.ARTI_ID%TYPE,   
 p_arti_nombre                IN SIPSA_RECOLECCION_INSUMOS_D.ARTI_NOMBRE%TYPE,  
 p_municipio                   IN SIPSA_RECOLECCION_INSUMOS_D.REIN_MUNICIPIO%TYPE,    
 p_muni_id                    IN SIPSA_RECOLECCION_INSUMOS_D.REIN_MUNI_ID%TYPE,    
 p_codigo_error               OUT NUMBER,
 p_mensaje                    OUT VARCHAR2) IS

contador NUMBER;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador         := NULL;
   
   SELECT COUNT(1) INTO contador FROM SIPSA_RECOLECCION_INSUMOS_D
   WHERE FUTI_ID = p_futi_id
   AND USPE_ID = p_uspe_id
   --AND GRIN_ID = p_grin2_id
   AND REIN_FECHA_PROGRAMADA = p_prre_fecha_programada
   AND REIN_PRECIO_RECOLECTADO = p_precio_recolectado
   AND REIN_NOM_INFORMANTE =p_nom_informante
   AND REIN_TEL_INFORMANTE = p_tel_informante
   AND REIN_NOVEDAD = p_novedad
   AND OBSE_ID = p_obse_id
   AND REIN_OBSERVACION = p_rein2_observacion
   AND REIN_FECHA_RECOLECCION = p_fecha_recoleccion
   AND REIN_TIPO = p_tipo
   AND REIN_MUNICIPIO = p_municipio
   AND REIN_MUNI_ID = p_muni_id
   AND REIN_FRECUENCIA = p_frecuencia
   AND REIN_OBSERVACION_PRODUCTO = p_observacion_producto
   AND REIN_UNIDAD_MEDIDA_OTRO = p_unidad_medida_otro
   AND REIN_UNIDAD_ID = p_unidad_id
   AND REIN_UNIDAD_MEDIDA = p_unidad_medida
   AND REIN_USUARIO_CREACION = UPPER(p_usuario)
   ;
   
   IF contador = 0 THEN
        INSERT INTO    SIPSA_RECOLECCION_INSUMOS_D (FUTI_ID,USPE_ID,GRIN_ID,REIN_FECHA_PROGRAMADA,REIN_ID_INFORMANTE,REIN_NOM_INFORMANTE,REIN_TEL_INFORMANTE,
													REIN_PRECIO_RECOLECTADO,REIN_NOVEDAD,OBSE_ID,REIN_OBSERVACION,REIN_FECHA_RECOLECCION,ESTA_ID,REIN_USUARIO_CREACION,REIN_FECHA_CREACION,
                          REIN_TIPO,REIN_FRECUENCIA,REIN_OBSERVACION_PRODUCTO,REIN_UNIDAD_MEDIDA_OTRO,REIN_UNIDAD_ID,REIN_UNIDAD_MEDIDA,REIN_MUNICIPIO,REIN_MUNI_ID)
        VALUES(p_futi_id,p_uspe_id,p_grin2_id,p_prre_fecha_programada,p_id_informante,p_nom_informante,p_tel_informante,
                     p_precio_recolectado,p_novedad,p_obse_id,p_rein2_observacion,p_fecha_recoleccion,p_esta_id,UPPER(p_usuario),sysdate,
                     p_tipo,p_frecuencia,p_observacion_producto,p_unidad_medida_otro,p_unidad_id,p_unidad_medida,p_municipio,p_muni_id); 
   ELSE
        
        PQ_SIPSA_RECOLECCION.pr_ActualizarI2_D(p_futi_id,p_uspe_id,p_grin2_id,p_prre_fecha_programada,p_id_informante,p_nom_informante,p_tel_informante,
                     p_precio_recolectado,p_novedad,p_obse_id,p_rein2_observacion,p_fecha_recoleccion,p_esta_id,UPPER(p_usuario),
                     p_tipo,p_frecuencia,p_observacion_producto,p_unidad_medida_otro,p_unidad_id,p_unidad_medida,p_municipio,p_muni_id,p_codigo_error,p_mensaje); 
   END IF;                      
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al insertar en la tabla SIPSA_RECOLECCION_INSUMOS_D se presento '||SQLERRM;                                                                                   
END Pr_InsertarI2_D;



PROCEDURE Pr_ActualizarI2_D
/********************************************************************************
 Descripcion   : método para actualizar información sobre la tabla SIPSA_RECOLECCION_INSUMOS

 Realizado por : Marco Guzman
 Fecha Creacion: 19/06/2019
*******************************************************************************/
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
 p_mensaje                      OUT VARCHAR2)  IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));

   UPDATE SIPSA_RECOLECCION_INSUMOS_D
   SET  REIN_PRECIO_RECOLECTADO = p_precio_recolectado,
           REIN_NOVEDAD = p_novedad,
           OBSE_ID = p_obse_id,
           REIN_OBSERVACION = p_rein2_observacion,
           ESTA_ID = p_esta_id,
           REIN_USUARIO_MODIFICACION=UPPER(p_usuario),
           REIN_FECHA_MODIFICACION = sysdate,
           REIN_TIPO = p_tipo,
           REIN_FRECUENCIA = p_frecuencia,
           REIN_OBSERVACION_PRODUCTO = p_observacion_producto,
           REIN_UNIDAD_MEDIDA_OTRO = p_unidad_medida_otro,
           REIN_UNIDAD_ID = p_unidad_id,
           REIN_UNIDAD_MEDIDA = p_unidad_medida
    WHERE FUTI_ID = p_futi_id
    AND     USPE_ID = p_uspe_id
    --AND     GRIN_ID = p_grin2_id
    AND     REIN_FECHA_PROGRAMADA = p_prre_fecha_programada
    AND     REIN_ID_INFORMANTE = p_id_informante
    AND     REIN_MUNICIPIO = p_municipio
    AND     REIN_MUNI_ID = p_muni_id
    /*AND     REIN_TIPO = p_tipo
    AND     REIN_FRECUENCIA = p_frecuencia
    AND     REIN_OBSERVACION_PRODUCTO = p_observacion_producto
    AND     REIN_UNIDAD_MEDIDA_OTRO = p_unidad_medida_otro
    AND     REIN_UNIDAD_ID = p_unidad_id
    AND     REIN_UNIDAD_MEDIDA = p_unidad_medida*/
    AND     ESTA_ID = 1;
    
       
   IF SQL%NOTFOUND THEN
      p_codigo_error := 1;
      p_mensaje      := 'no se encontro ningun registro para actualizar en la tabla SIPSA_RECOLECCION_INSUMOS_D con los identificadores dados ';
   END IF;

EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'al actualizar la tabla SIPSA_RECOLECCION_INSUMOS_D se presento '||SQLERRM;                                                                                   
END Pr_ActualizarI2_D;


PROCEDURE Pr_CargarDMCRecoleccionDIS
 /********************************************************************************
 Descripcion   : metodo para consultar las estructuras relacionadas con la recoleccion
                 para ser llamado varias veces en un periodo

 parametros    :
 in            : p_uspe_id  



 out           :  p_TiposRecolecciones
                   p_Principal
                   p_Fuentes
                   p_Informadores
                   p_FuentesTire
                   p_Persona
                   p_Articulos
                   p_Caracteristicas
                   p_ArtiCara
                   p_Observaciones
                   p_codigo_error valor que indioca la ocurrencia de error en el proceso
                   p_mensaje      mensaje del error ocurrido

  Realizado por : MGUZMAN
 Fecha Creacion: 15/04/2019
*******************************************************************************/
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
  p_Caracteristicas 		OUT PQ_GEN_GENERAL.ty_cursor,
  p_ValCaraPermitidos		 OUT PQ_GEN_GENERAL.ty_cursor,  
  p_ArtiCaraValores 		OUT PQ_GEN_GENERAL.ty_cursor,
  p_Observaciones			 OUT PQ_GEN_GENERAL.ty_cursor,
  p_codigo_error              OUT NUMBER,
  p_mensaje                   OUT VARCHAR2) IS

v_tire_id INTEGER;

BEGIN


            /**************************
                        SERVICIO PRINCIPAL
            **************************/           
               OPEN p_Principal FOR
                SELECT    futi.tire_id,
						  --tire.tire_nombre,
						  futi.futi_id,
						  fuen.muni_id,
						  fuen.fuen_id,
						  fuen.fuen_nombre,--fuen.fuen_direccion,                     
						  arti.arti_id,
						  arti.arti_nombre,--arti.arti_reg_ica,
						  futiar2.grin2_id, 
						  DECODE( futi.tire_id,9,                                     PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto1(futiar2.grin2_id, futi.tire_id, arti.arti_nombre), 
													  8, arti.arti_nombre||' en '||PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto1(futiar2.grin2_id,futi.tire_id, arti.arti_nombre),                                
														  arti.arti_nombre||' -- '||PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto1(futiar2.grin2_id,futi.tire_id, arti.arti_nombre)) nombre_completo,
						  arti.arti_vlr_min_tomas,arti.arti_vlr_max_tomas,arti.arti_vlr_min_rondas,arti.arti_vlr_max_rondas,arti.arti_vlr_min_diasm,arti.arti_vlr_max_diasm,prre.prre_fecha_programada,
						 NVL((SELECT ROUND(avg(pm.rein2_precio_recolectado),2) FROM SIPSA_RECOLECCION_INSUMOS2 pm 
								 WHERE futi.futi_id = pm.futi_id 
								 AND futi.futi_id=futiar2.futi_id
								 AND futi.Tire_id=13
								 AND futi.fuen_id=fuen.fuen_id 
								 AND futiar2.grin2_id = pm.grin2_id
								 AND pm.prre_fecha_programada =  (SELECT MAX(pm1.prre_fecha_programada) 
																  FROM SIPSA_RECOLECCION_INSUMOS2 pm1 
																  WHERE pm1.futi_id = pm.futi_id
																  AND pm1.futi_id=pm.futi_id 
																  AND futi.Tire_id=13
																  AND pm1.grin2_id = pm.grin2_id 
																  AND pm1.prre_fecha_programada < TRUNC(prre.prre_fecha_programada))),1000) Prom_Ant_Diario   
          ,PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto2(grin2.grin2_id, futi.Tire_id, arti.arti_nombre,1)  as tipo
          ,PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto2(grin2.grin2_id, futi.Tire_id, arti.arti_nombre,2) as frecuencia
          ,PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto2(grin2.grin2_id, futi.Tire_id, arti.arti_nombre,3) as unme_Nombre2
          ,PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto2(grin2.grin2_id, futi.Tire_id, arti.arti_nombre,4) as observacion_Producto
						FROM SIPSA_FUENTES fuen, SIPSA_ARTICULOS arti,
								 SIPSA_FUENTE_ARTICULOS2 futiar2,
								 SIPSA_PROGRAMACION_RECOLECCION prre,
								 SIPSA_FUENTES_USUARIO_PERFILES fuus,SIPSA_USUARIOS usua,
								 SIPSA_USUARIOS_PERFILES uspe,SIPSA_FUENTES_TIPO_RECOLECCION futi,
								 SIPSA_GRUPO_INSUMOS2 grin2,
								 SIPSA_ARTI_TIRE arre
						WHERE  futiar2.futi_id=futi.futi_id
						AND futi.fuen_id=fuen.fuen_id
						AND fuen.fuen_id=futi.fuen_id
						AND futiar2.grin2_id=grin2.grin2_id
						AND arti.arti_id=grin2.arti_id
						AND fuus.futi_id=futi.futi_id
						AND futi.Tire_id=13
						AND futi.futi_id=prre.futi_id
						AND futiar2.grin2_id=grin2.grin2_id
						AND futiar2.futiar2_estado=1
						AND prre.prre_estado='19'--ACTIVO
						AND uspe.uspe_id=fuus.uspe_id
						AND usua.usua_id=uspe.usua_id
					    AND uspe.uspe_id=p_uspe_id
						AND fuus.fuus_fecha_hasta >=SYSDATE
						AND futi.futi_fecha_hasta >= SYSDATE
						AND arti.arti_id = arre.arti_id
						AND futi.tire_id = arre.tire_id
						AND arre.artitire_fecha_hasta >= SYSDATE
						AND grin2.grin2_fecha_hasta >= SYSDATE
						AND TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') <= ( SELECT MAX (prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1
																													WHERE prre1.futi_id = futi.futi_id
																													AND     prre1.prre_estado = 19
																													) 
						 AND 0 = (SELECT COUNT(1) FROM SIPSA_ENCA_INSUMOS2 enin2
										WHERE  enin2.futi_id = futi.futi_id
										AND enin2.uspe_id = uspe.uspe_id
										AND enin2.prre_fecha_programada =   prre.prre_fecha_programada
										AND enin2.grin2_id =   futiar2.grin2_id)                                                                      
					   ORDER BY fuen.fuen_id,futi.tire_id,futiar2.grin2_id, arti.arti_id;       
    

         /**************************
           SERVICIO INFORMADORES
        **************************/         
        OPEN p_Informadores FOR
          SELECT DISTINCT info.muni_id,info.info_id,info.info_nombre,info.info_telefono
          FROM SIPSA_INFORMANTES info, SIPSA_FUENTES fuen, SIPSA_FUENTES_TIPO_RECOLECCION futi, SIPSA_FUENTES_USUARIO_PERFILES fuus
          WHERE fuen.fuen_id = futi.fuen_id      
          AND info.muni_id = fuen.muni_id
          AND futi.futi_id = fuus.futi_id
          AND futi.tire_id = 13         
          AND fuus.uspe_id=p_uspe_id
          AND info.info_fecha_hasta >= SYSDATE
          ORDER BY 1,2;

         /***************************************
                    SERVICIO FUENTES TIPO RECOLECION
        **************************************/       
        OPEN p_FuentesTire FOR
          SELECT futi.futi_id,fuen.fuen_id, tire.tire_id,tire.tire_nombre,fuen.fuen_nombre,fuen.muni_id,muni.muni_nombre,prre.prre_fecha_programada
          FROM SIPSA_FUENTES fuen, 
                   SIPSA_TIPO_RECOLECCIONES tire,
                   SIPSA_MUNICIPIOS muni,
                   SIPSA_PROGRAMACION_RECOLECCION prre,
                   SIPSA_FUENTES_USUARIO_PERFILES fuus,SIPSA_USUARIOS usua,
                   SIPSA_USUARIOS_PERFILES uspe,SIPSA_FUENTES_TIPO_RECOLECCION futi
          WHERE fuen.fuen_id=futi.fuen_id
          AND     fuus.futi_id=futi.futi_id
          AND     fuen.fuen_id=futi.fuen_id
          AND     futi.tire_id = tire.tire_id
          AND     fuen.muni_id=muni.muni_id
          AND     futi.futi_id=prre.futi_id
          AND     uspe.uspe_id=fuus.uspe_id
          AND     usua.usua_id=uspe.usua_id
          AND     futi.tire_id = 13
          AND     prre.prre_estado='19'
          AND     uspe.uspe_id=p_uspe_id
          AND fuus.fuus_fecha_hasta >=SYSDATE
          AND futi.futi_fecha_hasta >= SYSDATE
          AND TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') <= ( SELECT MAX (prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1
                                                                                                    WHERE prre1.futi_id = futi.futi_id
                                                                                                    AND     prre1.prre_estado = 19
                                                                                                    )
          ORDER BY tire.tire_id,futi.futi_id,fuen.fuen_id;   
		  
		  /**************************
                    SERVICIO FUENTE ARTICULOS
			**************************/   
		  OPEN p_FuentesArt FOR
          SELECT futi.futi_id,fuen.fuen_id,fuen.fuen_nombre,fuen.fuen_direccion,fuen.fuen_telefono,fuen.fuen_email,
                   fuen.fuen_nombre_informante,fuen.fuen_cod_interno as fuen_codigo_interno,fuen.fuen_telefono_informante,fuen.fuen_email_informante,
                    futi.tire_id,
                    tire.tire_nombre,
                    fuen.muni_id,muni.muni_nombre,prre.prre_fecha_programada
          FROM SIPSA_FUENTES fuen, 
                   SIPSA_TIPO_RECOLECCIONES tire,SIPSA_MUNICIPIOS muni,
                   SIPSA_PROGRAMACION_RECOLECCION prre,
                   SIPSA_FUENTES_USUARIO_PERFILES fuus,SIPSA_USUARIOS usua,
                   SIPSA_USUARIOS_PERFILES uspe,SIPSA_FUENTES_TIPO_RECOLECCION futi
          WHERE fuen.fuen_id=futi.fuen_id
          AND     fuus.futi_id=futi.futi_id
          AND     fuen.fuen_id=futi.fuen_id
          AND     futi.tire_id = tire.tire_id
          AND     fuen.muni_id=muni.muni_id
          AND     futi.futi_id=prre.futi_id
          AND     uspe.uspe_id=fuus.uspe_id
          AND     usua.usua_id=uspe.usua_id
          AND     futi.futi_fecha_hasta >= SYSDATE
          AND     futi.tire_id IN (13)
          AND     prre.prre_estado=19
          AND     uspe.uspe_id=p_uspe_id
          AND     fuus.fuus_fecha_hasta >=SYSDATE
        AND TO_DATE(prre.prre_fecha_programada, 'dd-mm-yy') <= ( SELECT MAX (prre1.prre_fecha_programada) FROM SIPSA_PROGRAMACION_RECOLECCION prre1 -- '30/09/2013'( SELECT MIN 
                                                                                                    WHERE prre1.futi_id = futi.futi_id
                                                                                                    AND     prre1.prre_estado = 19) 
          ORDER BY fuen.fuen_id;   

        /**************************
                    SERVICIO UNIDADES
        **************************/          
        OPEN p_Unidades FOR
          SELECT DISTINCT unmetire.tire_id,tire.tire_nombre,unme.unme_id,tipr.tipr_id,tipr.tipr_nombre,unme.unme_cantidad_ppal,unme.unme_nombre_ppal,
                      unme.unme_cantidad_2,unme.unme_nombre_2  
          FROM SIPSA_UNIDADES_MEDIDA unme,SIPSA_TIPO_PRESENTACIONES tipr, SIPSA_UNME_TIRE unmetire,SIPSA_TIPO_RECOLECCIONES tire
          WHERE unme.tipr_id=tipr.tipr_id
          AND unmetire.tire_id = tire.tire_id
          AND unme.unme_id = unmetire.unme_id 
          AND unmetire.unmetire_fecha_hasta >= SYSDATE
          AND unmetire.tire_id IN (13)
          ORDER BY  unme.unme_id;   

         /**************************
                    SERVICIO GRUPO INSUMOS2
        ***************************/      
        OPEN p_Articulos FOR
          SELECT grin2.grin2_id,arti.arti_id,arti.arti_nombre,
          DECODE( tire.tire_id,9,                                     PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto1(grin2.grin2_id, tire.tire_id, arti.arti_nombre), 
                                      8, arti.arti_nombre||' en '||PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto1(grin2.grin2_id, tire.tire_id, arti.arti_nombre),                                
                                          arti.arti_nombre||' -- ' ||PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto1(grin2.grin2_id, tire.tire_id, arti.arti_nombre)) nombre_completo,
                                          tire.tire_id
          ,PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto2(grin2.grin2_id, tire.tire_id, arti.arti_nombre,1)  as tipo
          ,PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto2(grin2.grin2_id, tire.tire_id, arti.arti_nombre,2) as frecuencia
          ,PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto2(grin2.grin2_id, tire.tire_id, arti.arti_nombre,3) as unidad
          ,PQ_SIPSA_RECOLECCION.fn_ObtenerDescripcionProducto2(grin2.grin2_id, tire.tire_id, arti.arti_nombre,4) as observacion
          FROM SIPSA_ARTICULOS arti, SIPSA_GRUPO_INSUMOS2 grin2, SIPSA_TIPO_RECOLECCIONES tire, SIPSA_ARTI_TIRE artitire
          WHERE arti.arti_id = grin2.arti_id
          AND artitire.arti_id = arti.arti_id
          AND artitire.tire_id = tire.tire_id
          AND grin2.grin2_fecha_hasta >= SYSDATE
          AND artitire.artitire_fecha_hasta >= SYSDATE
          AND artitire.tire_id = 13
          ORDER BY tire.tire_id,arti.arti_nombre;



         /**************************
           SERVICIO CARACTERISTICAS
        **************************/         
        OPEN p_Caracteristicas FOR
          SELECT cara.cara_id,cara.cara_descripcion,cara.tire_id,cara.cara_orden
          FROM SIPSA_CARACTERISTICAS cara
          WHERE cara.tire_id = 13
          ORDER BY cara.tire_id,cara.cara_id;  


        /***********************************************
           SERVICIO  VALORES CARACTERISTICAS PERMITIDAS
        ***********************************************/         
        OPEN p_ValCaraPermitidos FOR
          SELECT tire.tire_id,cara.cara_id,cara.cara_descripcion,vape.vape_id,vape.vape_descripcion
          FROM  SIPSA_TIPO_RECOLECCIONES tire, SIPSA_VAPE_CARA vape,SIPSA_CARACTERISTICAS cara
          WHERE cara.tire_id = tire.tire_id
          AND vape.cara_id = cara.cara_id      
          AND cara.cara_id in (20,21,22)
          ORDER BY tire.tire_id,cara.cara_id;
 


         /***********************************************
           SERVICIO  VALORES CARACTERISTICAS PRODUCTOS
        ***********************************************/         
        OPEN p_ArtiCaraValores FOR
          SELECT  DISTINCT grin2vaca.cara_id,grin2vaca.vape_id,vape.vape_descripcion 
          FROM SIPSA_ARTICULOS arti,SIPSA_ARTI_TIRE artitire,SIPSA_GRUPO_INSUMOS2 grin2,SIPSA_GRIN2_VACA grin2vaca, SIPSA_VAPE_CARA vape
          WHERE arti.arti_id      = artitire.arti_id 
          AND   artitire.tire_id  = 13
          AND   grin2.arti_id     = arti.arti_id 
          AND   grin2.grin2_id    = grin2vaca.grin2_id
          AND   grin2vaca.Cara_Id = vape.cara_id
          and   grin2vaca.vape_id = vape.vape_id
          and vape.cara_id in (20,21,22);

		/**************************
                    SERVICIO CASAS COMERCIALES
        **************************/          
        OPEN p_CasasComerciales FOR
          /*SELECT caco.caco_id,caco.caco_nombre
          FROM SIPSA_CASAS_COMERCIALES caco         
          ORDER BY caco.caco_id;   */
          SELECT caco.caco_id,caco.caco_nombre
          FROM SIPSA_CASAS_COMERCIALES caco, SIPSA_ARTI_CACO arca
          WHERE 1=1
          AND caco.caco_id = arca.caco_id
          AND arca.articaco_fecha_hasta >= sysdate
          GROUP BY caco.caco_id,caco.caco_nombre        
          ORDER BY caco.caco_id;


         /**************************
           SERVICIO GRUPOS
        **************************/          
        OPEN p_Grupos FOR
          /*SELECT grup.grup_id,grup.grup_nombre
          FROM SIPSA_GRUPOS grup
          WHERE grup.grup_id >=90
          ORDER BY grup.grup_id;  */
          
          SELECT DISTINCT grup.grup_id,grup.grup_nombre,tire.tire_id
          FROM SIPSA_ARTICULOS arti, SIPSA_GRUPOS grup, SIPSA_TIPO_RECOLECCIONES tire,SIPSA_CASAS_COMERCIALES caco, SIPSA_ARTI_TIRE artitire, SIPSA_ARTI_CACO articaco
          WHERE arti.grup_id = grup.grup_id
          AND articaco.caco_id=caco.caco_id
          AND articaco.arti_id = arti.arti_id
          AND artitire.arti_id = arti.arti_id
          AND artitire.tire_id = tire.tire_id
          AND artitire.artitire_fecha_hasta >= SYSDATE
          AND articaco.articaco_fecha_hasta >= SYSDATE
          AND artitire.tire_id IN (13)
          ORDER BY 1,3;


         /**************************
           SERVICIO OBSERVACIONES
        **************************/          

        PQ_SIPSA_OBSERVACIONES.pr_Consultar(p_obse_id                => NULL,
                                                                      p_uspe_id => p_uspe_id,
                                                                      p_resultado         => p_observaciones,
                                                                      p_codigo_error      => p_codigo_error,
                                                                      p_mensaje           => p_mensaje);

  
        /**************************
                    SERVICIO PERSONAS
        **************************/     
         PQ_SIPSA_USUARIOS_PERFILES.pr_consultar(p_uspe_id  => p_uspe_id,--v_usua_id_perfil,
                                                p_resultado      => p_Persona,
                                                p_codigo_error   => p_codigo_error,
                                                p_mensaje        => p_mensaje); 
 
EXCEPTION
    WHEN OTHERS THEN
    p_codigo_error := 1;
    p_mensaje:=p_mensaje;
      
/***********************************
  --  servicio fuentes

     Pq_Ind_Detalles_Fuente.pr_consultarGea( p_id             => NULL,
                                             p_ciudad         => v_ciudad,
                                             p_resultado      => p_fuentes,
                                             p_codigo_error   => p_codigo_error,
                                             p_mensaje        => p_mensaje);

***********************************/               

END Pr_CargarDMCRecoleccionDIS;


FUNCTION fn_ObtenerDescripcionProducto2(p_grin2_id IN NUMBER,p_tire_id IN NUMBER, p_articulo IN VARCHAR2, tipo IN NUMBER)
/********************************************************************************
 DESCRIPCION   : Metodo para obtener completa la descripcion de un producto a traves de sus caracteristicas

 REALIZADO POR : Marco Guzman
 FECHA CREACION: 05/04/2013

*******************************************************************************/
  RETURN VARCHAR2 IS

 CURSOR c_desc_producto IS
    SELECT vape_descripcion , c.cara_orden 
        FROM sipsa_vape_cara v, sipsa_grin2_vaca p, sipsa_caracteristicas c
   WHERE v.cara_id = p.cara_id
   AND v.vape_id = p.vape_id
   AND v.cara_id = c.cara_id
   AND p.grin2_id = p_grin2_id
   ORDER BY c.cara_orden;

 v_descripcion VARCHAR2(2000);
 v VARCHAR2(1);

BEGIN
    v:='';
    FOR x in c_desc_producto LOOP
        IF tipo = 1 AND x.cara_orden = 1 THEN
           RETURN x.vape_descripcion;
        ELSE
          IF tipo = 2 AND x.cara_orden = 2 THEN
            RETURN x.vape_descripcion;
          ELSE
             IF tipo = 3 AND x.cara_orden = 3 THEN
                RETURN x.vape_descripcion;
              ELSE
                IF tipo = 4 AND x.cara_orden = 4 THEN
                  RETURN x.vape_descripcion;
                END IF;
              END IF;
          END IF; 
        END IF; 
    END LOOP;

    RETURN v_descripcion;

END fn_ObtenerDescripcionProducto2;

END PQ_SIPSA_RECOLECCION;