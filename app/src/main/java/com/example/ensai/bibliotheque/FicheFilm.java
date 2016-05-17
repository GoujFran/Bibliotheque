package com.example.ensai.bibliotheque;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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

        if(nbRows==1){
            while(cursor.moveToNext()){
                film.setTitle(cursor.getString(0));
                film.setYear(cursor.getString(1));
                film.setRated(cursor.getString(2));
                film.setReleased(cursor.getString(3));
                film.setRuntime(cursor.getString(4));
                film.setGenre(cursor.getString(5));
                film.setDirector(cursor.getString(6));
                film.setWriter(cursor.getString(7));
                film.setActors(cursor.getString(8));
                film.setPlot(cursor.getString(9));
                film.setCountry(cursor.getString(10));
                film.setAwards(cursor.getString(11));
                film.setPoster(cursor.getString(12));
                film.setMetascore(cursor.getString(13));
                film.setImdbRating(cursor.getString(14));
                film.setImdbID(cursor.getString(15));
            }
        } else {
            //faire une requête internet
        }

        TextView titre = (TextView) findViewById(R.id.fTitre);
        titre.setText(film.getTitle());
        TextView date = (TextView) findViewById(R.id.fDate);
        date.setText(date.getText() +" "+ film.getYear());
        TextView realisateur = (TextView) findViewById(R.id.fRéalisateur);
        realisateur.setText(realisateur.getText() +" "+ film.getDirector());
        TextView acteurs = (TextView) findViewById(R.id.fActeurs);
        acteurs.setText(acteurs.getText() +" "+ film.getActors());
        TextView pays = (TextView) findViewById(R.id.fPays);
        pays.setText(pays.getText() +" "+ film.getCountry());
        TextView genre = (TextView) findViewById(R.id.fGenre);
        genre.setText(genre.getText() +" "+ film.getGenre());
        TextView resume = (TextView) findViewById(R.id.fResume);
        resume.setText(resume.getText() +" "+ film.getPlot());
        TextView rated = (TextView) findViewById(R.id.fRated);
        rated.setText(rated.getText() +" "+ film.getRated());
        TextView duree = (TextView) findViewById(R.id.fDuree);
        duree.setText(duree.getText() +" "+ film.getRuntime());
        TextView recompenses = (TextView) findViewById(R.id.fAwards);
        recompenses.setText(recompenses.getText() +" "+ film.getAwards());

        ImageView poster = (ImageView) findViewById(R.id.poster);
        /*try {
            URL url = new URL(film.getPoster());
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            poster.setImageBitmap(image);
        } catch (MalformedURLException e) {
            Log.e("TAG", "Erreur sur l'Url de l'affiche", e);
        } catch (IOException e) {
            Log.e("TAG", "Erreur pour transformer en Bitmap", e);
        }*/


    }
}
