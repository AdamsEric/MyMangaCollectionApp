package com.example.mymangacollection.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymangacollection.R;
import com.example.mymangacollection.models.Serie;

import java.util.ArrayList;

public class SerieAdapter extends RecyclerView.Adapter<SerieAdapter.SerieViewHolder> {
    private ArrayList<Serie> series;

    public SerieAdapter(ArrayList<Serie> series) {
        this.series = series;
    }

    @NonNull
    @Override
    public SerieAdapter.SerieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);

        SerieViewHolder serieHolder = new SerieViewHolder(view);

        return serieHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SerieAdapter.SerieViewHolder holder, int position) {
        if (series != null && series.size() > 0) {
            Serie serie = series.get(position);
            holder.txtSerieNome.setText(serie.getNome());
        }
    }

    @Override
    public int getItemCount() {
        return series.size();
    }

    public class SerieViewHolder extends RecyclerView.ViewHolder {
        public TextView txtSerieNome;

        public SerieViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSerieNome = itemView.findViewById(R.id.txtSerieNome);
        }
    }
}
