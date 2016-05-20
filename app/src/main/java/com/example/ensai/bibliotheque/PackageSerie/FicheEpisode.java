package com.example.ensai.bibliotheque.PackageSerie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ensai.bibliotheque.R;

public class FicheEpisode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_episode);

        Intent intent = getIntent();
    }
}
