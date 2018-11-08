package com.hog.newto.pf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AddTreinoActivity extends AppCompatActivity {
    private Toolbar tlbar;
    private FirebaseAuth fb;
    private String nomeTreino;
    private EditText txtNomeTreino;
    private Button btnSalvaTreino;
    private DbGateway gw;
    private Button btnAddExercicio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treino);
        tlbar = (Toolbar) findViewById(R.id.tlBar);
        setSupportActionBar(tlbar);
        getSupportActionBar().setTitle("Adicionar treino");
        fb=FirebaseAuth.getInstance();

        btnAddExercicio=findViewById(R.id.btnAddExercicio);
        txtNomeTreino=(EditText)findViewById(R.id.txtNomeTreino);


        btnSalvaTreino=(Button)findViewById(R.id.btnSalvaTreino);
        gw.getInstance(AddTreinoActivity.this);



        btnAddExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToExer();
            }
        });



        //Listener pro botão de salvar
        btnSalvaTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //testa se o campo tá vazio, se tiver com coisa ele insere o que tiver lá! xD
                nomeTreino=txtNomeTreino.getText().toString();
                if(!TextUtils.isEmpty(nomeTreino)) {
                    DatabaseHog bd= new DatabaseHog(AddTreinoActivity.this);
                    bd.addTreino(new Treino(nomeTreino));
                    Integer idTreino=bd.buscaultimoTreino();

                    System.out.println("lista"+AddExerciciosActivity.ListEx.size());
                    if(AddExerciciosActivity.ListEx !=null && !AddExerciciosActivity.ListEx.isEmpty()){
                    for(Exercicio ex:AddExerciciosActivity.ListEx ){
                        System.out.println("CU" + ex);
                        ex.setIdTreino(idTreino);
                        bd.addExercicio(ex);

                    }}



                    Toast.makeText(AddTreinoActivity.this,"Treino salvo com sucesso",Toast.LENGTH_LONG).show();

                    sendToMain();
                }else{
                    Toast.makeText(AddTreinoActivity.this,"O treino precisa de um nome", Toast.LENGTH_LONG).show();
                }
            }
        });




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity,menu);
        return true;


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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
    public void sendToConta(){
        Intent intentConta=new Intent(AddTreinoActivity.this,ContaActivity.class);
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


    }public void sendToExer(){
        Intent intentExe=new Intent(AddTreinoActivity.this,AddExerciciosActivity.class);
        startActivity(intentExe);
        finish();
    }
}
