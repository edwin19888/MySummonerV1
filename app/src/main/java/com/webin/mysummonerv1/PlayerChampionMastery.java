package com.webin.mysummonerv1;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by Edwin on 30/01/2018.
 */

public class PlayerChampionMastery implements Comparable{

    private boolean chestGranted;
    private int championLevel,championPoints,tokensEarned,championId;
    private long playerId,championPointsUntilNextLevel,championPointsSinceLastLevel,lastPlayTime;
    private String champName;

    public PlayerChampionMastery(boolean chestGranted, int championLevel, int championPoints, int tokensEarned, int championId, long playerId, long championPointsUntilNextLevel, long championPointsSinceLastLevel, long lastPlayTime, String champName) {
        this.chestGranted = chestGranted;
        this.championLevel = championLevel;
        this.championPoints = championPoints;
        this.tokensEarned = tokensEarned;
        this.championId = championId;
        this.playerId = playerId;
        this.championPointsUntilNextLevel = championPointsUntilNextLevel;
        this.championPointsSinceLastLevel = championPointsSinceLastLevel;
        this.lastPlayTime = lastPlayTime;
        this.champName = champName;
    }

    public String getChampName() {
        return champName;
    }

    public void setChampName(String champName) {
        this.champName = champName;
    }

    public boolean isChestGranted() {
        return chestGranted;
    }

    public void setChestGranted(boolean chestGranted) {
        this.chestGranted = chestGranted;
    }

    public int getChampionLevel() {
        return championLevel;
    }

    public void setChampionLevel(int championLevel) {
        this.championLevel = championLevel;
    }

    public int getChampionPoints() {
        return championPoints;
    }

    public void setChampionPoints(int championPoints) {
        this.championPoints = championPoints;
    }

    public int getTokensEarned() {
        return tokensEarned;
    }

    public void setTokensEarned(int tokensEarned) {
        this.tokensEarned = tokensEarned;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public long getChampionPointsUntilNextLevel() {
        return championPointsUntilNextLevel;
    }

    public void setChampionPointsUntilNextLevel(long championPointsUntilNextLevel) {
        this.championPointsUntilNextLevel = championPointsUntilNextLevel;
    }

    public long getChampionPointsSinceLastLevel() {
        return championPointsSinceLastLevel;
    }

    public void setChampionPointsSinceLastLevel(long championPointsSinceLastLevel) {
        this.championPointsSinceLastLevel = championPointsSinceLastLevel;
    }

    public long getLastPlayTime() {
        return lastPlayTime;
    }

    public void setLastPlayTime(long lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    @Override
    public int compareTo(Object o) {
        int compareage= ((PlayerChampionMastery)o).getChampionPoints();
        return compareage - this.championPoints;
    }
}
