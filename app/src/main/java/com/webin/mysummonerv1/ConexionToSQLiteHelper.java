package com.webin.mysummonerv1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionToSQLiteHelper extends SQLiteOpenHelper {

    //String sqlCreate = "CREATE TABLE busquedas (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, date_insert DATETIME DEFAULT (datetime('now','localtime')))";
    public static String DB_NAME = "SQLiteRecentSearch";
    private final Context context;
    public static int v_db = 2;
    String sqlCreate = "CREATE TABLE busquedas (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, accountId INTEGER, idplayer INTEGER, profileIconId INTEGER, summonerLevel INTEGER, date_insert DATETIME DEFAULT (datetime('now','localtime')))";
    String sqlUpdate = "ALTER TABLE busquedas ADD COLUMN fecha TEXT";

    public ConexionToSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(db.isReadOnly()){
            db = getWritableDatabase();
        }
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL(sqlUpdate);
        }
    }
}