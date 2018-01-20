package com.webin.mysummonerv1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterMatch extends RecyclerView.Adapter<AdapterMatch.ViewHolderMatches> {

    ArrayList<String> listMatches;

    public AdapterMatch(ArrayList<String> listMatches) {
        this.listMatches = listMatches;
    }

    @Override
    public ViewHolderMatches onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_match,null,false);
        return new ViewHolderMatches(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderMatches holder, int position) {
        holder.asignarDatos(listMatches.get(position));
    }

    @Override
    public int getItemCount() {
        return listMatches.size();
    }

    public class ViewHolderMatches extends RecyclerView.ViewHolder {

        TextView gameMode;

        public ViewHolderMatches(View itemView) {
            super(itemView);
            //gameMode = (TextView) itemView.findViewById(R.id.tvGameMode);
        }

        public void asignarDatos(String s) {
            gameMode.setText(s);
        }
    }
}