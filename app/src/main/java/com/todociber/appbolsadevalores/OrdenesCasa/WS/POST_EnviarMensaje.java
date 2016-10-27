package com.todociber.appbolsadevalores.OrdenesCasa.WS;

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
public class POST_EnviarMensaje {


    BufferedReader in = null;

    public int error, erroCode;
    public String idCliente, idUsuario,idOrden,comentario,nombreCliente,apellidoCliente,tokenPush;
    public Context context;

    public POST_EnviarMensaje(String idCliente,String idUsuario, String idOrden,String comentario,String nombreCliente,String apellidCliente,String tokenPush, Context context) {
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.idOrden = idOrden;
        this.comentario = comentario;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidCliente;
        this.tokenPush = tokenPush;
        this.context = context;
        Enviar();
    }

    public int Enviar() {

        try {
            String urlBase = context.getResources().getString(R.string.urlSistemaASI);
            String url = urlBase + "api/makemessage";
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("idCliente", idCliente);
            jsonObj.put("idUsuario", idUsuario);
            jsonObj.put("idOrden", idOrden);
            jsonObj.put("comentario", comentario);
            jsonObj.put("nombreCliente", nombreCliente);
            jsonObj.put("apellidoCliente", apellidoCliente);
            jsonObj.put("token", tokenPush);

            Log.d("objetoMensaje",jsonObj.toString());


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
            error = jsonObject.getInt("ErrorCode");
            Log.d("JSON RESPUESTA MENSAJE",jsonObject.toString());
            if (error == 0) {

            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block

            error = 1;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            error = 1;
            e.printStackTrace();
        } catch (HttpHostConnectException e) {
            error = 1;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            error = 1;
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            error = 1;
            e.printStackTrace();
        } finally {

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
