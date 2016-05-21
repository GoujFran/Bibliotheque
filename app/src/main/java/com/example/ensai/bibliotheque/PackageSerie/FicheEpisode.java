package com.example.ensai.bibliotheque.PackageSerie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

public class FicheEpisode extends AppCompatActivity {

    private String serieID;
    private int saison;
    private int numEpisode;
    private Episode episode = new Episode();
    private Context contexte =this;
    private String nomSerie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_episode);

        Intent intent = getIntent();
        serieID = intent.getStringExtra("imdbID");
        saison = intent.getIntExtra("saison", 0);
        numEpisode = intent.getIntExtra("episode",0);
        nomSerie = intent.getStringExtra("nomSerie");

        TextView textView = (TextView) findViewById(R.id.eTitreSerie);
        textView.setText(nomSerie);
        TextView textViewSaison = (TextView) findViewById(R.id.sSaisonEpisode);
        textViewSaison.setText("Saison "+saison + " Episode "+ numEpisode);

        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase readableDB = helper.getReadableDatabase();
        Log.e("Valeur",serieID+" "+saison+" "+numEpisode);
        Cursor cursor = readableDB.rawQuery("SELECT * FROM episodes WHERE serieID=? AND season=? AND episode=?;", new String[]{serieID,""+saison, ""+numEpisode});
        int nbRows = cursor.getCount();
        Log.e("nbRows",""+nbRows + " " + serieID);
        if(nbRows==1){
            while(cursor.moveToNext()){
                episode.setTitle(cursor.getString(0));
                episode.setYear(cursor.getString(1));
                episode.setRated(cursor.getString(2));
                episode.setReleased(cursor.getString(3));
                episode.setRuntime(cursor.getString(6));
                episode.setDirector(cursor.getString(8));
                episode.setWriter(cursor.getString(9));
                episode.setActors(cursor.getString(10));
                episode.setPlot(cursor.getString(11));
                episode.setImdbID(cursor.getString(17));

                TextView titre = (TextView) findViewById(R.id.eTitre);
                titre.setText(episode.getTitle());
                TextView date = (TextView) findViewById(R.id.eDate);
                date.setText("Date de diffusion : "+ episode.getYear());
                TextView realisateur = (TextView) findViewById(R.id.eRéalisateur);
                realisateur.setText("Réalisateur : " + episode.getDirector());
                TextView writers = (TextView) findViewById(R.id.eWriters);
                writers.setText("Scénaristes : "+ episode.getActors());
                TextView acteurs = (TextView) findViewById(R.id.eActeurs);
                acteurs.setText("Acteurs : "+ episode.getActors());
                TextView resume = (TextView) findViewById(R.id.eResume);
                resume.setText("Résumé : "+ episode.getPlot());
                TextView rated = (TextView) findViewById(R.id.eRated);
                rated.setText("Rated : "+ episode.getRated());
                TextView duree = (TextView) findViewById(R.id.eDuree);
                duree.setText("Durée : "+ episode.getRuntime());
                TextView recompenses = (TextView) findViewById(R.id.sAwards);
            }
        } else {
            if(!isOnline()){
                Toast.makeText(this,"La connexion Internet n'est pas disponible",Toast.LENGTH_LONG).show();
            } else {
                Runnable recherche = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String json = rechercheInternet("http://www.omdbapi.com/?i=" + serieID + "&Season=" + saison + "&Episode=" + numEpisode);
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            episode = gson.fromJson(json, Episode.class);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TextView titre = (TextView) findViewById(R.id.eTitre);
                                    titre.setText(episode.getTitle());
                                    TextView date = (TextView) findViewById(R.id.eDate);
                                    date.setText("Date de diffusion : "+ episode.getYear());
                                    TextView realisateur = (TextView) findViewById(R.id.eRéalisateur);
                                    realisateur.setText("Réalisateur : " + episode.getDirector());
                                    TextView writers = (TextView) findViewById(R.id.eWriters);
                                    writers.setText("Scénaristes : "+ episode.getActors());
                                    TextView acteurs = (TextView) findViewById(R.id.eActeurs);
                                    acteurs.setText("Acteurs : "+ episode.getActors());
                                    TextView resume = (TextView) findViewById(R.id.eResume);
                                    resume.setText("Résumé : "+ episode.getPlot());
                                    TextView rated = (TextView) findViewById(R.id.eRated);
                                    rated.setText("Rated : "+ episode.getRated());
                                    TextView duree = (TextView) findViewById(R.id.eDuree);
                                    duree.setText("Durée : "+ episode.getRuntime());
                                    TextView recompenses = (TextView) findViewById(R.id.sAwards);
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
        }
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public String rechercheInternet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
