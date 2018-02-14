package com.webin.mysummonerv1.Clases;

public class Recent {

    private int idTable,accountId,idplayer,profileIconId,summonerLevel;
    private String username,date_insert;

    public Recent(int idTable, String username, int accountId, int idplayer, int profileIconId, int summonerLevel, String date_insert) {
        this.idTable = idTable;
        this.accountId = accountId;
        this.idplayer = idplayer;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.username = username;
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

    public String getDate_insert() {
        return date_insert;
    }

    public void setDate_insert(String date_insert) {
        this.date_insert = date_insert;
    }
}