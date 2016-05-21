package com.example.ensai.bibliotheque.PackageSerie;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.ensai.bibliotheque.MyOpenHelper;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ensai on 20/05/16.
 */
public class Episode {

    @SerializedName("Title") private String title;
    @SerializedName("Year") private String year;
    @SerializedName("Rated")private String rated;
    @SerializedName("Released") private String released;
    @SerializedName("Season") private String season;
    @SerializedName("Episode") private String episode;
    @SerializedName("Runtime") private String runtime;
    @SerializedName("Genre") private String genre;
    @SerializedName("Director") private String director;
    @SerializedName("Writer") private String writer;
    @SerializedName("Actors") private String actors;
    @SerializedName("Plot") private String plot;
    @SerializedName("Language") private String language;
    @SerializedName("Country") private String country;
    @SerializedName("Awards") private String awards;
    @SerializedName("Poster") private String poster;
    @SerializedName("Metascore") private String metascore;
    private String imdbRating;
    private String imdbVotes;
    private String imdbID;
    private String seriesID;
    @SerializedName("Type") private String type;
    @SerializedName("Response") private String response;

    public Episode() {}

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMetascore() {
        return metascore;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSeriesID() {
        return seriesID;
    }

    public void setSeriesID(String seriesID) {
        this.seriesID = seriesID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void inserer(Context contexte, SQLiteDatabase writableDB) {
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("year",year);
        values.put("rated",rated);
        values.put("released",released);
        values.put("season",season);
        values.put("episode",episode);
        values.put("runtime",runtime);
        values.put("genre",genre);
        values.put("director",director);
        values.put("writer",writer);
        values.put("actors",actors);
        values.put("plot",plot);
        values.put("country",country);
        values.put("awards",awards);
        values.put("poster",poster);
        values.put("metascore",metascore);
        values.put("imdbRating",imdbRating);
        values.put("imdbID", imdbID);
        values.put("serieID", seriesID);
        long rowID = writableDB.insert("episodes", null, values);
    }
}
