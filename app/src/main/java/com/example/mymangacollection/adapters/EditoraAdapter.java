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
import com.example.mymangacollection.models.Editora;
import com.example.mymangacollection.views.editora.EditoraFormActivity;

import java.util.ArrayList;

public class EditoraAdapter extends RecyclerView.Adapter<EditoraAdapter.EditoraViewHolder> {
    private ArrayList<Editora> editoras;
    private Fragment fragment;

    public EditoraAdapter(ArrayList<Editora> editoras, Fragment fragment) {
        this.editoras = editoras;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public EditoraAdapter.EditoraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_editora, parent, false);

        return new EditoraAdapter.EditoraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditoraAdapter.EditoraViewHolder holder, int position) {
        if (editoras != null && editoras.size() > 0) {
            Editora editora = editoras.get(position);
            holder.txtEditoraNome.setText(editora.getNome());
        }
    }

    @Override
    public int getItemCount() {
        return editoras.size();
    }

    public class EditoraViewHolder extends RecyclerView.ViewHolder {
        public TextView txtEditoraNome;

        public EditoraViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEditoraNome = itemView.findViewById(R.id.txtEditoraNome);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (editoras.size() > 0) {
                    Editora editora = editoras.get(getLayoutPosition());

                    Intent it = new Intent(fragment.getContext(), EditoraFormActivity.class);
                    it.putExtra("EDITORA", editora);
                    fragment.startActivityForResult(it, ActivityRequestCodeEnum.EDITORA.getValue());
                }
                }
            });
        }
    }
}
