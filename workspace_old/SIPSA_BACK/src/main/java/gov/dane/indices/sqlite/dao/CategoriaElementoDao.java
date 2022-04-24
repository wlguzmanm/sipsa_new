package gov.dane.indices.sqlite.dao;


public class CategoriaElementoDao  {

    public static String createTable( boolean ifNotExists) {
    	String constraint = ifNotExists? "IF NOT EXISTS ": "";
        return "CREATE TABLE " + constraint + "\"CATEGORIA_ELEMENTO\" (" + //
                "\"ID_CATEGORIA_ELEMENTO\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idCategoriaElemento
                "\"NOMBRE\" TEXT," + // 1: nombre
                "\"ID_CANASTA\" INTEGER);"; // 2: idCanasta
    }

   
    public static String dropTable(boolean ifExists) {
        return "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CATEGORIA_ELEMENTO\"";
      
    }

}
