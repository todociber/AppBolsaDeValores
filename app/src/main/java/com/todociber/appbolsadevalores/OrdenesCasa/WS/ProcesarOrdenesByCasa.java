package com.todociber.appbolsadevalores.OrdenesCasa.WS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.Mensajes;
import com.todociber.appbolsadevalores.db.MensajesDao;
import com.todociber.appbolsadevalores.db.OperacionesBolsa;
import com.todociber.appbolsadevalores.db.OperacionesBolsaDao;
import com.todociber.appbolsadevalores.db.Ordenes;
import com.todociber.appbolsadevalores.db.OrdenesDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Todociber on 18/10/2016.
 */
public class ProcesarOrdenesByCasa {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    public Context context;

    public MensajesDao mensajesDao;
    public OperacionesBolsaDao operacionesBolsaDao;
    public  ProcesarOrdenesByCasa(Context context) {
        this.context = context;
    }

    public void procesarOrden(JSONArray jsonData) throws JSONException {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        OrdenesDao ordenesDao = daoSession.getOrdenesDao();
        mensajesDao = daoSession.getMensajesDao();
        operacionesBolsaDao = daoSession.getOperacionesBolsaDao();
        ordenesDao.deleteAll();
        mensajesDao.deleteAll();
        operacionesBolsaDao.deleteAll();
        for (int i = 0; i<jsonData.length() ;i++){
            Ordenes ordenes = new Ordenes();
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
            ordenesDao.insertInTx(ordenes);
            JSONArray jsonMensajes = ordenV.getJSONArray("mensajes");
            procesarMensajes(idOrden,jsonMensajes);
            JSONArray jsonOperaciones = ordenV.getJSONArray("operaciones");
            procesarOperaciones(idOrden,jsonOperaciones);




        }

    }

    private void procesarMensajes(String idOrden, JSONArray jsonMensajes) throws JSONException {
        for(int i =0; i<jsonMensajes.length();i++){
            Mensajes mensajes = new Mensajes();
            JSONObject jsonObjectMensaje = jsonMensajes.getJSONObject(i);
            mensajes.setIdMensaje(jsonObjectMensaje.getString("id"));
            mensajes.setTipoMensaje(jsonObjectMensaje.getString("id_Tipo"));
            mensajes.setIdUsuario(jsonObjectMensaje.getString("id_Tipo"));
            mensajes.setNombreUsuario(jsonObjectMensaje.getString("nombre_usuario"));
            mensajes.setMensaje(jsonObjectMensaje.getString("mensaje"));
            mensajes.setIdOrden(idOrden);
            mensajesDao.insertInTx(mensajes);

        }


    }

    private void procesarOperaciones(String idOrden, JSONArray jsonOperaciones) throws JSONException {
        for(int i =0; i<jsonOperaciones.length(); i++){
            OperacionesBolsa operacionesBolsa = new OperacionesBolsa();
            JSONObject jsonOperacion = jsonOperaciones.getJSONObject(i);
            operacionesBolsa.setIdOrden(idOrden);
            operacionesBolsa.setIdOperacion(jsonOperacion.getString("id"));
            operacionesBolsa.setMontoOperacion(jsonOperacion.getString("monto"));
            operacionesBolsaDao.insertInTx(operacionesBolsa);
        }
    }

}
