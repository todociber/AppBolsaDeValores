package com.todociber.appbolsadevalores.NuevaOrden.WS.ProcesarWS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.Emisores;
import com.todociber.appbolsadevalores.db.EmisoresDao;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Todociber on 21/10/2016.
 */
public class ProcesarGet_Emisores {
    Context context; private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    EmisoresDao emisoresDao;
    JSONObject jsonArrayEmisores;
    private String idTitulo;
    public ProcesarGet_Emisores(Context context, JSONObject arrayEmisores,String idTitulo) throws JSONException {
        this.context = context;
        this.jsonArrayEmisores= arrayEmisores;
        this.idTitulo = idTitulo;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        emisoresDao = daoSession.getEmisoresDao();
        emisoresDao.deleteAll();
        GuardarEmisor(jsonArrayEmisores);
    }

    private void GuardarEmisor(JSONObject jsonEmisor) throws JSONException {
        Emisores emisores = new Emisores();
        emisores.setIdEmisor(jsonEmisor.getString("id"));
        emisores.setNombreEmisor(jsonEmisor.getString("nombreEmisor"));
        emisores.setIdTitulo(idTitulo);
        emisoresDao.insertInTx(emisores);
    }
}
