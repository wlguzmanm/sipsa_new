package gov.dane.indices.sqlite.dao;


public class EstadoFuenteInfoDao  {

    public static String createTable( boolean ifNotExists) {
    	String constraint = ifNotExists? "IF NOT EXISTS ": "";
        return "CREATE TABLE " + constraint + "\"ESTADO_FUENTE_INFO\" (" + //
                "\"ID_VALOR_ARGUMENTO\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idValorArgumento
                "\"ID_ARGUMENTO\" INTEGER," + // 1: idArgumento
                "\"ABREVIATURA\" TEXT," + // 2: abreviatura
                "\"DESCRIPCION\" TEXT," + // 3: descripcion
                "\"CATEGORIA\" TEXT," + // 4: categoria
                "\"ORDEN\" TEXT," + // 5: orden
                "\"ID_PADRE\" INTEGER);"; // 6: id_padre
    }

   
    public static String dropTable(boolean ifExists) {
        return "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ESTADO_FUENTE_INFO\"";
      
    }

}
