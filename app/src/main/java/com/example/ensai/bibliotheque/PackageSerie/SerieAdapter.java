package com.example.ensai.bibliotheque.PackageSerie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ensai.bibliotheque.PackageSerie.Serie;
import com.example.ensai.bibliotheque.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ensai on 19/05/16.
 */
public class SerieAdapter extends BaseAdapter {
    List<Serie> liste = new ArrayList<Serie>();
    Context context;

    public SerieAdapter(Context context, List<Serie> liste) {
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
        Serie serie = (Serie) getItem(position);
        View view =  LayoutInflater.from(context).inflate(R.layout.item_serie, parent, false);
        TextView titre = (TextView) view.findViewById(R.id.titreSerie);
        TextView soustitre = (TextView) view.findViewById(R.id.saisons);
        titre.setText(serie.getTitle() + " (" + serie.getYear() + ") ");
        soustitre.setText(serie.getDirector());
        return view;
    }

    public TextView getTextView(View view) {
        TextView titre = (TextView) view.findViewById(R.id.titre);
        return titre;
    }
}
