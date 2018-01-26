package com.webin.mysummonerv1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolderHistory> {

    ArrayList<Matches> listMatches;
    Context context;

    public AdapterHistory(ArrayList<Matches> listMatches, Context context) {
        this.listMatches = listMatches;
        this.context = context;
    }

    @Override
    public ViewHolderHistory onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_match,null,false);
        return new ViewHolderHistory(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderHistory holder, int position) {
        final Matches oneMatch = listMatches.get(position);

        if(oneMatch.getWin()) {
            holder.viewWin.setBackgroundColor(context.getResources().getColor(R.color.win));
            holder.relativeLayoutWinOrLose.setBackgroundColor(context.getResources().getColor(R.color.win_row));
            //holder.relativeLayoutDetails.setBackgroundColor(context.getResources().getColor(R.color.win_row));
            holder.textViewWinOrDefeat.setTextColor(context.getResources().getColor(R.color.win_title));
            holder.textViewWinOrDefeat.setText("V");
        }else{
            holder.viewWin.setBackgroundColor(context.getResources().getColor(R.color.lose));
            holder.relativeLayoutWinOrLose.setBackgroundColor(context.getResources().getColor(R.color.lose_row));
            //holder.relativeLayoutDetails.setBackgroundColor(context.getResources().getColor(R.color.lose_row));
            holder.textViewWinOrDefeat.setTextColor(context.getResources().getColor(R.color.lose_title));
            holder.textViewWinOrDefeat.setText("D");
        }
        //Log.d("Populating Inicio", oneMatch.getNameChamp());
        //Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/8.1.1/img/champion/"+oneMatch.getNameChamp()).into(holder.imageViewChamp);
        //Log.d("Populating Fin", oneMatch.getNameChamp());
        holder.textViewGameMode.setText(oneMatch.getQueueName());
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/8.1.1/img/spell/"+oneMatch.getSpell1()).into(holder.imageViewSpell1);
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/8.1.1/img/spell/"+oneMatch.getSpell2()).into(holder.imageViewSpell2);

        int kill = oneMatch.getKills();
        int deaths = oneMatch.getDeaths();
        int assists = oneMatch.getAssists();
        String kda = String.valueOf(kill)+"/"+String.valueOf(deaths)+"/"+String.valueOf(assists);
        holder.textViewKDA.setText(kda);
        holder.textViewLevel.setText(String.valueOf(oneMatch.getLevelChamp()));
        holder.textViewCS.setText(String.valueOf(oneMatch.getCs()));

        //Log.d("Populating Inicio", oneMatch.getItems()[0]+"");
        //Picasso.with(context).setLoggingEnabled(true);

        Helper.setImageItems(context,oneMatch.getItems()[0],holder.imageViewItem00);
        Helper.setImageItems(context,oneMatch.getItems()[1],holder.imageViewItem01);
        Helper.setImageItems(context,oneMatch.getItems()[2],holder.imageViewItem02);
        Helper.setImageItems(context,oneMatch.getItems()[3],holder.imageViewItem03);
        Helper.setImageItems(context,oneMatch.getItems()[4],holder.imageViewItem04);
        Helper.setImageItems(context,oneMatch.getItems()[5],holder.imageViewItem05);

        holder.textViewDuration.setText(Helper.convertDuration(oneMatch.getDuration()));

        holder.textViewCreation.setText(Helper.getDateOfLong(oneMatch.getCreation()));
        holder.textViewGold.setText(Helper.getRedondear(oneMatch.getGold()));
        String dataKill = Helper.getTypeKills(oneMatch.getDoubleKills(),oneMatch.getTripleKills(),oneMatch.getQuadraKills(),oneMatch.getPentaKills());

        if (dataKill != null) {
            holder.textViewTypeKills.setVisibility(View.VISIBLE);
            holder.textViewTypeKills.setText(dataKill);
        }else{
            holder.textViewTypeKills.setVisibility(View.INVISIBLE);
            holder.textViewTypeKills.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return listMatches.size();
    }

    public class ViewHolderHistory extends RecyclerView.ViewHolder {

        View viewWin;
        ImageView imageViewChamp,imageViewSpell1,imageViewSpell2,imageViewItem00,imageViewItem01,imageViewItem02,imageViewItem03,imageViewItem04,imageViewItem05;
        TextView textViewGameMode,textViewKDA,textViewLevel,textViewCS,textViewDuration,textViewCreation,textViewWinOrDefeat,textViewGold,textViewTypeKills;
        RelativeLayout relativeLayoutWinOrLose,relativeLayoutDetails;

        public ViewHolderHistory(View itemView) {
            super(itemView);

            viewWin = (View) itemView.findViewById(R.id.ViewWinOrLose);
            textViewGameMode = (TextView) itemView.findViewById(R.id.tvGameMode);
            imageViewChamp = (ImageView) itemView.findViewById(R.id.ivChampion);
            relativeLayoutWinOrLose = (RelativeLayout) itemView.findViewById(R.id.RelativeLayoutFondoWinOrLose);
            //relativeLayoutDetails = (RelativeLayout) itemView.findViewById(R.id.RelativeLayoutDetailMatches);
            imageViewSpell1 = (ImageView) itemView.findViewById(R.id.ivSpell1);
            imageViewSpell2 = (ImageView) itemView.findViewById(R.id.ivSpell2);

            textViewKDA = (TextView) itemView.findViewById(R.id.tvKDA);
            textViewLevel = (TextView) itemView.findViewById(R.id.tvLevelPlayer);
            textViewCS = (TextView) itemView.findViewById(R.id.tvCSPlayer);

            imageViewItem00 = (ImageView) itemView.findViewById(R.id.ivItem00);
            imageViewItem01 = (ImageView) itemView.findViewById(R.id.ivItem01);
            imageViewItem02 = (ImageView) itemView.findViewById(R.id.ivItem02);
            imageViewItem03 = (ImageView) itemView.findViewById(R.id.ivItem03);
            imageViewItem04 = (ImageView) itemView.findViewById(R.id.ivItem04);
            imageViewItem05 = (ImageView) itemView.findViewById(R.id.ivItem05);
            textViewCreation = (TextView) itemView.findViewById(R.id.tvGameCreation);

            textViewWinOrDefeat = (TextView) itemView.findViewById(R.id.tvWinOrDefeat);
            textViewGold = (TextView) itemView.findViewById(R.id.tvGold);
            textViewDuration = (TextView) itemView.findViewById(R.id.tvGameDurationn);
            textViewTypeKills = (TextView) itemView.findViewById(R.id.tvTypeKill);

        }
    }
}