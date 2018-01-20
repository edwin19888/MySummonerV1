package com.webin.mysummonerv1;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.webin.mysummonerv1.request.ApiRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {

    ArrayList<Matches> listMatches = new ArrayList<>();
    RecyclerView recyclerViewMatches;
    private String playerName;
    private Long playerAccountId,playerId;
    private RequestQueue queue;
    private ApiRequest request;
    private Handler handler,handler2;
    private ProgressBar progressBarLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//no girar activity

        recyclerViewMatches = (RecyclerView) findViewById(R.id.RecyclerViewMatches);
        recyclerViewMatches.setLayoutManager(new LinearLayoutManager(this));

        queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        request = new ApiRequest(queue,this);

        progressBarLoader = (ProgressBar) findViewById(R.id.pb_Matches);
        progressBarLoader.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("USUARIO") != null && bundle.getLong("ACCOUNTID") > 0 && bundle.getLong("ID") > 0){

            playerName = bundle.getString("USUARIO");
            playerAccountId = bundle.getLong("ACCOUNTID");
            playerId = bundle.getLong("ID");

            setTitle(playerName);

        }else{
            //Redireccionar a PirncipalActivity
        }

        handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
          public void run() {
                request.getHistoryMatchesAccoundId(playerAccountId, new ApiRequest.HistoryCallback() {
                    @Override
                    public void onSuccess(final List<Long> matchesList) {

                        if(matchesList.size() > 0) {

                            ViewRecycler(matchesList,playerId,request);

                            //handler = new Handler();
                            //handler.postDelayed(new Runnable() {
                            //    @Override
                            //    public void run() {



                            //    }
                            //},10);

                            setTitle(playerName);
                        }else{
                            setTitle("Not Found");
                        }

                    }

                    @Override
                    public void noMatches(String message) {
                        Mensaje(message);
                        progressBarLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(String message) {
                        Mensaje(message);
                        progressBarLoader.setVisibility(View.GONE);
                    }
                });


            }
        }, 1000);

        progressBarLoader.setVisibility(View.INVISIBLE);
        //listMatches = new ArrayList<>();
        //LlenarMatches();
        //Matches matches1 = new Matches(true,"Demo","Brand.png");
        //listMatches.add(matches1);

        //AdapterHistory adapterHistory = new AdapterHistory(listMatches,getApplicationContext());
        //recyclerViewMatches.setAdapter(adapterHistory);

    }

    public void ViewRecycler(final List<Long> matchesList, final long playerId, final ApiRequest request){

        progressBarLoader.setVisibility(View.VISIBLE);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                request.getHistoryMatchListsByMatchId(matchesList.get(0), playerId, new ApiRequest.HistoryCallbackMatch() {
                    @Override
                    public void onSuccess(Matches matches) {
                        listMatches.add(matches);
                    }

                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void noMatch(String message) {

                    }
                });


                request.getHistoryMatchListsByMatchId(matchesList.get(1), playerId, new ApiRequest.HistoryCallbackMatch() {
                    @Override
                    public void onSuccess(Matches matches) {
                        listMatches.add(matches);
                    }

                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void noMatch(String message) {

                    }
                });

                request.getHistoryMatchListsByMatchId(matchesList.get(2), playerId, new ApiRequest.HistoryCallbackMatch() {
                    @Override
                    public void onSuccess(Matches matches) {
                        listMatches.add(matches);
                    }

                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void noMatch(String message) {

                    }
                });

                request.getHistoryMatchListsByMatchId(matchesList.get(3), playerId, new ApiRequest.HistoryCallbackMatch() {
                    @Override
                    public void onSuccess(Matches matches) {
                        listMatches.add(matches);
                    }

                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void noMatch(String message) {

                    }
                });
                request.getHistoryMatchListsByMatchId(matchesList.get(4), playerId, new ApiRequest.HistoryCallbackMatch() {
                    @Override
                    public void onSuccess(Matches matches) {
                        listMatches.add(matches);
                    }

                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void noMatch(String message) {

                    }
                });
                request.getHistoryMatchListsByMatchId(matchesList.get(5), playerId, new ApiRequest.HistoryCallbackMatch() {
                    @Override
                    public void onSuccess(Matches matches) {
                        listMatches.add(matches);
                    }

                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void noMatch(String message) {

                    }
                });
                request.getHistoryMatchListsByMatchId(matchesList.get(6), playerId, new ApiRequest.HistoryCallbackMatch() {
                    @Override
                    public void onSuccess(Matches matches) {
                        listMatches.add(matches);
                    }

                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void noMatch(String message) {

                    }
                });
                request.getHistoryMatchListsByMatchId(matchesList.get(7), playerId, new ApiRequest.HistoryCallbackMatch() {
                    @Override
                    public void onSuccess(Matches matches) {
                        listMatches.add(matches);
                    }

                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void noMatch(String message) {

                    }
                });
                request.getHistoryMatchListsByMatchId(matchesList.get(8), playerId, new ApiRequest.HistoryCallbackMatch() {
                    @Override
                    public void onSuccess(Matches matches) {
                        listMatches.add(matches);
                    }

                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void noMatch(String message) {

                    }
                });
                request.getHistoryMatchListsByMatchId(matchesList.get(9), playerId, new ApiRequest.HistoryCallbackMatch() {
                    @Override
                    public void onSuccess(Matches matches) {
                        listMatches.add(matches);
                        progressBarLoader.setVisibility(View.VISIBLE);
                        //Collections.sort(listMatches);
                        AdapterHistory adapterHistory = new AdapterHistory(listMatches,getApplicationContext());
                        recyclerViewMatches.setAdapter(adapterHistory);
                        progressBarLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void noMatch(String message) {

                    }
                });

            }
        },1000);

        progressBarLoader.setVisibility(View.VISIBLE);

    }






    public void Mensaje(String msj){
        Toast.makeText(MatchesActivity.this,""+ msj,Toast.LENGTH_SHORT).show();
    }
}
