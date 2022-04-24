package gov.dane.sipsa.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import gov.dane.sipsa.dao.EnvioArtiCaraValoresI01;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ENVIO_ARTI_CARA_VALORES_I01".
*/
public class EnvioArtiCaraValoresI01Dao extends AbstractDao<EnvioArtiCaraValoresI01, Long> {

    public static final String TABLENAME = "ENVIO_ARTI_CARA_VALORES_I01";

    /**
     * Properties of entity EnvioArtiCaraValoresI01.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Grin2Id = new Property(0, Long.class, "grin2Id", true, "GRIN2_ID");
        public final static Property FutiId = new Property(1, Long.class, "futiId", false, "FUTI_ID");
        public final static Property TireId = new Property(2, Long.class, "tireId", false, "TIRE_ID");
        public final static Property CaraId = new Property(3, Long.class, "caraId", false, "CARA_ID");
        public final static Property ArtiId = new Property(4, Long.class, "artiId", false, "ARTI_ID");
        public final static Property VapeId = new Property(5, Long.class, "vapeId", false, "VAPE_ID");
        public final static Property VapeDescripcion = new Property(6, String.class, "vapeDescripcion", false, "VAPE_DESCRIPCION");
    };


    public EnvioArtiCaraValoresI01Dao(DaoConfig config) {
        super(config);
    }
    
    public EnvioArtiCaraValoresI01Dao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ENVIO_ARTI_CARA_VALORES_I01\" (" + //
                "\"GRIN2_ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: grin2Id
                "\"FUTI_ID\" INTEGER," + // 1: futiId
                "\"TIRE_ID\" INTEGER," + // 2: tireId
                "\"CARA_ID\" INTEGER," + // 3: caraId
                "\"ARTI_ID\" INTEGER," + // 4: artiId
                "\"VAPE_ID\" INTEGER," + // 5: vapeId
                "\"VAPE_DESCRIPCION\" TEXT);"); // 6: vapeDescripcion
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ENVIO_ARTI_CARA_VALORES_I01\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, EnvioArtiCaraValoresI01 entity) {
        stmt.clearBindings();
 
        Long grin2Id = entity.getGrin2Id();
        if (grin2Id != null) {
            stmt.bindLong(1, grin2Id);
        }
 
        Long futiId = entity.getFutiId();
        if (futiId != null) {
            stmt.bindLong(2, futiId);
        }
 
        Long tireId = entity.getTireId();
        if (tireId != null) {
            stmt.bindLong(3, tireId);
        }
 
        Long caraId = entity.getCaraId();
        if (caraId != null) {
            stmt.bindLong(4, caraId);
        }
 
        Long artiId = entity.getArtiId();
        if (artiId != null) {
            stmt.bindLong(5, artiId);
        }
 
        Long vapeId = entity.getVapeId();
        if (vapeId != null) {
            stmt.bindLong(6, vapeId);
        }
 
        String vapeDescripcion = entity.getVapeDescripcion();
        if (vapeDescripcion != null) {
            stmt.bindString(7, vapeDescripcion);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public EnvioArtiCaraValoresI01 readEntity(Cursor cursor, int offset) {
        EnvioArtiCaraValoresI01 entity = new EnvioArtiCaraValoresI01( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // grin2Id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // futiId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // tireId
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // caraId
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // artiId
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // vapeId
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // vapeDescripcion
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, EnvioArtiCaraValoresI01 entity, int offset) {
        entity.setGrin2Id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFutiId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setTireId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setCaraId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setArtiId(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setVapeId(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setVapeDescripcion(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(EnvioArtiCaraValoresI01 entity, long rowId) {
        entity.setGrin2Id(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(EnvioArtiCaraValoresI01 entity) {
        if(entity != null) {
            return entity.getGrin2Id();
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