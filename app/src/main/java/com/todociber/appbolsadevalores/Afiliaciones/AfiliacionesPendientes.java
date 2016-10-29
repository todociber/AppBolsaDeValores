package com.todociber.appbolsadevalores.Afiliaciones;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.todociber.appbolsadevalores.Afiliaciones.Adapter.AdapterAfiliaciones;
import com.todociber.appbolsadevalores.Afiliaciones.WS.GET_Afiliaciones;
import com.todociber.appbolsadevalores.R;
import com.todociber.appbolsadevalores.db.AfiliacionesDao;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.TokenPushDao;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AfiliacionesPendientes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AfiliacionesPendientes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AfiliacionesPendientes extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ProgressDialog loading;
    private  Context context;
    private Cursor cursorCliente;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private TokenPushDao tokenPushDao;
    private ClienteDao clienteDao;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private Cursor cursorAfiliaciones;
    private String mParam2;
    private AfiliacionesDao afiliacionesDao;
    private LinearLayout sincontenido;
    private ListView listadoAfiliaciones;
    private OnFragmentInteractionListener mListener;

    public AfiliacionesPendientes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AfiliacionesPendientes.
     */
    // TODO: Rename and change types and number of parameters
    public static AfiliacionesPendientes newInstance(String param1, String param2) {
        AfiliacionesPendientes fragment = new AfiliacionesPendientes();
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
        View rootview = inflater.inflate(R.layout.fragment_afiliaciones_pendientes, container, false);
        listadoAfiliaciones =(ListView) rootview.findViewById(R.id.ListadAfiliaciones);
        sincontenido = (LinearLayout)rootview.findViewById(R.id.sinConetido);
        context = getActivity();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        tokenPushDao = daoSession.getTokenPushDao();
        clienteDao = daoSession.getClienteDao();
        afiliacionesDao = daoSession.getAfiliacionesDao();
        cursorCliente = db.query(clienteDao.getTablename(),clienteDao.getAllColumns(),null,null,null,null,null);
        if(cursorCliente.moveToFirst()){
            new getOrdenes().execute();
        }
        return rootview;
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




    private class getOrdenes extends AsyncTask<Void, Void, Void> {
        int ErrorCode;

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
            GET_Afiliaciones get_afiliaciones = new GET_Afiliaciones(context,cursorCliente.getString(5),cursorCliente.getString(9));
            ErrorCode = get_afiliaciones.result;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {



                loading.dismiss();
            cursorAfiliaciones = db.query(afiliacionesDao.getTablename(),afiliacionesDao.getAllColumns(),null,null,null,null,null);

            if(ErrorCode==0){
                if(cursorAfiliaciones.moveToFirst()){
                    listadoAfiliaciones.setVisibility(View.VISIBLE);
                    AdapterAfiliaciones adapterAfiliaciones = new AdapterAfiliaciones(context,cursorAfiliaciones);
                    listadoAfiliaciones.setAdapter(adapterAfiliaciones);
                }else{
                    new AlertDialog.Builder(context)
                            .setTitle("Error")
                            .setMessage("No se encontraron  afiliaciones ")
                            .setCancelable(false)
                            .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sincontenido.setVisibility(View.VISIBLE);
                                    listadoAfiliaciones.setVisibility(View.GONE);
                                    dialog.dismiss();
                                }
                            }).create().show();
                }


            }else  {
                new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("No se encontraron  afiliaciones ")
                        .setCancelable(false)
                        .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sincontenido.setVisibility(View.VISIBLE);
                                listadoAfiliaciones.setVisibility(View.GONE);
                                dialog.dismiss();
                            }
                        }).create().show();
            }


        }

    }
}
