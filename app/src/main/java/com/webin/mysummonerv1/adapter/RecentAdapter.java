package com.webin.mysummonerv1.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.webin.mysummonerv1.Clases.Recent;
import com.webin.mysummonerv1.ConexionToSQLiteHelper;
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
        holder.tvRSPlayer.setText(recent.getUsername());
        holder.tvRSaccountId.setText(String.valueOf(recent.getAccountId()));
        holder.tvRSid.setText(String.valueOf(recent.getIdplayer()));
        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/8.1.1/img/profileicon/"+recent.getProfileIconId()+".png").into(holder.ivRSprofileIconId);
        holder.tvRSsummonerLevel.setText(String.valueOf(recent.getSummonerLevel()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"Usuario : "+holder.tvRSPlayer.getText(),Toast.LENGTH_LONG).show();

                ConexionToSQLiteHelper cnx = new ConexionToSQLiteHelper(context,ConexionToSQLiteHelper.DB_NAME,null,ConexionToSQLiteHelper.v_db);
                SQLiteDatabase db = cnx.getWritableDatabase();

                String[] campos = new String[] {"id","username","accountId","idplayer","profileIconId","summonerLevel","date_insert"};
                String[] args = new String[] {holder.tvRSPlayer.getText().toString()};
                String username = null,fecha=null;
                int idTable = 0;
                int accountId = 0,idplayer=0,profileIconId=0,summonerLevel=0;

                Cursor c = db.query("busquedas", campos, "username=?", args, null, null, null);

                ArrayList<Recent> recentArrayList = new ArrayList<>();
                //Nos aseguramos de que existe al menos un registro
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        idTable= c.getInt(0);
                        username = c.getString(1);
                        accountId = c.getInt(2);
                        idplayer = c.getInt(3);
                        profileIconId = c.getInt(4);
                        summonerLevel = c.getInt(5);
                        fecha = c.getString(6);
                        Recent recent = new Recent(idTable,username,accountId,idplayer,profileIconId,summonerLevel,fecha);
                        recentArrayList.add(recent);
                        Log.d("SQLite idTable=",idTable+"");
                        Log.d("SQLite username=",username+"");
                        Log.d("SQLite accountId=",accountId+"");
                        Log.d("SQLite idplayer=",idplayer+"");
                        Log.d("SQLite profileIconId=",profileIconId+"");
                        Log.d("SQLite summonerLevel=",summonerLevel+"");
                        Log.d("SQLite fecha=",fecha+"");
                    } while(c.moveToNext());
                }
                db.close();


                Log.d("CAT tvRSPlayer=",holder.tvRSPlayer.getText()+"");
                Log.d("CAT tvRSaccountId=",holder.tvRSaccountId.getText()+"");
                Log.d("CAT tvRSid=",holder.tvRSid.getText()+"");
                Log.d("CAT tvRSsummonerLevel=",holder.tvRSsummonerLevel.getText()+"");

                Intent intent = new Intent(context,MatchesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("USUARIO",username);
                bundle.putLong("ACCOUNTID",accountId);
                bundle.putLong("ID",idplayer);
                bundle.putInt("PROFILEICONID",profileIconId);
                bundle.putLong("SUMMONERLEVEL",summonerLevel);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListRecent.size();
    }

    public class ViewHolderRecent extends RecyclerView.ViewHolder {

        TextView tvRSPlayer,tvRSsummonerLevel,tvRSaccountId,tvRSid;
        ImageView ivRSprofileIconId;

        public ViewHolderRecent(View itemView) {
            super(itemView);

            ivRSprofileIconId = (ImageView) itemView.findViewById(R.id.ivRSprofileIconId);
            tvRSPlayer = (TextView) itemView.findViewById(R.id.tvRSPlayer);
            tvRSsummonerLevel = (TextView) itemView.findViewById(R.id.tvRSsummonerLevel);
            tvRSaccountId = (TextView) itemView.findViewById(R.id.tvRSaccountId);
            tvRSid = (TextView) itemView.findViewById(R.id.tvRSid);

        }
    }

    //

}