package gov.dane.indices.sqlite.dao;


public class ElementoDao  {

    public static final String TABLENAME = "ELEMENTO";


    /** Creates the underlying database table. */
    public static String createTable(boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        return "CREATE TABLE " + constraint + "\"ELEMENTO\" (" + //
                "\"ID_ELEMENTO\" INTEGER PRIMARY KEY ," + // 0: idElemento
                "\"PRIMER_NIVEL\" TEXT," + // 1: primerNivel
                "\"SEGUNDO_NIVEL\" TEXT," + // 2: segundoNivel
                "\"NOMBRE_ELEMENTO\" TEXT," + // 3: nombreElemento
                "\"UNIDAD_MEDIDA\" TEXT," + // 4: unidadMedida
                "\"TIPO\" INTEGER," + // 5: tipo
                "\"CODIGO_TEMATICO\" TEXT," + // 6: codigoTematico
                "\"CANTIDAD_BASE\" REAL," + // 7: cantidadBase
                "\"MENSUAL_OBSERVADO\" TEXT," + // 8: mensualObservado
                "\"CODIGO_INVIMA\" TEXT," + // 9: codigoInvima
                "\"CODIGO_DANE\" TEXT);"; // 10: codigoDane
    }

    /** Drops the underlying database table. */
    public static String dropTable( boolean ifExists) {
        return "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "ELEMENTO";
    }


}
