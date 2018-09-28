package com.hog.newto.pf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class AddTreinoActivity extends AppCompatActivity {
    private Toolbar tlbar;
    private FirebaseAuth fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treino);
        tlbar = (Toolbar) findViewById(R.id.tlBar);
        setSupportActionBar(tlbar);
        getSupportActionBar().setTitle("Adicionar treino");
        fb=FirebaseAuth.getInstance();
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
            default:
                return false;

        }
    }

    private void logOut() {

            fb.signOut();
            sendToMain();

    }

    public void sendToMain(){
        Intent intentTreinos= new Intent(AddTreinoActivity.this,TreinosActivity.class);
        startActivity(intentTreinos);
        finish();
    }
}
