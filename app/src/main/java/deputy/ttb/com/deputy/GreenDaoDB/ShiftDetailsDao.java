package deputy.ttb.com.deputy.GreenDaoDB;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SHIFT_DETAILS".
*/
public class ShiftDetailsDao extends AbstractDao<ShiftDetails, Long> {

    public static final String TABLENAME = "SHIFT_DETAILS";

    /**
     * Properties of entity ShiftDetails.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ShiftDetails = new Property(1, String.class, "shiftDetails", false, "SHIFT_DETAILS");
    }


    public ShiftDetailsDao(DaoConfig config) {
        super(config);
    }
    
    public ShiftDetailsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SHIFT_DETAILS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"SHIFT_DETAILS\" TEXT NOT NULL UNIQUE );"); // 1: shiftDetails
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SHIFT_DETAILS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ShiftDetails entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getShiftDetails());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ShiftDetails entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getShiftDetails());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ShiftDetails readEntity(Cursor cursor, int offset) {
        ShiftDetails entity = new ShiftDetails( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1) // shiftDetails
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ShiftDetails entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setShiftDetails(cursor.getString(offset + 1));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ShiftDetails entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ShiftDetails entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ShiftDetails entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}