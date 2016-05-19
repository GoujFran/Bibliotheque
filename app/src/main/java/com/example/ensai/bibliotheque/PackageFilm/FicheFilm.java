package com.example.ensai.bibliotheque.PackageFilm;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ensai.bibliotheque.MyOpenHelper;
import com.example.ensai.bibliotheque.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

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

                TextView titre = (TextView) findViewById(R.id.fTitre); //Mettre les find en dehors du if
                titre.setText(film.getTitle());
                TextView date = (TextView) findViewById(R.id.fDate);
                date.setText("Date de sortie : "+ film.getYear());
                TextView realisateur = (TextView) findViewById(R.id.fRéalisateur);
                realisateur.setText("Réalisateur : "+ film.getDirector());
                TextView acteurs = (TextView) findViewById(R.id.fActeurs);
                acteurs.setText("Acteurs : "+ film.getActors());
                TextView pays = (TextView) findViewById(R.id.fPays);
                pays.setText("Pays : "+ film.getCountry());
                TextView genre = (TextView) findViewById(R.id.fGenre);
                genre.setText("Genre : "+ film.getGenre());
                TextView resume = (TextView) findViewById(R.id.fResume);
                resume.setText("Résumé : "+ film.getPlot());
                TextView rated = (TextView) findViewById(R.id.fRated);
                rated.setText("Rated : "+ film.getRated());
                TextView duree = (TextView) findViewById(R.id.fDuree);
                duree.setText("Durée : "+ film.getRuntime());
                TextView recompenses = (TextView) findViewById(R.id.fAwards);
                recompenses.setText("Récompenses : "+ film.getAwards());
                Button bouton = (Button) findViewById(R.id.boutonAjout);
                bouton.setText("Supprimer");
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
                                date.setText("Date de sortie : "+ film.getYear());
                                TextView realisateur = (TextView) findViewById(R.id.fRéalisateur);
                                realisateur.setText("Réalisateur : "+ film.getDirector());
                                TextView acteurs = (TextView) findViewById(R.id.fActeurs);
                                acteurs.setText("Acteurs : "+ film.getActors());
                                TextView pays = (TextView) findViewById(R.id.fPays);
                                pays.setText("Pays : "+ film.getCountry());
                                TextView genre = (TextView) findViewById(R.id.fGenre);
                                genre.setText("Genre : "+ film.getGenre());
                                TextView resume = (TextView) findViewById(R.id.fResume);
                                resume.setText("Résumé : "+ film.getPlot());
                                TextView rated = (TextView) findViewById(R.id.fRated);
                                rated.setText("Rated : "+ film.getRated());
                                TextView duree = (TextView) findViewById(R.id.fDuree);
                                duree.setText("Durée : "+ film.getRuntime());
                                TextView recompenses = (TextView) findViewById(R.id.fAwards);
                                recompenses.setText("Récompenses : "+ film.getAwards());
                                Button bouton = (Button) findViewById(R.id.boutonAjout);
                                bouton.setText("Ajouter");
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
            cursor.close();
            readableDB.close();
            helper.close();
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

    public String rechercheInternet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public void clickAjout(View view) {
        Button bouton = (Button) findViewById(R.id.boutonAjout);
        if(bouton.getText().toString().equals("Supprimer")){
            film.supprimer(this);
            bouton.setText("Ajouter");
            Toast.makeText(this,"Supprimé",Toast.LENGTH_SHORT).show();
        }
        else {
            film.inserer(this);
            bouton.setText("Supprimer");
            Toast.makeText(this,"Ajouté",Toast.LENGTH_SHORT).show();
        }
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
        Button bouton = (Button) findViewById(R.id.boutonAjout);
        outState.putString("bouton",bouton.getText().toString());
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
        Button bouton = (Button) findViewById(R.id.boutonAjout);
        bouton.setText(savedInstanceState.getString("bouton"));
    }
}
