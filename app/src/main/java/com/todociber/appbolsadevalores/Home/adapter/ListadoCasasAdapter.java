package com.todociber.appbolsadevalores.Home.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.todociber.appbolsadevalores.R;

/**
 * Created by Todociber on 17/10/2016.
 */
public class ListadoCasasAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private final Context context;
    Cursor cursor;


    public ListadoCasasAdapter(Context context, Cursor cursor) {

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
            view = inflater.inflate(R.layout.detalle_listado_casas, null);
            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.CasaNombre);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        cursor.moveToPosition(position);
        holder.text.setText(cursor.getString(2));
        return view;
    }


    private class ViewHolder {
        public TextView text;

    }
}
