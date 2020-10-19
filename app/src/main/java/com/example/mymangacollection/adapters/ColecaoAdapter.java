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
import com.example.mymangacollection.models.Colecao;
import com.example.mymangacollection.views.colecao.ColecaoDetalhesActivity;

import java.util.ArrayList;

public class ColecaoAdapter extends RecyclerView.Adapter<ColecaoAdapter.ColecaoViewHolder> {
    private ArrayList<Colecao> colecoes;
    private Fragment fragment;

    public ColecaoAdapter(ArrayList<Colecao> colecoes, Fragment fragment) {
        this.colecoes = colecoes;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ColecaoAdapter.ColecaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_colecao, parent, false);

        return new ColecaoAdapter.ColecaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColecaoAdapter.ColecaoViewHolder holder, int position) {
        if (colecoes != null && colecoes.size() > 0) {
            Colecao colecao = colecoes.get(position);
            holder.txtColecaoTitulo.setText(colecao.getTitulo());

            if (colecao.getEditora() != null) {
                holder.txtColecaoEditora.setText(colecao.getEditora().getNome());
            }

            if (colecao.getTituloOriginal() != null) {
                holder.txtColecaoTituloOriginal.setVisibility(View.VISIBLE);
                holder.txtColecaoTituloOriginal.setText("(" + colecao.getTituloOriginal() + ")");
            } else {
                holder.txtColecaoTituloOriginal.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return colecoes.size();
    }

    public class ColecaoViewHolder extends RecyclerView.ViewHolder {
        public TextView txtColecaoTitulo;
        public TextView txtColecaoTituloOriginal;
        public TextView txtColecaoEditora;

        public ColecaoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtColecaoTitulo = itemView.findViewById(R.id.txtColecaoTitulo);
            txtColecaoTituloOriginal = itemView.findViewById(R.id.txtColecaoTituloOriginal);
            txtColecaoEditora = itemView.findViewById(R.id.txtColecaoEditora);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (colecoes.size() > 0) {
                        Colecao colecao = colecoes.get(getLayoutPosition());

                        Intent it = new Intent(fragment.getContext(), ColecaoDetalhesActivity.class);
                        it.putExtra("COLECAO", colecao);
                        fragment.startActivityForResult(it, ActivityRequestCodeEnum.COLECAO.getValue());
                    }
                }
            });
        }
    }
}