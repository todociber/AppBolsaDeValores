package com.todociber.appbolsadevalores.CasasCorredoras;

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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.todociber.appbolsadevalores.Home.WS.GETCasasCorredoras;
import com.todociber.appbolsadevalores.Home.adapter.ListadoCasasAdapter;
import com.todociber.appbolsadevalores.OrdenesCasa.OrdenesPorCasa;
import com.todociber.appbolsadevalores.R;
import com.todociber.appbolsadevalores.db.CasasCorredorasDao;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.TokenPushDao;
import com.todociber.appbolsadevalores.login.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CasasCorredoras.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CasasCorredoras#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CasasCorredoras extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public Context context;
    public ProgressDialog loading;
    public Cursor CursorCliete,CursorCasas;
    public ListView ListadoCasas;
    public ListadoCasasAdapter CasasAdapter;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private TokenPushDao tokenPushDao;
    private ClienteDao clienteDao;
    private SwipeRefreshLayout swipeContainer;
    private RelativeLayout content_frame;
    private GETCasasCorredoras getOrdenesByCasa;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CasasCorredoras() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CasasCorredoras.
     */
    // TODO: Rename and change types and number of parameters
    public static CasasCorredoras newInstance(String param1, String param2) {
        CasasCorredoras fragment = new CasasCorredoras();
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
        View rootview = inflater.inflate(R.layout.fragment_casas_corredoras, container, false);
                context = getActivity();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        tokenPushDao = daoSession.getTokenPushDao();
        clienteDao = daoSession.getClienteDao();

        ListadoCasas = (ListView) rootview.findViewById(R.id.ListadoCasas);
        CasasCorredorasDao casasCorredorasDao = daoSession.getCasasCorredorasDao();
        CursorCasas = db.query(casasCorredorasDao.getTablename(),casasCorredorasDao.getAllColumns(),null,null,null,null,null);
        CursorCliete =db.query(clienteDao.getTablename(),clienteDao.getAllColumns(),null,null,null,null,null);


        ListadoCasas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent a = new Intent(context, OrdenesPorCasa.class);
                CursorCasas.moveToPosition(position);
                a.putExtra("idCasa", CursorCasas.getString(1));
                a.putExtra("posicionCasa",position);
                startActivity(a);

            }
        });
        new GETCasas(0).execute();
        swipeContainer = (SwipeRefreshLayout) rootview.findViewById(R.id.swipe);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //new getOrdenes(1).execute();
                CursorCliete =db.query(clienteDao.getTablename(),clienteDao.getAllColumns(),null,null,null,null,null);
                if(CursorCliete.moveToFirst()){
                    new GETCasas(1).execute();
                }

            }
        });

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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private class GETCasas extends AsyncTask<Void, Void, Void> {
        int ErrorCode;
        int a=0;
        public GETCasas(int a) {
            this.a =a;
        }

        @Override
        protected void onPreExecute() {
            if(a!=1){
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
            CursorCliete.moveToFirst();
            getOrdenesByCasa  = new GETCasasCorredoras(context,CursorCliete.getString(9),CursorCliete.getString(5));
            ErrorCode = getOrdenesByCasa.result;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if(a ==1){
                swipeContainer.setRefreshing(false);
            }else{
                loading.dismiss();
            }

            if(ErrorCode==0) {
                CasasCorredorasDao casasCorredorasDao = daoSession.getCasasCorredorasDao();
                CursorCasas = db.query(casasCorredorasDao.getTablename(), casasCorredorasDao.getAllColumns(), null, null, null, null, null);

                if (CursorCasas.moveToFirst()) {
                    CasasAdapter = new ListadoCasasAdapter(context, CursorCasas);
                    ListadoCasas.setAdapter(CasasAdapter);
                    ListadoCasas.setVerticalScrollBarEnabled(false);
                }
            }else if(ErrorCode ==2){
                clienteDao.deleteAll();
                Intent a = new Intent(context, MainActivity.class);
                startActivity(a);
                getActivity().finish();

            }else  {
                new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("Error en Actualizacion")
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
