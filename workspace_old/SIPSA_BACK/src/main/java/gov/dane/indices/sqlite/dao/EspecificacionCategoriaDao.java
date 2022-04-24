package gov.dane.indices.sqlite.dao;


public class EspecificacionCategoriaDao  {

    public static String createTable( boolean ifNotExists) {
    	String constraint = ifNotExists? "IF NOT EXISTS ": "";
        return "CREATE TABLE " + constraint + "\"ESPECIFICACION_CATEGORIA\" (" + //
                "\"ID_TIPO_ESPECIFICACION\" INTEGER," + // 0: idTipoEspecificacion
                "\"CATEGORIA\" INTEGER," + // 1: categoria
                "\"ESPECIFICACION\" TEXT," + // 2: especificacion
                "\"NOMBRE_CATEGORIA\" TEXT);"; // 3: nombreCategoria
    }

   
    public static String dropTable(boolean ifExists) {
        return "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ESPECIFICACION_CATEGORIA\"";
      
    }

}
