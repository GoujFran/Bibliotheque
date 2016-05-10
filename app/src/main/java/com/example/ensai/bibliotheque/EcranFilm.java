package com.example.ensai.bibliotheque;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.R.layout.simple_dropdown_item_1line;

public class EcranFilm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_film);
        ListView listView = (ListView) findViewById(R.id.listeFilm);
        ArrayList<Film> liste = new ArrayList<Film>();
        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase readableDB = helper.getReadableDatabase();
        Cursor cursor = readableDB.rawQuery("SELECT * FROM films;",null);
        int nbRows = cursor.getCount();
        while(cursor.moveToNext()){
            Film frozen = new Film();
            frozen.setTitle(cursor.getString(0));
            frozen.setYear(cursor.getString(1));
            frozen.setDirector(cursor.getString(6));
            frozen.setImdbID(cursor.getString(15));
            liste.add(frozen);
        }
        cursor.close();
        ArrayList<String> listeTitre = new ArrayList<String>();
        listeTitre.add(liste.get(0).getTitle());
        AutoCompleteTextView autoView = (AutoCompleteTextView) findViewById(R.id.autofilm);
        ArrayAdapter<String> adapterAuto = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listeTitre);
        autoView.setAdapter(adapterAuto);

        //Aficher la liste des filmq
        FilmAdapter adapter = new FilmAdapter(this,liste);
        listView.setAdapter(adapter);
    }
}
