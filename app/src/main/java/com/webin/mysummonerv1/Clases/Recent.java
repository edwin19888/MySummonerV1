package com.webin.mysummonerv1.Clases;

public class Recent {

    private int id;
    private String username,date_insert;

    public Recent(int id, String username, String date_insert) {
        this.id = id;
        this.username = username;
        this.date_insert = date_insert;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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