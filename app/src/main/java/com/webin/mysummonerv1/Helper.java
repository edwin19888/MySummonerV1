package com.webin.mysummonerv1;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Helper {

    public static void setImageItems(Context context, int item, ImageView image){

        if(item != 0){
            //Picasso.with(context).setLoggingEnabled(true);
            Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/"+HomeActivity.versionActualString+"/img/item/"+item+".png ").error(R.drawable.empty).into(image);

        }else{
            //Picasso.with(context).setLoggingEnabled(true);
            Picasso.with(context).load(R.drawable.empty).into(image);
        }

    }

    public static String convertDate(long creation){
        Date date = new Date(creation);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static String convertDuration(long duration) {

        String time;
        int millis = (int) (duration*1000);
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);
        time = df.format(new Date(millis));

        String[] array = time.split(":");
        if(array[0].equals("00")){

            time = array[1]+"m "+array[2]+"s";

        }else{
            time = array[0]+"h "+array[1]+"m "+array[2]+"s";
        }


        return time;
    }


    public static String getDateOfLong(long input){
        Date date = new Date(input);
        Calendar cal = new GregorianCalendar(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setCalendar(cal);
        cal.setTime(date);
        return sdf.format(date);

    }




    public static String getRedondear(int numero){

        String gold;
        DecimalFormat df = new DecimalFormat("#.0");
        gold = String.valueOf(df.format(numero/1000.0) + "K");
        return gold;
    }

    public static String getDiferencia(Date fechaInicial, Date fechaFinal){

        long diferencia = fechaFinal.getTime() - fechaInicial.getTime();

        Log.i("MainActivity", "fechaInicial : " + fechaInicial);
        Log.i("MainActivity", "fechaFinal : " + fechaFinal);

        long segsMilli = 1000;
        long minsMilli = segsMilli * 60;
        long horasMilli = minsMilli * 60;
        long diasMilli = horasMilli * 24;

        long diasTranscurridos = diferencia / diasMilli;
        diferencia = diferencia % diasMilli;

        long horasTranscurridos = diferencia / horasMilli;
        diferencia = diferencia % horasMilli;

        long minutosTranscurridos = diferencia / minsMilli;
        diferencia = diferencia % minsMilli;

        long segsTranscurridos = diferencia / segsMilli;

        return "diasTranscurridos: " + diasTranscurridos + " , horasTranscurridos: " + horasTranscurridos +
                " , minutosTranscurridos: " + minutosTranscurridos + " , segsTranscurridos: " + segsTranscurridos;


    }

    public static String getTypeKills(int doubleKills, int tripleKills, int quadraKills, int pentaKills){

        String typeKills = null;

        if(doubleKills == 0 && tripleKills == 0 && quadraKills == 0 && pentaKills == 0){
            typeKills = null;
        }else{

            if(pentaKills > 0) {
                typeKills = "Penta Kill";
            } else {
                //pentaKills=0
                if (quadraKills > 0) {
                    typeKills = "Quadra Kill";
                } else {
                    //quadraKills=0
                    if (tripleKills > 0) {
                        typeKills = "Triple Kill";
                    } else {
                        //tripleKills=0
                        if (doubleKills > 0) {
                            typeKills = "Double Kill";
                        }
                    }
                }
            }
        }

        return typeKills;
    }

    public static int getImageRank(String league){

        int image = R.drawable.unranked;

        if(league.equals("DIAMOND")){
            image = R.drawable.diamond;
        }

        return image;
    }

    public static String getLeagueName(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        } else {
            return  Character.toUpperCase(str.charAt(0)) + str.substring(1, str.length()).toLowerCase();
        }
    }
}