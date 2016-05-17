package com.example.ensai.bibliotheque;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ensai on 17/05/16.
 */
public class Recherche {
    @SerializedName("Search") private List<FilmRecherche> search;
    private String totalResults;
    @SerializedName("Response") private String response;

    public Recherche() {}

    public List<FilmRecherche> getSearch() {
        return search;
    }

    public void setSearch(List<FilmRecherche> search) {
        this.search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
