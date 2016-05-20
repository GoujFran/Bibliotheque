package com.example.ensai.bibliotheque.PackageSerie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ensai.bibliotheque.MyOpenHelper;
import com.example.ensai.bibliotheque.PackageSerie.Serie;
import com.example.ensai.bibliotheque.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FicheSerie extends AppCompatActivity {

    private Serie serie = new Serie();
    private Context contexte = this;
    private List<Saison> listeSaison = new ArrayList<Saison>();
    private ArrayList<Integer> listeNbEpisodes = new ArrayList<Integer>();
    private int nbSaison;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_serie);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        serie.setImdbID(id);

        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase readableDB = helper.getReadableDatabase();
        Cursor cursor = readableDB.rawQuery("SELECT * FROM series WHERE imdbID=?;", new String[]{id});
        int nbRows = cursor.getCount();

        if(nbRows==1){
            while(cursor.moveToNext()){
                serie.setTitle(cursor.getString(0));
                serie.setYear(cursor.getString(1));
                serie.setRated(cursor.getString(2));
                serie.setReleased(cursor.getString(3));
                serie.setRuntime(cursor.getString(4));
                serie.setGenre(cursor.getString(5));
                serie.setDirector(cursor.getString(6));
                serie.setWriter(cursor.getString(7));
                serie.setActors(cursor.getString(8));
                serie.setPlot(cursor.getString(9));
                serie.setCountry(cursor.getString(10));
                serie.setAwards(cursor.getString(11));
                serie.setPoster(cursor.getString(12));
                serie.setMetascore(cursor.getString(13));
                serie.setImdbRating(cursor.getString(14));
                serie.setImdbID(cursor.getString(15));
                nbSaison = Integer.parseInt(cursor.getString(16));

                TextView titre = (TextView) findViewById(R.id.sTitre); //Mettre les find en dehors du if
                titre.setText(serie.getTitle());
                TextView date = (TextView) findViewById(R.id.sDate);
                date.setText("Années : "+ serie.getYear());
                TextView realisateur = (TextView) findViewById(R.id.sRéalisateur);
                realisateur.setText("Réalisateur : "+ serie.getDirector());
                TextView acteurs = (TextView) findViewById(R.id.sActeurs);
                acteurs.setText("Acteurs : "+ serie.getActors());
                TextView pays = (TextView) findViewById(R.id.sPays);
                pays.setText("Pays : "+ serie.getCountry());
                TextView genre = (TextView) findViewById(R.id.sGenre);
                genre.setText("Genre : "+ serie.getGenre());
                TextView resume = (TextView) findViewById(R.id.sResume);
                resume.setText("Résumé : "+ serie.getPlot());
                TextView rated = (TextView) findViewById(R.id.sRated);
                rated.setText("Rated : "+ serie.getRated());
                TextView duree = (TextView) findViewById(R.id.sDuree);
                duree.setText("Durée : "+ serie.getRuntime());
                TextView recompenses = (TextView) findViewById(R.id.sAwards);
                recompenses.setText("Récompenses : "+ serie.getAwards());
                Button bouton = (Button) findViewById(R.id.sBoutonAjout);
                bouton.setText("Supprimer");
            }
        } else {
            Runnable recherche = new Runnable() {
                @Override
                public void run() {
                    try {
                        String json = rechercheInternet("http://www.omdbapi.com/?i=" + serie.getImdbID() + "&y=&plot=short&r=json");
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        serie = gson.fromJson(json, Serie.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView titre = (TextView) findViewById(R.id.sTitre);
                                titre.setText(serie.getTitle());
                                TextView date = (TextView) findViewById(R.id.sDate);
                                date.setText("Date de sortie : " + serie.getYear());
                                TextView realisateur = (TextView) findViewById(R.id.sRéalisateur);
                                realisateur.setText("Réalisateur : " + serie.getDirector());
                                TextView acteurs = (TextView) findViewById(R.id.sActeurs);
                                acteurs.setText("Acteurs : " + serie.getActors());
                                TextView pays = (TextView) findViewById(R.id.sPays);
                                pays.setText("Pays : " + serie.getCountry());
                                TextView genre = (TextView) findViewById(R.id.sGenre);
                                genre.setText("Genre : " + serie.getGenre());
                                TextView resume = (TextView) findViewById(R.id.sResume);
                                resume.setText("Résumé : " + serie.getPlot());
                                TextView rated = (TextView) findViewById(R.id.sRated);
                                rated.setText("Rated : " + serie.getRated());
                                TextView duree = (TextView) findViewById(R.id.sDuree);
                                duree.setText("Durée : " + serie.getRuntime());
                                TextView recompenses = (TextView) findViewById(R.id.sAwards);
                                recompenses.setText("Récompenses : " + serie.getAwards());
                                Button bouton = (Button) findViewById(R.id.sBoutonAjout);
                                bouton.setText("Ajouter");
                            }
                        });

                        //Parser Saison
                        int i = 1;
                        String url = "http://www.omdbapi.com/?i=" + serie.getImdbID() + "&Season=" + i;
                        String jsonSaison = rechercheInternet(url);

                        while (!jsonSaison.equals("{\"Response\":\"False\",\"Error\":\"Series or season not found!\"}")) {
                            i++;
                            Saison saison = gson.fromJson(jsonSaison, Saison.class);
                            listeSaison.add(saison);
                            listeNbEpisodes.add(listeSaison.get(i - 2).getEpisodes().size());
                            url = "http://www.omdbapi.com/?i=" + serie.getImdbID() + "&Season=" + i;
                            jsonSaison = rechercheInternet(url);
                        }
                        nbSaison = listeSaison.size();

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



        ImageView poster = (ImageView) findViewById(R.id.sposter);
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

    public void clickAjoutSerie(View view) {
        Button bouton = (Button) findViewById(R.id.sBoutonAjout);
        if(bouton.getText().toString().equals("Supprimer")){
            serie.supprimer(this);
            bouton.setText("Ajouter");
            Toast.makeText(this,"Supprimé",Toast.LENGTH_SHORT).show();
        }
        else {
            serie.inserer(this,""+nbSaison);
            for(int i=0;i<nbSaison;i++){
                listeSaison.get(i).inserer(this,serie.getImdbID());
            }
            bouton.setText("Supprimer");
            Toast.makeText(this,"Ajouté",Toast.LENGTH_SHORT).show();
        }
    }

    public void clickBoutonSaison(View view){
        Button bouton = (Button) findViewById(R.id.sBoutonAjout);
        Intent intent = new Intent(this,EcranSaison.class);
        intent.putExtra("id",serie.getImdbID());
        intent.putExtra("bouton",bouton.getText().toString());
        intent.putExtra("nbSaisons",nbSaison);
        intent.putIntegerArrayListExtra("listeNbEpisodes",listeNbEpisodes);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        TextView titre = (TextView) findViewById(R.id.sTitre);
        outState.putString("title",titre.getText().toString());
        TextView date = (TextView) findViewById(R.id.sDate);
        outState.putString("date",date.getText().toString());
        TextView realisateur = (TextView) findViewById(R.id.sRéalisateur);
        outState.putString("réalisateur", realisateur.getText().toString());
        TextView acteurs = (TextView) findViewById(R.id.sActeurs);
        outState.putString("acteurs", acteurs.getText().toString());
        TextView pays = (TextView) findViewById(R.id.sPays);
        outState.putString("pays", pays.getText().toString());
        TextView genre = (TextView) findViewById(R.id.sGenre);
        outState.putString("genre",genre.getText().toString());
        TextView resume = (TextView) findViewById(R.id.sResume);
        outState.putString("résumé", resume.getText().toString());
        TextView rated = (TextView) findViewById(R.id.sRated);
        outState.putString("rated", rated.getText().toString());
        TextView duree = (TextView) findViewById(R.id.sDuree);
        outState.putString("durée", duree.getText().toString());
        TextView recompenses = (TextView) findViewById(R.id.sAwards);
        outState.putString("récompenses",recompenses.getText().toString());
        Button bouton = (Button) findViewById(R.id.sBoutonAjout);
        outState.putString("bouton",bouton.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TextView titre = (TextView) findViewById(R.id.sTitre);
        titre.setText(savedInstanceState.getString("title"));
        TextView date = (TextView) findViewById(R.id.sDate);
        date.setText(savedInstanceState.getString("date"));
        TextView realisateur = (TextView) findViewById(R.id.sRéalisateur);
        realisateur.setText(savedInstanceState.getString("réalisateur"));
        TextView acteurs = (TextView) findViewById(R.id.sActeurs);
        acteurs.setText(savedInstanceState.getString("acteurs"));
        TextView pays = (TextView) findViewById(R.id.sPays);
        pays.setText(savedInstanceState.getString("pays"));
        TextView genre = (TextView) findViewById(R.id.sGenre);
        genre.setText(savedInstanceState.getString("genre"));
        TextView resume = (TextView) findViewById(R.id.sResume);
        resume.setText(savedInstanceState.getString("résumé"));
        TextView rated = (TextView) findViewById(R.id.sRated);
        rated.setText(savedInstanceState.getString("rated"));
        TextView duree = (TextView) findViewById(R.id.sDuree);
        duree.setText(savedInstanceState.getString("durée"));
        TextView recompenses = (TextView) findViewById(R.id.sAwards);
        recompenses.setText(savedInstanceState.getString("récompenses"));
        Button bouton = (Button) findViewById(R.id.sBoutonAjout);
        bouton.setText(savedInstanceState.getString("bouton"));
    }
}
