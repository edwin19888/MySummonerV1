package com.webin.mysummonerv1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import com.webin.mysummonerv1.Clases.ActiveGames;
import com.webin.mysummonerv1.adapter.GamesActiveAdapter;
import com.webin.mysummonerv1.request.ApiRequest;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    private TextView tvDataNotFound,tvChampionLevel,tvRankedInfo,tvWinLosses,tvToolbarTitle,tvLeaguePoints,tvTitleLoading,tvOnline;
    private AppBarLayout app_bar;
    private CollapsingToolbarLayout collapsing_toolbar;
    private ProgressBar progressBar;
    public static ArrayList<ActiveGames> dataGames = new ArrayList<>();
    public static List<String> datat100 = new ArrayList<>();
    public static List<String> datat200 = new ArrayList<>();

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
        tvRankedInfo = (TextView) findViewById(R.id.tvRankedInfo);
        tvWinLosses = (TextView) findViewById(R.id.tvWinLosses);
        app_bar = (AppBarLayout) findViewById(R.id.app_bar);
        tvLeaguePoints = (TextView) findViewById(R.id.tvLeaguePoints);
        tvTitleLoading = (TextView) findViewById(R.id.tvTitleLoading);
        tvOnline = (TextView) findViewById(R.id.tvOnline);

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


            collapsingToolbarLayout.setTitle(playerName);
            //tvToolbarTitle.setText(playerName);


        }else{
            //Redireccionar a PirncipalActivity
        }

        tvOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog builder2;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder2 = new AlertDialog.Builder(MatchesActivity.this, android.R.style.Theme_Material_Dialog_Alert).create();
                } else {
                    builder2 = new AlertDialog.Builder(MatchesActivity.this).create();
                }
                builder2.setTitle("Información");
                builder2.setMessage("Verificando ...");

                builder2.setIcon(android.R.drawable.ic_dialog_alert);
                builder2.show();

                request.getActiveGames(playerId, new ApiRequest.CallbackActiveGames() {
                    @Override
                    public void onSuccess(ArrayList<ActiveGames> activeGamesArrayList, List<String> t100, List<String>t200) {
                        dataGames = activeGamesArrayList;
                        datat100 = t100;
                        datat200 = t200;
                        Intent intent = new Intent(MatchesActivity.this,ActiveGamesActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        builder2.dismiss();
                    }

                    @Override
                    public void onError(String message) {
                        builder2.dismiss();
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(MatchesActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(MatchesActivity.this);
                        }
                        builder.setTitle("Información");
                        builder.setMessage("No se encuentra en una partida o no es espectable");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        });
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        builder.show();

                    }
                });
            }
        });

        Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/profileicon/"+profileIconId+".png").into(ivProfileIcon);
        tvChampionLevel.setText(String.valueOf(summonerLevel));

        progressBar = (ProgressBar) findViewById(R.id.prLoadingInfoPlayer);
        //progressBar.setBackgroundColor(Color.BLACK);
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

                String rank = null;
                String tier = "Unranked";
                int wins = 0;
                int losses = 0;
                int leaguePoints = 0;
                ArrayList<Leagues> leagues;
                leagues = leaguesArrayList;

                for (int i=0;i<leagues.size();i++){
                    if(leagues.get(i).getQueueType().equals("RANKED_SOLO_5x5")){
                        rank = leagues.get(i).getRank();
                        tier = leagues.get(i).getTier();
                        wins = leagues.get(i).getWins();
                        losses = leagues.get(i).getLosses();
                        leaguePoints = leagues.get(i).getLeaguePoints();
                    }
                }

                if(wins == 0 && losses == 0)
                    tvWinLosses.setVisibility(View.INVISIBLE);

                if(rank != null) {
                    tvRankedInfo.setText(tier + " " + rank);
                    tvWinLosses.setText(wins + "W " + losses + "L");
                    tvLeaguePoints.setText(leaguePoints+" LP");

                }else{
                    tvLeaguePoints.setVisibility(View.INVISIBLE);
                    tvRankedInfo.setText(tier);
                }
                //AdapterHistory adapterHistory = new AdapterHistory(listMatches,getApplicationContext());
                //recyclerViewMatches.setAdapter(adapterHistory);

                //Actualizar un registro
                ConexionToSQLiteHelper cnx = new ConexionToSQLiteHelper(getApplicationContext(),ConexionToSQLiteHelper.DB_NAME,null,ConexionToSQLiteHelper.v_db);
                SQLiteDatabase db = cnx.getWritableDatabase();
                //db.execSQL("UPDATE busquedas SET tier='tier',rank='rank',date_insert='"+dateFormat.format(date)+" WHERE idplayer='playerId'");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String[] args = new String[] {String.valueOf(playerId)};
                ContentValues valores = new ContentValues();
                valores.put("tier",tier);
                valores.put("rank",rank);
                valores.put("date_insert", dateFormat.format(date));
                //Actualizamos el registro en la base de datos
                int numreg = db.update("busquedas", valores, "idplayer=?", args);
                //Log.d("SQLite numreg=",numreg+"");
                db.close();

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
                    tvTitleLoading.setVisibility(View.INVISIBLE);
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

    public void MostrarAlert(String title, String message, String data){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MatchesActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MatchesActivity.this);
        }
        builder.setTitle("Información");
        builder.setMessage("El usuario no se encuentra en una partida");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                });
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();

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
        int total;
        if(matchesList.size()>10){
            total = 10;
        }else{
            total = matchesList.size();
        }

        if(total > 0){
            for (int i = 0; i < total; i++) {
                LlenarViewRecyler(i,total-1,matchesList.get(i),playerId,request);
                Log.d("APP Match ",i+"");
            }
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

    private void LlenarViewRecyler(final int indice, final int total, final Long aLong, final long playerId, final ApiRequest request) {

        request.getHistoryMatchListsByMatchId(aLong, playerId, new ApiRequest.HistoryCallbackMatch() {
            @Override
            public void onSuccess(Matches matches) {

                listMatches.add(matches);
                Collections.sort(listMatches);
                AdapterHistory adapterHistory = new AdapterHistory(listMatches,getApplicationContext());
                recyclerViewMatches.setAdapter(adapterHistory);
                if(indice == total) {

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
        Intent intPrincipal = new Intent(MatchesActivity.this,HomeActivity.class);
        intPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intPrincipal);
        finish();
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
