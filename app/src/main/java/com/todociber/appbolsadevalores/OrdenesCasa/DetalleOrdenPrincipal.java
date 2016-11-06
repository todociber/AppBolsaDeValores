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
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.todociber.appbolsadevalores.OrdenesCasa.WS.PutCancelarOrden;
import com.todociber.appbolsadevalores.OrdenesCasa.WS.PutEjecutarOrden;
import com.todociber.appbolsadevalores.R;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.OrdenesDao;
import com.todociber.appbolsadevalores.db.TokenPushDao;

public class DetalleOrdenPrincipal extends AppCompatActivity {
    public ProgressDialog loading;
    public Cursor cursorDetalleOrden, CursorCliente;
    int posicionCursorCasa;
    String tipoOrden ="", estadoOrden= "";
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private TokenPushDao tokenPushDao;
    private ClienteDao clienteDao;
    private OrdenesDao ordenesDao;
    private Context context;
    private String idOrden,motivo;
    private TextView NombreCasaCorredora,txtCorrelativo,txtFechaDeVigencia,
            txtAgenteCorredor,txtTipoDeOrden,txtTituloNombre,txtValorMinimo,
            txtValorMaximo,txtMontoDeInversion,txtTasaDeInversion,txtComision,
            txtCuentaCedeval,txtEmisor,txtTipoMercado,txtEstadoOrden;
    private LinearLayout bannerBotones;
    private RelativeLayout BannerHistorial;
    private CardView btnEjecutar,btnModificar, btnCancelar, btnHistorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_orden_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Declaracion de texview
        NombreCasaCorredora = (TextView) findViewById(R.id.NombreCasaCorredora);
        txtCorrelativo = (TextView) findViewById(R.id.txtCorrelativo);
        txtFechaDeVigencia = (TextView) findViewById(R.id.txtFechaDeVigencia);
        txtAgenteCorredor = (TextView) findViewById(R.id.txtAgenteCorredor);
        txtTipoDeOrden = (TextView) findViewById(R.id.txtTipoDeOrden);
        txtTituloNombre = (TextView) findViewById(R.id.txtTituloNombre);
        txtValorMinimo = (TextView) findViewById(R.id.txtValorMinimo);
        txtValorMaximo = (TextView) findViewById(R.id.txtValorMaximo);
        txtMontoDeInversion = (TextView) findViewById(R.id.txtMontoDeInversion);
        txtTasaDeInversion = (TextView) findViewById(R.id.txtTasaDeInversion);
        txtComision = (TextView) findViewById(R.id.txtComision);
        txtCuentaCedeval = (TextView) findViewById(R.id.txtCuentaCedeval);
        txtEmisor = (TextView) findViewById(R.id.txtEmisor);
        txtTipoMercado = (TextView) findViewById(R.id.txtTipoMercado);
        txtEstadoOrden = (TextView) findViewById(R.id.txtEstadoOrden);
        btnEjecutar = (CardView) findViewById(R.id.btnEjecutarOrden);
        btnModificar = (CardView) findViewById(R.id.btnModificarOrden);
        btnCancelar = (CardView) findViewById(R.id.btnCancelarOrden);
        btnHistorial =(CardView) findViewById(R.id.btnHistorial);
        bannerBotones = (LinearLayout) findViewById(R.id.bannerBotones);
        BannerHistorial =(RelativeLayout) findViewById(R.id.BannerHistorial);
        //Recibir data de activity anterior
        Bundle extras = DetalleOrdenPrincipal.this.getIntent().getExtras();
        posicionCursorCasa = extras.getInt("posicionCursor");

        context = DetalleOrdenPrincipal.this;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        ordenesDao = daoSession.getOrdenesDao();
        clienteDao = daoSession.getClienteDao();
        cursorDetalleOrden = db.query(ordenesDao.getTablename(),ordenesDao.getAllColumns(),null,null,null,null,null);
        if(cursorDetalleOrden.moveToPosition(posicionCursorCasa)){
            NombreCasaCorredora.setText(cursorDetalleOrden.getString(12));
            txtCorrelativo.setText(cursorDetalleOrden.getString(2));
            txtFechaDeVigencia.setText(cursorDetalleOrden.getString(3));
            txtAgenteCorredor.setText(cursorDetalleOrden.getString(4));
            if(cursorDetalleOrden.getString(5).equals("1")){
                tipoOrden = "Compra";
            }else if(cursorDetalleOrden.getString(5).equals("2")){
                tipoOrden = "Venta";
            }
            txtTipoDeOrden.setText(tipoOrden);
            txtTituloNombre.setText(cursorDetalleOrden.getString(8));
            txtValorMinimo.setText(cursorDetalleOrden.getString(10));
            txtValorMaximo.setText(cursorDetalleOrden.getString(11));
            txtMontoDeInversion.setText(cursorDetalleOrden.getString(15));
            txtTasaDeInversion.setText(cursorDetalleOrden.getString(16));
            txtComision.setText(cursorDetalleOrden.getString(17));
            txtCuentaCedeval.setText(cursorDetalleOrden.getString(18));
            txtEmisor.setText(cursorDetalleOrden.getString(19));
            txtTipoMercado.setText(cursorDetalleOrden.getString(20));
            txtEstadoOrden.setText(setearEstadoOrden(cursorDetalleOrden.getString(7)));

            idOrden = cursorDetalleOrden.getString(1);

            if(cursorDetalleOrden.getString(7).equals("1") ||
                    cursorDetalleOrden.getString(7).equals("3")||
                    cursorDetalleOrden.getString(7).equals("4")||
                    cursorDetalleOrden.getString(7).equals("6")||
                    cursorDetalleOrden.getString(7).equals("7")||
                    cursorDetalleOrden.getString(7).equals("8")){
                bannerBotones.setVisibility(View.GONE);
            }

            if(cursorDetalleOrden.getString(6).equals("null")){
                BannerHistorial.setVisibility(View.GONE);
            }



        }else{
            new AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage("Error en obtencion de datos")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).create().show();
        }

        CursorCliente = db.query(clienteDao.getTablename(),clienteDao.getAllColumns(),null,null,null,null,null);

        btnEjecutar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CursorCliente.moveToFirst()){
                    new putEjecutarTask().execute();
                }
            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(context);
                input.setHint("motivo de rechazo");
                new AlertDialog.Builder(context)
                        .setView(input)
                        .setTitle("Rechazar")
                        .setMessage("Ingresa el motivo de rechazo para la Orden")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if(CursorCliente.moveToFirst()){
                                    motivo = input.getText().toString();
                                    new putCancelar().execute();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).create().show();


            }
        });

    }

    public String setearEstadoOrden(String estadoOrdenR){
        if(estadoOrdenR.equals("1")){
            estadoOrden = "Pre-Vigente";
        }else if(estadoOrdenR.equals("2")){
            estadoOrden = "Vigente";
        }else if(estadoOrdenR.equals("3")){
            estadoOrden = "Cancelada";
        }else if(estadoOrdenR.equals("4")){
            estadoOrden = "Modificada";
        }else if(estadoOrdenR.equals("5")){
            estadoOrden = "Ejecutada";
        }else if(estadoOrdenR.equals("6")){
            estadoOrden = "Finalizada";
        }else if(estadoOrdenR.equals("7")){
            estadoOrden = "Vencida";
        }else if(estadoOrdenR.equals("8")){
            estadoOrden = "Rechazada";
        }
        return estadoOrden;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class putEjecutarTask extends AsyncTask<Void, Void, Void> {
        int ErrorCode=1;

        public putEjecutarTask() {

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
            PutEjecutarOrden putEjecutarOrden = new PutEjecutarOrden(CursorCliente.getString(9),context,idOrden,CursorCliente.getString(2),CursorCliente.getString(3),CursorCliente.getString(5));
            putEjecutarOrden.ejecutarOrden();
             ErrorCode = putEjecutarOrden.error;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {



                loading.dismiss();

            if(ErrorCode==0){
                new AlertDialog.Builder(context)
                        .setTitle("Exito")
                        .setMessage("Orden Ejecutada con exito")
                        .setCancelable(false)
                        .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();


            }else  {
                new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("No se pudo Ejecutar la orden")
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


    private class putCancelar extends AsyncTask<Void, Void, Void> {
        int ErrorCode=1;

        public putCancelar() {

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
            PutCancelarOrden putCancelarOrden = new PutCancelarOrden(CursorCliente.getString(9),context,idOrden,CursorCliente.getString(2),CursorCliente.getString(3),CursorCliente.getString(5),motivo,CursorCliente.getString(1));
            putCancelarOrden.EjecutarCancelacion();
            ErrorCode = putCancelarOrden.error;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {



                loading.dismiss();

            if(ErrorCode==0){

                new AlertDialog.Builder(context)
                        .setTitle("Exito")
                        .setMessage("Orden Cancelada exitosamente")
                        .setCancelable(false)
                        .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        }).create().show();
            }else  {
                new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("Error al cancelar orden")
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
