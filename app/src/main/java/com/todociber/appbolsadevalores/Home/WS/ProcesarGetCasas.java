package com.todociber.appbolsadevalores.Home.WS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.todociber.appbolsadevalores.db.CasasCorredoras;
import com.todociber.appbolsadevalores.db.CasasCorredorasDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Todociber on 19/10/2016.
 */
public class ProcesarGetCasas {
    private Context context;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    public ProcesarGetCasas(Context context){
        this.context = context;
    }
    public void procesarCasasNuevas(JSONArray CasasCorredorasData) throws JSONException {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        CasasCorredorasDao casasCorredorasDao = daoSession.getCasasCorredorasDao();
        casasCorredorasDao.deleteAll();


        for (int i =0; i<CasasCorredorasData.length();i++){
            CasasCorredoras casasCorredoras = new CasasCorredoras();
            JSONObject CasaCorredora=  CasasCorredorasData.getJSONObject(i);
            casasCorredoras.setIdCasa(CasaCorredora.getString("id"));
            casasCorredoras.setNombreCasa(CasaCorredora.getString("nombre"));
            casasCorredorasDao.insertInTx(casasCorredoras);

        }
    }
}
