package com.webin.mysummonerv1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.webin.mysummonerv1.Helper;
import com.webin.mysummonerv1.Leagues;
import com.webin.mysummonerv1.R;

import java.util.ArrayList;

public class AdapterLeague extends RecyclerView.Adapter<AdapterLeague.ViewHolderLeague> {

    ArrayList<Leagues> leaguesArrayList;
    Context context;

    public AdapterLeague(ArrayList<Leagues> leaguesArrayList, Context context) {
        this.leaguesArrayList = leaguesArrayList;
        this.context = context;
    }

    @Override
    public ViewHolderLeague onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_league,null,false);
        return new ViewHolderLeague(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderLeague holder, int position) {
        Leagues oneLeague = leaguesArrayList.get(position);
        holder.tvRankedName.setText(oneLeague.getQueueType());
        holder.ivRankedImage.setImageResource(Helper.getImageRank(oneLeague.getTier()));
        holder.tvLeagueNameSolo.setText(Helper.getLeagueName(oneLeague.getTier()+" "+ oneLeague.getRank()));
        holder.tvRankedInfo.setText(oneLeague.getLeaguePoints()+" LP   "+oneLeague.getWins()+"W  "+oneLeague.getLosses()+"L");
    }

    @Override
    public int getItemCount() {
        return leaguesArrayList.size();
    }

    public class ViewHolderLeague extends RecyclerView.ViewHolder {

        TextView tvRankedName,tvLeagueNameSolo,tvRankedInfo;
        ImageView ivRankedImage;

        public ViewHolderLeague(View itemView) {
            super(itemView);

            tvRankedName = (TextView) itemView.findViewById(R.id.tvRankedName);
            ivRankedImage = (ImageView) itemView.findViewById(R.id.ivRanked);
            tvLeagueNameSolo = (TextView) itemView.findViewById(R.id.tvLeagueNameSolo);
            tvRankedInfo = (TextView) itemView.findViewById(R.id.tvRankedInfo);
        }
    }
}