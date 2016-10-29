package com.todociber.appbolsadevalores.Afiliaciones.WS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.todociber.appbolsadevalores.db.Afiliaciones;
import com.todociber.appbolsadevalores.db.AfiliacionesDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Todociber on 28/10/2016.
 */
public class ProcesarAfiliaciones {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Context context;
    private JSONArray jsonArrayAfiliaciones;
    private AfiliacionesDao afiliacionesDao;


    public ProcesarAfiliaciones(Context context, JSONArray jsonArrayAfiliaciones) throws JSONException {
        this.context = context;
        this.jsonArrayAfiliaciones = jsonArrayAfiliaciones;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        afiliacionesDao = daoSession.getAfiliacionesDao();
        afiliacionesDao.deleteAll();
        for (int i=0; i<jsonArrayAfiliaciones.length();i++){
            GuardarAfiliacion(jsonArrayAfiliaciones.getJSONObject(i));
        }

    }

    private void GuardarAfiliacion(JSONObject afiliacion)throws JSONException {
        Afiliaciones afiliaciones= new Afiliaciones();
        afiliaciones.setNumeroAfiliacion(afiliacion.getString("numeroAfiliacion"));
        afiliaciones.setNombreCasa(afiliacion.getString("nombreCasa"));
        afiliaciones.setEstadoAfiliacion(afiliacion.getString("estadoAfiliacion"));
        afiliacionesDao.insertInTx(afiliaciones);
    }
}