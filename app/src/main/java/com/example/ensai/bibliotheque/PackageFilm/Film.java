package com.example.ensai.bibliotheque.PackageFilm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.ensai.bibliotheque.MyOpenHelper;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ensai on 10/05/16.
 */
public class Film {
    @SerializedName("Title") private String title;
    @SerializedName("Year") private String year;
    @SerializedName("Rated")private String rated;
    @SerializedName("Released") private String released;
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
    @SerializedName("Type") private String type;
    @SerializedName("Response") private String response;

    public Film() {}

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", rated='" + rated + '\'' +
                ", released='" + released + '\'' +
                ", runtime='" + runtime + '\'' +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", writer='" + writer + '\'' +
                ", actors='" + actors + '\'' +
                ", plot='" + plot + '\'' +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", awards='" + awards + '\'' +
                ", poster='" + poster + '\'' +
                ", metascore='" + metascore + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                ", imdbVotes='" + imdbVotes + '\'' +
                ", imdbID='" + imdbID + '\'' +
                ", type='" + type + '\'' +
                ", response='" + response + '\'' +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getWriter() {
        return writer;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getAwards() {
        return awards;
    }

    public String getPoster() {
        return poster;
    }

    public String getMetascore() {
        return metascore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return type;
    }

    public void inserer(Context contexte) {
        MyOpenHelper helper = new MyOpenHelper(contexte);
        SQLiteDatabase writableDB = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("year",year);
        values.put("rated",rated);
        values.put("released",released);
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
        long rowID = writableDB.insert("films", null, values);
        writableDB.close();
        helper.close();
    }

    public void supprimer(Context contexte) {
        MyOpenHelper helper = new MyOpenHelper(contexte);
        SQLiteDatabase writableDB = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        writableDB.delete("films","imdbID=?",new String[] {getImdbID()});
        writableDB.close();
        helper.close();
    }


}
