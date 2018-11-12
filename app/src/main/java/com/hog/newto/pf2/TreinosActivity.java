package com.hog.newto.pf2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hog.newto.adapter.TreinosAdapter;

import java.util.ArrayList;
import java.util.List;


public class TreinosActivity extends AppCompatActivity {

    private Toolbar tlbar;
    private FirebaseAuth fb;
    private String nome;
    private TreinosAdapter adapter;
    private ListView lvTreinos;
    private List<Treino> lsTreino;
    protected DatabaseReference databaseTreinos;
    static String idS;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treinos);





        lvTreinos=findViewById(R.id.lvTreinos);
        tlbar=(Toolbar)findViewById(R.id.tlBar);
        setSupportActionBar(tlbar);
        getSupportActionBar().setTitle("HoGYM");
        
        lsTreino= new ArrayList<>();
        fb=FirebaseAuth.getInstance();
        user_id=fb.getCurrentUser().getUid();

        databaseTreinos=FirebaseDatabase.getInstance().getReference("Usuarios").child(user_id);
        lvTreinos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Treino t= lsTreino.get(position);

                Intent intent = new Intent(getApplicationContext(), AddTreinoActivity.class);
                intent.putExtra("TREINO_ID",t.getIdTreino());
;
                idS=t.getIdTreino();
                // enfim, chama a activity
                startActivity(intent);
            }
        });

        lvTreinos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Treino t= lsTreino.get(position);
                String i=t.getIdTreino();
                databaseTreinos=FirebaseDatabase.getInstance().getReference("Usuarios").child(user_id).child("Treinos").child(i);
                databaseTreinos.removeValue();
                return true;
            }
        });


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
                Toast.makeText(TreinosActivity.this,"Obrigado por usar o HoGYM",Toast.LENGTH_LONG).show();
                logOut();

                return true;
            //caso a opção de adicionar treino seja selecionada
            case R.id.btnAddExercicio:
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
        String user_id=fb.getCurrentUser().getUid();
        if(currentUser==null){

            //Caso não esteja logado nos leva ao intent de login! VV
            sendToMain();
        }else{
            //logou
            //Adiciona treinos na lista dos treinos
            databaseTreinos=FirebaseDatabase.getInstance().getReference("Usuarios").child(user_id).child("Treinos");
            databaseTreinos.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lsTreino.clear();

                    for(DataSnapshot treinoSnapshot:dataSnapshot.getChildren()){
                        Treino t= treinoSnapshot.getValue(Treino.class);

                        lsTreino.add(t);
                    }
                    TreinosAdapter adapter = new TreinosAdapter(TreinosActivity.this, lsTreino);
                    lvTreinos.setAdapter(adapter);
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
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
        Intent intent=new Intent(TreinosActivity.this,ContaActivity.class);
        intent.putExtra("validador","2");
        startActivity(intent);
        finish();
    }
    }

