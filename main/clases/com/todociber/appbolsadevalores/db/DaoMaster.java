package com.todociber.appbolsadevalores.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import com.todociber.appbolsadevalores.db.TokenPushDao;
import com.todociber.appbolsadevalores.db.EmisoresDao;
import com.todociber.appbolsadevalores.db.TipoMercadoDao;
import com.todociber.appbolsadevalores.db.TitulosDao;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.CedevalDao;
import com.todociber.appbolsadevalores.db.CasasCorredorasDao;
import com.todociber.appbolsadevalores.db.OrdenesDao;
import com.todociber.appbolsadevalores.db.OrdenesPadreDao;
import com.todociber.appbolsadevalores.db.MensajesDao;
import com.todociber.appbolsadevalores.db.OperacionesBolsaDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 2): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 2;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        TokenPushDao.createTable(db, ifNotExists);
        EmisoresDao.createTable(db, ifNotExists);
        TipoMercadoDao.createTable(db, ifNotExists);
        TitulosDao.createTable(db, ifNotExists);
        ClienteDao.createTable(db, ifNotExists);
        CedevalDao.createTable(db, ifNotExists);
        CasasCorredorasDao.createTable(db, ifNotExists);
        OrdenesDao.createTable(db, ifNotExists);
        OrdenesPadreDao.createTable(db, ifNotExists);
        MensajesDao.createTable(db, ifNotExists);
        OperacionesBolsaDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        TokenPushDao.dropTable(db, ifExists);
        EmisoresDao.dropTable(db, ifExists);
        TipoMercadoDao.dropTable(db, ifExists);
        TitulosDao.dropTable(db, ifExists);
        ClienteDao.dropTable(db, ifExists);
        CedevalDao.dropTable(db, ifExists);
        CasasCorredorasDao.dropTable(db, ifExists);
        OrdenesDao.dropTable(db, ifExists);
        OrdenesPadreDao.dropTable(db, ifExists);
        MensajesDao.dropTable(db, ifExists);
        OperacionesBolsaDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(TokenPushDao.class);
        registerDaoClass(EmisoresDao.class);
        registerDaoClass(TipoMercadoDao.class);
        registerDaoClass(TitulosDao.class);
        registerDaoClass(ClienteDao.class);
        registerDaoClass(CedevalDao.class);
        registerDaoClass(CasasCorredorasDao.class);
        registerDaoClass(OrdenesDao.class);
        registerDaoClass(OrdenesPadreDao.class);
        registerDaoClass(MensajesDao.class);
        registerDaoClass(OperacionesBolsaDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}