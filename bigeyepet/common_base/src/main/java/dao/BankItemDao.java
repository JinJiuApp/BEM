package dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table BANK_ITEM.
*/
public class BankItemDao extends AbstractDao<BankItem, Long> {

    public static final String TABLENAME = "BANK_ITEM";

    /**
     * Properties of entity BankItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PayeeName = new Property(1, String.class, "payeeName", false, "PAYEE_NAME");
        public final static Property PayeeCardNo = new Property(2, String.class, "payeeCardNo", false, "PAYEE_CARD_NO");
        public final static Property BankName = new Property(3, String.class, "bankName", false, "BANK_NAME");
        public final static Property UserId = new Property(4, String.class, "userId", false, "USER_ID");
        public final static Property UpdateTime = new Property(5, java.util.Date.class, "updateTime", false, "UPDATE_TIME");
    };


    public BankItemDao(DaoConfig config) {
        super(config);
    }
    
    public BankItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'BANK_ITEM' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'PAYEE_NAME' TEXT," + // 1: payeeName
                "'PAYEE_CARD_NO' TEXT UNIQUE ," + // 2: payeeCardNo
                "'BANK_NAME' TEXT," + // 3: bankName
                "'USER_ID' TEXT," + // 4: userId
                "'UPDATE_TIME' INTEGER);"); // 5: updateTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'BANK_ITEM'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, BankItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String payeeName = entity.getPayeeName();
        if (payeeName != null) {
            stmt.bindString(2, payeeName);
        }
 
        String payeeCardNo = entity.getPayeeCardNo();
        if (payeeCardNo != null) {
            stmt.bindString(3, payeeCardNo);
        }
 
        String bankName = entity.getBankName();
        if (bankName != null) {
            stmt.bindString(4, bankName);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(5, userId);
        }
 
        java.util.Date updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindLong(6, updateTime.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public BankItem readEntity(Cursor cursor, int offset) {
        BankItem entity = new BankItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // payeeName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // payeeCardNo
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // bankName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // userId
            cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)) // updateTime
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, BankItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPayeeName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPayeeCardNo(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBankName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUserId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUpdateTime(cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(BankItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(BankItem entity) {
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
