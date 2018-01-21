package com.webin.mysummonerv1.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.webin.mysummonerv1.Matches;
import com.webin.mysummonerv1.MatchesActivity;
import com.webin.mysummonerv1.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ApiRequest {

    private RequestQueue queue;
    private Context context;
    private static final String API_KEY = "RGAPI-4c418511-9e73-4ff4-bcca-0381d7ff5c1b";
    private String region;
    private ArrayList<Matches> arrayListMatches = new ArrayList<>();

    public ApiRequest(RequestQueue queue, Context context, String plataforma){
        this.queue = queue;
        this.context = context;
        this.region = plataforma;
    }

    public void checkPlayerName(final String summonerName, final CheckPlayerCallback callback){

        String url = "https://"+region+".api.riotgames.com/lol/summoner/v3/summoners/by-name/"+summonerName+"?api_key="+API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("APP", response.toString());

                try {
                    String name = response.getString("name");
                    Long id = response.getLong("id");
                    Long accountId = response.getLong("accountId");
                    callback.onSuccess(name, accountId, id);

                } catch (JSONException e) {
                    Log.d("APP", "EXCEPTION =" + e);
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof NetworkError){
                    callback.onError("Verifique su conexión a Internet.");
                }else if(error instanceof ServerError){
                    callback.dontExistSummoner("Invocador no existe en esta región");
                }else if(error instanceof NoConnectionError){
                    callback.dontExist("Url not found");
                }else{
                    callback.errorUnknown("Error Server");
                }
                Log.d("APP", "ERROR = " + error);

            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface CheckPlayerCallback{
        void onSuccess(String name, long accountId ,long id);
        void dontExist(String message);
        void onError(String message);
        void dontExistSummoner(String message);
        void errorUnknown(String message);
    }

    public String getJsonFile(Context context, String filename){

        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return json;
    }

    public String getChampionName(int champId) throws JSONException{

        String json = getJsonFile(context,"champion.json");
        String champName = "Default";
        JSONObject champ = null;

        champ = new JSONObject(json);
        JSONObject data = champ.getJSONObject("data");

        Iterator<String> keys = data.keys();
        while (keys.hasNext()){
            String key = keys.next();
            JSONObject inner = data.getJSONObject(key);
            int id = inner.getInt("id");
            JSONObject image = inner.getJSONObject("image");
            String champImage = image.getString("full");
            if(id == champId){
                champName = champImage;
                break;
            }
        }

        return champName;

    }

    public String getMapName(int mapId) throws JSONException{

        String json = getJsonFile(context,"map-es_mx.json");
        String mapName = "Default";
        JSONObject map = null;

        map = new JSONObject(json);
        JSONObject data = map.getJSONObject("data");
        Iterator<String> keys = data.keys();
        while (keys.hasNext()){
            String key = keys.next();
            JSONObject inner = data.getJSONObject(key);
            int mapIdjson = inner.getInt("mapId");
            String mapNameJson = inner.getString("mapName");
            if(mapId == mapIdjson){
                mapName = mapNameJson;
                break;
            }

        }
        return mapName;
    }

    public String getSummonerSpellName(int spellId) throws JSONException{

        String json = getJsonFile(context, "summoner-spells.json");
        String spellName = "Default";
        JSONObject spell = null;

        spell = new JSONObject(json);
        JSONObject data = spell.getJSONObject("data");

        Iterator<String> keys = data.keys();
        while (keys.hasNext()){
            String key = keys.next();
            JSONObject inner = data.getJSONObject(key);
            int id = inner.getInt("id");
            JSONObject image = inner.getJSONObject("image");
            String champImage = image.getString("full");
            if(id == spellId){
                spellName = champImage;
                break;
            }
        }


        return spellName;
    }

    public void getHistoryMatchesAccoundId(long accountId, final HistoryCallback callback){

        String url = "https://"+region+".api.riotgames.com/lol/match/v3/matchlists/by-account/"+accountId+"/recent?api_key="+API_KEY;
        Log.d("APP URL", "" + url);
        final List<Long> matchesList = new ArrayList<Long>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("APP Response", response+"");
                if(response.length() > 0){
                    try {
                        JSONArray games = response.getJSONArray("matches");

                        for (int i = 0; i < games.length();i++){
                            JSONObject oneMatch = games.getJSONObject(i);
                            long matchId = oneMatch.getLong("gameId");
                            matchesList.add(matchId);
                        }
                        callback.onSuccess(matchesList);

                    } catch (JSONException e) {
                        Log.d("APP:","EXCEPTION HISTORY 1 = " + e);
                        e.printStackTrace();
                    }
                }else {
                    callback.noMatches("No found match for player");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError("Imposible de conectar.");

                Log.d("APP ERROR",error.getMessage());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
        //queue.add(request);
        Log.d("APP FINAL","Termino");
    }



    public interface HistoryCallback{
        void onSuccess(List<Long> matchesList);
        void noMatches(String message);
        void onError(String message);
    }

    public void getAllChampions(AllChampionsCallback callback){
        List<Champion> allChampions = new ArrayList<>();
        String json = getJsonFile(context,"champion.json");
        String champName = null;
        JSONObject champ = null;

        try {
            champ = new JSONObject(json);
            JSONObject data = champ.getJSONObject("data");

            Iterator<String> keys = data.keys();
            while (keys.hasNext()){
                String key = keys.next();
                JSONObject inner = data.getJSONObject(key);
                int id = inner.getInt("id");
                JSONObject image = inner.getJSONObject("image");
                String champImage = image.getString("full");
                Champion champion = new Champion();
                champion.setKey(key);
                champion.setImageName(champImage);
                champion.setId(id);
                allChampions.add(champion);
            }

            callback.onSuccess(allChampions);


        } catch (JSONException e) {
            callback.onError("Error found");
            e.printStackTrace();

        }

    }

    public interface AllChampionsCallback{
        void onSuccess(List<Champion> listChampions);
        void onError(String message);
    }

    public void getHistoryMatchListsByMatchId(long matchId, final long id, final HistoryCallbackMatch callback) {

        String url = "https://"+region+".api.riotgames.com/lol/match/v3/matches/"+ matchId +"?api_key="+API_KEY;
        Log.d("url=",url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                List<Integer> teamWinners = new ArrayList<>();
                List<Integer> teamLossers = new ArrayList<>();
                Integer [] items = new Integer[7];

                if (response.length() > 0){
                    try {
                        Log.d("APP RESPONSE=",response+"");
                        long matchId = response.getLong("gameId");
                        long matchDuration = response.getLong("gameDuration");
                        long matchCreation = response.getLong("gameCreation");
                        String typeMatch = response.getString("gameMode");
                        int mapId = response.getInt("mapId");
                        int champId = 0;
                        int sum1 = 0;
                        int sum2 = 0;
                        int teamId = 0;
                        int participantId=0;
                        String summonerName=null;
                        int kills = 0;
                        int deaths = 0;
                        int assists = 0;
                        boolean win = false;
                        int gold=0;
                        int cs = 0;
                        int champLevel = 0;

                        JSONArray participantIdentities = response.getJSONArray("participantIdentities");

                        for(int i = 0; i < participantIdentities.length(); i++){
                            JSONObject parIdent = participantIdentities.getJSONObject(i);
                            int participantIdparIdent = parIdent.getInt("participantId");
                            JSONObject player = parIdent.getJSONObject("player");
                            int summonerId = player.getInt("summonerId");

                            if(id == summonerId){
                                summonerName = player.getString("summonerName");
                                participantId = parIdent.getInt("participantId");

                            }
                        }

                        JSONArray participants = response.getJSONArray("participants");

                        for (int j = 0; j < participants.length(); j++){
                            JSONObject participant = participants.getJSONObject(j);
                            int participantIdparticipants = participant.getInt("participantId");

                            if(participantId == participantIdparticipants){
                                teamId = participant.getInt("teamId");
                                sum1 = participant.getInt("spell1Id");
                                sum2 = participant.getInt("spell2Id");
                                champId = participant.getInt("championId");

                                JSONObject stats = participant.getJSONObject("stats");
                                for(int k = 0; k < 7; k++){
                                    String item = "item" + k;
                                    items[k] = stats.getInt(String.valueOf(item));
                                }
                                kills = stats.getInt("kills");
                                deaths = stats.getInt("deaths");
                                assists = stats.getInt("assists");
                                gold = stats.getInt("goldEarned");
                                cs = stats.getInt("totalMinionsKilled");
                                champLevel = stats.getInt("champLevel");

                            }
                        }

                        JSONArray teams = response.getJSONArray("teams");

                        for(int l = 0; l < teams.length(); l++){
                            JSONObject team = teams.getJSONObject(l);
                            int teamIdJsonteams = team.getInt("teamId");

                            if(teamIdJsonteams == teamId){
                                String winStatus = team.getString("win");
                                if(winStatus.equals("Fail")){
                                    win = false;
                                }

                                if(winStatus.equals("Win")){
                                    win = true;
                                }
                            }
                        }

                            /*Para definir TeamWinner o TeamLossers*/
                        JSONArray participantsteams = response.getJSONArray("participants");

                        for (int j = 0; j < participantsteams.length(); j++){
                            JSONObject participantTeam = participantsteams.getJSONObject(j);
                            int participantIdparticipants = participantTeam.getInt("participantId");

                            int teamIdxPlayerId = participantTeam.getInt("teamId");

                            if(win){
                                if(teamIdxPlayerId == teamId){
                                    teamWinners.add(participantTeam.getInt("championId"));
                                }else{
                                    teamLossers.add(participantTeam.getInt("championId"));
                                }
                            }else{
                                if(teamIdxPlayerId != teamId){
                                    teamWinners.add(participantTeam.getInt("championId"));
                                }else{
                                    teamLossers.add(participantTeam.getInt("championId"));
                                }
                            }
                        }

                        if(win){
                            teamWinners.add(champId);
                        }else{
                            teamLossers.add(champId);
                        }

                        String champName = getChampionName(champId);
                        String sum1Name = getSummonerSpellName(sum1);
                        String sum2Name = getSummonerSpellName(sum2);
                        String mapName = getMapName(mapId);

                        Matches matches = new Matches(win,typeMatch,champName,sum1Name,sum2Name,kills,deaths,assists,champLevel,cs,items,matchDuration,matchCreation,gold,matchId,mapName);
                        callback.onSuccess(matches);


                    } catch (JSONException e) {
                        Log.d("APP:","EXEPTION HISTORY  2 = " + e);
                        e.printStackTrace();
                    }
                }else{
                    callback.noMatch("No found match");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof NetworkError){
                    callback.onError("Impossible to the connect");
                }else if(error instanceof ServerError){
                    callback.onError("Server Error");
                }else  if(error instanceof AuthFailureError){
                    callback.onError("Expired Key");
                }
                Log.d("APP","ERROR FOUND = " + error);

            }

        });

        //queue.add(request);
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface HistoryCallbackMatch{
        void onSuccess(Matches matches);
        void onError(String message);
        void noMatch(String message);
    }


    public ArrayList<Matches> getArrayListMatches(){

        if(arrayListMatches.size() > 0){
            return arrayListMatches;
        }

        return null;
    }

}