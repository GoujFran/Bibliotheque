package com.example.ensai.bibliotheque;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ensai on 13/05/16.
 */
public class FilmRecherche {
    @SerializedName("Title") private String title;
    @SerializedName("Year") private String year;
    private String imdbID;
    @SerializedName("Type") private String type;
    @SerializedName("Poster") private String poster;

    public FilmRecherche() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}

