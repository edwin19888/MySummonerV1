package com.webin.mysummonerv1;

import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.webin.mysummonerv1.Clases.ActiveGames;
import com.webin.mysummonerv1.adapter.GamesActiveAdapter;
import com.webin.mysummonerv1.request.ApiRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ActiveGamesActivity extends AppCompatActivity {

    private RecyclerView RecyclerViewGamesActiveTeam100,RecyclerViewGamesActiveTeam200;
    ImageView ivBanTeam100Champ0,ivBanTeam100Champ1,ivBanTeam100Champ2,ivBanTeam100Champ3,ivBanTeam100Champ4;
    ImageView ivBanTeam200Champ0,ivBanTeam200Champ1,ivBanTeam200Champ2,ivBanTeam200Champ3,ivBanTeam200Champ4;
    RelativeLayout rvActiveGames;
    TextView tvMapInfo,tvGameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_games);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        rvActiveGames = (RelativeLayout) findViewById(R.id.rvActiveGames);
        tvMapInfo = (TextView) findViewById(R.id.tvMapInfo);
        tvGameType = (TextView) findViewById(R.id.tvGameType);

        ivBanTeam100Champ0 = (ImageView) findViewById(R.id.ivBanTeam100Champ0);
        ivBanTeam100Champ1 = (ImageView) findViewById(R.id.ivBanTeam100Champ1);
        ivBanTeam100Champ2 = (ImageView) findViewById(R.id.ivBanTeam100Champ2);
        ivBanTeam100Champ3 = (ImageView) findViewById(R.id.ivBanTeam100Champ3);
        ivBanTeam100Champ4 = (ImageView) findViewById(R.id.ivBanTeam100Champ4);

        ivBanTeam200Champ0 = (ImageView) findViewById(R.id.ivBanTeam200Champ0);
        ivBanTeam200Champ1 = (ImageView) findViewById(R.id.ivBanTeam200Champ1);
        ivBanTeam200Champ2 = (ImageView) findViewById(R.id.ivBanTeam200Champ2);
        ivBanTeam200Champ3 = (ImageView) findViewById(R.id.ivBanTeam200Champ3);
        ivBanTeam200Champ4 = (ImageView) findViewById(R.id.ivBanTeam200Champ4);

        Chronometer simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometerActiveGame); // initiate a chronometer
        ArrayList<ActiveGames> activeGamesArrayList = MatchesActivity.dataGames;
        //Log.d("activeGames=",activeGamesArrayList.toString()+"");
        ArrayList<ActiveGames> team100 = new ArrayList<>();
        ArrayList<ActiveGames> team200 = new ArrayList<>();

        List<String> banteam100 = MatchesActivity.datat100;
        List<String> banteam200 = MatchesActivity.datat200;

        if(banteam100.size() > 0 && banteam200.size() > 0) {

            Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/champion/" + banteam100.get(0)).into(ivBanTeam100Champ0);
            Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/champion/" + banteam100.get(1)).into(ivBanTeam100Champ1);
            Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/champion/" + banteam100.get(2)).into(ivBanTeam100Champ2);
            Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/champion/" + banteam100.get(3)).into(ivBanTeam100Champ3);
            Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/champion/" + banteam100.get(4)).into(ivBanTeam100Champ4);
            Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/champion/" + banteam200.get(0)).into(ivBanTeam200Champ0);
            Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/champion/" + banteam200.get(1)).into(ivBanTeam200Champ1);
            Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/champion/" + banteam200.get(2)).into(ivBanTeam200Champ2);
            Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/champion/" + banteam200.get(3)).into(ivBanTeam200Champ3);
            Picasso.with(getApplicationContext()).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/champion/" + banteam200.get(4)).into(ivBanTeam200Champ4);

        }

        for(int i=0;i<activeGamesArrayList.size();i++){
            ActiveGames activeGames = activeGamesArrayList.get(i);
            if(activeGamesArrayList.get(i).getTeamId() == 100){
                team100.add(activeGames);
            }else{
                team200.add(activeGames);
            }
        }

        tvMapInfo.setText(team100.get(0).getMapName());
        tvGameType.setText(team100.get(0).getGameType().replace("_"," "));
        int tsegundos = (int) team100.get(0).getGameLength();
        int horas = (tsegundos / 3600);
        int minutos = ((tsegundos-horas*3600)/60);
        int segundos = tsegundos-(horas*3600+minutos*60);

        simpleChronometer.setBase(SystemClock.elapsedRealtime() - (horas * 3600000 + minutos * 60000 + segundos * 1000));
        simpleChronometer.start();

        RecyclerViewGamesActiveTeam100 = (RecyclerView) findViewById(R.id.RecyclerViewGamesActiveTeam100);
        RecyclerViewGamesActiveTeam100.setLayoutManager(new LinearLayoutManager(this));
        GamesActiveAdapter gamesActiveAdapterTeam100 = new GamesActiveAdapter(getApplicationContext(),team100);
        RecyclerViewGamesActiveTeam100.setAdapter(gamesActiveAdapterTeam100);

        RecyclerViewGamesActiveTeam200 = (RecyclerView) findViewById(R.id.RecyclerViewGamesActiveTeam200);
        RecyclerViewGamesActiveTeam200.setLayoutManager(new LinearLayoutManager(this));
        GamesActiveAdapter gamesActiveAdapterTeam200 = new GamesActiveAdapter(getApplicationContext(),team200);
        RecyclerViewGamesActiveTeam200.setAdapter(gamesActiveAdapterTeam200);

        rvActiveGames.setVisibility(View.VISIBLE);

    }
}
