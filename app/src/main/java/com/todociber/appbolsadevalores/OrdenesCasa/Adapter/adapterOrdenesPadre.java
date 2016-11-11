package com.todociber.appbolsadevalores.OrdenesCasa.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.todociber.appbolsadevalores.R;

/**
 * Created by Todociber on 23/10/2016.
 */
public class adapterOrdenesPadre extends BaseAdapter {

    private final Context context;
    Cursor cursorDetalleOrden;
    private LayoutInflater inflater;
    private String estadoOrden,tipoOrden;
    private TextView NombreCasaCorredora,txtCorrelativo,txtFechaDeVigencia,
            txtAgenteCorredor,txtTipoDeOrden,txtTituloNombre,txtValorMinimo,
            txtValorMaximo,txtMontoDeInversion,txtTasaDeInversion,txtComision,
            txtCuentaCedeval,txtEmisor,txtTipoMercado,txtEstadoOrden;
    public adapterOrdenesPadre(Context context, Cursor cursor) {

        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.cursorDetalleOrden = cursor;
    }



    @Override
    public int getCount() {
        return cursorDetalleOrden.getCount();
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
            view = inflater.inflate(R.layout.detalle_listado_ordenes_padre, null);
            holder = new ViewHolder();
            holder.NombreCasaCorredora = (TextView)view.findViewById(R.id.NombreCasaCorredora);
            holder.txtCorrelativo = (TextView) view.findViewById(R.id.txtCorrelativo);
            holder.txtFechaDeVigencia = (TextView) view.findViewById(R.id.txtFechaDeVigencia);
            holder.txtAgenteCorredor = (TextView) view.findViewById(R.id.txtAgenteCorredor);
            holder.txtTipoDeOrden = (TextView) view.findViewById(R.id.txtTipoDeOrden);
            holder.txtTituloNombre = (TextView) view.findViewById(R.id.txtTituloNombre);
            holder.txtValorMinimo = (TextView) view.findViewById(R.id.txtValorMinimo);
            holder.txtValorMaximo = (TextView) view.findViewById(R.id.txtValorMaximo);
            holder.txtMontoDeInversion = (TextView) view.findViewById(R.id.txtMontoDeInversion);
            holder.txtTasaDeInversion = (TextView) view.findViewById(R.id.txtTasaDeInversion);
            holder.txtComision = (TextView) view.findViewById(R.id.txtComision);
            holder.txtCuentaCedeval = (TextView) view.findViewById(R.id.txtCuentaCedeval);
            holder.txtEmisor = (TextView) view.findViewById(R.id.txtEmisor);
            holder.txtTipoMercado = (TextView) view.findViewById(R.id.txtTipoMercado);
            holder.txtEstadoOrden = (TextView) view.findViewById(R.id.txtEstadoOrden);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        cursorDetalleOrden.moveToPosition(position);
        holder.NombreCasaCorredora.setText(cursorDetalleOrden.getString(12));

        Log.d("Correlativo ",cursorDetalleOrden.getString(2));
        holder.txtCorrelativo.setText(cursorDetalleOrden.getString(2));
        holder.txtFechaDeVigencia.setText(cursorDetalleOrden.getString(3));
        holder.txtAgenteCorredor.setText(cursorDetalleOrden.getString(4));
        if(cursorDetalleOrden.getString(5).equals("1")){
            tipoOrden = "Compra";
        }else if(cursorDetalleOrden.getString(5).equals("2")){
            tipoOrden = "Venta";
        }
        holder.txtTipoDeOrden.setText(tipoOrden);
        holder.txtTituloNombre.setText(cursorDetalleOrden.getString(8));
        holder.txtValorMinimo.setText(cursorDetalleOrden.getString(10));
        holder.txtValorMaximo.setText(cursorDetalleOrden.getString(11));
        holder.txtMontoDeInversion.setText(cursorDetalleOrden.getString(15));
        holder.txtTasaDeInversion.setText(cursorDetalleOrden.getString(16));
        holder.txtComision.setText(cursorDetalleOrden.getString(17));
        holder.txtCuentaCedeval.setText(cursorDetalleOrden.getString(18));
        holder.txtEmisor.setText(cursorDetalleOrden.getString(19));
        holder.txtTipoMercado.setText(cursorDetalleOrden.getString(20));
        holder.txtEstadoOrden.setText(setearEstadoOrden(cursorDetalleOrden.getString(7)));
        Log.d("ESTADOORDEN ",setearEstadoOrden(cursorDetalleOrden.getString(7)));
        return view;
    }
    public String setearEstadoOrden(String estadoOrdenR){
        if(estadoOrdenR.equals("1")){
            estadoOrden = "Pre-Vigente";
        }else if(estadoOrdenR.equals("2")){
            estadoOrden = "Vigente";
        }else if(estadoOrdenR.equals("3")){
            estadoOrden = "Cancelada";
        }else if(estadoOrdenR.equals("4")){
            estadoOrden = "Modificada";
        }else if(estadoOrdenR.equals("5")){
            estadoOrden = "Ejecutada";
        }else if(estadoOrdenR.equals("6")){
            estadoOrden = "Finalizada";
        }else if(estadoOrdenR.equals("7")){
            estadoOrden = "Vencida";
        }else if(estadoOrdenR.equals("8")){
            estadoOrden = "Rechazada";
        }
        return estadoOrden;
    }

    private class ViewHolder {
        public TextView NombreCasaCorredora,txtCorrelativo,txtFechaDeVigencia,
        txtAgenteCorredor,txtTipoDeOrden,txtTituloNombre,txtValorMinimo,txtValorMaximo,txtMontoDeInversion,
                txtTasaDeInversion,txtComision,txtCuentaCedeval,txtEmisor,txtTipoMercado,txtEstadoOrden;

    }
}
