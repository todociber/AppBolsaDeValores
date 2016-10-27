package com.todociber.appbolsadevalores.NuevaOrden.WS.ProcesarWS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.todociber.appbolsadevalores.db.Cedeval;
import com.todociber.appbolsadevalores.db.CedevalDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Todociber on 21/10/2016.
 */
public class ProcesarGet_CuentasCedevales {
    ;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    public String idCliente, TokenSession;
    public    int idUsuario;
    public Context context;
    JSONObject Usuario;
    JSONArray CuentasCedevales;
    public ProcesarGet_CuentasCedevales(JSONArray cuentas,String idCliente,Context context) throws JSONException {
       this.CuentasCedevales = cuentas;
        this.idCliente = idCliente;
        this.context = context;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        CedevalDao cedevalDao = daoSession.getCedevalDao();
        cedevalDao.deleteAll();
        ProcesarCuentasC();
    }


    public void ProcesarCuentasC() throws JSONException {
        for(int i =0; i<CuentasCedevales.length();i++){
            JSONObject cuentaCedeval = CuentasCedevales.getJSONObject(i);
            CedevalDao cedevalDao = daoSession.getCedevalDao();
            Cedeval cedeval = new Cedeval();
            cedeval.setIdCuenta(cuentaCedeval.getString("id"));
            cedeval.setIdCliente(idCliente);
            cedeval.setNumeroDeCuenta(cuentaCedeval.getString("cuenta"));
            cedevalDao.insertInTx(cedeval);
        }
    }

}
