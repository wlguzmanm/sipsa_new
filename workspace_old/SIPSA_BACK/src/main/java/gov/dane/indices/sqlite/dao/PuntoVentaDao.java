package gov.dane.indices.sqlite.dao;


public class PuntoVentaDao  {

    public static String createTable( boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        return "CREATE TABLE " + constraint + "\"PUNTO_VENTA\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"DESCRIPCION\" TEXT);"; // 1: descripcion
    }

   
    public static String dropTable(boolean ifExists) {
        return "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PUNTO_VENTA\"";
      
    }

}
