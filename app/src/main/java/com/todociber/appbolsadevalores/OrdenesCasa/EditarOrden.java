package com.todociber.appbolsadevalores.OrdenesCasa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.todociber.appbolsadevalores.Home.WS.GETCasasCorredoras;
import com.todociber.appbolsadevalores.NuevaOrden.WS.GET_TitulosBolsaDeValores;
import com.todociber.appbolsadevalores.NuevaOrden.WS.Get_CuentasCedevales;
import com.todociber.appbolsadevalores.NuevaOrden.WS.Get_TipoMercadosBolsaDeValores;
import com.todociber.appbolsadevalores.NuevaOrden.adapter.adapterSpinnerCedeval;
import com.todociber.appbolsadevalores.NuevaOrden.adapter.adapterSpinnerMercado;
import com.todociber.appbolsadevalores.NuevaOrden.adapter.adapterSpinnerTitulo;
import com.todociber.appbolsadevalores.R;
import com.todociber.appbolsadevalores.db.CasasCorredorasDao;
import com.todociber.appbolsadevalores.db.CedevalDao;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.OrdenesDao;
import com.todociber.appbolsadevalores.db.TipoMercadoDao;
import com.todociber.appbolsadevalores.db.TitulosDao;
import com.todociber.appbolsadevalores.db.TokenPushDao;

public class EditarOrden extends AppCompatActivity {
    private Context context;
    public ProgressDialog loading;
    private Spinner spinnerCedeval, spinnerMercado,spinnerTitulo;
    private RadioGroup tipoOrden;
    private RadioButton tipoVenta,tipoCompra;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private TokenPushDao tokenPushDao;
    private OrdenesDao ordenesDao;
    private ClienteDao clienteDao;
    private CardView btnContinuar;
    private CasasCorredorasDao casasCorredorasDao;
    private CedevalDao cedevalDao;
    private TipoMercadoDao tipoMercadoDao;
    private String idOrden;
    private TitulosDao titulosDao;
    private RelativeLayout content_frame;
    private Cursor cursorCliente,cursorCasas,cursorTitulos,cursorMercado,cursorCedeval, cursorOrden ,cursorOrdenes;
    private int posicionCedeval=0;
    // TODO: Rename and change types of parameters

    private int posicionCursorTitulo=0,posicionCursorMercado=0,posicionCursorCedeval=0,posicionTipoOrden=0;

    private ScrollView scrollPrincipal;
    private LinearLayout sinContenido;
    private ImageView imagenSinContenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_orden);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = EditarOrden.this.getIntent().getExtras();
        idOrden = extras.getString("idOrden");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        context = EditarOrden.this;


        scrollPrincipal = (ScrollView) findViewById(R.id.scrollView);
        sinContenido = (LinearLayout) findViewById(R.id.sinConetido);
        imagenSinContenido =(ImageView) findViewById(R.id.imagenSinContenido);
        imagenSinContenido.setVisibility(View.GONE);
        scrollPrincipal.setVisibility(View.GONE);
        sinContenido.setVisibility(View.VISIBLE);

        //declaracion de Spinners
        spinnerCedeval =(Spinner) findViewById(R.id.SpinnerCuentaCedeval) ;

        spinnerMercado = (Spinner) findViewById(R.id.SpinnerTipoDeMercado);
        spinnerTitulo = (Spinner) findViewById(R.id.SpinnerTitulo);
        tipoOrden = (RadioGroup) findViewById(R.id.TipoOrden);
        tipoCompra =(RadioButton) findViewById(R.id.TipoCompra);
        tipoVenta =(RadioButton) findViewById(R.id.TipoVenta);
        btnContinuar=(CardView) findViewById(R.id.btnContinuar);
        content_frame = (RelativeLayout) findViewById(R.id.content_frame);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        tokenPushDao = daoSession.getTokenPushDao();
        clienteDao = daoSession.getClienteDao();
        casasCorredorasDao = daoSession.getCasasCorredorasDao();
        cedevalDao = daoSession.getCedevalDao();
        tipoMercadoDao = daoSession.getTipoMercadoDao();
        titulosDao = daoSession.getTitulosDao();
        ordenesDao = daoSession.getOrdenesDao();
        cursorOrden = db.query(ordenesDao.getTablename(),ordenesDao.getAllColumns(),"ID_ORDEN="+idOrden,null,null,null,null);


        cursorCliente = db.query(clienteDao.getTablename(),clienteDao.getAllColumns(),null,null,null,null,null);
        if(cursorCliente.moveToFirst()){
            new GetApiBolsa().execute();
        }

        //Click en los Spinner
        spinnerCedeval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicionCursorCedeval = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spinnerTitulo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicionCursorTitulo =position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerMercado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicionCursorMercado = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipoCompra.isChecked()){
                    posicionTipoOrden=1;
                }else{
                    posicionTipoOrden=2;
                }
                Intent a = new Intent(context, EnviarOrdenEditada.class);
                a.putExtra("idOrden",idOrden);
                a.putExtra("posicionTipoOrden",posicionTipoOrden);
                a.putExtra("posicionCursorMercado",posicionCursorMercado);
                a.putExtra("posicionCursorTitulo",posicionCursorTitulo);
                a.putExtra("posicionCursorCedeval",posicionCursorCedeval);
                startActivity(a);

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
    private class GetApiBolsa extends AsyncTask<Void, Void, Void> {
        int ErrorCode1=1,ErrorCode2=1,ErrorCode3=1,ErrorCode4=1;
        public GetApiBolsa() {

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
            GET_TitulosBolsaDeValores get_titulosBolsaDeValores = new GET_TitulosBolsaDeValores(context);
            ErrorCode1 = get_titulosBolsaDeValores.result;

            Get_TipoMercadosBolsaDeValores get_tipoMercadosBolsaDeValores = new Get_TipoMercadosBolsaDeValores(context);
            ErrorCode2= get_tipoMercadosBolsaDeValores.result;

            Get_CuentasCedevales get_cuentasCedevales = new Get_CuentasCedevales(context,cursorCliente.getString(5),cursorCliente.getString(9));
            ErrorCode3=get_cuentasCedevales.result;

            GETCasasCorredoras getCasasCorredoras = new GETCasasCorredoras(context,cursorCliente.getString(9),cursorCliente.getString(5));
            ErrorCode4= getCasasCorredoras.result;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            loading.dismiss();
            if(ErrorCode1!=0||ErrorCode2!=0||ErrorCode3!=0||ErrorCode4!=0){
                new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("Error obteniendo datos de la Bolsa de Valores")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();

            }else{
                cursorCliente.moveToFirst();

                cursorTitulos = db.query(titulosDao.getTablename(),titulosDao.getAllColumns(),null,null,null,null,null);
                cursorMercado = db.query(tipoMercadoDao.getTablename(),tipoMercadoDao.getAllColumns(),null,null,null,null,null);
                cursorCedeval = db.query(cedevalDao.getTablename(),cedevalDao.getAllColumns(),null,null,null,null,null);
                if(cursorCliente.moveToFirst()||cursorCasas.moveToFirst()||cursorTitulos.moveToFirst()||cursorMercado.moveToFirst()||cursorCedeval.moveToFirst()){
                    scrollPrincipal.setVisibility(View.VISIBLE);
                    sinContenido.setVisibility(View.GONE);
                    adapterSpinnerCedeval AdapterSpinnerCedeval = new adapterSpinnerCedeval(context,cursorCedeval);
                    spinnerCedeval.setAdapter(AdapterSpinnerCedeval);


                    adapterSpinnerMercado AdapterSpinnerMercado = new adapterSpinnerMercado(context,cursorMercado);
                    spinnerMercado.setAdapter(AdapterSpinnerMercado);

                    adapterSpinnerTitulo AdapterSpinnerTitulo = new adapterSpinnerTitulo(context,cursorTitulos);
                    spinnerTitulo.setAdapter(AdapterSpinnerTitulo);
                    setearPosiciones();




                }else{
                    scrollPrincipal.setVisibility(View.GONE);
                    sinContenido.setVisibility(View.VISIBLE);
                    imagenSinContenido.setVisibility(View.VISIBLE);
                }



            }


        }

    }

    private void setearPosiciones(){
        if(cursorOrden.moveToFirst()) {


            if (cursorCedeval.moveToFirst()) {
                for (int posicion = 0; posicion < cursorCedeval.getCount(); posicion++) {
                    if (cursorCedeval.moveToPosition(posicion)) {
                        if (cursorCedeval.getString(2).equals(cursorOrden.getString(18))) {
                            posicionCursorCedeval = posicion;
                            spinnerCedeval.setSelection(posicionCursorCedeval);

                        }
                    }
                }
            }


            if (cursorMercado.moveToFirst()) {
                for (int posicion = 0; posicion < cursorMercado.getCount(); posicion++) {
                    if (cursorMercado.moveToPosition(posicion)) {
                        if (cursorMercado.getString(2).equals(cursorOrden.getString(20))) {
                            posicionCursorMercado = posicion;
                            spinnerMercado.setSelection(posicionCursorMercado);
                        }
                    }
                }
            }


            if (cursorTitulos.moveToFirst()) {
                for (int posicion = 0; posicion < cursorTitulos.getCount(); posicion++) {
                    if (cursorTitulos.moveToPosition(posicion)) {

                        if (cursorTitulos.getString(2).equals(cursorOrden.getString(8))) {
                            posicionCursorTitulo = posicion;
                            spinnerTitulo.setSelection(posicionCursorTitulo);
                        }
                    }
                }
            }


            if (cursorOrden.getString(5).equals("1")) {
                tipoCompra.setChecked(true);
            } else if (cursorOrden.getString(5).equals("2")) {
                tipoVenta.setChecked(true);
            }

        }
    }

}
