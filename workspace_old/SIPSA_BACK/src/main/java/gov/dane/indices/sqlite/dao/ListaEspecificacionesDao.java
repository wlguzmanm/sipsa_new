package gov.dane.indices.sqlite.dao;


public class ListaEspecificacionesDao  {

    public static String createTable( boolean ifNotExists) {
    	String constraint = ifNotExists? "IF NOT EXISTS ": "";
        return "CREATE TABLE " + constraint + "\"LISTA_ESPECIFICACIONES\" (" + //
                "\"ID_VALOR_ARGUMENTO\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idValorArgumento
                "\"CATEGORIA\" TEXT," + // 1: categoria
                "\"ABREVIATURA\" TEXT," + // 2: abreviatura
                "\"DESCRIPCION\" TEXT," + // 3: descripcion
                "\"ORDEN\" TEXT," + // 4: orden
                "\"ID_PADRE\" INTEGER," + // 5: id_padre
                "\"ADICIONAL\" TEXT);"; // 6: adicional
    }

   
    public static String dropTable(boolean ifExists) {
        return "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LISTA_ESPECIFICACIONES\"";
      
    }

}
