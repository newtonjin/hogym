package com.hog.newto.pf2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ExecutaTreinoActivity extends AppCompatActivity {
    private TextView txtExecNome;
    private TextView txtExecTempo;
    private TextView txtExecRep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executa_treino);
        txtExecNome=findViewById(R.id.txtExecNome);
        txtExecRep=findViewById(R.id.txtExecRep);
        txtExecTempo=findViewById(R.id.txtExecTempo);




    }
}
