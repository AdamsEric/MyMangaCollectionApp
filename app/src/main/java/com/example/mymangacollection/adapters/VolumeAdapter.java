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
import com.example.mymangacollection.models.Volume;
import com.example.mymangacollection.views.colecao.ColecaoVolumeFormActivity;

import java.util.ArrayList;

public class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.VolumeViewHolder> {
    private ArrayList<Volume> volumes;
    private Colecao colecao;
    private Fragment fragment;

    public VolumeAdapter(ArrayList<Volume> volumes, Colecao colecao, Fragment fragment) {
        this.volumes = volumes;
        this.colecao = colecao;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public VolumeAdapter.VolumeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_volume, parent, false);

        return new VolumeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VolumeAdapter.VolumeViewHolder holder, int position) {
        if (volumes != null && volumes.size() > 0) {
            Volume volume = volumes.get(position);
            holder.txtVolumeDescricao.setText(volume.toString());
            holder.txtVolumeSituacaoLeitura.setText(volume.getSituacaoLeitura().getDescricao());
        }
    }

    @Override
    public int getItemCount() {
        return volumes.size();
    }

    public class VolumeViewHolder extends RecyclerView.ViewHolder {
        public TextView txtVolumeDescricao;
        public TextView txtVolumeSituacaoLeitura;

        public VolumeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtVolumeDescricao = itemView.findViewById(R.id.txtVolumeDescricao);
            txtVolumeSituacaoLeitura = itemView.findViewById(R.id.txtVolumeSituacaoLeitura);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (volumes.size() > 0) {
                        Volume volume = volumes.get(getLayoutPosition());

                        Intent it = new Intent(fragment.getContext(), ColecaoVolumeFormActivity.class);
                        it.putExtra("COLECAO", colecao);
                        it.putExtra("VOLUME", volume);
                        fragment.startActivityForResult(it, ActivityRequestCodeEnum.VOLUME.getValue());
                    }
                }
            });
        }
    }
}
