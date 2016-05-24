package com.example.ensai.bibliotheque.PackageSerie;

import android.content.ContentValues;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ensai.bibliotheque.MyOpenHelper;
import com.example.ensai.bibliotheque.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EcranSaison extends AppCompatActivity {

    private Context contexte = this;
    private String imdbID;
    private int nbSaison;
    private String nomSerie;
    private ArrayList<Integer> listeNbEpisodes = new ArrayList<Integer>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_saison);

        Intent intent = getIntent();
        imdbID = intent.getStringExtra("id");
        nbSaison = intent.getIntExtra("nbSaisons", 1);
        nomSerie = intent.getStringExtra("nomSerie");
        listeNbEpisodes = intent.getIntegerArrayListExtra("listeNbEpisodes");

        listView = (ListView) findViewById(R.id.listeSaison);

        afficherListeSaison();

        TextView textView = (TextView) findViewById(R.id.sTitre2);
        textView.setText(nomSerie);
    }

    public void afficherListeSaison() {
        final List<String> listeSaison = new ArrayList<String>();

        for (int i = 1; i <= nbSaison; i++) {
            String texte = "Saison " + i;
            listeSaison.add(texte);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(contexte, android.R.layout.simple_list_item_1, listeSaison);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int saison = position + 1;
                    Intent intent = new Intent(contexte,FicheSaison.class);
                    intent.putExtra("id",imdbID);
                    intent.putExtra("saison",saison);
                    intent.putExtra("nomSerie", nomSerie);
                    intent.putIntegerArrayListExtra("listeNbEpisodes", listeNbEpisodes);
                    startActivity(intent);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
