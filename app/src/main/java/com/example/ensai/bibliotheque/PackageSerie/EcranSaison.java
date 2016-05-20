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
    private String id;
    private String bouton;
    private int nbSaison;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_saison);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        bouton = intent.getStringExtra("bouton");
        nbSaison = intent.getIntExtra("nbSaisons",1);

        listView = (ListView) findViewById(R.id.listeSaison);

        afficherListeSaison();
    }

    public void afficherListeSaison() {
        List<String> listeSaison = new ArrayList<String>();

        for (int i = 1; i <= nbSaison; i++) {
            String texte = "Saison " + i;
            listeSaison.add(texte);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(contexte, android.R.layout.simple_list_item_1, listeSaison);
        listView.setAdapter(adapter);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TextView textView = adapter.getTextView(view);
                                    //lancerFiche(contexte, index);
                                }
                            });*/
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
