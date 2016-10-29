package com.todociber.appbolsadevalores.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.todociber.appbolsadevalores.db.Afiliaciones;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table AFILIACIONES.
*/
public class AfiliacionesDao extends AbstractDao<Afiliaciones, Long> {

    public static final String TABLENAME = "AFILIACIONES";

        public AfiliacionesDao(DaoConfig config) {
        super(config);
    };


    public AfiliacionesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }
    
    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'AFILIACIONES' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NUMERO_AFILIACION' TEXT," + // 1: numeroAfiliacion
                "'NOMBRE_CASA' TEXT," + // 2: nombreCasa
                "'ESTADO_AFILIACION' TEXT);"); // 3: estadoAfiliacion
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'AFILIACIONES'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Afiliaciones entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String numeroAfiliacion = entity.getNumeroAfiliacion();
        if (numeroAfiliacion != null) {
            stmt.bindString(2, numeroAfiliacion);
        }

        String nombreCasa = entity.getNombreCasa();
        if (nombreCasa != null) {
            stmt.bindString(3, nombreCasa);
        }

        String estadoAfiliacion = entity.getEstadoAfiliacion();
        if (estadoAfiliacion != null) {
            stmt.bindString(4, estadoAfiliacion);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public Afiliaciones readEntity(Cursor cursor, int offset) {
        Afiliaciones entity = new Afiliaciones( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // numeroAfiliacion
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // nombreCasa
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // estadoAfiliacion
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Afiliaciones entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNumeroAfiliacion(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNombreCasa(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEstadoAfiliacion(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
     
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Afiliaciones entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Afiliaciones entity) {
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

/**
     * Properties of entity Afiliaciones.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property NumeroAfiliacion = new Property(1, String.class, "numeroAfiliacion", false, "NUMERO_AFILIACION");
        public final static Property NombreCasa = new Property(2, String.class, "nombreCasa", false, "NOMBRE_CASA");
        public final static Property EstadoAfiliacion = new Property(3, String.class, "estadoAfiliacion", false, "ESTADO_AFILIACION");
    }
    
}
