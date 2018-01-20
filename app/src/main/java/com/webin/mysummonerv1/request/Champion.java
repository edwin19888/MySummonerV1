package com.webin.mysummonerv1.request;

import android.support.annotation.NonNull;

public class Champion implements Comparable<Champion>{

    String key;
    String imageName;
    int id;

    public Champion(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public int compareTo(@NonNull Champion champion) {
        return imageName.compareTo(champion.imageName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}