package com.todociber.appbolsadevalores.OrdenesCasa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.todociber.appbolsadevalores.OrdenesCasa.Adapter.adapterOrdenesPadre;
import com.todociber.appbolsadevalores.OrdenesCasa.WS.GetOrdenesPadre;
import com.todociber.appbolsadevalores.R;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.OrdenesPadreDao;
import com.todociber.appbolsadevalores.db.TokenPushDao;

public class HistorialOrden extends AppCompatActivity {
    public ProgressDialog loading;
    private Context context;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private TokenPushDao tokenPushDao;
    private ClienteDao clienteDao;
    private Cursor cursorCliente, cursorOrdenesPadre;
    private OrdenesPadreDao ordenesPadreDao;
    String idOrden;
    adapterOrdenesPadre AdapterOrdenesPadre;
    private ListView listadoOrdenesPadre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_orden);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        context = HistorialOrden.this;
        Bundle extras = HistorialOrden.this.getIntent().getExtras();
        idOrden = extras.getString("idOrden");
        listadoOrdenesPadre = (ListView) findViewById(R.id.listadoOrdenesPadre);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        tokenPushDao = daoSession.getTokenPushDao();
        clienteDao = daoSession.getClienteDao();
        ordenesPadreDao = daoSession.getOrdenesPadreDao();
        cursorCliente = db.query(clienteDao.getTablename(),clienteDao.getAllColumns(),null,null,null,null,null);
        if(cursorCliente.moveToFirst()){
            new getOrdenes().execute();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class getOrdenes extends AsyncTask<Void, Void, Void> {
        int ErrorCode;
        int a ;
        public getOrdenes() {

        }

        @Override
        protected void onPreExecute() {

                try {
                    loading = new ProgressDialog(context);
                    loading.setMessage("Cargando");
                    loading.setCancelable(false);
                    loading.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }



        }

        @Override
        protected Void doInBackground(Void... arg0) {
            GetOrdenesPadre getOrdenesPadre = new GetOrdenesPadre(context,cursorCliente.getString(9),cursorCliente.getString(5),idOrden);
            ErrorCode = getOrdenesPadre.result;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {



                loading.dismiss();

            if(ErrorCode==0){
                cursorOrdenesPadre = db.query(ordenesPadreDao.getTablename(),ordenesPadreDao.getAllColumns(),null,null,null,null,"ID_ORDEN DESC");

                if(cursorOrdenesPadre.moveToFirst()){
                   AdapterOrdenesPadre = new adapterOrdenesPadre(context,cursorOrdenesPadre);
                    listadoOrdenesPadre.setAdapter(AdapterOrdenesPadre);
                }


            }else  {
                new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("No se encontro Historial disponible")
                        .setCancelable(false)
                        .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }


        }

    }
}
