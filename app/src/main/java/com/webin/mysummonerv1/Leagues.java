package com.webin.mysummonerv1;

public class Leagues {
    private String leagueName, tier, queueType, rank;
    private int wins,losses,leaguePoints;


    public Leagues(String leagueName, String tier, String queueType, String rank, int wins, int losses, int leaguePoints) {
        this.leagueName = leagueName;
        this.tier = tier;
        this.queueType = queueType;
        this.rank = rank;
        this.wins = wins;
        this.losses = losses;
        this.leaguePoints = leaguePoints;
    }

    public int getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(int leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
}