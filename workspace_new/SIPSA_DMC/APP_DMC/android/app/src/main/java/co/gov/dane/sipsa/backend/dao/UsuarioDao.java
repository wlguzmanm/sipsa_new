package co.gov.dane.sipsa.backend.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "USUARIO".
*/
public class UsuarioDao extends AbstractDao<Usuario, Long> {

    public static final String TABLENAME = "USUARIO";

    /**
     * Properties of entity Usuario.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property UspeID = new Property(0, Long.class, "uspeID", true, "USPE_ID");
        public final static Property UsuaId = new Property(1, Long.class, "usuaId", false, "USUA_ID");
        public final static Property PerfID = new Property(2, Long.class, "perfID", false, "PERF_ID");
        public final static Property Usuario = new Property(3, String.class, "usuario", false, "USUARIO");
        public final static Property Clave = new Property(4, String.class, "clave", false, "CLAVE");
        public final static Property NombrePersona = new Property(5, String.class, "nombrePersona", false, "NOMBRE_PERSONA");
        public final static Property NombrePerfil = new Property(6, String.class, "nombrePerfil", false, "NOMBRE_PERFIL");
        public final static Property UspeUsuarioModificacion = new Property(7, String.class, "uspeUsuarioModificacion", false, "USPE_USUARIO_MODIFICACION");
        public final static Property UspeFechaModificacion = new Property(8, java.util.Date.class, "uspeFechaModificacion", false, "USPE_FECHA_MODIFICACION");
        public final static Property UspeUsuarioCreacion = new Property(9, String.class, "uspeUsuarioCreacion", false, "USPE_USUARIO_CREACION");
        public final static Property UspeFechaCreacion = new Property(10, java.util.Date.class, "uspeFechaCreacion", false, "USPE_FECHA_CREACION");
        public final static Property UspeFechaDesde = new Property(11, java.util.Date.class, "uspeFechaDesde", false, "USPE_FECHA_DESDE");
        public final static Property UspeFechaHasta = new Property(12, java.util.Date.class, "uspeFechaHasta", false, "USPE_FECHA_HASTA");
    };


    public UsuarioDao(DaoConfig config) {
        super(config);
    }
    
    public UsuarioDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USUARIO\" (" + //
                "\"USPE_ID\" INTEGER PRIMARY KEY ," + // 0: uspeID
                "\"USUA_ID\" INTEGER," + // 1: usuaId
                "\"PERF_ID\" INTEGER," + // 2: perfID
                "\"USUARIO\" TEXT," + // 3: usuario
                "\"CLAVE\" TEXT," + // 4: clave
                "\"NOMBRE_PERSONA\" TEXT," + // 5: nombrePersona
                "\"NOMBRE_PERFIL\" TEXT," + // 6: nombrePerfil
                "\"USPE_USUARIO_MODIFICACION\" TEXT," + // 7: uspeUsuarioModificacion
                "\"USPE_FECHA_MODIFICACION\" INTEGER," + // 8: uspeFechaModificacion
                "\"USPE_USUARIO_CREACION\" TEXT," + // 9: uspeUsuarioCreacion
                "\"USPE_FECHA_CREACION\" INTEGER," + // 10: uspeFechaCreacion
                "\"USPE_FECHA_DESDE\" INTEGER," + // 11: uspeFechaDesde
                "\"USPE_FECHA_HASTA\" INTEGER);"); // 12: uspeFechaHasta
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USUARIO\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Usuario entity) {
        stmt.clearBindings();
 
        Long uspeID = entity.getUspeID();
        if (uspeID != null) {
            stmt.bindLong(1, uspeID);
        }
 
        Long usuaId = entity.getUsuaId();
        if (usuaId != null) {
            stmt.bindLong(2, usuaId);
        }
 
        Long perfID = entity.getPerfID();
        if (perfID != null) {
            stmt.bindLong(3, perfID);
        }
 
        String usuario = entity.getUsuario();
        if (usuario != null) {
            stmt.bindString(4, usuario);
        }
 
        String clave = entity.getClave();
        if (clave != null) {
            stmt.bindString(5, clave);
        }
 
        String nombrePersona = entity.getNombrePersona();
        if (nombrePersona != null) {
            stmt.bindString(6, nombrePersona);
        }
 
        String nombrePerfil = entity.getNombrePerfil();
        if (nombrePerfil != null) {
            stmt.bindString(7, nombrePerfil);
        }
 
        String uspeUsuarioModificacion = entity.getUspeUsuarioModificacion();
        if (uspeUsuarioModificacion != null) {
            stmt.bindString(8, uspeUsuarioModificacion);
        }
 
        java.util.Date uspeFechaModificacion = entity.getUspeFechaModificacion();
        if (uspeFechaModificacion != null) {
            stmt.bindLong(9, uspeFechaModificacion.getTime());
        }
 
        String uspeUsuarioCreacion = entity.getUspeUsuarioCreacion();
        if (uspeUsuarioCreacion != null) {
            stmt.bindString(10, uspeUsuarioCreacion);
        }
 
        java.util.Date uspeFechaCreacion = entity.getUspeFechaCreacion();
        if (uspeFechaCreacion != null) {
            stmt.bindLong(11, uspeFechaCreacion.getTime());
        }
 
        java.util.Date uspeFechaDesde = entity.getUspeFechaDesde();
        if (uspeFechaDesde != null) {
            stmt.bindLong(12, uspeFechaDesde.getTime());
        }
 
        java.util.Date uspeFechaHasta = entity.getUspeFechaHasta();
        if (uspeFechaHasta != null) {
            stmt.bindLong(13, uspeFechaHasta.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Usuario readEntity(Cursor cursor, int offset) {
        Usuario entity = new Usuario( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // uspeID
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // usuaId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // perfID
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // usuario
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // clave
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // nombrePersona
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // nombrePerfil
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // uspeUsuarioModificacion
            cursor.isNull(offset + 8) ? null : new java.util.Date(cursor.getLong(offset + 8)), // uspeFechaModificacion
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // uspeUsuarioCreacion
            cursor.isNull(offset + 10) ? null : new java.util.Date(cursor.getLong(offset + 10)), // uspeFechaCreacion
            cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)), // uspeFechaDesde
            cursor.isNull(offset + 12) ? null : new java.util.Date(cursor.getLong(offset + 12)) // uspeFechaHasta
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Usuario entity, int offset) {
        entity.setUspeID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUsuaId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setPerfID(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setUsuario(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setClave(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setNombrePersona(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setNombrePerfil(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setUspeUsuarioModificacion(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setUspeFechaModificacion(cursor.isNull(offset + 8) ? null : new java.util.Date(cursor.getLong(offset + 8)));
        entity.setUspeUsuarioCreacion(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setUspeFechaCreacion(cursor.isNull(offset + 10) ? null : new java.util.Date(cursor.getLong(offset + 10)));
        entity.setUspeFechaDesde(cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)));
        entity.setUspeFechaHasta(cursor.isNull(offset + 12) ? null : new java.util.Date(cursor.getLong(offset + 12)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Usuario entity, long rowId) {
        entity.setUspeID(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Usuario entity) {
        if(entity != null) {
            return entity.getUspeID();
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
