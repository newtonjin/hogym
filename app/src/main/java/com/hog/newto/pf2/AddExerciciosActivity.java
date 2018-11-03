package com.hog.newto.pf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class AddExerciciosActivity extends AppCompatActivity {
    private ImageView addExercicio;
    private EditText etxNomeEx;
    private EditText etxRep;
    private EditText etxTempo;
    private String nomeEx;
    private Integer qtRep;
    private Integer qtTempo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercicios);

        etxNomeEx=(EditText)findViewById(R.id.etxNomeEx);
        etxRep=findViewById(R.id.etxRep);
        etxTempo=findViewById(R.id.etxTempo);
        addExercicio=findViewById(R.id.addExercicio);



        addExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                salvaExercicio();
            }
        });
    }public void mudaTela(){



        Intent intent= new Intent(AddExerciciosActivity.this,AddTreinoActivity.class);
        startActivity(intent);
        finish();

    }public void salvaExercicio(){
        nomeEx=etxNomeEx.getText().toString();
        qtRep=Integer.parseInt(etxRep.getText().toString());
        qtTempo=Integer.parseInt(etxTempo.getText().toString());

        Exercicio ex= new Exercicio(nomeEx,qtRep,qtTempo);

        DatabaseHog bd= new DatabaseHog(AddExerciciosActivity.this);
        try{
            bd.addExercicio(ex);
            Toast.makeText(AddExerciciosActivity.this,"Exercicio adicionado com sucesso",Toast.LENGTH_LONG).show();
            mudaTela();
        }catch (Exception e){
            Toast.makeText(AddExerciciosActivity.this,"Erro"+ e, Toast.LENGTH_LONG).show();
        }
    }
}
