package com.todociber.appbolsadevalores;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.android.gcm.GCMRegistrar;
import com.todociber.appbolsadevalores.login.MainActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private long splashDelay = 2000; //2 segundos

    ProgressBar progressBar;
    private String regId;
    Cursor cursor,cursor1,cursor2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try{
            GCMRegistrar.checkDevice(SplashActivity.this);
            GCMRegistrar.checkManifest(SplashActivity.this);
            regId = GCMRegistrar.getRegistrationId(SplashActivity.this);
            if(regId.equals("")|| regId == null){
                Log.e("", " Registrado TOKEN NULLO O 0");
                GCMRegistrar.register(SplashActivity.this, GCMIntentService.SENDER_ID);
            }else{
                Log.e("", "Registrado");
                GCMRegistrar.register(SplashActivity.this, GCMIntentService.SENDER_ID);

            }
        }catch (Exception e){
            Log.e("", "Registrado Error "+e.toString());
        }





        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, splashDelay);

        try {



            printHashKey(SplashActivity.this);
        } catch (Exception ex) {

        }

    }

    public void printHashKey(Activity context) {


        try {
            PackageInfo info = context.getPackageManager().getPackageInfo("com.gorbin.androidsocialnetworksextended.asne",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("HASH KEY:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }



    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {

        super.onPause();
    }
}
