package com.example.ensai.bibliotheque.PackageSerie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ensai.bibliotheque.MyOpenHelper;
import com.example.ensai.bibliotheque.R;

import java.util.ArrayList;
import java.util.List;

public class FicheSaison extends AppCompatActivity {

    private String imdbID;
    private int saison;
    private String nomSerie;
    private ArrayList<Integer> listeNbEpisode = new ArrayList<Integer>();
    private ListView listView;
    private Context contexte = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_saison);

        listView = (ListView) findViewById(R.id.listeEpisode);

        Intent intent = getIntent();
        imdbID = intent.getStringExtra("id");
        saison = intent.getIntExtra("saison", 0);
        nomSerie = intent.getStringExtra("nomSerie");
        listeNbEpisode = intent.getIntegerArrayListExtra("listeNbEpisodes");

        TextView textView = (TextView) findViewById(R.id.sTitre3);
        textView.setText(nomSerie);
        TextView textViewSaison = (TextView) findViewById(R.id.sSaison);
        textViewSaison.setText("Saison "+saison);

        if(listeNbEpisode.size()==0){
            MyOpenHelper helper = new MyOpenHelper(this);
            SQLiteDatabase readableDB = helper.getReadableDatabase();
            Cursor cursor = readableDB.rawQuery("SELECT nbEpisodes FROM saisons WHERE imdbID=?", new String[] {imdbID});
            while(cursor.moveToNext()){
                listeNbEpisode.add(Integer.parseInt(cursor.getString(0)));
            }
            cursor.close();
            readableDB.close();
            helper.close();
        }

        afficherListeEpisodes();

    }

    public void afficherListeEpisodes() {
        final List<String> listeEpisodes = new ArrayList<String>();
        Log.e("TAG", listeNbEpisode.size() + " " + saison);
        for (int i = 1; i <= listeNbEpisode.get(saison-1); i++) {
            String texte = "Episode " + i;
            listeEpisodes.add(texte);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeEpisodes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int episode = position + 1;
                Intent intent = new Intent(contexte, FicheEpisode.class);
                intent.putExtra("imdbID", imdbID);
                intent.putExtra("saison", saison);
                intent.putExtra("episode", episode);
                intent.putExtra("nomSerie", nomSerie);
                startActivity(intent);
            }
        });
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
