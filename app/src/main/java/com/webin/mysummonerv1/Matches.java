package com.webin.mysummonerv1;

import android.support.annotation.NonNull;

public class Matches implements Comparable{

    private Boolean win;
    private String gameMode;
    private String nameChamp;
    private String spell1;
    private String spell2;
    private int kills;
    private int deaths;
    private int assists;
    private int levelChamp;
    private int cs;
    private Integer[] items = new Integer[7];
    private long duration,creation;
    private int gold;
    private long matchId;
    private String mapName;
    private int doubleKills,tripleKills,quadraKills,pentaKills;
    private String queueName;

    public Integer[] getItems() {
        return items;
    }

    public void setItems(Integer[] items) {
        this.items = items;
    }

    public Matches(Boolean win, String gameMode, String nameChamp, String spell1, String spell2, int kills, int deaths, int assists, int levelChamp, int cs, Integer[] items, long duration, long creation, int gold, long matchId, String mapName, int doubleKills, int tripleKills, int quadraKills, int pentaKills, String queueName) {
        this.win = win;
        this.gameMode = gameMode;
        this.nameChamp = nameChamp;
        this.spell1 = spell1;
        this.spell2 = spell2;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.levelChamp = levelChamp;
        this.cs = cs;
        this.items = items;
        this.duration = duration;
        this.creation = creation;
        this.gold = gold;
        this.matchId = matchId;
        this.mapName = mapName;
        this.doubleKills = doubleKills;
        this.tripleKills = tripleKills;
        this.quadraKills = quadraKills;
        this.pentaKills = pentaKills;
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public int getDoubleKills() {
        return doubleKills;
    }

    public void setDoubleKills(int doubleKills) {
        this.doubleKills = doubleKills;
    }

    public int getTripleKills() {
        return tripleKills;
    }

    public void setTripleKills(int tripleKills) {
        this.tripleKills = tripleKills;
    }

    public int getQuadraKills() {
        return quadraKills;
    }

    public void setQuadraKills(int quadraKills) {
        this.quadraKills = quadraKills;
    }

    public int getPentaKills() {
        return pentaKills;
    }

    public void setPentaKills(int pentaKills) {
        this.pentaKills = pentaKills;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getCreation() {
        return creation;
    }

    public void setCreation(long creation) {
        this.creation = creation;
    }

    public int getCs() {
        return cs;
    }

    public void setCs(int cs) {
        this.cs = cs;
    }

    public int getLevelChamp() {
        return levelChamp;
    }

    public void setLevelChamp(int levelChamp) {
        this.levelChamp = levelChamp;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public String getSpell1() {
        return spell1;
    }

    public void setSpell1(String spell1) {
        this.spell1 = spell1;
    }

    public String getSpell2() {
        return spell2;
    }

    public void setSpell2(String spell2) {
        this.spell2 = spell2;
    }

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getNameChamp() {
        return nameChamp;
    }

    public void setNameChamp(String nameChamp) {
        this.nameChamp = nameChamp;
    }


    @Override
    public int compareTo(Object o) {
        int compareage= (int)((Matches)o).getCreation();
        return compareage - (int) this.creation;

    }
}