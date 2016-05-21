package com.example.ensai.bibliotheque;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.example.ensai.bibliotheque.PackageFilm.EcranFilm;
import com.example.ensai.bibliotheque.PackageFilm.Film;
import com.example.ensai.bibliotheque.PackageSerie.EcranSerie;

import java.io.IOException;
import java.lang.Math;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EcranAccueil extends AppCompatActivity {

    private Film film = new Film();
    private Context contexte=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_accueil);

        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase readableDB = helper.getReadableDatabase();
        Cursor cursorFilm = readableDB.rawQuery("SELECT * FROM films;",null);
        int nbRowsFilm = cursorFilm.getCount();
        if(nbRowsFilm>0){
            double rand = Math.random()*(nbRowsFilm-1) ;
            int random = (int) rand;
            Log.e("random",""+random);
            cursorFilm.moveToPosition(random);
            TextView TFilm = (TextView) findViewById(R.id.sugFilm);
            TFilm.setText(cursorFilm.getString(0));
        }
        Cursor cursorSerie = readableDB.rawQuery("SELECT * FROM series;",null);
        int nbRowsSerie = cursorSerie.getCount();
        if(nbRowsSerie>0){
            double rand = Math.random()*(nbRowsSerie-1) ;
            int random = (int) rand;
            cursorSerie.moveToPosition(random);
            TextView TSerie = (TextView) findViewById(R.id.sugSerie);
            TSerie.setText(cursorSerie.getString(0));
        }
    }

    public void clickFilms(View v) {
        Intent intent = new Intent(this, EcranFilm.class);
        startActivity(intent);
    }

    public void clickSeries(View v) {
        Intent intent = new Intent(this, EcranSerie.class);
        startActivity(intent);
    }

}
