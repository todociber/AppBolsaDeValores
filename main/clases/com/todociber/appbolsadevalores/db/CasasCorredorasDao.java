package com.todociber.appbolsadevalores.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.todociber.appbolsadevalores.db.CasasCorredoras;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table CASAS_CORREDORAS.
*/
public class CasasCorredorasDao extends AbstractDao<CasasCorredoras, Long> {

    public static final String TABLENAME = "CASAS_CORREDORAS";

    /**
     * Properties of entity CasasCorredoras.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IdCasa = new Property(1, String.class, "idCasa", false, "ID_CASA");
        public final static Property NombreCasa = new Property(2, String.class, "NombreCasa", false, "NOMBRE_CASA");
    };


    public CasasCorredorasDao(DaoConfig config) {
        super(config);
    }
    
    public CasasCorredorasDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'CASAS_CORREDORAS' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ID_CASA' TEXT," + // 1: idCasa
                "'NOMBRE_CASA' TEXT);"); // 2: NombreCasa
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'CASAS_CORREDORAS'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CasasCorredoras entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String idCasa = entity.getIdCasa();
        if (idCasa != null) {
            stmt.bindString(2, idCasa);
        }
 
        String NombreCasa = entity.getNombreCasa();
        if (NombreCasa != null) {
            stmt.bindString(3, NombreCasa);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public CasasCorredoras readEntity(Cursor cursor, int offset) {
        CasasCorredoras entity = new CasasCorredoras( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // idCasa
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // NombreCasa
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CasasCorredoras entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdCasa(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNombreCasa(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(CasasCorredoras entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(CasasCorredoras entity) {
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