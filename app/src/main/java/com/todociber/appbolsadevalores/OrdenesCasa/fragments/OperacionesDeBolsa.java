package com.todociber.appbolsadevalores.OrdenesCasa.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.todociber.appbolsadevalores.OrdenesCasa.Adapter.AdapterOperaciones;
import com.todociber.appbolsadevalores.OrdenesCasa.WS.GetOrdenesByCasa;
import com.todociber.appbolsadevalores.R;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.OperacionesBolsaDao;
import com.todociber.appbolsadevalores.db.OrdenesDao;
import com.todociber.appbolsadevalores.db.TokenPushDao;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OperacionesDeBolsa.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OperacionesDeBolsa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OperacionesDeBolsa extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private TokenPushDao tokenPushDao;
    private ClienteDao clienteDao;
    public ProgressDialog loading;
    private OrdenesDao ordenesDao;
    private Context context;
    private OperacionesBolsaDao operacionesBolsaDao;
    private String idOrden,motivo;
    private GetOrdenesByCasa getOrdenesByCasa;
    public Cursor cursorDetalleOrden, CursorCliente, cursorOperaciones;
    private AdapterOperaciones adapterOperaciones;
    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private  int posicionCursorCasa;
    private OnFragmentInteractionListener mListener;
    ListView ListadoOperaciones;
    LinearLayout sinConetido;
    public OperacionesDeBolsa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OperacionesDeBolsa.
     */
    // TODO: Rename and change types and number of parameters
    public static OperacionesDeBolsa newInstance(int param1, String param2) {
        OperacionesDeBolsa fragment = new OperacionesDeBolsa();
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

        View rootView = inflater.inflate(R.layout.fragment_operaciones_de_bolsa, container, false);
        // Inflate the layout for this fragment
        ListadoOperaciones =(ListView) rootView.findViewById(R.id.ListadoOperaciones);
        sinConetido =(LinearLayout) rootView.findViewById(R.id.sinConetido);
        context = getActivity();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        ordenesDao = daoSession.getOrdenesDao();
        clienteDao = daoSession.getClienteDao();
        operacionesBolsaDao = daoSession.getOperacionesBolsaDao();
        cursorDetalleOrden = db.query(ordenesDao.getTablename(),ordenesDao.getAllColumns(),null,null,null,null,null);
        posicionCursorCasa = mParam1;
        if(cursorDetalleOrden.moveToPosition(posicionCursorCasa)){
            cursorOperaciones = db.query(operacionesBolsaDao.getTablename(),operacionesBolsaDao.getAllColumns(),"ID_ORDEN="+cursorDetalleOrden.getString(1),null,null,null,null);
            if(cursorOperaciones.moveToFirst()){
                adapterOperaciones = new AdapterOperaciones(getActivity(),cursorOperaciones);
                ListadoOperaciones.setAdapter(adapterOperaciones);
            }else{
                ListadoOperaciones.setVisibility(View.GONE);
                sinConetido.setVisibility(View.VISIBLE);
            }

        }else{
            ListadoOperaciones.setVisibility(View.GONE);
            sinConetido.setVisibility(View.VISIBLE);
        }

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
}
