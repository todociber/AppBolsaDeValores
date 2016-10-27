package com.todociber.appbolsadevalores.login.WS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.todociber.appbolsadevalores.db.CasasCorredoras;
import com.todociber.appbolsadevalores.db.CasasCorredorasDao;
import com.todociber.appbolsadevalores.db.Cedeval;
import com.todociber.appbolsadevalores.db.CedevalDao;
import com.todociber.appbolsadevalores.db.Cliente;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Todociber on 16/10/2016.
 */
public class ProcesarJsonLogin {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
        public String idCliente, TokenSession;
     public    int idUsuario;
    public  Context context;
    JSONObject Usuario;
    JSONArray CasasCorredorasData,CuentasCedevales;
    public ProcesarJsonLogin(JSONObject data, Context context) throws JSONException{

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        ClienteDao clienteDao = daoSession.getClienteDao();
        clienteDao.deleteAll();
        CasasCorredorasDao casasCorredorasDao = daoSession.getCasasCorredorasDao();
        casasCorredorasDao.deleteAll();
        CedevalDao cedevalDao = daoSession.getCedevalDao();
        cedevalDao.deleteAll();
        this.context = context;

            this.Usuario = data.getJSONObject("usuario");
            this.CasasCorredorasData = data.getJSONArray("casas");
            this.TokenSession = data.getString("token");

        ProcesarUsuario(Usuario);
        ProcesarCasasAfiliadas();

        db.close();
    }
   public void ProcesarUsuario(JSONObject Usuario) throws JSONException{
            JSONObject dataUsuario = Usuario;

           JSONObject dataCliente = Usuario.getJSONObject("cliente_n");

           ClienteDao clienteDao = daoSession.getClienteDao();
           Cliente cliente = new Cliente();
           idUsuario = Integer.valueOf(Usuario.getString("id"));
           idCliente = dataCliente.getString("id");
           cliente.setIdUsuario(idUsuario);
           cliente.setNombre(Usuario.getString("nombre"));
           cliente.setApellido(Usuario.getString("apellido"));
           cliente.setEmail(Usuario.getString("email"));
           cliente.setDUI(dataCliente.getString("dui"));
           cliente.setNIT(dataCliente.getString("nit"));
           cliente.setFechaDeNacimiento(dataCliente.getString("fechaDeNacimiento"));
           cliente.setIdCliente(idCliente);
           cliente.setToken(TokenSession);

           clienteDao.insertInTx(cliente);
           CuentasCedevales = dataCliente.getJSONArray("cuenta_cedeval");
           ProcesarCuentasC();

   }

   public void ProcesarCasasAfiliadas()throws JSONException{
       for (int i =0; i<CasasCorredorasData.length();i++){

               CasasCorredorasDao casasCorredorasDao = daoSession.getCasasCorredorasDao();
               CasasCorredoras casasCorredoras = new CasasCorredoras();
               JSONObject CasaCorredora=  CasasCorredorasData.getJSONObject(i);
               casasCorredoras.setIdCasa(CasaCorredora.getString("id"));
               casasCorredoras.setNombreCasa(CasaCorredora.getString("nombre"));

               casasCorredorasDao.insertInTx(casasCorredoras);

       }
   }
    public void ProcesarCuentasC() throws JSONException {
        for(int i =0; i<CuentasCedevales.length();i++){
            JSONObject cuentaCedeval = CuentasCedevales.getJSONObject(i);
            CedevalDao cedevalDao = daoSession.getCedevalDao();
            Cedeval cedeval = new Cedeval();
            cedeval.setIdCliente(idCliente);
            cedeval.setNumeroDeCuenta(cuentaCedeval.getString("cuenta"));
            cedevalDao.insertInTx(cedeval);
        }
    }
}
