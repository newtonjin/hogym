package com.hog.newto.pf2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class AddTreinoActivity extends AppCompatActivity {
    private Toolbar tlbar;
    private FirebaseAuth fb;
    private String nomeTreino;
    private EditText txtNomeTreino;
    private Button btnSalvaTreino;

    DatabaseHog bd= new DatabaseHog(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treino);
        tlbar = (Toolbar) findViewById(R.id.tlBar);
        setSupportActionBar(tlbar);
        getSupportActionBar().setTitle("Adicionar treino");
        fb=FirebaseAuth.getInstance();



        txtNomeTreino=(EditText)findViewById(R.id.txtNomeTreino);


        btnSalvaTreino=(Button)findViewById(R.id.btnSalvaTreino);


        //Listener pro botão de salvar
        btnSalvaTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //testa se o campo tá vazio, se tiver com coisa ele insere o que tiver lá! xD
                nomeTreino=txtNomeTreino.getText().toString();
                if(!TextUtils.isEmpty(nomeTreino)) {
                    bd.addTreino(new Treino(nomeTreino));
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
            case R.id.btnAddTreino:
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


    }
}
