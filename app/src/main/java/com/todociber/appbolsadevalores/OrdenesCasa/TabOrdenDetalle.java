package com.todociber.appbolsadevalores.OrdenesCasa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

import com.todociber.appbolsadevalores.OrdenesCasa.Adapter.TabOrdenDetalleAdapter;
import com.todociber.appbolsadevalores.R;


/**
 * Created by Todociber on 22/10/2016.
 */
public class TabOrdenDetalle extends ActionBarActivity implements TabListener {
    int posicionCursorCasa;
    private ViewPager viewPager;
    private TabOrdenDetalleAdapter mAdapter;
    private String idCasa;
    private String pantallaMostrar="0";
    private String[] tabs = { "Orden", "Mensajes", "Operaciones" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_principal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        getSupportActionBar().setTitle("Detalle  de orden");
        // Initilization

        Bundle extras = TabOrdenDetalle.this.getIntent().getExtras();
        posicionCursorCasa = extras.getInt("posicionCursor");
        idCasa = extras.getString("idCasa");
        Log.d("posicionCursor",String.valueOf(posicionCursorCasa));
        try{
            pantallaMostrar = extras.getString("tipo");
        }catch (Exception e ){
            pantallaMostrar ="0";
        }
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabOrdenDetalleAdapter(getSupportFragmentManager(),posicionCursorCasa,idCasa);

        viewPager.setAdapter(mAdapter);



        // Adding Tabs
        for (String tab_name : tabs) {
            getSupportActionBar().addTab(
                    getSupportActionBar().newTab().setText(tab_name)
                            .setTabListener(this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if(pantallaMostrar!=null){
            if(pantallaMostrar.equals("1")){
                getSupportActionBar().setSelectedNavigationItem(Integer.valueOf(0));
            }else if(pantallaMostrar.equals("3")){
                getSupportActionBar().setSelectedNavigationItem(Integer.valueOf(1));
            }else if(pantallaMostrar.equals("4")){
                getSupportActionBar().setSelectedNavigationItem(Integer.valueOf(2));
            }

        }
    }


    @Override
    public void onBackPressed (){
        Intent a = new Intent(TabOrdenDetalle.this,OrdenesPorCasa.class);
        a.putExtra("idCasa",idCasa);
        startActivity(a);
        finish();
    }
    @Override
    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub
        viewPager.setCurrentItem(arg0.getPosition());
    }

    @Override
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent a = new Intent(TabOrdenDetalle.this,OrdenesPorCasa.class);
            a.putExtra("idCasa",idCasa);
            startActivity(a);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}