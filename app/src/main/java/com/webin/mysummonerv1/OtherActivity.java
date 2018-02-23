package com.webin.mysummonerv1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.webin.mysummonerv1.Clases.Recent;
import com.webin.mysummonerv1.adapter.RecentAdapter;
import com.webin.mysummonerv1.request.ApiRequest;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OtherActivity extends AppCompatActivity{

    private ImageView ibBackActivity;
    private EditText edUserToSearch;
    private RecyclerView rvRecentSearch;
    private TextView tvNotDataRecent;
    String[] summoner = new String[1];
    String plataforma;

    private RequestQueue queue;
    private ApiRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//no girar activity

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String serverName = prefs.getString("serverName","Lationamerica Norte");
        plataforma = prefs.getString("plataforma","la1");
        int idServer = prefs.getInt("idServer",0);

        queue = MySingleton.getInstance(this).getRequestQueue();
        request = new ApiRequest(queue,this,plataforma);

        getSupportActionBar().hide();

        ibBackActivity = (ImageView) findViewById(R.id.ibBackActivity);
        ibBackActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intPrincipal = new Intent(OtherActivity.this,HomeActivity.class);
                startActivity(intPrincipal);
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }
        });

        edUserToSearch = (EditText) findViewById(R.id.edUserToSearch);
        edUserToSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if  ((actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    summoner[0] = edUserToSearch.getText().toString();
                    if(summoner[0].length() > 0){
                        VolleyCheckUser(summoner[0],request);
                    }else{
                        Mensaje("Debe ingresar un usuario");
                    }
                }
                return false;
            }
        });


        tvNotDataRecent = (TextView) findViewById(R.id.tvNotDataRecent);
        rvRecentSearch = (RecyclerView) findViewById(R.id.rvRecentSearch);
        rvRecentSearch.setLayoutManager(new LinearLayoutManager(this));

        ConexionToSQLiteHelper cnx = new ConexionToSQLiteHelper(getApplicationContext(),ConexionToSQLiteHelper.DB_NAME,null,ConexionToSQLiteHelper.v_db);
        SQLiteDatabase db = cnx.getWritableDatabase();

        String username=null,fecha=null,tier,rank,plat,reg;
        int idTable = 0;
        int accountId = 0,idplayer=0,profileIconId=0,summonerLevel=0;

        //Cursor c = db.query("busquedas", campos, "", null, null, null, null);
        Cursor c = db.rawQuery("SELECT id,username,accountId,idplayer,profileIconId,summonerLevel,tier,rank,plataforma,region,MAX(date_insert) FROM busquedas WHERE plataforma='"+plataforma+"' GROUP BY username ORDER BY 11 DESC",new String[]{});

        ArrayList<Recent> recentArrayList = new ArrayList<>();
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                idTable= c.getInt(0);
                username = c.getString(1);
                accountId = c.getInt(2);
                idplayer = c.getInt(3);
                profileIconId = c.getInt(4);
                summonerLevel = c.getInt(5);
                tier = c.getString(6);
                rank = c.getString(7);
                plat = c.getString(8);
                reg = c.getString(9);
                fecha = c.getString(10);
                Recent recent = new Recent(idTable,username,accountId,idplayer,profileIconId,summonerLevel,tier,rank,plat,reg,fecha);
                recentArrayList.add(recent);

                Log.d("SQLite username=",username+"");
                Log.d("SQLite fecha=",fecha+"");
            } while(c.moveToNext());
        }
        db.close();

        if(recentArrayList.size() > 0){
            RecentAdapter adapterRecent = new RecentAdapter(recentArrayList,getApplicationContext());
            rvRecentSearch.setAdapter(adapterRecent);
            rvRecentSearch.setVisibility(View.VISIBLE);
            tvNotDataRecent.setVisibility(View.INVISIBLE);
        }else{
            tvNotDataRecent.setVisibility(View.VISIBLE);
        }

    }

    public void VolleyCheckUser(String usuario, ApiRequest request){

        request.checkPlayerName(usuario, new ApiRequest.CheckPlayerCallback() {
            @Override
            public void onSuccess(String name,long accountId, long id, int profileIconId, long summonerLevel) {

                ConexionToSQLiteHelper cnx = new ConexionToSQLiteHelper(getApplicationContext(),ConexionToSQLiteHelper.DB_NAME,null,ConexionToSQLiteHelper.v_db);
                SQLiteDatabase db = cnx.getWritableDatabase();


                ContentValues nuevo_registro = new ContentValues();
                nuevo_registro.put("username",name);
                nuevo_registro.put("accountId",accountId);
                nuevo_registro.put("idplayer",id);
                nuevo_registro.put("profileIconId",profileIconId);
                nuevo_registro.put("summonerLevel",summonerLevel);
                nuevo_registro.put("plataforma",plataforma);
                db.insert("busquedas",null,nuevo_registro);

                db.close();



                Intent intent = new Intent(OtherActivity.this,MatchesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("USUARIO",name);
                bundle.putLong("ACCOUNTID",accountId);
                bundle.putLong("ID",id);
                bundle.putInt("PROFILEICONID",profileIconId);
                bundle.putLong("SUMMONERLEVEL",summonerLevel);
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

    public void Mensaje(String msj){
        Toast.makeText(OtherActivity.this,""+ msj,Toast.LENGTH_LONG).show();
    }


}
