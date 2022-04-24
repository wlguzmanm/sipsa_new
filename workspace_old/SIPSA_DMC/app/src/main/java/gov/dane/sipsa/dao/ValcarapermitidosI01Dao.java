package gov.dane.sipsa.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import gov.dane.sipsa.dao.ValcarapermitidosI01;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "VALCARAPERMITIDOS_I01".
*/
public class ValcarapermitidosI01Dao extends AbstractDao<ValcarapermitidosI01, Long> {

    public static final String TABLENAME = "VALCARAPERMITIDOS_I01";

    /**
     * Properties of entity ValcarapermitidosI01.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property TireId = new Property(1, Long.class, "tireId", false, "TIRE_ID");
        public final static Property CaraId = new Property(2, Long.class, "caraId", false, "CARA_ID");
        public final static Property CaraDescripcion = new Property(3, String.class, "caraDescripcion", false, "CARA_DESCRIPCION");
        public final static Property VapeId = new Property(4, Long.class, "vapeId", false, "VAPE_ID");
        public final static Property VapeDescripcion = new Property(5, String.class, "vapeDescripcion", false, "VAPE_DESCRIPCION");
    };


    public ValcarapermitidosI01Dao(DaoConfig config) {
        super(config);
    }
    
    public ValcarapermitidosI01Dao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"VALCARAPERMITIDOS_I01\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TIRE_ID\" INTEGER," + // 1: tireId
                "\"CARA_ID\" INTEGER," + // 2: caraId
                "\"CARA_DESCRIPCION\" TEXT," + // 3: caraDescripcion
                "\"VAPE_ID\" INTEGER," + // 4: vapeId
                "\"VAPE_DESCRIPCION\" TEXT);"); // 5: vapeDescripcion
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"VALCARAPERMITIDOS_I01\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ValcarapermitidosI01 entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long tireId = entity.getTireId();
        if (tireId != null) {
            stmt.bindLong(2, tireId);
        }
 
        Long caraId = entity.getCaraId();
        if (caraId != null) {
            stmt.bindLong(3, caraId);
        }
 
        String caraDescripcion = entity.getCaraDescripcion();
        if (caraDescripcion != null) {
            stmt.bindString(4, caraDescripcion);
        }
 
        Long vapeId = entity.getVapeId();
        if (vapeId != null) {
            stmt.bindLong(5, vapeId);
        }
 
        String vapeDescripcion = entity.getVapeDescripcion();
        if (vapeDescripcion != null) {
            stmt.bindString(6, vapeDescripcion);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ValcarapermitidosI01 readEntity(Cursor cursor, int offset) {
        ValcarapermitidosI01 entity = new ValcarapermitidosI01( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // tireId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // caraId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // caraDescripcion
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // vapeId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // vapeDescripcion
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ValcarapermitidosI01 entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTireId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setCaraId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setCaraDescripcion(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setVapeId(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setVapeDescripcion(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ValcarapermitidosI01 entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ValcarapermitidosI01 entity) {
        if(entity != null) {
            return entity.getId();
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
