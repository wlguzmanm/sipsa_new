package co.gov.dane.sipsa.backend.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "CARACTERISTICA_I01".
*/
public class CaracteristicaI01Dao extends AbstractDao<CaracteristicaI01, Long> {

    public static final String TABLENAME = "CARACTERISTICA_I01";

    /**
     * Properties of entity CaracteristicaI01.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property CaraId = new Property(0, Long.class, "caraId", true, "CARA_ID");
        public final static Property CaraDescripcion = new Property(1, String.class, "caraDescripcion", false, "CARA_DESCRIPCION");
        public final static Property TireId = new Property(2, Long.class, "tireId", false, "TIRE_ID");
        public final static Property CaraOrden = new Property(3, Long.class, "caraOrden", false, "CARA_ORDEN");
    };


    public CaracteristicaI01Dao(DaoConfig config) {
        super(config);
    }
    
    public CaracteristicaI01Dao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CARACTERISTICA_I01\" (" + //
                "\"CARA_ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: caraId
                "\"CARA_DESCRIPCION\" TEXT," + // 1: caraDescripcion
                "\"TIRE_ID\" INTEGER," + // 2: tireId
                "\"CARA_ORDEN\" INTEGER);"); // 3: caraOrden
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CARACTERISTICA_I01\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CaracteristicaI01 entity) {
        stmt.clearBindings();
 
        Long caraId = entity.getCaraId();
        if (caraId != null) {
            stmt.bindLong(1, caraId);
        }
 
        String caraDescripcion = entity.getCaraDescripcion();
        if (caraDescripcion != null) {
            stmt.bindString(2, caraDescripcion);
        }
 
        Long tireId = entity.getTireId();
        if (tireId != null) {
            stmt.bindLong(3, tireId);
        }
 
        Long caraOrden = entity.getCaraOrden();
        if (caraOrden != null) {
            stmt.bindLong(4, caraOrden);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public CaracteristicaI01 readEntity(Cursor cursor, int offset) {
        CaracteristicaI01 entity = new CaracteristicaI01( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // caraId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // caraDescripcion
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // tireId
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // caraOrden
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CaracteristicaI01 entity, int offset) {
        entity.setCaraId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCaraDescripcion(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTireId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setCaraOrden(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(CaracteristicaI01 entity, long rowId) {
        entity.setCaraId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(CaracteristicaI01 entity) {
        if(entity != null) {
            return entity.getCaraId();
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