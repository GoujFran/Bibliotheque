package com.example.ensai.bibliotheque;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EcranAccueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_accueil);
        //StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        String contenu = null;
        try {
            contenu = run("http://www.omdbapi.com/?t=frozen&y=&plot=short&r=json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Film film = gson.fromJson(contenu, Film.class);
            Toast.makeText(this,film.toString(),Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("TAG","Erreur",e);
        }

    }

    String run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
