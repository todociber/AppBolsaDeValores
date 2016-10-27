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
 * Created by Todociber on 23/10/2016.
 */
public class adapterOrdenesPadre extends BaseAdapter {

    private LayoutInflater inflater;
    private final Context context;
    Cursor cursorDetalleOrden;
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
            NombreCasaCorredora = (TextView)view.findViewById(R.id.NombreCasaCorredora);
            txtCorrelativo = (TextView) view.findViewById(R.id.txtCorrelativo);
            txtFechaDeVigencia = (TextView) view.findViewById(R.id.txtFechaDeVigencia);
            txtAgenteCorredor = (TextView) view.findViewById(R.id.txtAgenteCorredor);
            txtTipoDeOrden = (TextView) view.findViewById(R.id.txtTipoDeOrden);
            txtTituloNombre = (TextView) view.findViewById(R.id.txtTituloNombre);
            txtValorMinimo = (TextView) view.findViewById(R.id.txtValorMinimo);
            txtValorMaximo = (TextView) view.findViewById(R.id.txtValorMaximo);
            txtMontoDeInversion = (TextView) view.findViewById(R.id.txtMontoDeInversion);
            txtTasaDeInversion = (TextView) view.findViewById(R.id.txtTasaDeInversion);
            txtComision = (TextView) view.findViewById(R.id.txtComision);
            txtCuentaCedeval = (TextView) view.findViewById(R.id.txtCuentaCedeval);
            txtEmisor = (TextView) view.findViewById(R.id.txtEmisor);
            txtTipoMercado = (TextView) view.findViewById(R.id.txtTipoMercado);
            txtEstadoOrden = (TextView) view.findViewById(R.id.txtEstadoOrden);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        cursorDetalleOrden.moveToPosition(position);
        NombreCasaCorredora.setText(cursorDetalleOrden.getString(12));
        txtCorrelativo.setText(cursorDetalleOrden.getString(2));
        txtFechaDeVigencia.setText(cursorDetalleOrden.getString(3));
        txtAgenteCorredor.setText(cursorDetalleOrden.getString(4));
        if(cursorDetalleOrden.getString(5).equals("1")){
            tipoOrden = "Compra";
        }else if(cursorDetalleOrden.getString(5).equals("2")){
            tipoOrden = "Venta";
        }
        txtTipoDeOrden.setText(tipoOrden);
        txtTituloNombre.setText(cursorDetalleOrden.getString(8));
        txtValorMinimo.setText(cursorDetalleOrden.getString(10));
        txtValorMaximo.setText(cursorDetalleOrden.getString(11));
        txtMontoDeInversion.setText(cursorDetalleOrden.getString(15));
        txtTasaDeInversion.setText(cursorDetalleOrden.getString(16));
        txtComision.setText(cursorDetalleOrden.getString(17));
        txtCuentaCedeval.setText(cursorDetalleOrden.getString(18));
        txtEmisor.setText(cursorDetalleOrden.getString(19));
        txtTipoMercado.setText(cursorDetalleOrden.getString(20));
        txtEstadoOrden.setText(setearEstadoOrden(cursorDetalleOrden.getString(7)));
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
        public TextView text;

    }
}
