package com.webin.mysummonerv1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;
import com.webin.mysummonerv1.request.ApiRequest;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MatchesActivity extends AppCompatActivity {

    ArrayList<Matches> listMatches = new ArrayList<>();
    RecyclerView recyclerViewMatches,recyclerViewLeagues;
    private String playerName;
    private Long playerAccountId,playerId,summonerLevel;
    private int profileIconId;
    private RequestQueue queue;
    private ApiRequest request;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView ivChampPointsFirst,ivProfileIcon;
    private TextView tvDataNotFound,tvChampionLevel,tvPlayerName,tvRankedInfo,tvWinLosses,tvToolbarTitle;
    private AppBarLayout app_bar;
    private CollapsingToolbarLayout collapsing_toolbar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int mostrarServer = prefs.getInt("mostrarServer", 0);
        String serverName = prefs.getString("serverName","Korea");
        final String plataforma = prefs.getString("plataforma","kr");
        int idServer = prefs.getInt("idServer",0);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//no girar activity

        Toolbar toolbarMatches = (Toolbar) findViewById(R.id.toolbarMatches);
        setSupportActionBar(toolbarMatches);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        ivChampPointsFirst = (ImageView) findViewById(R.id.ivChampPointsFirst);
        ivProfileIcon = (ImageView) findViewById(R.id.ivProfileIcon);
        tvDataNotFound = (TextView) findViewById(R.id.tvDataNotFound);
        tvChampionLevel = (TextView) findViewById(R.id.tvChampionLevel);
        tvPlayerName = (TextView) findViewById(R.id.tvPlayerName);
        tvRankedInfo = (TextView) findViewById(R.id.tvRankedInfo);
        tvWinLosses = (TextView) findViewById(R.id.tvWinLosses);
        app_bar = (AppBarLayout) findViewById(R.id.app_bar);

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

            if(bundle.getInt("PROFILEICONID") > 0){
                profileIconId = bundle.getInt("PROFILEICONID");
            }
            if(bundle.getLong("SUMMONERLEVEL") >= 0){
                summonerLevel = bundle.getLong("SUMMONERLEVEL");
            }


            collapsingToolbarLayout.setTitle(playerName.toUpperCase());
            //tvToolbarTitle.setText(playerName);


        }else{
            //Redireccionar a PirncipalActivity
        }

        progressBar = (ProgressBar) findViewById(R.id.prLoadingInfoPlayer);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            Drawable wrapDrawable = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(this, R.color.button_pressed));
            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        } else {
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.button_pressed), PorterDuff.Mode.SRC_IN);
        }

        request.getPlayerChampMastery(playerId, new ApiRequest.CallbackChampMastery() {
            @Override
            public void onSuccess(ArrayList<PlayerChampionMastery> arrayListPlayerChampMast) {


                ArrayList<PlayerChampionMastery> playerChampionMasteryArrayList;
                playerChampionMasteryArrayList = arrayListPlayerChampMast;
                Collections.sort(playerChampionMasteryArrayList);
                String champName = playerChampionMasteryArrayList.get(0).getChampName();
                String image = champName.replace(".png","_0.jpg");
                Picasso.with(getApplicationContext()).setLoggingEnabled(true);
                Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+image).into(ivChampPointsFirst);
                Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/8.1.1/img/profileicon/"+profileIconId+".png").into(ivProfileIcon);
                tvChampionLevel.setText(String.valueOf(summonerLevel));
                tvPlayerName.setText(playerName.toUpperCase());
                int alphaAmount = 90; // some value 0-255 where 0 is fully transparent and 255 is fully opaque
                ivChampPointsFirst.setAlpha(alphaAmount);
            }

            @Override
            public void onError(String messaje) {

            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                // Actions to do after 10 seconds
            }
        }, 5000);

        request.getPlayerLeague(playerId, new ApiRequest.CallbackLeague() {
            @Override
            public void onSuccess(ArrayList<Leagues> leaguesArrayList) {

                String rank = "";
                String tier = "Unranked";
                int wins = 0;
                int losses = 0;
                ArrayList<Leagues> leagues;
                leagues = leaguesArrayList;

                for (int i=0;i<leagues.size();i++){
                    if(leagues.get(i).getQueueType().equals("RANKED_SOLO_5x5")){
                        rank = leagues.get(i).getRank();
                        tier = leagues.get(i).getTier();
                        wins = leagues.get(i).getWins();
                        losses = leagues.get(i).getLosses();
                    }
                }

                if(wins == 0 && losses == 0)
                    tvWinLosses.setVisibility(View.INVISIBLE);

                tvRankedInfo.setText(tier+" "+rank);
                tvWinLosses.setText(wins+"W "+losses+"L");
                //AdapterHistory adapterHistory = new AdapterHistory(listMatches,getApplicationContext());
                //recyclerViewMatches.setAdapter(adapterHistory);

            }

            @Override
            public void onError(String message) {
                tvRankedInfo.setText("Unranked");
            }

            @Override
            public void onUnranked(int largo) {
                tvRankedInfo.setText("Unranked");
            }
        });


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
                    msg = null;
                    tvDataNotFound.setVisibility(View.VISIBLE);
                    tvDataNotFound.setText("Partidas no encontradas");
                    app_bar.setVisibility(View.VISIBLE);
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

                /*
                LlenarViewRecyler(0,matchesList.get(0),playerId,request);
                LlenarViewRecyler(1,matchesList.get(1),playerId,request);
                LlenarViewRecyler(2,matchesList.get(2),playerId,request);
                LlenarViewRecyler(3,matchesList.get(3),playerId,request);
                LlenarViewRecyler(4,matchesList.get(4),playerId,request);
                LlenarViewRecyler(5,matchesList.get(5),playerId,request);
                LlenarViewRecyler(6,matchesList.get(6),playerId,request);
                LlenarViewRecyler(7,matchesList.get(7),playerId,request);
                LlenarViewRecyler(8,matchesList.get(8),playerId,request);
                LlenarViewRecyler(9,matchesList.get(9),playerId,request);
                */

        for (int i = 0; i < matchesList.size()-10; i++) {
            LlenarViewRecyler(i,matchesList.get(i),playerId,request);
        }

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

    private void LlenarViewRecyler(final int indice, final Long aLong, final long playerId, final ApiRequest request) {

        request.getHistoryMatchListsByMatchId(aLong, playerId, new ApiRequest.HistoryCallbackMatch() {
            @Override
            public void onSuccess(Matches matches) {

                listMatches.add(matches);
                Collections.sort(listMatches);
                AdapterHistory adapterHistory = new AdapterHistory(listMatches,getApplicationContext());
                recyclerViewMatches.setAdapter(adapterHistory);
                if(indice == 9) {

                    app_bar.setVisibility(View.VISIBLE);
                    recyclerViewMatches.setVisibility(View.VISIBLE);
                    //getSupportActionBar().show();
                }
            }

            @Override
            public void onError(String message) {
                Mensaje(message);
            }

            @Override
            public void noMatch(String message) {
                Mensaje(message);
            }
        });
    }

    public void Mensaje(String msj){
        if (msj != null){
            Toast.makeText(MatchesActivity.this,""+ msj,Toast.LENGTH_SHORT).show();
            finish();
            Intent intPrincipal = new Intent(MatchesActivity.this,HomeActivity.class);
            startActivity(intPrincipal);
        }
    }

    @Override
    public void onBackPressed(){
        finish();
        Intent intPrincipal = new Intent(MatchesActivity.this,HomeActivity.class);
        startActivity(intPrincipal);
    }

    public static ColorMatrixColorFilter brightIt(int fb) {
        ColorMatrix cmB = new ColorMatrix();
        cmB.set(new float[] {
                1, 0, 0, 0, fb,
                0, 1, 0, 0, fb,
                0, 0, 1, 0, fb,
                0, 0, 0, 1, 0   });

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(cmB);
//Canvas c = new Canvas(b2);
//Paint paint = new Paint();
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(colorMatrix);
//paint.setColorFilter(f);
        return f;
    }

    public static ColorMatrixColorFilter setContrast(float contrast) {
        float scale = contrast + 1.f;
        float translate = (-.5f * scale + .5f) * 255.f;
        float[] array = new float[] {
                scale, 0, 0, 0, translate,
                0, scale, 0, 0, translate,
                0, 0, scale, 0, translate,
                0, 0, 0, 1, 0};
        ColorMatrix matrix = new ColorMatrix(array);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        return filter;
    }


}
