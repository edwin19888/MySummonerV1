package com.webin.mysummonerv1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;
import com.webin.mysummonerv1.request.ApiRequest;

public class PrincipalProfileActivity extends AppCompatActivity {

    private RequestQueue queue;
    private ApiRequest request;
    private ImageView ivChampPointsFirst,ivProfileIcon,ivFirstChampion,ivSecondChampion,ivThirdChampion;
    private TextView tvDataNotFound,tvChampionLevel,tvPlayerName,tvRankedInfo,tvWinLosses,tvLoadingData;

    String playerName,champName,image,firstChampion,secondChampion,thirdChampion;
    long summonerLevel = 0;
    int profileIconId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_profile);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int mostrarServer = prefs.getInt("mostrarServer", 0);
        String serverName = prefs.getString("serverName","Korea");
        final String plataforma = prefs.getString("plataforma","kr");
        int idServer = prefs.getInt("idServer",0);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//no girar activity

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivChampPointsFirst = (ImageView) findViewById(R.id.ivChampPointsFirst);
        ivProfileIcon = (ImageView) findViewById(R.id.ivProfileIcon);
        tvDataNotFound = (TextView) findViewById(R.id.tvDataNotFound);
        tvChampionLevel = (TextView) findViewById(R.id.tvChampionLevel);
        tvPlayerName = (TextView) findViewById(R.id.tvPlayerName);
        tvRankedInfo = (TextView) findViewById(R.id.tvRankedInfo);
        tvWinLosses = (TextView) findViewById(R.id.tvWinLosses);
        ivFirstChampion = (ImageView) findViewById(R.id.ivFirstChampion);
        ivSecondChampion = (ImageView) findViewById(R.id.ivSecondChampion);
        ivThirdChampion = (ImageView) findViewById(R.id.ivThirdChampion);
        tvLoadingData = (TextView) findViewById(R.id.tvLoadingData);

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

        Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+image).into(ivChampPointsFirst);
        Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/profileicon/"+profileIconId+".png").into(ivProfileIcon);
        tvChampionLevel.setText(String.valueOf(summonerLevel));
        tvPlayerName.setText(playerName.toUpperCase());
        //ivChampPointsFirst.setColorFilter(brightIt(-10));
        //ivChampPointsFirst.setColorFilter(setContrast(0));
        int alphaAmount = 90; // some value 0-255 where 0 is fully transparent and 255 is fully opaque
        ivChampPointsFirst.setAlpha(alphaAmount);

        Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/champion/"+firstChampion).into(ivFirstChampion);
        Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/champion/"+secondChampion).into(ivSecondChampion);
        Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/champion/"+thirdChampion).into(ivThirdChampion);

    }

    @Override
    public void onBackPressed(){
        finish();
        Intent intPrincipal = new Intent(PrincipalProfileActivity.this,PrincipalActivity.class);
        startActivity(intPrincipal);
    }
}
