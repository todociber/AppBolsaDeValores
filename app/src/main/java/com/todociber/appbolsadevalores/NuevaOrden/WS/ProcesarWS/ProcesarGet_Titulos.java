package com.todociber.appbolsadevalores.NuevaOrden.WS.ProcesarWS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.Titulos;
import com.todociber.appbolsadevalores.db.TitulosDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Todociber on 21/10/2016.
 */
public class ProcesarGet_Titulos {
    private SQLiteDatabase db;
    private TitulosDao titulosDao;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Context context;
    private JSONArray jsonArraytitulos;
    private JSONObject jsonObjectTitulo;
    public ProcesarGet_Titulos(JSONArray jsonArrayTitulos, Context context) throws JSONException {
        this.context = context;
        this.jsonArraytitulos = jsonArrayTitulos;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        titulosDao = daoSession.getTitulosDao();
        titulosDao.deleteAll();
        for (int i=0; i<jsonArraytitulos.length();i++){
            jsonObjectTitulo = jsonArraytitulos.getJSONObject(i);
            GuardarTitulo(jsonObjectTitulo);

        }
    }

    private void GuardarTitulo(JSONObject jsonTitulo) throws JSONException {
        Titulos titulos = new Titulos();
        titulos.setIdTitulo(jsonTitulo.getString("id"));
        titulos.setNombreTitulo(jsonTitulo.getString("nombreTitulo"));
        titulos.setTasaDeInteres(jsonTitulo.getString("tasaDeInteres"));
        titulos.setIdEmisor(jsonTitulo.getString("idEmisor"));
        titulos.setIdTipoTitulo(jsonTitulo.getString("idTipoTitulo"));
        titulosDao.insertInTx(titulos);

    }
}
