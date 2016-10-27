package com.todociber.appbolsadevalores.OrdenesCasa.WS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.MensajesDao;
import com.todociber.appbolsadevalores.db.OperacionesBolsaDao;
import com.todociber.appbolsadevalores.db.OrdenesPadre;
import com.todociber.appbolsadevalores.db.OrdenesPadreDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Todociber on 23/10/2016.
 */
public class ProcesarOrdenesPadre {

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    public Context context;

    public MensajesDao mensajesDao;
    public OperacionesBolsaDao operacionesBolsaDao;
    public  ProcesarOrdenesPadre(Context context,JSONArray jsonArrayOrdenesPadre) throws JSONException {
        this.context = context;
        procesarOrden(jsonArrayOrdenesPadre);
    }

    public void procesarOrden(JSONArray jsonData) throws JSONException {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        OrdenesPadreDao ordenesPadreDao = daoSession.getOrdenesPadreDao();
        ordenesPadreDao.deleteAll();

        for (int i = 0; i<jsonData.length() ;i++){
                OrdenesPadre ordenes = new OrdenesPadre();
                JSONObject ordenV = jsonData.getJSONObject(i);
                String idOrden = ordenV.getString("id");
                ordenes.setIdOrden(idOrden);
                ordenes.setCorrelativo(ordenV.getString("correlativo"));
                ordenes.setFechaDeVigencia(ordenV.getString("FechaDeVigencia"));
                if(ordenV.getString("agente_corredor").equals("null")){
                    ordenes.setAgenteCorredor("Pendiente de Asignacion");
                }else{
                    ordenes.setAgenteCorredor(ordenV.getString("agente_corredor"));
                }
                ordenes.setTipoOrden(ordenV.getString("Tipo_orden"));
                ordenes.setIdOrdenPadre(ordenV.getString("idOrden"));
                ordenes.setEstadoOrden(ordenV.getString("estado_orden"));
                ordenes.setTitulo(ordenV.getString("titulo"));
                ordenes.setTipoEjecucion(ordenV.getString("tipo_ejecucion"));
                ordenes.setValorMinimo(ordenV.getString("valor_minimo"));
                ordenes.setValorMaximo(ordenV.getString("valor_maximo"));
                ordenes.setNombreCasaCorredora(ordenV.getString("casa_corredora"));
                ordenes.setFechaCreacion(ordenV.getString("fecha_creacion"));
                ordenes.setMonto(ordenV.getString("monto"));
                ordenes.setTasaDeInterez(ordenV.getString("tasa_interes"));
                if(ordenV.getString("comision").equals("null")){
                    ordenes.setComision("Pendiente");
                }else{
                    ordenes.setComision(ordenV.getString("comision"));
                }

                ordenes.setCuentasCedeval(ordenV.getString("cuenta_cedeval"));
                ordenes.setEmisorNombre(ordenV.getString("emisor"));
                ordenes.setTipoMercado(ordenV.getString("tipo_mercado"));
                ordenesPadreDao.insertInTx(ordenes);
            }








    }

}
