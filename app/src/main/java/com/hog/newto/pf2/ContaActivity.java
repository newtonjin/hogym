package com.hog.newto.pf2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContaActivity extends AppCompatActivity {
    private EditText txtNome;
    private Toolbar tlbar;
    private CircleImageView imgConta;
    private Button btnConfirma;
    private FirebaseAuth fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);
        tlbar=(Toolbar)findViewById(R.id.tlBar);
        setSupportActionBar(tlbar);
        getSupportActionBar().setTitle("Minha Conta");
        txtNome=(EditText)findViewById(R.id.etxNome);
        imgConta=(CircleImageView)findViewById(R.id.imgConta);
        btnConfirma=(Button)findViewById(R.id.btnConfirma);
        fb=FirebaseAuth.getInstance();


        btnConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTreino=new Intent(ContaActivity.this,TreinosActivity.class);
                startActivity(intentTreino);
                finish();
            }
        });

        imgConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Testa permissão do android para acessar ao armazenamento e trocar a foto
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(ContaActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(ContaActivity.this,"Permissão não concedida",Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(ContaActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);



                    }else{
                        Toast.makeText(ContaActivity.this, "Você já tem permissão", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
