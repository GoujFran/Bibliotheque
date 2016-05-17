package com.example.ensai.bibliotheque;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EcranAccueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_accueil);
        /*StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        String contenu = null;
        try {
            contenu = run("http://www.omdbapi.com/?s=frozen&y=&plot=short&r=json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Recherche recherche = gson.fromJson(contenu, Recherche.class);
            List<FilmRecherche> listeRecherche = recherche.getSearch();
            Toast.makeText(this,listeRecherche.get(0).getTitle().toString(),Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("TAG","Erreur",e);
        }*/

    }

    String run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public void clickFilms(View v) {
        Intent intent = new Intent(this, EcranFilm.class);
        startActivity(intent);
    }

}
