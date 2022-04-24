create or replace PACKAGE BODY PQ_SIPSA_APROBACIONES IS

PROCEDURE Pr_InsertarIA
/********************************************************************************
 Descripcion   : metodo para incorporar informacion sobre la tabla SIPSA_APROBA_INSUMOS

 parametros    :
 in            :      p_fuen_id
                      p_futi_id
                      p_tire_id
                      p_uspe_id
                      p_prre_fecha_programada
                      p_arti_id
                      p_arti_nombre
                      p_ica
                      p_caco_id
                      p_casa_comercial
                      p_unme_id
                      p_precio_recolectado
                      p_novedad
                      p_obse_id
                      p_observacion
                      p_usuario


 out           :    p_futi_id
                     p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                     p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/02/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/

( p_fuen_id               IN SIPSA_APROBA_INSUMOS.apin_fuen_id%TYPE, 
  p_futi_id               IN OUT SIPSA_APROBA_INSUMOS.apin_futi_id%TYPE,
  p_tire_id   IN  SIPSA_APROBA_INSUMOS.tire_id%TYPE,
 p_uspe_id   IN  SIPSA_APROBA_INSUMOS.uspe_id%TYPE,
 p_prre_fecha_programada IN SIPSA_APROBA_INSUMOS.apin_prre_fecha_programada%TYPE,
 p_articaco_id IN SIPSA_APROBA_INSUMOS.apin_articaco_id%TYPE,
 p_arti_id       IN SIPSA_APROBA_INSUMOS.apin_arti_id%TYPE,
 p_arti_nombre       IN  SIPSA_APROBA_INSUMOS.apin_arti_nombre%TYPE,
 p_caco_id       IN  SIPSA_APROBA_INSUMOS.apin_caco_id%TYPE,
 p_casa_comercial       IN  SIPSA_APROBA_INSUMOS.apin_casa_comercial%TYPE,
 p_regica_linea       IN  SIPSA_APROBA_INSUMOS.apin_regica_linea%TYPE,
 p_unme_id    IN  SIPSA_APROBA_INSUMOS.unme_id%TYPE,
 p_precio_recolectado            IN SIPSA_APROBA_INSUMOS.apin_precio_recolectado%TYPE,
 p_novedad            IN SIPSA_APROBA_INSUMOS.apin_novedad%TYPE,
 p_obse_id            IN SIPSA_APROBA_INSUMOS.obse_id%TYPE,
 p_observacion           IN SIPSA_APROBA_INSUMOS.apin_observacion%TYPE,
 p_esta_id           IN SIPSA_APROBA_INSUMOS.esta_id%TYPE,
 p_fecha_recoleccion IN SIPSA_APROBA_INSUMOS.apin_fecha_recoleccion%TYPE,
 p_usuario                       IN SIPSA_APROBA_INSUMOS.apin_usuario_creacion%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2)IS

contador NUMBER;
v_fecha_programa DATE;
--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador := NULL;

    
    /*SELECT MAX(prog.prre_fecha_programada) INTO v_fecha_programa
    FROM sipsa_programacion_recoleccion prog
    WHERE 1=1
    AND prog.futi_id = p_futi_id
    ;*/
    
    
    SELECT COUNT(1) INTO contador FROM SIPSA_APROBA_INSUMOS
    WHERE apin_futi_id = p_futi_id
    AND apin_fuen_id = p_fuen_id 
    AND tire_id = p_tire_id
    AND uspe_id = p_uspe_id
    AND apin_prre_fecha_programada = p_prre_fecha_programada
    --AND apin_prre_fecha_programada = v_fecha_programa
    AND apin_articaco_id = p_articaco_id
    AND apin_arti_id = p_arti_id
    AND apin_caco_id = p_caco_id
    AND unme_id = p_unme_id;
   
    
   
   IF contador= 0 THEN
   --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
        INSERT INTO    SIPSA_APROBA_INSUMOS (apin_fuen_id,apin_futi_id,tire_id,uspe_id,apin_prre_fecha_programada,apin_articaco_id,apin_arti_id,apin_arti_nombre,
                                                                         apin_caco_id,apin_casa_comercial,apin_regica_linea,unme_id,apin_precio_recolectado,apin_novedad,obse_id,apin_observacion,esta_id,
                                                                         apin_fecha_recoleccion,apin_usuario_creacion)
        VALUES(p_fuen_id,p_futi_id,p_tire_id,p_uspe_id,p_prre_fecha_programada ,p_articaco_id,p_arti_id,p_arti_nombre,
                    p_caco_id,p_casa_comercial,p_regica_linea,p_unme_id,p_precio_recolectado,p_novedad,p_obse_id,p_observacion,p_esta_id,
                    p_fecha_recoleccion,UPPER(p_usuario));
  ELSE
                PQ_SIPSA_APROBACIONES.Pr_ActualizarIA(p_fuen_id,p_futi_id,p_tire_id,p_uspe_id,p_prre_fecha_programada,p_articaco_id,p_arti_id,p_arti_nombre,
                                                                               p_caco_id,p_casa_comercial,p_regica_linea,p_unme_id,p_precio_recolectado,p_novedad,p_obse_id,
                                                                               p_observacion,p_esta_id,p_fecha_recoleccion,UPPER(p_usuario),p_codigo_error,p_mensaje);   
  END IF;
                         
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_APROBA_INSUMOS SE PRESENTO'||SQLERRM;                                                                                   
END Pr_InsertarIA;




PROCEDURE pr_ActualizarIA
/********************************************************************************
 Descripcion   : metodo para incorporar informacion sobre la tabla SIPSA_APROBA_INSUMOS

 parametros    :
 in            :      p_fuen_id
                      p_futi_id
                      p_tire_id
                      p_uspe_id
                      p_prre_fecha_programada
                      p_arti_id
                      p_arti_nombre
                      p_ica
                      p_caco_id
                      p_casa_comercial
                      p_unme_id
                      p_precio_recolectado
                      p_novedad
                      p_obse_id
                      p_observacion
                      p_usuario


 out           :    p_futi_id
                     p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                     p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/02/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/

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
 p_mensaje                       OUT VARCHAR2)IS

v_fecha_programa DATE;
BEGIN
    p_codigo_error := 0;
    p_mensaje      := NULL;
    
    
    
    UPDATE     SIPSA_APROBA_INSUMOS
    SET apin_arti_nombre = p_arti_nombre,apin_casa_comercial = p_casa_comercial,apin_regica_linea = p_regica_linea,
           apin_precio_recolectado = p_precio_recolectado,apin_novedad = p_novedad,obse_id = p_obse_id,
           apin_observacion = p_observacion,esta_id = p_esta_id,apin_fecha_recoleccion = p_fecha_recoleccion,
           apin_usuario_creacion = UPPER(p_usuario)
    WHERE apin_futi_id = p_futi_id
    AND apin_fuen_id = p_fuen_id 
    AND tire_id = p_tire_id
    AND uspe_id = p_uspe_id
    AND apin_prre_fecha_programada =p_prre_fecha_programada
    AND apin_articaco_id = p_articaco_id
    AND apin_arti_id = p_arti_id
    AND apin_caco_id = p_caco_id
    AND unme_id = p_unme_id;
    
       IF SQL%NOTFOUND THEN
          p_codigo_error := 1;
          p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ACTUALIZAR CON LOS IDENTIFICADORES DADOS EN LA TABLA SIPSA_APROBA_INSUMOS';
       END IF; 
 
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_APROBA_INSUMOS SE PRESENTO'||SQLERRM;                                                                                   
END pr_ActualizarIA;

PROCEDURE Pr_InsertarIAFI
/********************************************************************************
 Descripcion   : metodo para incorporar informacion sobre la tabla SIPSA_APROBA_FUEN_INFO

 parametros    :
 in            :  p_fuen_id
                  p_futi_id
                  p_muni_id
                  p_tire_id
                  p_uspe_id
                  p_fecha_visita
                  p_fuen_nombre
                  p_fuen_direccion
                  p_fuen_telefono
                  p_fuen_email
                  p_info_nombre
                  p_info_cargo
                  p_info_telefono
                  p_info_email
                  p_usuario

 out           :  p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                   p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/02/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/

( p_fuen_id               IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_id%TYPE,
  p_futi_id               IN SIPSA_APROBA_FUEN_INFO.apfuin_futi_id%TYPE,
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
  p_mensaje                       OUT VARCHAR2)IS


contador NUMBER;
--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador := NULL;

    SELECT COUNT(1) INTO contador FROM SIPSA_APROBA_FUEN_INFO
     WHERE apfuin_fuen_id = p_fuen_id
    AND apfuin_futi_id= p_futi_id
    AND tire_id = p_tire_id
    AND muni_id = p_muni_id
    AND uspe_id = p_uspe_id
    AND apfuin_fecha_visita = p_fecha_visita;

    IF contador = 0 THEN  
   --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario)))); 
        INSERT INTO    SIPSA_APROBA_FUEN_INFO (apfuin_fuen_id,apfuin_futi_id,muni_id,tire_id,uspe_id,apfuin_fecha_visita,
                                                                            apfuin_fuen_nombre,apfuin_fuen_direccion,apfuin_fuen_telefono,apfuin_fuen_email,
                                                                            apfuin_info_nombre,apfuin_info_cargo,apfuin_info_telefono,apfuin_info_email,
                                                                            apfuin_usuario_creacion)
        VALUES(p_fuen_id,p_futi_id,p_muni_id,p_tire_id,p_uspe_id,p_fecha_visita,
                     p_fuen_nombre,p_fuen_direccion,p_fuen_telefono,p_fuen_email,
                     p_info_nombre,p_info_cargo,p_info_telefono,p_info_email,
                     p_usuario); 
    ELSE    
        PQ_SIPSA_APROBACIONES.Pr_ActualizarIAFI(p_fuen_id,p_futi_id,p_muni_id,p_tire_id,p_uspe_id,p_fecha_visita,
                                                                             p_fuen_nombre,p_fuen_direccion,p_fuen_telefono,p_fuen_email,
                                                                             p_info_nombre,p_info_cargo,p_info_telefono,p_info_email,
                                                                             p_usuario,p_codigo_error,p_mensaje);   
    END IF;           


             
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_APROBA_FUEN_INFO SE PRESENTO'||SQLERRM;                                                                                   
END Pr_InsertarIAFI;

PROCEDURE Pr_ActualizarIAFI
/********************************************************************************
 Descripcion   : metodo para incorporar informacion sobre la tabla SIPSA_APROBA_FUEN_INFO

 parametros    :
 in            :  p_fuen_id
                  p_futi_id
                  p_muni_id
                  p_tire_id
                  p_uspe_id
                  p_fecha_visita
                  p_fuen_nombre
                  p_fuen_direccion
                  p_fuen_telefono
                  p_fuen_email
                  p_info_nombre
                  p_info_cargo
                  p_info_telefono
                  p_info_email
                  p_usuario

 out           :  p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                   p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 07/02/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/

( p_fuen_id               IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_id%TYPE,
  p_futi_id               IN SIPSA_APROBA_FUEN_INFO.apfuin_futi_id%TYPE,
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
  p_mensaje                       OUT VARCHAR2)IS

BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

   --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario)))); 
        
        UPDATE SIPSA_APROBA_FUEN_INFO
        SET  apfuin_fuen_nombre = p_fuen_nombre,
               apfuin_fuen_direccion = p_fuen_direccion,
               apfuin_fuen_telefono = p_fuen_telefono,
               apfuin_fuen_email = p_fuen_email,
               apfuin_info_nombre = p_info_nombre,
               apfuin_info_cargo = p_info_cargo,
               apfuin_info_telefono = p_info_telefono,
               apfuin_info_email = p_info_email,
               apfuin_usuario_modificacion = UPPER(p_usuario)
       WHERE apfuin_fuen_id = p_fuen_id
       AND apfuin_futi_id = p_futi_id
       AND tire_id = p_tire_id
       AND muni_id = p_muni_id    
       AND uspe_id = p_uspe_id
       AND apfuin_fecha_visita = p_fecha_visita;        
       
       IF SQL%NOTFOUND THEN
          p_codigo_error := 1;
          p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ACTUALIZAR CON LOS IDENTIFICADORES DADOS EN LA TABLA SIPSA_APROBA_FUEN_INFO';
       END IF;       
                                                                               
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL ACTUALIZAR EN LA TABLA SIPSA_APROBA_FUEN_INFO SE PRESENTO'||SQLERRM;                                                                                   
END Pr_ActualizarIAFI;

PROCEDURE pr_InsertarAPGRIN2
/********************************************************************************
 Descripcion   : metodo para incorporar informacion sobre la tabla SIPSA_APROBA_GRIN2

 parametros    :
 in            :     p_futi_id
                     p_uspe_id
                     p_apgrin2_id
                     p_arti_id
                     p_usuario


 out           :    p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                     p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 15/04/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
( p_futi_id               IN  SIPSA_APROBA_GRIN2.futi_id%TYPE,
  p_uspe_id   IN  SIPSA_APROBA_GRIN2.uspe_id%TYPE,
  p_apgrin2_id IN SIPSA_APROBA_GRIN2.apgrin2_id%TYPE,
  p_arti_id IN SIPSA_APROBA_GRIN2.arti_id%TYPE,
  p_usuario                       IN SIPSA_APROBA_GRIN2.apgrin2_usuario_creacion%TYPE,
  p_codigo_error                  OUT NUMBER,
  p_mensaje                       OUT VARCHAR2)IS


contador NUMBER;
--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador := NULL;

    SELECT COUNT(1) INTO contador FROM SIPSA_APROBA_GRIN2
     WHERE futi_id = p_futi_id
    AND uspe_id= p_uspe_id
    AND apgrin2_id = p_apgrin2_id;
        
    IF contador = 0 THEN
        --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
        INSERT INTO    SIPSA_APROBA_GRIN2 (futi_id,uspe_id,apgrin2_id,arti_id,apgrin2_usuario_creacion)
        VALUES(p_futi_id,p_uspe_id,p_apgrin2_id,p_arti_id,p_usuario);  
    ELSE    
        PQ_SIPSA_APROBACIONES.pr_ActualizarAPGRIN2(p_futi_id,p_uspe_id,p_apgrin2_id,p_arti_id,p_usuario,p_codigo_error,p_mensaje);   
    END IF;           
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_APROBA_GRIN2 SE PRESENTO '||SQLERRM;                                                                                   
END pr_InsertarAPGRIN2;

PROCEDURE pr_ActualizarAPGRIN2
/********************************************************************************
 Descripcion   : metodo para actualizar informacion sobre la tabla SIPSA_APROBA_GRIN2

 parametros    :
 in            :     p_futi_id
                     p_uspe_id
                     p_apgrin2_id
                     p_arti_id
                     p_usuario


 out           :    p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                     p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 15/04/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
( p_futi_id               IN  SIPSA_APROBA_GRIN2.futi_id%TYPE,
  p_uspe_id   IN  SIPSA_APROBA_GRIN2.uspe_id%TYPE,
  p_apgrin2_id IN SIPSA_APROBA_GRIN2.apgrin2_id%TYPE,
  p_arti_id IN SIPSA_APROBA_GRIN2.arti_id%TYPE,
  p_usuario                       IN SIPSA_APROBA_GRIN2.apgrin2_usuario_modificacion%TYPE,
  p_codigo_error                  OUT NUMBER,
  p_mensaje                       OUT VARCHAR2)IS

--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;

    UPDATE   SIPSA_APROBA_GRIN2
    SET arti_id=p_arti_id, apgrin2_usuario_modificacion=p_usuario, apgrin2_fecha_modificacion=SYSDATE
    WHERE futi_id = p_futi_id
    AND uspe_id= p_uspe_id
    AND apgrin2_id = p_apgrin2_id;
    
       IF SQL%NOTFOUND THEN
          p_codigo_error := 1;
          p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ACTUALIZAR CON LOS IDENTIFICADORES DADOS EN LA TABLA SIPSA_APROBA_GRIN2';
       END IF; 
           
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL ACTUALIZAR LA TABLA SIPSA_APROBA_GRIN2 SE PRESENTO'||SQLERRM;                                                                                   
END pr_ActualizarAPGRIN2;

PROCEDURE pr_InsertarAPGRIN2_VACA
/********************************************************************************
 Descripcion   : metodo para incorporar informacion sobre la tabla SIPSA_APROBA_GRIN2_VACA

 parametros    :
 in            :     p_futi_id
                     p_uspe_id
                     p_apgrin2_id
                     p_arti_id
                     p_usuario


 out           :    p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                     p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 15/04/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
( p_futi_id               IN  SIPSA_APROBA_GRIN2_VACA.futi_id%TYPE,
  p_uspe_id   IN  SIPSA_APROBA_GRIN2_VACA.uspe_id%TYPE,
  p_apgrin2_id IN SIPSA_APROBA_GRIN2_VACA.apgrin2_id%TYPE,
  p_cara_id IN SIPSA_APROBA_GRIN2_VACA.cara_id%TYPE,  
  p_vape_id IN SIPSA_APROBA_GRIN2_VACA.vape_id%TYPE,
  p_valor IN SIPSA_APROBA_GRIN2_VACA.valor%TYPE,
  p_usuario                       IN SIPSA_APROBA_GRIN2_VACA.apgrin2vaca_usua_creacion%TYPE,
  p_codigo_error                  OUT NUMBER,
  p_mensaje                       OUT VARCHAR2) IS

contador NUMBER;
--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador := NULL;

    SELECT COUNT(1) INTO contador FROM SIPSA_APROBA_GRIN2_VACA
     WHERE futi_id = p_futi_id
    AND uspe_id= p_uspe_id
    AND apgrin2_id = p_apgrin2_id
    AND cara_id = p_cara_id
    AND vape_id = p_vape_id;

    IF contador = 0 THEN
        --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
        INSERT INTO    SIPSA_APROBA_GRIN2_VACA (futi_id,uspe_id,apgrin2_id,cara_id,vape_id,valor,apgrin2vaca_usua_creacion)
        VALUES(p_futi_id,p_uspe_id,p_apgrin2_id,p_cara_id,p_vape_id,p_valor,UPPER(p_usuario));
    END IF;     
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_APROBA_GRIN2_VACA SE PRESENTO '||SQLERRM;                                                                                   
END pr_InsertarAPGRIN2_VACA;

PROCEDURE pr_ActualizarAPGRIN2_VACA
/********************************************************************************
 Descripcion   : metodo para incorporar informacion sobre la tabla SIPSA_APROBA_GRIN2_VACA

 parametros    :
 in            :     p_futi_id
                     p_uspe_id
                     p_apgrin2_id
                     p_arti_id
                     p_usuario


 out           :    p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                     p_mensaje           mensaje del error ocurrido

 Realizado por : Vitaliano Corredor
 Fecha Creacion: 15/04/2013

 Modificado por:
 Fecha Modificacion:
 Cambios       :
*******************************************************************************/
( p_futi_id               IN  SIPSA_APROBA_GRIN2_VACA.futi_id%TYPE,
  p_uspe_id   IN  SIPSA_APROBA_GRIN2_VACA.uspe_id%TYPE,
  p_apgrin2_id IN SIPSA_APROBA_GRIN2_VACA.apgrin2_id%TYPE,
  p_cara_id IN SIPSA_APROBA_GRIN2_VACA.cara_id%TYPE,  
  p_vape_id IN SIPSA_APROBA_GRIN2_VACA.vape_id%TYPE,
  p_valor IN SIPSA_APROBA_GRIN2_VACA.valor%TYPE,
  p_usuario                       IN SIPSA_APROBA_GRIN2_VACA.apgrin2vaca_usua_modificacion%TYPE,
  p_codigo_error                  OUT NUMBER,
  p_mensaje                       OUT VARCHAR2) IS


BEGIN
    p_codigo_error := 0;
    p_mensaje      := NULL;

    UPDATE SIPSA_APROBA_GRIN2_VACA
    SET valor = p_valor
    WHERE futi_id = p_futi_id
    AND uspe_id = p_uspe_id
    AND apgrin2_id = p_apgrin2_id
    AND cara_id = p_cara_id
    AND vape_id = p_vape_id
    AND apgrin2vaca_usua_creacion = UPPER(p_usuario);
        
       IF SQL%NOTFOUND THEN
          p_codigo_error := 1;
          p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ACTUALIZAR CON LOS IDENTIFICADORES DADOS EN LA TABLA SIPSA_APROBA_GRIN2_VACA';
       END IF; 
   
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_APROBA_GRIN2_VACA SE PRESENTO '||SQLERRM;                                                                                   
END pr_ActualizarAPGRIN2_VACA;

PROCEDURE pr_InsertarAPENCI2
/********************************************************************************
 DESCRIPCION   : Metodo para incorporar informacion sobre la tabla SIPSA_APROBA_ENCA_INSUMOS2

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
 p_mensaje                     OUT VARCHAR2) IS

contador NUMBER;
--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador := NULL;

    SELECT COUNT(1) INTO contador FROM SIPSA_APROBA_ENCA_INSUMOS2
     WHERE futi_id = p_futi_id
    AND uspe_id= p_uspe_id
    AND arti_id = p_arti_id
    AND apgrin2_id = p_grin2_id
    AND prre_fecha_programada = p_prre_fecha_programada;


    IF contador = 0 THEN
        --p_id    := Pq_Gen_Tablas_Secuencias.fn_ObtenerLlave(p_sistema,'SIPSA_ENCA_INSUMOS2');
        --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
        INSERT INTO SIPSA_APROBA_ENCA_INSUMOS2(futi_id,uspe_id,arti_id,apgrin2_id,prre_fecha_programada,apenin2_arti_nombre,apenin2_novedad,
        apenin2_ftes_capturadas,obse_id,apenin2_observacion,apenin2_usuario_creacion)
        VALUES (p_futi_id, p_uspe_id,p_arti_id, p_grin2_id, p_prre_fecha_programada,p_arti_nombre, p_novedad, 
                     p_ftes_capturadas,p_obse_id, p_observacion,p_usuario);
   ELSE
        PQ_SIPSA_APROBACIONES.pr_ActualizarAPENCI2(p_futi_id, p_uspe_id,p_arti_id, p_grin2_id, p_prre_fecha_programada,p_arti_nombre,p_novedad, 
                                                                                 p_ftes_capturadas,p_obse_id, p_observacion, p_usuario, p_codigo_error,p_mensaje);
   END IF;     
EXCEPTION
       WHEN OTHERS THEN
            IF SQLCODE = -1 THEN
             --p_codigo_error := 1;
             --p_mensaje      := 'YA EXISTE LA FUENTE: '||p_futi_id ||' Y EL USUARIO:' ||p_uspe_id ||' EN LA FECHA:' ||p_prre_fecha_programada ;
            --ELSE
             p_codigo_error := 1;
             p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_APROBA_ENCA_INSUMOS2 SE PRESENTO '||SQLERRM;
            END IF;
END pr_InsertarAPENCI2;

PROCEDURE pr_ActualizarAPENCI2
/********************************************************************************
 DESCRIPCION   : Metodo para actualizar informacion sobre la tabla SIPSA_APROBA_ENCA_INSUMOS2

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
 p_mensaje                     OUT VARCHAR2) IS


--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
    UPDATE SIPSA_APROBA_ENCA_INSUMOS2
    SET apenin2_novedad = p_novedad, 
            apenin2_ftes_capturadas= p_ftes_capturadas, 
           obse_id = p_obse_id, apenin2_observacion = p_observacion, 
           apenin2_usuario_modificacion = p_usuario
    WHERE futi_id = p_futi_id
    AND uspe_id =p_uspe_id
    AND arti_id = p_arti_id
    AND apgrin2_id = p_grin2_id
    AND  prre_fecha_programada = p_prre_fecha_programada;
    
       IF SQL%NOTFOUND THEN
          p_codigo_error := 1;
          p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ACTUALIZAR CON LOS IDENTIFICADORES DADOS EN LA TABLA SIPSA_APROBA_ENCA_INSUMOS2';
       END IF; 
    
EXCEPTION
       WHEN OTHERS THEN
            IF SQLCODE = -1 THEN
             p_codigo_error := 1;
             p_mensaje      := 'AL ACTUALIZAR EN LA TABLA SIPSA_APROBA_ENCA_INSUMOS2 SE PRESENTO '||SQLERRM;
            END IF;
END pr_ActualizarAPENCI2;



PROCEDURE pr_InsertarAPRECI2
/********************************************************************************
 Descripcion   : metodo para incorporar informacion sobre la tabla SIPSA_APROBA_RECO_INSUMOS2

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
 p_esta_id  IN SIPSA_APROBA_RECO_INSUMOS2.esta_id%TYPE,
 p_usuario                       IN SIPSA_APROBA_RECO_INSUMOS2.aprein2_usuario_creacion%TYPE,
 p_codigo_error               OUT NUMBER,
 p_mensaje                     OUT VARCHAR2) IS

contador NUMBER;
--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador := NULL;

    SELECT COUNT(1) INTO contador FROM SIPSA_APROBA_RECO_INSUMOS2
     WHERE futi_id = p_futi_id
    AND uspe_id= p_uspe_id
    AND arti_id = p_arti_id
    AND apgrin2_id = p_grin2_id
    AND prre_fecha_programada = p_prre_fecha_programada
    AND aprein2_id_informante = p_id_informante
    AND aprein2_nom_informante =p_nom_informante
    AND aprein2_tel_informante = p_tel_informante;

    IF contador = 0 THEN
        --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
        INSERT INTO    SIPSA_APROBA_RECO_INSUMOS2 (futi_id,uspe_id,arti_id,apgrin2_id,prre_fecha_programada,aprein2_novedad_gral,aprein2_id_informante,aprein2_nom_informante,aprein2_tel_informante,
                                                                             aprein2_precio_recolectado,aprein2_novedad,obse_id,aprein2_observacion,aprein2_fecha_recoleccion,esta_id,aprein2_usuario_creacion)
        VALUES(p_futi_id,p_uspe_id,p_arti_id,p_grin2_id,p_prre_fecha_programada,p_novedad_gral,p_id_informante,p_nom_informante,p_tel_informante,
                     p_precio_recolectado,p_novedad,p_obse_id,p_rein2_observacion,p_fecha_recoleccion,p_esta_id,p_usuario);
    ELSE
        PQ_SIPSA_APROBACIONES.pr_ActualizarAPRECI2(p_futi_id,p_uspe_id,p_arti_id,p_grin2_id,p_prre_fecha_programada,p_novedad_gral,p_id_informante,
                                                                                 p_nom_informante,p_tel_informante,p_precio_recolectado,p_novedad,p_obse_id,p_rein2_observacion,
                                                                                 p_fecha_recoleccion,p_esta_id,p_usuario,p_codigo_error,p_mensaje);
    END IF;
                  
    EXCEPTION
        WHEN OTHERS THEN
            --p_codigo_error := 1;
             --p_mensaje      := 'YA EXISTE LA FUENTE: '||p_futi_id ||' Y EL USUARIO:' ||p_uspe_id ||' EN LA FECHA:' ||p_prre_fecha_programada ;
            --ELSE
            p_codigo_error := 1;
            p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_APROBA_RECO_INSUMOS2 SE PRESENTO '||SQLERRM;                                                                                   
END pr_InsertarAPRECI2;


PROCEDURE pr_ActualizarAPRECI2
/********************************************************************************
 Descripcion   : metodo para actualizar informacion sobre la tabla SIPSA_APROBA_RECO_INSUMOS2

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
 p_mensaje                     OUT VARCHAR2) IS

--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   
   UPDATE SIPSA_APROBA_RECO_INSUMOS2
   SET aprein2_novedad_gral = p_novedad_gral, aprein2_precio_recolectado = p_precio_recolectado, aprein2_novedad = p_novedad, obse_id = p_obse_id, 
         aprein2_observacion = p_rein2_observacion, aprein2_fecha_recoleccion = p_fecha_recoleccion, aprein2_usuario_modificacion = p_usuario
   WHERE futi_id = p_futi_id
   AND uspe_id = p_uspe_id
   AND arti_id = p_arti_id
   AND apgrin2_id= p_grin2_id
   AND prre_fecha_programada = p_prre_fecha_programada
   AND aprein2_id_informante = p_id_informante
   AND aprein2_nom_informante = p_nom_informante
   AND aprein2_tel_informante = p_tel_informante;  
   
       IF SQL%NOTFOUND THEN
          p_codigo_error := 1;
          p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ACTUALIZAR CON LOS IDENTIFICADORES DADOS EN LA TABLA SIPSA_APROBA_RECO_INSUMOS2';
       END IF; 
          
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL ACTUALIZAR EN LA TABLA SIPSA_APROBA_RECO_INSUMOS2 SE PRESENTO '||SQLERRM;                                                                                   
END pr_ActualizarAPRECI2;

PROCEDURE pr_ConsultarAprobaInsumos
/********************************************************************************
 DESCRIPCION   : Metodo para consultar informacion de abastecimientos en la tabla sipsa_aproba

 PARAMETROS    :
 IN            : 

                 p_muni_id
                 p_prre_fecha_programada


 OUT           : 
                 p_resultado
                 p_codigo_error      Valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           Mensaje del error ocurrido

 REALIZADO POR : Carlos Alberto Lopez n.

 FECHA CREACION: 30/08/2013

 MODIFICADO POR:
 FECHA MODIFICA:
 CAMBIOS       :
*******************************************************************************/
(
p_prre_fecha_programada_ini IN VARCHAR2,
p_prre_fecha_programada_fin IN VARCHAR2,
p_tire_id                   IN sipsa_tipo_recolecciones.tire_id%TYPE,
p_resultado                 OUT Pq_Gen_General.ty_cursor,
p_codigo_error              OUT NUMBER,
p_mensaje                   OUT VARCHAR2) IS


 v_fecha_ini DATE;
 p_prre_fecha VARCHAR(10);
 v_consulta  VARCHAR(30000);
BEGIN

  p_codigo_error := 0;
  p_mensaje      := NULL;
  
  
  IF (p_tire_id =4 OR p_tire_id =5 OR p_tire_id =6 OR p_tire_id =7) THEN 
          v_consulta:= 'select ' ||
                   'decode ( ' ||
                   '(select dept.dept_id ' ||
                   'from sipsa_fuentes_tipo_recoleccion futi, sipsa_fuentes fuen, sipsa_departamentos dept, sipsa_municipios muni ' ||
                   'where 1=1 ' ||
                   'and futi.futi_id = reco.apin_futi_id ' ||
                   'and futi.fuen_id = fuen.fuen_id ' ||
                   'and muni.muni_id = fuen.muni_id ' ||
                   'and dept.dept_id = muni.dept_id ' ||
                   '), null, ' ||
                   '(select dept.dept_id ' ||
                   'from sipsa_aproba_fuen_info info, sipsa_departamentos dept,sipsa_municipios muni ' || 
                   'where 1=1 ' ||
                   'and info.apfuin_futi_id = reco.apin_futi_id ' ||
                   'and info.apfuin_fuen_id = reco.apin_fuen_id ' ||
                   'and info.tire_id = reco.tire_id ' ||
                   'and info.uspe_id = reco.uspe_id ' ||
                   'and muni.muni_id = info.muni_id ' ||
                   'and dept.dept_id = muni.dept_id ' ||
                   'and rownum=1 ' ||
                   '), '|| 
                   '(select dept.dept_id ' ||
                   'from sipsa_fuentes_tipo_recoleccion futi, sipsa_fuentes fuen, sipsa_departamentos dept, sipsa_municipios muni ' ||
                   'where 1=1 ' ||
                   'and futi.futi_id = reco.apin_futi_id ' ||
                   'and futi.fuen_id = fuen.fuen_id ' ||
                   'and muni.muni_id = fuen.muni_id ' ||
                   'and dept.dept_id = muni.dept_id ' ||
                   ')) dept_id, ' ||
                   'decode ( ' ||
                   '(select muni.muni_id ' ||
                   'from sipsa_fuentes_tipo_recoleccion futi, sipsa_fuentes fuen, sipsa_municipios muni ' ||
                   'where 1=1 ' ||
                   'and futi.futi_id = reco.apin_futi_id ' ||
                   'and futi.fuen_id = fuen.fuen_id ' ||
                   'and muni.muni_id = fuen.muni_id ' ||
                   '), null, ' ||
                   '(select muni.muni_id ' ||
                   'from sipsa_aproba_fuen_info info, sipsa_municipios muni ' ||
                   'where 1=1 ' ||
                   'and info.apfuin_futi_id = reco.apin_futi_id ' ||
                   'and info.apfuin_fuen_id = reco.apin_fuen_id ' ||
                   'and info.tire_id = reco.tire_id ' ||
                   'and info.uspe_id = reco.uspe_id ' ||
                   'and muni.muni_id = info.muni_id ' ||
                   'and rownum=1 ' ||
                   ')	, ' ||
                   '(select muni.muni_id ' ||
                   'from sipsa_fuentes_tipo_recoleccion futi, sipsa_fuentes fuen,  sipsa_municipios muni ' ||
                   'where 1=1 ' ||
                   'and futi.futi_id = reco.apin_futi_id ' ||
                   'and futi.fuen_id = fuen.fuen_id ' ||
                   'and muni.muni_id = fuen.muni_id ' ||
                   ')) muni_id, ' ||
                   'decode ( ' || 
                   '(select muni.muni_nombre ' || 
                   'from sipsa_fuentes_tipo_recoleccion futi, sipsa_fuentes fuen, sipsa_municipios muni ' || 
                   'where 1=1 ' || 
                   'and futi.futi_id = reco.apin_futi_id ' || 
                   'and futi.fuen_id = fuen.fuen_id ' || 
                   'and muni.muni_id = fuen.muni_id ' || 
                   '), null, ' || 
                   '(select muni.muni_nombre ' || 
                   'from sipsa_aproba_fuen_info info, sipsa_municipios muni ' || 
                   'where 1=1 ' || 
                   'and info.apfuin_futi_id = reco.apin_futi_id ' || 
                   'and info.apfuin_fuen_id = reco.apin_fuen_id ' || 
                   'and info.tire_id = reco.tire_id ' || 
                   'and info.uspe_id = reco.uspe_id ' || 
                   'and muni.muni_id = info.muni_id ' || 
                   'and rownum=1 ' || 
                   '), ' ||  
                   '(select muni.muni_nombre ' ||
                   'from sipsa_fuentes_tipo_recoleccion futi, sipsa_fuentes fuen,  sipsa_municipios muni ' || 
                   'where 1=1 ' || 
                   'and futi.futi_id = reco.apin_futi_id ' || 
                   'and futi.fuen_id = fuen.fuen_id ' || 
                   'and muni.muni_id = fuen.muni_id ' || 
                   ')) muni_nombre, ' || 
                   'reco.apin_futi_id futi_id, reco.apin_fuen_id fuen_id, ' ||
                   'decode ( ' ||
                   '(select fuen.fuen_nombre ' ||
                   'from sipsa_fuentes_tipo_recoleccion futi, sipsa_fuentes fuen ' ||
                   'where 1=1 ' ||
                   'and futi.futi_id = reco.apin_futi_id ' ||
                   'and futi.fuen_id = fuen.fuen_id ' ||
                   '), null, ' ||
                   '(select info.apfuin_fuen_nombre ' ||
                   'from sipsa_aproba_fuen_info info ' ||
                   'where 1=1 ' ||
                   'and info.apfuin_futi_id = reco.apin_futi_id ' ||
                   'and info.apfuin_fuen_id = reco.apin_fuen_id ' ||
                   'and info.tire_id = reco.tire_id ' || 
                   'and info.uspe_id = reco.uspe_id ' ||
                   'and rownum=1 ' ||
                   '), ' ||
                   '(select fuen.fuen_nombre ' ||
                   'from sipsa_fuentes_tipo_recoleccion futi, sipsa_fuentes fuen ' ||
                   'where 1=1 ' ||
                   'and futi.futi_id = reco.apin_futi_id ' ||
                   'and futi.fuen_id = fuen.fuen_id ' ||
                   ')) fuen_nombre, ' ||
                   '(select fuen.fuen_cod_interno ' || 
                   'from sipsa_fuentes_tipo_recoleccion futi, sipsa_fuentes fuen ' || 
                   'where 1=1 ' || 
                   'and futi.futi_id = reco.apin_futi_id ' || 
                   'and futi.fuen_id = fuen.fuen_id ' || 
                   ') fuen_cod_interno, ' ||
                   'decode ( ' ||
                   '(select fuen.fuen_nombre ' ||
                   'from sipsa_fuentes_tipo_recoleccion futi, sipsa_fuentes fuen ' ||
                   'where 1=1 ' ||
                   'and futi.futi_id = reco.apin_futi_id ' ||
                   'and futi.fuen_id = fuen.fuen_id ' ||
                   '), null, ' ||
                   '(select info.apfuin_fuen_direccion ' ||
                   'from sipsa_aproba_fuen_info info ' ||
                   'where 1=1 ' ||
                   'and info.apfuin_futi_id = reco.apin_futi_id ' ||
                   'and info.apfuin_fuen_id = reco.apin_fuen_id ' ||
                   'and info.tire_id = reco.tire_id ' || 
                   'and info.uspe_id = reco.uspe_id ' ||
                   'and rownum=1 ' ||
                   '), ' ||
                   'null) fuen_direccion, ' ||
                   'decode ( ' ||
                   '(select fuen.fuen_nombre ' ||
                   'from sipsa_fuentes_tipo_recoleccion futi, sipsa_fuentes fuen ' ||
                   'where 1=1 ' ||
                   'and futi.futi_id = reco.apin_futi_id ' ||
                   'and futi.fuen_id = fuen.fuen_id ' ||
                   '), null, ' ||
                   '(select info.apfuin_fuen_telefono ' ||
                   'from sipsa_aproba_fuen_info info ' ||
                   'where 1=1 ' ||
                   'and info.apfuin_futi_id = reco.apin_futi_id ' ||
                   'and info.apfuin_fuen_id = reco.apin_fuen_id ' ||
                   'and info.tire_id = reco.tire_id ' || 
                   'and info.uspe_id = reco.uspe_id ' ||
                   'and rownum=1 ' ||
                   '), ' ||
                   'null) fuen_telefono, ' ||
                   'decode ( ' ||
                   '(select fuen.fuen_nombre ' ||
                   'from sipsa_fuentes_tipo_recoleccion futi, sipsa_fuentes fuen ' ||
                   'where 1=1 ' ||
                   'and futi.futi_id = reco.apin_futi_id ' ||
                   'and futi.fuen_id = fuen.fuen_id ' ||
                   '), null, ' ||
                   '(select info.apfuin_fuen_email ' ||
                   'from sipsa_aproba_fuen_info info ' ||
                   'where 1=1 ' ||
                   'and info.apfuin_futi_id = reco.apin_futi_id ' ||
                   'and info.apfuin_fuen_id = reco.apin_fuen_id ' ||
                   'and info.tire_id = reco.tire_id ' || 
                   'and info.uspe_id = reco.uspe_id ' ||
                   'and rownum=1 ' ||
                   '), ' ||
                   'null) fuen_email, ' ||
                   'decode ( ' ||
                   '(select fuen.fuen_nombre ' ||
                   'from sipsa_fuentes_tipo_recoleccion futi, sipsa_fuentes fuen ' ||
                   'where 1=1 ' ||
                   'and futi.futi_id = reco.apin_futi_id ' ||
                   'and futi.fuen_id = fuen.fuen_id ' ||
                   '), null, ' ||
                   '(select info.apfuin_info_nombre '||
                   'from sipsa_aproba_fuen_info info ' ||
                   'where 1=1 ' ||
                   'and info.apfuin_futi_id = reco.apin_futi_id ' ||
                   'and info.apfuin_fuen_id = reco.apin_fuen_id ' ||
                   'and info.tire_id = reco.tire_id ' ||
                   'and info.uspe_id = reco.uspe_id ' ||
                   'and rownum=1 ' ||                   
                   '), ' ||
                   'null) fuen_informante, ' ||
                   '(select info.apfuin_fecha_visita ' ||
                   'from sipsa_aproba_fuen_info info ' || 
                   'where 1=1 ' || 
                   'and info.apfuin_futi_id = reco.apin_futi_id ' || 
                   'and info.apfuin_fuen_id = reco.apin_fuen_id ' || 
                   'and info.tire_id = reco.tire_id ' || 
                   'and info.uspe_id = reco.uspe_id ' ||                    
                   'and rownum = 1 ' ||
                   ') fecha_visita,reco.apin_prre_fecha_programada, reco.tire_id, ' ||
                   'null id_informante, null nom_informante, null tel_informante, reco.apin_arti_id, ' ||
                   'decode( ' ||
                   '(select arti.arti_nombre ' ||
                   'from sipsa_articulos arti ' ||
                   'where 1=1 ' ||
                   'and arti.arti_id = reco.apin_arti_id ' ||
                   '), null,reco.apin_arti_nombre, ' ||
                   '(select arti.arti_nombre ' ||
                   'from sipsa_articulos arti ' ||
                   'where 1=1 ' ||
                   'and arti.arti_id = reco.apin_arti_id ' ||
                   ')) arti_nombre, ' ||
                   '(select arti.arti_cod_interno ' || 
                   'from sipsa_articulos arti ' || 
                   'where 1=1 ' || 
                   'and arti.arti_id = reco.apin_arti_id ' || 
                   ')arti_cod_interno,
                   (select arti.grup_id ' || 
                   'from sipsa_articulos arti ' || 
                   'where 1=1 ' || 
                   'and arti.arti_id = reco.apin_arti_id ' || 
                   ')grup_id, ' ||
                   '(select arti.subg_id ' || 
                   'from sipsa_articulos arti ' || 
                   'where 1=1 ' || 
                   'and arti.arti_id = reco.apin_arti_id ' || 
                   ')subgrup_id, ' ||
                   'reco.apin_precio_recolectado, reco.apin_novedad, reco.obse_id, ' ||
                   'decode (reco.obse_id, null,reco.apin_observacion,(select obse.obse_descripcion ' ||
                   'from sipsa_observaciones obse where obse.obse_id = reco.obse_id )) observacion , ' ||
                   'uspe.uspe_id, usua.usua_usuario, null apgrin2_id, null Caracteristica, reco.apin_fecha_recoleccion, ' ||
                   'reco.apin_articaco_id, reco.apin_caco_id, ' ||
                   'decode( ' ||
                   '(select caco.caco_nombre ' ||
                   'from sipsa_casas_comerciales caco ' ||
                   'where 1=1 ' ||
                   'and caco.caco_id = reco.apin_caco_id ' ||
                   '), null, reco.apin_casa_comercial, ' ||
                   '(select caco.caco_nombre ' ||
                   'from sipsa_casas_comerciales caco ' ||
                   'where 1=1 ' ||
                   'and caco.caco_id = reco.apin_caco_id ' ||
                   ')) casaComercial, ' ||
                   'decode( ' ||
                   '(select arca.articaco_regica_linea ' ||
                   'from sipsa_arti_caco arca ' ||
                   'where 1=1 ' ||
                   'and arca.articaco_id = reco.apin_articaco_id ' ||
                   '), null,reco.apin_regica_linea, ' ||
                   '(select arca.articaco_regica_linea ' ||
                   'from sipsa_arti_caco arca ' ||
                   'where 1=1 ' ||
                   'and arca.articaco_id = reco.apin_articaco_id ' ||
                   ')) apin_regica_linea, ' ||
                   'reco.esta_id,  reco.unme_id, tipr.tipr_id,tipr.tipr_nombre, unme.unme_nombre_ppal, unme.unme_cantidad_ppal, ' ||
                   'unme.unme_nombre_2, unme.unme_cantidad_2 ' ||
                   'from  sipsa_aproba_insumos reco, sipsa_usuarios_perfiles uspe, sipsa_usuarios usua, sipsa_tipo_presentaciones tipr, ' ||
                   'sipsa_unidades_medida unme ' ||
                   'where 1=1 ' ||
                   'and reco.uspe_id = uspe.uspe_id ' ||
                   'and usua.usua_id = uspe.usua_id ' ||
                   'and reco.unme_id = unme.unme_id ' ||
                   'and tipr.tipr_id = unme.tipr_id ' ||
                   'and reco.apin_prre_fecha_programada ' ||
                   'between to_date(' || '' || p_prre_fecha_programada_ini || '' || ',''dd/mm/yyyy'') ' ||
                   'and to_date(' || '' ||p_prre_fecha_programada_fin||'' || ' ,''dd/mm/yyyy'') ' ||
                   'and reco.tire_id = ' || p_tire_id  || 
                   'order by 1,2,5,6,9 '
                  ;   
  ELSE              
         
      v_consulta := 'select dept.dept_id, muni.muni_id,muni.muni_nombre, futi.futi_id, fuen.fuen_id,fuen.fuen_nombre,fuen.fuen_cod_interno fuen_cod_interno, null fuen_direccion, ' ||
                    'null fuen_telefono, null fuen_email, null fuen_informante, null fecha_visita,reco.prre_fecha_programada, futi.tire_id,  ' ||
                    'reco.aprein2_id_informante, ' ||
                    'decode( ' ||  
                    '(select info.info_nombre ' ||  
                    'from sipsa_informantes info ' ||  
                    'where 1=1 ' ||  
                    'and info.info_id  = reco.aprein2_id_informante ' ||  
                    '),null, reco.aprein2_nom_informante, ' ||  
                    '(select info.info_nombre ' ||  
                    'from sipsa_informantes info ' ||  
                    'where 1=1 ' ||  
                    'and info.info_id  = reco.aprein2_id_informante ' ||  
                    ')) aprein2_nom_informante, ' ||  
                    'decode( ' ||  
                    '(select info.info_telefono ' ||  
                    'from sipsa_informantes info ' ||  
                    'where 1=1 ' || 
                    'and info.info_id  = reco.aprein2_id_informante ' ||  
                    '),null, reco.aprein2_tel_informante, ' || 
                    '(select info.info_telefono ' || 
                    'from sipsa_informantes info ' || 
                    'where 1=1 ' || 
                    'and info.info_id  = reco.aprein2_id_informante ' || 
                    ')) aprein2_tel_informante, ' || 
                    'reco.arti_id, ' ||
                    'decode( ' ||
                    '(select arti.arti_nombre ' ||
                    'from sipsa_articulos arti ' ||
                    'where 1=1 ' ||
                    'and arti.arti_id = enca.arti_id ' ||
                    '), null,enca.apenin2_arti_nombre, ' ||
                    '(select arti.arti_nombre ' ||
                    'from sipsa_articulos arti ' ||
                    'where 1=1 ' ||
                    'and arti.arti_id =  enca.arti_id ' ||
                    ')) arti_nombre,
                    (select arti.arti_cod_interno ' ||
                    'from sipsa_articulos arti ' ||
                    'where 1=1 ' ||
                    'and arti.arti_id =  enca.arti_id ' ||
                    ') arti_cod_interno, ' ||
                    '(select arti.grup_id ' ||
                    'from sipsa_articulos arti ' ||
                    'where 1=1 ' ||
                    'and arti.arti_id =  enca.arti_id ' ||
                    ') grup_id, ' ||
                    '(select arti.subg_id ' ||
                    'from sipsa_articulos arti ' ||
                    'where 1=1 ' ||
                    'and arti.arti_id =  enca.arti_id ' ||
                    ') subgrup_id, ' ||
                    'reco.aprein2_precio_recolectado, enca.apenin2_novedad, reco.obse_id, ' ||
                    'decode (reco.obse_id, null,reco.aprein2_observacion,(select obse.obse_descripcion from sipsa_observaciones obse where obse.obse_id = reco.obse_id )) observacion, ' ||
                    'uspe.uspe_id, usua.usua_usuario, reco.apgrin2_id,  '||
                    'decode( ' ||
                    'PQ_SIPSA_GRUPO_INSUMOS2.fn_ObtenerCaracteristica(reco.apgrin2_id, reco.arti_id), ' ||
                    'null,  ' ||
                    'PQ_SIPSA_APROBA_GRIN2.fn_ObtenerNuevaCaracteristica(reco.apgrin2_id, reco.arti_id, reco.futi_id), ' ||
                    'PQ_SIPSA_GRUPO_INSUMOS2.fn_ObtenerCaracteristica(reco.apgrin2_id, reco.arti_id)) Caracteristica, ' ||
                    'reco.aprein2_fecha_recoleccion, null articaco_id, null caco_id, null casaComercial, null apin_regica_linea, ' ||
                    'reco.esta_id, null unme_id, null tipr_id, null tipr_nombre, null unme_nombre_ppal, null unme_cantidad_ppal, null unme_nombre_2, ' ||
                    'null unme_cantidad_2 ' ||
                    'from sipsa_aproba_reco_insumos2 reco, sipsa_aproba_enca_insumos2 enca , ' ||
                    'sipsa_fuentes_tipo_recoleccion futi, sipsa_fuentes fuen, sipsa_municipios muni, sipsa_departamentos dept, ' ||
                    'sipsa_usuarios_perfiles uspe, sipsa_usuarios usua ' ||
                    'where 1=1 ' ||
                    'and reco.futi_id = enca.futi_id ' ||
                    'and reco.uspe_id = enca.uspe_id ' ||
                    'and reco.arti_id = enca.arti_id ' ||
                    'and reco.apgrin2_id = enca.apgrin2_id ' ||
                    'and reco.prre_fecha_programada = enca.prre_fecha_programada ' ||
                    'and futi.futi_id = reco.futi_id ' ||
                    'and fuen.fuen_id = futi.fuen_id ' ||
                    'and muni.muni_id = fuen.muni_id ' ||
                    'and dept.dept_id = muni.dept_id ' ||
                    'and uspe.uspe_id = reco.uspe_id ' ||
                    'and usua.usua_id = uspe.usua_id ' ||
                    'and reco.prre_fecha_programada ' ||
                    'between to_date(' || '' || p_prre_fecha_programada_ini || '' || ',''dd/mm/yyyy'') ' ||
                    'and to_date(' || '' ||p_prre_fecha_programada_fin||'' || ' ,''dd/mm/yyyy'') ' ||
                    'and futi.tire_id = ' || p_tire_id  ||
                    'order by 1,2,5,6,9 ' 
                    ;
  END IF;
   DBMS_OUTPUT.PUT_LINE(v_consulta);   
   --insert into prueba values(v_consulta);
   OPEN p_resultado FOR v_consulta;
  
  EXCEPTION
    WHEN OTHERS THEN
      p_codigo_error := 1;
      p_mensaje      := 'AL CONSULTAR LA TABLA SIPSA APROBACIONES SE PRESENTO '||SQLERRM;

END pr_ConsultarAprobaInsumos;

PROCEDURE pr_AprobarInsumos1
(
/********************************************************************************
 DESCRIPCION   : Metodo para insertar informacion de aprobaciones en la tabla sipsa_insumos

 PARAMETROS    :
 IN            : 

                 p_muni_id
                 p_prre_fecha_programada


 OUT           : 
                 p_resultado
                 p_codigo_error      Valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           Mensaje del error ocurrido

 REALIZADO POR : Carlos Alberto López Narváez.

 FECHA CREACION: 30/09/2013

 MODIFICADO POR:
 FECHA MODIFICA:
 CAMBIOS       :
*******************************************************************************/
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

) IS

v_excepcion                 EXCEPTION;
v_arti_id                   NUMBER;
v_caco_id                   NUMBER;
v_articaco_id               NUMBER;
v_fuen_id                   NUMBER;
v_futi_id                   NUMBER;
v_unme_id                   NUMBER;
v_valida_unmetire           NUMBER;
v_valida_fuar               NUMBER;
v_uspe_id                   NUMBER;
v_usua_id                   NUMBER;
v_valida_prog               NUMBER;
v_arti_nombre               VARCHAR2(100);
v_caco_nombre               VARCHAR2(100);
v_fuen_nombre               VARCHAR2(100);
v_mes                       NUMBER;
v_precio_anterior           NUMBER;
v_fecha_hasta               DATE;
v_apro_reco                 NUMBER;
v_reco_temp_futi            NUMBER;
v_reco_temp_count           NUMBER;
BEGIN

  p_codigo_error := 0;
  p_mensaje      := NULL;
   --Obtengo el ultimo dia del año para utilizarlos en fechas de vencimiento
  v_fecha_hasta:= to_date('12/31/' || to_char(SYSDATE,'YYYY'),'mm/dd/yyyy');
  
  
  --Valida el articulo
  v_arti_nombre := PQ_SIPSA_ARTICULOS.fn_ObtenerNombre(p_arti_id);
  IF v_arti_nombre IS NULL THEN
     v_arti_id  := PQ_SIPSA_ARTICULOS.fn_ObtenerArtiId(p_arti_nombre);
     IF v_arti_id IS NULL THEN
        PQ_SIPSA_ARTICULOS.pr_Insertar(v_arti_id,p_grupo_id,p_subgrupo_id,p_arti_cod_interno,p_arti_nombre,
                             0,0,0,0,0,0,0,p_usuario,p_codigo_error,p_mensaje);
     
        IF v_arti_id IS NULL THEN
           p_mensaje := 'ERROR AL INSERTAR ARTICULO, ' || p_mensaje;
           RAISE v_excepcion;
        ELSE
           --Si es Nuevo se inserta en arti_tire
           PQ_SIPSA_ARTI_TIRE.pr_Insertar(v_arti_id,p_tire_id,v_fecha_hasta,p_usuario,p_codigo_error,p_mensaje);  
           IF p_codigo_error =1 THEN
              p_mensaje := 'ERROR AL INSERTAR ARTICULO TIPO RECOLECCIÓN, ' || p_mensaje;
              RAISE v_excepcion;
           END IF;
        END IF;
     END IF;  
  ELSE 
    v_arti_id  := p_arti_id;
  END IF;  
  
  --Obtengo el nombre del articulo sin modificación
  SELECT INSU.APIN_ARTI_NOMBRE INTO v_arti_nombre
  FROM SIPSA_APROBA_INSUMOS INSU
  WHERE 1=1
  AND INSU.APIN_FUTI_ID = p_futi_id  
  AND INSU.APIN_FUEN_ID = p_fuen_id
  AND INSU.TIRE_ID = p_tire_id
  AND INSU.USPE_ID = p_uspe_id
  AND INSU.APIN_PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada
  AND INSU.APIN_ARTICACO_ID = p_articaco_id
  AND INSU.APIN_ARTI_ID = p_arti_id
  AND INSU.APIN_CACO_ID = p_caco_id
  AND INSU.UNME_ID = p_unme_id
   ;
  
               
  --Valida la Casa Comercial
  v_caco_nombre := PQ_SIPSA_CASAS_COMERCIALES.fn_ObtenerCacoNombre(p_caco_id);
  IF v_caco_nombre IS NULL THEN
    v_caco_id := PQ_SIPSA_CASAS_COMERCIALES.fn_ObtenerCacoId(p_casa_comercial);
    IF v_caco_id IS NULL THEN 
       PQ_SIPSA_CASAS_COMERCIALES.pr_Insertar(p_casa_comercial,p_usuario,p_codigo_error,p_mensaje);
       IF p_codigo_error =1 THEN
          p_mensaje := 'ERROR AL INSERTAR CASA COMERCIAL, ' || p_mensaje;
          RAISE v_excepcion;        
       ELSE
          v_caco_id := PQ_SIPSA_CASAS_COMERCIALES.fn_ObtenerCacoId(p_casa_comercial);
         
       END IF;
    END IF;
  ELSE
    v_caco_id := p_caco_id;
  END IF;  

  
  --Obtengo el nombre de la casa comercial antes de ser modificado
  SELECT INSU.APIN_CASA_COMERCIAL INTO v_caco_nombre
  FROM SIPSA_APROBA_INSUMOS INSU
  WHERE 1=1
  AND INSU.APIN_FUTI_ID = p_futi_id  
  AND INSU.APIN_FUEN_ID = p_fuen_id
  AND INSU.TIRE_ID = p_tire_id
  AND INSU.USPE_ID = p_uspe_id
  AND INSU.APIN_PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada
  AND INSU.APIN_ARTICACO_ID = p_articaco_id
  AND INSU.APIN_ARTI_ID = p_arti_id
  AND INSU.APIN_CACO_ID = p_caco_id
  AND INSU.UNME_ID = p_unme_id
  ; 
   
  --Valida Arti_caco     
  v_articaco_id :=  PQ_SIPSA_ARTI_CACO.fn_ObtenerArtiCacoId(v_arti_id,v_caco_id,p_regica_linea);
  IF v_articaco_id IS NULL THEN            
    PQ_SIPSA_ARTI_CACO.pr_Insertar(v_arti_id,v_caco_id, NVL(p_regica_linea,'NA'),v_fecha_hasta,p_usuario,p_codigo_error,p_mensaje); 
    IF p_codigo_error =1 THEN
        p_mensaje := 'ERROR AL INSERTAR ARTICULO CASA COMERCIAL, ' || p_mensaje;
        RAISE v_excepcion; 
    ELSE
        v_articaco_id :=  PQ_SIPSA_ARTI_CACO.fn_ObtenerArtiCacoId(v_arti_id,v_caco_id,p_regica_linea);
    END IF;
  END IF;

--Valida Fuente
  v_fuen_nombre := Pq_SIPSA_Fuentes.fn_ObtenerNombre(p_fuen_id);
    IF v_fuen_nombre IS NULL THEN
        v_fuen_id := Pq_SIPSA_Fuentes.fn_ObtenerIdFuente(p_muni_id,TRIM(p_fuen_nombre) );      
        IF v_fuen_id IS NULL THEN
            Pq_SIPSA_Fuentes.pr_Insertar(p_tire_id,p_fuen_cod_interno,p_muni_id,TRIM(p_fuen_nombre),'1,2,3,4,5,6',p_fuen_direccion,
                 p_fuen_telefono , p_fuen_email,p_fuen_info_nombre,p_fuen_info_cargo,p_fuen_info_telefono,
                 p_fuen_info_email,0,v_fecha_hasta,p_usuario,p_codigo_error,p_mensaje);
            IF p_codigo_error =1 THEN
                p_mensaje := 'ERROR AL INSERTAR FUENTE, ' || p_mensaje;
                 
                RAISE v_excepcion; 
            END IF;
            v_fuen_id := Pq_SIPSA_Fuentes.fn_ObtenerIdFuente(p_muni_id,TRIM(p_fuen_nombre));
        ELSE
            v_fuen_id := Pq_SIPSA_Fuentes.fn_ObtenerIdFuente(p_muni_id,TRIM(p_fuen_nombre));
        END IF;
    ELSE 
        v_fuen_id := p_fuen_id; 
    END IF;
    v_futi_id := PQ_SIPSA_FUENTES_TIPO_RECO.fn_ObtenerFutiId(p_tire_id,v_fuen_id);
    IF (v_futi_id IS NULL) THEN
        PQ_SIPSA_FUENTES_TIPO_RECO.pr_Insertar(v_fuen_id,p_tire_id,'1,2,3,4,5,6',0,v_fecha_hasta,
                                           p_usuario,p_codigo_error,p_mensaje);
            IF p_codigo_error =1 THEN
                p_mensaje := 'ERROR AL INSERTAR FUENTE TIPO, ' || p_mensaje;
                RAISE v_excepcion; 
            END IF;
          v_futi_id := PQ_SIPSA_FUENTES_TIPO_RECO.fn_ObtenerFutiId(p_tire_id,v_fuen_id);
    END IF;                                         
  IF (p_fuen_id > 9000000000) THEN
     --Obtengo el nombre la fuente antes de la modificación
     SELECT INFO.APFUIN_FUEN_NOMBRE INTO v_fuen_nombre
     FROM SIPSA_APROBA_FUEN_INFO INFO
     WHERE 1=1
     AND APFUIN_FUEN_ID = p_fuen_id
     AND APFUIN_FUTI_ID = p_futi_id
     AND TIRE_ID = p_tire_id
     AND MUNI_ID = p_muni_id
     AND USPE_ID = p_uspe_id
     AND APFUIN_FECHA_VISITA = p_fecha_visita
     ;
  END IF;            
    
  --Valida unidad de medida
  v_unme_id := PQ_SIPSA_UNIDADES_MEDIDA.fn_ObtenerUnmeId(p_tipr_id,TRIM(p_unme_nombre_ppal)
                  ,p_unme_cantidad_ppal,TRIM(p_unme_nombre_2),p_unme_cantidad_2);
  IF v_unme_id IS NULL THEN 
    p_mensaje := 'NO SE ENCONTRO LA UNIDAD DE MEDIDA : ' || p_unme_nombre_ppal;
    RAISE v_excepcion; 
  END IF;               
  --Valida Unme Tire
   v_valida_unmetire := PQ_SIPSA_UNME_TIRE.fn_ValidarUnmeTire(v_unme_id,p_tire_id);
   IF v_valida_unmetire IS NULL THEN
      PQ_SIPSA_UNME_TIRE.pr_Insertar(v_unme_id, p_tire_id,v_fecha_hasta, p_usuario,p_codigo_error,p_mensaje);
      IF p_codigo_error =1 THEN
        p_mensaje := 'ERROR AL INSERTAR UNME TIRE, ' || p_mensaje;
        RAISE v_excepcion;
      END IF;
   END IF;
   --Valida en Fuentes Articulos
   v_valida_fuar := PQ_SIPSA_FUENTE_ARTICULOS1.fn_ValidarFuenteArticulos1(v_futi_id, v_articaco_id,
                        v_unme_id);
   IF v_valida_fuar IS NULL THEN
     PQ_SIPSA_FUENTE_ARTICULOS1.pr_Insertar(v_futi_id,v_articaco_id,v_unme_id,1,
                                            p_usuario,p_codigo_error,p_mensaje);
     IF p_codigo_error =1 THEN
        p_mensaje := 'ERROR AL INSERTAR FUENTE ARTICULOS1, ' || p_mensaje;
        RAISE v_excepcion;
      END IF;
   END IF;
   --Valida en Fuente usuario perfiles
   v_uspe_id := PQ_SIPSA_FUEN_USUA_PERF.Fn_ObtenerUspeId(v_futi_id,p_uspe_id);
   IF v_uspe_id IS NULL THEN
      --Obtiene usuario id
      v_usua_id := PQ_SIPSA_USUARIOS.fn_ObtenerUsuarioId(p_usuario_creacion);
      IF v_usua_id IS NULL THEN
         p_mensaje := 'NO SE ENCONTRO ID DEL USUARIO ' || p_usuario_creacion;
         RAISE v_excepcion;
      ELSE
         --Valida en usuario perfiles
         v_uspe_id := PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerUspeId(v_usua_id);
         IF v_uspe_id IS NULL THEN
            p_mensaje := 'NO SE ENCONTRO USUARIO PERFIL DEL USUARIO ' || p_usuario_creacion;
            RAISE v_excepcion;
         ELSE    
            PQ_SIPSA_FUEN_USUA_PERF.pr_Insertar(p_tire_id, v_fuen_id, v_uspe_id,SYSDATE,v_fecha_hasta,
                                                p_usuario,p_codigo_error,p_mensaje);
            IF p_codigo_error =1 THEN
               p_mensaje := 'ERROR AL INSERTAR FUENTE USUARIO PERFIL, ' || p_mensaje;
               RAISE v_excepcion;
            END IF;                                               
         END IF; 
      END IF;   
   END IF;
   --Valida Programación de la fuente
    --XXXXXXXX
   v_valida_prog := PQ_SIPSA_PROG_RECOLECCION.fn_ValidarProgramacion(v_futi_id,p_prre_fecha_programada);
   IF v_valida_prog IS NULL THEN
      PQ_SIPSA_PROG_RECOLECCION.pr_Insertar(v_futi_id,p_prre_fecha_programada,20,
                                            p_usuario,p_codigo_error,p_mensaje);
      IF p_codigo_error =1 THEN
         p_mensaje := 'ERROR AL INSERTAR PROGRAMACION, ' || p_mensaje;
         RAISE v_excepcion;
      END IF; 
   END IF;
   --Obtiene el mes de la fecha programada
   v_mes:= EXTRACT (MONTH FROM p_prre_fecha_programada);
   
   --Obtiene el precio anterior
   
    begin    
        SELECT
            nvl(case when pm.rein_precio_recolectado =0 then 1000 else pm.rein_precio_recolectado end ,1000)
            into v_precio_anterior
        FROM
            sipsa_recoleccion_insumos pm
        WHERE
            v_futi_id = pm.futi_id
            AND   v_articaco_id = pm.articaco_id
            AND   v_unme_id = pm.unme_id
            AND   pm.prre_fecha_programada = (
                SELECT
                    MAX(pm1.prre_fecha_programada)
                FROM
                    sipsa_recoleccion_insumos pm1
                WHERE
                    pm1.futi_id = pm.futi_id
                    AND   pm1.articaco_id = pm.articaco_id
                    AND   pm1.unme_id = pm.unme_id 
                    AND   pm1.prre_fecha_programada < p_prre_fecha_programada )
            ;
    exception when no_data_found then 
          v_precio_anterior:=1000;
    end;
   
   --Insertar en Insumos1
   
   
   -- Se consulta si ya existe el sipsa_recoleccion_insumos
   -- SI ya existe se le cambia el estado a activo
   select  count(recotemp.futi_id) into v_reco_temp_count  
   from sipsa_recoleccion_insumos recoTemp
   where recoTemp.futi_id= v_futi_id
   and recoTemp.uspe_id =v_uspe_id
   and recoTemp.prre_fecha_programada = p_prre_fecha_programada
   and recotemp.articaco_id= v_articaco_id
   and recotemp.unme_id = v_unme_id;
   
   IF (v_reco_temp_count) >0 THEN
      select  recotemp.futi_id into v_reco_temp_futi  
      from sipsa_recoleccion_insumos recoTemp
      where recoTemp.futi_id= v_futi_id
      and recoTemp.uspe_id =v_uspe_id
      and recoTemp.prre_fecha_programada = p_prre_fecha_programada
      and recotemp.articaco_id= v_articaco_id
      and recotemp.unme_id = v_unme_id;
   END IF;   
   
   INSERT INTO SIPSA_RECOLECCION_INSUMOS (futi_id,uspe_id,prre_fecha_programada,articaco_id,unme_id,rein_precio_anterior,rein_novedad_anterior,
                                                                           rein_precio_recolectado,rein_novedad,obse_id, rein_observacion,esta_id,rein_fecha_recoleccion,rein_usuario_creacion)
            VALUES(v_futi_id,v_uspe_id,p_prre_fecha_programada,v_articaco_id,v_unme_id,v_precio_anterior,null,
                p_precio_recolectado,p_novedad,p_obse_id,p_observacion,1,p_fecha_recoleccion,UPPER(p_usuario));
   
   IF SQL%NOTFOUND THEN 
     p_mensaje := 'ERROR AL INSERTAR RECOLECCION_INSUMOS ';
     RAISE v_excepcion;
   ELSE            
     IF (p_fuen_id > 9000000000) THEN
        --Inserta Log de Fuente Aprobaciones
        INSERT INTO SIPSA_APROBA_FUEN_INFO_LOG  (
        SELECT * FROM SIPSA_APROBA_FUEN_INFO INFO
        WHERE 1=1 
        AND INFO.APFUIN_FUTI_ID = p_futi_id 
        AND INFO.APFUIN_FUEN_ID = p_fuen_id 
        AND INFO.TIRE_ID= p_tire_id 
        AND INFO.MUNI_ID = p_muni_id
        AND INFO.USPE_ID = p_uspe_id 
        AND INFO.APFUIN_FECHA_VISITA = p_fecha_visita
        )
        ;
        
        SELECT COUNT(*) INTO v_apro_reco
        FROM SIPSA_APROBA_INSUMOS INSU
        WHERE 1=1 
        AND INSU.APIN_FUTI_ID = p_futi_id
        AND INSU.APIN_FUEN_ID = p_fuen_id 
        AND INSU.TIRE_ID = p_tire_id 
        AND INSU.USPE_ID = p_uspe_id
        AND INSU.APIN_PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
        ;
        
        IF (v_apro_reco = 1) THEn
           --Elimina de Fuente Aprobaciones 
           DELETE FROM SIPSA_APROBA_FUEN_INFO INFO WHERE 1=1 AND INFO.APFUIN_FUTI_ID = p_futi_id 
           AND INFO.APFUIN_FUEN_ID = p_fuen_id AND INFO.TIRE_ID= p_tire_id AND INFO.MUNI_ID = p_muni_id
           AND INFO.USPE_ID = p_uspe_id AND INFO.APFUIN_FECHA_VISITA = p_fecha_visita
           ;
           IF SQL%NOTFOUND THEN 
              p_mensaje := 'ERROR AL ELIMINAR FUENTE APROBACIÓN ';
              RAISE v_excepcion;
           END IF;
        END IF;
             

        --Actualiza los demas registros de aprobaciones con la nueva fuente 
        UPDATE SIPSA_APROBA_FUEN_INFO INFO
        SET INFO.APFUIN_FUTI_ID = v_futi_id,
            INFO.APFUIN_FUEN_ID = v_fuen_id,
            INFO.APFUIN_FUEN_NOMBRE = p_fuen_nombre
        WHERE 1=1
        AND INFO.APFUIN_FUTI_ID = p_futi_id 
        AND INFO.APFUIN_FUEN_ID = p_fuen_id 
        AND INFO.TIRE_ID= p_tire_id 
        AND INFO.MUNI_ID = p_muni_id
        AND INFO.USPE_ID = p_uspe_id 
        AND INFO.APFUIN_FECHA_VISITA = p_fecha_visita
        ;
        
        
        
     END IF;
       
     --Inserta Log de Aprobaciones
     INSERT INTO SIPSA_APROBA_INSUMOS_LOG (
     SELECT * FROM SIPSA_APROBA_INSUMOS INSU
     WHERE 1=1 
     AND INSU.APIN_FUTI_ID = p_futi_id
     AND INSU.APIN_FUEN_ID = p_fuen_id 
     AND INSU.TIRE_ID = p_tire_id 
     AND INSU.USPE_ID = p_uspe_id
     AND INSU.APIN_PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
     AND INSU.APIN_ARTICACO_ID = p_articaco_id 
     AND INSU.APIN_ARTI_ID = p_arti_id 
     AND INSU.APIN_CACO_ID = p_caco_id 
     AND INSU.UNME_ID = p_unme_id
     )
     ;
     --Elimina de Aprobaciones     
     DELETE FROM SIPSA_APROBA_INSUMOS INSU WHERE 1=1 AND INSU.APIN_FUTI_ID = p_futi_id
     AND INSU.APIN_FUEN_ID = p_fuen_id AND INSU.TIRE_ID = p_tire_id AND INSU.USPE_ID = p_uspe_id
     AND INSU.APIN_PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
     AND INSU.APIN_ARTICACO_ID = p_articaco_id AND INSU.APIN_ARTI_ID = p_arti_id 
     AND INSU.APIN_CACO_ID = p_caco_id AND INSU.UNME_ID = p_unme_id
     ;
     IF SQL%NOTFOUND THEN 
        p_mensaje := 'ERROR AL ELIMINAR APROBACIÓN INSUMOS ';
        RAISE v_excepcion;
     
     ELSE
       IF v_fuen_nombre IS NOT NULL THEN
         --Actualiza los demas registros de aprobaciones con la nueva fuente
         UPDATE  SIPSA_APROBA_INSUMOS INSU 
         SET INSU.APIN_FUTI_ID = v_futi_id,
            INSU.APIN_FUEN_ID = v_fuen_id
         WHERE 1=1 
         AND INSU.APIN_FUTI_ID = p_futi_id
         AND INSU.APIN_FUEN_ID = p_fuen_id 
         AND INSU.TIRE_ID = p_tire_id 
         AND INSU.USPE_ID = p_uspe_id
         AND INSU.APIN_PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
         ;
       END IF;
       
       --Actualiza los demas registros de aprobaciones con el nuevo articulo
       IF v_arti_nombre IS NOT NULL THEN
          UPDATE SIPSA_APROBA_INSUMOS INSU
          SET INSU.APIN_ARTI_ID = v_arti_id,
              INSU.APIN_ARTI_NOMBRE = p_arti_nombre
          WHERE 1=1
          AND INSU.APIN_ARTI_NOMBRE = v_arti_nombre
          AND INSU.TIRE_ID = p_tire_id 
          AND INSU.USPE_ID = p_uspe_id
          AND INSU.APIN_PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
          ;
       END IF;
       --Actualiza los demas registros de aprobaciones con la nueva Casa Comercial
       IF v_caco_nombre IS NOT NULL THEN
          UPDATE SIPSA_APROBA_INSUMOS INSU
          SET INSU.APIN_CACO_ID = v_caco_id,
              INSU.APIN_CASA_COMERCIAL = p_casa_comercial
          WHERE 1=1
          AND INSU.APIN_CASA_COMERCIAL = v_caco_nombre
          ;
       END IF;  
     END IF;     
   END IF;
   
   
                
EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      p_codigo_error := 1;
      p_mensaje := p_mensaje || SQLERRM;
        
END pr_AprobarInsumos1;

PROCEDURE pr_EliminarInsumos1
(
/********************************************************************************
 DESCRIPCION   : Metodo para borrar informacion de aprobaciones en la tabla sipsa_insumos

 PARAMETROS    :
 IN            : 

                 p_muni_id
                 p_prre_fecha_programada


 OUT           : 
                 p_resultado
                 p_codigo_error      Valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           Mensaje del error ocurrido

 REALIZADO POR : Carlos Alberto López Narváez.

 FECHA CREACION: 28/10/2013

 MODIFICADO POR:
 FECHA MODIFICA:
 CAMBIOS       :
*******************************************************************************/
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

) IS

v_excepcion                 EXCEPTION;
v_fuen_info                 NUMBER;
v_apro_reco                 NUMBER;
BEGIN

  p_codigo_error := 0;
  p_mensaje      := NULL;
 
  --Cuenta los registros
  SELECT COUNT(*) INTO v_fuen_info
   FROM SIPSA_APROBA_FUEN_INFO INFO
    WHERE 1=1 
    AND INFO.APFUIN_FUTI_ID = p_futi_id 
    AND INFO.APFUIN_FUEN_ID = p_fuen_id 
    AND INFO.TIRE_ID= p_tire_id 
    AND INFO.MUNI_ID = p_muni_id
    AND INFO.USPE_ID = p_uspe_id 
    AND INFO.APFUIN_FECHA_VISITA = p_fecha_visita
    ;
  IF v_fuen_info > 0 THEN  
     --Inserta Log de Fuente Aprobaciones
    INSERT INTO SIPSA_APROBA_FUEN_INFO_LOG  (
    SELECT * FROM SIPSA_APROBA_FUEN_INFO INFO
    WHERE 1=1 
    AND INFO.APFUIN_FUTI_ID = p_futi_id 
    AND INFO.APFUIN_FUEN_ID = p_fuen_id 
    AND INFO.TIRE_ID= p_tire_id 
    AND INFO.MUNI_ID = p_muni_id
    AND INFO.USPE_ID = p_uspe_id 
    AND INFO.APFUIN_FECHA_VISITA = p_fecha_visita
    )
    ;
    
    --Cuenta los Registros de Aprobaciones
    SELECT COUNT(*) INTO v_apro_reco
    FROM SIPSA_APROBA_INSUMOS reco
    WHERE 1=1
    AND reco.apin_futi_id = p_futi_id
    AND reco.apin_fuen_id = p_fuen_id
    AND reco.tire_id = p_tire_id
    AND reco.uspe_id = p_uspe_id
    AND reco.apin_prre_fecha_programada = p_prre_fecha_programada
    ;
    
    IF ( v_apro_reco = v_fuen_info) THEN--Borra la fuente info cuando es el ultimo registro
       --Elimina de Fuente Aprobaciones 
       DELETE FROM SIPSA_APROBA_FUEN_INFO INFO WHERE 1=1 AND INFO.APFUIN_FUTI_ID = p_futi_id 
       AND INFO.APFUIN_FUEN_ID = p_fuen_id AND INFO.TIRE_ID= p_tire_id AND INFO.MUNI_ID = p_muni_id
       AND INFO.USPE_ID = p_uspe_id AND INFO.APFUIN_FECHA_VISITA = p_fecha_visita
       ;   
    END IF;      
  END IF;       
  
  --Inserta Log de Aprobaciones
  INSERT INTO SIPSA_APROBA_INSUMOS_LOG (
         SELECT * FROM SIPSA_APROBA_INSUMOS INSU
         WHERE 1=1 
         AND INSU.APIN_FUTI_ID = p_futi_id
         AND INSU.APIN_FUEN_ID = p_fuen_id 
         AND INSU.TIRE_ID = p_tire_id 
         AND INSU.USPE_ID = p_uspe_id
         AND INSU.APIN_PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
         AND INSU.APIN_ARTICACO_ID = p_articaco_id 
         AND INSU.APIN_ARTI_ID = p_arti_id 
         AND INSU.APIN_CACO_ID = p_caco_id 
         AND INSU.UNME_ID = p_unme_id
   )
   ;
  --Elimina de Aprobaciones     
  DELETE FROM SIPSA_APROBA_INSUMOS INSU WHERE 1=1 AND INSU.APIN_FUTI_ID = p_futi_id
  AND INSU.APIN_FUEN_ID = p_fuen_id AND INSU.TIRE_ID = p_tire_id AND INSU.USPE_ID = p_uspe_id
  AND INSU.APIN_PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
  AND INSU.APIN_ARTICACO_ID = p_articaco_id AND INSU.APIN_ARTI_ID = p_arti_id 
  AND INSU.APIN_CACO_ID = p_caco_id AND INSU.UNME_ID = p_unme_id
  ;
  IF SQL%NOTFOUND THEN 
     p_mensaje := 'ERROR AL ELIMINAR APROBACIÓN INSUMOS ';
     RAISE v_excepcion;    
         
  END IF;
   
   
EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      p_codigo_error := 1;
      p_mensaje := p_mensaje || SQLERRM;
        
END pr_EliminarInsumos1;

PROCEDURE pr_AprobarInsumos2
(
/********************************************************************************
 DESCRIPCION   : Metodo para insertar informacion de aprobaciones en la tabla sipsa_insumos2

 PARAMETROS    :
 IN            : 

                 p_muni_id
                 p_prre_fecha_programada


 OUT           : 
                 p_resultado
                 p_codigo_error      Valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           Mensaje del error ocurrido

 REALIZADO POR : Carlos Alberto López Narváez.

 FECHA CREACION: 21/10/2013

 MODIFICADO POR: Carlos Alberto Manrique Palacios
 FECHA MODIFICA: 10/11/2018 
 CAMBIOS       : Se realiza la actualizacion a la tabla sipsa_aproba_reco_insumos2 
                actualizando el id del informante por el guardado en la tabla de informantes, 
                esto para todos los registros con el municipio correspondiente.
*******************************************************************************/
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

) IS

CURSOR c_aproba_grin2_vaca (p_grin2_id IN sipsa_aproba_grin2_vaca.apgrin2_id%TYPE,
                     p_futi_id  IN sipsa_aproba_grin2_vaca.futi_id%TYPE,
                     p_uspe_id  IN sipsa_aproba_grin2_vaca.uspe_id%TYPE) IS
                     
      select vaca.cara_id, vaca.vape_id, vaca.valor
      from sipsa_aproba_grin2_vaca vaca
      where 1=1
      and vaca.apgrin2_id = p_grin2_id
      and vaca.futi_id = p_futi_id
      and vaca.uspe_id =p_uspe_id
 ;

v_excepcion                 EXCEPTION;
v_arti_id                   NUMBER;
v_valida_grin               NUMBER;
v_valida_fuar               NUMBER;
v_uspe_id                   NUMBER;
v_usua_id                   NUMBER;
v_grin2_id                  NUMBER;
v_valida_prog               NUMBER;
v_valida_vape               NUMBER;
v_id_informante             NUMBER;
v_ftes_capturadas           NUMBER;
v_conta_reco                NUMBER;
v_enca                      NUMBER;
v_reco                      NUMBER;
v_arti_nombre               VARCHAR2(100);
v_caco_nombre               VARCHAR2(100);
v_fuen_nombre               VARCHAR2(100);
v_vape_id                   VARCHAR(10);
v_valor                     VARCHAR(100);
i                           NUMBER:=1;
j                           NUMBER:=1;
k                           NUMBER;
v_mes                       NUMBER;
v_precio_anterior           NUMBER;
v_fecha_hasta               DATE;

BEGIN

  p_codigo_error := 0;
  p_mensaje      := NULL;
 
  --Obtengo el ultimo dia del año para utilizarlos en fechas de vencimiento
  SELECT to_date('12/31/' || to_char(SYSDATE,'YYYY'),'mm/dd/yyyy') INTO v_fecha_hasta
  FROM dual 
  ;
  --Valida el articulo
  v_arti_nombre := PQ_SIPSA_ARTICULOS.fn_ObtenerNombre(p_arti_id);
  IF v_arti_nombre IS NULL THEN
     v_arti_id  := PQ_SIPSA_ARTICULOS.fn_ObtenerArtiId(p_arti_nombre);
     IF v_arti_id IS NULL THEN
        PQ_SIPSA_ARTICULOS.pr_Insertar(v_arti_id,p_grupo_id,p_subgrupo_id,p_arti_cod_interno,p_arti_nombre,
                             0,0,0,0,0,0,0,p_usuario,p_codigo_error,p_mensaje);
     
        IF v_arti_id IS NULL THEN
           p_mensaje := 'ERROR AL INSERTAR ARTICULO, ' || p_mensaje;
           RAISE v_excepcion;
        ELSE
           --Si es Nuevo se inserta en arti_tire
           PQ_SIPSA_ARTI_TIRE.pr_Insertar(v_arti_id,p_tire_id,v_fecha_hasta,p_usuario,p_codigo_error,p_mensaje);  
           IF p_codigo_error =1 THEN
              p_mensaje := 'ERROR AL INSERTAR ARTICULO TIPO RECOLECCIÓN, ' || p_mensaje;
              RAISE v_excepcion;
           END IF;
        END IF;
     END IF;  
  ELSE 
    v_arti_id  := p_arti_id;
  END IF;  
  
  --Obtengo el nombre del articulo sin modificación
  SELECT INSU.APENIN2_ARTI_NOMBRE INTO v_arti_nombre
  FROM SIPSA_APROBA_ENCA_INSUMOS2 INSU
  WHERE 1=1
  AND INSU.FUTI_ID = p_futi_id  
  AND INSU.USPE_ID = p_uspe_id
  AND INSU.ARTI_ID = p_arti_id_old
  AND INSU.APGRIN2_ID = p_grin2_id_old
  AND INSU.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada
   ;
   
  --Valida Grupo de Insumos
  v_valida_grin := PQ_SIPSA_GRUPO_INSUMOS2.fn_ValidarGrupoInsumos2(p_grin2_id,v_arti_id);
  IF v_valida_grin IS NULL THEN
       PQ_SIPSA_GRUPO_INSUMOS2.pr_Insertar(v_grin2_id, v_arti_id,v_fecha_hasta,p_usuario,p_codigo_error,p_mensaje);
       IF p_codigo_error =1 THEN
        p_mensaje := 'ERROR AL INSERTAR GRUPO INSUMOS2, ' || p_mensaje;
        RAISE v_excepcion;
      END IF;
  ELSE
      v_grin2_id := p_grin2_id;
  END IF;
  
  --Consulta y valida los valores de las caracteristcas
  FOR v_aproba_grin2_vaca IN c_aproba_grin2_vaca(p_grin2_id,p_futi_id, p_uspe_id)  LOOP
     v_valida_vape := PQ_SIPSA_VAPE_CARA.fn_ValidarVapeCara ( v_aproba_grin2_vaca.cara_id,v_aproba_grin2_vaca.vape_id);
     IF v_valida_vape IS NULL THEN
        WHILE j <= LENGTH(p_caracteristicas) LOOP
            i := instr(p_caracteristicas,'|',j,1);
            v_vape_id := SUBSTR(p_caracteristicas,j,i-j);
            k:=instr(p_caracteristicas,'|',i+1,1);
            v_valor := SUBSTR(p_caracteristicas,i+1,k-i-1);
            j:=k+1;
            IF (v_aproba_grin2_vaca.vape_id = v_vape_id) THEN
               PQ_SIPSA_VAPE_CARA.pr_Insertar(v_vape_id, v_aproba_grin2_vaca.cara_id,v_valor,p_usuario,p_codigo_error,p_mensaje);
               IF p_codigo_error =1 THEN
                  p_mensaje := 'ERROR AL INSERTAR VAPE CARA, ' || p_mensaje;
                  RAISE v_excepcion;
               ELSE
                  PQ_SIPSA_GRIN2_VACA.pr_Insertar(v_grin2_id, v_aproba_grin2_vaca.cara_id, v_vape_id,p_usuario,p_codigo_error,p_mensaje);
                  IF p_codigo_error =1 THEN
                     p_mensaje := 'ERROR AL INSERTAR GRIN2 VACA, ' || p_mensaje;
                     RAISE v_excepcion;
                  END IF;
               END IF;
            END IF;
        END LOOP;
     ELSE
        PQ_SIPSA_GRIN2_VACA.pr_Insertar(v_grin2_id, v_aproba_grin2_vaca.cara_id, v_aproba_grin2_vaca.vape_id,p_usuario,p_codigo_error,p_mensaje);
        IF p_codigo_error =1 THEN
           p_mensaje := 'ERROR AL INSERTAR GRIN2 VACA, ' || p_mensaje;
           RAISE v_excepcion;
        END IF;     
     END IF;
  END LOOP;
  
  --Valida el informante
  v_id_informante := PQ_SIPSA_INFORMANTES.fn_ObtenerIdInformante (p_muni_id, TRIM(p_nom_informante));
  IF v_id_informante IS NULL THEN
    PQ_SIPSA_INFORMANTES.pr_Insertar (p_muni_id,trim(p_nom_informante),p_tel_informante,v_fecha_hasta,p_usuario,p_codigo_error,p_mensaje);
    IF p_codigo_error =1 THEN
       p_mensaje := 'ERROR AL INSERTAR INFORMANTE, ' || p_mensaje;
       RAISE v_excepcion;
    ELSE
      v_id_informante := PQ_SIPSA_INFORMANTES.fn_ObtenerIdInformante (p_muni_id, TRIM(p_nom_informante));
    END IF;    
  END IF;
  UPDATE sipsa_aproba_reco_insumos2 SET APREIN2_ID_INFORMANTE=v_id_informante,APREIN2_NOM_INFORMANTE=TRIM(p_nom_informante) WHERE APREIN2_ID_INFORMANTE =  p_id_informante AND APREIN2_ID_INFORMANTE >=900000000 AND futi_id IN (SELECT FUTI_ID FROM SIPSA_FUENTES_TIPO_RECOLECCION FT INNER JOIN SIPSA_FUENTES F ON FT.FUEN_ID=F.FUEN_ID WHERE F.MUNI_ID=p_muni_id );
                 
   --Valida en Fuentes Articulos
   v_valida_fuar := PQ_SIPSA_FUENTE_ARTICULOS2.fn_ValidarFuenteArticulos2(p_futi_id, v_grin2_id);
   IF v_valida_fuar IS NULL THEN
     PQ_SIPSA_FUENTE_ARTICULOS2.pr_Insertar(p_futi_id,v_grin2_id,1,
                                            p_usuario,p_codigo_error,p_mensaje);
     IF p_codigo_error =1 THEN
        p_mensaje := 'ERROR AL INSERTAR FUENTE ARTICULOS2, ' || p_mensaje;
        RAISE v_excepcion;
      END IF;
   END IF;
   --Valida en Fuente usuario perfiles
   v_uspe_id := PQ_SIPSA_FUEN_USUA_PERF.Fn_ObtenerUspeId(p_futi_id,p_uspe_id);
   IF v_uspe_id IS NULL THEN
      --Obtiene usuario id
      v_usua_id := PQ_SIPSA_USUARIOS.fn_ObtenerUsuarioId(p_usuario_creacion);
      IF v_usua_id IS NULL THEN
         p_mensaje := 'NO SE ENCONTRO ID DEL USUARIO ' || p_usuario_creacion;
         RAISE v_excepcion;
      ELSE
         --Valida en usuario perfiles
         v_uspe_id := PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerUspeId(v_usua_id);
         IF v_uspe_id IS NULL THEN
            p_mensaje := 'NO SE ENCONTRO USUARIO PERFIL DEL USUARIO ' || p_usuario_creacion;
            RAISE v_excepcion;
         ELSE    
            PQ_SIPSA_FUEN_USUA_PERF.pr_Insertar(p_tire_id, p_fuen_id, v_uspe_id,SYSDATE,SYSDATE+180,
                                                p_usuario,p_codigo_error,p_mensaje);
            IF p_codigo_error =1 THEN
               p_mensaje := 'ERROR AL INSERTAR FUENTE USUARIO PERFIL, ' || p_mensaje;
               RAISE v_excepcion;
            END IF;                                               
         END IF; 
      END IF;   
   END IF;
   --Valida Programación de la fuente
   v_valida_prog := PQ_SIPSA_PROG_RECOLECCION.fn_ValidarProgramacion(p_futi_id,p_prre_fecha_programada);
   IF v_valida_prog IS NULL THEN
      PQ_SIPSA_PROG_RECOLECCION.pr_Insertar(p_futi_id,p_prre_fecha_programada,20,
                                            p_usuario,p_codigo_error,p_mensaje);
      IF p_codigo_error =1 THEN
         p_mensaje := 'ERROR AL INSERTAR PROGRAMACION, ' || p_mensaje;
         RAISE v_excepcion;
      END IF; 
   END IF;
   
   --Consulta las fuentes capturadas
   SELECT INSU.APENIN2_FTES_CAPTURADAS INTO v_ftes_capturadas
   FROM SIPSA_APROBA_ENCA_INSUMOS2 INSU 
   WHERE 1=1 
   AND INSU.FUTI_ID= p_futi_id
   AND INSU.USPE_ID = p_uspe_id
   AND INSU.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
   AND INSU.APGRIN2_ID = p_grin2_id_old 
   AND INSU.ARTI_ID = p_arti_id_old 
   ;
   
   --Cuenta los registros capturados
   SELECT COUNT(*) INTO v_conta_reco
   FROM SIPSA_APROBA_RECO_INSUMOS2 INSU WHERE 1=1 
   AND INSU.FUTI_ID= p_futi_id
   AND INSU.USPE_ID = p_uspe_id
   AND INSU.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
   AND INSU.APGRIN2_ID = p_grin2_id_old 
   AND INSU.ARTI_ID = p_arti_id_old 
    ;

 --  IF (v_conta_reco = v_ftes_capturadas) THEN
        --Obtengo el mes de la fecha programada
        v_mes := EXTRACT (MONTH FROM p_prre_fecha_programada);
   
        --Obtengo el precio anterior
       SELECT DECODE(COUNT(1),0,1000,MAX(reco.rein2_precio_recolectado)) INTO v_precio_anterior
       FROM sipsa_recoleccion_insumos2 reco
       WHERE 1=1
       AND reco.futi_id = p_futi_id
       AND reco.grin2_id = v_grin2_id
       AND reco.rein2_id_informante = v_id_informante
       ;
   
       --Valida si ya tenia cotizaciones aprobadas
       SELECT COUNT(1) INTO v_enca
       FROM SIPSA_ENCA_INSUMOS2 ENCA 
       WHERE 1=1 
       AND ENCA.FUTI_ID= p_futi_id
       AND ENCA.USPE_ID = p_uspe_id
       AND ENCA.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
       AND ENCA.GRIN2_ID = p_grin2_id  
       ;
         
       --el registro no existe en encabezado insumos2
       IF (v_enca = 0) THEN    
          --Insertar en Encabezado Insumos2
          
          INSERT INTO SIPSA_ENCA_INSUMOS2 (FUTI_ID,USPE_ID,GRIN2_ID,PRRE_FECHA_PROGRAMADA,ENIN2_PRECIO_PROM_ANTERIOR,
                                           ENIN2_NOVEDAD,ENIN2_FTES_CAPTURADAS,OBSE_ID,ENIN2_OBSERVACION,
                                           ENIN2_USUARIO_CREACION)
          VALUES (p_futi_id, p_uspe_id,v_grin2_id, p_prre_fecha_programada,v_precio_anterior,p_novedad,
                  v_ftes_capturadas,p_obse_id, p_observacion, UPPER(p_usuario));
   
          IF SQL%NOTFOUND THEN 
             p_mensaje := 'ERROR AL INSERTAR ENCABEZADO_INSUMOS2 ';
             RAISE v_excepcion;
          END IF;
       ELSE
          --Valida si el registro ya existe en recoleccion
          SELECT COUNT(1) INTO v_reco
          FROM SIPSA_RECOLECCION_INSUMOS2 RECO 
          WHERE 1=1 
          AND RECO.FUTI_ID= p_futi_id
          AND RECO.USPE_ID = p_uspe_id
          AND RECO.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
          AND RECO.GRIN2_ID = p_grin2_id 
          AND RECO.REIN2_ID_INFORMANTE = v_id_informante 
          ; 
   
          IF (v_reco = 1 ) THEN
             p_mensaje := 'ERROR AL INSERTAR RECOLECCION_INSUMOS2, REGISTRO YA EXISTE ';
             RAISE v_excepcion;
          ELSE
             --Actualizo las fuentes capturadas en el encabezado
             UPDATE  SIPSA_ENCA_INSUMOS2 ENCA
             SET ENCA.ENIN2_FTES_CAPTURADAS = ( SELECT ENCA.ENIN2_FTES_CAPTURADAS+1
                                                FROM SIPSA_ENCA_INSUMOS2 ENCA 
                                                WHERE 1=1 
                                                AND ENCA.FUTI_ID= p_futi_id
                                                AND ENCA.USPE_ID = p_uspe_id
                                                AND ENCA.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
                                                AND ENCA.GRIN2_ID = p_grin2_id  
                                              )
             WHERE 1=1 
             AND ENCA.FUTI_ID= p_futi_id
             AND ENCA.USPE_ID = p_uspe_id
             AND ENCA.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
             AND ENCA.GRIN2_ID = p_grin2_id 
             ;        
          END IF;
       END IF;   
  -- END IF;
   
   --Inserta en Recoleccion Insumos2
   INSERT INTO SIPSA_RECOLECCION_INSUMOS2 (FUTI_ID,USPE_ID,GRIN2_ID,PRRE_FECHA_PROGRAMADA,REIN2_NOVEDAD_GRAL,
               REIN2_ID_INFORMANTE,REIN2_NOM_INFORMANTE,REIN2_TEL_INFORMANTE,REIN2_PRECIO_RECOLECTADO,
               REIN2_NOVEDAD,OBSE_ID,REIN2_OBSERVACION,ESTA_ID,REIN2_FECHA_RECOLECCION, REIN2_USUARIO_CREACION)
   VALUES(p_futi_id,p_uspe_id,v_grin2_id, p_prre_fecha_programada,null,v_id_informante,
          p_nom_informante,p_tel_informante, p_precio_recolectado,p_novedad, p_obse_id,
          p_observacion,1, p_fecha_recoleccion, UPPER(p_usuario));                               

   IF SQL%NOTFOUND THEN 
      p_mensaje := 'ERROR AL INSERTAR RECOLECCION_INSUMOS2 ';
      RAISE v_excepcion;
   ELSE                 
     --Inserta en Log
     INSERT INTO SIPSA_APROBA_RECO_INSUMOS2_LOG (
     SELECT * FROM SIPSA_APROBA_RECO_INSUMOS2 INSU
     WHERE 1=1
     AND INSU.FUTI_ID= p_futi_id
     AND INSU.USPE_ID = p_uspe_id
     AND INSU.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
     AND INSU.APGRIN2_ID = p_grin2_id_old 
     AND INSU.ARTI_ID = p_arti_id_old 
     AND INSU.APREIN2_ID_INFORMANTE = p_id_informante
     )
     ;
     --Elimina de Aprobaciones     
     DELETE FROM SIPSA_APROBA_RECO_INSUMOS2 INSU WHERE 1=1 
     AND INSU.FUTI_ID= p_futi_id
     AND INSU.USPE_ID = p_uspe_id
     AND INSU.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
     AND INSU.APGRIN2_ID = p_grin2_id_old 
     AND INSU.ARTI_ID = p_arti_id_old 
     AND INSU.APREIN2_ID_INFORMANTE = v_id_informante
     ;
     IF SQL%NOTFOUND THEN 
        p_mensaje := 'ERROR AL ELIMINAR ENCABEZADO APROBACIÓN INSUMOS2 --> '    || SQLERRM;
        RAISE v_excepcion;
     ELSE       
        IF (v_conta_reco = 1) THEN
           --Inserta Log
           INSERT INTO SIPSA_APROBA_ENCA_INSUMOS2_LOG (
            SELECT * FROM SIPSA_APROBA_ENCA_INSUMOS2 INSU
            WHERE 1=1 
            AND INSU.FUTI_ID= p_futi_id
            AND INSU.USPE_ID = p_uspe_id
            AND INSU.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
            AND INSU.APGRIN2_ID = p_grin2_id_old 
            AND INSU.ARTI_ID = p_arti_id_old 
           ) 
           ;
           
           --Elimina Encabezado de Aprobaciones     
            DELETE FROM SIPSA_APROBA_ENCA_INSUMOS2 INSU 
            WHERE 1=1 
            AND INSU.FUTI_ID= p_futi_id
            AND INSU.USPE_ID = p_uspe_id
            AND INSU.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
            AND INSU.APGRIN2_ID = p_grin2_id_old 
            AND INSU.ARTI_ID = p_arti_id_old 
            ;
            IF SQL%NOTFOUND THEN 
             p_mensaje := 'ERROR AL ELIMINAR RECOLECCIÓN APROBACIÓN INSUMOS2 ';
             RAISE v_excepcion;
            END IF;      
        END IF;
        --ELimina Aproba Grin
        FOR v_aproba_grin2_vaca IN c_aproba_grin2_vaca(p_grin2_id_old,p_futi_id, p_uspe_id)  LOOP
            --Inserta Log
            INSERT INTO SIPSA_APROBA_GRIN2_VACA_LOG (
             SELECT * FROM SIPSA_APROBA_GRIN2_VACA GRIN
             WHERE 1=1
             AND GRIN.FUTI_ID = p_futi_id
             AND GRIN.USPE_ID = p_uspe_id
             AND GRIN.APGRIN2_ID = p_grin2_id_old
             AND GRIN.CARA_ID = v_aproba_grin2_vaca.cara_id 
             AND GRIN.VAPE_ID =  v_aproba_grin2_vaca.vape_id
            )
            ;
                
            --Elimina Aproba Grin valores
            DELETE FROM SIPSA_APROBA_GRIN2_VACA GRIN
            WHERE 1=1
            AND GRIN.FUTI_ID = p_futi_id
            AND GRIN.USPE_ID = p_uspe_id
            AND GRIN.APGRIN2_ID = p_grin2_id_old
            AND GRIN.CARA_ID = v_aproba_grin2_vaca.cara_id 
            AND GRIN.VAPE_ID =  v_aproba_grin2_vaca.vape_id
            ;
            IF SQL%NOTFOUND THEN 
               p_mensaje := 'ERROR AL ELIMINAR APROBACIÓN GRUPO INSUMOS2 VALORES ';
               RAISE v_excepcion;
              
            END IF;
        END LOOP;
            
        IF (p_grin2_id_old >= 900000000) THEN
           --Inserta Log
           INSERT INTO SIPSA_APROBA_GRIN2_LOG (
           SELECT * FROM SIPSA_APROBA_GRIN2 GRIN
           WHERE 1=1
           AND GRIN.FUTI_ID = p_futi_id
           AND GRIN.USPE_ID = p_uspe_id
           AND GRIN.APGRIN2_ID = p_grin2_id_old
           )
           ;
           --Elimina Aproba Grin
           DELETE FROM SIPSA_APROBA_GRIN2 GRIN
           WHERE 1=1
           AND GRIN.FUTI_ID = p_futi_id
           AND GRIN.USPE_ID = p_uspe_id
           AND GRIN.APGRIN2_ID = p_grin2_id_old
           ;
           IF SQL%NOTFOUND THEN 
              p_mensaje := 'ERROR AL ELIMINAR APROBACIÓN GRUPO INSUMOS2 ';
              RAISE v_excepcion;
           END IF;  
           
           IF (v_conta_reco > 1) THEN
             --Inserta el encabezado con el nuevo grin2
             INSERT INTO SIPSA_APROBA_ENCA_INSUMOS2 (
             SELECT ENCA.FUTI_ID,ENCA.USPE_ID, ENCA.ARTI_ID, v_grin2_id,ENCA.PRRE_FECHA_PROGRAMADA, ENCA.APENIN2_ARTI_NOMBRE,
             ENCA.APENIN2_NOVEDAD, ENCA.APENIN2_FTES_CAPTURADAS, ENCA.OBSE_ID, ENCA.APENIN2_OBSERVACION,
             ENCA.APENIN2_FECHA_CREACION, ENCA.APENIN2_USUARIO_CREACION, ENCA.APENIN2_FECHA_MODIFICACION,
             ENCA.APENIN2_USUARIO_MODIFICACION
             FROM SIPSA_APROBA_ENCA_INSUMOS2 ENCA
             WHERE 1=1
             AND ENCA.FUTI_ID = p_futi_id
             AND ENCA.USPE_ID = p_uspe_id
             AND ENCA.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
             AND ENCA.APGRIN2_ID = p_grin2_id_old 
             AND ENCA.ARTI_ID = p_arti_id_old)
             ;
             IF SQL%NOTFOUND THEN 
                p_mensaje := 'ERROR AL INSERTAR APROBACIÓN ENCABEZADO GRIN2 ';
                RAISE v_excepcion;
             END IF;
             
             --Insertar recoleccion con el nuevo grin2
             INSERT INTO SIPSA_APROBA_RECO_INSUMOS2 (
             SELECT RECO.FUTI_ID,RECO.USPE_ID ,RECO.ARTI_ID,v_grin2_id,RECO.PRRE_FECHA_PROGRAMADA,RECO.APREIN2_NOVEDAD_GRAL, 
             RECO.APREIN2_ID_INFORMANTE,RECO.APREIN2_NOM_INFORMANTE, RECO.APREIN2_TEL_INFORMANTE, RECO.APREIN2_PRECIO_RECOLECTADO,
             RECO.APREIN2_NOVEDAD, RECO.OBSE_ID, RECO.APREIN2_OBSERVACION, RECO.APREIN2_FECHA_RECOLECCION, RECO.ESTA_ID,
             RECO.APREIN2_FECHA_CREACION, RECO.APREIN2_USUARIO_CREACION, RECO.APREIN2_FECHA_MODIFICACION, RECO.APREIN2_USUARIO_MODIFICACION
             FROM  SIPSA_APROBA_RECO_INSUMOS2 RECO         
             WHERE 1=1
             AND RECO.FUTI_ID = p_futi_id
             AND RECO.USPE_ID = p_uspe_id
             AND RECO.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
             AND RECO.APGRIN2_ID = p_grin2_id_old 
             );
           
          
             IF SQL%NOTFOUND THEN 
                p_mensaje := 'ERROR AL INSERTAR APROBACIÓN RECOLECCIÓN GRIN2 ';
                RAISE v_excepcion;
             END IF;
           
             --Elimina grin2 vijeo en Aprobaciones Recolección     
             DELETE
             FROM SIPSA_APROBA_RECO_INSUMOS2 INSU 
             WHERE 1=1 
             AND INSU.FUTI_ID= p_futi_id
             AND INSU.USPE_ID = p_uspe_id
             AND INSU.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
             AND INSU.APGRIN2_ID = p_grin2_id_old 
             ;

             IF SQL%NOTFOUND THEN 
              p_mensaje := 'ERROR AL ELIMINAR APROBACIÓN ENCABEZADO GRIN2 ';
              RAISE v_excepcion;
             ELSE   
               --Elimina grin2 viejo en Aprobación Encabezado
               DELETE
               FROM SIPSA_APROBA_ENCA_INSUMOS2 ENCA
               WHERE 1=1
               AND ENCA.FUTI_ID = p_futi_id
               AND ENCA.USPE_ID = p_uspe_id
               AND ENCA.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
               AND ENCA.APGRIN2_ID = p_grin2_id_old 
               AND ENCA.ARTI_ID = p_arti_id_old 
               ;
             END IF;            
           END IF;  
        END IF;
         --Actualiza los demas registros de aprobaciones con el nuevo articulo
         IF (p_arti_id_old > 900000000)  THEN
            UPDATE SIPSA_APROBA_ENCA_INSUMOS2 INSU
            SET INSU.ARTI_ID = v_arti_id,
                INSU.APENIN2_ARTI_NOMBRE = p_arti_nombre
            WHERE 1=1
            AND INSU.APENIN2_ARTI_NOMBRE = v_arti_nombre 
            AND INSU.USPE_ID = p_uspe_id
            AND INSU.ARTI_ID = p_arti_id_old
            AND INSU.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
            AND INSU.APGRIN2_ID = p_grin2_id_old
            ;
            UPDATE SIPSA_APROBA_RECO_INSUMOS2 INSU
            SET INSU.ARTI_ID = v_arti_id
            WHERE 1=1 
            AND INSU.USPE_ID = p_uspe_id
            AND INSU.ARTI_ID = p_arti_id_old
            AND INSU.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
            AND INSU.FUTI_ID = p_futi_id
            ;
         END IF;  
     END IF;     
   END IF;    
                
EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      
      p_codigo_error := 1;
      p_mensaje := p_mensaje || SQLERRM;
        
END pr_AprobarInsumos2;

PROCEDURE pr_EliminarInsumos2
(
/********************************************************************************
 DESCRIPCION   : Metodo para borrar informacion de aprobaciones en la tabla sipsa_insumos2

 PARAMETROS    :
 IN            : 

                 p_muni_id
                 p_prre_fecha_programada


 OUT           : 
                 p_resultado
                 p_codigo_error      Valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           Mensaje del error ocurrido

 REALIZADO POR : Carlos Alberto López Narváez.

 FECHA CREACION: 14/11/2013

 MODIFICADO POR:
 FECHA MODIFICA:
 CAMBIOS       :
*******************************************************************************/
p_futi_id                   IN sipsa_aproba_reco_insumos2.futi_id%TYPE,
p_uspe_id                   IN sipsa_aproba_reco_insumos2.uspe_id%TYPE,
p_prre_fecha_programada     IN sipsa_aproba_reco_insumos2.prre_fecha_programada%TYPE,
p_id_informante             IN sipsa_aproba_reco_insumos2.aprein2_id_informante%TYPE,
p_arti_id                   IN sipsa_aproba_reco_insumos2.arti_id%TYPE,
p_grin2_id                  IN sipsa_aproba_reco_insumos2.apgrin2_id%TYPE,
p_usuario                   IN sipsa_aproba_insumos.apin_usuario_creacion%TYPE,
p_codigo_error              OUT NUMBER,
p_mensaje                   OUT VARCHAR2

) IS

v_excepcion                 EXCEPTION;
v_ftes_capturadas           NUMBER;
v_conta_reco                NUMBER;

CURSOR c_aproba_grin2_vaca (p_grin2_id IN sipsa_aproba_grin2_vaca.apgrin2_id%TYPE,
                     p_futi_id  IN sipsa_aproba_grin2_vaca.futi_id%TYPE,
                     p_uspe_id  IN sipsa_aproba_grin2_vaca.uspe_id%TYPE) IS
                     
      select vaca.cara_id, vaca.vape_id, vaca.valor
      from sipsa_aproba_grin2_vaca vaca
      where 1=1
      and vaca.apgrin2_id = p_grin2_id
      and vaca.futi_id = p_futi_id
      and vaca.uspe_id =p_uspe_id
 ;
 
BEGIN

  p_codigo_error := 0;
  p_mensaje      := NULL;
 
   --Consulta las fuentes capturadas
  /* SELECT INSU.APENIN2_FTES_CAPTURADAS INTO v_ftes_capturadas
   FROM SIPSA_APROBA_ENCA_INSUMOS2 INSU 
   WHERE 1=1 
   AND INSU.FUTI_ID= p_futi_id
   AND INSU.USPE_ID = p_uspe_id
   AND INSU.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
   AND INSU.APGRIN2_ID = p_grin2_id 
   AND INSU.ARTI_ID = p_arti_id 
   ;*/
   
    --Cuenta los registros capturados
   SELECT COUNT(*) INTO v_conta_reco
   FROM SIPSA_APROBA_RECO_INSUMOS2 INSU WHERE 1=1 
   AND INSU.FUTI_ID= p_futi_id
   AND INSU.USPE_ID = p_uspe_id
   AND INSU.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
   AND INSU.APGRIN2_ID = p_grin2_id 
   AND INSU.ARTI_ID = p_arti_id 
    ;
    
   --Inserta en Recolección Insumo2 Log
   INSERT INTO SIPSA_APROBA_RECO_INSUMOS2_LOG (
   SELECT * FROM SIPSA_APROBA_RECO_INSUMOS2 INSU
   WHERE 1=1
   AND INSU.FUTI_ID= p_futi_id
   AND INSU.USPE_ID = p_uspe_id
   AND INSU.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
   AND INSU.APGRIN2_ID = p_grin2_id
   AND INSU.ARTI_ID = p_arti_id 
   AND INSU.APREIN2_ID_INFORMANTE = p_id_informante
   )
   ; 
   --Elimina Recolección Insumos2    
   DELETE FROM SIPSA_APROBA_RECO_INSUMOS2 INSU WHERE 1=1 
   AND INSU.FUTI_ID= p_futi_id
   AND INSU.USPE_ID = p_uspe_id
   AND INSU.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
   AND INSU.APGRIN2_ID = p_grin2_id 
   AND INSU.ARTI_ID = p_arti_id 
   AND INSU.APREIN2_ID_INFORMANTE = p_id_informante
   ;
   IF SQL%NOTFOUND THEN 
      p_mensaje := 'ERROR AL ELIMINAR RECOLECCIÓN APROBACIÓN ';
      RAISE v_excepcion;
   END IF;
  
   IF (v_conta_reco = 1) THEN
      --Inserta Log
       INSERT INTO SIPSA_APROBA_ENCA_INSUMOS2_LOG (
        SELECT * FROM SIPSA_APROBA_ENCA_INSUMOS2 ENCA
        WHERE 1=1 
        AND ENCA.FUTI_ID= p_futi_id
        AND ENCA.USPE_ID = p_uspe_id
        AND ENCA.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
        AND ENCA.APGRIN2_ID = p_grin2_id 
        AND ENCA.ARTI_ID = p_arti_id 
       ) 
       ;
      --Elimina Encabezado Insumos2 
      DELETE
      FROM SIPSA_APROBA_ENCA_INSUMOS2 ENCA 
      WHERE 1=1 
      AND ENCA.FUTI_ID= p_futi_id
      AND ENCA.USPE_ID = p_uspe_id
      AND ENCA.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
      AND ENCA.APGRIN2_ID = p_grin2_id 
      AND ENCA.ARTI_ID = p_arti_id 
      ;
      IF SQL%NOTFOUND THEN 
         p_mensaje := 'ERROR AL ELIMINAR ENCABEZADO APROBACIÓN ';
         RAISE v_excepcion;
      END IF;  
      
      FOR v_aproba_grin2_vaca IN c_aproba_grin2_vaca(p_grin2_id,p_futi_id, p_uspe_id)  LOOP
         --Inserta Log
         INSERT INTO SIPSA_APROBA_GRIN2_VACA_LOG (
          SELECT * FROM SIPSA_APROBA_GRIN2_VACA GRIN
          WHERE 1=1
          AND GRIN.FUTI_ID = p_futi_id
          AND GRIN.USPE_ID = p_uspe_id
          AND GRIN.APGRIN2_ID = p_grin2_id
          AND GRIN.CARA_ID = v_aproba_grin2_vaca.cara_id 
          AND GRIN.VAPE_ID =  v_aproba_grin2_vaca.vape_id
         )
         ;
                  
         --Elimina Aproba Grin valores
         DELETE FROM SIPSA_APROBA_GRIN2_VACA GRIN
         WHERE 1=1
         AND GRIN.FUTI_ID = p_futi_id
         AND GRIN.USPE_ID = p_uspe_id
         AND GRIN.APGRIN2_ID = p_grin2_id
         AND GRIN.CARA_ID = v_aproba_grin2_vaca.cara_id 
         AND GRIN.VAPE_ID =  v_aproba_grin2_vaca.vape_id
          ;
         IF SQL%NOTFOUND THEN 
            p_mensaje := 'ERROR AL ELIMINAR APROBACIÓN GRUPO INSUMOS2 VALORES ';
            RAISE v_excepcion;
         END IF;
     END LOOP;
     IF (p_grin2_id >= 900000000) THEN
         --Inserta Log
         INSERT INTO SIPSA_APROBA_GRIN2_LOG (
         SELECT * FROM SIPSA_APROBA_GRIN2 GRIN
         WHERE 1=1
         AND GRIN.FUTI_ID = p_futi_id
         AND GRIN.USPE_ID = p_uspe_id
         AND GRIN.APGRIN2_ID = p_grin2_id
         )
         ;
         --Elimina Aproba Grin
         DELETE FROM SIPSA_APROBA_GRIN2 GRIN
         WHERE 1=1
         AND GRIN.FUTI_ID = p_futi_id
         AND GRIN.USPE_ID = p_uspe_id
         AND GRIN.APGRIN2_ID = p_grin2_id
         ;
         IF SQL%NOTFOUND THEN 
            p_mensaje := 'ERROR AL ELIMINAR APROBACIÓN GRUPO INSUMOS2 ';
            RAISE v_excepcion;
         null;
        END IF;
     
     END IF;
   ELSE
      --Actualiza las fuentes capturadas del encabezado
      UPDATE SIPSA_APROBA_ENCA_INSUMOS2 ENCA
      SET ENCA.APENIN2_FTES_CAPTURADAS = v_conta_reco-1
      WHERE 1=1 
      AND ENCA.FUTI_ID= p_futi_id
      AND ENCA.USPE_ID = p_uspe_id
      AND ENCA.PRRE_FECHA_PROGRAMADA = p_prre_fecha_programada 
      AND ENCA.APGRIN2_ID = p_grin2_id 
      AND ENCA.ARTI_ID = p_arti_id
      ;
      IF SQL%NOTFOUND THEN 
         p_mensaje := 'ERROR AL ACTUALIZAR ENCABEZADO APROBACIÓN ';
         RAISE v_excepcion;
      END IF; 
   END IF;
        
EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      p_codigo_error := 1;
      p_mensaje := p_mensaje || SQLERRM;
        
END pr_EliminarInsumos2;




PROCEDURE Pr_InsertarIAFI_DISTRITO
/********************************************************************************
 Descripcion   : metodo para incorporar informacion sobre la tabla SIPSA_APROBA_FUEN_INFO DISTRITO

 parametros    :
 in            :  p_fuen_id
                  p_futi_id
                  p_muni_id
                  p_tire_id
                  p_uspe_id
                  p_fecha_visita
                  p_fuen_nombre
                  p_fuen_direccion
                  p_fuen_telefono
                  p_fuen_email
                  p_info_nombre
                  p_info_cargo
                  p_info_telefono
                  p_info_email
                  p_usuario

 out           :  p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                   p_mensaje           mensaje del error ocurrido

 Realizado por : Marco Guzman
 Fecha Creacion: 21/06/2019
*******************************************************************************/

( p_fuen_id               IN SIPSA_APROBA_FUEN_INFO.apfuin_fuen_id%TYPE,
  p_futi_id               IN SIPSA_APROBA_FUEN_INFO.apfuin_futi_id%TYPE,
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
  p_mensaje                       OUT VARCHAR2)IS


contador NUMBER;
--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador := NULL;

    SELECT COUNT(1) INTO contador FROM SIPSA_APROBA_FUEN_INFO
     WHERE apfuin_fuen_id = p_fuen_id
    AND apfuin_futi_id= p_futi_id
    AND tire_id = p_tire_id
    AND muni_id = p_muni_id
    AND uspe_id = p_uspe_id
    AND apfuin_fecha_visita = p_fecha_visita;

    IF contador = 0 THEN  
   --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario)))); 
        INSERT INTO    SIPSA_APROBA_FUEN_INFO (apfuin_fuen_id,apfuin_futi_id,muni_id,tire_id,uspe_id,apfuin_fecha_visita,
                                                                            apfuin_fuen_nombre,apfuin_fuen_direccion,apfuin_fuen_telefono,apfuin_fuen_email,
                                                                            apfuin_info_nombre,apfuin_info_cargo,apfuin_info_telefono,apfuin_info_email,
                                                                            apfuin_usuario_creacion)
        VALUES(p_fuen_id,p_futi_id,p_muni_id,p_tire_id,p_uspe_id,p_fecha_visita,
                     p_fuen_nombre,p_fuen_direccion,p_fuen_telefono,p_fuen_email,
                     p_info_nombre,p_info_cargo,p_info_telefono,p_info_email,
                     p_usuario); 
    ELSE    
        PQ_SIPSA_APROBACIONES.Pr_ActualizarIAFI(p_fuen_id,p_futi_id,p_muni_id,p_tire_id,p_uspe_id,p_fecha_visita,
                                                                             p_fuen_nombre,p_fuen_direccion,p_fuen_telefono,p_fuen_email,
                                                                             p_info_nombre,p_info_cargo,p_info_telefono,p_info_email,
                                                                             p_usuario,p_codigo_error,p_mensaje);   
    END IF;           


             
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_APROBA_FUEN_INFO SE PRESENTO'||SQLERRM;                                                                                   
END Pr_InsertarIAFI_DISTRITO;


PROCEDURE Pr_InsertarIA_DISTRITO
/********************************************************************************
 Descripcion   : metodo para incorporar informacion sobre la tabla SIPSA_APROBA_INSUMOS

 parametros    :
 in            :      p_fuen_id
                      p_futi_id
                      p_tire_id
                      p_uspe_id
                      p_prre_fecha_programada
                      p_arti_id
                      p_arti_nombre
                      p_ica
                      p_caco_id
                      p_casa_comercial
                      p_unme_id
                      p_precio_recolectado
                      p_novedad
                      p_obse_id
                      p_observacion
                      p_usuario


 out           :    p_futi_id
                     p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                     p_mensaje           mensaje del error ocurrido

 Realizado por : Marco Guzman
 Fecha Creacion: 19/06/2019
*******************************************************************************/

( p_fuen_id               IN SIPSA_APROBA_INSUMOS.apin_fuen_id%TYPE, 
  p_futi_id               IN OUT SIPSA_APROBA_INSUMOS.apin_futi_id%TYPE,
  p_tire_id   IN  SIPSA_APROBA_INSUMOS.tire_id%TYPE,
 p_uspe_id   IN  SIPSA_APROBA_INSUMOS.uspe_id%TYPE,
 p_prre_fecha_programada IN SIPSA_APROBA_INSUMOS.apin_prre_fecha_programada%TYPE,
 p_articaco_id IN SIPSA_APROBA_INSUMOS.apin_articaco_id%TYPE,
 p_arti_id       IN SIPSA_APROBA_INSUMOS.apin_arti_id%TYPE,
 p_arti_nombre       IN  SIPSA_APROBA_INSUMOS.apin_arti_nombre%TYPE,
 p_caco_id       IN  SIPSA_APROBA_INSUMOS.apin_caco_id%TYPE,
 p_casa_comercial       IN  SIPSA_APROBA_INSUMOS.apin_casa_comercial%TYPE,
 p_regica_linea       IN  SIPSA_APROBA_INSUMOS.apin_regica_linea%TYPE,
 p_unme_id    IN  SIPSA_APROBA_INSUMOS.unme_id%TYPE,
 p_precio_recolectado            IN SIPSA_APROBA_INSUMOS.apin_precio_recolectado%TYPE,
 p_novedad            IN SIPSA_APROBA_INSUMOS.apin_novedad%TYPE,
 p_obse_id            IN SIPSA_APROBA_INSUMOS.obse_id%TYPE,
 p_observacion           IN SIPSA_APROBA_INSUMOS.apin_observacion%TYPE,
 p_esta_id           IN SIPSA_APROBA_INSUMOS.esta_id%TYPE,
 p_fecha_recoleccion IN SIPSA_APROBA_INSUMOS.apin_fecha_recoleccion%TYPE,
 p_usuario                       IN SIPSA_APROBA_INSUMOS.apin_usuario_creacion%TYPE,
 p_codigo_error                  OUT NUMBER,
 p_mensaje                       OUT VARCHAR2)IS

contador NUMBER;
v_fecha_programa DATE;
--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador := NULL;

    
    /*SELECT MAX(prog.prre_fecha_programada) INTO v_fecha_programa
    FROM sipsa_programacion_recoleccion prog
    WHERE 1=1
    AND prog.futi_id = p_futi_id
    ;*/
    
    
    SELECT COUNT(1) INTO contador FROM SIPSA_APROBA_INSUMOS
    WHERE apin_futi_id = p_futi_id
    AND apin_fuen_id = p_fuen_id 
    AND tire_id = p_tire_id
    AND uspe_id = p_uspe_id
    AND apin_prre_fecha_programada = p_prre_fecha_programada
    --AND apin_prre_fecha_programada = v_fecha_programa
    AND apin_articaco_id = p_articaco_id
    AND apin_arti_id = p_arti_id
    AND apin_caco_id = p_caco_id
    AND unme_id = p_unme_id;
   
    
   
   IF contador= 0 THEN
   --v_usuario:= PQ_SIPSA_USUARIOS.fn_ObtenerUsuario(PQ_SIPSA_USUARIOS_PERFILES.fn_ObtenerIdPersona(PQ_SIPSA_USUARIOS_PERFILES.Fn_Obtenerid(p_usuario,PQ_SIPSA_USUARIOS_PERFILES.Fn_Obteneridperfilporpersona(p_usuario))));
        INSERT INTO    SIPSA_APROBA_INSUMOS (apin_fuen_id,apin_futi_id,tire_id,uspe_id,apin_prre_fecha_programada,apin_articaco_id,apin_arti_id,apin_arti_nombre,
                                                                         apin_caco_id,apin_casa_comercial,apin_regica_linea,unme_id,apin_precio_recolectado,apin_novedad,obse_id,apin_observacion,esta_id,
                                                                         apin_fecha_recoleccion,apin_usuario_creacion)
        VALUES(p_fuen_id,p_futi_id,p_tire_id,p_uspe_id,p_prre_fecha_programada ,p_articaco_id,p_arti_id,p_arti_nombre,
                    p_caco_id,p_casa_comercial,p_regica_linea,p_unme_id,p_precio_recolectado,p_novedad,p_obse_id,p_observacion,p_esta_id,
                    p_fecha_recoleccion,UPPER(p_usuario));
  ELSE
                PQ_SIPSA_APROBACIONES.Pr_ActualizarIA(p_fuen_id,p_futi_id,p_tire_id,p_uspe_id,p_prre_fecha_programada,p_articaco_id,p_arti_id,p_arti_nombre,
                                                                               p_caco_id,p_casa_comercial,p_regica_linea,p_unme_id,p_precio_recolectado,p_novedad,p_obse_id,
                                                                               p_observacion,p_esta_id,p_fecha_recoleccion,UPPER(p_usuario),p_codigo_error,p_mensaje);   
  END IF;
                         
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_APROBA_INSUMOS SE PRESENTO'||SQLERRM;                                                                                   
END Pr_InsertarIA_DISTRITO;

PROCEDURE pr_InsertarAPENIN_D
/********************************************************************************
 DESCRIPCION   : Metodo para incorporar informacion sobre la tabla SIPSA_APROBA_ENCA_INSUMOS_D

 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 Realizado por : Marco Guzman
 Fecha Creacion: 29/06/2019
 
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS

contador NUMBER;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador := NULL;

    SELECT COUNT(1) INTO contador FROM SIPSA_APROBA_ENCA_INSUMOS_D
    WHERE FUTI_ID = p_futi_id
    AND uspe_id= p_uspe_id
    AND ARTI_ID = p_arti_id 
    ---AND GRIN_ID = p_grin2_id
    AND ENIN_ARTI_NOMBRE = p_arti_nombre
    --AND ENIN_NOVEDAD = p_novedad
    AND ENIN_NOMBRE_FUENTE = p_nom_fuente 
    AND ENIN_MUNICIPIO = p_municipio
    AND ENIN_MUNI_ID = p_muni_id
    AND ENIN_DIRECCION = p_direccion
    AND ENIN_TELEFONO = p_telefono
    AND ENIN_EMAIL = p_email
    AND ENIN_INFORMANTE = p_informante
    AND ENIN_TEL_INFORMANTE = p_tel_informante
    AND ENIN_FECHA_PROGRAMADA = p_prre_fecha_programada;


    IF contador = 0 THEN
      
	   INSERT INTO SIPSA_APROBA_ENCA_INSUMOS_D(FUTI_ID,USPE_ID,ARTI_ID,ENIN_FECHA_PROGRAMADA,ENIN_ARTI_NOMBRE,ENIN_USUARIO_CREACION,ENIN_FECHA_CREACION,
     ENIN_NOMBRE_FUENTE,ENIN_MUNICIPIO,ENIN_MUNI_ID,ENIN_DIRECCION,ENIN_TELEFONO,ENIN_EMAIL,ENIN_INFORMANTE,ENIN_TEL_INFORMANTE)
        VALUES (p_futi_id, p_uspe_id,p_arti_id, p_prre_fecha_programada,p_arti_nombre,p_usuario,sysdate,
        p_nom_fuente,p_municipio,p_muni_id,p_direccion,p_telefono,p_email,p_informante,p_tel_informante);
   ELSE
        PQ_SIPSA_APROBACIONES.pr_ActualizarAPENIN_D (p_futi_id, p_uspe_id,p_arti_id, NULL, p_prre_fecha_programada,p_arti_nombre,p_novedad, p_obse_id, p_observacion, p_usuario, 
        p_nom_fuente,p_municipio,p_muni_id,p_direccion,p_telefono,p_email,p_informante,p_tel_informante, p_codigo_error,p_mensaje);
   END IF;     
EXCEPTION
       WHEN OTHERS THEN
            IF SQLCODE = -1 THEN
             --p_codigo_error := 1;
             --p_mensaje      := 'YA EXISTE LA FUENTE: '||p_futi_id ||' Y EL USUARIO:' ||p_uspe_id ||' EN LA FECHA:' ||p_prre_fecha_programada ;
            --ELSE
             p_codigo_error := 1;
             p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_APROBA_ENCA_INSUMOS_D SE PRESENTO '||SQLERRM;
            END IF;
END pr_InsertarAPENIN_D;

PROCEDURE pr_ActualizarAPENIN_D
/********************************************************************************
 DESCRIPCION   : Metodo para actualizar informacion sobre la tabla SIPSA_APROBA_ENCA_INSUMOS_D

 out           : p_codigo_error      valor que indioca la ocurrencia o no de error en el proceso
                 p_mensaje           mensaje del error ocurrido

 Realizado por : Marco Guzman
 Fecha Creacion: 29/06/2019
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS 
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
    UPDATE SIPSA_APROBA_ENCA_INSUMOS_D
    SET --ENIN_NOVEDAD = p_novedad, OBSE_ID = p_obse_id, ENIN_OBSERVACION = p_observacion, 
           ENIN_USUARIO_modificacion = p_usuario
    WHERE FUTI_ID = p_futi_id
    AND USPE_ID =p_uspe_id
    AND ARTI_ID = p_arti_id
    --AND GRIN_ID = p_grin2_id
    AND ENIN_ARTI_NOMBRE = p_arti_nombre
    --AND ENIN_NOVEDAD = p_novedad
    AND ENIN_NOMBRE_FUENTE = p_nom_fuente 
    AND ENIN_MUNICIPIO = p_municipio
    AND ENIN_MUNI_ID = p_muni_id
    AND ENIN_DIRECCION = p_direccion
    AND ENIN_TELEFONO = p_telefono
    AND ENIN_EMAIL = p_email
    AND ENIN_INFORMANTE = p_informante
    AND ENIN_TEL_INFORMANTE = p_tel_informante
    AND ENIN_FECHA_PROGRAMADA = p_prre_fecha_programada;
    
       IF SQL%NOTFOUND THEN
          p_codigo_error := 1;
          p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ACTUALIZAR CON LOS IDENTIFICADORES DADOS EN LA TABLA SIPSA_APROBA_ENCA_INSUMOS_D';
       END IF; 
    
EXCEPTION
       WHEN OTHERS THEN
            IF SQLCODE = -1 THEN
             p_codigo_error := 1;
             p_mensaje      := 'AL ACTUALIZAR EN LA TABLA SIPSA_APROBA_ENCA_INSUMOS_D SE PRESENTO '||SQLERRM;
            END IF;
END pr_ActualizarAPENIN_D;


PROCEDURE pr_InsertarAPRECI2_D
/********************************************************************************
 Descripcion   : metodo para incorporar informacion sobre la tabla SIPSA_APROBA_RECO_INSUMOS_D
 
 Realizado por : Marco Guzman
 Fecha Creacion: 19/06/2019
*******************************************************************************/
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
 p_obse_id                  IN SIPSA_APROBA_RECO_INSUMOS_D.OBSE_ID%TYPE,
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
 p_mensaje                     OUT VARCHAR2) IS 
contador NUMBER;
--v_usuario SIPSA_USUARIOS.usua_usuario%TYPE;
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   contador := NULL;

    SELECT COUNT(1) INTO contador FROM SIPSA_APROBA_RECO_INSUMOS_D
     WHERE FUTI_ID = p_futi_id
    AND USPE_ID= p_uspe_id
    AND ARTI_ID = p_arti_id
    AND GRIN_ID = p_grin2_id
    AND APRE_FECHA_PROGRAMADA = p_prre_fecha_programada
    AND APRE_NOM_INFORMANTE =p_nom_informante
    AND APRE_TEL_INFORMANTE = p_tel_informante    
    AND APRE_NOMBRE_FUENTE = p_nom_fuente
    AND APRE_MUNICIPIO = p_municipio
    AND APRE_MUNI_ID = p_muni_id 
    AND APRE_DIRECCION = p_direccion
    AND APRE_TELEFONO = p_telefono
    AND APRE_EMAIL = p_email
    AND APRE_INFORMANTE = p_informante;
    

    IF contador = 0 THEN
        INSERT INTO    SIPSA_APROBA_RECO_INSUMOS_D (FUTI_ID,USPE_ID,ARTI_ID,GRIN_ID,APRE_FECHA_PROGRAMADA,APRE_ID_INFORMANTE,APRE_NOM_INFORMANTE,APRE_TEL_INFORMANTE,
                                                                             APRE_PRECIO_RECOLECTADO,APRE_NOVEDAD,OBSE_ID,APRE_OBSERVACION,APRE_FECHA_RECOLECCION,ESTA_ID,APRE_USUARIO_CREACION,
                                                                             APRE_FECHA_CREACION,APRE_NOMBRE_FUENTE,APRE_MUNICIPIO,APRE_MUNI_ID,APRE_DIRECCION,APRE_TELEFONO,APRE_EMAIL,APRE_INFORMANTE)
                                                                             
        VALUES(p_futi_id,p_uspe_id,p_arti_id,p_grin2_id,p_prre_fecha_programada,p_id_informante,p_nom_informante,p_tel_informante,
                     p_precio_recolectado,p_novedad,p_obse_id,p_rein2_observacion,p_fecha_recoleccion,p_esta_id,p_usuario,sysdate,
                     p_nom_fuente,p_municipio,p_muni_id,p_direccion,p_telefono,p_email,p_informante);
    ELSE
        PQ_SIPSA_APROBACIONES.pr_ActualizarAPRECI2_D(p_futi_id,p_uspe_id,p_arti_id,p_grin2_id,p_prre_fecha_programada,p_id_informante,
                                                                                 p_nom_informante,p_tel_informante,p_precio_recolectado,p_novedad,p_obse_id,p_rein2_observacion,
                                                                                 p_fecha_recoleccion,p_esta_id,p_usuario,
                                                                                 p_nom_fuente,p_municipio,p_muni_id,p_direccion,p_telefono,p_email,p_informante,
                                                                                 p_codigo_error,p_mensaje);
    END IF;
                  
    EXCEPTION
        WHEN OTHERS THEN
            --p_codigo_error := 1;
             --p_mensaje      := 'YA EXISTE LA FUENTE: '||p_futi_id ||' Y EL USUARIO:' ||p_uspe_id ||' EN LA FECHA:' ||p_prre_fecha_programada ;
            --ELSE
            p_codigo_error := 1;
            p_mensaje      := 'AL INSERTAR EN LA TABLA SIPSA_APROBA_RECO_INSUMOS_D SE PRESENTO '||SQLERRM;                                                                                   
END pr_InsertarAPRECI2_D;


PROCEDURE pr_ActualizarAPRECI2_D
/********************************************************************************
 Descripcion   : metodo para actualizar informacion sobre la tabla SIPSA_APROBA_RECO_INSUMOS_D

 Realizado por : Marco Guzman
 Fecha Creacion: 19/06/2019
*******************************************************************************/
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
 p_mensaje                     OUT VARCHAR2) IS
BEGIN
   p_codigo_error := 0;
   p_mensaje      := NULL;
   
   UPDATE SIPSA_APROBA_RECO_INSUMOS_D
   SET  APRE_PRECIO_RECOLECTADO = p_precio_recolectado, 
        APRE_NOVEDAD = p_novedad, OBSE_ID = p_obse_id, 
        APRE_OBSERVACION = p_rein2_observacion, 
        APRE_FECHA_RECOLECCION = p_fecha_recoleccion, 
        APRE_USUARIO_MODIFICACION = p_usuario,
        APRE_FECHA_MODIFICACION = sysdate
   WHERE FUTI_ID = p_futi_id 
   AND USPE_ID= p_uspe_id
   AND ARTI_ID = p_arti_id
   AND GRIN_ID = p_grin2_id
   AND APRE_FECHA_PROGRAMADA = p_prre_fecha_programada
   AND APRE_NOM_INFORMANTE =p_nom_informante
   AND APRE_TEL_INFORMANTE = p_tel_informante    
   AND APRE_NOMBRE_FUENTE = p_nom_fuente
   AND APRE_MUNICIPIO = p_municipio
   AND APRE_MUNI_ID = p_muni_id 
   AND APRE_DIRECCION = p_direccion
   AND APRE_TELEFONO = p_telefono
   AND APRE_EMAIL = p_email
   AND APRE_INFORMANTE = p_informante;
    
       IF SQL%NOTFOUND THEN
          p_codigo_error := 1;
          p_mensaje      := 'NO SE ENCONTRO NINGUN REGISTRO PARA ACTUALIZAR CON LOS IDENTIFICADORES DADOS EN LA TABLA SIPSA_APROBA_RECO_INSUMOS_D';
       END IF; 
          
EXCEPTION
       WHEN OTHERS THEN
            p_codigo_error := 1;
            p_mensaje      := 'AL ACTUALIZAR EN LA TABLA SIPSA_APROBA_RECO_INSUMOS_D SE PRESENTO '||SQLERRM;                                                                                   
END pr_ActualizarAPRECI2_D; 


END PQ_SIPSA_APROBACIONES;