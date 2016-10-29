package com.todociber.appbolsadevalores.login.WS;

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
 * Created by Todociber on 16/10/2016.
 */
public class POSTLogin {


    public int  error,erroCode;
    public String Email,Password,TokenPush;
    public Context context;
    BufferedReader in = null;

    public POSTLogin(String Email, String Password, String TokenPush,Context context){
        this.Email = Email;
        this.Password = Password;
        this.TokenPush = TokenPush;
        this.context = context;

    }

    public int Loguear(){

        try {
            String urlBase = context.getResources().getString(R.string.urlSistemaASI);
            String  url = urlBase+"api/login";
             JSONObject jsonObj = new JSONObject();
            jsonObj.put("email"	, Email);
            jsonObj.put("password", Password);
            jsonObj.put("TokenPush",TokenPush);

            System.out.println("TOKENPUSH ENVIADO "+TokenPush);
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
            Log.d("json",jsonObject.toString());
            error = jsonObject.getInt("ErrorCode");
            if (error ==0){
               ProcesarJsonLogin procesarJsonLogin = new ProcesarJsonLogin(jsonObject,context);
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
