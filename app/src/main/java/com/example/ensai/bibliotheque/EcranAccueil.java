package com.example.ensai.bibliotheque;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ensai.bibliotheque.PackageFilm.EcranFilm;
import com.example.ensai.bibliotheque.PackageFilm.Film;
import com.example.ensai.bibliotheque.PackageSerie.EcranSerie;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EcranAccueil extends AppCompatActivity {

    private Film film = new Film();
    private Context contexte=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_accueil);
        /*StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        String contenu = null;
        try {
            String json = rechercheInternet("http://www.omdbapi.com/?i=tt2294629&plot=short&r=json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Film film = gson.fromJson(json, Film.class);
            Toast.makeText(this,film.getTitle().toString(),Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("TAG","Erreur",e);
        }*/
        /*Runnable recherche = new Runnable() {
            @Override
            public void run() {
                try {
                    String json = rechercheInternet("http://www.omdbapi.com/?i=tt2294629&plot=short&r=json");
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    film = gson.fromJson(json, Film.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(contexte,film.getTitle().toString(),Toast.LENGTH_LONG).show();
                            Toast.makeText(contexte,film.getYear().toString(),Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(contexte, "Problème de connexion", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        };
        new Thread(recherche).start();*/
    }

    String rechercheInternet(String url) throws IOException {
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

    public void clickSeries(View v) {
        Intent intent = new Intent(this, EcranSerie.class);
        startActivity(intent);
    }

}
