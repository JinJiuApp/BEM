package dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table business_list.
*/
public class BusinessItemDao extends AbstractDao<BusinessItem, Long> {

    public static final String TABLENAME = "business_list";

    /**
     * Properties of entity BusinessItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property BusinessId = new Property(1, Integer.class, "businessId", false, "BUSINESS_ID");
        public final static Property BusinessName = new Property(2, String.class, "businessName", false, "BUSINESS_NAME");
        public final static Property CityId = new Property(3, Long.class, "cityId", false, "CITY_ID");
    };

    private DaoSession daoSession;

    private Query<BusinessItem> cityItem_BusinessItemListQuery;

    public BusinessItemDao(DaoConfig config) {
        super(config);
    }
    
    public BusinessItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'business_list' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'BUSINESS_ID' INTEGER," + // 1: businessId
                "'BUSINESS_NAME' TEXT," + // 2: businessName
                "'CITY_ID' INTEGER);"); // 3: cityId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'business_list'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, BusinessItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer businessId = entity.getBusinessId();
        if (businessId != null) {
            stmt.bindLong(2, businessId);
        }
 
        String businessName = entity.getBusinessName();
        if (businessName != null) {
            stmt.bindString(3, businessName);
        }
 
        Long cityId = entity.getCityId();
        if (cityId != null) {
            stmt.bindLong(4, cityId);
        }
    }

    @Override
    protected void attachEntity(BusinessItem entity) {
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
    public BusinessItem readEntity(Cursor cursor, int offset) {
        BusinessItem entity = new BusinessItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // businessId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // businessName
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // cityId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, BusinessItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBusinessId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setBusinessName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCityId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(BusinessItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(BusinessItem entity) {
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
    
    /** Internal query to resolve the "businessItemList" to-many relationship of CityItem. */
    public List<BusinessItem> _queryCityItem_BusinessItemList(Long cityId) {
        synchronized (this) {
            if (cityItem_BusinessItemListQuery == null) {
                QueryBuilder<BusinessItem> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.CityId.eq(null));
                cityItem_BusinessItemListQuery = queryBuilder.build();
            }
        }
        Query<BusinessItem> query = cityItem_BusinessItemListQuery.forCurrentThread();
        query.setParameter(0, cityId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getCityItemDao().getAllColumns());
            builder.append(" FROM business_list T");
            builder.append(" LEFT JOIN city_list T0 ON T.'CITY_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected BusinessItem loadCurrentDeep(Cursor cursor, boolean lock) {
        BusinessItem entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        CityItem cityItem = loadCurrentOther(daoSession.getCityItemDao(), cursor, offset);
        entity.setCityItem(cityItem);

        return entity;    
    }

    public BusinessItem loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<BusinessItem> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<BusinessItem> list = new ArrayList<BusinessItem>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<BusinessItem> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<BusinessItem> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}