package com.todociber.appbolsadevalores.Home;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.todociber.appbolsadevalores.Afiliaciones.AfiliacionesPendientes;
import com.todociber.appbolsadevalores.CasasCorredoras.CasasCorredoras;
import com.todociber.appbolsadevalores.Home.WS.GETCasasCorredoras;
import com.todociber.appbolsadevalores.Home.adapter.ListadoCasasAdapter;
import com.todociber.appbolsadevalores.NuevaOrden.SeleccionarCasa;
import com.todociber.appbolsadevalores.R;
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
    String afiliacionCode ="";
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
        context = MenuPrincipal.this;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        tokenPushDao = daoSession.getTokenPushDao();
        clienteDao = daoSession.getClienteDao();
        try{
            Bundle extras = MenuPrincipal.this.getIntent().getExtras();
            afiliacionCode = extras.getString("afliacionCode");
        }catch (Exception ignored){

        }
        if (afiliacionCode != null) {
            if(afiliacionCode.equals("2")){
                displayView(2);
            }else{
                displayView(0);
            }
        }else{
            displayView(0);
        }


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
            displayView(0);
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
                fragment = new CasasCorredoras();
                title = "Casas Corredoras";


                break;
            case 1:

                fragment = new SeleccionarCasa();
                title = getString(R.string.SeleccionarCasa);
                break;
            case 2:

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


}
