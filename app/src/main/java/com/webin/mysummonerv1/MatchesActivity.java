package com.webin.mysummonerv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.webin.mysummonerv1.adapter.AdapterLeague;
import com.webin.mysummonerv1.request.ApiRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {

    ArrayList<Matches> listMatches = new ArrayList<>();
    RecyclerView recyclerViewMatches,recyclerViewLeagues;
    private String playerName;
    private Long playerAccountId,playerId;
    private RequestQueue queue;
    private ApiRequest request;
    private Handler handler,handler2;
    private TextView textViewLoading;
    private ImageView imageViewLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int mostrarServer = prefs.getInt("mostrarServer", 0);
        String serverName = prefs.getString("serverName","Korea");
        String plataforma = prefs.getString("plataforma","kr");
        int idServer = prefs.getInt("idServer",0);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//no girar activity

        textViewLoading = (TextView) findViewById(R.id.tvLoading);

        recyclerViewMatches = (RecyclerView) findViewById(R.id.RecyclerViewMatches);
        recyclerViewMatches.setLayoutManager(new LinearLayoutManager(this));

        //recyclerViewLeagues = (RecyclerView) findViewById(R.id.RecyclerViewLeagues);
        //recyclerViewLeagues.setLayoutManager(new LinearLayoutManager(this));

        queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        request = new ApiRequest(queue,this, plataforma);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("USUARIO") != null && bundle.getLong("ACCOUNTID") > 0 && bundle.getLong("ID") > 0){

            playerName = bundle.getString("USUARIO");
            playerAccountId = bundle.getLong("ACCOUNTID");
            playerId = bundle.getLong("ID");

            setTitle(playerName);

        }else{
            //Redireccionar a PirncipalActivity
        }

        /*
        request.getPlayerLeague(playerId, new ApiRequest.CallbackLeague() {
            @Override
            public void onSuccess(ArrayList<Leagues> leaguesArrayList) {

                ArrayList<Leagues> leagues;
                leagues = leaguesArrayList;

                AdapterLeague adapterLeague = new AdapterLeague(leagues,getApplicationContext());
                recyclerViewLeagues.setAdapter(adapterLeague);

                //AdapterHistory adapterHistory = new AdapterHistory(listMatches,getApplicationContext());
                //recyclerViewMatches.setAdapter(adapterHistory);

            }

            @Override
            public void onError(String message) {

            }

            @Override
            public void onUnranked(int largo) {

            }
        });
        */

        request.getHistoryMatchesAccoundId(playerAccountId, new ApiRequest.HistoryCallback() {
            @Override
            public void onSuccess(final List<Long> matchesList) {
                if(matchesList.size() > 0) {

                    ViewRecycler(matchesList,playerId,request);
                    setTitle(playerName);
                }else{
                    setTitle("Not Found");
                }
            }

            @Override
            public void noMatches(String message) {
                Mensaje(message);
                textViewLoading.setText("ERROR FOUND");
                textViewLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String message) {
                textViewLoading.setText("ERROR FOUND");
                textViewLoading.setVisibility(View.VISIBLE);

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
                    msg = "Error en servidor";
                }else if(message.equals("TimeoutError")){
                    msg = "Tiempo de espera agotado";
                }else {
                    msg = "Error desconocido";
                }

                Log.d("APP ERROR : ",message);
                Mensaje(msg);

            }
        });
    }

    public void ViewRecycler(final List<Long> matchesList, final long playerId, final ApiRequest request){

        LlenarViewRecyler(matchesList.get(0),playerId,request);
        LlenarViewRecyler(matchesList.get(1),playerId,request);
        LlenarViewRecyler(matchesList.get(2),playerId,request);
        LlenarViewRecyler(matchesList.get(3),playerId,request);
        LlenarViewRecyler(matchesList.get(4),playerId,request);
        LlenarViewRecyler(matchesList.get(5),playerId,request);
        LlenarViewRecyler(matchesList.get(6),playerId,request);
        LlenarViewRecyler(matchesList.get(7),playerId,request);
        LlenarViewRecyler(matchesList.get(8),playerId,request);
        LlenarViewRecyler(matchesList.get(9),playerId,request);
        textViewLoading.setEnabled(false);
        /*
        LlenarViewRecyler(matchesList.get(10),playerId,request);
        LlenarViewRecyler(matchesList.get(11),playerId,request);
        LlenarViewRecyler(matchesList.get(12),playerId,request);
        LlenarViewRecyler(matchesList.get(13),playerId,request);
        LlenarViewRecyler(matchesList.get(14),playerId,request);
        LlenarViewRecyler(matchesList.get(15),playerId,request);
        LlenarViewRecyler(matchesList.get(16),playerId,request);
        LlenarViewRecyler(matchesList.get(17),playerId,request);
        LlenarViewRecyler(matchesList.get(18),playerId,request);
        LlenarViewRecyler(matchesList.get(19),playerId,request);
        */

    }

    private void LlenarViewRecyler(final Long aLong, final long playerId, final ApiRequest request) {

        request.getHistoryMatchListsByMatchId(aLong, playerId, new ApiRequest.HistoryCallbackMatch() {
                    @Override
                    public void onSuccess(Matches matches) {

                        listMatches.add(matches);
                        Collections.sort(listMatches);
                        AdapterHistory adapterHistory = new AdapterHistory(listMatches,getApplicationContext());
                        recyclerViewMatches.setAdapter(adapterHistory);

                    }

                    @Override
                    public void onError(String message) {
                        textViewLoading.setText("ERROR FOUND");
                        textViewLoading.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void noMatch(String message) {
                        textViewLoading.setText("ERROR FOUND");
                        textViewLoading.setVisibility(View.VISIBLE);
                    }
                });



    }

    public void Mensaje(String msj){
        Toast.makeText(MatchesActivity.this,""+ msj,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed(){
        finish();
        Intent intPrincipal = new Intent(MatchesActivity.this,PrincipalActivity.class);
        startActivity(intPrincipal);
    }
}
