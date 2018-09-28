package com.hog.newto.pf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TreinosActivity extends AppCompatActivity {

    private Toolbar tlbar;
    private FirebaseAuth fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treinos);


        tlbar=(Toolbar)findViewById(R.id.tlBar);
        setSupportActionBar(tlbar);
        getSupportActionBar().setTitle("HoGYM");
        fb=FirebaseAuth.getInstance();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity,menu);
        return true;

    }



//menu de opções
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
           //caso o botão de sair seja selecionado
            case R.id.btnSair:
                logOut();
                return true;
            //caso a opção de adicionar treino seja selecionada
            case R.id.btnAddTreino:
                sendToTreino();
                return true;
            case R.id.btnConta:
                sendToConta();
                return true;
             default:

                 return false;
        }

    }




    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser==null){

            //Caso não esteja logado nos leva ao intent de login! VV
            sendToMain();
        }else{
            //logou

        }
    }
//método para mandar para página de treinos
    private void sendToMain() {

        Intent intentLogin=new Intent(TreinosActivity.this,LoginActivity.class);
        startActivity(intentLogin);
        finish();
    }

//método para deslogar
    private void logOut() {
        fb.signOut();
        sendToMain();
    }
    private void sendToTreino() {

        Intent intentTreino=new Intent(TreinosActivity.this,AddTreinoActivity.class);
        startActivity(intentTreino);
        finish();
    }public void sendToConta() {
        Intent intentConta=new Intent(TreinosActivity.this,ContaActivity.class);
        startActivity(intentConta);
        finish();
    }
}
