package com.todociber.appbolsadevalores.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.todociber.appbolsadevalores.Home.MenuPrincipal;
import com.todociber.appbolsadevalores.R;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.TokenPushDao;
import com.todociber.appbolsadevalores.login.WS.POSTLogin;

public class MainActivity extends AppCompatActivity {
    Button BtnIniciarSession;
    Cursor cursorTokenPush, cursorUsuarioLogueado;
    EditText TxtEmail,TxtPassword;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private TokenPushDao tokenPushDao;
    private ClienteDao clienteDao;
    public ProgressDialog loading;
    public String Email, Password, TokenPushG;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TxtEmail = (EditText)findViewById(R.id.TxtEmail);
        TxtPassword =(EditText) findViewById(R.id.Txtpassword);
        BtnIniciarSession = (Button) findViewById(R.id.btnIniciarSession);
        context = MainActivity.this;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        tokenPushDao = daoSession.getTokenPushDao();
        clienteDao = daoSession.getClienteDao();

        cursorTokenPush = db.query(tokenPushDao.getTablename(), tokenPushDao.getAllColumns(), null, null, null, null,null);
        cursorUsuarioLogueado = db.query(clienteDao.getTablename(),clienteDao.getAllColumns(),null,null,null,null,null);
        if(cursorUsuarioLogueado.getCount()>0){
            Intent a = new Intent(context, MenuPrincipal.class);
            startActivity(a);
            finish();
        }

        BtnIniciarSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(cursorTokenPush.moveToFirst()){
                   Email = TxtEmail.getText().toString();
                   Password = TxtPassword.getText().toString();

                    if(Password.length()==0){
                        new AlertDialog.Builder(context)
                                .setTitle("Error")
                                .setMessage("Ingrese un Password")
                                .setCancelable(false)
                                .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create().show();
                    }else{
                        if(Email.trim().matches("[a-zA-Z0-9._-]+@[a-z._-]+\\.+[a-z]+")) {
                            TokenPushG = cursorTokenPush.getString(1);
                            new PostLogin().execute();
                        }else {
                            new AlertDialog.Builder(context)
                                    .setTitle("Error")
                                    .setMessage("Formato de email incorrecto")
                                    .setCancelable(false)
                                    .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();
                        }
                    }



               }

            }
        });
    }

    private class PostLogin extends AsyncTask<Void, Void, Void> {
        int ErrorCode;
        public PostLogin() {

        }

        @Override
        protected void onPreExecute() {

                try {
                    loading = new ProgressDialog(context);
                    loading.setMessage("Cargando");
                    loading.setCancelable(false);
                    loading.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            POSTLogin postLogin = new POSTLogin(Email,Password,TokenPushG,context);
            ErrorCode = postLogin.Loguear();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


                loading.dismiss();
            if(ErrorCode==0){
                Intent a = new Intent(context, MenuPrincipal.class);
                startActivity(a);
                finish();
            }else  {
                  new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("Usuario o Contraseña incorrectos")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }


        }

    }
}
