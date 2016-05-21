package com.example.ensai.bibliotheque.PackageFilm;

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

import com.example.ensai.bibliotheque.MyOpenHelper;
import com.example.ensai.bibliotheque.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EcranFilm extends AppCompatActivity {

    private ArrayList<Film> liste = new ArrayList<Film>();
    private ArrayList<String> listeBase = new ArrayList<String>();
    private ArrayList<String> listeTitre = new ArrayList<String>();
    private Context contexte = this;
    private ListView listView;
    private AutoCompleteTextView autoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_film);

        listView = (ListView) findViewById(R.id.listeFilm);
        autoView = (AutoCompleteTextView) findViewById(R.id.autofilm);

        afficherAutoCompletion();
        afficherListeFilm();
    }
    
    public void lancerFiche(Context contexte, int index) {
        Intent intent = new Intent(contexte, FicheFilm.class);
        Film film = liste.get(index);
        intent.putExtra("id", film.getImdbID());
        startActivity(intent);
    }

    public void lancerRecherche(Context contexte,String contenu, String json) {
        Log.e("TAG", contenu);
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
        if(contenu.isEmpty()){
            Toast.makeText(this,"Veuillez entrer un nom de film",Toast.LENGTH_LONG).show();
        }
        else {
            if(!isOnline()) {
                Toast.makeText(this,"La connexion Internet n'est pas disponible",Toast.LENGTH_LONG).show();
                lancerRecherche(this,contenu,"{\"Response\":\"False\",\"Error\":\"Movie not found!\"}");
            } else {
                Runnable recherche = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String json = rechercheInternet("http://www.omdbapi.com/?s=" + contenu + "&y=&plot=short&r=json");
                            lancerRecherche(contexte,contenu,json);
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
        }
    }

    public void afficherAutoCompletion(){
        //Afficher autocomplétion
        liste.clear();
        listeTitre.clear();
        listeBase.clear();
        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase readableDB = helper.getReadableDatabase();
        Cursor cursor = readableDB.rawQuery("SELECT * FROM films;", null);
        int nbRows = cursor.getCount();
        while(cursor.moveToNext()){
            Film film = new Film();
            film.setTitle(cursor.getString(0));
            film.setYear(cursor.getString(1));
            film.setDirector(cursor.getString(6));
            film.setPoster(cursor.getString(12));
            film.setImdbID(cursor.getString(15));
            liste.add(film);
            listeTitre.add(film.getTitle());
            listeBase.add(film.getTitle() + " (" + film.getYear() + ") ");
        }
        cursor.close();
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
    }

    public void afficherListeFilm(){
        //Aficher la liste des films
        final FilmAdapter adapter = new FilmAdapter(this,liste);
        listView.setAdapter(adapter);

        //Afficher la fiche du film
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = adapter.getTextView(view);
                int index = listeBase.indexOf(textView.getText().toString());
                lancerFiche(contexte,index);
            }
        });
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

    @Override
    protected void onResume() {
        super.onResume();
        afficherAutoCompletion();
        afficherListeFilm();
        autoView.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
