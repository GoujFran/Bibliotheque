package com.example.ensai.bibliotheque;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Base";

    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE films (title TEXT , year TEXT, rated TEXT, released TEXT, runtime TEXT, genre TEXT, director TEXT, writer TEXT, actors TEXT, plot TEXT,country TEXT," +
                "awards TEXT, poster TEXT, metascore TEXT, imdbRating TEXT, imdbID TEXT PRIMARY KEY)");
        db.execSQL("CREATE TABLE series (title TEXT , year TEXT, rated TEXT, released TEXT, runtime TEXT, genre TEXT, director TEXT, writer TEXT, actors TEXT, plot TEXT,country TEXT," +
                "awards TEXT, poster TEXT, metascore TEXT, imdbRating TEXT,  imdbID TEXT PRIMARY KEY, nbSaisons TEXT)");
        db.execSQL("CREATE TABLE saisons (numero TEXT ,imdbID TEXT, saisonID TEXT PRIMARY KEY, nbEpisodes TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}