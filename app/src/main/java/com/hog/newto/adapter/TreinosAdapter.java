package com.hog.newto.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hog.newto.holder.TreinosHolder;
import com.hog.newto.pf2.R;
import com.hog.newto.pf2.Treino;

import java.util.List;

public class TreinosAdapter extends RecyclerView.Adapter<TreinosHolder> {
    private final List<Treino> treino;
    public TreinosAdapter(List<Treino> treino){
        this.treino=treino;
    }

    @NonNull
    @Override
    public TreinosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TreinosHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_treino, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TreinosHolder holder, int position) {
        holder.tvNome.setText(treino.get(position).getNomeTreino());
    }

    @Override
    public int getItemCount() {
        return treino != null ? treino.size():0;
    }
}
