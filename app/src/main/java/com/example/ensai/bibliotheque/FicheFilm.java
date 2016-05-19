package com.example.ensai.bibliotheque;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FicheFilm extends AppCompatActivity {

    private Film film = new Film();
    private Context contexte = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_film);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        film.setImdbID(id);

        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase readableDB = helper.getReadableDatabase();
        Cursor cursor = readableDB.rawQuery("SELECT * FROM films WHERE imdbID=?;", new String[]{id});
        int nbRows = cursor.getCount();

        if(nbRows==1){
            while(cursor.moveToNext()){
                film.setTitle(cursor.getString(0));
                film.setYear(cursor.getString(1));
                film.setRated(cursor.getString(2));
                film.setReleased(cursor.getString(3));
                film.setRuntime(cursor.getString(4));
                film.setGenre(cursor.getString(5));
                film.setDirector(cursor.getString(6));
                film.setWriter(cursor.getString(7));
                film.setActors(cursor.getString(8));
                film.setPlot(cursor.getString(9));
                film.setCountry(cursor.getString(10));
                film.setAwards(cursor.getString(11));
                film.setPoster(cursor.getString(12));
                film.setMetascore(cursor.getString(13));
                film.setImdbRating(cursor.getString(14));
                film.setImdbID(cursor.getString(15));

                TextView titre = (TextView) findViewById(R.id.fTitre);
                titre.setText(film.getTitle());
                TextView date = (TextView) findViewById(R.id.fDate);
                date.setText(date.getText() +" "+ film.getYear());
                TextView realisateur = (TextView) findViewById(R.id.fRéalisateur);
                realisateur.setText(realisateur.getText() +" "+ film.getDirector());
                TextView acteurs = (TextView) findViewById(R.id.fActeurs);
                acteurs.setText(acteurs.getText() +" "+ film.getActors());
                TextView pays = (TextView) findViewById(R.id.fPays);
                pays.setText(pays.getText() +" "+ film.getCountry());
                TextView genre = (TextView) findViewById(R.id.fGenre);
                genre.setText(genre.getText() +" "+ film.getGenre());
                TextView resume = (TextView) findViewById(R.id.fResume);
                resume.setText(resume.getText() +" "+ film.getPlot());
                TextView rated = (TextView) findViewById(R.id.fRated);
                rated.setText(rated.getText() +" "+ film.getRated());
                TextView duree = (TextView) findViewById(R.id.fDuree);
                duree.setText(duree.getText() +" "+ film.getRuntime());
                TextView recompenses = (TextView) findViewById(R.id.fAwards);
                recompenses.setText(recompenses.getText() +" "+ film.getAwards());
            }
        } else {
            Runnable recherche = new Runnable() {
                @Override
                public void run() {
                    try {
                        String json = rechercheInternet("http://www.omdbapi.com/?i=" + film.getImdbID() + "&y=&plot=short&r=json");
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        film = gson.fromJson(json, Film.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView titre = (TextView) findViewById(R.id.fTitre);
                                titre.setText(film.getTitle());
                                TextView date = (TextView) findViewById(R.id.fDate);
                                date.setText(date.getText() +" "+ film.getYear());
                                TextView realisateur = (TextView) findViewById(R.id.fRéalisateur);
                                realisateur.setText(realisateur.getText() +" "+ film.getDirector());
                                TextView acteurs = (TextView) findViewById(R.id.fActeurs);
                                acteurs.setText(acteurs.getText() +" "+ film.getActors());
                                TextView pays = (TextView) findViewById(R.id.fPays);
                                pays.setText(pays.getText() +" "+ film.getCountry());
                                TextView genre = (TextView) findViewById(R.id.fGenre);
                                genre.setText(genre.getText() +" "+ film.getGenre());
                                TextView resume = (TextView) findViewById(R.id.fResume);
                                resume.setText(resume.getText() +" "+ film.getPlot());
                                TextView rated = (TextView) findViewById(R.id.fRated);
                                rated.setText(rated.getText() +" "+ film.getRated());
                                TextView duree = (TextView) findViewById(R.id.fDuree);
                                duree.setText(duree.getText() +" "+ film.getRuntime());
                                TextView recompenses = (TextView) findViewById(R.id.fAwards);
                                recompenses.setText(recompenses.getText() +" "+ film.getAwards());
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
            new Thread(recherche).start();
        }



        ImageView poster = (ImageView) findViewById(R.id.poster);
        /*try {
            URL url = new URL(film.getPoster());
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            poster.setImageBitmap(image);
        } catch (MalformedURLException e) {
            Log.e("TAG", "Erreur sur l'Url de l'affiche", e);
        } catch (IOException e) {
            Log.e("TAG", "Erreur pour transformer en Bitmap", e);
        }*/


    }

    String rechercheInternet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        TextView titre = (TextView) findViewById(R.id.fTitre);
        outState.putString("title",titre.getText().toString());
        TextView date = (TextView) findViewById(R.id.fDate);
        outState.putString("date",date.getText().toString());
        TextView realisateur = (TextView) findViewById(R.id.fRéalisateur);
        outState.putString("réalisateur", realisateur.getText().toString());
        TextView acteurs = (TextView) findViewById(R.id.fActeurs);
        outState.putString("acteurs", acteurs.getText().toString());
        TextView pays = (TextView) findViewById(R.id.fPays);
        outState.putString("pays", pays.getText().toString());
        TextView genre = (TextView) findViewById(R.id.fGenre);
        outState.putString("genre",genre.getText().toString());
        TextView resume = (TextView) findViewById(R.id.fResume);
        outState.putString("résumé", resume.getText().toString());
        TextView rated = (TextView) findViewById(R.id.fRated);
        outState.putString("rated", rated.getText().toString());
        TextView duree = (TextView) findViewById(R.id.fDuree);
        outState.putString("durée", duree.getText().toString());
        TextView recompenses = (TextView) findViewById(R.id.fAwards);
        outState.putString("récompenses",recompenses.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TextView titre = (TextView) findViewById(R.id.fTitre);
        titre.setText(savedInstanceState.getString("title"));
        TextView date = (TextView) findViewById(R.id.fDate);
        date.setText(savedInstanceState.getString("date"));
        TextView realisateur = (TextView) findViewById(R.id.fRéalisateur);
        realisateur.setText(savedInstanceState.getString("réalisateur"));
        TextView acteurs = (TextView) findViewById(R.id.fActeurs);
        acteurs.setText(savedInstanceState.getString("acteurs"));
        TextView pays = (TextView) findViewById(R.id.fPays);
        pays.setText(savedInstanceState.getString("pays"));
        TextView genre = (TextView) findViewById(R.id.fGenre);
        genre.setText(savedInstanceState.getString("genre"));
        TextView resume = (TextView) findViewById(R.id.fResume);
        resume.setText(savedInstanceState.getString("résumé"));
        TextView rated = (TextView) findViewById(R.id.fRated);
        rated.setText(savedInstanceState.getString("rated"));
        TextView duree = (TextView) findViewById(R.id.fDuree);
        duree.setText(savedInstanceState.getString("durée"));
        TextView recompenses = (TextView) findViewById(R.id.fAwards);
        recompenses.setText(savedInstanceState.getString("récompenses"));
    }
}
