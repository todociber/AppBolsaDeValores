package com.todociber.appbolsadevalores.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Todociber on 16/10/2016.
 */


public class IniciarBD {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private TokenPushDao tokenPushDao;


    public DaoSession Iniciar(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
       return daoSession;
    }
}
