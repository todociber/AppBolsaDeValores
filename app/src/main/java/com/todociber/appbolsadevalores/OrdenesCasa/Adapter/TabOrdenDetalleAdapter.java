package com.todociber.appbolsadevalores.OrdenesCasa.Adapter;

/**
 * Created by Todociber on 22/10/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.todociber.appbolsadevalores.OrdenesCasa.fragments.DetalleMensajes;
import com.todociber.appbolsadevalores.OrdenesCasa.fragments.DetalleOrden;
import com.todociber.appbolsadevalores.OrdenesCasa.fragments.OperacionesDeBolsa;

public class TabOrdenDetalleAdapter extends FragmentStatePagerAdapter  {
    private int parametro;
    private String idCasa;
    public TabOrdenDetalleAdapter(FragmentManager fm,int parametro,String idCasa) {
        super(fm);
        this.parametro = parametro;
        this.idCasa = idCasa;

    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
               return new DetalleOrden().newInstance(parametro,idCasa);
            case 1:
                // Games fragment activity
                return  new DetalleMensajes().newInstance(parametro,idCasa);
            case 2:
                // Movies fragment activity
                return new OperacionesDeBolsa().newInstance(parametro,idCasa);
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
}