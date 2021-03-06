package gov.dane.sipsa.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import gov.dane.sipsa.dao.Informante;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "INFORMANTE".
*/
public class InformanteDao extends AbstractDao<Informante, Long> {

    public static final String TABLENAME = "INFORMANTE";

    /**
     * Properties of entity Informante.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property IdInformante = new Property(0, Long.class, "idInformante", true, "ID_INFORMANTE");
        public final static Property IdMunicipio = new Property(1, Long.class, "idMunicipio", false, "ID_MUNICIPIO");
        public final static Property Nombre = new Property(2, String.class, "nombre", false, "NOMBRE");
        public final static Property Telefono = new Property(3, String.class, "telefono", false, "TELEFONO");
        public final static Property FechaCreacion = new Property(4, java.util.Date.class, "fechaCreacion", false, "FECHA_CREACION");
        public final static Property UsuarioCreacion = new Property(5, String.class, "usuarioCreacion", false, "USUARIO_CREACION");
        public final static Property FechaModificacion = new Property(6, java.util.Date.class, "fechaModificacion", false, "FECHA_MODIFICACION");
        public final static Property FechaVencimineto = new Property(7, java.util.Date.class, "fechaVencimineto", false, "FECHA_VENCIMINETO");
    };


    public InformanteDao(DaoConfig config) {
        super(config);
    }
    
    public InformanteDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"INFORMANTE\" (" + //
                "\"ID_INFORMANTE\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idInformante
                "\"ID_MUNICIPIO\" INTEGER," + // 1: idMunicipio
                "\"NOMBRE\" TEXT," + // 2: nombre
                "\"TELEFONO\" TEXT," + // 3: telefono
                "\"FECHA_CREACION\" INTEGER," + // 4: fechaCreacion
                "\"USUARIO_CREACION\" TEXT," + // 5: usuarioCreacion
                "\"FECHA_MODIFICACION\" INTEGER," + // 6: fechaModificacion
                "\"FECHA_VENCIMINETO\" INTEGER);"); // 7: fechaVencimineto
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"INFORMANTE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Informante entity) {
        stmt.clearBindings();
 
        Long idInformante = entity.getIdInformante();
        if (idInformante != null) {
            stmt.bindLong(1, idInformante);
        }
 
        Long idMunicipio = entity.getIdMunicipio();
        if (idMunicipio != null) {
            stmt.bindLong(2, idMunicipio);
        }
 
        String nombre = entity.getNombre();
        if (nombre != null) {
            stmt.bindString(3, nombre);
        }
 
        String telefono = entity.getTelefono();
        if (telefono != null) {
            stmt.bindString(4, telefono);
        }
 
        java.util.Date fechaCreacion = entity.getFechaCreacion();
        if (fechaCreacion != null) {
            stmt.bindLong(5, fechaCreacion.getTime());
        }
 
        String usuarioCreacion = entity.getUsuarioCreacion();
        if (usuarioCreacion != null) {
            stmt.bindString(6, usuarioCreacion);
        }
 
        java.util.Date fechaModificacion = entity.getFechaModificacion();
        if (fechaModificacion != null) {
            stmt.bindLong(7, fechaModificacion.getTime());
        }
 
        java.util.Date fechaVencimineto = entity.getFechaVencimineto();
        if (fechaVencimineto != null) {
            stmt.bindLong(8, fechaVencimineto.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Informante readEntity(Cursor cursor, int offset) {
        Informante entity = new Informante( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // idInformante
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // idMunicipio
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // nombre
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // telefono
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // fechaCreacion
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // usuarioCreacion
            cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)), // fechaModificacion
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)) // fechaVencimineto
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Informante entity, int offset) {
        entity.setIdInformante(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdMunicipio(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setNombre(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTelefono(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFechaCreacion(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setUsuarioCreacion(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setFechaModificacion(cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)));
        entity.setFechaVencimineto(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Informante entity, long rowId) {
        entity.setIdInformante(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Informante entity) {
        if(entity != null) {
            return entity.getIdInformante();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
