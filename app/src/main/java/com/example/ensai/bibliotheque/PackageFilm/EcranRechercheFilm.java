package com.example.ensai.bibliotheque.PackageFilm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ensai.bibliotheque.FilmRecherche;
import com.example.ensai.bibliotheque.R;
import com.example.ensai.bibliotheque.Recherche;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class EcranRechercheFilm extends AppCompatActivity {

    private List<Film> listeFilm = new ArrayList<Film>();
    private ArrayList<String> listeBase = new ArrayList<String>();
    private Context contexte = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_recherche_film);
        ListView listView = (ListView) findViewById(R.id.listeFilmRecherche);
        Intent intent = getIntent();
        String contenuRecherche = intent.getStringExtra("contenuRecherche");
        String json = intent.getStringExtra("json");
        TextView textView = (TextView) findViewById(R.id.resultat);
        textView.setText(textView.getText().toString() + " : " + contenuRecherche);
        if(json.equals("{\"Response\":\"False\",\"Error\":\"Movie not found!\"}")){
            TextView textVide = (TextView) findViewById(R.id.pasDeResultats);
            textVide.setText("Il n'y a pas de films correspondants à votre recherche.");
        } else {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Recherche recherche = gson.fromJson(json, Recherche.class);
            List<FilmRecherche> listeRecherche = recherche.getSearch();
            for(int i=0;i<listeRecherche.size();i++){
                if(listeRecherche.get(i).getType().equals("movie")){
                    Film film = new Film();
                    film.setTitle(listeRecherche.get(i).getTitle());
                    film.setYear(listeRecherche.get(i).getYear());
                    film.setImdbID(listeRecherche.get(i).getImdbID());
                    film.setDirector("");
                    listeFilm.add(film);
                    listeBase.add(film.getTitle() + " (" + film.getYear() + ") ");
                }
            }
            final FilmAdapter adapter = new FilmAdapter(this,listeFilm);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = adapter.getTextView(view);
                    int index = listeBase.indexOf(textView.getText().toString());
                    lancerFiche(contexte, index);
                }
            });
        }
    }

    public void lancerFiche(Context contexte, int index) {
        Intent intent = new Intent(contexte, FicheFilm.class);
        Film film = listeFilm.get(index);
        intent.putExtra("id", film.getImdbID());
        startActivity(intent);
    }
}
