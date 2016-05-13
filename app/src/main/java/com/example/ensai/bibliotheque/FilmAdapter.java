package com.example.ensai.bibliotheque;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ensai on 10/05/16.
 */
public class FilmAdapter extends BaseAdapter {
    List<Film> liste = new ArrayList<Film>();
    Context context;

    public FilmAdapter(Context context, List<Film> liste) {
        this.context = context;
        this.liste = liste;
    }

    @Override
    public int getCount() {
        return liste.size();
    }

    @Override
    public Object getItem(int position) {
        return liste.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Film film = (Film) getItem(position);
        View view =  LayoutInflater.from(context).inflate(R.layout.item_film, parent, false);
        TextView titre = (TextView) view.findViewById(R.id.titre);
        TextView soustitre = (TextView) view.findViewById(R.id.realisateur);
        titre.setText(film.getTitle() + " (" + film.getYear() + ") ");
        soustitre.setText(film.getDirector());
        return view;
    }

    public TextView getTextView(View view) {
        TextView titre = (TextView) view.findViewById(R.id.titre);
        return titre;
    }
}
