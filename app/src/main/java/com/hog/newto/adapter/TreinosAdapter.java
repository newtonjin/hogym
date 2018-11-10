package com.hog.newto.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hog.newto.pf2.R;
import com.hog.newto.pf2.Treino;

import java.util.List;

public class TreinosAdapter extends ArrayAdapter<Treino> {
    private final List<Treino> lsTreino;
    private Activity context;
    public TreinosAdapter(Activity context,List<Treino> lsTreino){
        super(context,R.layout.recycler_treino,lsTreino);
        this.lsTreino=lsTreino;
        this.context=context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater lf= context.getLayoutInflater();
        View listViewItem = lf.inflate(R.layout.recycler_treino, null, true);


        TextView tvNome = (TextView) listViewItem.findViewById(R.id.tvNome);



        Treino t= lsTreino.get(position);


        tvNome.setText(t.getNomeTreino());


        return listViewItem;
    }
}
