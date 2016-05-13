package com.example.ensai.bibliotheque;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.layout.simple_dropdown_item_1line;

public class EcranFilm extends AppCompatActivity {

    ArrayList<Film> liste = new ArrayList<Film>();
    ArrayList<String> listeTitre = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_film);
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
            frozen.setImdbID(cursor.getString(15));
            liste.add(frozen);
            listeTitre.add(frozen.getTitle() + " (" + frozen.getYear() + ") ");
        }
        cursor.close();
        final AutoCompleteTextView autoView = (AutoCompleteTextView) findViewById(R.id.autofilm);
        ArrayAdapter<String> adapterAuto = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listeTitre);
        autoView.setAdapter(adapterAuto);

        final Context contexte = this;
        autoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(contexte, FicheFilm.class);
                int index = listeTitre.indexOf(autoView.getText().toString());
                Film film = liste.get(index);
                intent.putExtra("id", film.getImdbID());
                startActivity(intent);
            }
        });

        //Aficher la liste des filmq
        final FilmAdapter adapter = new FilmAdapter(this,liste);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = adapter.getTextView(view);
                Intent intent = new Intent(contexte, FicheFilm.class);
                Toast.makeText(contexte, textView.getText().toString(),Toast.LENGTH_LONG).show();
                int index = listeTitre.indexOf(textView.getText().toString());
                Film film = liste.get(index);
                intent.putExtra("id", film.getImdbID());
                startActivity(intent);
            }
        });

    }

}
