package com.todociber.appbolsadevalores.Afiliaciones.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.todociber.appbolsadevalores.R;

/**
 * Created by Todociber on 28/10/2016.
 */
public class AdapterAfiliaciones extends BaseAdapter {

    private final Context context;
    Cursor cursor;
    private LayoutInflater inflater;


    public AdapterAfiliaciones(Context context, Cursor cursor) {

        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.cursor = cursor;
    }



    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (convertView == null) {
            view = inflater.inflate(R.layout.detalle_listado_afiliaciones, null);
            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.CasaNombre);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        cursor.moveToPosition(position);
        String estado ;
        if(cursor.getString(3).equals("5")){
            estado ="En revision";
        }else{
            estado = "En Proceso";
        }
        String NombreOrden = "Numero de Afiliacion: "+cursor.getString(1)+"\n"
                +"Casa: "+"\n"+cursor.getString(2)+"\n"+"Estado: "+estado;
        holder.text.setText(NombreOrden);
        return view;
    }


    private class ViewHolder {
        public TextView text;

    }
}
