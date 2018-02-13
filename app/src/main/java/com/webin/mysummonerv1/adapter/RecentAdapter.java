package com.webin.mysummonerv1.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.webin.mysummonerv1.Clases.Recent;
import com.webin.mysummonerv1.MatchesActivity;
import com.webin.mysummonerv1.R;

import java.util.ArrayList;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolderRecent> {

    ArrayList<Recent> arrayListRecent;
    Context context;

    public RecentAdapter(ArrayList<Recent> arrayListRecent, Context context) {
        this.arrayListRecent = arrayListRecent;
        this.context = context;
    }

    @Override
    public ViewHolderRecent onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recent_search,null,false);
        return new ViewHolderRecent(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderRecent holder, int position) {
        Recent recent = arrayListRecent.get(position);
        holder.tvRecentPlayer.setText(recent.getUsername());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Usuario : "+holder.tvRecentPlayer.getText(),Toast.LENGTH_LONG).show();
                Log.d("APP Usuario=",holder.tvRecentPlayer.getText()+"");
                Intent intent = new Intent(context,MatchesActivity.class);
                Bundle bundle = new Bundle();
                /*
                bundle.putString("USUARIO",name);
                bundle.putLong("ACCOUNTID",accountId);
                bundle.putLong("ID",id);
                bundle.putInt("PROFILEICONID",profileIconId);
                bundle.putLong("SUMMONERLEVEL",summonerLevel);
                intent.putExtras(bundle);
                context.startActivity(intent);
                */
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListRecent.size();
    }

    public class ViewHolderRecent extends RecyclerView.ViewHolder {

        TextView tvRecentPlayer;

        public ViewHolderRecent(View itemView) {
            super(itemView);

            tvRecentPlayer = (TextView) itemView.findViewById(R.id.tvRecentPlayer);
        }
    }

    //

}