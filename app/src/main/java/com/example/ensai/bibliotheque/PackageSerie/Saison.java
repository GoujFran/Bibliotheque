package com.example.ensai.bibliotheque.PackageSerie;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.ensai.bibliotheque.MyOpenHelper;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ensai on 20/05/16.
 */
public class Saison {

    @SerializedName("Title") private String title;
    @SerializedName("Season") private String season;
    @SerializedName("Episodes") private List<Episode> episodes;
    @SerializedName("Response") private String response;

    public Saison() {}

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void inserer(Context contexte, String imdbID, SQLiteDatabase writableDB) {
        ContentValues values = new ContentValues();
        values.put("numero",season);
        values.put("imdbID",imdbID);
        values.put("saisonID", imdbID + "_" + season);
        values.put("nbEpisodes", episodes.size());
        long rowID = writableDB.insert("saisons", null, values);
    }
}
