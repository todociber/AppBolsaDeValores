package com.todociber.appbolsadevalores.NuevaOrden;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.todociber.appbolsadevalores.Home.MenuPrincipal;
import com.todociber.appbolsadevalores.NuevaOrden.WS.GET_EmisorBolsaDeValores;
import com.todociber.appbolsadevalores.NuevaOrden.WS.POST_NuevaOrden;
import com.todociber.appbolsadevalores.R;
import com.todociber.appbolsadevalores.db.CasasCorredorasDao;
import com.todociber.appbolsadevalores.db.CedevalDao;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.EmisoresDao;
import com.todociber.appbolsadevalores.db.TipoMercadoDao;
import com.todociber.appbolsadevalores.db.TitulosDao;
import com.todociber.appbolsadevalores.util.DatePickerDialogWithMaxMinRange;
import com.todociber.appbolsadevalores.util.DecimalDigitsInputFilter;

import java.util.Calendar;

public class TerminarOrden extends AppCompatActivity {
    public ProgressDialog loading;
    protected int mYear;
    protected int mMonth;
    protected int mDay;
    InputFilter timeFilter;
    Calendar c, cMax, cMin;
    DatePickerDialog.OnDateSetListener datePickerOnDateSetListener;
    TimePickerDialog datePickerOnTimerSetListener;
    int kilometraje, setday, setmonth, setyear, getDay, getMonth, getYear;
    String fechaSelect;
    String date,valorMinimoR,valorMaximoR,montoR;
    private  EditText fechaVigencia;
    private TitulosDao titulosDao;
    private EmisoresDao emisoresDao;
    private Context context;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private TipoMercadoDao tipoMercadoDao;
    private CedevalDao cedevalDao;
    private ClienteDao clienteDao;
    private CasasCorredorasDao casasCorredorasDao;
    private Cursor cursorCasaCorredora,cursorCedeval,cursorTitulo,cursorMercado,cursorEmisor,cursorCliente;
    private int posicionCursorCasa,posicionCursorCedeval,posicionCursorTitulo,posicionCursorMercado,posicionCursorTipoOrden;
    private TextView txtEmisor,txtTasaDeInteres;
    private EditText valorMinimo,valorMaximo,monto,fechaDeVigencia;
    private CardView EnviarOrden;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminar_orden);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        context = TerminarOrden.this;
        c = Calendar.getInstance();

        cMin = Calendar.getInstance();
        fechaVigencia =(EditText)findViewById(R.id.txtFechaDeVigencia);
        txtEmisor =(TextView)findViewById(R.id.txtEmisor);
        txtTasaDeInteres=(TextView)findViewById(R.id.txtTasaDeInteres);
        EnviarOrden = (CardView) findViewById(R.id.btnEnviarOrden);
        valorMaximo =(EditText)findViewById(R.id.valorMaximo);
        valorMinimo =(EditText)findViewById(R.id.valorMinimo);
        monto =(EditText)findViewById(R.id.monto);

        valorMaximo.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(64,2)});
        valorMinimo.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(64,2)});
        monto.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(64,2)});

        assert fechaVigencia != null;
        fechaVigencia.setFocusable(false);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        titulosDao = daoSession.getTitulosDao();
        emisoresDao = daoSession.getEmisoresDao();
        cedevalDao = daoSession.getCedevalDao();
        tipoMercadoDao = daoSession.getTipoMercadoDao();
        casasCorredorasDao = daoSession.getCasasCorredorasDao();
        clienteDao = daoSession.getClienteDao();
        Bundle extras = TerminarOrden.this.getIntent().getExtras();
        posicionCursorCasa = extras.getInt("posicionCursorCasa");
        posicionCursorCedeval = extras.getInt("posicionCursorCedeval");
        posicionCursorTitulo = extras.getInt("posicionCursorTitulo");
        posicionCursorMercado = extras.getInt("posicionCursorMercado");
        posicionCursorTipoOrden = extras.getInt("posicionTipoOrden");

        cursorTitulo = db.query(titulosDao.getTablename(),titulosDao.getAllColumns(),null,null,null,null,null,null);
        if (cursorTitulo.moveToPosition(posicionCursorTitulo)){
            new GetEmisor().execute();
            String tasa = cursorTitulo.getString(3)+" %";
            txtTasaDeInteres.setText(tasa);
        }


        fechaVigencia.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                Calendar cMax = Calendar.getInstance();
                cMax.set(Calendar.DAY_OF_MONTH, mDay+1);
                cMax.set(Calendar.MONTH, mMonth+3);
                cMax.set(Calendar.YEAR, mYear);
                cMin.set(Calendar.DAY_OF_MONTH, mDay+3);
                cMin.set(Calendar.MONTH, mMonth);
                cMin.set(Calendar.YEAR, mYear );


                // Launch Date Picker Dialog

                datePickerOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        setday = dayOfMonth;
                        setmonth = monthOfYear;
                        setyear = year;



                        fechaVigencia.setText((monthOfYear+1)+"/"+dayOfMonth+"/"+year);
                        fechaSelect = String.valueOf((monthOfYear+1)+"/"+dayOfMonth+"/"+year);
                        date = year + "-" + (monthOfYear + 1) + "-"
                                + dayOfMonth;

                    }
                };

                DatePickerDialogWithMaxMinRange a = new DatePickerDialogWithMaxMinRange(
                        context, datePickerOnDateSetListener, cMin, cMax, c);

				/*DatePickerDialog dpd = new DatePickerDialog(
						BitacoraPaso2Activity.this,datePickerOnDateSetListener, mYear, mMonth, mDay);*/
                a.setTitle("Fecha de vigencia");
                a.show();
                return false;
            }
        });
        fechaVigencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                Calendar cMax = Calendar.getInstance();
                cMax.set(Calendar.DAY_OF_MONTH, mDay+1);
                cMax.set(Calendar.MONTH, mMonth+3);
                cMax.set(Calendar.YEAR, mYear);
                cMin.set(Calendar.DAY_OF_MONTH, mDay+3);
                cMin.set(Calendar.MONTH, mMonth);
                cMin.set(Calendar.YEAR, mYear );


                // Launch Date Picker Dialog

                datePickerOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        setday = dayOfMonth;
                        setmonth = monthOfYear;
                        setyear = year;

                        fechaVigencia.setText(year + "-"
                                + (monthOfYear + 1) + "-" + dayOfMonth);
                        fechaSelect = String.valueOf((monthOfYear+1)+"/"+dayOfMonth+"/"+year);
                        date = year + "-" + (monthOfYear + 1) + "-"
                                + dayOfMonth;

                    }
                };

                DatePickerDialogWithMaxMinRange a = new DatePickerDialogWithMaxMinRange(
                        context, datePickerOnDateSetListener, cMin, cMax, c);

				/*DatePickerDialog dpd = new DatePickerDialog(
						BitacoraPaso2Activity.this,datePickerOnDateSetListener, mYear, mMonth, mDay);*/
                a.setTitle("Fecha de vigencia");
                a.show();
            }




        });

        EnviarOrden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cursorEmisor = db.query(emisoresDao.getTablename(),emisoresDao.getAllColumns(),null,null,null,null,null);
                cursorTitulo = db.query(titulosDao.getTablename(),titulosDao.getAllColumns(),null,null,null,null,null);
                cursorMercado = db.query(tipoMercadoDao.getTablename(),tipoMercadoDao.getAllColumns(),null,null,null,null,null);
                cursorCedeval = db.query(cedevalDao.getTablename(),cedevalDao.getAllColumns(),null,null,null,null,null);
                cursorCasaCorredora = db.query(casasCorredorasDao.getTablename(),casasCorredorasDao.getAllColumns(),null,null,null,null,null);
                cursorCliente = db.query(clienteDao.getTablename(),clienteDao.getAllColumns(),null,null,null,null,null);


                if(cursorTitulo.moveToPosition(posicionCursorTitulo)&& cursorMercado.moveToPosition(posicionCursorMercado)&&
                        cursorCedeval.moveToPosition(posicionCursorCedeval)&& cursorCasaCorredora.moveToPosition(posicionCursorCasa)&& cursorCliente.moveToFirst()&& cursorEmisor.moveToFirst()
                        ){

                    valorMaximoR = valorMaximo.getText().toString();
                    valorMinimoR = valorMinimo.getText().toString();
                    montoR = monto.getText().toString();
                    if(validador()){
                        new POSOrden().execute();
                    }


                }


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

    public boolean validador(){
        boolean sinErrore = true;
        String Error ="";
        if(valorMinimoR.equals(valorMaximoR)){
            sinErrore = false;
            Error ="valor minimo y máximo no pueden ser iguales";
        }else if(valorMinimoR.equals("")|| valorMinimoR.equals("0")){
            sinErrore = false;
            Error = "Valor Minimo no es un valor valido";
        }else if(valorMaximo.equals("")|| valorMaximo.equals("0")){
            sinErrore = false;
            Error = "Valor Maximo no es un valor valido";
        }

        new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage(Error)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
        return sinErrore;
    }

    private class GetEmisor extends AsyncTask<Void, Void, Void> {
        int ErrorCode1=1,ErrorCode2=1,ErrorCode3=1,ErrorCode4=1;
        public GetEmisor() {

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
            if(cursorTitulo.moveToPosition(posicionCursorTitulo)){
                GET_EmisorBolsaDeValores get_emisorBolsaDeValores = new GET_EmisorBolsaDeValores(context,cursorTitulo.getString(1));
                ErrorCode1 = get_emisorBolsaDeValores.result;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            loading.dismiss();
            if(ErrorCode1==0){
                cursorEmisor =db.query(emisoresDao.getTablename(),emisoresDao.getAllColumns(),null,null,null,null,null);
                if(cursorEmisor.moveToFirst()){
                    txtEmisor.setText(cursorEmisor.getString(2));
                }
            }





        }

    }

    private class POSOrden extends AsyncTask<Void, Void, Void> {
        int ErrorCode1=1,ErrorCode2=1,ErrorCode3=1,ErrorCode4=1;
        String msg;
        public POSOrden() {

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
            if(cursorTitulo.moveToPosition(posicionCursorTitulo)){
                POST_NuevaOrden post_nuevaOrden = new POST_NuevaOrden(cursorCedeval.getString(1),cursorCasaCorredora.getString(1),cursorCliente.getString(5),String.valueOf(posicionCursorTipoOrden),cursorCliente.getString(2),cursorMercado.getString(2),
                        cursorTitulo.getString(2),cursorEmisor.getString(2),valorMaximoR,valorMinimoR,montoR,fechaSelect,cursorTitulo.getString(3),cursorCliente.getString(9),context);
                ErrorCode1 = post_nuevaOrden.error;
                msg = post_nuevaOrden.msg;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            loading.dismiss();
            if(ErrorCode1==0){
                new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("Orden Creada con exito")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, MenuPrincipal.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // <- Aquí :)
                                startActivity(intent);
                                finish();
                                dialog.dismiss();
                            }
                        }).create().show();
            }else{
                if(msg.equals("")){
                    msg="Error de conexion";
                }
                new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage(msg)
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }




        }

    }

}
