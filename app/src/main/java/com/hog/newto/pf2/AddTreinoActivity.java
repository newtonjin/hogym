package com.hog.newto.pf2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hog.newto.adapter.ExerciciosAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddTreinoActivity extends AppCompatActivity {
    private Toolbar tlbar;
    private FirebaseAuth fb;
    private String nomeTreino;
    private EditText txtNomeTreino;
    private Button btnSalvaTreino;
    private DatabaseReference databaseTreinos;
    private DatabaseReference databaseExercicio;
    private Button btnAddExercicio;
    private ListView lvExercicio;
    static Treino t;
    private EditText etxNomeExercicio,etxRep,etxTemp;
    private String nomeEx;
    private Integer qtRep,qtTempo;
    private List<Exercicio> lsExercicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treino);
        tlbar = (Toolbar) findViewById(R.id.tlBar);
        setSupportActionBar(tlbar);
        getSupportActionBar().setTitle("Adicionar treino");
        fb = FirebaseAuth.getInstance();
        etxNomeExercicio=findViewById(R.id.etxNomeExercicio);
        etxRep=findViewById(R.id.etxRep);
        etxTemp=findViewById(R.id.etxTemp);
        btnAddExercicio = findViewById(R.id.btnAddExercicio);
        txtNomeTreino = (EditText) findViewById(R.id.txtNomeTreino);
        lsExercicio=new ArrayList<>();

        databaseTreinos=FirebaseDatabase.getInstance().getReference("Treinos");



        btnSalvaTreino = (Button) findViewById(R.id.btnSalvaTreino);
        Intent intent=getIntent();
        String id=intent.getStringExtra("TREINO_ID");
        if (id == null) {
            id = getIntent().getStringExtra("TREINO_ID");
        } else {
            id = getIntent().getStringExtra("TREINO_ID");
        }
        databaseExercicio=FirebaseDatabase.getInstance().getReference("Exercicios").child(id);

        btnAddExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExercicio();
            }
        });


        //Listener pro botão de salvar
        btnSalvaTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //testa se o campo tá vazio, se tiver com coisa ele insere o que tiver lá! xD

                addTreino();


                Toast.makeText(AddTreinoActivity.this, "Treino salvo com sucesso", Toast.LENGTH_LONG).show();

                sendToMain();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnAddExercicio:
                sendToMain();
                return true;
            case R.id.btnSair:
                logOut();
                return true;
            case R.id.btnConta:
                sendToConta();
                return true;
            default:
                return false;

        }
    }

    private void logOut() {

        fb.signOut();
        sendToMain();

    }

    public void sendToConta() {
        Intent intentConta = new Intent(AddTreinoActivity.this, ContaActivity.class);
        startActivity(intentConta);
        finish();
    }

    public void sendToMain() {
        Intent intentTreinos = new Intent(AddTreinoActivity.this, TreinosActivity.class);
        Bundle nome = new Bundle();
        nome.putString("nome", nomeTreino);
        intentTreinos.putExtras(nome);
        startActivity(intentTreinos);
        finish();


    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseExercicio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lsExercicio.clear();
                for(DataSnapshot exeSnapshot:dataSnapshot.getChildren()){
                    Exercicio ex = exeSnapshot.getValue(Exercicio.class);
                    lsExercicio.add(ex);
                }


                ExerciciosAdapter exerciciosAdapter= new ExerciciosAdapter(AddTreinoActivity.this,lsExercicio);
                lvExercicio.setAdapter(exerciciosAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addTreino() {
        nomeTreino = txtNomeTreino.getText().toString();

        String idTreino = databaseTreinos.push().getKey();
        if (!TextUtils.isEmpty(nomeTreino)) {
            t = new Treino(idTreino, nomeTreino);
            databaseTreinos.child(idTreino).setValue(t);

        } else {
            Toast.makeText(AddTreinoActivity.this, "O treino precisa de um nome", Toast.LENGTH_LONG).show();
        }}public void addExercicio(){



        nomeEx=etxNomeExercicio.getText().toString();
        qtRep=Integer.parseInt(etxRep.getText().toString());
        qtTempo=Integer.parseInt(etxTemp.getText().toString());




        if(!TextUtils.isEmpty(nomeEx)&&!TextUtils.isEmpty(etxRep.getText().toString())&&!TextUtils.isEmpty(etxTemp.getText().toString())){
            try{
                String idExercicio=databaseExercicio.push().getKey();
                Exercicio ex= new Exercicio(idExercicio,nomeEx,qtRep,qtTempo);
                databaseExercicio.child(idExercicio).setValue(ex);
                Toast.makeText(AddTreinoActivity.this,"Exercicio adicionado com sucesso",Toast.LENGTH_LONG).show();

                startActivity(new Intent(AddTreinoActivity.this,TreinosActivity.class));
            }catch (Exception e){
                Toast.makeText(AddTreinoActivity.this,"Erro"+ e, Toast.LENGTH_LONG).show();
            }}else{
            Toast.makeText(AddTreinoActivity.this,"Não pode ter campos vazios",Toast.LENGTH_LONG).show();
        }
        }
    }
