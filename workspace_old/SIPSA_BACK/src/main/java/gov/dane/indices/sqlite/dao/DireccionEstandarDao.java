package gov.dane.indices.sqlite.dao;


public class DireccionEstandarDao  {

    public static String createTable( boolean ifNotExists) {
    	String constraint = ifNotExists? "IF NOT EXISTS ": "";
        return "CREATE TABLE " + constraint + "\"DIRECCION_ESTANDAR\" (" + //
        		"\"ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idValorArgumento
                "\"ID_VALOR_ARGUMENTO\" INTEGER ," + // 0: idValorArgumento
                "\"CATEGORIA\" TEXT," + // 1: categoria
                "\"ABREVIATURA\" TEXT," + // 2: abreviatura
                "\"DESCRIPCION\" TEXT," + // 3: descripcion
                "\"ORDEN\" TEXT);"; // 4: orden
    }

   
    public static String dropTable(boolean ifExists) {
        return "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DIRECCION_ESTANDAR\"";
      
    }

}
