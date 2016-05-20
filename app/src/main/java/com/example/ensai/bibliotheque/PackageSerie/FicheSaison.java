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

import com.example.ensai.bibliotheque.MyOpenHelper;
import com.example.ensai.bibliotheque.R;

import java.util.ArrayList;
import java.util.List;

public class FicheSaison extends AppCompatActivity {

    private String id;
    private int saison;
    private ArrayList<Integer> listeNbEpisode = new ArrayList<Integer>();
    private ListView listView;
    private Context contexte = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_saison);

        listView = (ListView) findViewById(R.id.listeEpisode);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        saison = intent.getIntExtra("saison", 0);
        Log.e("Tag",""+saison);
        listeNbEpisode = intent.getIntegerArrayListExtra("listeNbEpisodes");

        if(listeNbEpisode.size()==0){
            MyOpenHelper helper = new MyOpenHelper(this);
            SQLiteDatabase readableDB = helper.getReadableDatabase();
            Cursor cursor = readableDB.rawQuery("SELECT nbEpisodes FROM saisons WHERE imdbID=?", new String[] {id});
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
        for (int i = 1; i <= listeNbEpisode.get(saison-1); i++) {
            String texte = "Episode " + i;
            listeEpisodes.add(texte);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeEpisodes);
        listView.setAdapter(adapter);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String saison = adapter.getItem(position);
                Intent intent = new Intent(contexte,FicheSaison.class);
                intent.putExtra("id",id);
                intent.putExtra("Saison",saison);
                intent.putIntegerArrayListExtra("listeNbEpisodes",listeEpisodes);
                startActivity(intent);
            }
        });*/
    }
}
