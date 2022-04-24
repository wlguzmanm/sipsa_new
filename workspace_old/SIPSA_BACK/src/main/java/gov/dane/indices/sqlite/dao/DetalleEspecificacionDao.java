package gov.dane.indices.sqlite.dao;


public class DetalleEspecificacionDao  {

    public static String createTable( boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        return "CREATE TABLE " + constraint + "\"DETALLE_ESPECIFICACION\" (" + //
                "\"ID_ESPECIFICACION_DETALLE\" INTEGER PRIMARY KEY ," + // 0: idEspecificacionDetalle
                "\"ID_TIPO_ESPECIFICACION\" INTEGER," + // 1: idTipoEspecificacion
                "\"NOMBRE_ELEMENTO\" TEXT," + // 2: nombreElemento
                "\"TIPO\" TEXT," + // 3: tipo
                "\"VALIDACION\" TEXT," + // 4: validacion
                "\"USUARIO_CREACION\" TEXT," + // 5: usuarioCreacion
                "\"USUARIO_MODIFICACION\" TEXT," + // 6: usuarioModificacion
                "\"LISTA\" TEXT," + // 7: lista
                "\"VALOR_MINIMO\" TEXT," + // 8: valorMinimo
                "\"VALOR_MAXIMO\" TEXT);"; // 9: valorMaximo
    }

   
    public static String dropTable(boolean ifExists) {
        return "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DETALLE_ESPECIFICACION\"";
      
    }

}
