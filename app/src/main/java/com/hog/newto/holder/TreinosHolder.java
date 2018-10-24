package com.hog.newto.holder;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hog.newto.pf2.R;

public class TreinosHolder extends RecyclerView.ViewHolder {

    public TextView tvNome;
    public ImageButton btnEdit;
    public ImageButton btnDelet;

    public TreinosHolder(View itemView) {
        super(itemView);
        tvNome=itemView.findViewById(R.id.tvNome);
        btnEdit=itemView.findViewById(R.id.btnEdit);
        btnDelet=itemView.findViewById(R.id.btnDelete);

    }
}
