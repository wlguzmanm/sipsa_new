package gov.dane.sipsa.dao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "INDICE_ELEMENTO".
*/
public class IndiceElementoDao extends AbstractDao<IndiceElemento, Long> {

    public static final String TABLENAME = "INDICE_ELEMENTO";

    /**
     * Properties of entity IndiceElemento.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IdIndice = new Property(1, long.class, "idIndice", false, "ID_INDICE");
        public final static Property IdElemento = new Property(2, long.class, "idElemento", false, "ID_ELEMENTO");
    };

    private Query<IndiceElemento> elemento_IndiceElementoQuery;
    private Query<IndiceElemento> indice_ElementoIndiceQuery;

    public IndiceElementoDao(DaoConfig config) {
        super(config);
    }
    
    public IndiceElementoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"INDICE_ELEMENTO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"ID_INDICE\" INTEGER NOT NULL ," + // 1: idIndice
                "\"ID_ELEMENTO\" INTEGER NOT NULL );"); // 2: idElemento
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"INDICE_ELEMENTO\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, IndiceElemento entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getIdIndice());
        stmt.bindLong(3, entity.getIdElemento());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public IndiceElemento readEntity(Cursor cursor, int offset) {
        IndiceElemento entity = new IndiceElemento( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // idIndice
            cursor.getLong(offset + 2) // idElemento
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, IndiceElemento entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdIndice(cursor.getLong(offset + 1));
        entity.setIdElemento(cursor.getLong(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(IndiceElemento entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(IndiceElemento entity) {
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
    
    /** Internal query to resolve the "indiceElemento" to-many relationship of Elemento. */
    public List<IndiceElemento> _queryElemento_IndiceElemento(long idElemento) {
        synchronized (this) {
            if (elemento_IndiceElementoQuery == null) {
                QueryBuilder<IndiceElemento> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.IdElemento.eq(null));
                elemento_IndiceElementoQuery = queryBuilder.build();
            }
        }
        Query<IndiceElemento> query = elemento_IndiceElementoQuery.forCurrentThread();
        query.setParameter(0, idElemento);
        return query.list();
    }

    /** Internal query to resolve the "elementoIndice" to-many relationship of Indice. */
    public List<IndiceElemento> _queryIndice_ElementoIndice(long idIndice) {
        synchronized (this) {
            if (indice_ElementoIndiceQuery == null) {
                QueryBuilder<IndiceElemento> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.IdIndice.eq(null));
                indice_ElementoIndiceQuery = queryBuilder.build();
            }
        }
        Query<IndiceElemento> query = indice_ElementoIndiceQuery.forCurrentThread();
        query.setParameter(0, idIndice);
        return query.list();
    }

}