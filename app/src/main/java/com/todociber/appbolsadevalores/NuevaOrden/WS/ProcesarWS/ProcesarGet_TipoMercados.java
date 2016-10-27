package com.todociber.appbolsadevalores.NuevaOrden.WS.ProcesarWS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.TipoMercado;
import com.todociber.appbolsadevalores.db.TipoMercadoDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Todociber on 21/10/2016.
 */
public class ProcesarGet_TipoMercados {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Context context;
    private JSONArray jsonArrayTipoMercados;
    private TipoMercadoDao tipoMercadoDao;

    public ProcesarGet_TipoMercados(Context context, JSONArray jsonArrayTipoMercados) throws JSONException {
        this.context = context;
        this.jsonArrayTipoMercados = jsonArrayTipoMercados;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        tipoMercadoDao = daoSession.getTipoMercadoDao();
        tipoMercadoDao.deleteAll();
        for (int i=0; i<jsonArrayTipoMercados.length();i++){
            GuardarTipoMercado(jsonArrayTipoMercados.getJSONObject(i));
        }

    }

    private void GuardarTipoMercado(JSONObject tipoMercadoJson)throws JSONException {
        TipoMercado tipoMercado = new TipoMercado();
        tipoMercado.setIdTipoMercado(tipoMercadoJson.getString("id"));
        tipoMercado.setNombreMercado(tipoMercadoJson.getString("nombreMercado"));
        tipoMercadoDao.insertInTx(tipoMercado);
    }
}
