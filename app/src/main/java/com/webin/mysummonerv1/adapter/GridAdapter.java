package com.webin.mysummonerv1.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.webin.mysummonerv1.HomeActivity;
import com.webin.mysummonerv1.R;
import com.webin.mysummonerv1.request.Champion;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter{

    private Context context;
    private List<Champion> championList = new ArrayList<>();

    LayoutInflater inflater;

    public GridAdapter(Context context, List<Champion> championList) {
        this.context = context;
        this.championList = championList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return championList.size();
    }

    @Override
    public Object getItem(int position) {
        return championList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;

        if(convertView == null){

            convertView = inflater.inflate(R.layout.champion_row,parent,false);
            myViewHolder = new MyViewHolder();
            myViewHolder.image = (ImageView) convertView.findViewById(R.id.iv_champ);
            myViewHolder.name = (TextView) convertView.findViewById(R.id.tv_champ_name);
            convertView.setTag(myViewHolder);

        }else{

            myViewHolder = (MyViewHolder) convertView.getTag();

        }

        final Champion champion = championList.get(i);
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/champion/"+champion.getImageName()).into(myViewHolder.image);
        myViewHolder.name.setText(champion.getImageName().replace(".png",""));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String champName = champion.getImageName().replace(".png","");
                int id = champion.getId();
                Toast.makeText(context,champName+" / "+ id,Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }

    public class MyViewHolder{
        ImageView image;
        TextView name;
    }
}