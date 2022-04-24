package gov.dane.indices.sqlite.dao;


public class EstratoDao  {

    public static final String TABLENAME = "ESTRATO";

    
    public static String createTable(boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        return "CREATE TABLE " + constraint + "ESTRATO (" + //
                "\"GRUPO_FUENTE\" INTEGER," + // 0: grupoFuente
                "\"ESTRATO_ASIGNADO\" TEXT," + // 1: estratoAsignado
                "\"_id\" INTEGER PRIMARY KEY ," + // 2: id
                "\"TIPO_FUENTE\" TEXT," + // 3: tipoFuente
                "\"GRUPO\" TEXT);"; // 4: grupo
    }

    
    public static String dropTable( boolean ifExists) {
        return "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "ESTRATO";
    }

    
}
