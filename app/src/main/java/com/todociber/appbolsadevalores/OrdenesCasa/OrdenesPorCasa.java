package com.todociber.appbolsadevalores.OrdenesCasa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.todociber.appbolsadevalores.OrdenesCasa.Adapter.AdapterOrdenes;
import com.todociber.appbolsadevalores.OrdenesCasa.WS.GetOrdenesByCasa;
import com.todociber.appbolsadevalores.R;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.OrdenesDao;
import com.todociber.appbolsadevalores.db.TokenPushDao;

public class OrdenesPorCasa extends AppCompatActivity {
    public String idCasa, posicionCasa;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private TokenPushDao tokenPushDao;
    private ClienteDao clienteDao;
    private OrdenesDao ordenesDao;
    public Context context;
    public ListView listadoOrdenes;
    public ProgressDialog loading;
    public Cursor cursorCliente, cursorOrdenes;
    public String idCliente, tokenSession;
    public GetOrdenesByCasa getOrdenesByCasa;
    public AdapterOrdenes adapterOrdenes;
    private SwipeRefreshLayout swipeContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes_por_casa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        listadoOrdenes =(ListView)findViewById(R.id.ListadoOrdenesCasa);


        context = OrdenesPorCasa.this;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        tokenPushDao = daoSession.getTokenPushDao();
        clienteDao = daoSession.getClienteDao();

        ordenesDao = daoSession.getOrdenesDao();
        cursorCliente = db.query(clienteDao.getTablename(),clienteDao.getAllColumns(),null,null,null,null,null);



        Bundle extras = OrdenesPorCasa.this.getIntent().getExtras();
        idCasa = extras.getString("idCasa");


        if(cursorCliente.moveToFirst()){
            idCliente = cursorCliente.getString(5);
            tokenSession = cursorCliente.getString(9);
            new getOrdenes(0).execute();
        }

        listadoOrdenes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent a = new Intent(context, TabOrdenDetalle.class);
                a.putExtra("posicionCursor",position);
                a.putExtra("idCasa",idCasa);
                finish();
                startActivity(a);

            }
        });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                new getOrdenes(1).execute();

            }
        });


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
        public getOrdenes(int a) {
            this.a=a;
        }

        @Override
        protected void onPreExecute() {
            if(a==0){
                try {
                    loading = new ProgressDialog(context);
                    loading.setMessage("Cargando");
                    loading.setCancelable(false);
                    loading.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            getOrdenesByCasa = new GetOrdenesByCasa(context,tokenSession,idCliente,idCasa);
            ErrorCode = getOrdenesByCasa.erroCode;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            if(a==0){
                loading.dismiss();
            }else{
                swipeContainer.setRefreshing(false);
            }
            if(ErrorCode==0){
                cursorOrdenes = db.query(ordenesDao.getTablename(),ordenesDao.getAllColumns(),null,null,null,null,null);
                if(cursorOrdenes.moveToFirst()){
                    adapterOrdenes = new AdapterOrdenes(context,cursorOrdenes);
                    listadoOrdenes.setAdapter(adapterOrdenes);
                }else{
                    new AlertDialog.Builder(context)
                            .setTitle("Error")
                            .setMessage("No se encontraron ordenes para esta casa corredora")
                            .setCancelable(false)
                            .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                }
            }else  {
                new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("No se encontraron ordenes para esta casa corredora")
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
