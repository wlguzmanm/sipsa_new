package gov.dane.sipsa.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import gov.dane.sipsa.dao.TipoRecoleccion;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TIPO_RECOLECCION".
*/
public class TipoRecoleccionDao extends AbstractDao<TipoRecoleccion, Long> {

    public static final String TABLENAME = "TIPO_RECOLECCION";

    /**
     * Properties of entity TipoRecoleccion.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property TireId = new Property(0, Long.class, "tireId", true, "TIRE_ID");
        public final static Property TireNombre = new Property(1, String.class, "tireNombre", false, "TIRE_NOMBRE");
    };

    private DaoSession daoSession;


    public TipoRecoleccionDao(DaoConfig config) {
        super(config);
    }
    
    public TipoRecoleccionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TIPO_RECOLECCION\" (" + //
                "\"TIRE_ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: tireId
                "\"TIRE_NOMBRE\" TEXT);"); // 1: tireNombre
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TIPO_RECOLECCION\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TipoRecoleccion entity) {
        stmt.clearBindings();
 
        Long tireId = entity.getTireId();
        if (tireId != null) {
            stmt.bindLong(1, tireId);
        }
 
        String tireNombre = entity.getTireNombre();
        if (tireNombre != null) {
            stmt.bindString(2, tireNombre);
        }
    }

    @Override
    protected void attachEntity(TipoRecoleccion entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public TipoRecoleccion readEntity(Cursor cursor, int offset) {
        TipoRecoleccion entity = new TipoRecoleccion( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // tireId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // tireNombre
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TipoRecoleccion entity, int offset) {
        entity.setTireId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTireNombre(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TipoRecoleccion entity, long rowId) {
        entity.setTireId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TipoRecoleccion entity) {
        if(entity != null) {
            return entity.getTireId();
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
