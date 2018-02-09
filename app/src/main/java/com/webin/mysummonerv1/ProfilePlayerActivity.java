package com.webin.mysummonerv1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;
import com.webin.mysummonerv1.request.ApiRequest;

import java.util.ArrayList;
import java.util.Collections;

public class ProfilePlayerActivity extends AppCompatActivity {

    private RequestQueue queue;
    private ApiRequest request;
    private ProgressDialog pd = null;
    String playerName,champName,image,firstChampion,secondChampion,thirdChampion;
    long summonerLevel = 0;
    int profileIconId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int mostrarServer = prefs.getInt("mostrarServer", 0);
        String serverName = prefs.getString("serverName","Korea");
        final String plataforma = prefs.getString("plataforma","kr");
        int idServer = prefs.getInt("idServer",0);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pd = ProgressDialog.show(this, "Procesando", "Cargando Partidas...", true, false);
        pd.show();

        Bundle bundle = getIntent().getExtras();


        if(bundle.getString("playerName") != null){
            playerName = bundle.getString("playerName");
        }
        if(bundle.getString("champName") != null){
            champName = bundle.getString("champName");
        }
        if(bundle.getString("image") != null){
            image = bundle.getString("image");
        }
        if(bundle.getString("firstChampion") != null){
            firstChampion = bundle.getString("firstChampion");
        }
        if(bundle.getString("secondChampion") != null){
            secondChampion = bundle.getString("secondChampion");
        }
        if(bundle.getString("thirdChampion") != null){
            thirdChampion = bundle.getString("thirdChampion");
        }
        if(bundle.getLong("summonerLevel") > 0){
            summonerLevel = bundle.getLong("summonerLevel");
        }
        if(bundle.getInt("profileIconId") > 0){
            profileIconId = bundle.getInt("profileIconId");
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                changetoapp();

                // Actions to do after 10 seconds
            }
        }, 1000);

    }

    public void changetoapp(){
        pd.dismiss();
        Intent intent = new Intent(ProfilePlayerActivity.this, PrincipalProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("champName",champName);
        bundle.putString("image",image);
        bundle.putLong("summonerLevel",summonerLevel);
        bundle.putString("playerName",playerName.toUpperCase());
        bundle.putString("firstChampion",firstChampion);
        bundle.putString("secondChampion",secondChampion);
        bundle.putString("thirdChampion",thirdChampion);
        bundle.putInt("profileIconId",profileIconId);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
