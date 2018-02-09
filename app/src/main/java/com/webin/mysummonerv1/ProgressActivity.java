package com.webin.mysummonerv1;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class ProgressActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    View v;
    private String playerName;
    private Long playerAccountId,playerId,summonerLevel;
    private int profileIconId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        getSupportActionBar().hide();

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            Drawable wrapDrawable = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(this, R.color.button_pressed));
            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        } else {
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.button_pressed), PorterDuff.Mode.SRC_IN);
        }

        /*
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


            setTitle(playerName);

        }
        */

        //Handler handler = new Handler();
        //handler.postDelayed(new Runnable() {
        //    public void run() {
        //        changetoapp(v);
        //        progressBar.setVisibility(View.VISIBLE);
        //        // Actions to do after 10 seconds
        //    }
        //}, 2000);
    }

    public void changetoapp(View v){
        progressBar.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(ProgressActivity.this,MatchesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("USUARIO",playerName);
        bundle.putLong("ACCOUNTID",playerAccountId);
        bundle.putLong("ID",playerId);
        bundle.putInt("PROFILEICONID",profileIconId);
        bundle.putLong("SUMMONERLEVEL",summonerLevel);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
    }
}
