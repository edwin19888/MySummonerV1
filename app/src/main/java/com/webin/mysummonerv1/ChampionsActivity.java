package com.webin.mysummonerv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.webin.mysummonerv1.adapter.GridAdapter;
import com.webin.mysummonerv1.request.ApiRequest;
import com.webin.mysummonerv1.request.Champion;

import java.util.Collections;
import java.util.List;

public class ChampionsActivity extends AppCompatActivity {

    ImageView imageViewSearch,imageViewSetting;

    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;
    private ProgressBar progressBarLoader;
    private GridView gridViewGallery;
    private RequestQueue queue;
    private ApiRequest request;
    private GridAdapter gridAdapter;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champions);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//no girar activity

        setTitle("Campeones");

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int mostrarServer = prefs.getInt("mostrarServer", 0);
        String serverName = prefs.getString("serverName","Korea");
        String plataforma = prefs.getString("plataforma","kr");
        int idServer = prefs.getInt("idServer",0);


        queue = MySingleton.getInstance(this).getRequestQueue();
        request = new ApiRequest(queue,this, plataforma);

        progressBarLoader = (ProgressBar) findViewById(R.id.pb_ChampionLoader);
        gridViewGallery = (GridView) findViewById(R.id.gv_ChampionGallery);
        progressBarLoader.setVisibility(View.VISIBLE);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                request.getAllChampions(new ApiRequest.AllChampionsCallback() {
                    @Override
                    public void onSuccess(List<Champion> listChampions) {

                        gridAdapter = new GridAdapter(getApplicationContext(),listChampions);
                        Collections.sort(listChampions);
                        gridViewGallery.setAdapter(gridAdapter);
                        progressBarLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(String message) {
                        progressBarLoader.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, 1000);


        imageViewSearch = (ImageView) findViewById(R.id.ivSearchSummoner);
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChampionsActivity.this,PrincipalActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        imageViewSetting = (ImageView) findViewById(R.id.ivSettings);
        imageViewSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChampionsActivity.this,SettingsActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });


    }



    @Override
    public void onBackPressed(){
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
    }

}
