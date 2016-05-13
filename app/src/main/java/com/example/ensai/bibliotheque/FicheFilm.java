package com.example.ensai.bibliotheque;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FicheFilm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_film);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase readableDB = helper.getReadableDatabase();
        Cursor cursor = readableDB.rawQuery("SELECT * FROM films WHERE imdbID=?;", new String[]{id});
        int nbRows = cursor.getCount();
        Film film = new Film();
        while(cursor.moveToNext()){
            film.setTitle(cursor.getString(0));
            film.setYear(cursor.getString(1));
            film.setDirector(cursor.getString(6));
            film.setImdbID(cursor.getString(15));
        }


        TextView titre = (TextView) findViewById(R.id.fTitre);
        titre.setText(film.getTitle());
        TextView date = (TextView) findViewById(R.id.fDate);
        date.setText(date.getText() +" "+ film.getYear());
        TextView realisateur = (TextView) findViewById(R.id.fRéalisateur);
        realisateur.setText(realisateur.getText() +" "+ film.getDirector());
        TextView acteurs = (TextView) findViewById(R.id.fActeurs);
        TextView pays = (TextView) findViewById(R.id.fPays);
        TextView genre = (TextView) findViewById(R.id.fGenre);
        TextView resume = (TextView) findViewById(R.id.fResume);
        TextView rated = (TextView) findViewById(R.id.fRated);
        TextView duree = (TextView) findViewById(R.id.fDuree);
        TextView recompenses = (TextView) findViewById(R.id.fAwards);

    }
}
