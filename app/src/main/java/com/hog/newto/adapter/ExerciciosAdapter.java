package com.hog.newto.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hog.newto.pf2.Exercicio;
import com.hog.newto.pf2.R;

import java.util.List;

public class ExerciciosAdapter  extends ArrayAdapter<Exercicio> {
    private final List<Exercicio> lsExercicio;
    private Activity context;

    public ExerciciosAdapter(Activity context, @NonNull List<Exercicio> lsExercicio) {
        super(context, R.layout.list_exercicio, lsExercicio);
        this.context=context;
        this.lsExercicio=lsExercicio;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater lf= context.getLayoutInflater();
        View listViewItem = lf.inflate(R.layout.list_exercicio, null, true);

        TextView txtNome = listViewItem.findViewById(R.id.nomeExList);
        TextView txtRep =  listViewItem.findViewById(R.id.nomeRepList);
        TextView txtTempo = listViewItem.findViewById(R.id.nomeTempList);


        Exercicio ex= lsExercicio.get(position);


        txtNome.setText(ex.getNomeEx());
        txtRep.setText("x"+String.valueOf(ex.getRep()));
        txtTempo.setText("00:"+String.valueOf(ex.getTempo()));




        return listViewItem;

    }
}
