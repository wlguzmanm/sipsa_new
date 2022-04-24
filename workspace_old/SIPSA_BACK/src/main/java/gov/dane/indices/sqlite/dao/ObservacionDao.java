package gov.dane.indices.sqlite.dao;


public class ObservacionDao {

  
    public static String createTable(boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        return "CREATE TABLE " + constraint + "\"OBSERVACION\" (" + //
                "\"ID_OBSERVACION\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idObservacion
                "\"DESCRIPCION\" TEXT," + // 1: descripcion
                "\"NOVEDAD\" TEXT," + // 2: novedad
                "\"TENDENCIA\" INTEGER," + // 3: tendencia
                "\"ID_FUENTE\" INTEGER," + // 4: idFuente
                "\"FECHA_CREACION\" INTEGER," + // 5: fechaCreacion
                "\"ESTADO\" INTEGER," + // 6: estado
                "\"FECHA_MODIFICACION\" INTEGER," + // 7: fechaModificacion
                "\"TIPO\" TEXT," + // 8: tipo
                "\"CIUD_ID_CIUDAD\" INTEGER);"; // 9: ciudIdCiudad
    }

    /** Drops the underlying database table. */
    public static String dropTable( boolean ifExists) {
       return "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"OBSERVACION\"";
    }

}
