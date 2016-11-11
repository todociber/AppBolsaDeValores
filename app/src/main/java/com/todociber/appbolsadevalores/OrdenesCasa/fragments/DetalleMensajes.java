package com.todociber.appbolsadevalores.OrdenesCasa.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ListView;

import com.todociber.appbolsadevalores.OrdenesCasa.Adapter.AdapterMensajes;
import com.todociber.appbolsadevalores.OrdenesCasa.WS.GetOrdenesByCasa;
import com.todociber.appbolsadevalores.OrdenesCasa.WS.POST_EnviarMensaje;
import com.todociber.appbolsadevalores.R;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.MensajesDao;
import com.todociber.appbolsadevalores.db.OrdenesDao;
import com.todociber.appbolsadevalores.db.TokenPushDao;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetalleMensajes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetalleMensajes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleMensajes extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ProgressDialog loading;
    public Cursor cursorDetalleOrden, cursorCliente,cursorMensajes;
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
    private MensajesDao mensajesDao;
    private AdapterMensajes adapterMensajes;
    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private ListView listadoMensajes;
    private EditText txtMensaje;
    private CardView btnEnviarMensaje;
    private  String textoComentaio;
    private OnFragmentInteractionListener mListener;
    private LinearLayout linearLayout;
    private int EstadoOrden;
    public DetalleMensajes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleMensajes.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleMensajes newInstance(int param1, String param2) {
        DetalleMensajes fragment = new DetalleMensajes();
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
        View rootView =inflater.inflate(R.layout.fragment_detalle_mensajes, container, false);
        posicionCursorCasa = mParam1;
        listadoMensajes = (ListView) rootView.findViewById(R.id.ListadoMensajes);
        txtMensaje = (EditText) rootView.findViewById(R.id.txtMensajeEdit);
        btnEnviarMensaje =(CardView) rootView.findViewById(R.id.btnEnviarMensaje);
        linearLayout = (LinearLayout)rootView.findViewById(R.id.linearLayout);



        context = getActivity();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        ordenesDao = daoSession.getOrdenesDao();
        clienteDao = daoSession.getClienteDao();
        mensajesDao = daoSession.getMensajesDao();
        clienteDao = daoSession.getClienteDao();
        cursorDetalleOrden = db.query(ordenesDao.getTablename(),ordenesDao.getAllColumns(),null,null,null,null,null);
        if(cursorDetalleOrden.moveToPosition(posicionCursorCasa)){

            EstadoOrden = Integer.parseInt(cursorDetalleOrden.getString(7));
            if(EstadoOrden ==3||EstadoOrden ==4||EstadoOrden ==6||EstadoOrden ==7||EstadoOrden ==8){
                linearLayout.setVisibility(View.GONE);
            }

            cursorMensajes = db.query(mensajesDao.getTablename(),mensajesDao.getAllColumns(),"ID_ORDEN="+cursorDetalleOrden.getString(1),null,null,null,null);
            cursorCliente = db.query(clienteDao.getTablename(),clienteDao.getAllColumns(),null,null,null,null,null);
            if(cursorMensajes.moveToFirst()&&cursorCliente.moveToFirst()){

                adapterMensajes = new AdapterMensajes(getActivity(),cursorMensajes,cursorCliente.getString(1));
                listadoMensajes.setAdapter(adapterMensajes);
            }
        }



        btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textoComentaio = txtMensaje.getText().toString();
                if(textoComentaio.length()>0){
                    cursorCliente.moveToFirst();
                    cursorDetalleOrden.moveToPosition(posicionCursorCasa);
                    new POSTMensaje().execute();
                }
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

    private class POSTMensaje extends AsyncTask<Void, Void, Void> {
        int ErrorCode;
        String idOrden;
        public POSTMensaje() {

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
            idOrden = cursorDetalleOrden.getString(1);
            POST_EnviarMensaje post_enviarMensaje = new POST_EnviarMensaje(cursorCliente.getString(5),cursorCliente.getString(1),
                    idOrden,textoComentaio,cursorCliente.getString(2),cursorCliente.getString(3),cursorCliente.getString(9),getActivity());
            ErrorCode = post_enviarMensaje.error;
            String idCliente = cursorCliente.getString(5);
            String tokenSession = cursorCliente.getString(9);
            GetOrdenesByCasa getOrdenesByCasa = new GetOrdenesByCasa(context,tokenSession,idCliente,mParam2);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            loading.dismiss();
            if(ErrorCode==0){
                new AlertDialog.Builder(context)
                        .setTitle("Exito")
                        .setMessage("Comentario Enviado con exito")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cursorMensajes = db.query(mensajesDao.getTablename(),mensajesDao.getAllColumns(),"ID_ORDEN="+idOrden,null,null,null,null);
                                cursorCliente = db.query(clienteDao.getTablename(),clienteDao.getAllColumns(),null,null,null,null,null);
                                if(cursorMensajes.moveToFirst()&&cursorCliente.moveToFirst()){
                                    adapterMensajes = new AdapterMensajes(getActivity(),cursorMensajes,cursorCliente.getString(1));
                                    listadoMensajes.setAdapter(adapterMensajes);
                                }
                                txtMensaje.setText("");

                            }
                        }).create().show();
            }else  {
                new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("Error enviando Comentario")
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
