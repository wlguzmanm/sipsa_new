package co.gov.dane.sipsa.backend.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "ARTICULO_DISTRITO".
*/
public class ArticuloDistritoDao extends AbstractDao<ArticuloDistrito, Long> {

    public static final String TABLENAME = "ARTICULO_DISTRITO";

    /**
     * Properties of entity Articulo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property ArticacoId = new Property(0, Long.class, "articacoId", true, "ARTICACO_ID");
        public final static Property ArtiId = new Property(1, Long.class, "artiId", false, "ARTI_ID");
        public final static Property ArtiNombre = new Property(2, String.class, "artiNombre", false, "ARTI_NOMBRE");
        public final static Property CacoNombre = new Property(3, String.class, "cacoNombre", false, "CACO_NOMBRE");
        public final static Property TireNombre = new Property(4, String.class, "tireNombre", false, "TIRE_NOMBRE");
        public final static Property GrupNombre = new Property(5, String.class, "grupNombre", false, "GRUP_NOMBRE");
        public final static Property ArticacoRegicaLinea = new Property(6, String.class, "articacoRegicaLinea", false, "ARTICACO_REGICA_LINEA");
        public final static Property TireId = new Property(7, Long.class, "tireId", false, "TIRE_ID");
        public final static Property CacoId = new Property(8, Long.class, "cacoId", false, "CACO_ID");
        public final static Property tipo = new Property(9, String.class, "tipo", false, "TIPO");
        public final static Property frecuencia = new Property(10, String.class, "frecuencia", false, "FRECUENCIA");
        public final static Property unidad = new Property(11, String.class, "unidad", false, "UNIDAD");
        public final static Property observacion = new Property(12, String.class, "observacion", false, "OBSERVACION");
    };

    private DaoSession daoSession;

    private Query<ArticuloDistrito> tipoRecoleccion_ArtiTipoRecoleccionQuery;
    private Query<ArticuloDistrito> casaComercial_ArtiCasaComercialQuery;

    public ArticuloDistritoDao(DaoConfig config) {
        super(config);
    }

    public ArticuloDistritoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        Boolean campo1 = false, campo2= false, campo3= false, campo4= false;
        if(ifNotExists){
            Cursor cursor = db.rawQuery("pragma table_info(ARTICULO_DISTRITO)", null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        if(cursor.getString(1).equals("TIPO") ){
                            campo1 = true;
                        }
                        if(cursor.getString(1).equals("FRECUENCIA") ){
                            campo2 = true;
                        }
                        if(cursor.getString(1).equals("UNIDAD") ){
                            campo3 = true;
                        }
                        if(cursor.getString(1).equals("OBSERVACION") ){
                            campo4 = true;
                        }
                    }
                    if(!campo1)db.execSQL("ALTER TABLE ARTICULO_DISTRITO ADD COLUMN TIPO TEXT");
                    if(!campo2)db.execSQL("ALTER TABLE ARTICULO_DISTRITO ADD COLUMN FRECUENCIA TEXT");
                    if(!campo3)db.execSQL("ALTER TABLE ARTICULO_DISTRITO ADD COLUMN UNIDAD TEXT");
                    if(!campo4)db.execSQL("ALTER TABLE ARTICULO_DISTRITO ADD COLUMN OBSERVACION TEXT");
                }
                cursor.close();
            }
        }
        db.execSQL("CREATE TABLE " + constraint + "\"ARTICULO_DISTRITO\" (" + //
                "\"ARTICACO_ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: articacoId
                "\"ARTI_ID\" INTEGER," + // 1: artiId
                "\"ARTI_NOMBRE\" TEXT," + // 2: artiNombre
                "\"CACO_NOMBRE\" TEXT," + // 3: cacoNombre
                "\"TIRE_NOMBRE\" TEXT," + // 4: tireNombre
                "\"GRUP_NOMBRE\" TEXT," + // 5: grupNombre
                "\"ARTICACO_REGICA_LINEA\" TEXT," + // 6: articacoRegicaLinea
                "\"TIRE_ID\" INTEGER," + // 7: tireId
                "\"CACO_ID\" INTEGER," + // 8: cacoId
                "\"TIPO\" TEXT," + // 9: tipo
                "\"FRECUENCIA\" TEXT," + // 10: frecuencia
                "\"UNIDAD\" TEXT," + // 11: unidad
                "\"OBSERVACION\" TEXT);"); // 12: observacion

    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ARTICULO_DISTRITO\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ArticuloDistrito entity) {
        stmt.clearBindings();
 
        Long articacoId = entity.getArticacoId();
        if (articacoId != null) {
            stmt.bindLong(1, articacoId);
        }
 
        Long artiId = entity.getArtiId();
        if (artiId != null) {
            stmt.bindLong(2, artiId);
        }
 
        String artiNombre = entity.getArtiNombre();
        if (artiNombre != null) {
            stmt.bindString(3, artiNombre);
        }
 
        String cacoNombre = entity.getCacoNombre();
        if (cacoNombre != null) {
            stmt.bindString(4, cacoNombre);
        }
 
        String tireNombre = entity.getTireNombre();
        if (tireNombre != null) {
            stmt.bindString(5, tireNombre);
        }
 
        String grupNombre = entity.getGrupNombre();
        if (grupNombre != null) {
            stmt.bindString(6, grupNombre);
        }
 
        String articacoRegicaLinea = entity.getArticacoRegicaLinea();
        if (articacoRegicaLinea != null) {
            stmt.bindString(7, articacoRegicaLinea);
        }
 
        Long tireId = entity.getTireId();
        if (tireId != null) {
            stmt.bindLong(8, tireId);
        }
 
        Long cacoId = entity.getCacoId();
        if (cacoId != null) {
            stmt.bindLong(9, cacoId);
        }

        String tipo = entity.getTipo();
        if (tipo != null) {
            stmt.bindString(10, tipo);
        }

        String frecuencia = entity.getFrecuencia();
        if (frecuencia != null) {
            stmt.bindString(11, frecuencia);
        }

        String unidad = entity.getUnidad();
        if (unidad != null) {
            stmt.bindString(12, unidad);
        }

        String observacion = entity.getObservacion();
        if (observacion != null) {
            stmt.bindString(13, observacion);
        }
    }

    @Override
    protected void attachEntity(ArticuloDistrito entity) {
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
    public ArticuloDistrito readEntity(Cursor cursor, int offset) {
        ArticuloDistrito entity = new ArticuloDistrito( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // articacoId
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // artiId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // artiNombre
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // cacoNombre
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // tireNombre
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // grupNombre
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // articacoRegicaLinea
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7), // tireId
            cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8), // cacoId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // cacoId
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // cacoId
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // cacoId
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12) // cacoId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ArticuloDistrito entity, int offset) {
        entity.setArticacoId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setArtiId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setArtiNombre(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCacoNombre(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTireNombre(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setGrupNombre(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setArticacoRegicaLinea(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setTireId(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
        entity.setCacoId(cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8));
        entity.setTipo(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setFrecuencia(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setUnidad(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setObservacion(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ArticuloDistrito entity, long rowId) {
        entity.setArticacoId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ArticuloDistrito entity) {
        if(entity != null) {
            return entity.getArticacoId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "artiTipoRecoleccion" to-many relationship of TipoRecoleccion. */
    public List<ArticuloDistrito> _queryTipoRecoleccion_ArtiTipoRecoleccion(Long tireId) {
        synchronized (this) {
            if (tipoRecoleccion_ArtiTipoRecoleccionQuery == null) {
                QueryBuilder<ArticuloDistrito> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.TireId.eq(null));
                tipoRecoleccion_ArtiTipoRecoleccionQuery = queryBuilder.build();
            }
        }
        Query<ArticuloDistrito> query = tipoRecoleccion_ArtiTipoRecoleccionQuery.forCurrentThread();
        query.setParameter(0, tireId);
        return query.list();
    }

    /** Internal query to resolve the "artiCasaComercial" to-many relationship of CasaComercial. */
    public List<ArticuloDistrito> _queryCasaComercial_ArtiCasaComercial(Long cacoId) {
        synchronized (this) {
            if (casaComercial_ArtiCasaComercialQuery == null) {
                QueryBuilder<ArticuloDistrito> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.CacoId.eq(null));
                casaComercial_ArtiCasaComercialQuery = queryBuilder.build();
            }
        }
        Query<ArticuloDistrito> query = casaComercial_ArtiCasaComercialQuery.forCurrentThread();
        query.setParameter(0, cacoId);
        return query.list();
    }

}
