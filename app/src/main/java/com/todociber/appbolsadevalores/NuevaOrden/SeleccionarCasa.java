package com.todociber.appbolsadevalores.NuevaOrden;

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
import com.todociber.appbolsadevalores.NuevaOrden.adapter.adapterSpinnerCasa;
import com.todociber.appbolsadevalores.NuevaOrden.adapter.adapterSpinnerCedeval;
import com.todociber.appbolsadevalores.NuevaOrden.adapter.adapterSpinnerMercado;
import com.todociber.appbolsadevalores.NuevaOrden.adapter.adapterSpinnerTitulo;
import com.todociber.appbolsadevalores.R;
import com.todociber.appbolsadevalores.db.CasasCorredorasDao;
import com.todociber.appbolsadevalores.db.CedevalDao;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.TipoMercadoDao;
import com.todociber.appbolsadevalores.db.TitulosDao;
import com.todociber.appbolsadevalores.db.TokenPushDao;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SeleccionarCasa.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeleccionarCasa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeleccionarCasa extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ProgressDialog loading;
    private Spinner spinnerCedeval, spinnerCasaCorredora,spinnerMercado,spinnerTitulo;
    private RadioGroup tipoOrden;
    private RadioButton tipoVenta,tipoCompra;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private TokenPushDao tokenPushDao;
    private ClienteDao clienteDao;
    private CardView btnContinuar;
    private CasasCorredorasDao casasCorredorasDao;
    private CedevalDao cedevalDao;
    private TipoMercadoDao tipoMercadoDao;
    private TitulosDao titulosDao;
    private RelativeLayout content_frame;
    private Cursor cursorCliente,cursorCasas,cursorTitulos,cursorMercado,cursorCedeval;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int posicionCursorCasa=0,posicionCursorTitulo=0,posicionCursorMercado=0,posicionCursorCedeval=0,posicionTipoOrden=0;

    private ScrollView scrollPrincipal;
    private LinearLayout sinContenido;
    private ImageView imagenSinContenido;
    private OnFragmentInteractionListener mListener;

    public SeleccionarCasa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeleccionarCasa.
     */
    // TODO: Rename and change types and number of parameters
    public static SeleccionarCasa newInstance(String param1, String param2) {
        SeleccionarCasa fragment = new SeleccionarCasa();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_seleccionar_casa, container, false);
        scrollPrincipal = (ScrollView) rootView.findViewById(R.id.scrollView);
        sinContenido = (LinearLayout) rootView.findViewById(R.id.sinConetido);
        imagenSinContenido =(ImageView) rootView.findViewById(R.id.imagenSinContenido);
        imagenSinContenido.setVisibility(View.GONE);
        scrollPrincipal.setVisibility(View.GONE);
        sinContenido.setVisibility(View.VISIBLE);

        //declaracion de Spinners
        spinnerCedeval =(Spinner) rootView.findViewById(R.id.SpinnerCuentaCedeval) ;
        spinnerCasaCorredora= (Spinner) rootView.findViewById(R.id.SpinnerCasaCorredora);
        spinnerMercado = (Spinner) rootView.findViewById(R.id.SpinnerTipoDeMercado);
        spinnerTitulo = (Spinner) rootView.findViewById(R.id.SpinnerTitulo);
        tipoOrden = (RadioGroup) rootView.findViewById(R.id.TipoOrden);
        tipoCompra =(RadioButton) rootView.findViewById(R.id.TipoCompra);
        tipoVenta =(RadioButton) rootView.findViewById(R.id.TipoVenta);
        btnContinuar=(CardView) rootView.findViewById(R.id.btnContinuar);
        content_frame = (RelativeLayout) rootView.findViewById(R.id.content_frame);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        tokenPushDao = daoSession.getTokenPushDao();
        clienteDao = daoSession.getClienteDao();
        casasCorredorasDao = daoSession.getCasasCorredorasDao();
        cedevalDao = daoSession.getCedevalDao();
        tipoMercadoDao = daoSession.getTipoMercadoDao();
        titulosDao = daoSession.getTitulosDao();

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

        spinnerCasaCorredora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicionCursorCasa =position;
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
                Intent a = new Intent(getActivity(), TerminarOrden.class);
                a.putExtra("posicionTipoOrden",posicionTipoOrden);
                a.putExtra("posicionCursorMercado",posicionCursorMercado);
                a.putExtra("posicionCursorTitulo",posicionCursorTitulo);
                a.putExtra("posicionCursorCasa",posicionCursorCasa);
                a.putExtra("posicionCursorCedeval",posicionCursorCedeval);
                startActivity(a);

            }
        });
        return rootView;
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

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private class GetApiBolsa extends AsyncTask<Void, Void, Void> {
        int ErrorCode1=1,ErrorCode2=1,ErrorCode3=1,ErrorCode4=1;
        public GetApiBolsa() {

        }

        @Override
        protected void onPreExecute() {

                try {
                    loading = new ProgressDialog(getActivity());
                    loading.setMessage("Cargando");
                    loading.setCancelable(false);
                    loading.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }



        }

        @Override
        protected Void doInBackground(Void... arg0) {
            GET_TitulosBolsaDeValores get_titulosBolsaDeValores = new GET_TitulosBolsaDeValores(getActivity());
            ErrorCode1 = get_titulosBolsaDeValores.result;

            Get_TipoMercadosBolsaDeValores get_tipoMercadosBolsaDeValores = new Get_TipoMercadosBolsaDeValores(getActivity());
            ErrorCode2= get_tipoMercadosBolsaDeValores.result;

            Get_CuentasCedevales get_cuentasCedevales = new Get_CuentasCedevales(getActivity(),cursorCliente.getString(5),cursorCliente.getString(9));
            ErrorCode3=get_cuentasCedevales.result;

            GETCasasCorredoras getCasasCorredoras = new GETCasasCorredoras(getActivity(),cursorCliente.getString(9),cursorCliente.getString(5));
            ErrorCode4= getCasasCorredoras.result;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


          loading.dismiss();
                if(ErrorCode1!=0||ErrorCode2!=0||ErrorCode3!=0||ErrorCode4!=0){
                    new AlertDialog.Builder(getActivity())
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
                    cursorCasas = db.query(casasCorredorasDao.getTablename(),casasCorredorasDao.getAllColumns(),null,null,null,null,null);
                    cursorTitulos = db.query(titulosDao.getTablename(),titulosDao.getAllColumns(),null,null,null,null,null);
                    cursorMercado = db.query(tipoMercadoDao.getTablename(),tipoMercadoDao.getAllColumns(),null,null,null,null,null);
                    cursorCedeval = db.query(cedevalDao.getTablename(),cedevalDao.getAllColumns(),null,null,null,null,null);
                    if(cursorCliente.moveToFirst()||cursorCasas.moveToFirst()||cursorTitulos.moveToFirst()||cursorMercado.moveToFirst()||cursorCedeval.moveToFirst()){
                        scrollPrincipal.setVisibility(View.VISIBLE);
                        sinContenido.setVisibility(View.GONE);
                        adapterSpinnerCedeval AdapterSpinnerCedeval = new adapterSpinnerCedeval(getActivity(),cursorCedeval);
                        spinnerCedeval.setAdapter(AdapterSpinnerCedeval);

                        adapterSpinnerCasa  AdapterSpinnerCasa = new adapterSpinnerCasa(getActivity(),cursorCasas);
                        spinnerCasaCorredora.setAdapter(AdapterSpinnerCasa);

                        adapterSpinnerMercado AdapterSpinnerMercado = new adapterSpinnerMercado(getActivity(),cursorMercado);
                        spinnerMercado.setAdapter(AdapterSpinnerMercado);

                        adapterSpinnerTitulo AdapterSpinnerTitulo = new adapterSpinnerTitulo(getActivity(),cursorTitulos);
                        spinnerTitulo.setAdapter(AdapterSpinnerTitulo);




                    }else{
                        scrollPrincipal.setVisibility(View.GONE);
                        sinContenido.setVisibility(View.VISIBLE);
                        imagenSinContenido.setVisibility(View.VISIBLE);
                    }



                }


        }

    }
}
