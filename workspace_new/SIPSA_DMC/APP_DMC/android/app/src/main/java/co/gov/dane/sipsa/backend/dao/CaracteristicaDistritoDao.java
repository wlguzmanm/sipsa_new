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
public class CaracteristicaDistritoDao extends AbstractDao<CaracteristicaDistrito, Long> {

    public static final String TABLENAME = "CARACTERISTICA_D";

    /**
     * Properties of entity CaracteristicaI01.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property CaraId = new Property(1, Long.class, "caraId", true, "CARA_ID");
        public final static Property CaraDescripcion = new Property(2, String.class, "caraDescripcion", false, "CARA_DESCRIPCION");
        public final static Property TireId = new Property(3, Long.class, "tireId", false, "TIRE_ID");
        public final static Property VapeId = new Property(4, Long.class, "vapeId", false, "VAPE_ID");
        public final static Property VapeDescripcion = new Property(5, Long.class, "vapeDescripcion", false, "VAPE_DESCRIPCION");
    };


    public CaracteristicaDistritoDao(DaoConfig config) {
        super(config);
    }

    public CaracteristicaDistritoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {

       /* String sql = "DROP TABLE \"CARACTERISTICA_D\"";
        db.execSQL(sql);*/

        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CARACTERISTICA_D\" (" + //
                "\"ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CARA_ID\" INTEGER," + // 0: caraId
                "\"CARA_DESCRIPCION\" TEXT," + // 1: caraDescripcion
                "\"TIRE_ID\" INTEGER," + // 2: tireId
                "\"VAPE_ID\" INTEGER," + // 3: vapeId
                "\"VAPE_DESCRIPCION\" TEXT );");  // 1: caraDescripcion);
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CARACTERISTICA_D\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CaracteristicaDistrito entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        Long caraId = entity.getCaraId();
        if (caraId != null) {
            stmt.bindLong(2, caraId);
        }
 
        String caraDescripcion = entity.getCaraDescripcion();
        if (caraDescripcion != null) {
            stmt.bindString(3, caraDescripcion);
        }
 
        Long tireId = entity.getTireId();
        if (tireId != null) {
            stmt.bindLong(4, tireId);
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
    public CaracteristicaDistrito readEntity(Cursor cursor, int offset) {
        CaracteristicaDistrito entity = new CaracteristicaDistrito( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // Id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // caraId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // caraDescripcion
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // tireId
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // vapeId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // vapeDescripcion
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CaracteristicaDistrito entity, int offset) {
        entity.setCaraId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCaraId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setCaraDescripcion(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTireId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setVapeId(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setVapeDescripcion(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(CaracteristicaDistrito entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(CaracteristicaDistrito entity) {
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