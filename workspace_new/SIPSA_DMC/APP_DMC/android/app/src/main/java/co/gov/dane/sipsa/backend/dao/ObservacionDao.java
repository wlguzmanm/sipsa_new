package co.gov.dane.sipsa.backend.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "OBSERVACION".
*/
public class ObservacionDao extends AbstractDao<Observacion, Long> {

    public static final String TABLENAME = "OBSERVACION";

    /**
     * Properties of entity ArtiCaraValores.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property ObseId = new Property(0, Long.class, "obseId", true, "OBSE_ID");
        public final static Property ObseTipo = new Property(1, String.class, "obseTipo", false, "OBSE_TIPO");
        public final static Property Novedad = new Property(2, String.class, "novedad", false, "NOVEDAD");
        public final static Property ObseDescripcion = new Property(3, String.class, "obseDescripcion", false, "OBSE_DESCRIPCION");
    };


    public ObservacionDao(DaoConfig config) {
        super(config);
    }
    
    public ObservacionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"OBSERVACION\" (" + //
                "\"OBSE_ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: obseId
                "\"OBSE_TIPO\" TEXT," + // 1: obseTipo
                "\"NOVEDAD\" TEXT," + // 2: novedad
                "\"OBSE_DESCRIPCION\" TEXT);"); // 3: obseDescripcion
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"OBSERVACION\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Observacion entity) {
        stmt.clearBindings();
 
        Long obseId = entity.getObseId();
        if (obseId != null) {
            stmt.bindLong(1, obseId);
        }
 
        String obseTipo = entity.getObseTipo();
        if (obseTipo != null) {
            stmt.bindString(2, obseTipo);
        }
 
        String novedad = entity.getNovedad();
        if (novedad != null) {
            stmt.bindString(3, novedad);
        }
 
        String obseDescripcion = entity.getObseDescripcion();
        if (obseDescripcion != null) {
            stmt.bindString(4, obseDescripcion);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Observacion readEntity(Cursor cursor, int offset) {
        Observacion entity = new Observacion( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // obseId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // obseTipo
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // novedad
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // obseDescripcion
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Observacion entity, int offset) {
        entity.setObseId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setObseTipo(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNovedad(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setObseDescripcion(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Observacion entity, long rowId) {
        entity.setObseId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Observacion entity) {
        if(entity != null) {
            return entity.getObseId();
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
