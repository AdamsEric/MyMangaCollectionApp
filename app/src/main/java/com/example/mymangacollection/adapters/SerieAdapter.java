package com.example.mymangacollection.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymangacollection.R;
import com.example.mymangacollection.helpers.ActivityRequestCodeEnum;
import com.example.mymangacollection.models.Serie;
import com.example.mymangacollection.views.serie.SerieFormActivity;

import java.util.ArrayList;

public class SerieAdapter extends RecyclerView.Adapter<SerieAdapter.SerieViewHolder> {
    private ArrayList<Serie> series;
    private Fragment fragment;

    public SerieAdapter(ArrayList<Serie> series, Fragment fragment) {
        this.series = series;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public SerieAdapter.SerieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_serie, parent, false);

        return new SerieViewHolder(view);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (series.size() > 0) {
                    Serie serie = series.get(getLayoutPosition());

                    Intent it = new Intent(fragment.getContext(), SerieFormActivity.class);
                    it.putExtra("SERIE", serie);
                    fragment.startActivityForResult(it, ActivityRequestCodeEnum.SERIE.getValue());
                }
                }
            });
        }
    }
}
