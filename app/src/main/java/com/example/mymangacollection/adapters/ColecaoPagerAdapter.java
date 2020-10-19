package com.example.mymangacollection.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mymangacollection.models.Colecao;
import com.example.mymangacollection.views.colecao.ColecaoDadosFragment;
import com.example.mymangacollection.views.colecao.ColecaoVolumesFragment;

public class ColecaoPagerAdapter extends FragmentStateAdapter {
    public Colecao colecao;

    public ColecaoPagerAdapter(FragmentActivity fragmentActivity, Colecao colecao) {
        super(fragmentActivity);
        this.colecao = colecao;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ColecaoDadosFragment(colecao);
            case 1:
                return new ColecaoVolumesFragment(colecao);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
