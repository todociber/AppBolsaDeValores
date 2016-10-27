package com.todociber.appbolsadevalores.NuevaOrden.WS;

import android.content.Context;
import android.util.Log;

import com.todociber.appbolsadevalores.NuevaOrden.WS.ProcesarWS.ProcesarGet_CuentasCedevales;
import com.todociber.appbolsadevalores.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Todociber on 21/10/2016.
 */
public class Get_CuentasCedevales {
    BufferedReader in = null;

    public int  error,erroCode,result=2;
    public String Email,Password,TokenPush;
    public Context context;

    public Get_CuentasCedevales(Context context, String idCliente,String TokenSession) {

        String urlBase = context.getResources().getString(R.string.urlSistemaASI);
        String  url = urlBase+"api/getCedevales/"+idCliente+"?token="+TokenSession;

        try {
            HttpClient client = new DefaultHttpClient(timeOut(5000, 5000));

            client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
                    "android");
            HttpGet request = new HttpGet();
            request.setHeader("Content-Type", "application/json; charset=utf-8");
            Log.i("url", "" +url);
            request.setURI(new URI(url));

            HttpResponse response = client.execute(request);
            Log.i("response", "" + response);
            in = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String page = sb.toString();
            Log.i("PAGE", page);
            JSONObject jsonObject = new JSONObject(page);


            int error = jsonObject.getInt("ErrorCode");
            if (error == 0) {

                ProcesarGet_CuentasCedevales procesarGet_cuentasCedevales = new ProcesarGet_CuentasCedevales(jsonObject.getJSONArray("data"),idCliente,context);

                result = 0;
            } else if (error == 1) {
                result = 1;
            } else if (error == 2) {
                result = 2;
            } else if (error == 3) {
                result = 3;
            } else if (error == 4) {
                result = 4;
            } else if (error == 5) {
                result = 5;
            } else if (error == 6) {
                result = 6;
            } else if (error == 7) {
                result = 7;
            } else if (error == 8) {
                result = 8;
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.i("eeeeeeeeeeeeee1", "" + e);
            result = 15;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.i("eeeeeeeeeeeeee2", "" + e);
            result = 15;
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("eeeeeeeeeeeeee3", "" + e);
            result = 15;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("eeeeeeeeeeeeee4", "" + e);
            result = 15;
        } finally {

        }
    }

    public HttpParams timeOut(int timeout, int timesocket) {

        HttpParams httpParameters = new BasicHttpParams();

        int timeoutConnection = timeout;
        HttpConnectionParams.setConnectionTimeout(httpParameters,
                timeoutConnection);

        int timeoutSocket = timesocket;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        return httpParameters;
    }
}
