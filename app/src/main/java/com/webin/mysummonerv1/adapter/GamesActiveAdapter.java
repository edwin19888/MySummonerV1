package com.webin.mysummonerv1.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.webin.mysummonerv1.Clases.ActiveGames;
import com.webin.mysummonerv1.HomeActivity;
import com.webin.mysummonerv1.Leagues;
import com.webin.mysummonerv1.MySingleton;
import com.webin.mysummonerv1.R;
import com.webin.mysummonerv1.request.ApiRequest;

import java.util.ArrayList;

public class GamesActiveAdapter extends RecyclerView.Adapter<GamesActiveAdapter.ViewHolderGames> {
    ArrayList<ActiveGames> activeGamesArrayList;
    Context context;
    private RequestQueue queue;
    private ApiRequest request;

    public GamesActiveAdapter(Context context, ArrayList<ActiveGames> activeGamesArrayList) {
        this.activeGamesArrayList = activeGamesArrayList;
        this.context = context;
    }

    @Override
    public ViewHolderGames onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_activegames,null,false);
        return new ViewHolderGames(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderGames holder, int position) {

        queue = MySingleton.getInstance(context).getRequestQueue();
        request = new ApiRequest(queue,context, HomeActivity.platCurrent);

        ActiveGames activeGames = activeGamesArrayList.get(position);
        holder.tvActPlayer.setText(activeGames.getSummonerName());
        Picasso.with(context).setLoggingEnabled(true);

        if(activeGames.getTeamId() == 100){
            if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.ivActChamp.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_square_blue));
            }else{
                holder.ivActChamp.setBackground(context.getResources().getDrawable(R.drawable.border_square_blue));
            }
        }else if(activeGames.getTeamId() == 200){
            if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.ivActChamp.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_square_red));
            }else{
                holder.ivActChamp.setBackground(context.getResources().getDrawable(R.drawable.border_square_red));
            }
        }

        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/spell/"+activeGames.getSpell1IdName()).into(holder.ivActSpell1);
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/spell/"+activeGames.getSpell2IdName()).into(holder.ivActSpell2);
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/champion/"+activeGames.getChampionName()).into(holder.ivActChamp);
        //Picasso.with(context).load("http://static.lolskill.net/img/skins/tablerow/"+activeGames.getChampionName().replace(".png","_0.jpg")).into((Target) holder.rlRowPlayerInGame);

        request.getPlayerLeague(activeGames.getSummonerId(), new ApiRequest.CallbackLeague() {
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

                if(rank != null) {
                    holder.tvActCurrentSeason.setText(tier + " " + rank);


                }else{
                    holder.tvActCurrentSeason.setText(tier);
                }

            }

            @Override
            public void onError(String message) {
                holder.tvActCurrentSeason.setText("Unranked");
            }

            @Override
            public void onUnranked(int largo) {
                holder.tvActCurrentSeason.setText("Unranked");
            }
        });
        /*
        Picasso.with(context).load("http://static.lolskill.net/img/skins/tablerow/"+activeGames.getChampionName().replace(".png","_0.jpg")).into(new Target(){

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    holder.rlRowPlayerInGame.setBackgroundDrawable(new BitmapDrawable(context.getResources(), bitmap));
                } else {
                    holder.rlRowPlayerInGame.setBackground(new BitmapDrawable(context.getResources(), bitmap));
                }
            }

            @Override
            public void onBitmapFailed(final Drawable errorDrawable) {
                Log.d("TAG", "FAILED");
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.d("TAG", "Prepare Load");
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return activeGamesArrayList.size();
    }

    public class ViewHolderGames extends RecyclerView.ViewHolder{

        TextView tvActPlayer,tvActCurrentSeason;
        ImageView ivActSpell1,ivActSpell2,ivActChamp;
        RelativeLayout rlRowPlayerInGame;

        public ViewHolderGames(View itemView) {
            super(itemView);

            tvActPlayer = (TextView) itemView.findViewById(R.id.tvActPlayer);
            ivActSpell1 = (ImageView) itemView.findViewById(R.id.ivActSpell1);
            ivActSpell2 = (ImageView) itemView.findViewById(R.id.ivActSpell2);
            rlRowPlayerInGame = (RelativeLayout) itemView.findViewById(R.id.rlRowPlayerInGame);
            ivActChamp = (ImageView) itemView.findViewById(R.id.ivActChamp);
            tvActCurrentSeason  = (TextView) itemView.findViewById(R.id.tvActCurrentSeason);
        }
    }
}