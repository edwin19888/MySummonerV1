package com.webin.mysummonerv1.Clases;

import java.util.ArrayList;
import java.util.List;

public class ActiveGames {

    long gameId,mapId,gameStartTime,gameLength,summonerId,teamId,profileIconId;
    int spell1Id,spell2Id,championId;
    String gameMode,gameType,summonerName,championName,spell1IdName,spell2IdName,mapName;
    Boolean bot;

    public ActiveGames(long gameId, long mapId, long gameStartTime, long gameLength, long summonerId, long teamId, long profileIconId, int spell1Id, int spell2Id, int championId, String gameMode, String gameType, String summonerName, String championName, String spell1IdName, String spell2IdName, Boolean bot, String mapName) {
        this.gameId = gameId;
        this.mapId = mapId;
        this.gameStartTime = gameStartTime;
        this.gameLength = gameLength;
        this.summonerId = summonerId;
        this.teamId = teamId;
        this.profileIconId = profileIconId;
        this.spell1Id = spell1Id;
        this.spell2Id = spell2Id;
        this.championId = championId;
        this.gameMode = gameMode;
        this.gameType = gameType;
        this.summonerName = summonerName;
        this.championName = championName;
        this.spell1IdName = spell1IdName;
        this.spell2IdName = spell2IdName;
        this.bot = bot;
        this.mapName = mapName;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getMapId() {
        return mapId;
    }

    public void setMapId(long mapId) {
        this.mapId = mapId;
    }

    public long getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(long gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public long getGameLength() {
        return gameLength;
    }

    public void setGameLength(long gameLength) {
        this.gameLength = gameLength;
    }

    public long getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(long summonerId) {
        this.summonerId = summonerId;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public long getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(long profileIconId) {
        this.profileIconId = profileIconId;
    }

    public int getSpell1Id() {
        return spell1Id;
    }

    public void setSpell1Id(int spell1Id) {
        this.spell1Id = spell1Id;
    }

    public int getSpell2Id() {
        return spell2Id;
    }

    public void setSpell2Id(int spell2Id) {
        this.spell2Id = spell2Id;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public String getSpell1IdName() {
        return spell1IdName;
    }

    public void setSpell1IdName(String spell1IdName) {
        this.spell1IdName = spell1IdName;
    }

    public String getSpell2IdName() {
        return spell2IdName;
    }

    public void setSpell2IdName(String spell2IdName) {
        this.spell2IdName = spell2IdName;
    }

    public Boolean getBot() {
        return bot;
    }

    public void setBot(Boolean bot) {
        this.bot = bot;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}