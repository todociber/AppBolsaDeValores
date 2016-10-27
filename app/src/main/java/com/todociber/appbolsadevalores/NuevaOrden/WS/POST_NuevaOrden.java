package com.todociber.appbolsadevalores.NuevaOrden.WS;

import android.content.Context;
import android.util.Log;

import com.todociber.appbolsadevalores.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Todociber on 22/10/2016.
 */
public class POST_NuevaOrden {


    BufferedReader in = null;
    public String msg;
    public int  error,erroCode;
    public String token,cuentacedeval,casacorredora,idCliente,tipodeorden,nombreCliente,mercado,titulo,emisor
            ,valorMaximo,valorMinimo,monto,FechaDeVigencia,tasaDeInteres;
    public Context context;

    public POST_NuevaOrden(String cuentacedeval,String casacorredora,String idCliente,String tipodeorden,
                           String nombreCliente,String mercado,String titulo,String emisor,String valorMaximo
                           ,String valorMinimo,String monto,String FechaDeVigencia,String tasaDeInteres,
                           String token,Context context){
        this.cuentacedeval = cuentacedeval;
        this.casacorredora = casacorredora;
        this.idCliente = idCliente;
        this.tipodeorden = tipodeorden;
        this.nombreCliente = nombreCliente;
        this.mercado = mercado;
        this.titulo = titulo;
        this.emisor = emisor;
        this.valorMaximo = valorMaximo;
        this.valorMinimo = valorMinimo;
        this.monto = monto;
        this.FechaDeVigencia = FechaDeVigencia;
        this.tasaDeInteres = tasaDeInteres;
        this.token = token;
        this.context = context;
        CrearOrden();
    }

    public int CrearOrden(){

        try {
            String urlBase = context.getResources().getString(R.string.urlSistemaASI);
            String  url = urlBase+"api/makeOrder";
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("cuentacedeval",cuentacedeval);
            jsonObj.put("casacorredora",casacorredora);
            jsonObj.put("idCliente",idCliente);
            jsonObj.put("tipodeorden",tipodeorden);
            jsonObj.put("nombreCliente",nombreCliente);
            jsonObj.put("mercado",mercado);
            jsonObj.put("titulo",titulo);
            jsonObj.put("emisor",emisor);
            jsonObj.put("valorMaximo",valorMaximo);
            jsonObj.put("valorMinimo",valorMinimo);
            jsonObj.put("monto",monto);
            jsonObj.put("FechaDeVigencia",FechaDeVigencia);
            jsonObj.put("tasaDeInteres",tasaDeInteres);
            jsonObj.put("token",token);
            Log.d("JSON PUT ",jsonObj.toString());

            // Create the POST object and add the parameters
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity;
            entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            HttpClient client = new DefaultHttpClient(timeOut(30000, 30000));
            HttpResponse response = client.execute(httpPost);
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";

            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String page = sb.toString();
            JSONObject jsonObject = new JSONObject(page);
            Log.d("EROR CODE",jsonObject.toString());
            error = jsonObject.getInt("ErrorCode");
            msg = jsonObject.getString("msg");
            if (error ==0){

            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block

            error = 1;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            error = 1;
            e.printStackTrace();
        } catch (HttpHostConnectException e){
            error = 1;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            error = 1;
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            error = 1;
            e.printStackTrace();
        } finally{

        }
        return error;
    }

    public HttpParams timeOut(int timeout, int timesocket) {
        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = timeout;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = timesocket;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        return httpParameters;
    }
}
