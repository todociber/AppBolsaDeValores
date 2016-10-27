package com.todociber.appbolsadevalores.OrdenesCasa.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.todociber.appbolsadevalores.R;

/**
 * Created by Todociber on 22/10/2016.
 */
public class AdapterMensajes extends BaseAdapter {

    private LayoutInflater inflater;
    private final Context context;
    Cursor cursor;
    String idUsuario,TipoMensaje;

    public AdapterMensajes(Context context, Cursor cursor,String idUsuario) {

        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.cursor = cursor;
        this.idUsuario = idUsuario;
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
            view = inflater.inflate(R.layout.detalle_mensajes_para_listview, null);
            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.txtMEnsaje);
            holder.textNombre =(TextView) view.findViewById(R.id.txtNombreUsuario);
            holder.TextTipo = (TextView) view.findViewById(R.id.txtTipoMensaje);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        cursor.moveToPosition(position);
        String NombreOrden = cursor.getString(5);
        if(cursor.getString(3).equals(idUsuario)){
            NombreOrden = "YO";
        }

        holder.textNombre.setText(cursor.getString(4));
        if(cursor.getString(2).equals("1")){
            TipoMensaje = "General";
        }else {
            TipoMensaje = "Motivo de Rechazo";
        }
        holder.TextTipo.setText(TipoMensaje);
        holder.text.setText(NombreOrden);
        return view;
    }


    private class ViewHolder {
        public TextView text,textNombre,TextTipo;

    }
}