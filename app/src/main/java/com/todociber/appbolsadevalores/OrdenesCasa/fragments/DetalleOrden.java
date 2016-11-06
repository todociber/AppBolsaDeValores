package com.todociber.appbolsadevalores.OrdenesCasa.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.todociber.appbolsadevalores.OrdenesCasa.EditarOrden;
import com.todociber.appbolsadevalores.OrdenesCasa.HistorialOrden;
import com.todociber.appbolsadevalores.OrdenesCasa.WS.GetOrdenesByCasa;
import com.todociber.appbolsadevalores.OrdenesCasa.WS.PutCancelarOrden;
import com.todociber.appbolsadevalores.OrdenesCasa.WS.PutEjecutarOrden;
import com.todociber.appbolsadevalores.R;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.OrdenesDao;
import com.todociber.appbolsadevalores.db.TokenPushDao;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetalleOrden.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetalleOrden#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleOrden extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
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
    private String idOrden,motivo,idOrdenPadre;
    private GetOrdenesByCasa getOrdenesByCasa;
    private TextView NombreCasaCorredora,txtCorrelativo,txtFechaDeVigencia,
            txtAgenteCorredor,txtTipoDeOrden,txtTituloNombre,txtValorMinimo,
            txtValorMaximo,txtMontoDeInversion,txtTasaDeInversion,txtComision,
            txtCuentaCedeval,txtEmisor,txtTipoMercado,txtEstadoOrden;
    private LinearLayout bannerBotones;
    private RelativeLayout BannerHistorial;
    private CardView btnEjecutar,btnModificar, btnCancelar, btnHistorial;
    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetalleOrden() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleOrden.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleOrden newInstance(int param1, String param2) {
        DetalleOrden fragment = new DetalleOrden();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detalle_orden, container, false);
        NombreCasaCorredora = (TextView)rootView.findViewById(R.id.NombreCasaCorredora);
        txtCorrelativo = (TextView) rootView.findViewById(R.id.txtCorrelativo);
        txtFechaDeVigencia = (TextView) rootView.findViewById(R.id.txtFechaDeVigencia);
        txtAgenteCorredor = (TextView) rootView.findViewById(R.id.txtAgenteCorredor);
        txtTipoDeOrden = (TextView) rootView.findViewById(R.id.txtTipoDeOrden);
        txtTituloNombre = (TextView) rootView.findViewById(R.id.txtTituloNombre);
        txtValorMinimo = (TextView) rootView.findViewById(R.id.txtValorMinimo);
        txtValorMaximo = (TextView) rootView.findViewById(R.id.txtValorMaximo);
        txtMontoDeInversion = (TextView) rootView.findViewById(R.id.txtMontoDeInversion);
        txtTasaDeInversion = (TextView) rootView.findViewById(R.id.txtTasaDeInversion);
        txtComision = (TextView) rootView.findViewById(R.id.txtComision);
        txtCuentaCedeval = (TextView) rootView.findViewById(R.id.txtCuentaCedeval);
        txtEmisor = (TextView) rootView.findViewById(R.id.txtEmisor);
        txtTipoMercado = (TextView) rootView.findViewById(R.id.txtTipoMercado);
        txtEstadoOrden = (TextView) rootView.findViewById(R.id.txtEstadoOrden);
        btnEjecutar = (CardView) rootView.findViewById(R.id.btnEjecutarOrden);
        btnModificar = (CardView) rootView.findViewById(R.id.btnModificarOrden);
        btnCancelar = (CardView) rootView.findViewById(R.id.btnCancelarOrden);
        btnHistorial =(CardView) rootView.findViewById(R.id.btnHistorial);
        bannerBotones = (LinearLayout) rootView.findViewById(R.id.bannerBotones);
        BannerHistorial =(RelativeLayout) rootView.findViewById(R.id.BannerHistorial);
        //Recibir data de activity anterior

        posicionCursorCasa = mParam1;

        context = getActivity();
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
            idOrdenPadre =cursorDetalleOrden.getString(6);
            if(cursorDetalleOrden.getString(7).equals("1") ||
                    cursorDetalleOrden.getString(7).equals("3")||
                    cursorDetalleOrden.getString(7).equals("4")||
                    cursorDetalleOrden.getString(7).equals("6")||
                    cursorDetalleOrden.getString(7).equals("5")||
                    cursorDetalleOrden.getString(7).equals("7")||
                    cursorDetalleOrden.getString(7).equals("8")){

                if (cursorDetalleOrden.getString(7).equals("1")){
                    bannerBotones.setVisibility(View.VISIBLE);
                    btnEjecutar.setVisibility(View.GONE);
                    btnCancelar.setVisibility(View.GONE);
                }else{
                    bannerBotones.setVisibility(View.GONE);
                }

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
                            getActivity().finish();
                        }
                    }).create().show();
        }

        CursorCliente = db.query(clienteDao.getTablename(),clienteDao.getAllColumns(),null,null,null,null,null);

        btnEjecutar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CursorCliente.moveToFirst()){
                    new AlertDialog.Builder(context)
                            .setTitle("Ejecutar")
                            .setMessage("¿Realmente desea ejecutar esta orden?")
                            .setCancelable(false)
                            .setPositiveButton("Ejecutar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new putEjecutarTask().execute();
                                    dialog.dismiss();

                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            }).create().show();


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

                                if(CursorCliente.moveToFirst()){
                                    motivo = input.getText().toString();
                                    if(motivo.equals("")){
                                        new AlertDialog.Builder(context)
                                                .setTitle("Error")
                                                .setMessage("Necesita Escribir un motivo")
                                                .setCancelable(false)
                                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();

                                                    }
                                                }).create().show();
                                    }else{
                                        dialog.dismiss();
                                        new putCancelar().execute();
                                    }

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
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), HistorialOrden.class);
                a.putExtra("idOrden",idOrdenPadre);
                startActivity(a);
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), EditarOrden.class);
                a.putExtra("idOrden",idOrden);
                startActivity(a);
            }
        });
        return rootView;
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void actualizarPantalla(){
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
            idOrdenPadre =cursorDetalleOrden.getString(6);
            if(cursorDetalleOrden.getString(7).equals("1") ||
                    cursorDetalleOrden.getString(7).equals("3")||
                    cursorDetalleOrden.getString(7).equals("4")||
                    cursorDetalleOrden.getString(7).equals("6")||
                    cursorDetalleOrden.getString(7).equals("5")||
                    cursorDetalleOrden.getString(7).equals("7")||
                    cursorDetalleOrden.getString(7).equals("8")){

                if (cursorDetalleOrden.getString(7).equals("1")){
                    bannerBotones.setVisibility(View.VISIBLE);
                    btnEjecutar.setVisibility(View.GONE);
                    btnCancelar.setVisibility(View.GONE);
                }else{
                    bannerBotones.setVisibility(View.GONE);
                }

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
                            getActivity().finish();
                        }
                    }).create().show();
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
            String idCliente = CursorCliente.getString(5);
            String tokenSession = CursorCliente.getString(9);
            GetOrdenesByCasa getOrdenesByCasa = new GetOrdenesByCasa(context,tokenSession,idCliente,mParam2);
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
                                actualizarPantalla();

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
            String idCliente = CursorCliente.getString(5);
            String tokenSession = CursorCliente.getString(9);
            GetOrdenesByCasa getOrdenesByCasa = new GetOrdenesByCasa(context,tokenSession,idCliente,mParam2);
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
                                actualizarPantalla();
                                dialog.dismiss();

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
