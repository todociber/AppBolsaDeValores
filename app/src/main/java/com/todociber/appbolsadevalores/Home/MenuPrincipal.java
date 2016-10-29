package com.todociber.appbolsadevalores.Home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.todociber.appbolsadevalores.Afiliaciones.AfiliacionesPendientes;
import com.todociber.appbolsadevalores.Home.WS.GETCasasCorredoras;
import com.todociber.appbolsadevalores.Home.adapter.ListadoCasasAdapter;
import com.todociber.appbolsadevalores.NuevaOrden.SeleccionarCasa;
import com.todociber.appbolsadevalores.OrdenesCasa.OrdenesPorCasa;
import com.todociber.appbolsadevalores.R;
import com.todociber.appbolsadevalores.db.CasasCorredorasDao;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.TokenPushDao;
import com.todociber.appbolsadevalores.login.MainActivity;

public class MenuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public Context context;
    public  Cursor CursorCliete,CursorCasas;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        content_frame = (RelativeLayout) findViewById(R.id.content_frame);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        try {
            //new GetDataTask(0).execute();

        }catch (Exception e){

        }
        context = MenuPrincipal.this;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        tokenPushDao = daoSession.getTokenPushDao();
        clienteDao = daoSession.getClienteDao();

        ListadoCasas = (ListView) findViewById(R.id.ListadoCasas);
        CasasCorredorasDao casasCorredorasDao = daoSession.getCasasCorredorasDao();
        CursorCasas = db.query(casasCorredorasDao.getTablename(),casasCorredorasDao.getAllColumns(),null,null,null,null,null);

        if(CursorCasas.moveToFirst()){
            CasasAdapter = new ListadoCasasAdapter(context,CursorCasas);
            ListadoCasas.setAdapter(CasasAdapter);
            ListadoCasas.setVerticalScrollBarEnabled(false);
        }


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

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //new getOrdenes(1).execute();
                CursorCliete =db.query(clienteDao.getTablename(),clienteDao.getAllColumns(),null,null,null,null,null);
                if(CursorCliete.moveToFirst()){
                    new GETCasas().execute();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.CasaCorredora) {
            content_frame.setVisibility(View.GONE);
            swipeContainer.setVisibility(View.VISIBLE);
        } else if (id == R.id.Afiliaciones) {
            displayView(2);
        } else if(id == R.id.NuevaOrden){
            displayView(1);
        }else if (id == R.id.cerrarSession) {
            clienteDao.deleteAll();
            Intent a = new Intent(context, MainActivity.class);
            startActivity(a);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void displayView(int position) {
        Fragment fragment = null;
   String title ="";
        switch (position) {
            case 0:

                content_frame.setVisibility(View.GONE);
                swipeContainer.setVisibility(View.VISIBLE);


                break;
            case 1:
                swipeContainer.setVisibility(View.GONE);
                content_frame.setVisibility(View.VISIBLE);
                fragment = new SeleccionarCasa();
                title = getString(R.string.SeleccionarCasa);
                break;
            case 2:
                swipeContainer.setVisibility(View.GONE);
                content_frame.setVisibility(View.GONE);
                fragment = new AfiliacionesPendientes();
                title = "Afiliaciones Pendientes";
                break;
            case 3:

                break;
            case 4:

                break;
            case 7:

                break;
            default:
                break;

        }

        if (fragment != null) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }


    private class GETCasas extends AsyncTask<Void, Void, Void> {
        int ErrorCode;
        public GETCasas() {

        }

        @Override
        protected void onPreExecute() {



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


            swipeContainer.setRefreshing(false);
            if(ErrorCode==0){
                CasasCorredorasDao casasCorredorasDao = daoSession.getCasasCorredorasDao();
                CursorCasas = db.query(casasCorredorasDao.getTablename(),casasCorredorasDao.getAllColumns(),null,null,null,null,null);

                if(CursorCasas.moveToFirst()){
                    CasasAdapter = new ListadoCasasAdapter(MenuPrincipal.this,CursorCasas);
                    ListadoCasas.setAdapter(CasasAdapter);
                    ListadoCasas.setVerticalScrollBarEnabled(false);
                }
            }else  {
                new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("Error en Actualizacion")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        }).create().show();
            }


        }

    }
}
