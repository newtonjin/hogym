package com.hog.newto.pf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ExecutaTreinoActivity extends AppCompatActivity {
    private TextView txtExecNome;
    private TextView txtExecTempo;
    private TextView txtExecRep;
    private String rep,tempo;
    private String nomeExercicioIntent;
    private ImageView btnProx;
    static Thread th;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executa_treino);
        txtExecNome=findViewById(R.id.txtExecNome);
        txtExecRep=findViewById(R.id.txtExecRep);
        txtExecTempo=findViewById(R.id.txtExecTempo);
        btnProx=findViewById(R.id.btnProx);

        Intent i= getIntent();
        th=new Thread();
        nomeExercicioIntent=i.getStringExtra("NOME_EXE");
        rep=i.getStringExtra("REP_EXE");
        tempo=i.getStringExtra("TEMPO_EXE");



    }

    @Override
    protected void onStart() {
        super.onStart();
        //Contador do meu mano Sekcy ABRAÇO AMIGO, QUANDO EU TIVER NA BANCA VO FALAR DE TU!
        Thread th = new Thread(new Runnable() { //cria uma thread
            public void run() {
                int t=Integer.parseInt(tempo);
                while (t>= 0) {
                    txtExecTempo.setText(String.valueOf(t));

                    t--;
                    try {
                        Thread.sleep(1000); //espera 1 segundo para fazer a nova evolução
                    } catch (InterruptedException ex) {
                    }
                }
                startActivity(new Intent(ExecutaTreinoActivity.this,AddTreinoActivity.class));
            }
        });
        if(Integer.parseInt(tempo)>0){

            txtExecNome.setText(nomeExercicioIntent);
            txtExecTempo.setText(tempo);
            txtExecRep.setText("");
            btnProx.setVisibility(View.INVISIBLE);
            th.start();

        }else{
            txtExecNome.setText(nomeExercicioIntent);
            txtExecRep.setText(rep);
            txtExecTempo.setText("");
            btnProx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ExecutaTreinoActivity.this, "Exercicio completado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ExecutaTreinoActivity.this,AddTreinoActivity.class));
                }
            });
            btnProx.setVisibility(View.VISIBLE);
        }

    }
}
