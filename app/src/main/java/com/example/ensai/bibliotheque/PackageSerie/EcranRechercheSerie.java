package com.example.ensai.bibliotheque.PackageSerie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class EcranRechercheSerie extends AppCompatActivity {

    private List<Serie> listeSerie = new ArrayList<Serie>();
    private ArrayList<String> listeBase = new ArrayList<String>();
    private Context contexte = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_recherche_serie);
        ListView listView = (ListView) findViewById(R.id.listeSerieRecherche);
        Intent intent = getIntent();
        String contenuRecherche = intent.getStringExtra("contenuRecherche");
        String json = intent.getStringExtra("json");
        TextView textView = (TextView) findViewById(R.id.sresultat);
        textView.setText(textView.getText().toString() + " : " + contenuRecherche);
        if(json.equals("{\"Response\":\"False\",\"Error\":\"Movie not found!\"}")){
            TextView textVide = (TextView) findViewById(R.id.spasDeResultats);
            textVide.setText("Il n'y a pas de séries correspondants à votre recherche.");
        } else {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Recherche recherche = gson.fromJson(json, Recherche.class);
            List<FilmRecherche> listeRecherche = recherche.getSearch();
            for(int i=0;i<listeRecherche.size();i++){
                if(listeRecherche.get(i).getType().equals("series")){
                    Serie serie = new Serie();
                    serie.setTitle(listeRecherche.get(i).getTitle());
                    serie.setYear(listeRecherche.get(i).getYear());
                    serie.setImdbID(listeRecherche.get(i).getImdbID());
                    serie.setDirector("");
                    listeSerie.add(serie);
                    listeBase.add(serie.getTitle() + " (" + serie.getYear() + ") ");
                }
            }
            final SerieAdapter adapter = new SerieAdapter(this,listeSerie);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = adapter.getTextView(view); //ne pointe sur rien
                    int index = listeBase.indexOf(textView.getText().toString());
                    lancerFiche(contexte, index);
                }
            });
        }
    }

    public void lancerFiche(Context contexte, int index) {
        Intent intent = new Intent(contexte, FicheSerie.class);
        Serie serie = listeSerie.get(index);
        intent.putExtra("id", serie.getImdbID());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
