package com.example.ensai.bibliotheque;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EcranFilm extends AppCompatActivity {

    private ArrayList<Film> liste = new ArrayList<Film>();
    private ArrayList<String> listeBase = new ArrayList<String>();
    private ArrayList<String> listeTitre = new ArrayList<String>();
    final private Context contexte = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_film);

        //Afficher autocomplétion
        final ListView listView = (ListView) findViewById(R.id.listeFilm);
        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase readableDB = helper.getReadableDatabase();
        Cursor cursor = readableDB.rawQuery("SELECT * FROM films;", null);
        int nbRows = cursor.getCount();
        while(cursor.moveToNext()){
            Film frozen = new Film();
            frozen.setTitle(cursor.getString(0));
            frozen.setYear(cursor.getString(1));
            frozen.setDirector(cursor.getString(6));
            frozen.setPoster(cursor.getString(12));
            frozen.setImdbID(cursor.getString(15));
            liste.add(frozen);
            listeTitre.add(frozen.getTitle());
            listeBase.add(frozen.getTitle() + " (" + frozen.getYear() + ") ");
        }
        cursor.close();
        final AutoCompleteTextView autoView = (AutoCompleteTextView) findViewById(R.id.autofilm);
        ArrayAdapter<String> adapterAuto = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listeBase);
        autoView.setAdapter(adapterAuto);

        //Afficher la fiche du film
        autoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = listeBase.indexOf(autoView.getText().toString());
                lancerFiche(contexte, index);
            }
        });

        //Aficher la liste des filmq
        final FilmAdapter adapter = new FilmAdapter(this,liste);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = adapter.getTextView(view);
                int index = listeBase.indexOf(textView.getText().toString());
                lancerFiche(contexte,index);
            }
        });

    }
    
    public void lancerFiche(Context contexte, int index) {
        Intent intent = new Intent(contexte, FicheFilm.class);
        Film film = liste.get(index);
        intent.putExtra("id", film.getImdbID());
        startActivity(intent);
    }

    public void lancerRecherche(Context contexte,String contenu, String json) {
        Intent intent = new Intent(contexte, EcranRechercheFilm.class);
        intent.putExtra("contenuRecherche", contenu);
        intent.putExtra("json", json);
        startActivity(intent);
    }

    public void clickRechercher(View view){
        AutoCompleteTextView autoView = (AutoCompleteTextView) findViewById(R.id.autofilm);
        final String contenu = autoView.getText().toString();
        if(listeTitre.contains(contenu)){
            int index = listeTitre.indexOf(contenu);
            lancerFiche(this,index);
        }
        else {
            if(!isOnline()) {
                Toast.makeText(this,"La connexion Internet n'est pas disponible.",Toast.LENGTH_LONG).show();
                lancerRecherche(this,contenu,"{\"Response\":\"False\",\"Error\":\"Movie not found!\"}");
            } else {
                Runnable recherche = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String json = rechercheInternet("http://www.omdbapi.com/?s=" + contenu + "&y=&plot=short&r=json");
                            lancerRecherche(contexte,contenu,json);
                        } catch (IOException e) {
                            Toast.makeText(contexte, "Problème de connexion", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                new Thread(recherche).start();
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    String rechercheInternet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
