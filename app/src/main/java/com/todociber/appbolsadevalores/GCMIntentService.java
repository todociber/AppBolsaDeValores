package com.todociber.appbolsadevalores;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.todociber.appbolsadevalores.OrdenesCasa.TabOrdenDetalle;
import com.todociber.appbolsadevalores.OrdenesCasa.WS.GetOrdenesByCasa;
import com.todociber.appbolsadevalores.db.ClienteDao;
import com.todociber.appbolsadevalores.db.DaoMaster;
import com.todociber.appbolsadevalores.db.DaoSession;
import com.todociber.appbolsadevalores.db.OrdenesDao;
import com.todociber.appbolsadevalores.db.TokenPush;
import com.todociber.appbolsadevalores.db.TokenPushDao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by root on 08-22-15.
 */
public class GCMIntentService extends GCMBaseIntentService {

    // Use your PROJECT ID from Google API into SENDER_ID
    //public static final String SENDER_ID = "207590749481";
    public static final String SENDER_ID = "183988954799";
    private static final String TAG = "GCM Tutorial::Service";
    public String iddispositivo;
    //API KEY del proyecto = AIzaSyC4mkkCpUSj2HiUxbYfWsMkxp9txPP1WZ4
    long x = 1;
    String auten;
    String regId;
    Intent intent;
    int idNo;
    PendingIntent pIntent;
    Cursor cursor;
    private GetOrdenesByCasa getOrdenesByCasa;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private TokenPushDao tokenPushDao;
    //DaoAccessPush daoAccessPush;
    private ClienteDao clienteDao;
    private OrdenesDao ordenesDao;
    private Cursor cursorCliente,cursorOrden,cursorOrdenes;
    private Cursor cursor1;
    private int indicator;

    public GCMIntentService() {
        super(SENDER_ID);

    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        Log.i("data---", "" + sha1hash);
        return convertToHex(sha1hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {

        Log.i("GCMIntentService", "Device registered: regId000 = " + registrationId);
        String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println(" id es " +id );
        try {
            auten = SHA1(id);
            Log.i("id", auten+"     |      "+regId);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
            db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
            tokenPushDao = daoSession.getTokenPushDao();
            if(registrationId!=null){
                tokenPushDao.deleteAll();
            }
            TokenPush tokenPush = new TokenPush();
            tokenPush.setTokenPush(registrationId);
            tokenPushDao.insertInTx(tokenPush);
            System.out.println("Registrndo "+registrationId);

        }catch (Exception e){

        }
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "onUnregistered: registrationId=" + registrationId);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onMessage(Context context, Intent data) {
        String titulo = null, tipo="0", idOrden="46",idOrganizacion="2", mensaje;
        mensaje ="";
        // Message from PHP server
        Log.d("data",data.toString());
        if(data.hasExtra("mensaje")){
            mensaje = data.getStringExtra("mensaje");
        }
        if(data.hasExtra("tipo")){
            tipo   = data.getStringExtra("tipo");
        }if(data.hasExtra("idOrden")){
            idOrden = data.getStringExtra("idOrden");
        }if(data.hasExtra("idOrganizacion")){
            idOrganizacion = data.getStringExtra("idOrganizacion");
        }

        Log.d("mensaje",mensaje);
        Log.d("tipo",tipo);
        Log.d("idOrden",idOrden);
        Log.d("idOrganizacion",idOrganizacion);


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "bolsaDeValoresSV", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        tokenPushDao = daoSession.getTokenPushDao();
        clienteDao = daoSession.getClienteDao();

        ordenesDao = daoSession.getOrdenesDao();
        cursorCliente = db.query(clienteDao.getTablename(),clienteDao.getAllColumns(),null,null,null,null,null);


        if(tipo.equals("1")||tipo.equals("3")||tipo.equals("4")){
            if(cursorCliente.moveToFirst()){
                new getOrdenes(context,cursorCliente.getString(9),cursorCliente.getString(5),idOrganizacion,mensaje,idOrden, tipo).execute();
            }

        }



        //Timestamp para el id cliente
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();


        {
            // Wake Android Device when notification received
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            final PowerManager.WakeLock mWakelock = pm.newWakeLock(
                    PowerManager.FULL_WAKE_LOCK
                            | PowerManager.ACQUIRE_CAUSES_WAKEUP, "GCM_PUSH");
            mWakelock.acquire();

            // Timer before putting Android Device to sleep mode.
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    mWakelock.release();
                }
            };
            timer.schedule(task, 5000);
        }

    }

    @Override
    protected void onError(Context arg0, String errorId) {
        Log.e(TAG, "onError: errorId=" + errorId);
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.home : R.drawable.home;
    }

    private class PutDataTask extends AsyncTask<Void, Void, Void> {

        int resultado;
        String token, deviceId;
        Context context;

        public PutDataTask(Context context, String token, String deviceId) {
            this.token = token;
            this.context = context;
            this.deviceId = deviceId;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... arg0) { // TODO
            //   Auto - generated method stub;


            String id = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (resultado == 1) {
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);


                startActivity(i);

            } else if (resultado == 2) {

            } else {

            }
        }

    }

    private class getOrdenes extends AsyncTask<Void, Void, Void> {
        int ErrorCode;
        int a ;
        String tipo;
        int posicionCursor;
        String tokenSession;
        String idCliente;
        String idOrganizacion;
        Context context;
        String mensaje;
        String idOrden;
        public getOrdenes(Context context,String tokenSession,String idCliente,String idOrganizacion,String mensaje,String idOrden, String tipo) {
            this.context = context;
            this.tokenSession = tokenSession;
            this.idOrganizacion = idOrganizacion;
            this.idCliente = idCliente;
            this.mensaje = mensaje;
            this.idOrden = idOrden;
            this.tipo = tipo;
        }

        @Override
        protected void onPreExecute() {



        }

        @Override
        protected Void doInBackground(Void... arg0) {
            getOrdenesByCasa = new GetOrdenesByCasa(context,tokenSession,idCliente,idOrganizacion);
            ErrorCode = getOrdenesByCasa.erroCode;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

           cursorOrdenes = db.query(ordenesDao.getTablename(),ordenesDao.getAllColumns(),null,null,null,null,null);

                int a =0;
                while (cursorOrdenes.moveToNext()){

                    if(cursorOrdenes.getString(1).equals(idOrden)){

                        posicionCursor = a;

                    }else{

                    }
                    a++;
                }



            intent = new Intent(context, TabOrdenDetalle.class);
            intent.putExtra("posicionCursor", posicionCursor);
            intent.putExtra("idCasa", idOrganizacion);
            intent.putExtra("tipo", tipo);
            Log.d("largoCursor",String.valueOf(cursorOrdenes.getCount()));
            Log.d("posicionCursor",String.valueOf(posicionCursor));
            Log.d("idCasa",idOrganizacion);
            Log.d("tipo",tipo);

            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(getNotificationIcon()).setTicker("Bolsa de Valores")
                    .setWhen(System.currentTimeMillis()).setContentTitle("Bolsa de Valores")
                    .setContentText(mensaje)
                    .setContentIntent(pIntent)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(""))
                    .getNotification();
            // Remove the notification on click
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.PRIORITY_MAX;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(0, notification);



        }

    }

}
