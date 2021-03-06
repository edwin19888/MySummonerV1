package com.webin.mysummonerv1.Clases;

public class Recent {

    private int idTable,accountId,idplayer,profileIconId,summonerLevel;
    private String username,date_insert,tier,rank,plataforma,region;

    public Recent(int idTable, String username, int accountId, int idplayer, int profileIconId, int summonerLevel,String tier, String rank, String plataforma, String region, String date_insert) {
        this.idTable = idTable;
        this.accountId = accountId;
        this.idplayer = idplayer;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.username = username;
        this.tier = tier;
        this.rank = rank;
        this.plataforma = plataforma;
        this.region = region;
        this.date_insert = date_insert;
    }

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getIdplayer() {
        return idplayer;
    }

    public void setIdplayer(int idplayer) {
        this.idplayer = idplayer;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(int summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDate_insert() {
        return date_insert;
    }

    public void setDate_insert(String date_insert) {
        this.date_insert = date_insert;
    }
}