package gov.dane.indices.sqlite.dao;


public class CiudadDao {

  
    public static String createTable( boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
       return "CREATE TABLE " + constraint + "CIUDAD (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NOMBRE\" TEXT NOT NULL );"; // 1: nombre
    }

   
    public static String dropTable(boolean ifExists) {
        return "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CIUDAD\"";
       
    }

}
