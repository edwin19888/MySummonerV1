package com.webin.mysummonerv1;


import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.webin.mysummonerv1.request.ApiRequest;

public class PrincipalActivity extends AppCompatActivity implements AlertDialogRadio.AlertPositiveListener,AlertDialogRadio.AlertNegativeListener {

    EditText editTextSummoner;
    ImageView imageViewChampion,imageViewSetting,imageViewSearchUser;

    int position = 0;
    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;
    String usuario = null;

    private RequestQueue queue;
    private ApiRequest request;
    String[] summoner = new String[1];


    @Override
    public void onCreate(Bundle savedInstanceState) {

        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        int mostrarServer = prefs.getInt("mostrarServer", 0);
        String serverName = prefs.getString("serverName","Korea");
        String plataforma = prefs.getString("plataforma","kr");
        int idServer = prefs.getInt("idServer",0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//no girar activity

        queue = MySingleton.getInstance(this).getRequestQueue();
        request = new ApiRequest(queue,this,plataforma);

        imageViewChampion = (ImageView) findViewById(R.id.ivChampions);
        imageViewChampion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PrincipalActivity.this,ChampionsActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        imageViewSetting = (ImageView) findViewById(R.id.ivSettings);
        imageViewSetting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(PrincipalActivity.this,SettingsActivity.class);
                startActivity(s);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        if (mostrarServer == 0) {
            openDialogServer();
        }



        editTextSummoner = (EditText) findViewById(R.id.etSummoner);


        editTextSummoner.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if  ((actionId == EditorInfo.IME_ACTION_SEARCH)) {


                    summoner[0] = editTextSummoner.getText().toString();
                    if(summoner[0].length() > 0){
                        usuario = summoner[0];

                        VolleyCheckUser(usuario,request);

                    }else{
                        Mensaje("Debe ingresar un usuario");
                    }


                }
                return false;
            }
        });

        imageViewSearchUser = (ImageView) findViewById(R.id.ImageViewSearchUser);
        imageViewSearchUser.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = editTextSummoner.getText().toString();
                if(usuario != null){
                    VolleyCheckUser(usuario,request);
                }
                else{
                    Mensaje("Debe ingresar un usuario");
                }
            }
        });


    }

    public void Mensaje(String msj){
        Toast.makeText(PrincipalActivity.this,""+ msj,Toast.LENGTH_LONG).show();
    }


    public void openDialogServer() {
        /** Getting the fragment manager */
        FragmentManager manager = getFragmentManager();

        /** Instantiating the DialogFragment class */
        AlertDialogRadio alert = new AlertDialogRadio();

        /** Creating a bundle object to store the selected item's index */
        Bundle b  = new Bundle();

        /** Storing the selected item's index in the bundle object */
        b.putInt("position", position);

        /** Setting the bundle object to the dialog fragment object */
        alert.setArguments(b);

        /** Creating the dialog fragment object, which will in turn open the alert dialog window */
        alert.show(manager, "alert_dialog_radio");
    }


    @Override
    public void onPositiveClick(int position) {
        this.position = position;
        String plataforma;

        /** Getting the reference of the textview from the main layout */
        String serverSelection = Android.code[this.position];
        switch (this.position){
            case 0:
                plataforma = "kr";
                break;
            case 1:
                plataforma = "la1";
                break;
            case 2:
                plataforma = "la2";
                break;
            case 3:
                plataforma = "br1";
                break;
            case 4:
                plataforma = "na1";
                break;
            case 5:
                plataforma = "tr1";
                break;
            case 6:
                plataforma = "eun1";
                break;
            default:
                plataforma = "kr";
        }

        //Toast.makeText(PrincipalActivity.this,"Check: "+Android.code[this.position],Toast.LENGTH_SHORT).show();
        /** Setting the selected android version in the textview */
        //tv.setText("Your Choice : " + Android.code[this.position]);

        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("serverName", serverSelection);
        editor.putInt("mostrarServer", 1);
        editor.putInt("idServer",position);
        editor.putString("plataforma",plataforma);
        editor.commit();
    }

    @Override
    public void onNegativeClick(int position){
        this.position = 0;
        String plataforma = "kr";

        Toast.makeText(PrincipalActivity.this,"Check: "+Android.code[this.position],Toast.LENGTH_SHORT).show();
        /** Setting the selected android version in the textview */
        //tv.setText("Your Choice : " + Android.code[this.position]);
        String serverSelection = "Korea";
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("serverName", serverSelection);
        editor.putInt("mostrarServer", 1);
        editor.putInt("idServer",position);
        editor.putString("kr",plataforma);
        editor.commit();
    }

    @Override
    public void onBackPressed(){
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
            return;
        }else {
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
    }

    public void VolleyCheckUser(String usuario, ApiRequest request){

        request.checkPlayerName(usuario, new ApiRequest.CheckPlayerCallback() {
            @Override
            public void onSuccess(String name,long accountId, long id) {

                Intent intent = new Intent(PrincipalActivity.this,MatchesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("USUARIO",name);
                bundle.putLong("ACCOUNTID",accountId);
                bundle.putLong("ID",id);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                finish();
            }

            @Override
            public void dontExist(String message) {
                Mensaje(message);
            }

            @Override
            public void onError(String message) {

                String msg;

                if(message.equals("AuthFailureError")){
                    msg = "Error en la petición";
                }else if(message.equals("NetworkError")){
                    msg = "Verifique su conexión a Internet";
                }else if(message.equals("NoConnectionError")){
                    msg = "Error en la conexión";
                }else if(message.equals("ParseError")){
                    msg = "Error al procesar información";
                }else if(message.equals("RedirectError")){
                    msg = "Error en respuesta del servidor";
                }else if(message.equals("ServerError")){
                    msg = "Usuario no existe en esta región";
                }else if(message.equals("TimeoutError")){
                    msg = "Tiempo de espera agotado";
                }else {
                    msg = "Error desconocido";
                }

                Log.d("APP ERROR : ",message);
                Mensaje(msg);
            }

            @Override
            public void dontExistSummoner(String message) {
                Mensaje(message);
            }

            @Override
            public void errorUnknown(String message) {
                Mensaje(message);
            }
        });
    }
}
